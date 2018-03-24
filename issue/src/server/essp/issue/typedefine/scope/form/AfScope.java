package server.essp.issue.typedefine.scope.form;

import javax.servlet.http.*;

import org.apache.struts.action.*;

public class AfScope extends ActionForm {
    private String description;
    private String scope;
    private String sequence;
    private String vision;
    private String typeName;
    private String selectedRowObj;

    public AfScope() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public String getScope() {
        return scope;
    }

    public String getSequence() {
        return sequence;
    }


    public String getTypeName() {
        return typeName;
    }

    public ActionErrors validate(ActionMapping actionMapping,
                                 HttpServletRequest httpServletRequest) {
        /** @todo: finish this method, this is just the skeleton.*/
        return null;
    }

    public void reset(ActionMapping actionMapping,
                      HttpServletRequest servletRequest) {
    }
    public String getVision() {
        return vision;
    }

    public String getSelectedRowObj() {
        return selectedRowObj;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public void setSelectedRowObj(String selectedRowObj) {
        this.selectedRowObj = selectedRowObj;
    }
}
