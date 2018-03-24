package server.essp.issue.typedefine.priority.form;

import org.apache.struts.action.*;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author QianLiu
 * @version 1.0
 */
public class AfPriority extends ActionForm {
    private String description="";
    private String duration="";
    private String priority="";
    private String sequence="";
    private String typeName="";
    private String selectedRowObj="";
    private String addPriority="";

    /**
     *
     * @return String
     */
    public String getDescription() {
        return description.trim();
    }

    /**
     *
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @param typeName String
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     *
     * @param sequence String
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     *
     * @param priority String
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     *
     * @param duration String
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setAddPriority(String addPriority) {
        this.addPriority = addPriority;
    }

    public void setSelectedRowObj(String selectedRowObj) {
        this.selectedRowObj = selectedRowObj;
    }

    /**
     *
     * @return String
     */
    public String getDuration() {
        return duration.trim();
    }

    /**
     *
     * @return String
     */
    public String getPriority() {
        return priority.trim();
    }

    /**
     *
     * @return String
     */
    public String getSequence() {
        return sequence.trim();
    }

    /**
     *
     * @return String
     */
    public String getTypeName() {
        return typeName.trim();
    }

    public String getAddPriority() {
        return addPriority;
    }

    public String getSelectedRowObj() {
        return selectedRowObj;
    }

}
