<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="c2s.essp.common.user.DtoUser" %>
<%@page import="itf.hr.HrFactory"%>
<%@include file="/inc/pagedef.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<%DtoUser user = (DtoUser) session.getAttribute(DtoUser.SESSION_USER);%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Add Topic"/>
  <tiles:put name="jspName" value="discuss"/>
</tiles:insert>
<script language="JavaScript" src="<%=contextPath%>/js/humanAllocate.js"></script>
<script language="javaScript" type="text/javascript">
function onBodyLoad(){
     document.issueDiscussTitleForm.title.focus();
}

function onsave(){
     var title=issueDiscussTitleForm.title.value;
     if(title==null||title==""){
            alert("Please fill in title");
            document.issueDiscussTitleForm.title.focus();
     }else{
         try{
              document.issueDiscussTitleForm.submit();
            } catch(e){
            	alertError(e);
            }
     }
}

function onsavesend(){
     var title=issueDiscussTitleForm.title.value;
     if(title==null || title==""){
            alert("Please fill in title");
            document.issueDiscussTitleForm.title.focus();
     }else{
         try{
             issueDiscussTitleForm.action="<%=contextPath%>/issue/issue/discuss/issueDiscussTitleAdd.do?isMail=true";
             issueDiscussTitleForm.submit();
         } catch(e){
            	alertError(e);
            }
     }
}

function allocateHr(){
  var oldValue=document.issueDiscussTitleForm.filledBy.value;
  var oldName=document.issueDiscussTitleForm.filledByName.value;
  var acntRid=document.issueDiscussTitleForm.accountRid.value;
  var filledByScope = document.issueDiscussTitleForm.filledByScope.value;
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
  document.issueDiscussTitleForm.filledBy.value=param.values()[0].loginId;
  document.issueDiscussTitleForm.filledByName.value = param.values()[0].name;
  document.issueDiscussTitleForm.filledByName.title = param.values()[0].loginId+"/"+param.values()[0].name;
  document.issueDiscussTitleForm.filledByScope.value = param.values()[0].type;
}
</script>
</head>
<style type="text/css">
    #input_field {width:100%;}
    #text1_display {width:220;}
</style>
<body bgcolor="#ffffff" onload="onBodyLoad()">
<center class="form_title">Add Question</center>
<br/>
<html:form  action="/issue/issue/discuss/issueDiscussTitleAdd.do" method="post" enctype="multipart/form-data">
  <html:hidden name="issueRid" beanName="webVo"/>
  <html:hidden name="accountRid" beanName="webVo"/>
  <input type="hidden" name="filledByScope" value="<%=user.getUserType()%>"/>
  <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
    <tr>
      <td class="list_range" width="80">Title</td>
      <td class="list_range" colspan="5">
        <html:text beanName="webVo" name="title" fieldtype="text" styleId="title_display_all" next="attachment" req="true" maxlength="500"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Filled Date</td>
      <td class="list_range" width="300">
        <html:text beanName="webVo" name="filledDate" fieldtype="dateyyyymmdd" styleId="title_display" ondblclick="getDATE(this)" req="false" maxlength="500"/>
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
        <html:text beanName="webVo" name="attachmentDesc" fieldtype="text" styleId="text_display" next="description" req="false" maxlength="1000"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Description</td>
      <td class="list_range" width="600" colspan="5">
        <html:textarea beanName="webVo" name="description" maxlength="1000" prev="attachmentDesc" cols="45" rows="8" styleId="text_area" />
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Remark</td>
      <td class="list_range" width="600" colspan="5">
        <html:textarea beanName="webVo" name="remark" maxlength="1000" prev="attachmentDesc" cols="45" rows="8" styleId="text_area" />
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
</html:form>
</body>
</html>
