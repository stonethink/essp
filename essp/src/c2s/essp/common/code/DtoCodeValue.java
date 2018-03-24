package c2s.essp.common.code;

import c2s.dto.DtoBase;

public class DtoCodeValue extends DtoBase{

    /** identifier field */
    private Long rid;
    private Long parentRid;

    /** nullable persistent field */
    //private String codeName;
    private Long codeRid;

    /** nullable persistent field */
    private String value;

    /** nullable persistent field */
    //private String path;

    /** nullable persistent field */
    private String description;
    private String status;
//    public String getCodeName() {
//        return codeName;
//    }

    public Long getCodeRid() {
        return codeRid;
    }

    public String getDescription() {
        return description;
    }

    public Long getParentRid() {
        return parentRid;
    }

//    public String getPath() {
//        return path;
//    }

    public Long getRid() {
        return rid;
    }

    public String getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

//    public void setPath(String path) {
//        this.path = path;
//    }

    public void setParentRid(Long parentRid) {
        this.parentRid = parentRid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCodeRid(Long codeRid) {
        this.codeRid = codeRid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //    public void setCodeName(String codeName) {
//        this.codeName = codeName;
//    }


}
