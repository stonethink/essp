package server.essp.issue.issue.conclusion.form;

import org.apache.struts.action.*;
import org.apache.struts.upload.*;
public class AfIssueConclusion extends ActionForm {
    /** identifier field */
    private String rid;

    /** nullable persistent field */
    private String actualInfluence;

    /** nullable persistent field */
    private String solvedDescription;

    /** nullable persistent field */
    private String finishedDate;

    /** nullable persistent field */
    private String deliveredDate;

    /** nullable persistent field */
    private FormFile attachment;

    /** nullable persistent field */
    private String attachmentDesc;

    /** nullable persistent field */
    private String closureStatus;

    /** nullable persistent field */
    private String confirmDate;

    /** nullable persistent field */
    private String confirmBy;

    /** nullable persistent field */
    private String instructionClosure;
     private String rst;
     private String waiting;
    private String confirmByScope;

    public AfIssueConclusion() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
    }

    public String getSolvedDescription() {
        return solvedDescription;
    }

    public String getActualInfluence() {
        return actualInfluence;
    }

    public FormFile getAttachment() {
        return attachment;
    }

    public String getAttachmentDesc() {
        return attachmentDesc;
    }

    public String getClosureStatus() {
        return closureStatus;
    }

    public String getConfirmBy() {
        return confirmBy;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public String getFinishedDate() {
        return finishedDate;
    }

    public String getInstructionClosure() {
        return instructionClosure;
    }

    public String getRid() {
        return rid;
    }

    public String getRst() {
        return rst;
    }

    public String getWaiting() {
        return waiting;
    }

    public String getConfirmByScope() {
        return confirmByScope;
    }

    public void setSolvedDescription(String solvedDescription) {
        this.solvedDescription = solvedDescription;
    }

    public void setActualInfluence(String actualInfluence) {
        this.actualInfluence = actualInfluence;
    }

    public void setAttachment(FormFile attachment) {
        this.attachment = attachment;
    }

    public void setAttachmentDesc(String attachmentDesc) {
        this.attachmentDesc = attachmentDesc;
    }

    public void setClosureStatus(String closureStatus) {
        this.closureStatus = closureStatus;
    }

    public void setConfirmBy(String confirmBy) {
        this.confirmBy = confirmBy;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public void setFinishedDate(String finishedDate) {
        this.finishedDate = finishedDate;
    }

    public void setInstructionClosure(String instructionClosure) {
        this.instructionClosure = instructionClosure;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    public void setWaiting(String waiting) {
        this.waiting = waiting;
    }

    public void setConfirmByScope(String confirmByScope) {
        this.confirmByScope = confirmByScope;
    }


}
