package server.essp.issue.issue.conclusion.viewbean;

import java.util.*;

import server.framework.taglib.util.*;

/**
 * Issue Conclusion界面显示所需内容
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VbIssueConclusion {
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
    private String attachment;

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
    private List allStatus = new ArrayList();
    private List urgeList = new ArrayList();
    private String deleterefresh = "";
   private String selectedRow = "tr0";
   private List confirmByList;
   private String isPrincipal="";
   private String isFilledBy="";
   private String isNull="";
    private String isexecutor;
    private String confirmByScope;
    public String getActualInfluence() {
        return actualInfluence;
    }

    public String getAttachment() {
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

    public String getSolvedDescription() {
        return solvedDescription;
    }

    public String getWaiting() {
        return waiting;
    }

    public List getAllStatus() {
        return allStatus;
    }

    public void setActualInfluence(String actualInfluence) {
        this.actualInfluence = actualInfluence;
    }

    public void setAttachment(String attachment) {
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

    public void setSolvedDescription(String solvedDescription) {
        this.solvedDescription = solvedDescription;
    }

    public void setWaiting(String waiting) {
        this.waiting = waiting;
    }

    public VbIssueConclusion() {
        allStatus.add(new SelectOptionImpl("---  Please Select  ---", ""));
        allStatus.add(new SelectOptionImpl("Nonacceptance", "Nonacceptance"));
        allStatus.add(new SelectOptionImpl("Normal Closed", "Normal Closed"));
        allStatus.add(new SelectOptionImpl("Abnormal Closed", "Abnormal Closed"));
    }
    public String getDeleterefresh() {
       return deleterefresh;
   }

   public String getSelectedRow() {
       return selectedRow;
   }

   public List getUrgeList() {
       return urgeList;
   }

    public List getConfirmByList() {
        return confirmByList;
    }

    public String getIsPrincipal() {
        return isPrincipal;
    }

    public String getIsNull() {
        return isNull;
    }

    public String getIsFilledBy() {
        return isFilledBy;
    }

    public String getIsexecutor() {
        return isexecutor;
    }

    public String getConfirmByScope() {
        return confirmByScope;
    }

    public void setDeleterefresh(String deleterefresh) {
        this.deleterefresh = deleterefresh;
    }

    public void setSelectedRow(String selectedRow) {
        this.selectedRow = selectedRow;
    }

    public void setUrgeList(List urgeList) {
        this.urgeList = urgeList;
    }

    public void setConfirmByList(List confirmByList) {
        this.confirmByList = confirmByList;
    }

    public void setIsPrincipal(String isPrincipal) {
        this.isPrincipal = isPrincipal;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public void setIsFilledBy(String isFilledBy) {
        this.isFilledBy = isFilledBy;
    }

    public void setIsexecutor(String isexecutor) {
        this.isexecutor = isexecutor;
    }

    public void setConfirmByScope(String confirmByScope) {
        this.confirmByScope = confirmByScope;
    }


}
