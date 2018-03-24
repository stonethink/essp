<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<HTML>
<HEAD>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="System Error"/>
  <tiles:put name="jspName" value="error"/>
</tiles:insert>
<style type="text/css">
.tableTitle {
	background-color: White;
	color: #336699;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 24px;
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
      <td class="tableTitle">Function Error</td>
    </tr>
  </tbody>
</table>
<table class="OraTable" summary="">
  <!-- Header -->
  <thead>
    <tr>
      <th class="columnTitle" width="18%">Error Code</th>
      <th class="columnTitle" width="*">Error Message</th>
    </tr>
  </thead>
  <tbody>
    <logic:present name="exception" scope="session">
    <tr id="workplan_tr" class="oracletdone">
      <td id="Account" class="cellText" width="18%"><bean:write name="exception" property="errorCode"/></td>
      <td id="Activity" class="cellText" width="*"><bean:write name="exception" property="errorMsg"/></td>
    </tr>
    </logic:present>
    <tr>
      <td colspan="2" align="right"><INPUT type="button" value="back" onclick="history.back();" class="button"></td>
    </tr>
  </tbody>
</table>
</BODY>
</HTML>
