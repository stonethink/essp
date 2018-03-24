package db.essp.code;

import java.io.*;
import java.util.*;

import org.apache.commons.lang.builder.*;


/** @author Hibernate CodeGenerator */
public class Code implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private String name;

    /** nullable persistent field */
    private String type;

    /** nullable persistent field */
    private String catalog;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private Long seq;

    /** nullable persistent field */
    private String rst;
    private String status;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public Code(Long rid, String name, String type, String catalog, String description, Long seq, String status, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.name = name;
        this.type = type;
        this.catalog = catalog;
        this.description = description;
        this.seq = seq;
        this.status = status;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public Code() {
    }

    /** minimal constructor */
    public Code(Long rid) {
        this.rid = rid;
    }

    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCatalog() {
        return this.catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSeq() {
        return this.seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
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

    public String getStatus() {
        return status;
    }

    public void setRut(Date rut) {
        this.rut = rut;
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
