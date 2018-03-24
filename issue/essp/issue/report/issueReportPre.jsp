<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" Issue Report "/>
  <tiles:put name="jspName" value=""/>
</tiles:insert>
<logic:equal name="webVo" property="submitFlag" value="submit">
<script language="javascript">
  window.open("<%=request.getContextPath()%>/issue/report/waiting.htm","revexp","width=1000,height=600,left=5,top=80,status=yes,resizable=yes,scrollbars=yes");
  </script></logic:equal>
<script language="javascript" type="text/javascript" src="<%=contextPath%>/issue/selectbox.js"></script><%--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--%>
<script language="javascript" type="text/javascript">

function openerRefresh(){
  //opener.location.reload(true);
}

function subSearch(frm){
var isValid = submitForm(frm);
if(isValid){
    if(document.frm.accountId.value==""){
        alert("AccountId must be filled!");
        submit_flug=false;
        return false;
    }
    var begin = document.frm.dateBegin.value;
    var end = document.frm.dateEnd.value;
    if(begin>end){
        alert("Report begin date must less than end date!");
        submit_flug = false;
        return false;
    }
     return true;
}
return false;
}

//function valideFunc(){
//    issueReportForm.action="<%=contextPath%>/issue/report/issueReport.do";
//    issueReportForm.submit();
//    //window.open("<%=request.getContextPath()%>/issue/report/waiting.htm","revexp","width=1000,height=600,left=5,top=80,status=yes,resizable=yes,scrollbars=yes");
//}
</script><%--++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++--%>
<style type="text/css">#input_field {width:100%}</style>
</head>
<body bgcolor="#ffffff">
<%--************************************************************************--%>
<center>
  <font class="form_title">Issue Report</font>
  <br/>
  <html:form id="frm" name="reportForm"  action="/essp/issue/report/issueReportPre.do" target="_self" onsubmit="return subSearch(this);">
    <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
      <tr valign="middle">
        <td class="list_range" width="50" valign="top" align="right">Account</td>
        <td class="list_range" width="750" valign="top" align="left">
        <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
        <tr valign="middle">
          <td class="list_range" width="280" align="right" valign="top">
          <html:select name="accountId" beanName="webVo" styleId="input_field">
            <logic:notPresent name="webVo" property="accountList">
              <option value="">--Please select account--</option>
            </logic:notPresent>
            <logic:present name="webVo" property="accountList">
              <html:optionsCollection name="webVo" property="accountList" styleClass=""/>
            </logic:present>
          </html:select>
         </td>
         <td class="list_range" width="*" align="right" valign="top">
         </td>
       </tr>
      </table>
      </tr>
      <tr valign="middle">
        <td class="list_range" width="50" align="right" valign="top">Period</td>
        <td class="list_range" width="750" align="left" valign="top">
          <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
            <tr valign="middle">
              <td class="list_range" width="70" align="right" valign="top">
                <html:text beanName="webVo" name="dateBegin" fieldtype="dateyyyymmdd" styleId="input_field" next="dateEnd" prev="dateBegin"/>
              </td>
              <td class="list_range" width="70" align="right" valign="top">
                <html:text beanName="webVo" name="dateEnd" fieldtype="dateyyyymmdd" styleId="input_field" next="dateEnd" prev="dateBegin"/>
              </td>
              <td class="list_range" width="90" align="right" valign="top">
                <input type="radio" name="dateBy" value="Weekly" checked="checked">
                Weekly
</td>
              <td class="list_range" width="90" align="right" valign="top">
                <input type="radio" name="dateBy" value="Monthly">
                Monthly
</td>
              <td class="list_range" width="*" align="right" valign="top">
               <input type="radio" name="dateBy" value="Seasonly">
                Seasonly
 </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr valign="middle">
        <td class="list_range" width="50" align="right" valign="top">IssueType</td>
        <td class="list_range" width="750" align="left" valign="top">
          <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
            <tr valign="middle">
              <logic:iterate id="issueTypeItem" name="webVo" property="typeList">
                <input type="hidden" name="<%=issueTypeItem%>" value="0"/>
                <td class="list_range" width="100" align="right" valign="top">
                <input type="checkbox" name="type_<%=issueTypeItem%>" value="<%=issueTypeItem%>" onchange="if(this.checked) { reportForm.<%=issueTypeItem%>.value=1; } else { reportForm.<%=issueTypeItem%>.value=0; }">
                <%=issueTypeItem%>
                </td>
             </logic:iterate>
            </tr>
          </table>
        </td>
      </tr>
      <tr valign="middle">
        <td class="list_range" width="50" align="right" valign="top">Status</td>
        <td class="list_range" width="750" align="left" valign="top">
          <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center" width="100%">
            <tr valign="middle">
              <td class="list_range" width="80" align="right" valign="top">
                <input type="checkbox" name="rejected" value="">
                Rejected
</td>
              <td class="list_range" width="95" align="right" valign="top">
                <input type="checkbox" name="processing" value="">
                Processing
</td>
              <td class="list_range" width="80" align="right" valign="top">
                <input type="checkbox" name="delivered" value="">
                Delivered
</td>
              <td class="list_range" width="65" align="right" valign="top">
                <input type="checkbox" name="closed" value="">
                Closed
</td>
              <td class="list_range" width="*" align="right" valign="top">
                <input type="checkbox" name="duplation" value="">
                Duplation
</td>
            </tr>
          </table>
        </td>
      </tr>
      <tr valign="middle">
        <td colspan="2" align="right" valign="middle">&nbsp;</td>
      </tr>
      <tr valign="middle">
        <td colspan="2" align="right" valign="middle">
          <html:submit value="submit" name="submit" styleId="button"/>
          <input type="reset" value="Reset" name="reset" class="button">
        </td>
      </tr>
    </table>
    <br>
    <br>
  </html:form>
</center>
<%--************************************************************************--%>
</body>
</html>
