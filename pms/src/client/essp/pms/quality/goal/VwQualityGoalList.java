package client.essp.pms.quality.goal;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.quality.goal.DtoQualityGoal;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import db.essp.pms.PmsPcbParameter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;
import client.framework.view.vwcomp.VWJReal;
import client.essp.common.view.VWAccountLabel;
import itf.account.AccountFactory;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.account.IAccountModel;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwQualityGoalList extends VWTableWorkArea {

    static final String actionIdList = "FQuQualityGoalListAction";
    static final String actionIdDelete = "FQuQualityGoalDeleteAction";
    static final String actionIdFindPcbParameter =
        "FQuQualityGoalFindPcbParameterAction";
    /**
     * define common data (globe)
     */
    private List qualityGoalList;
    private List wbsList;
    JButton btnLoad = null;
    JButton btnDel = null;
    JButton btnAdd = null;
    private Long acntRid;
    private boolean isSaveOk = true;
    VWAccountLabel accountLabel = null;
    public VwQualityGoalList() {
        VWJReal real_2 = new VWJReal();
        real_2.setMaxInputDecimalDigit(2);
        VWJReal real_0 = new VWJReal();
        real_0.setMaxInputDecimalDigit(0);
        Object[][] configs = new Object[][] { {"Delivery Production",
                             "production", VMColumnConfig.UNEDITABLE,
                             new VWJText()}, {"Unit", "unit",
                             VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Plan Rate", "planRate",
                             VMColumnConfig.UNEDITABLE, real_2},
                             {"Actual Rate", "actualRate",
                             VMColumnConfig.UNEDITABLE, real_2},
                             {"Description", "description",
                             VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Seqence", "seq", VMColumnConfig.UNEDITABLE,
                             real_0}
        };

        try {
            super.jbInit(configs, DtoQualityGoal.class);
            getTable().setSortable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addUICEvent();
        //调整列的宽度
        JTableHeader header = this.getTable().getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
        tcModel.getColumn(0).setPreferredWidth(100);
        tcModel.getColumn(1).setPreferredWidth(50);
        tcModel.getColumn(2).setPreferredWidth(50);
        tcModel.getColumn(3).setPreferredWidth(50);
        tcModel.getColumn(4).setPreferredWidth(200);
        tcModel.getColumn(5).setPreferredWidth(50);

    }

    private void addUICEvent() {
        accountLabel = new VWAccountLabel();
        this.getButtonPanel().add(accountLabel);
        btnAdd = this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });
        btnAdd.setToolTipText("Add Quality Goal");

        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });
        btnDel.setToolTipText("Del Quality Goal");

        TableColumnChooseDisplay chooseDisplay =
            new TableColumnChooseDisplay(this.getTable(), this);
        JButton button = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(button);

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });
        btnLoad.setToolTipText("Refresh Quality Goal");
    }

    protected void actionPerformedAdd(ActionEvent e) {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setInputObj(DtoKey.ACNT_RID, this.acntRid);

        inputInfo.setActionId(this.actionIdFindPcbParameter);

        ReturnInfo returnInfo = accessData(inputInfo);
        PmsPcbParameter pmsPcbParameter = (PmsPcbParameter) returnInfo.
                                          getReturnObj("pmsPcbParameter");

        DtoQualityGoal dto = new DtoQualityGoal();
        if(pmsPcbParameter != null) {
            dto.setUnit(pmsPcbParameter.getUnit());
            dto.setPlanRate(pmsPcbParameter.getPlan());
        }
        dto.setAcntRid(this.acntRid);

        getTable().addRow(dto);
    }

    protected void actionPerformedDel(ActionEvent e) {
        DtoQualityGoal dtoQualityGoal = (DtoQualityGoal)this.getSelectedData();
        if (dtoQualityGoal == null) {
            return;
        }

        int option = comMSG.dispConfirmDialog("Do you delete the data?");
        if (option == Constant.OK) {
            if (dtoQualityGoal.isInsert() == false) {
                InputInfo inputInfo = new InputInfo();
                inputInfo.setInputObj("dtoQualityGoal", dtoQualityGoal);
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
        if (qualityGoalList == null) {
            return false;
        }
        for (Iterator it = this.qualityGoalList.iterator();
                           it.hasNext(); ) {
            DtoQualityGoal dtoQualityGoal = (DtoQualityGoal) it.next();

            if (dtoQualityGoal.isChanged() == true) {
                return true;
            }
        }
        return true;
    }

    protected void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.acntRid = (Long) (param.get(DtoAcntKEY.ACNT_RID));
    }

    //页面刷新－－－－－
    protected void resetUI() {
        setButtonVisible(true);
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionIdList);
        inputInfo.setInputObj(DtoAcntKEY.ACNT_RID, this.acntRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            qualityGoalList = (List) returnInfo.getReturnObj("qualityGoalList");
            IDtoAccount accountDto =(IDtoAccount)returnInfo.getReturnObj("accountDto");
            getTable().setRows(qualityGoalList);
            IAccountModel model = VWAccountLabel.createtDefaultModel(accountDto.getId(),
            accountDto.getName());
            accountLabel.setModel(model);
        }
  }

    private void setButtonVisible(boolean isVisible) {
        this.btnAdd.setVisible(isVisible);
        this.btnDel.setVisible(isVisible);
        this.btnLoad.setVisible(isVisible);
//        this.btnSave.setVisible(true);
    }


    public static void main(String[] args) {

//        VWWorkArea workArea = new VwQualityGoalList();
//        Parameter param = new Parameter();
//        param.put("entryFunType", "PmsAccountFun");
//        workArea.setParameter(param);
//
//        workArea.refreshWorkArea();
//        TestPanel.show(workArea);
    }
}
