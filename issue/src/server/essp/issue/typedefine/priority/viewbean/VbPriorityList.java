package server.essp.issue.typedefine.priority.viewbean;

import java.util.*;

public class VbPriorityList {
    private List detail = new ArrayList();
    private Long detailSize = new Long(0);
    private String selectedRowObj = "";
    private String addPriority = "";
    private String typeName="";

    public VbPriorityList() {
    }

    public void setTypeName(String typeName){
      this.typeName=typeName;
    }

    public String getTypeName(){
      return this.typeName;
    }

    public String getAddPriority() {
        return this.addPriority;
    }

    public void setAddPriority(String addPriority) {
        this.addPriority = addPriority;
    }

    public List getDetail() {
        return detail;
    }

    public void setDetailSize(Long detailSize) {
        this.detailSize = detailSize;
    }

    public Long getDetailSize() {
        return this.detailSize;
    }

    public String getSelectedRowObj() {
        return selectedRowObj;
    }

    public void setSelectedRowObj(String selectedRowObj) {
        this.selectedRowObj = selectedRowObj;
    }
}
