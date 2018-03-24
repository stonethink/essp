package server.essp.pms.pbs.logic;

import java.util.Iterator;

import c2s.dto.DtoUtil;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pms.pbs.DtoPmsPbs;
import com.wits.util.StringUtil;
import db.essp.pms.Pbs;
import db.essp.pms.PbsPK;
import server.essp.framework.logic.AbstractESSPLogic;
import server.essp.pms.wbs.logic.LgAcntSeq;
import server.framework.common.BusinessException;
import java.util.*;

public class LgPmsPbs extends AbstractESSPLogic {
    IDtoAccount account = null;
    Long acntrid =null;

    public LgPmsPbs() {
        account = getAccount();
        if (account == null) {
            throw new BusinessException("E0000", "Can't get account information.Please choose one account first.");
        }
    }

    public LgPmsPbs(Long acnttrid){
        this.acntrid = acnttrid;
    }

    public void add(DtoPmsPbs dataBean) throws BusinessException {
        try {
            checkParameter(dataBean);

            Long pbsRid = LgAcntSeq.getSeq(account.getRid(), Pbs.class);
            dataBean.setPbsRid(pbsRid);

            Pbs dbBean = new Pbs();
            copyPbs(dbBean, dataBean);

            getDbAccessor().save(dbBean);

            Long parentRid = dataBean.getPbsParentRid();
            if (parentRid != null) {
                PbsPK parentPk = new PbsPK(dataBean.getAcntRid(), parentRid);
                Pbs parent = (Pbs) getDbAccessor().load(Pbs.class, parentPk);
                parent.getChilds().add(dbBean);
                getDbAccessor().update(parent);
            } else {
                LgAcntSeq.setRootRid(account.getRid(), Pbs.class, pbsRid);
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "Add pbs error.");
        }

    }

    public Long addDate(DtoPmsPbs dataBean) throws BusinessException {
        try {

            Long pbsRid = LgAcntSeq.getSeq(this.acntrid, Pbs.class);
            dataBean.setPbsRid(pbsRid);

            Pbs dbBean = new Pbs();
            copyPbs(dbBean, dataBean);
            getDbAccessor().save(dbBean);

            Long parentRid = dataBean.getPbsParentRid();
            if (parentRid != null) {
                PbsPK parentPk = new PbsPK(dataBean.getAcntRid(), parentRid);
                Pbs parent = (Pbs) getDbAccessor().load(Pbs.class, parentPk);
                if(parent.getChilds()==null){
                 List list=new ArrayList();
                 list.add(dbBean);
                 parent.setChilds(list);
                }else{
                    parent.getChilds().add(dbBean);
                }
                getDbAccessor().update(parent);
            } else {
                LgAcntSeq.setRootRid(this.acntrid, Pbs.class, pbsRid);
            }
            return dbBean.getPk().getPbsRid();
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "Add pbs error.");
        }

    }


    public void delete(DtoPmsPbs dataBean) throws BusinessException {
        try {

            PbsPK pk = new PbsPK(dataBean.getAcntRid(), dataBean.getPbsRid());
            Pbs dbBean = (Pbs)this.getDbAccessor().load(Pbs.class, pk);
            delete( dbBean );
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "delete pbs error.");
        }
    }

    public void delete(Pbs dbBean){
        try {
            Pbs parent = dbBean.getParent();

            //delete file
            getDbAccessor().delete(dbBean.getFiles());

            //delete assignment
            getDbAccessor().delete(dbBean.getAssignments());

            //delete children
            List children = new ArrayList();
            if( dbBean.getChilds() != null ){
                for (Iterator iter = dbBean.getChilds().iterator(); iter.hasNext(); ) {
                    Pbs child = (Pbs) iter.next();
                    children.add(child);
                }
            }
            for (int i = 0; i < children.size(); i++) {
                Pbs child = (Pbs)children.get(i);
                delete(child);
            }

            //delete pbs self
            if (parent != null) {
                parent.getChilds().remove(dbBean);
                getDbAccessor().delete(dbBean);
                getDbAccessor().update(parent);
            } else {
                getDbAccessor().delete(dbBean);
            }
        } catch (Exception ex) {
            throw new BusinessException("E000000", "delete pbs error.",ex);
        }

    }

    public void update(DtoPmsPbs dataBean) throws BusinessException {
        try {
            checkParameter(dataBean);

            PbsPK pk = new PbsPK(dataBean.getAcntRid(), dataBean.getPbsRid());
            Pbs dbBean = (Pbs)this.getDbAccessor().load(Pbs.class, pk);
            copyPbs(dbBean, dataBean);

            getDbAccessor().update(dbBean);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "update pbs error.");
        }
    }


    private void copyPbs(Pbs ebs, DtoPmsPbs dto) throws Exception {
        try {
            DtoUtil.copyProperties(ebs, dto);
        } catch (Exception e) {
            //
        }

        if (StringUtil.nvl(dto.getPbsParentRid()).equals("") == false) {
            PbsPK parentPK = new PbsPK(dto.getAcntRid(), dto.getPbsParentRid());
            //Pbs parentEbs = (Pbs)this.getDbAccessor().load(Pbs.class, parentPK);
            Pbs parentEbs = new Pbs();
            parentEbs.setPk(parentPK);
            ebs.setParent(parentEbs);
        } else {
            ebs.setParent(null);
        }

        PbsPK pk = new PbsPK(dto.getAcntRid(), dto.getPbsRid());
        ebs.setPk(pk);
    }

    private void checkParameter(DtoPmsPbs dataBean) {
        if (dataBean.getPbsParentRid() == null) {
            return;
        }

        String code = StringUtil.nvl(dataBean.getProductCode());
        Long pbsRid = dataBean.getPbsRid();
        PbsPK parentPk = new PbsPK(dataBean.getAcntRid(), dataBean.getPbsParentRid());
        try {
            Pbs parent = (Pbs) getDbAccessor().load(Pbs.class, parentPk);
            if (parent != null) {
                for (Iterator it = parent.getChilds().iterator(); it.hasNext(); ) {
                    Pbs child = (Pbs) it.next();
                    if (code.equals(child.getProductCode()) == true) {
                        if (pbsRid == null || pbsRid.equals(child.getPk().getPbsRid()) == false) {
                            throw new BusinessException("E000", "The production code is exist.");
                        }
                    }
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException("E0000", "Error when check data.", ex);
        }
    }
}
