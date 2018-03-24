package client.essp.pms.activity;

import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import javax.swing.JComboBox;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import client.framework.view.vwcomp.VWJLabel;
import c2s.essp.pms.wbs.DtoWbsActivity;
import java.util.List;
import c2s.dto.ITreeNode;
import client.framework.view.vwcomp.VWJTreeTable;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.awt.event.ActionEvent;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.common.comMSG;
import com.wits.util.ProcessVariant;

/**
 * <p>Title:Activity Filter </p>
 *
 * <p>Description:Activity Filter Implement </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author:Robin
 * @version 1.0
 */
public class VwActivityFilter extends VWWorkArea implements IVWPopupEditorEvent {
    private VWJComboBox conditionSel = new VWJComboBox();
    final static String[] FILTER_CONDITION = {"All Activity",
                                             "Uncompleted Activity",
                                             "Completed Activity",
                                             "Activity Manager",
                                             "Duration", "Milestone"};
    private VWJTreeTable table;
    private String currentFilter;
    private VwActivityList activityList;
    VWJLabel filterLbl = new VWJLabel();
    VWJLabel managerLbl = new VWJLabel();
    VWJText managerTxt = new VWJText();
    String manager;
    String tooltip = "";

    public VwActivityFilter(VWJTreeTable table, VwActivityList activityList,
                            VWJLabel currentFilterLbl) {
        this.table = table;
        this.activityList = activityList;
        this.filterLbl = currentFilterLbl;
        this.currentFilter = currentFilterLbl.getText();
        init();
    }

    public VwActivityFilter(VWJTreeTable table, VwActivityList activityList) {
        this(table, activityList, null);
    }

    //初始化画面布局
    public void init() {
        this.setLayout(null);
        VWJLabel filterLbl = new VWJLabel();
        filterLbl.setText("Please select filter condition");
        filterLbl.setSize(200, 20);
        filterLbl.setLocation(50, 10);
        this.add(filterLbl);

        VMComboBox conditionSelModel = VMComboBox.toVMComboBox(VwActivityFilter.
            FILTER_CONDITION, VwActivityFilter.FILTER_CONDITION);
        conditionSel.setModel(conditionSelModel);
        conditionSel.setSize(200, 20);
        conditionSel.setLocation(50, 35);
        this.add(conditionSel);

        managerLbl.setText("Manager:");
        managerLbl.setSize(60, 20);
        managerLbl.setLocation(50, 65);
        this.add(managerLbl);
        managerLbl.setVisible(false);

        managerTxt.setSize(140, 20);
        managerTxt.setLocation(110, 65);
        this.add(managerTxt);
        managerTxt.setVisible(false);

        if (this.currentFilter != null && this.currentFilter.length() > 0) {
            conditionSel.setSelectedItem(this.currentFilter);
            int index = 0;
            for (int i = 0; i < FILTER_CONDITION.length; i++) {
                if (this.currentFilter.equals(FILTER_CONDITION[i])) {
                    index = i;
                    break;
                }
            }
            conditionSel.setSelectedIndex(index);
            if (currentFilter.equals(FILTER_CONDITION[3])) {
                managerLbl.setVisible(true);
                managerTxt.setVisible(true);
                managerLbl.setText("Manager");
                managerTxt.requestFocus();
            } else if (currentFilter.equals(FILTER_CONDITION[4])) {
                managerLbl.setVisible(true);
                managerLbl.setText("<=(day)");
                managerTxt.setVisible(true);
                managerTxt.requestFocus();

            }
        } else {
            conditionSel.setSelectedIndex(0);
            currentFilter = FILTER_CONDITION[0];
        }
        conditionSel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                currentFilter = conditionSel.getUICValue().toString();
                if (currentFilter.equals(FILTER_CONDITION[3])) {
                    //输入管理者
                    managerLbl.setVisible(true);
                    managerTxt.setVisible(true);
                    managerLbl.setText("Manager");
                    managerTxt.requestFocus();
                } else if (currentFilter.equals(FILTER_CONDITION[4])) {
                    //输入工期
                    managerLbl.setVisible(true);
                    managerLbl.setText("<=(day)");
                    managerTxt.setVisible(true);
                    managerTxt.requestFocus();
                } else {
                    managerLbl.setVisible(false);
                    managerTxt.setVisible(false);
                }
            } //Method end
        });
    }

    public void filterUncompeleted(DtoWbsTreeNode node) {
        DtoWbsActivity wbs = (DtoWbsActivity) node.getDataBean();
        if (!node.isLeaf()) {
            List childs = node.children();
            for (int i = childs.size(); i > 0; i--) {
                filterUncompeleted((DtoWbsTreeNode) childs.get(i - 1));
            }
        }
        if (wbs.getCompleteRate() != null &&
            (wbs.getCompleteRate().equals(new Double("100.00")) || wbs.isWbs()) &&
            node.isLeaf() && node.getParent() != null) {
            node.getParent().deleteChild(node);
        }
    }

    public void filterCompeleted(DtoWbsTreeNode node) {
        DtoWbsActivity wbs = (DtoWbsActivity) node.getDataBean();
        if (!node.isLeaf()) {
            List childs = node.children();
            for (int i = childs.size(); i > 0; i--) {
                filterCompeleted((DtoWbsTreeNode) childs.get(i - 1));
            }
        }
        if ((wbs.getCompleteRate() == null ||
             !wbs.getCompleteRate().equals(new Double("100.00")) || wbs.isWbs()) &&
            node.isLeaf() && node.getParent() != null) {
            node.getParent().deleteChild(node);
        }
    }

    public void filterMilestone(DtoWbsTreeNode node) {
        DtoWbsActivity wbs = (DtoWbsActivity) node.getDataBean();
        if (!node.isLeaf()) {
            List childs = node.children();
            for (int i = childs.size(); i > 0; i--) {
                filterMilestone((DtoWbsTreeNode) childs.get(i - 1));
            }
        }
        if ((wbs.isWbs() || wbs.isMilestone() == null ||
             !wbs.isMilestone().booleanValue()) && node.isLeaf() &&
            node.getParent() != null) {
            node.getParent().deleteChild(node);
        }
    }

    public void filterManager(DtoWbsTreeNode node) {
        DtoWbsActivity wbs = (DtoWbsActivity) node.getDataBean();
        if (!node.isLeaf()) {
            List childs = node.children();
            for (int i = childs.size(); i > 0; i--) {
                filterManager((DtoWbsTreeNode) childs.get(i - 1));
            }
        }
        if (((wbs.getManager() == null || wbs.getManager().equals("") ||
              !wbs.getManager().equals(this.manager)) || wbs.isWbs()) &&
            node.isLeaf() && node.getParent() != null) {
            node.getParent().deleteChild(node);
        }
    }

    public void filterTimeLimit(DtoWbsTreeNode node, Double timeLimit) {
        DtoWbsActivity wbs = (DtoWbsActivity) node.getDataBean();
        if (!node.isLeaf()) {
            List childs = node.children();
            for (int i = childs.size(); i > 0; i--) {
                filterTimeLimit((DtoWbsTreeNode) childs.get(i - 1), timeLimit);
            }
        }
        if (((wbs.getTimeLimit().compareTo(timeLimit) >= 0) || wbs.isWbs()) &&
            node.isLeaf() && node.getParent() != null) {
            node.getParent().deleteChild(node);
        }
    }

    public boolean onClickOK(ActionEvent e) {
        DtoWbsTreeNode root = new DtoWbsTreeNode(null);
        if (currentFilter.equals(FILTER_CONDITION[0])) {
            //选取的是所有的Activity
            root = activityList.getBackRoot();
        } else if (currentFilter.equals(FILTER_CONDITION[1])) {
            //未完成Activity
            root = activityList.getBackRoot();
            filterUncompeleted(root);
        } else if (currentFilter.equals(FILTER_CONDITION[2])) {
            //已完成Activity
            root = activityList.getBackRoot();
            filterCompeleted(root);
        } else if (currentFilter.equals(FILTER_CONDITION[3])) {
            //选的是根据Activity Manager来查找
            this.manager = managerTxt.getText();
            if (this.manager != null && !this.manager.equals("")) {
                root = activityList.getBackRoot();
                filterManager(root);
            } else {
                comMSG.dispErrorDialog("Please input manager!");
                return false;
            }
            tooltip = ":" + manager;
        } else if (currentFilter.equals(FILTER_CONDITION[4])) {
            //根据timeLimit时间范围来过滤
            this.manager = managerTxt.getText();
            if (this.manager != null && !this.manager.equals("")) {
                if (this.manager.matches("[0-9]+[.]?[0-9]{0,2}")) {
                    root = activityList.getBackRoot();
                    filterTimeLimit(root, new Double(manager));
                } else {
                    comMSG.dispErrorDialog(
                        "Please input the correct floating number form!");
                    return false;
                }
            } else {
                comMSG.dispErrorDialog("Please input duration!");
                return false;
            }
            tooltip = "<=" + manager + " (days)";
        } else if (currentFilter.equals(FILTER_CONDITION[5])) {
            //过滤非Milestone
            root = activityList.getBackRoot();
            filterMilestone(root);
        }
        table.setRoot(root);
        filterLbl.setText(currentFilter);
        filterLbl.setToolTipText("Filter By:" + currentFilter + tooltip);
        ProcessVariant.set("activitytree", root);

        return true;

    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

}
