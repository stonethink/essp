package server.essp.timecard.timecard.logic;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoUtil;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import c2s.essp.timecard.timecard.DtoTcTimecard;
import c2s.essp.timecard.timecard.DtoTcTm;
import com.wits.util.Parameter;
import server.framework.common.BusinessException;
import server.framework.logic.AbstractBusinessLogic;

public class ExcelLogic extends AbstractBusinessLogic {
    private String OutPutFileName = "d:/test.xls";

    public ExcelLogic() {
    }
    public void executeLogic(Parameter param)  throws BusinessException  {
        TransactionData transData = (TransactionData) param.get("TransactionData");
        String funId = transData.getInputInfo().getFunId();

        if (funId == null || funId.equals("")) {
            System.out.println("Can't find funid!");
        } else if (funId.equals("getTmCdRpList")) {
            getTmCdRpList(transData);
            getOutPutExcel(transData);
        }
        param.put("OutPutFileName",OutPutFileName);
    }

    public void getOutPutExcel(TransactionData transData) throws BusinessException{
        Excel ex = new Excel();
        //�趨ģ���ļ�
        String inTempleteFileName = (String) transData.getInputInfo().getInputObj("inTempleteFileName");
        ex.setTempleteFileName(inTempleteFileName);
        //�趨����ļ�
        ex.setOutPutFileName(OutPutFileName);
        //�趨����excel�б���ļ�
        Object[][] intbSConfig = (Object[][]) transData.getInputInfo().getInputObj("intbSConfig");
        ex.setTblConfig(intbSConfig);

        List data = new ArrayList();
        ReturnInfo returnInfo = transData.getReturnInfo();
        if (!returnInfo.isError()) {
            data = ( (List) returnInfo.getReturnObj("tcrList"));
        }
        //�������ݵ�excel�ļ�
        boolean resultFlag = ex.getForExcel(data);

    }

    public void getTmCdRpList(TransactionData transData) throws BusinessException  {
            String inStartTime = (String) transData.getInputInfo().getInputObj("inStartTime");
            String inFinishTime = (String) transData.getInputInfo().getInputObj("inFinishTime");
            List tcrList = new ArrayList();
            try {
                List tcrListDB = getTCProjectList(inStartTime, inFinishTime);
                for(int i=0;i<tcrListDB.size();i++){
                    DtoTcTm tctm = (DtoTcTm)tcrListDB.get(i);
                    String inAccid = tctm.getTmcProjId().toString();
                    List tcprListDB = getTCPersonList(inStartTime, inFinishTime, inAccid);
                    for(int j=0;j<tcprListDB.size();j++){
                        DtoTcTimecard tctmcd = (DtoTcTimecard)tcprListDB.get(j);
                        tcrList.add(tctmcd);
                    }

                    DtoTcTimecard tctmcdpj = new DtoTcTimecard();
                    try {
                        DtoUtil.copyProperties(tctmcdpj, tctm);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    tctmcdpj.setTmcEmpName(tctm.getAccountCode());
                    tctmcdpj.setTmcEmpPositionType(tctm.getAccountName());
                    tcrList.add(tctmcdpj);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("select getCurrWPList error!!");
                transData.getReturnInfo().setError(e);
                throw new BusinessException(e);
            }
            transData.getReturnInfo().setReturnObj("tcrList", tcrList);
            transData.getReturnInfo().setError(false);
    }

    public List getTCPersonList(String inStartTime, String inFinishTime, String inAccid) throws BusinessException  {
        String sql = "select TCR.* "
            + " from TC_TIMECARD TCR"
            + " where ( TO_CHAR(TCR.TMC_WEEKLY_START,'YYYY-MM-DD')<='" + inStartTime + "' and TO_CHAR(TCR.TMC_WEEKLY_FINISH,'YYYY-MM-DD')>='" + inFinishTime + "' and TCR.TMC_PROJ_ID = " + inAccid + " )";

        List tcrList = null;
        try {
            tcrList = this.getDbAccessor().executeQueryToList(sql, DtoTcTimecard.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getPlanList error!!");
            throw new BusinessException(e);
        }
        return tcrList;
    }

    public List getTCProjectList(String inStartTime, String inFinishTime) throws BusinessException  {
        String sql = "select TCR.*, ACC.ACCOUNT_CODE, ACC.ACCOUNT_NAME "
            + " from TC_TMS TCR, ESSP_SYS_ACCOUNT_T ACC"
            + " where ( TCR.TMC_PROJ_ID = ACC.ID and TO_CHAR(TCR.TMC_WEEKLY_START,'YYYY-MM-DD')<='" + inStartTime + "' and TO_CHAR(TCR.TMC_WEEKLY_FINISH,'YYYY-MM-DD')>='" + inFinishTime + "' )";

        List tcrList = null;
        try {
            tcrList = this.getDbAccessor().executeQueryToList(sql, DtoTcTm.class);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getPlanList error!!");
            throw new BusinessException(e);
        }
        return tcrList;
    }


}
