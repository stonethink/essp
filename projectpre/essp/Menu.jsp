<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>


<html>
<head>

    <meta http-equiv="Expires" content="0">
	<meta http-equiv="Pragma"  content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <TITLE></TITLE>

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/essp.css">


	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/AppMessage.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/UIdesignDef.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/sysjava.js"></script>

	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/FieldCheck.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/Format.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/UserUtils.js"></script>

    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/tab.js"></script>
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/hashmap.js"></script>
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/selectToolTip.js"></script>
    



<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>

<script language="JavaScript"><!--
MSFPhover =
  (((navigator.appName == "Netscape") &&
  (parseInt(navigator.appVersion) >= 3 )) ||
  ((navigator.appName == "Microsoft Internet Explorer") &&
  (parseInt(navigator.appVersion) >= 4 )));
function MSFPpreload(img)
{
  var a=new Image(); a.src=img; return a;
}

// -->
</script>
</head>
<body >
<p>
<p>
</p>
<div id="Layer2" style="position:absolute; width:3000px; height:70px; z-index:0; left: 0px; top: 0px;">
<table width=100% border="0" height="70"  cellSpacing=0 cellPadding=0>
  <tr>
    <td height=48px  class="HeaderFrame"></td>
  </tr>
  <tr>
    <td height="100%" class="HalfWindow" ></td>
  </tr>
</table>
</div>
<div style="position: absolute; left: 0px; top: 0px; width: 129px; height: 48px" id="ICon"> <img src="image/essp_log.jpg" width="129" height="48" border="0"></div>
<div id="FirstMemu" style="position:absolute; width:570px; height:23px; z-index:2; left: 142px; top: 0px">
  <table border="0" cellspacing="1" cellpadding="1" class="tableinout" height="4" align="left" >
    <tr>
      <td  height="1" >&nbsp;  </td>
      <td  class="menu" height="1" width="10" ></td>
      <td  height="1" >&nbsp;  </td>
      <td  class="menu" height="1" width="10" ></td>
      <td  height="1" >&nbsp; </td>
   </tr>
  </table>
</div>
<div id="FirstMemu" style="position:absolute; width:100%; height:22px; z-index:2; right: 10px; top: 8px">
  <table  border="0"  align="right">
    <tr>
      <td      class="menu" height="1" width="1" ></td>

      <script language="JavaScript"><!--
	if(MSFPhover) { MSFPnav9n=MSFPpreload("image/exit.gif"); MSFPnav9h=MSFPpreload("image/exit_On.gif"); }
     // -->
     </script>
     <td class="tableinout" >
	<a href="j_acegi_logout" target="_top" onmouseover="if(MSFPhover) document['MSFPnav9'].src=MSFPnav9h.src" onmouseout="if(MSFPhover) document['MSFPnav9'].src=MSFPnav9n.src"><img src="image/exit.gif" alt="Exit" width="28" height="28" border="0" name="MSFPnav9"></a>
     </td>

      <td      class="menu" height="1" width="1" ></td>
    </tr>
  </table>
</div>
</body>
</html>
