<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>

<bean:define id="typeName" name="typeName"/>
<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />

<html>
<head>
<title>
sucInfo
</title>
</head>
<body bgcolor="#ffffff">
<script type="text/javascript">
  self.location="<%=contextPath%>/issue/typedefine/priority/issuePriorityList.do?typeName=<%=typeName%>";
</script>
</body>
</html>
