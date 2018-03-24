<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<%boolean readOnlyFlag = true;%>
<logic:equal name="webVo" property="isFilledBy" value="FilledBy">
<%readOnlyFlag = true;%>
</logic:equal>
<logic:equal name="webVo" property="isPrincipal" value="Principal">
<%readOnlyFlag = false;%>
</logic:equal>
<logic:equal name="webVo" property="isexecutor" value="Assign To">
<%readOnlyFlag = false;%>
</logic:equal>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" CRM V2.1"/>
  <tiles:put name="jspName" value="conclusion"/>
</tiles:insert>
</head>
<script type="text/javascript">
function onChangeStatus() {
    var currentStatus=issueConlusionForm.closureStatus.value;
    if(currentStatus=="") {
//        issueConlusionForm.confirmDate.disabled=true;
//         issueConlusionForm.confirmBy.disabled=true;
//         issueConlusionForm.instructionClosure.disabled=true;
    } else {
//        issueConlusionForm.confirmDate.disabled=false;
//        issueConlusionForm.confirmBy.disabled=false;
//        issueConlusionForm.instructionClosure.disabled=false;
    }
}
 function onSaveData(){
     <%
     if(readOnlyFlag) {
         %>
         return;
         <%
     }
     %>
     issueConlusionForm.submit();

    }
   function urged(){
    var option = "Top=200,Left=150,Height=350,Width=740,toolbar=no,scrollbars=no,status=yes";
    window.open("<%=contextPath%>/issue/issue/conclusion/issueConclusionUrgeList.do?rid=<bean:write name="webVo"
property="rid"/>" ,"",option);
}
</script>
<body bgcolor="#ffffff">
<html:form name="issueConlusionForm" action="<%=contextPath+"/issue/issue/conclusion/issueConclusion.do"%>" method="post" enctype="multipart/form-data">
  <html:hidden name="rid" beanName="webVo"/>
  <bean:define id="_attachmentDesc" name="webVo" property="attachmentDesc"/>
  <table bgcolor="ffffff" cellpadding="0" cellspacing="1" border="0" align="left" style="padding-left:8px" width="100%">
    <tr>
      <td class="list_range" width="180">Solved Desc</td>
      <td class="list_range" colspan="4" width="*">
        <html:textarea beanName="webVo" name="solvedDescription" maxlength="250" next="finishedDate" prev="actualInflu" rows="4" styleId="solvedDesc" readonly="<%=readOnlyFlag%>"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="180">Finished Date</td>
      <td class="list_range">
        <html:text beanName="webVo" name="finishedDate" fieldtype="dateyyyymmdd" ondblclick="getDATE(this)" styleId="finishedDate" prev="solvedDesc" next="deliveredDate" readonly="<%=readOnlyFlag%>"/>
      </td>
      <td width="50">      </td>
      <td class="list_range" width="160">Delivered Date</td>
      <td width="*" class="list_range">
        <html:text beanName="webVo" name="deliveredDate" fieldtype="dateyyyymmdd" ondblclick="getDATE(this)" styleId="deliveredDate" next="conclusionAttachment" prev="finishedDate" readonly="<%=readOnlyFlag%>"/>
      </td>
    </tr>
    <TR>
      <TD class="list_range" width="180">Attachment</TD>
      <TD class="list_range" width="270">
        <logic:notEmpty name="webVo" property="attachment">
          <bean:define id="downloadUrl" name="webVo" property="attachment"/>
          <html:file name="attachment" styleId="conclusionAttachment" imageSrc="<%=contextPath+"/image/download.gif"%>" imageHref="<%=String.valueOf(downloadUrl)%>" imageStyle="border:0" imageWidth="20" readonly="<%=readOnlyFlag%>"/>
        </logic:notEmpty>
        <logic:empty name="webVo" property="attachment">
<!-- <html:file name="attachment" styleId="conclusionAttachment" styleClass="input" onchange="setChanged()" next="conclusionAttachmentDec" prev="deliveredDate" readonly="<%=readOnlyFlag%>"/>-->
          <html:file name="attachment" styleId="conclusionAttachment" onchange="setChanged()" next="conclusionAttachmentDec" prev="deliveredDate" readonly="<%=readOnlyFlag%>"/>
        </logic:empty>
      </TD>
      <td width="50">      </td>
      <TD class="list_range" width="160">Attachment Desc</TD>
      <TD class="list_range" title="<%=_attachmentDesc%>" width="*">
        <html:text styleId="conclusionAttachmentDec" fieldtype="text" name="attachmentDesc" beanName="webVo" value="" next="closureStatus" prev="conclusionAttachment" readonly="<%=readOnlyFlag%>" maxlength="250"/>
      </TD>
    </tr>
    <tr>
      <td class="list_range" width="180">Waiting (Days)</td>
      <td class="list_range" align="right">
        <html:text beanName="webVo" name="waiting" fieldtype="number" styleId="waitingDays" fmt="3.0" prev="closureStatus" next="confirmDate" readonly="true"/>
        <html:button styleId="btnOk" name="btnOk" value="Urged" onclick="urged()" next="btnCancel" prev=""/>

      <td width="50">      </td>
      <td class="list_range" width="160">      </td>
      <td class="list_range" width="260">      </td>
    </tr>
  </TABLE>
</html:form>
</body>
</html>
