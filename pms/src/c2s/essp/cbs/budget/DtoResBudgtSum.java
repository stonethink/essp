package c2s.essp.cbs.budget;

import c2s.essp.cbs.cost.DtoResCostSum;

/**
 * 记录资源汇总记录那行
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
public class DtoResBudgtSum extends DtoResBudgetItem {
    private Double amt;
    private Double unitNum;
    public DtoResBudgtSum(){
        super();
        setAcntRid(new Long(0));
        setResName(DtoResCostSum.SUM);
        setRid(new Long(0));
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Double getAmt() {
        if(amt == null)
            return new Double(0);
        return amt;
    }
    public Double getUnitNum() {
        return unitNum;
    }
    public void setUnitNum(Double unitNum){
        this.unitNum = unitNum;
    }

}
