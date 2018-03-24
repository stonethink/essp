<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%@page import="c2s.essp.common.user.DtoUser"%>
<%@page import="server.essp.tc.outwork.viewbean.vbOutWorker"%>
<%@page import="server.essp.tc.outwork.form.AfSearchForm"%>
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
  }
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
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="Travels On Office Business Info Management"/>
  <tiles:put name="jspName" value="."/>
</tiles:insert>
<style type="text">
  #input_date
  {
  width:180px;FONT-SIZE: 12px;
  }
</style>
<script language="javascript">
var currentRowObj;
var listRid;
var orgIds="<%=orgId%>";
var attManager="<%=attendenceManager%>";
function sumbitSearch(){
  var departId=searchOutWorker.depart.value;
  var accountId=searchOutWorker.account.value;
  var add='<%=request.getContextPath()%>/tc/outwork/outWorkerList.do?isDel=No&departRid='+departId+'&acntRid='+accountId;
  searchOutWorker.action=add;
  searchOutWorker.submit();
}

function clickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	rid = rowObj.rid;
        var orga=rowObj.organization;
        //modified by lipengxu
        //include sub obs
        if(orgIds!=null && orgIds.indexOf(orga) > -1){
          modify.disabled=false;
          del.disabled=false;
        }else{
          modify.disabled=true;
          del.disabled=true;
        }
        if(attManager=="true"){
          modify.disabled=false;
          del.disabled=false;
        }
    }catch( ex ){
        //alert(ex);
    }
}

function delWorker(){
   if(currentRowObj==null) {
        alert("Please select a row first!");
        return;
    }
    if(confirm("Do you want to delete this row?")) {
        delForm.action='<%=request.getContextPath()%>/tc/outwork/outWorkerDel.do?rid='+rid;
        delForm.submit();
    }
}

function addOutWorker(){

  var url='<%=request.getContextPath()%>/tc/outwork/outWorkerAddPre.do';
  var leftPos=(screen.width-750)/2;
  var topPos=(screen.height-300)/2;
  var pos='width=750,height=300,left='+leftPos+',top='+topPos;
  window.open(url, 'popupnav', pos);
  //"height:350px;width:750px;left:(screen.width - width) / 2; top:(screen.height - height) / 2;");

  //,'width=750,height=350,resizable=1,scrollbars=auto'
}

function modifyOutWorker(){
  if(currentRowObj==null) {
        alert("Please select a row first!");
        return;
    }

  var url='<%=request.getContextPath()%>/tc/outwork/outWorkerModifyPre.do?rid='+rid;
  var leftPos=(screen.width-750)/2;
  var topPos=(screen.height-300)/2;
  var pos='width=750,height=300,left='+leftPos+',top='+topPos;
  window.open(url, 'popupnav', pos);
  //window.open(url, 'popupnav', 'width=750,height=350,resizable=1,scrollbars=auto');
}

function exportOutWorkerList(){
    var url='<%=request.getContextPath()%>/ExcelExport?actionId=FTCOutWorkerExportAction';
    window.open(url,"");
}
function change(){
}
</script>
</head>
<body>
<center class="form_title">Travels On Office Business Info Management</center>
<br/>
<center>
  <html:form id="searchOutWorker" action="/tc/outwork/outWorkerList.do" method="POST">
    <table width="600" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="20" colspan="6">
          <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
              <td height="30" align="left" class="list_range">                Department
                <logic:equal value="true" name="bigRang" scope="request">
                  <ws:select id="depart" property="depart_code" type="server.essp.tc.outwork.logic.DepartmentSelectImpl" onchange="depart_codeSelectChangeEventaccount('null')" orgIds="<%=orgId%>" default="<%=departRid%>" style="width:200px" includeall="true"/>
                </logic:equal>
                <logic:equal value="false" name="bigRang" scope="request">
                  <ws:select id="depart" property="depart_code" type="server.essp.tc.outwork.logic.DepartmentSelectImpl" onchange="depart_codeSelectChangeEventaccount('null')" orgIds="<%=orgId%>" default="<%=departRid%>" style="width:200px"/>
                </logic:equal>
              </td>
              <td>              </td>
              <td align="right" class="list_range">                Account
                <ws:upselect id="account" property="account_id" up="depart_code" type="server.essp.tc.outwork.logic.AccountSelectImpl" onchange="" orgIds="<%=orgId%>" default="<%=acntRid%>" style="width:200px"/>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td height="30" class="list_range">BeginDate</td>
        <td width="180">
          <html:text name="beginDate" beanName="outWorkerSearchForm" fieldtype="dateyyyymmdd" styleId="input_date" prev="" next="" ondblclick="getDATE(this);" defaultvalue="true" value="<%=begin%>"/>
        </td>
        <td class="list_range">EndDate</td>
        <td width="180" class="list_range">
          <html:text name="endDate" beanName="outWorkerSearchForm" fieldtype="dateyyyymmdd" styleId="input_date" prev="" next="" ondblclick="getDATE(this);" defaultvalue="true" value="<%=end%>"/>
        </td>
        <td class="list_range">Employee</td>
        <td>
          <html:text name="loginId" beanName="outWorkerSearchForm" fieldtype="text" styleId=""/>
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
    <input type="button" class="button" name="Submit" value="Search" onClick="sumbitSearch();">
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="Reset" name="reset" class="button">
    <br/>
  </html:form>
</center>
<br/>
<br/>
<center>
  <!--  <logic:present name="webVo"> -->
  <html:tabpanel id="outWorkerList" width="765">
    <html:tabtitles>
      <html:tabtitle selected="true" width="90" title="Employee List">
      </html:tabtitle>
    </html:tabtitles>
    <html:tabbuttons>
        <input type="submit" value=" Add " class="button" onClick="addOutWorker();"/>
        <input type="submit" value="Modify" class="button" onClick="modifyOutWorker();" id="modify" disabled/>
        <input type="submit" value="Delete" class="button" onClick="delWorker();" id="del" disabled/>
<!--    -->
      <input type="submit" value="Export" class="button" onClick="exportOutWorkerList();"/>
    </html:tabbuttons>
    <html:outborder height="200" width="765">
      <html:table styleId="tableStyle">
        <html:tablehead styleId="tableTitleStyle">
          <html:tabletitle width="100">Account</html:tabletitle>
          <html:tabletitle width="80">Login Id</html:tabletitle>
          <html:tabletitle width="80">Chinese Name</html:tabletitle>
          <html:tabletitle width="70">Begin Date</html:tabletitle>
          <html:tabletitle width="70">Finish Date</html:tabletitle>
          <html:tabletitle width="40">Days</html:tabletitle>
          <html:tabletitle width="45" toolTip="Is Auto Fill Weekly Report">Fill WR</html:tabletitle>
          <html:tabletitle width="80" toolTip="Destination Address">Dest Address</html:tabletitle>
          <html:tabletitle width="70" toolTip="Is TravellingAllowance">Allowance</html:tabletitle>
           <html:tabletitle width="*">Type</html:tabletitle>
        </html:tablehead>
        <html:tablebody styleId="tableDataStyle" height="100%" id="searchTable">
          <logic:iterate id="wv" name="webVo" scope="request">
          <%
            vbOutWorker vb = (vbOutWorker) wv;
            String selfProp = "rid='" + vb.getRid() + "'";
            selfProp = selfProp + " organization='" + vb.getOrganization() + "'";
          %>
            <html:tablerow id="" selfProperty="<%=selfProp%>" oddClass="tableDataOdd" evenClass="tableDataEven" height="15" canSelected="true" onclick="clickRow(this);">
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="account"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="loginId"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="chineseName"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="beginDate"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="endDate"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="days"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="isAutoFillWR"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="destAddress"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="isTravellingAllowance"/>
              </html:tablecolumn>
              <html:tablecolumn styleId="oracelltext">
                <bean:write name="wv" property="evectionType"/>
              </html:tablecolumn>
            </html:tablerow>
          </logic:iterate>
        </html:tablebody>
      </html:table>
    </html:outborder>
  </html:tabpanel>
  <!--  </logic:present> -->
</center>
<form name="delForm" id="delForm" action="" method="POST"></form>
</body>
</html>
