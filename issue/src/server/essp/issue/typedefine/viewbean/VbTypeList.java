package server.essp.issue.typedefine.viewbean;

import java.util.*;

public class VbTypeList {
    private String selectedRowObj = "";
    private String selectedTabIndex = "";
    private String addTypeName = "";
    private Long detailSize = new Long(0);
    private List detail = new ArrayList();

    public void setAddTypeName(String addTypeName) {
        this.addTypeName = addTypeName;
    }

    public String getAddTypeName() {
        return this.addTypeName;
    }

    public void setSelectedTabIndex(String selectedTabIndex) {
        this.selectedTabIndex = selectedTabIndex;
    }

    public void setSelectedRowObj(String selectedRowObj) {
        this.selectedRowObj = selectedRowObj;
    }

    public void setDetailSize(Long detailSize) {
        this.detailSize = detailSize;
    }

    public String getSelectedRowObj() {
        return this.selectedRowObj;
    }

    public String getSelectedTabIndex() {
        return this.selectedTabIndex;
    }

    public List getDetail() {
        return this.detail;
    }

    public Long getDetailSize() {
        return this.detailSize;
    }

    public VbTypeList() {
    }
}
