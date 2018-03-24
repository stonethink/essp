package server.essp.issue.typedefine.category.form;

import javax.servlet.http.*;

import org.apache.struts.action.*;

public class AfCategory extends ActionForm {
  private String categoryName;
  private String description;
  private String sequence;
  private String typeName;
  private String selectedRowObj;
  public String getCategoryName() {

    return categoryName;
  }

  public void setCategoryName(String categoryName) {

    this.categoryName = categoryName;
  }

  public void setSequence(String sequence) {
    this.sequence = sequence;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

    public void setSelectedRowObj(String selectedRowObj) {
        this.selectedRowObj = selectedRowObj;
    }

    public String getDescription() {
    return description;
  }

  public String getSequence() {
    return sequence;
  }

  public String getTypeName() {
    return typeName;
  }

    public String getSelectedRowObj() {
        return selectedRowObj;
    }

    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      /** @todo: finish this method, this is just the skeleton.*/
    return null;
  }

  public void reset(ActionMapping actionMapping, HttpServletRequest servletRequest) {
  }
}
