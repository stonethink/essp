package c2s.essp.cbs.cost;

import java.util.Date;

import c2s.dto.DtoBase;
import c2s.dto.DtoTreeNode;

public class DtoCbsCost extends DtoBase {
//    private Long rid;
//    private String baseId; //��ǰCost�Ŀ�Ŀ�ṹ�������Ǵ��ĸ�BaseLineԤ��Copy��,�����ж��Ƿ����
    private Long acntRid;
    private Date costDate;
    private String currency;

    DtoTreeNode costRoot;

    private Double lastPm;
    private Double lastAmt;
    public DtoTreeNode getCostRoot() {
        return costRoot;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getLastAmt() {
        return lastAmt;
    }

    public Double getLastPm() {
        return lastPm;
    }

//    public Long getRid() {
//        return rid;
//    }

    public Date getCostDate() {
        return costDate;
    }

    public Long getAcntRid() {
        return acntRid;
    }

//    public String getBaseId() {
//        return baseId;
//    }

//    public void setRid(Long rid) {
//        this.rid = rid;
//    }

    public void setLastPm(Double lastPm) {
        this.lastPm = lastPm;
    }

    public void setLastAmt(Double lastAmt) {
        this.lastAmt = lastAmt;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCostRoot(DtoTreeNode costRoot) {
        this.costRoot = costRoot;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setCostDate(Date costDate) {
        this.costDate = costDate;
    }

//    public void setBaseId(String baseId) {
//        this.baseId = baseId;
//    }

}
