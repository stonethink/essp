<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="oldValue" name="oldValue" />
<bean:define id="acntId" name="acntId" />
<bean:define id="requestStr" name="requestStr"/>
<bean:define id="labors" name="labors"/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate Multi Users"/>
        <tiles:put name="jspName" value=""/>
</tiles:insert>
</head>

<frameset rows="27,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="Head.jsp?acntId=<%=acntId%>&requestStr=<%=requestStr%>" noresize scrolling="no" name="alloc_tool_head">
  <frameset rows="*" cols="348,649" frameborder="no" border="0" framespacing="0">
    <frame src="Guide.jsp?oldValue=<%=oldValue%>&&labors=<%=labors%>" noresize scrolling="no" name="alloc_tool_guide">
    <frameset rows="185,215" cols="*" frameborder="no" border="0" framespacing="0" name="content">
      <frame src="Function.jsp" noresize scrolling="no">
      <frame src="Result.jsp" noresize scrolling="no" name="alloc_tool_result">
    </frameset>
  </frameset>
</frameset>
<noframes><body>

</body></noframes>
</html>
