<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value="Over Time Import"/>
        <tiles:put name="jspName" value="."/>
</tiles:insert>

</head>
<script language="javascript" type="">
function sumbitImport(){
var fileName = document.getElementsByName("localFile")[0].value;
  if(fileName == null || fileName == "") {
    alert("Local File is required!");
    return;
  }
  imForm.submit();
}
</script>
<body>
<center class="form_title">Over Time Import</center>
<br/>
<html:form id="imForm" action="/attendance/overtime/OverTimeImport" enctype="multipart/form-data" target="_self" method="POST">
  <table width="450" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="ffffff">
    <tr>
      <td class="list_range" width="70" height="40" align="left">Local File</td>
      <td width="380" height="40" class="list_range">
        <html:file name="localFile" styleId="input_common" next="submit" prev="" req="true"/>

        <input type="button" class="button" name="Submit" value="Import" onclick="sumbitImport();">

        <input type="reset" value="Reset" name="reset" class="button" >
      </td>
    </tr>
  </table>
</html:form>
<br/>
<center><logic:present name="webVo">
  <table width="450" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="20" style="font-size:11px"><strong>Imported successful:</strong></td>
    </tr>
    <tr>
      <td height="20" style="font-size:11px">Total rows:<bean:write name="webVo" property="totalRows" scope="request"/> rows</td>
    </tr>
    <tr>
      <td height="20" style="font-size:11px">Imported rows:<bean:write name="webVo" property="importedRows" scope="request"/> rows</td>
    </tr>
    <tr>
      <td height="20" style="font-size:11px">Total hours:<bean:write name="webVo" property="totalHours" format="#,###,##0.0#"  scope="request"/> hours</td>
    </tr>
    <tr>
      <td height="20" style="font-size:11px">Remark:<bean:write name="webVo" property="remark" scope="request"/></td>
    </tr>

  </table>
  </logic:present>
</center>

</body>
</html>
