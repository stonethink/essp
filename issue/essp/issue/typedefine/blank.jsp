<html>
<head>
<script language="javascript">
function onRefreshData(url,typename) {
	detailForm.action=url;
    detailForm.typeName.value=typename;
    detailForm.submit();
}
</script>
</head>
<body>
<form id="detailForm" name="detailForm" method="post">
<input type="hidden" name="typeName">
</form>
</body>
</html>