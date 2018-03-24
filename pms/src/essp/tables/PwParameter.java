package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="pw_parameter"
 *     
*/
public class PwParameter implements Serializable {

    /** identifier field */
    private Long rid;

    /** persistent field */
    private String paraTypeId;

    /** persistent field */
    private String paraType;

    /** nullable persistent field */
    private String paraName;

    /** nullable persistent field */
    private String paraExtName;

    /** nullable persistent field */
    private Integer paraSequence;

    /** nullable persistent field */
    private String paraDescreption;

    /** nullable persistent field */
    private String rst;

    /** nullable persistent field */
    private Date rct;

    /** nullable persistent field */
    private Date rut;

    /** full constructor */
    public PwParameter(Long rid, String paraTypeId, String paraType, String paraName, String paraExtName, Integer paraSequence, String paraDescreption, String rst, Date rct, Date rut) {
        this.rid = rid;
        this.paraTypeId = paraTypeId;
        this.paraType = paraType;
        this.paraName = paraName;
        this.paraExtName = paraExtName;
        this.paraSequence = paraSequence;
        this.paraDescreption = paraDescreption;
        this.rst = rst;
        this.rct = rct;
        this.rut = rut;
    }

    /** default constructor */
    public PwParameter() {
    }

    /** minimal constructor */
    public PwParameter(Long rid, String paraTypeId, String paraType) {
        this.rid = rid;
        this.paraTypeId = paraTypeId;
        this.paraType = paraType;
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
     *             column="PARA_TYPE_ID"
     *             length="50"
     *             not-null="true"
     *         
     */
    public String getParaTypeId() {
        return this.paraTypeId;
    }

    public void setParaTypeId(String paraTypeId) {
        this.paraTypeId = paraTypeId;
    }

    /** 
     *            @hibernate.property
     *             column="PARA_TYPE"
     *             length="50"
     *             not-null="true"
     *         
     */
    public String getParaType() {
        return this.paraType;
    }

    public void setParaType(String paraType) {
        this.paraType = paraType;
    }

    /** 
     *            @hibernate.property
     *             column="PARA_NAME"
     *             length="50"
     *         
     */
    public String getParaName() {
        return this.paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    /** 
     *            @hibernate.property
     *             column="PARA_EXT_NAME"
     *             length="50"
     *         
     */
    public String getParaExtName() {
        return this.paraExtName;
    }

    public void setParaExtName(String paraExtName) {
        this.paraExtName = paraExtName;
    }

    /** 
     *            @hibernate.property
     *             column="PARA_SEQUENCE"
     *             length="8"
     *         
     */
    public Integer getParaSequence() {
        return this.paraSequence;
    }

    public void setParaSequence(Integer paraSequence) {
        this.paraSequence = paraSequence;
    }

    /** 
     *            @hibernate.property
     *             column="PARA_DESCREPTION"
     *             length="65535"
     *         
     */
    public String getParaDescreption() {
        return this.paraDescreption;
    }

    public void setParaDescreption(String paraDescreption) {
        this.paraDescreption = paraDescreption;
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
     *             length="10"
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
     *             length="10"
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

    public boolean equals(Object other) {
        if ( !(other instanceof PwParameter) ) return false;
        PwParameter castOther = (PwParameter) other;
        return new EqualsBuilder()
            .append(this.getRid(), castOther.getRid())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getRid())
            .toHashCode();
    }

}
