/*
 * Created on 2008-4-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.report.attvariant.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.report.attvariant.service.IAttVariantService;
import c2s.dto.TransactionData;
import c2s.essp.timesheet.report.DtoAttVariantQuery;

public class AcSendMails  extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData data)  {
     IAttVariantService lg = (IAttVariantService)this.
     getBean("attVariantService");
     List sendList = (List) data.getInputInfo().getInputObj(DtoAttVariantQuery
            .DTO_SEND_LIST);
     lg.sendMails(sendList);
    }
}
