package server.essp.issue.issue.conclusion.viewbean;

import java.util.List;

public class VbIssueConclusionUrgeList {
    /**
        * ��ѯ����б�
        */
       private List detail;
       /**
        * ���ص�������Ҫѡ�е���
        */
       private String selectedRowObj;
       /**
        * ��ѯ����
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
