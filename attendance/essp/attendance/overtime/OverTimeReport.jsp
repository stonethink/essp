<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value="Attendance Report"/>
        <tiles:put name="jspName" value="."/>
      </tiles:insert>
</head>
<script language="javascript">
function sumbitReport(){
  if(submitForm(document.frm2)){
    var acntRid = document.frm2.acntRid.value;
    var endPeriod = document.frm2.endDate.value;
    var beginPeriod = document.frm2.beginDate.value;
    if(endPeriod < beginPeriod){
      alert("The end date must be larger than begin date!");
      return;
    }
    var url = "/essp/ExcelExport?acntRid=" + acntRid +
    "&beginDate=" + beginPeriod +
    "&endDate=" + endPeriod +
    "&actionId=FATOverTimeExcelAction";
    window.open(url,"","width=1000,height=600,left=5,top=80,status=yes,resizable=yes,scrollbars=yes");
    location.reload();
  }
}
</script>
<body>
    <center class="form_title">
        Over Time Report
    </center>
    <br />
    <html:form id="frm2" action="/attendance/overtime/OverTimeReportPre"  onsubmit="return submitForm(this);">
      <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
        <tr>
          <td class="list_range" width="100" height="40" align="left">Project Account</td>
          <td class="list_range" height="40" colspan="3" >
            <html:select name="acntRid" styleClass="project_select" styleId="project_select" req="true" next="acntName" >
              <html:optionsCollection property="acntList" name="webVo" >
              </html:optionsCollection>
            </html:select>
          </td>
        </tr>
        <tr>
          <td class="list_range" width="100" height="40">Report Period</td>
          <td class="list_range" width="100" height="40">
            <html:text beanName="webVo" name="beginDate" fieldtype="dateyyyymmdd" styleId="input_common" req="true" maxlength="100" ondblclick="getDATE(this);"/>
          </td>
          <td width="23"  height="40" align="center">
            ~
          </td>
          <td class="list_range" width="100" height="40">
            <html:text beanName="webVo" name="endDate" fieldtype="dateyyyymmdd" styleId="input_common" req="true" maxlength="100" ondblclick="getDATE(this);"/>
          </td>
        </tr>
        <tr>
          <td colspan="4" align="right" height="50" valign="bottom">
            <html:button value="submit" name="submit" styleId="button" onclick="sumbitReport();" />
            <input type="reset" value="Reset" name="reset" class="button" >
            </td>
          </tr>
        </table>
      </html:form>
    </body>
</html>
