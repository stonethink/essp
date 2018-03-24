<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<meta http-equiv="Expires" content="0">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>
<tiles:getAsString name="title"/>
</TITLE>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/BaseLayout.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Theme.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/wits.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/essp.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/table.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tab.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/extremecomponents.css">
<link rel="stylesheet" type="text/css" href='<tiles:getAsString name="jspName"/>.css'>
<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/AppMessage.js"></script><script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/UIdesignDef.js"></script><script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/global.js"></script><script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/EventHandler.js"></script><script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/FieldCheck.js"></script><script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/Format.js"></script><script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/UserUtils.js"></script><script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/get_calendar.js"></script><script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/tab.js"></script><script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/hashmap.js"></script><script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/selectToolTip.js"></script><script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/extremecomponents.js"></script><script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/humanAllocate.js"></script><wits-script:focusControl/>
<wits-script:messageDialog/>
<%try {%>
<wits-script:windowOpen/>
<%} catch (Exception e) {}%>
<%try {%>
<wits-script:confirm/>
<%} catch (Exception e) {}%>
<script language="JavaScript" type="text/JavaScript">
var id1000 = "id1000";
var id2000 = "id2000";

//var id0001="Wait a moment please!";
var id0001='<bean:message key="page.id0001" bundle="error"/>';
//var id0002="Please input required item!";
var id0002='<bean:message key="page.id0002" bundle="error"/>';
//var id0003="Data surpasses the limit!";
var id0003='<bean:message key="page.id0003" bundle="error"/>';
//var id0004="Please input Number or '-' or '.'!";
var id0004='<bean:message key="page.id0004" bundle="error"/>';
//var id0005="Please input Number";
var id0005='<bean:message key="page.id0005" bundle="error"/>';
//var id0006="Error on format of date!";
var id0006='<bean:message key="page.id0006" bundle="error"/>';
//var id0007="Please input Number or English letter!";
var id0007='<bean:message key="page.id0007" bundle="error"/>';
//var id0008="Only Number or '-' can be inputed!";
var id0008='<bean:message key="page.id0008" bundle="error"/>';
var id0009="id0009";
var id0010="id0010";
var id0011="id0011";
//var id0012="Error on format of time!";
var id0012='<bean:message key="page.id0012" bundle="error"/>';;
//var id0013="Data has oversteped the  maxlength";
var id0013='<bean:message key="page.id0013" bundle="error"/>';
var id0014="'.' not accepted";
var id0015="'-' not accepted!";
</script>
