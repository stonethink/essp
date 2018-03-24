package server.essp.timesheet.code.coderelation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.code.DtoCodeValue;
import c2s.dto.ITreeNode;
import server.essp.timesheet.code.coderelation.service.ICodeRelationService;
import c2s.essp.timesheet.code.DtoCodeType;

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
public class AcSaveCodeRelation extends AbstractESSPAction {

    /**
     * ±£¥ÊCode Relation
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
        ITreeNode root = (ITreeNode) data.getInputInfo().getInputObj(DtoCodeValue.DTO_TREE);
        ICodeRelationService service = (ICodeRelationService) this.getBean("codeRelationService");
        service.saveRelation(codeTypeRid, root, isLeaveType);
    }
}
