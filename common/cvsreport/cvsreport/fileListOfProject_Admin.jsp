<%@ page import="com.essp.cvsreport.*"%>
<%@ page import="com.essp.cvsreport.jsp.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%
	//ReportDate reportDate = (ReportDate)session.getAttribute("reportDate");
	ReportDate reportDate = ReportDate.getInstance();
  	String projectName = (String)request.getParameter("projectName");
  	String action = (String)request.getParameter("action");
    FileListOfProject_AdminJsp  jsp = new FileListOfProject_AdminJsp(request, response, out, action);
%>

<html>

<head> 

<title>essp cvs report</title>
<link rel="stylesheet" type="text/css" href="../stylesheets/antmanual.css">
</head>

<body>
<p>

 <a name="top">
<INPUT TYPE="button" VALUE="后退" onClick="history.go(-1)">
<INPUT TYPE="button" VALUE="前进" onClick="history.go(1)">
<%
if( projectName != null ){
	out.print("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"fileListOfProjectUser_Admin.jsp?projectName=" +projectName+ "&action=" +action+ "\">分user查看</a>");
}
%>
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

