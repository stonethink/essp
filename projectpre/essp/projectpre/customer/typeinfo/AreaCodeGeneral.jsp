
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
       #input_field {width:60}
       #input_text {width:60}  
   
     </STYLE>
     <script language="javascript" type="text/javascript">
        function onSaveData(){
          alert("Save in General?");
        }
        function onSubmitData(){
          alert("Submit in General?");
        }
     </script>
   
  </head>
  
  <body>
  <html:form name="CodeUpdate" id="TypeCodeUpdateForm" method="post" action="">
  <table id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0" style="padding-left:8px">
	
  <tr>
    <td class="list_range" width="120"><bean:message bundle="projectpre" key="customer.CountryCode"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text" name="input1" beanName="webVo" value="WH0" maxlength="50"/></td>
    <td class="list_range" width="*">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="customer.ChineseInstruction"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" name="input2" beanName="webVo" value="H0"/></td>
    <td class="list_range">&nbsp;</td>
  </tr>
  <tr>
     <td class="list_range"><bean:message bundle="projectpre" key="customer.Enable"/></td>
  <td class="list_range" colspan="2"><input type="checkbox"/>
   </tr>
</table>

</html:form>
  </body>
</html>
