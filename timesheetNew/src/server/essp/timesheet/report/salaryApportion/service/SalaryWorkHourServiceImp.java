/*
 * Created on 2008-1-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.salaryApportion.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.activity.resources.dao.IResourceDao;
import server.essp.timesheet.code.codevalue.dao.ICodeValueDao;
import server.essp.timesheet.period.dao.IPeriodDao;
import server.essp.timesheet.preference.dao.IPreferenceDao;
import server.essp.timesheet.report.salaryApportion.dao.ISalaryWorkHourDao;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetDao;
import server.framework.common.BusinessException;
import c2s.dto.DtoComboItem;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.user.DtoRole;
import c2s.essp.timesheet.report.DtoSalaryApportion;
import c2s.essp.timesheet.report.DtoSalaryWkHrQuery;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

import com.primavera.integration.client.bo.BusinessObjectException;
import com.wits.util.comDate;

import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsCodeValue;
import db.essp.timesheet.TsHumanBase;

/**
 * SalaryWorkHourServiceImp薪資分攤報表
 * 
 * @author TuBaoHui
 * 
 */
public class SalaryWorkHourServiceImp implements ISalaryWorkHourService {
    
        private ISalaryWorkHourDao salaryWkHrDao;
    
        private IRmMaintDao rmMaintDao;
    
        private ICodeValueDao codeValueDao;
    
        private IAccountDao accountDao;
    
        private IPeriodDao periodDao;
    
        private ITimeSheetDao timeSheetDao;
    
//      private ITimeSheetP3ApiDao timeSheetApiDao;
    
        private IPreferenceDao preferenceDao;
        
        private IResourceDao iresDao;
        
        Map empMap = new HashMap();
        
        Map expTypeMap = new HashMap();
        
        Map approvedMap = new HashMap();
        
        Map subMap = new HashMap();
        
        Map calendarNameMap = new HashMap();
        
        Map calendarMap = new HashMap();
        
        Map accountMap = new HashMap();
        
        Map codeValueMap = new HashMap();
        
        /**
         * 根据查询条件列出工时汇总记录
         * 
         * @param loginId
         * @param beginDate
         * @param endDate
         * @return List
         * @throws Exception 
         */
        public List queryByCondition(String loginId, DtoSalaryWkHrQuery dtoQry) {
                empMap.clear();
                expTypeMap.clear();
                approvedMap.clear();
                subMap.clear();
                calendarNameMap.clear();
                calendarMap.clear();
                accountMap.clear();
                codeValueMap.clear();
                if (dtoQry.getBeginDate() == null) {
                    return null;
                }
                List resultList = new ArrayList();
                try{
                    //根据SITE,開始,結束日期得到SITE下对应有效期（入职、离职日期）范围内的人员
                    getDatePeriod(dtoQry);
                    String site = dtoQry.getSite();
                    Date begin = dtoQry.getBeginDate();
                    Date end = dtoQry.getEndDate();
                    List unApproveList = timeSheetDao.getApprovedTsList(begin, end);
                    List unSubmitList = timeSheetDao.getSubmitTsList(begin, end);
                    setApprovedMap(unApproveList);
                    setSubMap(unSubmitList);
                    Map map = iresDao.getEmployInfoMap();
                    List accountList = accountDao.listNormalAccounts();
                    setAccountMap(accountList);
                    //每个人对应的Canlendar
                    calendarNameMap = (Map)map.get("CanlendarName");
                    //通过CanlendarName得到对应的Canlendar
                    calendarMap = (Map)map.get("Canlendar");
                    List userList = rmMaintDao.listEnableLoginId(site, begin, end);
                    boolean needApprove = isNeedApprove(site);
                    List qryList = salaryWkHrDao.queryByCondition(site, begin, end,
                                needApprove);
                    if (qryList != null && qryList.size() > 0) {
                            getResultList(resultList, qryList, begin, end, site);
                    }
                    // 将未填写工时单的记录挂在员工对应的部门下面按标准工时补齐，如果已填写的工时但中有直接挂在部门下的项目则将和未填写的工时单合并在一起
                    getUnfillData(resultList, begin, end, userList,needApprove);
                }catch(Exception e){
                    e.printStackTrace();
                    throw new BusinessException(e.getMessage(), e.getMessage());
                }
                return resultList;
        }
         
        /**
         * 將已審批的數據放入緩存
         * @param unAppList
         */
        private void setApprovedMap(List unAppList){
                String key = "";
                if(unAppList != null){
                    for(int i=0;i<unAppList.size();i++){
                        Object[] obj = (Object[])unAppList.get(i);
                        Long tsId = (Long)obj[0];
                        String loginId = (String)obj[1];
                        Date begin = (Date)obj[2];
                        Date end = (Date)obj[3];
                        key = loginId.toUpperCase() + begin + end;
                        approvedMap.put(key,tsId);
                    }
                }
        }
        
        /**
         * 將未已經提交的數據放入緩存
         * @param unAppList
         */
        private void setSubMap(List unAppList){
                String key = "";
                if(unAppList != null){
                    for(int i=0;i<unAppList.size();i++){
                        Object[] obj = (Object[])unAppList.get(i);
                        Long tsId = (Long)obj[0];
                        String loginId = (String)obj[1];
                        Date begin = (Date)obj[2];
                        Date end = (Date)obj[3];
                        key = loginId.toUpperCase() + begin + end;
                        subMap.put(key,tsId);
                    }
                }
      }
        
        /**
         * 將有效的專案或部門放入緩存中
         * @param accountList
         */
        private void setAccountMap(List accountList){
                if(accountList != null){
                    for(int i=0;i<accountList.size();i++){
                        TsAccount account = (TsAccount)accountList.get(i);
                        accountMap.put(account.getAccountId(),account);
                    }
                }
        }
        
        /**
         * 得到月开始和结束日期
         * @param dtoQry
         */
         private void getDatePeriod(DtoSalaryWkHrQuery dtoQry){
                Boolean isBalanceOne = dtoQry.getIsBalanceOne();
                String dateStr = comDate.dateToString(dtoQry.getBeginDate(), comDate.pattenDate);
                Date beginDate = null;
                Date endDate = null;
                if(isBalanceOne){
                    beginDate = getBeginDate(dateStr,26);
                    endDate = getEndDate(dateStr,25);
                }else{
                    beginDate = getBeginDate(dateStr,1);
                    endDate = getEndDate(dateStr,0);
                }
                dtoQry.setBeginDate(beginDate);
                dtoQry.setEndDate(endDate);
        }
        
         /**
          * isNeedApprove
          * @param site
          * @return boolean
          */
        private boolean isNeedApprove(String site) {
                String type = (String)expTypeMap.get(site);
                if(type == null){
                 type = preferenceDao.loadPreferenceBySite(site).getExportType();
                 expTypeMap.put(site,type);
                }
                if (type == null || "".equals(type)
                        || DtoTimeSheet.STATUS_APPROVED.equals(type)) {
                    return true;
                 } else {
                    return false;
                }
        }

        /**
         * 得到未填寫周報的員工資料，将未填写工时单的记录挂在员工对应的部门下面按标准工时补齐，
         * 如果已填写的工时但中有直接挂在部门下的项目则将和未填写的工时单合并在一起
         * 
         * @param dtoQuery
         * @return List
         * @throws Exception
         */
        private List getUnfillData(List resultList, Date begin, Date end,
                List loginIdList,boolean needApprove) {
                String dateStr = comDate.dateToString(end, comDate.pattenDate);
                int year = Integer.valueOf(dateStr.substring(0, 4)).intValue();
                int month = Integer.valueOf(dateStr.substring(5, 7)).intValue();
                com.primavera.integration.client.bo.object.Calendar calendar;
    //            Date[] ioDate;
                List priodLst = null;
                try {
                    priodLst = periodDao.getPeriodByDate(begin, end);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if (loginIdList == null || priodLst == null) {
                    return resultList;
                }
                for (int i = 0; i < loginIdList.size(); i++) {
                    TsHumanBase humanBase = (TsHumanBase) loginIdList.get(i);
                    String loginId = humanBase.getEmployeeId().toUpperCase();
                    Long tsId;
                    DtoSalaryApportion dto = new DtoSalaryApportion();
                    Double workHours = Double.valueOf(0);
                    try {
    //                  ioDate = timeSheetApiDao.getInOutDate(loginId);
                        String calName = (String)calendarNameMap.get(loginId);
                        calendar = (com.primavera.integration.client.bo.object.Calendar)calendarMap.
                        get(calName);
                        if(calendar == null){
                            String Msg = "LoginId:" + loginId
                            + " has not standard hours in Primavera!";
                            throw new BusinessException(Msg, Msg);
                        }
                    } catch (Exception e) {
                        String Msg = "LoginId:" + loginId
                                + " has not standard hours in Primavera!";
                        throw new BusinessException(Msg, Msg);
                    }
                    for (int j = 0; j < priodLst.size(); j++) {
                        DtoTimeSheetPeriod dtoPeriod = (DtoTimeSheetPeriod) priodLst
                                .get(j);
                        String bDate = comDate.dateToString(dtoPeriod.getBeginDate(),comDate.pattenDate);
                        String eDate = comDate.dateToString(dtoPeriod.getEndDate(),comDate.pattenDate);
                        String key = loginId + bDate + eDate;
                        if (needApprove) {
                            tsId =(Long)approvedMap.get(key);
                        } else {
                            tsId = (Long)subMap.get(key);
                        }
                        if (tsId != null && tsId.equals(dtoPeriod.getTsId())) {
                            continue;
                        }
                        workHours = workHours+ getStandardHours(calendar, 
                                    getDateList(dtoPeriod.getBeginDate(), dtoPeriod.
                                    getEndDate(),humanBase.getInDate(), humanBase.getOutDate()));
                    }
                    if(workHours.equals(0D)) {
                        continue;
                    }
                    TsAccount account =(TsAccount)accountMap.get(humanBase.getUnitCode());
                    if (account != null) {
                        dto.setPrjName(account.getAccountName());
                        dto.setAchieveBelong(account.getAchieveBelong());
                    }
                    dto.setCompId(humanBase.getSite());
                    dto.setEmpId(humanBase.getEmployeeId());
                    dto.setPrjCode(humanBase.getUnitCode());
                    dto.setEmpName(humanBase.getFullName());
                    dto.setYear(year);
                    dto.setMonth(month);
                    dto.setWorkHours(workHours);
                    dto.setLeaveHours(Double.valueOf(0));
                    dto.setOvertimeHours(Double.valueOf(0));
                    resultList.add(dto);
                }
                return resultList;
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
    
        /**
         * 根据查询到的结果放入结果记录中
         * 
         * @param resultList
         * @param qryList
         * @param begin
         * @param end
         * @param site
         */
        private void getResultList(List resultList, List qryList, Date begin,
                Date end, String site) {
                String dateStr = comDate.dateToString(end, comDate.pattenDate);
                int year = Integer.valueOf(dateStr.substring(0, 4)).intValue();
                int month = Integer.valueOf(dateStr.substring(5, 7)).intValue();
                if (qryList != null && qryList.size() > 0) {
                for (int i = 0; i < qryList.size(); i++) {
                    Object[] obj = (Object[]) qryList.get(i);
                    String loginId = (String) obj[0];
                    String accountId = (String) obj[1];
                    String accountName = (String)obj[2];
                    Long codeValueRid = (Long) obj[3];
                    Boolean isLeaveType = (Boolean) obj[4];
                    String achieveBelong = (String) obj[5];
                    Double workHours = (Double) obj[6];
                    Double overtimeHours = (Double) obj[7];
                    DtoSalaryApportion dtoSalaryApp = new DtoSalaryApportion();
                    dtoSalaryApp.setPrjCode(accountId);
                    dtoSalaryApp.setPrjName(accountName);
                    dtoSalaryApp.setAchieveBelong(achieveBelong);
                    String phaseCode = getCodeValueName(codeValueRid);
                    if (isLeaveType != null && isLeaveType) {
                        dtoSalaryApp.setLeaveHours(workHours);
                        dtoSalaryApp.setWorkHours(Double.valueOf(0));
                    }else{
                        dtoSalaryApp.setWorkHours(workHours);
                        dtoSalaryApp.setLeaveHours(Double.valueOf(0));
                    }
                    if (overtimeHours != null) {
                        dtoSalaryApp.setOvertimeHours(overtimeHours);
                    } else {
                        dtoSalaryApp.setOvertimeHours(Double.valueOf(0));
                    }
                    dtoSalaryApp.setCompId(site);
                    dtoSalaryApp.setEmpId(loginId);
                    dtoSalaryApp.setEmpName(getEmpFullName(loginId));
                    dtoSalaryApp.setPhaseCode(phaseCode);
                    dtoSalaryApp.setYear(year);
                    dtoSalaryApp.setMonth(month);
                    resultList.add(dtoSalaryApp);
                }
            }
        }
        
         /**
          * 根据loginId判斷該員工是否在緩存中存在，如果存在则取得员工全稱（英文+中文），如果不存在則查詢數據庫，并將該員工的信息放入緩存中
          * @param loginId
          * @return String
          */
         private String getEmpFullName(String loginId){
             TsHumanBase hb = (TsHumanBase)empMap.get(loginId);
             if(hb != null){
                 return hb.getFullName();
             }else{
                 TsHumanBase humanBase = rmMaintDao.loadHumanById(loginId);
                 if(humanBase != null){
                 empMap.put(loginId,humanBase);
                 return humanBase.getFullName();
                 }
             }
             return null;
         }
    
        /**
         * 根据RID得到CodeValue代码
         * 
         * @param rid
         * @return String
         */
        private String getCodeValueName(Long rid) {
            String codeValueName =  (String)codeValueMap.get(rid);
            if (codeValueName == null) {
                TsCodeValue codeValue = codeValueDao.findCodeValue(rid);
                if(codeValue != null){
                    codeValueName = codeValue.getName();
                    codeValueMap.put(rid,codeValueName);
                }
            }
            return codeValueName;
        }
    
        /**
         * 得到月结束时间
         * 
         * @param endDate
         * @return Date
         */
        private Date getEndDate(String ym,int date) {
                int year = Integer.valueOf(ym.substring(0,4));
                int month = Integer.valueOf(ym.substring(5,7))-1;
                Calendar calendar = Calendar.getInstance();
                if(date != 25){
                 calendar.setTime(comDate.toDate(ym, comDate.pattenDate));
                 calendar.add(Calendar.DAY_OF_MONTH, calendar
                        .getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
                }else{
                    calendar.set(year,month,date);
                }
                Date eDate = WorkCalendar.resetEndDate(calendar.getTime());
                return eDate;
        }
    
        /**
         * 得到月起始时间
         * 
         * @param endYM
         *            String
         * @return Date
         */
        private Date getBeginDate(String begYM,int date) {
                int year = Integer.valueOf(begYM.substring(0,4));
                int month = Integer.valueOf(begYM.substring(5,7))-1;
                if(date == 26){
                    if(month==0){
                        year--;
                        month = 11;
                    }else{
                        month--;
                    }
                }
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,date);
                Date beginDate = WorkCalendar.resetBeginDate(calendar.getTime());
                return beginDate;
        }
        
        /**
         * 得到不重復SITE，且判斷當前登錄者是否為PMO
         * @param roleList
         * @return Map
         */
        public Map getSiteList(List rolesList,String loginId) {
                Map map = new HashMap();
                String site = "";
                List siteList = new ArrayList();
                TsHumanBase tshb = rmMaintDao.loadHumanById(loginId);
                if(tshb != null){
                    site = tshb.getSite();
                }
                Boolean isPMO = false;
                DtoRole dtoRole = null;
                Vector siteVector = new Vector();
                if(rolesList != null && rolesList.size() > 0){
                 for(int i = 0;i<rolesList.size();i++){  
                  dtoRole = (DtoRole)rolesList.get(i);
                  String roleId = dtoRole.getRoleId();
                  if(DtoRole.ROLE_APO.equals(roleId) || DtoRole.ROLE_HAP.
                          equals(roleId)||DtoRole.ROLE_UAP.equals(roleId)
                          || DtoRole.ROLE_SYSUSER.equals(roleId)){
                      isPMO = true;
                  }
                }
                if(isPMO){
                  siteList = rmMaintDao.getSiteFromHumanBase(site);
                 }
               }
                siteVector = beanList2DtoComboItemList(site,siteList);
                map.put(DtoSalaryWkHrQuery.DTO_SITE_LIST,siteVector);
                map.put(DtoSalaryWkHrQuery.DTO_IS_PMO,isPMO);
                return map;
        }
        
        /**
         * 將Site集合放入Vector中
         * @param beanList
         * @return Vector
         */
        private static Vector beanList2DtoComboItemList(String site,
                List beanList) {
                Vector siteVector = new Vector(beanList.size());
                if(beanList != null){
                 siteVector.add(new DtoComboItem(site,site));
                 for (int i=0;i<beanList.size();i++) {
                    String sites = (String)beanList.get(i);
                    DtoComboItem dto = new DtoComboItem(sites,sites);
                    siteVector.add(dto);
                 }
                }
                return siteVector;
        }
        
        
        public void setSalaryWkHrDao(ISalaryWorkHourDao salaryWkHrDao) {
            this.salaryWkHrDao = salaryWkHrDao;
        }
    
        public void setAccountDao(IAccountDao accountDao) {
            this.accountDao = accountDao;
        }
    
        public void setCodeValueDao(ICodeValueDao codeValueDao) {
            this.codeValueDao = codeValueDao;
        }
    
        public void setRmMaintDao(IRmMaintDao rmMaintDao) {
            this.rmMaintDao = rmMaintDao;
        }
    
        public void setPeriodDao(IPeriodDao periodDao) {
            this.periodDao = periodDao;
        }
    
        public void setTimeSheetDao(ITimeSheetDao timeSheetDao) {
            this.timeSheetDao = timeSheetDao;
        }
    
        public IRmMaintDao getRmMaintDao() {
            return rmMaintDao;
        }
    
//      public void setTimeSheetApiDao(ITimeSheetP3ApiDao timeSheetApiDao) {
//          this.timeSheetApiDao = timeSheetApiDao;
//      }
//    
        public void setPreferenceDao(IPreferenceDao preferenceDao) {
            this.preferenceDao = preferenceDao;
        }
    
        public static long getCTime() {
            return new Date().getTime();
        }

        public void setIresDao(IResourceDao iresDao) {
            this.iresDao = iresDao;
        }
    }
