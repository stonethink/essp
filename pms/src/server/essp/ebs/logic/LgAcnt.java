package server.essp.ebs.logic;

import java.util.List;

import c2s.dto.DtoUtil;
import c2s.essp.ebs.DtoPmsAcnt;
import db.essp.pms.Account;
import net.sf.hibernate.Query;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import java.util.Iterator;
import java.sql.ResultSet;

public class LgAcnt extends AbstractBusinessLogic {
    public void add(DtoPmsAcnt dataBean, Long parentId) throws BusinessException {
        try {
            checkParameter(dataBean, parentId);

            Account dbBean = new Account();
            try {
                DtoUtil.copyProperties(dbBean, dataBean);
            } catch (Exception e) {
                //
            }
            //getDbAccessor().assignedRid(dbBean, "PMS_ACNT");

            //1: insert to Account table
            dbBean.setInner("I");
            log.debug("insert to PmsAcnt:");
            getDbAccessor().save(dbBean);
            dataBean.setRid(dbBean.getRid());

            //2: insert to EbsEbs9acnt
            LgEbs9Acnt ebs9AcntLogic = new LgEbs9Acnt();
            //ebs9AcntLogic.setDbAccessor(getDbAccessor());
            ebs9AcntLogic.insert(dbBean.getRid(), parentId);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "Add pms account error.");
        }
    }

    public DtoPmsAcnt select(Long rid) throws BusinessException {
        try {
            Account dbBean = (Account)this.getDbAccessor().load(Account.class, rid);
            if (dbBean == null) {
                return null;
            } else {
                DtoPmsAcnt dataBean = new DtoPmsAcnt();
                try {
                    DtoUtil.copyProperties(dataBean, dbBean);
                } catch (Exception e) {
                    //
                }

                return dataBean;
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "select pms account error.");
        }
    }

    public void delete(DtoPmsAcnt dataBean, Long parentId) throws BusinessException {
        try {
            //1: delete from EbsEbs9acnt
            Query q = getDbAccessor().getSession().createQuery("from EbsEbs9acnt t where t.comp_id.acntRid = :acntRid and t.comp_id.ebsRid = :ebsRid ");
            q.setLong("acntRid",dataBean.getRid().longValue());
            q.setLong("ebsRid", parentId.longValue());
            List ebsEbs9acntList = q.list();
            getDbAccessor().delete(ebsEbs9acntList);

            //1: delete from Account
//            Account dbBean = (Account)this.getDbAccessor().load( Account.class, new Long(dataBean.getRid()) );
//            getDbAccessor().delete(dbBean);


        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "delete pms account error.");
        }
    }

    public void update(DtoPmsAcnt dataBean) throws BusinessException {
        try {
            checkParameter(dataBean);

            Account dbBean = (Account)this.getDbAccessor().load(Account.class, dataBean.getRid());
            try {
                DtoUtil.copyProperties(dbBean, dataBean);
            } catch (Exception e) {
                //
            }

            getDbAccessor().update(dbBean);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "update pms account error.");
        }

    }

    private void checkParameter(DtoPmsAcnt dto, Long parentId)throws BusinessException {
        if(dto == null || parentId == null ){
            throw new BusinessException( "E00001", "Submit invalid data - null." );
        }

        checkParameter(dto);
    }

    private void checkParameter(DtoPmsAcnt dto)throws BusinessException {
        if(dto == null ){
            throw new BusinessException( "E00001", "Submit invalid data - null." );
        }

        //Code≤ªƒ‹÷ÿ∏¥
        String sSel = " select * from pms_acnt t where t.acnt_id='" + dto.getId() + "' ";
        try {
            ResultSet it = this.getDbAccessor().executeQuery(sSel);
            if (it.next()) {
                if( dto.getRid()==null || dto.getRid().longValue() != it.getLong("rid") ){
                    throw new BusinessException("E00001", "Account code is already exist.");
                }
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BusinessException("E00001", "Exception when check the code of Account");
        }

    }

}
