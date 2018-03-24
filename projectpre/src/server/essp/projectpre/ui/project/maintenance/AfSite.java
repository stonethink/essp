package server.essp.projectpre.ui.project.maintenance;

import org.apache.struts.action.ActionForm;

public class AfSite extends ActionForm {
	private String siteName;

	private String siteCode;
    
    private String manager;
	
	private String status;
    
    private String description;
    
    private Long siteLoading;

	public Long getSiteLoading() {
		return siteLoading;
	}

	public void setSiteLoading(Long siteLoading) {
		this.siteLoading = siteLoading;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
