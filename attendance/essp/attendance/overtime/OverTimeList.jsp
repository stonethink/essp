<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="itf.hr.HrFactory"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="OverTime List"/>
  <tiles:put name="jspName" value="OverTimeApp"/>
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

function ondbClick(rowObj){
  onclickRow(rowObj);
  var rid = rowObj.rid;
//  alert(rid);
  var url = "<%=request.getContextPath()%>/attendance/overtime/OverTimeView.do?rid="+rid
  var option = "Top=30,Left=120,Height=650,Width=600,toolbar=no,resizable=yes,scrollbars=no,status=yes";
  window.open(encodeURI(url),"",option);
}

function onViewOverTime(){
  if(currentRowObj == null){
    return;
  }
  ondbClick(currentRowObj)
}

function onAppOverTime(){
  var url = "<%=request.getContextPath()%>/attendance/overtime/OverTimeAppPre.do";
  var option = "Top=30,Left=120,Height=500,Width=600,toolbar=no,resizable=yes,scrollbars=no,status=yes";
  window.open(encodeURI(url),"",option);
}
function onChangeYear(year){
//  alert(year);
  var url = "<%=request.getContextPath()%>/attendance/overtime/OverTimeList.do?year="+year;
  location=url;
}
</script></head>
<body>
<html:tabpanel id="OverTimeList" width="98%">
  <%-- card title--%>
  <html:tabtitles>
    <html:tabtitle selected="true" width="120">
      <center>
        <a class="tabs_title">OverTime Record</a>
      </center>
    </html:tabtitle>
  </html:tabtitles>
  <%-- card buttons--%>
  <html:tabbuttons>
    <html:form action=".">
      <html:select beanName="webVo" name="selectedYear" styleId="project_select2" styleClass="project_select2" onchange="onChangeYear(this.value)" >
        <html:optionsCollection property="yearList" name="webVo">
        </html:optionsCollection>
      </html:select>
      <input type="button" name="view" value="View"  onclick="onViewOverTime()" class="button" />
      <%-- input type="button" name="add" value="Application"  onclick="onAppOverTime()" class="button" / --%>
    </html:form>
  </html:tabbuttons>
  <html:tabcontents>
    <html:tabcontent styleClass="wind">
      <html:outborder height="5%" width="98%">
        <html:table styleId="tableStyle">
          <%-- head --%>
          <html:tablehead styleId="tableTitleStyle">
            <html:tabletitle width="80" styleId="oracolumntextheader" toolTip="Worker">Worker</html:tabletitle>
            <html:tabletitle width="170" styleId="oracolumntextheader" toolTip="Account">Account</html:tabletitle>
            <html:tabletitle width="110" styleId="oracolumndateheader" toolTip="From">From</html:tabletitle>
            <html:tabletitle width="110" styleId="oracolumndateheader" toolTip="To">To</html:tabletitle>
            <html:tabletitle width="95" styleId="oracolumnnumberheader" toolTip="OverTime Hours">OverTime Hours</html:tabletitle>
            <html:tabletitle width="80" styleId="oracolumnnumberheader" toolTip="Shifted Hours">Shifted Hours</html:tabletitle>
            <html:tabletitle width="75" styleId="oracolumnnumberheader" toolTip="Payed Hours">Payed Hours</html:tabletitle>
            <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Status">Status</html:tabletitle>
          </html:tablehead>
          <html:tablebody styleId="tableDataStyle" height="470" id="searchTable">
            <logic:present  name="webVo" scope="request">
              <logic:iterate id="overTime" name="webVo" property="overTimeList" indexId="indexId">
                <bean:define id="rid" name="overTime" property="rid" />
                <%
                String selfProperty = "rid=" + rid;
                %>
                <html:tablerow
                  styleId="<%=indexId.intValue()%2==1?"tableDataOdd2":"tableDataEven2"%>"
                  id="<%="tr" + indexId.intValue()%>"
                  onclick="onclickRow(this);"
                  ondblclick="ondbClick(this);"
                  selfProperty="<%=selfProperty%>"
                  height="18">
                  <bean:define id="loginId" name="overTime" property="loginId" />
                  <%String userName=HrFactory.create().getName((String)loginId);%>
                  <html:tablecolumn styleId="oracelltext" toolTip="<%=loginId+"/"+userName%>">
                    <%=userName%>
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="accountName" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="actualDateFrom" /> &nbsp;&nbsp;
                    <bean:write name="overTime" property="actualTimeFrom" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="actualDateTo" /> &nbsp;&nbsp;
                    <bean:write name="overTime" property="actualTimeTo" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracellnumber">
                    <bean:write name="overTime" property="actualTotalHours" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracellnumber">
                    <bean:write name="overTime" property="shiftHours" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracellnumber">
                    <bean:write name="overTime" property="payedHours" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="status" />
                  </html:tablecolumn>
                </html:tablerow>
              </logic:iterate>
            </logic:present>
          </html:tablebody>
        </html:table>
      </html:outborder>
    </html:tabcontent>
  </html:tabcontents>
</html:tabpanel>
</body>
</html>
