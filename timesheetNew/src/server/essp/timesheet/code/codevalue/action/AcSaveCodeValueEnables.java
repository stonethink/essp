package server.essp.timesheet.code.codevalue.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.timesheet.code.codevalue.service.ICodeValueService;
import c2s.essp.timesheet.code.DtoCodeValue;
import c2s.dto.ITreeNode;

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
public class AcSaveCodeValueEnables extends AbstractESSPAction {

    /**
     * 保存CodeValue树所有节点的的Enable属性
     *
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @param transactionData TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {

        ITreeNode root = (ITreeNode) data.getInputInfo().getInputObj(DtoCodeValue.DTO_TREE);
        ICodeValueService service = (ICodeValueService) this.getBean("codeValueService");
        service.saveEnables(root);
    }
}
