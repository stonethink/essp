<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="c2s.workflow.DtoRequestProcess,c2s.workflow.DtoWorkingProcess,itf.hr.HrFactory"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="worklist"/>
  <tiles:put name="jspName" value="worklist"/>
</tiles:insert>
</head>
<script language="javaScript" type="text/javascript">
var currentRequest;
var currentWorking;
<%----------------------------------------------------------------------------%>
function onclickRequestProcess(rowObj){
    try{
    	currentRequest = rowObj;
    	changeRowColor(rowObj);
    }catch( ex ){
    }
}
function onDbClickRequestProcess(rowObj){
  onclickRequestProcess(rowObj);
}
function onclickWorkingProcess(rowObj){
    try{
    	currentWorking = rowObj;
    	changeRowColor(rowObj);
    }catch( ex ){
    }
}
function onDbClickWorkingProcess(rowObj){
  onclickWorkingProcess(rowObj);
}
function viewRequestProcess(){
  if(currentRequest == null){
    alert("Please select your request process!");
    return;
  }
  var url = "<%=request.getContextPath()%>/wf/ViewProcess.do?wkRid="+currentRequest.wkRid;
  var option = "Top=100,Left=150,Height=400,Width=550,toolbar=no,resizable=yes,scrollbars=yes,status=yes";
  window.open(encodeURI(url),"",option);
//  alert(currentRequest.wkRid);
}
function handleWorkingProcess(){
  if(currentWorking == null){
      alert("Please select your working process!");
      return;
  }
  var url = "<%=request.getContextPath()%>/wf/WorkProcessHandle.do?wkRid="+currentWorking.wkRid+"&currActivityRid="+currentWorking.currActivityRid;
  var option = "Top=30,Left=120,Height=650,Width=600,toolbar=no,resizable=yes,scrollbars=yes,status=yes";
  window.open(encodeURI(url),"",option);
}
function agreeAllWorkingProcess(){
  if(document.WorkForm == null)
  return;
  var length = document.WorkForm.elements.length;
  for(var i = 0;i < length ;i ++){
    if(document.WorkForm.elements[i].type=="checkbox" && document.WorkForm.elements[i].checked){
      document.WorkForm.submit();
      return;
    }
  }
}
</script>
<body>
<table cellpadding="0" cellspacing="0">
<tr>
  <td height="245">
  <html:tabpanel id="OverTimeDetailList" width="100%">
    <html:tabtitles>
      <html:tabtitle selected="true" width="100">
        <center><a class="tabs_title">My Work In Process</a></center>
      </html:tabtitle>
    </html:tabtitles>
    <html:tabbuttons>
      <input type="button" name="view" value="View" class="button" onclick="handleWorkingProcess();" />
      <input type="button" name="submit" value="Submit" class="button" onclick="agreeAllWorkingProcess();" />
    </html:tabbuttons>
    <html:tabcontents>
      <html:tabcontent styleClass="wind">
        <html:outborder height="5%" width="100%">
          <html:table styleId="tableStyle">
            <%-- head --%>
            <html:tablehead styleId="tableTitleStyle">
              <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="From">From</html:tabletitle>
              <html:tabletitle width="140" styleId="oracolumntextheader" toolTip="Process Name">Process Name</html:tabletitle>
              <html:tabletitle width="480" styleId="oracolumntextheader" toolTip="Description">Description</html:tabletitle>
              <html:tabletitle width="100" styleId="oracolumdateheader" toolTip="Submit  Time">Submit  Time</html:tabletitle>
              <html:tabletitle width="90" styleId="oracolumnnumberheader" toolTip="Waiting (Hour)">Waiting (Hour)</html:tabletitle>
              <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Check">Check</html:tabletitle>
            </html:tablehead>
            <html:tablebody styleId="tableDataStyle" height="180" id="searchTable">
              <logic:equal value="0" name="webVo" property="workingProcessSize">
                <tr class="oracletdone" ><td class="OraCellText" colspan="6" >(No application)</td></tr>
              </logic:equal>
              <logic:notEqual value="0" name="webVo" property="workingProcessSize">
                <html:form action="/wf/WorkProcessHandle" id="WorkForm">
                  <input type="hidden" name="submitAll" value="true" />
                <logic:iterate id="workingProcess" name="webVo" property="workingProcessList" indexId="indexId">
                  <bean:define id="rid" name="workingProcess" property="rid" />
                  <bean:define id="status" name="workingProcess" property="status" />
                  <bean:define id="currActivityRid" name="workingProcess" property="currActivityRid" />
                  <%String selfPro = "wkRid='" + rid + "' status='" + status + "' currActivityRid='"+ currActivityRid +"'";%>
                  <html:tablerow
                  styleId="<%=indexId.intValue()%2==1?"tableDataOdd2":"tableDataEven2"%>"
                  id="<%="tr" + indexId.intValue()%>"
                  onclick="onclickWorkingProcess(this);"
                  ondblclick="onclickWorkingProcess(this);handleWorkingProcess();"
                  selfProperty="<%=selfPro%>"
                  height="18"
                  >
                  <bean:define id="loginId"  name="workingProcess" property="subEmpLoginid"/>
                  <%String userName=HrFactory.create().getName((String)loginId);%>
                  <html:tablecolumn styleId="oracelltext" toolTip="<%=loginId+"/"+userName%>">
                    <%=userName%>
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="workingProcess" property="processName" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="workingProcess" property="description" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelldate">
                    <bean:date name="workingProcess" property="currActivityStart" fmt="yyyy/MM/dd HH:mm" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="workingProcess" property="waitingHours" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <input type="checkbox" class="checkbox" name="wkRid" checked="checked"  value="<%=rid%>" />
                  </html:tablecolumn>
                    <input type="hidden" name="currActivityRid<%=rid%>" value="<%=currActivityRid%>" />
                  </html:tablerow>
                </logic:iterate>
              </html:form>
              </logic:notEqual>
            </html:tablebody>
          </html:table>
        </html:outborder>
      </html:tabcontent>
    </html:tabcontents>
  </html:tabpanel>
  </td>
</tr>
<tr>
  <td height="245">
  <html:tabpanel id="OverTimeDetailList" width="100%">
    <html:tabtitles>
      <html:tabtitle selected="true" width="105">
        <center><a class="tabs_title">My Request In Process</a></center>
      </html:tabtitle>
    </html:tabtitles>
    <html:tabbuttons>
      <input type="button" name="confirm" value="Confirm" class="button" onclick="viewRequestProcess();" />
    </html:tabbuttons>
    <html:tabcontents>
      <html:tabcontent styleClass="wind">
        <html:outborder height="5%" width="100%">
          <html:table styleId="tableStyle">
            <%-- head --%>
            <html:tablehead styleId="tableTitleStyle">
              <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="Processing">Processing</html:tabletitle>
              <html:tabletitle width="140" styleId="oracolumntextheader" toolTip="Process Name">Process Name</html:tabletitle>
              <html:tabletitle width="480" styleId="oracolumntextheader" toolTip="Description">Description</html:tabletitle>
              <html:tabletitle width="100" styleId="oracolumdateheader" toolTip="Submit  Time">Submit  Time</html:tabletitle>
              <html:tabletitle width="90" styleId="oracolumnnumberheader" toolTip="Waiting (Hour)">Waiting (Hour)</html:tabletitle>
              <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Status">Status</html:tabletitle>
            </html:tablehead>
            <html:tablebody styleId="tableDataStyle" height="180" id="searchTable">
              <logic:equal value="0" name="webVo" property="requestProcessSize">
                <tr class="oracletdone" ><td class="OraCellText" colspan="6" >(No application)</td></tr>
              </logic:equal>
              <logic:notEqual value="0" name="webVo" property="requestProcessList">
                <logic:iterate id="requestProcess" name="webVo" property="requestProcessList" indexId="indexId">
                  <bean:define id="rid" name="requestProcess" property="rid" />
                    <bean:define id="status" name="requestProcess" property="status" />
                  <%String selfPro = "wkRid='" + rid + "' status='" + status + "'";%>
                  <html:tablerow
                  styleId="<%=indexId.intValue()%2==1?"tableDataOdd2":"tableDataEven2"%>"
                  id="<%="tr" + indexId.intValue()%>"
                  onclick="onclickRequestProcess(this);"
                  ondblclick="viewRequestProcess();"
                  selfProperty="<%=selfPro%>"
                  height="18"
                  >
                  <%String showTitle = "";
                    String showName = "";%>
                  <%-- 流程已结束则显示申请人,否则显示当前Activity的处理人 --%>
                    <logic:equal value="finish" name="requestProcess" property="status" >
                      <bean:define id="loginId"  name="requestProcess" property="subEmpLoginid"/>
                      <%showName = HrFactory.create().getName((String)loginId);
                        showTitle = loginId + "/" + showName;%>
                    </logic:equal>
                    <logic:notEqual value="finish" name="requestProcess" property="status" >
                      <%String name = " ";%>
                      <logic:iterate id="currActivity" name="requestProcess" property="currActivityList">
                        <bean:define id="performer" name="currActivity" property="performerLoginid" />
                        <%
                        String performerName = HrFactory.create().getName((String)performer);
                        name += (performerName + ",");
                        if("".equals(showTitle)) {
                          showTitle += (performer + "/" + performerName);
                        } else {
                          showTitle += ("," + performer + "/" + performerName);
                        }
                        %>
                      </logic:iterate>
                      <% showName = name.substring(0,name.length() - 1);%>
                    </logic:notEqual>
                  <html:tablecolumn styleId="oracelltext" toolTip="<%=showTitle%>">
                    <%=showName%>
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="requestProcess" property="processName" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="requestProcess" property="description" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelldate">
                    <bean:date name="requestProcess" property="startDate" fmt="yyyy/MM/dd HH:mm" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracellnumber">
                    <bean:write name="requestProcess" property="waitingHours" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="requestProcess" property="status" />
                  </html:tablecolumn>
                  </html:tablerow>
                </logic:iterate>
              </logic:notEqual>
            </html:tablebody>
          </html:table>
        </html:outborder>
      </html:tabcontent>
    </html:tabcontents>
  </html:tabpanel>
</td>
</tr>
</table>
</body>
