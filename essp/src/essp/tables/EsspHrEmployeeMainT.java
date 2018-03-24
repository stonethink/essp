package essp.tables;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** 
 *        @hibernate.class
 *         table="essp_hr_employee_main_t"
 *     
*/
public class EsspHrEmployeeMainT implements Serializable {

    /** identifier field */
    private String code;

    /** nullable persistent field */
    private String empCode;

    /** persistent field */
    private String name;

    /** nullable persistent field */
    private String sex;

    /** nullable persistent field */
    private Date birth;

    /** nullable persistent field */
    private String nationality;

    /** nullable persistent field */
    private String socialSecurityId;

    /** nullable persistent field */
    private String socialSecurityDescription;

    /** nullable persistent field */
    private String nativePlace;

    /** nullable persistent field */
    private String marry;

    /** nullable persistent field */
    private String rprAddress;

    /** nullable persistent field */
    private String cellPjone;

    /** nullable persistent field */
    private String call;

    /** nullable persistent field */
    private String homePhone;

    /** nullable persistent field */
    private String EMail;

    /** nullable persistent field */
    private String postalcode;

    /** nullable persistent field */
    private String address;

    /** nullable persistent field */
    private String famililaName1;

    /** nullable persistent field */
    private String famililaNexus1;

    /** nullable persistent field */
    private String famililaContact1;

    /** nullable persistent field */
    private String famililaName2;

    /** nullable persistent field */
    private String famililaNexus2;

    /** nullable persistent field */
    private String famililaContact2;

    /** nullable persistent field */
    private String famililaName3;

    /** nullable persistent field */
    private String famililaNexus3;

    /** nullable persistent field */
    private String famililaContact3;

    /** nullable persistent field */
    private String passNo;

    /** nullable persistent field */
    private String visaNo;

    /** nullable persistent field */
    private Date visaPeriod;

    /** nullable persistent field */
    private String visaNation;

    /** nullable persistent field */
    private String dimissionFlag;

    /** nullable persistent field */
    private String types;

    /** nullable persistent field */
    private String dept;

    /** nullable persistent field */
    private String positionLevel;

    /** nullable persistent field */
    private String status;

    /** nullable persistent field */
    private String company;

    /** nullable persistent field */
    private String photo;

    /** nullable persistent field */
    private String ldap;

    /** full constructor */
    public EsspHrEmployeeMainT(String code, String empCode, String name, String sex, Date birth, String nationality, String socialSecurityId, String socialSecurityDescription, String nativePlace, String marry, String rprAddress, String cellPjone, String call, String homePhone, String EMail, String postalcode, String address, String famililaName1, String famililaNexus1, String famililaContact1, String famililaName2, String famililaNexus2, String famililaContact2, String famililaName3, String famililaNexus3, String famililaContact3, String passNo, String visaNo, Date visaPeriod, String visaNation, String dimissionFlag, String types, String dept, String positionLevel, String status, String company, String photo, String ldap) {
        this.code = code;
        this.empCode = empCode;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.nationality = nationality;
        this.socialSecurityId = socialSecurityId;
        this.socialSecurityDescription = socialSecurityDescription;
        this.nativePlace = nativePlace;
        this.marry = marry;
        this.rprAddress = rprAddress;
        this.cellPjone = cellPjone;
        this.call = call;
        this.homePhone = homePhone;
        this.EMail = EMail;
        this.postalcode = postalcode;
        this.address = address;
        this.famililaName1 = famililaName1;
        this.famililaNexus1 = famililaNexus1;
        this.famililaContact1 = famililaContact1;
        this.famililaName2 = famililaName2;
        this.famililaNexus2 = famililaNexus2;
        this.famililaContact2 = famililaContact2;
        this.famililaName3 = famililaName3;
        this.famililaNexus3 = famililaNexus3;
        this.famililaContact3 = famililaContact3;
        this.passNo = passNo;
        this.visaNo = visaNo;
        this.visaPeriod = visaPeriod;
        this.visaNation = visaNation;
        this.dimissionFlag = dimissionFlag;
        this.types = types;
        this.dept = dept;
        this.positionLevel = positionLevel;
        this.status = status;
        this.company = company;
        this.photo = photo;
        this.ldap = ldap;
    }

    /** default constructor */
    public EsspHrEmployeeMainT() {
    }

    /** minimal constructor */
    public EsspHrEmployeeMainT(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /** 
     *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.String"
     *             column="CODE"
     *         
     */
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /** 
     *            @hibernate.property
     *             column="EMP_CODE"
     *             length="20"
     *         
     */
    public String getEmpCode() {
        return this.empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    /** 
     *            @hibernate.property
     *             column="NAME"
     *             length="50"
     *             not-null="true"
     *         
     */
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** 
     *            @hibernate.property
     *             column="SEX"
     *             length="1"
     *         
     */
    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    /** 
     *            @hibernate.property
     *             column="BIRTH"
     *             length="10"
     *         
     */
    public Date getBirth() {
        return this.birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /** 
     *            @hibernate.property
     *             column="NATIONALITY"
     *             length="20"
     *         
     */
    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /** 
     *            @hibernate.property
     *             column="SOCIAL_SECURITY_ID"
     *             length="20"
     *         
     */
    public String getSocialSecurityId() {
        return this.socialSecurityId;
    }

    public void setSocialSecurityId(String socialSecurityId) {
        this.socialSecurityId = socialSecurityId;
    }

    /** 
     *            @hibernate.property
     *             column="SOCIAL_SECURITY_DESCRIPTION"
     *             length="200"
     *         
     */
    public String getSocialSecurityDescription() {
        return this.socialSecurityDescription;
    }

    public void setSocialSecurityDescription(String socialSecurityDescription) {
        this.socialSecurityDescription = socialSecurityDescription;
    }

    /** 
     *            @hibernate.property
     *             column="NATIVE_PLACE"
     *             length="200"
     *         
     */
    public String getNativePlace() {
        return this.nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    /** 
     *            @hibernate.property
     *             column="MARRY"
     *             length="1"
     *         
     */
    public String getMarry() {
        return this.marry;
    }

    public void setMarry(String marry) {
        this.marry = marry;
    }

    /** 
     *            @hibernate.property
     *             column="RPR_ADDRESS"
     *             length="200"
     *         
     */
    public String getRprAddress() {
        return this.rprAddress;
    }

    public void setRprAddress(String rprAddress) {
        this.rprAddress = rprAddress;
    }

    /** 
     *            @hibernate.property
     *             column="CELL_PJONE"
     *             length="20"
     *         
     */
    public String getCellPjone() {
        return this.cellPjone;
    }

    public void setCellPjone(String cellPjone) {
        this.cellPjone = cellPjone;
    }

    /** 
     *            @hibernate.property
     *             column="CALL"
     *             length="20"
     *         
     */
    public String getCall() {
        return this.call;
    }

    public void setCall(String call) {
        this.call = call;
    }

    /** 
     *            @hibernate.property
     *             column="HOME_PHONE"
     *             length="20"
     *         
     */
    public String getHomePhone() {
        return this.homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /** 
     *            @hibernate.property
     *             column="E_MAIL"
     *             length="50"
     *         
     */
    public String getEMail() {
        return this.EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    /** 
     *            @hibernate.property
     *             column="POSTALCODE"
     *             length="10"
     *         
     */
    public String getPostalcode() {
        return this.postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    /** 
     *            @hibernate.property
     *             column="ADDRESS"
     *             length="200"
     *         
     */
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /** 
     *            @hibernate.property
     *             column="FAMILILA_NAME1"
     *             length="50"
     *         
     */
    public String getFamililaName1() {
        return this.famililaName1;
    }

    public void setFamililaName1(String famililaName1) {
        this.famililaName1 = famililaName1;
    }

    /** 
     *            @hibernate.property
     *             column="FAMILILA_NEXUS1"
     *             length="20"
     *         
     */
    public String getFamililaNexus1() {
        return this.famililaNexus1;
    }

    public void setFamililaNexus1(String famililaNexus1) {
        this.famililaNexus1 = famililaNexus1;
    }

    /** 
     *            @hibernate.property
     *             column="FAMILILA_CONTACT1"
     *             length="200"
     *         
     */
    public String getFamililaContact1() {
        return this.famililaContact1;
    }

    public void setFamililaContact1(String famililaContact1) {
        this.famililaContact1 = famililaContact1;
    }

    /** 
     *            @hibernate.property
     *             column="FAMILILA_NAME2"
     *             length="50"
     *         
     */
    public String getFamililaName2() {
        return this.famililaName2;
    }

    public void setFamililaName2(String famililaName2) {
        this.famililaName2 = famililaName2;
    }

    /** 
     *            @hibernate.property
     *             column="FAMILILA_NEXUS2"
     *             length="20"
     *         
     */
    public String getFamililaNexus2() {
        return this.famililaNexus2;
    }

    public void setFamililaNexus2(String famililaNexus2) {
        this.famililaNexus2 = famililaNexus2;
    }

    /** 
     *            @hibernate.property
     *             column="FAMILILA_CONTACT2"
     *             length="200"
     *         
     */
    public String getFamililaContact2() {
        return this.famililaContact2;
    }

    public void setFamililaContact2(String famililaContact2) {
        this.famililaContact2 = famililaContact2;
    }

    /** 
     *            @hibernate.property
     *             column="FAMILILA_NAME3"
     *             length="50"
     *         
     */
    public String getFamililaName3() {
        return this.famililaName3;
    }

    public void setFamililaName3(String famililaName3) {
        this.famililaName3 = famililaName3;
    }

    /** 
     *            @hibernate.property
     *             column="FAMILILA_NEXUS3"
     *             length="20"
     *         
     */
    public String getFamililaNexus3() {
        return this.famililaNexus3;
    }

    public void setFamililaNexus3(String famililaNexus3) {
        this.famililaNexus3 = famililaNexus3;
    }

    /** 
     *            @hibernate.property
     *             column="FAMILILA_CONTACT3"
     *             length="200"
     *         
     */
    public String getFamililaContact3() {
        return this.famililaContact3;
    }

    public void setFamililaContact3(String famililaContact3) {
        this.famililaContact3 = famililaContact3;
    }

    /** 
     *            @hibernate.property
     *             column="PASS_NO"
     *             length="20"
     *         
     */
    public String getPassNo() {
        return this.passNo;
    }

    public void setPassNo(String passNo) {
        this.passNo = passNo;
    }

    /** 
     *            @hibernate.property
     *             column="VISA_NO"
     *             length="20"
     *         
     */
    public String getVisaNo() {
        return this.visaNo;
    }

    public void setVisaNo(String visaNo) {
        this.visaNo = visaNo;
    }

    /** 
     *            @hibernate.property
     *             column="VISA_PERIOD"
     *             length="10"
     *         
     */
    public Date getVisaPeriod() {
        return this.visaPeriod;
    }

    public void setVisaPeriod(Date visaPeriod) {
        this.visaPeriod = visaPeriod;
    }

    /** 
     *            @hibernate.property
     *             column="VISA_NATION"
     *             length="20"
     *         
     */
    public String getVisaNation() {
        return this.visaNation;
    }

    public void setVisaNation(String visaNation) {
        this.visaNation = visaNation;
    }

    /** 
     *            @hibernate.property
     *             column="DIMISSION_FLAG"
     *             length="1"
     *         
     */
    public String getDimissionFlag() {
        return this.dimissionFlag;
    }

    public void setDimissionFlag(String dimissionFlag) {
        this.dimissionFlag = dimissionFlag;
    }

    /** 
     *            @hibernate.property
     *             column="TYPES"
     *             length="1"
     *         
     */
    public String getTypes() {
        return this.types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    /** 
     *            @hibernate.property
     *             column="DEPT"
     *             length="100"
     *         
     */
    public String getDept() {
        return this.dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    /** 
     *            @hibernate.property
     *             column="POSITION_LEVEL"
     *             length="3"
     *         
     */
    public String getPositionLevel() {
        return this.positionLevel;
    }

    public void setPositionLevel(String positionLevel) {
        this.positionLevel = positionLevel;
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

    /** 
     *            @hibernate.property
     *             column="COMPANY"
     *             length="20"
     *         
     */
    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    /** 
     *            @hibernate.property
     *             column="PHOTO"
     *             length="100"
     *         
     */
    public String getPhoto() {
        return this.photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /** 
     *            @hibernate.property
     *             column="LDAP"
     *             length="100"
     *         
     */
    public String getLdap() {
        return this.ldap;
    }

    public void setLdap(String ldap) {
        this.ldap = ldap;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("code", getCode())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof EsspHrEmployeeMainT) ) return false;
        EsspHrEmployeeMainT castOther = (EsspHrEmployeeMainT) other;
        return new EqualsBuilder()
            .append(this.getCode(), castOther.getCode())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getCode())
            .toHashCode();
    }

}
