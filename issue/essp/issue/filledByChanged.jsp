<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" "/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<script language="javascript">
function updateFilledByInfo() {
    try {
        var currentForm;
        var formName;
        if(parent.issueForm) {
            currentForm=parent.issueForm;
            formName="document.issueForm";
        } else {
            formName="document.issueUpdate";
            currentForm=parent.issueUpdate;
        }
        currentForm.phone.value="<bean:write name="webVo" property="phone"/>";
        currentForm.fax.value="<bean:write name="webVo" property="fax"/>";
        currentForm.email.value="<bean:write name="webVo" property="email"/>";
    } catch(e) {

    }
}
</script>
</head>
<body onload="updateFilledByInfo()">
</body>
</html>
