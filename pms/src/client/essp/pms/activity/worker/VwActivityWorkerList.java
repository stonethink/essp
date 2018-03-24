package client.essp.pms.activity.worker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoBase;
import c2s.dto.DtoComboItem;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.wbs.DtoActivityWorker;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoKEY;
import client.framework.common.Constant;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.framework.view.event.RowSelectedLostListener;
import c2s.dto.IDto;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import javax.swing.JButton;
import client.framework.view.vwcomp.VWJTable;

public class VwActivityWorkerList extends VWTableWorkArea implements
    IVWPopupEditorEvent {
    public static final String ACTIONID_WORKS_LIST =
        "FWbsActivityWorkerListAction";
    public static final String ACTIONID_WORKS_SAVE =
        "FWbsActivityWorkerSaveAction";
    public static final String ACTIONID_WORKS_GETRESOURCE =
        "FWbsActivityGetResourceAction";
    public static final String ACTIONID_WORKS_DELETE =
        "FWbsActivityWorkerDeleteAction";
    private DtoWbsActivity dtoActivity ;
    private List activityWorkerList;
    private Vector resourceList;

    private VwWorkerPopWorkArea popWindow = new VwWorkerPopWorkArea();
    private VWJDate inputDate = new VWJDate();
    private VWJNumber inputNumber = new VWJNumber();
    private VWJText inputText = new VWJText();
    private ActivityWorkerTableModel model;

    private boolean isSaveOk = true;
    private boolean isParameterValid = true;
    public VwActivityWorkerList() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        inputDate.setCanSelect(true);
        inputNumber.setMaxInputIntegerDigit(8);
        inputText.setMaxByteLength(1000);
        inputText.setInput2ByteOk(true);

        VWJText remarkInput=new VWJText();
        remarkInput.setMaxByteLength(1000);
        remarkInput.setInput2ByteOk(true);

        //标题栏位
        Object[][] configs = new Object[][] {
                             {"Login Name", "loginId",VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.FALSE},
                             {"Name","empName", VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.FALSE},
                             {"Job Code", "jobCode", VMColumnConfig.UNEDITABLE,new VWJText(),Boolean.FALSE},
                             {"Role Name", "roleName",VMColumnConfig.UNEDITABLE, new VWJText(),Boolean.FALSE},
                             {"Job Description", "jobDescription", VMColumnConfig.EDITABLE, inputText,Boolean.TRUE},
                             {"Planned Start", "planStart", VMColumnConfig.EDITABLE, inputDate,Boolean.FALSE},
                             {"Planned Finish", "planFinish", VMColumnConfig.EDITABLE, inputDate,Boolean.FALSE},
                             {"Planned Work Time", "planWorkTime", VMColumnConfig.EDITABLE, inputNumber,Boolean.FALSE},
                             {"Actual Start", "actualStart", VMColumnConfig.UNEDITABLE, inputDate,Boolean.FALSE},
                             {"Actual Finish", "actualFinish", VMColumnConfig.UNEDITABLE, inputDate,Boolean.FALSE},
                             {"Actual Work Time", "actualWorkTime", VMColumnConfig.UNEDITABLE,inputNumber,Boolean.FALSE},
                             //{"At Completion(P.H.)", "etcWorkTime", VMColumnConfig.EDITABLE,inputNumber,Boolean.FALSE},
                             {"Remark", "remark", VMColumnConfig.EDITABLE, remarkInput,Boolean.TRUE}
        };
        try {
            model = new ActivityWorkerTableModel(configs);
            model.setDtoType(DtoActivityWorker.class);
            table = new VWJTable(model);
            this.add(table.getScrollPane(), null);
           //super.jbInit(configs, DtoActivityWorker.class);
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       addUICEvent();
    }

    /**
     * 添加事件
     */
    public void addUICEvent() {
        this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd();
            }
        });
        this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel();
            }
        });
        this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave();
            }
        });
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });

        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(this.getTable(), this);
        JButton btnDisplay = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(btnDisplay);

        this.getTable().addRowSelectedLostListener(new RowSelectedLostListener(){
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedData) {
                DtoActivityWorker selected = (DtoActivityWorker) oldSelectedData;
                Long planWorkTime = selected.getPlanWorkTime();
                Long actualWorkTime = selected.getActualWorkTime();
                Long acCompleteTime = selected.getEtcWorkTime();
                if(planWorkTime == null || actualWorkTime == null || acCompleteTime== null)
                    return true;
                if(acCompleteTime.longValue() < actualWorkTime.longValue()){
                    if (planWorkTime.longValue() > actualWorkTime.longValue())
                        selected.setEtcWorkTime(planWorkTime);
                    else
                        selected.setEtcWorkTime(actualWorkTime);
                    selected.setOp(IDto.OP_MODIFY);
                }
                return true;
            }

        });

    }

    /**
     * 新增事件
     */
    public void actionPerformedAdd() {
        getAvailabelResouce();
        if (resourceList == null || resourceList.size() == 0) {
            comMSG.dispErrorDialog(
                "There are not more labor resource in the account!");
            return;
        }
        Parameter param = new Parameter();
        param.put("resourceList", resourceList);
        param.put("dtoActivity", dtoActivity);
        popWindow.setParameter(param);
        popWindow.refreshWorkArea();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(), "Workers",
                                                popWindow, this);
        pop.show();
    }

    /**
     * 删除事件
     */
    public void actionPerformedDel() {
        int f = comMSG.dispConfirmDialog("Confirm to delete selected record!");
        if( f == Constant.OK ){
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.ACTIONID_WORKS_DELETE);
            inputInfo.setInputObj("dto", this.getTable().getSelectedData());
            ReturnInfo returnInfo = accessData(inputInfo);
            if(!returnInfo.isError())
                resetUI();
        }
    }

    /**
     * 保存事件
     */
    public void actionPerformedSave() {
        if(checkModified() && validateData())
           saveData();
    }

    private boolean saveData() {
        if (activityWorkerList == null) {
            return true;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_WORKS_SAVE);
        inputInfo.setInputObj("activityWorkerList", activityWorkerList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            resetUI();
            return true;
        }else{
            return false;
        }
    }
    private boolean checkModified() {
        if(activityWorkerList == null || activityWorkerList.size() <= 0)
            return false;
        if(!isParameterValid)
            return false;
        for (Iterator it = this.activityWorkerList.iterator();
                           it.hasNext(); ) {
            DtoBase dto = (DtoBase) it.next();
            if (dto.isChanged()) {
                return true;
            }
        }
        return false;
    }
    private boolean validateData(){
        if(activityWorkerList == null || activityWorkerList.size() <= 0)
            return true;
        boolean isValidate = true;
        StringBuffer msg = new StringBuffer();
        for(int i = 0;i < activityWorkerList.size();i ++){
            DtoActivityWorker dto = (DtoActivityWorker) activityWorkerList.get(i);
            Date begin = dto.getPlanStart();
            Date end = dto.getPlanFinish();
            if (begin == null || end == null) {
                isValidate = false;
                msg.append("Line["+i+"]:Please input plan start date and end date!");
            }
            else if (begin.after(end)) {
                msg.append("Line["+i+"]:End date must be larger than begin date!");
                isValidate = false;
            }
            begin = dto.getActualStart();
            end = dto.getActualFinish();
            if ( begin != null && end != null && begin.after(end)) {
                msg.append("Line["+i+"]:Actual finish date must be larger than actual start date");
                isValidate = false;
            }
            Long planWorkTime = dto.getPlanWorkTime();
            if(planWorkTime != null && planWorkTime.longValue() < 0 ){
                msg.append("Line["+i+"]:Plan work time must be larger than 0 !");
                isValidate = false;
            }
            Long acCompleteTime = dto.getEtcWorkTime();
            Long actualWorkTime = dto.getActualWorkTime();
            if(acCompleteTime != null && actualWorkTime!= null && actualWorkTime.longValue() > acCompleteTime.longValue()){
                msg.append("Line["+i+"]:Ac.Complete Time can't be less than actual work time!");
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
    public void setParameter(Parameter param) {
        super.setParameter(param);
        ITreeNode treeNode = (ITreeNode) param.get(DtoKEY.WBSTREE);
        this.dtoActivity = (DtoWbsActivity)  treeNode.getDataBean();
        //dtoActivity.setReadonly(false);
        if(dtoActivity == null || dtoActivity.isReadonly())
            isParameterValid = false;
        else
            isParameterValid = true;
    }

    private void setButtonVisible(){
        this.getButtonPanel().setVisible(isParameterValid);
        this.getTable().setEnabled(isParameterValid);
    }

    public void resetUI() {
        setButtonVisible();
        if(dtoActivity == null)
            return;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_WORKS_LIST);
        inputInfo.setInputObj("acntRid", dtoActivity.getAcntRid());
        inputInfo.setInputObj("activityRid", dtoActivity.getActivityRid() );
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            activityWorkerList = (List) returnInfo.getReturnObj(
                "activityWorkerList");
            this.getTable().setRows(activityWorkerList);
        }
    }
    //获得项目中可用的人员，并去掉已添加到该Activity中的人
    private void getAvailabelResouce() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_WORKS_GETRESOURCE);
        inputInfo.setInputObj("acntRid", dtoActivity.getAcntRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            resourceList = (Vector) returnInfo.getReturnObj("resourceList");
            getRidOfUsed();
        }
    }
    //删除resourceList中已添加到该Activity的人员
    private void getRidOfUsed() {
        if(activityWorkerList == null || activityWorkerList.size() == 0 ||
            resourceList == null || resourceList.size() == 0)
            return;
        Iterator i = resourceList.iterator();
        while (i.hasNext()) {
            DtoComboItem resource = (DtoComboItem) i.next();
            for (int j = 0; j < activityWorkerList.size(); j++) {
                DtoActivityWorker worker = (DtoActivityWorker)
                                           activityWorkerList.get(j);
                if (worker.getLoginId().equals(resource.getItemValue()) ||
                    "".equals(resource.getItemValue())) {
                    i.remove();
                    break;
                }
            }
        }
    }

    public boolean onClickOK(ActionEvent e) {
        if(popWindow.saveData()){
            resetUI();
            return true;
        }
        return false;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }


    public static void main(String[] args) {
        VWWorkArea w1 = new VWWorkArea();
        VwActivityWorkerList resource = new VwActivityWorkerList();

        w1.addTab("Labor Resource", resource);
        DtoWbsActivity dtoActivity = new DtoWbsActivity();
        dtoActivity.setAcntRid(new Long(1));
        dtoActivity.setActivityRid(new Long(10240));
        Parameter param = new Parameter();
        param.put("dtoActivity", dtoActivity);
        resource.setParameter(param);
        TestPanel.show(w1);
        resource.resetUI();
    }
}
