package server.essp.timesheet.code.coderelation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import server.essp.timesheet.code.coderelation.service.ICodeRelationService;
import c2s.essp.timesheet.code.DtoCodeType;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.code.DtoCodeValue;

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
public class AcListCodeRelation extends AbstractESSPAction {

    /**
     * 列出某Code Type下的Code Relation
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
    	boolean isLeaveType = (Boolean) data.getInputInfo().getInputObj(DtoCodeType.DTO_IS_LEAVE_TYPE);
        Long codeTypeRid = (Long) data.getInputInfo().getInputObj(DtoCodeType.DTO_RID);
        ICodeRelationService service = (ICodeRelationService) this.getBean("codeRelationService");
        ITreeNode root = service.getRelationTree(codeTypeRid, isLeaveType);
        data.getReturnInfo().setReturnObj(DtoCodeValue.DTO_TREE, root);
    }
}
