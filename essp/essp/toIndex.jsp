<%@ page contentType="text/html;charset=GBK" language="java" %>

<HTML>
<HEAD>
  <meta http-equiv="content-type" content="text/html; charset=GBK">
  <LINK rel="stylesheet" href="css/common.css" type="text/css">
  <basefont size="2">
  <TITLE>Success Info</TITLE>
  <script language="JavaScript" type="text/javascript">
  function onCloseWindow() {
      try {
      	returnValue="OK";
      } catch(e) {

      }
      try {
      	opener.location.reload();
      } catch (e) {
       //�˷���Ϊ�򿪴���Ϊ�༶JSPʱʹ��
       window.parent.parent.opener.location.reload();
       window.parent.parent.close();
       return;
      }

      window.close();
  }
  </script>
</HEAD>
<body bgcolor="#ffff66" text="#000000" link="#0000ff" alink="#ff0000" vlink="#800080" onload="onCloseWindow()">

</BODY>
</HTML>
