package server.essp.timesheet.template.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class AfTemplate extends ActionForm {

	private String rid;

	private String templateCode;

	private String templateName;
	
	private String description;
	
	private List methodMap;
	
	private String rst;

	public String getRst() {
		return rst;
	}

	public void setRst(String rst) {
		this.rst = rst;
	}

	public List getMethodMap() {
		return methodMap;
	}

	public void setMethodMap(List methodMap) {
		this.methodMap = methodMap;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
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
