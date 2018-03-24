package c2s.essp.pms.quality.activity;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoTestRound extends DtoBase {
    public DtoTestRound() {
    }

    private Long rid;
    private Long qulityRid;
    private String testRound;
    private Date start;
    private Date finish;
    private Long totalTestCase;
    private Long usingTestCase;
    private Long removedDefectNum;
    private Long seq;
    private String comment;
    private Long defectNum;
    private Long acntRid;
    private String activityName;


    public String getComment() {
        return comment;
    }

    public Long getDefectNum() {
        return defectNum;
    }

    public Date getFinish() {
        return finish;
    }

    public Long getQulityRid() {
        return qulityRid;
    }

    public Long getRemovedDefectNum() {
        return removedDefectNum;
    }

    public Long getRid() {
        return rid;
    }

    public Long getSeq() {
        return seq;
    }

    public Date getStart() {
        return start;
    }

    public String getTestRound() {
        return testRound;
    }

    public Long getTotalTestCase() {
        return totalTestCase;
    }

    public Long getUsingTestCase() {
        return usingTestCase;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDefectNum(Long defectNum) {
        this.defectNum = defectNum;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public void setQulityRid(Long qulityRid) {
        this.qulityRid = qulityRid;
    }

    public void setRemovedDefectNum(Long removedDefectNum) {
        this.removedDefectNum = removedDefectNum;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setTestRound(String testRound) {
        this.testRound = testRound;
    }

    public void setTotalTestCase(Long totalTestCase) {
        this.totalTestCase = totalTestCase;
    }

    public void setUsingTestCase(Long usingTestCase) {
        this.usingTestCase = usingTestCase;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }


}
