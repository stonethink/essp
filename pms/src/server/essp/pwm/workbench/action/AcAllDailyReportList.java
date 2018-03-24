package server.essp.pwm.workbench.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pwm.workbench.DtoKey;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pwm.workbench.logic.LgAllDailyReportList;
import server.framework.common.BusinessException;

public class AcAllDailyReportList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        if( inputInfo.getFunId().equals(DtoKey.SELECT_ALL_DLRPT) ){
            Date selectedDate = (Date) inputInfo.getInputObj(DtoKey.SELECTED_DATE);

            LgAllDailyReportList logic = new LgAllDailyReportList();
            ITreeNode allDlrpt = logic.selectAllDlrpt(selectedDate);

            returnInfo.setReturnObj(DtoKey.ALL_DLRPT, allDlrpt);
        }else {
            throw new BusinessException("E00", "Invalid function id - " + inputInfo.getFunId());
        }
    }
}
