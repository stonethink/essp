<%@ page language="java"  contentType="text/html;charset=gbk" %>
<%
	
  	String fileFullName = (String)request.getParameter("fileFullName");
  	String leftRevision = (String)request.getParameter("leftRevision");
  	String rightRevision = (String)request.getParameter("rightRevision");
%>

<html>

<head>
<meta name="GENERATOR" content="Microsoft FrontPage 3.0">
<title>
<%
out.print("Different between " +leftRevision+ " and " + rightRevision + " of file " + fileFullName);
%>
</title>

</head>
 
<script type="text/javascript" language="JavaScript">
	function setRelation(){
		 leftFrame.name="left";
		 rightFrame.name="right";
		 leftFrame.btnScroll.style.visibility="visible";
		 rightFrame.btnScroll.style.visibility="visible";
	}
	 
</script>

<frameset rows="30%,*" >
  <frame name="diff" scrolling="no" target="contents" src="fileDiffContentOfTwoRevision.jsp?fileFullName=<%=fileFullName%>&leftRevision=<%=leftRevision%>&rightRevision=<%=rightRevision%>"
  marginheight="6" marginwidth="20">
  <frameset cols="50%,*" onload="setRelation();">
    <frame name="leftFrame" src="fileContentOfTheRevision.jsp?fileFullName=<%=fileFullName%>&revision=<%=leftRevision%>">
    <frame name="rightFrame" src="fileContentOfTheRevision.jsp?fileFullName=<%=fileFullName%>&revision=<%=rightRevision%>">
  </frameset>
  <noframes>
  <body>
  <p>This page uses frames, but your browser doesn't support them.</p>
  </body>
  </noframes>
</frameset>
</html>
