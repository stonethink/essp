package server.essp.issue.typedefine.action;

import javax.servlet.http.*;

import c2s.dto.*;
import server.framework.action.*;
import server.framework.common.*;
import server.essp.issue.typedefine.logic.LgTypeList;
import server.essp.issue.typedefine.viewbean.VbTypeList;
import com.wits.util.StringUtil;


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
public class AcTypeList extends AbstractBusinessAction {
  /**
   * 获取该Issue Type的列表
   * Call: LgTypeList.list()
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

            String selectedRowObj=(String)request.getParameter("selectedRowObj");
            String selectedTabIndex=(String)request.getParameter("selectedTabIndex");
            String newTypeName=(String)request.getParameter("newTypeName");
            if (StringUtil.nvl(newTypeName).equals("")==true){
              newTypeName="";
            }
            if (StringUtil.nvl(selectedRowObj).equals("")==true){
              selectedRowObj="tr0";
            }
            if (StringUtil.nvl(selectedTabIndex).equals("")==true){
              selectedTabIndex="1";
            }

            LgTypeList logic = new LgTypeList();
            logic.setDbAccessor(this.getDbAccessor());

            VbTypeList listViewBean=logic.getListViewBean();
            listViewBean.setSelectedRowObj(selectedRowObj);
            listViewBean.setSelectedTabIndex(selectedTabIndex);
            listViewBean.setAddTypeName(newTypeName.toUpperCase().trim());
            request.setAttribute("webVo",listViewBean);
  }

}
