package server.essp.issue.issue.form;

import org.apache.struts.action.*;
/**
 * Issue查询条件
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
public class AfIssueSearch extends ActionForm {
    public final static String FUNCTION_SEARCH="SEARCH";
    public final static String FUNCTION_SORT="SORT";

    private String addonRidInfo;
    private String addonRid;

    private String functionType;

    private String sortInfo;
    private String sortItem;

    private String issueType;

    private String accountId;

    private String priority;

    /** persistent field */
    private String fillBy;

    private String fillDateBegin;

    private String fillDateEnd;

    private String scope;

    private String issueId;

    private String issueName;

    private String principal;

    private String dueDateBegin;

    private String dueDateEnd;

    private String status;

    private String pageStandard;
    private String pageNo;       //当前页号
    private String pageAction;   //其值为定义在PageAction中的常量:上一页、下一页、无动作

    private String resolveBy;
    private String principalScope;

    public String getAccountId() {
        return accountId;
    }

    public String getDueDateBegin() {
        return dueDateBegin;
    }

    public String getDueDateEnd() {
        return dueDateEnd;
    }

    public String getFillBy() {
        return fillBy;
    }

    public String getFillDateBegin() {
        return fillDateBegin;
    }

    public String getFillDateEnd() {
        return fillDateEnd;
    }

    public String getIssueId() {
        return issueId;
    }

    public String getIssueName() {
        return issueName;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getPriority() {
        return priority;
    }

    public String getScope() {
        return scope;
    }

    public String getStatus() {
        return status;
    }

    public String getPageAction() {
        return pageAction;
    }

    public String getPageNo() {
        return pageNo;
    }

    public String getSortInfo() {
        return sortInfo;
    }

    public String getSortItem() {
        return sortItem;
    }

    public String getPageStandard() {
        return pageStandard;
    }

    public String getAddonRid() {
        return addonRid;
    }

    public String getAddonRidInfo() {
        return addonRidInfo;
    }

    public String getFunctionType() {
        return functionType;
    }

    public String getResolveBy() {
        return resolveBy;
    }

    public String getPrincipalScope() {
        return principalScope;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public void setFillDateEnd(String fillDateEnd) {
        this.fillDateEnd = fillDateEnd;
    }

    public void setFillDateBegin(String fillDateBegin) {
        this.fillDateBegin = fillDateBegin;
    }

    public void setFillBy(String fillBy) {
        this.fillBy = fillBy;
    }

    public void setDueDateEnd(String dueDateEnd) {
        this.dueDateEnd = dueDateEnd;
    }

    public void setDueDateBegin(String dueDateBegin) {
        this.dueDateBegin = dueDateBegin;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public void setPageAction(String pageAction) {
        this.pageAction = pageAction;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public void setSortInfo(String sortInfo) {
        this.sortInfo = sortInfo;
    }

    public void setSortItem(String sortItem) {
        this.sortItem = sortItem;
    }

    public void setPageStandard(String pageStandard) {
        this.pageStandard = pageStandard;
    }

    public void setAddonRid(String addonRid) {
        this.addonRid = addonRid;
    }

    public void setAddonRidInfo(String addonRidInfo) {
        this.addonRidInfo = addonRidInfo;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public void setResolveBy(String resolveBy) {
        this.resolveBy = resolveBy;
    }

    public void setPrincipalScope(String principalScope) {
        this.principalScope = principalScope;
    }

    public String getFUNCTION_SEARCH(){
        return FUNCTION_SEARCH;
    }
    public String getFUNCTION_SORT(){
        return FUNCTION_SORT;
    }
    public String setFUNCTION_SEARCH(String FUNCTION_SEARCH){
       return null;
   }
   public String setFUNCTION_SORT(String FUNCTION_SORT){
       return null;
   }

}
