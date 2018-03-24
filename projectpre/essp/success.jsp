<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ include file="/inc/pagedef.jsp" %>
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
      	 //如果关闭窗口前需要提示信息，则弹出提示信息框
      	 var msgBeforeClose = "<%=request.getAttribute("Message")%>";
         if(msgBeforeClose!="null" && msgBeforeClose!=null && msgBeforeClose!=""){
           alert("<bean:message bundle="application" key="global.carry.fail"/>");
         }
      } catch(e) {
      }
      try {
      	opener.location.reload();
      } catch (e) {
       try{
       //此方法为打开窗口为多级JSP时使用
       window.parent.opener.location.reload();
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
