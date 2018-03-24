<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>

<bean:parameter id="typeName" name="typeName"/>
<bean:parameter id="defaultSequence" name="DetailSize"/>
<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />


<html>
<head>
<tiles:insert page="/layout/head.jsp">
        <tiles:put name="title" value=" Issue Priority Add" />
        <tiles:put name="jspName" value="IssuePriority" />
</tiles:insert>
<%---------------------script begin---------------------------%>
<script type="text/javascript">
  function addFunc(){
  var tn=document.forms[0].typeName.value;
  if (tn==""){
    tn=opener.parent.parent.typeName;
    document.forms[0].typeName.value=tn;
  }
  if (checkValid()!=false){
    opener.addPriority=document.forms[0].priority.value;
    //document.forms[0].submit();
    opener.dataForm.action="<%=contextPath%>/issue/typedefine/priority/issuePriorityAddAction.do";
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
<%---------------------script end---------------------------%>
</head>
<body bgcolor="#ffffff">
<%--
out.println("typeName:"+typeName);
out.println("/defaultSequence:"+sizes);
--%>
<%--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--%>
<center>
      <font class="form_title">Issue  Priority</font>
      <br/>
      <html:form name="dataForm" action="/issue/typedefine/priority/issuePriorityAddAction.do">
      <html:hidden name="typeName" beanName="webVo"/>

      <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
      <tr valign="middle">
          <td height="40" class="list_range" width="80" align="right" valign="top">IssuePriority</td>
          <td  colspan="3" class="list_range" align="left" valign="top" width="300">
              <html:text name="priority" styleId="input_name" fieldtype="text" beanName="" req="true" next="duration" maxlength="100"/>
          </td>
      </tr>
      <tr valign="top">
          <td height="40" class="list_range" valign="top"  width="80">Sequence</td>
          <td class="list_range" valign="top" width="120">
            <html:text name="sequence" styleId="input_common" fieldtype="number" fmt="4.0" beanName="webVo" req="true" value="<%=defaultSequence%>"  next="description" prev="duration"/>
          </td>
          <td class="list_range" width="80" valign="top" >Duration</td>
          <td class="list_range" width="100" valign="top">
              <html:text name="duration" styleId="input_common" fieldtype="number" fmt="2.0" beanName="webVo" req="true" next="sequence" prev="priority"/>
          </td>
      </tr>
      <tr valign="top">
          <td height="40" class="list_range" valign="top" width="80">Description</td>
          <td colspan="3" valign="top" class="list_range" width="300">
               <html:textarea styleId="text_area" name="description" beanName="" rows="3" maxlength="500" prev="sequence" cols="54"/>
          </td>
      </tr>

        <tr>
           <td height="40" class="list_range" valign="top" width="80"></td>
           <td colspan="3" align="right"  height="40" valign="middle" >
               <html:button name="ok" value="  Save  " styleId="button" onclick="addFunc();"/>
               <input type="reset" value="Reset" name="reset" class="button" >
               <html:button name="close" value="Close" styleId="button" onclick="closeFunc();"/>
            </td>
        </tr>
</table><br><br>
</html:form>
</center>
<%--+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--%>
</body>
</html>
