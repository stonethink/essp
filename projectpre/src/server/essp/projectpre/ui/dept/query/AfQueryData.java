/*
 * Created on 2006-11-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.projectpre.ui.dept.query;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class AfQueryData extends ActionForm{
    
    private String acntId;
    private String applicantName;
    private String applicantId;
    private String acntName;
    private String deptManager;
    private String TCSName;
    private String achieveBelong;
    private List bdList;
    private String belongBd; 
    private String BDMName;
    private String DMLoginId;
    private String BDLoginId;
    private String TCSLoginId;
    public String getAchieveBelong() {
        return achieveBelong;
    }
    public void setAchieveBelong(String achieveBelong) {
        this.achieveBelong = achieveBelong;
    }
    public String getAcntId() {
        return acntId;
    }
    public void setAcntId(String acntId) {
        this.acntId = acntId;
    }
    public String getAcntName() {
        return acntName;
    }
    public void setAcntName(String acntName) {
        this.acntName = acntName;
    }
    public String getApplicantId() {
        return applicantId;
    }
    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }
    public String getApplicantName() {
        return applicantName;
    }
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    public String getBDLoginId() {
        return BDLoginId;
    }
    public void setBDLoginId(String loginId) {
        BDLoginId = loginId;
    }
    public String getBDMName() {
        return BDMName;
    }
    public void setBDMName(String name) {
        BDMName = name;
    }
    public String getDeptManager() {
        return deptManager;
    }
    public void setDeptManager(String deptManager) {
        this.deptManager = deptManager;
    }
    public String getDMLoginId() {
        return DMLoginId;
    }
    public void setDMLoginId(String loginId) {
        DMLoginId = loginId;
    }
    public String getTCSLoginId() {
        return TCSLoginId;
    }
    public void setTCSLoginId(String loginId) {
        TCSLoginId = loginId;
    }
    public String getTCSName() {
        return TCSName;
    }
    public void setTCSName(String name) {
        TCSName = name;
    }
    public List getBdList() {
        return bdList;
    }
    public void setBdList(List bdList) {
        this.bdList = bdList;
    }
 
    public void setBelongBd(String belongBd) {
        this.belongBd = belongBd;
    }
    public String getBelongBd() {
        return belongBd;
    }

}
