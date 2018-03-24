<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="itf.hr.HrFactory,c2s.essp.common.user.DtoUser"%>
<%@include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value="Add Urged Record!"/>
        <tiles:put name="jspName" value="conclusion"/>
    </tiles:insert>

</head>
<script language="javaScript" type="text/javascript">

function allocateHr(){
  var oldValue=document.issueConclusionUrgeForm.urgedBy.value;
  var oldName=document.issueConclusionUrgeForm.urgedByName.value;
  var oldScope=document.issueConclusionUrgeForm.urgedByScope.value;
  var acntRid=document.issueConclusionUrgeForm.accountId.value;
  sType="single";
  //sType="multi";
  var result=oldValue;
  if(acntRid==null || acntRid=="") {
      alert("Please select a account");
      return;
  }
  var param = new HashMap();
  var oldUser = new allocUser(oldValue,oldName,"",oldScope);
  param.put(oldValue,oldUser);
  var result = allocSingleInALL(param,acntRid,"","");
  document.issueConclusionUrgeForm.urgedBy.value=param.values()[0].loginId;
  document.issueConclusionUrgeForm.urgedByName.value = param.values()[0].name;
  document.issueConclusionUrgeForm.urgedByName.title = param.values()[0].loginId+"/"+param.values()[0].name;
  document.issueConclusionUrgeForm.urgedByScope.value = param.values()[0].type;
}

function SaveSend(){

    if(!confirm('Do you really save and send it?'))
    {  return ;}
    issueConclusionUrgeForm.action="<%=contextPath%>/issue/issue/conclusion/urgeAdd.do?IsMail=true";
    issueConclusionUrgeForm.submit();
}
</script>
<body bgcolor="#ffffff" >

<center class="form_title">
        Urged Record Detail
    </center>
<br />

<html:form name="issueConclusionUrgeForm"action="<%=contextPath+"/issue/issue/conclusion/urgeAdd.do"%>" method="post" enctype="multipart/form-data">
 <html:hidden name="rid"  beanName="webVo" />
 <html:hidden name="issueRid"  beanName="webVo" />
 <html:hidden name="accountId"  beanName="webVo" />


<table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">

        <tr>
            <td class="list_range" width="80">Urged by</td>                   <%--Urged Record--%>
            <bean:define id="urgedBy" name="webVo" property="urgedBy" />
            <bean:define id="urgedByScope" name="webVo" property="urgedByScope" />
             <%
                String urgedByName = "";
                if(DtoUser.USER_TYPE_EMPLOYEE.equals(urgedByScope)) {
                  urgedByName = HrFactory.create().getName((String)urgedBy);
                } else {
                  urgedByName = HrFactory.create().getCustomerName((String)urgedBy);
                }
             %>
             <input type="hidden" name="urgedBy" value="<%=urgedBy%>">
             <input type="hidden" name="urgedByScope" value="<%=urgedByScope%>">
            <td class="list_range" width="145" title="<%=urgedBy+"/"+urgedByName%>">
            <html:text beanName="webVo" name="urgedByName" value="<%=urgedByName%>" fieldtype="text" styleId="input_common"  next="urgeTo" prev="" imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>"
                      imageWidth="20"
                      readonly="true"
                      imageOnclick="allocateHr()" />
            </td>
             <td class="list_range" width="60" >&nbsp;&nbsp;&nbsp;Urged to</td>
             <bean:define id="urgeTo" name="webVo" property="urgeTo" />
             <bean:define id="urgeToScope" name="webVo" property="urgeToScope" />
             <%
                String urgeToName = "";
                if(DtoUser.USER_TYPE_EMPLOYEE.equals(urgeToScope)) {
                  urgeToName = HrFactory.create().getName((String)urgeTo);
                } else {
                  urgeToName = HrFactory.create().getCustomerName((String)urgeTo);
                }
             %>
            <input type="hidden" name="urgeTo" value="<%=urgeTo%>">
            <input type="hidden" name="urgeToScope" value="<%=urgeToScope%>">
            <td class="list_range" title="<%=urgeTo+"/"+urgeToName%>">
                <html:text beanName="webVo" name="urgeToName" value="<%=urgeToName%>" fieldtype="text"  styleId="input_common" next="attachment"  prev="urgedBy" readonly="true"/>
            </td>
        </tr>
        <tr>

            <td class="list_range" width="80">Attachment</td>
            <td class="list_range"  width="145">
                <logic:notEmpty name="webVo" property="attachment">
            <bean:define id="downloadUrl" name="webVo" property="attachment"/>
            <html:file name="attachment" styleId="conclusionUgAttachment"
                imageSrc="<%=contextPath+"/image/download.gif"%>"
                imageHref="<%=String.valueOf(downloadUrl)%>" imageStyle="border:0" imageWidth="20"/>
        </logic:notEmpty>
        <logic:empty name="webVo" property="attachment">
        <html:file name="attachment" styleId="conclusionUgAttachment"
next="attachmentdesc" prev="urgeTo"/>
         </logic:empty>

            </td>

            <td class="list_range" width="60"> &nbsp;&nbsp;&nbsp;Desc</td>
            <td class="list_range"  >
                <html:text beanName="webVo" name="attachmentdesc"   prev="urgedAttachment" fieldtype="text" next="urgDate"styleId="attachmentdesc" maxlength="250"/>
            </td>
            </tr>

        <tr>
            <td class="list_range" width="80">Date</td>
            <td class="list_range"  >
               <html:text beanName="webVo" name="urgDate" fieldtype="dateyyyymmdd"  ondblclick="getDATE(this)" styleId="urgDate" prev="attachmentdesc" next="description"  />
            </td>
            </tr>
        <tr>
            <td class="list_range" width="80">Description</td>
            <td class="list_range"  colspan="3">
                <html:textarea beanName="webVo" name="description" maxlength="250"  prev="urgDate" cols="45" rows="5" styleId="text_area" ></html:textarea>
            </td>
        </tr>

        <tr>
            <td colspan="4" align="right" height="50" valign="bottom">
                <html:button  value="Save&Send" name="save&send" styleId="button" onclick="SaveSend();"/>
                <!--<html:submit value="Save" name="save" styleId="button" />-->
                <input type="reset" value="Reset" name="reset" class="button" >
                <html:button value="Close" name="close" styleId="button"  onclick="window.close();"/>
            </td>
        </tr>
</table><br><br>

</html:form>
</body>
</html>

