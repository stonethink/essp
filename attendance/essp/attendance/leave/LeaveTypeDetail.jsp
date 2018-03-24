<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<%String actionURI = "",title = "";%>
<logic:equal value="Add" name="webVo" property="functionId" >
  <%actionURI = "/attendance/leave/LeaveTypeAdd";
  title = "Leave Type Add";%>
</logic:equal>
<logic:equal value="Edit" name="webVo" property="functionId">
  <%actionURI = "/attendance/leave/LeaveTypeUpdate";
  title = "Leave Type Edit";%>
</logic:equal>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="<%=title%>"/>
  <tiles:put name="jspName" value="LeaveType"/>
</tiles:insert>
</head>
<body>
  <logic:equal value="Add" name="webVo" property="functionId" >
    <center class="form_title">Leave Type Add</center>
  </logic:equal>
  <logic:equal value="Edit" name="webVo" property="functionId" >
    <center class="form_title">Leave Type Edit</center>
  </logic:equal>
<br/>
<html:form action="<%=actionURI%>" onsubmit="return submitForm(this);">
  <table bgcolor="ffffff" cellpadding="1" cellspacing="1" border="0" align="center">
    <tr>
      <td class="list_range" width="80">Type Name</td>
      <td class="list_range" width="120">
        <logic:equal value="Add" name="webVo" property="functionId" >
          <html:text beanName="webVo" name="leaveName" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
        </logic:equal>
        <logic:equal value="Edit" name="webVo" property="functionId">
          <html:text beanName="webVo" readonly="true" name="leaveName" fieldtype="text" styleId="input_common" next="account" prev="Close" req="true" maxlength="25"/>
        </logic:equal>

      </td>
      <td class="list_range" width="135">Settlement Way</td>
      <td class="list_range" width="70">
        <html:select name="settlementWay" beanName="webVo" styleId="">
          <html:optionsCollection name="webVo"  property="settlementWayList">
          </html:optionsCollection>
        </html:select>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Max Days</td>
      <td class="list_range" width="120">
        <html:text beanName="webVo" name="maxDay" fieldtype="number" fmt="8.0" styleId="input_common" next="account" prev="Close" maxlength="25"/>
      </td>
      <td class="list_range" width="100">Relation</td>
      <td class="list_range" width="70">
        <html:select name="relation" beanName="webVo" styleId="">
          <html:optionsCollection name="webVo"  property="relationList">
          </html:optionsCollection>
        </html:select>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Status</td>
      <td class="list_range" >
        <html:radiobutton name="status" beanName="webVo" styleId="" value="N" /> Enable
        <html:radiobutton name="status" beanName="webVo" styleId="" value="X" /> Disable
      </td>
      <td class="list_range" >Sequence</td>
      <td class="list_range" >
        <html:text beanName="webVo" req="true"  name="seq" fieldtype="number" fmt="8.0" styleId="input_common" next="account" prev="Close" maxlength="25"/>
      </td>
    </tr>
    <tr>
      <td class="list_range" width="80">Description</td>
      <td class="list_range"  colspan="3">
        <html:textarea name="description" beanName="webVo" styleId="" maxlength="200" rows="3" cols="46" />
      </td>
    </tr>
    <tr>
      <td class="" colspan="4" align="right">
        <input type="submit" name="submit" value="Submit" class="button" />
        <input type="reset"  name="reset" value="Reset" class="button"/>
        <input type="button"  name="cancel" value="Cancel" class="button"  onclick="window.close();"/>
      </td>
    </tr>
  </table>
</html:form>
