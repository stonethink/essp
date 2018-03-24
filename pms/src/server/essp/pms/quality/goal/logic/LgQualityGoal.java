package server.essp.pms.quality.goal.logic;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pms.quality.goal.DtoQualityGoal;
import db.essp.pms.QualityGoal;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import server.essp.pms.account.pcb.logic.LgPcb;
import db.essp.pms.PmsPcbParameter;
import c2s.essp.pms.quality.goal.DtoReleaseRecord;
import com.wits.util.Config;
import server.essp.pms.quality.QualityConfig;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgQualityGoal extends AbstractESSPLogic {
    /**
     *
     * @param acntRid Long
     * @return List DtoQualityGoal
     */
    public List listQualityGoal(Long acntRid) {
        List list = new ArrayList();
        try {
            list = this.getDbAccessor().getSession().createQuery(
                "from QualityGoal as t " +
                "where t.acntRid=:acntRid " +
                " and t.rst = 'N'")
                   .setParameter("acntRid", acntRid)
                   .list();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000", "List QualityGoal error.",
                                        ex);
        }
        return InitData(acntRid, dbBeanToDtoBean(list));

    }


    /**
     * change DB Bean to DtoBean
     * @param list List
     * @return List DtoBean
     */
    private List dbBeanToDtoBean(List list) {
        for (int i = 0; i < list.size(); i++) {
            QualityGoal bean = (QualityGoal) (list.get(i));
            DtoQualityGoal dto = new DtoQualityGoal();
            exchangeDbBeanToDto(dto, bean);
            list.set(i, dto);
        }
        return list;
    }

    private List InitData(Long acntRid, List list) {
        LgPcb lgPcb = new LgPcb();
        LgQualityGoalRelease lg = new LgQualityGoalRelease();
        String pcbId = QualityConfig.getQualificationRatePcbId();
        PmsPcbParameter dbBean = lgPcb.findFromPcb(acntRid, pcbId);
        String Unit = "";
        Double planRate = new Double(0);
        if (dbBean != null) {
            Unit = dbBean.getUnit();
            planRate = dbBean.getPlan();
        } else {
            log.error("In account: " + acntRid +
                      "(Rid) can't find quality PCB: " + pcbId + " (Id)");
//            throw new BusinessException("This account have no quality PCB!");
        }
        for (int i = 0; i < list.size(); i++) {
            DtoQualityGoal dto = (DtoQualityGoal) list.get(i);
            dto.setUnit(Unit);
            dto.setPlanRate(planRate);
            List qualityGoalReleaseList = lg.listReleaseRecord(dto.getRid());
            initAtualRate(dto, qualityGoalReleaseList);
        }
        return list;
    }

    private void initAtualRate(DtoQualityGoal dataBean,
                               List qualityGoalReleaseList) {
        long actualSize = 0;
        long actualDefects = 0;
        double actualRate = 0;
        if (dataBean == null || qualityGoalReleaseList == null
            || qualityGoalReleaseList.size() < 1) {
            return;
        }
        if (dataBean.getIsSumSize().booleanValue()) {

            for (int i = 0; i < qualityGoalReleaseList.size(); i++) {
                actualSize +=
                    ((DtoReleaseRecord) qualityGoalReleaseList.get(i)).
                    getSize().longValue();
            }
            dataBean.setActualSize(new Long(actualSize));
        } else
        //set acturalSize as the last element'size
        {
            //get the last element in List qualityGoalReleaseList
            DtoReleaseRecord dto = (DtoReleaseRecord)
                                   qualityGoalReleaseList.get(
                                       qualityGoalReleaseList.size() -
                                       1);
            actualSize = dto.getSize().longValue();
            dataBean.setActualSize(new Long(actualSize));
        }
        //
        if (dataBean.getIsSumDefect().booleanValue()) {
            for (int i = 0; i < qualityGoalReleaseList.size(); i++) {
                actualDefects +=
                    ((DtoReleaseRecord) qualityGoalReleaseList.get(i)).
                    getDefects().longValue();
            }
            dataBean.setActualDefect(new Long(actualDefects));
        } else {
            DtoReleaseRecord dto = (DtoReleaseRecord)
                                   qualityGoalReleaseList.get(
                                       qualityGoalReleaseList.size() -
                                       1);
            actualDefects = dto.getDefects().longValue();
            dataBean.setActualDefect(new Long(actualDefects));
        }
        actualRate = actualDefects / ((double) actualSize);
        dataBean.setActualRate(new Double(actualRate));
    }

    /**
     * exchange DB Bean to DtoBean
     * @param dto DtoQualityGoal
     * @param bean QualityGoal
     */
    private void exchangeDbBeanToDto(DtoQualityGoal dto, QualityGoal bean) {
        DtoUtil.copyBeanToBean(dto, bean);
        dto.setRid(bean.getRid());
    }

    /**
     * save DtoQualityGoal to database
     * @param dto DtoQualityGoal
     */
    public void saveDto(DtoQualityGoal dto) {
        if (IDto.OP_INSERT.equals(dto.getOp())) {
            insertQualityGoal(dto);
            dto.setOp(IDto.OP_NOCHANGE);
        } else if (IDto.OP_MODIFY.equals(dto.getOp())) {
            updateQualityGoal(dto);
            dto.setOp(IDto.OP_NOCHANGE);
        }
    }

    public void insertQualityGoal(DtoQualityGoal dto) {
        QualityGoal bean = new QualityGoal();
        exchangeDtoToDbBean(bean, dto);
        try {
            this.getDbAccessor().save(bean);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "Insert QualityGoal error.", ex);
        }
        exchangeDbBeanToDto(dto, bean);
    }

    /**
     * update a qa check point
     * @param dto DtoQaCheckPoint
     */
    public void updateQualityGoal(DtoQualityGoal dto) {
        QualityGoal bean = new QualityGoal();
        exchangeDtoToDbBean(bean, dto);
        try {
            this.getDbAccessor().update(bean);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "Update Quality Goal error.", ex);
        }
        exchangeDbBeanToDto(dto, bean);
    }


    private void exchangeDtoToDbBean(QualityGoal bean, DtoQualityGoal dto) {
        DtoUtil.copyBeanToBean(bean, dto);

    }

    public static void main(String[] args) {
        LgQualityGoal lgqualitygoal = new LgQualityGoal();
    }

    /**
     * deleteDtoQuality
     *
     * @param dtoQualityGoal DtoQualityGoal
     */
    public void deleteDtoQuality(DtoQualityGoal dtoQualityGoal) {

        try {
            QualityGoal qualityGoal = new QualityGoal();
            DtoUtil.copyBeanToBean(qualityGoal, dtoQualityGoal);

            //delect pms_release_record where goal_rid=dtoQualityGoal.rid
            String sql = "delete pms_release_record where goal_rid = " +
                         dtoQualityGoal.getRid();
            this.getDbAccessor().executeUpdate(sql);

            this.getDbAccessor().delete(qualityGoal);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
