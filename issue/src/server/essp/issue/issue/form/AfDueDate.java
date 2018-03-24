package server.essp.issue.issue.form;

import org.apache.struts.action.ActionForm;

public class AfDueDate extends ActionForm {
     private String typeName;
     private String priority;
     private String filleDate;
     public String getFilleDate() {
        return filleDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setFilleDate(String filleDate) {
        this.filleDate = filleDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
