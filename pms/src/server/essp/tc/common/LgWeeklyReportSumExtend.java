package server.essp.tc.common;

import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.dto.IDto;
import server.framework.common.BusinessException;
import db.essp.tc.TcByWorkerAccount;
import java.util.List;
import java.math.BigDecimal;
import java.util.Date;
import javax.sql.RowSet;
import java.util.Iterator;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Calendar;

public class LgWeeklyReportSumExtend extends LgWeeklyReportSum {

    /**
     * 根据weeklyReport的时间数据变化来修改weeklyReportSum的时间数据
     * @param dto DtoWeeklyReport
     */
    public void updateAsDetail(DtoWeeklyReport dto) {
        if (dto.isInsert() == true) {
            updateSimply(dto, dto.getAcntRid(), IDto.OP_INSERT);
        } else if (dto.isDelete() == true) {
            updateSimply(dto, dto.getOldAcntRid(), IDto.OP_DELETE);
        } else if (dto.isModify() == true) {
            if (dto.getAcntRid() != null) {
                if (dto.getAcntRid().equals(dto.getOldAcntRid()) == true) {
                    updateSimply(dto, dto.getAcntRid(), IDto.OP_MODIFY);
                } else {
                    if (dto.getOldAcntRid() == null) {
                        throw new BusinessException("E00000", "Old account of the DtoWeeklyReport is null.");
                    } else {
                        updateSimply(dto, dto.getOldAcntRid(), IDto.OP_DELETE);
                    }

                    updateSimply(dto, dto.getAcntRid(), IDto.OP_INSERT);
                }
            } else {
                throw new BusinessException("E00000", "Account of the DtoWeeklyReport is null.");
            }
        }
    }

    private void updateSimply(DtoWeeklyReport dto, Long acntRid, String op) {

        try {
            List weekrpt = getByUserAcnt(dto.getUserId()
                                         , acntRid, dto.getBeginPeriod(), dto.getEndPeriod());

            TcByWorkerAccount tc = null;
            if (weekrpt.iterator().hasNext()) {
                tc = (TcByWorkerAccount) weekrpt.iterator().next();
            } else {

                //找不到就新增一个
                tc = new TcByWorkerAccount();
                tc.setAcntRid(acntRid);

                Date beginPeriod = dto.getBeginPeriod();
                Date endPeriod = dto.getEndPeriod();
                //个人周报每Account的Sum记录区间只记其年月日
                beginPeriod = LgTcCommon.resetBeginDate(beginPeriod);
                endPeriod = LgTcCommon.resetBeginDate(endPeriod);

                tc.setBeginPeriod(beginPeriod);
                tc.setEndPeriod(endPeriod);
                tc.setUserId(dto.getUserId());

                if( lgWeeklyReportLock.getLock(dto.getUserId(), dto.getBeginPeriod(), dto.getEndPeriod()).booleanValue() ){
                    tc.setConfirmStatus(DtoWeeklyReport.STATUS_LOCK);
                }else{
                    tc.setConfirmStatus(DtoWeeklyReport.STATUS_UNLOCK);
                }
                getDbAccessor().save(tc);
            }

            if (tc != null) {
                tc.setSatHour(updateSumHour(tc.getSatHour(), dto.getOldSatHour(), dto.getSatHour(), op));
                tc.setSunHour(updateSumHour(tc.getSunHour(), dto.getOldSunHour(), dto.getSunHour(), op));
                tc.setMonHour(updateSumHour(tc.getMonHour(), dto.getOldMonHour(), dto.getMonHour(), op));
                tc.setTueHour(updateSumHour(tc.getTueHour(), dto.getOldTueHour(), dto.getTueHour(), op));
                tc.setWedHour(updateSumHour(tc.getWedHour(), dto.getOldWedHour(), dto.getWedHour(), op));
                tc.setThuHour(updateSumHour(tc.getThuHour(), dto.getOldThuHour(), dto.getThuHour(), op));
                tc.setFriHour(updateSumHour(tc.getFriHour(), dto.getOldFriHour(), dto.getFriHour(), op));
            }

            getDbAccessor().update(tc);

            getDbAccessor().endTxCommit();
            getDbAccessor().newTx();

            if( op.equals(IDto.OP_DELETE) ){
                checkDetailExist(tc.getUserId(), tc.getAcntRid(),
                        tc.getBeginPeriod(), tc.getEndPeriod());
            }
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when delete weekly report - " + dto.getRid(), ex);
        }
    }

    private BigDecimal updateSumHour(BigDecimal sum, BigDecimal oneOld, BigDecimal oneNew, String op) {
        if (sum == null) {
            sum = new BigDecimal(0);
        }
        if (oneOld == null) {
            oneOld = new BigDecimal(0);
        }
        if (oneNew == null) {
            oneNew = new BigDecimal(0);
        }

        if (IDto.OP_MODIFY.equals(op)) {
            return sum.subtract(oneOld).add(oneNew);
        } else if (IDto.OP_DELETE.equals(op)) {
            return sum.subtract(oneOld);
        } else if (IDto.OP_INSERT.equals(op)) {
            return sum.add(oneNew);
        } else {
            return sum;
        }
    }


    /**
     * 检查一条weeklyReportSum下是否有对应的明细数据，如果没有，那么将删除该weeklyReportSum
     * @param userId String
     * @param acntRid Long
     * @param beginPeriod Date
     * @param endPeriod Date
     */
    private void checkDetailExist(String userId, Long acntRid, Date beginPeriod, Date endPeriod){
        List param = new ArrayList();
        param.add(userId);
        param.add(acntRid);
        Date ds[] = LgTcCommon.resetBeginDate(beginPeriod,endPeriod);
        beginPeriod = ds[0];
        endPeriod=ds[1];
        param.add(new Timestamp(beginPeriod.getTime()));
        param.add(new Timestamp(endPeriod.getTime()));

        String sql = "select count(*) num from TC_WEEKLY_REPORT t where t.USER_ID =? "
                     + " and t.ACNT_RID = ? and t.BEGIN_PERIOD >= ? and t.END_PERIOD <= ?";
        try {
            RowSet rset = getDbAccessor().executeQuery(sql, param);
            while( rset.next() ){
                int num = rset.getInt("num");
                if( num == 0 ){

                    //detail not exist, delete the sum data
                    delete(userId, acntRid, beginPeriod, endPeriod );
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when update TcByWorkerAccount of user - " + userId, ex);
        }
    }


    private void delete(String userId, Long acntRid, Date beginPeriod, Date endPeriod) {
        List weekrptList = getByUserAcnt(userId, acntRid, beginPeriod, endPeriod);
        try {
            for (Iterator iter = weekrptList.iterator(); iter.hasNext(); ) {
                Object item = (Object) iter.next();
                getDbAccessor().getSession().delete(item);
            }
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when delete TcByWorkerAccount of user - " + userId, ex);
        }
    }

}
