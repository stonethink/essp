package server.essp.timesheet.code.codevalue.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ITreeNode;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.code.DtoCodeValue;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.code.codevalue.service.ICodeValueService;
import server.framework.common.BusinessException;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class AcListLeaveCodeValue extends AbstractESSPAction {

    /**
     * 列出所有Leave Code Value
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {

        ICodeValueService service = (ICodeValueService) this.getBean("codeValueService");
        ITreeNode root = service.getLeaveCodeValueTree();
        data.getReturnInfo().setReturnObj(DtoCodeValue.DTO_TREE, root);

    }
}
