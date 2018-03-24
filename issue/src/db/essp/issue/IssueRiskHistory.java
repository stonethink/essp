package db.essp.issue;

import java.io.*;
import java.util.*;

public class IssueRiskHistory implements Serializable {
    private Long rid;
    private Long issueRid;
    private Date updateDate;
    private String updateBy;
    private double probabilityFrom;
    private double probabilityTo;
    private double risklevelFrom;
    private double risklevelTo;
    private String influenceFrom;
    private String influenceTo;
    private String categoryFrom;
    private String categoryTo;
    private String memo;
    private String rst;
    private Date rct;
    private Date rut;
    private String updateByScope;
    public String getCategoryFrom() {
        return categoryFrom;
    }

    public String getCategoryTo() {
        return categoryTo;
    }

    public String getInfluenceFrom() {
        return influenceFrom;
    }

    public String getInfluenceTo() {
        return influenceTo;
    }

    public Long getIssueRid() {
        return issueRid;
    }

    public String getMemo() {
        return memo;
    }

    public double getProbabilityFrom() {
        return probabilityFrom;
    }

    public double getProbabilityTo() {
        return probabilityTo;
    }

    public Date getRct() {
        return rct;
    }

    public Long getRid() {
        return rid;
    }

    public double getRisklevelFrom() {
        return risklevelFrom;
    }

    public double getRisklevelTo() {
        return risklevelTo;
    }

    public String getRst() {
        return rst;
    }

    public Date getRut() {
        return rut;
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

    public void setCategoryFrom(String categoryFrom) {
        this.categoryFrom = categoryFrom;
    }

    public void setCategoryTo(String categoryTo) {
        this.categoryTo = categoryTo;
    }

    public void setInfluenceFrom(String influenceFrom) {
        this.influenceFrom = influenceFrom;
    }

    public void setInfluenceTo(String influenceTo) {
        this.influenceTo = influenceTo;
    }

    public void setIssueRid(Long issueRid) {
        this.issueRid = issueRid;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setProbabilityFrom(double probabilityFrom) {
        this.probabilityFrom = probabilityFrom;
    }

    public void setProbabilityTo(double probabilityTo) {
        this.probabilityTo = probabilityTo;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public void setRisklevelFrom(double risklevelFrom) {
        this.risklevelFrom = risklevelFrom;
    }

    public void setRisklevelTo(double risklevelTo) {
        this.risklevelTo = risklevelTo;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setRut(Date rut) {
        this.rut = rut;
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
