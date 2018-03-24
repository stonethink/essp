package server.essp.pms.quality.activity.action;

import server.essp.framework.action.AbstractESSPAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.framework.common.BusinessException;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import c2s.essp.pms.quality.activity.DtoQualityActivity;
import server.essp.pms.quality.activity.logic.LgQualityActivity;
import javax.servlet.http.HttpSession;
import c2s.essp.common.user.DtoUser;

public class AcSaveGeneral extends AbstractESSPAction {
    public AcSaveGeneral() {
    }

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData data) throws
        BusinessException {

        HttpSession session = request.getSession();
        DtoUser user = (DtoUser) session.getAttribute(DtoUser.SESSION_USER);
        String userName = (String)user.getUserID();
        InputInfo inputInfo = data.getInputInfo();
        ReturnInfo returnInfo = data.getReturnInfo();
        DtoQualityActivity dtoQualityActivity = (DtoQualityActivity) inputInfo.
                                                getInputObj(
            "dtoQualityActivity");
        String conclusionBy = (String) inputInfo.getInputObj("conclusionBy");
        if (conclusionBy != null && conclusionBy.equals("conclusionBy")) {
            dtoQualityActivity.setConclusionBy(userName);
        }
        LgQualityActivity lgQualityActivity = new LgQualityActivity();
        lgQualityActivity.update(dtoQualityActivity);
    }
}
