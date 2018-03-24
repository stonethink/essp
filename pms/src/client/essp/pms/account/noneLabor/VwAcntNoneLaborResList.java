package client.essp.pms.account.noneLabor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoNoneLaborRes;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import javax.swing.JButton;
import com.wits.util.StringUtil;
import client.framework.view.common.comMSG;
import client.framework.common.Constant;

public class VwAcntNoneLaborResList extends VWTableWorkArea {
    static final String actionIdList = "FAcntNoneLaborResListAction";
    static final String actionIdUpdate = "FAcntNoneLaborResUpdateAction";
    static final String actionIdDelete = "FAcntNoneLaborResDeleteAction";

    /**
     * define common data
     */
    private List noneLaborResList;
    private Long acntRid;
    private boolean isSaveOk = true;
    JButton btnSave = null;
    JButton btnAdd = null;
    JButton btnDel = null;
    JButton btnLoad = null;


    public VwAcntNoneLaborResList() {
        Object[][] configs = new Object[][] {
                             {"Environment Name", "envName", VMColumnConfig.EDITABLE, new VWJText()}
        };

        try {
            super.jbInit(configs, DtoNoneLaborRes.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    private void addUICEvent() {
        //捕获事件－－－－
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
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
    }

    public void actionPerformedAdd(ActionEvent e) {
        DtoNoneLaborRes dto = new DtoNoneLaborRes();
        dto.setAcntRid(this.acntRid);
        getTable().addRow(dto);
    }

    public void actionPerformedDel(ActionEvent e) {
        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            DtoNoneLaborRes dto = (DtoNoneLaborRes)this.getSelectedData();
            if (dto != null) {
                if (dto.isInsert() == false) {
                    InputInfo inputInfo = new InputInfo();
                    inputInfo.setInputObj(DtoAcntKEY.DTO, dto);
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
    }

    public void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            if (validateData() == true) {
                saveData();
            }
        }
    }

    private boolean checkModified() {
        for (Iterator it = this.noneLaborResList.iterator();
                           it.hasNext(); ) {
            DtoNoneLaborRes dtoNoneLaborRes = (DtoNoneLaborRes) it.next();

            if (dtoNoneLaborRes.isChanged() == true) {
                return true;
            }
        }

        return false;
    }


    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    private boolean validateData() {
        StringBuffer msg = new StringBuffer("");

        List repeatRow = new ArrayList();
        for (int i = 0; i < this.getModel().getRows().size(); i++) {
            DtoNoneLaborRes dto = (DtoNoneLaborRes)this.getModel().getRows().get(i);
            if (StringUtil.nvl(dto.getEnvName()).equals("") == true) {
                msg.append("The " + (i + 1) + " row： Must input environment name.\r\n");
            } else {

                //是否有重复
                if (repeatRow.contains(new Integer(i)) == false) {
                    StringBuffer strRepeatRow = new StringBuffer();
                    boolean bRepeat = false;
                    for (int j = i + 1; j < this.getModel().getRows().size(); j++) {
                        DtoNoneLaborRes tmpDto = (DtoNoneLaborRes)this.getModel().getRows().get(j);

                        if (dto.getEnvName().equals(tmpDto.getEnvName()) == true) {
                            strRepeatRow.append(" ,");
                            strRepeatRow.append(j+1);

                            repeatRow.add(new Integer(j));
                            bRepeat = true;
                        }
                    }
                    if (bRepeat == true) {
                        if (repeatRow.contains(new Integer(i)) == false) {
                            strRepeatRow.insert(0, i+1);
                            repeatRow.add(new Integer(i));
                        }

                        msg.append("The enviroment name of rows: ");
                        msg.append(strRepeatRow);
                        msg.append("  are the same.Enviroent name can't be reduplicate.\r\n");
                    }
                }
            }
        }

        if( msg.toString().equals("") == false ){
            comMSG.dispErrorDialog(msg.toString());
            return false;
        }else{
            return true;
        }
    }

    private void saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj(DtoAcntKEY.NONE_LABOR_RES_LIST, noneLaborResList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            noneLaborResList = (List) returnInfo.getReturnObj(DtoAcntKEY.NONE_LABOR_RES_LIST);

            this.getTable().setRows(noneLaborResList);
            isSaveOk = true;
        }
    }

    //本workArea不需要外界的parameter
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.acntRid = (Long) (param.get(DtoAcntKEY.ACNT_RID));
    }

    //页面刷新－－－－－
    protected void resetUI() {

        if (this.acntRid == null) {
            setButtonVisible(false);
            noneLaborResList = new ArrayList();
            getTable().setRows(noneLaborResList);
            return;
        }

        setButtonVisible(true);
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj(DtoAcntKEY.ACNT_RID, this.acntRid);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            noneLaborResList = (List) returnInfo.getReturnObj(DtoAcntKEY.NONE_LABOR_RES_LIST);

            getTable().setRows(noneLaborResList);
        }
    }

    private void setButtonVisible(boolean isVisible) {
        this.btnAdd.setVisible(isVisible);
        this.btnDel.setVisible(isVisible);
        this.btnLoad.setVisible(isVisible);
        this.btnSave.setVisible(isVisible);
    }


    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the noneLabor resource?") == true) {
                if (validateData() == true) {
                    saveData();
                }
            }else{

                isSaveOk = true;
            }
        }
    }

    public boolean isSaveOk(){
        return this.isSaveOk;
    }

    public static void main(String args[]) {
        VWWorkArea w1 = new VWWorkArea();
        VWWorkArea workArea = new VwAcntNoneLaborResList();

        w1.addTab("None labor resource", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoAcntKEY.ACNT_RID, new Long(7));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
