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

  <frameset rows="8%,*,42%"  frameborder="no" border="0" framespacing="0">
        <frame src="ResPersonnelHead.jsp" noresize  scrolling="no" name="personnel_head">
        <frame src="ResPersonnelView.jsp?accountId=<bean:write name="accountId" />" noresize  name="personnel_view" scrolling="auto">
        <frame src="ResPersonnelFoot.jsp?accountId=<bean:write name="accountId" />" noresize scrolling="no" name="personnel_foot">
  </frameset>

<noframes><body>

</body>

</noframes>
</html>
