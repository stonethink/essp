<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="default_issueType_value" name="webVo" property="issueType"/>
<bean:define id="default_issueScope_value" name="webVo" property="scope"/>
<bean:define id="default_issuePriority_value" name="webVo" property="priority"/>
<bean:define id="default_filledBy_value" name="searchForm" property="filledBy" scope="session"/>
<bean:define id="default_principal_value" name="searchForm" property="principal" scope="session"/>
<bean:define id="default_resolveBy_value" name="searchForm" property="resolveBy" scope="session"/>

<%@page import="server.essp.issue.issue.form.AfIssueSearch"%>
<%
  boolean filledByDis = false;
  boolean prinDis = false;
  boolean rosDis = false;
  if (request.getSession().getAttribute("searchForm") != null) {
    AfIssueSearch afS = (AfIssueSearch) request.getSession().getAttribute("searchForm");
    if (afS.getFillBy() != null && !afS.getFillBy().equals("")) {
      filledByDis = true;
    }
    if (afS.getPrincipal() != null && !afS.getPrincipal().equals("")) {
      prinDis = true;
    }
    if (afS.getResolveBy() != null && !afS.getResolveBy().equals("")) {
      rosDis = true;
    }
  }
%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" Issue Export "/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<style type="text/css">#input1_field {width:200;}</style>
<script language="javascript" src="<%=contextPath%>/issue/selectbox.js"></script>
  <%--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--%>
<script language="javascript" type="text/javascript">
var map = window.dialogArguments;
function valideFunc(){

       var fillDateBegin= document.issueListForm.fillDateBegin.value;
       var fillDateEnd = document.issueListForm.fillDateEnd.value;

       if( fillDateEnd!="" && fillDateBegin>fillDateEnd){
           alert("FilledDate begin date must less than end date!");
           return false;
       }
       var dueDateBegin= document.issueListForm.dueDateBegin.value;
       var dueDateEnd = document.issueListForm.dueDateEnd.value;
       if( dueDateEnd!="" && dueDateBegin>dueDateEnd){
           alert("DueDate begin date must less than end date!");
           return false;
       }else{
           //�˵���ڸ�Ϊģ̬�󣬲��ܹ����ø����ڵ�issueForm������������������¸�д
           //map���ڸ������ж��壬��Ϊ�����4��
           //By jun.zheng

           map.put("issueType",document.issueListForm.issueType.value);
           map.put("accountId",document.issueListForm.accountId.value);
           map.put("priority",document.issueListForm.priority.value);
           map.put("scope",document.issueListForm.scope.value);
           map.put("status",document.issueListForm.status.value);
           map.put("fillBy",document.issueListForm.fillBy.value);
           map.put("principal",document.issueListForm.principal.value);
           map.put("resolveBy",document.issueListForm.resolveBy.value);
           map.put("fillDateBegin",document.issueListForm.fillDateBegin.value);
           map.put("fillDateEnd",document.issueListForm.fillDateEnd.value);
           map.put("issueId",document.issueListForm.issueId.value);
           map.put("issueName",document.issueListForm.issueName.value);
           map.put("dueDateBegin",document.issueListForm.dueDateBegin.value);
           map.put("dueDateEnd",document.issueListForm.dueDateEnd.value);
           map.put("pageNo","1");
           map.put("addonRidInfo","");
           map.put("addonRid","");
           map.put("action","<%=contextPath%>/issue/issue/issueList.do");
           self.close();
       }
}


function openerRefresh(){
  opener.location.reload(true);
}

function closeFunc(){
    map.clear();
  self.close();
}

function onChangeIssueType(issueTypeValue) {
    dataForm.issueType.value=issueTypeValue;
    dataForm.submit();
}

function exportList(){
  var radioValue='';
  var url='';
  if (issueExportForm.radiobutton[0].checked) {
    radioValue='withoutDetail';
  }
  if(issueExportForm.radiobutton[1].checked){
    radioValue='withDetail';
  }
  url='<%=contextPath%>/issue/issue/issueExport.do?ReportType='+radioValue;
//  alert(radioValue);
//  window.open(url,"","");
issueExportForm.action=url;
issueExportForm.submit();
}

 function showHistoryValue(){
   onChangeIssueType('<%=default_issueType_value%>');
 }

</script>
  <%--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--%>
<style type="text/css">#input_field {width:100%}</style>
</head>
<body bgcolor="#ffffff" onLoad="showHistoryValue()">
  <%--************************************************************************--%>
<center>
  <font class="form_title">Issue Export</font>
  <br/>
  <html:form name="issueExportForm" action="" target="_parent">
    <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center" width="94%">
      <tr valign="middle">
        <td class="list_range" width="100" align="right" valign="top">Account</td>
        <td class="list_range" width="150" align="left" valign="top">
          <html:select name="accountId" beanName="webVo" styleId="input1_field" next="issueType">
            <logic:notPresent name="webVo" property="accountList">
              <option value="">--Please select account--</option>
            </logic:notPresent>
            <logic:present name="webVo" property="accountList">
              <html:optionsCollection name="webVo" property="accountList" styleClass=""/>
            </logic:present>
          </html:select>        </td>
        <td width="30">&nbsp</td>
        <td class="list_range" width="100" valign="top" align="right">IssueType</td>
        <td class="list_range" width="150" valign="top" align="left">
          <html:select name="issueType" beanName="webVo" styleId="input1_field" onchange="onChangeIssueType(this.value)" prev="accountList" next="status">
            <html:optionsCollection name="webVo" property="issueTypeList" styleClass=""/>
          </html:select>        </td>
      </tr>
      <tr valign="middle">
        <td class="list_range" width="100" align="right" valign="top">Status</td>
        <td class="list_range" width="150" align="left" valign="top">
          <html:select name="status" beanName="webVo" styleId="input1_field" prev="issueType" next="priority">
            <html:optionsCollection name="webVo" property="statusList" styleClass=""/>
          </html:select>        </td>
        <td width="30">        </td>
        <td class="list_range" width="100" valign="top" align="right">Priority</td>
        <td class="list_range" width="150" valign="top" align="left">
          <html:select name="priority" beanName="webVo" styleId="input1_field" next="scope">
            <html:optionsCollection name="webVo" property="priorityList" styleClass=""/>
          </html:select>        </td>
      </tr>
      <tr valign="middle">
        <td class="list_range" width="100" valign="top" align="right">Scope</td>
        <td class="list_range" width="150" valign="top" align="left">
          <html:select name="scope" beanName="webVo" styleId="input1_field" next="issueId" prev="priority">
            <html:optionsCollection name="webVo" property="scopeList" styleClass=""/>
          </html:select>        </td>
        <td width="30">&nbsp</td>
        <td class="list_range" width="100" valign="top" align="right">IssueID</td>
        <td class="list_range" width="150" valign="top" align="left">
          <html:text beanName="webVo" name="issueId" fieldtype="text" styleId="input1_field" next="issueName" prev="principal"/>        </td>
      </tr>
      <tr valign="middle">
        <td class="list_range" width="100" valign="top" align="right">Filled By</td>
        <td class="list_range" width="150" align="left" valign="top">
          <html:text beanName="webVo" name="fillBy" fieldtype="text" styleId="input1_field" next="principal" prev="" readonly="<%=filledByDis%>">
            <bean:write property="fillBy" name="searchForm" scope="session"/>
          </html:text>        </td>
        <td width="30">        </td>
        <td class="list_range" width="100" valign="top" align="right">Principal</td>
        <td valign="top" width="150" class="list_range" align="left">
          <html:text beanName="webVo" name="principal" fieldtype="text" styleId="input1_field" readonly="<%=prinDis%>">
            <bean:write property="principal" name="searchForm" scope="session"/>
          </html:text>        </td>
      </tr>
      <tr valign="middle">
        <td class="list_range" width="100" valign="top" align="right">Resolve By</td>
        <td valign="top" width="150" class="list_range" align="left">
          <html:text beanName="webVo" name="resolveBy" fieldtype="text" styleId="input1_field" readonly="<%=rosDis%>">
            <bean:write property="resolveBy" name="searchForm" scope="session"/>
          </html:text>        </td>
        <td width="30">&nbsp</td>
        <td class="list_range" width="100" valign="top" align="right">IssueName</td>
        <td valign="top" width="150" class="list_range" align="left">
          <html:text beanName="webVo" name="issueName" fieldtype="text" styleId="input1_field"/>        </td>
      </tr>
      <tr valign="middle">
        <td class="list_range" width="100" valign="top" align="right">FilledDate(Begin)</td>
        <td valign="top" width="150" class="list_range" align="left">
          <html:text beanName="webVo" name="fillDateBegin" fieldtype="dateyyyymmdd" styleId="input1_field" ondblclick="getDATE(this)"/>        </td>
        <td width="30">&nbsp</td>
        <td class="list_range" width="100" valign="top" align="right">FilledDate(End)</td>
        <td valign="top" width="150" class="list_range" align="left">
          <html:text beanName="webVo" name="fillDateEnd" fieldtype="dateyyyymmdd" styleId="input1_field" next="dueDateBegin" prev="fillDateBegin" ondblclick="getDATE(this)"/>        </td>
      </tr>
      <tr valign="middle">
        <td class="list_range" width="100" valign="top" align="right">DueDate(Begin)</td>
        <td valign="top" width="150" class="list_range" align="left">
          <html:text beanName="webVo" name="dueDateBegin" fieldtype="dateyyyymmdd" styleId="input1_field" next="dueDateEnd" prev="fillDateEnd" ondblclick="getDATE(this)"/>        </td>
        <td width="30">&nbsp;</td>
        <td class="list_range" width="100" valign="top" align="right">DueDate(End)</td>
        <td valign="top" width="150" class="list_range" align="left">
          <html:text beanName="webVo" name="dueDateEnd" fieldtype="dateyyyymmdd" styleId="input1_field" next="" prev="dueDateBegin" ondblclick="getDATE(this)"/>        </td>
      </tr>
      <tr valign="middle">
        <td valign="middle" class="list_range">Template Type        </td>
        <td colspan="4" >
		<font size="-1">
		<input name="radiobutton" type="radio" value="without" checked>
          Only last Title/Reply&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="radiobutton" value="with">
          All detail discuss </font></td>
      </tr>
      <tr valign="middle">
        <td colspan="5" align="right" valign="middle">
          <html:button name="ok" value=" Export " styleId="button" onclick="exportList();"/>
          <!--
            <input type="button" value="Reset" name="reset" class="button" onclick="window.location.reload();">
          -->
          <html:button name="close" value="Close" styleId="button" onclick="window.close();"/>        </td>
      </tr>
    </table>
    <br>
    <br>
  </html:form>
</center>
<iframe id="optionListFrm" name="optionListFrm" src="" width="0" height="0"></iframe>
  <%--************************************************************************--%>
<form name="dataForm" target="optionListFrm" action="<%=request.getContextPath()%>/issue/issueTypeChanged.do" method="POST">
<input type="hidden" name="issueType">
<input type="hidden" name="prioritySelectbox" value="document.issueExportForm.priority">
<input type="hidden" name="scopeSelectbox" value="document.issueExportForm.scope">
</form>
</body>
</html>

