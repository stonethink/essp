<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Query Account Head"/>
        <tiles:put name="jspName" value="Head"/>
</tiles:insert>
<STYLE>
td {
	font-family: "Verdana", "Arial", "Helvetica", "sans-serif";
	font-size: 12px;
	font-weight: bold;
	color: #FFFFFF;
	text-overflow:ellipsis;
	overflow:hidden;
	white-space: nowrap;
}

table {
	table-layout:fixed;
	word-wrap:break-word;
	word-break:break-all;
}

</STYLE>

</head>
<body bgcolor="#7587AD" leftmargin="0" topmargin="0">
<form name="search_tool_head" method="post">
<table width="100%" height="27" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td width="3%" background="../photo/search_tool/search_tool_back1.jpg" >&nbsp;</td>
    <td id="showParam" width="94%" background="../photo/search_tool/search_tool_back1.jpg">
     </td>
    <td width="3%" background="../photo/search_tool/search_tool_back1.jpg" >&nbsp;</td>  
  </tr>
</table>
</form>
</body>
</html>
