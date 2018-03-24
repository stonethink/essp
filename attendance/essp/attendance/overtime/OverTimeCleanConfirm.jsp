<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="OverTime List"/>
  <tiles:put name="jspName" value="OverTimeCleanConfirm"/>
</tiles:insert>
<script language="javaScript" type="text/javascript">
var currentRowObj;
<%----------------------------------------------------------------------------%>
function onclickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	changeRowColor(rowObj);
        if(rowObj.status == "tableDataWarn") {
          rowObj.style.backgroundColor = "E86AE0";
        }
    }catch( ex ){
    }
}
function onSubmit(){
  imForm.submit();
}
</script></head>
<%
String subDisable = "false";
String hasRow = "false";
%>
<body>
<html:form id="imForm" action="/attendance/overtime/OverTimeClean" method="POST">
<html:tabpanel id="OverTimeList" width="98%">
  <%-- card title--%>
  <html:tabtitles>
    <html:tabtitle selected="true" width="80">
      <center>
        <a class="tabs_title">Confirm</a>
      </center>
    </html:tabtitle>
  </html:tabtitles>
  <html:tabbuttons>
    <input type="button" name="Submit" value="Submit" disabled="disabled" onclick="onSubmit();" class="button" />
  </html:tabbuttons>
  <html:tabcontents>
    <html:tabcontent styleClass="wind">
      <html:outborder height="5%" width="98%">
        <html:table styleId="tableStyle">
          <%-- head --%>
          <html:tablehead styleId="tableTitleStyle">
            <html:tabletitle width="150" styleId="oracolumntextheader" toolTip="LoginId">LoginId</html:tabletitle>
            <html:tabletitle width="150" styleId="oracolumntextheader" toolTip="Name">Name</html:tabletitle>
            <html:tabletitle width="200" styleId="oracolumntextheader" toolTip="Organization">Organization</html:tabletitle>
            <html:tabletitle width="*" styleId="oracolumntextheader" toolTip="Usable Hours">Usable Hours</html:tabletitle>
            <html:tabletitle width="170" styleId="oracolumntextheader" toolTip="Clean Hours">Clean Hours</html:tabletitle>

          </html:tablehead>
          <html:tablebody styleId="tableDataStyle" height="470" id="searchTable">
            <logic:present  name="overTimeCleanList" scope="session">
              <logic:iterate id="overTime" name="overTimeCleanList" scope="session" indexId="indexId">
                <bean:define id="isEnoughHours" name="overTime" property="isEnoughHours" />
                <%
                String style = indexId.intValue()%2==1?"tableDataOdd":"tableDataEven";
                hasRow = "true";
                if((Boolean)isEnoughHours) {
                  style = "tableDataWarn";
                  subDisable = "true";
                }
                %>
                <html:tablerow
                  styleId="<%=style%>"
                  id="<%="tr" + indexId.intValue()%>"
                  onclick="onclickRow(this);"
                  selfProperty="<%="status="+style%>"
                  height="18" >
                  <html:tablecolumn styleId="oracelltext">
                  <bean:write name="overTime" property="loginId" />
                  </html:tablecolumn>
                   <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="name" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="orgName" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="usableHours" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="cleanHours" />
                  </html:tablecolumn>
                </html:tablerow>
              </logic:iterate>
            </logic:present>
            <logic:present  name="overTimeCleanList" scope="request">
              <logic:iterate id="overTime" name="overTimeCleanList" scope="request" indexId="indexId">
                <bean:define id="isEnoughHours" name="overTime" property="isEnoughHours" />
                <%
                String style = indexId.intValue()%2==1?"tableDataOdd":"tableDataEven";
                hasRow = "true";
                if((Boolean)isEnoughHours) {
                  style = "tableDataWarn";
                  subDisable = "true";
                }
                %>
                <html:tablerow
                  styleId="<%=style%>"
                  id="<%="tr" + indexId.intValue()%>"
                  onclick="onclickRow(this);"
                  selfProperty="<%="status="+style%>"
                  height="18" >
                  <html:tablecolumn styleId="oracelltext">
                  <bean:write name="overTime" property="loginId" />
                  </html:tablecolumn>
                   <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="name" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="orgName" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="usableHours" />
                  </html:tablecolumn>
                  <html:tablecolumn styleId="oracelltext">
                    <bean:write name="overTime" property="cleanHours" />
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
</html:form>
<script language="javaScript" type="text/javascript">
var disableSubmit = true;
if("true" == "<%=hasRow%>") {
  disableSubmit = <%=subDisable%>;
} else {
  disableSubmit = true;
}
<logic:notPresent  name="overTimeCleanList" scope="session">
disableSubmit = true;
</logic:notPresent>
document.getElementsByName('Submit')[0].disabled = disableSubmit;
</script>
</body>
</html>
