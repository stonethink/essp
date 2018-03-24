package client.essp.pwm.workbench.workitem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pwm.workbench.DtoKey;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTreeTableModel;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJTreeTable;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import c2s.essp.pwm.workbench.DtoDailyReport;

public class VwDailyReportList extends VWGeneralWorkArea {
    //parameter
    Date selectedDate = null;

    VWJTreeTable tableDlrpt = null;
    VMTreeTableModel modelDlrpt = null;
    VWWorkArea workAreaDlrpt = null;

    public VwDailyReportList() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    private void jbInit()throws Exception{
        VWJText text = new VWJText();
        VWJReal real = new VWJReal();

        //daily report
        Object[][] configs = new Object[][] {
                             {"Name", "wkitemOwner", VMColumnConfig.EDITABLE, null}
                             , {"Report Hour", "wkitemWkhours", VMColumnConfig.UNEDITABLE, real}
                             , {"Work Title", "wkitemName", VMColumnConfig.UNEDITABLE, text}
        };
        this.modelDlrpt = new VMTreeTableModel(null, configs, "wkitemOwner");
        modelDlrpt.setDtoType(DtoDailyReport.class);
        modelDlrpt.setTreeColumnName("wkitemOwner");
        tableDlrpt = new VWJTreeTable(modelDlrpt);

        workAreaDlrpt = new VWWorkArea();
        workAreaDlrpt.add(tableDlrpt.getScrollPane());

        this.addTab("Daily Report", workAreaDlrpt);
    }

    private void addUICEvent() {
        //捕获事件－－－－
        this.workAreaDlrpt.getButtonPanel().addLoadButton(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                resetUI();
            }
        });
    }

    public void setParameter(Parameter p){
        super.setParameter(p);
        selectedDate = (Date)p.get(DtoKey.SELECTED_DATE);
    }

    //页面刷新－－－－－
    protected void resetUI() {
        if( selectedDate == null ){
            tableDlrpt.setRoot(null);
            return;
        }

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(DtoKey.ACTION_ALL_DAILY_REPORT_LIST);
        inputInfo.setFunId(DtoKey.SELECT_ALL_DLRPT);
        inputInfo.setInputObj(DtoKey.SELECTED_DATE, this.selectedDate);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            ITreeNode root  = (ITreeNode) returnInfo.getReturnObj(DtoKey.ALL_DLRPT);
            tableDlrpt.setRoot(root);
        }
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VwWorkItemList workArea = new VwWorkItemList();

        w1.addTab("Dialy Report", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        Calendar c = Calendar.getInstance();
        c.set(2005,9,10);
        param.put("selectedDate", c.getTime());
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
