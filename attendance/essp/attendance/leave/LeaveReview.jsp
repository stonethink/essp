<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="itf.hr.HrFactory"%>
<bean:define id="readOnly" name="webVo" property="readOnly" type="java.lang.Boolean" />

<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Leave Review"/>
  <tiles:put name="jspName" value="LeaveApp"/>
</tiles:insert>
<script language="JavaScript">

//-----------------------计算请假时间----------------
function onCaculateTime(){
  var dateFrom = document.leaveForm.actualDateFrom.value;
  var dateTo = document.leaveForm.actualDateTo.value;
  var timeFrom = document.leaveForm.actualTimeFrom.value;
  var timeTo = document.leaveForm.actualTimeTo.value;
  if(  dateFrom == null || dateTo == null || timeFrom == null || timeTo == null
     ||dateFrom == "" || dateTo == "" || timeFrom == "" || timeTo == ""){
    alert("Please fill the leave period!");
    return false;
  }
  if(dateTo < dateFrom || (dateFrom == dateTo && timeFrom > timeTo)){
    alert("The start date time can not be larger than finish date tiem!");
    return false;
  }
  var query = "dateFrom="+dateFrom+"&dateTo="+dateTo+"&timeFrom="+timeFrom+"&timeTo="+timeTo
  var url = "<%=request.getContextPath()%>/attendance/leave/LeaveTimeCaculate.do?"+query;
//  alert(url)
  var option = "Top=1000,Left=2000,Height=1,Width=1,toolbar=no,resizable=yes,scrollbars=no,status=yes";
  document.leaveForm.needReCaculate.value = "false";
  window.open(url,"",option);
}

function onChange(){
//  alert("onChange()");
  document.leaveForm.needReCaculate.value = "true";
}

function onSubmitLeaveReview(){
   if(!document.leaveForm.decision[0].checked &&
   !document.leaveForm.decision[1].checked &&
   !document.leaveForm.decision[2].checked){
      alert("Please choose the review decision");
      return false;
    }
    //选择通用或不同意不需检查表达
  if(document.leaveForm.decision[0].checked || document.leaveForm.decision[1].checked)
  return true;
  if(validateData()){
    if(document.leaveForm.needReCaculate.value == "true"){
      submit_flug = false;
      alert("Before submitting,please recaculate the over time!");
      return false;
    }else{
      //      alert(document.leaveForm.type);
      //      document.leaveForm.submit();
      return true;
    }
  }else{
    submit_flug = false;
    return false;
  }
}

function validateData(){
  var dateFrom = document.leaveForm.actualDateFrom.value;
  var dateTo = document.leaveForm.actualDateTo.value;
  var timeFrom = document.leaveForm.actualTimeFrom.value;
  var timeTo = document.leaveForm.actualTimeTo.value;
  /**
   *actualTotalHours1:审核前的实际请假时间,actualTotalHours2:审核时在页面上时间请假时间控件的值
   *currUsableHours:当前该假别的可请时间,maxHours:该假别最大可请时间
   *验证时间的方法:
   *1.maxHours==0时,判断currUsableHours + actualTotalHours1是否大于actualTotalHours2
   *2.maxHours!=0时,还需判断maxHours是否大于actualTotalHours2
   */
  var actualTotalHours1 = <bean:write name="webVo" property="actualTotalHours" />
  var actualTotalHours2 = parseFloat(document.leaveForm.actualTotalHours.value);
  var currUsableHours = parseFloat('<bean:write name="webVo" property="currUsableHours" />');
  var maxHours = <bean:write name="webVo" property="maxHours" />
  if(  dateFrom == null || dateTo == null || timeFrom == null || timeTo == null
     ||dateFrom == "" || dateTo == "" || timeFrom == "" || timeTo == ""){
    alert("Please fill the leave period!");
    return false;
  }
  if(dateTo < dateFrom || (dateFrom == dateTo && timeFrom > timeTo)){
    alert("The start date time can not be larger than finish date tiem!");
    return false;
  }

  if(actualTotalHours2 > (currUsableHours + actualTotalHours1)){
    alert("You have not enough hours for applying leave!");
    return false;
  }
  if(maxHours != 0 && actualTotalHours2 > maxHours){
    alert("The applying hours is larger than the max hours ("+maxHours+") of this leave!");
    return false;
  }

  return true;
}

function reviewDisable(){
  document.leaveForm.actualDateFrom.disabled=true;
  document.leaveForm.actualDateTo.disabled=true;
  document.leaveForm.actualTimeFrom.disabled=true;
  document.leaveForm.actualTimeTo.disabled=true;
  document.leaveForm.caculate.disabled=true;
}

function reviewEnable(){
  document.leaveForm.actualDateFrom.disabled=false;
  document.leaveForm.actualDateTo.disabled=false;
  document.leaveForm.actualTimeFrom.disabled=false;
  document.leaveForm.actualTimeTo.disabled=false;
  document.leaveForm.caculate.disabled=false;
}
</script>
</head>
<body>
    <logic:equal value="true"  name="readOnly">
    <center class="form_title">Leave View</center>
  </logic:equal>
  <logic:notEqual value="true" name="readOnly">
    <center class="form_title">Leave Review</center>
  </logic:notEqual>
<table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
  <html:form action="./" name="leaveAppForm">
    <tr>
      <td colspan="4" class="orarowheader">Application</td>
    </tr>
    <tr>
      <td class="list_range" width="80">Worker</td>
      <td class="list_range" width="120">
        <%--显示申请人姓名而不是LoginId--%>
        <bean:define id="loginId" name="webVo" property="loginId" />
        <%String userName=HrFactory.create().getName((String)loginId);%>
        <input name="userName" value="<%=userName%>" title="<%=loginId+"/"+userName%>" next="account" prev="Close" msg="" req="true" sreq="false" class=" Xtext Req Display" id="input_common" onblur="doBlur();" onfocus="doFocus();" readonly="readonly" fieldtype="text" fmt="" type="text" maxlength="25" fielderrorflg="" fieldmsg="" defaultvalue="true" />
        <%--html:text beanName="webVo" readonly="true" name="loginId" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/--%>
      </td>
      <td class="list_range" width="100"></td>
      <td class="list_range" width="120"> </td>
    </tr>

    <tr>
      <td class="list_range" width="80">Account</td>
      <td class="list_range" width="120">
        <html:text beanName="webVo" readonly="true" name="accountName" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
      </td>
      <td class="list_range" width="100">Dept</td>
      <td class="list_range" width="120">
        <html:text beanName="webVo" readonly="true" name="organization" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Type</td>
      <td class="list_range" align="left">
        <html:text beanName="webVo" readonly="true" name="leaveName" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
      </td>
      <td class="list_range" width="100">Settlement Way</td>
      <td class="list_range" align="left">
        <html:text beanName="webVo" readonly="true" name="settlementWay" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="100">Usable Hours</td>
      <td class="list_range" width="120">
        <html:text beanName="webVo" readonly="true" name="usableHours" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
      </td>
      <td class="list_range" width="100">Max Hours</td>
      <td class="list_range" width="120">
        <html:text beanName="webVo" readonly="true" name="maxHours" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="60">Date:From</td>
      <td class="list_range" align="left">
        <html:text beanName="webVo" readonly="true" name="dateFrom" fieldtype="dateyyyymmdd" styleId="input_common" next="dateTo" prev="account" req="true" />
      </td>
      <td class="list_range" width="80">To</td>
      <td class="list_range" align="left">
        <html:text beanName="webVo" readonly="true" name="dateTo" fieldtype="dateyyyymmdd" styleId="input_common" next="isEachDay" prev="dateFrom" req="true" />
      </td>
    </tr>
    <tr>
      <td class="list_range" width="60">Time:From</td>
      <td class="list_range" align="left">
        <html:text beanName="webVo" readonly="true" name="timeFrom" fieldtype="timehhmm" styleId="input_common" next="dateTo" prev="account" req="true" ondblclick="getDATE(this);"/>
      </td>
      <td class="list_range" width="80">To</td>
      <td class="list_range" align="left">
        <html:text beanName="webVo" readonly="true" name="timeTo" fieldtype="timehhmm" styleId="input_common" next="isEachDay" prev="dateFrom" req="true" ondblclick="getDATE(this);"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="100">Total Hours</td>
      <td class="list_range" colspan="3">
        <html:text beanName="webVo" readonly="true" name="totalHours" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="60">Cause</td>
      <td class="list_range" width="180" colspan="3">
        <html:textarea beanName="webVo" readonly="true" name="cause" maxlength="500" prev="sequence" next="save" cols="44" rows="3" styleId="text_area"/>
      </td>
    </tr>
  </html:form>
  <tr>
    <td colspan="4" class="orarowheader">Review</td>
  </tr>
  <html:form action="/attendance/leave/LeaveReview" id="leaveForm" onsubmit="return onSubmitLeaveReview();">
    <logic:present parameter="currActivityRid">
      <bean:parameter id="currActivityRid" name="currActivityRid"/>
      <input type="hidden" name="currActivityRid" value="<%=currActivityRid%>" />
    </logic:present>

    <html:hidden beanName="webVo" property="rid" />
    <tr>
      <td colspan="4">
        <table border="0" cellpadding="1" cellspacing="0">
          <tr>
            <td class="list_range" width="80">Decision</td>
            <td class="list_range">
              <html:radiobutton disabled="<%=readOnly.booleanValue()%>" name="decision" beanName="webVo" styleId="" value="Agree" onclick="reviewDisable();"/>
              Agree
            </td>
            <td class="list_range">
              <html:radiobutton disabled="<%=readOnly.booleanValue()%>" name="decision" beanName="webVo" styleId="" value="Disagree" onclick="reviewDisable();"/>
              Disagree
            </td>
            <td class="list_range">
              <html:radiobutton disabled="<%=readOnly.booleanValue()%>" name="decision" beanName="webVo" styleId="" value="Modify" onclick="reviewEnable();"/>
              Modify
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="60">Date:From</td>
      <td class="list_range" align="left">
        <html:text readonly="<%=readOnly.booleanValue()%>" beanName="webVo" name="actualDateFrom" fieldtype="dateyyyymmdd" styleId="input_common" next="dateTo" prev="account" req="true" ondblclick="getDATE(this);" onchange=" onChange();"/>
      </td>
      <td class="list_range" width="80">To</td>
      <td class="list_range" align="left">
        <html:text readonly="<%=readOnly.booleanValue()%>" beanName="webVo" name="actualDateTo" fieldtype="dateyyyymmdd" styleId="input_common" next="isEachDay" prev="dateFrom" req="true" ondblclick="getDATE(this);" onchange=" onChange();"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="60">Time:From</td>
      <td class="list_range" align="left">
        <html:text readonly="<%=readOnly.booleanValue()%>" beanName="webVo" name="actualTimeFrom" fieldtype="timehhmm" styleId="input_common" next="dateTo" prev="account" req="true" onchange=" onChange();"/>
      </td>
      <td class="list_range" width="80">To</td>
      <td class="list_range" align="left">
        <html:text readonly="<%=readOnly.booleanValue()%>" beanName="webVo" name="actualTimeTo" fieldtype="timehhmm" styleId="input_common" next="isEachDay" prev="dateFrom" req="true" onchange=" onChange();"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="60">Total</td>
      <td class="list_range" colspan="3">
        <html:text beanName="webVo" readonly="true" name="actualTotalHours" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
        (Hour)
          <logic:notEqual value="true" name="readOnly">
            <input type="button" name="caculate" value="Caculate" class="button" onclick="onCaculateTime();"/>
          </logic:notEqual>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="60">Comments</td>
      <td class="list_range" width="180" colspan="3">
        <html:textarea readonly="<%=readOnly.booleanValue()%>" beanName="webVo" name="comments" maxlength="500" prev="sequence" next="save" cols="44" rows="3" styleId="text_area"/>
      </td>
    </tr>

  <tr align="right">
    <td colspan="4" align="right" height="130">
      <IFRAME id="cardFrm"
        name="cardFrm"
        SRC='<%=request.getContextPath()%>/attendance/leave/LeaveReviewLog.do?rid=<bean:write name="webVo" property="rid"/>'
        width="100%" height="100%" frameborder="no" border="0"
        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
      </IFRAME>
    </td>
  </tr>
  <input type="hidden"  name="needReCaculate" value="false"/>
  <tr align="right">
    <td colspan="4" align="right" height="30">
      <%if(!readOnly.booleanValue()){
      %>
      <input type="submit" name="submit" value="Submit" class="button"/>
      <input type="reset" name="reset" value="Reset" class="button"/>
      <%}%>
      <input type="button" name="cancel" value="Cancel" class="button" onclick="window.close();"/>
    </td>
  </tr>
</html:form>
</table>
</body>
</html>
<script language="JavaScript">
          <logic:notEqual value="true" name="readOnly">
            reviewDisable();
          </logic:notEqual>
</script>
