<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value="Update Issue Scope"/>
        <tiles:put name="jspName" value="IssueScope"/>
    </tiles:insert>
<script language="javascript">
function onSaveData() {
    if(!submitForm(issueScopeForm)) {
        submit_flug=false;
    } else {
        opener.dataForm.action="<%=contextPath%>/issue/typedefine/scope/scopeUpdateAction.do";
        opener.dataForm.typeName.value=document.issueScopeForm.typeName.value;
        opener.dataForm.scope.value=document.issueScopeForm.scope.value;
        opener.dataForm.vision.value=document.issueScopeForm.vision.value;
        opener.dataForm.sequence.value=document.issueScopeForm.sequence.value;
        opener.dataForm.description.value=document.issueScopeForm.description.value;
        opener.dataForm.submit();
        window.close();
    }
}
</script>
</head>
<body>
    <center class="form_title">
        Issue Scope Detail
    </center>
    <br />

    <html:form action="/issue/typedefine/scope/scopeUpdateAction">
    <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
        <html:hidden  beanName="webVo" property="typeName" />
            <tr>
            <td class="list_range" width="50">Scope</td>
            <td class="list_range" width="100">
               <html:text beanName="webVo" name="scope" fieldtype="text" styleId="input_common" next="vision"  prev="Close"  req="true" maxlength="100" readonly="true"/>
            </td>
            <td class="list_range1" width="140">Vision To Customer</td>
            <td class="list_range" width="10">
                <html:select beanName="webVo" name="vision"   styleId="select_range" next="Sequence" prev="scop">
                  <html:optionsCollection name="webVo" property="allVision" />
                </html:select>
            </td>
        </tr>
        <tr>
            <td class="list_range" width="50" >Sequence</td>
            <td class="list_range"  align="left" width="100">
                <html:text beanName="webVo" name="sequence" fieldtype="number" fmt="4.0" styleId="input_common" next="description"  prev="vision"  req="true" />
            </td>
           <td class="list_range" width="50" colspan="2">&nbsp;</td>
        </tr>
        <tr>
            <td class="list_range" width="50">Description</td>
            <td class="list_range" colspan="3" width="250">

                <html:textarea  name="description" maxlength="500"  prev="sequence" next="save" cols="45" rows="5" styleId="text_area" beanName="webVo" value=""/>
            </td>
        </tr>
        <tr>
            <td colspan="4" align="right" height="50" valign="middle">
                <html:button value="Save" name="save" styleId="button"  onclick="onSaveData();"/>
                <input type="reset" value="Reset" name="reset" class="button" >
                <html:button value="Close" name="close" styleId="button" onclick="window.close();" />
            </td>
        </tr>
    </table>

    </html:form>
</body>
</html>
