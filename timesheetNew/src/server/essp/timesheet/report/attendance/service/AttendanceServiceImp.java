/*
 * Created on 2008-6-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attendance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.essp.timesheet.activity.resources.dao.IResourceDao;
import server.essp.timesheet.report.attendance.dao.IAttendanceDao;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetP3ApiDao;
import c2s.dto.DtoBase;
import c2s.essp.timesheet.report.DtoAttendance;
import c2s.essp.timesheet.report.DtoAttendanceQuery;
import db.essp.timesheet.TsHumanBase;

/**
 * AttendanceServiceImp
 * @author TBH
 *
 */
public class AttendanceServiceImp  implements IAttendanceService{
        private IAttendanceDao attendanceDao;
        private IResourceDao iresDao;
        private IRmMaintDao rmMaintDao;
        private ITimeSheetP3ApiDao timeSheetApiDao;
        
        private Map calendarNameMap = new HashMap();
        private Map calendarMap = new HashMap();
        private Map wMap = new HashMap();
        private Map leaveMap = new HashMap();
        private boolean excelDto = false;
        
        public List queryAttendance(DtoAttendanceQuery dto)  throws Exception{
               wMap.clear();
               leaveMap.clear();
               calendarNameMap.clear();
               calendarMap.clear();
               Date begin = dto.getBegin();
               Date end = dto.getEnd();
               String site = dto.getSite();
               Map map = iresDao.getEmployInfoMap();
               //每个人对应的Canlendar
               calendarNameMap = (Map)map.get("CanlendarName");
               //通过CanlendarName得到对应的Canlendar
               calendarMap = (Map)map.get("Canlendar");
               List qryList =  attendanceDao.query(dto);
               setWMap(qryList);
               setLeaveMap(begin,end,site);
               List<TsHumanBase> humanList = rmMaintDao.listAllHuman(begin,end,site);
               List resList = getResultList(humanList,begin,end);
               return resList;
        }
        
        /**
         * 得到結果集合
         * @param hList
         * @param begin
         * @param end
         * @return List
         */
        private List getResultList(List<TsHumanBase> hList,Date begin,Date end){
                DtoAttendance dtoAtt = null;
                int seq = 0;
                List resList = new ArrayList();
                com.primavera.integration.client.bo.object.Calendar cal;
                List dateList = getDateList(begin, end);
                for(TsHumanBase hb:hList){
                    seq++;
                    dtoAtt = new DtoAttendance(); 
                    String empId = hb.getEmployeeId();
                    List wList = (List)wMap.get(empId.toUpperCase());
                    if(wList != null){
                        getOutProjHours(wList,dtoAtt);
                    }
                    String cName = (String)calendarNameMap.get(empId.toUpperCase());
                    cal = (com.primavera.integration.client.bo.object.Calendar)calendarMap.
                           get(cName);
                    Double sumHours = Double.valueOf(0);
                    if(cal != null){
                     sumHours = timeSheetApiDao.getSumStandarHours(cal,
                            dateList,hb.getInDate(),hb.getOutDate());
                    }
                    dtoAtt.setEmpId(empId);
                    dtoAtt.setEmpName(hb.getFullName());
                    dtoAtt.setUnitCode(hb.getUnitCode());
                    dtoAtt.setSeq(String.valueOf(seq));
                    dtoAtt.setFullHours(sumHours);
                    getLeaveHours(dtoAtt,empId);
                    Double actualHours = dtoAtt.getActualHours();
                    Double inProjHours = actualHours - dtoAtt.getOutProjHours();
                    dtoAtt.setProjActHours(inProjHours);
                    dtoAtt.setActualHours(actualHours);
                    if(sumHours > 0 && dtoAtt.getProjActHours() != null){
                        Double rate = divide(dtoAtt.getProjActHours(),sumHours);
                        dtoAtt.setInProjRate(rate);
                    }else{
                        dtoAtt.setInProjRate(Double.valueOf(0));
                    }
                    if(sumHours > 0 && actualHours > 0){
                        Double rate = divide(actualHours,sumHours);
                        dtoAtt.setPersonMath(rate);
                    }else{
                        dtoAtt.setPersonMath(Double.valueOf(0));
                    }
                    resList.add(dtoAtt);
                    
                }
                return resList;
            }
        
        
        /**
         * 除法
         * @param hoursO
         * @param hoursT
         * @return Double
         */
        private Double divide(Double hoursO,Double hoursT){
                BigDecimal h1 = BigDecimal.valueOf(hoursO);
                BigDecimal h2 = BigDecimal.valueOf(hoursT);
                BigDecimal  rate = h1.divide(h2,2,BigDecimal.ROUND_HALF_UP);
                return rate.doubleValue();
        }
        
        /**
         * 得到員工的請假工時數
         * @param dtoAtt
         * @param empId
         */
          private void getLeaveHours(DtoAttendance dtoAtt,String empId){
                   List leaveList = (List)leaveMap.get(empId.toUpperCase());
                   if(leaveList == null) return;
                   Double nondeductSum = Double.valueOf(0);
                   Double deductSum = Double.valueOf(0);
                   for(int i = 0;i < leaveList.size();i++){
                         DtoLeave dtoLeave = (DtoLeave)leaveList.get(i);
                         String leaveType = dtoLeave.getLeaveType();
                         Double leaveHours = dtoLeave.getLeaveHours();
                         if(DtoAttendanceQuery.DTO_SICK_LEAVE.equals(leaveType)){
                             dtoAtt.setSickLeave(leaveHours);
                             deductSum += leaveHours;
                         }
                         if(DtoAttendanceQuery.DTO_PRIVATE_LAEAVE.equals(leaveType)){
                             dtoAtt.setPrivateLeave(leaveHours);
                             deductSum += leaveHours;
                         }
                         if(DtoAttendanceQuery.DTO_OHTERS_DEDUCT.equals(leaveType)){
                             dtoAtt.setOthersDuduct(leaveHours);
                             deductSum += leaveHours;
                         }
                         if(DtoAttendanceQuery.DTO_ABSENTEEISM.equals(leaveType)){
                             dtoAtt.setAbsenteeism(leaveHours);
                             deductSum += leaveHours;
                         }
                         if(DtoAttendanceQuery.DTO_SHIFT_ADJUSTMENT.equals(leaveType)){
                             dtoAtt.setShiftAdjsut(leaveHours);
                             nondeductSum += leaveHours;
                         }
                         if(DtoAttendanceQuery.DTO_ANNUAL_LEAVE.equals(leaveType)){
                             dtoAtt.setAnnualLeave(leaveHours);
                             nondeductSum += leaveHours;
                         }
                         if(DtoAttendanceQuery.DTO_MARRIAGE_LEAVE.equals(leaveType)){
                             dtoAtt.setMarrigeLeave(leaveHours);
                             nondeductSum += leaveHours;
                         }
                         if(DtoAttendanceQuery.DTO_MATERNITY_LEAVE.equals(leaveType)){
                             dtoAtt.setMaternityLeave(leaveHours);
                             nondeductSum += leaveHours;
                         }
                         
                         if(DtoAttendanceQuery.DTO_MEDICAL_LEAVE.equals(leaveType)){
                             dtoAtt.setMedicalLeave(leaveHours);
                             nondeductSum += leaveHours;
                         }
                         if(DtoAttendanceQuery.DTO_WORK_INJURY_LEAVE.equals(leaveType)){
                             dtoAtt.setWorkInjuryLeave(leaveHours);
                             nondeductSum += leaveHours;
                         }
                         if(DtoAttendanceQuery.DTO_FUNERAL_LEAVE.equals(leaveType)){
                             dtoAtt.setFuneralLeave(leaveHours);
                             nondeductSum += leaveHours;
                         }
                         if(DtoAttendanceQuery.DTO_LACTATION_LEAVE.equals(leaveType)){
                             dtoAtt.setLactationLeave(leaveHours);
                             nondeductSum += leaveHours;
                         }
                        
                         if(DtoAttendanceQuery.DTO_OHTHERS_NONDEDUCT.equals(leaveType)){
                             dtoAtt.setOthersNondeduct(leaveHours);
                             nondeductSum += leaveHours;
                         }
                        
                     }
                     dtoAtt.setDeductSum(deductSum);
                     dtoAtt.setNondeductSum(nondeductSum);
        }
        
        /**
         * 根据时间区间将日期划分成天存放在LIST中
         * @param beginDate
         * @param endDate
         * @return List
         */
        private List getDateList(Date beginDate, Date endDate){
            List dateList = new ArrayList();
            dateList.add(beginDate);
            while(endDate.after(beginDate)){
                 Calendar calendar = Calendar.getInstance();
                 calendar.setTime(beginDate);
                 calendar.add(Calendar.DATE, 1);
                 Date date = calendar.getTime();
                 dateList.add(date);
                 beginDate = date;
            }
            return dateList;
        }
        
        /**
         * 得到專案代碼集合，出項目工時數（即所有填在部門代碼的工時)
         * @param wList
         * @param dtoAtt
         */
        private void getOutProjHours(List wList,DtoAttendance dtoAtt){
                String acntIds = "";
                Double outProjHours = Double.valueOf(0);
                Double actualHours = Double.valueOf(0);
                for(int j=0;j<wList.size();j++){
                   DtoWorkHours dtowk = (DtoWorkHours)wList.get(j);
                   String acntId = dtowk.getAcntId();
                   String deptFlag = dtowk.getDeptFlag();
                   Double wkHours = dtowk.getWorkHours();
                   if(j==0){
                    acntIds=acntId;
                   }else{
                    acntIds +=","+acntId;
                  }
                  if(deptFlag != null && deptFlag.equals("1")){
                     outProjHours +=wkHours;
                  }
                  actualHours +=wkHours;
               }
               dtoAtt.setProjCode(acntIds);
               dtoAtt.setOutProjHours(outProjHours);
               dtoAtt.setActualHours(actualHours);
        }
        
           private void setWMap(List list){
                if(list == null)return;
                DtoWorkHours dto = null;
                for(int i=0;i<list.size();i++){
                    Object[] obj = (Object[])list.get(i);
                    dto = new DtoWorkHours();
                    String empId = (String)obj[0];
                    dto.setEmpId(empId);
                    dto.setAcntId((String)obj[1]);
                    dto.setDeptFlag((String)obj[2]);
                    dto.setWorkHours((Double)obj[3]);
                    List whList = (List)wMap.get(empId.toUpperCase());
                    if(whList != null){
                        whList.add(dto);
                    }else{
                        whList = new ArrayList();
                        whList.add(dto);
                        wMap.put(empId.toUpperCase(),whList);
                    }
                }
            }
        
        /**
         * 將同一員工的請假工時信息放入同一個LIST中并暫存在緩存中
         * @param begin
         * @param end
         */
        private void setLeaveMap(Date begin,Date end,String site){
               List list =  rmMaintDao.getSumLeaveHoursByDate(begin,end,site);
               DtoLeave dtoLeave = new DtoLeave();
               if(list == null)return;
               for(int i=0;i<list.size();i++){
                   Object[] obj = (Object[])list.get(i);
                   String empId = (String)obj[0];
                   String leaveType = (String)obj[1];
                   Double leaveHours = (Double)obj[2];
                   dtoLeave = new DtoLeave();
                   dtoLeave.setEmpId(empId);
                   dtoLeave.setLeaveType(leaveType);
                   dtoLeave.setLeaveHours(leaveHours);
                   List leaveList = (List)leaveMap.get(empId.toUpperCase());
                   if(leaveList != null){
                       leaveList.add(dtoLeave);
                   }else{
                       leaveList = new ArrayList();
                       leaveList.add(dtoLeave);
                       leaveMap.put(empId.toUpperCase(),leaveList);
                   }
               }
        }

        static class DtoLeave extends DtoBase{
                private String empId;
                private String leaveType;
                private Double leaveHours;
                public String getEmpId() {
                    return empId;
                }
                public void setEmpId(String empId) {
                    this.empId = empId;
                }
                public Double getLeaveHours() {
                    return leaveHours;
                }
                public void setLeaveHours(Double leaveHours) {
                    this.leaveHours = leaveHours;
                }
                public String getLeaveType() {
                    return leaveType;
                }
                public void setLeaveType(String leaveType) {
                    this.leaveType = leaveType;
                }
        }
        
        static class DtoWorkHours extends DtoBase{
                private String empId;
                private String acntId;
                private String deptFlag;
                private Double workHours;
                public String getAcntId() {
                    return acntId;
                }
                public void setAcntId(String acntId) {
                    this.acntId = acntId;
                }
                public String getEmpId() {
                    return empId;
                }
                public void setEmpId(String empId) {
                    this.empId = empId;
                }
                public Double getWorkHours() {
                    return workHours;
                }
                public void setWorkHours(Double workHours) {
                    this.workHours = workHours;
                }
                public String getDeptFlag() {
                    return deptFlag;
                }
                public void setDeptFlag(String deptFlag) {
                    this.deptFlag = deptFlag;
                }
        }
        
        public void setIresDao(IResourceDao iresDao) {
            this.iresDao = iresDao;
        }
        
        public void setAttendanceDao(IAttendanceDao attendanceDao) {
            this.attendanceDao = attendanceDao;
        }

        public void setRmMaintDao(IRmMaintDao rmMaintDao) {
            this.rmMaintDao = rmMaintDao;
        }

        public void setTimeSheetApiDao(ITimeSheetP3ApiDao timeSheetApiDao) {
            this.timeSheetApiDao = timeSheetApiDao;
        }

        public void setExcelDto(boolean flag) {
            this.excelDto  = flag;
        }

}
