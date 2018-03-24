<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<html>
<head>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Send Email"/>
  <tiles:put name="jspName" value="sendMail"/>
</tiles:insert>
<script language="JavaScript" src="<%=contextPath%>/js/humanAllocate.js"></script>
<script language="javaScript" type="text/javascript">
function allocateHrTo(){
  var oldValue=document.issueGeneralResolutionForm.mailto.value;
  var acntRid=document.issueGeneralResolutionForm.accountId.value;
  //sType="single";
  sType="multi";
  var result=oldValue;
  if(acntRid==null || acntRid=="") {
      alert("Please select a account");
      return;
  }
   // var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type="+sType+"&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr=" , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
    var result=allocMultipleInJsp(oldValue,acntRid,'');
    document.issueGeneralResolutionForm.mailto.value=result;
}
function allocateHrCc(){
  var oldValue=document.issueGeneralResolutionForm.cc.value;
  var acntRid=document.issueGeneralResolutionForm.accountId.value;
  //sType="single";
  sType="multi";
  var result=oldValue;
  if(acntRid==null || acntRid=="") {
      alert("Please select a account");
      return;
  }
   // var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type="+sType+"&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr=" , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
    var result=allocMultipleInJsp(oldValue,acntRid,'');
    document.issueGeneralResolutionForm.cc.value=result;
}

function send(){
    var mailto = issueGeneralResolutionForm.mailto.value;
    if(mailto==null || mailto==""){
         alert("Please fill in mailto");
         document.issueGeneralResolutionForm.mailto.focus();
    }else if(!confirm('Do you really save and send it?'))
    {  return ;}
    else{
        issueGeneralResolutionForm.action="<%=contextPath%>/issue/issue/ResolutionSend.do";
        issueGeneralResolutionForm.submit();
    }
}
</script>
</head>
<body bgcolor="#ffffff">
<center>
<font class="form_title">Assigned Issue</font>
<br/>
</center>

<html:form name="issueGeneralResolutionForm" action="" method="post" enctype="multipart/form-data">
     <html:hidden name="rid"  beanName="webVo" />
     <html:hidden name="issuefid" beanName="webVo"/>
     <html:hidden name="accountId" beanName="webVo"/>
     <html:hidden name="issue" beanName="webVo"/>

  <table align="center" cellpadding="1" cellspacing="1" border="0">
      <tr>
        <td colspan="5" class="orarowheader"></td>
      </tr>
      <tr>
          <td class="list_range" width="120"><b>Mail To:</b></td>
          <td class="list_range" width="180">
        <html:text
          name="mailto"
          beanName="webVo"
          fieldtype="text"
          styleId="hr_select_w162"
          next="cc"
          imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>"
          imageWidth="18"
          imageOnclick="allocateHrTo()"
          maxlength="500"
          onfocus="true" />          </td><td width="20"></td>
          <td class="list_range" width="150"><b>CC:</b></td>
          <td class="list_range" width="160">
          <html:text
          name="cc"
          beanName="webVo"
          fieldtype="text"
          styleId="hr_select_w142"
          next="content"
          imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>"
          imageWidth="18"
          imageOnclick="allocateHrCc()"
          maxlength="300"
          onfocus="true"
          prev="to"/></td>
      </tr>


        <tr>
          <td colspan="5" class="orarowheader">General</td>
       </tr>
        <tr>
          <td class="list_range" width="120"><b>Issue Id</b></td>
          <td class="list_range" width="180">
             <html:text beanName="webVo" name="issueid" fieldtype="text" styleId="input_text"
                 readonly= "true"/>
          </td><td width="20"></td>
          <td class="list_range" width="150"><b>Issue Title</b></td>
          <td class="list_range" width="160">
             <html:text beanName="webVo" name="issuename" fieldtype="text" styleId="input_common" readonly= "true"/>
         </td>
      </tr>
       <tr>
          <td width="120" class="list_range"><b>Account<b/></td>
          <td class="list_range">
              <html:text beanName="webVo" name="account" fieldtype="text" styleId="input_text" readonly= "true"/>
          </td><td width="20"></td>
          <td width="150" class="list_range"><b>Priority</b></td>
          <td width="160" class="list_range">
             <html:text beanName="webVo" name="priority" fieldtype="text" styleId="input_common" readonly= "true"/>
         </td>
      </tr>
       <tr>
          <td width="120" class="list_range"><b>Filled By</b></td>
          <td class="list_range">
              <html:text beanName="webVo" name="fillby" fieldtype="text" styleId="input_text" readonly= "true"/>
          </td><td width="20"></td>
          <td width="150" class="list_range"><b>Filled Date</b></td>
          <td width="160" class="list_range">
              <html:text beanName="webVo" name="filldate" fieldtype="text" styleId="input_common" readonly= "true"/>
          </td>
      </tr>
       <tr>
          <td width="120" class="list_range"><b>Status</b></td>
          <td class="list_range">
             <html:text beanName="webVo" name="status" fieldtype="text" styleId="input_text" readonly= "true"/>
          </td><td width="20"></td>
          <td width="150" class="list_range"><b>Due Date</b></td>
          <td width="160" class="list_range">
              <html:text beanName="webVo" name="duedate" fieldtype="text" styleId="input_common" readonly= "true"/>
         </td>
      </tr>
       <tr>
          <td width="120" class="list_range"><b>Issue Description</b></td>
          <td class="list_range" colspan="4">
              <html:textarea beanName="webVo" name="issuedesc" maxlength="1000"  rows="3" cols="61"   styleId="text_area" readonly= "true"></html:textarea>
          </td>
      </tr>
       <tr>
          <td width="120" height="28" class="list_range"><b>Attachment Name</b></td>
          <td class="list_range">
            <html:text beanName="webVo" name="attachmentname1" fieldtype="text" styleId="input_text" readonly= "true"/>
          </td><td width="20"></td>
          <td width="150" class="list_range"><b>Attachment Description</b></td>
          <td width="160" class="list_range">
             <html:text beanName="webVo" name="attachmentdesc1" fieldtype="text" styleId="input_common"   maxlength="1000" readonly= "true"/>
          </td>
      </tr>
       <tr>
          <td class="list_range"></td>
          <td class="list_range"></td>
          <td class="list_range"></td><td class="list_range"></td>
          <td class="list_range"></td>
      </tr>

       <tr>
           <td colspan="5" class="orarowheader">Resolution</td>
        </tr>
        <tr>
          <td class="list_range" width="120"><b>Resolution</b></td>
          <td class="list_range" colspan="4" >
              <html:textarea beanName="webVo" name="resolution" maxlength="1000"   rows="3" cols="61" styleId="text_area"  readonly= "true"></html:textarea>
         </td>
      </tr>
       <tr>
          <td class="list_range" width="120" ><b>Assigned Date</b></td>
          <td class="list_range" width="180">
             <html:text beanName="webVo" name="assigneddate" fieldtype="text" styleId="input_text" readonly= "true"/>
          </td><td width="20"></td>
          <td class="list_range" width="150"><b>Plan Finish Date</b></td>
          <td class="list_range" width="160">
             <html:text beanName="webVo" name="finishdate" fieldtype="text" styleId="input_common" readonly= "true"/>
          </td>
      </tr>
       <tr>
          <td width="120" class="list_range"><b>Attachment Name</b></td>
          <td width="180" class="list_range">
             <html:text beanName="webVo" name="attachmentname2" fieldtype="text" styleId="input_text" readonly= "true"/>
          </td><td width="20"></td>
          <td width="150" class="list_range"><b>Attachment Description</b></td>
          <td width="160" class="list_range">
              <html:text beanName="webVo" name="attachmentdesc2" fieldtype="text" styleId="input_common"  maxlength="1000" readonly= "true"/>
          </td>
      </tr>
      <tr>
          <td class="list_range">&nbsp;</td><td class="list_range">&nbsp;</td>
          <td class="list_range">&nbsp;</td>
          <td class="list_range">&nbsp;</td>
          <td class="list_range">&nbsp;</td>
      </tr>
      <tr>
          <td colspan="5" align="right">
            <html:button name="Send" value="Send" styleId="button" onclick="send();"/>
            <html:button  name="Close" value="Close" styleId="button" onclick="window.close();" />
          </td>
      </tr>
  </table>
</html:form>
</body>
</html>
