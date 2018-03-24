<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>

<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<bean:define id="webVo" name="webVo" scope="request">
</bean:define>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
        <tiles:put name="title" value="Issue Detail" />
        <tiles:put name="jspName" value="" />
</tiles:insert>
<style type="text/css">
    #input_field {width:100%;word-wrap:break-word}
    #input_text {width:170}
</style>

</head>
<body bgcolor="#ffffff">

<%--************************************************************************--%>
<center>
  <font class="form_title">Issue Management</font>
  <br/>
  <html:form  name="issueForm" action="" target="_parent" enctype="multipart/form-data">
    <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center" width="550">
        <html:hidden name="rid" beanName = "webVo" property="rid" />
        <tr>
        	<td valign="bottom" height="5" colspan="5" class="orarowheader">General</td>
        </tr>
      <tr valign="middle">
        <td class="list_range" width="80" align="right" valign="top" >Account</td>
        <td class="list_range"  align="left" valign="top"  colspan="4" width="*">
          <html:select name="accountId" styleId="input_field" beanName="webVo"  req="true" next="issueType" prev="" readonly="true">
            <logic:present name="webVo">
            <html:optionsCollection name="webVo" property="accountList"/>
            </logic:present>
        </html:select>        </td>
      </tr>
      <tr>
          <td class="list_range" width="80" valign="top" align="right">Issue Type</td>
          <td class="list_range" width="150" align="left" valign="top">
          <html:select name="issueType" styleId="input_text" beanName="webVo"  req="true"
              next="priority" prev="accountId"
               readonly="true">
            <html:optionsCollection name="webVo" property="issueTypeList" />
        </html:select>        </td>
        <td width="20"></td>
        <td class="list_range" valign="top" width="150" align="right">Priority</td>
        <td width="150" align="left" valign="top" class="list_range">
          <html:select name="priority"
                       styleId="input_text"
                       beanName="webVo"
                       req="true"
                       next="filleBy"
                       prev="issueType"
                       readonly="true">
            <html:optionsCollection name="webVo" property="priorityList"/>
        </html:select>        </td>
      </tr>
      <tr>
          <td class="list_range" width="80" valign="top"  align="right">Scope</td>
        <td class="list_range"  align="left" valign="top">
        <html:select name="scope" styleId="input_text" beanName="webVo"  next="issueId" prev="fax" req="true" readonly="true">
           <html:optionsCollection name="webVo" property="scopeList"/>
        </html:select> </td>
        <td width="20"></td>
          <td class="list_range" width="150" valign="top" align="right">Filld By</td>
          <td class="list_range" align="left" valign="top">
          <html:select styleId="input_text"  name="filleBy"
           beanName="webVo" req="true" next="filleDate" prev="priority" readonly="true">
             <logic:notEmpty name="webVo" property="filleByList">
             <html:optionsCollection name="webVo" property="filleByList"/>
             </logic:notEmpty>
           </html:select>        </td>
      </tr>


      <tr>
          <td width="80"  align="right" valign="top" class="list_range">Issue Id</td>
           <td class="list_range"  align="left" valign="top">
            <html:text styleId="input_text" fieldtype="text" maxlength="50" name="issueId"
           beanName="webVo" value="" next="issueName" readonly="true" prev="scope" req="true" />           </td>
           <td width="20"></td>
           <td class="list_range" width="150" valign="top"  align="right">Issue Name</td>
           <td class="list_range"  align="left" valign="top">
            <html:text styleId="input_text" fieldtype="text" name="issueName"
           beanName="webVo" value="" maxlength="50" next="description" prev="issueId" req="true" readonly="true"/></td>
      </tr>
        <tr>
           <td width="80"  align="right" valign="top" class="list_range">Filled Date</td>
           <td class="list_range" align="left" valign="top">
            <html:text styleId="input_text" fieldtype="dateyyyymmdd" name="filleDate"
          beanName="webVo" value="" next="phone" readonly="true" prev="filleBy" req="true"/> </td>
        <td width="20"></td>
           <td class="list_range" width="150" valign="top" align="right"></td>
          <td class="list_range" align="left" valign="top">          </td>
      </tr>
      <tr>
        <td width="80" height="40" valign="top" class="list_range">Description</td>
        <td colspan="4" valign="top" class="list_range" width="*">
          <html:textarea styleId="input_field" name="description" beanName="webVo" rows="3" maxlength="150"  next="attachment" prev="issueName" cols="44" readonly="true"/>        </td>
      </tr>
      <tr>
          <td width="80" align="right" valign="top" class="list_range">Attachment</td>
           <td class="list_range"  align="left" valign="top" width="170">
               <logic:notEmpty name="webVo" property="attachment">
            <bean:define id="downloadUrl" name="webVo" property="attachment"/>
           <html:file  name="attachment" styleId="input_text"
                imageSrc="<%=contextPath+"/image/download.gif"%>"
                imageHref="<%=String.valueOf(downloadUrl)%>" readonly="true"/>
             </logic:notEmpty>
          <logic:empty name="webVo" property="attachment">
            <html:file name="attachment" styleId="input_field" readonly="true"/>
          </logic:empty>           </td>
           <td width="20"></td>
           <td width="150"  align="right" valign="top" class="list_range">Desc</td>
           <td class="list_range"  align="left" valign="top">
               <html:text styleId="input_text" fieldtype="text" name="attachmentdesc"
          beanName="webVo" value=""  maxlength="250" next="issuePrincipal" prev="issueAttachment" readonly="true"/>         </td>
      </tr>
       <tr>
        	<td valign="bottom" height="5" colspan="5" class="orarowheader">&nbsp;</td>
      </tr>
       <tr>
           <td width="80"  align="right" valign="top" class="list_range">Principal</td>
           <td class="list_range"  align="left" valign="top" width="170">
           <html:text name="principal"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_field"
                      next="dueDate"
                      prev="attachmentdesc"
                      req="true"
                      readonly="true"
           />           </td>
        <td width="20"></td>
        <td width="150"  align="right" valign="top" class="list_range">Due Date</td>
           <td class="list_range"  align="left" valign="top">
            <html:text styleId="input_text" fieldtype="dateyyyymmdd" name="dueDate"
           beanName="webVo" value="" next="issueStatus" prev="Principal" req="true" readonly="true"/>         </td>
      </tr>
       <tr>
           <td width="80"  align="right" valign="top" class="list_range">Status</td>
           <td class="list_range"  align="left" valign="top">
               <html:select name="issueStatus" styleId="input_text" beanName="webVo"  next="issueDuplation" prev="issueDueDate" req="true" readonly="true">
             <html:optionsCollection name="webVo" property="statusList"/>
        </html:select>        </td>
           <td width="20"></td>
        <td width="150"  align="right" valign="top" class="list_range">Duplation Issue</td>
           <td class="list_range"  align="left" valign="top">
               <html:select name="duplationIssue" styleId="input_text" beanName="webVo"  next="" prev="issueStatus" req="true" readonly="true">
             <html:optionsCollection name="webVo" property="duplationIssueList" />
        </html:select>        </td>
      </tr>
       <tr>
           <td colspan="5" align="right"><html:button name="closeBtn" value="Close" styleId="closeBtn" onclick="window.close();"/></td>
       </tr>
    </table>
    <br>
    <br>
  </html:form>
</center>

</body>
</html>
