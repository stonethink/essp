<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="c2s.essp.common.user.DtoUser" %>
<%@page import="itf.hr.HrFactory"%>
<%@include file="/inc/pagedef.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<%DtoUser user = (DtoUser) session.getAttribute(DtoUser.SESSION_USER);%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Add Reply"/>
  <tiles:put name="jspName" value="discuss"/>
</tiles:insert>
<script language="JavaScript" src="<%=contextPath%>/js/humanAllocate.js"></script>
<script language="javaScript" type="text/javascript">
      function onBodyLoad(){
          document.issueDiscussReplyForm.title.focus();
      }

      function onsave(){
          var title=issueDiscussReplyForm.title.value;
          if(title=="" ||title==null){
            alert("Please fill in title");
            document.issueDiscussReplyForm.title.focus();
          }else{
          	try{
             document.issueDiscussReplyForm.submit();
            } catch(e){
          	 	alertError(e);
            }

          }
      }

      function onsavesend(){
          var title=issueDiscussReplyForm.title.value;

          if(title=="" ||title==null){
            alert("Please fill in title");
            document.issueDiscussReplyForm.title.focus();
          }else{
          	try{
                issueDiscussReplyForm.action="<%=contextPath%>/issue/issue/discuss/issueDiscussReplyAdd.do?isMail=true";
                issueDiscussReplyForm.submit();
            } catch(e){
          	 	alertError(e);
            }
          }
      }

function allocateHr(){
  var oldValue=document.issueDiscussReplyForm.filledBy.value;
  var oldName=document.issueDiscussReplyForm.filledByName.value;
  var acntRid=document.issueDiscussReplyForm.accountRid.value;
  var filledByScope = document.issueDiscussReplyForm.filledByScope.value;
  sType="single";
  //sType="multi";
  var result=oldValue;
  if(acntRid==null || acntRid=="") {
      alert("Please select a account");
      return;
  }
  var param = new HashMap();
  var oldUser = new allocUser(oldValue,oldName,"",filledByScope);
  param.put(oldValue,oldUser);
  var result = allocSingleInALL(param,acntRid,"","");
//  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type="+sType+"&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr=" , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  document.issueDiscussReplyForm.filledBy.value=param.values()[0].loginId;
  document.issueDiscussReplyForm.filledByName.value = param.values()[0].name;
  document.issueDiscussReplyForm.filledByName.title = param.values()[0].loginId+"/"+param.values()[0].name;
  document.issueDiscussReplyForm.filledByScope.value = param.values()[0].type;
}
</script>
</head>
<style type="text/css">
    #input_field {width:100%}
    #text1_display {width:220;}
</style>
<body bgcolor="#ffffff" onload="onBodyLoad()">
<center class="form_title">Add  Response</center>
<br/>
<html:form action="/issue/issue/discuss/issueDiscussReplyAdd.do" method="post" onsubmit="return submitForm(this);" enctype="multipart/form-data">
  <html:hidden name="titleId" beanName="webVo"/>
  <html:hidden name="accountRid" beanName="webVo"/>
  <input type="hidden" name="filledByScope" value="<%=user.getUserType()%>"/>
  <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center" width="98%">
    <tr>
      <td class="list_range" width="80">Title</td>
      <td class="list_range" colspan="5">
        <html:text beanName="webVo" name="title" fieldtype="text" styleId="title_display_all" req="true" maxlength="500"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Filled Date</td>
      <td class="list_range" width="300">
        <html:text beanName="webVo" name="filledDate" fieldtype="dateyyyymmdd" styleId="title_display" ondblclick="getDATE(this)" next="attachment" prev="cc" req="false" maxlength="500"/>
      </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td class="list_range" width="80">Filled By</td>
      <bean:define id="filledBy_id" name="webVo" property="filledBy" />
      <%
         String filledByName="";
         if(DtoUser.USER_TYPE_EMPLOYEE.equals(user.getUserType())) {
           filledByName=HrFactory.create().getName((String)filledBy_id);
         } else {
           filledByName=HrFactory.create().getCustomerName((String)filledBy_id);
         }
      %>
      <td class="list_range" width="220" title="<%=filledBy_id+"/"+filledByName%>">
      <html:hidden beanName="webVo" property="filledBy" />
        <html:text
            beanName="webVo"
            name="filledByName"
            fieldtype="text"
            styleId="text1_display"
            req="false"
            readonly="true"
            value="<%=filledByName%>"
            imageSrc="<%=user.getUserType().equals(DtoUser.USER_TYPE_EMPLOYEE)?contextPath+"/image/humanAllocate.gif":""%>"
            imageWidth="20"
            imageOnclick="allocateHr()"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Attachment</td>
      <td class="list_range" width="300">
        <html:file name="attachment" styleId="attachment2" next="attachmentDesc" prev="attachmen"/>
      </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td class="list_range" width="80">Desc</td>
      <td class="list_range" width="240">
        <html:text beanName="webVo" name="attachmentDesc" fieldtype="text" styleId="text_display" prev="urgedTo" next="description" req="false" maxlength="1000"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Description</td>
      <td class="list_range" width="600" colspan="5">
        <html:textarea beanName="webVo" name="description" maxlength="1000" prev="attachmentDesc" next="save" cols="45" rows="8" styleId="text_area"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Remark</td>
      <td class="list_range" width="600" colspan="5">
        <html:textarea beanName="webVo" name="remark" maxlength="1000" prev="attachmentDesc" next="save" cols="45" rows="8" styleId="text_area"/>
      </td>
    </tr>
    <tr>
      <td colspan="6" align="right" height="30" valign="top">
        <html:button value="Save&Send" name="Save&Send" styleId="button" onclick="onsavesend();"/>
        <html:button value="Save" name="save" styleId="button" onclick="onsave();"/>
        <input type="reset" value="Reset" name="reset" class="button">
        <html:button value="Close" name="close" styleId="button" onclick="window.close();"/>
      </td>
      <td>      </td>
    </tr>
  </table>
  <br>
  <br>
</html:form>
</body>
</html>
