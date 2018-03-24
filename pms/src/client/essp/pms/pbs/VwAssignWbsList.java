package client.essp.pms.pbs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.pbs.DtoAssignWbs;
import client.essp.common.view.VWTreeTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.VWJReal;
import javax.swing.JButton;
import client.essp.common.loginId.VWJLoginId;

public class VwAssignWbsList extends VWTreeTableWorkArea {
    private String actionIdList = "FPbsAssignWbsListAction";

    /**
     * define common data (globe)
     */
    private String inAcntRid;
    private JButton btnLoad;

    public VwAssignWbsList() {
        VWJDate date = new VWJDate();
        VWJText text = new VWJText();
        VWJReal real = new VWJReal();

        Object[][] configs = new Object[][] {
                             {"Name", "name", VMColumnConfig.EDITABLE, null},
                             {"Anticipated Start", "anticipatedStart", VMColumnConfig.UNEDITABLE, date},
                             {"Anticipated Finish", "anticipatedFinish", VMColumnConfig.UNEDITABLE, date},
                             {"Weight", "weight", VMColumnConfig.EDITABLE, real},
                             {"Complete Rate", "completeRate", VMColumnConfig.EDITABLE, real},
                             {"Manager", "manager", VMColumnConfig.UNEDITABLE, new VWJLoginId()},
        };

        try {
            super.jbInit(configs, "name", DtoAssignWbs.class, new AssignWbsNodeViewManager());
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    private void addUICEvent() {
        //捕获事件－－－－
       btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                actionPerformedLoad(event);
            }
        });
    }

    private void actionPerformedLoad(ActionEvent event) {
        resetUI();
    }

    //本workArea不需要外界的parameter
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.inAcntRid = (String) (param.get("inAcntRid"));
    }

    //页面刷新－－－－－
    protected void resetUI() {
        if( this.inAcntRid == null ){
            setButtonVisible(false);
            this.getTreeTable().setRoot(null);
            return;
        }

        setButtonVisible(true);
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj("inAcntRid", inAcntRid);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            ITreeNode root = (ITreeNode) returnInfo.getReturnObj("root");

            this.getTreeTable().setRoot(root);
        }
    }

    private void setButtonVisible(boolean isVisible){
        this.btnLoad.setVisible(isVisible);
    }

    public static void main(String args[]) {
        VWWorkArea workArea = new VwAssignWbsList();
        Parameter param = new Parameter();
        param.put("inAcntRid", "7");
        workArea.setParameter(param);
        workArea.refreshWorkArea();

        VWWorkArea workArea0 = new VWWorkArea();
        workArea0.addTab("Wbs", workArea);
        TestPanel.show(workArea0);
    }

}
