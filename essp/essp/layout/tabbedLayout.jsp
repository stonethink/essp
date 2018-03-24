<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %> 
<%
	String servletPath=request.getServletPath();
    int lastIndex=servletPath.lastIndexOf("/");
    String dir=servletPath.substring(0,lastIndex);
%>
<html>
<head>
    <meta http-equiv="Expires" content="0">
	<meta http-equiv="Pragma"  content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <TITLE><bean:write name="tabbedBean" property="title" scope="request"/></TITLE>

	<link rel="stylesheet" type="text/css" href='<bean:write name="tabbedBean" property="css" scope="request"/>'>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/BaseLayout.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Theme.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/wits.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/essp.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/table.css">
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/commonjs/AppMessage.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/commonjs/UIdesignDef.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/commonjs/sysjava.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/commonjs/EventHandler.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/commonjs/FieldCheck.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/commonjs/Format.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/commonjs/UserUtils.js"></script>

    <script:focusControl />
	<script:messageDialog/>
	<script:windowOpen/>
	<script:confirm/>

    <!-- �û��ű�������(��(button �¼�����) -->
    <tiles:insert beanName="tabbedBean" beanProperty="scriptArea" beanScope="request"/>

</head>

<body onload="onBodyLoad();">
</table>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="148, 170, 214" >
  <tr height="22">
    <td>
<applet
  archive = "<%=request.getContextPath()%>/applet/AppletTabbed.jar"
  code     = "client.essp.common.appletTabbed.AppletTabbed.class"
  name     = "TabbedApplet"
  width    = "100%"
  height   = "22"
  hspace   = "3"
  vspace   = "0"
  align    = "center"
  mayscript>

<tiles:insert beanName="tabbedBean" beanProperty="paramArea" beanScope="request"/>
</applet>
  </td>
  </tr>
  <tr bordercolor="#003366" bordercolordark="#0033CC" width="100%" height="100%"  border="2" >
    <td align="center" bordercolor="#003366" >
      <table  width="99.3%" height="100%" border="1" cellpadding="1" cellspacing="0" bordercolor="#94AAD6" >
        <tr>
		<td align="center" valign="top">

<!-- ������ -->
<tiles:insert beanName="tabbedBean" beanProperty="contentArea" beanScope="request"/>
        </td>
  		</tr>
      </table>
    </td>
  </tr>

</td>
</tr>
</table>




</body>
</html>
