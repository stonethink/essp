package server.essp.issue.issue.form;

import c2s.essp.common.issue.*;
import org.apache.struts.action.*;
import org.apache.struts.upload.*;

public class AfIssue extends ActionForm implements IDtoIssue{
    /** identifier field */
    private String rid;

    /** persistent field */
    private String accountId;

    /** nullable persistent field */
    private String priority;

    /** nullable persistent field */
    private String filleBy;

    /** nullable persistent field */
    private String filleDate;

    /** nullable persistent field */
    private String phone;

    /** nullable persistent field */
    private String fax;

    private String email;

    /** nullable persistent field */
    private String scope;

    /** nullable persistent field */
    private String issueId;

    /** nullable persistent field */
    private String issueName;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private FormFile attachment;



    /** nullable persistent field */
    private String attachmentdesc;

    /** nullable persistent field */
    private String principal;

    /** nullable persistent field */
    private String dueDate;

    /** nullable persistent field */
    private String issueStatus;

    /** nullable persistent field */
    private String duplationIssue;

//    /** nullable persistent field */
//    private String rst;
//
//    /** nullable persistent field */
//    private String rct;
//
//    /** nullable persistent field */
//    private String rut;

    /** persistent field */
    private String issueType;
    private String accountCode;

    //调整后的三属性
    //modify by :Robin 20060428
    private String confirmDate;
    private String confirmBy;
    private String instructionOfClosure;
    private String actualFilledBy;
    private String filleByScope;
    private String principalScope;
    private String confirmByScope;

    public AfIssue() {
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAttachmentdesc() {
        return attachmentdesc;
    }

    public String getDescription() {
        return description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getDuplationIssue() {
        return duplationIssue;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
		return email;
    }

    public String getFilleBy() {
        return filleBy;
    }

    public String getFilleDate() {
        return filleDate;
    }

    public String getIssueId() {
        return issueId;
    }

    public String getIssueName() {
        return issueName;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getPhone() {
        return phone;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getPriority() {
        return priority;
    }

//    public String getRct() {
//        return rct;
//    }

    public String getRid() {
        return rid;
    }

//    public String getRst() {
//        return rst;
//    }

//    public String getRut() {
//        return rut;
//    }

    public String getScope() {
        return scope;
    }

    public FormFile getAttachment() {
        return attachment;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public String getConfirmBy() {
        return confirmBy;
    }

    public String getInstructionOfClosure() {
        return instructionOfClosure;
    }

    public String getActualFilledBy() {
        return actualFilledBy;
    }

    public String getFilleByScope() {
        return filleByScope;
    }

    public String getPrincipalScope() {
        return principalScope;
    }

    public String getConfirmByScope() {
        return confirmByScope;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAttachmentdesc(String attachmentdesc) {
        this.attachmentdesc = attachmentdesc;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setDuplationIssue(String duplationIssue) {
        this.duplationIssue = duplationIssue;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setEmail(String email) {
		this.email=email;
    }

    public void setFilleBy(String filleBy) {
        this.filleBy = filleBy;
    }

    public void setFilleDate(String filleDate) {
        this.filleDate = filleDate;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

//    public void setRct(String rct) {
//        this.rct = rct;
//    }

    public void setRid(String rid) {
        this.rid = rid;
    }

//    public void setRst(String rst) {
//        this.rst = rst;
//    }
//
//    public void setRut(String rut) {
//        this.rut = rut;
//    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setAttachment(FormFile attachment) {
        this.attachment = attachment;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public void setConfirmBy(String confirmBy) {
        this.confirmBy = confirmBy;
    }

    public void setInstructionOfClosure(String instructionOfClosure) {
        this.instructionOfClosure = instructionOfClosure;
    }

    public void setActualFilledBy(String actualFilledBy) {
        this.actualFilledBy = actualFilledBy;
    }

    public void setFilleByScope(String filleByScope) {
        this.filleByScope = filleByScope;
    }

    public void setPrincipalScope(String principalScope) {
        this.principalScope = principalScope;
    }

    public void setConfirmByScope(String confirmByScope) {
        this.confirmByScope = confirmByScope;
    }

    public void setCloseDate(String confirmDate) {
         this.confirmDate = confirmDate;
    }

    public String getCloseDate() {
       return confirmDate;
    }
}
