package server.essp.pms.account.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.user.DtoUser;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoPmsAcnt;
import server.essp.framework.action.AbstractESSPAction;
//import c2s.essp.common.user.User;
import server.essp.pms.account.logic.LgAccountList;
import server.framework.common.BusinessException;
import java.util.Vector;
import server.essp.common.syscode.LgSysParameter;
import java.util.ArrayList;
import c2s.dto.DtoComboItem;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcAccountList extends AbstractESSPAction {
    public AcAccountList() {
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        String status = (String) inputInfo.getInputObj("status");
        String accountType = (String) inputInfo.getInputObj("accountType");
        String template = (String) inputInfo.getInputObj("template");
        String AllAccount = (String)inputInfo.getInputObj("AllAccount");
        String condition = (String) inputInfo.getInputObj(DtoAcntKEY.ACCOUNT_LIST_CONDITION);
        String entryFunType = (String) inputInfo.getInputObj(DtoAcntKEY.ACCOUNT_ENTRY_FUN_TYPE);

        IDtoAccount currAccount = (IDtoAccount)getSession().getAttribute( DtoPmsAcnt.SESSION_KEY );
        DtoUser user = (DtoUser)getSession().getAttribute( DtoUser.SESSION_USER );

        LgAccountList logic = new LgAccountList(condition, entryFunType);
        logic.setCurrAccount(currAccount);
        //查询Account Type和Status的配置
        LgSysParameter lgSysParameter = new LgSysParameter();
        Vector accountTypeList = lgSysParameter.listComboSysParas(AcAcntInit.kindAccountType);
        Vector accountStatusList = lgSysParameter.listComboSysParas(AcAcntInit.kindAccountStatus);

        //根据传入的status和type参数,将过滤条件放入List中
        List statusSearchList = new ArrayList();
        if(status == null || "".equals(status)){
            statusSearchList = getValueList(accountStatusList);
        }else{
            statusSearchList.add(status);
        }
        List typeSearchList = new ArrayList();
        if(accountType == null || "".equals(accountType)){
            typeSearchList = getValueList(accountTypeList);
        }else{
            typeSearchList.add(accountType);
        }

        List result = new ArrayList();
        //查找项目
        if(DtoAcntKEY.ALL.equals(template) || DtoAcntKEY.ONLY_ACCOUNT.equals(template)){
            List accountList = null;
            if(AllAccount !=null){
                if(statusSearchList!=null&&statusSearchList.size()>1){
                    //StatusList中有很多项，表明没有选择一个确定的类型
                    statusSearchList.add("null");
                }
                if(typeSearchList!=null&&typeSearchList.size()>1){
                    //typeList中有很多项，表明没有选择一个确定的类型
                    typeSearchList.add("null");
                }
                accountList = logic.listAllUser(statusSearchList,typeSearchList);
            }else{
                if (DtoAcntKEY.EBS_ACCOUNT_FUN.equals(inputInfo.getFunId())) {
                    accountList = logic.listEbsAcntByLoginID(user.getUserLoginId(),
                                                             statusSearchList,
                                                             typeSearchList);
                } else {
                    accountList = logic.listAccountByLoginID(user.getUserLoginId(),
                                                             statusSearchList,
                                                             typeSearchList);

                    if (accountList != null && accountList.size() > 0) {
                        this.getSession().setAttribute(IDtoAccount.SESSION_KEY,
                                                       logic.getChooseAccount());
                    }
                }
            }
            if(accountList != null)
                result.addAll(accountList);
        }
        //查找模板
        if(DtoAcntKEY.ALL.equals(template) || DtoAcntKEY.ONLY_TEMPLATE.equals(template)){
            if(statusSearchList!=null&&statusSearchList.size()>1){
                //StatusList中有很多项，表明没有选择一个确定的类型
                statusSearchList.add("null");
            }
            List templateList = logic.listTemplate(statusSearchList,typeSearchList);
            if(templateList != null)
                result.addAll(templateList);
        }

        //查找OSP
        if(DtoAcntKEY.ALL.equals(template) || DtoAcntKEY.ONLY_OSP.equals(template)){
            if(statusSearchList!=null&&statusSearchList.size()>1){
                //StatusList中有很多项，表明没有选择一个确定的类型
                statusSearchList.add("null");
            }
            List ospList = logic.listOSP(statusSearchList);
            if(ospList != null)
                result.addAll(ospList);
        }
        returnInfo.setReturnObj(DtoAcntKEY.ACCOUNT_LIST,result);
        returnInfo.setReturnObj(DtoAcntKEY.ROW_INDEX_CHOOSED,
                                new Integer(logic.getChooseRowIndex()) );
        returnInfo.setReturnObj("accountTypeList", accountTypeList);
        returnInfo.setReturnObj("accountStatusList", accountStatusList);
    }

    private List getValueList(Vector comItemList) {
        List tempList = new ArrayList();
        for(int i = 0 ;i < comItemList.size() ;i ++){
            DtoComboItem item = (DtoComboItem) comItemList.get(i);
            String value = (String) item.getItemValue();
            if(value != null)
            tempList.add(value);
        }
        return tempList;
    }
}
