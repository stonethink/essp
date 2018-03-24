<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>

<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="LeaveType List"/>
  <tiles:put name="jspName" value="LeaveTypeList"/>
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
function ondbclickRow(rowObj){
  onclickRow(rowObj);
  onEdit();
}
function onAdd(){
  var url = "<%=request.getContextPath()%>/attendance/leave/LeaveTypeAddPre.do";
  var option = "Top=200,Left=300,Height=250,Width=480,toolbar=no,resizable=yes,scrollbars=no,status=yes";
  window.open(encodeURI(url),"",option);
}

function onEdit(){
  var leaveName = currentRowObj.leaveName;
  var url = "<%=request.getContextPath()%>/attendance/leave/LeaveTypeUpdatePre.do?leaveName="+leaveName;
  var option = "Top=200,Left=300,Height=250,Width=480,toolbar=no,resizable=yes,scrollbars=no,status=yes";
  window.open(encodeURI(url),"",option);
}
</script>
</head>
<body >
    <html:tabpanel id="LeaveTypeList" width="98%">
        <%-- card title--%>
        <html:tabtitles>
            <html:tabtitle selected="true" width="100">
                <center><a class="tabs_title">Leave Type</a></center>
            </html:tabtitle>
        </html:tabtitles>
        <%-- card buttons--%>
        <html:tabbuttons>
          <input type="button" name="add" value="Add" class="button" onclick="onAdd();"  />
          <input type="button" name="edit" value="Edit" class="button" onclick="onEdit();"  />
        </html:tabbuttons>
        <%-- card --%>
        <html:tabcontents>
            <html:tabcontent styleClass="wind">
                <html:outborder height="40%" width="98%">
                    <html:table styleId="tableStyle">
                         <%-- head --%>
                        <html:tablehead styleId="tableTitleStyle">
                            <html:tabletitle width="150" styleId="oracolumntextheader" toolTip="Type Name">Type Name</html:tabletitle>
                            <html:tabletitle width="110" styleId="oracolumntextheader" toolTip="Settlement Way">Settlement Way</html:tabletitle>
                            <html:tabletitle width="90" styleId="oracolumnnumberheader" toolTip="Max Days">Max Days</html:tabletitle>
                            <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="Relation">Relation</html:tabletitle>
                            <html:tabletitle width="100" styleId="oracolumntextheader" toolTip="Status">Status</html:tabletitle>
                            <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Remark">Remark</html:tabletitle>
                        </html:tablehead>
                        <html:tablebody styleId="tableDataStyle" height="186" id="searchTable">
                          <logic:present name="webVo" scope="request">
                            <logic:iterate id="leaveType" name="webVo" indexId="indexId">
                              <bean:define id="leaveName" name="leaveType" property="leaveName" />
                              <%
                              String selfProperty = "leaveName='" + leaveName + "'";
                              %>
                              <html:tablerow
                              styleId="<%=indexId.intValue()%2==1?"tableDataOdd2":"tableDataEven2"%>"
                              id="<%="tr" + indexId.intValue()%>"
                              onclick="onclickRow(this);"
                              ondblclick="ondbclickRow(this);"
                              selfProperty="<%=selfProperty%>"
                              height="18">
                              <html:tablecolumn styleId="oracelltext">
                                <bean:write name="leaveType" property="leaveName" />
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracelltext">
                                <bean:write name="leaveType" property="settlementWay" />
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracellnumber">
                                <bean:write name="leaveType" property="maxDay" />
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracelltext">
                                <bean:write name="leaveType" property="relation" />
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracelltext">
                                <bean:write name="leaveType" property="status" />
                              </html:tablecolumn>
                              <html:tablecolumn styleId="oracelltext">
                                <bean:write name="leaveType" property="description" />
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
