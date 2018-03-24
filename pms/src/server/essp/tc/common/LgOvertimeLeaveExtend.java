package server.essp.tc.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import c2s.essp.tc.weeklyreport.IDtoAllocateHour;
import c2s.essp.tc.weeklyreport.IDtoAllocateHourInTheAcnt;
import c2s.essp.tc.weeklyreport.IDtoAllocateHourIncLeaveDetail;
import db.essp.attendance.TcLeaveMain;
import db.essp.attendance.TcLeaveType;
import db.essp.attendance.TcOvertime;
import server.framework.common.BusinessException;
import c2s.essp.attendance.Constant;
import server.essp.attendance.leave.logic.LgLeaveType;

public class LgOvertimeLeaveExtend extends LgOvertimeLeave {
    private LgLeaveType lgLeaveType = new LgLeaveType();

    /**
     * 1. 取得userId在时间段内的（finish overtime, all overtime, finish leave, all leave）信息，每一项都是总和
     * @param userId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return BigDecimal[]
     */
    public void getOvertime(IDtoAllocateHour dto) {
        BigDecimal overtimeSumConfirmed = new BigDecimal(0);
        BigDecimal overtimeSum = new BigDecimal(0);

        String userId = dto.getUserId();
        Date beginPeriod = dto.getBeginPeriod();
        Date endPeriod = dto.getEndPeriod();

        try {
            List ovetimeList = listPeriodStatusOverTime(userId, beginPeriod, endPeriod);
            for (Iterator iterOvertime = ovetimeList.iterator(); iterOvertime.hasNext(); ) {
                TcOvertime overtime = (TcOvertime) iterOvertime.next();

                BigDecimal totalHours = getOvertimeSum(overtime.getRid(), beginPeriod, endPeriod);
                if( totalHours.doubleValue() == 0 ){
                    continue;
                }

                //overtime
                if (Constant.STATUS_DISAGREED.equals(overtime.getStatus()) == false) {
                    overtimeSum = overtimeSum.add(totalHours);
                }

                if (Constant.STATUS_FINISHED.equals(overtime.getStatus())) {
                    overtimeSumConfirmed = overtimeSumConfirmed.add(totalHours);
                }
            }

            dto.setOvertimeSumConfirmed(overtimeSumConfirmed);
            dto.setOvertimeSum(overtimeSum);
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when get the overtime and leave detail information.", ex);
        }
    }

    //------------------------------------------------------
    /**
     * 1. 取得userId在时间段内的（finish overtime, all overtime, finish leave, all leave）信息，每一项都是总和
     * 2. 取得userId在项目acntRid中的总加班时间
     * @param userId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return BigDecimal[]
     */
    public void getOvertimeInTheAcnt(IDtoAllocateHourInTheAcnt dto) {
        BigDecimal overtimeSumConfirmed = new BigDecimal(0);
        BigDecimal overtimeSum = new BigDecimal(0);
        BigDecimal overtimeSumInTheAcnt = new BigDecimal(0);
        BigDecimal overtimeSumConfirmedInTheAcnt = new BigDecimal(0);

        String userId = dto.getUserId();
        Date beginPeriod = dto.getBeginPeriod();
        Date endPeriod = dto.getEndPeriod();
        Long acntRid = dto.getAcntRid();

        try {
            List ovetimeList = listPeriodStatusOverTime(userId, beginPeriod, endPeriod);
            for (Iterator iterOvertime = ovetimeList.iterator(); iterOvertime.hasNext(); ) {
                TcOvertime overtime = (TcOvertime) iterOvertime.next();

                BigDecimal totalHours = getOvertimeSum(overtime.getRid(), beginPeriod, endPeriod);
                if (totalHours.doubleValue() == 0) {
                    continue;
                }

                //overtime
                if (Constant.STATUS_DISAGREED.equals(overtime.getStatus()) == false) {
                    overtimeSum = overtimeSum.add(totalHours);

                    //overtime in the account
                    if (acntRid.equals(overtime.getAcntRid()) == true) {
                        overtimeSumInTheAcnt = overtimeSumInTheAcnt.add(totalHours);
                    }
                }

                if (Constant.STATUS_FINISHED.equals(overtime.getStatus())) {
                    overtimeSumConfirmed = overtimeSumConfirmed.add(totalHours);

                    //overtime in the account
                    if (acntRid.equals(overtime.getAcntRid()) == true) {
                        overtimeSumConfirmedInTheAcnt = overtimeSumConfirmedInTheAcnt.add(totalHours);
                    }
                }
            }

            dto.setOvertimeSumConfirmed(overtimeSumConfirmed);
            dto.setOvertimeSum(overtimeSum);
            dto.setOvertimeSumConfirmedInTheAcnt(overtimeSumConfirmedInTheAcnt);
            dto.setOvertimeSumInTheAcnt(overtimeSumInTheAcnt);
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when get the overtime and leave detail information.", ex);
        }
    }
    public void getAcntOvertime(IDtoAllocateHourInTheAcnt dto) {
        BigDecimal overtimeSumConfirmed = new BigDecimal(0);
        BigDecimal overtimeSum = new BigDecimal(0);
        BigDecimal overtimeSumInTheAcnt = new BigDecimal(0);
        BigDecimal overtimeSumConfirmedInTheAcnt = new BigDecimal(0);

        Date beginPeriod = dto.getBeginPeriod();
        Date endPeriod = dto.getEndPeriod();
        Long acntRid = dto.getAcntRid();

        try {
            List ovetimeList = listPeriodStatusOverTime(acntRid, beginPeriod, endPeriod);
            for (Iterator iterOvertime = ovetimeList.iterator(); iterOvertime.hasNext(); ) {
                TcOvertime overtime = (TcOvertime) iterOvertime.next();

                BigDecimal totalHours = getOvertimeSum(overtime.getRid(), beginPeriod, endPeriod);
                if (totalHours.doubleValue() == 0) {
                    continue;
                }

                //overtime
                if (Constant.STATUS_DISAGREED.equals(overtime.getStatus()) == false) {
                    overtimeSum = overtimeSum.add(totalHours);

                    //overtime in the account
                    if (acntRid.equals(overtime.getAcntRid()) == true) {
                        overtimeSumInTheAcnt = overtimeSumInTheAcnt.add(totalHours);
                    }
                }

                if (Constant.STATUS_FINISHED.equals(overtime.getStatus())) {
                    overtimeSumConfirmed = overtimeSumConfirmed.add(totalHours);

                    //overtime in the account
                    if (acntRid.equals(overtime.getAcntRid()) == true) {
                        overtimeSumConfirmedInTheAcnt = overtimeSumConfirmedInTheAcnt.add(totalHours);
                    }
                }
            }

            dto.setOvertimeSumConfirmed(overtimeSumConfirmed);
            dto.setOvertimeSum(overtimeSum);
            dto.setOvertimeSumConfirmedInTheAcnt(overtimeSumConfirmedInTheAcnt);
            dto.setOvertimeSumInTheAcnt(overtimeSumInTheAcnt);
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when get the overtime and leave detail information.", ex);
        }
    }


    public void getLeave(IDtoAllocateHour dto) {
        BigDecimal leaveSumConfirmed = new BigDecimal(0);
        BigDecimal leaveSum = new BigDecimal(0);

        String userId = dto.getUserId();
        Date beginPeriod = dto.getBeginPeriod();
        Date endPeriod = dto.getEndPeriod();

        try {
            List leaveList = listPeriodStatusLeave(userId, beginPeriod, endPeriod);
            for (Iterator iterLeave = leaveList.iterator(); iterLeave.hasNext(); ) {
                TcLeaveMain leave = (TcLeaveMain) iterLeave.next();

                BigDecimal totalHours = getLeaveSum(leave.getRid(), beginPeriod, endPeriod);
                if (totalHours.doubleValue() == 0) {
                    continue;
                }

                //leave
                if (Constant.STATUS_DISAGREED.equals(leave.getStatus()) == false) {
                    leaveSum = leaveSum.add(totalHours);
                }

                if (Constant.STATUS_FINISHED.equals(leave.getStatus())) {
                    leaveSumConfirmed = leaveSumConfirmed.add(totalHours);
                }
            }

            dto.setLeaveSumConfirmed(leaveSumConfirmed);
            dto.setLeaveSum(leaveSum);
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when get the leave detail information.", ex);
        }
    }

    //------------------------------------------------------
    /**
     * 1. 取得userId在时间段内的leave信息
     * 2. 取得leave的明细，分全薪/半薪/不付薪三种
     * @param userId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return BigDecimal[]
     */
    public void getLeaveDetail(IDtoAllocateHourIncLeaveDetail dto) {
        BigDecimal leaveSumConfirmed = new BigDecimal(0);
        BigDecimal leaveSum = new BigDecimal(0);

       BigDecimal leaveOfPayAll = new BigDecimal(0);
       BigDecimal leaveOfPayAllConfirmed = new BigDecimal(0);
       BigDecimal leaveOfPayHalf = new BigDecimal(0);
       BigDecimal leaveOfPayHalfConfirmed = new BigDecimal(0);
       BigDecimal leaveOfPayNone = new BigDecimal(0);
       BigDecimal leaveOfPayNoneConfirmed = new BigDecimal(0);

       String userId = dto.getUserId();
       Date beginPeriod = dto.getBeginPeriod();
       Date endPeriod = dto.getEndPeriod();

       try {
           List leaveList = listPeriodStatusLeave(userId, beginPeriod, endPeriod);
           for (Iterator iterLeave = leaveList.iterator(); iterLeave.hasNext(); ) {
               TcLeaveMain leave = (TcLeaveMain) iterLeave.next();

               BigDecimal totalLeaveHours = getLeaveSum(leave.getRid(), beginPeriod, endPeriod);
               if (totalLeaveHours.doubleValue() == 0) {
                   continue;
               }
               String leaveName = leave.getLeaveName();
               String payType = null;
               if (leaveName != null) {
                   TcLeaveType tcLeaveType = lgLeaveType.getLeaveType(leaveName);
                   if (tcLeaveType != null) {
                       payType = tcLeaveType.getSettlementWay();
                   }
               }

               //leave
               if (Constant.STATUS_DISAGREED.equals(leave.getStatus()) == false) {
                   leaveSum = leaveSum.add(totalLeaveHours);
                   if ("Full Salary".equals(payType)) {
                       leaveOfPayAll = leaveOfPayAll.add(totalLeaveHours);
                   } else if ("Half Salary".equals(payType)) {
                       leaveOfPayHalf = leaveOfPayHalf.add(totalLeaveHours);
                   } else {
                       leaveOfPayNone = leaveOfPayNone.add(totalLeaveHours);
                   }
               }

               if (Constant.STATUS_FINISHED.equals(leave.getStatus())) {
                   leaveSumConfirmed = leaveSumConfirmed.add(totalLeaveHours);
                   if ("Full Salary".equals(payType)) {
                       leaveOfPayAllConfirmed = leaveOfPayAllConfirmed.add(totalLeaveHours);
                   } else if ("Half Salary".equals(payType)) {
                       leaveOfPayHalfConfirmed = leaveOfPayHalfConfirmed.add(totalLeaveHours);
                   } else {
                       leaveOfPayNoneConfirmed = leaveOfPayNoneConfirmed.add(totalLeaveHours);
                   }
               }
           }

           dto.setLeaveSum(leaveSum);
           dto.setLeaveSumConfirmed(leaveSumConfirmed);
           dto.setLeaveOfPayAll(leaveOfPayAll);
           dto.setLeaveOfPayAllConfirmed(leaveOfPayAllConfirmed);
           dto.setLeaveOfPayHalf(leaveOfPayHalf);
           dto.setLeaveOfPayHalfConfirmed(leaveOfPayHalfConfirmed);
           dto.setLeaveOfPayNone(leaveOfPayNone);
           dto.setLeaveOfPayNoneConfirmed(leaveOfPayNoneConfirmed);
       } catch (Exception ex) {
           throw new BusinessException("E0000", "Error when get the overtime and leave detail information.", ex);
       }
    }



}
