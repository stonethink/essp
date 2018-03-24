<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
        <tiles:put name="title" value=" Issue Add Success" />
        <tiles:put name="jspName" value="" />
</tiles:insert>
</head>
<body bgcolor="#ffffff">
<script language="javascript">
try {
    if(opener.issueForm) {
        opener.issueForm.functionType.value="SEARCH";
        opener.issueForm.pageNo.value="1";
        opener.issueForm.pageAction.value="";
        opener.issueForm.addonRid.value="<bean:write name="rid"/>";
        opener.issueForm.submit();
    }
} catch(e) {
    alertError(e);
}
window.close();
</script>
</body>
</html>
