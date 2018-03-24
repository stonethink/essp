package client.essp.pms.wbs;

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
 * <p>Title:WBS Filter </p>
 *
 * <p>Description:WBS Filter Implement </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author:Robin
 * @version 1.0
 */
public class VwWbsFilter extends VWWorkArea implements IVWPopupEditorEvent {
    private VWJComboBox conditionSel = new VWJComboBox();
    final static String[] FILTER_CONDITION = {"All WBS",
                                             "Uncompleted WBS", "Completed WBS",
                                             "WBS Manager"};
    private VWJTreeTable table;
    private String currentFilter;
    private VwWbsList wbsList;
    VWJLabel filterLbl = new VWJLabel();
    VWJLabel managerLbl = new VWJLabel();
    VWJText managerTxt = new VWJText();
    String manager;
    String tooltip = "";

    public VwWbsFilter(VWJTreeTable table, VwWbsList wbsList,
                       VWJLabel currentFilterLbl) {
        this.table = table;
        this.wbsList = wbsList;
        this.filterLbl = currentFilterLbl;
        this.currentFilter = currentFilterLbl.getText();
        init();
    }

    public VwWbsFilter(VWJTreeTable table, VwWbsList wbsList) {
        this(table, wbsList, null);
    }

    //初始化画面布局
    public void init() {
        this.setLayout(null);
        VWJLabel filterLbl = new VWJLabel();
        filterLbl.setText("Please select filter condition");
        filterLbl.setSize(200, 20);
        filterLbl.setLocation(50, 10);
        this.add(filterLbl);

        VMComboBox conditionSelModel = VMComboBox.toVMComboBox(VwWbsFilter.
            FILTER_CONDITION, VwWbsFilter.FILTER_CONDITION);
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
            }

        } else {
            conditionSel.setSelectedIndex(0);
            currentFilter = FILTER_CONDITION[0];
        }
        conditionSel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                currentFilter = conditionSel.getUICValue().toString();
                if (currentFilter.equals(FILTER_CONDITION[3])) {
                    managerLbl.setVisible(true);
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
        if (wbs.getCompleteRate().equals(new Double("100.00")) && node.isLeaf() &&
            node.getParent() != null) {
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
        if (!wbs.getCompleteRate().equals(new Double("100.00")) && node.isLeaf() &&
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
        if ((wbs.getManager() == null || wbs.getManager().equals("") ||
             !wbs.getManager().equals(this.manager)) &&
            node.isLeaf() &&
            node.getParent() != null) {
            node.getParent().deleteChild(node);
        }
    }


    public boolean onClickOK(ActionEvent e) {
        DtoWbsTreeNode root = new DtoWbsTreeNode(null);
        if (currentFilter.equals(FILTER_CONDITION[0])) {
            //选取的是所有的WBS
            root = wbsList.getBackRoot();
        } else if (currentFilter.equals(FILTER_CONDITION[1])) {
            //未完成WBS
            root = wbsList.getBackRoot();
            filterUncompeleted(root);
        } else if (currentFilter.equals(FILTER_CONDITION[2])) {
            //已完成WBS
            root = wbsList.getBackRoot();
            filterCompeleted(root);
        } else if (currentFilter.equals(FILTER_CONDITION[3])) {
            //选的是根据WBS Manager来查找
            this.manager = managerTxt.getText();
            if (this.manager != null && !this.manager.equals("")) {
                root = wbsList.getBackRoot();
                filterManager(root);
            } else {
                comMSG.dispErrorDialog("Please input manager!");
                return false;
            }
            tooltip = ":"+manager;
        }
        table.setRoot(root);
        filterLbl.setText(currentFilter);
        filterLbl.setToolTipText("Filter By:"+currentFilter + tooltip);
        ProcessVariant.set("wbstree", root);
        return true;

    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

}
