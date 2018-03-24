<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/inc/pagedef.jsp"%>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value= "APRM Import"/>
        <tiles:put name="jspName" value="."/>
</tiles:insert>
<script language="javascript" type="">
function sumbitImport(){

var fileName = document.getElementsByName("localFile")[0].value;
  if(fileName == null || fileName == "") {
    alert("<bean:message bundle="application" key="global.localfile.requre"/>");
    return;
  }
  aprmTSImport.submit();
}
</script>
<style type="text/css">
   #select_file{
    width=200px;
    }
</style>
</head>
<body>
<center class="form_title"><bean:message bundle="timesheet" key="aprmImport.common.aprmImport"/></center>
<br/>
<html:form id="aprmTSImport" action="/timesheet/aprmimport/aprmImport"   enctype="multipart/form-data" method="POST">
  <table width="450" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="ffffff">
    <tr>
      <td class="list_range" width="70" height="40" align="left"><bean:message bundle="timesheet" key="aprmImport.common.localFile"/></td>
      <td width="380" height="40" class="list_range">
        <html:file name="localFile" styleId="select_file" />
        <input type="button"  class="button" value="<bean:message bundle="application" key="global.button.import"/>" onclick="sumbitImport();">
        <input type="button"  class="button"  value="<bean:message bundle="application" key="global.button.reset"/>">
      </td>
    </tr>
  </table>
    </html:form>
 <center>
 <logic:equal name="flag" value="Success!">
 <logic:present name="webVo">
  <table width="450" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td height="20" style="font-size:11px"><strong><bean:message bundle="timesheet" key="aprmImport.common.importSuccess"/>:</strong></td>
    </tr>
    <tr>
      <td height="20" style="font-size:11px"><bean:message bundle="timesheet" key="aprmImport.common.totalRows"/>:<bean:write name="webVo" property="totalRows" scope="request"/><bean:message bundle="timesheet" key="aprmImport.common.rows"/></td>
    </tr>
    <tr>
      <td height="20" style="font-size:11px"><bean:message bundle="timesheet" key="aprmImport.common.importedTotalRows"/>:<bean:write name="webVo" property="timeSheetRows" scope="request"/><bean:message bundle="timesheet" key="aprmImport.common.rows"/></td>
    </tr>
    <tr>
      <td height="20" style="font-size:11px"><bean:message bundle="timesheet" key="outWorker.common.beginDate"/>:<bean:write name="webVo" property="beginDate"  scope="request"/> </td>
    </tr>
      <tr>
      <td height="20" style="font-size:11px"><bean:message bundle="timesheet" key="outWorker.common.endDate"/>:<bean:write name="webVo" property="endDate"  scope="request"/> </td>
    </tr>
    <tr>
      <td height="20" style="font-size:11px"><bean:message bundle="timesheet" key="aprmImport.common.remark"/>:<bean:write name="webVo" property="remark" scope="request"/></td>
    </tr>
  </table>
    </logic:present>
   </logic:equal>

<logic:notEqual name="flag" value="Success!">
	 <logic:present name="webVo">
	   <table width="450" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	      <td height="20" style="font-size:11px"><bean:message bundle="timesheet" key="aprmImport.common.remark"/>:<bean:write name="webVo" property="remark" scope="request"/></td>
	    </tr>
	     </table>
	     </logic:present>
	  </logic:notEqual>
	</center>
  </body>
</html>
