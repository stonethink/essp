package client.essp.pms.pbs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.pbs.DtoPmsPbsFiles;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.common.Global;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.common.comMSG;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import client.essp.common.file.VwJFileButton;
import javax.swing.BorderFactory;
import c2s.dto.IDto;
import client.essp.common.loginId.VWJLoginId;

public class VwPmsPbsFilesList extends VWTableWorkArea
implements IVWPopupEditorEvent{
    static final String actionIdList = "FPbsFilesListAction";
    static final String actionIdUpdate = "FPbsFilesUpdateAction";
    static final String actionIdDelete = "FPbsFilesDeleteAction";

    /**
     * define common data
     */
    private List fileList=new ArrayList();
    private Long acntRid;
    private String acntCode;
    private Long pbsRid;
    private boolean isSaveOk = true;
    private boolean isParameterValid = false;
    private boolean isReadonly = true;
    boolean isInsert = false;
    JButton btnUpdate = null;
    JButton btnSave = null;
    JButton btnLoad = null;
    JButton btnDel = null;
    JButton btnAdd = null;
    VwPmsPbsFilesGeneral vwPmsPbsFilesGeneral = new VwPmsPbsFilesGeneral();
    VwJFileButton btnFile = null;

    public VwPmsPbsFilesList() {
        VWJDate dteCreateDate = new VWJDate();
        VWJDate dteModDate = new VWJDate();
        dteCreateDate.setCanSelect(true);
        dteModDate.setCanSelect(true);

        btnFile = new VwJFileButton("PBS", false);
        btnFile.getTextComp().setBorder(BorderFactory.createEmptyBorder());

        Object[][] configs = new Object[][] {
                             {"Name", "fileName", VMColumnConfig.EDITABLE, new VWJText()}
                             , {"Version", "fileVersion", VMColumnConfig.EDITABLE, new VWJText()}
                             , {"Author", "fileAuthor", VMColumnConfig.EDITABLE, new VWJLoginId()}
                             , {"Create Date", "fileCreateDate", VMColumnConfig.EDITABLE, dteCreateDate}
                             , {"Modify Date", "fileModDate", VMColumnConfig.EDITABLE, dteModDate}
                             , {"Attachment", "fileLink", VMColumnConfig.UNEDITABLE, btnFile}
                             , {"Remark", "fileRemark", VMColumnConfig.EDITABLE, new VWJText()}

        };

        try {
            super.jbInit(configs, DtoPmsPbsFiles.class);
            getTable().setSortable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        setButtonVisible();
    }

    private void addUICEvent() {
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

       btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });

        vwPmsPbsFilesGeneral.addDataChangedListener(new DataChangedListener(){
            public void processDataChanged(){
                getTable().refreshTable();
            }
        });

        getTable().addMouseListener(new MouseAdapter(){
            public void mouseClick(MouseEvent e){
                //mouseClick2(e);
            }
        });
    }

    public void actionPerformedAdd(ActionEvent e) {
        DtoPmsPbsFiles dto = new DtoPmsPbsFiles();
        dto.setAcntRid(this.acntRid);
        dto.setPbsRid(this.pbsRid);
        dto.setFileAuthor(Global.userId);
        dto.setFileCreateDate( Global.todayDate);
        dto.setFileVersion("1.0");
        //getTable().addRow(dto);
        isInsert = true;

        Parameter param = new Parameter();
        param.put("dto", dto);
        param.put("isReadonly", new Boolean(isReadonly));
        param.put("acntCode", this.acntCode);
        vwPmsPbsFilesGeneral.setParameter(param);
        vwPmsPbsFilesGeneral.refreshWorkArea();

        VWJPopupEditor poputEditor = new VWJPopupEditor(getParentWindow(),"File", vwPmsPbsFilesGeneral, this);
        poputEditor.show();
    }

    public void actionPerformedUpdate(ActionEvent e) {
        DtoPmsPbsFiles dto = (DtoPmsPbsFiles)this.getSelectedData();
        if( dto == null ){
            return;
        }

        Parameter param = new Parameter();
        param.put("dto", dto);
        param.put("isReadonly", new Boolean(isReadonly));
        param.put("acntCode", this.acntCode);
        vwPmsPbsFilesGeneral.setParameter(param);
        vwPmsPbsFilesGeneral.refreshWorkArea();

        VWJPopupEditor poputEditor = new VWJPopupEditor(getParentWindow(),"File", vwPmsPbsFilesGeneral, this);
        poputEditor.show();
    }

    public void actionPerformedDel(ActionEvent e) {
        DtoPmsPbsFiles dto = (DtoPmsPbsFiles)this.getSelectedData();
        if( dto == null ){
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
                    this.fileList.remove(dto);
                }
            } else {
                getTable().deleteRow();
            }
        }
    }

    public void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            if (validateData() == true) {
                saveData();
            }
        }
    }

    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    private boolean checkModified() {
        for (Iterator it = this.fileList.iterator();
                           it.hasNext(); ) {
            DtoPmsPbsFiles doPmsPbsFiles = (DtoPmsPbsFiles) it.next();

            if (doPmsPbsFiles.isChanged() == true) {
                return true;
            }
        }

        return false;
    }

    private boolean validateData() {
        boolean bValid = true;
        StringBuffer msg = new StringBuffer("");

        for (int i = 0; i < this.getModel().getRows().size(); i++) {
            DtoPmsPbsFiles dto = (DtoPmsPbsFiles)this.getModel().getRows().get(i);
            if (StringUtil.nvl(dto.getFileName()).equals("") == true) {
                msg.append("Row " + (i + 1) + " ： Must input file name.\r\n");
                bValid = false;
            }
        }

        if (bValid == false) {
            comMSG.dispErrorDialog(msg.toString());
        }

        return bValid;
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj("fileList", fileList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            fileList = (List) returnInfo.getReturnObj("fileList");

            this.getTable().setRows(fileList);
            return true;
        }else{
            return false;
        }
    }

    //本workArea不需要外界的parameter
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.acntRid = (Long) (param.get("acntRid"));
        this.pbsRid = (Long) (param.get("pbsRid"));

        if( this.acntRid != null && this.pbsRid != null ){
            isParameterValid = true;
        }else{
            isParameterValid = false;
        }

        Boolean bIsReadonly = (Boolean) param.get("isReadonly");
        if (bIsReadonly == null ) {
            isReadonly = true;
        }else{
            isReadonly = bIsReadonly.booleanValue();
        }
    }

    //页面刷新－－－－－
    protected void resetUI() {
        if( this.acntRid == null || this.pbsRid == null ){
            setButtonVisible();
            setEnabledMode();
            fileList = new ArrayList();
            getTable().setRows(fileList);
            return;
        }

        setButtonVisible();
        setEnabledMode();
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj("acntRid", this.acntRid);
        inputInfo.setInputObj("pbsRid", this.pbsRid);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            fileList = (List) returnInfo.getReturnObj("fileList");
            acntCode = (String)returnInfo.getReturnObj("acntCode");

            btnFile.setAcntCode(acntCode);

            getTable().setRows(fileList);
        }
    }

    private void setButtonVisible() {
        if (isParameterValid == true &&
            this.isReadonly == false) {
            this.btnAdd.setVisible(true);
            this.btnDel.setVisible(true);
            this.btnSave.setVisible(true);
            this.btnUpdate.setVisible(true);
        } else {
            this.btnAdd.setVisible(false);
            this.btnDel.setVisible(false);
            this.btnSave.setVisible(false);
            this.btnUpdate.setVisible(false);
        }
    }

    private void setEnabledMode() {
        VMTable2 model = this.getModel();
        if (isParameterValid == true &&
                               this.isReadonly == false) {
            for (int i = 0; i < model.getColumnConfigs().size(); i++) {
                VMColumnConfig config = (VMColumnConfig) model.getColumnConfigs().get(i);
                config.setEditable(VMColumnConfig.EDITABLE);
            }
        } else {
            for (int i = 0; i < model.getColumnConfigs().size(); i++) {
                VMColumnConfig config = (VMColumnConfig) model.getColumnConfigs().get(i);
                config.setEditable(VMColumnConfig.UNEDITABLE);
            }
        }
    }

    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the files?") == true) {
                if (validateData() == true) {
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

    public boolean onClickOK(ActionEvent e) {
        vwPmsPbsFilesGeneral.saveWorkArea();
        boolean ok = vwPmsPbsFilesGeneral.isSaveOk();
        if (ok == true) {
            if (isInsert) {
                DtoPmsPbsFiles dto = vwPmsPbsFilesGeneral.getDto();
                if( dto.getFilesRid() != null ){
                    getTable().addRow(dto);
                    dto.setOp(IDto.OP_NOCHANGE);
                }
                isInsert = false;
            }
        }

        return ok;
    }

    public boolean onClickCancel(ActionEvent e) {
        isInsert = false;
        return true;
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwPmsPbsFilesList();

        w1.addTab("PmsPbsFiles", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put("acntRid", new Long(7));
        param.put("pbsRid", new Long(15));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
