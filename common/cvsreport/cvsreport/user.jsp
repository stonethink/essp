<%@ page import="com.essp.cvsreport.*"%>
<%@ page import="com.essp.cvsreport.jsp.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%
	//ReportDate reportDate = (ReportDate)session.getAttribute("reportDate");
	ReportDate reportDate = ReportDate.getInstance();
  	String userName = (String)request.getParameter("userName");
    UserJsp jsp = new UserJsp(request, response, out, userName); 

%>

<html>

<head> 

<title>╦ейЖ</title>
<link rel="stylesheet" type="text/css" href="../stylesheets/antmanual.css">
</head>

<body>
<p>
<INPUT TYPE="button" VALUE="╨Смк" onClick="history.go(-1)">
<INPUT TYPE="button" VALUE="г╟╫Ь" onClick="history.go(1)">
</p>

<p>
<%jsp.outputGeneral();%>
</p>
<table border="1" cellpadding="5" cellspacing="0">
  	
  	<%
  	jsp.outputTableHeader();
  	jsp.outputTableTr();
  	jsp.outputTableTotal();
  	%>

</table>


</body>
</html>
