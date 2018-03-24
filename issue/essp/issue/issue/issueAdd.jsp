<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="itf.hr.HrFactory"%>
<%@page import="c2s.essp.common.user.DtoUser,server.framework.taglib.util.ISelectOption"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="webVo" name="webVo" scope="request"></bean:define>
<%DtoUser user = (DtoUser) session.getAttribute(DtoUser.SESSION_USER);%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" Issue Add"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<style type="text/css">
  #input_field {
    width:100%;
    word-wrap:break-word;
    font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
    }
  #accountId {width:100%;word-wrap:break-word}
  #accountIdaccountId {width:100%;word-wrap:break-word}
  #input_text {width:170}
  #issueType {width:170}
  #issueTypeissueType {width:170}
  #priority {width:170}
  #scope {width:170}
  #scopescope {width:170}
  #prioritypriority {width:170}
  #phone {width:170}
  #fax {width:170}
  #email {width:170}
  #issueId {width:170}
  #issueName {width:170}
  #filleDate {width:170}
  #filleByCompany_Display {width:170}
</style>
<script language="JavaScript" src="<%=contextPath%>/js/humanAllocate.js"></script>
<script language="javascript" src="<%=contextPath%>/issue/selectbox.js"></script><script language="javascript" type="text/javascript">
var labels=null;
var values=null;
var filledType="";

function addFunc(){
    if (issueForm.accountId.value==""){
        alert("Please Fill in Account");
        document.issueForm.accountId.focus();
        return;
    }
    if (issueForm.issueType.value==""){
        alert("Please Fill in Issue Type");
        document.issueForm.issueType.focus();
        return;
    }
    if (issueForm.priority.value==""){
        alert("Please Fill in Priority");
        document.issueForm.priority.focus();
        return;
    }
    if (issueForm.scope.value==""){
        alert("Please Fill in Scope");
        document.issueForm.scope.focus();
        return;
    }
    if (issueForm.filleBy.value==""){
        alert("Please Fill in Filled By");
        document.issueForm.filleBy.focus();
        return;
    }

    if (issueForm.filleDate.value==""){
        alert("Please Fill in Filled Date");
        document.issueForm.filleDate.focus();
        return;
    }

    if (issueForm.issueId.value==""){
        alert("Please Fill in IssueId");
        document.issueForm.issueId.focus();
        return;
    }
    if (issueForm.issueName.value==""){
        alert("Please Fill in IssueName");
        document.issueForm.issueName.focus();
        return;
    }
    if (issueForm.principal.value==""){
        alert("Please Fill in Principal");
        document.issueForm.principal.focus();
        return;
    }
    if (issueForm.dueDate.value==""){
        alert("Please Fill in Due Date");
        document.issueForm.dueDate.focus();
        return;
    }
    if (issueForm.issueStatus.value==""){
        alert("Please Fill in Status");
        document.issueForm.issueStatus.focus();
        return;
    }
    issueForm.filleBy.value=getFilledBy();
    issueForm.submit();
}

function SaveSend(){

    if (issueForm.accountId.value==""){
        alert("Please Fill in Account");
        document.issueForm.accountId.focus();
        return;
    }
    if (issueForm.issueType.value==""){
        alert("Please Fill in Issue Type");
        document.issueForm.issueType.focus();
        return;
    }
    if (issueForm.priority.value==""){
        alert("Please Fill in Priority");
        document.issueForm.priority.focus();
        return;
    }
    if (issueForm.scope.value==""){
        alert("Please Fill in Scope");
        document.issueForm.scope.focus();
        return;
    }
    if (issueForm.filleBy.value==""){
        alert("Please Fill in Filled By");
        document.issueForm.filleBy.focus();
        return;
    }

    if (issueForm.filleDate.value==""){
        alert("Please Fill in Filled Date");
        document.issueForm.filleDate.focus();
        return;
    }

    if (issueForm.issueId.value==""){
        alert("Please Fill in IssueId");
        document.issueForm.issueId.focus();
        return;
    }
    if (issueForm.issueName.value==""){
        alert("Please Fill in IssueName");
        document.issueForm.issueName.focus();
        return;
    }
    if (issueForm.principal.value==""){
        alert("Please Fill in Principal");
        document.issueForm.principal.focus();
        return;
    }
    if (issueForm.dueDate.value==""){
        alert("Please Fill in Due Date");
        document.issueForm.dueDate.focus();
        return;
    }
    if (issueForm.issueStatus.value==""){
        alert("Please Fill in Status");
        document.issueForm.issueStatus.focus();
        return;
    }
    if(!confirm('Do you really save and send it?'))
    {  return ;}
    issueForm.filleBy.value=getFilledBy();
    issueForm.action="<%=contextPath%>/issue/issue/issueAdd.do?isMail=true"
    issueForm.submit();
}

function onChangeIssueType() {
    //alert("issueForm.issueType.value="+issueForm.issueType.value);
    var typeName=issueForm.issueType.value;
    dataForm.action="<%=contextPath%>/issue/optionList.do";
    dataForm.target="optionListFrm";
    dataForm.issueType.value=typeName;
    dataForm.submit();
    //optionListFrm.location="<%=contextPath%>/issue/optionList.do?issueType="+typeName+"&prioritySelectbox=document.issueForm.priority"+"&scopeSelectbox=document.issueForm.scope"+"&statusSelectbox=document.issueForm.issueStatus";

    onIssueIdCreate();
}

function onChangeDueDate() {
    var typeName=issueForm.issueType.value;
    var filleDate=issueForm.filleDate.value;
    var priority=issueForm.priority.value;
    //alert("typeName="+typeName+",filleDate="+filleDate+",priority="+priority);
    if(filleDate!="" && typeName!="" && priority!="" && typeName!="undefined" && priority!="undefined" ) {
        dataForm.action="<%=contextPath%>/issue/issue/issueDueDate.do";
        dataForm.target="optionListFrm";
        dataForm.typeName.value=typeName;
        dataForm.filleDate.value=filleDate;
        dataForm.priority.value=priority;
        dataForm.submit();

//        optionListFrm.location="<%=contextPath%>/issue/issue/issueDueDate.do?"+
//        "typeName="+typeName+
//        "&filleDate="+filleDate+
//        "&priority="+priority;
    }
}


function onChangeIssueDuplation(){
    var accountRid=issueForm.accountId.value;
    var typeName=issueForm.issueType.value;
    var statusName=issueForm.issueStatus.value;

    dataForm.action="<%=contextPath%>/issue/issue/issueDuplation.do";
    dataForm.target="optionListFrm4";
    dataForm.typeName.value=typeName;
    dataForm.statusName.value=statusName;
    dataForm.accountRid.value=accountRid;
    dataForm.rid.value="";
    dataForm.submit();

//    optionListFrm4.location="<%=contextPath%>/issue/issue/issueDuplation.do?"+
//                            "typeName="+typeName+
//                            "&statusName="+statusName+
//                            "&accountRid="+accountRid;
}

function onIssueIdCreate() {
    //å–å¾—å½“å‰é€‰ä¸­çš„account rid
    var accountRid=document.issueForm.accountId.value;
    //å–å¾—å½“å‰é€‰ä¸­çš„type name
    var typeName=document.issueForm.issueType.value;
    //æ£€æŸ¥typeNameå’ŒaccountRidæ˜¯å¦åˆæ³•
    if(typeName!=null && typeName!="" && accountRid!=null && accountRid!="") {
        dataForm.action="<%=contextPath%>/issue/issue/issueIdCreate.do";
        dataForm.target="optionListFrm2";
        dataForm.typeName.value=typeName;
        dataForm.accountRid.value=accountRid;
        dataForm.submit();
//        optionListFrm2.location="<%=contextPath%>/issue/issue/issueIdCreate.do?"+
//                            "typeName="+typeName+
//                            "&accountRid="+accountRid;
    }
}

function onChangeIssueId(){
    var checkIssueId;
    if(document.issueForm.accountId.value!="" && document.issueForm.issueId.value!="") {
        var issueIdUrl="<%=contextPath%>/issue/issue/issueId.do?"+"accountId="+document.issueForm.accountId.value+"&issueId="+document.issueForm.issueId.value;
        checkIssueId=window.showModalDialog(issueIdUrl,document.issueForm,"dialogWidth=0;dialogHeight=0");
        if(checkIssueId=="Error"){
            alert("Issue Id can't repeat");
            document.issueForm.issueId.focus();
        }
    }
}


function allocateHr(){
  var oldValue=document.issueForm.principal.value;
  var oldName=document.issueForm.principal_name.value;
  var oldScope=document.issueForm.principalScope.value;
  var acntRid=document.issueForm.accountId.value;
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
 var result = allocSingleInALL(param,acntRid,"","","true");
 document.issueForm.principal.value = param.values()[0].loginId;
 document.issueForm.principal_name.value = param.values()[0].name;
 document.issueForm.principal_name.title = param.values()[0].loginId+"/"+param.values()[0].name
 document.issueForm.principalScope.value = param.values()[0].type
}

function getPrincipal(){
    var accountRid=issueForm.accountId.value;
    if(accountRid!=""){
    optionListFrm3.location="<%=contextPath%>/issue/issue/issuePrincipal.do?"+
                            "accountRid="+accountRid;
    }
}

function gotoDuplation() {
    var option = "Top=200,Left=150,Height=428,Width=570,toolbar=no,scrollbars=no,status=no";
    window.open("<%=contextPath%>/issue/issue/issueGotoPre.do?rid="+issueForm.duplationIssue.value,"",option);
}

function onFilledByChanged() {
    dataForm.action="<%=contextPath%>/issue/filledByChanged.do";
    dataForm.target="optionListFrm5";
    dataForm.accountRid.value=issueForm.accountId.value;
    dataForm.filledBy.value=getFilledBy();
    dataForm.submit();
}

var testFlag=false;
function onScopeChanged() {
//    dataForm.action="<%=contextPath%>/issue/scopeChanged.do";
//    dataForm.target="optionListFrm5";
//    dataForm.typeName.value=issueForm.issueType.value;
//    dataForm.scope.value=issueForm.scope.value;
//    dataForm.submit();
      if(issueForm.scope.value=="Company" || issueForm.scope.value=="") {
          switchFilledBy(true);
      } else {
          switchFilledBy(false);
          getPrincipal();
      }
      //testFlag=!testFlag;
}


function initFilledBy() {
    labels=null;
    values=null;
    <logic:notEmpty name="webVo" property="filleByList">
      labels=new Array();
      values=new Array();
      <logic:iterate id="optionItem" name="webVo" property="filleByList" indexId="currentIndex">
        labels[<%=currentIndex%>]="<bean:write name="optionItem" property="label"/>";
        values[<%=currentIndex%>]="<bean:write name="optionItem" property="value"/>";
      </logic:iterate>
    </logic:notEmpty>

    <%
    if(user.getUserType().equals(DtoUser.USER_TYPE_CUST)) {
        %>
        switchFilledBy(false);
        <%
    } else {
    %>
        switchFilledBy(true);
    <% } %>
}

function getFilledBy() {
    if(filledType=="Company") {
        issueForm.filleByScope.value="EMP";
        return issueForm.filleBy.value;
    } else {
        if(issueForm.filleBy.value != issueForm.filleByfilleBy.value){
          issueForm.filleByScope.value="CUST"
        } else {
          issueForm.filleByScope.value="EMP";
        }
        return issueForm.filleByfilleBy.value;
    }
}

function switchFilledBy(isCompany) {
    if(isCompany) {
        filledType="Company";
        var filleByHtmlText='<input type="text" name="filleByfilleBy" title="<%=user.getUserLoginId()+"/"+user.getUserName()%>" type="text" class=" Xtext Nreq Display" next="phone" prev="scope" msg="" req="true" sreq="false" fieldtype="text" fielderrorflg="" fieldmsg="" fmt="" defaultvalue="true" readonly="readonly" id="input_text" onchange="onFilledByChanged()" onblur="doBlur();" onfocus="doFocus();" readonly="readonly" value="<%=user.getUserName()%>">';
        issueForm.filleBy.value="<%=user.getUserLoginId()%>";
        //filleByHtmlText=filleByHtmlText+"<input type='hidden' name='filleBy' value='<%=user.getUserID()%>'>";
        filledByColumn.innerHTML=filleByHtmlText;
    } else {
        filledType="Customer";
        var filleByHtmlText='<select name="filleByfilleBy" next="phone" prev="scope" msg="" req="true" sreq="false" size="" class="Selectbox  Req" id="input_text" onchange="onFilledByChanged()" onblur="doBlur();" onfocus="doFocus();">';
        filleByHtmlText=filleByHtmlText+"</select>";
        filledByColumn.innerHTML=filleByHtmlText;
        if(labels!=null) {
            updateOptionList("document.issueForm.filleByfilleBy",labels,values);
            try {
                setSelectedValue("document.issueForm.filleByfilleBy",'<%=user.getUserLoginId()%>');
            } catch(e) {
                //alertError(e);
            }
        }

    }

}
function onBodyLoad(){
     document.issueForm.issueName.focus();
}
</script></head>
<body bgcolor="#ffffff" onload="onBodyLoad();onChangeIssueType();initFilledBy();onChangeDueDate();getPrincipal();">
<%--************************************************************************--%>
<center>
  <font class="form_title">Issue Management</font>
  <br/>
  <html:form name="issueForm" action="<%=contextPath+"/issue/issue/issueAdd.do"%>" target="_parent" enctype="multipart/form-data">
    <html:hidden name="filleBy" beanName="webVo"/>
    <html:hidden name="phone" beanName="webVo"/>
    <html:hidden name="fax" beanName="webVo"/>
    <html:hidden name="email" beanName="webVo"/>
    <html:hidden name="filleByScope" beanName="webVo"/>
    <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center" width="550">
      <html:hidden name="rid" value=""/>
      <tr>
        <td valign="bottom" height="5" colspan="5" class="orarowheader">General</td>
      </tr>
      <tr valign="middle">
        <td class="list_range" width="80" align="right" valign="top">Account</td>
        <td class="list_range" align="left" valign="top" width="170">
          <bean:define id="accountReadonly" name="webVo" property="accountReadonly"/>
        <%if (!Boolean.valueOf((String) accountReadonly).booleanValue()) {        %>
          <html:select name="accountId" styleId="accountId" beanName="webVo" req="true" next="issueType" prev="" onchange="onIssueIdCreate();getPrincipal();onChangeIssueDuplation()">
            <logic:present name="webVo">
              <html:optionsCollection name="webVo" property="accountList"/>
            </logic:present>
          </html:select>
        <%} else {        %>
          <bean:define id="optionsList1" name="webVo" property="accountList"/>
          <bean:define id="optionValue1" name="webVo" property="accountId"/>
        <%
          String optionLabel1 = "";
          if (optionsList1 != null && optionValue1 != null) {
            for (int i = 0; i < ((java.util.List) optionsList1).size(); i++) {
              ISelectOption option = (ISelectOption) ((java.util.List) optionsList1).get(i);
              if (option.getValue().equals(optionValue1)) {
                optionLabel1 = option.getLabel();
                break;
              }
            }
          }
        %>
          <html:text name="accountIdaccountId" beanName="" value="<%=optionLabel1%>" fieldtype="text" styleId="accountIdaccountId" next="issueType" prev="" readonly="true" onblur="onIssueIdCreate();getPrincipal();onChangeIssueDuplation()"/>
          <input type="hidden" name="accountId" value="<%=(String)optionValue1%>">
        <%}        %>
        </td>
        <td>        </td>
        <td class="list_range" width="200" valign="top" align="right">Issue Type</td>
        <td class="list_range" width="130" align="left" valign="top">
          <bean:define id="issueTypeReadonly" name="webVo" property="issueTypeReadonly"/>
        <%if (!Boolean.valueOf((String) issueTypeReadonly).booleanValue()) {        %>
          <html:select name="issueType" styleId="issueType" beanName="webVo" req="true" next="priority" prev="accountId" onchange="onChangeIssueType()">
            <html:optionsCollection name="webVo" property="issueTypeList"/>
          </html:select>
        <%} else {        %>
          <bean:define id="optionsList2" name="webVo" property="issueTypeList"/>
          <bean:define id="optionValue2" name="webVo" property="issueType"/>
        <%
          String optionLabel2 = "";
          if (optionsList2 != null && optionValue2 != null) {
            for (int i = 0; i < ((java.util.List) optionsList2).size(); i++) {
              ISelectOption option = (ISelectOption) ((java.util.List) optionsList2).get(i);
              if (option.getValue().equals(optionValue2)) {
                optionLabel2 = option.getLabel();
                break;
              }
            }
          }
        %>
          <html:text name="issueTypeissueType" beanName="" value="<%=optionLabel2%>" fieldtype="text" styleId="issueTypeissueType" next="priority" prev="accountId" readonly="true" onblur="onChangeIssueType()"/>
          <input type="hidden" name="issueType" value="<%=(String)optionValue2%>">
        <%}        %>
        </td>

      </tr>
      <tr>
        <td class="list_range" valign="top" align="right">Filled Date</td>
        <td class="list_range" align="left" valign="top">
          <html:text styleId="filleDate" fieldtype="dateyyyymmdd" name="filleDate" beanName="webVo" next="fax" onblur="onChangeDueDate()" ondblclick="getDATE(this)" prev="phone" req="true"/>

        <td width="40">        </td>
        <td class="list_range" valign="top" width="110" align="right">Priority</td>
        <td class="list_range" align="left" valign="top">
          <bean:define id="priorityReadonly" name="webVo" property="priorityReadonly"/>
        <%if (!Boolean.valueOf((String) issueTypeReadonly).booleanValue()) {        %>
          <html:select name="priority" styleId="input_text" beanName="webVo" readonly="<%=Boolean.valueOf((String)priorityReadonly).booleanValue()%>" req="true" onchange="onChangeDueDate()" next="scope" prev="issueType">
            <logic:notEmpty name="webVo" property="priorityList">
              <html:optionsCollection name="webVo" property="priorityList"/>
            </logic:notEmpty>
            <logic:empty name="webVo" property="priorityList">
              <option value="">please select</option>
            </logic:empty>
          </html:select>
        <%} else {        %>
          <bean:define id="optionsList3" name="webVo" property="priorityList"/>
          <bean:define id="optionValue3" name="webVo" property="priority"/>
        <%
          String optionLabel3 = "";
          if (optionsList3 != null && optionValue3 != null) {
            for (int i = 0; i < ((java.util.List) optionsList3).size(); i++) {
              ISelectOption option = (ISelectOption) ((java.util.List) optionsList3).get(i);
              if (option.getValue().equals(optionValue3)) {
                optionLabel3 = option.getLabel();
                break;
              }
            }
          }
        %>
          <html:text name="prioritypriority" beanName="" value="<%=optionLabel3%>" fieldtype="text" styleId="prioritypriority" next="scope" prev="issueType" readonly="true" onblur="onChangeDueDate()"/>
          <input type="hidden" name="priority" value="<%=(String)optionValue3%>">
        <%}        %>
        </td>
      </tr>
      <tr>
        <td class="list_range" width="80" valign="top" align="right">Scope</td>
        <td class="list_range" align="left" valign="top">
          <bean:define id="scopeReadonly" name="webVo" property="scopeReadonly"/>
        <%if (!Boolean.valueOf((String) scopeReadonly).booleanValue()) {        %>
          <html:select name="scope" styleId="input_text" beanName="webVo" readonly="<%=Boolean.valueOf((String)scopeReadonly).booleanValue()%>" next="filleByfilleBy" prev="priority" req="true" onchange="onScopeChanged()">
            <logic:notEmpty name="webVo" property="scopeList">
              <html:optionsCollection name="webVo" property="scopeList"/>
            </logic:notEmpty>
            <logic:empty name="webVo" property="scopeList">
              <option value="">please select</option>
            </logic:empty>
          </html:select>
        <%} else {        %>
          <bean:define id="optionsList4" name="webVo" property="scopeList"/>
          <bean:define id="optionValue4" name="webVo" property="scope"/>
        <%
          String optionLabel4 = "";
          if (optionsList4 != null && optionValue4 != null) {
            for (int i = 0; i < ((java.util.List) optionsList4).size(); i++) {
              ISelectOption option = (ISelectOption) ((java.util.List) optionsList4).get(i);
              if (option.getValue().equals(optionValue4)) {
                optionLabel4 = option.getLabel();
                break;
              }
            }
          }
        %>
          <html:text name="scopescope" beanName="" value="<%=optionLabel4%>" fieldtype="text" styleId="scopescope" next="filleByfilleBy" prev="priority" readonly="true" onblur="onScopeChanged()"/>
          <input type="hidden" name="scope" value="<%=(String)optionValue4%>">
        <%}        %>
        </td>
        <td>        </td>
        <td class="list_range" valign="top" align="right">Filled By</td>
        <td class="list_range" align="left" valign="top" id="filledByColumn">        </td>
      </tr>
      <!--
        <tr>
        <td class="list_range" width="80" valign="top"  align="right">Phone</td>
        <td class="list_range" align="left" valign="top">
        <html:text styleId="phone" fieldtype="text" name="phone"
        beanName="webVo" maxlength="20" prev="filleByfilleBy" next="filleDate" req="false"/>
        <td></td>
        </tr>
      -->
      <!--
        <tr>
        <td class="list_range" width="80" valign="top"  align="right">Fax</td>
        <td class="list_range" align="left" valign="top">
        <html:text styleId="fax" fieldtype="text" name="fax"
        beanName="webVo" value="" next="email" prev="filleDate"  maxlength="20" req="false"/>
        <td></td>
        <td class="list_range" valign="top" align="right">Email</td>
        <td class="list_range"  align="left" valign="top">
        <html:text styleId="email" fieldtype="text" name="email"
        beanName="webVo" value="" next="issueId" maxlength="100" prev="phone" req="false"/>
        </tr>
      -->
      <tr>
        <td class="list_range" width="80" valign="top" align="right">Issue Id</td>
        <td class="list_range" align="left" valign="top">
          <html:text
            styleId="issueId"
            fieldtype="text"
            maxlength="500"
            name="issueId"
            beanName="webVo"
            value=""
            next="issueName"
            onblur="onChangeIssueId()"
            prev="scope"
            req="true"/>

        <td>        </td>
        <td class="list_range" valign="top" align="right">Issue Name</td>
        <td class="list_range" align="left" valign="top">
          <html:text styleId="issueName" fieldtype="text" name="issueName" beanName="webVo" value="" maxlength="500" next="description" prev="issueId" req="true"/>

      </tr>
      <tr>
        <td class="list_range" valign="top" height="40">Description</td>
        <td colspan="4" valign="top" class="list_range">
          <html:textarea styleId="input_field" name="description" beanName="webVo" rows="3" maxlength="1000" next="attachment" prev="issueName" cols="44"/>
        </td>
      </tr>
      <tr>
        <td class="list_range" valign="top" align="right">Attachment</td>
        <td class="list_range" align="left" valign="top">
          <logic:notEmpty name="webVo" property="attachment">
            <bean:define id="downloadUrl" name="webVo" property="attachment"/>
            <html:file name="attachment" styleId="input_field" imageSrc="<%=contextPath+"/image/download.gif"%>" imageHref="<%=String.valueOf(downloadUrl)%>"/>
          </logic:notEmpty>
          <logic:empty name="webVo" property="attachment">
            <html:file name="attachment" styleId="input_field"/>
          </logic:empty>

        <td>        </td>
        <td class="list_range" valign="top" align="right">Desc</td>
        <td class="list_range" align="left" valign="top">
          <html:text styleId="input_field" fieldtype="text" name="attachmentdesc" beanName="webVo" value="" maxlength="250" next="principal" prev="attachment"/>

      </tr>
      <tr>
        <td valign="bottom" height="5" colspan="5" class="orarowheader">&nbsp;</td>
      </tr>
      <tr>
        <td class="list_range" valign="top" align="right">Principal</td>
        <bean:define id="principal_id" name="webVo" property="principal" />
       <%String principalName=HrFactory.create().getName((String)principal_id);%>
        <td class="list_range" align="left" valign="top" title="<%=principal_id+"/"+principalName%>">
          <input type="hidden" name="principal" value="<%=principal_id%>">
          <input type="hidden" name="principalScope" value="EMP">
          <html:text
            name="principal_name"
            beanName="webVo"
            fieldtype="text"
            styleId="input_field"
            next="dueDate"
            prev="attachmentdesc"
            req="true"
            value="<%=principalName%>"
	    readonly="true"
            imageSrc="<%=user.getUserType().equals(DtoUser.USER_TYPE_EMPLOYEE)?contextPath+"/image/humanAllocate.gif":""%>"
            imageWidth="20"
            imageOnclick="allocateHr()"/>
        </td>
        <td>        </td>
        <td class="list_range" valign="top" align="right">Due Date</td>
        <td class="list_range" align="left" valign="top">
          <html:text styleId="input_field" fieldtype="dateyyyymmdd" name="dueDate" ondblclick="getDATE(this)" beanName="webVo" value="" next="issueStatus" prev="Principal" req="true"/>

      </tr>
      <tr>
        <td class="list_range" valign="top" align="right">Status</td>
        <td class="list_range" align="left" valign="top">
          <bean:define id="issueStatusReadonly" name="webVo" property="issueStatusReadonly"/>
          <html:select name="issueStatus" styleId="input_field" beanName="webVo" readonly="<%=Boolean.valueOf((String)issueStatusReadonly).booleanValue()%>" next="issueDuplation" prev="issueDueDate" req="true" onchange="onChangeDueDate(this.value);onChangeIssueDuplation()">
            <logic:notEmpty name="webVo" property="statusList">
              <html:optionsCollection name="webVo" property="statusList"/>
            </logic:notEmpty>
            <logic:empty name="webVo" property="statusList">
              <option value="">please select</option>
            </logic:empty>
          </html:select>

        <td>        </td>
        <td class="list_range" valign="top" align="right">Duplation Issue</td>
        <td class="list_range" align="left" valign="top">
          <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
              <td width="142" valign="middle">
                <html:select name="duplationIssue" styleId="input_field" beanName="webVo" next="" prev="issueStatus" req="true" disabled="true">
                  <option value="">please select</option>
                </html:select>
              </td>
              <td>
                <html:button styleId="btnOK" name="btnOK" value="goto" onclick="gotoDuplation()" disabled="true"/>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td colspan="5" align="right" height="40" valign="middle">
          <html:button name="ok" value=" Save&Send" styleId="button" onclick="SaveSend();"/>
          <html:button name="ok" value=" Save " styleId="button" onclick="addFunc();"/>
          <input type="reset" value="Reset" name="reset" class="button">
          <html:button name="close" value="Close" styleId="button" onclick="window.close();"/>
        </td>
      </tr>
    </table>
    <br>
    <br>
  </html:form>
</center>
<%--************************************************************************--%>
<iframe id="optionListFrm" name="optionListFrm" src="" width="0" height="0"></iframe>
<iframe id="optionListFrm2" name="optionListFrm2" src="" width="0" height="0"></iframe>
<iframe id="optionListFrm3" name="optionListFrm3" src="" width="0" height="0"></iframe>
<iframe id="optionListFrm4" name="optionListFrm4" src="" width="0" height="0"></iframe>
<iframe id="optionListFrm5" name="optionListFrm5" src="" width="0" height="0"></iframe>
<form name="dataForm" action="" method="POST">
<input type="hidden" name="issueType">
<input type="hidden" name="typeName">
<input type="hidden" name="filledBy">
<input type="hidden" name="filledByScope">
<input type="hidden" name="filleDate">
<input type="hidden" name="priority">
<input type="hidden" name="scope">
<input type="hidden" name="statusName">
<input type="hidden" name="accountRid">
<input type="hidden" name="rid">
<input type="hidden" name="prioritySelectbox" value="document.issueForm.priority">
<input type="hidden" name="scopeSelectbox" value="document.issueForm.scope">
<input type="hidden" name="statusSelectbox" value="document.issueForm.issueStatus">
</form>
</body>
</html>
