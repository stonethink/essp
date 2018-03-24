<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="server.essp.attendance.leave.viewbean.VbLeave" %>
<%@page import="itf.hr.HrFactory"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="LeaveList"/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<script language="JavaScript">
var currentRowObj;
<%----------------------------------------------------------------------------%>
<%-- 单击事件 --%>
function onclickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	changeRowColor(rowObj);
    }catch( ex ){
    }
}
<%-- 双击事件 --%>
function ondbClickRow(rowObj){
  onclickRow(rowObj);
  var height = 650;
  var width = 520;
  var topis = (screen.height - height) / 2;
  var leftis = (screen.width - width) / 2;
  var option = "height=" + height + "px"
                +", width=" + width + "px"
                +", top=" + topis + "px"
                +", left=" + leftis + "px"
                +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
  var url = "<%=request.getContextPath()%>/attendance/leave/LeaveView.do?rid=" + rowObj.rid;
  window.open(url, "", option);
}
<%-- 查看请假记录 --%>
function onViewLeave(){
  if(currentRowObj == null){
    return;
  }
  ondbClickRow(currentRowObj);
}
<%-- 修改流程已完成的请假(销假) --%>
function onModifyLeave(){
  if(currentRowObj == null){
    return;
  }
  if(currentRowObj.status != "Finished"){
    alert("Can not modify unfinished leave!");
    return;
  }
//  alert(currentRowObj.rid);
  var height = 480;
  var width = 530;
  var topis = (screen.height - height) / 2;
  var leftis = (screen.width - width) / 2;
  var option = "height=" + height + "px"
                +", width=" + width + "px"
                +", top=" + topis + "px"
                +", left=" + leftis + "px"
                +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
  var url = "<%=request.getContextPath()%>/attendance/modify/ModifyLeavePre.do?rid=" + currentRowObj.rid;
  window.open(url, "", option);
}
<%-- 请假申请 --%>
function onAppLeave(){
    var height = 365;
    var width = 520;
    var topis = (screen.height - height) / 2;
    var leftis = (screen.width - width) / 2;
    var option = "height=" + height + "px"
                +", width=" + width + "px"
                +", top=" + topis + "px"
                +", left=" + leftis + "px"
                +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
    var url = "<%=request.getContextPath()%>/attendance/leave/LeaveAppPre.do";
    window.open(url, "", option);
}
</script>
</head>
<body >
    <html:tabpanel id="LeaveList" width="98%">
        <%-- card title--%>
        <html:tabtitles>
            <html:tabtitle selected="true" width="105">
                <center><a class="tabs_title">Leave Record List</a></center>
            </html:tabtitle>
        </html:tabtitles>
        <%-- card buttons--%>
        <html:tabbuttons>
          <input type="button" class="button" name="modify" value="Modify" onclick="onModifyLeave()"  />
          <input type="button" class="button" name="view" value="View" onclick="onViewLeave()"  />
          <input type="button" class="button" name="add" value="Application" onclick="onAppLeave()"  />
        </html:tabbuttons>
        <%-- card --%>
        <html:tabcontents>
            <html:tabcontent styleClass="wind">
                <html:outborder height="40%" width="98%">
                    <html:table styleId="tableStyle">
                         <%-- head --%>
                        <html:tablehead styleId="tableTitleStyle">
                            <html:tabletitle width="110" styleId="oracolumntextheader" toolTip="Worker">Worker</html:tabletitle>
                            <html:tabletitle width="110" styleId="oracolumntextheader" toolTip="Leave Type">Leave Type</html:tabletitle>
                            <html:tabletitle width="*" styleId="oracolumndateheader" toolTip="From">From</html:tabletitle>
                            <html:tabletitle width="*" styleId="oracolumndateheader" toolTip="To">To</html:tabletitle>
                            <html:tabletitle width="*" styleId="oracolumnnumberheader" toolTip="Total (Hours)">Total (Hours)</html:tabletitle>
                            <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Status">Status</html:tabletitle>
                        </html:tablehead>
                        <html:tablebody styleId="tableDataStyle" height="370" id="searchTable">
                           <logic:present name="webVo" scope="request">
                             <logic:iterate id="leave" name="webVo" property="leaveList" indexId="indexId">
                               <%
                               VbLeave vBleave = (VbLeave)leave;
                               String selfPro = "rid='"+vBleave.getRid()+"' status='"+vBleave.getStatus()+"'";
                               %>
                               <html:tablerow
                                 styleId="<%=indexId.intValue()%2==1?"tableDataOdd2":"tableDataEven2"%>"
                                 id="<%="tr" + indexId.intValue()%>"
                                 onclick="onclickRow(this);"
                                 ondblclick="ondbClickRow(this);"
                                 height="18"
                                 selfProperty="<%=selfPro%>"
                                 >
                                 <bean:define id="loginId" name="leave" property="loginId"/>
                                 <%String userName=HrFactory.create().getName((String)loginId);%>
                                 <html:tablecolumn styleId="oracelltext" toolTip="<%=loginId+"/"+userName%>">
                                   <%=userName%>
                                 </html:tablecolumn>
                                 <html:tablecolumn styleId="oracelltext" toolTip="">
                                   <bean:write name="leave" property="leaveName"/>
                                 </html:tablecolumn>
                                 <html:tablecolumn styleId="oracelldate" toolTip="">
                                   <bean:write name="leave" property="dateFrom"/>  <bean:write name="leave" property="timeFrom"/>
                                 </html:tablecolumn>
                                 <html:tablecolumn styleId="oracelldate" toolTip="">
                                   <bean:write name="leave" property="dateTo"/>  <bean:write name="leave" property="timeTo"/>
                                 </html:tablecolumn>
                                 <html:tablecolumn styleId="oracellnumber" toolTip="">
                                   <bean:write name="leave" property="actualTotalHours"/>
                                 </html:tablecolumn>
                                 <html:tablecolumn styleId="oracelltext" toolTip="">
                                   <bean:write name="leave" property="status"/>
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
