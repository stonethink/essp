<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="server.essp.issue.common.logic.LgAccount,c2s.essp.common.user.DtoUser,java.util.List" %>

<%
	LgAccount logic=new LgAccount();
	try {
		DtoUser user=(DtoUser)session.getAttribute(DtoUser.SESSION_USER);
		if(user!=null) {
			List availabeAccounts=logic.getAccountOptions(user.getUserType(),user.getUserLoginId());
			session.setAttribute("availabeAccounts",availabeAccounts);
		}
	} catch(Exception e) {
	} finally {
		try {
			logic.getDbAccessor().close();
		} catch(Exception ex) {
		}
	}

%>

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
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/EventHandler.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/FieldCheck.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/Format.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/UserUtils.js"></script>
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/get_calendar.js"></script>
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/tab.js"></script>
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/hashmap.js"></script>
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/selectToolTip.js"></script>
    <wits-script:focusControl />
	<wits-script:messageDialog/>

<%try{%>
  <wits-script:windowOpen/>
<%}catch (Exception e){  }%>
<%try{%>
	<wits-script:confirm/>
<%}catch (Exception e){  }%>

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
function onChangeProject(acntRid){
  //parent.window.main.location = "<%=request.getContextPath()%>/overview.do?acntRid="+acntRid;
}
		function addNewEsspIssue() {
		    openWindow('<%=request.getContextPath()%>/issue/issue/issueAddPre.do?accountCode=002645W&accountId=&issueType=SPR&typeName=SPR&issueStatus=Received&scope=Company&priority=NORMAL');
		}

		function openWindow(url, windowTitle) {
			var height = 430;
			var width = 580;
			var topis = (screen.height - height) / 2;
			var leftis = (screen.width - width) / 2;
			var option = "height=" + height + "px"
						+", width=" + width + "px"
						+", top=" + topis + "px"
						+", left=" + leftis + "px"
						+", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
			window.open(url, windowTitle, option);
		}

// -->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<p>
<p>
</p>
<div id="Layer2" style="position:absolute; width:1000px; height:70px; z-index:0; left: 0px; top: 0px;">
<table width=1024px border="0" height="70"  cellSpacing=0 cellPadding=0>
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
      <td  height="1" > &nbsp; </td>
      <td  class="menu" height="1" width="10" ></td>
      <td  height="1" > &nbsp; </td>
      <td  class="menu" height="1" width="10" ></td>
      <td  height="1" > &nbsp;</td>
   </tr>
   </table>
</div>
<div id="Layer1" style="position:absolute; width:534px; height:23px; z-index:2; left: 478px; top: 47px; visibility: visible;">
<html:form action="" >
  <table width="75%" border="0" align="right" cellpadding="1" cellspacing="1" class="tableinout" >
    <tr>
      <td class="menu"  width="150" valign =top align="right">
      <%--<bean:write name="customer" property="userName" scope="session"/>:--%>
      </td>
      <td class="menu"  width="250" >
        <div align="right">
          <html:select  name="selectedAccount" beanName="customer"  onchange="onChangeProject(this.value);" styleId="project_select"  styleClass="project_select">
			<logic:notPresent name="availabeAccounts">
            <option value="">--Please select account--</option>
            </logic:notPresent>
            <logic:present name="availabeAccounts">
            <html:optionsCollection name="availabeAccounts" />
            </logic:present>
          </html:select>
        </div>
        <div align="right">
          </div></td>
    </tr>
  </table>
  </html:form>
</div>
<div id="FirstMemu" style="position:absolute; width:144px; height:22px; z-index:2; right: 10px; top: 8px">
  <table  border="0"  align="right">
    <tr>
      <script language="JavaScript"><!--
	if(MSFPhover) { MSFPnav11n=MSFPpreload("image/orderForm.gif"); MSFPnav11h=MSFPpreload("image/orderForm_On.gif"); }
     // -->
     </script>
     <td class="tableinout" >
	<a href="#" target="_self"
                onmouseover="if(MSFPhover) document['MSFPnav11'].src=MSFPnav11h.src"
                onmouseout="if(MSFPhover) document['MSFPnav11'].src=MSFPnav11n.src">
        <img src="image/orderForm.gif" alt="Help" width="28"
             height="28" border="0" name="MSFPnav11" onclick="addNewEsspIssue()">
    </a>
     </td>

      <td      class="menu" height="1" width="1" ></td>

      <script language="JavaScript"><!--
	if(MSFPhover) { MSFPnav10n=MSFPpreload("image/pass.gif"); MSFPnav10h=MSFPpreload("image/pass_On.gif"); }
     // -->
     </script>
     <td class="tableinout" >
	<a href="/crm/Menu.jsp#" target="_top" language="JavaScript" onmouseover="if(MSFPhover) document['MSFPnav10'].src=MSFPnav10h.src" onmouseout="if(MSFPhover) document['MSFPnav10'].src=MSFPnav10n.src"><img src="image/pass.gif" alt="Change Password" width="28" height="28" border="0" name="MSFPnav10"></a>
     </td>

      <td      class="menu" height="1" width="1" ></td>

      <script language="JavaScript"><!--
	if(MSFPhover) { MSFPnav9n=MSFPpreload("image/exit.gif"); MSFPnav9h=MSFPpreload("image/exit_On.gif"); }
     // -->
     </script>
     <td class="tableinout" >
	<a href="<%=request.getContextPath()%>/logout.jsp" target="_top" language="JavaScript" onmouseover="if(MSFPhover) document['MSFPnav9'].src=MSFPnav9h.src" onmouseout="if(MSFPhover) document['MSFPnav9'].src=MSFPnav9n.src"><img src="image/exit.gif" alt="Exit" width="28" height="28" border="0" name="MSFPnav9"></a>
     </td>

      <td      class="menu" height="1" width="1" ></td>
    </tr>
  </table>
</div>
</body>
</html>
