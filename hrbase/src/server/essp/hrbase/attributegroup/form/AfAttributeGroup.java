/*
 * Created on 2008-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attributegroup.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class AfAttributeGroup extends ActionForm{

    private String rid;
    private String attributeRid;
    private String hrbAttCode;
    private String site;
    private String isFormal;
    private String code;
    private String description;
    private String isEnable;
    private List hrbAttList;
    private List siteList;
    
    public String getHrbAttCode() {
        return hrbAttCode;
    }
    public void setHrbAttCode(String hrbAttCode) {
        this.hrbAttCode = hrbAttCode;
    }
    public List getHrbAttList() {
        return hrbAttList;
    }
    public void setHrbAttList(List hrbAttList) {
        this.hrbAttList = hrbAttList;
    }
    public List getSiteList() {
        return siteList;
    }
    public void setSiteList(List siteList) {
        this.siteList = siteList;
    }
    public String getAttributeRid() {
        return attributeRid;
    }
    public void setAttributeRid(String attributeRid) {
        this.attributeRid = attributeRid;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIsEnable() {
        return isEnable;
    }
    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }
    public String getIsFormal() {
        return isFormal;
    }
    public void setIsFormal(String isFormal) {
        this.isFormal = isFormal;
    }
    public String getRid() {
        return rid;
    }
    public void setRid(String rid) {
        this.rid = rid;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
}
