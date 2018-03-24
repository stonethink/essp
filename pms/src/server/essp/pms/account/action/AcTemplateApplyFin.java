package server.essp.pms.account.action;

import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import server.essp.framework.action.AbstractESSPAction;
import c2s.dto.ReturnInfo;
import c2s.dto.InputInfo;
import server.essp.pms.account.logic.LgTemplateApply;
import java.util.List;

public class AcTemplateApplyFin extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                          HttpServletResponse response, TransactionData data) throws
       BusinessException {
       InputInfo inputInfo = data.getInputInfo();
       ReturnInfo returnInfo = data.getReturnInfo();
       Long acntRid = (Long) inputInfo.getInputObj("acntRid");
        Long apprAcntRid = (Long) inputInfo.getInputObj("apprAcntRid");
       LgTemplateApply lg=new LgTemplateApply();
          List wpIdList=lg.getWpRid(acntRid);
           for (int i = 0; i < wpIdList.size(); i++) {
               Long wpId=(Long)wpIdList.get(i);
               List wpchkIdList=lg.getWpchkId(wpId);
               for(int j=0;j<wpchkIdList.size();j++){
                   Long  wpchkId=(Long)wpchkIdList.get(j);
                   lg.deleteWpchklogs(wpchkId);
               }
               lg.deleteWpchk(wpId);
           }
           lg.deleteWp(acntRid);
           lg.deleteActivity(acntRid);
           lg.deleteWbs(acntRid);
           lg.deletePbs(acntRid);
           lg.updTemplateRidAndTailor(apprAcntRid,"",acntRid);


   }
}
