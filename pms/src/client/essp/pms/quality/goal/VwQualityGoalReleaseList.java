package client.essp.pms.quality.goal;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.quality.goal.DtoQualityGoal;
import c2s.essp.pms.quality.goal.DtoReleaseRecord;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;
import client.framework.view.vwcomp.*;

public class VwQualityGoalReleaseList extends VWTableWorkArea {
    String entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN; //PmsAccountFun, EbsAccountFun,SepgAccountFun
    static final String actionIdList = "FQuQualityGoalReleaseListAction";
    static final String actionIdDelete = "FQuQualityGoalReleaseDeleteAction";
    static final String actionIdUpdate = "FQuQualityGoalReleaseUpdateAction";

    /**
     * define common data (globe)
     */
    List qualityGoalReleaseList;
    private boolean isSaveOk = true;

    JButton btnSave = null;
    JButton btnLoad = null;
    JButton btnDel = null;
    JButton btnAdd = null;
    private Long goalRid;
    private DtoQualityGoal dataBean;
    private boolean refreshFlag = false;
    /**
     * default constructor
     */
    public VwQualityGoalReleaseList() {
        VWJDate date = new VWJDate();
        date.setCanSelect(true);

        VWJReal real_2 = new VWJReal();
        real_2.setMaxInputDecimalDigit(2);
        VWJReal real_0 = new VWJReal();
        real_0.setMaxInputDecimalDigit(0);

        Object[][] configs = new Object[][] { {"Version", "version",
                             VMColumnConfig.EDITABLE, new VWJText()},
                             {"Release Date", "releaseDate",
                             VMColumnConfig.EDITABLE, date}, {"Description",
                             "description",
                             VMColumnConfig.EDITABLE, new VWJText()},
                             {"Start.Date", "statDate", VMColumnConfig.EDITABLE,
                             date}, {"Size", "size",
                             VMColumnConfig.EDITABLE, real_0},
                             {"Problems", "problems", VMColumnConfig.EDITABLE,
                             real_0}, {"Defects", "defects",
                             VMColumnConfig.EDITABLE,real_0},
                             {"Qualification Rate", "qualificationRate",
                             VMColumnConfig.UNEDITABLE, real_2} ////////
        };

        try {
            model = new VMReleaseListModel(configs);
            model.setDtoType(DtoReleaseRecord.class);
            table = new VWJTable(model);
            this.add(table.getScrollPane(), null);
            getTable().setSortable(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        addUICEvent();
        JTableHeader header = this.getTable().getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(100);
        tcModel.getColumn(7).setPreferredWidth(100);

    }

    private void addUICEvent() {

        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });
        btnAdd.setToolTipText("Add Data");

        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });
        btnDel.setToolTipText("Del Data");

        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });
        btnSave.setToolTipText("Save Data");

        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(this.getTable(), this);
        JButton button = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(button);

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
        btnLoad.setToolTipText("Refresh");

    }

    protected void actionPerformedAdd(ActionEvent e) {
        DtoReleaseRecord dto = new DtoReleaseRecord();
        dto.setGoalRid(goalRid);
        getTable().addRow(dto);
    }

    protected void actionPerformedDel(ActionEvent e) {
        DtoReleaseRecord dtoReleaseRecord = (DtoReleaseRecord)this.
                                            getSelectedData();
        if (dtoReleaseRecord == null) {
            return;
        }

        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            if (dtoReleaseRecord.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj("dtoReleaseRecord", dtoReleaseRecord);
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


    private boolean checkModified() {
        if (this.qualityGoalReleaseList == null) {
            return false;
        }

        for (Iterator it = this.qualityGoalReleaseList.iterator();
                           it.hasNext(); ) {
            DtoReleaseRecord dtoReleaseRecord = (DtoReleaseRecord) it.next();

            if (dtoReleaseRecord.isChanged() == true) {
                return true;
            }
        }
        return false;
    }

    protected void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    protected void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            if (validateData() == true) {
                saveData();
            }
        }
    }


    private boolean validateData() {
        StringBuffer msg = new StringBuffer("");
        for (int i = 0; i < this.getModel().getRows().size(); i++) {
            DtoReleaseRecord dto = (DtoReleaseRecord)this.getModel().getRows().
                                   get(i);
            if (StringUtil.nvl(dto.getSize()).equals("") == true) {
                msg.append("The " + (i + 1) +
                           " row£º Must input Size.\r\n");
            }
            if (StringUtil.nvl(dto.getDefects()).equals("") == true) {
                msg.append("The " + (i + 1) +
                           " row£º Must input defects.\r\n");
            }
        }
        if (msg.toString().equals("") == false) {
            comMSG.dispErrorDialog(msg.toString());
            return false;
        } else {
            return true;
        }
    }


    private void saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdUpdate);

        inputInfo.setInputObj("qualityGoalReleaseList",
                              this.qualityGoalReleaseList);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            qualityGoalReleaseList = (List) returnInfo.getReturnObj(
                "qualityGoalReleaseList");

            this.getTable().setRows(qualityGoalReleaseList);
            isSaveOk = true;
        }
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.dataBean = (DtoQualityGoal) param.get(DtoQualityGoal.
            DTO_QUALITY_GOAL);
        if(dataBean != null) {
            this.goalRid = dataBean.getRid();
        }
    }
    public void refreshWorkArea() {
        if (refreshFlag) {
            VWUtil.clearUI(this);
            resetUI();
            refreshFlag = false;
        } else {
            super.refreshWorkArea();
        }
    }

    public void fireNeedRefresh() {
        this.refreshFlag = true;
    }


    //Ò³ÃæË¢ÐÂ£­£­£­£­£­
    protected void resetUI() {
        if (this.goalRid == null) {
            setButtonVisible(false);
            this.qualityGoalReleaseList = new ArrayList();
            getTable().setRows(qualityGoalReleaseList);
            return;
        }

        setButtonVisible(true);
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj("goalRid", this.goalRid);

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            qualityGoalReleaseList = (List) returnInfo.getReturnObj(
                "qualityGoalReleaseList");

            getTable().setRows(qualityGoalReleaseList);
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
            if (confirmSaveWorkArea("Do you save the Release resource?") == true) {
                if (validateData() == true) {
                    saveData();
                }
            } else {
                isSaveOk = true;
            }
        }
    }


    public boolean isSaveOk() {
        return this.isSaveOk;
    }


    public static void main(String[] args) {

    }

}
