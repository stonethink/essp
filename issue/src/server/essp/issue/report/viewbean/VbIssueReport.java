package server.essp.issue.report.viewbean;

import java.util.List;

/**
 * Issue Report 页面显示所需内容
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VbIssueReport {

    private String dateBegin;

    private String dateEnd;

    private String submitFlag;

    private List accountList;

    private List typeList;

    public VbIssueReport() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public List getAccountList() {
        return accountList;
    }

    public String getSubmitFlag() {
        return submitFlag;
    }

    public List getTypeList() {
        return typeList;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public void setAccountList(List accountList) {
        this.accountList = accountList;
    }

    public void setSubmitFlag(String submitFlag) {
        this.submitFlag = submitFlag;
    }

    public void setTypeList(List typeList) {
        this.typeList = typeList;
    }

}
