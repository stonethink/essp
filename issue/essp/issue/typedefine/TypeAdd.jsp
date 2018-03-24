<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>

<bean:parameter id="defaultSequence" name="DetailSize"/>
<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />

<html>
<head>
<tiles:insert page="/layout/head.jsp">
        <tiles:put name="title" value=" Issue Type Add" />
        <% String jspName = contextPath + "/issue/typedefine/TypeDefineCSS"; %>
        <tiles:put name="jspName" value="<%=jspName%>" />
</tiles:insert>
<%--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--%>
<script language="javascript" type="text/javascript">
var newTypeName="";
function addFunc(){
  var se=document.forms[0].sequence.value;
  if (checkValid()!=false){
    newTypeName=document.forms[0].typeName.value;
    opener.newTypeName=newTypeName;
    //document.forms[0].submit();
    opener.issueTypeForm.action="<%=contextPath%>/issue/typedefine/issueTypeAddAction.do";
    opener.issueTypeForm.typeName.value=document.forms[0].typeName.value;
    opener.issueTypeForm.sequence.value=document.forms[0].sequence.value;
    if(document.forms[0].rst[0].checked) {
        opener.issueTypeForm.rst.value=document.forms[0].rst[0].value;
    } else {
        opener.issueTypeForm.rst.value=document.forms[0].rst[1].value;
    }
    opener.issueTypeForm.description.value=document.forms[0].description.value;
    opener.issueTypeForm.submit();
    window.close();
  }
}
function checkValid(){
  if (document.forms[0].typeName.value==""){
    alert("Issue Type must be filled!");
    return false;
  }else
    return true;
}


function openerRefresh(){
  opener.location.reload(true);
}

function closeFunc(){
  self.close();
}

</script>
<%--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--%>
</head>
<body bgcolor="#ffffff">

<%--************************************************************************--%>
<center>
  <font class="form_title">Issue  Type  Add</font>
  <br/>
  <html:form action="/issue/typedefine/issueTypeAddAction.do" target="_parent">
    <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
      <tr valign="middle">
        <td class="list_range" width="25%" align="right" valign="top" height="30">Issue Type</td>
        <td class="list_range" width="35%" align="left" valign="top">
          <html:text beanName="IssueTypeForm" name="typeName" fieldtype="text" styleId="typeName" next="sequence" req="true" maxlength="50"/>
        </td>
        <td class="list_range" valign="top" width="12%" align="right">Sequence</td>
        <td class="list_range" valign="top" width="28%">
          <html:text beanName="IssueTypeForm" name="sequence" fieldtype="number" fmt="8.0" styleId="sequence" next="rst" prev="typename" req="true" value="<%=defaultSequence%>"/>
        </td>
      </tr>
      <tr valign="middle">
        <td class="list_range" width="25%" valign="top" align="right" height="30">Status</td>
        <td class="list_range" colspan="3" valign="top" align="left">
          <input type="radio" name="rst" value="N" checked="checked">
          Enable
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="rst" value="X">
          Disable
        </td>
      </tr>
      <tr valign="middle">
        <td class="list_range" valign="top" height="30">Description</td>
        <td colspan="3" valign="top" class="list_range">
          <html:textarea styleId="description" name="description" beanName="IssueTypeForm" rows="3" maxlength="150" prev="false" cols="44"/>
        </td>
      </tr>
      <tr valign="middle">
        <td colspan="4" align="right" height="30" valign="bottom">
          <html:button name="ok" value=" Save " styleId="button" onclick="addFunc();"/>
          <input type="reset" value="Reset" name="reset" class="button">
          <html:button name="close" value="Close" styleId="button" onclick="closeFunc();"/>
        </td>
      </tr>
    </table>
    <br>
    <br>
  </html:form>
</center>
<%--************************************************************************--%>
</body>
</html>
