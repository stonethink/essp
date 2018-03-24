<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="server.essp.attendance.leave.viewbean.VbLeaveReviewLog" %>
<%@page import="itf.hr.HrFactory"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="LeaveLog"/>
  <tiles:put name="jspName" value="LeaveLog"/>
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

</script>
</head>
<body >
    <html:tabpanel id="OverTimeLogList" width="100%">
        <%-- card title--%>
        <html:tabtitles>
            <html:tabtitle selected="true" width="47">
                <center><a class="tabs_title">Log</a></center>
            </html:tabtitle>
        </html:tabtitles>
        <%-- card buttons--%>
        <html:tabbuttons>
        </html:tabbuttons>
        <%-- card --%>
        <html:tabcontents>
            <html:tabcontent styleClass="wind">
                <html:outborder height="5%" width="100%">
                    <html:table styleId="tableStyle">
                         <%-- head --%>
                         <html:tablehead styleId="tableTitleStyle">
                             <html:tabletitle width="60" styleId="oracolumntextheader" toolTip="Deal Date">Deal Date</html:tabletitle>
                             <html:tabletitle width="70" styleId="oracolumntextheader" toolTip="Auditor">Auditor</html:tabletitle>
                             <html:tabletitle width="200" styleId="oracolumntextheader" toolTip="Status">Status</html:tabletitle>
                             <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Remark">Remark</html:tabletitle>
                         </html:tablehead>
                         <html:tablebody styleId="tableDataStyle" height="60" id="searchTable">
                           <logic:present name="webVo">
                             <logic:iterate id="log" name="webVo" indexId="indexId">
                               <%
                               VbLeaveReviewLog reviewLog = (VbLeaveReviewLog)log;
                               %>
                               <html:tablerow
                                 styleId="<%=indexId.intValue()%2==1?"tableDataOdd2":"tableDataEven2"%>"
                                 id="<%="tr" + indexId.intValue()%>"
                                 onclick="onclickRow(this);"
                                 height="18"
                                 >
                                 <html:tablecolumn styleId="oracelltext" toolTip="<%=reviewLog.getLogDate().toString()%>">
                                   <bean:date name="log" property="logDate"/>
                                 </html:tablecolumn>
                                 <bean:define id="loginId" name="log" property="loginId"/>
                                 <%String userName=HrFactory.create().getName((String)loginId);%>
                                 <html:tablecolumn styleId="oracelltext" toolTip="<%=loginId+"/"+userName%>">
                                   <%=userName%>
                                 </html:tablecolumn>
                                 <html:tablecolumn styleId="oracelltext" toolTip="<%=reviewLog.getDeal()%>">
                                   <bean:write name="log" property="deal"/>
                                 </html:tablecolumn>
                                 <html:tablecolumn styleId="oracelltext" toolTip="<%=reviewLog.getComments()%>">
                                   <bean:write name="log" property="comments"/>
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
