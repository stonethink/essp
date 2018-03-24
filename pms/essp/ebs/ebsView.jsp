<%@include  file="../inc/langpage.jsp"%>
<html>
<head>
<%@include  file="../inc/langhtm.jsp"%>
<title>
EBS
</title>
</head>

<body class="navtree" leftMargin=9 rightMargin=0 topMargin=8 bottomMargin=2 MARGINWIDTH="0" MARGINHEIGHT="0" >
<%@include  file="../inc/appletdef.jsp"%>
<APPLET ARCHIVE="<%=appletDir%>/ebs_client.jar,<%=comJar%>"
        CODE="client.essp.common.view.VWJMultiApplet"
        NAME="EBS"
        WIDTH="100%"
        HEIGHT="100%"
        HSPACE="0"
        VSPACE="0"
        ALIGN="middle">
<!--common parameter-->
	<PARAM NAME="cfg" VALUE="ebsAppletCfg">
	<PARAM NAME="controllerURL" VALUE="<%=controllerURL%>">
	<PARAM NAME="todayDate" VALUE="<%=todayDate%>">
	<PARAM NAME="todayDatePattern" VALUE="<%=todayDatePattern%>">
	<PARAM NAME="timeZoneID" VALUE="<%=timeZoneID%>">
	<PARAM NAME="userId"    VALUE="<%=userId%>">
	<PARAM NAME="userName"  VALUE="<%=userName%>">
	<PARAM NAME="userType"  VALUE="<%=userType%>">
	
<!--app parameter-->
	<PARAM NAME="entryFunType" VALUE="EbsView">
</APPLET>

</body>
</html>
