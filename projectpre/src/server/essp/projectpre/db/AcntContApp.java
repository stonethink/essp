package server.essp.projectpre.db;

import java.util.Date;

public class AcntContApp implements java.io.Serializable {


    // Fields    

     private Long rid;
     private Long acntAppRid;
     private String acntId;
     private String contractAcntId;
     private String parentId;
     private String relPrjType;
     private String rst;
     private Date rct;
     private Date rut;
     private String rcu;
     private String ruu;
    public Long getAcntAppRid() {
        return acntAppRid;
    }
    public void setAcntAppRid(Long acntAppRid) {
        this.acntAppRid = acntAppRid;
    }
    public String getAcntId() {
        return acntId;
    }
    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }
  

    public String getRelPrjType() {
        return relPrjType;
    }
    public void setRelPrjType(String relPrjType) {
        this.relPrjType = relPrjType;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public Date getRct() {
        return rct;
    }
    public void setRct(Date rct) {
        this.rct = rct;
    }
    public String getRcu() {
        return rcu;
    }
    public void setRcu(String rcu) {
        this.rcu = rcu;
    }
    public Long getRid() {
        return rid;
    }
    public void setRid(Long rid) {
        this.rid = rid;
    }
    public String getRst() {
        return rst;
    }
    public void setRst(String rst) {
        this.rst = rst;
    }
    public Date getRut() {
        return rut;
    }
    public void setRut(Date rut) {
        this.rut = rut;
    }
    public String getRuu() {
        return ruu;
    }
    public void setRuu(String ruu) {
        this.ruu = ruu;
    }
    public String getContractAcntId() {
        return contractAcntId;
    }
    public void setContractAcntId(String contractAcntId) {
        this.contractAcntId = contractAcntId;
    }
     
}