<%@ page import="com.essp.cvsreport.*"%>
<%@ page import="com.essp.cvsreport.jsp.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%
	//ReportDate reportDate = (ReportDate)session.getAttribute("reportDate");
	ReportDate reportDate = ReportDate.getInstance();
  	String projectName = (String)request.getParameter("projectName");
  	String fileType = (String)request.getParameter("fileType");
    SpecialFileListOfProject jsp = new SpecialFileListOfProject(request, response, out, fileType); 
%>

<html>

<head> 

<title>essp cvs report</title>
<link rel="stylesheet" type="text/css" href="../stylesheets/antmanual.css">
</head>

<body>
<p>

<a name="top">
<INPUT TYPE="button" VALUE="ºóÍË" onClick="history.go(-1)">
<INPUT TYPE="button" VALUE="Ç°½ø" onClick="history.go(1)">
</p>


<%if( projectName != null ){
	jsp.outputTheProject(projectName);
		 
}else{
	jsp.outputAllProject();
}
%>	

</body>
</html>

