package server.essp.pms.account.pcb.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.essp.pms.account.pcb.DtoPcbItem;
import c2s.essp.pms.account.pcb.DtoPcbParameter;
import db.essp.pms.PmsPcbItem;
import db.essp.pms.PmsPcbParameter;

import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;
import net.sf.hibernate.Query;
import net.sf.hibernate.Session;

import server.essp.pms.wbs.logic.LgAcntSeq;
import db.essp.pms.PmsPcbItemPK;
import db.essp.pms.PmsPcbParameterPK;
import javax.sql.RowSet;



public class LgPcb extends AbstractESSPLogic {
    /**
     * 新增一条pcbitem
     * @param dtoPcbItem DtoPcbItem
     */
    public void addPcbItem(DtoPcbItem dtoPcbItem) {
        if (dtoPcbItem == null) {
            return;
        }
        if (dtoPcbItem.getAcntRid() == null) {
            throw new BusinessException("acntRid of dtoPcbItem is null");
        }
        if (dtoPcbItem.getName() == null || "".equals(dtoPcbItem.getName())) {
            throw new BusinessException("item of dtoPcbItem is null");
        }
        if (dtoPcbItem.getType() == null || "".equals(dtoPcbItem.getType())) {
            throw new BusinessException("type of dtoPcbItem is null");
        }
        PmsPcbItem pmsPcbItem = new PmsPcbItem();

        try {
            DtoUtil.copyProperties(pmsPcbItem, dtoPcbItem);
            Long acntRid = dtoPcbItem.getAcntRid();
            PmsPcbItemPK itemPk = new PmsPcbItemPK();
            itemPk.setAcntRid(acntRid);
            Long newRid = LgAcntSeq.getSeq(acntRid, PmsPcbItem.class);
            itemPk.setRid(newRid);
            pmsPcbItem.setPK(itemPk);
            this.getDbAccessor().getSession().save(pmsPcbItem);
            //获取生成的pcbitem的rid
            dtoPcbItem.setRid(pmsPcbItem.getPK().getRid());
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
    }

    /**
     * 保存多条pcbItem
     * @param pcbItemList List
     */
    public void addPcbItem(List pcbItemList) {
        if (pcbItemList == null || pcbItemList.size() == 0) {
            return;
        }
        for (int i = 0; i < pcbItemList.size(); i++) {
            DtoPcbItem dtoPcbItem = (DtoPcbItem) pcbItemList.get(i);
            addPcbItem(dtoPcbItem);
        }
    }

    /**
     * 根据acntRid获取account对应的PCB
     * @param acntRid Long
     * @return List
     */
    //返回装了DB(持久化类)的list
    public List listPcbItemByAcntRid(Long acntRid) {
        List resultList = null;
        if (acntRid == null) {
            throw new BusinessException("anctRid is null!");
        }
        try {
            String sqlStr =
                "from PmsPcbItem as t where t.PK.acntRid=:acntRid and t.rst='N' order by t.seq";
            resultList = this.getDbAccessor().getSession().createQuery(sqlStr)
                         .setLong("acntRid", acntRid.longValue())
                         .list();
            return resultList;
        } catch (Exception e) {
            log.error(e);
            throw new BusinessException("List.PcbItem.Exception", e);
        }
    }

    //返回装了Dto的list
    public List listPcbItem(Long acntRid) {
        if (acntRid == null) {
            return null;
        }
        List resultListDB = this.listPcbItemByAcntRid(acntRid);
        if (resultListDB == null) {
            return null;
        }
        List resultListDto = new ArrayList();
        for (int i = 0; i < resultListDB.size(); i++) {
            PmsPcbItem pmsPcbItem = (PmsPcbItem) resultListDB.get(i);

            DtoPcbItem dtoPcbItem = new DtoPcbItem();

            dtoPcbItem.setAcntRid(pmsPcbItem.getPK().getAcntRid());

            dtoPcbItem.setRid(pmsPcbItem.getPK().getRid());
            DtoUtil.copyBeanToBean(dtoPcbItem,pmsPcbItem);

            resultListDto.add(dtoPcbItem);
        }
        return resultListDto;
    }

    /**
     * 新增一条pcbParameter
     * @param dtoPcbParameter DtoPcbParameter
     */
    public void addPcbParameter(DtoPcbParameter dtoPcbParameter) {
        if (dtoPcbParameter == null) {
            return;
        }
        if (dtoPcbParameter.getName() == null ||
            "".equals(dtoPcbParameter.getName())) {
            throw new BusinessException("name of dtoPcbParameter is null");
        }
        if (dtoPcbParameter.getUnit() == null ||
            "".equals(dtoPcbParameter.getUnit())) {
            throw new BusinessException("unit of dtoPcbParameter is null");
        }
        PmsPcbParameter pmsPcbParameter = new PmsPcbParameter();

        try {
            DtoUtil.copyProperties(pmsPcbParameter, dtoPcbParameter);
            Long acntRid = dtoPcbParameter.getAcntRid();
            PmsPcbParameterPK paramPk = new PmsPcbParameterPK();
            paramPk.setAcntRid(acntRid);
            Long newRid = LgAcntSeq.getSeq(acntRid, PmsPcbParameter.class);
            paramPk.setRid(newRid);
            pmsPcbParameter.setPK(paramPk);
            this.getDbAccessor().getSession().save(pmsPcbParameter);
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }

    }

    /**
     * 保存多条pcbParameter
     * @param itemRid Long
     * @param pcbParameterList List
     */
    public void addPcbParameter(List pcbParameterList, Long itemRid) {
        if (pcbParameterList == null || pcbParameterList.size() == 0) {
            return;
        }
        if (itemRid == null) {
            throw new BusinessException("itemRid is null!");
        }
        for (int j = 0; j < pcbParameterList.size(); j++) {
            DtoPcbParameter dtoPcbParameter =
                (DtoPcbParameter) pcbParameterList.get(j);
            dtoPcbParameter.setItemRid(itemRid);
            addPcbParameter(dtoPcbParameter);
        }
    }


    /**
     * 根据acntRid和itemRid获取PCB item对应的parameter
     * @param acntRid Long，itemRid Long
     * @return List，list中的元素是Dto
     * {@code    gamble}
     */
    public List listPcbParameter(Long acntRid, Long itemRid) throws
        BusinessException {
        List PcbParameterList = new ArrayList();
        try {
            String sqlSel = " from PmsPcbParameter t "
                            + " where t.PK.acntRid =:acntRid "
                            + " and t.itemRid =:itemRid order by t.seq";
            Iterator it = getDbAccessor().getSession().createQuery(sqlSel)
                          .setLong("acntRid", acntRid.longValue())
                          .setLong("itemRid", itemRid.longValue())
                          .iterate();
            while (it.hasNext()) {
                PmsPcbParameter pmsPcbParameter = (PmsPcbParameter) it.next();
                DtoPcbParameter dtoPcbParameter = new DtoPcbParameter();

                DtoUtil.copyBeanToBean(dtoPcbParameter, pmsPcbParameter);

                dtoPcbParameter.setRid(pmsPcbParameter.getPK().getRid());
                dtoPcbParameter.setAcntRid(pmsPcbParameter.getPK().getAcntRid());

                PcbParameterList.add(dtoPcbParameter);
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Select  item error.", ex);
        }
        return PcbParameterList;
    }

    /**
     * 保存Item表中的数据到数据库
     * @param List pcbItemList
     *
     */
    public void updatePcbItem(List pcbItemList) throws BusinessException {
        try {

            for (int i = 0; i < pcbItemList.size(); i++) {
                DtoPcbItem dtoPcbItem = (DtoPcbItem) pcbItemList.get(i);

                if (dtoPcbItem.isInsert()) {
                    PmsPcbItem pmsPcbItem = new PmsPcbItem();
                    copyPmsPcbItem(pmsPcbItem, dtoPcbItem);

                    getDbAccessor().save(pmsPcbItem);
                    //新增的，则设置它的Rid
                    Long newRid = LgAcntSeq.getSeq(dtoPcbItem.getAcntRid(), PmsPcbItem.class);
                    pmsPcbItem.getPK().setRid(newRid);
                    dtoPcbItem.setRid(pmsPcbItem.getPK().getRid());

                    DtoUtil.copyBeanToBean(dtoPcbItem,pmsPcbItem);
                    //
                    dtoPcbItem.setOp(IDto.OP_NOCHANGE);
                } else if (dtoPcbItem.isDelete()) {

                    pcbItemList.remove(i);
                    i--;
                } else if (dtoPcbItem.isModify()) {
                    Long rid=dtoPcbItem.getRid();
                    PmsPcbItem pmsPcbItem = new PmsPcbItem();
                    copyPmsPcbItem(pmsPcbItem, dtoPcbItem);
                    pmsPcbItem.getPK().setRid(rid);
//

                    getDbAccessor().update(pmsPcbItem);

                    dtoPcbItem.setOp(IDto.OP_NOCHANGE);
                }
            }

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Update Pcb Item error.", ex);
        }
    }

    /**
     * 将DtoPcbItem中的字段（除了Rid之外）拷贝到DbBean中，
     * @param PmsPcbItem dbBean, DtoPcbItem dataBean
     *
     */
    public void copyPmsPcbItem(PmsPcbItem dbBean, DtoPcbItem dataBean) throws
        Exception {
        DtoUtil.copyBeanToBean(dbBean,dataBean);
        PmsPcbItemPK itemPk = new PmsPcbItemPK();
        itemPk.setAcntRid(dataBean.getAcntRid());
        dbBean.setPK(itemPk);
//        dbBean.setName(dataBean.getName());
//        dbBean.setType(dataBean.getType());
//        dbBean.setRemark(dataBean.getRemark());
    }

    /**
     * 删除ItemList中的一行，
     * 会相应的删除ParameterList表中的对应的数据
     * @ DtoPcbItem dtoPcbItem
     *
     */
    public void deletePcbItem(DtoPcbItem dtoPcbItem) throws
        BusinessException {
        try {
            //如果是增加了之后没有保存，就直接删除呢？？？这个在前台就进行了控制
//            PmsPcbItem pmsPcbItem = (PmsPcbItem)this.
//                                    getDbAccessor().load(
//                                        PmsPcbItem.class, dtoPcbItem.getRid());
            PmsPcbItem pmsPcbItem=new PmsPcbItem();
            PmsPcbItemPK itemPK=new PmsPcbItemPK();
            itemPK.setAcntRid(dtoPcbItem.getAcntRid());
            itemPK.setRid(dtoPcbItem.getRid());
            pmsPcbItem.setPK(itemPK);
            DtoUtil.copyBeanToBean(pmsPcbItem,dtoPcbItem);

            String sql =
                "delete pms_pcb_parameter where item_rid = " +
                dtoPcbItem.getRid() + " and acnt_rid= " + dtoPcbItem.getAcntRid();

            //直接用sql删除Parameter表中为此itemRid和acntRid的值的parameter记录
            this.getDbAccessor().executeUpdate(sql);
            getDbAccessor().delete(pmsPcbItem);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Delete Pcb Item error.", ex);
        }

    }

    /**
     * 保存Parameter表中的数据到数据库
     * @param List pcbParameterList
     *
     */
    public void updatePcbParameter(List pcbParameterList) throws
        BusinessException {
        try {

            for (int i = 0; i < pcbParameterList.size(); i++) {
                DtoPcbParameter dtoPcbParameter = (DtoPcbParameter)
                                                  pcbParameterList.get(i);

                if (dtoPcbParameter.isInsert()) {
                    PmsPcbParameter pmsPcbParameter = new PmsPcbParameter();
                    copyPmsPcbParameter(pmsPcbParameter, dtoPcbParameter);

                    getDbAccessor().save(pmsPcbParameter);
                    //新增的，设置其rid
                    Long newRid = LgAcntSeq.getSeq(dtoPcbParameter.getAcntRid(), PmsPcbParameter.class);
                    pmsPcbParameter.getPK().setRid(newRid);
                    dtoPcbParameter.setRid(pmsPcbParameter.getPK().getRid());
//                    DtoUtil.copyBeanToBean(dtoPcbParameter,pmsPcbParameter);
                    dtoPcbParameter.setOp(IDto.OP_NOCHANGE);
                } else if (dtoPcbParameter.isDelete()) {

                    pcbParameterList.remove(i);
                    i--;
                } else if (dtoPcbParameter.isModify()) {
                    PmsPcbParameter pmsPcbParameter = new PmsPcbParameter();
                    Long rid=dtoPcbParameter.getRid();
                    copyPmsPcbParameter(pmsPcbParameter, dtoPcbParameter);
                    pmsPcbParameter.getPK().setRid(rid);

                    getDbAccessor().update(pmsPcbParameter);

                    dtoPcbParameter.setOp(IDto.OP_NOCHANGE);
                }
            }

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Update Pcb Parameter error.", ex);
        }
    }

    /**
     * checkPcbParameter
     * check parameter's id or name exist
     * @param List pcbParameterList
     *
     */
    public String checkPcbParameter(List pcbParameterList) throws
        BusinessException {
        String existInfo = "";
        for(int i = 0; i < pcbParameterList.size(); i++) {
            DtoPcbParameter dto = (DtoPcbParameter)
                                                  pcbParameterList.get(i);
            if(IDto.OP_INSERT.equals(dto.getOp()) || IDto.OP_MODIFY.equals(dto.getOp())) {
                if (checkIdExist(dto)) {
                    existInfo = existInfo + "Id: " + dto.getId() + "\r\n";
                }
                if (checkNameExist(dto)) {
                    existInfo = existInfo + "Name: " + dto.getName() + "\r\n";
                }
            }
        }
        if("".equals(existInfo)) {
            return null;
        } else {
            return existInfo + "already existed!";
        }
    }

    /**
     * checkIdExist
     * check parameter's id exist
     * @param String parameterId
     *
     */
    private boolean checkIdExist(DtoPcbParameter dto) throws
        BusinessException {
        try {
            String sqlSel = "select t.rid from Pms_Pcb_Parameter t  where t.id = '"
                            + dto.getId()+"' and t.acnt_rid = "+dto.getAcntRid();
            RowSet rs = this.getDbAccessor().executeQuery(sqlSel);
            if(rs.next()) {
                if(dto.getRid().longValue() != rs.getLong(1)) {
                    return true;
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "check parameter id exist error.", ex);
        }
        return false;
    }

    /**
     * checkIdExist
     * check parameter's id exist
     * @param String parameterName
     *
     */
    private boolean checkNameExist(DtoPcbParameter dto) throws
        BusinessException {
        try {
            String sqlSel = "select t.rid from Pms_Pcb_Parameter t  where t.name = '"
                            + dto.getName()+"' and t.acnt_rid = "+dto.getAcntRid();
            RowSet rs = this.getDbAccessor().executeQuery(sqlSel);
            if(rs.next()) {
                if(dto.getRid().longValue() != rs.getLong(1)) {
                    return true;
                }
            }

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "check parameter name exist error.", ex);
        }
        return false;

    }




    /**
     * 将DtoPcbParameter中的字段（除了Rid之外）拷贝到DbBean PmsPcbParameter中，
     * @param PmsPcbParameter pmsPcbParameter, DtoPcbParameter dtoPcbParameter
     *
     */
    public void copyPmsPcbParameter(PmsPcbParameter pmsPcbParameter,
                                    DtoPcbParameter dtoPcbParameter) {
        DtoUtil.copyBeanToBean(pmsPcbParameter,dtoPcbParameter);
//        pmsPcbParameter.setId(dtoPcbParameter.getId());
//        pmsPcbParameter.setItemRid(dtoPcbParameter.getItemRid());
//        pmsPcbParameter.setLcl(dtoPcbParameter.getLcl());
//        pmsPcbParameter.setMean(dtoPcbParameter.getMean());
//        pmsPcbParameter.setName(dtoPcbParameter.getName());
//        pmsPcbParameter.setPlan(dtoPcbParameter.getPlan());
//        pmsPcbParameter.setRemark(dtoPcbParameter.getRemark());
//        pmsPcbParameter.setUcl(dtoPcbParameter.getUcl());
//        pmsPcbParameter.setUnit(dtoPcbParameter.getUnit());
//        pmsPcbParameter.setActual(dtoPcbParameter.getActual());
        PmsPcbParameterPK itemPk = new PmsPcbParameterPK();
        itemPk.setAcntRid(dtoPcbParameter.getAcntRid());
        pmsPcbParameter.setPK(itemPk);

    }

    /**
     * 删除ParameterList中的一行，
     * @ DtoPcbParameter dtoParameter
     *
     */
    public void deletePcbParameter(DtoPcbParameter dtoParameter) {
        try {
//            PmsPcbParameter pmsPcbParameter = (PmsPcbParameter)this.
//                                              getDbAccessor().
//                                              load(PmsPcbParameter.class,
//                dtoParameter.getRid());
            PmsPcbParameter pmsPcbParameter = new PmsPcbParameter();
            PmsPcbParameterPK paramPk=new PmsPcbParameterPK();
            paramPk.setAcntRid(dtoParameter.getAcntRid());
            paramPk.setRid(dtoParameter.getRid());
            pmsPcbParameter.setPK(paramPk);
            DtoUtil.copyBeanToBean(pmsPcbParameter,dtoParameter);


            getDbAccessor().delete(pmsPcbParameter);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E000000",
                                        "Delete Pcb Parameter error.", ex);
        }

    }

    public static void main(String[] args) {
        LgPcb lgPcb = new LgPcb();
        List parameterList = lgPcb.listPcbParameter(new Long(5900), new Long(28));
    }

    //find the plan_density, plan_defect rate and units by id and acntRid;
    public PmsPcbParameter findFromPcb(Long acntRid, String id) {

        Session session = null;
        PmsPcbParameter pcbP = null;
        try {
            session = this.getDbAccessor().getSession();

            Query query = session.createQuery(
                "from PmsPcbParameter t where t.PK.acntRid=:acntRid  and  id=:id");
            query.setLong("acntRid", acntRid.longValue());
            query.setString("id", id);
            pcbP = (PmsPcbParameter) query.setMaxResults(1).uniqueResult();
        } catch (Exception ex) {
            throw new BusinessException(ex);
        }
        return pcbP;
    }


}
