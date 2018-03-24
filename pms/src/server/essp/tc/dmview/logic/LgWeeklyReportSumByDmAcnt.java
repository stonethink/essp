
package server.essp.tc.dmview.logic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import c2s.essp.common.account.IDtoAccount;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByDmAcnt;
import db.essp.tc.TcByWorkerAccount;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import itf.hr.HrFactory;
import itf.hr.IHrUtil;
import itf.orgnization.IOrgnizationUtil;
import itf.orgnization.OrgnizationFactory;
import net.sf.hibernate.Query;
import server.essp.tc.common.LgOvertimeLeaveExtend;
import server.essp.tc.common.LgWeeklyReportSum;
import server.framework.common.BusinessException;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByDmAcntWorker;
import server.essp.tc.common.LgTcCommon;
import java.util.TreeMap;
import java.util.*;

public class LgWeeklyReportSumByDmAcnt extends LgWeeklyReportSum {
    IHrUtil hrUtil = HrFactory.create();
    LgOvertimeLeaveExtend lgOvertimeLeave = new LgOvertimeLeaveExtend();
    IOrgnizationUtil orgUtil = OrgnizationFactory.create();
    IAccountUtil acntUtil = AccountFactory.create();

    /**
     * ���ã����¡����ܡ��ڣ��г�org�µ�ÿ��account��weeklyReportSum��Ϣ��ֻ������ʱ����Ϣ������ÿ���ʱ����Ϣ
     * @param orgId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List listByAcntInOrg(String orgId, Date beginPeriod, Date endPeriod) {
        List tcList = getByAcntInOrg(orgId, beginPeriod, endPeriod);
        return dbList2DtoListByAcnt(tcList, beginPeriod, endPeriod);
    }

    public List listByAcntInOrgIncSub(String orgId, Date beginPeriod, Date endPeriod) {
        List tcList = getByAcntInOrgIncSub(orgId, beginPeriod, endPeriod);
        return dbList2DtoListByAcnt(tcList, beginPeriod, endPeriod);
    }

    public List listByUserInTheAcnt(Long acntRid, Date beginPeriod, Date endPeriod) {
        List tcList = getByAcnt(acntRid, beginPeriod, endPeriod);
        return dbList2DtoListByUserInTheAcnt(tcList, beginPeriod, endPeriod);
    }

    /**
     * ���ؽ����Ԫ��ΪDtoWeeklyReportSumByDmAcnt,��������Ŀ��
     * �ܹ���ʱ��/��ȷ�ϵ��ܹ���ʱ�䣬 �ܼӰ�ʱ��/��ȷ�ϵ��ܼӰ�ʱ�䣬�ܷ��乤ʱ/��ȷ�ϵ��ܷ��乤ʱ
     */
    protected List dbList2DtoListByAcnt(List dbList, Date beginPeriod, Date endPeriod) {

        Map dtoMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);

        for (Iterator iter = dbList.iterator(); iter.hasNext(); ) {
            TcByWorkerAccount tc = (TcByWorkerAccount) iter.next();

            Long acntRid = tc.getAcntRid();
            IDtoAccount dtoAcnt = acntUtil.getAccountByRID(acntRid);
            String acntId = dtoAcnt.getId();
            DtoWeeklyReportSumByDmAcnt dto = (DtoWeeklyReportSumByDmAcnt) dtoMap.get(acntId);
            if (dto == null) {
                dto = new DtoWeeklyReportSumByDmAcnt();
                dto.setAcntRid(acntRid);
                dto.setBeginPeriod(beginPeriod);
                dto.setEndPeriod(endPeriod);
                IDtoAccount acnt = acntUtil.getAccountByRID(acntRid);
                if (acnt != null) {
                    dto.setAcntName(acnt.getId() + "--" + acnt.getName());
                    dto.setAcntType(acnt.getType());
                }

                dto.setActualHour( new BigDecimal(0) );
                dto.setActualHourConfirmed( new BigDecimal(0) );

                //overtime hours
                lgOvertimeLeave.getAcntOvertime(dto);

                //leave hours
                lgOvertimeLeave.getLeave(dto);

                dtoMap.put(acntId, dto);
            }

            BigDecimal sumhour = getSumHour(tc);
            dto.setActualHour(dto.getActualHour().add(sumhour));
            if (DtoWeeklyReport.STATUS_CONFIRMED.equals(tc.getConfirmStatus())) {
                dto.setActualHourConfirmed(dto.getActualHourConfirmed().add(sumhour));
            }
        }

        List dtoList = new ArrayList();
        for (Iterator iter = dtoMap.values().iterator(); iter.hasNext(); ) {
            dtoList.add( iter.next() );
        }
        return dtoList;
    }


    protected List dbList2DtoListByUserInTheAcnt(List dbList, Date beginPeriod, Date endPeriod) {

        Map dtoMap = new HashMap();

        for (Iterator iter = dbList.iterator(); iter.hasNext(); ) {
            TcByWorkerAccount tc = (TcByWorkerAccount) iter.next();

            Long acntRid = tc.getAcntRid();
            DtoWeeklyReportSumByDmAcntWorker dto = new DtoWeeklyReportSumByDmAcntWorker();
            dto.setUserId(tc.getUserId());
            dto.setAcntRid(acntRid);
            dto.setBeginPeriod(tc.getBeginPeriod());
            dto.setEndPeriod(tc.getEndPeriod());
            dto.setComments(tc.getComments());
            dto.setConfirmStatus(tc.getConfirmStatus());

            //job code of user
            String jobCode = hrUtil.getUserJobCode(tc.getUserId());
            dto.setJobCode(hrUtil.getJobCodeById(jobCode));

            //overtime hours
            lgOvertimeLeave.getOvertimeInTheAcnt(dto);

            //leave hours
            lgOvertimeLeave.getLeave(dto);

            //�ڱ���Ŀ�Ĺ���ʱ��
            dto.setSumHour(getSumHour(tc));

            //��������Ŀ�Ĺ���ʱ��
            getActualHourByUser(dto);

            dtoMap.put(dto.getUserId(), dto);
        }

        List dtoList = new ArrayList();
        for (Iterator iter = dtoMap.values().iterator(); iter.hasNext(); ) {
            dtoList.add( iter.next() );
        }
        return dtoList;
    }


    /**
     * ����orgnazition�µ�ÿ��Ŀ��weeklyReportSum
     * ���ʱ������Ϊ���ܡ�����ô����Ŀ�е�ÿ��user�����ܻ���һ������
     * ���ʱ������Ϊ���¡�����ô�����ڵ�ÿ���� & ÿ��user�����ܻ���һ������
     * @param orgId String
     * @param beginPeriod Date
     * @param endPeriod Date
     * @return List
     */
    public List getByAcntInOrg(String orgId, Date beginPeriod, Date endPeriod) {
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];

        List tcList = new ArrayList();
        try {
            String acntStr = orgUtil.getAcntStrInOrgs(orgUtil.getAcntListInOrgs("'" + orgId + "'"));
            Query q = getDbAccessor().getSession().createQuery("from TcByWorkerAccount t where t.acntRid in (" + acntStr + ") "
                                                               + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod"
                      );
            q.setDate("beginPeriod", beginPeriod);
            q.setDate("endPeriod", endPeriod);

            tcList = q.list();

            return tcList;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when select TcByWorkerAccount of orgnazition - " + orgId, ex);
        }
    }

    public List getByAcntInOrgIncSub(String orgId, Date beginPeriod, Date endPeriod) {
        Date[] ds = LgTcCommon.resetBeginDate(beginPeriod, endPeriod);
        beginPeriod = ds[0];
        endPeriod = ds[1];
        List tcList = new ArrayList();
        try {
            String acntStr = orgUtil.getAcntStrInOrgs(orgUtil.getAcntListInOrgs(orgUtil.getSubOrgs(orgId)));
            Query q = getDbAccessor().getSession().createQuery("from TcByWorkerAccount t where t.acntRid in (" + acntStr + ") "
                                                               + " and t.beginPeriod >=:beginPeriod and t.endPeriod <=:endPeriod"
                      );
            q.setDate("beginPeriod", beginPeriod);
            q.setDate("endPeriod", endPeriod);

            tcList = q.list();

            return tcList;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when select TcByWorkerAccount of orgnazition - " + orgId, ex);
        }
    }
}
