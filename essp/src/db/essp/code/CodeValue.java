package db.essp.code;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class CodeValue implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String codeName;

    /** nullable persistent field */
    private String value;

    /** nullable persistent field */
    private String path;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private db.essp.code.CodeValue parent;

    /** nullable persistent field */
    private db.essp.code.Code code;

    /** persistent field */
    private List childs;

    private String status;

    /** full constructor */
    public CodeValue(Long rid, String codeName, String value, String path, String description, String status, String rst, Date rct, Date rut, db.essp.code.CodeValue parent, db.essp.code.Code code, List childs) {
        this.rid = rid;
        this.codeName = codeName;
        this.value = value;
        this.path = path;
        this.description = description;
        this.status = status;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.parent = parent;
        this.code = code;
        this.childs = childs;
    }

    /** default constructor */
    public CodeValue() {
    }

    /** minimal constructor */
    public CodeValue(Long rid, List childs) {
        this.rid = rid;
        this.childs = childs;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public db.essp.code.CodeValue getParent() {
        return this.parent;
    }

    public void setParent(db.essp.code.CodeValue parent) {
        this.parent = parent;
    }

    public db.essp.code.Code getCode() {
        return this.code;
    }

    public void setCode(db.essp.code.Code code) {
        this.code = code;
    }

    public List getChilds() {
        return this.childs;
    }

    public String getStatus() {
        return status;
    }

    public void setChilds(List childs) {
        this.childs = childs;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

}
