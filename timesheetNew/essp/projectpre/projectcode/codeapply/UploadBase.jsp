<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
<TITLE><bean:message bundle="projectpre" key="projectCode.upload.Title" /></TITLE>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value=""/>
        <tiles:put name="jspName" value=""/>
</tiles:insert>
</head>
<!-- frameset rows="1,*" cols="*" frameborder="no" border="0" framespacing="0" >
  <frame src="" noresize scrolling="no" name=""-->
    <frameset rows="70,*" cols="*" frameborder="no" border="0" framespacing="0" >
      <frame src="Blank.jsp" noresize scrolling="no" name="">
      <frame  src="UploadPage.jsp" noresize scrolling="no">
    </frameset>
<!-- /frameset-->
<noframes><body>

</body></noframes>
</html>