package client.essp.pms.activity.wp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pms.wbs.DtoWbsActivity;
import c2s.essp.pwm.wp.DtoPwWp;
import client.essp.common.view.VWWorkArea;
import client.essp.pms.workerPop.VwAllocateWorker;
import client.essp.pwm.wp.CalculateWPDefaultDate;
import client.essp.pwm.wp.FPW01000PwWp;
import client.framework.common.Global;
import client.framework.view.common.comMSG;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.DataCreateListener;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJPopupEditor;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

public class VwWpListActivity extends VwWpList implements
    IVWPopupEditorEvent {

    DtoWbsActivity dtoActivity = null;
    ITreeNode currentNode = null;
    static final String actionListUpdate = "FPWWPListUpdateAction";
    static final String actionGenerCode = "FPWWPListGenerCodeAction";

    FPW01000PwWp fPW01000PwWp;
    boolean isInsert = false;
    public VwWpListActivity() {
        super();
        fPW01000PwWp = new FPW01000PwWp();

        this.fPW01000PwWp.getGeneralWorkArea().addDataChangedListener(new
            DataChangedListener() {
            public void processDataChanged() {
                getTable().refreshTable();
            }
        });
        this.fPW01000PwWp.getGeneralWorkArea().addDataCreateListener(new
            DataCreateListener() {
            public void processDataCreate() {
                processDataCreateWp();
            }
        });

    }

    public void setParameter(Parameter param) {
        currentNode = (ITreeNode) param.get(DtoKEY.WBSTREE);
//        if(workers != null) {
//            workers.getToolBar().setVisible(false);
//            ((NonDockingToolBarUI) workers.getToolBar().getUI()).setFloating(false, null);
//        }
        DtoWbsActivity dtoActivity = (DtoWbsActivity) currentNode.getDataBean();
        if (dtoActivity != null && dtoActivity.getActivityRid() != null) {
//            workers = null;
            Parameter p = new Parameter();
            this.dtoActivity = dtoActivity;
            p.put("dtoActivity", dtoActivity);
            if (workers != null && dtoActivity != null) {
                Parameter paramWkr = new Parameter();
                paramWkr.put("acntRid", dtoActivity.getAcntRid());
                this.workers.setParameter(paramWkr);
            }
            ((VMWpListModel)this.getTable().getModel()).setDtoActivity(
                dtoActivity);
            super.setParameter(p);
        } else {
            super.setParameter(new Parameter());
            isParameterValid = false;
        }
    }

    protected void actionPerformedUpdate(ActionEvent e) {
        log.info("1:actionPerformedUpdate");
        DtoPwWp dtoPwWp = (DtoPwWp)this.getSelectedData();
        if (dtoPwWp != null) {
            updateData(dtoPwWp);
        }
    }

    protected void actionPerformedAdd(ActionEvent e) {
        DtoPwWp dtoPwWp = new DtoPwWp();
        Date actStart = dtoActivity.getPlannedStart();
        Date actFinish = dtoActivity.getPlannedFinish();
        float reqWkHr = 0;
        ITreeNode node = currentNode;
        while (actStart == null && actFinish == null && node.getParent() != null) {
            node = node.getParent();
            DtoWbsActivity parent = (DtoWbsActivity) node.getDataBean();
            actStart = parent.getPlannedStart();
            actFinish = parent.getPlannedFinish();
        }
        Date[] wpPlanDate = CalculateWPDefaultDate.calculateDefaultPlanDate(
            actStart, actFinish);
        Date wpStart = wpPlanDate[0];
        Date wpFinish = wpPlanDate[1];
        Calendar cStart = Calendar.getInstance();
        Calendar cFinish = Calendar.getInstance();
        cStart.setTime(wpStart);
        cFinish.setTime(wpFinish);
        WorkCalendar wc = WrokCalendarFactory.clientCreate();

        reqWkHr = wc.getWorkHours(cStart, cFinish);
        generateWpCode(dtoPwWp);
        dtoPwWp.setProjectId(dtoActivity.getAcntRid());
        dtoPwWp.setAccountName(dtoActivity.getAccountName());
        dtoPwWp.setActivityCode(dtoActivity.getCode());
        dtoPwWp.setActivityId(dtoActivity.getActivityRid());
        dtoPwWp.setActivityName(this.dtoActivity.getName());
        dtoPwWp.setWpAssignby(Global.userId);
        dtoPwWp.setWpAssignbyName(Global.userName);
        dtoPwWp.setWpAssigndate(Global.todayDate);
        dtoPwWp.setWpCmpltrateType(DtoPwWp.COMPETE_RATE_TYPE_HOUR);
        dtoPwWp.setWpStatus("Assigned");
        dtoPwWp.setWpSequence(0);
        dtoPwWp.setWpPwporwp("0");
        dtoPwWp.setWpPlanStart(wpStart);
        dtoPwWp.setWpPlanFihish(wpFinish);
        dtoPwWp.setWpReqWkhr(new BigDecimal(reqWkHr));
        dtoPwWp.setOp(IDto.OP_INSERT);
        this.getModel().addRow(model.getRowCount() - 1, dtoPwWp);
        isInsert = true;
    }

    private void generateWpCode(DtoPwWp dtoPwWp) {
        Long activityRid = dtoActivity.getActivityRid();
        Long acntRid = dtoActivity.getAcntRid();
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionGenerCode);
        inputInfo.setInputObj("activityRid", activityRid);
        inputInfo.setInputObj("acntRid", acntRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        Long maxWpCodeNum = (Long)returnInfo.getReturnObj("generatedWpCodeNum");
        Long activityCodeRid = (Long)returnInfo.getReturnObj("activityCodeRid");
        String generatedWpCodeNum;
        if (maxWpCodeNum != null) {
            generatedWpCodeNum = maxWpCodeNum.toString();
            int len = generatedWpCodeNum.length();
            for (int i = len; i < DtoPwWp.CODE_NUMBER_LENGTH; i++) {
                generatedWpCodeNum = "0" + generatedWpCodeNum;
            }
        } else {
            generatedWpCodeNum = null;
        }
        String code = "WP-" + dtoActivity.getCode() + "-" + generatedWpCodeNum;
        dtoPwWp.setWpCode(code);
        dtoPwWp.setWpTypeCode(activityCodeRid);
    }

    protected void actionPerformedSave(ActionEvent e) {
        if (validateTableData() == false) {
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionListUpdate);
        inputInfo.setInputObj("wpList", this.wpList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(returnInfo.isError() == false) {
            for (int i = 0; i < wpList.size(); i++) {
                DtoPwWp wp = (DtoPwWp) wpList.get(i);
                wp.setOp(IDto.OP_NOCHANGE);
            }
            resetUI();
        }
        btnUpdate.setEnabled(true);
    }

    private boolean validateTableData() {
        VMWpListModel model = (VMWpListModel) table.getModel();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            DtoPwWp wp = (DtoPwWp) model.getRow(i);
            if (wp.getWpCode() == null || "".equals(wp.getWpCode())) {
                comMSG.dispErrorDialog("Line [" + (i + 1) +
                                       "]: Wp Code is required!");
                return false;
            }
            if (wp.getWpName() == null || "".equals(wp.getWpName())) {
                comMSG.dispErrorDialog("Line [" + (i + 1) +
                                       "]: Wp Name is required!");
                return false;
            }
            if (wp.getWpWorker() == null || "".equals(wp.getWpWorker())) {
                comMSG.dispErrorDialog("Line [" + (i + 1) +
                                       "]: Wp Worker is required!");
                return false;
            }
        }
        return true;
    }

    protected void actionPerformedWorker(ActionEvent e) {
        if(dtoActivity == null || dtoActivity.isReadonly()) {
            return;
        }
        if (workers == null) {
            Container c = this.getMyParentWindow();
            workers = new VwAllocateWorker(c);
            Parameter param = new Parameter();
            param.put("acntRid", dtoActivity.getAcntRid());
            workers.setParameter(param);
            workers.firePropertyChange("resizable", Boolean.parseBoolean("false"), Boolean.parseBoolean("true"));
            this.add(workers, BorderLayout.EAST);
        } else {
            workers.showPopSelect();
        }
    }


    private void updateData(DtoPwWp wp) {
        if (wp != null) {
            Parameter param1 = new Parameter();
            param1.put("inActivityId", getParameterActivityRid());
            param1.put("dtoPwWp", wp);
            log.info("2:activityrid - " + getParameterActivityRid());
            this.fPW01000PwWp.setParameter(param1);
        } else {
            this.fPW01000PwWp.setParameter(new Parameter());
        }

        fPW01000PwWp.refreshWorkArea();
        VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(),
            "Work Package"
            , fPW01000PwWp, VwWpListActivity.this);
        popupEditor.show();
    }

    protected void setButtonVisible() {
        if (dtoActivity == null || dtoActivity.isReadonly()) {
            lblAccountName.setVisible(false);
            this.btnAdd.setVisible(false);
            this.btnDel.setVisible(false);
            this.btnWorker.setVisible(false);
            this.btnSave.setVisible(false);
        } else {
            lblAccountName.setVisible(false);
            this.btnAdd.setVisible(true);
            this.btnDel.setVisible(true);
            this.btnWorker.setVisible(true);
            this.btnSave.setVisible(true);
        }

    }

    public boolean onClickOK(ActionEvent e) {
        if (fPW01000PwWp != null) {
            fPW01000PwWp.saveWorkAreaDirect();
            boolean ok = fPW01000PwWp.isSaveOk();
//            if( ok && isInsert ){
//                DtoPwWp dto = fPW01000PwWp.getGeneralWorkArea().getDto();
//
//                //防止 fPW01000PwWp.getGeneralWorkArea().checkModify() == false
//                //时isSaveOk == true 但实际没有真正新增
//                if( dto.getRid() != null ){
//                    getTable().addRow(dto);
//                    dto.setOp(IDto.OP_NOCHANGE);
//                }
//                isInsert = false;
//            }

            return ok;
        }

        return true;
    }

    protected void processDataCreateWp() {
        DtoPwWp dto = fPW01000PwWp.getGeneralWorkArea().getDto();

        if (dto.getRid() != null) {
            getTable().addRow(dto);
        }
    }

    public boolean onClickCancel(ActionEvent e) {
//        if (fPW01000PwWp != null) {
//            //fPW01000PwWp.saveWorkAreaDirect();
//            boolean ok = fPW01000PwWp.isSaveOk();
//            if( ok && isInsert ){
//                DtoPwWp dto = fPW01000PwWp.getGeneralWorkArea().getDto();
//
//                //防止 fPW01000PwWp.getGeneralWorkArea().checkModify() == false
//                //时isSaveOk == true 但实际没有真正新增
//                if( dto.getRid() != null ){
//                    getTable().addRow(dto);
//                    dto.setOp(IDto.OP_NOCHANGE);
//                }
//                isInsert = false;
//            }
//        }

        return true;

        //isInsert = false;
        //return true;
    }

    protected Long getParameterActivityRid() {
        log.info("getParameterActivityRid - dto is " + dtoActivity);
        if (this.dtoActivity != null) {
            log.info("getParameterActivityRid - activity rid is " + dtoActivity);
            return dtoActivity.getActivityRid();
        } else {
            return null;
        }
    }

    protected boolean getIsReadOnly() {
        if (this.dtoActivity != null) {
            return dtoActivity.isReadonly();
        } else {
            return true;
        }
    }

    /**
     * 取得父窗口句柄
     * @return Frame
     */
    protected Container getMyParentWindow() {
        java.awt.Container c = this.getParent();

        while (c != null) {
            if ((c instanceof java.awt.Frame) || (c instanceof java.awt.Dialog)) {
                return c;
            }

            c = c.getParent();
        }

        return null;
    }


    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwWpListActivity();

        w1.addTab("Wp", workArea);
        TestPanel.show(w1);
    }
}
