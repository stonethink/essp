<%@ include file="/inc/pagedef.jsp" %>
<%@ include file="/layout/dynamicLoginId.jsp" %>
<html>
	
	<script type="text/javascript">
		
		function getQueryUserElementArray(){
			var names = new Array(document.mains.tt,document.mains.t2);
			var loginIds = new Array(document.mains.loginId,document.mains.loginId2);
			var domains = new Array(document.mains.domain1,document.mains.domain2);
			var types = new Array(document.mains.type1,document.mains.type2);
      return new Array(names,loginIds,domains,types);
    }
    
	</script>
	<body>
		<form name="mains" onsubmit="return false;">
			<input id="tt" type="text" style="width:60px;">
			<input id="t2" type="text">
			<br>
			<select><option value="tttttttt">ttttttttttt</select>
			<br>
			<div id='showSpan' style="background-color:FFFFF0;">sssss</div>
				<span>test</span>
				<br>
			<div id='showSpan' style="background-color:FFFFF0;">sssss</div>
				<span>test</span>
				<br>
			<div id='showSpan' style="background-color:FFFFF0;">sssss</div>
				<span>test</span>
				<br>
			<div id='showSpan' style="background-color:FFFFF0;">sssss</div>
				<span>test</span>
				<br>LoginIds:
				<br>
				<input id="loginId" type="text"><input id="loginId2" type="text">
				<br>Domains:
				<br>
				<input id="domain1" type="text"><input id="domain2" type="text">
				<br>Types:
				<br>
				<input id="type1" type="text"><input id="type2" type="text">
		</form>
	</body>
</html>