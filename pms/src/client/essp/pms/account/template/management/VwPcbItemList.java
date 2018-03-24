package client.essp.pms.account.template.management;

import client.essp.common.view.VWTableWorkArea;
import java.util.List;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;
import c2s.essp.pms.account.pcb.DtoPcbItem;
import com.wits.util.Parameter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import client.framework.view.common.comMSG;
import client.framework.view.event.RowSelectedListener;
import c2s.dto.InputInfo;
import java.util.ArrayList;
import c2s.dto.ReturnInfo;

public class VwPcbItemList extends VWTableWorkArea {
    private static final String actionIdList = "FAcntTemplatePcbItemListAction";
    private List pcbItemList;
    private Parameter para ;
    private boolean isRefresh = true;

    public VwPcbItemList() {
        try{
            JbInit();
            addUIEvent();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void JbInit(){
        para = new Parameter();
        Object[][] configs = new Object[][] { {"Name", "name",
                             VMColumnConfig.UNEDITABLE, new VWJText()}
                             , {"Type", "type", VMColumnConfig.UNEDITABLE,
                             new VWJText()}
                             , {"Remark", "remark", VMColumnConfig.UNEDITABLE,
                                 new VWJText()},
                             {"Seq", "seq", VMColumnConfig.UNEDITABLE,
                             new VWJText()}};
        try{
            super.jbInit(configs,DtoPcbItem.class);
            getTable().setSortable(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addUIEvent(){
//        this.getButtonPanel().addAddButton(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
//                actionPerformedAdd();
//            }
//        });
//        this.getButtonPanel().addDelButton(new ActionListener(){
//            public void actionPerformed(ActionEvent e){
//                actionPerformedDelete();
//            }
//        });
//        this.getTable().addRowSelectedListener(new RowSelectedListener(){
//            public void processRowSelected(){
//                processRowSelectedEvent();
//            }
//        });
    }
    public void actionPerformedAdd(){
        comMSG.dispMessageDialog("Ok,add ");
    }
    public void actionPerformedDelete(){
        comMSG.dispMessageDialog("Ok,delete");
    }
//    public void processRowSelectedEvent(){
//
//    }
    public void setParameter(Parameter para){
        super.setParameter(para);
        if(this.para == null){
            this.para = new Parameter();
        }
        this.para =para;
    }
    public void resetUI(){
        try{
            Long acntRid = (Long)para.get("acntrid");
            if(acntRid == null){
                pcbItemList = new ArrayList();
                getTable().setRows(pcbItemList);
                return;
            }

            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionIdList);
            inputInfo.setInputObj("acntRid",acntRid);

            ReturnInfo returnInfo = accessData(inputInfo);

            if(returnInfo.isError() == false){
                pcbItemList = (List)returnInfo.getReturnObj("pcbItemList");
                this.getTable().setRows(pcbItemList);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
