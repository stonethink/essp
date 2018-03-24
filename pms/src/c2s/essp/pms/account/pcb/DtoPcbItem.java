package c2s.essp.pms.account.pcb;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoPcbItem extends DtoBase {
    private Long rid;
    private Long acntRid;
    private String name;
    private String type;
    private String remark;
    private String rst;
    private Date rct;
    private Date rut;
    private Long seq;


    public String getRemark() {
        return remark;
    }

    public String getType() {
        return type;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Long getRid() {
        return rid;
    }

    public String getName() {
        return name;
    }

    public Date getRct() {
        return rct;
    }

    public String getRst() {
        return rst;
    }

    public Date getRut() {
        return rut;
    }

    public Long getSeq() {
        return seq;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }
}
