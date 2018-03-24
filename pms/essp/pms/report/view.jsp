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
server.essp.pms.report.form.AfWeeklyReport"
%>
<%
  AfWeeklyReport form = (AfWeeklyReport)session.getAttribute("ReportForm");
  //��ѯ����
  String reportDate = form.getReportDate();
  String reportBegin = form.getReportBegin();
  String reportEnd = form.getReportEnd();
  String acntRid = form.getAcntRid();
  String reportedBy = form.getReportedBy();
  String acntId = form.getAcntId();
  String acntName = form.getAcntName();
  //ָ�������ļ�
  String strPath = "";
  String strReport = "PMSWeeklyReport.rpt";

  //��ѯ��SQL���
  String sql = "select t.TYPE, " +
      "       t.ACNT_RID, " +
      "       t.ACTIVITY_CODE, " +
      "       t.WP_CODE, " +
      "       t.NAME, " +
      "       t.WORKER, " +
      "       to_char(t.PLANNED_START,'yyyy/MM/dd') as PLANNED_START, " +
      "       to_char(t.PLANNED_FINISH,'yyyy/MM/dd') as PLANNED_FINISH, " +
      "       t.planned_work_time, " +
      "       to_char(t.ACTUAL_START,'yyyy/MM/dd') as ACTUAL_START, " +
      "       to_char(t.ACTUAL_FINISH,'yyyy/MM/dd') as ACTUAL_FINISH, " +
      //"       t.ACTUAL_START, " +
      //"       t.ACTUAL_FINISH, " +
      "       t.ACTUAL_WORK_TIME, " +
      "       t.COMPLETE_RATE, " +
      "       t.STATUS, " +
      "       t.REMARK, " +
      "       t.IS_START, " +
      "       t.IS_FINISH, " +
      "       t.ACTIVITY_RID, " +
      "       t.ACTIVITY_PLANNED_START, " +
      "       t.ACTIVITY_ACTUAL_START  " +
      "from project_weekly_report t " +
      "where t.ACNT_RID='" + acntRid + "' " +
      "  and (PLANNED_START is not null and PLANNED_FINISH is not null) " +
      "  and (not( " +
      "          (PLANNED_FINISH < to_date('" + reportBegin + "','yyyy/MM/dd')) or  " +
      "          (PLANNED_START > to_date('" + reportEnd + "','yyyy/MM/dd'))  " +
      "          ) " +
      "      or not( " +
      "          (ACTUAL_FINISH < to_date('" + reportBegin + "','yyyy/MM/dd')) or  " +
      "          (ACTUAL_START > to_date('" + reportEnd + "','yyyy/MM/dd'))  " +
      "         )    " +
      "      )    " +
      "UNION  " +
      "select t.TYPE, " +
      "       t.ACNT_RID, " +
      "       t.ACTIVITY_CODE, " +
      "       t.WP_CODE, " +
      "       t.NAME, " +
      "       t.WORKER, " +
      "       to_char(t.PLANNED_START,'yyyy/MM/dd') as PLANNED_START, " +
      "       to_char(t.PLANNED_FINISH,'yyyy/MM/dd') as PLANNED_FINISH, " +
      //"       t.PLANNED_START, " +
      //"       t.PLANNED_FINISH, " +
      "       t.planned_work_time, " +
      "       to_char(t.ACTUAL_START,'yyyy/MM/dd') as ACTUAL_START, " +
      "       to_char(t.ACTUAL_FINISH,'yyyy/MM/dd') as ACTUAL_FINISH, " +
      //"       t.ACTUAL_START, " +
      //"       t.ACTUAL_FINISH, " +
      "       t.ACTUAL_WORK_TIME, " +
      "       t.COMPLETE_RATE, " +
      "       t.STATUS, " +
      "       t.REMARK, " +
      "       t.IS_START, " +
      "       t.IS_FINISH, " +
      "       t.ACTIVITY_RID, " +
      "       t.ACTIVITY_PLANNED_START, " +
      "       t.ACTIVITY_ACTUAL_START   " +
      "from project_weekly_report t  " +
      "where t.ACNT_RID = '" + acntRid + "'  " +
      "  and t.TYPE='A' " +
      "  and t.ACTIVITY_RID in (select ACTIVITY_RID  " +
      "                         from project_weekly_report  " +
      "                         where ACNT_RID = '" + acntRid + "'  " +
      "                           and TYPE='P' " +
      "                           and (PLANNED_START is not null and PLANNED_FINISH is not null) " +
      "                           and (not( " +
      "                                   (PLANNED_FINISH < to_date('" + reportBegin + "','yyyy/MM/dd')) or  " +
      "                                   (PLANNED_START > to_date('" + reportEnd + "','yyyy/MM/dd'))  " +
      "                                   )   " +
      "                                or not( " +
      "                                   (ACTUAL_FINISH < to_date('" + reportBegin + "','yyyy/MM/dd')) or  " +
      "                                   (ACTUAL_START > to_date('" + reportEnd + "','yyyy/MM/dd'))  " +
      "                                   )  " +
      "                                )  " +
      "                         )         " +
      "order by ACTIVITY_PLANNED_START,ACTIVITY_ACTUAL_START,ACTIVITY_RID,TYPE,PLANNED_START,PLANNED_FINISH";
  System.out.println("Project Weekly Report SQL:" + sql);

  HBComAccess dbAccess = new HBComAccess();
  javax.sql.RowSet rs = dbAccess.executeQuery(sql);
  //�����ѯ�������Activity��WP״̬
  while (rs.next()) {
    String type = rs.getString("TYPE");
    String status = rs.getString("STATUS");
    String plannedFinish = rs.getString("PLANNED_FINISH");
    String actualStart = rs.getString("ACTUAL_START");
    if ("P".equals(type)) {
        if ("Assigned".equalsIgnoreCase(status) || "Rework".equalsIgnoreCase(status)) {
            if (plannedFinish != null && reportDate.compareTo(plannedFinish) > 0) {
                status = "TD(" + status + ")";
            }
            else if (actualStart != null && reportDate.compareTo(actualStart) >= 0) {
                status = "TW(" + status + ")";
            }
            else {
                status = "NP(" + status + ")";
            }
        }
        else {
            status = "TF" + "(" + status + ")";
        }
    }
    else {
        String isStart = rs.getString("IS_START");
        String isFinish = rs.getString("IS_FINISH");
        if ("1".equals(isFinish)) {
            status = "TF(Finish)";
        }
        else {
            String subStatus = "1".equals(isStart) ? "Processing" : "NotStart";
            if (plannedFinish != null && reportDate.compareTo(plannedFinish) > 0) {
                status = "TD(" + subStatus + ")";
            }
            else if (actualStart != null && reportDate.compareTo(actualStart) >= 0) {
                status = "TW(" + subStatus + ")";
            }
            else {
                status = "NP(" + subStatus + ")";
            }
        }
    }
    rs.updateString("STATUS", status);
  }
  rs.beforeFirst();

  ReportAppSession ra = new ReportAppSession();
  ra.createService("com.crystaldecisions.sdk.occa.report.application.ReportClientDocument");
  ra.setReportAppServer("127.0.0.1");
  ra.initialize();

  ReportClientDocument clientDoc = new ReportClientDocument();
  clientDoc.setReportAppServer(ra.getReportAppServer() );
  clientDoc.open( strReport, OpenReportOptions._openAsReadOnly);

  DatabaseController dbCtrl = clientDoc.getDatabaseController();
  dbCtrl.setDataSource(rs,clientDoc.getDatabase().getTables().getTable(0).getName(),"command");
  rs.close();

  //���ݸ�����
  HashMap params = new HashMap();
  params.put("acntId",acntId);
  params.put("acntName",acntName);
  params.put("reportedBy",reportedBy);
  params.put("reportDate",reportDate);
  params.put("reportBegin",reportBegin);
  params.put("reportEnd",reportEnd);
  setParameter(clientDoc,params);

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
  double iMultiple = viewer.getZoomFactor()/100.00;
%>

<%!
public void setParameter (ReportClientDocument clientDoc,HashMap hmPar) {
    //HashMap hmPar = (HashMap) session.getAttribute("hmPar");
    try{
      Iterator it = hmPar.keySet().iterator();
      while(it.hasNext()){
        String strKey = (String)it.next();
        Object oVal = hmPar.get(strKey);

        //The following section of code sets the current value of each parameter.  This allows you to bypass the parameter prompting screen
        clientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", strKey, oVal);
      }
    }catch(Exception e){
      e.printStackTrace();
    }
}
%>
<script language="javascript">
try{
    parent.changeSize(<%=iWidth%>,<%=iHeight%>,<%=iMultiple%>);
}catch(e){
}
</script>
