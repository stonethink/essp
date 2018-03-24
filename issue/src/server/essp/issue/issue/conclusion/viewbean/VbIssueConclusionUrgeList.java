package server.essp.issue.issue.conclusion.viewbean;

import java.util.List;

public class VbIssueConclusionUrgeList {
    /**
        * 查询结果列表
        */
       private List detail;
       /**
        * 返回到界面上要选中的行
        */
       private String selectedRowObj;
       /**
        * 查询条件
        */
       private String issueRid;
       private int detailSize;




    public VbIssueConclusionUrgeList() {
    }

    public List getDetail() {
        return detail;
    }

    public int getDetailSize() {
        if(detail == null)
               detailSize = 0;
           else
               detailSize = detail.size();
           return detailSize;

    }

    public String getIssueRid() {
        return issueRid;
    }

    public String getSelectedRowObj() {
        return selectedRowObj;
    }

    public void setDetail(List detail) {
        this.detail = detail;
    }

    public void setDetailSize(int detailSize) {
        this.detailSize = detailSize;
    }

    public void setIssueRid(String issueRid) {
        this.issueRid = issueRid;
    }

    public void setSelectedRowObj(String selectedRowObj) {
        this.selectedRowObj = selectedRowObj;
    }
}
