<%@include  file="/inc/langpage.jsp"%>
<html>
<head>
<%@include  file="/inc/langhtm.jsp"%>
<title>
PMS
</title>
<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/humanAllocate.js"></script>
<script language="JavaScript">
var account_id = 'null';
function onResoucePlan(accountId){
  window.open("/essp/pms/resplan/ResourcePlan.do?accountId="+accountId , "", "status=yes,width=740,height=450,top=150,left=150");
}

function validate(){
	try{
		FTC01030.repaint();
	}catch(ex){
	
	}
}
</script>
</head>

<body class="navtree" leftMargin=9 rightMargin=0 topMargin=8 bottomMargin=2 MARGINWIDTH="0" MARGINHEIGHT="0" >

<%@include  file="/inc/appletdef.jsp"%>
<APPLET ARCHIVE="<%=appletDir%>/worktime_client.jar,<%=comJar%>"
        CODE="client.essp.timecard.worktime.FTC01030Applet"
        NAME="FTC01030"
        WIDTH="100%"
        HEIGHT="100%"
        HSPACE="0"
        VSPACE="0"
        ALIGN="middle">
<!--common parameter-->
	<PARAM NAME="cfg" VALUE="worktimeAppletCfg">
	<PARAM NAME="controllerURL" VALUE="<%=controllerURL%>">
	<PARAM NAME="todayDate" VALUE="<%=todayDate%>">
	<PARAM NAME="todayDatePattern" VALUE="<%=todayDatePattern%>">
	<PARAM NAME="timeZoneID" VALUE="<%=timeZoneID%>">
	<PARAM NAME="userId"    VALUE="<%=userId%>">
	<PARAM NAME="userName"  VALUE="<%=userName%>">
	<PARAM NAME="userType"  VALUE="<%=userType%>">
	  
</APPLET>
<script type="text/javascript">
  appletInstance = FTC01030;
</script>
</body>
</html>
