package server.essp.pwm.workbench.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.pwm.workbench.DtoKey;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pwm.workbench.logic.LgWorkItemList;
import server.framework.common.BusinessException;
import c2s.essp.pwm.workbench.DtoPwWkitemPeriod;
import c2s.essp.pwm.workbench.DtoPwWkitem;

public class AcWorkItemList extends AbstractESSPAction {

    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response, TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        if (inputInfo.getFunId().equals(DtoKey.SELECT)
            || inputInfo.getFunId().equals(DtoKey.RELOAD)
            ) {
            Date selectedDate = (Date) inputInfo.getInputObj(DtoKey.SELECTED_DATE);

            LgWorkItemList logic = new LgWorkItemList();
            List wkitemList = logic.select(selectedDate);

            returnInfo.setReturnObj(DtoKey.WKITEM_LIST, wkitemList);
        } else if (inputInfo.getFunId().equals(DtoKey.UPDATE)) {
            List wkitemList = (List) inputInfo.getInputObj(DtoKey.WKITEM_LIST);

            LgWorkItemList logic = new LgWorkItemList();
            logic.update(wkitemList);

            returnInfo.setReturnObj(DtoKey.WKITEM_LIST, wkitemList);
        } else if (inputInfo.getFunId().equals(DtoKey.SUBMIT_DAILY_REPORT)) {
            List wkitemList = (List) inputInfo.getInputObj(DtoKey.WKITEM_LIST);

            LgWorkItemList logic = new LgWorkItemList();
            logic.submitDailyReport(wkitemList);

            returnInfo.setReturnObj(DtoKey.WKITEM_LIST, wkitemList);
        } else if (inputInfo.getFunId().equals(DtoKey.CUSTOM)) {
            DtoPwWkitemPeriod dto = (DtoPwWkitemPeriod) inputInfo.getInputObj(DtoKey.DTO);

            LgWorkItemList logic = new LgWorkItemList();
            logic.custom(dto);
        } else if (inputInfo.getFunId().equals(DtoKey.DEL_CUSTOM)) {
            DtoPwWkitem dto = (DtoPwWkitem) inputInfo.getInputObj(DtoKey.DTO);

            LgWorkItemList logic = new LgWorkItemList();
            List dateList = logic.delCustom(dto);
            returnInfo.setReturnObj(DtoKey.DUPLICATE_DATE_LIST, dateList);
        } else {
            throw new BusinessException("E00", "Invalid function id - " + inputInfo.getFunId());
        }
    }
}
