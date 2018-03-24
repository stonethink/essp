package server.essp.common.humanAllocate.viewbean;

/**
 * @author not attributable
 * @version 1.0
 */
public class VbDetailUser {
    private String name;
    private String loginid;
    private String code;
    private String sex;
    private String postrank;
    private String skill1;
    private String skill2;
    private String skillrank;
    private String industry;
    private String industryrank;
    private String language;
    private String languagerank;
    private String management;
    private String managementrank;

    public String getCode() {
        return code;
    }

    public String getLoginid() {
        return loginid;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getIndustry() {
        return industry;
    }

    public String getIndustryrank() {
        return postrank;
    }

    public String getLanguage() {
        return language;
    }

    public String getLanguagerank() {
        return languagerank;
    }

    public String getManagement() {
        return management;
    }

    public String getManagementrank() {
        return managementrank;
    }

    public String getPostrank() {
        return postrank;
    }

    public String getSkill1() {
        return skill1;
    }

    public String getSkill2() {
        return skill2;
    }

    public String getSkillrank() {
        return skillrank;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        if(VbGeneralUser.MALE_CODE.equals(sex))
            this.sex = "MALE";
        else if(VbGeneralUser.FEMALE_CODE.equals(sex))
            this.sex = "FEMALE";
        else
            this.sex = sex;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setIndustryrank(String industryRank) {
        this.industryrank = industryRank;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLanguagerank(String languageRank) {
        this.languagerank = languageRank;
    }

    public void setManagement(String management) {
        this.management = management;
    }

    public void setManagementrank(String managementRank) {
        this.managementrank = managementRank;
    }

    public void setPostrank(String postRank) {
        this.postrank = postRank;
    }

    public void setSkill1(String skill1) {
        this.skill1 = skill1;
    }

    public void setSkill2(String skill2) {
        this.skill2 = skill2;
    }

    public void setSkillrank(String skillRank) {
        this.skillrank = skillRank;
    }

}
