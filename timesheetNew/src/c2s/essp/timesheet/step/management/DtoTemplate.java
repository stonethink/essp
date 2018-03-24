package c2s.essp.timesheet.step.management;

import c2s.dto.DtoBase;

public class DtoTemplate extends DtoBase {
	public static String KEY_TEMPLATE_LIST = "TemplateList";
	private Long rid;
	private String templateCode;
	private String templateName;
	private String description;

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
