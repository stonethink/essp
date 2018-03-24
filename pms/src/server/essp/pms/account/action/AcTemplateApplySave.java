package server.essp.pms.account.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.dto.TransactionData;
import server.essp.framework.action.AbstractESSPAction;
import server.essp.pms.account.logic.LgTemplate;
import server.essp.pms.account.logic.LgTemplateApply;
import server.framework.common.BusinessException;

public class AcTemplateApplySave extends AbstractESSPAction {
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException {
        InputInfo inputInfo = transData.getInputInfo();
         ReturnInfo returnInfo = transData.getReturnInfo();
         //应用的模板的rid
         Long templaterid = (Long) inputInfo.getInputObj("Account");
         //目标项目的rid
         Long acntRid= (Long) inputInfo.getInputObj("acntRid");
         //裁剪说明
         String tailorDescription = (String)inputInfo.getInputObj("TailorDescription");
         if(acntRid == null){
             throw new BusinessException("acntRid is null!");
         }
         //删除原account下面的pcb,pms,wbs/activity及其关联信息
         LgTemplateApply lg=new LgTemplateApply();
         lg.deletePmsAcntSeq(acntRid);
         lg.deleteAccountPcb(acntRid);

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
          lg.updTemplateRidAndTailor(templaterid,tailorDescription,acntRid);

          //复制template的pcb,pms,wbs/activity及其关联信息到account下
          LgTemplate lgTemplate = new LgTemplate();
          lgTemplate.copyPCB(templaterid,acntRid);
          lgTemplate.copyPBSTree(templaterid,acntRid);
          lgTemplate.copyWbsActivityTree(templaterid,acntRid);

        returnInfo.setReturnObj("acntrid", acntRid);
    }
}
