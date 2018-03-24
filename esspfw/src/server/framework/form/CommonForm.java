package server.framework.form;

import org.apache.struts.action.*;

public class CommonForm extends ActionForm {
    public final static String QUERY="QUERY";
    public final static String ADD="ADD";
    public final static String INITADDPAGE="INITADDPAGE";
    public final static String DELETE="DELETE";
    public final static String UPDATE="UPDATE";
    public final static String INITUPDATEPAGE="INITUPDATEPAGE";
    public final static String EDIT="EDIT"; // same to UPDATE
    public final static String DETAIL="DETAIL";

    private String functionId="";

    public CommonForm() {
        super();
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }
}
