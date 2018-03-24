package server.essp.pms.wbs.logic;

import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoWbsActivity;
import server.framework.hibernate.HBComAccess;
import c2s.essp.common.calendar.WorkCalendar;
import c2s.essp.common.calendar.WrokCalendarFactory;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import c2s.dto.DtoComboItem;
import server.essp.pms.wbs.action.UserTest;

public class LgWbsComplete extends LgWbs {
    public LgWbsComplete() {
        super();
    }

    public LgWbsComplete(HBComAccess hbAccess) {
        super(hbAccess);
    }

    public ITreeNode caculateComplete(Long accountRid) {
        ITreeNode wbsTree = getWbsTree(accountRid);
        caculateTreeComplete(wbsTree);
        saveWbsTree(wbsTree);
        return wbsTree;
    }

    //仅仅计算其完工比例，不需要写回，用于在统计项目周报的统计中调用
    //因为PSR中不知道是否有权限写回去
    //add by:Robin
    public Double getCompleteRate(Long accountRid) {
        ITreeNode wbsTree = getWbsTree(accountRid);
        caculateTreeComplete(wbsTree);
        Double rate = ((DtoWbsActivity) wbsTree.getDataBean()).getCompleteRate();
        return rate;
    }

    protected void caculateTreeComplete(ITreeNode treeNode) {
        caculateComplete(treeNode);
        for (int i = 0; i < treeNode.getChildCount(); i++) {
            ITreeNode child = treeNode.getChildAt(i);
            caculateTreeComplete(child);
        }
    }

    private void caculateComplete(ITreeNode treeNode) {
        DtoWbsActivity dataBean = (DtoWbsActivity) treeNode.getDataBean();
        if (dataBean.isWbs()) {
            caculateWbsComplete(treeNode);
        } else if (dataBean.isActivity()) {
            caculateActivityComplete(treeNode);
        }
    }

    private void caculateWbsComplete(ITreeNode treeNode) {
        DtoWbsActivity dataBean = (DtoWbsActivity) treeNode.getDataBean();

        String sCompleteMethod = dataBean.getCompleteMethod();
        if (DtoWbsActivity.WBS_COMPLETE_BY_ACTIVITY.equals(sCompleteMethod)) {
            caculateWbsCompleteByActivity(treeNode);
        } else if (DtoWbsActivity.WBS_COMPLETE_BY_CHECKPOINT.equals(
            sCompleteMethod)) {
            caculateWbsCompleteByCheckpoint(treeNode);
        } else if (DtoWbsActivity.WBS_COMPLETE_BY_MANUAL.equals(sCompleteMethod)) {
            caculateWbsCompleteByManual(treeNode);
        }
    }

    private void caculateWbsCompleteByActivity(ITreeNode treeNode) {
        double totalCompleteRate = 0;
        double totalWeight = 0;

        for (int i = 0; i < treeNode.getChildCount(); i++) {
            ITreeNode child = treeNode.getChildAt(i);
            caculateComplete(child);
            DtoWbsActivity childDataBean = (DtoWbsActivity) child.getDataBean();
            if (childDataBean.getCompleteRate() == null) {
                childDataBean.setCompleteRate(new Double(0));
            }
            double completeRate = childDataBean.getCompleteRate().doubleValue();
            double weight = childDataBean.getWeight().doubleValue();
            totalCompleteRate = totalCompleteRate + (completeRate * weight);
            totalWeight = totalWeight + weight;
        }

        double rate = 0;
        if (totalWeight > 0) {
            rate = totalCompleteRate / totalWeight;
            if (rate > 100) {
                rate = 100;
            }
        } else {
            rate = 0;
        }

        DtoWbsActivity dataBean = (DtoWbsActivity) treeNode.getDataBean();
        dataBean.setCompleteRate(new Double(rate));
    }


    /**
     * 读取当前WBS的所有CheckPoint,依据CheckPoint是否完成及权重来计算完工比例
     * @param treeNode ITreeNode
     */
    private void caculateWbsCompleteByCheckpoint(ITreeNode treeNode) {
        double totalCompleteRate = 0;
        double totalWeight = 0;
        DtoWbsActivity dataBean = (DtoWbsActivity) treeNode.getDataBean();
        long acntRid = dataBean.getAcntRid().longValue();
        long wbsRid = dataBean.getWbsRid().longValue();
        String sql = "select * from PMS_WBS_CHECKPOINT where acnt_rid=" +
                     acntRid + " and wbs_rid=" +
                     wbsRid + " order by chk_rid ";

        try {
            List checkPointList = this.getDbAccessor().executeQueryToList(sql,
                CheckPoint.class);
            if (checkPointList != null && checkPointList.size() > 0) {
                for (int i = 0; i < checkPointList.size(); i++) {
                    CheckPoint checkPoint = (CheckPoint) checkPointList.get(i);
                    double weight = 0;
                    if (checkPoint != null &&
                        checkPoint.getChkWeight() != null) {
                        weight = checkPoint.getChkWeight().doubleValue();
                    }

                    if (checkPoint != null &&
                        checkPoint.getIsCompleted() != null &&
                        checkPoint.getIsCompleted().equals("Y")) {
                        totalCompleteRate = totalCompleteRate + weight;
                    }

                    totalWeight = totalWeight + weight;
                }
            }

            if (totalWeight != 0) {
                double percent = totalCompleteRate / totalWeight;
                totalCompleteRate = percent * 100;
            }
        } catch (Exception ex) {
        }
        dataBean.setCompleteRate(new Double(totalCompleteRate));
    }

    private void caculateWbsCompleteByManual(ITreeNode treeNode) {
        return;
    }

    private void caculateActivityComplete(ITreeNode treeNode) {
        DtoWbsActivity dataBean = (DtoWbsActivity) treeNode.getDataBean();
        String sCompleteMethod = dataBean.getCompleteMethod();
        if (DtoWbsActivity.ACTIVITY_COMPLETE_BY_WP.equals(sCompleteMethod)) {
            caculateActivityCompleteByWp(treeNode);
        } else if (DtoWbsActivity.ACTIVITY_COMPLETE_BY_DURATION.equals(
            sCompleteMethod)) {
            caculateActivityCompleteByDuration(treeNode);
        } else if (DtoWbsActivity.ACTIVITY_COMPLETE_BY_0_100.equals(
            sCompleteMethod)) {
            caculateActivityCompleteBy0or100(treeNode);
        } else if (DtoWbsActivity.ACTIVITY_COMPLETE_BY_50_50.equals(
            sCompleteMethod)) {
            caculateActivityCompleteBy50or50(treeNode);
        } else if (DtoWbsActivity.ACTIVITY_COMPLETE_BY_MANUAL.equals(
            sCompleteMethod)) {
            caculateActivityCompleteByManual(treeNode);
        }
    }


    /**
     * 读取所有WP，依据WP的完工比例及权重来计算Activity的完工比例
     * @param treeNode ITreeNode
     */
    private void caculateActivityCompleteByWp(ITreeNode treeNode) {
        /**
         * 取得所有WP， 完工比例 = sum({每个WP的required hours} * {每个WP的完工比例}) / sum({每个WP的required hours})
         */
        DtoWbsActivity dataBean = (DtoWbsActivity) treeNode.getDataBean();
        List allWP = getWPs(dataBean.getAcntRid(), dataBean.getActivityRid());
        double completeHours = 0;
        double requiredHours = 0;
        double completeRate = 0;
        if (allWP != null && allWP.size() > 0) {
            for (int i = 0; i < allWP.size(); i++) {
                IWP wp = (IWP) allWP.get(i);
                completeHours = completeHours +
                                wp.getRequiredHours() * wp.getCompleteRate() /
                                100;
                requiredHours = requiredHours + wp.getRequiredHours();
            }
            if (requiredHours != 0) {
                completeRate = completeHours * 100 / requiredHours;
            } else {
                completeRate = 0;
            }
        }
        dataBean.setCompleteRate(new Double(completeRate));
    }

    private List getWPs(Long accountRid, Long activityRid) {
        List wps = new ArrayList();
        /**
         * 根据accountRid和activityRid从数据库中查询WP
         */
        try {
            if (accountRid != null && activityRid != null) {
                String sql = "select * from PW_WP where PROJECT_ID=" +
                             accountRid + " and ACTIVITY_ID=" +
                             activityRid;
                List wpList = this.getDbAccessor().executeQueryToList(sql, PwWp.class);
                if (wpList != null) {
                    for (int i = 0; i < wpList.size(); i++) {
                        PwWp wp = (PwWp) wpList.get(i);
                        wps.add(new WPAdapter(wp));
                    }
                }
            } else {
                log.debug("accountRid=" + accountRid + ",activityRid=" +
                          activityRid);
                wps = new ArrayList();
            }
        } catch (Exception ex) {
            wps = new ArrayList();
        }
        return wps;
    }


    /**
     * 依据工期的完成比例来计算完工比例
     * @param treeNode ITreeNode
     */
    private void caculateActivityCompleteByDuration(ITreeNode treeNode) {
        DtoWbsActivity dataBean = (DtoWbsActivity) treeNode.getDataBean();
        double durationPlan = caculateDurationPlan(dataBean);
        double durationActual = caculateDurationActual(dataBean);
//        int durationRemain = durationPlan - durationActual;

        double rate = 0;
        if (durationPlan > 0) {
            rate = durationActual * 100 / durationPlan;
            if (rate > 100) {
                rate = 100;
            }
        } else {
            rate = 0;
        }
        dataBean.setCompleteRate(new Double(rate));
    }

    public static int caculateDurationPlan(DtoWbsActivity dataBean) {
        Date begin = dataBean.getPlannedStart();
        Date end = dataBean.getPlannedFinish();
        if (begin == null || end == null) {
            return (0);
        }
        Calendar b = new GregorianCalendar();
        b.setTime(begin);
        Calendar e = new GregorianCalendar();
        e.setTime(end);
        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        int duration = wc.getWorkDayNum(b, e);
        if (duration < 0) {
            duration = 0;
        }
        return duration;
    }

    public static int caculateDurationActual(DtoWbsActivity dataBean) {
        Date begin = dataBean.getActualStart();
        Date end = dataBean.getActualFinish();
        Calendar b = new GregorianCalendar();
        Calendar e = new GregorianCalendar();
        if (begin == null) {
            return (0);
        }
        b.setTime(begin);

        if (end == null) {
            //取系统当前时间
        } else {
            e.setTime(end);
        }

        WorkCalendar wc = WrokCalendarFactory.serverCreate();
        int duration = wc.getWorkDayNum(b, e);
        if (duration < 0) {
            duration = 0;
        }
        return duration;
    }

    /**
     * 依据Activity之is_start/is_finish来判断完工比例，如果为is_finish则为100,否则为0
     * @param treeNode ITreeNode
     */
    private void caculateActivityCompleteBy0or100(ITreeNode treeNode) {
        DtoWbsActivity dto = (DtoWbsActivity) treeNode.getDataBean();
        if (dto.isFinish() != null && dto.isFinish().equals(Boolean.TRUE)) {
            dto.setCompleteRate(new Double(100));
        } else {
            dto.setCompleteRate(new Double(0));
        }
    }

    /**
     * 依据Activity之is_start/is_finish来判断完工比例，如果为is_start而未finish则为50
     * ,如果is_finish否则为100,否则为0
     * @param treeNode ITreeNode
     */
    private void caculateActivityCompleteBy50or50(ITreeNode treeNode) {
        DtoWbsActivity dto = (DtoWbsActivity) treeNode.getDataBean();
        if (dto.isStart() != null && dto.isStart().equals(Boolean.TRUE)) {
            if (dto.isFinish() != null && dto.isFinish().equals(Boolean.TRUE)) {
                dto.setCompleteRate(new Double(100));
            } else {
                dto.setCompleteRate(new Double(50));
            }
        } else {
            dto.setCompleteRate(new Double(0));
        }
    }

    private void caculateActivityCompleteByManual(ITreeNode treeNode) {
        return;
    }

    public List getCompleteMethods() {
        List methods = new ArrayList();
        methods.add(new DtoComboItem(DtoWbsActivity.
                                     ACTIVITY_COMPLETE_BY_WP_LABEL,
                                     DtoWbsActivity.ACTIVITY_COMPLETE_BY_WP));
        methods.add(new DtoComboItem(DtoWbsActivity.
                                     ACTIVITY_COMPLETE_BY_DURATION_LABEL,
                                     DtoWbsActivity.
                                     ACTIVITY_COMPLETE_BY_DURATION));
        methods.add(new DtoComboItem(DtoWbsActivity.
                                     ACTIVITY_COMPLETE_BY_0_100_LABEL,
                                     DtoWbsActivity.ACTIVITY_COMPLETE_BY_0_100));
        methods.add(new DtoComboItem(DtoWbsActivity.
                                     ACTIVITY_COMPLETE_BY_50_50_LABEL,
                                     DtoWbsActivity.ACTIVITY_COMPLETE_BY_50_50));
        methods.add(new DtoComboItem(DtoWbsActivity.
                                     ACTIVITY_COMPLETE_BY_MANUAL_LABEL,
                                     DtoWbsActivity.ACTIVITY_COMPLETE_BY_MANUAL));
        return methods;
    }

    public static void main(String[] args) {
        UserTest.test();
        new LgWbsComplete().getWPs(new Long(12), new Long(10060));
    }
}


class WPAdapter implements IWP {
    PwWp wp;
    public WPAdapter(PwWp wp) {
        this.wp = wp;
    }

    public long getRequiredHours() {
        if (wp != null && wp.getWpReqWkhr() != null) {
            return wp.getWpReqWkhr().longValue();
        }
        return 0;
    }

    /**
     * rate>=0 && rate<=100
     * @return double
     */
    public double getCompleteRate() {
        if (wp != null && wp.getWpCmpltrate() != null) {
            return wp.getWpCmpltrate().doubleValue();
        }
        return 0;
    }
}


interface IWP {
    long getRequiredHours();

    double getCompleteRate();
}
