package server.essp.common.humanAllocate.viewbean;

import java.util.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VbAllocByCon {
        /**
         * Post Rank
         */
        private List postRanks = new ArrayList();
        /**
         * Skill
         */
        private List skillLevel1 = new ArrayList();
        private List skillLevel2 = new ArrayList();
        /**
         * Industry
         */
        private List opera1 = new ArrayList();
        /**
         * Language
         */
        private List languages = new ArrayList();
        /**
         * Management
         */
        private List managements = new ArrayList();

        private List generalRanks = new ArrayList();

        private List detail = new ArrayList();

        private String typeList;
    public List getGeneralRanks() {
        return generalRanks;
    }

    public List getLanguages() {
        return languages;
    }

    public List getManagements() {
        return managements;
    }

    public List getOpera1() {
        return opera1;
    }

    public List getPostRanks() {
        return postRanks;
    }

    public List getSkillLevel1() {
        return skillLevel1;
    }

    public List getSkillLevel2() {
        return skillLevel2;
    }

    public List getDetail() {
        return detail;
    }

    public String getTypeList() {
        return typeList;
    }

    public void setGeneralRanks(List generalRanks) {
        this.generalRanks = generalRanks;
    }

    public void setLanguages(List languages) {
        this.languages = languages;
    }

    public void setManagements(List managements) {
        this.managements = managements;
    }

    public void setOpera1(List opera1) {
        this.opera1 = opera1;
    }

    public void setPostRanks(List postRanks) {
        this.postRanks = postRanks;
    }

    public void setSkillLevel1(List skillLevel1) {
        this.skillLevel1 = skillLevel1;
    }

    public void setSkillLevel2(List skillLevel2) {
        this.skillLevel2 = skillLevel2;
    }

    public void setDetail(List detail) {
        this.detail = detail;
    }

    public void setTypeList(String typeList) {
        this.typeList = typeList;
    }


}
