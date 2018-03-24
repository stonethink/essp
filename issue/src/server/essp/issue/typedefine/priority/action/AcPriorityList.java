package server.essp.issue.typedefine.priority.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import com.wits.util.StringUtil;
import server.essp.issue.typedefine.priority.logic.LgPriorityList;
import server.essp.issue.typedefine.priority.viewbean.VbPriorityList;
import server.essp.issue.typedefine.priority.form.AfPriority;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author QianLiu
 * @version 1.0
 */
public class AcPriorityList extends AbstractBusinessAction {
  /**
   * 获取该IssueType Priority的列表
   * Call: LgPriorityList.list()
   * ForwardID: list
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param data TransactionData
   * @throws BusinessException
   * @todo Implement this server.framework.action.AbstractBusinessAction method
   */
  public void executeAct(HttpServletRequest request,
                         HttpServletResponse response, TransactionData data) throws
      BusinessException {
            AfPriority issuePriorityForm = (AfPriority)this.getForm();
            String selectedRowObj=issuePriorityForm.getSelectedRowObj();
            String typeName=issuePriorityForm.getTypeName();
            String addPriority=issuePriorityForm.getAddPriority();
            if (StringUtil.nvl(addPriority).equals("")==true){
              addPriority="";
            }
            if (StringUtil.nvl(selectedRowObj).equals("")==true){
              selectedRowObj="tr0";
            }
            if (StringUtil.nvl(typeName).equals("")==true){
              typeName="";
            }

            LgPriorityList logic = new LgPriorityList();
            logic.setDbAccessor(this.getDbAccessor());

            VbPriorityList listViewBean=logic.getListViewBean(typeName);
            listViewBean.setSelectedRowObj(selectedRowObj);
            listViewBean.setAddPriority(addPriority.toUpperCase());
            listViewBean.setTypeName(typeName.toUpperCase().trim());
            request.setAttribute("webVo",listViewBean);
            data.getReturnInfo().setForwardID("list");
  }

}
