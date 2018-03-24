<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:parameter id="acntId" name="acntId"/>
<bean:parameter id="requestStr" name="requestStr"/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate User Head"/>
        <tiles:put name="jspName" value="Head"/>
</tiles:insert>
</head>
<body bgcolor="#7587AD" leftmargin="0" topmargin="0">
<form name="alloc_tool_head" method="post">
<table width="100%" height="27" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="64" height="27"><img src="../photo/alloc_tool/alloc_tool_logo1.jpg" width="64" height="27"></td>
    <td width="20" background="../photo/alloc_tool/alloc_tool_back1.jpg" class="allochead">&nbsp;</td>
    <td width="378" background="../photo/alloc_tool/alloc_tool_back1.jpg" class="allochead"></td>
    <td width="346" background="../photo/alloc_tool/alloc_tool_back1.jpg" class="allochead">
	  </td>
  </tr>
</table>
</form>
</body>
</html>
