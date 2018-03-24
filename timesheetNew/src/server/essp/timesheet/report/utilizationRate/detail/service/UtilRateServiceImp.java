package server.essp.timesheet.report.utilizationRate.detail.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.account.dao.LgAccountPrivilege;
import server.essp.timesheet.activity.resources.dao.IResourceDao;
import server.essp.timesheet.period.dao.IPeriodDao;
import server.essp.timesheet.report.LevelCellStyleUtils;
import server.essp.timesheet.report.utilizationRate.detail.dao.IUtilRateDao;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import server.framework.common.BusinessException;
import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.user.DtoRole;
import c2s.essp.timesheet.report.DtoUtilRate;
import c2s.essp.timesheet.report.DtoUtilRateQuery;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import com.primavera.integration.client.bo.BusinessObjectException;
import com.wits.excel.ICellStyleSwitch;
import com.wits.util.comDate;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsHumanBase;
/**
 * <p>Title: </p>
 *
 * <p>Description: UtilRateServiceImp</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tubaohui
 * @version 1.0
 */
public class UtilRateServiceImp implements IUtilRateService {
        private IUtilRateDao utilRateDao;
        private IAccountDao accountDao;
        private IRmMaintDao rmMaintDao;
        private IResourceDao iresDao;
        private IPeriodDao periodDao;
        private boolean excelDto = false;
        private int scale = 2;
        private String ACTUAL = "actual";
        private String VALID = "valid";
        private Map validDMap = new HashMap();
        private Map validCMap = new HashMap();
        private Map monthMap = new HashMap();
        private Map empMap = new HashMap();
        private Map calendarNameMap = new HashMap();
        private Map calendarMap = new HashMap();
    
        /**
         * 以DAY为单位查询在时间区域内的人员使用率及实际工时和有产值工时
         * @param dtoQuery DtoUtilRateQuery
         * @param userList Vector
         * @return List
         * @throws Exception 
         */
        public List getUtilRateData(DtoUtilRateQuery dtoQuery, Vector userList) {
              validDMap.clear();
              calendarNameMap.clear();
              calendarMap.clear();
              empMap.clear();
              List queryList = new ArrayList();
              List existDateList = new ArrayList();  
              DtoUtilRate dtoTotal = getDtoDetailInstance();
              dtoTotal.setSumLevel(2);
              DtoUtilRate dto;
              DtoUtilRate dtoSum = getDtoDetailInstance();
              dtoSum.setSumLevel(1);
              String key;
              try{
               List loginIdList = getDtoUtilQuery(dtoQuery,userList);
               if(!dtoQuery.getIsSub()){
                   loginIdList = getLoginIdList(userList);
               }
               List qlist = utilRateDao.getDataByDate(dtoQuery);
               List validHoursList = utilRateDao.getValidHoursByDate(dtoQuery);
               setValidMapByDate(validHoursList);
               setEmpMap();
               Map map = iresDao.getEmployInfoMap();
               //每个人对应的Canlendar
               calendarNameMap = (Map)map.get("CanlendarName");
              //通过CanlendarName得到对应的Canlendar
               calendarMap = (Map)map.get("Canlendar");
               List dateList = getDateList(dtoQuery.getBegin(),dtoQuery.getEnd());
               List empIdTempList = new ArrayList();
               for (int j = 0; j < qlist.size(); j++) {
                    dto = getDtoDetailInstance();
                    dto.setSumLevel(0);
                    Object[] obj = (Object[]) qlist.get(j);
                    String loginId = (String)obj[0];
                    String acntId = (String)obj[1];
                    String eName = (String)obj[2];
                    String cName = (String)obj[3];
                    Date workDate = (Date)obj[4];
                    Double actualHour = (Double)obj[5];
                    if(!empIdTempList.contains(loginId)){
                        empIdTempList.add(loginId);
                    }
                    existDateList.add(workDate);
                    String dateStr = comDate.dateToString(workDate,"yyyy-MM-dd");
                    Double standardHours = getStandardHours(loginId,workDate,workDate);
                    dto.setStandardHours(standardHours);
                    dto.setDateStr(dateStr);
                    dto.setDate(workDate);
                    dto.setAcntId(acntId);
                    dto.setLoginId(loginId);
                    dto.setLoginName(getFullName(eName,cName));
                    //根据查询条件得到有产值工时
                    key = loginId + acntId + workDate;
                    Double vaHour = Double.valueOf(0);
                    Double actHour = Double.valueOf(0);
                    Double validHour = (Double)validDMap.get(key);
                     if (validHour != null) {
                          vaHour = setScale(validHour);
                      }
                     if (actualHour != null) {
                          actHour = setScale(actualHour);
                     }
                      dto.setValidHours(vaHour);
                      dto.setActualHours(actHour);
                      Double rate = getRate(vaHour,standardHours);
                      dto.setUtilRate(setScale(rate));
                     if (dtoSum.getAcntId() == null) {
                          dtoSum.setAcntRid(Long.valueOf(2));
                          dtoSum.setAcntId("Sum");
                          dtoSum.setActualHours(actHour);
                          dtoSum.setValidHours(vaHour);
                          dtoSum.setStandardHours(standardHours);
                          dtoSum.setUtilRate(setScale(rate));
                     } else {
                          dtoSum.setActualHours(actHour +
                                                  dtoSum.getActualHours());
                          dtoSum.setValidHours(vaHour + dtoSum.getValidHours());
                          dtoSum.setStandardHours(standardHours + dtoSum.getStandardHours());
                          Double sumRate = getRate(dtoSum.getValidHours(), 
                                  dtoSum.getStandardHours());
                          dtoSum.setUtilRate(setScale(sumRate));
                    }
                  queryList.add(dto);
                  String loginIdNex = null;
                  if(j+1 < qlist.size()){
                     Object[] objNext = (Object[]) qlist.get(j+1);
                     loginIdNex = (String)objNext[0];
                  }
                  // 如果不是同一个员工
                 if(loginIdNex ==null || !loginIdNex.equals(loginId)){
                     unfillByDate(queryList,dateList,existDateList,dto,dtoSum);
                    dtoSum.setDate(null);
                    dtoSum.setLoginId(null);
                    dtoSum.setLoginName(null);
                   if (queryList != null && queryList.size() > 0) {
                    queryList.add(dtoSum);
                   }
                   if (queryList != null && queryList.size() > 0 &&
                    dtoTotal.getAcntId() == null) {
                    dtoTotal.setAcntId("Total");
                    dtoTotal.setStandardHours(dtoSum.getStandardHours());
                    dtoTotal.setActualHours(dtoSum.getActualHours());
                    dtoTotal.setValidHours(dtoSum.getValidHours());
                    Double tRate = getRate(dtoSum.getValidHours(),dtoSum.getStandardHours());
                    dtoTotal.setUtilRate(setScale(tRate));
                   } else if (queryList != null && queryList.size() > 0) {
                    dtoTotal.setActualHours(dtoSum.getActualHours() +
                                                dtoTotal.getActualHours());
                    dtoTotal.setValidHours(dtoSum.getValidHours() +
                                               dtoTotal.getValidHours());
                    dtoTotal.setStandardHours(dtoSum.getStandardHours() + 
                                             dtoTotal.getStandardHours());
                    Double tRate = getRate(dtoTotal.getValidHours(),dtoTotal.getStandardHours());
                    dtoTotal.setUtilRate(setScale(tRate));
                    }
                    dtoTotal.setDate(null);
                    dtoTotal.setLoginId(null);
                    dtoTotal.setLoginName(null);
                    dtoSum = getDtoDetailInstance();
                    dtoSum.setSumLevel(1);
                    existDateList = new ArrayList();
                 }
               }
               addStandardHoursByEmpId(queryList,dtoTotal,empIdTempList,
                           loginIdList,dateList,"date",dtoQuery);
               
            if (queryList != null && queryList.size() > 0) {
                queryList.add(dtoTotal);
            }
            }catch(Exception e){
                throw new BusinessException("error.UtilRateServiceImp.getUtilRateData",
                        "getUtilRateData is error!",e);
            }
        return queryList;
    }
      
         private String getFullName(final String eName, final String cName) {
            String fullName = eName;
            if(fullName == null) {
                return cName;
            }
            if(cName != null && "".equals(cName) == false) {
                fullName += "(" + cName + ")";
            }
            return fullName;
        }
        
      /**
       * 沒有填寫工時單的員工補齊標準工時
       * @param queryList
       * @param dtoTotal
       * @param empIdTempList
       * @param loginIdList
       * @param dateList
       * @param flag
       */
      private void addStandardHoursByEmpId(List queryList,DtoUtilRate dtoTotal,
              List empIdTempList,List loginIdList,List dateList,String flag,
              DtoUtilRateQuery dtoQuery){
              Double totalHours = Double.valueOf(0);
             for(int i=0;i<loginIdList.size();i++){
               Double sumHours = Double.valueOf(0);
               String loginId = ((String)loginIdList.get(i)).trim();
               if(!empIdTempList.contains(loginId) && ((dtoQuery.getIsSub()
                       || dtoQuery.getLoginId() == null) || (!dtoQuery.getIsSub() 
                               &&  dtoQuery.getLoginId()!= null &&  
                               dtoQuery.getLoginId().equals(loginId)))){
                   TsHumanBase human = (TsHumanBase)empMap.get(loginId.trim().toUpperCase());
                   if(human == null){
                      continue;
                   }
                  if(flag.equals("date")){
                      sumHours = addByDate(queryList,dateList,sumHours,human);
                  }else if("cycle".equals(flag)){
                      sumHours = addByCycle(queryList,dateList,sumHours,human);
                  }else{
                      sumHours = addByMonth(queryList,dateList,sumHours,human);
                  }
                  if(sumHours > 0){
                   totalHours += sumHours;
                   DtoUtilRate dtoSum = getDtoDetailInstance();
                   dtoSum.setSumLevel(1);
                   dtoSum.setAcntId("Sum");
                   dtoSum.setStandardHours(sumHours);
                   dtoSum.setValidHours(Double.valueOf(0));
                   dtoSum.setActualHours(Double.valueOf(0));
                   dtoSum.setUtilRate(Double.valueOf(0));
                   queryList.add(dtoSum);
                  }
              }
          }
          if(dtoTotal.getAcntId() == null){
              dtoTotal.setAcntId("Total");
              dtoTotal.setActualHours(Double.valueOf(0));
              dtoTotal.setUtilRate(Double.valueOf(0));
              dtoTotal.setValidHours(Double.valueOf(0));
              dtoTotal.setStandardHours(Double.valueOf(0));
          }
          dtoTotal.setStandardHours(dtoTotal.getStandardHours()+totalHours);
          Double tRate = getRate(dtoTotal.getValidHours(),dtoTotal.getStandardHours());
          dtoTotal.setUtilRate(setScale(tRate));  
      }
      
      /**
       * 按天得到員工的標準工時
       * @param queryList
       * @param dateList
       * @param sumHours
       * @param human
       * @return Double
       */
      private Double addByDate(List queryList,List dateList,Double sumHours,
              TsHumanBase human){
          for(int j=0;j<dateList.size();j++){
              DtoUtilRate dto = getDtoDetailInstance();
              Date date = (Date)dateList.get(j);
              Double standardHours = getStandardHours(human.
                                  getEmployeeId(),date,date);
              if(standardHours <=0){
                  continue;
              }
              String dateStr = comDate.dateToString(date,"yyyy-MM-dd");
              dto.setSumLevel(0);
              dto.setStandardHours(standardHours);
              dto.setValidHours(Double.valueOf(0));
              dto.setActualHours(Double.valueOf(0));
              dto.setDateStr(dateStr);
              dto.setAcntId(human.getUnitCode());
              dto.setLoginId(human.getEmployeeId());
              dto.setLoginName(human.getFullName());
              dto.setUtilRate(Double.valueOf(0));
              queryList.add(dto);
              sumHours += standardHours;
          }
          return sumHours;
      }
      
      /**
       * 按周期計算員工的標準工時
       * @param queryList
       * @param dateList
       * @param sumHours
       * @param human
       * @return Double
       */
      private Double addByCycle(List queryList,List dateList,Double sumHours,
              TsHumanBase human){
              for(int j=0;j<dateList.size();j++){
               DtoUtilRate dto = getDtoDetailInstance();
               DtoTimeSheetPeriod dtoPeriod = (DtoTimeSheetPeriod) dateList.get(j);
               String bDate = comDate.dateToString(dtoPeriod.getBeginDate(),comDate.pattenDate);
               String eDate = comDate.dateToString(dtoPeriod.getEndDate(),comDate.pattenDate);
               String dateStr = bDate  + "~"+ eDate;
               Double standardHours = getStandardHours(human.getEmployeeId(),dtoPeriod.
                      getBeginDate(),dtoPeriod.getEndDate());
               if(standardHours <=0){
                  continue;
               }
               dto.setSumLevel(0);
               dto.setStandardHours(standardHours);
               dto.setValidHours(Double.valueOf(0));
               dto.setActualHours(Double.valueOf(0));
               dto.setDateStr(dateStr);
               dto.setAcntId(human.getUnitCode());
               dto.setLoginId(human.getEmployeeId());
               dto.setLoginName(human.getFullName());
               dto.setUtilRate(Double.valueOf(0));
               queryList.add(dto);
               sumHours += standardHours;
          }
          return sumHours;
      }
        
      /**
       * 按月計算員工的標準工時
       * @param queryList
       * @param monList
       * @param sumHours
       * @param human
       * @return Double
       */
      private Double addByMonth(List queryList,List monList,Double sumHours,
              TsHumanBase human){
          for(int j=0;j<monList.size();j++){
              DtoUtilRate dto = getDtoDetailInstance();
              String ym = (String) monList.get(j);
              Date begin = getBeginDate(ym);
              Date end = getEndDate(ym);
              Double standardHours = getStandardHours(human.getEmployeeId(),begin,end);
              if(standardHours <=0){
                  continue;
              }
              dto.setSumLevel(0);
              dto.setStandardHours(standardHours);
              dto.setValidHours(Double.valueOf(0));
              dto.setActualHours(Double.valueOf(0));
              dto.setDateStr(ym);
              dto.setAcntId(human.getUnitCode());
              dto.setLoginId(human.getEmployeeId());
              dto.setLoginName(human.getFullName());
              dto.setUtilRate(Double.valueOf(0));
              queryList.add(dto);
              sumHours += standardHours;
          }
          return sumHours;
      }
      
        /**
         * 按天將需填寫單員工沒有填寫的工時單補齊
         * @param queryList
         * @param dateList
         * @param existList
         * @param dto
         * @param dtoSum
         */
        private void unfillByDate(List queryList,List dateList,List existList,
            DtoUtilRate dto,DtoUtilRate dtoSum){
               DtoUtilRate dtoUtil = new DtoUtilRate();
               Double standardHours;
               for(int i=0;i<dateList.size();i++){
                   Date date = (Date)dateList.get(i);
                   if(!existList.contains(date)){
                      standardHours = getStandardHours(dto.getLoginId(),date,date);
                      if(standardHours > 0){
                         dtoUtil = new DtoUtilRate();
                         DtoUtil.copyBeanToBean(dtoUtil,dto);
                         dtoUtil.setActualHours(Double.valueOf(0));
                         dtoUtil.setValidHours(Double.valueOf(0));
                         dtoUtil.setStandardHours(standardHours);
                         dtoUtil.setUtilRate(Double.valueOf(0));
                         String dateStr = comDate.dateToString(date,"yyyy-MM-dd");
                         dtoUtil.setDateStr(dateStr);
                         queryList.add(dtoUtil);
                         dtoSum.setStandardHours(dtoSum.getStandardHours()+standardHours);
                         Double sumRate = getRate(dtoSum.getValidHours(),dtoSum.getStandardHours());
                         dtoSum.setUtilRate(setScale(sumRate));
                         }
                     }
                 }
        }
    
        /**
         * 按周期將員工需填寫工時單但沒有填寫工時單補齊
         * @param queryList
         * @param priodLst
         * @param existList
         * @param dto
         * @param dtoSum
         */
        private void unfillByCycle(List queryList,List priodLst,List existList,
            DtoUtilRate dto,DtoUtilRate dtoSum){
                 DtoUtilRate dtoUtil = new DtoUtilRate();
                 Double standardHours;
                 for(int i=0;i<priodLst.size();i++){
                     DtoTimeSheetPeriod dtoPeriod = (DtoTimeSheetPeriod) priodLst.get(i);
                     String bDate = comDate.dateToString(dtoPeriod.getBeginDate(),comDate.pattenDate);
                     String eDate = comDate.dateToString(dtoPeriod.getEndDate(),comDate.pattenDate);
                     String dateStr = bDate  + "~"+ eDate;
                     if(!existList.contains(dateStr)){
                         standardHours = getStandardHours(dto.getLoginId(),dtoPeriod.
                                 getBeginDate(),dtoPeriod.getEndDate());
                         if(standardHours > 0){
                             dtoUtil = new DtoUtilRate();
                             DtoUtil.copyBeanToBean(dtoUtil,dto);
                             dtoUtil.setActualHours(Double.valueOf(0));
                             dtoUtil.setValidHours(Double.valueOf(0));
                             dtoUtil.setStandardHours(standardHours);
                             dtoUtil.setUtilRate(Double.valueOf(0));
                             dtoUtil.setDateStr(dateStr);
                             queryList.add(dtoUtil);
                             dtoSum.setStandardHours(dtoSum.getStandardHours()+standardHours);
                             Double sumRate = getRate(dtoSum.getValidHours(),dtoSum.getStandardHours());
                             dtoSum.setUtilRate(setScale(sumRate));
                         }
                     }
                 }
    }
    
    /**
     * 得到每个员工在指定区间的标准工时
     * @param loginId
     * @param begin
     * @param end
     * @return  Double
     */
    private Double getStandardHours(String loginId,Date begin,Date end){
            String empId =  loginId.trim().toUpperCase();
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
     * 将区间按天为单位分隔
     * @param beginDate
     * @param endDate
     * @return List
     */
    private List getDateList(Date beginDate,Date endDate){
            List dateList = new ArrayList();
            Date bD = WorkCalendar.resetBeginDate(beginDate);
            Date eD = WorkCalendar.resetBeginDate(endDate);
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
    
    /**
     * 根据员工代号，开始，结束时间得到对应的标准工时
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
     * 根据时间区间将日期划分成天存放在LIST中
     * @param beginDate
     * @param endDate
     * @return List
     */
    private static List<Date> getDateList(Date beginDate, Date endDate,
            Date inDate, Date outDate) {
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
    
    /**
     * 
     * 将TsHumanBase中的員工信息放入緩存中
     */
    private void setEmpMap(){
        List list = rmMaintDao.listAllEmployee();
        if(list != null){
            for(int i= 0;i<list.size();i++){
                TsHumanBase humanBase = (TsHumanBase)list.get(i);
                String empId = humanBase.getEmployeeId().trim().toUpperCase();
                empMap.put(empId,humanBase);
            }
        }
    }
    
    /**
     * 按任意區間將有產值工時放入緩存中
     * @param validHoursList
     */
    private void setValidMapByDate(List validHoursList){
            String key = "";
            if(validHoursList != null){
                for(int i=0;i<validHoursList.size();i++){
                    Object[] obj = (Object[])validHoursList.get(i);
                    String loginId = (String)obj[0];
                    String accountId = (String)obj[1];
                    Date workDate = (Date)obj[2];
                    Double workHours = (Double)obj[3];
                    key = loginId + accountId + workDate;
                    validDMap.put(key,workHours);
                }
            }
     }
     
    /**
     * 案工時單周期將有產工時放入緩存中
     * @param validHoursList
     */
     private void setValidMapByCycle(List validHoursList){
            String key = "";
            if(validHoursList != null){
                for(int i=0;i<validHoursList.size();i++){
                    Object[] obj = (Object[])validHoursList.get(i);
                    String loginId = (String)obj[0];
                    String accountId = (String)obj[1];
                    Date beginDate = (Date)obj[2];
                    Date endDate = (Date)obj[3];
                    Double workHours = (Double)obj[4];
                    key = loginId + accountId + beginDate + endDate;
                    validCMap.put(key,workHours);
                }
            }
  }
    
    /**
     * 按月份將實際工時或有產值工時放入緩存中
     * @param hoursList
     * @param ym
     * @param flag
     */
    private void setHoursByMonth(List hoursList,String ym,String flag){
            String key = "";
            if(hoursList != null){
                for(int i=0;i<hoursList.size();i++){
                    Object[] obj = (Object[])hoursList.get(i);
                    String loginId = (String)obj[0];
                    String accountId = (String)obj[1];
                    Double workHours = (Double)obj[2];
                    key = loginId + accountId + ym + flag;
                    monthMap.put(key,workHours);
                }
            }
    }
    
        /**
         * 按TIMESHEET周期得到符合条件的数据
         * @param dtoQuery DtoUtilRateQuery
         * @param manager String
         * @return List
         */
        public List getUtilRateDataByCycle(DtoUtilRateQuery dtoQuery, Vector userList) {
               validCMap.clear();
               calendarNameMap.clear();
               calendarMap.clear();
               empMap.clear();
               List queryList = new ArrayList();
               List periodExistList = new ArrayList();
               DtoUtilRate dto;
               DtoUtilRate dtoTotal = getDtoDetailInstance();
               DtoUtilRate dtoSum = getDtoDetailInstance();
               dtoTotal.setSumLevel(2);
               dtoSum.setSumLevel(1);
               String loginIdNex = null;
               List empIdTempList = new ArrayList();
               String key;
               try{
               List loginIdList = getDtoUtilQuery(dtoQuery,userList);
               if(!dtoQuery.getIsSub()){
                      loginIdList = getLoginIdList(userList);
               }
              List periodList = periodDao.getPeriodByDate(dtoQuery.getBegin(), dtoQuery.getEnd());
              List list = utilRateDao.getDataByCycle(dtoQuery);
              List validHourList = utilRateDao.getValidHourByCycle(dtoQuery);
              setValidMapByCycle(validHourList);
              setEmpMap();
              Map map = iresDao.getEmployInfoMap();
             //每个人对应的Canlendar
              calendarNameMap = (Map)map.get("CanlendarName");
            //通过CanlendarName得到对应的Canlendar
              calendarMap = (Map)map.get("Canlendar");
              for (int j = 0; j < list.size(); j++) {
                  dto = getDtoDetailInstance();
                  dto.setSumLevel(0);
                  Object[] obj = (Object[]) list.get(j);
                  String loginId = (String)obj[0];
                  String acntId = (String)obj[1];
                  String eName = (String)obj[2];
                  String cName = (String)obj[3];
                  Date begin = (Date)obj[4];
                  Date end = (Date)obj[5];
                  Double actualHour = (Double)obj[6];
                  String dateStr = begin + "~" + end;
                  periodExistList.add(dateStr);
                  dto.setAcntId(acntId);
                  dto.setLoginId(loginId);
                  dto.setLoginName(getFullName(eName,cName));
                  dto.setBeginDate(begin);
                  dto.setEndDate(end);
                  dto.setDateStr(dateStr);
                  Double standardHours = getStandardHours(loginId,begin,end);
                  dto.setStandardHours(standardHours);
                  if(!empIdTempList.contains(loginId)){
                      empIdTempList.add(loginId);
                  }
                    //根据查询条件得到有产值工时
                  Double actHours = new Double(0);
                  Double valHours = new Double(0);
                  key = loginId + acntId + begin + end;
                  Double validHour = (Double)validCMap.get(key);
                  if (actualHour != null ) {
                      actHours = setScale(actualHour);
                   }
                  if (validHour != null ) {
                      valHours = setScale(validHour);
                   }
                  dto.setActualHours(actHours);
                  dto.setValidHours(valHours);
                  Double rate = getRate(valHours,standardHours);
                  dto.setUtilRate(setScale(rate));
                  queryList.add(dto);
                  if (dto.getLoginId().equals(loginId)) {
                   if (dtoSum.getAcntId() == null) {
                        dtoSum.setAcntId("Sum");
                        dtoSum.setAcntRid(Long.valueOf(0));
                        dtoSum.setActualHours(actHours);
                        dtoSum.setValidHours(valHours);
                        dtoSum.setStandardHours(standardHours);
                        dtoSum.setUtilRate(setScale(rate));
                     } else {
                        dtoSum.setActualHours(actHours +
                                                  dtoSum.getActualHours());
                        dtoSum.setValidHours(valHours + dtoSum.getValidHours());
                        dtoSum.setStandardHours(standardHours + dtoSum.getStandardHours());
                        Double sumRate = getRate(dtoSum.getValidHours(),dtoSum.getStandardHours());
                        dtoSum.setUtilRate(setScale(sumRate));
                       }
                   }
                 dtoSum.setDate(null);
                 dtoSum.setLoginId(null);
                 dtoSum.setLoginName(null);
                loginIdNex = null;
                if(j+1 < list.size()){
                    Object[] objNext = (Object[]) list.get(j+1);
                    loginIdNex = (String)objNext[0];
                }
                if(loginIdNex ==null || !loginIdNex.equals(loginId)){
                    unfillByCycle(queryList,periodList,periodExistList,dto,dtoSum);
                  if (queryList != null && queryList.size() > 0) {
                    queryList.add(dtoSum);
                 }
                if (queryList != null && queryList.size() > 0 && dtoTotal.getAcntId() == null) {
                    dtoTotal.setAcntId("Total");
                    dtoTotal.setActualHours(dtoSum.getActualHours());
                    dtoTotal.setValidHours(dtoSum.getValidHours());
                    dtoTotal.setStandardHours(dtoSum.getStandardHours());
                    Double tRate = getRate(dtoSum.getValidHours(),dtoSum.getStandardHours());
                    dtoTotal.setUtilRate(setScale(tRate));
                } else if(queryList != null && queryList.size() > 0){
                    dtoTotal.setActualHours(dtoSum.getActualHours() +
                                            dtoTotal.getActualHours());
                    dtoTotal.setValidHours(dtoSum.getValidHours() +
                                           dtoTotal.getValidHours());
                    dtoTotal.setStandardHours(dtoSum.getStandardHours()+
                            dtoTotal.getStandardHours());
                    Double tRate = getRate(dtoTotal.getValidHours(),
                            dtoTotal.getStandardHours());
                    dtoTotal.setUtilRate(setScale(tRate));
                }
                dtoTotal.setDate(null);
                dtoTotal.setLoginId(null);
                dtoTotal.setLoginName(null);
                dtoSum = getDtoDetailInstance();
                dtoSum.setSumLevel(1);
                periodExistList = new ArrayList();
              }
            }
             addStandardHoursByEmpId(queryList,dtoTotal,empIdTempList,
                     loginIdList,periodList,"cycle",dtoQuery);
            if (queryList != null && queryList.size() > 0) {
                queryList.add(dtoTotal);
            }
           }catch(Exception e){
               throw new BusinessException("error.UtilRateServiceImp.getUtilRateDataByCycle",
                       "getUtilRateDataByCycle is error!",e);
           }
            return queryList;
    }
    
        /**
         * 以月为单位查询人员使用率及实际工时和有产值工时
         * @param dtoQuery DtoUtilRateQuery
         * @param userList Vector
         * @return List
         */
        public List getUtilRateDataByMonths(DtoUtilRateQuery dtoQuery,
                                        Vector userList) {
               monthMap.clear();
               calendarNameMap.clear();
               calendarMap.clear();
               empMap.clear();
               List queryList = new ArrayList();
               List empIdTempList = new ArrayList();
               DtoUtilRate dtoTotal = getDtoDetailInstance();
               dtoTotal.setSumLevel(2);
               DtoUtilRate dtoSum = getDtoDetailInstance();
               dtoSum.setSumLevel(1);
               DtoUtilRate dto;
               String key;
               try{
               setEmpMap();
               List loginIdList = getDtoUtilQuery(dtoQuery,userList);
               if(!dtoQuery.getIsSub()){
                   loginIdList = getLoginIdList(userList);
                   
               }
               List list = utilRateDao.getDataBeanByMonths(dtoQuery);
               Map map = iresDao.getEmployInfoMap();
               //每个人对应的Canlendar
               calendarNameMap = (Map)map.get("CanlendarName");
              //通过CanlendarName得到对应的Canlendar
               calendarMap = (Map)map.get("Canlendar");
               List monList = getMonthsList(dtoQuery.getBegin(),dtoQuery.getEnd());
               for (int j = 0; j < list.size(); j++) {
                     Object[] obj = (Object[]) list.get(j);
                     String loginId = (String)obj[0];
                     String eName = (String)obj[1];
                     String cName = (String)obj[2];
                     String acntId =(String)obj[3];
                     dtoSum = getDtoDetailInstance();
                     dtoSum.setSumLevel(1);
                     if(!empIdTempList.contains(loginId)){
                         empIdTempList.add(loginId);
                     }
                     for (int m = 0; m < monList.size(); m++) {
                       String ym = (String) monList.get(m);
                       Date begin = getBeginDate(ym);
                       Date end = getEndDate(ym);
                       dto = getDtoDetailInstance();
                       dto.setAcntId(acntId);
                       dto.setLoginId(loginId);
                       dto.setLoginName(getFullName(eName,cName));
                       dto.setDateStr(ym);
                       dto.setSumLevel(0);
                       dtoQuery.setBegin(begin);
                       dtoQuery.setEnd(end);
                       Double standardHours = getStandardHours(loginId,begin,end);
                       dto.setStandardHours(standardHours);
                       String flag = (String)monthMap.get(ym);
                       if(flag == null){
                          List actualHourList = utilRateDao.getAcutalHourByMonths(
                                dtoQuery);
                          setHoursByMonth(actualHourList,ym,ACTUAL);
                          List validHourList = utilRateDao.getValidHourByMonths(dtoQuery);
                          setHoursByMonth(validHourList,ym,VALID);
                          monthMap.put(ym,"exist");
                       }
                       key = loginId + acntId + ym;
                       Double actualHour = (Double)monthMap.get(key + ACTUAL);
                       Double validHour = (Double)monthMap.get(key + VALID);
                       Double actHour = Double.valueOf(0);
                       Double vaHour = Double.valueOf(0);
                       if (actualHour != null ) {
                          actHour = setScale(actualHour);
                        }
                       if (validHour != null) {
                          vaHour = setScale(validHour);
                        }
                        Double rate = getRate(vaHour,standardHours);
                        dto.setActualHours(actHour);
                        dto.setValidHours(vaHour);
                        dto.setUtilRate(setScale(rate));
                        queryList.add(dto);
                        if (dto.getLoginId().equals(loginId)) {
                            if (dtoSum.getAcntId() == null) {
                                dtoSum.setAcntId("Sum");
                                dtoSum.setAcntRid(Long.valueOf(0));
                                dtoSum.setActualHours(actHour);
                                dtoSum.setValidHours(vaHour);
                                dtoSum.setStandardHours(standardHours);
                                dtoSum.setUtilRate(setScale(rate));
                            }else {
                                dtoSum.setActualHours(actHour +
                                                          dtoSum.getActualHours());
                                dtoSum.setValidHours(vaHour +
                                                         dtoSum.getValidHours());
                                dtoSum.setStandardHours(standardHours+dtoSum.getStandardHours());
                                Double sumRate = getRate(dtoSum.getValidHours(),dtoSum.getStandardHours());
                                dtoSum.setUtilRate(setScale(sumRate));
                            }
                        }
                     dtoSum.setDate(null);
                     dtoSum.setLoginId(null);
                     dtoSum.setLoginName(null);
                     }
                    if (queryList != null && queryList.size() > 0) {
                        queryList.add(dtoSum);
                    }
                    if (queryList != null && queryList.size() > 0 && dtoTotal.getAcntId() == null) {
                        dtoTotal.setAcntId("Total");
                        dtoTotal.setActualHours(dtoSum.getActualHours());
                        dtoTotal.setValidHours(dtoSum.getValidHours());
                        dtoTotal.setStandardHours(dtoSum.getStandardHours());
                        Double tRate = getRate(dtoSum.getValidHours(),dtoSum.getStandardHours());
                        dtoTotal.setUtilRate(setScale(tRate));
                    } else if(queryList != null && queryList.size() > 0){
                        dtoTotal.setActualHours(dtoSum.getActualHours() +
                                                dtoTotal.getActualHours());
                        dtoTotal.setValidHours(dtoSum.getValidHours() +
                                               dtoTotal.getValidHours());
                        dtoTotal.setStandardHours(dtoSum.getStandardHours() +
                                dtoTotal.getStandardHours());
                        Double tRate = getRate(dtoTotal.getValidHours(),dtoTotal.getStandardHours());
                        dtoTotal.setUtilRate(setScale(tRate));
                    }
                    dtoTotal.setDate(null);
                    dtoTotal.setLoginId(null);
                    dtoTotal.setLoginName(null);
              }
               addStandardHoursByEmpId(queryList,dtoTotal,empIdTempList,loginIdList,
                       monList,"month",dtoQuery);
              if (queryList != null && queryList.size() > 0) {
                    queryList.add(dtoTotal);
              }
              }catch(Exception e){
                  throw new BusinessException("error.UtilRateServiceImp.getUtilRateDataByMonths",
                          "getUtilRateDataByMonths is error!",e);
              }
             return queryList;
    }
    
       /**
        * 根據查詢條件將LoginId和部門代碼拼接成集合
        * @param dtoQuery
        * @param userList
        * @return DtoUtilRateQuery
        */
        private List getDtoUtilQuery(DtoUtilRateQuery dtoQuery,Vector userList){
                List loginIdList = null;
                if(dtoQuery.getIsSub()){
                    String deptIds = getDeptIds(dtoQuery.getAcntId());
                    dtoQuery.setDeptIds(deptIds);
                    loginIdList = rmMaintDao.getLoginIdsByDept(deptIds);
                    String loginIds =  getLoginIdsByDepts(loginIdList);
                    dtoQuery.setLoginIds(loginIds);
                 }else{
                  if(dtoQuery.getLoginId() == null){
                     String loginIds =  getLoginIds(userList);
                     dtoQuery.setLoginIds(loginIds);
                  }else{
                    dtoQuery.setLoginIds("('" + dtoQuery.getLoginId() + "')"); 
                  }
                 }
             return loginIdList;
        }
         
         /**
          * 人员使用率
          * @param validHours
          * @param actualHours
          * @return
          */
        private Double getRate(Double validHours, Double actualHours){
                  Double Rate = Double.valueOf(0);
                  if(actualHours != 0){
                      Rate = validHours / actualHours;
                  }
                return Rate;
        }
     
         /**
          * 将得到的金额四舍五入到小数点第二位
          * @param ammount
          * @return Double
          */
         private Double setScale(Double ammount){
                 BigDecimal ammountBig = BigDecimal.valueOf(ammount);
                 ammountBig = ammountBig.setScale(scale,BigDecimal.ROUND_HALF_UP);
                 ammount = ammountBig.doubleValue();
                 return ammount;
         }
         
    
        /**
         * 根据输入的时间段获得查询的月份期间的各个月份（包括跨年）
         * @param dto DtoUtilRateGather
         * @return List
         */
        private List getMonthsList(Date begDate,Date endDate){
                String begStr = comDate.dateToString(begDate,"yyyy-MM-dd");
                String endStr = comDate.dateToString(endDate,"yyyy-MM-dd");
                int beginYear = Integer.valueOf(begStr.substring(0,4)).intValue();
                int endYear = Integer.valueOf(endStr.substring(0,4)).intValue();
                int begMon = Integer.valueOf(begStr.substring(5,7)).intValue();
                int endMon = Integer.valueOf(endStr.substring(5,7)).intValue();
                int i = 0;
                List monList = new ArrayList();
                //如果跨年，则算出年内的月集合
                while(beginYear < endYear && begMon <= 12){
                      i++;
                      if(begMon==12){
                          beginYear++;
                          monList = getMonthList(i,begStr,monList);
                          begStr = beginYear + "-01-01";
                          begMon = 1;
                          i = 0;
                      }else{
                          begMon =  begMon + 1;
                      }
                }
                i=0;
                while(beginYear == endYear && begMon <= endMon){
                    i++;
                    begMon = begMon + 1;
                }
                monList = getMonthList(i,begStr,monList);
                return monList;
        }
    
        /**
         * 根据部门代码获取连同其子孙部门的代码
         * @param deptId
         * @return
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
         * 根据部门代码获取连同其子孙部门的代码
         * @param deptId
         * @return
         */
        private String getLoginIds(Vector userList) {
                String result = "('')";
                String tmp = "''";
                String loginId;
                DtoComboItem dtoCom;
                for(int i = 0; i < userList.size(); i++) {
                    dtoCom = (DtoComboItem) userList.get(i);
                    loginId = (String) dtoCom.getItemValue();
                    if(loginId == null || "".equals(loginId.trim())){
                        continue;
                    }
                    if(i == 0) {
                        tmp = "'" + (String) loginId + "'";
                    } else {
                        tmp += ", '" + (String) loginId + "'";
                    }
                }
                result = "(" + tmp + ")";
                return result;
        }
        
        /**
         *getLoginIdsByDepts
         * @param depts
         * @return
         */
        private String getLoginIdsByDepts(List list){
                String result = "('')";
                String tmp = "''";
                if(list != null){
                    for(int i=0;i<list.size();i++){
                         String loginId = (String)list.get(i);
                         if(i==0){
                             tmp = "'"+loginId+"'";
                         }else{
                             tmp +=","+"'"+loginId+"'";
                         }
                    }
                    result = "(" + tmp + ")";
                }
                return result;
        }
        
        private List getLoginIdList(Vector userList){
             DtoComboItem dtoCom;
             String loginId;
             List loginIdList = new ArrayList();
             for(int i = 0; i < userList.size(); i++) {
                 dtoCom = (DtoComboItem) userList.get(i);
                 loginId = (String) dtoCom.getItemValue();
                if(loginId == null){
                    continue;
                }
                loginIdList.add(loginId);
            }
           return loginIdList;
        }
    
        /**
         * 递归查询部门下的子孙部门
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
         * 得到指定年的月份集合
         * @param i
         * @param begStr
         * @param monList
         * @return List
         */
        private List getMonthList(int i,String begStr,List monList){
                int newMon = 0;
                for(int j=0;j<i;j++){
                    String ym;
                   if(j==0){
                        newMon = Integer.valueOf(begStr.substring(5, 7)).intValue();
                    }
                   if(newMon<10){
                       ym = begStr.substring(0,4) + "-0" + newMon;
                   }else{
                       ym = begStr.substring(0,4)+"-" + newMon;
                   }
                   monList.add(ym);
                   newMon++;
                }
                return monList;
        }
    
        /**
         * 每个月最后一天
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
         * 得到指定月的第一天
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
         * 根据得到的部门代号找出该部门下的员工
         * @param acntRid Long
         * @return List
         */
        public Vector getUserList(String acntId) {
                List userList = new ArrayList();
                List listUser = utilRateDao.listUsers(acntId);
                    for (int j = 0; j < listUser.size(); j++) {
                        TsHumanBase hb = (TsHumanBase) listUser.get(j);
                        if (!isExist(hb.getEmployeeId(), userList)) {
                            userList.add(hb);
                        }
                    }
                return beanList2DtoComItemList(userList);
        }
    
        /**
         * 得到部门集合
         * @param beanList List
         * @return Vector
         */
        private static Vector beanList2DtoComboItemList(List<TsAccount> beanList) {
                Vector dtoList = new Vector(beanList.size());
                for (TsAccount bean : beanList) {
                    String accountName = bean.getAccountId() + "--" + bean.getAccountName();
                    DtoComboItem dto = new DtoComboItem(accountName,
                                                        bean.getAccountId());
                    dtoList.add(dto);
                }
                return dtoList;
        }
    
        /**
         * 得到员工集合
         * @param beanList List
         * @return Vector
         */
        private static Vector beanList2DtoComItemList(List<TsHumanBase>
                beanList) {
                Vector dtoList = new Vector(beanList.size());
                DtoComboItem dto = new DtoComboItem("Select All", null);
                dtoList.add(dto);
                for (TsHumanBase bean : beanList) {
                    dto = new DtoComboItem(bean.getFullName(), bean.getEmployeeId());
                    dtoList.add(dto);
                }
                return dtoList;
        }
    
        /**
         * 判断loginId是否已经存在，如果存在则返回TRUE，否则返回FALSE
         * @param loginId String
         * @param userList List
         * @return Boolean
         */
        private Boolean isExist(String loginId, List userList) {
            if (userList != null && userList.size() > 0) {
                for (int i = 0; i < userList.size(); i++) {
                    TsHumanBase hb = (TsHumanBase) userList.get(i);
                    if (hb.getEmployeeId().equals(loginId)) {
                        return true;
                    }
                }
                return false;
            } else {
                return false;
            }
        }
    
         /**
          * 根据查询或导出动作来判断调用哪个DTO
          * @return DtoUtilRate
          */
        private DtoUtilRate getDtoDetailInstance() {
              if(excelDto) {
                  return new StyledDtoUtilRate();
              } else {
                  return new DtoUtilRate();
              }
          }
    
        /**
          * 得到部门集合
          * @param loginId String
          * @param roleId
          * @return Vector
          */
         public Vector getDeptList(String loginId, String roleId) {
                List deptList = new ArrayList();
                if(DtoRole.ROLE_SYSUSER.equals(roleId)
                 ||DtoRole.ROLE_HAP.equals(roleId)){
                    deptList = utilRateDao.listDepts();
                } else if(DtoRole.ROLE_UAP.equals(roleId)) {
                    LgAccountPrivilege lg = new LgAccountPrivilege();
                    String units = lg.getPrivilegeUnit(loginId);
                    String[] unit = null;
                    if(units != null && !"".equals(units)) {
                        unit = units.split(",");
                    }
                    deptList = new ArrayList<TsAccount>();
                    TsAccount account = null;
                    if (unit != null && unit.length > 0) {
                        for (String unitId : unit) {
                            account = accountDao.loadByAccountId(unitId);
                            if (account != null && account.getAccountId() != null) {
                                deptList.add(account);
                            }
                        }
                    }
                } else {
                    deptList = utilRateDao.listDeptInfo(loginId);
                }
                return beanList2DtoComboItemList(deptList);
            }

        public class StyledDtoUtilRate extends DtoUtilRate
            implements ICellStyleSwitch {
        public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
            return LevelCellStyleUtils.getStyleByName(styleName, cellStyle);
        }
    
        public String getStyleName(String propertyName) {
            return LevelCellStyleUtils.getStyleName(this);
        }
    }

       
    public void setUtilRateDao(IUtilRateDao utilRateDao) {
        this.utilRateDao = utilRateDao;
    }

    public void setExcelDto(boolean excelDto) {
        this.excelDto = excelDto;
    }

    public boolean isExcelDto() {
        return excelDto;
    }
    
    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setRmMaintDao(IRmMaintDao rmMaintDao) {
        this.rmMaintDao = rmMaintDao;
    }

    public void setIresDao(IResourceDao iresDao) {
        this.iresDao = iresDao;
    }

    public void setPeriodDao(IPeriodDao periodDao) {
        this.periodDao = periodDao;
    }
}
