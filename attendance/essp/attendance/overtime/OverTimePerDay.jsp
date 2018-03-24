<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>

<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="OverTimeDetail"/>
  <tiles:put name="jspName" value="OverTimeDetail"/>
</tiles:insert>
<script language="javaScript" type="text/javascript">
var currentRowObj;
<%----------------------------------------------------------------------------%>
function onclickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	changeRowColor(rowObj);
    }catch( ex ){
    }
}
//--------------------计算合计时间
function onSum(Obj){
  var length = document.detailForm.elements.length;
  var totalHours = 0;
  for(var i = 0;i < length; i ++){
    var element = document.detailForm.elements[i];
    if(element != null){
      var name = element.name;
      if(name.indexOf("hours") != -1){
        totalHours  += parseFloat(element.value);
      }
    }
  }
//  alert(totalHours);
  if(parent.overTimeForm != null && parent.overTimeForm.totalHours != null)
      parent.overTimeForm.totalHours.value = totalHours;
  if (parent.overTimeForm != null &&parent.overTimeForm.actualTotalHours != null)
       parent.overTimeForm.actualTotalHours.value = totalHours;
}
function onCaculate(dateFrom,dateTo,timeFrom,timeTo,isEachDay){
//  alert("onCaculate()");
    document.caculateForm.dateFrom.value = dateFrom;
    document.caculateForm.dateTo.value = dateTo;
    document.caculateForm.timeFrom.value = timeFrom;
    document.caculateForm.timeTo.value = timeTo;
    document.caculateForm.isEachDay.value = isEachDay;
    document.caculateForm.submit();
}
//验证每天时间应小于24小时
function validateData(){
  var length = document.detailForm.elements.length;
  var totalHours = 0;
  for(var i = 0;i < length; i ++){
    var element = document.detailForm.elements[i];
    if(element != null){
      var name = element.name;
      if(name.indexOf("hours") != -1){
        var hours  = parseFloat(element.value);
        if(hours > 24){
          alert("Hours per day can not be larger than 24!");
          return false;
        }
      }
    }
  }
  return true;
}
</script>
</head>
<body >
  <html:form id="caculateForm" action="/attendance/overtime/OverTimeAppCaculate">
    <html:hidden name="dateFrom" />
    <html:hidden name="dateTo"  />
    <html:hidden name="timeFrom"  />
    <html:hidden name="timeTo"  />
    <html:hidden name="isEachDay"  />
  </html:form>
    <html:tabpanel id="OverTimeDetailList" width="522">
        <%-- card title--%>
        <html:tabtitles>
            <html:tabtitle selected="true" width="80">
                <center><a class="tabs_title">Detail</a></center>
            </html:tabtitle>
        </html:tabtitles>
        <%-- card buttons--%>
        <html:tabbuttons>
        </html:tabbuttons>
        <%-- card --%>
        <html:tabcontents>
            <html:tabcontent styleClass="wind">
                <html:outborder height="5%" width="522">
                    <html:table styleId="tableStyle">
                         <%-- head --%>
                         <html:tablehead styleId="tableTitleStyle">
                             <html:tabletitle width="65" styleId="oracolumntextheader" toolTip="Day">Day</html:tabletitle>
                             <html:tabletitle width="95" styleId="oracolumnnumberheader" toolTip="OverTime Hours">OverTime Hours</html:tabletitle>
                             <html:tabletitle width="80" styleId="oracolumnnumberheader" toolTip="Shifted Hours">Shifted Hours</html:tabletitle>
                             <html:tabletitle width="75" styleId="oracolumnnumberheader" toolTip="Payed Hours">Payed Hours</html:tabletitle>
                             <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Remark">Remark</html:tabletitle>
                         </html:tablehead>
                         <html:tablebody styleId="tableDataStyle" height="80" id="searchTable">
                           <logic:present name="webVo" scope="request">
                             <bean:define id="readOnly" name="webVo" property="readOnly" />
                             <form name="detailForm" id="detailForm" action="">
                             <logic:iterate id="perDay" name="webVo" property="perDay" indexId="indexId">
                               <html:tablerow
                                 styleId="<%=indexId.intValue()%2==1?"tableDataOdd2":"tableDataEven2"%>"
                                 id="<%="tr" + indexId.intValue()%>"
                                 onclick="onclickRow(this);"
                                 height="18"
                                 >
                               <html:tablecolumn styleId="oracelltext">
                                 <bean:date name="perDay" property="overtimeDay" fmt="yyyy/MM/dd"/>
                               </html:tablecolumn>
                               <html:tablecolumn styleId="oracellnumber">
                                <input name='hours<bean:date name="perDay" property="overtimeDay" fmt="yyyyMMdd"/>' value='<bean:write name="perDay" property="hours"/>'  req="false" sreq="false" class="input_text" onblur="doBlur();" onfocus="doFocus();" onchange="onSum(this);" fieldtype="number" fmt="8.2" maxlength="12" defaultvalue="true" <%=readOnly%>/>
                               </html:tablecolumn>
                               <html:tablecolumn styleId="oracellnumber">
                                 <input name='shifted' value='<bean:write name="perDay" property="shiftHours"/>'  req="false" sreq="false" class="input_text" onblur="doBlur();" onfocus="doFocus();" onchange="onSum(this);" fieldtype="number" fmt="8.2" maxlength="12" defaultvalue="true" readonly/>
                               </html:tablecolumn>
                               <html:tablecolumn styleId="oracellnumber">
                                 <input name='payed' value='<bean:write name="perDay" property="payedHours"/>'  req="false" sreq="false" class="input_text" onblur="doBlur();" onfocus="doFocus();" onchange="onSum(this);" fieldtype="number" fmt="8.2" maxlength="12" defaultvalue="true" readOnly/>
                               </html:tablecolumn>
                               <html:tablecolumn styleId="oracelltext">
                                 <input name='remark<bean:date name="perDay" property="overtimeDay" fmt="yyyyMMdd"/>' value='<bean:write name="perDay" property="remark"/>' req="false" sreq="false" class="input_text" onblur="doBlur();" onfocus="doFocus();" onchange="onSum(this);" fieldtype="text" fmt="" maxlength="40" defaultvalue="true" <%=readOnly%>/>
                               </html:tablecolumn>
                             </html:tablerow>
                             </logic:iterate>
                             </form>
                           </logic:present>
                         </html:tablebody>
                     </html:table>
                 </html:outborder>
             </html:tabcontent>
         </html:tabcontents>
     </html:tabpanel>
 </body>
</html>
<logic:present name="webVo" scope="request">
  <script language="javaScript" type="text/javascript">
  if(parent.overTimeForm != null && parent.overTimeForm.totalHours != null)
      parent.overTimeForm.totalHours.value = <bean:write name="webVo" property="totalHours"/>;
  if (parent.overTimeForm != null &&parent.overTimeForm.actualTotalHours != null)
       parent.overTimeForm.actualTotalHours.value = <bean:write name="webVo" property="totalHours"/>;
  </script>
</logic:present>

