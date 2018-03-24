package server.essp.ebs.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.essp.ebs.DtoAssignAcnt;
import db.essp.pms.Account;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public class LgAcntList extends AbstractBusinessLogic {

    public List list() throws BusinessException {
        List acntList = new ArrayList();
        try {
            //get root
            String sqlSel = " from Account t where t.rst='N' and t.inner='I' order by acnt_id desc";
            Iterator it = getDbAccessor().getSession().createQuery(sqlSel).iterate();
            while (it.hasNext()) {
                Account account = (Account) it.next();

                DtoAssignAcnt dtoAssignAcnt = new DtoAssignAcnt();
                dtoAssignAcnt.setRid(account.getRid());
                dtoAssignAcnt.setName(account.getId()
                                      + "( " + account.getName() + " ) - " + account.getRid());

                acntList.add(dtoAssignAcnt);
            }
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("", ex);
            throw new BusinessException("E000000",
                                        "Select ebs error.");
        }

        return acntList;
    }
}
