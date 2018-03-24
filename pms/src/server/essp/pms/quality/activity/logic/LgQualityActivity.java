package server.essp.pms.quality.activity.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import java.util.List;
import java.util.ArrayList;
import server.framework.common.BusinessException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Query;
import db.essp.pms.Activity;
import db.essp.pms.ActivityQuality;
import db.essp.pms.PcbParameter;
import c2s.essp.pms.quality.activity.DtoQualityActivity;
import db.essp.pms.ActivityPK;
import db.essp.pms.QualityActivityPK;
import c2s.essp.common.account.IDtoAccount;
import server.essp.pms.account.pcb.logic.LgPcb;
import db.essp.pms.PmsPcbParameter;
import com.wits.util.*;
import c2s.dto.*;
import server.essp.pms.quality.QualityConfig;

public class LgQualityActivity extends AbstractESSPLogic {
    public LgQualityActivity() {
    }

    //check the information of the table pms_activity and pms activityQuality;
    private List listQualityActivity(Long acntID) {
        List list = new ArrayList();
        String sAcntID = acntID.toString();
        try {

            Session session = this.getDbAccessor().getSession();
            Query query = session.createQuery(
                "from Activity as a where a.pk.acntRid=:sAcntID and a.isActivityQuality = '1'");
            query.setString("sAcntID", sAcntID);
            list = query.list();

        } catch (Exception ex) {
            log.error(
                "Error: query a PMS_ACTIVITY_QUALITY record by HQL failed!");
            throw new BusinessException(ex);
        }
        return list;
    }

    public List listQualityActivity(IDtoAccount accountDto) {
        if (accountDto == null || accountDto.getRid() == null) {
            return null;
        }
        List list = listQualityActivity(accountDto.getRid());
        List result = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Activity act = (Activity) list.get(i);
            DtoQualityActivity dtoQA = new DtoQualityActivity();
            dtoQA.setName(act.getName());
            dtoQA.setActivityRid(act.getPk().getActivityId());
            dtoQA.setAcntRid(act.getPk().getAcntRid());
            ActivityQuality aq = act.getActivityQuality();
            String pcbDensityID = null;
            String pcbDefectRateID = null;
            if (aq != null) {
                DtoUtil.copyBeanToBean(dtoQA, aq);
                pcbDensityID = QualityConfig.getQulityActDensityPcbId(aq.getType()) ;
                pcbDefectRateID = QualityConfig.getQulityActDefectRatePcbId(aq.getType());
            }
            dtoQA.setCode(StringUtil.nvl(act.getCode()));
            Long acntRid = act.getPk().getAcntRid();
            LgPcb pcb = new LgPcb();
            //load plan-density, plan-defect rate and their units;
            PmsPcbParameter pcbDensity = pcb.findFromPcb(acntRid,
                pcbDensityID);
            PmsPcbParameter pcbDefectRate = pcb.findFromPcb(acntRid,
                pcbDefectRateID);
            if (pcbDefectRate != null) {
                dtoQA.setDensityUnits(pcbDensity.getUnit());
                dtoQA.setPlanDensity(pcbDensity.getPlan());
                dtoQA.setDefectRateUnits(pcbDensity.getUnit());
                dtoQA.setPlanDefectRate(pcbDefectRate.getPlan());
            }
            result.add(dtoQA);
        }

        return result;
    }


    //check the information of the table pms_activity and pms activityQuality;
    public Activity findQualityActivity(Long acntRid, Long activityRid) {
        Activity qa = new Activity();

        try {

            Session session = this.getDbAccessor().getSession();
            Query query = session.createQuery(
                "from Activity as a where a.pk.acntRid=:acntRid and a.pk.activityId=:activityRid");
            query.setLong("acntRid", acntRid.longValue());
            query.setLong("activityRid", activityRid.longValue());
            qa = (Activity) query.setMaxResults(1).uniqueResult(); ;

        } catch (Exception ex) {
            log.error(
                "Error: query a PMS_ACTIVITY_QUALITY record by HQL failed!");
            throw new BusinessException(ex);
        }
        return qa;
    }

    public void update(DtoQualityActivity dtoQualityActivity) throws
        BusinessException {
        boolean insertFlag = false;
        ActivityPK qualityActivityPK = new ActivityPK();
        qualityActivityPK.setAcntRid(dtoQualityActivity.getAcntRid());
        qualityActivityPK.setActivityId(dtoQualityActivity.getActivityRid());
        try {
            ActivityQuality activityTest = (ActivityQuality)this.getDbAccessor().
                                           getSession().get(
                                               ActivityQuality.class,
                                               qualityActivityPK);
            if (activityTest == null) {
                activityTest = new ActivityQuality(qualityActivityPK);
                insertFlag = true;
            }

            DtoUtil.copyBeanToBean(activityTest, dtoQualityActivity);

            if (insertFlag) {
                getDbAccessor().save(activityTest);
                log.info("Insert a activity quality: " +
                         activityTest.getProduction());
            } else {
                getDbAccessor().update(activityTest);
                log.info("Update a activity quality: " +
                         activityTest.getProduction());
            }
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000",
                                        "update quality activity error.");
        }

    }


    public void updateDefectNum(DtoQualityActivity dtoQualityActivity) throws
        BusinessException {
        boolean insertFlag = false;
        QualityActivityPK qualityActivityPK = new QualityActivityPK();
        qualityActivityPK.setAcntRid(dtoQualityActivity.getAcntRid());
        qualityActivityPK.setActivityId(dtoQualityActivity.getActivityRid());

        try {
            ActivityQuality activityTest = (ActivityQuality)this.getDbAccessor().
                                           getSession().get(
                                               ActivityQuality.class,
                                               qualityActivityPK.getPk());
            if (activityTest == null) {
                activityTest = new ActivityQuality(qualityActivityPK.getPk());
                insertFlag = true;
            }

            DtoUtil.copyBeanToBean(activityTest, dtoQualityActivity);

            if (insertFlag) {
                getDbAccessor().save(activityTest);
                log.info("Insert a activity quality: " +
                         activityTest.getProduction());
            } else {
                getDbAccessor().update(activityTest);
                log.info("Update a activity quality: " +
                         activityTest.getProduction());
            }

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "Update defect num error.");
        }

    }


    public static void main(String[] args) {
        LgQualityActivity lg = new LgQualityActivity();
        try {

            Activity l = lg.findQualityActivity(new Long(6042), new Long(2));
            System.out.print(l.getActivityQuality().getMethod());
//            for (int i = 0; i < l.size(); i++) {
//                Activity dqa = (Activity) l.get(i);
//                System.out.println(dqa.getName());
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
