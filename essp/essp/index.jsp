<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
<title>ESSP</title>
<script>
    var isloaded = 0;
    var treeopen = new Array();
    var treecontent = new Array();
    var tmptreecontent = new Array();
    var tmptreeopen = new Array();
    var treecounter = 0;
    var pleaseWait = 'Please wait...';
    var statusCollection = "no";
  </script>
</head>
<frameset frameborder="no" border="0" framespacing="0" name="index" rows="56,*">
   <frame scrolling="no" noresize src="<%=request.getContextPath()%>/top.jsp">
   <frame name="content" scrolling="no" noresize src="">
   <noframes>
     <body>
       <p></p>
    </body>
   </noframes>

</frameset>
</html>
