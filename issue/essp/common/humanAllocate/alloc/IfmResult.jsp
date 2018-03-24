<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<logic:present parameter="controlName">
  <bean:parameter id="controlName" name="controlName" />
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
        <iframe name="frm11" src="top_left.jsp" width="149" height="100%" frameborder="0" hspace="0" scrolling="no"></iframe></td>
    <td width="251" height="10">
      <iframe name="frm12" src="top_right.jsp" width="297" height="100%" frameborder="0" hspace="0" scrolling="no"></iframe></td>
    <td width="10" height="10" bgcolor="#999999">
	 <table width="16" height="100%" border="0" cellpadding="1" cellspacing="1">
        <tr>
          <td background="../photo/alloc_tool/alloc_tool_back3.jpg" height="18"></td>
        </tr>
      </table> </td>
  </tr>
  <tr>
      <td width="90" valign="top" bgcolor="#eeeeee" id="tdbotleft">
       <logic:present parameter="controlName">
         <iframe name="frm21" src="bot_left.jsp?controlName=<bean:write name="controlName"/>" width="149" height="100%" frameborder="0" hspace="0" scrolling="no"></iframe></td>
       </logic:present>
       <logic:notPresent parameter="controlName">
         <iframe name="frm21" src="bot_left.jsp" width="149" height="100%" frameborder="0" hspace="0" scrolling="no"></iframe></td>
       </logic:notPresent>
      <td width="160" height="20" colspan="2" rowspan="2" valign="top" bgcolor="#CCCCCC" id="tdbotright">
        <iframe name="frm22" src="bot_right.jsp" width="313" height="88%" frameborder="0" hspace="0" scrolling="yes"></iframe></td>
  </tr>
  <tr>
    <td valign="top" background="../photo/alloc_tool/alloc_tool_back3.jpg" height="15"></td>
  </tr>
</table>
</form>
</body>
</html>
