<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="typeName" name="webVo" property="typeName"/>

<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value="Edit Risk Influence"/>
        <tiles:put name="jspName" value=""/>
    </tiles:insert>
<script language="javascript" type="text/javascript">
function onvalidate(obj){
    var min = 0;
    var max = 0;
    min = document.forms[0].riskMinLevel.value;
    max = document.forms[0].riskMaxLevel.value;
    //alert(max-min);
    if ( (max-min) < 0 ){
      alert('<bean:message bundle="issue" key="issue.risk.update.errro.info"/>');
      return false;

    }else{
      if (submitForm(obj)==true){
          return true;
      }else{
        return false;
      }
    }


}

function onSaveData() {
    if(!onvalidate(issueRiskForm)) {
        submit_flug=false;
    } else {
        opener.dataForm.action="<%=contextPath%>/issue/typedefine/risk/riskUpdateAction.do";
        opener.dataForm.typeName.value=document.issueRiskForm.typeName.value;
        opener.dataForm.riskInfluence.value=document.issueRiskForm.riskInfluence.value;
        opener.dataForm.riskMinLevel.value=document.issueRiskForm.riskMinLevel.value;
        opener.dataForm.riskMaxLevel.value=document.issueRiskForm.riskMaxLevel.value;
        opener.dataForm.riskWeight.value=document.issueRiskForm.riskWeight.value;
        opener.dataForm.riskSequence.value=document.issueRiskForm.riskSequence.value;
        opener.dataForm.riskDescription.value=document.issueRiskForm.riskDescription.value;
        opener.dataForm.submit();
        window.close();
    }
}
</script>
</head>
<body bgcolor="#ffffff" >
<center>
        <font face="Arial Black" size="3" class="form_title">Risk Influence</font>
</center>
<br />

<html:form action="/issue/typedefine/risk/riskUpdateAction.do" method="post">
<input type="hidden" name="typeName" value="<%=(String)typeName%>"/>

<logic:present name="webVo" scope="request">
<table bgcolor="#ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
         <tr>
            <td class="list_range" width="50"><bean:message bundle="issue" key="issue.risk.update.riskInfluence"/></td>
            <td class="list_range" width="100">
                <html:text value="" beanName="webVo" name="riskInfluence" fieldtype="text" styleId="input_common" next="riskMinLevel" prev="Close" req="true" readonly="true" maxlength="100"/>
            </td>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td class="list_range" width="60"><bean:message bundle="issue" key="issue.risk.update.riskMinLevel"/></td>
            <td class="list_range" width="100">
                <html:text value="" beanName="webVo" name="riskMinLevel" fieldtype="number" fmt="4.0" styleId="input_common" prev="riskInfluence" next="riskMaxLevel" req="true" />
            </td>
            <td class="list_range" width="60"><bean:message bundle="issue" key="issue.risk.update.riskMaxLevel"/></td>
            <td class="list_range" width="100">
                <html:text value="" beanName="webVo" name="riskMaxLevel" fieldtype="number" fmt="4.0" styleId="input_common" next="riskWeight"  prev="riskMinLevel" req="true" />
            </td>
        </tr>
        <tr>
            <td class="list_range" width="50"><bean:message bundle="issue" key="issue.risk.update.riskWeight"/></td>
            <td class="list_range" width="100">
                <html:text value="" beanName="webVo" name="riskWeight" fieldtype="number" fmt="3.0" styleId="input_common" prev="riskMaxLevel" next="riskSequence" req="true" />
            </td>
            <td class="list_range" width="50"><bean:message bundle="issue" key="issue.risk.update.riskSequence"/></td>
            <td class="list_range" width="100">
                <html:text value="" beanName="webVo" name="riskSequence" fieldtype="number" fmt="4.0" styleId="input_common" next="riskDescription"  prev="riskWeight" req="true" />
            </td>
        </tr>
        <tr>
            <td class="list_range" width="60"><bean:message bundle="issue" key="issue.risk.update.riskDescription"/></td>
            <td class="list_range" width="100" colspan="3">
                <html:textarea value="" beanName="webVo" name="riskDescription" maxlength="500" prev="riskSequence" next="save" cols="40" rows="5" styleId="text_area"/>
            </td>
        </tr>

         <tr>
            <td colspan="4" align="right" height="50" valign="middle">
                <html:button value="Save" name="save" styleId="button" prev="riskDescription" next="reset" onclick="onSaveData();"/>
                 <input type="reset" value="Reset" name="reset" class="button" >
                <html:button value="Close" name="close" styleId="button"  onclick="window.close();"/>
            </td>
        </tr>
</table>
</logic:present>
</html:form>
</body>
</html>
