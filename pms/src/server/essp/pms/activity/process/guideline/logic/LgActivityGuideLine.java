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
    //activity guideline reference change 监听器列表
    private List<IActivityReferenceChangeListener> actReferenceChangeListeners = new
        ArrayList();

    public LgActivityGuideLine() {
        actReferenceChangeListeners.add(new LgQaCheckPoint());
    }

    /**
     * 将界面上的anctRid,activityRid,refAcntRid,refActivityRid,desription存到数据库中
     * 1.若acntRid是模版时，直接保存数据
     * 2.若acntRid是项目时，查找refAcntRid和refActivityRid对应的guideline的desription，
     * 判断User输入与其是否相同，相同时设为空（查找时引用refAcntRid和refActivityRid的desription）
     * 不同时直接保存
     * @param dto DtoWbsGuideLine
     */
    public void saveActivityGuideLineInfo(DtoWbsGuideLine dto) {
        try {
            Long acntRid = dto.getAcntRid();
            Long activityRid = dto.getActivityRid();
            Long refAcntRid = dto.getRefAcntRid();
            Long refActRid = dto.getRefActivityRid();
            String description = dto.getDescription();

            //更新Quality Activity标志
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

            //如果引用是发生变化，触发事件
            if(hasChangedReference(oldRefAcntRid,oldRefActRid,refAcntRid,refActRid)){
                fireReferenceChanged(dbBean, oldRefAcntRid, oldRefActRid);
            }

        } catch (Exception ex) {
            throw new BusinessException("LgGiudeLine",
                                        "error while saving AcGuideLine!", ex);
        }
    }

    /**
     * 从数据库中获取ActivityGuideLine
     * 1.若acntRid是模版时，直接从数据库中获取
     * 2.若acntRid是项目且desription为空时，查找refAcntRid和refActivityRid对应的guideline的desription，
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

        //当description 为空，类型是项目，存在引用。取引用的Description
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
     * 从数据库查找acntRid和activityRid对应的guideline的desription
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
     * 获取acntRid项目想所有Activity封装为DtoComboItem
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
     * 判断activity guideline reference是否发生了变化
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
     * 判断两个Long数值是否相等，同为null也相等。
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
     * 从数据库获取PmsActivityGuideline
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
     * 获取某Activity 是否是Quality Activity
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
        //通知所有监听器
        for (int i = 0; i < actReferenceChangeListeners.size(); i++) {
            IActivityReferenceChangeListener l =
                (IActivityReferenceChangeListener) actReferenceChangeListeners.
                                                 get(i);
            l.activityReferenceChange(activityGuideLine, oldRefAcntRid,
                                      oldRefActivityRid);
        }
    }
}
