package client.essp.pms.account.template.management;

import java.util.List;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJReal;
import client.essp.common.view.VWTableWorkArea;
import c2s.essp.pms.account.pcb.DtoPcbParameter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import client.framework.view.common.comMSG;
import com.wits.util.Parameter;
import java.util.ArrayList;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;

public class VwPcbParameterList extends VWTableWorkArea{
    private static final String actionIdList = "FAcntTemplatePcbParameterListAction";
    private List parameterList;
    private VWJReal RealType = new VWJReal();
    private Parameter para = new Parameter();
    private boolean isRefresh = true;
    public VwPcbParameterList(){
        try{
            JbInit();
           // addUIEvent();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void JbInit(){
        RealType.setMaxInputIntegerDigit(8);
        RealType.setMaxInputDecimalDigit(2);
        Object[][] configs = new Object[][]{
                             {"Name","name",VMColumnConfig.UNEDITABLE,new VWJText()},
                             {"Unit","unit",VMColumnConfig.UNEDITABLE,new VWJText()},
                             {"UCL","ucl",VMColumnConfig.UNEDITABLE,RealType},
                             {"Mean","mean",VMColumnConfig.UNEDITABLE,RealType},
                             {"LCL","lcl",VMColumnConfig.UNEDITABLE,RealType},
                             {"Plan","plan",VMColumnConfig.UNEDITABLE,RealType},
                             {"Remark","remark",VMColumnConfig.UNEDITABLE,new VWJText()},
                             {"Seq", "seq", VMColumnConfig.UNEDITABLE, new VWJText()}

        };
        try{
            super.jbInit(configs,DtoPcbParameter.class);
            getTable().setSortable(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addUIEvent(){
        this.getButtonPanel().addAddButton(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                actionPerformedAdd();
            }
        });
        this.getButtonPanel().addDelButton(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                actionPerformedDelete();
            }
        });
    }

    public void actionPerformedAdd(){
        comMSG.dispMessageDialog("Ok,add parameter");
    }

    public void actionPerformedDelete(){
        comMSG.dispMessageDialog("Ok,deletete parameter");
    }

    public void setParameter(Parameter para){
        super.setParameter(para);
        if(this.para == null){
            this.para = new Parameter();
        }
        this.para = para;
    }

    public void resetUI(){
        try{
            Long acntRid =(Long) para.get("acntrid");
            Long itemRid = (Long) para.get("itemRid");
            if(itemRid == null){
                parameterList = new ArrayList();
                getTable().setRows(parameterList);
                return;
            }
            //根据acntRid和itemRid查询其对应的parameter list
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(actionIdList);
            inputInfo.setInputObj("acntRid",acntRid);
            inputInfo.setInputObj("itemRid",itemRid);

            ReturnInfo returnInfo = accessData(inputInfo);
            if(returnInfo.isError() == false){
               parameterList =(List) returnInfo.getReturnObj("pcbParameterList") ;
               this.getTable().setRows(parameterList);
           }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
