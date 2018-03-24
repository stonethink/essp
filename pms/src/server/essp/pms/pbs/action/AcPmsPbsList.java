package server.essp.pms.pbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.common.account.IDtoAccount;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.pbs.logic.LgPmsPbsList;
import server.framework.common.BusinessException;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.pbs.DtoPmsPbs;
import java.util.List;

public class AcPmsPbsList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {

        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        String entryFunType = (String) inputInfo.getInputObj("entryFunType");
        Long inAcntRid = null;
        String inAcntName = "";
        String inAcntCode = "";
        Boolean isPm = Boolean.FALSE;
        IDtoAccount account = (IDtoAccount) request.getSession().getAttribute(
            IDtoAccount.SESSION_KEY);
        if (account != null) {
            inAcntRid = account.getRid();
            inAcntName = account.getName();
            inAcntCode = account.getId();
            isPm = new Boolean(account.isPm());
        } else {
                throw new BusinessException("E000", "Can't get the account information from session.Please select one account.");
        }

        LgPmsPbsList lgPmsPbsList = new LgPmsPbsList();
        lgPmsPbsList.setDbAccessor(this.getDbAccessor());
        ITreeNode root = lgPmsPbsList.list();


        //add by xr 2006/06/26
        //增加界面菜单的参数,SEPG可任意修改,其他菜单保持不变
        if(DtoAcntKEY.SEPG_ACCOUNT_FUN.equals(entryFunType)){
            isPm = Boolean.TRUE;

            if(root != null){
                DtoPmsPbs data = (DtoPmsPbs) root.getDataBean();
                data.setReadonly(false);
                List children = root.listAllChildrenByPostOrder();
                for (int i = 0; i < children.size(); i++) {
                    ITreeNode node = (ITreeNode) children.get(i);
                    data = (DtoPmsPbs)  node.getDataBean();
                    data.setReadonly(false);
                }
            }
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("isPm:"+isPm);
        System.out.println("entryFunType:"+entryFunType);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++");

        returnInfo.setReturnObj("root", root);
        returnInfo.setReturnObj("acntName", inAcntName);
        returnInfo.setReturnObj("acntCode", inAcntCode);
        returnInfo.setReturnObj("isPm", isPm);
    }
}
