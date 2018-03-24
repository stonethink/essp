package server.essp.pms.qa.monitoring.action;

import c2s.essp.pms.wbs.DtoWbsActivity;
import server.essp.pms.activity.logic.LgActivity;
import server.essp.pms.wbs.logic.LgWbs;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletResponse;
import server.essp.pms.activity.action.AcActivityDelete;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoQaMonitoring;
import c2s.essp.pms.qa.DtoQaCheckPoint;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckPoint;
import c2s.essp.pms.qa.DtoQaCheckAction;
import server.essp.pms.wbs.process.checklist.logic.LgQaCheckAction;
import c2s.dto.DtoUtil;

public class AcQaMonitoringDelete extends AbstractESSPAction {
    public AcQaMonitoringDelete() {
    }

    /**
     * executeAct
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data TransactionData
     * @throws BusinessException
     * @todo Implement this server.framework.action.AbstractBusinessAction
     *   method
     */
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {
        IDtoAccount accountDto = (IDtoAccount)this.getSession().getAttribute(
            IDtoAccount.SESSION_KEY);
        if (accountDto == null) {
            data.getReturnInfo().setError(true);
            data.getReturnInfo().setReturnMessage(
                "Please select a account at first!!!");
            return;
        }
        if (accountDto.getRid() == null) {
            data.getReturnInfo().setError(true);
            data.getReturnInfo().setReturnMessage("Account'Rid is null!!!");
            return;
        }
            DtoQaMonitoring dtoMoni = (DtoQaMonitoring) data.getInputInfo().getInputObj(
            DtoKEY.DTO);
            DtoQaCheckPoint cp=new DtoQaCheckPoint();
            DtoQaCheckAction ca=new DtoQaCheckAction();
          if(dtoMoni.getType().equals(cp.DTO_PMS_CHECK_POINT_TYPE)){
            LgQaCheckPoint lgCp=new LgQaCheckPoint();
            DtoUtil.copyBeanToBean(cp,dtoMoni);
            cp.setAcntRid(accountDto.getRid());
            lgCp.deleteCheckPoint(cp);
          }else if(dtoMoni.getType().equals(ca.DTO_PMS_CHECK_ACTION_TYPE)){
            LgQaCheckAction lgCa=new LgQaCheckAction();
            DtoUtil.copyBeanToBean(ca,dtoMoni);
            ca.setAcntRid(accountDto.getRid());
            lgCa.deleteCheckAction(ca);
          }
    }

}
