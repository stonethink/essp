package client.essp.pms.wbs.checkpoint;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.wbs.DtoCheckPoint;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import c2s.dto.IDto;

public class VwCheckPointList extends VWTableWorkArea {
    public static final String ACTIONID_CHECKPOINT_LIST="FWbsCheckPointListAction";
    public static final String ACTIONID_CHECKPOINT_SAVE="FWbsCheckPointSaveAction";
    public static final String ACTIONID_CHECKPOINT_DELETE="FWbsCheckPointDeleteAction";
    private DtoWbsActivity wbs;
    private List checkPointList;

    private CheckPointTableModel model;
    private VWJDate inputDate = new VWJDate();
    private VWJCheckBox inputComplete = new VWJCheckBox();
    private VWJReal inputWeight = new VWJReal();
    private VWJText inputName = new VWJText();
    private VWJText inputRemark = new VWJText();

    private boolean isSaveOk = true;
    private boolean isParameterValid = true;
    public VwCheckPointList() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void jbInit() throws Exception {
        inputDate.setCanSelect(true);
        inputName.setMaxByteLength(50);
        inputRemark.setMaxByteLength(1000);
        inputWeight.setMaxInputIntegerDigit(3);
        inputWeight.setMaxInputDecimalDigit(2);
        inputWeight.setUICValue("1.00");
        //标题栏位
        Object[][] configs = new Object[][] {
                             { "Checkpoint", "name", VMColumnConfig.EDITABLE, new  VWJText() },
                             { "Weight","weight",VMColumnConfig.EDITABLE, inputWeight },
                             { "Due Date","dueDate",VMColumnConfig.EDITABLE, inputDate },
                             { "Finish Date","finishDate",VMColumnConfig.EDITABLE, inputDate },
                             { "Completed","completed",VMColumnConfig.EDITABLE, inputComplete },
                             { "Remark","remark",VMColumnConfig.EDITABLE, inputRemark },
        };

        this.setPreferredSize(new Dimension(680, 240));
        //table
        model = new CheckPointTableModel(configs);
        model.setDtoType(DtoCheckPoint.class);
        table = new VWJTable(model);
        this.add(table.getScrollPane(), null);
         addUICEvent();
    }
    public void addUICEvent(){
        this.getButtonPanel().addAddButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd();
            }
        });

        this.getButtonPanel().addDelButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel();
            }
        });

        this.getButtonPanel().addSaveButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave();
            }
        });

        this.getButtonPanel().addLoadButton(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });

    }
    //新增事件
    public void actionPerformedAdd(){
        DtoCheckPoint dto = (DtoCheckPoint) this.getTable().addRow();
        dto.setAcntRid(wbs.getAcntRid());
        dto.setWbsRid(wbs.getWbsRid());
    }

    //删除事件
    public void actionPerformedDel(){
        int f = comMSG.dispConfirmDialog("Confirm to delete selected record!");
        if( f == Constant.OK ){
            DtoCheckPoint dto = (DtoCheckPoint) this.getTable().getSelectedData();
            if(dto != null && dto.getOp().equals(IDto.OP_INSERT)){
                this.getTable().deleteRow();
            }else{
                InputInfo inputInfo = new InputInfo();
                inputInfo.setActionId(this.ACTIONID_CHECKPOINT_DELETE);
                inputInfo.setInputObj("dto", this.getTable().getSelectedData());
                ReturnInfo returnInfo = accessData(inputInfo);
                if (!returnInfo.isError())
                    resetUI();
            }
        }
    }
    //保存事件
    public void actionPerformedSave(){
        if(checkModified() &&  validateData())
            saveData();
    }
    public boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_CHECKPOINT_SAVE);
        inputInfo.setInputObj("checkPointList", checkPointList);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            resetUI();
            return true;
        }else
            return false;
    }
    public boolean checkModified() {
        if(checkPointList == null || checkPointList.size()<= 0 )
            return false;
        for (Iterator it = this.checkPointList.iterator();
                           it.hasNext(); ) {
            DtoCheckPoint dto = (DtoCheckPoint) it.next();
            if (dto.isChanged()) {
                return true;
            }
        }
        return false;
    }
    public boolean validateData(){
        boolean bValid = true;
        StringBuffer msg = new StringBuffer("");
        for (int i = 0; i < checkPointList.size();i ++) {
            DtoCheckPoint dto = (DtoCheckPoint) checkPointList.get(i);
            String name = StringUtil.nvl(dto.getName()).trim();
            if( "".equals(name)){
                msg.append("Line["+i+"]:Checkpoint name must be filled!\n");
                bValid = false;
            }
            Double weight = dto.getWeight();
            if(weight != null ){
                if( weight.doubleValue() < 0 || weight.doubleValue() > 100 ){
                    msg.append("Line[" + i +
                               "]:weight must be between 0 and 100!\n");
                    bValid = false;
                }
            }
        }
        if(!bValid)
            comMSG.dispErrorDialog(msg.toString());
        return bValid;
    }
    public void setParameter(Parameter param) {
        super.setParameter(param);
        wbs = (DtoWbsActivity) param.get("wbs");
        if(wbs == null || wbs.isReadonly())
            isParameterValid = false;
        else
            isParameterValid = true;
    }

    private void setButtonVisible(){
        this.getButtonPanel().setVisible(isParameterValid);
        this.getTable().setEnabled(isParameterValid);
    }

    public boolean isSaveOk(){
        return this.isSaveOk;
    }
    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if(confirmSaveWorkArea()){
                if (validateData() == true) {
                    isSaveOk = saveData();
                }
            }else{
                isSaveOk = true;
            }
        } else {
            isSaveOk = true;
        }
    }

    public void resetUI(){
        setButtonVisible();
        if(wbs == null || wbs.isReadonly())
            return;
        Long acntRid = wbs.getAcntRid();
        Long wbsRid = wbs.getWbsRid();
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_CHECKPOINT_LIST);
        inputInfo.setInputObj("acntRid", acntRid);
        inputInfo.setInputObj("wbsRid", wbsRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        if(!returnInfo.isError()){
            checkPointList = (List) returnInfo.getReturnObj("checkPointList");
            if(checkPointList == null)
                checkPointList = new ArrayList();
            this.getTable().setRows(checkPointList);
        }
    }

}
