package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="essp_sys_account_t"
 *     
*/
public class EsspSysAccountT implements Serializable {

    /** identifier field */
    private Long id;

    /** persistent field */
    private String accountCode;

    /** persistent field */
    private String accountName;

    /** nullable persistent field */
    private String organization;

    /** nullable persistent field */
    private String manager;

    /** nullable persistent field */
    private Date planStart;

    /** nullable persistent field */
    private Date planFinish;

    /** nullable persistent field */
    private Date actualStart;

    /** nullable persistent field */
    private Date actualFinish;

    /** nullable persistent field */
    private String description;

    /** nullable persistent field */
    private Long accountType;

    /** nullable persistent field */
    private byte[] budget;

    /** nullable persistent field */
    private String currency;

    /** nullable persistent field */
    private String showfields;

    /** nullable persistent field */
    private String status;

    /** full constructor */
    public EsspSysAccountT(Long id, String accountCode, String accountName, String organization, String manager, Date planStart, Date planFinish, Date actualStart, Date actualFinish, String description, Long accountType, byte[] budget, String currency, String showfields, String status) {
        this.id = id;
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.organization = organization;
        this.manager = manager;
        this.planStart = planStart;
        this.planFinish = planFinish;
        this.actualStart = actualStart;
        this.actualFinish = actualFinish;
        this.description = description;
        this.accountType = accountType;
        this.budget = budget;
        this.currency = currency;
        this.showfields = showfields;
        this.status = status;
    }

    /** default constructor */
    public EsspSysAccountT() {
    }

    /** minimal constructor */
    public EsspSysAccountT(Long id, String accountCode, String accountName) {
        this.id = id;
        this.accountCode = accountCode;
        this.accountName = accountName;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Long"
     *             column="ID"
     *         
     */
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** 
     *            @hibernate.property
     *             column="ACCOUNT_CODE"
     *             length="50"
     *             not-null="true"
     *         
     */
    public String getAccountCode() {
        return this.accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    /** 
     *            @hibernate.property
     *             column="ACCOUNT_NAME"
     *             length="50"
     *             not-null="true"
     *         
     */
    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /** 
     *            @hibernate.property
     *             column="ORGANIZATION"
     *             length="20"
     *         
     */
    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /** 
     *            @hibernate.property
     *             column="MANAGER"
     *             length="20"
     *         
     */
    public String getManager() {
        return this.manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    /** 
     *            @hibernate.property
     *             column="PLAN_START"
     *             length="10"
     *         
     */
    public Date getPlanStart() {
        return this.planStart;
    }

    public void setPlanStart(Date planStart) {
        this.planStart = planStart;
    }

    /** 
     *            @hibernate.property
     *             column="PLAN_FINISH"
     *             length="10"
     *         
     */
    public Date getPlanFinish() {
        return this.planFinish;
    }

    public void setPlanFinish(Date planFinish) {
        this.planFinish = planFinish;
    }

    /** 
     *            @hibernate.property
     *             column="ACTUAL_START"
     *             length="10"
     *         
     */
    public Date getActualStart() {
        return this.actualStart;
    }

    public void setActualStart(Date actualStart) {
        this.actualStart = actualStart;
    }

    /** 
     *            @hibernate.property
     *             column="ACTUAL_FINISH"
     *             length="10"
     *         
     */
    public Date getActualFinish() {
        return this.actualFinish;
    }

    public void setActualFinish(Date actualFinish) {
        this.actualFinish = actualFinish;
    }

    /** 
     *            @hibernate.property
     *             column="DESCRIPTION"
     *             length="255"
     *         
     */
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** 
     *            @hibernate.property
     *             column="ACCOUNT_TYPE"
     *             length="10"
     *         
     */
    public Long getAccountType() {
        return this.accountType;
    }

    public void setAccountType(Long accountType) {
        this.accountType = accountType;
    }

    /** 
     *            @hibernate.property
     *             column="BUDGET"
     *             length="65535"
     *         
     */
    public byte[] getBudget() {
        return this.budget;
    }

    public void setBudget(byte[] budget) {
        this.budget = budget;
    }

    /** 
     *            @hibernate.property
     *             column="CURRENCY"
     *             length="20"
     *         
     */
    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /** 
     *            @hibernate.property
     *             column="SHOWFIELDS"
     *             length="65535"
     *         
     */
    public String getShowfields() {
        return this.showfields;
    }

    public void setShowfields(String showfields) {
        this.showfields = showfields;
    }

    /** 
     *            @hibernate.property
     *             column="STATUS"
     *             length="20"
     *         
     */
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof EsspSysAccountT) ) return false;
        EsspSysAccountT castOther = (EsspSysAccountT) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
