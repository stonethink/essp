package client.essp.pms.wbs.activity;

import client.framework.model.VMTable2;
import c2s.essp.pms.wbs.DtoWbsActivity;
import java.util.List;
import client.framework.model.VMColumnConfig;
import java.util.Date;
import client.framework.view.common.comMSG;

import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import java.util.Calendar;
import java.math.BigDecimal;
import c2s.dto.ITreeNode;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Robin
 * @version 1.0
 */
public class VmWbsActivityListModel extends VMTable2 {
    private DtoWbsActivity wbs;
    private boolean locked;
    private ITreeNode treeNode;

    public VmWbsActivityListModel(Object[][] configs) {
        super(configs);
    }

    public void setWbsNode(ITreeNode wbsNode) {
        this.treeNode = wbsNode;
        this.wbs = (DtoWbsActivity) wbsNode.getDataBean();
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (locked == true) {
            return;
        }

        VMColumnConfig columnConfig = (VMColumnConfig)this.getColumnConfigs().
                                      get(columnIndex);
        String dataColName = columnConfig.getDataName();
        DtoWbsActivity activity = (DtoWbsActivity)this.getRow(rowIndex);
        if (dataColName != null &&
            ("plannedFinish".equals(dataColName) ||
             "plannedStart".equals(dataColName))) {
            //如果是计划日期的话，就判断不能超出WBS的边界
            Date tmp = (Date) aValue;
            String type = "finish";
            if ("plannedStart".equals(dataColName)) {
                type = "start";
            }

//            if (!checkDateScope(tmp, type)) {
//                //越界
//                locked = true;
//                comMSG.dispErrorDialog(
//                    "This planned " + type +
//                    " date has surpassed the WBS's plan scope.");
//                locked = false;
//                return;
//            } else
            if (!checkDataOrder(tmp, type, activity)) {
                //未越界，但是计划开始时间比结束时间晚
                locked = true;
                if (type.equals("start")) {
                    comMSG.dispErrorDialog(
                        "Planned start date shouldn't be earlier than finish date!");
                } else {
                    comMSG.dispErrorDialog(
                        "Planned finish date shouldn't be later than start date!");
                }
                locked = false;
                return;

            } else {
                super.setValueAt(aValue, rowIndex, columnIndex);
                if (activity.getPlannedStart() != null &&
                    activity.getPlannedFinish() != null) {
                    Double timeLimit = WorkCalendar.calculateTimeLimit(
                        activity.getPlannedStart(), activity.getPlannedFinish());
                    activity.setTimeLimit(timeLimit);
                    this.fireTableDataChanged();
                }
            }

        } else if (dataColName != null && "timeLimit".equals(dataColName)) {
            //如果是工期的话，就自动计算计划完成时间
            Double timeLimit = new Double(aValue.toString());
//            if (this.wbs.getPlannedStart() != null && this.wbs.getPlannedFinish() != null &&
//                timeLimit.compareTo(CalculateDefaultDate.calculateTimeLimit(
//                    this.wbs.getPlannedStart(), this.wbs.getPlannedFinish())) >
//                0) {
//                //工期范围过长
//                locked = true;
//                comMSG.dispErrorDialog(
//                    "The time limt already surpassed the WBS's plan scope.");
//                locked = false;
//                return;
//            } else
            if (this.wbs.getPlannedStart() != null &&
                this.wbs.getPlannedFinish() != null) {
                if (this.wbs.getPlannedFinish() != null && timeLimit.compareTo(
                    WorkCalendar.
                    calculateTimeLimit(activity.getPlannedStart(),
                                       this.wbs.getPlannedFinish())) > 0) {
                    //工期加上开始时间超出了WBS的结束时间
                    //此时便用结束时间减去开始时间
                    activity.setPlannedFinish(this.wbs.getPlannedFinish());
                    BigDecimal inteBig = new BigDecimal(timeLimit.doubleValue());
                    inteBig = inteBig.setScale(0, BigDecimal.ROUND_CEILING);
                    int timeLimitInt = inteBig.intValue();
                    WorkCalendar w = WrokCalendarFactory.clientCreate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(activity.getPlannedFinish());
                    cal = w.getNextWorkDay(cal, -(timeLimitInt - 1));
                    activity.setPlannedStart(cal.getTime());
                    activity.setTimeLimit(timeLimit);
                    this.fireTableDataChanged();

                } else {
                    //工期加上开始时间仍在范围内
                    BigDecimal inteBig = new BigDecimal(timeLimit.doubleValue());
                    inteBig = inteBig.setScale(0, BigDecimal.ROUND_CEILING);
                    int timeLimitInt = inteBig.intValue();
                    WorkCalendar w = WrokCalendarFactory.clientCreate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(activity.getPlannedStart());
                    cal = w.getNextWorkDay(cal,
                                           timeLimitInt == 0 ? timeLimitInt :
                                           timeLimitInt - 1);
                    activity.setPlannedFinish(cal.getTime());
                    activity.setTimeLimit(timeLimit);
                    this.fireTableDataChanged();
                }
            } else {
                super.setValueAt(aValue, rowIndex, columnIndex);
            }
        } else {
            super.setValueAt(aValue, rowIndex, columnIndex);
        }

    }

    //检查日期是否超过了祖先的日期范围
    private boolean checkDateScope(Date date, String type) {
        Date sDate = this.wbs.getPlannedStart();
        ITreeNode nodeForStart = this.treeNode.getParent();
        while (sDate == null && nodeForStart.getParent() != null) {
            nodeForStart = nodeForStart.getParent();
            sDate = ((DtoWbsActivity) nodeForStart.getDataBean()).
                    getPlannedStart();
        }
        Date fDate = this.wbs.getPlannedFinish();
        ITreeNode nodeForFinish = this.treeNode.getParent();
        while (fDate == null && nodeForFinish.getParent() != null) {
            nodeForFinish = nodeForFinish.getParent();
            fDate = ((DtoWbsActivity) nodeForFinish.getDataBean()).
                    getPlannedFinish();
        }
        if (type.equals("start")) {
            if (sDate != null && date != null && fDate != null) {
                //三项均不为空
                if (date.before(sDate) || date.after(fDate)) {
                    return false;
                }
            } else if (sDate != null && date != null && fDate == null) {
                //结束为空
                if (date.before(sDate)) {
                    return false;
                }
            } else if (sDate == null && date != null && fDate != null) {
                //开始为空
                if (date.after(fDate)) {
                    return false;
                }
            }
        }
        if (type.equals("finish")) {
            if (fDate != null && date != null && sDate != null) {
                if (date.after(fDate) || date.before(sDate)) {
                    return false;
                }
            } else if (fDate == null && date != null && sDate != null) {
                if (date.before(sDate)) {
                    return false;
                }
            } else if (fDate != null && date != null && sDate == null) {
                if (date.after(fDate)) {
                    return false;
                }
            }

        }
        return true;
    }


    //检查开始时间是否在结束时间之前
    private boolean checkDataOrder(Date date, String type,
                                   DtoWbsActivity activity) {
        if (type.equals("start") && activity.getPlannedFinish() != null) {
            if (date.compareTo(activity.getPlannedFinish()) > 0) {
                return false;
            }
        }
        if (type.equals("finish") && activity.getPlannedStart() != null) {
            if (date.compareTo(activity.getPlannedStart()) < 0) {
                return false;
            }
        }
        return true;
    }
}
