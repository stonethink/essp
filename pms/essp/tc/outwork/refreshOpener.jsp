<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
<script type="" language="javascript">
function submitOpener(){
  opener.searchOutWorker.action='<%=request.getContextPath()%>/tc/outwork/outWorkerList.do?isDel=Yes';
  opener.searchOutWorker.submit();
  window.close();
}
</script>
<title>
refreshOpener
</title>
</head>
<body bgcolor="#ffffff" onload="submitOpener();">
<h1>
JBuilder Generated JSP
</h1>
</body>
</html>
