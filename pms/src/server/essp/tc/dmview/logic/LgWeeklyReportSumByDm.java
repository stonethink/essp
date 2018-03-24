package server.essp.tc.dmview.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import c2s.essp.tc.weeklyreport.DtoAllocateHour;
import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByPm;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumOnMonthByPm;
import db.essp.tc.TcByWorkerAccount;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;
import itf.orgnization.IOrgnizationUtil;
import itf.orgnization.OrgnizationFactory;
import net.sf.hibernate.Query;
import server.essp.tc.common.LgOvertimeLeaveExtend;
import server.essp.tc.common.LgWeeklyReportSum;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;
import com.wits.util.comDate;
import c2s.essp.tc.weeklyreport.IDtoAllocateHourInTheAcnt;
import server.essp.tc.common.LgTcCommon;
import java.util.Calendar;

public class LgWeeklyReportSumByDm extends LgWeeklyReportSum {
    IOrgnizationUtil orgUtil = OrgnizationFactory.create();
    IHrUtil hrUtil = HrFactory.create();
    LgOvertimeLeaveExtend lgOvertimeLeave = new LgOvertimeLeaveExtend();

    /**
     * ���ã������ܡ��г�org�µ�ÿ��userId��weeklyReportSum��Ϣ����ÿ���ʱ����Ϣ
     * ���ܡ��ڣ�userId��ÿ���μӵ���Ŀ������һ��weeklyReportSum��Ϣ�����������ǻ��ܳ�һ��
     * @param orgId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List listByUserInOrgOnWeek(String orgId, Date beginPeriod, Date endPeriod) {
        List tcList = getByUserInOrg(orgId, beginPeriod, endPeriod);
        return dbList2DtoListOnWeek(tcList, beginPeriod, endPeriod);
    }

    /**@see listByUserInOrgOnWeek*/
    public List listByUserInOrgIncSubOnWeek(String orgId, Date beginPeriod, Date endPeriod) {
        List tcList = getByUserInOrgIncSub(orgId, beginPeriod,endPeriod);
        return dbList2DtoListOnWeek(tcList, beginPeriod, endPeriod);
    }

    public DtoWeeklyReportSumByPm listByTheUserOnWeek(String userId, Date beginPeriod, Date endPeriod) {
        List tcList = getByUser(userId, beginPeriod, endPeriod);
        List dtoList = dbList2DtoListOnWeek(tcList, beginPeriod, endPeriod);
        if( dtoList.size() > 0 ){
            return (DtoWeeklyReportSumByPm)dtoList.get(0);
        }else{
            DtoWeeklyReportSumByPm dto = createEmptyDtoWeeklyReportSumByPm(userId, beginPeriod, endPeriod);
            return dto;
        }
    }


    /**
     * ���ã������¡��г�org�µ�ÿ��userId��weeklyReportSum��Ϣ��ֻ�л�����Ϣ������ÿ���ʱ����Ϣ
     * ���¡��ڣ���ÿ���ܡ���userId��ÿ���μӵ���Ŀ������һ��weeklyReportSum��Ϣ�����������ǻ��ܳ�һ��
     * @param orgId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List listByUserInOrgOnMonth(String orgId, Date beginPeriod, Date endPeriod) {
        List tcList = getByUserInOrg(orgId, beginPeriod,endPeriod);
        return dbList2DtoListOnMonth(tcList, beginPeriod, endPeriod);
    }

    /**@see listByUserInOrgOnMonth*/
    public List listByUserInOrgIncSubOnMonth(String orgId, Date beginPeriod, Date endPeriod) {
        List tcList = getByUserInOrgIncSub(orgId, beginPeriod,endPeriod);
        return dbList2DtoListOnMonth(tcList, beginPeriod, endPeriod);
    }

    public DtoWeeklyReportSumOnMonthByPm listByTheUserOnMonth(String userId, Date beginPeriod, Date endPeriod) {
        List tcList = getByUser(userId, beginPeriod, endPeriod);
        List dtoList = dbList2DtoListOnMonth(tcList, beginPeriod, endPeriod);
        if( dtoList.size() > 0 ){
            return (DtoWeeklyReportSumOnMonthByPm)dtoList.get(0);
        }else{
            DtoWeeklyReportSumOnMonthByPm dto = createEmptyDtoWeeklyReportSumOnMonthByPm(userId, beginPeriod, endPeriod);
            return dto;
        }
    }



    /**
     * ����orgnazition�µ�ÿ���˵�weeklyReportSum
     * ���ʱ������Ϊ���ܡ�����ô��user���ڵ�ÿ����Ŀ�����ܻ���һ������
     * ���ʱ������Ϊ���¡�����ô�����ڵ�ÿ���� & ÿ����Ŀ�����ܻ���һ������
     * @param orgId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List getByUserInOrg(String orgId, Date beginPeriod, Date endPeriod) {
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];

        List tcList = new ArrayList();
        try {
            String userStr = orgUtil.getUserStrInOrgs( orgUtil.getUserListInOrgs( "'"+orgId+"'" ) );
            Query q = getDbAccessor().getSession().createQuery("from TcByWorkerAccount t where t.userId in (" + userStr + ") "
                                                               + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod order by lower(t.userId)"
                      );
            q.setDate("beginPeriod", beginPeriod);
            q.setDate("endPeriod", endPeriod);

            tcList = q.list();

            return tcList;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when select TcByWorkerAccount of orgnazition - " + orgId, ex);
        }
    }

    /**@see getByUserInOrg*/
    public List getByUserInOrgIncSub(String orgId, Date beginPeriod, Date endPeriod) {
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];
        List tcList = new ArrayList();
        try {
            String userStr = orgUtil.getUserStrInOrgs(orgUtil.getUserListInOrgs(orgUtil.getSubOrgs(orgId)));
            Query q = getDbAccessor().getSession().createQuery("from TcByWorkerAccount t where t.userId in (" + userStr + ") "
                    + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod order by lower(t.userId)"
                      );
            q.setDate("beginPeriod", beginPeriod);
            q.setDate("endPeriod", endPeriod);

            tcList = q.list();

            return tcList;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when select TcByWorkerAccount of orgnazition - " + orgId, ex);
        }
    }

    protected DtoHoursOnWeek createDtoHoursOnWeek(TcByWorkerAccount db, Date beginOfWeek, Date endOfWeek) {
        DtoWeeklyReportSumByPm dto = new DtoWeeklyReportSumByPm();
        dto.setUserId(db.getUserId());

        dto.setBeginPeriod(db.getBeginPeriod());
        dto.setEndPeriod(db.getEndPeriod());

        dto.setConfirmStatus(db.getConfirmStatus());

        //job code of user
        String jobCode = hrUtil.getUserJobCode(db.getUserId());
        dto.setJobCode(hrUtil.getJobCodeById(jobCode));

        //overtime hours
        lgOvertimeLeave.getOvertime(dto);
        dto.setOvertimeSumInTheAcnt(dto.getOvertimeSum()); //��dm��˵��OvertimeSumInTheAcnt = OvertimeSum
        dto.setOvertimeSumConfirmedInTheAcnt(dto.getOvertimeSumConfirmed()); //��dm��˵��OvertimeSumConfirmedInTheAcnt = OvertimeSumConfirmed

        //leave hours
        lgOvertimeLeave.getLeave(dto);

        return dto;
    }

    protected void mergeDtoAndDbOnWeek(DtoHoursOnWeek dto, TcByWorkerAccount db, Date beginOfWeek, Date endOfWeek){
        super.mergeDtoAndDbOnWeek(dto , db, beginOfWeek, endOfWeek);

        DtoWeeklyReportSumByPm dtoByPm = (DtoWeeklyReportSumByPm)dto;
        dtoByPm.setActualHour(dto.getSumHour()); //��dm��˵��sumHour = actual hour
        //dtoByPm.setActualHourConfirmed(dto.getSumHour()); //��dm��˵��sumHour = actual hour
        if( DtoWeeklyReport.STATUS_CONFIRMED.equals(db.getConfirmStatus()) ){
            if( dtoByPm.getActualHourConfirmed() == null ){
                dtoByPm.setActualHourConfirmed(getSumHour(db));
            }else{
                dtoByPm.setActualHourConfirmed(
                        dtoByPm.getActualHourConfirmed().add(getSumHour(db)));
            }
        }
    }

    protected DtoAllocateHour createDtoAllocateHour(TcByWorkerAccount db, Date beginPeriod, Date endPeriod) {
        DtoWeeklyReportSumOnMonthByPm dto = new DtoWeeklyReportSumOnMonthByPm();
        dto.setUserId(db.getUserId());
        //dto.setBeginPeriod(db.getBeginPeriod());
        //dto.setEndPeriod(db.getEndPeriod());

        dto.setBeginPeriod(beginPeriod);
        dto.setEndPeriod(endPeriod);

        dto.setAcntRid(db.getAcntRid());

        //init hours
        dto.setActualHour(new BigDecimal(0));
        dto.setActualHourConfirmed(new BigDecimal(0));
        dto.setActualHourConfirmedInTheAcnt(new BigDecimal(0));
        dto.setActualHourInTheAcnt(new BigDecimal(0));

        //job code of user
        String jobCode = hrUtil.getUserJobCode(db.getUserId());
        dto.setJobCode(hrUtil.getJobCodeById(jobCode));

        //overtime hours
        lgOvertimeLeave.getOvertime(dto);
        dto.setOvertimeSumInTheAcnt(dto.getOvertimeSum()); //��dm��˵��OvertimeSumInTheAcnt = OvertimeSum
        dto.setOvertimeSumConfirmedInTheAcnt(dto.getOvertimeSumConfirmed()); //��dm��˵��OvertimeSumConfirmedInTheAcnt = OvertimeSumConfirmed

        //leave hours
        lgOvertimeLeave.getLeave(dto);

        return dto;
    }

    protected void mergeDtoAndDbOnMonth(DtoAllocateHour dto, TcByWorkerAccount db, Date beginOfMonth, Date endOfMonth) {
        //����beginPeriod��endPeriod
        if (comDate.compareDate(dto.getBeginPeriod(), db.getBeginPeriod()) > 0) {
            dto.setBeginPeriod(db.getBeginPeriod());
        }

        if (comDate.compareDate(dto.getEndPeriod(), db.getEndPeriod()) < 0) {
            dto.setEndPeriod(db.getEndPeriod());
        }

        //�����е�ʱ�����
        BigDecimal sum = getSumHour(db);

        IDtoAllocateHourInTheAcnt dtoInAcnt = (IDtoAllocateHourInTheAcnt) dto;
        dtoInAcnt.setActualHourInTheAcnt(dtoInAcnt.getActualHourInTheAcnt().add(sum));
        dtoInAcnt.setActualHour(dtoInAcnt.getActualHour().add(sum));
        if (DtoWeeklyReport.STATUS_CONFIRMED.equals(db.getConfirmStatus())) {
            dtoInAcnt.setActualHourConfirmedInTheAcnt(dtoInAcnt.getActualHourConfirmedInTheAcnt().add(sum));
            dtoInAcnt.setActualHourConfirmed(dtoInAcnt.getActualHourConfirmed().add(sum));
        }
    }

    private DtoWeeklyReportSumByPm createEmptyDtoWeeklyReportSumByPm(String userId, Date beginPeriod, Date endPeriod) {
        DtoWeeklyReportSumByPm dto = new DtoWeeklyReportSumByPm();
        dto.setUserId(userId);
        dto.setBeginPeriod(beginPeriod);
        dto.setEndPeriod(endPeriod);
        for (int i = DtoHoursOnWeek.SATURDAY; i <= DtoHoursOnWeek.SUMMARY; i++) {
            dto.setHour(i, new BigDecimal(0));
        }
        dto.setActualHour(new BigDecimal(0));
        dto.setActualHourConfirmed(new BigDecimal(0));
        dto.setOvertimeSum(new BigDecimal(0));
        dto.setOvertimeSumConfirmed(new BigDecimal(0));
        dto.setOvertimeSumConfirmedInTheAcnt(new BigDecimal(0));
        dto.setOvertimeSumInTheAcnt(new BigDecimal(0));
        dto.setLeaveSum(new BigDecimal(0));
        dto.setLeaveSumConfirmed(new BigDecimal(0));
        return dto;
    }

    private DtoWeeklyReportSumOnMonthByPm createEmptyDtoWeeklyReportSumOnMonthByPm
            (String userId, Date beginPeriod, Date endPeriod) {
        DtoWeeklyReportSumOnMonthByPm dto = new DtoWeeklyReportSumOnMonthByPm();
        dto.setUserId(userId);
        dto.setBeginPeriod(beginPeriod);
        dto.setEndPeriod(endPeriod);

        dto.setActualHour(new BigDecimal(0));
        dto.setActualHourConfirmed(new BigDecimal(0));
        dto.setOvertimeSum(new BigDecimal(0));
        dto.setOvertimeSumConfirmed(new BigDecimal(0));
        dto.setOvertimeSumConfirmedInTheAcnt(new BigDecimal(0));
        dto.setOvertimeSumInTheAcnt(new BigDecimal(0));
        dto.setLeaveSum(new BigDecimal(0));
        dto.setLeaveSumConfirmed(new BigDecimal(0));
        return dto;
    }

    public static void main(String args[]) {
        try {
            HBComAccess hbCombAccess = new HBComAccess();
            LgWeeklyReportSumByDm logic = new LgWeeklyReportSumByDm();
            logic.setDbAccessor(hbCombAccess);
            hbCombAccess.newTx();
//            System.out.println(logic.getUserInOrg("UNIT00000195"));
//            System.out.println(logic.getByUserInOrg("UNIT00000195", null,null).size());
//            System.out.println(logic.getByUserInOrgIncSub("UNIT00000195", null,null).size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
