<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="typeName" name="webVo" property="typeName"/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value="Edit Issue Status!"/>
        <tiles:put name="jspName" value="IssueStatus"/>
    </tiles:insert>
<script language="javascript">
function onSaveData() {
    if(!submitForm(issueStatusForm)) {
        submit_flug=false;
    } else {
        opener.dataForm.action="<%=contextPath%>/issue/typedefine/status/statusEditAction.do";
        opener.dataForm.typeName.value=document.issueStatusForm.typeName.value;
        opener.dataForm.statusName.value=document.issueStatusForm.statusName.value;
        opener.dataForm.statusSequence.value=document.issueStatusForm.statusSequence.value;
        opener.dataForm.statusBelongTo.value=document.issueStatusForm.statusBelongTo.value;
        opener.dataForm.statusRelationDate.value=document.issueStatusForm.statusRelationDate.value;
        opener.dataForm.statusDescription.value=document.issueStatusForm.statusDescription.value;
        opener.dataForm.submit();
        window.close();
    }
}
</script>
</head>

<body bgcolor="#ffffff" >

<center>
        <font face="Arial Black" size="3" class="form_title"><bean:message bundle="issue" key="issue.status.title"/></font>   <%--Issue Status--%>
</center>
<br />

<html:form name="issueStatusForm" action="/issue/typedefine/status/statusEditAction" method="post">
    <html:hidden property="typeName"  beanName="webVo" />

<logic:present name="webVo" scope="request">
<table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">

        <tr>
            <td class="list_range" width="80" height="40"><bean:message bundle="issue" key="issue.status.update.table.status"/></td>                   <%--Issue Status--%>
            <td class="list_range" width="100">
            <html:text beanName="webVo" name="statusName" fieldtype="text" styleId="input_common"  next="statusSequence" req="true" readonly="true" maxlength="100"/>
            </td>
             <td class="list_range" width="80"><bean:message bundle="issue" key="issue.status.update.table.sequence"/></td>
            <td class="list_range" width="100">
                <html:text beanName="webVo" name="statusSequence" fieldtype="number" fmt="4.0" styleId="input_common2" next="statusBelongTo"  prev="statusName"  req="true" />
            </td>
        </tr>
        <tr>
            <td class="list_range" width="80" height="40"><bean:message bundle="issue" key="issue.status.update.table.belongto"/></td>
            <td class="list_range" width="100" align="right">
               <html:select beanName="webVo" name="statusBelongTo" styleId="statusBelongTo" next="statusRelationDate" prev="statusSequence" req="true">
                   <html:optionsCollection name="webVo" property="belongToArray" />
               </html:select>
            </td>
            <td class="list_range" width="80"><bean:message bundle="issue" key="issue.status.update.table.relationdate"/></td>
            <td class="list_range" width="100" align="right">
               <html:select beanName="webVo" name="statusRelationDate" styleId="statusRelationDate2" next="statusDescription" prev="statusBelongTo">
                  <html:optionsCollection name="webVo" property="relationDateArray" />
               </html:select>
            </td>
        </tr>
        <tr>
            <td class="list_range" width="80" height="40"><bean:message bundle="issue" key="issue.status.update.table.description"/></td>
            <td class="list_range" valign="top" colspan="3" width="280">
                <html:textarea beanName="webVo" name="statusDescription" maxlength="500"  prev="statusRelationDate" cols="45" rows="5" styleId="text_area"/>
            </td>
        </tr>

        <tr>
            <td colspan="4" align="right" height="40" valign="middle">
                <html:button value="Save" name="addd" styleId="button" onclick="onSaveData();"/>
                <input type="reset" value="Reset" name="reset" class="button" >
                <html:button value="Close" name="close" styleId="button"  onclick="window.close();"/>
            </td>
        </tr>
</table><br><br>
</logic:present>
</html:form>
</body>
</html>
