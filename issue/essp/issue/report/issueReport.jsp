<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="java.net.*,
	java.util.*,
	com.crystaldecisions.sdk.occa.report.data.*,
	com.crystaldecisions.sdk.occa.report.application.*,
	com.crystaldecisions.sdk.occa.report.reportsource.IReportSource,
	com.crystaldecisions.sdk.occa.report.lib.ReportSDKException"%>
<%@page import="com.crystaldecisions.report.web.viewer.*,
com.crystaldecisions.sdk.occa.report.data.*,
server.framework.hibernate.HBComAccess,
server.essp.issue.report.form.AfReportForm"%>
<%
  AfReportForm reportForm = (AfReportForm) session.getAttribute("ReportForm");
  String account = reportForm.getAccount();
  String userName = reportForm.getUserName();
  String issueType = reportForm.getIssueType();
  String accountId = reportForm.getAccountId();
  String dateBegin = reportForm.getDateBegin();
  String dateEnd = reportForm.getDateEnd();
  String dateBy = reportForm.getDateBy();
  String Disp_Rejected = "";
  String Disp_Processing = "";
  String Disp_Delivered = "";
  String Disp_Closed = "";
  String Disp_Duplation = "";
  boolean rejectFlg = false;
  boolean processFlg = false;
  boolean deliveredFlg = false;
  boolean closedFlg = false;
  boolean duplationFlg = false;
  if (reportForm.getRejected() != null) {
    rejectFlg = true;
    Disp_Rejected = "1";
  }
  if (reportForm.getProcessing() != null) {
    processFlg = true;
    Disp_Processing = "1";
  }
  if (reportForm.getDelivered() != null) {
    deliveredFlg = true;
    Disp_Delivered = "1";
  }
  if (reportForm.getClosed() != null) {
    closedFlg = true;
    Disp_Closed = "1";
  }
  if (reportForm.getDuplation() != null) {
    duplationFlg = true;
    Disp_Duplation = "1";
  }
  System.out.println(dateBegin);
  String strPath = "";
  String strReport = "IssueReport.rpt";
  //��ѯ��SQL���?
  //
  String sql = "select to_char( it.PR_NO ) as PR_NO ";
  String sumSql = ",( to_char ( 0 ";
  if (rejectFlg) {
    sql = sql + " , to_char(sum( it.REJECT )) as REJECT";
    sumSql = sumSql + " + sum( it.REJECT ) ";
  }
  else {
    sql = sql + ", '0' as REJECT ";
  }
  if (processFlg) {
    sql = sql + " , to_char( sum( it.PROCESSING ) ) as PROCESSING";
    sumSql = sumSql + " +  sum( it.PROCESSING )  ";
  }
  else {
    sql = sql + ", '0' as PROCESSING ";
  }
  if (deliveredFlg) {
    sql = sql + " , to_char( sum( it.DELIVERD ) )as DELIVERD";
    sumSql = sumSql + " + sum( it.DELIVERD )";
  }
  else {
    sql = sql + ", '0' as DELIVERD ";
  }
  if (closedFlg) {
    sql = sql + " , to_char( sum( it.CLOSED ) ) as CLOSED";
    sumSql = sumSql + " + sum( it.CLOSED ) ";
  }
  else {
    sql = sql + ", '0' as CLOSED ";
  }
  if (duplationFlg) {
    sql = sql + " , to_char( sum( it.DUPLATION ) ) as DUPLATION";
    sumSql = sumSql + " + sum( it.DUPLATION ) ";
  }
  else {
    sql = sql + ", '0' as DUPLATION ";
  }

  sumSql =  sumSql + ") )";

  sql = sql + sumSql + " as SUM from ISSUE_REPORTSTATUS_SUM it where it.ACNT_ID = " + accountId + " and it.LOGINNAME = '" + userName + "' group by it.LOGINNAME , it.ACNT_ID , it.PR_NO";
  // String sql = "select PR_NO, REJECT, PROCESSING, DELIVERD, CLOSED, DUPLATION, SUM from ISSUE_REPORTSTATUS_SUM it where it.ACNT_ID = "+accountId;
  System.out.println("ISSUE Report SQL:" + sql);
  HBComAccess dbAccess = new HBComAccess();
  javax.sql.RowSet rs = dbAccess.executeQuery(sql);
  rs.beforeFirst();
  ReportAppSession ra = new ReportAppSession();
  ra.createService("com.crystaldecisions.sdk.occa.report.application.ReportClientDocument");
  ra.setReportAppServer("127.0.0.1");
  ra.initialize();
  ReportClientDocument clientDoc = new ReportClientDocument();
  clientDoc.setReportAppServer(ra.getReportAppServer());
  clientDoc.open(strReport, OpenReportOptions._openAsReadOnly);
  DatabaseController dbCtrl = clientDoc.getDatabaseController();
  dbCtrl.setDataSource(rs, clientDoc.getDatabase().getTables().getTable(0).getName(), "command");
  rs.close();
  HashMap params = new HashMap();
  params.put("account", account);
  params.put("dateBegin", dateBegin);
  params.put("dateEnd", dateEnd);
  params.put("dateBy", dateBy);
  params.put("issueType",issueType);
  params.put("Disp_Processing", Disp_Processing);
  params.put("Disp_Rejected", Disp_Rejected);
  params.put("Disp_Delivered", Disp_Delivered);
  params.put("Disp_Closed", Disp_Closed);
  params.put("Disp_Duplation", Disp_Duplation);
  setParameter(clientDoc, params);
  CrystalReportViewer viewer = new CrystalReportViewer();
  viewer.setName("Crystal_Report_Viewer");
  viewer.setOwnForm(true);
  viewer.setOwnPage(true);
  viewer.setDisplayGroupTree(false);
  viewer.setHasLogo(false);
  viewer.setHasRefreshButton(true);
  viewer.setPrintMode(CrPrintMode.PDF);
  String queryString = request.getQueryString();
  if (queryString == null)
    viewer.setURI(request.getRequestURI());
  else
    viewer.setURI(request.getRequestURI() + "?" + queryString);
  viewer.setReportSource(clientDoc.getReportSource());
  viewer.processHttpRequest(request, response, getServletConfig().getServletContext(), out);
  viewer.dispose();
  int iWidth = viewer.getWidth();
  int iHeight = viewer.getHeight();
  double iMultiple = viewer.getZoomFactor() / 100.00;
%>
<%!
  public void setParameter(ReportClientDocument clientDoc, HashMap hmPar) {
    //HashMap hmPar = (HashMap) session.getAttribute("hmPar");
    try {
      Iterator it = hmPar.keySet().iterator();
      while (it.hasNext()) {
        String strKey = (String) it.next();
        Object oVal = hmPar.get(strKey);
        //The following section of code sets the current value of each parameter.  This allows you to bypass the parameter prompting screen
        clientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", strKey, oVal);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
%>
<script language="javascript" type="text/javascript">
try{
    parent.changeSize(<%=iWidth%>,<%=iHeight%>,<%=iMultiple%>);
}catch(e){
}
</script>
