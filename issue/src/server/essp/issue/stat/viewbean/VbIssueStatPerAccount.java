package server.essp.issue.stat.viewbean;

/**
 *
 * <p>Title: 统计每个Account各个状态下Issue的数量</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VbIssueStatPerAccount {
    private String accountRid;
    private String accountCode;
    private String accountName;
    private long receivedCount;
    private long processingCount;
    private long deliveredCount;
    private long closedCount;
    private long rejectedCount;
    private long duplationCount;
    private long sum;
    private long leftSum;
    private long abnormalCount;
    public String getAccountRid() {
        return accountRid;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public long getReceivedCount() {
        return receivedCount;
    }

    public long getProcessingCount() {
        return processingCount;
    }

    public long getDeliveredCount() {
        return deliveredCount;
    }

    public long getClosedCount() {
        return closedCount;
    }

    public long getRejectedCount() {

        return rejectedCount;
    }

    public long getDuplationCount() {
        return duplationCount;
    }

    public long getSum() {
        return receivedCount + processingCount + deliveredCount + closedCount + rejectedCount + duplationCount;
    }

    public long getLeftSum() {
        return processingCount+deliveredCount;
    }

    public long getAbnormalCount() {
        return abnormalCount;
    }

    public void setAccountRid(String accountRid) {
        this.accountRid = accountRid;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void increReceivedCount(long increCount) {
        this.receivedCount += increCount;
    }

    public void increProcessingCount(long increCount) {
        this.processingCount += increCount;
    }

    public void increDeliveredCount(long increCount) {
        this.deliveredCount += increCount;
    }

    public void increClosedCount(long increCount) {
        this.closedCount += increCount;
    }

    public void increRejectedCount(long increCount) {

        this.rejectedCount += increCount;
    }

    public void increDuplationCount(long increCount) {
        this.duplationCount += increCount;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public void setLeftSum(long leftSum) {
        this.leftSum = leftSum;
    }

    public void increAbnormalCount(long increCount) {
        this.abnormalCount += increCount;
    }

}
