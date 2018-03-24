package server.essp.pms.wbs.process.checklist.logic;

import java.util.List;
import server.essp.framework.logic.AbstractESSPLogic;
import c2s.dto.DtoUtil;
import com.wits.util.Config;
import db.essp.pms.PmsQaCheckAction;
import c2s.essp.pms.qa.DtoQaCheckAction;
import server.framework.common.BusinessException;
import java.util.ArrayList;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import server.essp.pms.wbs.logic.LgAcntSeq;
import net.sf.hibernate.Query;
import net.sf.hibernate.*;
import java.util.Date;
import c2s.dto.IDto;
import db.essp.pms.PmsQaCheckActionPK;
import java.text.DecimalFormat;
import java.text.FieldPosition;

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
public class LgQaCheckAction extends AbstractESSPLogic {
    //qa account config file
    public static String CFG_FILE_NAME = "QaAccount";
    public static String CFG_FILE_ACNTRID = "acntRid";

    public LgQaCheckAction() {
    }

    /**
     * get all qa check action in a qa check point
     * @param cpRid Long
     * @param acntRid Long
     * @return List: dto
     */
    public List listCheckAction(Long cpRid, Long acntRid) {
        return BeanList2DtoList(listCheckActionOfBean(cpRid, acntRid));
    }

    /**
     * get qa check actions in a qa check point with plan date
     * @param cpRid Long
     * @param acntRid Long
     * @param pDate Date
     * @return List :DtoQaCheckAction
     */
    public List listCheckActionByPlanDate(Long cpRid, Long acntRid, Date pDateStart, Date pDateFinish) {
        return BeanList2DtoList(listCheckActionBeanByPlanDate(cpRid, acntRid,
            pDateStart, pDateFinish));
    }

    /**
     * get qa check actions in a qa check point with actual date
     * @param cpRid Long
     * @param acntRid Long
     * @param aDate Date
     * @return List :DtoQaCheckAction
     */
    public List listCheckActionByActDate(Long cpRid, Long acntRid, Date aDateStart, Date aDateFinish) {
        return BeanList2DtoList(listCheckActionBeanByActDate(cpRid, acntRid,
            aDateStart, aDateFinish));
    }

    /**
     * get qa check actions in a qa check point with a plan performer
     * @param cpRid Long
     * @param acntRid Long
     * @param pPerformer String
     * @return List :DtoQaCheckAction
     */
    public List listCheckActionByPlanPerformer(Long cpRid, Long acntRid,
                                               String pPerformer) {
        return BeanList2DtoList(listCheckActionBeanByPlanPerformer(cpRid,
            acntRid, pPerformer));
    }

    /**
     * get qa check actions in a qa check point with an actual performer
     * @param cpRid Long
     * @param acntRid Long
     * @param aPerformer String
     * @return List : DtoQaCheckAction
     */
    public List listCheckActionByActPerformer(Long cpRid, Long acntRid,
                                              String aPerformer) {
        return BeanList2DtoList(listCheckActionBeanByActPerformer(cpRid, acntRid,
            aPerformer));
    }


    /**
     * get all qa check actions in a qa check point
     * @param cpRid Long
     * @param acntRid Long
     * @return List :PmsQaCheckAction bean
     */
    public List listCheckActionOfBean(Long cpRid, Long acntRid) {
        List list = new ArrayList();
        try {
            list = this.getDbAccessor().getSession().createQuery(
                "from PmsQaCheckAction as ca " +
                "where ca.cpRid=:cpRid and ca.PK.acntRid=:acntRid and ca.rst = 'N' order by ca.planDate")
                   .setParameter("cpRid", cpRid)
                   .setParameter("acntRid", acntRid)
                   .list();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "List Qa Check Action error.", ex);
        }
    }

    /**
     * get qa check action in a qa check point with a status
     * @param cpRid Long
     * @param acntRid Long
     * @param status String
     * @return List
     */
    public List listCheckActionByStatus(Long cpRid, Long acntRid, String status) {
        List list = new ArrayList();
        try {
            list = this.getDbAccessor().getSession().createQuery(
                "from PmsQaCheckAction as ca " +
                "where ca.cpRid=:cpRid " +
                "and ca.PK.acntRid=:acntRid " +
                "and ca.status=:status " +
                "and ca.rst = 'N' order by ca.planDate")
                   .setParameter("cpRid", cpRid)
                   .setParameter("acntRid", acntRid)
                   .setParameter("status", status)
                   .list();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000", "List Qa Check Ation error.",
                                        ex);
        }
        return BeanList2DtoList(list);
    }

    /**
     * get qa check actions in a qa check point with plan date
     * @param cpRid Long
     * @param acntRid Long
     * @param pDate Date
     * @return List :PmsQaCheckAction bean
     */
    public List listCheckActionBeanByPlanDate(Long cpRid, Long acntRid,
                                              Date pDateStart, Date pDateFinish) {
        List list = new ArrayList();
        try {
            list = this.getDbAccessor().getSession().createQuery(
                "from PmsQaCheckAction as ca " +
                "where ca.cpRid=:cpRid " +
                "and ca.PK.acntRid=:acntRid " +
                "and ca.planDate>=:pDateStart " +
                "and ca.planDate<=:pDateFinish and ca.rst = 'N'")
                   .setParameter("cpRid", cpRid)
                   .setParameter("acntRid", acntRid)
                   .setParameter("pDateStart", pDateStart)
                   .setParameter("pDateFinish", pDateFinish)
                   .list();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "List Qa Check Action error.", ex);
        }
    }

    /**
     * get qa check actions in a qa check point with actual date
     * @param cpRid Long
     * @param acntRid Long
     * @param aDate Date
     * @return List :PmsQaCheckAction bean
     */
    public List listCheckActionBeanByActDate(Long cpRid, Long acntRid,
                                             Date aDateStart, Date aDateFinish) {
        List list = new ArrayList();
        try {
            list = this.getDbAccessor().getSession().createQuery(
                "from PmsQaCheckAction as ca " +
                "where ca.cpRid=:cpRid " +
                "and ca.PK.acntRid=:acntRid " +
                "and ca.actDate>=:aDateStart " +
                "and ca.actDate<=:aDateFinish and ca.rst = 'N'")
                   .setParameter("cpRid", cpRid)
                   .setParameter("acntRid", acntRid)
                   .setParameter("aDateStart", aDateStart)
                   .setParameter("aDateFinish", aDateFinish)
                   .list();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "List Qa Check Action error.", ex);
        }
    }

    /**
     * get qa check actions in a qa check point with a plan performer
     * @param cpRid Long
     * @param acntRid Long
     * @param pPerformer String
     * @return List :PmsQaCheckAction bean
     */
    public List listCheckActionBeanByPlanPerformer(Long cpRid, Long acntRid,
        String pPerformer) {
        List list = new ArrayList();
        try {
            list = this.getDbAccessor().getSession().createQuery(
                "from PmsQaCheckAction as ca " +
                "where ca.cpRid=:cpRid " +
                "and ca.PK.acntRid=:acntRid " +
                "and ca.planPerformer=:pPerformer and ca.rst = 'N'")
                   .setParameter("cpRid", cpRid)
                   .setParameter("acntRid", acntRid)
                   .setParameter("pPerformer", pPerformer)
                   .list();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "List Qa Check Action error.", ex);
        }
    }

    /**
     * get qa check actions in a qa check point with an actual performer
     * @param cpRid Long
     * @param acntRid Long
     * @param aPerformer String
     * @return List :PmsQaCheckAction bean
     */
    public List listCheckActionBeanByActPerformer(Long cpRid, Long acntRid,
                                                  String aPerformer) {
        List list = new ArrayList();
        try {
            list = this.getDbAccessor().getSession().createQuery(
                "from PmsQaCheckAction as ca " +
                "where ca.cpRid=:cpRid " +
                "and ca.PK.acntRid=:acntRid " +
                "and ca.actPerformer=:aPerformer and ca.rst = 'N'")
                   .setParameter("cpRid", cpRid)
                   .setParameter("acntRid", acntRid)
                   .setParameter("aPerformer", aPerformer)
                   .list();
            return list;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "List Qa Check Action error.", ex);
        }
    }

    /**
     * exchange PmsQaCheckAction to DtoQaCheckAction
     * @param list List
     * @return List
     */
    private List BeanList2DtoList(List list) {
        for (int i = 0; i < list.size(); i++) {
            PmsQaCheckAction bean = (PmsQaCheckAction) (list.get(i));
            DtoQaCheckAction dto = new DtoQaCheckAction();
            exchangeBean2Dto(dto, bean);
            list.set(i, dto);
        }
        return list;
    }

    /**
     * save a DtoQaCheckAction list
     * @param list List
     */
    public void saveList(List list) {
        for (int i = 0; i < list.size(); i++) {
            DtoQaCheckAction dto = (DtoQaCheckAction) (list.get(i));
            if (IDto.OP_INSERT.equals(dto.getOp())) {
                insertCheckAction(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            } else if (IDto.OP_MODIFY.equals(dto.getOp())) {
                updateCheckAction(dto);
                dto.setOp(IDto.OP_NOCHANGE);
            } else if (IDto.OP_DELETE.equals(dto.getOp())) {
                deleteCheckAction(dto);
                list.remove(i);
            }
        }
    }

    /**
     * add a qa check action
     * @param dto DtoQaCheckAction
     */
    public void insertCheckAction(DtoQaCheckAction dto) {
        PmsQaCheckAction bean = new PmsQaCheckAction();
        exchangeDto2Bean(bean, dto);
        saveBean(bean);
        exchangeBean2Dto(dto, bean);
    }

    public void saveBean(PmsQaCheckAction bean) {
        try {
            this.getDbAccessor().save(bean);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "Insert Qa Check Action error.", ex);
        }
    }

    /**
     * update a qa check action
     * @param dto DtoQaCheckAction
     */
    public void updateCheckAction(DtoQaCheckAction dto) {
        PmsQaCheckAction bean = new PmsQaCheckAction();
        exchangeDto2Bean(bean, dto);
        try {
            this.getDbAccessor().update(bean);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "Update Qa Check Action error.", ex);
        }
        exchangeBean2Dto(dto, bean);
    }

    /**
     * delete a qa check action
     * @param dto DtoQaCheckAction
     */
    public void deleteCheckAction(DtoQaCheckAction dto) {
        PmsQaCheckAction bean = new PmsQaCheckAction();
        exchangeDto2Bean(bean, dto);
        try {
            this.getDbAccessor().delete(bean);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E000000",
                                        "Delete Qa Check Action error.", ex);
        }
    }

    /**
     * add all qa check action in a qa check point with rid = cpRid
     * @param cpRid Long
     */
    public void deleteCaInCp(Long acntRid, Long cpRid) {
        String sql = "delete pms_qa_check_action where cp_rid = " +
                     cpRid.toString()+" and acnt_rid = "+acntRid.toString();
        this.getDbAccessor().executeUpdate(sql);
    }

    /**
     * get all labors in qa account
     * @return List
     */
    public List getQaLaborRes() {
        LgAccountLaborRes lgLaborRes = new LgAccountLaborRes();
        return lgLaborRes.listLaborResDto(getQaAcntRid());
    }

    /**
     * get qa account rid in config file
     * @return Long
     */
    private Long getQaAcntRid() {
        Config cfg = new Config(CFG_FILE_NAME);
        String strAcntRid = cfg.getValue(CFG_FILE_ACNTRID);
        return new Long(strAcntRid);
    }

    /**
     * get a new qa check action rid
     * @return String
     */
    public Long getQaCheckActionRid(Long acntRid) {
        Long newRid = LgAcntSeq.getSeq(acntRid, PmsQaCheckAction.class);
        return newRid;
    }
    /**
     * get a new qa check action rid
     * @return String
     */
    public String exchangeRid2Code(String cpCode, Long caRid) {
        String checkActionCode = "";
        if (caRid != null) {
            String Format = "0000";
            DecimalFormat df = new DecimalFormat(Format);
            checkActionCode = "CA" +
                      df.format(caRid.longValue(), new StringBuffer(),
                                new FieldPosition(0)).toString();
        }
        return cpCode + "-" + checkActionCode;
    }

    /**
     * Exchange a DtoQaCheckAction to a  PmsQaCheckAction
     * @param bean PmsQaCheckAction
     * @param dto DtoQaCheckAction
     */
    private void exchangeDto2Bean(PmsQaCheckAction bean, DtoQaCheckAction dto) {
        DtoUtil.copyBeanToBean(bean, dto);
        PmsQaCheckActionPK cpPk = new PmsQaCheckActionPK();
        cpPk.setRid(dto.getRid());
        cpPk.setAcntRid(dto.getAcntRid());
        bean.setPK(cpPk);
    }

    /**
     * Exchange a PmsQaCheckAction to a  DtoQaCheckAction
     * @param dto DtoQaCheckAction
     * @param bean PmsQaCheckAction
     */
    private void exchangeBean2Dto(DtoQaCheckAction dto, PmsQaCheckAction bean) {
        DtoUtil.copyBeanToBean(dto, bean);
        dto.setRid(bean.getPK().getRid());
        dto.setAcntRid(bean.getPK().getAcntRid());
    }



    public static void main(String[] args) {
        LgQaCheckAction lgcheckaction = new LgQaCheckAction();
        List rl = null;
        try {
            rl = lgcheckaction.getQaLaborRes();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < rl.size(); i++) {
            DtoAcntLoaborRes dto = (DtoAcntLoaborRes) rl.get(i);
            System.out.println("@@@@@@@@@@@@@: " + dto.getEmpName());
        }
    }
}
