<%@ page import="com.essp.cvsreport.*"%>
<%@ page import="com.essp.cvsreport.jsp.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%
	//ReportDate reportDate = (ReportDate)session.getAttribute("reportDate");
	ReportDate reportDate = ReportDate.getInstance();
  	String projectName = (String)request.getParameter("projectName");
  	String action = (String)request.getParameter("action");
  	String userName = (String)request.getParameter("userName");
    FileListOfUserProject_AdminJsp  jsp = new FileListOfUserProject_AdminJsp(request, response, out, userName, action);
%>

<html>

<head> 

<title>essp cvs report</title>
<link rel="stylesheet" type="text/css" href="../stylesheets/antmanual.css">
</head>

<body>
<p>

 <a name="top">
<INPUT TYPE="button" VALUE="����" onClick="history.go(-1)">
<INPUT TYPE="button" VALUE="ǰ��" onClick="history.go(1)">
</p>


<%
if( projectName != null ){
	jsp.outputTheProject(projectName);

}else{
	jsp.outputAllProject();
}
%>

</body>
</html>

