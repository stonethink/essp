<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate Multi Users"/>
        <tiles:put name="jspName" value="TOP LEFT"/>
</tiles:insert>
</head>
<body leftmargin="0" topmargin="0">
<script language="JavaScript">
function checkAllTd() {
  var isChecked = window.parent.parent.alloc_tool_result.chSelectall.checked;
  window.parent.parent.checkAllTds(!isChecked);
  window.parent.parent.alloc_tool_result.chSelectall.checked = !isChecked;
}
</script>
<table width="415" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td bgcolor="#999999"><table width="100%" border="0" cellspacing="1" cellpadding="0">
        <tr>
          <td width="21" bgcolor="#FFFFFF" class="oracelltext"><div align="center"></div></td>
          <td width="36" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext" style="cursor:hand" onclick="checkAllTd()">
<div align="center"><bean:message bundle="projectpre" key="projectCode.check" /></div></td>
          <td width="110" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
<div align="center"><bean:message bundle="application" key="UserAssignRoles.UserList.loginId" /></div></td>
          <td width="222" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
<div align="center"><bean:message bundle="application" key="UserAssignRoles.UserList.loginName" /></div></td>
          <td width="18" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
<div align="center"></div></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
