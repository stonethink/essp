package server.essp.tc.pmmanage.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;
import itf.account.AccountFactory;
import itf.account.IAccountUtil;
import server.essp.framework.logic.AbstractESSPLogic;
import server.framework.common.BusinessException;

public class LgTcInitAccountList extends AbstractESSPLogic {
    String loginId = "";
    List acntRidList = null;
    List acntNameList = null;
    public LgTcInitAccountList() {
        DtoUser user = getUser();
        if( user != null ){
            loginId = user.getUserLoginId();
            log.info( "loginId in session: " + loginId );
        }else{
//            loginId = "stone.shi";//for test
//            log.info( "loginId for test: " + loginId );
                throw new BusinessException("E0000","Please login first.");
        }
    }

    private void list() {
        acntRidList = new ArrayList();
        acntNameList = new ArrayList();

        IAccountUtil util = AccountFactory.create();
        List acntList = util.listAccountsByLoginID(loginId);
        acntList = util.listPMAccounts(acntList);

        for (Iterator iter = acntList.iterator(); iter.hasNext(); ) {
            IDtoAccount item = (IDtoAccount) iter.next();
            acntRidList.add(item.getRid());
            acntNameList.add(item.getId() + " -- " + item.getName());
        }
    }

    public List listAcntRid(){
        if( acntRidList == null ){
            list();
        }

        return acntRidList;
    }

    public List listAcntName(){
        if( acntNameList == null ){
            list();
        }

        return acntNameList;
    }

}
