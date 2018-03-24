package server.essp.pms.wbs.process.checklist.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import java.util.List;
import db.essp.pms.PmsQaCheckPoint;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import c2s.dto.DtoUtil;
import server.framework.common.BusinessException;
import java.util.ArrayList;
import server.essp.pms.wbs.logic.LgAcntSeq;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import c2s.dto.IDto;
import db.essp.pms.PmsQaCheckPointPK;
import server.essp.pms.wbs.process.guideline.logic.IWbsReferenceChangeListener;
import db.essp.pms.PmsQaCheckAction;
import db.essp.pms.PmsQaCheckActionPK;
import db.essp.pms.PmsWbsGuideline;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.qa.DtoQaCheckAction;
import server.essp.pms.activity.process.guideline.logic.IActivityReferenceChangeListener;
import db.essp.pms.PmsActivityGuideline;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipeng.xu
 * @version 1.0
 */
public class LgQaCheckPoint extends AbstractESSPLogic
    implements IWbsReferenceChangeListener,IActivityReferenceChangeListener {
    private LgQaCheckAction lgCa = new LgQaCheckAction();
    public LgQaCheckPoint() {
    }
    /**
     * get all qa check point in a wbs/activity
     * @param acntRid Long
     * @param belongRid Long
     * @param belongTo String
     * @return List: PmsQaCheckPoint bean
     */

    public List listCheckPointBean(Long acntRid, Long belongRid, String belongTo){
        List list = new ArrayList();
       try {
           list = this.getDbAccessor().getSession().createQuery(
               "from PmsQaCheckPoint as cp " +
               "where cp.PK.acntRid=:acntRid " +
               "and cp.belongRid=:belongRid " +
               "and cp.belongTo=:belongTo and cp.rst = 'N' order by cp.seq")
                  .setLong("acntRid", acntRid)
                  .setLong("belongRid", belongRid)
                  .setString("belongTo", belongTo)
                  .list();
           return list;
       } catch (Exception ex) {
           ex.printStackTrace();
           throw new BusinessException("E000000", "List Qa Check Point error.",
                                       ex);
       }

    }
    /**
     * get all qa check point in a wbs/activity
     * @param acntRid Long
     * @param belongRid Long
     * @param belongTo String
     * @return List
     */
    public List listCheckPoint(Long acntRid, Long belongRid, String belongTo) {
        List list = listCheckPointBean(acntRid,belongRid,belongTo);

        return BeanList2DtoList(list);
    }

    /**
     * 获取指定项目的所有PmsQaCheckPoint（包括Wbs和Activity的）
     * @param acntRid Long
     * @return List
     */
    public List listALLPmsQaCheckPointByAcntRid(Long acntRid) {
        List resultList = null;
        if (acntRid == null) {
            return null;
        }
        try {
            resultList = this.getDbAccessor().getSession()
                         .createQuery(
                             " from PmsQaCheckPoint as t  where t.PK.acntRid=:acntRid")
                         .setLong("acntRid", acntRid.longValue())
                         .list();
            return resultList;
        } catch (Exception e) {
            log.error(e);
            throw new BusinessException("wbsCheckPoint.list.error", e);
        }

    }

    /**
     * exchange PmsQaCheckPoint to DtoQaCheckPoint
     * @param list List
     * @return List
     */
    private List BeanList2DtoList(List list) {
        for (int i = 0; i < list.size(); i++) {
            PmsQaCheckPoint bean = (PmsQaCheckPoint) (list.get(i));
            DtoQaCheckPoint dto = new DtoQaCheckPoint();
            exchangeBean2Dto(dto, bean);
            list.set(i, dto);
        }
        return list;
    }

    /**
     * save a DtoQaCheckPoint list
     * @param list List
     */
    public void saveList(List list) {
        for (int i = 0; i < list.size(); i++) {
            DtoQaCheckPoint dto = (DtoQaCheckPoint) (list.get(i));
            if (IDto.OP_INSERT.equals(dto.getOp())) {
                insertCheckPoint(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            } else if (IDto.OP_MODIFY.equals(dto.getOp())) {
                updateCheckPoint(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            } else if (IDto.OP_DELETE.equals(dto.getOp())) {
                deleteCheckPoint(dto);
                list.remove(i);
            }
        }
    }

    /**
     * add a qa check point
     * @param dto DtoQaCheckPoint
     */
    public void insertCheckPoint(DtoQaCheckPoint dto) {
        PmsQaCheckPoint bean = new PmsQaCheckPoint();
        exchangeDto2Bean(bean, dto);
        saveBean(bean);
        exchangeBean2Dto(dto, bean);
    }

    /**
     * save a PmsQaCheckPoint bean
     * @param bean PmsQaCheckPoint
     */
    private void saveBean(PmsQaCheckPoint bean) {
        try {
            this.getDbAccessor().save(bean);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "Insert Qa Check Point error.", ex);
        }
    }

    /**
     * update a qa check point
     * @param dto DtoQaCheckPoint
     */
    public void updateCheckPoint(DtoQaCheckPoint dto) {
        PmsQaCheckPoint bean = new PmsQaCheckPoint();
        exchangeDto2Bean(bean, dto);
        try {
            this.getDbAccessor().update(bean);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "Update Qa Check Point error.", ex);
        }
        exchangeBean2Dto(dto, bean);
    }

    /**
     * delete all check points in a wbs/activity
     * @param acntRid Long
     * @param belongRid Long
     * @param belongTo String
     */
    public void deleteCheckPoints(Long acntRid, Long belongRid, String belongTo) {
        //delete check actions sql
        String sql_actions = "delete pms_qa_check_action ca where ca.cp_rid in ("
                     + "select cp.rid from pms_qa_check_point cp "
                     + "where cp.acnt_rid = " + acntRid.toString()
                     + " and cp.belong_rid = " + belongRid.toString()
                     + " and cp.belong_to = '" + belongTo
                     + "' ) and ca.acnt_rid = " + acntRid.toString();

        //delete check points sql
        String sql_points =  "delete pms_qa_check_point cp "
                             + "where cp.acnt_rid = " + acntRid.toString()
                             + " and cp.belong_rid = " + belongRid.toString()
                             + " and cp.belong_to = '" + belongTo +"'";
        try {
            this.getDbAccessor().executeUpdate(sql_actions);
            this.getDbAccessor().executeUpdate(sql_points);
         } catch (Exception ex) {
             throw new BusinessException("E000000",
                                        "Delete Qa Check Points and actions error.", ex);
         }
    }

    /**
     * delete a qa check point and all check action in it
     * @param dto DtoQaCheckPoint
     */
    public void deleteCheckPoint(DtoQaCheckPoint dto) {
        //Delete Actions in this check point
        lgCa.deleteCaInCp(dto.getAcntRid() ,dto.getRid());
        //Delete check point
        PmsQaCheckPoint bean = new PmsQaCheckPoint();
        exchangeDto2Bean(bean, dto);
        try {
            this.getDbAccessor().delete(bean);
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Delete Qa Check Point error.", ex);
        }
    }

    /**
     * get a new qa check point rid
     * @param acntRid Long
     * @return Long
     */
    public Long getQaCheckPointRid(Long acntRid) {
        Long newRid = LgAcntSeq.getSeq(acntRid, PmsQaCheckPoint.class);
        return newRid;
    }

    /**
     * get a qa check point Code from a qa check point rid
     * @param cpRid Long
     * @return String
     */
    public String exchangeRid2Code(Long cpRid) {
        String checkPointCode = "";
        if (cpRid != null) {
            String Format = "0000";
            DecimalFormat df = new DecimalFormat(Format);
            checkPointCode = "CP" +
                             df.format(cpRid.longValue(), new StringBuffer(),
                                       new FieldPosition(0)).toString();
        }
        return checkPointCode;
    }

    /**
     * Exchange a DtoQaCheckPoint to a  PmsQaCheckPoint
     * @param bean PmsQaCheckPoint
     * @param dto DtoQaCheckPoint
     */
    private void exchangeDto2Bean(PmsQaCheckPoint bean, DtoQaCheckPoint dto) {
        DtoUtil.copyBeanToBean(bean, dto);
        PmsQaCheckPointPK cpPk = new PmsQaCheckPointPK();
        cpPk.setRid(dto.getRid());
        cpPk.setAcntRid(dto.getAcntRid());
        bean.setPK(cpPk);
    }

    /**
     * Exchange a PmsQaCheckPoint to a  DtoQaCheckPoint
     * @param dto DtoQaCheckPoint
     * @param bean PmsQaCheckPoint
     */
    private void exchangeBean2Dto(DtoQaCheckPoint dto, PmsQaCheckPoint bean) {
        DtoUtil.copyBeanToBean(dto, bean);
        dto.setRid(bean.getPK().getRid());
        dto.setAcntRid(bean.getPK().getAcntRid());
    }

    /**
     * 当Activity reference 改变时：
     * 1.删除当前Activity下所有Check Point和Check Action
     * 2.复制新引用的所有Check Point和Check Action到当前Wbs下
     * @param activityGuideLine PmsActivityGuideline
     * @param oldRefAcntRid Long
     * @param oldRefActivityRid Long
     */
    public void activityReferenceChange(PmsActivityGuideline activityGuideLine,
                                        Long oldRefAcntRid,
                                        Long oldRefActivityRid) {
        Long acntRid = activityGuideLine.getId().getAcntRid();
        Long actRid = activityGuideLine.getId().getActRid();
        Long refAcntRid = activityGuideLine.getRefAcntRid();
        Long refActRid = activityGuideLine.getRefActRid();

        //delete current check points and actions
        deleteCheckPoints(acntRid,actRid,DtoKey.TYPE_ACTIVITY);
        if(refAcntRid == null || refActRid == null)
            return ;

        copyAllCheckPoint(acntRid, actRid, refAcntRid, refActRid, DtoKey.TYPE_ACTIVITY);
    }


    /**
     * 当Wbs reference 改变时：
     * 1.删除当前Wbs下所有Check Point和Check Action
     * 2.复制新引用的所有Check Point和Check Action到当前Wbs下
     * @param wbsGuideLine PmsWbsGuideline
     * @param oldRefAcntRid Long
     * @param oldRefWbsRid Long
     */
    public void wbsReferenceChange(PmsWbsGuideline wbsGuideLine,
                                   Long oldRefAcntRid, Long oldRefWbsRid) {
        Long acntRid = wbsGuideLine.getId().getAcntRid();
        Long wbsRid = wbsGuideLine.getId().getWbsRid();
        Long refAcntRid = wbsGuideLine.getRefAcntRid();
        Long refWbsRid = wbsGuideLine.getRefWbsRid();

        //delete current check points and actions
        deleteCheckPoints(acntRid,wbsRid,DtoKey.TYPE_WBS);
        if(refAcntRid == null || refWbsRid == null)
            return ;

        copyAllCheckPoint(acntRid, wbsRid, refAcntRid, refWbsRid, DtoKey.TYPE_WBS);

    }

    /**
     * copy all check points and actions in acntRid and belongRid with belongTo = type
     * @param acntRid Long
     * @param belongRid Long
     * @param refAcntRid Long
     * @param refWbsRid Long
     * @param type String
     */
    private void copyAllCheckPoint(Long acntRid, Long belongRid, Long refAcntRid, Long refWbsRid, String type) {

        List cpList = listCheckPointBean(refAcntRid, refWbsRid, type);
        for(int i=0; i<cpList.size(); i++) {
            PmsQaCheckPoint cp = (PmsQaCheckPoint) cpList.get(i);
            Long cpRid = copyCheckPoint(cp, acntRid, belongRid);
            copyAllCheckAction(cp, acntRid, cpRid);
        }
    }

    /**
     * copy one check point
     * @param oldCp PmsQaCheckPoint
     * @param acntRid Long
     * @param belongRid Long
     * @return Long
     */
    private Long copyCheckPoint(PmsQaCheckPoint oldCp, Long acntRid, Long belongRid) {
        PmsQaCheckPoint newCp = new PmsQaCheckPoint();
        PmsQaCheckPointPK newCpPk = new PmsQaCheckPointPK();

        Long newRid = LgAcntSeq.getSeq(acntRid, PmsQaCheckPoint.class);
        newCpPk.setRid(newRid);
        newCpPk.setAcntRid(acntRid);

        DtoUtil.copyBeanToBean(newCp, oldCp);
        newCp.setPK(newCpPk);
        newCp.setBelongRid(belongRid);

        saveBean(newCp);

        return newRid;
    }

    /**
     * copy a check action under check point oldCp to check point (acntRid,cpRid)
     * @param oldCp PmsQaCheckPoint
     * @param acntRid Long
     * @param cpRid Long
     */
    private void copyAllCheckAction(PmsQaCheckPoint oldCp, Long acntRid, Long cpRid) {
        Long oldCpRid = oldCp.getPK().getRid();
        Long oldAcntRid = oldCp.getPK().getAcntRid();


        List caList = lgCa.listCheckActionOfBean(oldCpRid, oldAcntRid);

        for(int i=0; i<caList.size(); i++) {
            PmsQaCheckAction oldCa = (PmsQaCheckAction) caList.get(i);
            copyCheckAction(oldCa, acntRid, cpRid);
        }

    }

    /**
     * copy a check action's occasion, content, init status
     * @param oldCa PmsQaCheckAction
     * @param acntRid Long
     * @param cpRid Long
     */
    private void copyCheckAction(PmsQaCheckAction oldCa, Long acntRid, Long cpRid) {
        PmsQaCheckAction newCa = new PmsQaCheckAction();
        PmsQaCheckActionPK newCaPK = new PmsQaCheckActionPK();

        Long newRid = LgAcntSeq.getSeq(acntRid, PmsQaCheckAction.class);
        newCaPK.setRid(newRid);
        newCaPK.setAcntRid(acntRid);

        newCa.setPK(newCaPK);
        newCa.setCpRid(cpRid);
        newCa.setOccasion(oldCa.getOccasion());
        newCa.setContent(oldCa.getContent());
        newCa.setStatus(DtoQaCheckAction.chkActionStatus[0]);

        lgCa.saveBean(newCa);
    }
}
