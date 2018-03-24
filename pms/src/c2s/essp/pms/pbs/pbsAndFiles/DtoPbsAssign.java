package c2s.essp.pms.pbs.pbsAndFiles;

import c2s.dto.DtoBase;

public class DtoPbsAssign extends DtoBase  {
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

    private String name;
    private String code;
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

    public String getStatus(){
        return this.status;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setStatus(String status){
        this.status = status;
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

}
