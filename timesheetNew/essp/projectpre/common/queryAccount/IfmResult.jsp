<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Query Account Iframe Result"/>
        <tiles:put name="jspName" value="Result"/>
</tiles:insert>
</head>

<body leftmargin="0" topmargin="0">
<form name="frm_result_view">
  <input type="hidden" name="type">
  <table border="0" cellpadding="0" cellspacing="0" height="100%">

  <tr>
    <td width="90" height="10">
        <iframe name="frm11" src="ListHead.jsp" width="460" height="100%" frameborder="0" hspace="0" scrolling="no"></iframe></td>
    <td width="10" height="10" bgcolor="#999999">
	 <table width="16" height="100%" border="0" cellpadding="1" cellspacing="1">
        <tr>
          <td background="../photo/search_tool/search_tool_back3.jpg" height="18"></td>
        </tr>
      </table> </td>
  </tr>
  <tr>
      <td width="457" valign="top" bgcolor="#eeeeee" id="tdbotleft">
      <iframe name="frm21" src="ResultList.jsp" width="413" height="100%" frameborder="0" hspace="0" scrolling="yes"></iframe></td>
  </tr>
  <tr>
    <td valign="top" background="../photo/search_tool/search_tool_back3.jpg" height="15"></td>
  </tr>
</table>
</form>
</body>
</html>
