<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>


<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<html>
<head>
<title>
issueIdCreate
</title>
</head>
<body bgcolor="#ffffff">
<script language="javascript">
parent.document.issueForm.issueId.value="<bean:write name="issueId"/>";
</script>
</body>
</html>
