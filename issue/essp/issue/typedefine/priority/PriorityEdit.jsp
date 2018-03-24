<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="server.essp.issue.typedefine.priority.viewbean.VbPriority"%>

<bean:define id="typeName" name="oneVB" property="typeName"/>
<bean:define id="priority" name="oneVB" property="priority"/>
<bean:define id="duration" name="oneVB" property="duration"/>
<bean:define id="sequence" name="oneVB" property="sequence"/>
<bean:define id="description" name="oneVB" property="description"/>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<html>
<head>
    <tiles:insert page="/layout/head.jsp">
        <tiles:put name="title" value=" Issue Priority Edit " />
        <tiles:put name="jspName" value="IssuePriority"/>
      </tiles:insert>
<%------------------------script begin-----------------------------------------%>
<script type="text/javascript">
  function updateFunc(){
  if (checkValid()){
    opener.addPriority=document.dataForm.priority.value;
    opener.dataForm.action="<%=contextPath%>/issue/typedefine/priority/issuePriorityUpdateAction.do";
    opener.dataForm.typeName.value=document.dataForm.typeName.value;
    opener.dataForm.priority.value=document.dataForm.priority.value;
    opener.dataForm.sequence.value=document.dataForm.sequence.value;
    opener.dataForm.duration.value=document.dataForm.duration.value;
    opener.dataForm.description.value=document.dataForm.description.value;
    opener.dataForm.submit();
    window.close();
  }
}
function checkValid(){
  if (document.forms[0].priority.value==""){
    alert("Issue Priority must be filled!");
    return false;
  }else
    return true;
}
function closeFunc(){
  self.close();
}
</script>
<%------------------------script end-----------------------------------------%>
</head>
<body bgcolor="#ffffff">
<%----------------------------------------------------------------------------%>
<center>
      <font class="form_title">Issue  Priority  Edit</font>
      <br/>
      <html:form name="dataForm" action="/issue/typedefine/priority/issuePriorityUpdateAction.do">

      <input type="hidden" name="typeName" value="<%=typeName%>"/>
      <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center" >
      <tr valign="middle">
          <td height="40" class="list_range" width="80" align="right" valign="top">IssuePriority</td>
          <td  colspan="3" class="list_range" align="left" valign="top" width="120">
              <html:text name="priority" styleId="input_name" fieldtype="text" beanName="oneVB" req="true" readonly="true" maxlength="100"/>
          </td>
      </tr>
      <tr valign="top">
          <td height="40" class="list_range" valign="top" width="80">Sequence</td>
          <td class="list_range" valign="top" width="120">
            <html:text name="sequence" styleId="input_common" fieldtype="number" fmt="4.0" beanName="oneVB" req="true" />
          </td>
          <td class="list_range" width="80" valign="top" align="right">Duration</td>
          <td class="list_range" width="100" valign="top" align="left">
              <html:text name="duration" styleId="input_common" fieldtype="number" fmt="2.0" beanName="oneVB" req="true"/>
          </td>
      </tr>
      <tr valign="top">
          <td height="40" class="list_range" valign="top" width="80">Description</td>
          <td colspan="3" valign="top" class="list_range" width="300">
               <html:textarea styleId="text_area" name="description" beanName="" rows="3" maxlength="500" prev="status" cols="20" req="false"/>
          </td>
      </tr>

        <tr>
           <td height="40" class="list_range" valign="top" width="80"></td>
           <td colspan="3" align="right"  height="40" valign="middle">
               <html:button name="ok" value="  Save  " styleId="button" onclick="updateFunc();"/>
               <input type="reset" value="Reset" name="reset" class="button" >
               <html:button name="close" value="Close" styleId="button" onclick="closeFunc();"/>
            </td>
        </tr>
</table>
</html:form>
</center>
<%----------------------------------------------------------------------------%>
</body>
</html>
