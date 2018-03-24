/*
 * Created on 2008-6-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.busyRate.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.activity.resources.dao.IResourceDao;
import server.essp.timesheet.report.LevelCellStyleUtils;
import server.essp.timesheet.report.busyRate.dao.IBusyRateGatherDao;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.timesheet.report.DtoBusyRateGather;
import c2s.essp.timesheet.report.DtoBusyRateQuery;
import c2s.essp.timesheet.report.DtoMonthData;

import com.primavera.integration.client.bo.BusinessObjectException;
import com.wits.excel.ICellStyleSwitch;
import com.wits.util.comDate;

import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsHumanBase;

public class BusyRateGatherServiceImp implements IBusyRateGatherService{
        private IBusyRateGatherDao busyRateDao;
        private IAccountDao accountDao;
        private IResourceDao iresDao;
        private IRmMaintDao rmMaintDao;
        private int scale = 2;
        private Map deptInfoMap = new HashMap();
        private Map empMap = new HashMap();
        private Map calendarNameMap = new HashMap();
        private Map calendarMap = new HashMap();
        private Map accountMap = new HashMap();
        private Map loginIdsMap = new HashMap();
        private String VALID_HOURS = "validHours";
        private String ACTUAL_HOURS = "actualHours";
        
       /**
        * ���������ʱ�䷶Χ�ж��Ƿ���꣬�������������Ϊ��λȡ��ÿ���Ӧ������
        * @param dtoQuery DtoUtilRateQuery
        * @return Map
        */
       public Map getBusyRateInfoByYear(DtoBusyRateQuery dtoQuery) {
               deptInfoMap.clear();
               calendarNameMap.clear();
               calendarMap.clear();
               accountMap.clear();
               empMap.clear();
               loginIdsMap.clear();
               TreeMap resultMap = new TreeMap();
               try{
               setEmpMap();
               Map map = iresDao.getEmployInfoMap();
               //ÿ���˶�Ӧ��Canlendar
               calendarNameMap = (Map)map.get("CanlendarName");
              //ͨ��CanlendarName�õ���Ӧ��Canlendar
               calendarMap = (Map)map.get("Canlendar");
               TsAccount tsAccount = accountDao.loadByAccountId(
                       dtoQuery.getAcntId());
               List acntList = accountDao.listAllDept();
               setAccountMap(acntList);
               if(tsAccount != null){
                String deptIds = getDeptIds(tsAccount.getAccountId());
                setLoginIdsMap(deptIds);
                List lstYear = getYearsList(dtoQuery.getBegin(), dtoQuery.getEnd());
                for (int i = 0; i < lstYear.size(); i++) {
                   DtoBusyRateGather dto = (DtoBusyRateGather) lstYear.get(i);
                   dtoQuery.setBegin(dto.getBeginDate());
                   dtoQuery.setEnd(dto.getEndDate());
                   ITreeNode node = getGatherTreeByMonths(dtoQuery,deptIds,
                           tsAccount.getAccountId(),tsAccount.getAccountName());
                   resultMap.put(comDate.dateToString(dto.getEndDate(), "yyyy"), node);
                }
               }
              }catch(Exception e){
                  e.printStackTrace();
              }
               return resultMap;
       }
       
       
         private void setAccountMap(List accountList){
               if(accountList == null)return;
               for(int i = 0;i<accountList.size();i++){
                   TsAccount acnt = (TsAccount)accountList.get(i);
                   List acntList = (List)accountMap.get(acnt.getParentId());
                   if(acntList == null){
                       acntList = new ArrayList();
                       acntList.add(acnt);
                       accountMap.put(acnt.getParentId(),acntList);
                   }else{
                       acntList.add(acnt);
                   }
               }
         }
       
       /**
        * �õ�ÿ��Ա����ָ������ı�׼��ʱ
        * @param loginId
        * @param begin
        * @param end
        * @return  Double
        */
       private Double getStandardHours(String loginId,Date begin,Date end){
               String empId =  loginId.toUpperCase();
               TsHumanBase human = (TsHumanBase)empMap.get(empId);
               String clandarName = (String)calendarNameMap.get(empId);
               com.primavera.integration.client.bo.object.Calendar calendar = (com.primavera.
                       integration.client.bo.object.Calendar)calendarMap.get(clandarName);
               if(human == null || clandarName == null || calendar == null){
                   return Double.valueOf(0);
               }
               List dateList = getDateList(begin,end,human.getInDate(),human.getOutDate());
               Double standardHours = getStandardHours(calendar,dateList);
               return standardHours;
       }
       
       /**
        * ����Ա�����ţ���ʼ������ʱ��õ���Ӧ�ı�׼��ʱ
        * 
        * @param loginId
        * @param begin
        * @param end
        * @return Double
        */
       private Double getStandardHours(com.primavera.integration.client.bo.object.Calendar calendar
               , List<Date> dList) {
               Double standardHour = Double.valueOf(0);
               for(Date d : dList) {
                   try {
                       standardHour += calendar.getTotalWorkHours(d);
                   } catch (BusinessObjectException e) {
                       e.printStackTrace();
                   }
               }
               return standardHour;
       }
       
       /**
        * ����ʱ�����佫���ڻ��ֳ�������LIST��
        * 
        * @param beginDate
        * @param endDate
        * @return List
        */
       private static List<Date> getDateList(Date beginDate, Date endDate, Date inDate, Date outDate) {
               List dateList = new ArrayList();
               Date bD = (inDate != null && inDate.after(beginDate)) ? inDate : beginDate;
               Date eD = (outDate != null && outDate.before(endDate)) ? outDate : endDate;
               bD = WorkCalendar.resetBeginDate(bD);
               eD = WorkCalendar.resetBeginDate(eD);
               if(eD.before(bD)) {
                   return dateList;
               }
               dateList.add(bD);
               while (eD.after(bD)) {
                   Calendar calendar = Calendar.getInstance();
                   calendar.setTime(bD);
                   calendar.add(Calendar.DATE, 1);
                   Date date = calendar.getTime();
                   dateList.add(date);
                   bD = date;
               }
               return dateList;
       }
       
       private void setEmpMap(){
           List list = rmMaintDao.listDirectHuman();
           if(list != null){
               for(int i= 0;i<list.size();i++){
                   TsHumanBase humanBase = (TsHumanBase)list.get(i);
                   String empId = humanBase.getEmployeeId().toUpperCase();
                   empMap.put(empId,humanBase);
               }
           }
       }
       
       /**
        * ���ݲ��Ŵ����ȡ��ͬ�����ﲿ�ŵĴ���
        * @param deptId
        * @return String
        */
       private String getDeptIds(String deptId) {
               String result = "('')";
               TsAccount account = new TsAccount();
               account.setAccountId(deptId);
               List dList = new ArrayList();
               List deptList = new ArrayList();
               deptList.add(account);
               findDeptChildrenByDeptId(dList, deptList);
               String tmp = "''";
               int size = dList.size();
               for(int i = 0; i < size; i++) {
                   if(i == 0) {
                       tmp = "'" + (String) dList.get(0) + "'";
                   } else {
                       tmp += ", '" + (String) dList.get(i) + "'";
                   }
               }
               result = "(" + tmp + ")";
               return result;
       }
       
       
       /**
        * �ݹ��ѯ�����µ����ﲿ��
        * @param deptMap Map
        * @param deptList List
        */
        private void findDeptChildrenByDeptId(List dList, List<TsAccount> deptList) {
               List list = null;
               for(TsAccount tsAccount : deptList) {
                   dList.add(tsAccount.getAccountId());
                   list = accountDao.listChildrenDeptByParentId(tsAccount.getAccountId());
                   if(list == null || list.size() ==0) {
                       continue;
                   }
                   findDeptChildrenByDeptId(dList, list);
               }
       }
    
       /**
        * ���ݲ�ѯ������ѯ������������������·ݸ�����ʱ���͵�ǰ��ȵ��ܻ�
        * @param dtoQuery DtoUtilRateQuery
        * @return ITreeNode
        */
       private ITreeNode getGatherTreeByMonths(DtoBusyRateQuery dtoQuery,
               String deptIds,String acntId,String acntName) {
                DtoBusyRateGather dtoGather = getDtoDetailInstance();
                dtoGather.setAcntId(acntId);
                dtoGather.setAcntName(acntName);
                dtoGather.setBeginDate(dtoQuery.getBegin());
                dtoGather.setEndDate(dtoQuery.getEnd());
                dtoGather.setSumLevel(0);
                ITreeNode root = new DtoTreeNode(dtoGather);
                getChildren(root,0,deptIds);
                return root;
       }
    
        /**
         * �õ��ӽӵ�
         * @param node ITreeNode
         */
        private void getChildren(ITreeNode node,int j,String depIds) {
               DtoBusyRateGather dto = (DtoBusyRateGather) node.getDataBean();
               String ouId = dto.getAcntId();
               getDtoBusyRate(dto,ouId,depIds);
               //����ouId��ȡdto��ʱ
               List cList = (List)accountMap.get(ouId);
               List chidren = listChildren(cList);
               j=j+1;
               for (int i = 0; i <chidren.size(); i++) {        
                   DtoBusyRateGather dtoChild = (DtoBusyRateGather) chidren.get(i);
                   dtoChild.setBeginDate(dto.getBeginDate());
                   dtoChild.setEndDate(dto.getEndDate());
                   getDtoBusyRate(dtoChild,ouId,depIds);
                   dtoChild.setSumLevel(j);
                   DtoTreeNode child = new DtoTreeNode(dtoChild);
                   node.addChild(child);
                   getChildren(child,j,depIds);
                   addHours(dto, dtoChild);       
               }
       }
    
        /**
         * �õ�ÿ�������µ���ʱ����ڵ�ÿ���·ݵ�ʵ�ʹ�ʱ���в�ֵ��ʱ��
         * �޲�ֵ��ʱ����ʱ����ڵ��ܵ�ʵ�ʹ�ʱ���в�ֵ��ʱ���޲�ֵ��ʱ
         * @param dto DtoBusyRateGather
         * @return DtoBusyRateGather
         */
        private DtoBusyRateGather getDtoBusyRate(DtoBusyRateGather dto,
               String ouId,String depIds) {
               List monList = getMonthsList(dto);
               Double validHour = new Double(0);
               Double invalidHour = new Double(0);;
               Double actualHours = new Double(0);;
               Double standardHour = new Double(0);
               Double assginHours = new Double(0);
               for (int m = 0; m < monList.size(); m++) {
                   String ym = (String) monList.get(m);
                   int month = Integer.valueOf(ym.substring(5,7)).intValue();
                   Date begin = getBeginDate(ym);
                   Date end = getEndDate(ym);
                   String flag = (String)deptInfoMap.get(ym);
                   if(flag == null){
                       //һ���Բ�ԃ���ƶ��r�g�Σ�ָ�����T�µĹ��r��
                       List validHoursList = busyRateDao.getValidHours(
                               begin, end,depIds);
                       setWorkHoursByDeptId(validHoursList,ym,VALID_HOURS);
                       List actualHoursList =  busyRateDao.getActualHours(
                               begin, end,depIds);
                       setWorkHoursByDeptId(actualHoursList,ym,ACTUAL_HOURS);
                       deptInfoMap.put(ym,"exist");
                   }
                   validHour = (Double)deptInfoMap.get(dto.getAcntId()+VALID_HOURS+ym);
                   actualHours = (Double)deptInfoMap.get(dto.getAcntId()+ACTUAL_HOURS+ym);
                   standardHour = getSumStandardHours(ouId,begin,end);
                   if (validHour == null) {
                       validHour = Double.valueOf(0);
                   }
                   if (actualHours == null) {
                       actualHours = Double.valueOf(0);
                   }
    //              �õ����H���r���Юaֵ���r+�o�aֵ���r��
                   if(actualHours > 0 || invalidHour > 0){
                       invalidHour = getInvalidHours(actualHours,validHour);
                   }
                   if(actualHours > 0){
                      assginHours =  standardHour*(validHour/actualHours);
                   }
                   DtoMonthData dtoMonth = new DtoMonthData();
                   dtoMonth.setActualHour(getAmount(actualHours));
                   dtoMonth.setValidHour(getAmount(validHour));
                   dtoMonth.setInvalidHour(getAmount(invalidHour));
                   dtoMonth.setStandardHour(getAmount(standardHour));
                   dtoMonth.setAssignHours(getAmount(assginHours));
                   dto.setMonthData(month-1, dtoMonth);
               }
               Double validHours = dto.getValidHours();
               Double inValidHours = dto.getInvalidHours();
               Double standardHours = dto.getStandardHours();
               dto.setValidHours(getAmount(validHours));
               dto.setInvalidHours(getAmount(inValidHours));
               dto.setActualHours(getAmount(dto.getActualHours()));
               dto.setStandardHours(getAmount(standardHours));
               dto.setAssginHours(getAmount(dto.getAssginHours()));
               return dto;
      }
     
     /**
      * ����������ò����µ�����Ա����ĳ�·ݵ��ܱ�׼��ʱ
      * @param anctId
      * @param begin
      * @param end
      * @return Double
      */        
      private Double getSumStandardHours(String anctId,Date begin,Date end){
             Double sumStandardHours = Double.valueOf(0);
             List list = (List)loginIdsMap.get(anctId);
             if(list == null) return Double.valueOf(0);
             for(int i=0;i<list.size();i++){
                 String loginId = (String)list.get(i);
                 Double sHours = getStandardHours(loginId,begin,end);
                 sumStandardHours +=sHours;
             }
             return sumStandardHours;
      }
    
      /**
       * ��ͬһ�������µ�Ա������ͬһ��LIST��
       * @param deptIds
       */
      private void setLoginIdsMap(String deptIds){
              List list = busyRateDao.getLoginIdsByDeptIds(deptIds);
              if(list == null)return;
              for(int i=0;i<list.size();i++){
                  Object[] obj = (Object[])list.get(i);
                  String loginId = (String)obj[0];
                  String acntId = (String)obj[1];
                  List empIdList = (List)loginIdsMap.get(acntId);
                  if(empIdList != null){
                      empIdList.add(loginId);
                  }else{
                      empIdList = new ArrayList();
                      empIdList.add(loginId);
                      loginIdsMap.put(acntId,empIdList);
                  }
              }
      }
      
       /**
        * �����Ž�ÿ��ʱ��εĹ�ʱ������MAP��
        * @param workHoursList
        * @param ym
        * @param flag
        */
      private void setWorkHoursByDeptId(List workHoursList,String ym,String flag){
             for(int i=0;i<workHoursList.size();i++){
                 Object[] obj = (Object[])workHoursList.get(i);
                 String acntId = (String)obj[0];
                 String isDirect = (String)obj[1];
                 Double workHours = (Double)obj[2];
                 if(isDirect != null && isDirect.equals("D")){
                   deptInfoMap.put(acntId+flag+ym,workHours);
                 }
             }
      }
     
     /**
      * �õ����H���r���Юaֵ���r+�o�aֵ���r��
      * @param validHours
      * @param invalidHours
      * @return Double
      */
      private Double getInvalidHours(Double actualHours,Double validHours){
           BigDecimal aHours = BigDecimal.valueOf(actualHours);
           BigDecimal vHours = BigDecimal.valueOf(validHours);
           BigDecimal invalidHours = aHours.subtract(vHours);
           return invalidHours.doubleValue();
      }
    
      /**
       * ���ӽ��ĸ���ʱ����ڵĸ�����ʱ�ӵ��������
       * @param dto DtoBusyRateGather
       * @param dtoChid DtoBusyRateGather
       */
      private void addHours(DtoBusyRateGather dto,DtoBusyRateGather dtoChid){
          List monList = this.getMonthsList(dto);
           for (int m = 0; m < monList.size(); m++) {
               String ym = (String)monList.get(m);
               int month = Integer.valueOf(ym.substring(5,7));
               DtoMonthData dtoMonth = new DtoMonthData();
               
               Double actHours = dto.getMonthData(month-1).getActualHour()
               + dtoChid.getMonthData(month-1).getActualHour();
               dtoMonth.setActualHour(getAmount(actHours));
               
               Double validHours = dto.getMonthData(month-1).getValidHour()
               + dtoChid.getMonthData(month-1).getValidHour();
               dtoMonth.setValidHour(getAmount(validHours));
               
               Double invalidHours = dto.getMonthData(month-1).getInvalidHour()
               +dtoChid.getMonthData(month-1).getInvalidHour();
               dtoMonth.setInvalidHour(getAmount(invalidHours));
               
               Double standardHour = dto.getMonthData(month-1).getStandardHour()
               +dtoChid.getMonthData(month-1).getStandardHour();
               dtoMonth.setStandardHour(getAmount(standardHour));
               
               Double assignHours = dto.getMonthData(month-1).getAssignHours()
               + dtoChid.getMonthData(month - 1).getAssignHours();
               dtoMonth.setAssignHours(getAmount(assignHours));
               
               dto.setMonthData(month-1, dtoMonth);
           }
      }
      
      /**
       * ���õ��Ľ���������뵽С����ڶ�λ
       * @param ammount
       * @return Double
       */
      private Double getAmount(Double ammount){
              BigDecimal ammountBig = BigDecimal.valueOf(ammount);
              ammountBig = ammountBig.setScale(scale,BigDecimal.ROUND_HALF_UP);
              ammount = ammountBig.doubleValue();
              return ammount;
      }
      
      
      /**
       * �õ��ӽ���LIST
       * @param acntId String
       * @return List
       */
      private List listChildren(List list){
           List nodeList = new ArrayList();
           if(list == null) return new ArrayList();
           for (int i = 0; i < list.size(); i++) {
               TsAccount ts = (TsAccount) list.get(i);
               DtoBusyRateGather dto = getDtoDetailInstance();
               dto.setAcntId(ts.getAccountId());
               dto.setAcntName(ts.getAccountName());
               dto.setAcntRid(ts.getRid());
               nodeList.add(dto);
           }
          return nodeList;
       }
      
         /**
          * ���������ʱ��λ�ò�ѯ���·��ڼ�ĸ����·�
          * @param dto DtoBusyRateGather
          * @return List
          */
         private List getMonthsList(DtoBusyRateGather dto){
                 Date begDate = dto.getBeginDate();
                 Date endDate = dto.getEndDate();
                 String begStr = comDate.dateToString(begDate,"yyyy-MM-dd");
                 String endStr = comDate.dateToString(endDate,"yyyy-MM-dd");
                 String beginYear = begStr.substring(0,4);
                 int begMon = Integer.valueOf(begStr.substring(5,7)).intValue();
                 int endMon = Integer.valueOf(endStr.substring(5,7)).intValue();
                 int i = 0;
                 while(begMon <= endMon){
                     i++;
                     begMon = begMon + 1;
                 }
                 List monList = new ArrayList();
                 int newMon = 0;
                 for(int j=0;j<i;j++){
                     String ym;
                    if(j==0){
                         newMon = Integer.valueOf(begStr.substring(5, 7)).intValue();
                     }
                    if(newMon<10){
                        ym = beginYear + "-0" + newMon;
                    }else{
                        ym = beginYear+"-" + newMon;
                    }
                    monList.add(ym);
                    newMon++;
                 }
                 return monList;
         }
    
         /**
          * ÿ�����µ�
          * @param endYM String
          * @return Date
          */
         private Date getEndDate(String endYM) {
          String beginDate = endYM + "-01";
             Calendar calendar = Calendar.getInstance();
             calendar.setTime(comDate.toDate(beginDate, "yyyy-MM-dd"));
             calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
             Date eDate = calendar.getTime();
             return eDate;
         }
    
         /**
          * �õ�ָ���µ�һ��
          * @param endYM String
          * @return Date
          */
         private Date getBeginDate(String begYM) {
             String beginDate = begYM + "-01";
             Calendar calendar = Calendar.getInstance();
             calendar.setTime(comDate.toDate(beginDate, "yyyy-MM-dd"));
             Date bDate = calendar.getTime();
             return bDate;
         }
    
    
         /**
          * ��ʱ��������껮��
          * @param beginDate Date
          * @param endDate Date
          * @return List
          */
         public List getYearsList(Date beginDate, Date endDate) {
          Date begin = beginDate;
          Date end = endDate;
             List yearList = new ArrayList();
             int begYear = Integer.valueOf(comDate.
                                           dateToString(begin,"yyyy"));
             int endYear = Integer.valueOf(comDate.
                                           dateToString(end,"yyyy"));
             while(endYear >= begYear){
                 DtoBusyRateGather dto = new DtoBusyRateGather();
                 
                 if (endYear == begYear) {
                  end = endDate;
                 } else {
                  end = comDate.toDate(String.valueOf(begYear) + "-12-31");
                 }
                 dto.setBeginDate(begin);
                 dto.setEndDate(end);
                 yearList.add(dto);
                 begYear++;
                 begin = comDate.toDate(begYear + "-01-01");
             }
             return yearList;
         }
    
         /**
          * ���õ���ʱ������ת����"YYYY-MM��~YYYY-MM��"�ĸ�ʽ
          * @param beginDate Date
          * @param endDate Date
          * @return String
          */
         public String getDateStr(Date beginDate, Date endDate) {
             int month = Integer.valueOf(comDate.dateToString(beginDate,
                     "yyyy-MM-dd").substring(5, 7)).intValue() + 1;
             String monthStr = String.valueOf(month);
             if (month < 10) {
                 monthStr = "0" + String.valueOf(month);
             }
             String begDate = comDate.dateToString(beginDate,
                                                   "yyyy-MM-dd").substring(0, 5) + monthStr;
             String eDate = comDate.dateToString(endDate,
                                                 "yyyy-MM-dd").substring(0, 7);
             String dateStr = begDate + "�� ~ " + eDate + "��";
             return dateStr;
         }

    
       public void setBusyRateDao(IBusyRateGatherDao busyRateDao) {
            this.busyRateDao = busyRateDao;
        }

      public void setAccountDao(IAccountDao accountDao) {
           this.accountDao = accountDao;
       }
    
       private DtoBusyRateGather getDtoDetailInstance() {
           if (excelDto) {
               return new StyledDtoUtilRate();
           } else {
               return new DtoBusyRateGather();
           }
       }
    
       public class StyledDtoUtilRate extends DtoBusyRateGather implements
               ICellStyleSwitch {
           public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
               return LevelCellStyleUtils.getStyleByName(styleName, cellStyle);
           }
           public String getStyleName(String propertyName) {
               return LevelCellStyleUtils.getStyleName(this);
           }
       }
    
       private boolean excelDto = false;
    
       public void setExcelDto(boolean excelDto) {
           this.excelDto = excelDto;
       }
    
       public void setIresDao(IResourceDao iresDao) {
           this.iresDao = iresDao;
       }
    
       public void setRmMaintDao(IRmMaintDao rmMaintDao) {
           this.rmMaintDao = rmMaintDao;
       }
        
     

}
