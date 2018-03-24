package server.essp.issue.issue.viewbean;

public class VbDueDate {
    private String typeName;
    private String priority;
    private String filleDate;
    private String dueDate;
    public String getFilleDate() {
        return filleDate;
    }

    public String getPriority() {
        return priority;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getDueDate() {
        return dueDate;
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

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
