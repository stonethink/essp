<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:parameter id="accountId" name="accountId"/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Resource Plan"/>
        <tiles:put name="jspName" value=""/>
</tiles:insert>
</head>

<body leftmargin="0" topmargin="0">
<form name="frm_result_view">
  <input type="hidden" name="type">
  <table border="0" cellpadding="0" cellspacing="0">

  <tr>
    <td width="120" height="60">
      <iframe name="frm11" src="ResTopLeft.jsp" width="120" height="100%" frameborder="0" hspace="0" scrolling="no"></iframe></td>
    <td width="480" height="60">
      <iframe name="frm12" src="PlanPeriodPart.do?viewbase=1&accountId=<%=accountId%>" width="590" height="100%" frameborder="0" hspace="0" scrolling="no"></iframe></td>
    <td width="10" height="60" bgcolor="#999999">
	 <table width="16" height="100%" border="0" cellpadding="1" cellspacing="1">
        <tr>
          <td background="../photo/alloc_tool/alloc_tool_back3.jpg" height="54"></td>
        </tr>
      </table> </td>
  </tr>
  <tr height="12">
      <td width="120" height="148" valign="top" bgcolor="#eeeeee" id="tdbotleft">
        <iframe name="frm21" src="ListAllocedResource.do?viewbase=1&accountId=<%=accountId%>" frameborder="0" hspace="0" width="120" height="100%" scrolling="no"></iframe></td>
      <td width="480" height="148" colspan="2" rowspan="2" valign="top" bgcolor="#CCCCCC" id="tdbotright">
        <iframe name="frm22" src="ListResourcePlan.do?viewbase=1&accountId=<%=accountId%>" width="606" height="100%" frameborder="0" hspace="0" scrolling="yes"></iframe></td>
  </tr>
</table>
</form>

</body>
</html>
