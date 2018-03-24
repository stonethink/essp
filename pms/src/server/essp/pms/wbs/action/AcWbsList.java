package server.essp.pms.wbs.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.wbs.logic.LgWbs;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.dto.ITreeNode;
import c2s.essp.common.user.DtoUser;
import com.wits.util.ThreadVariant;
import c2s.essp.common.account.IDtoAccount;
import java.util.List;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import c2s.essp.pms.account.DtoAcntKEY;

public class AcWbsList extends AbstractESSPAction {
    public AcWbsList() {
        super();
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
        //UserTest.test();
        LgWbs wbsLogic=new LgWbs();
        IDtoAccount accountDto=(IDtoAccount)this.getSession().getAttribute(IDtoAccount.SESSION_KEY);
        if(accountDto==null) {
            data.getReturnInfo().setError(true);
            data.getReturnInfo().setReturnMessage("Please select a account at first!!!");
            return;
        }
        if(accountDto.getRid()==null) {
            data.getReturnInfo().setError(true);
            data.getReturnInfo().setReturnMessage("Account'Rid is null!!!");
            return;
        }
        ITreeNode treeNode=wbsLogic.getWbsTree(accountDto.getRid());

        //mod by xr 2006/06/26
        //增加界面菜单的参数,SEPG可任意修改,其他菜单保持不变
        InputInfo inputInfo = data.getInputInfo();
        String entryFunType = (String) inputInfo.getInputObj("entryFunType");
        if(DtoAcntKEY.SEPG_ACCOUNT_FUN.equals(entryFunType)){

            if(treeNode != null){
                DtoWbsActivity dto = (DtoWbsActivity) treeNode.getDataBean();
                dto.setReadonly(false);
                List allChild = treeNode.listAllChildrenByPostOrder();
                for (int i = 0; i < allChild.size(); i++) {
                    ITreeNode node = (ITreeNode) allChild.get(i);
                    dto = (DtoWbsActivity) node.getDataBean();
                    dto.setReadonly(false);
                }
            }
        }

        data.getReturnInfo().setReturnObj(DtoKEY.WBSTREE,treeNode);
    }
}
