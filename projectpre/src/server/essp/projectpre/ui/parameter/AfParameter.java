package server.essp.projectpre.ui.parameter;

import org.apache.struts.action.ActionForm;



public class AfParameter extends ActionForm {
    

	
	private String kind;
	
	private String code;
	private String name;   
    private String sequence;
	private String description;
	private String status;

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence =sequence ;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

  
}


