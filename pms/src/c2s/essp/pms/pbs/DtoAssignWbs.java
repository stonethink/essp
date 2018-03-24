package c2s.essp.pms.pbs;

import c2s.dto.DtoBase;
import java.util.Date;

public class DtoAssignWbs extends DtoBase{

    /** identifier field */
    private Long acntRid;

    /** identifier field */
    private Long joinRid;

    /**
     * @see DtoPmsPbsAssignment
     * DtoPmsPbsAssignment.JOINTYPEWBS -- assign wbs to pbs
     * DtoPmsPbsAssignment.JOINTYPEACT -- assign activity to pbs
     */
    private Long joinType;

    /** nullable persistent field */
    private Date anticipatedStart;

    /** nullable persistent field */
    private Date anticipatedFinish;


    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String manager;

    /** nullable persistent field */
    private Double weight;

    /** nullable persistent field */
    private Double completeRate;

    private String code;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getJoinRid() {
        return this.joinRid;
    }

    public void setJoinRid(Long joinRid) {
        this.joinRid = joinRid;
    }

    public Long getAcntRid() {
        return this.acntRid;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return this.manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Date getAnticipatedStart() {
        return this.anticipatedStart;
    }

    public void setAnticipatedStart(Date anticipatedStart) {
        this.anticipatedStart = anticipatedStart;
    }

    public Date getAnticipatedFinish() {
        return this.anticipatedFinish;
    }

    public void setAnticipatedFinish(Date anticipatedFinish) {
        this.anticipatedFinish = anticipatedFinish;
    }

    public Double getCompleteRate() {
        return this.completeRate;
    }

    public void setCompleteRate(Double completeRate) {
        this.completeRate = completeRate;
    }

    public Long getJoinType() {
        return this.joinType;
    }

    public void setJoinType(Long joinType) {
        this.joinType = joinType;
    }

    public String toString(){
        return this.getName();
    }

}
