
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <tiles:insert page="/layout/head.jsp">
      <tiles:put name="title" value=" "/>
      <tiles:put name="jspName" value=""/>
    </tiles:insert>
   
     <STYLE type="text/css">
       #input_field {width:210}
       #input_text {width:60}  
   	   #input_area {width:630}
     </STYLE>
   
  </head>
  
  <body >
  <html:form id="" method="post" action="" >
  <table id="Table1" cellSpacing="0" cellPadding="0" width="810" border="0" style="padding-left:8px">
  <tr>
    <td class="list_range" width="90"><bean:message bundle="timesheet" key="timesheet.syncexception.accountId"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text"  readonly="true" name="accountId" beanName="webVo" /></td>
    <td class="list_range" width="120"><bean:message bundle="timesheet" key="timesheet.syncexception.accountName"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text"  readonly="true" name="accountName" beanName="webVo" /></td>
  </tr>
   <tr>
    <td class="list_range" width="90"><bean:message bundle="timesheet" key="timesheet.syncexception.employeeId"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text"  readonly="true" name="employeeId" beanName="webVo" /></td>
    <td class="list_range" width="120"><bean:message bundle="timesheet" key="timesheet.syncexception.employeeName"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text"  readonly="true" name="employeeName" beanName="webVo" /></td>
  </tr>
  <tr>
    <td class="list_range" width="90"><bean:message bundle="timesheet" key="timesheet.syncexception.model"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text"  readonly="true" name="model" beanName="webVo" /></td>
    <td class="list_range" width="120"><bean:message bundle="timesheet" key="timesheet.syncexception.rct"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text"  readonly="true" name="occurTime" beanName="webVo" /></td>
  </tr>

  <tr>
    <td class="list_range"><bean:message bundle="timesheet" key="timesheet.syncexception.operation"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" name="operation" beanName="webVo" readonly="true" /></td>
    <td class="list_range"><bean:message bundle="timesheet" key="timesheet.syncexception.status"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" name="status" beanName="webVo" readonly="true" /></td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="timesheet" key="timesheet.syncexception.message"/></td>
    <td class="list_range" colspan="3">
    <html:textarea name="errorMessage" beanName="webVo" rows="10" styleId="input_area" req="false" readonly="true" />
    </td>
  </tr>
</table>
</html:form>
  </body>
</html>
