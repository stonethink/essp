<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
        <tiles:put name="title" value=" " />
        <tiles:put name="jspName" value="" />
</tiles:insert>
</head>
<body bgcolor="#ffffff">
    <script language="javaScript">
    var dateObj=null;
    dateStr=jsFormatDateYYYYMMDDSys
    if(parent.issueUpdate) {
        dateObj=parent.issueUpdate.dueDate;
    } else {
        dateObj=parent.issueForm.dueDate;
    }
    dateObj.value="<bean:write name="webVo" property="dueDate"/>";
    dateObj.value=jsDoFormatSys(dateObj);
    </script>

</body>
</html>
