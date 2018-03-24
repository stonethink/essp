<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="c2s.essp.common.user.DtoUser"%>
<%@page import="db.essp.timesheet.TsOutWorker"%>
<%@page import="com.wits.util.comDate"%>
<%
  DtoUser user = (DtoUser) session.getAttribute(DtoUser.SESSION_USER);
  String orgId = user.getOrgId();
  String begin = "";
  String end = "";
  String acntRid = "";
  String days = "";
  String destAdd = "";
  String code = "";
  String activityRid = "";
  String emp = "";
  String rid = "";
  String travellingallowance="";
  String evectionType = "";
  String isAuto = "false";
  if (request.getAttribute("AOutWorker") != null) {
    TsOutWorker worker = (TsOutWorker) request.getAttribute("AOutWorker");
    String orgCode = (String) request.getAttribute("orgCode");
    orgId = orgCode;
    begin = comDate.dateToString(worker.getBeginDate(), "yyyy/MM/dd");
    end = comDate.dateToString(worker.getEndDate(), "yyyy/MM/dd");
    acntRid = worker.getAcntRid().toString();
    days = worker.getDays().toString();
    code = worker.getCodeRid().toString();
    destAdd = worker.getDestAddress();
    evectionType = worker.getEvectionType();
    if(worker.getIsTravellingAllowance()!=null && !worker.getIsTravellingAllowance().equals("")){
      travellingallowance = worker.getIsTravellingAllowance();
    }else{
      travellingallowance = "false";
    }
    if (worker.getActivityRid() != null && !worker.getActivityRid().equals("")) {
      activityRid = worker.getActivityRid().toString();
    }
    emp = worker.getLoginId();
    rid = worker.getRid().toString();
    isAuto = worker.getIsAutoWeeklyReport() ? "true" : "false";
  }
%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Update Travels On Office Business Info"/>
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
<script language="javascript">
function getMyDATE(dateName){
     try{
    	var date = document.getElementById(dateName);
    	date.focus();
    	getDATE(date);
    	} catch(e){}
     }
function calculateDays(){
   var days = getOutWorkDays(true);
	if(days != null) {
    	document.addForm.days.value = days;
  	}
}

function getOutWorkDays(isAlert) {
	var endPeriod = document.addForm.endDate.value;
  	var beginPeriod = document.addForm.beginDate.value;
  	if(endPeriod!=null && beginPeriod!=null&&endPeriod.length>0&&beginPeriod.length>0){
    	if(endPeriod < beginPeriod && isAlert){
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
	return iDays;
	}
	return null;
}

function checkOutWorkDays(txtDays) {
	var cDays = getOutWorkDays(false);
	if(cDays != null && txtDays.value > cDays) {
		txtDays.value = cDays;
	}
}

function sumbitUpdate(){
	if (addForm.depart.value=="" || addForm.depart.value=="-1"){
        alert("<bean:message bundle="timesheet" key="outWorker.fill.dept"/>");
        addForm.depart.focus();
        return;
    }
	if (addForm.account.value=="" || addForm.account.value=="-1"){
       	alert("<bean:message bundle="timesheet" key="outWorker.fill.account"/>");
       	addForm.account.focus();
        return;
    }
    if (addForm.code.value=="" || addForm.code.value=="-1"){
        alert("<bean:message bundle="timesheet" key="outWorker.fill.code"/>");
        addForm.code.focus();
        return;
    }
	if (addForm.employee.value=="" || addForm.employee.value=="-1"){
        alert("<bean:message bundle="timesheet" key="outWorker.fill.employee"/>");
        addForm.employee.focus();
        return;
    }

    if (addForm.beginDate.value==""){
        alert("<bean:message bundle="timesheet" key="outWorker.fill.begin"/>");
        addForm.beginDate.focus();
        return;
    }

    if (addForm.endDate.value==""){
        alert("<bean:message bundle="timesheet" key="outWorker.fill.end"/>");
        addForm.endDate.focus();
        return;
    }

    if (addForm.days.value==""){
       	alert("<bean:message bundle="timesheet" key="outWorker.fill.days"/>");
       	addForm.days.focus();
        return;
    }
    
    if (addForm.evectionType.value==""){
      	alert("<bean:message bundle="timesheet" key="outWorker.fill.evectionType"/>");
      	addForm.evectionType.focus();
        return;
  	}

   var endPeriod = document.addForm.endDate.value;
   var beginPeriod = document.addForm.beginDate.value;
   if(endPeriod < beginPeriod){
      alert("<bean:message bundle="timesheet" key="outWorker.beginlessEnd"/>");
      return;
    }

  var accountId=addForm.account.value;
  var loginId=addForm.employee.value;
  var activityId=addForm.activity.value;
  var codeRid = addForm.code.value;
  var evectionType = addForm.evectionType.value;

  var update='<%=request.getContextPath()%>/timesheet/outwork/outWorkerUpdate.do?rid='+<%=rid%>+'&acntRid='+accountId+'&loginId='+loginId+'&activityRid='+activityId+'&codeRid='+codeRid+"&evectionType"+evectionType;
  addForm.action=update;
  addForm.submit();
  opener.window.parent.sumbitSearch();
  window.close();
}

</script>
</head>
<body bgcolor="#ffffff">
<!-- <h1>JBuilder Generated JSP <%out.print(user.getOrgId());%>
 </h1> -->
<center class="form_title"><bean:message bundle="timesheet" key="outWorker.edit.title"/></center>
<center>
  <html:form id="addForm" action="" method="POST">
    <table width="650" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="30" class="list_range"><bean:message bundle="timesheet" key="outWorker.common.department"/></td>
        <td width="200">
          <ws:select id="depart" property="depart_code" default="<%=orgId%>" type="server.essp.timesheet.outwork.logic.DepartmentSelectImpl" req="true" onchange="depart_codeSelectChangeEventaccount('null');" orgIds="<%=orgId%>" style="width:200px;background-color:#FFFACD" disabled="true"/>
        </td>
        <td width="50">&nbsp;</td>
        <td class="list_range"><bean:message bundle="timesheet" key="outWorker.common.account"/></td>
        <td width="200">
          <ws:upselect id="account" property="account_id" up="depart_code" type="server.essp.timesheet.outwork.logic.AccountSelectImpl" req="true" onchange="account_idSelectChangeEventemployee('null');account_idSelectChangeEventactivity('null');" orgIds="<%=orgId%>" style="width:200px;background-color:#FFFACD" default="<%=acntRid%>"/>
        </td>
      </tr>
      <tr>
        <td height="30" class="list_range"><bean:message bundle="timesheet" key="outWorker.common.code"/></td>
        <td width="200">
          <ws:upselect id="code" property="code_rid" up="account_id" type="server.essp.timesheet.outwork.logic.CodeSelectImpl" req="true" orgIds="<%=orgId%>" default="<%=code%>" style="width:200px;background-color:#FFFACD"/>
        </td>
        <td>&nbsp;</td>
        <td class="list_range"><bean:message bundle="timesheet" key="outWorker.common.activity"/></td>
        <td width="200">
          <ws:upselect id="activity" property="activity_id" up="account_id" type="server.essp.timesheet.outwork.logic.ActivitySelectImpl" req="true" orgIds="<%=orgId%>" default="<%=activityRid%>" style="width:200px"/>
        </td>
      </tr>
      <tr>
        <td height="30" class="list_range"><bean:message bundle="timesheet" key="outWorker.common.employee"/></td>
        <td width="200">
          <ws:upselect id="employee" property="employee_id" up="account_id" type="server.essp.timesheet.outwork.logic.EmployeeSelectImpl" req="true" orgIds="<%=orgId%>" default="<%=emp%>" style="width:200px;background-color:#FFFACD"/>
        </td>
        <td>&nbsp;</td>
        <td class="list_range"><bean:message bundle="timesheet" key="outWorker.common.type"/></td>
        <td width="200">
          <ws:select id="evection_type" property="evectionType" type="server.essp.timesheet.outwork.logic.EvectionTypeSelectImpl" req="true" default="<%=evectionType%>" style="width:200px;background-color:#FFFACD"/>
        </td>
      </tr>
      <tr>
        <td height="30" class="list_range"><bean:message bundle="timesheet" key="outWorker.common.beginDate"/></td>
        <td width="200">
        <html:text name="beginDate"
                      		beanName=""
                      		fieldtype="dateyyyymmdd"
                      		styleId="input_date"
                      		imageSrc="<%=request.getContextPath()+"/image/cal.png"%>"
                      		imageWidth="18"
                      		imageOnclick="getMyDATE('beginDate')"
                      		maxlength="10" 
                      		ondblclick="getDATE(this)"
                      		req="true"
                      		value="<%=begin%>"
                            />
        </td>
        <td>&nbsp;</td>
        <td class="list_range"><bean:message bundle="timesheet" key="outWorker.common.endDate"/></td>
        <td width="200">
        <html:text name="endDate"
                      		beanName=""
                      		fieldtype="dateyyyymmdd"
                      		styleId="input_date"
                      		imageSrc="<%=request.getContextPath()+"/image/cal.png"%>"
                      		imageWidth="18"
                      		imageOnclick="getMyDATE('endDate')"
                      		maxlength="10" 
                      		ondblclick="getDATE(this)"
                      		req="true"
                      		value="<%=end%>"
                            />
        </td>
      </tr>
      <bean:define id="fillTsTip"><bean:message bundle="timesheet" key="outWorker.common.fillTs.tip"/></bean:define>
      <bean:define id="destAddrTip"><bean:message bundle="timesheet" key="outWorker.common.destAddr.tip"/></bean:define>
      <bean:define id="allowanceTip"><bean:message bundle="timesheet" key="outWorker.common.allowance.tip"/></bean:define>
      <tr>
        <td height="30" class="list_range"><bean:message bundle="timesheet" key="outWorker.common.days"/></td>
        <td width="200">
          <html:text name="days" beanName="" fieldtype="text" styleId="input_date" onclick="calculateDays();" onblur="checkOutWorkDays(this);" defaultvalue="true" value="<%=days%>" req="true"/>
        </td>
        <td>&nbsp;</td>
        <td class="list_range" title="<%=destAddrTip%>"><bean:message bundle="timesheet" key="outWorker.common.destAddr"/></td>
        <td width="200">
          <html:text styleId="input_date" fieldtype="text" name="destAddress" beanName="" maxlength="250" value="<%=destAdd%>" next="" prev="" defaultvalue="true"/>
        </td>
      </tr>
      <tr>
        <td class="list_range" title="<%=fillTsTip%>"><bean:message bundle="timesheet" key="outWorker.common.fillTs"/></td>
        <td width="200">
         <html:checkbox name="isFillTimesheet" beanName="" checkedValue="true" defaultValue="<%=isAuto%>" uncheckedValue="false"/>
        </td>
        <td>&nbsp;</td>
        <td class="list_range" title="<%=allowanceTip%>"><bean:message bundle="timesheet" key="outWorker.common.allowance"/></td>
        <td width="200">
         <html:checkbox name="isTravellingAllowance" beanName="" checkedValue="true" defaultValue="<%=travellingallowance%>" uncheckedValue="false"/>
        </td>
      </tr>
    </table>
    <input type="button" class="button" name="Update" value="<bean:message bundle="application" key="global.button.edit"/>" onClick="sumbitUpdate();">
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="button" value="<bean:message bundle="timesheet" key="outWorker.common.close"/>" name="close" class="button" onClick="window.close();">
  </html:form>
</center>
</body>
</html>
