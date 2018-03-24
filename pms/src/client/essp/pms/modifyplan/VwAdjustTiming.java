package client.essp.pms.modifyplan;

import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWJDate;
import java.awt.event.ActionEvent;
import client.framework.model.VMComboBox;
import com.wits.util.TestPanel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import client.framework.view.vwcomp.VWJTreeTable;
import client.framework.view.common.comMSG;
import c2s.essp.pms.wbs.DtoWbsTreeNode;
import c2s.essp.pms.wbs.DtoWbsActivity;
import java.util.Date;

import java.util.List;
import c2s.dto.IDto;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;

/**
 * <p>Title: Adjust timing</p>
 *
 * <p>Description: Adjust timing of wbs and activity</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author lipeng.xu
 * @version 1.0
 */
public class VwAdjustTiming extends VWWorkArea implements IVWPopupEditorEvent {
    public VwAdjustTiming() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private VWJComboBox conditionSel = new VWJComboBox();
    final static String[] FILTER_CONDITION = {"Move plan date ±N days",
                                             "Move start date ±N days",
                                             "Move finish date ±N days",
                                             "Move start date to",
                                             "Move finish date to",
                                             "Customize"};
    final static int ADJUST_WHOLE = 0;
    final static int ADJUST_START = 1;
    final static int ADJUST_FINISH = 2;
    final static int SET_START = 3;
    final static int SET_FINISH = 4;
    final static int SET_BOTH = 5;

    private VWJTreeTable treeTable;
    private DtoWbsTreeNode root;
    private int offset = 0;
    private Date inputStart = null;
    private Date inputFinish = null;
    private boolean flagClose = false;

    VWJLabel attentionLbl = new VWJLabel();
    VWJLabel middleLbl = new VWJLabel();
    VWJText offSetTxt = new VWJText();
    VWJDate startDate = new VWJDate();
    VWJDate finishDate = new VWJDate();

    public VwAdjustTiming(VWJTreeTable tree) {
        try {
            this.treeTable = tree;
            root = (DtoWbsTreeNode) tree.getSelectedNode();
            init();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //初始化画面布局
    public void init() throws Exception {
        this.setLayout(null);
        VWJLabel titleLbl = new VWJLabel();
        titleLbl.setText("Please select adjust type");
        titleLbl.setSize(200, 20);
        titleLbl.setLocation(50, 10);
        this.add(titleLbl);

        VMComboBox conditionSelModel = VMComboBox.toVMComboBox(
            FILTER_CONDITION, FILTER_CONDITION);
        conditionSel.setModel(conditionSelModel);
        conditionSel.setSize(200, 20);
        conditionSel.setLocation(50, 35);
        this.add(conditionSel);
        conditionSel.setUICValue(FILTER_CONDITION[0]);

        attentionLbl.setText("OffSet days");
        attentionLbl.setSize(100, 20);
        attentionLbl.setLocation(50, 65);
        this.add(attentionLbl);

        middleLbl.setText("Finish date");
        middleLbl.setSize(100, 20);
        middleLbl.setLocation(50, 90);
        middleLbl.setVisible(false);
        this.add(middleLbl);

        offSetTxt.setSize(100, 20);
        offSetTxt.setLocation(150, 65);
        this.add(offSetTxt);

        startDate.setSize(100, 20);
        startDate.setLocation(150, 65);
        startDate.setCanSelect(true);
        this.add(startDate);
        startDate.setVisible(false);

        finishDate.setSize(100, 20);
        finishDate.setLocation(150, 90);
        finishDate.setCanSelect(true);
        this.add(finishDate);
        finishDate.setVisible(false);

        DtoWbsActivity wbs = (DtoWbsActivity) root.getDataBean();
        startDate.setUICValue(wbs.getPlannedStart());
        finishDate.setUICValue(wbs.getPlannedFinish());
        offSetTxt.setText("0");
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() throws Exception {
        conditionSel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                switch (conditionSel.getSelectedIndex()) {
                case ADJUST_WHOLE:
                case ADJUST_START:
                case ADJUST_FINISH:
                    setOffSetModel();
                    break;
                case SET_START:
                    setStartModel();
                    break;
                case SET_FINISH:
                    setFinishModel();
                    break;
                case SET_BOTH:
                    setBothModel();
                    break;
                }
            }
        });

    }

    private void setOffSetModel() {
        attentionLbl.setText("OffSet days");
        startDate.setVisible(false);
        finishDate.setVisible(false);
        middleLbl.setVisible(false);
        offSetTxt.setVisible(true);
    }

    private void setStartModel() {
        attentionLbl.setText("Start date");
        startDate.setVisible(true);
        finishDate.setVisible(false);
        middleLbl.setVisible(false);
        offSetTxt.setVisible(false);
    }

    private void setFinishModel() {
        attentionLbl.setText("Finish date");
        finishDate.setLocation(150, 65);
        startDate.setVisible(false);
        finishDate.setVisible(true);
        middleLbl.setVisible(false);
        offSetTxt.setVisible(false);
    }

    private void setBothModel() {
        attentionLbl.setText("Start date");
        finishDate.setLocation(150, 90);
        startDate.setVisible(true);
        finishDate.setVisible(true);
        middleLbl.setVisible(true);
        offSetTxt.setVisible(false);

    }

    /**
     * 整体平移以node为根的树计划日期 offset 天（正数为后移，负数为前移）。
     * @param node DtoWbsTreeNode
     */
    private void adjustWhole(DtoWbsTreeNode node, boolean moveCompleted) {
        DtoWbsActivity wbs = (DtoWbsActivity) node.getDataBean();
        Date start = wbs.getPlannedStart();
        Date finish = wbs.getPlannedFinish();

        //moveCompleted = false, 且已经完成了的就不移动（应用于扩展时的子节点移动）
        
        if (((moveCompleted == false &&
              wbs.getCompleteRate().equals(new Double("100.00")))) == false) {
            if (start != null) {
                wbs.setPlannedStart(WrokCalendarFactory.clientCreate().getNextNDay(start,
                    offset));
                if (IDto.OP_NOCHANGE.equals(wbs.getOp())) {
                    wbs.setOp(IDto.OP_MODIFY);
                }
            }
            if (finish != null) {
                wbs.setPlannedFinish(WrokCalendarFactory.clientCreate().getNextNDay(finish,
                    offset));
                if (IDto.OP_NOCHANGE.equals(wbs.getOp())) {
                    wbs.setOp(IDto.OP_MODIFY);
                }
            }
        }
        if (node.isLeaf() == false) {
            List childs = node.children();
            int count = childs.size();
            for (int i = count; i > 0; i--) {
                adjustWhole((DtoWbsTreeNode) childs.get(i - 1), moveCompleted);
            }

        }
    }

    /**
     * 计划开始日期移动 offset 天（正数为后移，负数为前移）。
     */
    private boolean adjustStart() {
        DtoWbsActivity rWbs = (DtoWbsActivity) root.getDataBean();
        inputStart = WrokCalendarFactory.clientCreate().getNextNDay(
            rWbs.getPlannedStart(), offset);
        Date rFinish = rWbs.getPlannedFinish();
        if (inputStart.compareTo(rFinish) > 0) {
            comMSG.dispErrorDialog(
                "Offset is too big!");
            return false;
        }
        return setStart();
    }

    private boolean adjustFinish() {
        DtoWbsActivity rWbs = (DtoWbsActivity) root.getDataBean();
        inputFinish = WrokCalendarFactory.clientCreate().getNextNDay(
            rWbs.getPlannedFinish(), offset);
        Date rStart = rWbs.getPlannedStart();
        if (rStart.compareTo(inputFinish) > 0) {
            comMSG.dispErrorDialog(
                "Offset is too small!");
            return false;
        }
        return setFinish();
    }

    /**
     * 压缩以node为根的树计划日期。
     * 各子节点要在压缩后的计划日期之内。
     *
     * @param node DtoWbsTreeNode
     */
    private void setStartZip(DtoWbsTreeNode node) {
        DtoWbsActivity wbs = (DtoWbsActivity) node.getDataBean();
        Date start = wbs.getPlannedStart();
        //如果当前节点超出了root的计划日期之，则向后移。
        if (start != null && inputStart.compareTo(start) > 0) {
            int off = WrokCalendarFactory.clientCreate().getDayNum(start, inputStart);
            Date finish = wbs.getPlannedFinish();
            if (finish != null) {
                Date cFinish = WrokCalendarFactory.clientCreate().getNextNDay(finish, off);
                Date rFinish = ((DtoWbsActivity) root.getDataBean()).
                               getPlannedFinish();
                //限制结束日期不得超越root结束日期
                if (cFinish.compareTo(rFinish) > 0) {
                    wbs.setPlannedFinish(rFinish);
                } else {
                    wbs.setPlannedFinish(cFinish);
                }
            }
            wbs.setPlannedStart(inputStart);
            double tl = wbs.getTimeLimit().doubleValue() - off;
            wbs.setTimeLimit(new Double(tl));
            if (IDto.OP_NOCHANGE.equals(wbs.getOp())) {
                wbs.setOp(IDto.OP_MODIFY);
            }
        }
        //递归子节点
        if (node.isLeaf() == false) {
            List childs = node.children();
            int count = childs.size();
            for (int i = count; i > 0; i--) {
                setStartZip((DtoWbsTreeNode) childs.get(i - 1));
            }
        }
    }

    /**
     * 设置计划开始日期，设置成功返回true，否则false。
     * @return boolean
     */
    private boolean setStart() {
        DtoWbsActivity wbs = (DtoWbsActivity) root.getDataBean();
        Date start = wbs.getPlannedStart();
        if (start != null && inputStart.compareTo(start) > 0) { //压缩
            if (canZip(root, true) == false) {
                return false;
            }
            wbs.setPlannedStart(inputStart);
            if (IDto.OP_NOCHANGE.equals(wbs.getOp())) {
                wbs.setOp(IDto.OP_MODIFY);
            }
            //压缩子节点
            if (root.isLeaf() == false) {
                List childs = root.children();
                int count = childs.size();
                for (int i = count; i > 0; i--) {
                    setStartZip((DtoWbsTreeNode) childs.get(i - 1));
                }
            }
        } else if (start != null && inputStart.compareTo(start) < 0) { //扩展
            wbs.setPlannedStart(inputStart);
            if (IDto.OP_NOCHANGE.equals(wbs.getOp())) {
                wbs.setOp(IDto.OP_MODIFY);
            }
            //所有的子节点扩大相同偏移量（动Start）
            int off = WorkCalendar.getDayNum(start, inputStart);
            List allChildren = root.listAllChildrenByPostOrder();
            for (int i = 0; i < allChildren.size(); i++) {
                DtoWbsActivity child = (DtoWbsActivity) ((DtoWbsTreeNode)allChildren.get(i)).getDataBean();
                Date childSart = child.getPlannedStart();
                if (childSart != null
                    && (child.getTimeLimitType() == null
                        || child.getTimeLimitType().equals(DtoWbsActivity.
                    ACTIVITY_TIME_LIMIT_TYPE[1]) == false)
                    && (child.getCompleteRate() == null ||
                        child.getCompleteRate().equals(new Double("100.00")) == false)) {
                    Date cStart = WrokCalendarFactory.clientCreate().getNextNDay(childSart,
                        off);
                    child.setPlannedStart(cStart);
                    if (IDto.OP_NOCHANGE.equals(child.getOp())) {
                        child.setOp(IDto.OP_MODIFY);
                    }
                }
            }
            //扩展祖先节点
            DtoWbsTreeNode pNode = (DtoWbsTreeNode) root.getParent();
            while (pNode != null) {
                DtoWbsActivity pWbs = (DtoWbsActivity) pNode.getDataBean();
                Date pStart = pWbs.getPlannedStart();
                if (pStart != null && inputStart.compareTo(pStart) < 0) {
                    pWbs.setPlannedStart(inputStart);
                    if (IDto.OP_NOCHANGE.equals(pWbs.getOp())) {
                        pWbs.setOp(IDto.OP_MODIFY);
                    }
                }
                pNode = (DtoWbsTreeNode) pNode.getParent();
            }
        }
        treeTable.repaint();
        return true;
    }

    /**
     * 压缩以node为根的树计划日期。
     * 各子节点要在压缩后的计划日期之内。
     *
     * @param node DtoWbsTreeNode
     */
    private void setFinishZip(DtoWbsTreeNode node) {
        DtoWbsActivity wbs = (DtoWbsActivity) node.getDataBean();
        Date finish = wbs.getPlannedFinish();
        //如果当前节点超出了root的计划日期之，则向前移。
        if (finish != null && inputFinish.compareTo(finish) < 0) {
            int off = WorkCalendar.getDayNum(finish, inputFinish);
            Date start = wbs.getPlannedStart();
            if (start != null) {
                Date cStart = WrokCalendarFactory.clientCreate().getNextNDay(start, off);
                Date rStart = ((DtoWbsActivity) root.getDataBean()).
                              getPlannedStart();
                //限制开始日期不得早于root开始日期
                if (cStart.compareTo(rStart) > 0) {
                    wbs.setPlannedStart(cStart);
                } else {
                    wbs.setPlannedStart(rStart);
                }
            }
            wbs.setPlannedFinish(inputFinish);
            double tl = wbs.getTimeLimit().doubleValue() + off;
            wbs.setTimeLimit(new Double(tl));
            if (IDto.OP_NOCHANGE.equals(wbs.getOp())) {
                wbs.setOp(IDto.OP_MODIFY);
            }
        }
        //递归子节点
        if (node.isLeaf() == false) {
            List childs = node.children();
            int count = childs.size();
            for (int i = count; i > 0; i--) {
                setFinishZip((DtoWbsTreeNode) childs.get(i - 1));
            }
        }

    }

    /**
     * 设置计划结束日期，设置成功返回true，否则false。
     * @return boolean
     */
    private boolean setFinish() {
        DtoWbsActivity wbs = (DtoWbsActivity) root.getDataBean();
        Date finish = wbs.getPlannedFinish();
        if (finish != null && inputFinish.compareTo(finish) < 0) { //压缩
            if (canZip(root, false) == false) {
                return false;
            }
            wbs.setPlannedFinish(inputFinish);
            if (IDto.OP_NOCHANGE.equals(wbs.getOp())) {
                wbs.setOp(IDto.OP_MODIFY);
            }
            //压缩子节点
            if (root.isLeaf() == false) {
                List childs = root.children();
                int count = childs.size();
                for (int i = count; i > 0; i--) {
                    setFinishZip((DtoWbsTreeNode) childs.get(i - 1));
                }
            }
        } else if (finish != null && inputFinish.compareTo(finish) > 0) { //扩展
            wbs.setPlannedFinish(inputFinish);
            if (IDto.OP_NOCHANGE.equals(wbs.getOp())) {
                wbs.setOp(IDto.OP_MODIFY);
            }
            //所有的子节点扩大相同偏移量（动Start）
            int off = WorkCalendar.getDayNum(finish, inputFinish);
            List allChildren = root.listAllChildrenByPostOrder();
            for (int i = 0; i < allChildren.size(); i++) {
                DtoWbsActivity child = (DtoWbsActivity) ((DtoWbsTreeNode)allChildren.get(i)).getDataBean();
                Date childFinish = child.getPlannedFinish();
                if (childFinish != null
                    && (child.getTimeLimitType() == null
                        || child.getTimeLimitType().equals(DtoWbsActivity.
                    ACTIVITY_TIME_LIMIT_TYPE[1]) == false)
                    && (child.getCompleteRate() == null
                        || child.getCompleteRate().equals(new Double("100.00")) == false)) {
                    Date cFinish = WrokCalendarFactory.clientCreate().getNextNDay(childFinish,
                        off);
                    child.setPlannedFinish(cFinish);
                    if (IDto.OP_NOCHANGE.equals(child.getOp())) {
                        child.setOp(IDto.OP_MODIFY);
                    }
                }
            }

            //扩展祖先节点
            DtoWbsTreeNode pNode = (DtoWbsTreeNode) root.getParent();
            while (pNode != null) {
                DtoWbsActivity pWbs = (DtoWbsActivity) pNode.getDataBean();
                Date pFinish = pWbs.getPlannedFinish();
                if (pFinish != null && inputFinish.compareTo(pFinish) > 0) {
                    pWbs.setPlannedFinish(inputFinish);
                    if (IDto.OP_NOCHANGE.equals(pWbs.getOp())) {
                        pWbs.setOp(IDto.OP_MODIFY);
                    }
                }
                pNode = (DtoWbsTreeNode) pNode.getParent();
            }
        }
        treeTable.repaint();
        return true;
    }


    /**
     * 判断以node为根的树是否可以压缩。
     * 当含有子节点计划日期超越了node，并且timeLimitType=="changeless"，返回false
     * @param node DtoWbsTreeNode
     * @param isStartModel boolean
     * @return boolean
     */
    private boolean canZip(DtoWbsTreeNode node, boolean isStartModel) {
        DtoWbsActivity wbs = (DtoWbsActivity) node.getDataBean();
        if (wbs.getTimeLimitType().equals(DtoWbsActivity.
                                          ACTIVITY_TIME_LIMIT_TYPE[1])) {
            DtoWbsActivity rWbs = (DtoWbsActivity) root.getDataBean();
            Date rStart = rWbs.getPlannedStart();
            Date rFinish = rWbs.getPlannedFinish();
            Double cPeriod = wbs.getTimeLimit();
            Double rPeriod;
            if (isStartModel) {
                rPeriod = WorkCalendar.calculateTimeLimit(inputStart,
                    rFinish);
            } else {
                rPeriod = WorkCalendar.calculateTimeLimit(rStart,
                    inputFinish);
            }
            if (cPeriod.compareTo(rPeriod) > 0) {
                comMSG.dispErrorDialog("Changeless activity: " + wbs.getName() +
                                       " couldn't be acceptance!");
                return false;
            }
        }
        //递归子节点
        if (node.isLeaf() == false) {
            List childs = node.children();
            int count = childs.size();
            for (int i = count; i > 0; i--) {
                if (canZip((DtoWbsTreeNode) childs.get(i - 1), isStartModel) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 同时设置计划开始日期和计划结束日期。
     * @return boolean
     */
    private boolean setBoth() {
        DtoWbsActivity rWbs = (DtoWbsActivity) root.getDataBean();
        Date rStart = rWbs.getPlannedStart();
        Date rFinish = rWbs.getPlannedFinish();
        boolean sChanged = !(inputStart.equals(rStart));
        boolean fChanged = !(inputFinish.equals(rFinish));
        if (sChanged == false && fChanged == false) {
            return true;
        } else if (sChanged == true && fChanged == false) {
            return setStart();
        } else if (sChanged == false && fChanged == true) {
            return setFinish();
        } else {
            //先对齐开始时间，
            offset = WorkCalendar.getDayNum(rStart, inputStart);
            if (offset != 0) {
                adjustWhole(root, true);
            }
            //再设置结束时间
            return setFinish();
        }
    }


    private boolean checkOffSet() {
        String offSetValue = this.offSetTxt.getText();
        if (offSetValue.matches("-?[0-9]+")) {
            if (offSetValue.equals("0")) {
                flagClose = true;
                return false;
            }
            offset = Integer.parseInt(offSetValue);
            return true;
        } else {
            comMSG.dispErrorDialog(
                "Please input the correct integer number!");
            return false;
        }
    }

    private boolean checkStartDate() {
        if (startDate == null) {
            comMSG.dispErrorDialog(
                "Please input the start date!");
            return false;
        }
        inputStart = (Date) startDate.getUICValue();
        DtoWbsActivity rWbs = (DtoWbsActivity) root.getDataBean();
        Date rFinish = rWbs.getPlannedFinish();
        if (inputStart.compareTo(rFinish) > 0) {
            comMSG.dispErrorDialog(
                "Start date shouldn't be bigger than finish!");
            return false;
        }

        return true;
    }

    private boolean checkFinishDate() {
        if (finishDate == null) {
            comMSG.dispErrorDialog(
                "Please input the finish date!");
            return false;
        }
        inputFinish = (Date) finishDate.getUICValue();
        DtoWbsActivity rWbs = (DtoWbsActivity) root.getDataBean();
        Date rStart = rWbs.getPlannedStart();
        if (rStart.compareTo(inputFinish) > 0) {
            comMSG.dispErrorDialog(
                "Finish date shouldn't be smaller than start!");
            return false;
        }

        return true;
    }

    private boolean checkBothDate() {
        if (startDate == null) {
            comMSG.dispErrorDialog(
                "Please input the start date!");
            return false;
        }
        if (finishDate == null) {
            comMSG.dispErrorDialog(
                "Please input the finish date!");
            return false;
        }
        inputFinish = (Date) finishDate.getUICValue();
        inputStart = (Date) startDate.getUICValue();
        if (inputStart.compareTo(inputFinish) > 0) {
            comMSG.dispErrorDialog(
                "Start date shouldn't be bigger than finish!");
            return false;
        }
        return true;
    }

    /**
     * 1.ADJUST_WHOLE  整体平移： 后移（正数）或前移（负数）offset个工作日。
     * 2.ADJUST_START  调整计划开始日期： 计划开始日期后移（正数）或前移（负数）offset个工作日。
     *            后移：
     *                （1）、检查所有子节点Time limit type 为 Changless 且不能容纳在调整周期之内的报错。
     *                （2）、root开始日期后移offset个工作日，结束日期不变。
     *                （3）、所有自节点开始日期如果越界，则该节点整体后移至开始对齐。
     *            前移：
     *                （1）、root开始日期前移offset个工作日，结束日期不变。
     *                （2）、所有尚未完工的子节点增大offset个工作日。
     *                （3）、root的祖先节点开始日期在此之后的，调整对齐。
     * 3.ADJUST_FINISH  调整计划结束日期：
     *           前移： 类似ADJUST_START 后移。
     *           后移： 类似ADJUST_START 前移。
     * 4.SET_START   设置计划开始日期： 计划开始日期设置到startDate
     *            后移：
     *                （1）、检查所有子节点Time limit type 为 Changless 且不能容纳在调整周期之内的报错。
     *                （2）、root开始日期后移到startDate，结束日期不变。
     *                （3）、所有自节点开始日期如果越界，则该节点整体后移至开始对齐。
     *            前移：
     *                （1）、root开始日期前移到startDate，结束日期不变。
     *                （2）、所有尚未完工的子节点增大时间差值个工作日。
     *                （3）、root的祖先节点开始日期在此之后的，调整对齐。
     *5.SET_FINISH  设置计划结束日期： 计划结束日期设置到finishDate
     *           前移： 类似SET_START 后移。
     *           后移： 类似SET_START 前移的。
     * 6.SET_BOTH   自定义计划开始结束日期： 计划开始日期设置到startDate，束日期设置到finishDate
     *                （1）、如果开始日期变了结束日期没变，调用SET_START
     *                （2）、如果结束日期变了开始日期没变，调用SET_FINISH
     *                （3）、如果开始日期和结束日期都变了，先平移Start对齐，再调用SET_FINISH调整finish
     *                （4）、如果开始日期和结束日期都没变，返回。
     *
     * @param e ActionEvent
     * @return boolean
     */
    public boolean onClickOK(ActionEvent e) {
        switch (conditionSel.getSelectedIndex()) {
        case ADJUST_WHOLE:
            if (checkOffSet()) {
                adjustWhole(root, true);
                treeTable.repaint();
                return true;
            } else {
                if(flagClose) {
                    return true;
                }
                return false;
            }
        case ADJUST_START:
            if (checkOffSet()) {
                return adjustStart();
            } else {
                if(flagClose) {
                    return true;
                }
                return false;
            }
        case ADJUST_FINISH:
            if (checkOffSet()) {
                if(flagClose) {
                    return true;
                }
                return adjustFinish();
            } else {
                return false;
            }
        case SET_START:
            if (checkStartDate()) {
                return setStart();
            } else {
                return false;
            }
        case SET_FINISH:
            if (checkFinishDate()) {
                return setFinish();
            } else {
                return false;
            }
        case SET_BOTH:
            if (checkBothDate()) {
                return setBoth();
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

    public void jbInit() throws Exception {
    }

    public static void main(String[] args) {
        VwAdjustTiming workArea = new VwAdjustTiming(null);
        workArea.setSize(400, 200);
        TestPanel.show(workArea);
    }

}
