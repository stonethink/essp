<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<% String sCssPath = request.getContextPath()+"/issue/typedefine/risk/IssueRisk"; %>
<bean:define id="cssPath" value="<%=sCssPath%>"/>
<bean:parameter id="typeName" name="typeName" value=""/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value="Add Risk Influence"/>
        <tiles:put name="jspName" value="<%=cssPath%>"/>
    </tiles:insert>
<script language="javascript" type="text/javascript">

function onvalidate(obj){
    var min = 0;
    var max = 0;
    min = document.forms[0].riskMinLevel.value;
    max = document.forms[0].riskMaxLevel.value;
    if ( (max-min) < 0 ){
      alert('<bean:message bundle="issue" key="issue.risk.add.error.info"/>');
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
        opener.dataForm.action="<%=contextPath%>/issue/typedefine/risk/riskAddAction.do";
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
     <font face="Arial Black" size="3" class="form_title"><bean:message bundle="issue" key="issue.risk.title"/></font>
</center>
<br />
<html:form action="/issue/typedefine/risk/riskAddAction" method="post" onsubmit="return onvalidate(this);">
<html:hidden name="typeName" beanName="webVo"/>

<table bgcolor="#ffffff" cellpadding="1" cellspacing="1" border="0" align="center">

        <tr>
            <td class="list_range" width="50"><bean:message bundle="issue" key="issue.risk.add.riskInfluence"/></td>
            <td class="list_range" width="100" colspan="2">
                <html:text beanName="" name="riskInfluence" fieldtype="text" styleId="input_common"  prev="Close" next="riskMinLevel" req="true" maxlength="100"/>
            </td>
           <td></td>
           <td></td>
        </tr>
        <tr>
            <td class="list_range" width="60"><bean:message bundle="issue" key="issue.risk.add.riskMinLevel"/></td>
            <td class="list_range" width="100">
                <html:text beanName="webVo" name="riskMinLevel" fieldtype="number" fmt="4.0" styleId="input_common" prev="riskInfluence" next="riskMaxLevel" req="true" />
            </td>
            <td class="list_range" width="60"><bean:message bundle="issue" key="issue.risk.add.riskMaxLevel"/></td>
            <td class="list_range" width="100">
                <html:text beanName="webVo" name="riskMaxLevel" fieldtype="number" fmt="4.0" styleId="input_common" next="riskWeight"  prev="riskMinLevel" req="true" />
            </td>
        </tr>
        <tr>
            <td class="list_range" width="60"><bean:message bundle="issue" key="issue.risk.add.riskWeight"/></td>
            <td class="list_range" width="100">
                <html:text beanName="webVo" name="riskWeight" fieldtype="number" fmt="3.0" styleId="input_common" next="riskSequence" prev="riskMaxLevel"  req="true" />
            </td>
            <td class="list_range" width="60"><bean:message bundle="issue" key="issue.risk.add.riskSequence"/></td>
            <td class="list_range" width="100">
                <html:text beanName="webVo" name="riskSequence" fieldtype="number" fmt="4.0" styleId="input_common" next="riskDescription"  prev="riskWeight"  req="true" />
            </td>
        </tr>
        <tr>
            <td class="list_range" width="60"><bean:message bundle="issue" key="issue.risk.add.riskDescription"/></td>
            <td class="list_range" width="100" colspan="3">
                <html:textarea beanName="" name="riskDescription" maxlength="500" next="save" prev="riskSequence" cols="40" rows="5" styleId="text_area" />
            </td>
        </tr>

         <tr>
            <td colspan="4" align="right" height="50" valign="middle">
                <html:button value="Save" name="save" styleId="button" next="reset" prev="riskDescription"  onclick="onSaveData();"/>
                <input type="reset" value="Reset" name="reset" class="button" >
                <html:button value="Close" name="close" styleId="button"  onclick="window.close();"/>
            </td>
        </tr>
</table>
</html:form>
</body>
</html>
