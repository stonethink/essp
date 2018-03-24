<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value="Attendance Import"/>
        <tiles:put name="jspName" value="."/>
</tiles:insert>

</head>
<script language="javascript" type="">
function sumbitImport(){
  var filename=imForm.localfile.value;
  var i=filename.lastIndexOf('.');
  var suffixName = filename.substring(i,filename.length);
  if(imForm.localfile.value==''){
  alert('File name not null.');
  }
  else{
    if(suffixName=='.xls'){
      imForm.submit();
    }
    else{
    alert('File type not match.');
    }
  }
}
</script>
<body>
<center class="form_title">Attendance Import</center>
<br/>
<html:form id="imForm" action="/tc/attImport/Import.do" enctype="multipart/form-data" target="_self" method="POST">
  <table width="450" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="ffffff">
    <tr>
      <td class="list_range" width="70" height="40" align="left">Local File</td>
      <td width="380" height="40" class="list_range">
        <html:file name="localfile" styleId="input_common" next="submit" prev="" req="true"/>

        <input type="button" class="button" name="Submit" value="Import" onClick="sumbitImport();">

        <input type="reset" value="Reset" name="reset" class="button" >
      </td>
    </tr>
  </table>
</html:form>
<br/>
<center><logic:present name="webVo">
  <table width="450" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="20" style="font-size:11px"><strong>Imported rows statics:</strong></td>
    </tr>
    <tr>
      <td height="20" style="font-size:11px">　Total rows:<bean:write name="webVo" property="total" scope="request"/> rows</td>
    </tr>
    <tr>
      <td height="20" style="font-size:11px">　Imported rows:<bean:write name="webVo" property="imported" scope="request"/> rows</td>
    </tr>
    <tr>
      <td height="20" style="font-size:11px">　Imported no attendence rows:<bean:write name="webVo" property="importedNoAtt" scope="request"/> rows</td>
    </tr>
    <tr>
      <td height="20" style="font-size:11px">　Imported deregulation rows:<bean:write name="webVo" property="importedAtt" scope="request"/> rows</td>
    </tr>

  </table>
  </logic:present>
</center>

</body>
</html>
