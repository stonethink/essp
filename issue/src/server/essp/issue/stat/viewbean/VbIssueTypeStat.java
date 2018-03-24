package server.essp.issue.stat.viewbean;

/**
 * 每种Issue Type中各状态的Issue统计
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
public class VbIssueTypeStat {

    private String issueType;

    private int receivedCount;

    private int rejectCount;

    private int processingCount;

    private int deliveredCount;

    private int closedCount;

    private int duplationCount;

    private int sum;

    private int leftSum;

    private int abnormal;

    public VbIssueTypeStat() {
        receivedCount=0;
        rejectCount=0;
        processingCount=0;
        deliveredCount=0;
        closedCount=0;
        duplationCount=0;
        sum=0;
        leftSum=0;
        abnormal=0;
        issueType="";
    }

    public int getAbnormal() {
        return abnormal;
    }

    public int getClosedCount() {
        return closedCount;
    }

    public int getDeliveredCount() {
        return deliveredCount;
    }

    public int getDuplationCount() {
        return duplationCount;
    }

    public String getIssueType() {
        return issueType;
    }

    public int getLeftSum() {
        return processingCount+deliveredCount;
    }

    public int getProcessingCount() {
        return processingCount;
    }

    public int getRejectCount() {
        return rejectCount;
    }

    public int getSum() {
        return sum;
    }

    public int getReceivedCount() {
        return receivedCount;
    }

    public void setAbnormal(int abnormal) {
        this.abnormal = abnormal;
    }

    public void setClosedCount(int closedCount) {
        this.closedCount = closedCount;
    }

    public void setDeliveredCount(int deliveredCount) {
        this.deliveredCount = deliveredCount;
    }

    public void setDuplationCount(int duplationCount) {
        this.duplationCount = duplationCount;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setLeftSum(int leftSum) {
        this.leftSum = leftSum;
    }

    public void setProcessingCount(int processingCount) {
        this.processingCount = processingCount;
    }

    public void setRejectCount(int rejectCount) {
        this.rejectCount = rejectCount;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setReceivedCount(int receivedCount) {
        this.receivedCount = receivedCount;
    }
}
