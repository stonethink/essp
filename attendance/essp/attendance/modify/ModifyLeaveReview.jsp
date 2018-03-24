<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="itf.hr.HrFactory"%>
<%@include file="/inc/pagedef.jsp"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Modify Leave Review"/>
  <tiles:put name="jspName" value="ModifyLeaveReview"/>
</tiles:insert>
<script language="JavaScript">
<%-----------------------------------------------------------------------------%>
<%----重置表单----%>
function onReset(){
  document.changeForm.reset();
  reviewDisable();
}
<%---数据验证:每天时间不能大于8小时,changeHours不能大于hours(修改后的时间不能大于原请假的时间)----%>
function validateData(){
  var len = document.changeForm.elements.length;
  for(var i = 0;i < len;i ++){
    var el = document.changeForm.elements[i];
    if(el.name.indexOf('changeHours') != -1){
      var changeHours = parseFloat(el.value);
      if(changeHours > 8){
        alert("leave hours per day can not be larger than 8 hours!");
        return false;
      }
      if(changeHours < 0){
        alert("leave hours per day can not be less than 0 hours!");
        return false;
      }
      var index = el.name.indexOf('-');
      var rid = parseInt(el.name.substring(0,index));
      var hours = document.getElementById(rid+'-hours');
//      alert("rid:"+rid+"    hours:"+hours.value);
      if(changeHours > parseInt(hours.value)){
        alert("change hours can not be greater than original leave hours!");
        return false;
      }
    }
  }
  return true;
}
<%---表单提交----%>
function onSubmit(){
  if(validateData()){
    document.changeForm.submit();
  }
}
<%------审核意见为同意和不同意时无需修改表单数据  -------%>
function reviewDisable(){
  var len = document.changeForm.elements.length;
  for(var i = 0;i < len;i ++){
    var el = document.changeForm.elements[i];
    if(el.name.indexOf('changeHours') != -1){
      el.disabled = true;
    }
  }
}
<%------审核意见为修改时改表单数据  -------%>
function reviewEnable(){
  var len = document.changeForm.elements.length;
  for(var i = 0;i < len;i ++){
    var el = document.changeForm.elements[i];
    if(el.name.indexOf('changeHours') != -1){
      el.disabled = false;
    }
  }
}
</script>
</head>
<body>
  <center class="form_title">Modify Leave Review</center>
  <br>
  <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
    <tr>
      <td colspan="4" class="orarowheader">Application</td>
    </tr>
    <%--html:form action="." id="leave"--%>
    <tr>
      <td class="list_range" >Worker</td>
      <td class="list_range" >
        <bean:define id="loginId" name="webVo" property="loginId" />
        <%String userName=HrFactory.create().getName((String)loginId);%>
        <input type="hidden" name="loginId" value="<%=loginId%>">
        <input name="userName" value="<%=userName%>" title="<%=loginId+"/"+userName%>" next="account" prev="Close" msg="" req="true" sreq="false" class=" Xtext Req Display" id="input_common" onblur="doBlur();" onfocus="doFocus();" readonly="readonly" fieldtype="text" fmt="" type="text" maxlength="25" fielderrorflg="" fieldmsg="" defaultvalue="true" />
      </td>
      <td class="list_range" >Acount</td>
      <td class="list_range" >
        <html:text beanName="webVo" readonly="true" name="accountName" fieldtype="text" styleId="input_common" next="leaveName" prev="Close" req="true" maxlength="25"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" >Type</td>
      <td class="list_range" >
        <html:text beanName="webVo" readonly="true" name="leaveName" fieldtype="text" styleId="input_common" next="organization" prev="Close" req="true" maxlength="25"/>
      </td>
      <td class="list_range" >Dept</td>
      <td class="list_range" >
        <html:text beanName="webVo" readonly="true" name="organization" fieldtype="text" styleId="input_common" next="actualDateFrom" prev="Close" req="true" maxlength="25"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" >Date:From</td>
      <td class="list_range" >
        <html:text beanName="webVo" name="actualDateFrom" readonly="true" fieldtype="dateyyyymmdd" styleId="input_common" next="actualDateTo" prev="account" req="true"  />
      </td>
      <td class="list_range">To</td>
      <td class="list_range">
        <html:text beanName="webVo" name="actualDateTo" readonly="true" fieldtype="dateyyyymmdd" styleId="input_common" next="actualTimeFrom" prev="dateFrom" req="true" />
      </td>
    </tr>
    <tr>
      <td class="list_range" >Time:From</td>
      <td class="list_range" >
        <html:text beanName="webVo" name="actualTimeFrom" readonly="true" fieldtype="timehhmm" styleId="input_common" next="actualTimeTo" prev="account" req="true" onchange="onChange();" ondblclick="getDATE(this);"/>
      </td>
      <td class="list_range" >To</td>
      <td class="list_range" >
        <html:text beanName="webVo" name="actualTimeTo" readonly="true" fieldtype="timehhmm" styleId="input_common" next="actualTotalHours" prev="dateFrom" req="true" onchange="onChange();" ondblclick="getDATE(this);"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" >Total Hours</td>
      <td class="list_range"  colspan="3">
        <html:text beanName="webVo" readonly="true" name="actualTotalHours" fieldtype="text" styleId="input_common" next="cause" prev="Close" req="true" maxlength="25"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" >Cause</td>
      <td class="list_range"  colspan="3">
        <html:textarea beanName="webVo" readonly="true" name="cause" maxlength="500" prev="sequence" next="save" cols="52" rows="3" styleId="text_area"/>
      </td>
    </tr>
    <%--/html:form--%>

    <tr>
      <td colspan="4" class="orarowheader">Review</td>
    </tr>
    <html:form action="/attendance/modify/ModifyLeaveReview" id="changeForm">
      <logic:present parameter="currActivityRid">
        <bean:parameter id="currActivityRid" name="currActivityRid"/>
        <input type="hidden" name="currActivityRid" value="<%=currActivityRid%>" />
        </logic:present>
      <input type="hidden" name="leaveRid" value='<bean:write name="webVo" property="rid"/>' />
    <tr>
      <td colspan="4">
        <table border="0" cellpadding="1" cellspacing="0">
          <tr>
            <td class="list_range" width="80">Decision</td>
            <td class="list_range"><html:radiobutton  name="decision" beanName="webVo" styleId="" value="Agree" onclick="reviewDisable();"/>Agree</td>
            <td class="list_range"><html:radiobutton  name="decision" beanName="webVo" styleId="" value="Disagree" onclick="reviewDisable();"/>Disagree</td>
            <td class="list_range"><html:radiobutton  name="decision" beanName="webVo" styleId="" value="Modify" onclick="reviewEnable();"/>Modify</td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td height="150" colspan="4">
        <html:tabpanel id="tabDetail" width="100%" >
          <html:tabtitles  selectedindex="1"  >
            <html:tabtitle width="70"><span class="tabs_title">Detail</span></html:tabtitle>
          </html:tabtitles>
          <html:tabbuttons>
          </html:tabbuttons>
          <html:tabcontents>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="82%">
                <html:table styleId="tableStyle">
                <%-- head --%>
                <html:tablehead styleId="tableTitleStyle">
                  <html:tabletitle width="85" styleId="oracolumndateheader" toolTip="Day">Day</html:tabletitle>
                  <html:tabletitle width="80" styleId="oracolumnnumberheader" toolTip="Leave Hours">Leave Hours</html:tabletitle>
                  <html:tabletitle width="85" styleId="oracolumnnumberheader" toolTip="Change Hours">Change Hours</html:tabletitle>
                  <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Remark">Remark</html:tabletitle>
                </html:tablehead>
                <%-- data --%>
                <html:tablebody styleId="tableDataStyle" height="80" id="searchTable">

                    <input type="hidden" name="leaveRid" value='<bean:write name="webVo" property="rid"/>' />
                    <logic:iterate id="detail" name="webVo" property="detailList" indexId="index">
                      <html:tablerow
                      styleId="<%=index.intValue()%2==1?"tableDataOdd2":"tableDataEven2"%>"
                      height="18"
                      >
                      <html:tablecolumn styleId="oracelldate">
                        <bean:write name="detail" property="leaveDay" format="yyyy/MM/dd"/>
                      </html:tablecolumn>
                      <html:tablecolumn styleId="oracellnumber">
                        <%--bean:write name="detail" property="hours" /--%>
                        <input name='<bean:write name="detail" property="rid" />-hours' align="right" value='<bean:write name="detail" property="hours" />' readonly="readonly"  req="false" sreq="false" class="input_text" onblur="doBlur();" onfocus="doFocus();"  fieldtype="number" fmt="8.2" maxlength="12" defaultvalue="true" />
                      </html:tablecolumn>
                      <html:tablecolumn styleId="oracellnumber">
                        <input name='<bean:write name="detail" property="rid" />-changeHours' align="right" value='<bean:write name="detail" property="changeHours" />'  req="false" sreq="false" class="input_text" onblur="doBlur();" onfocus="doFocus();"  fieldtype="number" fmt="8.2" maxlength="12" defaultvalue="true" />
                      </html:tablecolumn>
                      <html:tablecolumn styleId="oracellnumber">
                        <input name='<bean:write name="detail" property="rid" />-remark' value='<bean:write name="detail" property="remark" />'  req="false" sreq="false" class="input_text" onblur="doBlur();" onfocus="doFocus();"  fieldtype="text"  maxlength="50" size="55" defaultvalue="true" />
                      </html:tablecolumn>
                      </html:tablerow>
                    </logic:iterate>
                </html:tablebody>
                </html:table>
              </html:outborder>
            </html:tabcontent>
          </html:tabcontents>
        </html:tabpanel>
      </td>
    </tr>
    </html:form>
    <tr>
      <td height="25" colspan="4" align="right">
        <input type="button" name="submit" value="Submit" class="button" onclick="onSubmit();" />
        <input type="button" name="reset" value="Reset" class="button" onclick="onReset();" />
        <input type="button" name="cancel" value="Cancel" class="button" onclick="window.close();" />
      </td>
    </tr>
   </table>
</body>
</html>
<script language="JavaScript">
reviewDisable();//默认处理意见为同意
</script>
