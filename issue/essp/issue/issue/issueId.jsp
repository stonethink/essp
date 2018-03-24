<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" Issue Update"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
</head>
<body bgcolor="#ffffff">

 <script language="javaScript">
 window.returnValue="<bean:write name="webVo" property="checkIssueId"/>";
 window.close();
 </script>

</body>
</html>
