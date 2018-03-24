package server.essp.timecard.timecard.action;

import c2s.dto.*;

import org.apache.log4j.Category;

import server.essp.timecard.timecard.logic.*;
import server.framework.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wits.util.Parameter;
import server.framework.common.BusinessException;

public class FTC01010GeneralAction extends AbstractBusinessAction {
    static Category log = Category.getInstance("server");

    public FTC01010GeneralAction() {
    }

    /**
     * execute
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param data VDView_Data
     */
    public void executeAct(HttpServletRequest  request,
                        HttpServletResponse response,
                        TransactionData     data) throws BusinessException {
        log.debug("TimeCard Servlet:IN ");

        Parameter param = new Parameter();
        param.put("data",data);
        FTC01010GeneralLogic ftc = new FTC01010GeneralLogic();
        //ftc.setDbAccessor(this.getDbAccessor());
        //ftc.executeLogic(param);
        executeLogic(ftc,param);

    }

}
