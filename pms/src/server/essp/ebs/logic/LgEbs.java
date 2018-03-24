package server.essp.ebs.logic;

import java.util.Iterator;

import c2s.dto.DtoUtil;
import c2s.essp.ebs.DtoEbsEbs;
import db.essp.pms.Ebs;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import db.essp.pms.Account;

public class LgEbs extends AbstractBusinessLogic {

    public void add(DtoEbsEbs dataBean) throws BusinessException {
        try {
            checkParameter(dataBean);

            Ebs dbBean = new Ebs();
            copyEbs(dbBean, dataBean);
            Long lParentRid = dataBean.getEbsParentRid();
            if( lParentRid != null ){
                Ebs ebsParent = new Ebs();
                ebsParent.setRid(lParentRid);
                dbBean.setParent(ebsParent);
            }else{
                dbBean.setParent(null);
            }
            log.debug("insert:");
            getDbAccessor().save(dbBean);

            dataBean.setRid(dbBean.getRid());
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "Add ebs error.");
        }
    }

    public void delete(DtoEbsEbs dataBean) throws BusinessException {
        try {
            Ebs dbBean = (Ebs)this.getDbAccessor().load(Ebs.class, dataBean.getRid());
            delete(dbBean);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "delete ebs error.");
        }
    }

    public void delete(Ebs dbBean) throws BusinessException {
        try {
             log.debug(";delete:");
            getDbAccessor().delete(dbBean);

            //delete child ebs
            for( Iterator it = dbBean.getChilds().iterator(); it.hasNext(); ){
                Ebs child = (Ebs)it.next();
                delete(child);
            }

            //delete the relation with account
            for( Iterator it = dbBean.getAccounts().iterator(); it.hasNext(); ){
                Account acnt = (Account)it.next();

                LgEbs9Acnt lgEbs9Acnt = new LgEbs9Acnt();
                lgEbs9Acnt.delete(acnt.getRid(), dbBean.getRid());
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "delete ebs error.");
        }
    }


    public void update(DtoEbsEbs dataBean) throws BusinessException {
        try {
            checkParameter(dataBean);

            Ebs dbBean = (Ebs)this.getDbAccessor().load(Ebs.class, dataBean.getRid());
            copyEbs(dbBean, dataBean);

            getDbAccessor().update(dbBean);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "update ebs error.");
        }
    }

    private void copyEbs(Ebs ebs, DtoEbsEbs dto){
        try {
            DtoUtil.copyProperties(ebs, dto);
            Ebs parent = new Ebs();
            parent.setRid(dto.getEbsParentRid());
            ebs.setParent(parent);
        } catch (Exception e) {
            //
        }
    }

    private void checkParameter(DtoEbsEbs dto)throws BusinessException {
        if(dto == null){
            throw new BusinessException( "E00001", "Submit invalid data -" + dto );
        }

        //同一EBS下Code不能重复
        if(dto.getEbsParentRid() != null){
            String sSel = "from Ebs t where t.ebsId=:code and t.parent.rid=:parentRid";
            try {
                Iterator it = this.getDbAccessor().getSession().createQuery(sSel)
                              .setString("code", dto.getCode())
                              .setLong("parentRid", dto.getEbsParentRid().longValue())
                              .iterate();
                if( it.hasNext() ){
                    Ebs ebs = (Ebs)it.next();
                    if (dto.getRid() == null ||
                        dto.getRid().longValue() != ebs.getRid().longValue()) {
                        throw new BusinessException("E00001",
                            "Ebs code is already exist.");
                    }
                }
            } catch (BusinessException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new BusinessException("E00001", "Exception when check the code of Ebs");
            }
        }
    }

}
