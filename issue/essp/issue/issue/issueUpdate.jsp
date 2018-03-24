<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="itf.hr.HrFactory"%>
<%@page import="c2s.essp.common.user.DtoUser,server.essp.issue.issue.viewbean.VbIssue,server.essp.issue.common.constant.Status"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="issueStatusBelongto" name="webVo" property="issueStatusBelongto"/>
<bean:define id="filleByFlag" name="webVo" property="filleByFlag"/>
<bean:define id="principalFlag" name="webVo" property="principalFlag"/>
<bean:define id="pmFlag" name="webVo" property="pmFlag"/>

<%
boolean readonlyFlag=true;

if(pmFlag.equals("true") || filleByFlag.equals("true") || principalFlag.equals("true")) {
    readonlyFlag=false;
}

DtoUser user=(DtoUser)session.getAttribute(DtoUser.SESSION_USER);
%>
<html>
<head>

<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" Issue Update"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<title>issueUpdate</title>
<script language="JavaScript" src="<%=contextPath%>/js/humanAllocate.js"></script>
<script language="javascript" src="<%=contextPath%>/issue/selectbox.js"></script>

<style type="text/css">
    #input_field {width:160}
    .TextAreaStyle {width:100%;FONT-FAMILY: Arial, Helvetica, sans-serif;FONT-SIZE: 12px;}
    #input_text {width:160}
    #input_text1{width:100%}
    #input_text2 {width:100}
    #input_field1{width:100%}
#btnOK {
	BORDER-RIGHT: #104a7b 1px solid;
    BORDER-TOP: #afc4d5 1px solid;
    FONT-SIZE: 11px;
    BACKGROUND: #d6e7ef;
    BORDER-LEFT: #afc4d5 1px solid;
    CURSOR: hand;
    COLOR: #000066;
    BORDER-BOTTOM: #104a7b 1px solid;
    FONT-FAMILY: Arial, Helvetica, sans-serif;
    HEIGHT: 20px;
    font-weight : bold;
    TEXT-DECORATION: none;
    vertical-align: middle;

}
#description {
width:100%;

}
</style>
<script language="javascript" type="text/javascript">
function onChangeIssueType(issueTypeValue) {
    dataForm.action="<%=contextPath%>/issue/optionList.do";
    dataForm.target="optionListFrm";
    dataForm.issueType.value=issueTypeValue;
    dataForm.submit();
//    optionListFrm.location="<%=contextPath%>/issue/optionList.do?issueType="+issueTypeValue+
//                      "&prioritySelectbox=document.issueUpdate.priority"+
//                      "&scopeSelectbox=document.issueUpdate.scope"+
//                      "&statusSelectbox=document.issueUpdate.issueStatus";
}

function onChangeDueDate() {
    var typeName=issueUpdate.issueType.value;
    var filleDate=issueUpdate.filleDate.value;
    var priority=issueUpdate.priority.value;
    dataForm.action="<%=contextPath%>/issue/issue/issueDueDate.do";
    dataForm.target="optionListFrm";
    dataForm.typeName.value=typeName;
    dataForm.filleDate.value=filleDate;
    dataForm.priority.value=priority;
    dataForm.submit();
//    optionListFrm.location="<%=contextPath%>/issue/issue/issueDueDate.do?"+
//                            "typeName="+typeName+
//                            "&filleDate="+filleDate+
//                            "&priority="+priority;
}

function onChangeIssueDuplation(){
    var rid=issueUpdate.rid.value;
    var accountRid=issueUpdate.accountId.value;
    var typeName=issueUpdate.issueType.value;
    var statusName=issueUpdate.issueStatus.value;
    dataForm.action="<%=contextPath%>/issue/issue/issueDuplation.do";
    dataForm.target="optionListFrm2";
    dataForm.typeName.value=typeName;
    dataForm.statusName.value=statusName;
    dataForm.accountRid.value=accountRid;
    dataForm.rid.value=rid;
    dataForm.submit();
//    optionListFrm2.location="<%=contextPath%>/issue/issue/issueDuplation.do?"+
//                            "typeName="+typeName+
//                            "&statusName="+statusName+
//                            "&accountRid="+accountRid+
//                            "&rid="+rid;
}

function allocateHr(){
  var oldValue=document.issueUpdate.principal.value;
  var oldScope = document.issueUpdate.principalScope.value;
  var acntRid=document.issueUpdate.accountId.value;
  var oldName=document.issueUpdate.principal_name.value;
  sType="single";
  //sType="multi";
  var result=oldValue;
  if(acntRid==null || acntRid=="") {
      alert("Please select a account");
      return;
  }
 // var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type="+sType+"&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr=" , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
// var result=allocSingleInJsp(oldValue,acntRid,'');
 var param = new HashMap();
 var oldUser = new allocUser(oldValue,oldName,"",oldScope);
 param.put(oldValue,oldUser);
 var result = allocSingleInALL(param,acntRid,"","","true");
 document.issueUpdate.principal.value=param.values()[0].loginId;
 document.issueUpdate.principal_name.value = param.values()[0].name;
 document.issueUpdate.principal_name.title = param.values()[0].loginId+"/"+param.values()[0].name
 document.issueUpdate.principalScope.value = param.values()[0].type
}

function onSaveData(param){
    <%
    if(readonlyFlag) {
    %>
    return;
    <%
    }
    %>

    if (issueUpdate.issueType.value==""){
        alert("Please Fill in Issue Type");
        return;
    }
    if (issueUpdate.priority.value==""){
        alert("Please Fill in Priority");
        return;
    }

    if (issueUpdate.scope.value==""){
        alert("Please Fill in scope");
        return;
    }
    if (issueUpdate.issueId.value==""){
        alert("Please Fill in IssueId");
        return;
    }
    if (issueUpdate.issueName.value==""){
        alert("Please Fill in issueName");
        return;
    }
    if (issueUpdate.filleDate.value==""){
        alert("Please Fill in Filled Date");
        return;
    }
    if (issueUpdate.filleBy.value==""){
        alert("Please Fill in Filled By");
        return;
    }
    if (issueUpdate.principal.value==""){
        alert("Please Fill in Principal");
        return;
    }
    if (issueUpdate.dueDate.value==""){
        alert("Please Fill in Due Date");
        return;
    }
    if (issueUpdate.issueStatus.value==""){
        alert("Please Fill in Due Status");
        return;
    }

    if ( issueUpdate.issueStatus.value=="Duplation"){
        if ( issueUpdate.duplationIssue.value=="" ){
          alert("Please Fill in Duplation");
          return;
        }
    }

    issueUpdate.submit();


}
function onSaveAndSendData(){

    <%
    if(readonlyFlag) {
    %>
    return;
    <%
    }
    %>
  if(!confirm('Do you really save and send it?'))
  {  return ;}

    if (issueUpdate.issueType.value==""){
        alert("Please Fill in Issue Type");
        return;
    }
    if (issueUpdate.priority.value==""){
        alert("Please Fill in Priority");
        return;
    }

    if (issueUpdate.scope.value==""){
        alert("Please Fill in scope");
        return;
    }
    if (issueUpdate.issueId.value==""){
        alert("Please Fill in IssueId");
        return;
    }
    if (issueUpdate.issueName.value==""){
        alert("Please Fill in issueName");
        return;
    }
    if (issueUpdate.filleDate.value==""){
        alert("Please Fill in Filled Date");
        return;
    }
    if (issueUpdate.filleBy.value==""){
        alert("Please Fill in Filled By");
        return;
    }
    if (issueUpdate.principal.value==""){
        alert("Please Fill in Principal");
        return;
    }
    if (issueUpdate.dueDate.value==""){
        alert("Please Fill in Due Date");
        return;
    }
    if (issueUpdate.issueStatus.value==""){
        alert("Please Fill in Due Status");
        return;
    }

    if ( issueUpdate.issueStatus.value=="Duplation"){
        if ( issueUpdate.duplationIssue.value=="" ){
          alert("Please Fill in Duplation");
          return;
        }
    }
    issueUpdate.action="<%=contextPath%>/issue/issue/issueUpdate.do?isMail=true";
    issueUpdate.submit();
}

function checkDelete() {



    <%-- check status --%>
    <%
    if(Status.RECEIVED.equals(issueStatusBelongto)) {
    %>
    <%
    } else {
        %>
        alert("Can't delete processed issue !");
        return false;
    <%
    }
    %>

    <%-- check principal & pm --%>
    <%
    if(principalFlag.equals("true") || pmFlag.equals("true") || filleByFlag.equals("true")) {
    %>
    <%
    } else {
        %>
        alert("User not authorized!!!");
        return false;
    <%
    }
    %>
    return true;
}

function checkDelete() {



    <%-- check status --%>
    <%
    if(Status.RECEIVED.equals(issueStatusBelongto)) {
    %>
    <%
    } else {
        %>
        alert("Can't delete processed issue !");
        return false;
    <%
    }
    %>

    <%-- check principal & pm --%>
    <%
    if(principalFlag.equals("true") || pmFlag.equals("true") || filleByFlag.equals("true")) {
    %>
    <%
    } else {
        %>
        alert("User not authorized!!!");
        return false;
    <%
    }
    %>
    return true;
}

function gotoDuplation() {
    if(issueUpdate.duplationIssue.value==""){
      alert("please select a item!");
    }else{
      var option = "Top=200,Left=150,Height=428,Width=570,toolbar=no,scrollbars=no,status=no";
      var issueRid=issueUpdate.duplationIssue.value;
      window.open("<%=contextPath%>/issue/issue/issueGotoPre.do?rid="+issueRid,"",option);
    }
}
function onFilledByChanged() {
    dataForm.action="<%=contextPath%>/issue/filledByChanged.do";
    dataForm.target="optionListFrm3";
    dataForm.accountRid.value=issueUpdate.accountId.value;
    dataForm.filledBy.value=issueUpdate.filleBy.value;
    dataForm.submit();
}

function onStatusChange() {
  var status=document.getElementById('issueStatus');
  var close1=document.getElementById('closeTR1');
  var close2=document.getElementById('closeTR2');
  var title = status.value;
  if(title=='Closed'){
	close1.style.visibility="visible";
    close2.style.visibility="visible";
  }
  else{
  	close1.style.visibility="hidden";
    close2.style.visibility="hidden";
  }
}

function refreshOtherCard(){
  //æ­¤æ–¹æ³•ç”¨äºŽåˆ·æ–°å…¶å®ƒå¡ç‰‡é¡µé¢ï¼Œç”¨äºŽæƒé™çš„æ¸…æ™°æ˜¾ç¤º
  <%if(!user.getUserType().equals(DtoUser.USER_TYPE_CUST)){%>
  var parentWin = window.parent;
  parentWin.issueResolution.location='<%=request.getContextPath()%>/issue/issue/issueResolutionPre.do?rid=<bean:write property="rid" name="webVo" />';
  parentWin.issueConclusion.location='<%=request.getContextPath()%>/issue/issue/conclusion/issueConclusionPre.do?rid=<bean:write property="rid" name="webVo" />';
  <%}%>
}

</script>
</head>
<body style="overflow-x:auto;overflow-y:hidden;padding-right:10px" onLoad="onStatusChange();refreshOtherCard();">
<html:form name="issueUpdate" id="issueUpdate" method="post" action="<%=contextPath+"/issue/issue/issueUpdate.do"%>" enctype="multipart/form-data">
  <html:hidden name="rid" beanName="webVo"/>
   <html:hidden name="accountId" beanName="webVo"/>
  <TABLE id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0" style="padding-left:8px">
    <TR>
      <TD class="list_range" width="80">Issue Id</TD>
      <TD class="list_range" width="160">
        <html:text styleId="input_field" fieldtype="text" name="issueId" beanName="webVo" maxlength="50" value="" next="issueName" prev="scope" req="true" readonly="true" />
      </TD>

      <td width="2"></td>
      <TD class="list_range" width="100">Issue Title</TD>
      <TD class="list_range"  colspan="4">
        <html:text styleId="input_text1" fieldtype="text" name="issueName" beanName="webVo" value="" maxlength="500" next="filleDate" prev="issueId" req="true" readonly="<%=readonlyFlag%>"/>      </TD>
    </TR>
     <TR>
      <TD class="list_range" width="80">Issue Type</TD>
      <TD class="list_range" width="160">
        <html:select name="issueType" styleId="input_text" beanName="webVo" next="priority" prev="" req="true" onchange="onChangeIssueType(this.value)" readonly="true">
          <logic:present name="webVo">
            <html:optionsCollection name="webVo" property="issueTypeList"/>
          </logic:present>
        </html:select>
      </TD>
      <td width="2"></td>
      <TD class="list_range" width="100">Priority</TD>
      <TD class="list_range" width="10">
        <html:select name="priority" styleId="input_text2" beanName="webVo" disabled="false" onblur="onChangeDueDate()" next="scope" prev="issueType" req="true" readonly="<%=readonlyFlag%>">
          <html:optionsCollection name="webVo" property="priorityList"/>
        </html:select>
      </TD>
       <td width="1"></td>
      <td class="list_range" width="73">Scope</td>
       <TD class="list_range">
        <html:select name="scope" styleId="input_text1" beanName="webVo" disabled="false" next="issueId" prev="priority" req="true" readonly="<%=readonlyFlag%>" >
           <html:optionsCollection name="webVo" property="scopeList"/>
        </html:select>      </td>
    </TR>
    <TR>
      <TD width="80" class="list_range">Filled Date</TD>
      <td class="list_range" width="160">
        <html:text styleId="input_text" fieldtype="dateyyyymmdd"
             name="filleDate" beanName="webVo"
             ondblclick="getDATE(this)"
             onblur="onChangeDueDate()" value="" next="phone" prev="issueName" req="true" readonly="<%=readonlyFlag%>"/>
      </td>
      <td width="2" class="list_range">      </td>
      <TD width="100" class="list_range"> Filled By </TD>
      <bean:define id="filleByScope" name="webVo" property="filleByScope" />
        <bean:define id="filleBy" name="webVo" property="filleBy" />
        <input type="hidden" name="filleBy" value="<%=filleBy%>">
        <%
           String filleByName = "";
           if("EMP".equals(filleByScope)) {
             filleByName = HrFactory.create().getName((String)filleBy);
           } else {
             filleByName = HrFactory.create().getCustomerName((String)filleBy);
           }
        %>
      <TD class="list_range" width="10" title="<%=filleBy+"/"+filleByName%>">
        <html:text name="filleByName"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_text2"
                      next="issueStatus"
                      prev="dueDate"
                      req="true"
                      readonly="true"
                      value="<%=filleByName%>"
                      ></html:text>
      </TD>

      <td width="1" class="list_range">      </td>
	  <td width="73"></td>
	  <td></td>
    </TR>
    <TR>
      <TD width="80" class="list_range">Issue Desc</TD>
      <TD class="list_range" colspan="7">
        <html:textarea name="description" beanName="webVo" rows="3" styleId="description" maxlength="1000" styleClass="TextAreaStyle" readonly="<%=readonlyFlag%>"/>
      </TD>
    </TR>

    <TR>
      <TD width="80" class="list_range">Attachment</TD>
      <TD class="list_range" width="160">
        <logic:notEmpty name="webVo" property="attachment">
            <bean:define id="downloadUrl" name="webVo" property="attachment"/>
            <html:file name="attachment" styleId="input_field"
                imageSrc="<%=contextPath+"/image/download.gif"%>"
                imageHref="<%=String.valueOf(downloadUrl)%>" imageStyle="border:0" imageWidth="18" imageTooltip="" readonly="<%=readonlyFlag%>"/>
        </logic:notEmpty>
        <logic:empty name="webVo" property="attachment">
            <html:file name="attachment" styleId="input_field" readonly="<%=readonlyFlag%>"/>
        </logic:empty>
      </TD>
      <td width="2"></td>
      <TD width="100" class="list_range">Desc</TD>
      <TD class="list_range" width="10">
        <html:text styleId="input_text2" fieldtype="text" name="attachmentdesc" beanName="webVo" maxlength="250" value="" next="dueDate" prev="attachment" readonly="<%=readonlyFlag%>"/>
      </TD>
      <td width="1"></td>
      <td width="73" class="list_range">Due Date</td>
      <td class="list_range">
        <html:text styleId="input_text1" fieldtype="dateyyyymmdd" name="dueDate"
             beanName="webVo" value="" next="principal"
             prev="attachmentdesc" req="true" readonly="<%=readonlyFlag%>" ondblclick="getDATE(this)"/>      </td>
    </TR>

    <TR >
      <TD width="80" class="list_range">Principal</TD>
      <bean:define id="principal_id" name="webVo" property="principal" />
      <bean:define id="principal_scope" name="webVo" property="principalScope" />
      <%String principalName="";
        if(DtoUser.USER_TYPE_EMPLOYEE.equals(principal_scope)) {
          principalName = HrFactory.create().getName((String)principal_id);
        } else {
          principalName = HrFactory.create().getCustomerName((String)principal_id);
        }
     %>
      <TD class="list_range" width="160" title="<%=principal_id+"/"+principalName%>">
      <input type="hidden" name="principal" value="<%=principal_id%>">
      <input type="hidden" name="principalScope" value="<%=principal_scope%>">
        <html:text name="principal_name"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_field"
                      next="issueStatus"
                      prev="dueDate"
                      req="true"
                      readonly="true"
                      value="<%=principalName%>"
                      imageSrc="<%=(readonlyFlag || user.getUserType().equals(DtoUser.USER_TYPE_CUST))?"":contextPath+"/image/humanAllocate.gif"%>"
                      imageWidth="18"
                      imageOnclick="allocateHr()"
         />
      </TD>
      <td width="2"></td>
      <TD width="100" class="list_range">Status</TD>
      <TD class="list_range" width="10">
        <html:select name="issueStatus" styleId="input_text2" beanName="webVo"  next="duplationIssue" prev="principal" req="true" onchange="onChangeDueDate(this.value);onChangeIssueDuplation();onStatusChange();" readonly="<%=readonlyFlag%>">
            <html:optionsCollection name="webVo" property="statusList"/>
        </html:select>
      </TD>
      <td width="1"></td>
      <td width="73" class="list_range">Duplation</td>
      <td>
        <bean:define id="duplationIssueDisabled" name="webVo" property="duplationIssueDisabled"/>
        <table width="100%" cellpadding="0" cellspacing="0">
        <tr>
        <td width="90%" valign="middle">
        <html:select name="duplationIssue" styleId="input_field1" beanName="webVo"
            next="" prev="issueStatus" req="false"
            disabled="<%=((Boolean)duplationIssueDisabled).booleanValue()%>"
            readonly="<%=readonlyFlag%>" >
          <html:optionsCollection name="webVo" property="duplationIssueList"/>
        </html:select>        </td>
        <td width="10%">
        <html:button styleId="btnOK" name="btnOK" value="goto" onclick="gotoDuplation()" disabled="<%=((Boolean)duplationIssueDisabled).booleanValue()%>"/></td>
        </tr>
        </table>
      </td>
    </TR>
  <tr id="closeTR1">
         <td class="list_range" width="80">Confirm Date</td>
         <td class="list_range" width="160">
            <html:text styleId="input_text" fieldtype="dateyyyymmdd" name="confirmDate" beanName="webVo" readonly="true" ondblclick="getDATE(this)" />

        </td>
        <td width="2"></td>
        <td class="list_range" width="100">Confirm By</td>
        <bean:define id="confirmBy_id" name="webVo" property="confirmBy" />
      <bean:define id="confirmBy_scope" name="webVo" property="confirmByScope" />
      <%String confirmByName="";
        if(DtoUser.USER_TYPE_EMPLOYEE.equals(confirmBy_scope)) {
          confirmByName = HrFactory.create().getName((String)confirmBy_id);
        } else {
          confirmByName = HrFactory.create().getCustomerName((String)confirmBy_id);
        }
     %>
     <input type="hidden" name="confirmBy" value="<%=confirmBy_id%>">
        <td class="list_range" width="10" title="<%=confirmBy_id+"/"+confirmByName%>"><html:text fieldtype="text" styleId="input_text2" name="confirmByName" beanName="webVo" readonly="true" value="<%=confirmByName%>"  /></td>
        <td width="1"></td>
      <td class="list_range" width="73">&nbsp;</td>
       <TD class="list_range" width="297">

       </td>

    </tr>
     <tr id="closeTR2">
        <td class="list_range" width="80">Instruction<br>
       of closure</td>
        <TD class="list_range" colspan="7">
        <html:textarea name="instructionOfClosure" beanName="webVo" rows="3" styleId="description" maxlength="1000" styleClass="TextAreaStyle" readonly="<%=readonlyFlag%>"/>
      </TD>
  </tr></table>
</html:form>
<iframe id="optionListFrm" name="optionListFrm" src="" width="0" height="0"></iframe>
<iframe id="optionListFrm2" name="optionListFrm2" src="" width="0" height="0"></iframe>
<iframe id="optionListFrm3" name="optionListFrm3" src="" width="0" height="0"></iframe>
<form name="dataForm" action="" method="POST">
    <input type="hidden" name="issueType">
    <input type="hidden" name="typeName">
    <input type="hidden" name="filledBy">
    <input type="hidden" name="filleDate">
    <input type="hidden" name="priority">
    <input type="hidden" name="statusName">
    <input type="hidden" name="accountRid">
    <input type="hidden" name="rid">
    <input type="hidden" name="prioritySelectbox" value="document.issueUpdate.priority">
    <input type="hidden" name="scopeSelectbox" value="document.issueUpdate.scope">
    <input type="hidden" name="statusSelectbox" value="document.issueUpdate.issueStatus">
</form>
</body>
</html>
