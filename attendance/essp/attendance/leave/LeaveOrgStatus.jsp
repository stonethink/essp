<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="itf.hr.HrFactory"%>
<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<html>
<head>
 <tiles:insert page="/layout/head.jsp">
        <tiles:put name="title" value="Organization Leave Status" />
        <tiles:put name="jspName" value="" />
 </tiles:insert>
 <script language="JavaScript" type="text/javascript">
var currentRowObj;
<%----------------------------------------------------------------------------%>
function onclickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	changeRowColor(rowObj);
    }catch( ex ){
    }
}
</script>
</head>
<table cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td height="130">
      <html:tabpanel id="Form" width="100%" >
        <html:tabtitles  selectedindex="1"  >
          <html:tabtitle width="80"><span class="tabs_title">Leave Status</span></html:tabtitle>
        </html:tabtitles>
        <html:tabbuttons>
          <div id="btnPanel">
          </div>
        </html:tabbuttons>
        <html:tabcontents>
            <%--THE 1st Card--%>
            <html:tabcontent styleClass="wind">
                <html:outborder width="100%" height="100%">
                  <html:form action="/attendance/leave/LeaveOrgStatus">
                  <table cellspacing="1" cellpadding="1" width="100%" align="center">
                    <tr>
                      <td class="list_range" width="15%">Dept</td>
                      <td class="list_range" width="29%">
                        <html:select beanName="webVo" name="orgId" styleId="" >
                          <html:optionsCollection name="webVo" property="allOrg" >
                          </html:optionsCollection>
                        </html:select>
                      </td>
                      <td class="list_range" width="55%"></td>
                    </tr>
                    <tr>
                      <td class="list_range" >Include Sub OB</td>
                      <td class="list_range" >
                        <html:checkbox name="includeSub" beanName="webVo" checkedValue="true" defaultValue="false" uncheckedValue="false">
                        </html:checkbox>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td class="list_range" >Min Usable Hours</td>
                      <td class="list_range" >
                        <html:text name="minUsableHours" beanName="webVo" fieldtype="number" fmt="8.2" styleId="input_common">
                        </html:text>
                      </td>
                       <td></td>
                    </tr>
                    <tr>
                      <td class="list_range" ></td>
                      <td  align="right" >
                        <input type="submit" name="submit" value="Submit" class="button" />
                        <input type="reset" name="reset" value="Reset" class="button" />
                      </td>
                       <td></td>
                    </tr>
                  </table>
                  </html:form>
                </html:outborder>
            </html:tabcontent>
          </html:tabcontents>
        </html:tabpanel>
      </td>
    </tr>
    <tr>
      <td height="20"></td>
    </tr>
    <tr>
      <td height="280">
        <html:tabpanel id="tabDetail" width="100%" >
          <html:tabtitles  selectedindex="1"  >
            <html:tabtitle width="80"><span class="tabs_title">Works Status</span></html:tabtitle>
          </html:tabtitles>
          <html:tabbuttons>
          </html:tabbuttons>
          <html:tabcontents>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%">
                <html:table styleId="tableStyle">
                <%-- head --%>
                <html:tablehead styleId="tableTitleStyle">
                  <html:tabletitle width="80" styleId="oracolumntextheader" toolTip="Worker">Worker</html:tabletitle>
                  <html:tabletitle width="70" styleId="oracolumntextheader" ></html:tabletitle>
                  <logic:iterate id="leaveType" name="webVo" property="leaveType">
                    <bean:define id="leaveType2" name="leaveType" type="server.essp.attendance.leave.viewbean.VbLeaveType" />
                    <html:tabletitle width="50" styleId="oracolumnnumberheader" toolTip="<%=leaveType2.getLeaveName()%>"><bean:write name="leaveType" property="leaveName"/></html:tabletitle>
                  </logic:iterate>
                </html:tablehead>
                <%-- data --%>
                <html:tablebody styleId="tableDataStyle" height="275" id="searchTable">
                  <logic:iterate id="workerStatus" name="webVo" property="workersStatus" indexId="indexId">
                    <html:tablerow
                      styleId="<%=indexId.intValue()%2==1?"tableDataOdd":"tableDataEven"%>"
                      height="18"
                      onclick="onclickRow(this);">
                      <bean:define id="loginId" name="workerStatus" property="loginId" />
                      <%String userName=HrFactory.create().getName((String)loginId);%>
                      <html:tablecolumn styleId="oracelltext" toolTip="<%=loginId+"/"+userName%>">
                        <%=userName%>
                      </html:tablecolumn>

                      <html:tablecolumn styleId="oracelltext">
                        Usable (Hours)
                      </html:tablecolumn>
                      <logic:iterate id="usable" name="workerStatus" property="usableList">
                        <html:tablecolumn styleId="oracellnumber">
                          <bean:write name="usable" />
                        </html:tablecolumn>
                      </logic:iterate>
                    </html:tablerow>
                    <html:tablerow
                      styleId="<%=indexId.intValue()%2==1?"tableDataOdd":"tableDataEven"%>"
                      height="18"
                      onclick="onclickRow(this);">
                      <html:tablecolumn styleId="oracelltext">
                        <%--bean:write name="workerStatus" property="loginId" /--%>
                      </html:tablecolumn>
                      <html:tablecolumn styleId="oracelltext">
                        Used (Hours)
                      </html:tablecolumn>
                      <logic:iterate id="used" name="workerStatus" property="usedList">
                        <html:tablecolumn styleId="oracellnumber">
                          <bean:write name="used" />
                        </html:tablecolumn>
                      </logic:iterate>
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
</table>
