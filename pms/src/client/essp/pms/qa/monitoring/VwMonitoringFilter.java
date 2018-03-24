package client.essp.pms.qa.monitoring;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

import c2s.essp.pms.qa.DtoQaCheckAction;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJTreeTable;
import com.wits.util.comDate;

public class VwMonitoringFilter extends VWWorkArea implements
    IVWPopupEditorEvent {
    private VWJComboBox conditionSel = new VWJComboBox();
    final static String[] FILTER_CONDITION = {"All Actions",
                                             "UnStarted Actions",
                                             "Processing Actions",
                                             "Closed Actions",
                                             "Plan Performer",
                                             "Plan Date", "Actual Performer",
                                             "Actual Date"};
    private VWJTreeTable table;
    private String currentFilter;
    private VwMonitoringList mList;
    VWJLabel filterLbl = new VWJLabel();
    VWJLabel planPerformerLbl = new VWJLabel();
    VWJComboBox planPerformerCmb = new VWJComboBox();
    VWJLabel planStartDateLbl = new VWJLabel();
    VWJLabel planFinishDateLbl = new VWJLabel();
    VWJLabel actualStartDateLbl = new VWJLabel();
    VWJLabel actualFinishDateLbl = new VWJLabel();
    VWJLabel actualPerformerLbl = new VWJLabel();
    VWJComboBox actualPerformerCmb = new VWJComboBox();
    VWJDate planStartDate = new VWJDate();
    VWJDate planFinishDate = new VWJDate();
    VWJDate actualStartDate = new VWJDate();
    VWJDate actualFinishDate = new VWJDate();
    String Performer;
    String toolTip = "";

    public VwMonitoringFilter(VWJTreeTable table, VwMonitoringList mList,
                              VWJLabel currentFilterLbl) {
        this.table = table;
        this.mList = mList;
        this.filterLbl = currentFilterLbl;

        planPerformerCmb.setModel(this.mList.cmbPerformer.getModel());
        actualPerformerCmb.setModel(this.mList.cmbPerformer.getModel());
        init();
    }

    public VwMonitoringFilter(VWJTreeTable table, VwMonitoringList mList) {
        this(table, mList, null);
    }

    //初始化画面布局
    public void init() {
        this.setLayout(null);
        VWJLabel filterLbl = new VWJLabel();
        filterLbl.setText("Please select filter condition:");
        filterLbl.setSize(200, 20);
        filterLbl.setLocation(50, 10);
        this.add(filterLbl);

        VMComboBox conditionSelModel = VMComboBox.toVMComboBox(
            VwMonitoringFilter.
            FILTER_CONDITION, VwMonitoringFilter.FILTER_CONDITION);
        conditionSel.setModel(conditionSelModel);


        conditionSel.setSize(200, 20);
        conditionSel.setLocation(50, 35);
        this.add(conditionSel);

        planPerformerLbl.setText("Plan Perfomer:");
        planPerformerLbl.setSize(120, 20);
        planPerformerLbl.setLocation(50, 65);
        this.add(planPerformerLbl);
        planPerformerLbl.setVisible(false);

        planPerformerCmb.setSize(120, 20);
        planPerformerCmb.setLocation(130, 65);
        this.add(planPerformerCmb);
        planPerformerCmb.setVisible(false);

        actualPerformerLbl.setText("Actual Perfomer:");
        actualPerformerLbl.setSize(130, 20);
        actualPerformerLbl.setLocation(50, 65);
        this.add(actualPerformerLbl);
        actualPerformerLbl.setVisible(false);

        actualPerformerCmb.setSize(110, 20);
        actualPerformerCmb.setLocation(140, 65);
        this.add(actualPerformerCmb);
        actualPerformerCmb.setVisible(false);

        planStartDateLbl.setText("Start Date:");
        planStartDateLbl.setSize(85, 20);
        planStartDateLbl.setLocation(50, 65);
        this.add(planStartDateLbl);
        planStartDateLbl.setVisible(false);

        planFinishDateLbl.setText("Finish Date:");
        planFinishDateLbl.setSize(85, 20);
        planFinishDateLbl.setLocation(50, 90);
        this.add(planFinishDateLbl);
        planFinishDateLbl.setVisible(false);

        planStartDate.setSize(125, 20);
        planStartDate.setLocation(125, 65);
        planStartDate.setCanSelect(true);
        this.add(planStartDate);
        planStartDate.setVisible(false);

        planFinishDate.setSize(125, 20);
        planFinishDate.setLocation(125, 90);
        planFinishDate.setCanSelect(true);
        this.add(planFinishDate);
        planFinishDate.setVisible(false);

        actualStartDateLbl.setText("Start Date:");
        actualStartDateLbl.setSize(85, 20);
        actualStartDateLbl.setLocation(50, 65);
        this.add(actualStartDateLbl);
        actualStartDateLbl.setVisible(false);

        actualFinishDateLbl.setText("Finish Date:");
        actualFinishDateLbl.setSize(85, 20);
        actualFinishDateLbl.setLocation(50, 90);
        this.add(actualFinishDateLbl);
        actualFinishDateLbl.setVisible(false);

        actualStartDate.setSize(125, 20);
        actualStartDate.setLocation(125, 65);
        actualStartDate.setCanSelect(true);
        this.add(actualStartDate);
        actualStartDate.setVisible(false);

        actualFinishDate.setSize(125, 20);
        actualFinishDate.setLocation(125, 90);
        actualFinishDate.setCanSelect(true);
        this.add(actualFinishDate);
        actualFinishDate.setVisible(false);

        conditionSel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int indexSelected = conditionSel.getSelectedIndex();
                switch (indexSelected) {
                case 4:
                    planPerformerLbl.setVisible(true);
                    planPerformerCmb.setVisible(true);
                    planPerformerLbl.setText("Plan Perfomer");
                    planPerformerCmb.requestFocus();
                    actualPerformerLbl.setVisible(false);
                    actualPerformerCmb.setVisible(false);
                    planStartDateLbl.setVisible(false);
                    planFinishDateLbl.setVisible(false);
                    planStartDate.setVisible(false);
                    planFinishDate.setVisible(false);
                    actualStartDateLbl.setVisible(false);
                    actualFinishDateLbl.setVisible(false);
                    actualStartDate.setVisible(false);
                    actualFinishDate.setVisible(false);
                    break;
                case 5:
                    planStartDateLbl.setVisible(true);
                    planFinishDateLbl.setVisible(true);
                    planStartDate.setVisible(true);
                    planFinishDate.setVisible(true);
                    planStartDate.requestFocus();
                    actualPerformerLbl.setVisible(false);
                    actualPerformerCmb.setVisible(false);
                    planPerformerLbl.setVisible(false);
                    planPerformerCmb.setVisible(false);
                    actualStartDateLbl.setVisible(false);
                    actualFinishDateLbl.setVisible(false);
                    actualStartDate.setVisible(false);
                    actualFinishDate.setVisible(false);
                    break;
                case 6:
                    actualPerformerLbl.setVisible(true);
                    actualPerformerCmb.setVisible(true);
                    actualPerformerLbl.setText("Actual Perfomer");
                    actualPerformerCmb.requestFocus();
                    planPerformerLbl.setVisible(false);
                    planPerformerCmb.setVisible(false);
                    planStartDateLbl.setVisible(false);
                    planFinishDateLbl.setVisible(false);
                    planStartDate.setVisible(false);
                    planFinishDate.setVisible(false);
                    actualStartDateLbl.setVisible(false);
                    actualFinishDateLbl.setVisible(false);
                    actualStartDate.setVisible(false);
                    actualFinishDate.setVisible(false);
                    break;

                case 7:
                    actualStartDateLbl.setVisible(true);
                    actualFinishDateLbl.setVisible(true);
                    actualStartDate.setVisible(true);
                    actualFinishDate.setVisible(true);
                    actualStartDate.requestFocus();
                    planPerformerLbl.setVisible(false);
                    planPerformerCmb.setVisible(false);
                    planStartDateLbl.setVisible(false);
                    planFinishDateLbl.setVisible(false);
                    planStartDate.setVisible(false);
                    planFinishDate.setVisible(false);
                    actualPerformerLbl.setVisible(false);
                    actualPerformerCmb.setVisible(false);
                    break;
                default:
                    planPerformerLbl.setVisible(false);
                    planPerformerCmb.setVisible(false);
                    actualPerformerLbl.setVisible(false);
                    actualPerformerCmb.setVisible(false);
                    planStartDateLbl.setVisible(false);
                    planFinishDateLbl.setVisible(false);
                    planStartDate.setVisible(false);
                    planFinishDate.setVisible(false);
                    actualStartDateLbl.setVisible(false);
                    actualFinishDateLbl.setVisible(false);
                    actualStartDate.setVisible(false);
                    actualFinishDate.setVisible(false);
                    break;
                }
            } //Method end
        });
        if (mList.filterType.equals(mList.KEY_FILTER_ALL)) {
            conditionSel.setSelectedIndex(0);
        } else if (mList.filterType.equals(mList.KEY_FILTER_STATUS) &&
                   mList.filterStatus.equals(DtoQaCheckAction.chkActionStatus[0])) {
            conditionSel.setSelectedIndex(1);
        } else if (mList.filterType.equals(mList.KEY_FILTER_STATUS) &&
                   mList.filterStatus.equals(DtoQaCheckAction.chkActionStatus[1])) {
            conditionSel.setSelectedIndex(2);
        } else if (mList.filterType.equals(mList.KEY_FILTER_STATUS) &&
                   mList.filterStatus.equals(DtoQaCheckAction.chkActionStatus[2])) {
            conditionSel.setSelectedIndex(3);
        } else if (mList.filterType.equals(mList.KEY_PLAN_PERFORMER)) {
            conditionSel.setSelectedIndex(4);
            this.planPerformerCmb.setUICValue(mList.planPerformer);
        } else if (mList.filterType.equals(mList.KEY_PLAN_DATE)) {
            conditionSel.setSelectedIndex(5);
            planStartDate.setUICValue(mList.planStartDate);
            planFinishDate.setUICValue(mList.planFinishDate);
        } else if (mList.filterType.equals(mList.KEY_ACTUAL_PERFORMER)) {
            conditionSel.setSelectedIndex(6);
            this.actualPerformerCmb.setUICValue(mList.actualPerformer);
        } else if (mList.filterType.equals(mList.KEY_ACTUAL_DATE)) {
            conditionSel.setSelectedIndex(7);
            actualStartDate.setUICValue(mList.actualStartDate);
            actualFinishDate.setUICValue(mList.actualFinishDate);
        }

    }


    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public boolean onClickOK(ActionEvent actionEvent) {
        int index = conditionSel.getSelectedIndex();
        switch (index) {
        case 0:
            mList.filterType = VwMonitoringList.KEY_FILTER_ALL;
            this.filterLbl.setText(VwMonitoringList.KEY_FILTER_ALL);
            break;
        case 1:
            mList.filterType = VwMonitoringList.KEY_FILTER_STATUS;
            mList.filterStatus = DtoQaCheckAction.chkActionStatus[0];
            this.filterLbl.setText(mList.filterStatus);
            break;
        case 2:
            mList.filterType = VwMonitoringList.KEY_FILTER_STATUS;
            mList.filterStatus = DtoQaCheckAction.chkActionStatus[1];
            this.filterLbl.setText(mList.filterStatus);
            break;
        case 3:
            mList.filterType = VwMonitoringList.KEY_FILTER_STATUS;
            mList.filterStatus = DtoQaCheckAction.chkActionStatus[2];
            this.filterLbl.setText(mList.filterStatus);
            break;
        case 4:
            mList.filterType = VwMonitoringList.KEY_PLAN_PERFORMER;
            mList.planPerformer = (String)this.planPerformerCmb.getUICValue();
            if (mList.planPerformer == null) {
                comMSG.dispErrorDialog("Please select a planPerformer!");
                return false;
            }
            this.filterLbl.setText(VwMonitoringList.KEY_PLAN_PERFORMER + ": " +
                                    mList.planPerformer);
            break;
        case 5:
            mList.filterType = VwMonitoringList.KEY_PLAN_DATE;
            mList.planStartDate = (Date)this.planStartDate.getUICValue();
            mList.planFinishDate = (Date)this.planFinishDate.getUICValue();
            if (!(mList.planStartDate != null && mList.planFinishDate != null)) {
                comMSG.dispErrorDialog(
                    "Please input planStartDate and planFinishDate!");
                return false;
            }
            this.filterLbl.setText(VwMonitoringList.KEY_PLAN_DATE + ": " +
                                  comDate.dateToString(mList.planStartDate) +
                                  " ~ " +
                                  comDate.dateToString(mList.planFinishDate));

            break;
        case 6:
            mList.filterType = VwMonitoringList.KEY_ACTUAL_PERFORMER;
            mList.actualPerformer = (String)this.actualPerformerCmb.getUICValue();
            if (mList.actualPerformer == null) {
                comMSG.dispErrorDialog("Please select a actualPerformer!");
                return false;
            }
            this.filterLbl.setText(VwMonitoringList.KEY_ACTUAL_PERFORMER + ": " +
                                    mList.actualPerformer);

            break;
        case 7:
            mList.filterType = VwMonitoringList.KEY_ACTUAL_DATE;
            mList.actualStartDate = (Date)this.actualStartDate.getUICValue();
            mList.actualFinishDate = (Date)this.actualFinishDate.getUICValue();
            if (!(mList.actualStartDate != null && mList.actualFinishDate != null)) {
                comMSG.dispErrorDialog(
                    "Please input actualStartDate and actualFinishDate!");
                return false;
            }
            this.filterLbl.setText(VwMonitoringList.KEY_ACTUAL_DATE + ": " +
                                   comDate.dateToString(mList.actualStartDate) +
                                   " ~ " +
                                   comDate.dateToString(mList.actualFinishDate));
            break;
        }
        return true;
    }
}
