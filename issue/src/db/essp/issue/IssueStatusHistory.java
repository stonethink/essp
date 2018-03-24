package db.essp.issue;

import java.io.*;
import java.util.*;

public class IssueStatusHistory implements Serializable {
    private Long rid;
    private Long issueRid;
    private Date updateDate;
    private String updateBy;
    private String statusFrom;
    private String statusTo;
    private String memo;
    private String rst;
    private Date rct;
    private Date rut;
    private String updateByScope;
    public Long getIssueRid() {
        return issueRid;
    }

    public String getMemo() {
        return memo;
    }

    public Date getRct() {
        return rct;
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

    public String getStatusFrom() {
        return statusFrom;
    }

    public String getStatusTo() {
        return statusTo;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getUpdateByScope() {
        return updateByScope;
    }

    public void setIssueRid(Long issueRid) {
        this.issueRid = issueRid;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public void setStatusFrom(String statusFrom) {
        this.statusFrom = statusFrom;
    }

    public void setStatusTo(String statusTo) {
        this.statusTo = statusTo;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setUpdateByScope(String updateByScope) {
        this.updateByScope = updateByScope;
    }


}
