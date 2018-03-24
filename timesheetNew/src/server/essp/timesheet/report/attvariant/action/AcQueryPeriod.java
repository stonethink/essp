/*
 * Created on 2008-1-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attvariant.action;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.attvariant.service.IAttVariantService;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.report.DtoAttVariantQuery;

/**
 * AcQueryPeriod
 * @author tbh
 */
public class AcQueryPeriod extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData data)  {
     IAttVariantService lg = (IAttVariantService) this.getBean("attVariantService");
     String loginId = this.getUser().getUserLoginId();
     DtoAttVariantQuery dtoQuery = (DtoAttVariantQuery) data.getInputInfo().
                     getInputObj(DtoAttVariantQuery.DTO_ATTVARIANT_QUERY);
     dtoQuery.setLoginId(loginId);
     Map mapAtt= lg.queryByCondition(dtoQuery);
     data.getReturnInfo().setReturnObj(DtoAttVariantQuery.DTO_RESULT_LIST,mapAtt);
    }
}

