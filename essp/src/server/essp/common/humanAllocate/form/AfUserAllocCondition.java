package server.essp.common.humanAllocate.form;

import javax.servlet.http.*;

import org.apache.struts.action.*;

public class AfUserAllocCondition extends ActionForm {
    private String postRank;
    private String skill1;
    private String skill2;
    private String skillRank;
    private String industry;
    private String industryRank;
    private String language;
    private String languageRank;
    private String management;
    private String managementRank;
    public ActionErrors validate(ActionMapping actionMapping,
                                 HttpServletRequest httpServletRequest) {
            /** @todo: finish this method, this is just the skeleton.*/
        return null;
    }

    public void reset(ActionMapping actionMapping,
                      HttpServletRequest servletRequest) {
    }

    public void setPostRank(String postRank) {
        this.postRank = postRank;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    public void setSkillRank(String skillRank) {
        this.skillRank = skillRank;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setIndustryRank(String industryRank) {
        this.industryRank = industryRank;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLanguageRank(String languageRank) {
        this.languageRank = languageRank;
    }

    public void setManagement(String management) {
        this.management = management;
    }

    public void setManagementRank(String managementRank) {
        this.managementRank = managementRank;
    }

    public String getPostRank() {
        return postRank;
    }

    public String getSkill1() {
        return skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public String getSkillRank() {
        return skillRank;
    }

    public String getIndustry() {
        return industry;
    }

    public String getIndustryRank() {
        return industryRank;
    }

    public String getLanguage() {
        return language;
    }

    public String getLanguageRank() {
        return languageRank;
    }

    public String getManagement() {
        return management;
    }

    public String getManagementRank() {
        return managementRank;
    }
}
