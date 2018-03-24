<%@ page import="com.essp.cvsreport.*"%>
<%@ page import="com.essp.cvsreport.jsp.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%
	//ReportDate reportDate = (ReportDate)session.getAttribute("reportDate");
	ReportDate reportDate = ReportDate.getInstance();
    PeriodJsp jsp = new PeriodJsp(request, response, out); 
    String queryString = request.getQueryString();
    int i = queryString.indexOf("last=true");
    int j = queryString.indexOf("next=true");
    j = j > i ?j : i;
    if( j >= 0 ){
	    queryString = queryString.substring(j+ "last=true".length());
	    if( queryString.startsWith("&") ){
	    	queryString = queryString.substring(1);
	    }
    }
%>

<html>

<head> 

<title>����</title>
<link rel="stylesheet" type="text/css" href="../stylesheets/antmanual.css">
</head>

<body>
<p>
<INPUT TYPE="button" VALUE="����" onClick="history.go(-1)">
<INPUT TYPE="button" VALUE="ǰ��" onClick="history.go(1)">
<a href="period.jsp?last=true&<%=queryString%>">��һ��</a>&nbsp;&nbsp;
<a href="period.jsp?next=true&<%=queryString%>">��һ��</a>
</p>

<p>
<%

jsp.outputCondition();
jsp.outputPeriod();
jsp.outputProjectTable();
jsp.outputUserTable();

%>
</p>



</body>
</html>
