package client.essp.pms.modifyplan;

import c2s.essp.pms.wbs.DtoWbsActivity;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJText;
import java.awt.event.ActionEvent;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import client.essp.common.view.VWWorkArea;
import java.awt.event.ItemListener;
import client.framework.view.vwcomp.VWJTreeTable;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import java.util.List;
import client.framework.view.common.comMSG;
import java.awt.event.ItemEvent;
import client.framework.view.vwcomp.VWJDate;
import c2s.dto.ITreeNode;
import java.util.Date;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.essp.common.calendar.WorkCalendar;
import java.util.Calendar;
import com.wits.util.comDate;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwBLMonitorFilter extends VWWorkArea implements
    IVWPopupEditorEvent {
    public VwBLMonitorFilter() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private VWJComboBox conditionSel = new VWJComboBox();
    final static String[] FILTER_CONDITION = {"All Task", "Uncompleted Task",
                                             "Completed Task", "Duration",
                                             "Date Scope", "Manager",
                                             "Milestone"};
    private VWJTreeTable table;
    private String currentFilter;
    private VwBLMonitoringList planList;
    VWJLabel filterLbl = new VWJLabel();
    VWJLabel managerLbl = new VWJLabel();
    VWJLabel otherLbl = new VWJLabel();
    VWJLabel timeLbl = new VWJLabel();
    VWJLabel startLbl = new VWJLabel();
    VWJText managerTxt = new VWJText();
    VWJText timeTxt = new VWJText();
    VWJDate startDate = new VWJDate();
    VWJDate finishDate = new VWJDate();
    String manager;
    String toolTip = "";

    public VwBLMonitorFilter(VWJTreeTable table, VwBLMonitoringList planList,
                             VWJLabel currentFilterLbl) {
        this.table = table;
        this.planList = planList;
        this.filterLbl = currentFilterLbl;
        this.currentFilter = currentFilterLbl.getText();
        init();
    }

    public VwBLMonitorFilter(VWJTreeTable table, VwBLMonitoringList planList) {
        // this.table = table;
        //    this.planList = planList;
        //   init();
        this(table, planList, null);
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
            VwBLMonitorFilter.
            FILTER_CONDITION, VwBLMonitorFilter.FILTER_CONDITION);
        conditionSel.setModel(conditionSelModel);
        conditionSel.setSize(200, 20);
        conditionSel.setLocation(50, 35);
        this.add(conditionSel);

        managerLbl.setText("Manager:");
        managerLbl.setSize(60, 20);
        managerLbl.setLocation(50, 65);
        this.add(managerLbl);
        managerLbl.setVisible(false);

        timeLbl.setText("<=(date):");
        timeLbl.setSize(60, 20);
        timeLbl.setLocation(50, 65);
        this.add(timeLbl);
        timeLbl.setVisible(false);

        startLbl.setText("Start Date");
        startLbl.setSize(85, 20);
        startLbl.setLocation(50, 65);
        this.add(startLbl);
        startLbl.setVisible(false);

        otherLbl.setText("Finish Date");
        otherLbl.setSize(85, 20);
        otherLbl.setLocation(50, 90);
        this.add(otherLbl);
        otherLbl.setVisible(false);

        managerTxt.setSize(135, 20);
        managerTxt.setLocation(115, 65);
        this.add(managerTxt);
        managerTxt.setVisible(false);

        timeTxt.setSize(135, 20);
        timeTxt.setLocation(115, 65);
        this.add(timeTxt);
        timeTxt.setVisible(false);

        startDate.setSize(130, 20);
        startDate.setLocation(125, 65);
        startDate.setCanSelect(true);
        this.add(startDate);
        startDate.setVisible(false);

        finishDate.setSize(130, 20);
        finishDate.setLocation(125, 90);
        finishDate.setCanSelect(true);
        this.add(finishDate);
        finishDate.setVisible(false);

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
            //Duration
            if (currentFilter.equals(FILTER_CONDITION[3])) {
                timeLbl.setVisible(true);
                timeLbl.setText("<=(day)");
                timeTxt.setVisible(true);
                timeTxt.requestFocus();
            } else if (currentFilter.equals(FILTER_CONDITION[4])) {
                //Date scope
                WorkCalendar wc = WrokCalendarFactory.clientCreate();
                Calendar cal = Calendar.getInstance();
                ITreeNode root = (ITreeNode) planList.getModel().getRoot();
                DtoWbsActivity dto = (DtoWbsActivity) root.getDataBean();
                Date sDate = wc.getNextBEWeekDay(cal, -1)[0].getTime();
                Date eDate = wc.getNextBEWeekDay(cal, 1)[1].getTime();

                if (dto.getPlannedStart().compareTo(sDate) > 0) {
                    sDate = dto.getPlannedStart();
                }
                if (dto.getPlannedFinish().compareTo(eDate) < 0) {
                    eDate = dto.getPlannedFinish();
                }
                startDate.setUICValue(sDate);
                finishDate.setUICValue(eDate);
                timeLbl.setVisible(true);
                timeLbl.setText("Start Date");
                startDate.setVisible(true);
                otherLbl.setText("Finish Date");
                otherLbl.setVisible(true);
                finishDate.setVisible(true);
            } else if (currentFilter.equals(FILTER_CONDITION[5])
                ) {
                //Activity Manager or Wbs Manager
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
                    //输入工期
                    timeLbl.setVisible(true);
                    timeLbl.setText("<=(day)");
                    timeTxt.setVisible(true);
                    timeTxt.requestFocus();
                    //close other component
                    startDate.setVisible(false);
                    otherLbl.setVisible(false);
                    finishDate.setVisible(false);
                    startLbl.setVisible(false);
                } else if (currentFilter.equals(FILTER_CONDITION[4])) {
                    //Date scope
                    WorkCalendar wc = WrokCalendarFactory.clientCreate();
                    Calendar cal = Calendar.getInstance();
                    ITreeNode root = (ITreeNode) planList.getModel().getRoot();
                    DtoWbsActivity dto = (DtoWbsActivity) root.getDataBean();
                    Date sDate = wc.getNextBEWeekDay(cal, -1)[0].getTime();
                    Date eDate = wc.getNextBEWeekDay(cal, 1)[1].getTime();
                    if (dto.getPlannedStart().compareTo(sDate) > 0) {
                        sDate = dto.getPlannedStart();
                    }
                    if (dto.getPlannedFinish().compareTo(eDate) < 0) {
                        eDate = dto.getPlannedFinish();
                    }
                    startDate.setUICValue(sDate);
                    finishDate.setUICValue(eDate);
                    startLbl.setVisible(true);
                    startLbl.setText("Start Date");
                    startDate.setVisible(true);
                    otherLbl.setText("Finish Date");
                    otherLbl.setVisible(true);
                    finishDate.setVisible(true);
                    //close other component
                    timeLbl.setVisible(false);
                    timeTxt.setVisible(false);
                    managerTxt.setVisible(false);
                    managerLbl.setVisible(false);
                } else if (currentFilter.equals(FILTER_CONDITION[5])
                    ) {
                    managerLbl.setVisible(true);
                    managerTxt.setVisible(true);
                    managerTxt.requestFocus();
                    //close other component
                    timeLbl.setVisible(false);
                    timeTxt.setVisible(false);
                    startDate.setVisible(false);
                    otherLbl.setVisible(false);
                    finishDate.setVisible(false);
                    startLbl.setVisible(false);
                } else {
                    managerLbl.setVisible(false);
                    managerTxt.setVisible(false);
                    startDate.setVisible(false);
                    otherLbl.setVisible(false);
                    finishDate.setVisible(false);
                    timeLbl.setVisible(false);
                    timeTxt.setVisible(false);
                    startLbl.setVisible(false);
                }
            } //Method end
        });
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

    public boolean hasWbsChildNode(DtoWbsTreeNode node) {
        if (!node.isLeaf()) {
            List childs = node.children();
            for (int i = childs.size(); i > 0; i--) {
                DtoWbsActivity wbs = (DtoWbsActivity) ((ITreeNode) childs.get(i -
                    1)).getDataBean();
                if (wbs.isWbs()) {
                    return true;
                }
            }
        }
        return false;
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
            wbs.getCompleteRate().equals(new Double("100.00")) && node.isLeaf() &&
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
        if (wbs.getCompleteRate() != null &&
            !wbs.getCompleteRate().equals(new Double("100.00")) && node.isLeaf() &&
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
        if (((wbs.getTimeLimit().compareTo(timeLimit) > 0)) &&
            node.isLeaf() && node.getParent() != null) {
            node.getParent().deleteChild(node);
        }
    }

    public void filterDateScope(DtoWbsTreeNode node) {
        DtoWbsActivity wbs = (DtoWbsActivity) node.getDataBean();
        if (!node.isLeaf()) {
            List childs = node.children();
            for (int i = childs.size(); i > 0; i--) {
                filterDateScope((DtoWbsTreeNode) childs.get(i - 1));
            }
        }
        if (wbs.getPlannedStart() != null && wbs.getPlannedFinish() != null) {
            if ((Date) startDate.getUICValue() != null &&
                (Date) finishDate.getUICValue() != null) {
                Date start = (Date) startDate.getUICValue();
                Date finish = (Date) finishDate.getUICValue();
                int i = wbs.getPlannedStart().compareTo(finish);
                int j = wbs.getPlannedFinish().compareTo(start);
                if ((i > 0 || j < 0) && node.isLeaf() && node.getParent() != null) {
                    node.getParent().deleteChild(node);
                }
            } else if ((Date) startDate.getUICValue() == null &&
                       (Date) finishDate.getUICValue() != null) {
                Date finish = (Date) finishDate.getUICValue();
                int i = wbs.getPlannedStart().compareTo(finish);
                if (i > 0 && node.isLeaf() && node.getParent() != null) {
                    node.getParent().deleteChild(node);
                }
            } else if ((Date) finishDate.getUICValue() == null &&
                       (Date) startDate.getUICValue() != null) {
                Date start = (Date) startDate.getUICValue();
                int j = wbs.getPlannedFinish().compareTo(start);
                if (j < 0 && node.isLeaf() && node.getParent() != null) {
                    node.getParent().deleteChild(node);
                }
            }
        }
    }


    public boolean onClickOK(ActionEvent e) {
        DtoWbsTreeNode root = new DtoWbsTreeNode(null);
        if (currentFilter.equals(FILTER_CONDITION[0])) {
            //选取的是所有的Task
            root = planList.getBackRoot();
        } else if (currentFilter.equals(FILTER_CONDITION[1])) {
            //选取的是未完成的
            root = planList.getBackRoot();
            filterUncompeleted(root);
        } else if (currentFilter.equals(FILTER_CONDITION[2])) {
            //选出完成的
            root = planList.getBackRoot();
            filterCompeleted(root);
        } else if (currentFilter.equals(FILTER_CONDITION[3])) {
            //根据timeLimit时间范围来过滤
            this.manager = timeTxt.getText();
            if (this.manager != null && !this.manager.equals("")) {
                if (this.manager.matches("[0-9]+[.]?[0-9]{0,2}")) {
                    root = planList.getBackRoot();
                    filterTimeLimit(root, new Double(manager));
                    toolTip = "<= " + manager + " (days)";
                } else {
                    comMSG.dispErrorDialog(
                        "Please input the correct floating number form!");
                    return false;
                }
            } else {
                comMSG.dispErrorDialog("Please input duration!");
                return false;
            }
        } else if (currentFilter.equals(FILTER_CONDITION[4])) {
            root = planList.getBackRoot();
            Date start = (Date) startDate.getUICValue();
            DtoWbsActivity rDto = (DtoWbsActivity) root.getDataBean();
            if (start == null) {
                start = rDto.getPlannedStart();
            }
            Date finish = (Date) finishDate.getUICValue();
            if (finish == null) {
                finish = rDto.getPlannedFinish();
            }
            filterDateScope(root);
            toolTip = ":" + comDate.dateToString(start) + " ~~ " +
                      comDate.dateToString(finish);
        } else if (currentFilter.equals(FILTER_CONDITION[5])) {
            //选的是根据Activity Manager来查找
            this.manager = managerTxt.getText();
            if (this.manager != null && !this.manager.equals("")) {
                root = planList.getBackRoot();
                filterManager(root);
                toolTip = ":" + manager;
            } else {
                comMSG.dispErrorDialog("Please input manager!");
                return false;
            }
        } else if (currentFilter.equals(FILTER_CONDITION[6])) {
            //milstone
            root = planList.getBackRoot();
            filterMilestone(root);
        }
        table.setRoot(root);
        filterLbl.setText(currentFilter);
        filterLbl.setToolTipText("Filter By:" + currentFilter + toolTip);
        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public void jbInit() throws Exception {
    }

}
