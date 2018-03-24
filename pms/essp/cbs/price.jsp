<%@include  file="/inc/langpage.jsp"%>
<html>
<head>
<%@include  file="/inc/langhtm.jsp"%>
<title>
Price
</title>
<%@include  file="/inc/appletdef.jsp"%>
<APPLET ARCHIVE="<%=appletDir%>/cbs_client.jar,<%=comJar%>"
        CODE="client.essp.common.view.VWJMultiApplet"
        NAME="price"
        WIDTH="100%"
        HEIGHT="100%"
        HSPACE="0"
        VSPACE="0"
        ALIGN="middle">
<!--common parameter-->
	<PARAM NAME="cfg" VALUE="priceAppletCfg">
	<PARAM NAME="controllerURL" VALUE="<%=controllerURL%>">
	<PARAM NAME="todayDate" VALUE="<%=todayDate%>">
	<PARAM NAME="todayDatePattern" VALUE="<%=todayDatePattern%>">
	<PARAM NAME="timeZoneID" VALUE="<%=timeZoneID%>">
	<PARAM NAME="userId"    VALUE="<%=userId%>">
	<PARAM NAME="userName"  VALUE="<%=userName%>">
	<PARAM NAME="userType"  VALUE="<%=userType%>">


</APPLET>

<script type="text/javascript">
  appletInstance = price;
</script>
