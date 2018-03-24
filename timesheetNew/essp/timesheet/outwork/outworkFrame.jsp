<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="c2s.essp.common.user.DtoUser"%>
<%@page import="server.essp.timesheet.outwork.viewbean.vbOutWorker"%>
<%@page import="server.essp.timesheet.outwork.form.AfSearchForm"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Outwork"/>
  <tiles:put name="jspName" value="Outwork"/>
</tiles:insert>
<%
  DtoUser user = (DtoUser) session.getAttribute(DtoUser.SESSION_USER);
  String myOrgId = user.getOrgId();
  String orgId = ""; // user.getOrgId();
  if (session.getAttribute("orgIds") != null) {
    orgId = (String) session.getAttribute("orgIds");
  }
  String attendenceManager="false";
  if(request.getSession().getAttribute("ATTENDENCE_MANAGER")!=null){
    attendenceManager="true";
    request.setAttribute("bigRang", "true");
  }
  request.setAttribute("bigRang", "false");
  String begin = "";
  String end = "";
  String departRid = "";
  String acntRid = "";
  if (session.getAttribute("searchCondition") != null) {
    AfSearchForm form = (AfSearchForm) session.getAttribute("searchCondition");
    begin = form.getBeginDate();
    end = form.getEndDate();
    departRid = form.getDepartRid();
    acntRid = form.getAcntRid();
  }
%>
<style type="text/css">
  #input_date
  {
  width:200px;FONT-SIZE: 12px;
  }
</style>
<script language="JavaScript">
function getMyDATE(dateName){
     try{
    	var date = document.getElementById(dateName);
    	date.focus();
    	getDATE(date);
    	} catch(e){}
     }
function sumbitSearch(){
  var departId=searchOutWorker.depart.value;
  var accountId=searchOutWorker.account.value;
  var add='<%=request.getContextPath()%>/timesheet/outwork/outWorkerList.do?isDel=No&departRid='+departId+'&acntRid='+accountId;
  searchOutWorker.action=add;
  searchOutWorker.submit();
}
function delWorker(){
   searchList.delWorker();
}

function addOutWorker(){
searchList.addOutWorker();
}

function modifyOutWorker(){
  searchList.modifyOutWorker();
}
</script>
</head>
<body >
    <html:tabpanel id="condition" width="815">
        <%-- card title--%>
        <html:tabtitles>
            <html:tabtitle selected="true" width="230">
                <center><a class="tabs_title"><bean:message bundle="timesheet" key="outWorker.search.title"/></a></center>
            </html:tabtitle>
        </html:tabtitles>
    <%-- card buttons--%>
     <html:form id="searchOutWorker" action="/timesheet/outwork/outWorkerList.do" method="POST" target="searchList">
    <html:tabbuttons>
        <input type="submit" value='<bean:message bundle="application" key="global.button.search"/>' class="button" onClick="sumbitSearch();"/>
        <input type="reset" value='<bean:message bundle="application" key="global.button.reset"/>' name="Reset" class="button">
    </html:tabbuttons>
      <html:tabcontents>
      <html:tabcontent styleClass="wind">
     <html:outborder height="200" width="815">
    <table width="600" border="0" cellspacing="0" cellpadding="0">
     <tr height="20"><td height="20"></td></tr>
       <tr >
       <td width="20"></td>
           <td width="120" align="left" class="list_range"> <bean:message bundle="timesheet" key="outWorker.common.department"/> </td>
              <td> 
                <logic:equal value="true" name="bigRang" scope="request">
                  <ws:select id="depart" property="depart_code" type="server.essp.timesheet.outwork.logic.DepartmentSelectImpl" onchange="depart_codeSelectChangeEventaccount('null')" orgIds="<%=orgId%>" default="<%=departRid%>" style="width:200px" includeall="true"/>
                </logic:equal>
                <logic:equal value="false" name="bigRang" scope="request">
                  <ws:select id="depart" property="depart_code" type="server.essp.timesheet.outwork.logic.DepartmentSelectImpl" onchange="depart_codeSelectChangeEventaccount('null')" orgIds="<%=orgId%>" default="<%=departRid%>" style="width:200px"/>
                </logic:equal>
              </td>
              <td width="60"> </td>
              <td align="left" class="list_range" width="120"> <bean:message bundle="timesheet" key="outWorker.common.account"/></td>
              <td>
                <ws:upselect id="account" property="account_id" up="depart_code" type="server.essp.timesheet.outwork.logic.AccountSelectImpl" onchange="" orgIds="<%=orgId%>" default="<%=acntRid%>" style="width:200px"/>
              </td>
      </tr>
    <tr height="20"><td height="20"></td></tr>
      <tr>
      <td width="20"></td>
        <td  class="list_range" width="120"><bean:message bundle="timesheet" key="outWorker.common.beginDate"/></td>
        <td width="120">
        <html:text name="beginDate"
                      		beanName="outWorkerSearch"
                      		fieldtype="dateyyyymmdd"
                      		styleId="input_date"
                      		imageSrc="<%=request.getContextPath()+"/image/cal.png"%>"
                      		imageWidth="18"
                      		imageOnclick="getMyDATE('beginDate')"
                      		maxlength="10" 
                      		ondblclick="getDATE(this)"
                      		value="<%=begin%>"
                            />
        </td>
        <td width="60"> </td>
        <td class="list_range" width="120"><bean:message bundle="timesheet" key="outWorker.common.endDate"/></td>
       
        <td width="120" class="list_range">
        <html:text name="endDate"
                      		beanName="outWorkerSearchForm"
                      		fieldtype="dateyyyymmdd"
                      		styleId="input_date"
                      		imageSrc="<%=request.getContextPath()+"/image/cal.png"%>"
                      		imageWidth="18"
                      		imageOnclick="getMyDATE('endDate')"
                      		maxlength="10" 
                      		ondblclick="getDATE(this)"
                      		value="<%=end%>"
                            />
        </td>
          </tr>
        <tr height="20"><td height="20"></td></tr>
       <tr>
       <td width="20"></td>
        <td class="list_range" width="120"><bean:message bundle="timesheet" key="outWorker.common.employee"/></td>
        <td>
          <html:text name="loginId" beanName="outWorkerSearchForm" fieldtype="text" styleId="input_date"/>
        </td>
      </tr>
    </table>
   </html:outborder>
         <html:tabpanel id="outWorkerList" width="815">
    <%-- card title--%>
    <html:tabtitles>
        <html:tabtitle selected="true" width="90">
            <center><a class="tabs_title"><bean:message bundle="timesheet" key="outWorker.searchResult.employeeList"/></a></center>
        </html:tabtitle>
    </html:tabtitles>
    <%-- card buttons--%>
    <html:tabbuttons>
        <input type="button" value='<bean:message bundle="application" key="global.button.new"/>' class="button" onClick="addOutWorker();"/>
        <input type="button" value='<bean:message bundle="application" key="global.button.edit"/>' class="button" onClick="modifyOutWorker();" id="modify" disabled/>
        <input type="button" value='<bean:message bundle="application" key="global.button.delete"/>' class="button" onClick="delWorker();" id="del" disabled/>
      <!--input type="submit" value='<bean:message bundle="application" key="global.button.export"/>' class="button" onClick="exportOutWorkerList();"/ -->
    </html:tabbuttons>
    <html:outborder height="200" width="815">
    <iframe 
					id="searchList" 
					name="searchList"
					src="<%=request.getContextPath()%>/timesheet/outwork/outWorkerList.do" 
					scrolling="auto" 
					height="100%" width="100%" 
					marginHeight="0" marginWidth="0" 
					frameborder="0"
					>
					</iframe>
    </html:outborder>
  </html:tabpanel>

        </html:tabcontent>
      </html:tabcontents>
         </html:form>
    </html:tabpanel>
      <script type="text/javascript" language="javascript">
   sumbitSearch();
    </script>
</body>
</html>
