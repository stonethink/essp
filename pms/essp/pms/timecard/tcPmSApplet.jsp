<%@include  file="/inc/langpage.jsp"%>
<html>
<head>
<%@include  file="/inc/langhtm.jsp"%>
<title>
TimeCard
</title>
<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/humanAllocate.js"></script>
<script language="JavaScript">
var account_id = 'null';
function onResoucePlan(accountId){
  window.open("/essp/pms/resplan/ResourcePlan.do?accountId="+accountId , "", "status=yes,width=740,height=450,top=150,left=150");
}

function validate(){
	try{
		Timecard.repaint();
	}catch(ex){
	
	}
}

function openFile(fileName){
  window.open("/essp/"+fileName , "", "toolbar =yes, menubar=yes, scrollbars=yes, resizable=yes, status=yes,width=100,height=700,top=10,left=10");
}

</script>
</head>

<body class="navtree" leftMargin=9 rightMargin=0 topMargin=8 bottomMargin=2 MARGINWIDTH="0" MARGINHEIGHT="0" >

<%@include  file="/inc/appletdef.jsp"%>
<APPLET ARCHIVE="<%=appletDir%>/tc_client.jar,<%=comJar%>"
        CODE="client.essp.common.view.VWJMultiApplet"
        NAME="Timecard"
        WIDTH="100%"
        HEIGHT="100%"
        HSPACE="0"
        VSPACE="0"
        ALIGN="middle">
<!--common parameter-->
	<PARAM NAME="cfg" VALUE="tcPmSAppletCfg">
	<PARAM NAME="controllerURL" VALUE="<%=controllerURL%>">
	<PARAM NAME="todayDate" VALUE="<%=todayDate%>">
	<PARAM NAME="todayDatePattern" VALUE="<%=todayDatePattern%>">
	<PARAM NAME="timeZoneID" VALUE="<%=timeZoneID%>">
	<PARAM NAME="userId"    VALUE="<%=userId%>">
	<PARAM NAME="userName"  VALUE="<%=userName%>">
	<PARAM NAME="userType"  VALUE="<%=userType%>">
	
<!--app parameter-->

</APPLET>
<script type="text/javascript">
  appletInstance = Timecard;
</script>
</body>
</html>