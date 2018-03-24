<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Resource Plan"/>
        <tiles:put name="jspName" value=""/>
</tiles:insert>
</head>

<frameset rows="27,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src='ResHead.jsp?accountCode=<bean:write name="webVo" property="accountCode"/>' noresize scrolling="no" name="alloc_tool_head">
  <frame src="ResPersonnelMain.jsp?accountId=<bean:write name="webVo" property="accountId"/>" noresize  scrolling="no" name="personnel_main">
</frameset>
<noframes><body>

</body></noframes>
</html>
