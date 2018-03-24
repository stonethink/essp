package server.essp.ebs.logic;

import db.essp.pms.EbsEbs9acnt;
import db.essp.pms.EbsEbs9acntPK;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public class LgEbs9Acnt extends AbstractBusinessLogic {

    public void update(Long acntRid, Long newEbsRid, Long oldEbsRid) throws BusinessException {
        try {
            //1:delete old
            EbsEbs9acntPK ebsEbs9acntPKOld = new EbsEbs9acntPK(oldEbsRid, acntRid);
            EbsEbs9acnt ebsEbs9acntOld = new EbsEbs9acnt(ebsEbs9acntPKOld);
            log.debug("delete from EbsEbs9acnt:");
            getDbAccessor().delete(ebsEbs9acntOld);

            //2:insert new
            EbsEbs9acntPK ebsEbs9acntPKNew = new EbsEbs9acntPK(newEbsRid, acntRid);
            EbsEbs9acnt ebsEbs9acntNew = new EbsEbs9acnt(ebsEbs9acntPKNew);
            log.debug("insert to EbsEbs9acnt:");
            getDbAccessor().save(ebsEbs9acntNew);

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "update pms account error.");
        }
    }

    public void insert(Long acntRid, Long ebsRid) throws BusinessException {
        try {
            //insert new
            EbsEbs9acntPK ebsEbs9acntPKNew = new EbsEbs9acntPK(ebsRid, acntRid);
            EbsEbs9acnt ebsEbs9acntNew = new EbsEbs9acnt(ebsEbs9acntPKNew);
            log.debug("insert to EbsEbs9acnt:");
            getDbAccessor().save(ebsEbs9acntNew);

        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "update pms account error.");
        }

    }

    public void delete(Long acntRid, Long ebsRid) throws BusinessException {
        try {
            //
            EbsEbs9acntPK ebsEbs9acntPK = new EbsEbs9acntPK(ebsRid, acntRid);
            EbsEbs9acnt ebsEbs9acnt = (EbsEbs9acnt)getDbAccessor().load(EbsEbs9acnt.class, ebsEbs9acntPK);
            log.debug("delete from EbsEbs9acnt.");
            getDbAccessor().delete(ebsEbs9acnt);
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("", ex);
            throw new BusinessException("E000000", "delete pms account error.");
        }

    }

}
