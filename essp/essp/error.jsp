<%@page contentType="text/html; charset=UTF-8"%>

<%@include file="/inc/pagedef.jsp"%>

<HTML>
<HEAD>
<!-- 下面用Tiles实现的Page的Title不能实现国际化，所以在此处理 -->
<title><bean:message key="error.paga.title" bundle="application"/></title>

<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Error"/>
  <tiles:put name="jspName" value="error"/>
</tiles:insert>
<style type="text/css">
.tableTitle {
	background-color: White;
	color: #336699;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 20px;
	font-style: normal;
	font-weight: bold;
	border-bottom-color: #336699;
	border-bottom-style: inset;
	border-bottom-width: 3px;
	border-collapse: separate;
}

.columnTitle {
	BACKGROUND-COLOR: #94aad6;
	color: #FFFFFF;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	font-style: normal;
	font-weight: bold;
	text-align: left;
}

.cellText {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 13px;
}
</style>

</HEAD>
<BODY>
<table border="0" cellpadding="0" cellspacing="0" width="100%" id="table1">
  <tbody>
    <tr>
      <td  height="24" style="fone-size:20px;font-weight=bold;"><bean:message key="error.title.label" bundle="application"/><font color="#336699"></font></td>
    </tr>
    <tr>
      <td  height="3" bgcolor="#336699" ></td>
    </tr>
  </tbody>
</table>
<table class="OraTable" summary="">
  <!-- Header -->
  <thead>
    <tr>
      <th class="columnTitle" width="30%"><bean:message key="error.code.label" bundle="application"/></th>
      <th class="columnTitle" width="70"><bean:message key="error.message.label" bundle="application"/></th>
    </tr>
  </thead>
  <tbody>
    <logic:present name="errorCode" scope="request">
    <tr id="workplan_tr" class="oracletdone">
      <td id="Account" class="cellText" width="30%"><bean:write name="errorCode" /></td>
      <td id="Activity" class="cellText" width="70%">
      <%try{%>
      	<bean:message name="errorCode" bundle="error"/>
      <%}catch(Exception ex){%>
      	<bean:write name="errorMsg" />
      <%}%>
      </td>
    </tr>
    
    </logic:present>
    <tr>
      <td colspan="2" align="right"><INPUT type="button" id="return" value="<bean:message key="global.button.back" bundle="application"/>" onclick="history.back();"></td>
    </tr>
  </tbody>
</table>
</BODY>
</HTML>
<script>
	try{
		var btn = document.getElementById("return");
		btn.focus();
	}catch(e){};
</script>
