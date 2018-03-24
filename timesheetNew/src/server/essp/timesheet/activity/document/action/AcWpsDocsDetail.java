package server.essp.timesheet.activity.document.action;

import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.timesheet.activity.DtoDocumentDetail;
import server.essp.timesheet.activity.document.service.IDocumentService;
import c2s.essp.timesheet.activity.DtoActivityKey;

/**
 * <p>Description:得到Document详细信息的Action </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class AcWpsDocsDetail  extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        IDocumentService lg = (IDocumentService)this.getBean("iDocumentService");
        Long docRid = (Long) inputInfo.getInputObj(DtoActivityKey.DOCUMENT_RID);
        DtoDocumentDetail dtoDetail = lg.getDocDetail(docRid);
        returnInfo.setReturnObj(DtoActivityKey.DTO_DOCUMENT_DETAIL, dtoDetail);
    }

}
