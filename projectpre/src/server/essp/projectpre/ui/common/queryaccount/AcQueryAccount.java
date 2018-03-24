package server.essp.projectpre.ui.common.queryaccount;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.essp.framework.action.AbstractESSPAction;
import server.essp.projectpre.service.account.IAccountService;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;


public class AcQueryAccount extends AbstractESSPAction {

    /**
     * ����Action Form �д����������ѯר������
     * ����account�б�
     */
	public void executeAct(HttpServletRequest request,
            HttpServletResponse response, TransactionData data)
            throws BusinessException {
     
		AfQueryAccount af = (AfQueryAccount) this.getForm();
    	IAccountService logicAccount = (IAccountService) this.getBean("AccountLogic");
        List accountList = new ArrayList();
        String personKeys = "";
        if(af.getPersonKeys()!=null&&!af.getPersonKeys().equals("")){
            personKeys = af.getPersonKeys();
        } 
        accountList = logicAccount.queryAccount(af.getAccountId(),af.getAccountName(),af.getParamKeys(), personKeys);
		request.setAttribute(server.framework.common.Constant.VIEW_BEAN_KEY, accountList);  
    }

}