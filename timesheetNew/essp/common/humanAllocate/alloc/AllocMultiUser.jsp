<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="oldValue" name="oldValues" />
<bean:define id="acntId" name="acntId" />
<bean:define id="requestStr" name="requestStr"/>
<bean:define id="requestType" name="requestType"/>
<bean:define id="labors" name="labors"/>
<bean:define id="type" name="type"/>
<html>
<head>
 <TITLE><bean:message bundle="application" key="Human.card.AllocateUser"/></TITLE>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title"/>
        <tiles:put name="jspName" value=""/>
</tiles:insert>
</head>
<frameset rows="27,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="Head.jsp?acntId=<%=acntId%>&requestStr=<%=requestStr%>" noresize scrolling="no" name="alloc_tool_head">
  <frameset rows="*" cols="408,589" frameborder="no" border="0" framespacing="0">
    <frame src="Guide.jsp?oldValue=<%=oldValue%>&labors=<%=labors%>&type=<%=type%>" noresize scrolling="no" name="alloc_tool_guide">
    <frameset rows="185,215" cols="*" frameborder="no" border="0" framespacing="0" name="content">
      <frame src="Function.jsp?requestType=<%=requestType%>" noresize scrolling="no">
      <frame src="Result.jsp?labors=<%=labors%>" noresize scrolling="no" name="alloc_tool_result">
    </frameset>
  </frameset>
</frameset>
<noframes><body>

</body></noframes>
</html>
