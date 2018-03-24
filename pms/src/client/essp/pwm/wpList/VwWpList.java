package client.essp.pwm.wpList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pwm.wp.DtoPwWp;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWAccountLabel;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pwm.wp.FPW01000PwWp;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.VWJPopupEditor;
import c2s.essp.pms.wbs.DtoWbsActivity;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import client.framework.view.vwcomp.VWJLabel;
import client.essp.common.loginId.VWJLoginId;

public class VwWpList extends VWTableWorkArea{
    private String actionIdList = "FPWWpListAction";
    private String actionIdUpdate = "FPWWpUpdateAction";
    private String actionIdDelete = "FPWWpDeleteAction";

    /*parameter*/
    protected Long activityRid;
    protected Long acntRid;
    private DtoWbsActivity activity = null;

    /**
     * define common data (globe)
     */
    protected List wpList = new ArrayList();
    private boolean isSaveOk = true;
    protected boolean isParameterValid = true;
    boolean isReadonly = true;
    JButton btnfilter = null;
    JButton btnUpdate = null;
    JButton btnLoad = null;
    JButton btnDel = null;
    JButton btnAdd = null;
    //private Long acntRid = null;
    VWAccountLabel lblAccountName = null;
    VWJLabel filterLbl = new VWJLabel();
    FPW01000PwWp fpW01000PwWp;

    public VwWpList() {
        VWJText text = new VWJText();
        VWJNumber number = new VWJNumber();
        VWJDate date = new VWJDate();
        VWJReal real = new VWJReal();
        real.setMaxInputDecimalDigit(2);
        real.setMaxInputIntegerDigit(8);

        Object[][] configs = {
                             {"WP Code", "wpCode", VMColumnConfig.UNEDITABLE, text, Boolean.FALSE}
                             ,{"WP Name", "wpName", VMColumnConfig.UNEDITABLE, text, Boolean.FALSE}
                                  , {"Activity Name", "activityName", VMColumnConfig.UNEDITABLE, text, Boolean.FALSE}
                                  , {"%Complete", "wpCmpltrate", VMColumnConfig.UNEDITABLE, number, Boolean.FALSE}
                                  , {"Status", "wpStatus", VMColumnConfig.UNEDITABLE, text, Boolean.FALSE}
                                  , {"Worker", "wpWorker", VMColumnConfig.UNEDITABLE, new VWJLoginId(), Boolean.FALSE}
                                  , {"Activity Id", "activityCode", VMColumnConfig.UNEDITABLE, text, Boolean.TRUE}
                                  , {"Assign By", "wpAssignby", VMColumnConfig.UNEDITABLE, new VWJLoginId(), Boolean.TRUE}
                                  , {"Assign date", "wpAssigndate", VMColumnConfig.UNEDITABLE, date, Boolean.TRUE}
                                  , {"Required Work Hours", "wpReqWkhr", VMColumnConfig.UNEDITABLE, real, Boolean.TRUE}
                                  , {"Worker", "wpWorker", VMColumnConfig.UNEDITABLE, new VWJLoginId(), Boolean.TRUE}
                                  , {"Planned Work Hours", "wpPlanWkhr", VMColumnConfig.UNEDITABLE, real, Boolean.TRUE}
                                  , {"Actual Work Hours", "wpActWkhr", VMColumnConfig.UNEDITABLE, real, Boolean.TRUE}
                                  , {"Planned Start", "wpPlanStart", VMColumnConfig.UNEDITABLE, date, Boolean.TRUE}
                                  , {"Planned Finish", "wpPlanFihish", VMColumnConfig.UNEDITABLE, date, Boolean.TRUE}
                                  , {"Actual Start", "wpActStart", VMColumnConfig.UNEDITABLE, date, Boolean.TRUE}
                                  , {"Actual Finish", "wpActFinish", VMColumnConfig.UNEDITABLE, date, Boolean.TRUE}
                                  , {"Planned Size", "wpSizePlanAndUnit", VMColumnConfig.UNEDITABLE, text, Boolean.TRUE}
                                  , {"Planned Density Rate", "wpDensityratePlanAndUnit", VMColumnConfig.UNEDITABLE, text, Boolean.TRUE}
                                  , {"Planned Defect Rate", "wpDefectratePlanAndUnit", VMColumnConfig.UNEDITABLE, text, Boolean.TRUE}
                                  , {"Status", "wpStatus", VMColumnConfig.UNEDITABLE, text, Boolean.TRUE}

                             };

        try {
            super.jbInit(configs, DtoPwWp.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getTable().setSortable(true);
        addUICEvent();

        setButtonVisible();
    }

    private void addUICEvent() {
        lblAccountName = new VWAccountLabel();
        this.getButtonPanel().add(lblAccountName);

        //filter Label
        filterLbl.setSize(60, 20);
        filterLbl.setText("All WP");
        filterLbl.setToolTipText("Filter by:All WP");
        this.getButtonPanel().add(filterLbl);

        //捕获事件－－－－
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });

        btnUpdate = this.getButtonPanel().addEditButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedUpdate(e);
            }
        });


        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });

        btnfilter = this.getButtonPanel().addButton("filter.gif");
        btnfilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               actionPerformedFilter(e);
            }
        });
        btnfilter.setToolTipText("filter");

        JButton btnDisplay = new TableColumnChooseDisplay(this.getTable(), this).getDisplayButton();
        this.getButtonPanel().addButton(btnDisplay);
    }

    protected void actionPerformedFilter(ActionEvent e) {
        VwWpFilter filter = new VwWpFilter(this.getTable(), this, filterLbl);
        //set hr allocate button
        filter.wpperson.setAcntRid(acntRid);
        filter.wpperson.setTitle("worker of work package");
        VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(),
            "Filter"
            , filter, 2);
        popupEditor.setSize(300,200);
        popupEditor.show();

    }

    protected void actionPerformedAdd(ActionEvent e) {
        DtoPwWp dtoPwWp = new DtoPwWp();
        this.getTable().addRow(dtoPwWp);
    }

    protected void actionPerformedDel(ActionEvent e) {
        DtoPwWp dto = (DtoPwWp)this.getSelectedData();
        if (dto == null) {
            return;
        }

        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            if (dto.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj("dto", dto);
                inputInfo.setActionId(this.actionIdDelete);

                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {
                    getTable().deleteRow();
                }
            } else {
                getTable().deleteRow();
            }
        }
    }

    protected void actionPerformedUpdate(ActionEvent e) {
    }

    private boolean checkModified() {
        if( wpList == null ){
            return false;
        }

        for (Iterator it = this.wpList.iterator();
                           it.hasNext(); ) {
            DtoPwWp dtoPwWp = (DtoPwWp) it.next();

            if (dtoPwWp.isChanged() == true) {
                return true;
            }
        }

        return false;
    }

    protected void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    private boolean validateData() {
        return true;
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj("wpList", wpList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            wpList = (List) returnInfo.getReturnObj("wpList");

            this.getTable().setRows(wpList);
            return true;
        }else{
            return false;
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.activityRid = (Long) (param.get("activityRid"));
        this.activity = (DtoWbsActivity)(param.get("dtoActivity"));
        Boolean readonly = (Boolean)param.get("isReadonly");
        if( readonly == null ){
            readonly = Boolean.FALSE;
        }
        this.isReadonly = readonly.booleanValue();
        this.isParameterValid = true;
    }

    //页面刷新－－－－－
    protected void resetUI() {
        setButtonVisible();
        if( this.isParameterValid == false ){
            wpList = new ArrayList();
            getTable().setRows(wpList);
            return;
        }

        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);

        inputInfo.setInputObj("activityRid", this.getParameterActivityRid());
        inputInfo.setInputObj("Activity", this.activity);
        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            wpList = (List) returnInfo.getReturnObj("wpList");
            String acntCode = (String) returnInfo.getReturnObj("acntCode");
            String acntName = (String) returnInfo.getReturnObj("acntName");
            lblAccountName.setModel(lblAccountName.createtDefaultModel(acntCode, acntName));
            if(wpList.size() > 0) {
                DtoPwWp dto = (DtoPwWp) wpList.get(0);
                acntRid = dto.getProjectId();
            }
            getTable().setRows(wpList);
        }
    }

    protected void setButtonVisible() {
        if (isParameterValid == true &&
            this.getIsReadOnly() == false) {
            this.btnAdd.setVisible(true);
            this.btnDel.setVisible(true);
            this.btnUpdate.setVisible(false);
        } else {
            this.btnAdd.setVisible(false);
            this.btnDel.setVisible(false);
            this.btnUpdate.setVisible(false);
        }
    }

    public void saveWorkArea() {
        if (checkModified()) {
            this.isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the these wp?") == true) {
                if (validateData() == true) {
                    isSaveOk = saveData();
                }
            } else {
                isSaveOk = true;
            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean isSaveOk(){
        return this.isSaveOk;
    }

//    public Long getAccountRid(){
//        return this.acntRid;
//    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwWpList();

        w1.addTab("Wp", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put("isReadonly", Boolean.FALSE);
        workArea.setParameter(param);
        workArea.refreshWorkArea();

    }

    //为使本workarea能在两个位置（）用，所以这里做一个补丁
    protected Long getParameterActivityRid(){
        return this.activityRid;
    }

    protected boolean getIsReadOnly(){
        return this.isReadonly;
    }
}
