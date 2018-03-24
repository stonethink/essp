package server.essp.timesheet.preference.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.preference.DtoPreference;
import server.essp.timesheet.preference.service.IPreferenceService;
import server.essp.timesheet.approval.service.IApprovalAssistService;

public class AcSavePreference extends AbstractESSPAction {

    /**
     * 保存当前管理员设置信息
     *
     * @param httpServletRequest request
     * @param httpServletResponse response
     * @param transactionData data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {

        DtoPreference dtoPreference = (DtoPreference) data.getInputInfo()
                                      .getInputObj(DtoPreference.DTO);
        String processType = (String) data.getInputInfo()
                         .getInputObj(DtoPreference.PROCESS_TYPE);
        String site = (String) data.getInputInfo().getInputObj(DtoPreference.DTO_SITE);
        IPreferenceService service = (IPreferenceService)
                                     this.getBean("preferenceService");
        if(!DtoPreference.DTO_SITE_GLOBAL.equals(dtoPreference.getSite())) {
        	IApprovalAssistService approvalAssistService = (IApprovalAssistService)
            this.getBean("approvalAssistService");
        	approvalAssistService.processLevelChanged(processType, site);
        }
        service.savePreference(dtoPreference);
    }
}
