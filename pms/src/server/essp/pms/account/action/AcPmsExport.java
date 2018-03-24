package server.essp.pms.account.action;

import server.essp.common.excelUtil.AbstractExcelAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import com.wits.util.Parameter;
import server.framework.common.BusinessException;
import server.essp.pms.account.logic.AccountBaseLineExport;
import com.wits.excel.ExcelExporter;
import c2s.essp.tc.weeklyreport.DtoTcKey;

public class AcPmsExport extends AbstractExcelAction {
   public void execute(HttpServletRequest request,
                       HttpServletResponse response, OutputStream os, Parameter param) throws Exception {
       //根据选择的Account,导出Template_BaseLine的报表
       String acntRidStr = (String) param.get("acntRid");
       Long acntRid  = new Long(acntRidStr);
       if (acntRid == null) {
           throw new BusinessException("Please select an account first.");
       }

       ExcelExporter excel = new AccountBaseLineExport();

       Parameter inputData = new Parameter();
       inputData.put(DtoTcKey.ACNT_RID, acntRid);

       try{
           excel.webExport(response,os,inputData);
       }catch(Exception ex){
           ex.printStackTrace();
       }
   }

}
