package server.essp.pms.account.action;

import server.essp.framework.action.AbstractESSPAction;
import server.framework.common.BusinessException;
import javax.servlet.http.HttpServletRequest;
import c2s.dto.TransactionData;
import javax.servlet.http.HttpServletResponse;
import c2s.essp.pms.account.DtoPmsAcnt;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.dto.InputInfo;
import db.essp.pms.Account;
import server.essp.pms.account.logic.LgAccountBase;
import server.essp.pms.wbs.logic.LgWbs;
import server.essp.pms.pbs.logic.LgPmsPbsList;
import c2s.dto.ITreeNode;
import c2s.essp.pms.pbs.DtoPmsPbs;
import server.essp.pms.pbs.logic.LgPmsPbs;
import c2s.dto.IDto;
import server.essp.pms.account.logic.LgAccount;
import server.essp.pms.account.pcb.logic.LgPcb;
import java.util.List;
import c2s.essp.pms.account.pcb.DtoPcbItem;
import c2s.essp.pms.account.pcb.DtoPcbParameter;
import server.essp.pms.account.logic.LgTemplate;


public class AcTemplateAdd extends AbstractESSPAction{
    public void executeAct(HttpServletRequest request,
                           HttpServletResponse response,
                           TransactionData transData) throws BusinessException{
        InputInfo inputInfo = transData.getInputInfo();
        ReturnInfo returnInfo = transData.getReturnInfo();

        Long old_acntrid = (Long) inputInfo.getInputObj("Account");
        DtoPmsAcnt dto = (DtoPmsAcnt) inputInfo.getInputObj(DtoAcntKEY.DTO);

        LgTemplate lgTemplate = new LgTemplate();
        lgTemplate.createTemplate(old_acntrid,dto);

//        //先保存template，并把生成的template RID存入dto
//        LgAccount logic = new LgAccount();
//        logic.add(dto);
//
//        Long accountrid = dto.getRid();

//        //获取PCB并保存
//        LgPcb lgPcb =new LgPcb();
//        List oldPcbItemList = lgPcb.listPcbItemByAcntRid(old_acntrid);
//        //如果原来的account下有PCB，则把PCB复制到新template下
//        if(oldPcbItemList != null){
//            for (int i = 0; i < oldPcbItemList.size(); i++) {
//                DtoPcbItem dtoPcbItem = (DtoPcbItem) oldPcbItemList.get(i);
//                Long oldItemRid = dtoPcbItem.getRid();
//                dtoPcbItem.setAcntRid(accountrid);
//                //保存pcb item,并得到生成的item rid
//                lgPcb.addPcbItem(dtoPcbItem);
//                Long newItemRid = dtoPcbItem.getRid();
//
//                List PcbParameterList = lgPcb.listPcbParameterByItemRid(oldItemRid);
//                lgPcb.addPcbParameter(PcbParameterList, newItemRid);
//
//            }
//        }

//        ITreeNode rootpbs = (ITreeNode)inputInfo.getInputObj("RootPbs");
//        ITreeNode rootwbs = (ITreeNode)inputInfo.getInputObj("RootWbs");

        try{
            //获取Pbs根节点并保存
//            LgPmsPbsList lgppl = new LgPmsPbsList(old_acntrid);
//            ITreeNode root = null;
//            if(old_acntrid==null){
//                root = rootpbs;
//            }else{
//                root = (ITreeNode)lgppl.Pmslist();
//            }
//            if(root !=null){
//                DtoPmsPbs dtopbs = (DtoPmsPbs)root.getDataBean();
//                dtopbs.setAcntRid(accountrid);
//                dtopbs.setPbsManager("");
//                dtopbs.setActualFinish(null);
//                dtopbs.setPlannedFinish(null);
//                LgPmsPbs lgpp = new LgPmsPbs(accountrid);
//                Long pbsrid = (Long) lgpp.addDate(dtopbs);
//                lgppl.SavePbs(root, accountrid, pbsrid);
//            }
            //获取Wbs根节点并保存
//            ITreeNode rootWbs =null;
//            LgWbs lgw = new LgWbs();



            /**保存wbs
               * 保存的内容包括：
               *   wbs/activity
               *   wbs关联的checkpoint,pbs,code，Process(checkpoint,guideLine,QA)
               *   activity关联的status,Predecessors,Successors,pbs,code,Quaility,guideLine,QA,milestone
               */

//              lgw.saveWbsActivityDetail(accountrid,old_acntrid);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        returnInfo.setReturnObj("acntrid",old_acntrid);
    }
}
