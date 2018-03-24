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
		Account.repaint();
	}catch(ex){
	
	}
}
</script>
</head>

<body class="navtree" leftMargin=9 rightMargin=0 topMargin=8 bottomMargin=2 MARGINWIDTH="0" MARGINHEIGHT="0" >

<%@include  file="/inc/appletdef.jsp"%>
<object
    classid = "clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
    codebase = "http://essp.wh.wistronits.com/jre-1_5_0_09-windows-i586-p.exe#Version=5,0,0,9"
    WIDTH = "100%" HEIGHT = "100%" NAME = "Account" ALIGN = "middle" VSPACE = "0" HSPACE = "0" >
    <PARAM NAME = CODE VALUE = "client.essp.common.view.VWJMultiApplet" >
    <PARAM NAME = ARCHIVE VALUE = "<%=appletDir%>/pms_client.jar,<%=appletDir%>/wp_client.jar,<%=appletDir%>/ganttProject_withDepends.jar,<%=appletDir%>/HtmlEditor.jar,<%=appletDir%>/tc_client.jar,<%=comJar%>" >
    <PARAM NAME = NAME VALUE = "Account" >
    <param name = "type" value = "application/x-java-applet;version=1.5">
    <param name = "scriptable" value = "false">
    <PARAM NAME = "cfg" VALUE="pmsAppletCfg">
    <PARAM NAME = "controllerURL" VALUE="<%=controllerURL%>">
    <PARAM NAME = "todayDate" VALUE="<%=todayDate%>">
    <PARAM NAME = "todayDatePattern" VALUE="<%=todayDatePattern%>">
    <PARAM NAME = "timeZoneID" VALUE="<%=timeZoneID%>">
    <PARAM NAME = "userId" VALUE="<%=userId%>">
    <PARAM NAME = "userName" VALUE="<%=userName%>">
    <PARAM NAME = "userType" VALUE="<%=userType%>">
    <PARAM NAME = "entryFunType" VALUE="<%=c2s.essp.pms.account.DtoAcntKEY.PMS_ACCOUNT_FUN%>">

    <comment>
	<embed
            type = "application/x-java-applet;version=1.5" \
            CODE = "client.essp.common.view.VWJMultiApplet" \
            ARCHIVE = "<%=appletDir%>/pms_client.jar,<%=appletDir%>/wp_client.jar,<%=appletDir%>/ganttProject_withDepends.jar,<%=appletDir%>/tc_client.jar,<%=comJar%>" \
            NAME = "Account" \
            WIDTH = "100%" \
            HEIGHT = "100%" \
            ALIGN = "middle" \
            VSPACE = "0" \
            HSPACE = "0" \
            cfg ="pmsAppletCfg" \
            controllerURL ="<%=controllerURL%>" \
            todayDate ="<%=todayDate%>" \
            todayDatePattern ="<%=todayDatePattern%>" \
            timeZoneID ="<%=timeZoneID%>" \
            userId ="<%=userId%>" \
            userName ="<%=userName%>" \
            userType ="<%=userType%>" \
            entryFunType ="<%=c2s.essp.pms.account.DtoAcntKEY.PMS_ACCOUNT_FUN%>"
	    scriptable = false
	    pluginspage = "http://java.sun.com/products/plugin/index.html#download">
	    <noembed>
            
            </noembed>
	</embed>
    </comment>
</object>

<script type="text/javascript">
  appletInstance = Account;
</script>
</body>
</html>