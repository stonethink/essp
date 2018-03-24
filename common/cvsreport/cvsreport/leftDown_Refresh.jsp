<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%@ page import="com.essp.cvsreport.*"%>
<%@ page import="java.util.*"%>
<%
	//ReportDate reportDate = (ReportDate)session.getAttribute("reportDate");
	ReportDate reportDate = ReportDate.getInstance();
    String refreshFlag = (String)request.getParameter("refreshFlag");
    if( "1".equals(refreshFlag) ){
    	reportDate.fetchDate();
    }
%>

<html>
<head>
<meta http-equiv="Content-Language" content="en-us">
<title>Essp cvs report</title>

<link rel="stylesheet" type="text/css" href="stylesheets/antmanual.css">
</head>
 
<script type="text/javascript" language="JavaScript">
	function refresh(){
		 refreshForm.submit();	
	}

</script>

<body><%
out.print(reportDate.getFetchStr());
%>
<br>
	&nbsp;&nbsp;
	<form action="leftDown_Refresh.jsp" name="refreshForm">
		<input type=hidden name="refreshFlag" value="1"/>
		<input type=button value="重新生成report" onclick="refresh();"/>
	</form>
	<br>



</body>

</html>
