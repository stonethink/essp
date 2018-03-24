package db.essp.pms;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/** @author Hibernate CodeGenerator */
public class PbsFiles implements Serializable {

    /** identifier field */
    private db.essp.pms.PbsFilesPK pk;

    /** nullable persistent field */
    private String fileName;

    /** nullable persistent field */
    private String fileVersion;

    /** nullable persistent field */
    private String fileAuthor;

    /** nullable persistent field */
    private Date fileCreateDate;

    /** nullable persistent field */
    private Date fileModDate;

    /** nullable persistent field */
    private String fileLink;

    /** nullable persistent field */
    private String fileRemark;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** nullable persistent field */
    private db.essp.pms.Pbs pbs;

    /** full constructor */
    public PbsFiles(db.essp.pms.PbsFilesPK pk, String fileName, String fileVersion, String fileAuthor, Date fileCreateDate, Date fileModDate, String fileLink, String fileRemark, String rst, Date rct, Date rut, db.essp.pms.Pbs pbs) {
        this.pk = pk;
        this.fileName = fileName;
        this.fileVersion = fileVersion;
        this.fileAuthor = fileAuthor;
        this.fileCreateDate = fileCreateDate;
        this.fileModDate = fileModDate;
        this.fileLink = fileLink;
        this.fileRemark = fileRemark;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
        this.pbs = pbs;
    }

    /** default constructor */
    public PbsFiles() {
    }

    /** minimal constructor */
    public PbsFiles(db.essp.pms.PbsFilesPK pk) {
        this.pk = pk;
    }

    public db.essp.pms.PbsFilesPK getPk() {
        return this.pk;
    }

    public void setPk(db.essp.pms.PbsFilesPK pk) {
        this.pk = pk;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileVersion() {
        return this.fileVersion;
    }

    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }

    public String getFileAuthor() {
        return this.fileAuthor;
    }

    public void setFileAuthor(String fileAuthor) {
        this.fileAuthor = fileAuthor;
    }

    public Date getFileCreateDate() {
        return this.fileCreateDate;
    }

    public void setFileCreateDate(Date fileCreateDate) {
        this.fileCreateDate = fileCreateDate;
    }

    public Date getFileModDate() {
        return this.fileModDate;
    }

    public void setFileModDate(Date fileModDate) {
        this.fileModDate = fileModDate;
    }

    public String getFileLink() {
        return this.fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public String getFileRemark() {
        return this.fileRemark;
    }

    public void setFileRemark(String fileRemark) {
        this.fileRemark = fileRemark;
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

    public db.essp.pms.Pbs getPbs() {
        return this.pbs;
    }

    public void setPbs(db.essp.pms.Pbs pbs) {
        this.pbs = pbs;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("pk", getPk())
            .toString();
    }

    public boolean equals(Object other) {
        if ( (this == other ) ) return true;
        if ( !(other instanceof PbsFiles) ) return false;
        PbsFiles castOther = (PbsFiles) other;
        return new EqualsBuilder()
            .append(this.getPk(), castOther.getPk())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getPk())
            .toHashCode();
    }

}
