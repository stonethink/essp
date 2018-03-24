package server.essp.timesheet.methodology.form;

import org.apache.struts.action.ActionForm;

public class AfMethod extends ActionForm{
	
	private String rid;
	
	private String name;

	private String description;
	
	private String rst;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getRst() {
		return rst;
	}

	public void setRst(String rst) {
		this.rst = rst;
	}

	

}
