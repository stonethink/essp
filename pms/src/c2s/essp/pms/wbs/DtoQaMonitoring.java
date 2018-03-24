package c2s.essp.pms.wbs;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import c2s.dto.DtoBase;
import c2s.essp.pms.qa.DtoQaCheckAction;
import c2s.essp.pms.qa.DtoQaCheckPoint;

public class DtoQaMonitoring extends DtoBase implements
    Serializable {

    private Long rid;
    private boolean readonly = false;
    private Long acntRid;
    private Long belongRid;
    private String belongTo;
    private String code;
    private String name;
    private String method;
    private String occasion;
    private String status;
    private Long cpRid;
    private Date planDate;
    private String planPerformer;
    private Date actDate;
    private String actPerformer;
    private String content;
    private String result;
    private String nrcNo;
    private String type;
    private String rst;
    private Date rct;
    private Date rut;
    private DtoWbsActivity dtoWbsActivity;
    private List pointList;
    private String remark;

    public DtoQaMonitoring() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getActPerformer() {
        return actPerformer;
    }

    public Date getActDate() {
        return actDate;
    }

    public Long getBelongRid() {
        return belongRid;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public String getCode() {
        return code;
    }

    public String getContent() {
        return content;
    }

    public Long getCpRid() {
        return cpRid;
    }

    public String getName() {
        return name;
    }

    public String getNrcNo() {
        return nrcNo;
    }

    public String getOccasion() {
        return occasion;
    }

    public String getPlanPerformer() {
        return planPerformer;
    }

    public Date getRct() {
        return rct;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public String getResult() {
        return result;
    }

    public Long getRid() {
        return rid;
    }

    public String getRst() {
        return rst;
    }

    public Date getRut() {
        return rut;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public void setPlanPerformer(String planPerformer) {
        this.planPerformer = planPerformer;
    }

    public void setNrcNo(String nrcNo) {
        this.nrcNo = nrcNo;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setBelongRid(Long belongRid) {
        this.belongRid = belongRid;
    }

    public void setActPerformer(String actPerformer) {
        this.actPerformer = actPerformer;
    }

    public void setActDate(Date actDate) {
        this.actDate = actDate;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCpRid(Long cpRid) {
        this.cpRid = cpRid;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public String getStatus() {
        return status;
    }

    public DtoWbsActivity getDtoWbsActivity() {
        return dtoWbsActivity;
    }

    public String getMethod() {
        return method;
    }

    public List getPointList() {
        return pointList;
    }

    public String getRemark() {
        return remark;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDtoWbsActivity(DtoWbsActivity dtoWbsActivity) {
        this.dtoWbsActivity = dtoWbsActivity;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setPointList(List pointList) {
        this.pointList = pointList;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isCheckAction() {
        return DtoQaCheckAction.DTO_PMS_CHECK_ACTION_TYPE.equals(type);
    }

    public boolean isCheckPiont() {
        return DtoQaCheckPoint.DTO_PMS_CHECK_POINT_TYPE.equals(type);
    }

    public String getCheckActionName() {
        if(isCheckAction()) {
            return name;
        } else {
            return "";
        }
    }

    public String getWbsActivityName() {
        if(isCheckAction()) {
            return "";
        } else {
            return name;
        }
    }


}
