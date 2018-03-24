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
public class VbIssueStat {

    private String issueType;

    private String receivedCount;

    private String rejectCount;

    private String processingCount;

    private String deliveredCount;

    private String closedCount;

    private String duplationCount;

    private String sum;

    private String leftSum;

    private String abnormal;
    public VbIssueStat() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
    }

    public String getAbnormal() {
        return abnormal;
    }

    public String getClosedCount() {
        return closedCount;
    }

    public String getDeliveredCount() {
        return deliveredCount;
    }

    public String getDuplationCount() {
        return duplationCount;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getLeftSum() {
        return leftSum;
    }

    public String getProcessingCount() {
        return processingCount;
    }

    public String getRejectCount() {
        return rejectCount;
    }

    public String getSum() {
        return sum;
    }

    public String getReceivedCount() {
        return receivedCount;
    }

    public void setAbnormal(String abnormal) {
        this.abnormal = abnormal;
    }

    public void setClosedCount(String closedCount) {
        this.closedCount = closedCount;
    }

    public void setDeliveredCount(String deliveredCount) {
        this.deliveredCount = deliveredCount;
    }

    public void setDuplationCount(String duplationCount) {
        this.duplationCount = duplationCount;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setLeftSum(String leftSum) {
        this.leftSum = leftSum;
    }

    public void setProcessingCount(String processingCount) {
        this.processingCount = processingCount;
    }

    public void setRejectCount(String rejectCount) {
        this.rejectCount = rejectCount;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public void setReceivedCount(String receivedCount) {
        this.receivedCount = receivedCount;
    }


}
