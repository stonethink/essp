package server.essp.timesheet.preference.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.preference.DtoPreference;
import server.essp.timesheet.preference.service.IPreferenceService;

public class AcLoadPreference extends AbstractESSPAction {

    /**
     * 获取当前管理员设置信息
     *
     * @param httpServletRequest request
     * @param httpServletResponse response
     * @param transactionData data
     * @throws BusinessException
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws BusinessException {
    	String site = (String) data.getInputInfo().getInputObj(DtoPreference.DTO_SITE);
        IPreferenceService service = (IPreferenceService)
                                     this.getBean("preferenceService");
        DtoPreference dtoPreference = service.getLoadPreferenceBySite(site);

        data.getReturnInfo().setReturnObj(DtoPreference.DTO, dtoPreference);
    }
}
