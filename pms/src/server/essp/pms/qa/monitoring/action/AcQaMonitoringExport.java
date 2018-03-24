package server.essp.pms.qa.monitoring.action;

import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.excel.ExcelExporter;
import com.wits.util.Parameter;
import server.essp.common.excelUtil.AbstractExcelAction;
import server.framework.common.BusinessException;
import server.essp.pms.qa.monitoring.logic.QaMonitoringExporter;
import c2s.essp.common.user.DtoUser;
import com.wits.util.ThreadVariant;
import c2s.essp.common.account.IDtoAccount;

public class AcQaMonitoringExport extends AbstractExcelAction {
    public void execute(HttpServletRequest request,
                        HttpServletResponse response, OutputStream os, Parameter param) throws Exception {
        String acntRidStr = (String) param.get("acntRid");
        DtoUser user;
        ThreadVariant thread = ThreadVariant.getInstance();
        user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
        thread.set(DtoUser.SESSION_USER, user);
        IDtoAccount account = (IDtoAccount)request.getSession().getAttribute(IDtoAccount.
                SESSION_KEY);
        Long acntRid = new Long(acntRidStr);
        if (acntRid == null) {
            throw new BusinessException("Please select an account first.");
        }
        String fileName = QaMonitoringExporter.OUT_FILE_PREFIX
                          + "-" + account.getName() + "-" + account.getId()
                          + QaMonitoringExporter.OUT_FILE_POSTFIX;
        QaMonitoringExporter monitoringExporter = new QaMonitoringExporter(fileName);
        Parameter inputData = new Parameter();
        inputData.putAll(param);
        inputData.put(DtoTcKey.ACNT_RID, acntRid);
        inputData.put("accountName",account.getName());
        try {
            monitoringExporter.webExport(response, os, inputData);
        } catch (Exception ex) {
            throw new BusinessException("Error", "Error when Call export() 0f SampleExcelExporter ", ex);
        }
    }

}
