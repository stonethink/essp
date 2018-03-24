package server.essp.pms.activity.process.guideline.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.sql.RowSet;

import c2s.dto.DtoComboItem;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.wbs.DtoWbsGuideLine;
import com.wits.util.StringUtil;
import db.essp.pms.PmsActivityGuideline;
import db.essp.pms.PmsActivityGuidelineId;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.account.logic.LgAccount;
import server.framework.common.BusinessException;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckPoint;

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
public class LgActivityGuideLine extends AbstractESSPLogic {
    //activity guideline reference change �������б�
    private List<IActivityReferenceChangeListener> actReferenceChangeListeners = new
        ArrayList();

    public LgActivityGuideLine() {
        actReferenceChangeListeners.add(new LgQaCheckPoint());
    }

    /**
     * �������ϵ�anctRid,activityRid,refAcntRid,refActivityRid,desription�浽���ݿ���
     * 1.��acntRid��ģ��ʱ��ֱ�ӱ�������
     * 2.��acntRid����Ŀʱ������refAcntRid��refActivityRid��Ӧ��guideline��desription��
     * �ж�User���������Ƿ���ͬ����ͬʱ��Ϊ�գ�����ʱ����refAcntRid��refActivityRid��desription��
     * ��ͬʱֱ�ӱ���
     * @param dto DtoWbsGuideLine
     */
    public void saveActivityGuideLineInfo(DtoWbsGuideLine dto) {
        try {
            Long acntRid = dto.getAcntRid();
            Long activityRid = dto.getActivityRid();
            Long refAcntRid = dto.getRefAcntRid();
            Long refActRid = dto.getRefActivityRid();
            String description = dto.getDescription();

            //����Quality Activity��־
            updateQualityFlag(acntRid, activityRid,
                              dto.getIsQuality());

            String compareRef = null;

            if (isAccount(acntRid) && hasReference(refAcntRid,refActRid)) {
                PmsActivityGuideline refActvityGuideline = getActivityGuideline(
                    refAcntRid, refActRid);
                compareRef = (refActvityGuideline == null ? null :
                              refActvityGuideline.getDescription());
                //if description is referenced, set null
                if (StringUtil.nvl(description).equals(compareRef)) {
                    description = null;
                }
            }

            PmsActivityGuideline dbBean = getActivityGuideline(acntRid,
                activityRid);

            Long oldRefAcntRid = null;
            Long oldRefActRid = null;

            if (dbBean != null) {
                oldRefAcntRid = dbBean.getRefAcntRid();
                oldRefActRid = dbBean.getRefActRid();

                dbBean.setDescription(description);
                dbBean.setRefAcntRid(refAcntRid);
                dbBean.setRefActRid(refActRid);

                this.getDbAccessor().update(dbBean);

            } else {
                dbBean = new PmsActivityGuideline();
                PmsActivityGuidelineId pk = new PmsActivityGuidelineId();
                pk.setAcntRid(acntRid);
                pk.setActRid(activityRid);
                dbBean.setDescription(description);
                dbBean.setRefAcntRid(refAcntRid);
                dbBean.setRefActRid(refActRid);
                dbBean.setId(pk);

                this.getDbAccessor().save(dbBean);
            }

            //��������Ƿ����仯�������¼�
            if(hasChangedReference(oldRefAcntRid,oldRefActRid,refAcntRid,refActRid)){
                fireReferenceChanged(dbBean, oldRefAcntRid, oldRefActRid);
            }

        } catch (Exception ex) {
            throw new BusinessException("LgGiudeLine",
                                        "error while saving AcGuideLine!", ex);
        }
    }

    /**
     * �����ݿ��л�ȡActivityGuideLine
     * 1.��acntRid��ģ��ʱ��ֱ�Ӵ����ݿ��л�ȡ
     * 2.��acntRid����Ŀ��desriptionΪ��ʱ������refAcntRid��refActivityRid��Ӧ��guideline��desription��
     * @param acntRid Long
     * @param activityRid Long
     * @return DtoWbsGuideLine
     */
    public DtoWbsGuideLine getActivityGuideLineDto(Long acntRid, Long activityRid) {
        PmsActivityGuideline gl = getActivityGuideline(acntRid, activityRid);
        if(gl == null) {
            return null;
        }
        String description = gl.getDescription();
        Long refAcntRid = gl.getRefAcntRid();
        Long refActRid = gl.getRefActRid();

        //��description Ϊ�գ���������Ŀ���������á�ȡ���õ�Description
        if ("".equals(StringUtil.nvl(description)) && isAccount(acntRid) && hasReference(refAcntRid,refActRid)) {
            PmsActivityGuideline refGuideline = getActivityGuideline(refAcntRid,
                refActRid);
            description = (refGuideline == null ? null :
                          refGuideline.getDescription());
        }
        DtoWbsGuideLine dto = new DtoWbsGuideLine();
        dto.setAcntRid(gl.getId().getAcntRid());
        dto.setActivityRid(gl.getId().getActRid());
        dto.setRefAcntRid(refAcntRid);
        dto.setRefActivityRid(refActRid);
        dto.setDescription(description);
        dto.setBeLongTo(DtoKey.TYPE_ACTIVITY);
        return dto;
    }

    private boolean isAccount(Long acntRid) throws BusinessException {
        LgAccount lgAcnt = new LgAccount();
        boolean isTemplate = lgAcnt.load(acntRid).getIsTemplate();
        return !isTemplate;
    }
    private boolean hasReference(Long refAcntRid,Long refActRid){
        return refAcntRid != null && refActRid != null;
    }

    /**
     * �����ݿ����acntRid��activityRid��Ӧ��guideline��desription
     * @param acntRid Long
     * @param activityRid Long
     * @return String
     */
    public String getActivityDecription(Long acntRid, Long activityRid) {
        PmsActivityGuideline gl = getActivityGuideline(acntRid, activityRid);
        if(gl == null) {
            return null;
        }
        return gl.getDescription();
    }

    /**
     * ��ȡacntRid��Ŀ������Activity��װΪDtoComboItem
     * name = name , value = activityRid
     * @param acntRid Long
     * @return Vector
     */
    public Vector getActivityCombox(Long acntRid) {
        if (acntRid == null) {
            return null;
        }
        Vector activityV = new Vector();
        try {
            RowSet rs = this.getDbAccessor().executeQuery(
                "select * from pms_activity where acnt_rid = '" + acntRid +
                "'");

            while (rs.next()) {
                DtoComboItem item = new DtoComboItem(rs.getString("name"),
                                                     rs.getLong("activity_rid"));
                activityV.add(item);
            }
            rs.close();
        } catch (Exception ex) {
            throw new BusinessException("","get activity combox error",ex);
        }
        return activityV;
    }


    /**
     * �ж�activity guideline reference�Ƿ����˱仯
     * @param oldRefAcntRid Long
     * @param oldRefWbsRid Long
     * @param newRefAcntRid Long
     * @param newRefWbsRid Long
     * @return boolean
     */
    protected boolean hasChangedReference(Long oldRefAcntRid, Long oldRefActRid,
                                          Long newRefAcntRid, Long newRefActRid) {
        return!isEqual(oldRefAcntRid, newRefAcntRid) ||
            !isEqual(oldRefActRid, newRefActRid);
    }

    /**
     * �ж�����Long��ֵ�Ƿ���ȣ�ͬΪnullҲ��ȡ�
     * @param num1 Long
     * @param num2 Long
     * @return boolean
     */
    private boolean isEqual(Long num1, Long num2) {
        String s1 = "" + num1;
        String s2 = "" + num2;
        return s1.equals(s2);
    }


    /**
     * �����ݿ��ȡPmsActivityGuideline
     * @param refAcntRid Long
     * @param activityRid Long
     * @return PmsWbsGuideline
     */
    private PmsActivityGuideline getActivityGuideline(Long acntRid,
        Long activityRid) {
        PmsActivityGuidelineId pk = new PmsActivityGuidelineId();
        pk.setAcntRid(acntRid);
        pk.setActRid(activityRid);
        try {
            return (PmsActivityGuideline)
                this.getDbAccessor().getSession().get(PmsActivityGuideline.class, pk);
        } catch (Exception e) {
            throw new BusinessException("find PmsActivityGuideline error!", e);
        }
    }

    /**
     * updateQualityFlag
     *
     * @param acntRid Long
     * @param activityRid Long
     * @param qualityFlag String
     */
    private void updateQualityFlag(Long acntRid, Long activityRid,
                                  String qualityFlag) {
        try {
            String sql = "update pms_activity  set is_quality_act = '" +
                         qualityFlag
                         + "' where activity_rid = '" + activityRid
                         + "' and acnt_rid = '" + acntRid + "'";
            this.getDbAccessor().executeUpdate(sql);
        } catch (Exception ex) {
            throw new BusinessException("LgGiudeLine",
                                        "Update activity quality flag error",
                                        ex);
        }
    }

    /**
     * ��ȡĳActivity �Ƿ���Quality Activity
     * @param refActivitRid Long
     * @return String
     */
    public String getQualityFlag(Long acntRid, Long activityRid) {
        String flag = null;
        RowSet rs = this.getDbAccessor().executeQuery(
            "select * from pms_activity where activity_rid = '" +
            activityRid +
            "' and acnt_rid = '" + acntRid + "'");
        try {
            if(rs.next()) {
                flag = rs.getString("is_quality_act");
            }
        } catch (Exception ex) {
            throw new BusinessException("LgActivityGiudeLine",
                                        "get activity quality flag error", ex);
        }
        return flag;
    }


    private void fireReferenceChanged(PmsActivityGuideline activityGuideLine,
                                      Long oldRefAcntRid,
                                      Long oldRefActivityRid) {
        //֪ͨ���м�����
        for (int i = 0; i < actReferenceChangeListeners.size(); i++) {
            IActivityReferenceChangeListener l =
                (IActivityReferenceChangeListener) actReferenceChangeListeners.
                                                 get(i);
            l.activityReferenceChange(activityGuideLine, oldRefAcntRid,
                                      oldRefActivityRid);
        }
    }
}
