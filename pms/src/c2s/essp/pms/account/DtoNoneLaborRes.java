package c2s.essp.pms.account;

import c2s.dto.DtoBase;

public class DtoNoneLaborRes extends DtoBase {

    /** identifier field */
    private Long rid;

    private Long acntRid;

    /** nullable persistent field */
    private String envName;

    public void setRid(Long rid){
        this.rid = rid;
    }

    public Long getRid(){
        return this.rid;
    }

    public void setAcntRid(Long acntRid){
        this.acntRid = acntRid;
    }

    public Long getAcntRid(){
        return this.acntRid;
    }

    public void setEnvName(String envName){
        this.envName = envName;
    }

    public String getEnvName(){
        return this.envName;
    }

}
