<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<html>
<head>
<title>issuePrincipal</title>
</head>
<body bgcolor="#ffffff">
<script language="javaScript">
        parent.issueForm.principal.value='<bean:write name="webVo" />';
        parent.issueForm.principal_name.value='<bean:write name="pmName" />';
        parent.issueForm.principal_name.title='<bean:write name="webVo" />/<bean:write name="pmName" />';
        parent.issueForm.principalScope.value='EMP';
        try {
            if(parent.issueForm.filleByfilleBy.options) {
                var labels=new Array();
                var values=new Array();
                <logic:iterate id="optionItem" name="filleByList" indexId="currentIndex">
                labels[<%=currentIndex%>]="<bean:write name="optionItem" property="label"/>";
                values[<%=currentIndex%>]="<bean:write name="optionItem" property="value"/>";
                </logic:iterate>
                parent.updateOptionList("document.issueForm.filleByfilleBy",labels,values);
                <logic:present name="filleBy">
                <logic:notEmpty name="filleBy">
                try {
                    parent.setSelectedValue("document.issueForm.filleByfilleBy",'<bean:write name="filleBy"/>');
                } catch(e) {
                    //alertError(e);
                }

                try {
                    parent.issueForm.phone.value="<bean:write name="phone"/>";
                    parent.issueForm.fax.value="<bean:write name="fax"/>";
                    parent.issueForm.email.value="<bean:write name="email"/>";
                } catch(e) {
                }
                </logic:notEmpty>
                </logic:present>
            } else {
                <%--
                <logic:present name="filleBy">
                parent.issueForm.filleByfilleBy.value="<bean:write name="filleBy"/>";
                parent.issueForm.filleBy.value="<bean:write name="filleBy"/>";
                </logic:present>
                --%>
            }
        } catch(ex) {
        }
    </script></body>
</html>
