package server.essp.issue.typedefine.status.viewbean;

import java.util.*;

import server.framework.taglib.util.*;
import server.essp.issue.common.constant.RelationDate;
import server.essp.issue.common.constant.Status;

public class VbStatus {

    private String statusName = "";
    private String statusSequence = "";
    private String statusBelongTo = "";
    private String statusRelationDate = "";
    private String statusDescription = "";

    private String typeName = "";

    private List belongToArray = new ArrayList(6);
    private List relationDateArray = new ArrayList(3);

    private List statusList = new ArrayList();
    private String deleterefresh = "";
    private String selectedRow = "tr0";

    public VbStatus() {
      belongToArray.add(new SelectOptionImpl("-- Please Select --",""));
      belongToArray.add(new SelectOptionImpl(Status.RECEIVED,Status.RECEIVED));
      belongToArray.add(new SelectOptionImpl(Status.PROCESSING,Status.PROCESSING));
      belongToArray.add(new SelectOptionImpl(Status.DELIVERED,Status.DELIVERED));
      belongToArray.add(new SelectOptionImpl(Status.CLOSED,Status.CLOSED));
      belongToArray.add(new SelectOptionImpl(Status.REJECTED,Status.REJECTED));
      belongToArray.add(new SelectOptionImpl(Status.DUPLATION,Status.DUPLATION));

      relationDateArray.add(new SelectOptionImpl("-- Please Select --",""));
      relationDateArray.add(new SelectOptionImpl(RelationDate.ASSIGNED_DATE,RelationDate.ASSIGNED_DATE));
      relationDateArray.add(new SelectOptionImpl(RelationDate.FINISHED_DATE,RelationDate.FINISHED_DATE));
      relationDateArray.add(new SelectOptionImpl(RelationDate.DELIVERED_DATE,RelationDate.DELIVERED_DATE));
      relationDateArray.add(new SelectOptionImpl(RelationDate.CONFIRM_DATE,RelationDate.CONFIRM_DATE));
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

      public List getBelongToArray() {
          return belongToArray;
      }

      public List getRelationDateArray() {
          return relationDateArray;
      }

    public List getStatusList() {
        return statusList;
    }

    public String getDeleterefresh() {
        return deleterefresh;
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

    public void setStatusList(List statusList) {
        this.statusList = statusList;
    }

    public void setDeleterefresh(String deleterefresh) {
        this.deleterefresh = deleterefresh;
    }

    public void setSelectedRow(String selectedRow) {
        this.selectedRow = selectedRow;
    }

}
