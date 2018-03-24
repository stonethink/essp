<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="itf.hr.HrFactory,c2s.essp.common.user.DtoUser"%>
<%@include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value="Edit Urged Record!"/>
        <tiles:put name="jspName" value="conclusion"/>
    </tiles:insert>

</head>
<script type="text/javascript">

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
    issueConclusionUrgeForm.action="<%=contextPath%>/issue/issue/conclusion/urgeUpdate.do?IsMail=true";
    issueConclusionUrgeForm.submit();
}
</script>
<body bgcolor="#ffffff" >

<center class="form_title">
        Urged Record Detail
    </center>
<br />

<html:form name="issueConclusionUrgeForm" action="<%=contextPath+"/issue/issue/conclusion/urgeUpdate.do"%>" method="post" onsubmit="return submitForm(this);"enctype="multipart/form-data">
    <html:hidden name="rid"  beanName="webVo" />
    <html:hidden name="issueRid"  beanName="webVo" />
 <html:hidden name="accountId"  beanName="webVo" />
 <bean:define id="_urgedBy" name="webVo" property="urgedBy"/>
  <bean:define id="_urgeTo" name="webVo" property="urgeTo"/>
  <bean:define id="_urgedByScope" name="webVo" property="urgedByScope"/>
  <bean:define id="_urgeToScope" name="webVo" property="urgeToScope"/>
<bean:define id="_attachment" name="webVo" property="attachment"/>
  <bean:define id="_attachmentdesc" name="webVo" property="attachmentdesc"/>

<table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">

         <tr>
            <td class="list_range" width="80">Urged by</td>                   <%--Urged Record--%>
            <%
                String _urgedByName = "";
                if(DtoUser.USER_TYPE_EMPLOYEE.equals(_urgedByScope)) {
                  _urgedByName = HrFactory.create().getName((String)_urgedBy);
                } else {
                  _urgedByName = HrFactory.create().getCustomerName((String)_urgedBy);
                }
             %>
             <input type="hidden" name="urgedBy" value="<%=_urgedBy%>">
             <input type="hidden" name="urgedByScope" value="<%=_urgedByScope%>">
            <td class="list_range" width="145"  title="<%=_urgedBy+"/"+_urgedByName%>">
            <html:text beanName="webVo" name="urgedByName" value="<%=_urgedByName%>" fieldtype="text" styleId="input_common"  next="urgeTo" prev="" imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>"
                      imageWidth="20"
                      imageOnclick="allocateHr()" />
            </td>
             <td class="list_range" width="60">&nbsp;&nbsp;&nbsp;Urged to</td>
             <%
                String _urgeToName = "";
                if(DtoUser.USER_TYPE_EMPLOYEE.equals(_urgeToScope)) {
                  _urgeToName = HrFactory.create().getName((String)_urgeTo);
                } else {
                  _urgeToName = HrFactory.create().getCustomerName((String)_urgeTo);
                }
             %>
            <input type="hidden" name="urgeTo" value="<%=_urgeTo%>">
            <input type="hidden" name="urgeToScope" value="<%=_urgeToScope%>">
            <td class="list_range" title="<%=_urgeTo+"/"+_urgeToName%>">
                <html:text beanName="webVo" name="urgeToName" value="<%=_urgeToName%>" fieldtype="text"  styleId="input_common" next="attachment"  prev="urgedBy"  readonly="true" />
            </td>
        </tr>
        <tr>

            <td class="list_range" width="80">Attachment</td>
            <td class="list_range" width="145"  >
                <logic:notEmpty name="webVo" property="attachment">
            <bean:define id="downloadUrl" name="webVo" property="attachment"/>
            <html:file name="attachment" styleId="conclusionUgAttachment"
                imageSrc="<%=contextPath+"/image/download.gif"%>"
                imageHref="<%=String.valueOf(downloadUrl)%>" imageStyle="border:0" imageWidth="20"/>
        </logic:notEmpty>
        <logic:empty name="webVo" property="attachment">
        <html:file name="attachment" styleId="conclusionUgAttachment" onchange="setChanged()"
next="attachmentdesc" prev="urgeTo"/>
         </logic:empty>

            </td>

            <td class="list_range" width="60"> &nbsp;&nbsp;&nbsp;Desc</td>
            <td class="list_range"  title="<%=_attachmentdesc%>" >
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
