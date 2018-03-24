<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@page import="c2s.essp.common.user.DtoUser"%>
<%@page import="server.essp.timesheet.outwork.viewbean.vbOutWorker"%>
<%@page import="server.essp.timesheet.outwork.form.AfSearchForm"%>
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
%>


<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>

<html>
  <head>
        <tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
<script language="javascript">
var currentRowObj;
var listRid;
var orgIds="<%=orgId%>";
var attManager="<%=attendenceManager%>";


function clickRow(rowObj){
    try{
    	currentRowObj = rowObj;
    	rid = rowObj.selfproperty;
    	var cells=rowObj.cells
        var orga=cells[1].innerText;
        var pd =window.parent.document.all;
        //modified by lipengxu
        //include sub obs
        if(orgIds!=null && orgIds.indexOf(orga) > -1){
          pd.modify.disabled=false;
          pd.del.disabled=false;
        }else{
          pd.modify.disabled=true;
          pd.del.disabled=true;
        }
        if(attManager=="true"){
          pd.modify.disabled=false;
          pd.del.disabled=false;
        }
    }catch( ex ){
        //alert(ex);
    }
}


//当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	try{
	    var table=document.getElementById("SearchTable_table");
	    
		var str0="<bean:message bundle="timesheet" key="outWorker.common.account"/>";
       	var str1="<bean:message bundle="timesheet" key="outWorker.common.department"/>";
       	var str2="<bean:message bundle="application" key="UserAssignRoles.UserList.loginId"/>";
        var str3="<bean:message bundle="application" key="UserAssignRoles.UserList.loginName"/>";
        var str4="<bean:message bundle="timesheet" key="outWorker.common.beginDate"/>";
        var str5="<bean:message bundle="timesheet" key="outWorker.common.endDate"/>";
        var str6="<bean:message bundle="timesheet" key="outWorker.common.days"/>";
        var str7="<bean:message bundle="timesheet" key="outWorker.common.fillTs"/>";
        var str8="<bean:message bundle="timesheet" key="outWorker.common.destAddr"/>";
        var str9="<bean:message bundle="timesheet" key="outWorker.common.allowance"/>";
        var str10="<bean:message bundle="timesheet" key="outWorker.common.type"/>";
   
		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		//使EC标签的表头国际化
		cells[0].innerHTML=cells[0].innerHTML.replace("Account",str0);
		cells[0].title = cells[0].title.replace("Account",str0);
		cells[1].innerHTML=cells[1].innerHTML.replace("Organization",str1);
		cells[1].title = cells[1].title.replace("Organization",str1);
		cells[2].innerHTML=cells[2].innerHTML.replace("LoginId",str2);
		cells[2].title = cells[2].title.replace("LoginId",str2);
		cells[3].innerHTML=cells[3].innerHTML.replace("ChineseName",str3);
		cells[3].title = cells[3].title.replace("ChineseName",str3);
		cells[4].innerHTML=cells[4].innerHTML.replace("BeginDate",str4);
		cells[4].title = cells[4].title.replace("BeginDate",str4);
		cells[5].innerHTML=cells[5].innerHTML.replace("EndDate",str5);
		cells[5].title = cells[5].title.replace("EndDate",str5);
		cells[6].innerHTML=cells[6].innerHTML.replace("Days",str6);
		cells[6].title = cells[6].title.replace("Days",str6);
		cells[7].innerHTML=cells[7].innerHTML.replace("IsAutoFillWR",str7);
		cells[7].title = cells[7].title.replace("IsAutoFillWR",str7);
		cells[8].innerHTML=cells[8].innerHTML.replace("DestAddress",str8);
		cells[8].title = cells[8].title.replace("DestAddress",str8);
		cells[9].innerHTML=cells[9].innerHTML.replace("IsTravellingAllowance",str9);
		cells[9].title = cells[9].title.replace("IsTravellingAllowance",str9);
		cells[10].innerHTML=cells[10].innerHTML.replace("EvectionType",str10);
		cells[10].title = cells[10].title.replace("EvectionType",str10);
		 var firstRow=table.rows[thead_length];
		   clickRow(firstRow);
	    if(table.rows.length>thead_length){
	       //如果有数据
		   onChangeBackgroundColor(firstRow);
	    } 
	    }catch(e){}
	}
	
function delWorker(){
   if(currentRowObj==null) {
        alert('<bean:message bundle="application" key="global.confirm.selectRow"/>');
        return;
    }
    var rid = currentRowObj.selfproperty;
    if(confirm('<bean:message bundle="application" key="global.confirm.delete"/>')) {
      delForm.action='<%=request.getContextPath()%>/timesheet/outwork/outWorkerDel.do?rid='+rid;
      delForm.submit();
    }
}

function addOutWorker(){

  var url='<%=request.getContextPath()%>/timesheet/outwork/add.jsp';
  var leftPos=(screen.width-750)/2;
  var topPos=(screen.height-300)/2;
  var pos='width=750,height=300,left='+leftPos+',top='+topPos;
  window.open(url, 'popupnav', pos);
  //"height:350px;width:750px;left:(screen.width - width) / 2; top:(screen.height - height) / 2;");

  //,'width=750,height=350,resizable=1,scrollbars=auto'
}

function modifyOutWorker(){
  if(currentRowObj==null) {
        alert('<bean:message bundle="application" key="global.confirm.selectRow"/>');
        return;
    }

  var url='<%=request.getContextPath()%>/timesheet/outwork/outWorkerModifyPre.do?rid='+rid;
  var leftPos=(screen.width-750)/2;
  var topPos=(screen.height-300)/2;
  var pos='width=750,height=300,left='+leftPos+',top='+topPos;
  window.open(url, 'popupnav', pos);
  //window.open(url, 'popupnav', 'width=750,height=350,resizable=1,scrollbars=auto');
}
</script>
  </head>
  
<body >
<%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
  <ec:table 
       tableId="SearchTable"
       items="webVo"
       var="out" scope="request"
       action="${pageContext.request.contextPath}/timesheet/outwork/outWorkerList.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
       locale="<%=_language%>"
       >
        <ec:row onclick="clickRow(this);" selfproperty="${out.rid}" >
             <ec:column style="width:30%" property="account" tooltip="${out.account}" title="Account"/>
             <ec:column style="width:30%" property="organization" tooltip="${out.organization}" title="Organization"/>
             <ec:column style="width:30%" property="loginId" tooltip="${out.loginId}" title="LoginId"/>
             <ec:column style="width:30%" property="chineseName" tooltip="${out.chineseName}" title="ChineseName"/>
             <ec:column style="width:30%" property="beginDate" tooltip="${out.beginDate}" title="BeginDate"/>
             <ec:column style="width:30%" property="endDate" tooltip="${out.endDate}" title="EndDate"/>
             <ec:column style="width:30%" property="days" tooltip="${out.days}" title="Days"/>
             <ec:column style="width:30%" property="isAutoFillWR" tooltip="${out.isAutoFillWR}" title="IsAutoFillWR"/>
             <ec:column style="width:30%" property="destAddress" tooltip="${out.destAddress}" title="DestAddress"/>
             <ec:column style="width:30%" property="isTravellingAllowance" tooltip="${out.isTravellingAllowance}" title="IsTravellingAllowance"/>
             <ec:column style="width:30%" property="evectionType" tooltip="${out.evectionType}" title="EvectionType"/>
        </ec:row>
     </ec:table>
     <form name="delForm" id="delForm" action="" method="POST"></form>
     <SCRIPT language="JavaScript" type="Text/JavaScript">
         autoClickFirstRow();
     </SCRIPT>
  </body>
  
</html>
