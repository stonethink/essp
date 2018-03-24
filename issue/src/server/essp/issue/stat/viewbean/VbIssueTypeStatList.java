package server.essp.issue.stat.viewbean;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有Issue Type的Issue统计列表，同时包含有权限项目列表
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
public class VbIssueTypeStatList {
    private String accountId;
    private List accountList = new ArrayList();
    private List detail = new ArrayList();
    public List getDetail() {
        return detail;
    }

    public String getAccountId() {
        return accountId;
    }

    public List getAccountList() {
        return accountList;
    }

    public void setDetail(List detail) {
        this.detail = detail;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAccountList(List accountList) {
        this.accountList = accountList;
    }
    /** @link dependency */
    /*# VbIssueTypeStat lnkVbIssueTypeStat; */
}
