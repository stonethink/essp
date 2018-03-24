/*
 * Created on 2007-10-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.timesheet.aprm.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.timesheet.aprm.form.AfAPRMTSImport;
import server.essp.timesheet.aprm.service.IAPRMService;
import server.essp.timesheet.aprm.viewbean.VbAprmImportInfo;
import server.framework.common.BusinessException;

public class AcAPRMImport extends AbstractESSPAction{

    @Override
    public void executeAct(HttpServletRequest request,
            HttpServletResponse response,
            TransactionData data) throws BusinessException {
           AfAPRMTSImport af = (AfAPRMTSImport) getForm();
           IAPRMService aprmService = (IAPRMService) this.getBean("iAprmService");
           //将得到的字符流解析后保存到工时单的对应记录表中
           VbAprmImportInfo vb = aprmService.saveDateFromInputStream(af,getUser().getUserLoginId());
           this.getRequest().setAttribute("flag",vb.getRemark());
           this.getRequest().setAttribute("webVo",vb);
    }
}
