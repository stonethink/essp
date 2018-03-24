<%@include  file="../inc/langpage.jsp"%>
<html>
<head>
<%@include  file="../inc/langhtm.jsp"%>
<title>
PWM
</title>
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
<!--"CONVERTED_APPLET"-->
<!-- HTML CONVERTER -->
<object
    classid = "clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
    codebase = "http://essp.wh.wistronits.com/jre-1_5_0_09-windows-i586-p.exe#Version=5,0,0,9"
    WIDTH = "100%" HEIGHT = "100%" NAME = "pspApplet" ALIGN = "center" VSPACE = "0" HSPACE = "0" >
    <PARAM NAME = CODE VALUE = "client.essp.timesheet.WorkBenchApplet.class" >
    <PARAM NAME = ARCHIVE VALUE = "<%=appletDir%>/timesheet_client.jar,<%=comJar%>" >
    <PARAM NAME = NAME VALUE = "pspApplet" >
    <param name = "type" value = "application/x-java-applet;version=1.5">
    <param name = "scriptable" value = "false">
    <PARAM NAME = "controllerURL" VALUE="<%=controllerURL%>">
    <PARAM NAME = "todayDate" VALUE="<%=todayDate%>">
    <PARAM NAME = "todayDatePattern" VALUE="<%=todayDatePattern%>">
    <PARAM NAME = "timeZoneID" VALUE="<%=timeZoneID%>">
    <PARAM NAME = "userId" VALUE="<%=userId%>">
    <PARAM NAME = "userName" VALUE="<%=userName%>">
    <PARAM NAME = "userType" VALUE="<%=userType%>">
    <PARAM NAME = "locale" VALUE="<%=_language%>">

    <comment>
	<embed
            type = "application/x-java-applet;version=1.5" \
            CODE = "client.essp.timesheet.WorkBenchApplet.class" \
            ARCHIVE = "<%=appletDir%>/timesheet_client.jar,<%=comJar%>" \
            NAME = "pspApplet" \
            WIDTH = "100%" \
            HEIGHT = "100%" \
            ALIGN = "center" \
            VSPACE = "0" \
            HSPACE = "0" \
            controllerURL ="<%=controllerURL%>" \
            todayDate ="<%=todayDate%>" \
            todayDatePattern ="<%=todayDatePattern%>" \
            timeZoneID ="<%=timeZoneID%>" \
            userId ="<%=userId%>" \
            userName ="<%=userName%>" \
            userType ="<%=userType%>" \
            locale = "<%=_language%>"
	    scriptable = false
	    pluginspage = "http://java.sun.com/products/plugin/index.html#download">
	    <noembed>
            
            </noembed>
	</embed>
    </comment>
</object>
</body>
</html>
