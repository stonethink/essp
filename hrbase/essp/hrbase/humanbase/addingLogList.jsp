<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/inc/pagedef.jsp"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Adding Log List"/>
  <tiles:put name="jspName" value="."/>
</tiles:insert>
<script language="javascript">


function clickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	rid = rowObj.rid;
        if(rowObj != null && rid != null) {
        	btnView.disabled=false;
          	if(rowObj.canUpdate != null && rowObj.canUpdate == "true") {
	          	btnEdit.disabled=false;
	          	btnAbort.disabled=false;
          	} else {
	          	btnEdit.disabled=true;
	          	btnAbort.disabled=true;
          	}
        } else {
        	btnView.disabled=true;
        	btnEdit.disabled=true;
        	btnAbot.disabled=true;
        }
    }catch( ex ){
        //alert(ex);
    }
}

function editHumanBaseLog(){
  if(currentRowObj==null) {
        alert('<bean:message bundle="application" key="global.confirm.selectRow"/>');
        return;
    }
  var url='<%=request.getContextPath()%>/hrbase/humanbase/editHumanBaseLogPre.do?rid='+rid;
  var leftPos=(screen.width-750)/2;
  var topPos=(screen.height-300)/2;
  var pos='width=750,height=400,left='+leftPos+',top='+topPos;
  window.open(url, 'popupedit', pos);
}

function viewHumanBaseLog(){
  if(currentRowObj==null) {
        alert('<bean:message bundle="application" key="global.confirm.selectRow"/>');
        return;
    }
  var url='<%=request.getContextPath()%>/hrbase/humanbase/viewHumanBaseLog.do?rid='+rid;
  var leftPos=(screen.width-750)/2;
  var topPos=(screen.height-300)/2;
  var pos='width=750,height=350,left='+leftPos+',top='+topPos;
  window.open(url, 'popupview', pos);
}

function abortHumanBaseLog(){
   if(currentRowObj==null) {
        alert('<bean:message bundle="application" key="global.confirm.selectRow"/>');
        return;
    }
    if(confirm('<bean:message bundle="hrbase" key="hrbase.humanBase.abortMsg"/>')) {
        var url='<%=request.getContextPath()%>/hrbase/humanbase/abortHumanBaseLog.do?rid='+rid;
        window.open(url, 'popupabort', 'width=1,height=1,left=10000,top=1000000');
    }
}
function onLoad() {
	this.focus();
	selectFirstRow();
}

function selectFirstRow() {
	var firstTr = document.getElementById("trsearchTable_1");
	if(firstTr != null) {
		changeRowColorsearchTable(firstTr);
		clickRow(firstTr);
	}
}
</script>
</head>
<body onLoad="onLoad();">


<br/>
<center>
     <html:tabpanel id="hrbList" width="765">
    <%-- card title--%>
    <html:tabtitles>
        <html:tabtitle selected="true" width="90">
            <center><a class="tabs_title"><bean:message bundle="hrbase" key="hrbase.humanBase.btn.addLog"/></a></center>
        </html:tabtitle>
    </html:tabtitles>
    <%-- card buttons--%>
    <html:tabbuttons>
        <input type="button" value='<bean:message bundle="application" key="global.button.display"/>' class="button" onClick="viewHumanBaseLog();" id="btnView" disabled/>
        <input type="button" value='<bean:message bundle="application" key="global.button.edit"/>' class="button" onClick="editHumanBaseLog();" id="btnEdit" disabled/>
        <input type="button" value='<bean:message bundle="hrbase" key="hrbase.humanBase.btn.abort"/>' class="button" onClick="abortHumanBaseLog();" id="btnAbort" disabled/>
    </html:tabbuttons>
    <html:outborder height="80%" width="765">
      <html:table styleId="tableStyle">
        <html:tablehead styleId="tableTitleStyle">
          <html:tabletitle width="70"><bean:message bundle="hrbase" key="hrbase.humanBase.employeeId"/></html:tabletitle>
          <html:tabletitle width="80"><bean:message bundle="hrbase" key="hrbase.humanBase.englishName"/></html:tabletitle>
          <html:tabletitle width="60"><bean:message bundle="hrbase" key="hrbase.humanBase.operation"/></html:tabletitle>
          <html:tabletitle width="70"><bean:message bundle="hrbase" key="hrbase.humanBase.effectiveDate"/></html:tabletitle>
          <html:tabletitle width="70"><bean:message bundle="application" key="global.record.rcu"/></html:tabletitle>
          <html:tabletitle width="70"><bean:message bundle="application" key="global.record.rct"/></html:tabletitle>
          <html:tabletitle width="70" ><bean:message bundle="application" key="global.record.ruu"/></html:tabletitle>
          <html:tabletitle width="70" ><bean:message bundle="application" key="global.record.rut"/></html:tabletitle>
           <html:tabletitle width="*"><bean:message bundle="hrbase" key="hrbase.humanBase.status"/></html:tabletitle>
        </html:tablehead>
        <html:tablebody styleId="tableDataStyle" height="300" id="searchTable">
          <logic:iterate id="wv" name="webVo" indexId="indexId" scope="request">
          <bean:define id="rid" name="wv" property="rid" />
          <bean:define id="canUpdate" name="wv" property="canUpdate" />
          <%
                String selfProperty = "rid=" + rid + " canUpdate=" + canUpdate;
          %>
            <html:tablerow id="" selfProperty="<%=selfProperty%>" oddClass="tableDataOdd" evenClass="tableDataEven" height="15" canSelected="true" onclick="clickRow(this);">
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="employeeId"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="englishName"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="operation"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="effectiveDate"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="rcu"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="rct" format="yyyy-MM-dd"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="ruu"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="rut" format="yyyy-MM-dd"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="status"/>
              </html:tablecolumn>
            </html:tablerow>
          </logic:iterate>
        </html:tablebody>
       </html:table>
     </html:outborder>
   </html:tabpanel>
   <br>
   <input type="button" value='<bean:message bundle="application" key="global.button.close"/>' name="close" class="button" onClick="window.close();">
</center>
</body>
</html>
