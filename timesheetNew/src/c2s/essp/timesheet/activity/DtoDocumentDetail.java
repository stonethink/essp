package c2s.essp.timesheet.activity;

import c2s.dto.DtoBase;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoDocumentDetail extends DtoBase{
    private Long documentRid;
    private String title;
    private String refNo;
    private String location;
    private String version;
    private String author;
    private java.util.Date revisionDate;
    private String status;
    private String description;
    private Boolean deliverable;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setRevisionDate(java.util.Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDocumentRid(Long documentRid) {
        this.documentRid = documentRid;
    }

    public void setDeliverable(Boolean deliverable) {
        this.deliverable = deliverable;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getRefNo() {
        return refNo;
    }

    public java.util.Date getRevisionDate() {
        return revisionDate;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getVersion() {
        return version;
    }

    public Long getDocumentRid() {
        return documentRid;
    }

    public Boolean getDeliverable() {
        return deliverable;
    }

}
