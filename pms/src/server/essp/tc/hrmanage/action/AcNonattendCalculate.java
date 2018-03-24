package server.essp.tc.hrmanage.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import c2s.essp.tc.nonattend.DtoNonattend;
import java.util.Date;
import com.wits.util.*;
import c2s.essp.common.calendar.WorkTime;
import c2s.essp.common.calendar.WrokTimeFactory;
import c2s.essp.common.code.DtoKey;

public class AcNonattendCalculate extends AbstractESSPAction {


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
    public void executeAct(HttpServletRequest request, HttpServletResponse response, TransactionData data) throws BusinessException {
        DtoNonattend dto = (DtoNonattend) data.getInputInfo().getInputObj(DtoKey.DTO);
        Date dateFrom = dto.getDateFrom();
        Date dateTo = dto.getDateTo();
        String timeFromstr=comDate.dateToString(dateFrom,"HH:mm");
        String timeTostr=comDate.dateToString(dateTo,"HH:mm");
        WorkTime wt=WrokTimeFactory.serverCreate();
        double totalHours=wt.getWorkHours(timeFromstr,timeTostr);


        dto.setTotalHours(new Double(totalHours));

        data.getReturnInfo().setReturnObj(DtoKey.DTO, dto);
    }
}
