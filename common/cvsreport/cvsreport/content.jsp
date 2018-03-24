<%@ page import="com.essp.cvsreport.*"%>
<%@ page import="com.essp.cvsreport.jsp.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%

  	ContentJsp jsp = new ContentJsp(request, response, out);

%>

<html>

<head> 

<title>╦ейЖ</title>
<link rel="stylesheet" type="text/css" href="../stylesheets/antmanual.css">
</head>

<body>

<h2>╦ейЖ</h2>

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
