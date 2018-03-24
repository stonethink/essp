<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>

    <meta http-equiv="Expires" content="0">
	<meta http-equiv="Pragma"  content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <TITLE><tiles:getAsString name="title"/></TITLE>

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/BaseLayout.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/Theme.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/wits.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/essp.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/table.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tab.css">
    <link rel="stylesheet" type="text/css" href='<tiles:getAsString name="jspName"/>.css'>

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
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/optionTip.js"></script>
    <script:focusControl />
<%--
	<script:messageDialog/>


<%try{%>
  <wits-script:windowOpen/>
<%}catch (Exception e){  }%>
<%try{%>
	<wits-script:confirm/>
<%}catch (Exception e){  }%>


--%>
