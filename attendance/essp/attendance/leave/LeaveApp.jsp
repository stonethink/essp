<%@page contentType="text/html; charset=UTF-8"%>
 <%@include file="/inc/pagedef.jsp"%>
 <%@page import="itf.hr.HrFactory"%>
<bean:define id="usableList" name="webVo" property="leaveStatus.usableList" type="java.util.List" />
<bean:define id="maxHoursList" name="webVo" property="leaveStatus.maxHoursList" type="java.util.List" />
<bean:define id="leaveTypeList" name="webVo" property="leaveStatus.leaveTypeList" type="java.util.List" />
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Leave Application"/>
  <tiles:put name="jspName" value="LeaveApp"/>
</tiles:insert>
</head>
<script language="JavaScript">
<%--将所有假别的休假状况放入HashMap中,Key:假别名称,Value:数组,存放该假别可用时间,支付方式,一次最长可请时间--%>
var leaveStatus = new HashMap();
//var firstLeaveType;
<logic:iterate id="leaveType" name="leaveTypeList" indexId="indexId">
  var leaveType = new Array();
  leaveType[0] = '<bean:write name="leaveType" property="settlementWay"/>';
  leaveType[1] = '<%=usableList.get(indexId.intValue())%>';
  leaveType[2] = '<%=maxHoursList.get(indexId.intValue())%>';
  leaveStatus.put('<bean:write name="leaveType" property="leaveName"/>',leaveType)
</logic:iterate>

//---------------------------选择LeaveType,SettlementWay和UsableHours连动
function onChangeLeaveType(leaveName){
  var leaveType = leaveStatus.get(leaveName);
  var settlementWay = leaveType[0];

  var usableHours = leaveType[1];
  var maxHours = parseFloat(leaveType[2]);
  document.leaveForm.settlementWay.value = settlementWay;
  document.leaveForm.usableHours.value = usableHours;
  document.leaveForm.maxHours.value = maxHours;
}
//-----------------------计算请假时间----------------
function onCaculateTime(){
  var dateFrom = document.leaveForm.dateFrom.value;
  var dateTo = document.leaveForm.dateTo.value;
  var timeFrom = document.leaveForm.timeFrom.value;
  var timeTo = document.leaveForm.timeTo.value;
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

function onSubmitLeaveApp(){
 if(submitForm(document.leaveForm) && validateData()){
    if(document.leaveForm.needReCaculate.value == "true"){
      submit_flug = false;
      alert("Before submitting,please recaculate the leave time!");
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
//---------------表单数据验证----------------------------------------
//1.最大可请时间为0时,只需判断申请时间是否大于可用时间
//2.最大可请时间不为0时,还需判断申请时间是否大于最大可请时间
function validateData(){
  var usable = parseFloat(document.leaveForm.usableHours.value);
//  alert(usable);
  var maxHours = parseFloat(document.leaveForm.maxHours.value);
  var totalHours = parseFloat(document.leaveForm.totalHours.value);
  if(totalHours > usable){
    alert("You have not enough hours for applying leave!");
    return false;
  }
  if(maxHours != 0 && totalHours > maxHours){
    alert("The applying hours is larger than the max hours ("+maxHours+") of this leave!");
    return false;
  }
  return true;
}
function onChange(){
//  alert("onChange()");
  document.leaveForm.needReCaculate.value = "true";
}
//重置表单
function onReset(){
  document.leaveForm.leaveName.selectedIndex = 0;
//  document.leaveForm.leaveName.value = "shift";
  document.leaveForm.leaveName.onchange();
  document.leaveForm.dateFrom.value="";
  document.leaveForm.dateTo.value="";
  document.leaveForm.totalHours.value="";
  document.leaveForm.timeFrom.value="09:00";
  document.leaveForm.timeTo.value="18:00";
}
</script>
<body>
<center class="form_title">Leave Application</center>
<br/>
<html:form action="/attendance/leave/LeaveApp" onsubmit="return onSubmitLeaveApp();" id="leaveForm" method="post">
  <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
    <tr>
      <td class="list_range" width="80">Worker</td>
      <td class="list_range" width="120">
       <%--显示申请人姓名而不是LoginId--%>
        <bean:define id="loginId" name="webVo" property="loginId" />
        <input type="hidden" name="loginId" value="<%=loginId%>">
        <%String userName=HrFactory.create().getName((String)loginId);%>
        <input name="userName" value="<%=userName%>" title="<%=loginId+"/"+userName%>" next="account" prev="Close" msg="" req="true" sreq="false" class=" Xtext Req Display" id="input_common" onblur="doBlur();" onfocus="doFocus();" readonly="readonly" fieldtype="text" fmt="" type="text" maxlength="25" fielderrorflg="" fieldmsg="" defaultvalue="true" />
        <%--html:text beanName="webVo" readonly="<%=java.lang.Boolean.TRUE.booleanValue()%>" name="loginId" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/--%>
      </td>
      <td class="list_range" width="100"></td>
      <td class="list_range" width="120">
      </td>
    </tr>

    <tr>
      <td class="list_range" width="80">Acount</td>
      <td class="list_range" width="120">
        <html:select name="acntRid" styleId="select_leave" styleClass="select_leave" >
          <html:optionsCollection property="accountList" name="webVo">
          </html:optionsCollection >
        </html:select>
      </td>
      <td class="list_range" width="100">Dept</td>
      <td class="list_range" width="120">
        <html:text beanName="webVo" readonly="<%=java.lang.Boolean.TRUE.booleanValue()%>" name="organization" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
        <html:hidden beanName="webVo" property="orgId" name="orgId" />
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Type</td>
      <td class="list_range" align="left">
        <html:select value="shift" name="leaveName" styleId="select_leave" styleClass="select_leave" onchange="onChangeLeaveType(this.value)" >
          <html:optionsCollection property="leaveOptList" name="webVo">
          </html:optionsCollection >
        </html:select>
      </td>
      <td class="list_range" width="100">Settlement Way</td>
      <td class="list_range" align="left">
        <html:select disabled="true" name="settlementWay" styleId="" styleClass="select_leave" >
          <html:optionsCollection name="webVo" property="settlementWayList">
          </html:optionsCollection >
        </html:select>
      </td>
    </tr>

    <tr>
      <td class="list_range" width="80">Usable Hours</td>
      <td class="list_range" width="120">
        <html:text beanName="" value="0" readonly="true" name="usableHours" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
      </td>
      <td class="list_range" width="100">Max Hours</td>
      <td class="list_range" width="120">
        <html:text beanName="" readonly="true" name="maxHours" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
      </td>
    </tr>

    <tr>
      <td class="list_range" width="80">Date:From</td>
      <td class="list_range" align="left">
        <html:text beanName="" name="dateFrom" fieldtype="dateyyyymmdd" styleId="input_common" next="dateTo" prev="account" req="true" onchange="onChange();" ondblclick="getDATE(this);"/>
      </td>
      <td class="list_range" width="80">To</td>
      <td class="list_range" align="left">
        <html:text beanName="" name="dateTo" fieldtype="dateyyyymmdd" styleId="input_common" next="isEachDay" prev="dateFrom" req="true" onchange="onChange();" ondblclick="getDATE(this);"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="60">Time:From</td>
      <td class="list_range" align="left">
        <html:text beanName="" name="timeFrom" fieldtype="timehhmm" styleId="input_common" next="dateTo" prev="account" req="true" onchange="onChange();" ondblclick="getDATE(this);"/>
      </td>
      <td class="list_range" width="80">To</td>
      <td class="list_range" align="left">
        <html:text beanName="" name="timeTo" fieldtype="timehhmm" styleId="input_common" next="isEachDay" prev="dateFrom" req="true" onchange="onChange();" ondblclick="getDATE(this);"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Total Hours</td>
      <td class="list_range"  colspan="3">
        <html:text beanName="" readonly="true" name="totalHours" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
        <input type="button" name="cal" value="Calculate" class="button" onclick="onCaculateTime();" />
      </td>
    </tr>
    <tr>
      <td class="list_range" width="60">Cause</td>
      <td class="list_range" width="180" colspan="3">
        <html:textarea beanName="" name="cause" maxlength="500" prev="sequence" next="save" cols="44" rows="5" styleId="text_area"/>
      </td>
    </tr>
    <input type="hidden"  name="needReCaculate" value="false"/>
    <tr align="right">
      <td   colspan="4" align="right">
        <input type="submit" name="submit" value="Submit" class="button" />
        <input type="button" name="reset" value="Reset" class="button" onclick="onReset();" />
        <input type="button" name="cancel" value="Cancel" class="button" onclick="window.close();" />
      </td>
    </tr>
  </table>
</html:form>
</body>
</html>
<script language="JavaScript">
onReset();
</script>
