<html>
<head>
<script language="javascript" src="selectbox.js"></script>
</head>
<body>
<form name="testfrm" id="testfrm">
<select id="typename" name="typename" onchange="optionListFrm.location='/essp/issue/optionList.do?prioritySelectbox=document.forms[0].priority&userType=EMP&issueType=ISSUE'">
<option value="ISSUE">ISSUE</option>
<option value="PPR">PPR</option>
</select>
<select id="priority" name="priority">
<option value="TEST">TEST</option>
</select>

</form>
<iframe id="optionListFrm" name="optionListFrm" src="" width="0" height="0"></iframe>
</body>
</html>