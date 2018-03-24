package server.essp.tc.dmview.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import itf.orgnization.IOrgnizationUtil;
import itf.orgnization.OrgnizationFactory;
import server.framework.taglib.util.SelectOptionImpl;
import server.essp.framework.logic.AbstractESSPLogic;
import c2s.essp.common.user.DtoUser;
import server.framework.common.BusinessException;

public class LgTcInitObsList extends AbstractESSPLogic {
    String loginId = "";
    List orgIdList = null;
    List orgNameList = null;

    public LgTcInitObsList() {
        DtoUser user = getUser();
        if (user != null) {
            loginId = user.getUserLoginId();
            log.info("loginId in session: " + loginId);
        } else {
//            loginId = "stone.shi";
//            log.info("loginId for test: " + loginId);
            throw new BusinessException("E0000","Please login first.");
        }
    }

    private void list() {
        orgIdList = new ArrayList();
        orgNameList = new ArrayList();

        IOrgnizationUtil util = OrgnizationFactory.create();
        List orgList = util.listAllOrgByManager(loginId);

        for (Iterator iter = orgList.iterator(); iter.hasNext(); ) {
            SelectOptionImpl item = (SelectOptionImpl) iter.next();
            orgIdList.add(item.getValue());
            orgNameList.add(item.getLabel());
        }
    }

    public List listOrgId(){
        if( orgIdList == null ){
            list();
        }

        return orgIdList;
    }

    public List listOrgName(){
        if( orgNameList == null ){
            list();
        }

        return orgNameList;
    }

}
