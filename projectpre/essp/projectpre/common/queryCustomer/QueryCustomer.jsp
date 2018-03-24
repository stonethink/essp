<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
 <TITLE><bean:message bundle="projectpre" key="customer.CustomerInfoQuery.cardTitle.SearchClient"/></TITLE>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title"/>
        <tiles:put name="jspName" value=""/>
</tiles:insert>
</head>
<frameset rows="27,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="Head.jsp" noresize scrolling="no" name="search_tool_head">
    <frameset rows="130,270" cols="*" frameborder="no" border="0" framespacing="0" name="content">
      <frame src="Search.jsp" noresize scrolling="no">
      <frame src="Result.jsp" noresize scrolling="no" name="Search_tool_result">
    </frameset>
</frameset>
<noframes><body>

</body></noframes>
</html>