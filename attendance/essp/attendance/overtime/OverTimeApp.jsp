<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="itf.hr.HrFactory"%>
<bean:define id="webVo" name="webVo" type="server.essp.attendance.overtime.viewbean.VbOverTimeApp">
</bean:define>
<%
String cssPath = request.getContextPath() + "/attendance/overtime/OverTimeApp";
%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="OverTime Application"/>
  <tiles:put name="jspName" value="<%=cssPath%>"/>
</tiles:insert>
<script language="JavaScript">
<%--  设置帧上下--%>
function goup() {
  changeSize(522,0);
  tdgoup.innerText = "";
  tdgodown.innerHTML = '<a href="#" onclick="godown()" class="linkup">6</a>';
}
function godown() {
  changeSize(522,150);
  tdgoup.innerHTML = '<a href="#" onclick="goup()" class="linkup">5</a>';
  tdgodown.innerText = "";
}
//-------------------------表单验证--------------------------
function submitOverTimeApp(){
  if(submitForm(document.overTimeForm) && validateData() && document.detailFrm.validateData()){
      appendDetailForm();
      document.detailFrm.src = "<%=request.getContextPath()%>/attendance/overtime/OverTimePerDay.jsp";
//      document.overTimeForm.submit();
      return true;
  }else{
    submit_flug = false;
    return false;
  }
}
//------------------------将每天明细加到申请表单一起提交------------
function appendDetailForm(){
    var detailForm = eval("document.detailFrm.detailForm");
    var length = detailForm.elements.length;
    var query ;
    for(var i = 0;i < length; i++){
      var element = detailForm.elements[i];
      if(element != null && "text" == element.type){
        if(query == null){
//          query = "?" + element.name + "=" + element.value;
          query = element.name + "=" + element.value;
        }else{
          query = query + "&" + element.name + "=" + element.value;
        }
      }
    }
    document.overTimeForm.detailInfo.value = query;
}
function validateData(){
  if(validateDate()){
    if(document.overTimeForm.needReCaculate.value == "true"){
      alert("Before submitting,please recaculate the over time!");
      return false;
    }
  }
  return true;
}

//-------------------------日期时间验证--------------------------
function validateDate(){
  var dateFrom = document.overTimeForm.dateFrom.value;
  var dateTo = document.overTimeForm.dateTo.value;
  if(dateFrom > dateTo){
    alert("The from date can not be larger than to date!");
    return false;
  }
  var timeFrom = document.overTimeForm.timeFrom.value;
  var timeTo = document.overTimeForm.timeTo.value;
  //isEachDay勾上或在同一天内,加班起始时间不能大于结束时间
  if((dateFrom == dateTo || document.overTimeForm.isEachDay.checked ) &&
      timeFrom > timeTo){
     alert("The from time can not be larger than to time!");
     return false;
  }
  return true;
}

function onChange(){
//  alert("onChange()");
  document.overTimeForm.needReCaculate.value = "true";
}
//-------------------------计算明细及汇总时间--------------------------
function onCaculate(){
  var dateFrom = document.overTimeForm.dateFrom.value;
  var dateTo = document.overTimeForm.dateTo.value;
  var timeFrom = document.overTimeForm.timeFrom.value;
  var timeTo = document.overTimeForm.timeTo.value;
  var isEachDay = document.overTimeForm.isEachDay.checked;
  if(dateFrom == "" || dateTo == "" || timeFrom == "" || timeTo == ""){
    alert("Before caculating the hours,please fill the date and time!");
    return;
  }
  if( validateDate()){
    document.detailFrm.onCaculate(dateFrom,dateTo,timeFrom,timeTo,isEachDay);
    godown();
    document.overTimeForm.needReCaculate.value = "false";
  }
}

function changeSize(width,height){
  var frm = document.getElementById("detailFrm");
  frm.width = width;
  frm.height = height;
}
</script>
</head>
<body >
    <center class="form_title">
        Over Time Application
    </center>
    <br />
    <html:form id="overTimeForm" action="/attendance/overtime/OverTimeApp"  onsubmit=" return submitOverTimeApp();" method="POST" >
    <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
        <tr>
            <td class="list_range" width="80">Worker</td>
            <td class="list_range" width="150">
               <%--显示申请人姓名而不是LoginId--%>
               <bean:define id="loginId" name="webVo" property="loginId" />
               <%String userName=HrFactory.create().getName((String)loginId);%>
               <input type="hidden" name="loginId" value="<%=loginId%>">
               <input name="userName" value="<%=userName%>" title="<%=loginId+"/"+userName%>" next="account" prev="Close" msg="" req="true" sreq="false" class=" Xtext Req Display" id="input_common" onblur="doBlur();" onfocus="doFocus();" readonly="readonly" fieldtype="text" fmt="" type="text" maxlength="25" fielderrorflg="" fieldmsg="" defaultvalue="true" />
               <%--html:text beanName="webVo" name="loginId" fieldtype="text" styleId="input_common" next="account"  prev="Close"  req="true" maxlength="25" readonly="true"/--%>
            </td>
            <td class="list_range" width="50">Account</td>
            <td class="list_range" width="150" colspan="2">
                <html:select name="acntRid" styleClass="project_select2" styleId="project_select" next="dateFrom" prev="worker">
                  <html:optionsCollection name="webVo" property="accountList" />
                </html:select>
            </td>
        </tr>
        <tr>
            <td class="list_range" width="80" >Date:From</td>
            <td class="list_range" align="left">
                <html:text beanName="webVo" name="dateFrom" fieldtype="dateyyyymmdd" styleId="input_common" next="dateTo"  prev="account"  req="true" ondblclick="getDATE(this);" onchange="onChange();"/>
            </td>

            <td class="list_range" width="50" >To</td>
            <td class="list_range" align="left">
                <html:text beanName="webVo" name="dateTo"  fieldtype="dateyyyymmdd" styleId="input_common" next="isEachDay"  prev="dateFrom" req="true" ondblclick="getDATE(this);" onchange="onChange();"/>
            </td>

            <td class="list_range" >
                <html:multibox name="isEachDay" beanName="webVo" styleId="" value="true" next="isEachDay"  prev="dateTo" onchange="onChange();" />Each Day
            </td>
        </tr>
        <tr>
            <td class="list_range" width="80" >Time:From</td>
            <td class="list_range" align="left">
                <html:text beanName="webVo" name="timeFrom"  fieldtype="timehhmm" styleId="input_common" next="dateTo"  prev="account"  req="true" onchange="onChange();"/>
            </td>

            <td class="list_range" width="50" >To</td>
            <td class="list_range" align="left">
                <html:text beanName="webVo" name="timeTo" fieldtype="timehhmm" styleId="input_common" next="isEachDay"  prev="dateFrom" req="true" onchange="onChange();"/>
            </td>

            <td class="list_range" >
              (HHMM)
            </td>
        </tr>
        <tr>
            <td class="list_range" width="80" >Total</td>
            <td class="list_range" >
                <html:text beanName="webVo" name="totalHours"  fieldtype="number" fmt="8.2" styleId="input_common" next="account"  prev="Close" maxlength="25" readonly="true"/>
            </td>
            <td class="list_range" colspan="3"><html:button name="caculate" styleId="" value="Calculate" onclick="onCaculate();"></html:button></td>
        </tr>
        <tr>
            <td class="list_range" width="80" >Cause</td>
            <td class="list_range" width="180" colspan="4">
                <html:textarea beanName="webVo" name="cause"  maxlength="500"  prev="sequence" next="save" cols="52" rows="5" styleId="text_area"  />
            </td>
        </tr>
        <tr align="right">
            <td align="right"  colspan="5">
              <table align="right">
                <tr>
                  <td id="tdgoup"></td>
                  <td id="tdgodown"><a href="#" onclick="godown()" class="linkup">6</a></td>
                </tr>
              </table>
            </td>
          </tr>
        <tr >
          <html:hidden name="detailInfo" />
          <input type="hidden" name="needReCaculate" value="false"/>
        </tr>
        <tr align="center">
          <td colspan="5" align="center" >
            <IFRAME id="detailFrm"
              name="detailFrm"
              SRC='<%=request.getContextPath()%>/attendance/overtime/OverTimePerDay.jsp'
              width="100%" height="0%" frameborder="no" border="0"
              MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
            </IFRAME>
          </td>
        </tr>
        <tr valign="bottom">
          <td colspan="5" align="right" valign="bottom" height="30">
            <input type="submit" value="Submit" name="submit" class="button" />
            <input type="reset" value="Reset" name="reset" class="button" >
              <input type="button" name="cancel" value="Cancel" class="button" onclick="window.close();" />
            </td>
          </tr>
    </table>
    </html:form>
</body>
</html>
