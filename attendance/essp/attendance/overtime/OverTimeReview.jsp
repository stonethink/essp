<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="itf.hr.HrFactory"%>
<%-- readOnly=true时所有项目只能查看,不能修改  --%>
<bean:define id="readOnly" name="webVo" property="readOnly" type="java.lang.Boolean" />
<%
String cssPath = request.getContextPath() + "/attendance/overtime/OverTimeApp";
%>

<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="OverTime Review"/>
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
function submitOverTimeReview(){
  if(validateDate() && document.detailFrm.validateData()){
    if(!document.overTimeForm.decision[0].checked &&
    !document.overTimeForm.decision[1].checked &&
    !document.overTimeForm.decision[2].checked){
      alert("Please choose the review decision");
      return false;
    }
//    alert(document.overTimeForm.needReCaculate.value);
    if(isModify() && document.overTimeForm.needReCaculate.value == "true"){
      alert("Before submitting,please recaculate the over time!");
      return false;
    }else{
      if(isModify()){
        appendDetailForm();
      }
//      window.top.detail.location = "<%=request.getContextPath()%>/attendance/overtime/OverTimePerDay.jsp";
//      document.overTimeForm.submit();
      return true;
    }
  }
}
//------------------------判断是否选择Modify-----------------
function isModify(){
  return document.overTimeForm.decision[2].checked;
}
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
//-------------------------日期时间验证--------------------------
function validateDate(){
  var actualDateFrom = document.overTimeForm.actualDateFrom.value;
  var actualDateTo = document.overTimeForm.actualDateTo.value;
  if(actualDateFrom == null || actualDateTo ==null){
    alert("Please input the date period");
    return false;
  }
  if(actualDateFrom > actualDateTo){
    alert("The from date can not be larger than to date!");
    return false;
  }
  var actualTimeFrom = document.overTimeForm.actualTimeFrom.value;
  var actualTimeTo = document.overTimeForm.actualTimeTo.value;
  if(actualTimeFrom == null || actualTimeTo == null){
      alert("Please input the time period");
      return false;
  }
  //isEachDay勾上或在同一天内,加班起始时间不能大于结束时间
  if((actualDateFrom == actualDateTo || document.overTimeForm.actualIsEachDay.checked ) &&
      actualTimeFrom > actualTimeTo){
     alert("The from time can not be larger than to time!");
     return false;
  }
  return true;
}
function onChange(){
  document.overTimeForm.needReCaculate.value = "true";
//  alert(document.overTimeForm.needReCaculate.value);
}
//-------------------------计算明细及汇总时间--------------------------
function onCaculate(){
  var actualDateFrom = document.overTimeForm.actualDateFrom.value;
  var actualDateTo = document.overTimeForm.actualDateTo.value;
  var actualTimeFrom = document.overTimeForm.actualTimeFrom.value;
  var actualTimeTo = document.overTimeForm.actualTimeTo.value;
  var isEachDay = document.overTimeForm.actualIsEachDay.checked;
  if(actualDateFrom == "" || actualDateTo == "" || actualTimeFrom == "" || actualTimeTo == ""){
    alert("Before caculating the hours,please fill the date and time!");
    return;
  }
  if( validateDate()){
    document.detailFrm.onCaculate(actualDateFrom,actualDateTo,actualTimeFrom,actualTimeTo,isEachDay);
    godown();
    document.overTimeForm.needReCaculate.value = "false";
  }
}

function changeSize(width,height){
  var frm = document.getElementById("detailFrm");
  frm.width = width;
  frm.height = height;
}

function reviewDisable(){
  document.overTimeForm.actualDateFrom.disabled=true;
  document.overTimeForm.actualDateTo.disabled=true;
  document.overTimeForm.actualTimeFrom.disabled=true;
  document.overTimeForm.actualTimeTo.disabled=true;
  document.overTimeForm.actualIsEachDay.disabled=true;
  document.overTimeForm.caculate.disabled=true;
}

function reviewEnable(){
  document.overTimeForm.actualDateFrom.disabled=false;
  document.overTimeForm.actualDateTo.disabled=false;
  document.overTimeForm.actualTimeTo.disabled=false;
  document.overTimeForm.actualTimeFrom.disabled=false;
  document.overTimeForm.actualIsEachDay.disabled=false;
  document.overTimeForm.caculate.disabled=false;
}
</script></head>
<body>
  <logic:equal value="true" name="readOnly">
    <center class="form_title">Over Time View</center>
  </logic:equal>
  <logic:notEqual value="true" name="readOnly">
    <center class="form_title">Over Time Review</center>
  </logic:notEqual>

<table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
  <%--  OverTime Application ---%>
  <tr>
    <td colspan="5" class="orarowheader">Application</td>
  </tr>
    <tr>
      <td class="list_range" width="80">Worker</td>
      <td class="list_range" width="150">
        <%--显示申请人姓名而不是LoginId--%>
        <bean:define id="loginId" name="webVo" property="loginId" />
        <%String userName=HrFactory.create().getName((String)loginId);%>
        <input name="userName" value="<%=userName%>" title="<%=loginId+"/"+userName%>" next="account" prev="Close" msg="" req="true" sreq="false" class=" Xtext Req Display" id="input_common" onblur="doBlur();" onfocus="doFocus();" readonly="readonly" fieldtype="text" fmt="" type="text" maxlength="25" fielderrorflg="" fieldmsg="" defaultvalue="true" />
        <%--html:text beanName="webVo" name="loginId" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25" readonly="true" /--%>
      </td>
      <td class="list_range" width="50">Account</td>
      <td class="list_range" width="150" colspan="2">
        <html:text beanName="webVo" name="accountName" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25" readonly="true"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Date:From</td>
      <td class="list_range" align="left">
        <html:text beanName="webVo" name="dateFrom" readonly="true" fieldtype="dateyyyymmdd" styleId="input_common" next="dateTo" prev="account" req="true"/>
      </td>
      <td class="list_range" width="50">To</td>
      <td class="list_range" align="left">
        <html:text beanName="webVo" name="dateTo" readonly="true" fieldtype="dateyyyymmdd" styleId="input_common" next="isEachDay" prev="dateFrom" req="true"/>
      </td>
      <td class="list_range">
        <html:multibox name="isEachDay" beanName="webVo" disabled="true" styleId="" value="true" next="isEachDay" prev="dateTo"/>
        Each Day
</td>
    </tr>
    <tr>
      <td class="list_range" width="80">Time:From</td>
      <td class="list_range" align="left">
        <html:text beanName="webVo" name="timeFrom" readonly="true" fieldtype="timehhmm" styleId="input_common" next="dateTo" prev="account" req="true"/>
      </td>
      <td class="list_range" width="50">To</td>
      <td class="list_range" align="left">
        <html:text beanName="webVo" name="timeTo" readonly="true" fieldtype="timehhmm" styleId="input_common" next="isEachDay" prev="dateFrom" req="true"/>
      </td>
      <td class="list_range" >(HHMM)</td>
    </tr>
    <tr>
      <td class="list_range" width="80">Total</td>
      <td class="list_range">
        <html:text beanName="webVo" name="totalHours" fieldtype="number" fmt="8.2" styleId="input_common" next="account" prev="Close" maxlength="25" readonly="true"/>
      </td>
      <td class="list_range" colspan="3">      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Cause</td>
      <td class="list_range" width="150" colspan="4">
        <html:textarea
          beanName="webVo"
          name="cause"
          readonly="true"
          maxlength="500"
          prev="sequence"
          next="save"
          cols="52"
          rows="3"
          styleId="text_area"/>
      </td>
    </tr>
  <%--  OverTime Review ---%>
  <tr>
    <td colspan="5" class="orarowheader">Review</td>
  </tr>
  <html:form id="overTimeForm" action="/attendance/overtime/OverTimeReview" onsubmit="return submitOverTimeReview();" >
    <logic:present parameter="currActivityRid">
      <bean:parameter id="currActivityRid" name="currActivityRid"/>
      <input type="hidden" name="currActivityRid" value="<%=currActivityRid%>" />
    </logic:present>

    <html:hidden beanName="webVo" name="rid" />
    <tr>
      <td colspan="5">
      <table border="0" cellpadding="1" cellspacing="0">
        <tr>
          <td class="list_range" width="80">Decision</td>
          <td class="list_range">
            <html:radiobutton disabled="<%=readOnly.booleanValue()%>" name="decision" beanName="webVo" styleId="" value="Agree" onclick="reviewDisable()" />Agree
          </td>
          <td class="list_range">
            <html:radiobutton disabled="<%=readOnly.booleanValue()%>" name="decision" beanName="webVo" styleId="" value="Disagree" onclick="reviewDisable()" />Disagree
          </td>
          <td class="list_range">
            <html:radiobutton disabled="<%=readOnly.booleanValue()%>" name="decision" beanName="webVo" styleId="" value="Modify" onclick="reviewEnable()" />Modify
          </td>
        </tr>
      </table>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Date:From</td>
      <td class="list_range" align="left">
        <html:text readonly="<%=readOnly.booleanValue()%>" beanName="webVo" name="actualDateFrom" fieldtype="dateyyyymmdd" styleId="input_common" next="dateTo" prev="account" req="true" onchange="onChange()" ondblclick="getDATE(this);"/>
      </td>
      <td class="list_range" width="50">To</td>
      <td class="list_range" align="left">
        <html:text readonly="<%=readOnly.booleanValue()%>" beanName="webVo" name="actualDateTo" fieldtype="dateyyyymmdd" styleId="input_common" next="isEachDay" prev="actualDateFrom" req="true" onchange="onChange()" ondblclick="getDATE(this);"/>
      </td>
      <td class="list_range">
        <html:multibox disabled="<%=readOnly.booleanValue()%>" name="actualIsEachDay" beanName="webVo" styleId="" value="true" next="isEachDay" prev="dateTo" onchange="onChange()"/>
        Each Day
</td>
    </tr>
    <tr>
      <td class="list_range" width="80">Time:From</td>
      <td class="list_range" align="left">
        <html:text readonly="<%=readOnly.booleanValue()%>" beanName="webVo" name="actualTimeFrom" fieldtype="timehhmm" styleId="input_common" next="actualDateTo" prev="account" req="true" onchange="onChange()"/>
      </td>
      <td class="list_range" width="50">To</td>
      <td class="list_range" align="left">
        <html:text readonly="<%=readOnly.booleanValue()%>" beanName="webVo" name="actualTimeTo" fieldtype="timehhmm" styleId="input_common" next="isEachDay" prev="actualDateFrom" req="true" onchange="onChange()"/>
      </td>
      <td class="list_range" >(HHMM)</td>
    </tr>
    <tr>
      <td class="list_range" width="80">Total</td>
      <td class="list_range">
        <html:text readonly="true" beanName="webVo" name="actualTotalHours" fieldtype="number" fmt="8.2" styleId="input_common" next="account" prev="Close" maxlength="25" />
      </td>
      <td class="list_range" colspan="3">
        <html:button disabled="<%=readOnly.booleanValue()%>" name="caculate" styleId="" value="Caculate" onclick="onCaculate();">        </html:button>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Comments</td>
      <td class="list_range" width="150" colspan="4">
        <html:textarea
          readonly="<%=readOnly.booleanValue()%>"
          beanName="webVo"
          name="comments"
          maxlength="500"
          prev="sequence"
          next="save"
          cols="52"
          rows="3"
          styleId="text_area"/>
      </td>
    </tr>
    <html:hidden name="detailInfo" />
    <input type="hidden" name="needReCaculate" value="false"/>
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
  <tr align="center">
    <td colspan="5" align="center" >
      <IFRAME id="detailFrm"
        name="detailFrm"
        SRC='<%=request.getContextPath()%>/attendance/overtime/OverTimeDetailList.do?rid=<bean:write name="webVo" property="rid" />&readOnly=<%=readOnly.booleanValue()?"readOnly":""%>'
        width="100%" height="0%" frameborder="no" border="0"
        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
      </IFRAME>
    </td>
  </tr>
  <tr align="center">
    <td colspan="5" align="center" >
      <IFRAME id="ReviewFrm"
        name="ReviewFrm"
        SRC='<%=request.getContextPath()%>/attendance/overtime/OverTimeReviewLog.do?rid=<bean:write name="webVo" property="rid" />'
        width="100%" height="100%" frameborder="no" border="0"
        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
      </IFRAME>
    </td>
  </tr>
  <tr valign="bottom">
    <td colspan="5" align="right" valign="bottom" height="30">
      <%if(!readOnly.booleanValue()){ %>
      <input type="submit" value="Submit" name="submit" class="button" />
      <input type="reset" value="Reset" name="reset" class="button" >
      <%}%>
      <input type="button" value="Cancel" name="cancel" class="button" onclick="window.close();" >
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
