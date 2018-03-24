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

//        //�ȱ���template���������ɵ�template RID����dto
//        LgAccount logic = new LgAccount();
//        logic.add(dto);
//
//        Long accountrid = dto.getRid();

//        //��ȡPCB������
//        LgPcb lgPcb =new LgPcb();
//        List oldPcbItemList = lgPcb.listPcbItemByAcntRid(old_acntrid);
//        //���ԭ����account����PCB�����PCB���Ƶ���template��
//        if(oldPcbItemList != null){
//            for (int i = 0; i < oldPcbItemList.size(); i++) {
//                DtoPcbItem dtoPcbItem = (DtoPcbItem) oldPcbItemList.get(i);
//                Long oldItemRid = dtoPcbItem.getRid();
//                dtoPcbItem.setAcntRid(accountrid);
//                //����pcb item,���õ����ɵ�item rid
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
            //��ȡPbs���ڵ㲢����
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
            //��ȡWbs���ڵ㲢����
//            ITreeNode rootWbs =null;
//            LgWbs lgw = new LgWbs();



            /**����wbs
               * ��������ݰ�����
               *   wbs/activity
               *   wbs������checkpoint,pbs,code��Process(checkpoint,guideLine,QA)
               *   activity������status,Predecessors,Successors,pbs,code,Quaility,guideLine,QA,milestone
               */

//              lgw.saveWbsActivityDetail(accountrid,old_acntrid);

        }catch(Exception ex){
            ex.printStackTrace();
        }

        returnInfo.setReturnObj("acntrid",old_acntrid);
    }
}
