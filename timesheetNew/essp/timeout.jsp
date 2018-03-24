<HTML>
<HEAD>
<TITLE>Success Info</TITLE>
<script type="text/javascript" language="JavaScript" src="js/sysjava.js"></script><script language="JavaScript" type="text/javascript">
  function forwardIndex() {
      var outWindow = this.top;
      try {
        if(outWindow.opener != null){
          outWindow.opener.top.location  = "<%=request.getContextPath()%>/login.jsp";
          outWindow.opener.close();
        }else{
          outWindow.location  = "<%=request.getContextPath()%>/login.jsp";
        }
      } catch(e) {

      }
  }
  </script></HEAD>
<body onload="forwardIndex()"></BODY>
</HTML>
