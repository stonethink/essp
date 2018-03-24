package server.essp.issue.typedefine.status.form;

import org.apache.struts.action.*;
public class AfStatus extends ActionForm {

      private String statusName = "";
      private String statusSequence = "";
      private String statusBelongTo = "";
      private String statusRelationDate = "";
      private String statusDescription = "";

      private String typeName = "";
      private String selectedRow="";

  public AfStatus() {
  }

  public String getStatusBelongTo() {
    return statusBelongTo;
  }

  public String getStatusDescription() {
    return statusDescription;
  }

  public String getStatusRelationDate() {
    return statusRelationDate;
  }

  public String getStatusSequence() {
    return statusSequence;
  }

  public String getStatusName() {
    return statusName;
  }

  public String getTypeName() {
    return typeName;
  }

    public String getSelectedRow() {
        return selectedRow;
    }

    public void setStatusBelongTo(String statusBelongTo) {
    this.statusBelongTo = statusBelongTo;
  }

  public void setStatusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
  }

  public void setStatusRelationDate(String statusRelationDate) {
    this.statusRelationDate = statusRelationDate;
  }

  public void setStatusSequence(String statusSequence) {
    this.statusSequence = statusSequence;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

    public void setSelectedRow(String selectedRow) {
        this.selectedRow = selectedRow;
    }

}
