package server.essp.projectpre.ui.project.maintenance;

import org.apache.struts.action.ActionForm;

public class AfProjectId extends ActionForm {
    private String idType;
    
    private String length;
    
    private String codingMethod;
    
    private String currentSeq;

    public String getCodingMethod() {
        return codingMethod;
    }

    public void setCodingMethod(String codingMethod) {
        this.codingMethod = codingMethod;
    }

    public String getCurrentSeq() {
        return currentSeq;
    }

    public void setCurrentSeq(String currentSeq) {
        this.currentSeq = currentSeq;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

   
}
