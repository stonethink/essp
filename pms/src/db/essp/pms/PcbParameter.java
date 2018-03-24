package db.essp.pms;

import java.io.Serializable;

public class PcbParameter implements Serializable {
    private Long itemRid;
    private String name;
    private String unit;
    private Double ucl;
    private Double mean;
    private Double lcl;
    private Double plan;
    private String remark;
    private Double actual;
    private String rst;

    private String id;
    private Long rid;
    private Long acntRid;
    private String rct;


    // Constructors

   /** default constructor */
   public PcbParameter() {
   }

   /** minimal constructor */
   public PcbParameter(Long rid, Long itemRid, String name, String id,Long acntRid) {
       this.rid = rid;
       this.itemRid = itemRid;
       this.name = name;
       this.id = id;
       this.acntRid = acntRid;
   }

   /** full constructor */
   public PcbParameter(Long rid, Long itemRid, String name, String unit, Double ucl, Double mean, Double lcl, Double plan, String remark, Double actual, String rst, String id,Long acntRid) {
       this.rid = rid;
       this.itemRid = itemRid;
       this.name = name;
       this.unit = unit;
       this.ucl = ucl;
       this.mean = mean;
       this.lcl = lcl;
       this.plan = plan;
       this.remark = remark;
       this.actual = actual;
       this.rst = rst;
       this.acntRid = acntRid;
       this.id = id;
   }

    public Long getItemRid() {
       return this.itemRid;
   }

   public void setItemRid(Long itemRid) {
       this.itemRid = itemRid;
   }

   public String getName() {
       return this.name;
   }

   public void setName(String name) {
       this.name = name;
   }

   public String getUnit() {
       return this.unit;
   }

   public void setUnit(String unit) {
       this.unit = unit;
   }

   public Double getUcl() {
       return this.ucl;
   }

   public void setUcl(Double ucl) {
       this.ucl = ucl;
   }

   public Double getMean() {
       return this.mean;
   }

   public void setMean(Double mean) {
       this.mean = mean;
   }

   public Double getLcl() {
       return this.lcl;
   }

   public void setLcl(Double lcl) {
       this.lcl = lcl;
   }

   public Double getPlan() {
       return this.plan;
   }

   public void setPlan(Double plan) {
       this.plan = plan;
   }

   public String getRemark() {
       return this.remark;
   }

   public void setRemark(String remark) {
       this.remark = remark;
   }

   public Double getActual() {
       return this.actual;
   }

   public void setActual(Double actual) {
       this.actual = actual;
   }

   public String getRst() {
       return this.rst;
   }

   public void setRst(String rst) {
       this.rst = rst;
   }





   public String getId() {
       return this.id;
   }

    public Long getRid() {
        return rid;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getRct() {
        return rct;
    }

    public void setId(String id) {
       this.id = id;
   }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setRct(String rct) {
        this.rct = rct;
    }
}


