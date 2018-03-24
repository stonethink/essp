package server.essp.pwm.wp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pwm.wp.DtoAccountAcitivity;
import c2s.essp.pwm.wp.DtoPwWp;
import com.wits.util.Parameter;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pwm.wp.logic.FPW01000CommonLogic;
import server.essp.pwm.wp.logic.FPW01010GeneralInsertLogic;
import server.essp.pwm.wp.logic.FPW01010GeneralSelectLogic;
import server.essp.pwm.wp.logic.FPW01010GeneralUpdateLogic;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;
import server.essp.pms.activity.logic.LgActivityWorker;
import java.util.Date;
import java.util.Calendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.pms.wbs.DtoActivityWorker;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.GregorianCalendar;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import java.util.List;
import net.sf.hibernate.Query;
import server.essp.pwm.wpList.logic.LgWpList;

public class FPW01010GeneralAction extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                        HttpServletResponse response, TransactionData transData) throws BusinessException {
        AbstractBusinessLogic logic = null;
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();
        String funId = inputInfo.getFunId();

        if (funId.equals("getCurrAccountActivity") == true) {
//            Parameter param = new Parameter();
            Long inProjectId = (Long)inputInfo.getInputObj("inProjectId");
            Long inActivityId= (Long)inputInfo.getInputObj("inActivityId");

//            logic = new LGPwWpGetCurrAccountActivity();
//            logic.setDbAccessor( this.getDbAccessor() );
//            logic.executeLogic(param);
            DtoAccountAcitivity dtoAccountAcitivity =
                FPW01000CommonLogic.getCurrAccountActivity(this.getDbAccessor(), inProjectId, inActivityId);

            returnInfo.setReturnObj("dtoAccountAcitivity",dtoAccountAcitivity);
        } else if (funId.equals("getWpInfo") == true) {
            Parameter param = new Parameter();
            param.put("inWpId", (Long)inputInfo.getInputObj("inWpId"));
            //param.put("inUserId", (String)inputInfo.getInputObj("inUserId"));

            logic = new FPW01010GeneralSelectLogic();
            logic.setDbAccessor( this.getDbAccessor() );
            logic.executeLogic(param);

            returnInfo.setReturnObj("dto", param.get("dtoPwWp"));
            returnInfo.setReturnObj("isAssignby", param.get("isAssignby"));
            returnInfo.setReturnObj("dtoAccountAcitivity", param.get("dtoAccountAcitivity"));
        } else if (funId.equals("creatWp") == true) {
            Parameter param = new Parameter();
            DtoPwWp dtoPw = (DtoPwWp)inputInfo.getInputObj("dto");
            param.put("dtoPwWp", dtoPw);
            param.put("validatorResult", transData.getValidatorResult() );

            logic = new FPW01010GeneralInsertLogic();
            logic.setDbAccessor( this.getDbAccessor() );
            logic.executeLogic(param);

            FPW01000CommonLogic.CheckData(dtoPw,"creatWp");
            returnInfo.setReturnObj("dto", param.get("dtoPwWp"));
        } else if (funId.equals("modifyWp") == true) {
            Parameter param = new Parameter();
            DtoPwWp dtoPw = (DtoPwWp)inputInfo.getInputObj("dto");
            param.put("dtoPwWp", dtoPw);

            logic = new FPW01010GeneralUpdateLogic();
            logic.setDbAccessor( this.getDbAccessor() );
            logic.executeLogic(param);

            FPW01000CommonLogic.CheckData(dtoPw,"modifyWp");
            returnInfo.setReturnObj("dto", param.get("dtoPwWp"));
        }
    }
}



