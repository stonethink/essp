<%@ page import="com.essp.cvsreport.*"%>
<%@ page import="com.essp.cvsreport.jsp.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%
	
  	String fileFullName = (String)request.getParameter("fileFullName");
  	String leftRevision = (String)request.getParameter("leftRevision");
  	String rightRevision = (String)request.getParameter("rightRevision");
	DiffParser downloader = new DiffParser();
	String content = downloader.parser(request, response, out);
%>

<html>

<head><title>different content</title>
</head>

<script type="text/javascript" language="JavaScript">

</script>

<body id=body SCROLL="auto" NOWRAP='true'>
<table>
	<tr>
	<td width="1500" noWrap="true"><textarea id="fileContent" type="text" id="Textobj"  WRAP="off"
											 style="border:1px solid #000000;padding:13px 10px 10px 10px;overflow-x:visible;overflow-y:visible;text-overflow:ellipsis"
									><%=content%></textarea>
	</td>
	</tr>
</body>
</html>
