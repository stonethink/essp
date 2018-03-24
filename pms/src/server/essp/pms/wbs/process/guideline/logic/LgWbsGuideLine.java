package server.essp.pms.wbs.process.guideline.logic;

import c2s.essp.pms.wbs.DtoWbsGuideLine;
import com.wits.util.StringUtil;
import db.essp.pms.PmsWbsGuideline;
import db.essp.pms.PmsWbsGuidelineId;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.account.logic.LgAccount;
import server.framework.common.BusinessException;
import net.sf.hibernate.*;
import java.util.List;
import c2s.dto.DtoUtil;
import java.util.ArrayList;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckPoint;
import java.util.Vector;
import javax.sql.RowSet;
import c2s.dto.DtoComboItem;
import c2s.essp.common.code.DtoKey;

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
public class LgWbsGuideLine extends AbstractESSPLogic {
    public LgWbsGuideLine(){
        wbsReferenceChangeListeners.add(new LgQaCheckPoint());
    }

    /**
     * 将界面上的anctRid,wbsRid,refAcntRid,refWbsRid,desription存到数据库中
     * 1.若acntRid是模版时，直接保存数据
     * 2.若acntRid是项目时，查找refAcntRid和refWbsRid对应的guideline的desription，
     * 判断User输入与其是否相同，相同时设为空（查找时引用refAcntRid和refWbsRid的desription）
     * 不同时直接保存
     * @param dto DtoWbsGuideLine
     */
    public void saveWbsGuideLineInfo(DtoWbsGuideLine dto) {
        try {
            Long acntRid = dto.getAcntRid();
            Long wbsRid = dto.getWbsRid();
            Long refAcntRid = dto.getRefAcntRid();
            Long refWbsRid = dto.getRefWbsRid();
            String description = dto.getDescription();
            LgAccount lgAcnt = new LgAccount();
            boolean isTemplate = lgAcnt.load(acntRid).getIsTemplate();
            String compareRef = null;

            if (!isTemplate && refAcntRid != null && refWbsRid != null) {
                PmsWbsGuideline refWbsGuideline = getWbsGuideline(refAcntRid,refWbsRid);
                compareRef = (refWbsGuideline == null ? null : refWbsGuideline.getDescription());
                //if description is referenced, set null
                if(StringUtil.nvl(description).equals(compareRef)){
                    description = null;
                }
            }

            PmsWbsGuideline dbBean = getWbsGuideline(acntRid, wbsRid);

            Long oldRefAcntRid = null;
            Long oldRefWbsRid = null;
            if (dbBean != null) {

                oldRefWbsRid = dbBean.getRefWbsRid();
                oldRefAcntRid = dbBean.getRefAcntRid();

                dbBean.setDescription(description);
                dbBean.setRefAcntRid(refAcntRid);
                dbBean.setRefWbsRid(refWbsRid);
                this.getDbAccessor().update(dbBean);
            } else {
                dbBean = new PmsWbsGuideline();
                PmsWbsGuidelineId pk = new PmsWbsGuidelineId();
                pk.setAcntRid(acntRid);
                pk.setWbsRid(wbsRid);
                dbBean.setDescription(description);
                dbBean.setRefAcntRid(refAcntRid);
                dbBean.setRefWbsRid(refWbsRid);
                dbBean.setId(pk);
                this.getDbAccessor().save(dbBean);
            }

            //判断引用是否有变化
            if(hasChangedReference(oldRefAcntRid,oldRefWbsRid,refAcntRid,refWbsRid)){
                fireReferenceChanged(dbBean, oldRefAcntRid, oldRefWbsRid);
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception ex) {
            throw new BusinessException("LgGiudeLine",
                                        "error while saving GuideLine!", ex);
        }
    }
    /**
     * getWbsDescription
     *
     * @param acntRid Long
     * @param activityRid Long
     */
    public String getWbsDescription(Long acntRid, Long wbsRid) {
        PmsWbsGuideline guideline = getWbsGuideline(acntRid,wbsRid);
        if(guideline == null)
            return null;
        return guideline.getDescription();
    }
    /**
     * 获取项目(is_template='0') Wbs Guideline信息
     * @param acntRid Long
     * @param wbsRid Long
     * @return DtoWbsGuideLine
     */
    public DtoWbsGuideLine getDtoWbsGuideline(Long acntRid, Long wbsRid){
        PmsWbsGuideline guideline = getWbsGuideline(acntRid, wbsRid);
        if(guideline == null) {
            return null;
        }
        String description = guideline.getDescription();
        Long refAcntRid = guideline.getRefAcntRid();
        Long refWbsRid = guideline.getRefWbsRid();
        LgAccount lgAcnt = new LgAccount();
        boolean isTemplate = lgAcnt.load(acntRid).getIsTemplate();
        //当description 为空，类型是项目，存在引用。取引用的Description
        if ("".equals(StringUtil.nvl(description)) && !isTemplate && refAcntRid != null && refWbsRid != null) {
            PmsWbsGuideline refWbsGuideline = getWbsGuideline(refAcntRid,
                refWbsRid);
            description = (refWbsGuideline == null ? null :
                          refWbsGuideline.getDescription());
        }
        DtoWbsGuideLine dto = new DtoWbsGuideLine();
        DtoUtil.copyBeanToBean(dto, guideline);
        dto.setAcntRid(guideline.getId().getAcntRid());
        dto.setWbsRid(guideline.getId().getWbsRid());
        dto.setDescription(description);
        dto.setBeLongTo(DtoKey.TYPE_WBS);
        return dto;

    }
    /**
     * getWbs 得到一个account的wbs
     *
     * @param refAcntRid Object
     * @return List
     */
    public Vector getWbsCombox(Long refAcntRid) {
        if (refAcntRid == null) {
            return null;
        }
        Vector dtoWbsList = new Vector();
        try {
            RowSet rs = this.getDbAccessor().executeQuery(
                "select * from pms_wbs where acnt_rid = '" + refAcntRid + "'");

            while (rs.next()) {
                DtoComboItem item = new DtoComboItem(rs.getString("name"),
                                                     rs.getLong("wbs_rid"));
                dtoWbsList.add(item);
            }
            rs.close();

        } catch (Exception ex) {
            log.error(ex);
        }
        return dtoWbsList;

    }

    /**
     * getReferenceWbsDescription
     *
     * @param refAcntRid Long
     * @param refWbsRid Long
     * @return String
     */
    private PmsWbsGuideline getWbsGuideline(Long acntRid,Long wbsRid) {
        PmsWbsGuidelineId pk = new PmsWbsGuidelineId();
        pk.setAcntRid(acntRid);
        pk.setWbsRid(wbsRid);
        try {
            return (PmsWbsGuideline)
                this.getDbAccessor().getSession().get(PmsWbsGuideline.class, pk);
        } catch (Exception ex) {
            throw new BusinessException("", "find PmsWbsGuideline error!", ex);
        }
    }
    /**
     * 判断wbs guideline reference是否发生了变化
     * @param oldRefAcntRid Long
     * @param oldRefWbsRid Long
     * @param newRefAcntRid Long
     * @param newRefWbsRid Long
     * @return boolean
     */
    protected boolean hasChangedReference(Long oldRefAcntRid, Long oldRefWbsRid,Long newRefAcntRid,Long newRefWbsRid){
        return !isEqual(oldRefAcntRid,newRefAcntRid) || !isEqual(oldRefWbsRid,newRefWbsRid);
    }
    private boolean isEqual(Long num1,Long num2){
        String s1 = "" + num1;
        String s2 = "" + num2;
        return s1.equals(s2);
    }
    /**
     * 触发wbs guideline reference changed
     * @param oldRefAcntRid Long
     * @param oldRefWbsRid Long
     * @param newRefAcntRid Long
     * @param newRefWbsRid Long
     */
    private void fireReferenceChanged(PmsWbsGuideline wbsGuideLine, Long oldRefAcntRid, Long oldRefWbsRid){
        //通知所有监听器
        for(int i =0;i < wbsReferenceChangeListeners.size() ;i ++){
            IWbsReferenceChangeListener l = (IWbsReferenceChangeListener) wbsReferenceChangeListeners.get(i);
            l.wbsReferenceChange(wbsGuideLine,oldRefAcntRid,oldRefWbsRid);
        }
    }

    //wbs guideline reference change 监听器列表
    private List<IWbsReferenceChangeListener> wbsReferenceChangeListeners = new ArrayList();
}
