<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="c2s.essp.common.user.DtoUser"%>
<%
  DtoUser user = (DtoUser) session.getAttribute(DtoUser.SESSION_USER);
  String orgId = user.getOrgId();
//  if(request.getSession().getAttribute("ATTENDENCE_MANAGER")!=null){
    orgId=(String)request.getSession().getAttribute("orgIds");
//  }
%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Add Travels On Office Business Info"/>
  <tiles:put name="jspName" value="."/>
</tiles:insert>
<style type="text/css">
  <!--
    #input_date{
    width=100%;
    }
  -->
   #select_date{
    width=200px;
    }
</style>
<script type="" language="javascript">
function calculateDays(){
  var endPeriod = document.addForm.endDate.value;
  var beginPeriod = document.addForm.beginDate.value;
  if(endPeriod!=null && beginPeriod!=null&&endPeriod.length>0&&beginPeriod.length>0){
    if(endPeriod < beginPeriod){
      alert("The finish date must be larger than begin date!");
      return;
    }
    var  aDate,  oDate1,  oDate2,  iDays
    aDate  =  beginPeriod.split("/");
    oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])    //ת��Ϊ12-18-2002��ʽ
    aDate  =  endPeriod.split("/")
    oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0])
    iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //�����ĺ�����ת��Ϊ����
    //       alert(iDays);
	if(iDays>=0){
		iDays++;
	}

    document.addForm.days.value=iDays;
  }
}

function sumbitAdd(){
	if (addForm.depart.value==""){
        alert("Please select Department!");
        return;
    }
	if (addForm.account.value==""){
        alert("Please select account!");
        return;
    }
	if (addForm.employee.value==""){
        alert("Please select employee!");
        return;
    }

    if (addForm.beginDate.value==""){
        alert("Please Fill in begin date!");
        return;
    }

    if (addForm.endDate.value==""){
        alert("Please Fill in finish date!");
        return;
    }

    if (addForm.days.value==""){
        alert("Please Fill in days!");
        return;
    }

   var endPeriod = document.addForm.endDate.value;
   var beginPeriod = document.addForm.beginDate.value;
   if(endPeriod < beginPeriod){
      alert("The finish date must be larger than begin date!");
      return;
    }
  if (addForm.evectionType.value==""){
        alert("Please select type!");
        return;
  }
  var accountId=addForm.account.value;
  var loginId=addForm.employee.value;
  var activityId=addForm.activity.value;

  var add='<%=request.getContextPath()%>/tc/outwork/outWorkerAdd.do?acntRid='+accountId+'&loginId='+loginId+'&activityRid='+activityId;
  addForm.action=add;
  addForm.submit();
}

</script>
</head>
<body bgcolor="#ffffff">
<center class="form_title">Add Travels On Office Business Info</center>
<center>
  <html:form id="addForm" action="" method="POST">
    <table width="650" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="30" class="list_range">Department</td>
        <td width="200">
          <ws:select id="depart" property="depart_code" type="server.essp.tc.outwork.logic.DepartmentSelectImpl" onchange="depart_codeSelectChangeEventaccount('null');" orgIds="<%=orgId%>" style="width:200px;background-color:#FFFACD"/>
        </td>
        <td width="50">&nbsp;</td>
        <td class="list_range">Account</td>
        <td width="200">
          <ws:upselect id="account" property="account_id" up="depart_code" type="server.essp.tc.outwork.logic.AccountSelectImpl" onchange="account_idSelectChangeEventemployee('null');account_idSelectChangeEventactivity('null');" orgIds="<%=orgId%>" style="width:200px;background-color:#FFFACD"/>
        </td>
      </tr>
      <tr>
        <td height="30" class="list_range">Employee</td>
        <td width="200">
          <ws:upselect id="employee" property="employee_id" up="account_id" type="server.essp.tc.outwork.logic.EmployeeSelectImpl" orgIds="<%=orgId%>" style="width:200px;background-color:#FFFACD"/>
        </td>
        <td>&nbsp;</td>
        <td class="list_range">Activity</td>
        <td width="200">
          <ws:upselect id="activity" property="activity_id" up="account_id" type="server.essp.tc.outwork.logic.ActivitySelectImpl" orgIds="<%=orgId%>" style="width:200px"/>
        </td>
      </tr>
      <tr>
        <td height="30" class="list_range">Begin Date</td>
        <td width="200">
          <html:text name="beginDate" beanName="outWorkerSearchForm" fieldtype="dateyyyymmdd" styleId="input_date" prev="" next="" ondblclick="getDATE(this);" req="true"/>
        </td>
        <td>&nbsp;</td>
        <td class="list_range">Finish Date</td>
        <td width="200">
          <html:text name="endDate" beanName="" fieldtype="dateyyyymmdd" styleId="input_date" prev="" next="" ondblclick="getDATE(this);" req="true"/>
        </td>
      </tr>
      <tr>
        <td height="30" class="list_range">Days</td>
        <td width="200">
          <html:text name="days" beanName="" fieldtype="text" styleId="input_date" onclick="calculateDays();" req="true"/>
        </td>
        <td>&nbsp;</td>
        <td class="list_range">Dest Address</td>
        <td width="200">
          <html:text styleId="input_date" fieldtype="text" name="destAddress" beanName="" maxlength="250" value="" next="" prev=""/>
        </td>
      </tr>
      <tr>
        <td height="30" class="list_range">Is Auto Fill WR</td>
        <td width="200">
          <select name="isAutoFillWR" style="width:200px">
            <option value="true">Auto Fill</option>
            <option value="false" selected>Not Auto Fill</option>
          </select>
        </td>
        <td >&nbsp;</td>
        <td height="30" class="list_range">Type</td>
        <td width="200">
          <html:select name="evectionType" styleId="select_date" req="true">
           <html:optionsCollection name="webVo"  property="evectionTypeList"/>
          </html:select>
        </td>

      </tr>
      <tr>
        <td class="list_range">Is Present Allowance</td>
        <td width="200">
         <html:checkbox name="isTravellingAllowance" beanName="" checkedValue="true" defaultValue="true" uncheckedValue="false"/>
        </td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
    <input type="button" class="button" name="Submit" value="Submit" onClick="sumbitAdd();">
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="reset" value="Reset" name="reset" class="button">
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" value="Close" name="close" class="button" onClick="window.close();">
  </html:form>
</center>
</body>
</html>
