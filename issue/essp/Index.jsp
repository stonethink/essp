<%@ page contentType="text/html;charset=GBK" %>
<html>

<head>
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
<title>ESSP</title>
</head>
<script>
var isloaded = 0;
var treeopen = new Array();
var treecontent = new Array();
var tmptreecontent = new Array();
var tmptreeopen = new Array();
var treecounter = 0;
var pleaseWait = '请稍候...';
var statusCollection = "no";
</script>
<frameset  frameborder="NO" border="0" framespacing="0"  name="banner"  rows="70,*">

  <frame  scrolling="no" noresize src="<%=request.getContextPath()%>/Menu.jsp">

  <frameset name="content" cols="190,*">

    <frame name="left" marginwidth="0" marginheight="0" scrolling="auto" src="<%=request.getContextPath()%>/Left_l.jsp">

    <frame name="main" src="">
  </frameset>

<noframes>

<body>

  <p>此网页使用了框架，但您的浏览器不支持框架。</p>

</body>
</noframes>
</frameset>

</html>
