package c2s.essp.pms.quality.activity;

import c2s.dto.DtoBase;

public class DtoTestStat extends DtoBase {
    private String injectedPhase;
    private Long number;
    private Long rid;
    private Long acntRid;
    private Long testRid;
    public DtoTestStat() {
    }

    public void setInjectedPhase(String injectedPhase) {
        this.injectedPhase = injectedPhase;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setTestRid(Long testRid) {
        this.testRid = testRid;
    }

    public String getInjectedPhase() {
        return injectedPhase;
    }

    public Long getNumber() {
        return number;
    }

    public Long getRid() {
        return rid;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Long getTestRid() {
        return testRid;
    }
}
