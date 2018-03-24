<%@include  file="../inc/langpage.jsp"%>
<html>
<head>
<%@include  file="../inc/langhtm.jsp"%>
<title>
PMS
</title>
<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/humanAllocate.js"></script>
<script type="text/javascript" language="JavaScript">
	function validate(){
		try{
			pspApplet.repaint();
		}catch(ex){
		
		}
	}
</script>
</head>

<body class="navtree" leftMargin=9 rightMargin=0 topMargin=8 bottomMargin=2 MARGINWIDTH="0" MARGINHEIGHT="0" >

<%@include  file="../inc/appletdef.jsp"%>
<APPLET ARCHIVE="<%=appletDir%>/wp_client.jar,<%=appletDir%>/tc_client.jar,<%=comJar%>"
        CODE="client.essp.pwm.workbench.AppletWorkbench.class"
        NAME="pspApplet"
        WIDTH="100%"
        HEIGHT="100%"
        HSPACE="0"
        VSPACE="0"
        ALIGN="center">
<!--common parameter-->
<!--	<PARAM NAME="cfg" VALUE="ebsAccountAppletCfg">-->
	<PARAM NAME="controllerURL" VALUE="<%=controllerURL%>">
	<PARAM NAME="todayDate" VALUE="<%=todayDate%>">
	<PARAM NAME="todayDatePattern" VALUE="<%=todayDatePattern%>">
	<PARAM NAME="timeZoneID" VALUE="<%=timeZoneID%>">
	<PARAM NAME="userId"    VALUE="<%=userId%>">
	<PARAM NAME="userName"  VALUE="<%=userName%>">
	<PARAM NAME="userType"  VALUE="<%=userType%>">

</APPLET>
</body>
</html>
