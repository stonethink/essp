<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%@ page import="com.essp.cvsreport.*"%>
<%@ page import="java.util.*"%>
<%
	//ReportDate reportDate = (ReportDate)session.getAttribute("reportDate");
	ReportDate reportDate = ReportDate.getInstance();
  	
    
%>

<html>
<head>
<meta http-equiv="Content-Language" content="en-us">
<title>Essp cvs report</title>
<base target="mainFrame">
<link rel="stylesheet" type="text/css" href="stylesheets/antmanual.css">
</head>
<%
    for (Iterator iter = reportDate.getRoot().projects.entrySet().iterator(); iter.hasNext(); ) {
      Map.Entry item = (Map.Entry)iter.next();
      String projectName = (String)item.getKey();
%>

	&nbsp;&nbsp;<a href="project.jsp?projectName=<%=projectName%>"><%=projectName%></a>
	<br>

<%}%>

</html>
