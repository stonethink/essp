package server.essp.tc.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import db.essp.code.CodeValue;
import db.essp.pms.Account;
import db.essp.pms.Activity;
import db.essp.tc.TcByWorkerAccount;
import db.essp.tc.TcWeeklyReport;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class LgWeeklyReport extends AbstractESSPLogic {
    StringBuffer msg = new StringBuffer();
    List msgKeys = new ArrayList();

    LgWeeklyReportSumExtend lgWeeklyReportSumExtend = new LgWeeklyReportSumExtend();

    /**列出时间区间内userId的所有周报*/
    public List listByUserId(String userId, Date beginPeriod, Date endPeriod) {
        List weeklyReport = new ArrayList();
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];

        String sql = "select t from TcWeeklyReport t where t.userId =:userId"
                     + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod"
                     + " order by t.beginPeriod, NVL(t.acntRid,0) asc, NVL(t.activityRid,0) asc, NVL(t.satHour,0) desc, NVL(t.sunHour,0) desc, NVL(t.monHour,0) desc,NVL(t.tueHour,0) desc, NVL(t.wedHour,0) desc, NVL(t.thuHour,0) desc, NVL(t.friHour,0) desc "
                     ;
        try {
            Iterator iter = getDbAccessor().getSession().createQuery(sql)
                            .setString("userId", userId)
                            .setDate("beginPeriod", beginPeriod)
                            .setDate("endPeriod", endPeriod)
                            .list()
                            .iterator();
            for (; iter.hasNext(); ) {
                TcWeeklyReport db = (TcWeeklyReport) iter.next();

                DtoWeeklyReport dto = createDto(db);

                weeklyReport.add(dto);
            }
        } catch (Exception ex) {
            throw new BusinessException("E000", "Error when select weekly report.", ex);
        }

        return weeklyReport;
    }

    /**列出时间区间内userId在account中的所有周报*/
    public List listByUserAcnt(String userId, Long acntRid, Date beginPeriod, Date endPeriod) {
        List weeklyReport = new ArrayList();
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];
        String sql = "select t from TcWeeklyReport t where t.acntRid =:acntRid and t.userId =:userId"
                     + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod"
                     + " order by t.beginPeriod, NVL(t.acntRid,0) asc, NVL(t.activityRid,0) asc, NVL(t.satHour,0) desc, NVL(t.sunHour,0) desc, NVL(t.monHour,0) desc,NVL(t.tueHour,0) desc, NVL(t.wedHour,0) desc, NVL(t.thuHour,0) desc, NVL(t.friHour,0) desc "
                     ;
        try {
            Iterator iter = getDbAccessor().getSession().createQuery(sql)
                            .setLong("acntRid", acntRid.longValue())
                            .setString("userId", userId)
                            .setDate("beginPeriod", beginPeriod)
                            .setDate("endPeriod", endPeriod)
                            .iterate();
            for (; iter.hasNext(); ) {
                TcWeeklyReport db = (TcWeeklyReport) iter.next();

                DtoWeeklyReport dto = createDto(db);
                weeklyReport.add(dto);
            }
        } catch (Exception ex) {
            throw new BusinessException("E000", ex, "Error when select weekly report.");
        }

        return weeklyReport;
    }

    /**修改周报信息*/
    public void update(List weeklyReportList){
        try {
            for (int i = 0; i < weeklyReportList.size(); i++) {
                DtoWeeklyReport dto = (DtoWeeklyReport) weeklyReportList.get(i);

                if (dto.isInsert()) {
                    insert(dto);
                } else if (dto.isDelete()) {

                    delete(dto);
                    weeklyReportList.remove(i);
                    i--;
                } else if (dto.isModify()) {
                    update(dto);
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Update TcWeeklyReport error.", ex);
        }
    }

   public void update(DtoWeeklyReport dto) {
        try {
            TcWeeklyReport db = createDb(dto);
            TcWeeklyReport tcWeeklyReport = (TcWeeklyReport) getDbAccessor().getSession().get(TcWeeklyReport.class, dto.getRid());
            DtoUtil.copyBeanToBean(tcWeeklyReport, db);

            getDbAccessor().update(tcWeeklyReport);

            //同步更新TcByWorkerAccount汇总表
            lgWeeklyReportSumExtend.updateAsDetail(dto);

            dto.setOp(IDto.OP_NOCHANGE);
            dto.setOldFriHour(dto.getFriHour());
            dto.setOldMonHour(dto.getMonHour());
            dto.setOldSatHour(dto.getSatHour());
            dto.setOldSunHour(dto.getSunHour());
            dto.setOldThuHour(dto.getThuHour());
            dto.setOldTueHour(dto.getTueHour());
            dto.setOldWedHour(dto.getWedHour());

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Update TcWeeklyReport error.", ex);
        }
    }

    public void insert(DtoWeeklyReport dto) throws BusinessException {
        try {
            TcWeeklyReport tcWeeklyReport = createDb(dto);

            getDbAccessor().save(tcWeeklyReport);

            //同步更新TcByWorkerAccount汇总表
            lgWeeklyReportSumExtend.updateAsDetail(dto);

            dto.setOp(IDto.OP_NOCHANGE);
            dto.setRid(tcWeeklyReport.getRid());
            dto.setOldAcntRid(dto.getAcntRid());
            dto.setOldFriHour(dto.getFriHour());
            dto.setOldMonHour(dto.getMonHour());
            dto.setOldSatHour(dto.getSatHour());
            dto.setOldSunHour(dto.getSunHour());
            dto.setOldThuHour(dto.getThuHour());
            dto.setOldTueHour(dto.getTueHour());
            dto.setOldWedHour(dto.getWedHour());

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000", "Insert TcWeeklyReport error.", ex);
        }
    }

    public void delete(DtoWeeklyReport dto) {

        try {
            TcWeeklyReport tcWeeklyReport = (TcWeeklyReport) getDbAccessor().getSession().get(TcWeeklyReport.class, dto.getRid());

            getDbAccessor().delete(tcWeeklyReport);

            //同步更新TcByWorkerAccount汇总表
            lgWeeklyReportSumExtend.updateAsDetail(dto);
        } catch (Exception ex) {
            throw new BusinessException("E0000", ex, "Error when delete weekly report - " + dto.getRid());
        }
    }

    private DtoWeeklyReport createDto(TcWeeklyReport db) {
        DtoWeeklyReport dto = new DtoWeeklyReport();
        DtoUtil.copyBeanToBean(dto, db);

        dto.setOldAcntRid(dto.getAcntRid());
        dto.setOldFriHour(dto.getFriHour());
        dto.setOldMonHour(dto.getMonHour());
        dto.setOldSatHour(dto.getSatHour());
        dto.setOldSunHour(dto.getSunHour());
        dto.setOldThuHour(dto.getThuHour());
        dto.setOldTueHour(dto.getTueHour());
        dto.setOldWedHour(dto.getWedHour());

        try{
            Account account = db.getAccount();
            if (account != null) {
                dto.setAcntName( account.getId() + " -- " + account.getName());
            }

            if (dto.doesActivity() == true) {
                Activity activity = db.getActivity();
                if (activity != null) {
                    dto.setActivityName(activity.getCode() + " -- " + activity.getName());
                }
            } else {
                CodeValue codeValue = db.getCodeValue();
                if (codeValue != null) {
                    dto.setCodeValueName(codeValue.getValue());
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        //get confirm status
        String confirmStatus = getConfirmStatus(dto);
        dto.setConfirmStatus(confirmStatus);
        return dto;
    }

    private String getConfirmStatus(DtoWeeklyReport dto) {
        List weeklyrptSums = lgWeeklyReportSumExtend.getByUserAcnt(dto.getUserId(), dto.getAcntRid()
                                                             , dto.getBeginPeriod(), dto.getEndPeriod());
        if (weeklyrptSums.iterator().hasNext()) {
            TcByWorkerAccount tc = (TcByWorkerAccount) weeklyrptSums.iterator().next();
            return tc.getConfirmStatus();
        }else{
            return DtoWeeklyReport.STATUS_NONE;
        }
    }

    private TcWeeklyReport createDb(DtoWeeklyReport dto) {
        TcWeeklyReport db = new TcWeeklyReport();
        //周报每条记录区间只记年月日
        Date resetBeginPeriod = LgTcCommon.resetBeginDate(dto.getBeginPeriod());
        dto.setBeginPeriod(resetBeginPeriod);
        Date resetEndPeriod = LgTcCommon.resetBeginDate(dto.getEndPeriod());
        dto.setEndPeriod(resetEndPeriod);

        DtoUtil.copyBeanToBean(db, dto);
        if (dto.doesActivity() == true) {
            db.setIsActivity("1");
        } else {
            db.setIsActivity("0");
        }

        return db;
    }

    public boolean checkConfirmAndLockStatus(List weeklyReportList) {
        return checkStatus(weeklyReportList, new String[]{DtoWeeklyReport.STATUS_CONFIRMED, DtoWeeklyReport.STATUS_LOCK} );
    }
    public boolean checkConfirmStatus(List weeklyReportList) {
        return checkStatus(weeklyReportList, new String[]{DtoWeeklyReport.STATUS_CONFIRMED} );
    }


    protected boolean checkStatus(List weeklyReportList, String status[]) {
        boolean isOk = true;

        cleanMsg();
        for (int i = 0; i < weeklyReportList.size(); i++) {
            DtoWeeklyReport dto = (DtoWeeklyReport) weeklyReportList.get(i);

            if (dto.isInsert()
                || dto.isDelete()
                || dto.isModify()) {

                //check dto's status
                try {
                    List weeklyrptSum = lgWeeklyReportSumExtend.getByUserAcnt(dto.getUserId(),
                                                                        dto.getAcntRid(), dto.getBeginPeriod(), dto.getEndPeriod());

                    TcByWorkerAccount tc = null;
                    if (weeklyrptSum.iterator().hasNext()) {
                        tc = (TcByWorkerAccount) weeklyrptSum.iterator().next();

                        String acntName = tc.getAccount().getId() + "--" + tc.getAccount().getName();

                        String msg1 = "";

                        boolean invalid = false;
                        for (int j = 0; j < status.length; j++) {
                            if( status[j].equals(tc.getConfirmStatus()) ){
                                invalid = true;
                                break;
                            }
                        }

                        if (DtoWeeklyReport.STATUS_CONFIRMED.equals(tc.getConfirmStatus())) {
                            msg1 = "The job in the account[ " + acntName + " ] is already confirmed by manager.";
                        } else if (DtoWeeklyReport.STATUS_LOCK.equals(tc.getConfirmStatus())) {
                            msg1 = "The job in the account[ " + acntName + " ] is already locked.";
                        }

                        String msg2 = "";
                        if (dto.isModify()) {
                            msg2 = "Can't update it.";
                        } else if (dto.isDelete()) {
                            msg2 = "Can't delete it.";
                        } else if (dto.isInsert()) {
                            msg2 = "Can't add weekly report to the account.";
                        }

                        if (invalid == true) {
                            addMsg(dto.getAcntRid(), msg1 + msg2);

                            //还原数据
                            if (dto.isModify() || dto.isDelete()) {
                                TcWeeklyReport db = (TcWeeklyReport) getDbAccessor().load(TcWeeklyReport.class, dto.getRid());
                                DtoWeeklyReport tempDto = createDto(db);
                                DtoUtil.copyBeanToBean(dto, tempDto);
                                dto.setOp(IDto.OP_NOCHANGE);
                            } else if (dto.isInsert()) {
                                weeklyReportList.remove(i);
                                i--;
                            }
                            isOk = false;
                        }
                    }
                } catch (Exception ex) {
                    throw new BusinessException("E0000", "Error when check the status of weekly report[" + dto.getJobDescription() + "]", ex);
                }

            }
        }

        return isOk;
    }

    /**检查该人的该account下的周报是否已被pm confirm
     * 已被pm确认的数据不能再被worker修改
     * */
    private boolean checkStatus(DtoWeeklyReport dto){

        try {
            List weeklyrptSum = lgWeeklyReportSumExtend.getByUserAcnt(dto.getUserId(),
                                                                dto.getAcntRid(), dto.getBeginPeriod(), dto.getEndPeriod());

            TcByWorkerAccount tc = null;
            if (weeklyrptSum.iterator().hasNext()) {
                tc = (TcByWorkerAccount) weeklyrptSum.iterator().next();

                String acntName = tc.getAccount().getId() + "--" + tc.getAccount().getName();

                String msg1 = "";
                if (DtoWeeklyReport.STATUS_CONFIRMED.equals(tc.getConfirmStatus())) {
                    msg1 = "The job in the account[ " + acntName + " ] is already confirmed by manager.";
                } else if (DtoWeeklyReport.STATUS_LOCK.equals(tc.getConfirmStatus())) {
                    msg1 = "The job in the account[ " + acntName + " ] is already locked.";
                }

                String msg2 = "";
                if (dto.isModify()) {
                    msg2 = "Can't update it.The modified data is retrieved.";
                } else if (dto.isDelete()) {
                    msg2 = "Can't delete it.The deleted data is retrieved.";
                } else if (dto.isInsert()) {
                    msg2 = "Can't add weekly report to the account.Remove the added data.";
                }

                if (DtoWeeklyReport.STATUS_CONFIRMED.equals(tc.getConfirmStatus())
                    || DtoWeeklyReport.STATUS_LOCK.equals(tc.getConfirmStatus())) {
                    addMsg(dto.getAcntRid(), msg1 + msg2);

                    //还原数据
                    if (dto.isModify() || dto.isDelete()) {
                        TcWeeklyReport db = (TcWeeklyReport) getDbAccessor().load(TcWeeklyReport.class, dto.getRid());
                        DtoWeeklyReport tempDto = createDto(db);
                        DtoUtil.copyBeanToBean(dto, tempDto);
                        dto.setOp(IDto.OP_NOCHANGE);
                    }else if(dto.isInsert()){

                    }
                    return false;
                }
            }
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when check the status of weekly report[" + dto.getJobDescription() + "]", ex);
        }

        return true;
    }

    private void cleanMsg(){
        this.msg.delete(0, msg.length());
        msgKeys.clear();
    }

    private void addMsg(Long acntRid, String message){
//        if( msgKeys.contains(acntRid) == false ){
            this.msg.append("\r\n");
            this.msg.append(message);
            msgKeys.add(acntRid);
//        }
    }

    public String getMsg(){
        if( msg.length() > 0 ){
            return msg.toString();
        }else{
            return null;
        }
    }
}
