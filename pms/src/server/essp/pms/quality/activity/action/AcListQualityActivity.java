package server.essp.pms.quality.activity.action;

import server.essp.framework.action.AbstractESSPAction;
import java.util.List;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import server.framework.common.BusinessException;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import db.essp.pms.Activity;
import java.util.ArrayList;

import db.essp.pms.PmsPcbParameter;
import c2s.essp.pms.quality.activity.DtoQualityActivity;
import server.essp.pms.quality.activity.logic.LgQualityActivity;
import server.essp.pms.account.pcb.logic.LgPcb;
import javax.servlet.http.HttpSession;
import c2s.essp.common.account.IDtoAccount;
import db.essp.pms.ActivityQuality;


public class AcListQualityActivity extends AbstractESSPAction {
    public AcListQualityActivity() {
    }

    public void executeAct(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           TransactionData transactionData) throws
        BusinessException {
        HttpSession session = httpServletRequest.getSession();
        IDtoAccount accountDto = (IDtoAccount) session.getAttribute(
            IDtoAccount.SESSION_KEY);
        LgQualityActivity qa = new LgQualityActivity();
        List rsList = qa.listQualityActivity(accountDto);
        ReturnInfo returnInfo = transactionData.getReturnInfo();

        returnInfo.setReturnObj("qaList", rsList);
        returnInfo.setReturnObj("accountDto", accountDto);
    }
    }

