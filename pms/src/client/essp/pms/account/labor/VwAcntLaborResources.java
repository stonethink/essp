package client.essp.pms.account.labor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoBase;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntLoaborRes;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.common.Global;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.ProcessVariant;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;

import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJReal;



/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwAcntLaborResources extends VWTableWorkArea implements
    IVWPopupEditorEvent {
    /**
     * Labor Resource 的Action Id
     */
    public static final String ACTIONID_RES_LIST =
        "FAcntLaborResourceListAction";
    public static final String ACTIONID_RES_SAVE =
        "FAcntLaborResourceSaveAction";
    public static final String ACTIONID_RES_ADDPRE =
        "FAcntLaborResourceAddPreAction";
    public static final String ACTIONID_RES_DELETE =
        "FAcntLaborResourceDeleteAction";

    private VWJDate inputDate = new VWJDate();
    private VWJText inputText = new VWJText();
    private VWJComboBox inputResStatus = new VWJComboBox();

    private VwLaborResourcePopWorkArea popWindow;
    private List laborResourceList;
    private DtoPmsAcnt pmsAcc;
    private Vector jobCodeOptions;

    private boolean isParameterValid = true;
    private boolean isSaveOk = true;
    public VwAcntLaborResources() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(700, 260));
        /////////////////////////////
        // 添加按钮
        this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });

        this.getButtonPanel().addEditButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedEdit(e);
            }
        });

        this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });

        this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
        inputDate.setCanSelect(false);
        inputText.setMaxByteLength(1000);
        inputResStatus.setVMComboBox(
        VMComboBox.toVMComboBox(DtoAcntLoaborRes.RESOURCE_STATUS_NAME,
                                DtoAcntLoaborRes.RESOURCE_STATUS_VALUE));
        //设置标题栏位
        VWJReal inputHours = new VWJReal();
        inputHours.setMaxInputDecimalDigit(2);
        inputHours.setMaxInputIntegerDigit(8);
        Object[][] configs = new Object[][] {
                             {"Login Id", "loginId",VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Name", "empName", VMColumnConfig.UNEDITABLE,new VWJText()},
                             {"Job Code", "jobCode", VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Role", "roleName", VMColumnConfig.EDITABLE,new VWJText()},
                             {"Plan Start", "planStart",VMColumnConfig.UNEDITABLE, inputDate},
                             {"Plan Finish", "planFinish",VMColumnConfig.UNEDITABLE, inputDate},
                             {"Plan Work Time", "planWorkTime",VMColumnConfig.UNEDITABLE, inputHours},
                             {"Job Description", "jobDescription",VMColumnConfig.EDITABLE,inputText},
                             {"Status", "resStatus",VMColumnConfig.EDITABLE,inputResStatus},
                             {"Remark", "remark", VMColumnConfig.EDITABLE, inputText}
        };

        try {
            super.jbInit(configs, DtoAcntLoaborRes.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取Job Code option
     */
    private void addPre() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_RES_ADDPRE);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            jobCodeOptions = (Vector) returnInfo.getReturnObj("jobCodeOptions");
        }
    }
    /**
     * 处理新增事件
     * @param e ActionEvent
     */
    public void actionPerformedAdd(ActionEvent e) {
        if(pmsAcc.getPlannedStart() == null || pmsAcc.getPlannedFinish() == null) {
            comMSG.dispErrorDialog("Please fill plannedStart and plannedFinish of account!");
            return;
        }
        addPre();
        popWindow = new VwLaborResourcePopWorkArea();
        Parameter param = new Parameter();
        param.put("dtoAccount", pmsAcc);
        param.put("jobCodeOptions", jobCodeOptions);
        popWindow.setParameter(param);
        popWindow.refreshWorkArea();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Labor Resource",
                                                popWindow, this);
        pop.show();

    }


    /**
     * 处理删除事件
     * @param e ActionEvent
     */
    public void actionPerformedDel(ActionEvent e) {
        int f = comMSG.dispConfirmDialog("Confirm to delete selected record!");
        if( f == Constant.OK ){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.ACTIONID_RES_DELETE);
            inputInfo.setInputObj("dto", this.getTable().getSelectedData());
            ReturnInfo returnInfo = accessData(inputInfo);
            if(!returnInfo.isError()){
                ProcessVariant.fireDataChange("labor");
                resetUI();
            }
        }
    }


    /**
     * 处理保存事件
     * @param e ActionEvent
     */
    public void actionPerformedSave(ActionEvent e) {
        if(checkModified() && validateData())
           saveData();
    }
    private boolean saveData() {
        if (laborResourceList == null) {
            return true;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_RES_SAVE);
        inputInfo.setInputObj("laborResourceList", laborResourceList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            resetUI();
           return true ;
        }
        return false;
    }
    private boolean checkModified() {
        if(laborResourceList == null || laborResourceList.size() <= 0)
            return false;
        for (Iterator it = this.laborResourceList.iterator();
                           it.hasNext(); ) {
            DtoBase dto = (DtoBase) it.next();
            if (dto.isChanged()) {
                return true;
            }
        }
        return false;
    }
    private boolean validateData(){
        boolean isValidate = true;
        if(laborResourceList == null || laborResourceList.size() <= 0)
            return isValidate;
        StringBuffer msg = new StringBuffer();
        for (int i = 0;i < laborResourceList.size() ;i ++) {
            DtoAcntLoaborRes dto = (DtoAcntLoaborRes) laborResourceList.get(i);
            Date begin = dto.getPlanStart();
            Date end = dto.getPlanFinish();
            if(begin == null || end == null){
                msg.append("Line["+i+"]:Please begin date and end date!\n");
                isValidate = false;
            }else if (begin.after(end)) {
                msg.append("Line["+i+"]:End date must be larger than begin date!\n");
                isValidate = false;
            }
            //验证外部员工,status 必须是Not-Ready
            if(DtoAcntLoaborRes.LOGINID_STATUS_OUT.equals(dto.getLoginidStatus()) &&
               !DtoAcntLoaborRes.RESOURCE_STATUS_NOT_READY.equals(dto.getResStatus())
                ){
                msg.append("Line["+i+"]:The resource status must be \"Not Ready\"!\n");
                isValidate = false;
            }
        }
        if(!isValidate)
            comMSG.dispErrorDialog(msg.toString());
        return isValidate;
    }
    public void saveWorkArea() {
        if (checkModified()){
            isSaveOk = false;
            if(confirmSaveWorkArea()){
               if(validateData()){
                   isSaveOk = saveData();
               }
            }else{
                isSaveOk = true;
            }
        }else{
            isSaveOk = true;
        }
    }
    public boolean isSaveOk(){
        return this.isSaveOk;
    }
    /**
     * 处理刷新事件
     * @param e ActionEvent
     */
    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }
    /**
     * 进入修改界面事件
     * @param e ActionEvent
     */
    public void actionPerformedEdit(ActionEvent e) {
        DtoAcntLoaborRes primary = (DtoAcntLoaborRes)this.getTable().
                                   getSelectedData();
        addPre();
        DtoAcntLoaborRes copy = (DtoAcntLoaborRes) primary.clone();
        popWindow = new VwLaborResourcePopWorkArea();
        Parameter param = new Parameter();
        param.put("dtoAccount", pmsAcc);
        param.put("dto", copy);
        param.put("jobCodeOptions", jobCodeOptions);
        popWindow.setParameter(param);
        popWindow.refreshWorkArea();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Labor Resource",
                                                popWindow, this);
        pop.show();

    }

    public void setParameter(Parameter param) {
        this.pmsAcc = (DtoPmsAcnt) param.get("dtoAccount");
        if(pmsAcc == null){
            pmsAcc = new DtoPmsAcnt();
            isParameterValid = false;
        }else{
            isParameterValid = true;
        }
        super.setParameter(param);
    }

    private void setButtonVisible(){
        this.getButtonPanel().setVisible(isParameterValid);
        this.getTable().setEnabled(isParameterValid);
    }

    public void resetUI() {
        setButtonVisible();
        if(pmsAcc.getRid() == null){
            getTable().setRows(new ArrayList());
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_RES_LIST);
        inputInfo.setInputObj("acntRid", this.pmsAcc.getRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            laborResourceList = (List) returnInfo.getReturnObj(
                "laborResourceList");
            getTable().setRows(laborResourceList);
        }
    }


    /**
     * 弹出窗口的OK Button事件
     * @param e ActionEvent
     * @return boolean
     */
    public boolean onClickOK(ActionEvent e) {
        boolean isSuccess = popWindow.saveOrUpdate();
        if(isSuccess){
            resetUI();
            return true;
        }else
            return false;
    }

    /**
     * 弹出窗口的Cancel Button事件
     * @param e ActionEvent
     * @return boolean
     */
    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public static void main(String[] args) {
        VWWorkArea w1 = new VWWorkArea();
        VwAcntLaborResources resource = new VwAcntLaborResources();
        DtoPmsAcnt pmsAcc = new DtoPmsAcnt();
        Long lAccRid = new Long(1);
        pmsAcc.setRid(lAccRid);
        pmsAcc.setActualStart(Global.todayDate);
        pmsAcc.setActualFinish(Global.todayDate);
        w1.addTab("Labor Resource", resource);
        Parameter param = new Parameter();
        param.put("dtoAccount", pmsAcc);
        resource.setParameter(param);
        TestPanel.show(w1);
        resource.refreshWorkArea();
//        resource.resetUI();
    }

}
