package server.essp.timesheet.report.utilizationRate.detail.action;

import javax.servlet.http.HttpServletRequest;
import server.essp.timesheet.report.utilizationRate.detail.service.IUtilRateService;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.timesheet.report.DtoUtilRateKey;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: AcLoadUserList</p>
 *
 * <p>Description: 得到員工集合</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class AcLoadUserList extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData data) throws
            BusinessException {
        IUtilRateService lg = (IUtilRateService) this.getBean("iUtilRateService");
        String acntId = (String)data.getInputInfo().getInputObj(DtoUtilRateKey.DTO_ACCOUNT_ID);
        data.getReturnInfo().setReturnObj(DtoUtilRateKey.
                    DTO_USER_LIST, lg.getUserList(acntId));

    }
}
