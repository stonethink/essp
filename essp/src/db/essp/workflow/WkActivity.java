package db.essp.workflow;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;


/** 
 *        @hibernate.class
 *         table="wk_activity"
 *     
*/
public class WkActivity implements Serializable {

    /** identifier field */
    private Long rid;

    /** nullable persistent field */
    private Long instanceID;

    /** nullable persistent field */
    private String processClassName;

    /** nullable persistent field */
    private String currentEmpLoginID;

    /** nullable persistent field */
    private String status;

    /** nullable persistent field */
    private String steps;

    /** nullable persistent field */
    private Date startDate;

    /** nullable persistent field */
    private Date finishDate;

    /** nullable persistent field */
    private String defineID;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public WkActivity(Long rid, Long instanceID, String processClassName, String currentEmpLoginID, String status, String steps, Date startDate, Date finishDate, String defineID, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.instanceID = instanceID;
        this.processClassName = processClassName;
        this.currentEmpLoginID = currentEmpLoginID;
        this.status = status;
        this.steps = steps;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.defineID = defineID;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public WkActivity() {
    }

    /** minimal constructor */
    public WkActivity(Long rid) {
        this.rid = rid;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="RID"
     *         
     */
    public Long getRid() {
        return this.rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    /** 
     *            @hibernate.property
     *             column="INSTANCE_ID"
     *             length="8"
     *         
     */
    public Long getInstanceID() {
        return this.instanceID;
    }

    public void setInstanceID(Long instanceID) {
        this.instanceID = instanceID;
    }

    /** 
     *            @hibernate.property
     *             column="PROCESS_CLASS_NAME"
     *             length="100"
     *         
     */
    public String getProcessClassName() {
        return this.processClassName;
    }

    public void setProcessClassName(String processClassName) {
        this.processClassName = processClassName;
    }

    /** 
     *            @hibernate.property
     *             column="CURRENT_EMP_LOGINID"
     *             length="100"
     *         
     */
    public String getCurrentEmpLoginID() {
        return this.currentEmpLoginID;
    }

    public void setCurrentEmpLoginID(String currentEmpLoginID) {
        this.currentEmpLoginID = currentEmpLoginID;
    }

    /** 
     *            @hibernate.property
     *             column="STATUS"
     *             length="10"
     *         
     */
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /** 
     *            @hibernate.property
     *             column="STEPS"
     *             length="100"
     *         
     */
    public String getSteps() {
        return this.steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    /** 
     *            @hibernate.property
     *             column="START_DATE"
     *             length="19"
     *         
     */
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /** 
     *            @hibernate.property
     *             column="FINISH_DATE"
     *             length="19"
     *         
     */
    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    /** 
     *            @hibernate.property
     *             column="DEFINE_ID"
     *             length="20"
     *         
     */
    public String getDefineID() {
        return this.defineID;
    }

    public void setDefineID(String defineID) {
        this.defineID = defineID;
    }

    /** 
     *            @hibernate.property
     *             column="RST"
     *             length="1"
     *         
     */
    public String getRst() {
        return this.rst;
    }

    public void setRst(String rst) {
        this.rst = rst;
    }

    /** 
     *            @hibernate.property
     *             column="RCT"
     *             length="19"
     *         
     */
    public Date getRct() {
        return this.rct;
    }

    public void setRct(Date rct) {
        this.rct = rct;
    }

    /** 
     *            @hibernate.property
     *             column="RUT"
     *             length="19"
     *         
     */
    public Date getRut() {
        return this.rut;
    }

    public void setRut(Date rut) {
        this.rut = rut;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("rid", getRid())
            .toString();
    }

}
