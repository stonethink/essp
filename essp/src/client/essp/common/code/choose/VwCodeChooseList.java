package client.essp.common.code.choose;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.code.DtoCodeValueChoose;
import c2s.essp.common.code.DtoKey;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJDisp;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;

public abstract class VwCodeChooseList extends VWTableWorkArea implements
    IVWPopupEditorEvent {

    /**
     * define common data
     */
    private List codeList = new ArrayList();

    private boolean isSaveOk = true;
    protected boolean isParameterValid = false;
    private boolean isReadonly = true;
    JButton btnUpdate = null;
    JButton btnSave = null;
    JButton btnLoad = null;
    JButton btnDel = null;
    JButton btnAdd = null;
    VwCodeTree vwCodeTree = new VwCodeTree();
    private String catalog=null;

    public VwCodeChooseList() {
        VWJDisp disp = new VWJDisp();

        Object[][] configs = new Object[][] {
                             //{"Extend","extend", VMColumnConfig.EDITABLE, new VwJCodeButton()},
                             {"Name", "codeName",
                             VMColumnConfig.UNEDITABLE, new VWJText()}
                             , {"Value", "codeValuePath",
                             VMColumnConfig.EDITABLE, disp}
                             , {"Description", "description",
                             VMColumnConfig.UNEDITABLE, new VWJText()}
        };

        try {
            super.jbInit(configs, DtoCodeValueChoose.class);
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
                actionPerformedUpdate();
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

        getTable().addMouseListener(new MouseAdapter() {
            public void mouseClick(MouseEvent e) {
                if (e.getClickCount() != 2) {
                    return;
                }
                int col = getTable().getSelectedColumn();
                if (col != 1) {
                    return;
                }

                actionPerformedUpdate();
            }
        });
    }

    public void actionPerformedUpdate() {

        DtoCodeValueChoose dto = (DtoCodeValueChoose)this.getSelectedData();
        if (dto == null) {
            return;
        }

        Parameter param = new Parameter();
        param.put(DtoKey.TYPE, getCodeType());
        param.put(DtoKey.CATALOG, this.catalog);
        param.put(DtoKey.CODE_RID, dto.getCodeRid());
        vwCodeTree.setParameter(param);
        vwCodeTree.refreshWorkArea();

        vwCodeTree.setChooser(new ICodeChooser() {
            public void chooseCode(DtoCodeValueChoose chooseDto) {
                updateCodeValue(chooseDto);
            }
        });

        VWWorkArea w = new VWWorkArea();
        w.addTab("Code", vwCodeTree);
        VWJPopupEditor poputEditor = new VWJPopupEditor(getParentWindow(),
            "Code", w, this);
        if( poputEditor.showConfirm() == Constant.OK ){
            DtoCodeValueChoose chooseDto = (DtoCodeValueChoose)vwCodeTree.getSelectedData();
            updateCodeValue(chooseDto);
        }
    }

    private void updateCodeValue(DtoCodeValueChoose chooseDto) {
        if (chooseDto.isCodeValue() == false) {
            return;
        }

        DtoCodeValueChoose selectCode = (DtoCodeValueChoose)
                                        getSelectedData();
        if (selectCode != null && chooseDto.getValueRid().equals(selectCode.getValueRid()) == false) {
            DtoUtil.copyBeanToBean(selectCode, chooseDto,
                                   new String[] {"oldValueRid", "op"});
            selectCode.setOp(IDto.OP_MODIFY);
            getTable().refreshTable();
        }
    }

    public void actionPerformedAdd(ActionEvent e) {
        Parameter param = new Parameter();
        param.put(DtoKey.TYPE, getCodeType());
        param.put(DtoKey.CATALOG, this.catalog);
        vwCodeTree.setParameter(param);
        vwCodeTree.refreshWorkArea();

        vwCodeTree.setChooser(new ICodeChooser() {
            public void chooseCode(DtoCodeValueChoose chooseDto) {
                addCodeValue(chooseDto);
            }
        });

        VWWorkArea w = new VWWorkArea();
        w.addTab("Code", vwCodeTree);
        VWJPopupEditor poputEditor = new VWJPopupEditor(getParentWindow(),
            "Code", w, this);
        if( poputEditor.showConfirm() == Constant.OK ){
            DtoCodeValueChoose chooseDto = (DtoCodeValueChoose)vwCodeTree.getSelectedData();
            addCodeValue(chooseDto);
        }
    }

    private void addCodeValue(DtoCodeValueChoose chooseDto) {
        if (chooseDto.isCodeValue() == false) {
            return;
        }

        for (int i = 0; i < getModel().getRowCount(); i++) {
            DtoCodeValueChoose dtoCodeValue = (DtoCodeValueChoose)
                                              getModel().getRow(i);

            /**相同的code只有一个value
             */
            if (chooseDto.getCodeRid().equals(dtoCodeValue.getCodeRid())) {
                if (chooseDto.getValueRid().equals(dtoCodeValue.getValueRid())) {
                    return;
                } else {
                    DtoUtil.copyBeanToBean(dtoCodeValue, chooseDto,
                                           new String[] {"oldValueRid", "op"});
                    dtoCodeValue.setOp(IDto.OP_MODIFY);
                    getModel().fireTableRowsUpdated(i, i);
                    return;
                }
            }
        }

        DtoCodeValueChoose newDto = new DtoCodeValueChoose();
        DtoUtil.copyBeanToBean(newDto, chooseDto, new String[] {"oldValueRid",
                               "op"});
        newDto.setOldValueRid(newDto.getValueRid());
        getTable().addRow(newDto);
    }

    public void actionPerformedDel(ActionEvent e) {
        DtoCodeValueChoose dto = (DtoCodeValueChoose)this.getSelectedData();
        if (dto == null) {
            return;
        }

        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            if (dto.isInsert() == false) {
                InputInfo inputInfo = this.createDeleteInputInfo();
                inputInfo.setInputObj(DtoKey.DTO, dto);

                ReturnInfo returnInfo = accessData(inputInfo);
                if (returnInfo.isError() == false) {

                    getTable().deleteRow();
                    this.codeList.remove(dto);
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
        for (Iterator it = this.codeList.iterator();
                           it.hasNext(); ) {
            DtoCodeValueChoose dtoCodeValueChoose = (DtoCodeValueChoose) it.
                next();

            if (dtoCodeValueChoose.isChanged() == true) {
                return true;
            }
        }

        return false;
    }

    private boolean validateData() {
        boolean bValid = true;

        return bValid;
    }

    private boolean saveData() {
        InputInfo inputInfo = this.createUpdateInputInfo();
        inputInfo.setInputObj(DtoKey.DTO_LIST, codeList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            codeList = (List) returnInfo.getReturnObj(DtoKey.DTO_LIST);

            this.getTable().setRows(codeList);
            return true;
        } else {
            return false;
        }
    }

    //本workArea不需要外界的parameter
    public void setParameter(Parameter param) {
        super.setParameter(param);

        Boolean bIsReadonly = (Boolean) param.get("isReadonly");
        if (bIsReadonly == null) {
            isReadonly = false;
        } else {
            isReadonly = bIsReadonly.booleanValue();
        }
    }

    //页面刷新－－－－－
    protected void resetUI() {
        if (this.isParameterValid == false) {
            setButtonVisible();
            codeList = new ArrayList();
            getTable().setRows(codeList);
            return;
        }

        setButtonVisible();
        InputInfo inputInfo = this.createSelectInputInfo();

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            codeList = (List) returnInfo.getReturnObj(DtoKey.DTO_LIST);
            catalog = (String)returnInfo.getReturnObj(DtoKey.CATALOG);

            getTable().setRows(codeList);
        }
    }

    private void setButtonVisible() {
        if (isParameterValid == true &&
            getIsReadOnly() == false) {
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

    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the codes?") == true) {
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

    public boolean isSaveOk() {
        return this.isSaveOk;
    }

    public boolean onClickOK(ActionEvent e) {


        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    protected boolean getIsReadOnly(){
        return this.isReadonly;
    }

    protected abstract InputInfo createDeleteInputInfo();

    protected abstract InputInfo createUpdateInputInfo();

    protected abstract InputInfo createSelectInputInfo();

    protected abstract String getCodeType();
}
