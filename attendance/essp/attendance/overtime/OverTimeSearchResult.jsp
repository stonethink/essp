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
  var url = "/essp/attendance/overtime/OverTimeView.do?rid="+rid
  var option = "Top=30,Left=120,Height=650,Width=600,toolbar=no,resizable=yes,scrollbars=no,status=yes";
  window.open(encodeURI(url),"",option);
}

</script></head>
<body>
<html:tabpanel id="OverTimeList" width="98%">
  <%-- card title--%>
  <html:tabtitles>
    <html:tabtitle selected="true" width="80">
      <center>
        <a class="tabs_title">Result</a>
      </center>
    </html:tabtitle>
  </html:tabtitles>
  <html:tabbuttons>
  </html:tabbuttons>
  <html:tabcontents>
    <html:tabcontent styleClass="wind">
      <html:outborder height="5%" width="98%">
        <html:table styleId="tableStyle">
          <%-- head --%>
          <html:tablehead styleId="tableTitleStyle">
            <html:tabletitle width="80" styleId="oracolumntextheader" toolTip="Worker">Worker</html:tabletitle>
            <html:tabletitle width="150" styleId="oracolumntextheader" toolTip="Organization">Organization</html:tabletitle>
            <html:tabletitle width="170" styleId="oracolumntextheader" toolTip="Account">Account</html:tabletitle>
            <html:tabletitle width="110" styleId="oracolumndateheader" toolTip="From">From</html:tabletitle>
            <html:tabletitle width="110" styleId="oracolumndateheader" toolTip="To">To</html:tabletitle>
            <html:tabletitle width="95" styleId="oracolumnnumberheader" toolTip="OverTime Hours">OverTime Hours</html:tabletitle>
            <html:tabletitle width="80" styleId="oracolumnnumberheader" toolTip="Shifted Hours">Shifted Hours</html:tabletitle>
            <html:tabletitle width="75" styleId="oracolumnnumberheader" toolTip="Payed Hours">Payed Hours</html:tabletitle>
            <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Usable Hours">Usable Hours</html:tabletitle>
          </html:tablehead>
          <html:tablebody styleId="tableDataStyle" height="400" id="searchTable">
            <logic:present  name="webVo" scope="request">
              <logic:iterate id="overTime" name="webVo" indexId="indexId">
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
                  <bean:define id="name" name="overTime" property="empName" />
                  <html:tablecolumn styleId="oracelltext" toolTip="<%=loginId+"/"+name%>">
                    <bean:write name="overTime" property="chineseName" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="orgName" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="acntName" />
                  </html:tablecolumn>

                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="beginDate" format="yyyy-MM-dd  HH:mm" /> &nbsp;&nbsp;

                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="endDate" format="yyyy-MM-dd  HH:mm"/> &nbsp;&nbsp;
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracellnumber">
                    <bean:write name="overTime" property="totalHours" format="#,###,##0.0#" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracellnumber">
                    <bean:write name="overTime" property="shiftHours" format="#,###,##0.0#"  />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracellnumber">
                    <bean:write name="overTime" property="payedHours" format="#,###,##0.0#"  />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracellnumber">
                    <bean:write name="overTime" property="usableHours" format="#,###,##0.0#" />
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
