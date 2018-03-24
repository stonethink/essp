<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<logic:present parameter="labors">
  <bean:parameter id="labors" name="labors" />
</logic:present>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate Multi Users"/>
        <tiles:put name="jspName" value="Result"/>
</tiles:insert>
</head>

<body leftmargin="0" topmargin="0">
<form name="frm_result_view">
  <input type="hidden" name="type">
  <table border="0" cellpadding="0" cellspacing="0">

  <tr>
    <td width="90" height="10">
        <iframe name="frm11" src="top_left.jsp" width="460" height="100%" frameborder="0" hspace="0" scrolling="no"></iframe></td>
    <td width="10" height="10" bgcolor="#999999">
	 <table width="16" height="100%" border="0" cellpadding="1" cellspacing="1">
        <tr>
          <td background="../photo/alloc_tool/alloc_tool_back3.jpg" height="18"></td>
        </tr>
      </table> </td>
  </tr>
  <tr>
      <td width="457" valign="top" bgcolor="#eeeeee" id="tdbotleft">
       <logic:present parameter="labors">
         <iframe name="frm21" src="bot_left.jsp?labors=<bean:write name="labors"/>" width="415" height="100%" frameborder="0" hspace="0" scrolling="yes"></iframe></td>
       </logic:present>
       <logic:notPresent parameter="labors">
         <iframe name="frm21" src="bot_left.jsp" width="415" height="100%" frameborder="0" hspace="0" scrolling="yes"></iframe></td>
       </logic:notPresent>
  </tr>
  <tr>
    <td valign="top" background="../photo/alloc_tool/alloc_tool_back3.jpg" height="15"></td>
  </tr>
</table>
</form>
</body>
</html>
