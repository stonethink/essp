/*
 * Created on 2008-3-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package server.essp.hrbase.attribute.form;

import org.apache.struts.action.ActionForm;

public class AfAttribute extends ActionForm{
    
    private String rid;
    private String code;
    private String description;
    private String isEnable;
    private String seq;
    
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
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getRid() {
        return rid;
    }
    public void setRid(String rid) {
        this.rid = rid;
    }
    public String getSeq() {
        return seq;
    }
    public void setSeq(String seq) {
        this.seq = seq;
    }

}
