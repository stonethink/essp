/*
 * Created on 2007-12-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.hrbase.maintenance;

import c2s.dto.DtoBase;

public class DtoHrbSitePrivilege extends DtoBase{
    
    private String loginId;
    private String belongSite;
    private boolean status;
    private boolean visible;

    public String getBelongSite() {
        return belongSite;
    }
    public void setBelongSite(String belongSite) {
        this.belongSite = belongSite;
    }
    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    } 

  
}
