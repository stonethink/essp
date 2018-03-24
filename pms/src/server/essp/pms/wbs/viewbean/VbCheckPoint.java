package server.essp.pms.wbs.viewbean;



public class VbCheckPoint {
    private String name;
    /** nullable persistent field */
    private String weight;

    /** nullable persistent field */
    private String dueDate;

    /** nullable persistent field */
    private String finishDate;

    /** nullable persistent field */
    private String completed;

    /** nullable persistent field */
    private String remark;
    public String getCompleted() {
        return completed;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getName() {
        return name;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public String getRemark() {
        return remark;
    }

    public String getWeight() {
        return weight;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}
