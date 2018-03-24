<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="itf.hr.HrFactory"%>

<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="LeaveSum"/>
  <tiles:put name="jspName" value="LeaveApp"/>
</tiles:insert>
<script language="JavaScript">
var currentRowObj;
<%----------------------------------------------------------------------------%>
function onclickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	changeRowColor(rowObj);
    }catch( ex ){
    }
}
function onChangeYear(year){
//  alert(year);
  var url = "<%=request.getContextPath()%>/attendance/leave/LeaveStatus.do?year="+year;
  location=url;
}
</script>
</head>
<body >
    <html:tabpanel id="LeaveList" width="98%">
        <%-- card title--%>
        <html:tabtitles>
            <html:tabtitle selected="true" width="80">
                <center><a class="tabs_title">Leave Sum</a></center>
            </html:tabtitle>
        </html:tabtitles>
        <%-- card buttons--%>
        <html:tabbuttons>
          <html:form action=".">
            <html:select name="selectedYear" beanName="webVo" styleClass="project_select2"  styleId="" next="Sequence" prev="scope" onchange="onChangeYear(this.value);">
              <html:optionsCollection name="webVo" property="yearList">
              </html:optionsCollection>
            </html:select>
          </html:form>
        </html:tabbuttons>
        <%-- card --%>
        <html:tabcontents>
            <html:tabcontent styleClass="wind">
                <html:outborder height="8%" width="98%">
                    <html:table styleId="tableStyle">
                         <%-- head --%>
                        <html:tablehead styleId="tableTitleStyle">
                            <html:tabletitle width="80" styleId="oracolumntextheader" toolTip="Worker">Worker</html:tabletitle>
                            <html:tabletitle width="70" styleId="oracolumntextheader"  ></html:tabletitle>
                            <logic:iterate id="leaveType" name="webVo" property="leaveTypeList">
                              <bean:define id="leaveType2" name="leaveType" type="server.essp.attendance.leave.viewbean.VbLeaveType" />
                              <html:tabletitle width="50" styleId="oracolumnnumberheader" toolTip="<%=leaveType2.getLeaveName()%>" ><bean:write name="leaveType" property="leaveName"/></html:tabletitle>
                            </logic:iterate>
                        </html:tablehead>
                        <html:tablebody styleId="tableDataStyle" height="40" id="searchTable">
                          <html:tablerow styleId="tableDataOdd" height="18" onclick="onclickRow(this);">
                            <bean:define id="loginId" name="webVo" property="loginId" />
                            <%String userName=HrFactory.create().getName((String)loginId);%>
                            <html:tablecolumn styleId="oracelltext" toolTip="<%=loginId+"/"+userName%>">
                              <%=userName%>
                            </html:tablecolumn>
                            <html:tablecolumn styleId="oracelltext">
                              Usable (Hours)
                            </html:tablecolumn>
                            <logic:iterate id="usable" name="webVo" property="usableList">
                              <html:tablecolumn styleId="oracellnumber">
                                <bean:write name="usable" />
                              </html:tablecolumn>
                            </logic:iterate>
                          </html:tablerow>

                          <html:tablerow styleId="tableDataEven" height="18" onclick="onclickRow(this);">
                            <bean:define id="loginId" name="webVo" property="loginId" />
                            <%String userName=HrFactory.create().getName((String)loginId);%>
                            <html:tablecolumn styleId="oracelltext" toolTip="<%=loginId+"/"+userName%>">
                              <%=userName%>
                            </html:tablecolumn>
                            <html:tablecolumn styleId="oracelltext">
                              Used (Hours)
                            </html:tablecolumn>
                            <logic:iterate id="used" name="webVo" property="usedList">
                              <html:tablecolumn styleId="oracellnumber">
                                <bean:write name="used" />
                              </html:tablecolumn>
                            </logic:iterate>
                          </html:tablerow>
                        </html:tablebody>
                    </html:table>
                </html:outborder>

                <table align="center" width="100%" cellSpacing="0" cellPadding="0" >
                  <tr ><td height="430">
                    <logic:notEqual name="detailSize" value="0">
                      <IFRAME id="cardFrm" name="cardFrm" SRC='<%=request.getContextPath()%>/attendance/leave/LeaveList.do?year=<bean:write name="webVo" property="selectedYear"/>'
                        width="100%" height="100%" frameborder="no" border="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
                      </IFRAME>
                    </logic:notEqual>
                  </td></tr>
                </table>
            </html:tabcontent>
        </html:tabcontents>
    </html:tabpanel>
</body>
</html>
