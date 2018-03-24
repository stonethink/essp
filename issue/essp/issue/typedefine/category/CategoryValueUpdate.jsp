<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value="Update Issue Category Value"/>
        <tiles:put name="jspName" value="IssueCategory"/>
    </tiles:insert>
<script language="javascript">
function onSaveData() {
    if(!submitForm(issueCategoryValueForm)) {
        submit_flug=false;
    } else {
        opener.dataForm.action="<%=contextPath%>/issue/typedefine/category/categoryValueUpdateAction.do";
        opener.dataForm.typeName.value=document.issueCategoryValueForm.typeName.value;
        opener.dataForm.categoryName.value=document.issueCategoryValueForm.categoryName.value;
        opener.dataForm.categoryValue.value=document.issueCategoryValueForm.categoryValue.value;
        opener.dataForm.sequence.value=document.issueCategoryValueForm.sequence.value;
        opener.dataForm.description.value=document.issueCategoryValueForm.description.value;
        opener.dataForm.submit();
        window.close();
    }
}
</script>
</head>
<body>
    <center class="form_title">
        Issue Category Value Detail
    </center>
    <br />
    <html:form action="/issue/typedefine/category/categoryValueUpdateAction">
    <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
        <html:hidden  beanName="webVo" property="typeName" />

        <tr>

            <td class="list_range" width="50">Category</td>
            <td class="list_range" width="100">
               <html:text beanName="webVo" name="categoryName" fieldtype="text" styleId="input_common" next="categoryValue"    req="true"  readonly="true" maxlength="100"/>
            </td>
            <td width="20"></td>
            <td class="list_range2" width="40">Value</td>
            <td class="list_range" width="90">
               <html:text beanName="webVo" name="categoryValue" fieldtype="text" styleId="input_common" next="sequence"   prev="categoryName" req="true"  readonly="true" maxlength="100"/>
            </td>
        </tr>

        <tr>
            <td class="list_range" width="50" >Sequence</td>
            <td class="list_range" colspan="4" width="250">
                <html:text beanName="webVo" name="sequence" fieldtype="number" fmt="4.0" styleId="input_common" next="description"  prev="categoryValue"  req="true" />
            </td>

        </tr>
        <tr>
            <td class="list_range" width="50">Description</td>
            <td class="list_range" colspan="4" width="250">
                <html:textarea beanName="webVo" name="description" maxlength="500"  prev="sequence" next="save" cols="45" rows="5" styleId="text_area"/>
            </td>
        </tr>
        <tr>
            <td colspan="5" align="right" height="50" valign="middle">
                <html:button value="Save" name="save" styleId="button" onclick="onSaveData();"/>
                <input type="reset" value="Reset" name="reset" class="button" >
                <html:button value="Close" name="close" styleId="button" onclick="window.close();" />
            </td>
        </tr>

    </table>
    </html:form>

</body>
</html>
