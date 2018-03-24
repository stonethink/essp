package c2s.essp.pms.pbs;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoPmsPbsFiles extends DtoBase  {

    /** identifier field */
    private Long acntRid;
    private String acntCode;

    /** identifier field */
    private Long pbsRid;

    /** identifier field */
    private Long filesRid;

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
    public String getFileVersion() {
        return fileVersion;
    }

    public String getFileRemark() {
        return fileRemark;
    }

    public Long getFilesRid() {
        return filesRid;
    }

    public Long getPbsRid() {
        return pbsRid;
    }

    public String getFileLink() {
        return fileLink;
    }

    public Long getAcntRid() {
        return acntRid;
    }

    public String getFileAuthor() {
        return fileAuthor;
    }

    public Date getFileCreateDate() {
        return fileCreateDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileModDate(Date fileModDate) {
        this.fileModDate = fileModDate;
    }

    public void setFileVersion(String fileVersion) {
        this.fileVersion = fileVersion;
    }

    public void setFileRemark(String fileRemark) {
        this.fileRemark = fileRemark;
    }

    public void setFilesRid(Long filesRid) {
        this.filesRid = filesRid;
    }

    public void setPbsRid(Long pbsRid) {
        this.pbsRid = pbsRid;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public void setAcntRid(Long acntRid) {
        this.acntRid = acntRid;
    }

    public void setFileAuthor(String fileAuthor) {
        this.fileAuthor = fileAuthor;
    }

    public void setFileCreateDate(Date fileCreateDate) {
        this.fileCreateDate = fileCreateDate;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setAcntCode(String acntCode) {
        this.acntCode = acntCode;
    }

    public Date getFileModDate() {
        return fileModDate;
    }

    public String getAcntCode() {
        return acntCode;
    }

}
