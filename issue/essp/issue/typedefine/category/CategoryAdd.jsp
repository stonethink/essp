<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:parameter id="typeName" name="typeName"/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value="Add Issue Category"/>
        <tiles:put name="jspName" value="IssueCategory"/>
    </tiles:insert>
<script language="javascript">
function onSaveData() {
    if(!submitForm(issueCategoryForm)) {
        submit_flug=false;
    } else {
        opener.dataForm.action="<%=contextPath%>/issue/typedefine/category/categoryAddAction.do";
        opener.dataForm.typeName.value=document.issueCategoryForm.typeName.value;
        opener.dataForm.categoryName.value=document.issueCategoryForm.categoryName.value;
        opener.dataForm.sequence.value=document.issueCategoryForm.sequence.value;
        opener.dataForm.description.value=document.issueCategoryForm.description.value;
        opener.dataForm.submit();
        window.close();
    }
}
</script>
</head>
<body>
    <center class="form_title">
        Issue Category Detail
    </center>
    <br />
    <html:form action="/issue/typedefine/category/categoryAddAction"  onsubmit="return submitForm(this);">
        <html:hidden name="typeName" beanName="webVo"/>
    <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
        <tr>
            <td class="list_range" width="50" height="40">Category</td>
            <td class="list_range" width="160">
               <html:text beanName="" name="categoryName" fieldtype="text" styleId="input_common" next="sequence"    req="true" maxlength="100"/>
            </td>
            <td class="list_range2" width="70">Sequence</td>
            <td class="list_range" width="20">
                <html:text beanName="webVo" name="sequence" fieldtype="number" fmt="4.0" styleId="input_common2" next="description"  prev="categoryName"  req="true" />
            </td>
        </tr>
        <tr>
            <td class="list_range" width="50" height="40">Description</td>
            <td class="list_range" colspan="3" width="250">
               <html:textarea beanName="" name="description" maxlength="500"  prev="sequence" next="save" cols="45" rows="5" styleId="text_area"  />
            </td>
        </tr>
        <tr>
            <td colspan="4" align="right" height="50" valign="middle">
                <html:button value="Save" name="save" styleId="button" onclick="onSaveData();"/>
                <input type="reset" value="Reset" name="reset" class="button" >
                <html:button value="Close" name="close" styleId="button" onclick="window.close();" />
            </td>
        </tr>
    </table>
    </html:form>
</body>
</html>
