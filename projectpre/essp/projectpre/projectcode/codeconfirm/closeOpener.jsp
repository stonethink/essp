<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<HTML>
<HEAD>
  <meta http-equiv="content-type" content="text/html; charset=GBK">
  <LINK rel="stylesheet" href="css/common.css" type="text/css">
  <basefont size="2">
  <TITLE>Success Info</TITLE>
  <script type="text/javascript" language="JavaScript" src="js/sysjava.js"></script>
  <script language="JavaScript" type="text/javascript">
  function onCloseWindow() {
      try {
      	returnValue="OK";
      } catch(e) {

      }
      try {
      	opener.location="<%=contextPath%>/project/confirm/resultList.do";
      } catch (e) {
       try{
       //此方法为打开窗口为多级JSP时使用
       window.parent.opener.parent.queryData.subForm();
       //window.parent.opener.location="<%--=contextPath--%>/project/confirm/resultList.do";
       window.parent.close();
       } catch (e) {
       //找不到父页面就直接将本页面关闭
       window.parent.close(); 
		return;
       }
       return;
      }
      
      window.close();
  }
  </script>
</HEAD>
<body bgcolor="#ffff66" text="#000000" link="#0000ff" alink="#ff0000" vlink="#800080" onload="onCloseWindow()">

</BODY>
</HTML>
