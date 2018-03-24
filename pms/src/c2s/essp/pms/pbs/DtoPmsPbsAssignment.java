package c2s.essp.pms.pbs;

import c2s.dto.DtoBase;

public class DtoPmsPbsAssignment extends DtoBase  {
    public static final Long JOINTYPEWBS = new Long(0);
    public static final Long JOINTYPEACT = new Long(1);

    /** identifier field */
    private Long acntRid;

    /** identifier field */
    private Long pbsRid;

    /** identifier field */
    private Long joinType;

    /** identifier field */
    private Long joinRid;

    /** nullable persistent field */
    private String isWorkproduct;

    private String name;
    private String code;
    private String manager;
    private String status;

    public Long getPbsRid() {
        return pbsRid;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public Long getJoinRid() {
        return joinRid;
    }

    public Long getJoinType() {
        return joinType;
    }

    public String getName(){
        return this.name;
    }

    public String getCode(){
        return this.code;
    }

    public String getManager(){
        return this.manager;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setManager(String manager){
        this.manager = manager;
    }

    public void setIsWorkproduct(String isWorkproduct) {
        this.isWorkproduct = isWorkproduct;
    }

    public void setPbsRid(Long pbsRid) {
        this.pbsRid = pbsRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setJoinRid(Long joinRid) {
        this.joinRid = joinRid;
    }

    public void setJoinType(Long joinType) {
        this.joinType = joinType;
    }

    public String getIsWorkproduct() {
        return isWorkproduct;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
