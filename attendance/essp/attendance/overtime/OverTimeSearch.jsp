<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="OverTimeSearch"/>
  <tiles:put name="jspName" value="OverTimeSearch"/>
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
function onSearch(){
 AfOvertimeSearch.submit();
}
</script>
</head>
<body >
    <html:tabpanel id="condition" width="98%">
        <%-- card title--%>
        <html:tabtitles>
            <html:tabtitle selected="true" width="80">
                <center><a class="tabs_title">Search</a></center>
            </html:tabtitle>
        </html:tabtitles>
        <%-- card buttons--%>
  <html:tabbuttons>
    <html:form action=".">

      <input type="button" name="search" value="Search"  onclick="onSearch();" class="button" />
      <%-- input type="button" name="add" value="Application"  onclick="onAppOverTime()" class="button" / --%>
    </html:form>
  </html:tabbuttons>
        <%-- card --%>
        <html:tabcontents>
          <html:tabcontent styleClass="wind">
            <html:outborder height="8%" width="98%">
              <html:form id="AfOvertimeSearch" action="/attendance/overtime/OverTimeSearch" method="POST" target="cardFrm">
                <table width="600" border="0" cellspacing="0" cellpadding="0" align="center">
                <tr style="height:15px"><td></td><td></td></tr>
                  <tr>
                    <td height="20" colspan="6">
                    <bean:define id="orgIds" name="orgIds" type="String" />
                      <table width="100%" cellpadding="0" cellspacing="0">
                        <tr>
                          <td height="30" align="left" class="list_range">                Department
                              <ws:select id="depart" property="depart_code" type="server.essp.tc.outwork.logic.DepartmentSelectImpl" onchange="depart_codeSelectChangeEventaccount('null')" orgIds="<%=orgIds%>" default="-1" style="width:200px" includeall="true"/>
                          </td>
                          <td>              </td>
                          <td align="right" class="list_range">                Account
                            <ws:upselect id="account" property="account_id" up="depart_code" type="server.essp.tc.outwork.logic.AccountSelectImpl" onchange="" orgIds="<%=orgIds%>" style="width:200px"/>
                          </td>
                        </tr>
                      </table>
</td>
                  </tr>
                  <tr>
                    <td height="30" class="list_range">BeginDate</td>
                    <td width="180">
                      <html:text name="beginDate" beanName="webVo" fieldtype="dateyyyymmdd" styleId="input_date" prev="" next="" ondblclick="getDATE(this);"/>
                    </td>
                    <td class="list_range">EndDate</td>
                    <td width="180" class="list_range">
                      <html:text name="endDate" beanName="webVo" fieldtype="dateyyyymmdd" styleId="input_date" prev="" next="" ondblclick="getDATE(this);"/>
                    </td>
                    <td class="list_range">Employee</td>
                    <td>
                      <html:text name="empName" beanName="webVo" fieldtype="text" styleId=""/>
                    </td>
                  </tr>
                  <tr>
                    <td height="5" class="list_range">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td class="list_range">&nbsp;</td>
                    <td class="list_range">&nbsp;</td>
                    <td class="list_range">&nbsp;</td>
                    <td>&nbsp;</td>
                  </tr>
                </table>
              </html:form>
            </html:outborder>

            <table align="center" width="100%" cellSpacing="0" cellPadding="0" >
              <tr ><td height="500">
                  <IFRAME id="cardFrm" name="cardFrm" SRC='<%=request.getContextPath()%>/attendance/overtime/OverTimeSearchResult.jsp'
                  width="100%" height="100%" frameborder="no" border="0"
                  MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
                  </IFRAME>
</td></tr>
            </table>
          </html:tabcontent>
        </html:tabcontents>
    </html:tabpanel>
</body>
</html>
