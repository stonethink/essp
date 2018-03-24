<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value="Project Weekly Report"/>
        <tiles:put name="jspName" value="ProjectWeeklyReport"/>
    </tiles:insert>
    <logic:equal name="webVo" property="submitFlag" value="submit">
        <script language="javascript">
        window.open("<%=request.getContextPath()%>/pms/report/waiting.htm","revexp","width=1000,height=600,left=5,top=80,status=yes,resizable=yes,scrollbars=yes");
        </script>
    </logic:equal>
<script language="javascript">
function onChangeProject(index){
    var acntRid = document.frm2.acntRid.options[index].value;
    var acntStr = document.frm2.acntRid.options[index].text;
    try{
        var dash_index = acntStr.indexOf("--");
        //alert(dash_index);
        var acntId = acntStr.substring(0,dash_index);
        var acntName = acntStr.substring(dash_index + 2);
        //document.frm2.acntRid.value = acntRid;
        document.frm2.acntId.value = acntId;
        document.frm2.acntName.value = acntName;
    }catch(ex){
        alert("Account show style is not dash!");
    }
    //alert("rid:" +document.frm1.acntRid.value + "  acntId:" + document.frm1.acntId.value + "  acntName:"+document.frm1.acntName.value);
}
function subSearch(frm2){
    var isValid = submitForm(frm2);
    if(isValid){
        var begin = document.frm2.reportBegin.value;
        var end = document.frm2.reportEnd.value;
        if(begin > end){
            alert("Report begin date must less than end date!");
            submit_flug = false;
            return false;
        }

        return true;
    }
    return false;
}
</script>
</head>

<body>
    <center class="form_title">
        Project Weekly Report
    </center>
    <br />
   <%----%>
     <html:form id="frm2" action="/pms/ProjectWeeklyReportPre"  onsubmit="return subSearch(this);">
        <html:hidden property="acntId" />
        <html:hidden property="acntName" />
         <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
            <tr>
                <td class="list_range" width="100" height="40" align="left">Project Account</td>
                <td class="list_range" height="40" colspan="3" >
                    <html:select name="acntRid" styleClass="project_select" styleId="project_select" req="true" next="acntName" onchange="onChangeProject(this.options.selectedIndex)" >
                        <html:optionsCollection property="accounts" name="webVo" >
                        </html:optionsCollection>
                    </html:select>
                </td>
            </tr>
            <tr>
                <td class="list_range" width="100" height="40">Reported By</td>
                <td class="list_range" height="40" colspan="3">
                    <html:text beanName="webVo" name="reportedBy" fieldtype="text" prev="acntName" next="reportDate"  maxlength="100" readonly="true" styleId="project_select"/>
                </td>
            </tr>
            <tr>
                <td class="list_range" width="100" height="40">Report Date</td>
                <td class="list_range" width="100" height="40" colspan="3">
                    <html:text beanName="webVo" name="reportDate" fieldtype="dateyyyymmdd" styleId="input_common" prev="reportedBy" next="reportBegin"    req="true" maxlength="100" ondblclick="getDATE(this);"/>
                </td>
            </tr>
            <tr>
                <td class="list_range" width="100" height="40">Report Period</td>
                <td class="list_range" width="100" height="40">
                    <html:text beanName="webVo" name="reportBegin" fieldtype="dateyyyymmdd" styleId="input_common" prev="reportDate" next="reportEnd"    req="true" maxlength="100" ondblclick="getDATE(this);"/>
               </td>
               <td width="23"  height="40" align="center">
                   ~
               </td>
                <td class="list_range" width="100" height="40">
                    <html:text beanName="webVo" name="reportEnd" fieldtype="dateyyyymmdd" styleId="input_common" prev="reportBegin" next="report"    req="true" maxlength="100" ondblclick="getDATE(this);"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="right" height="50" valign="bottom">
                    <html:submit value="submit" name="submit" styleId="button" />
                    <input type="reset" value="Reset" name="reset" class="button" >
                </td>
            </tr>
        </table>
    </html:form>
</body>
</html>
