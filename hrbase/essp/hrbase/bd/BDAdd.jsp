
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title><bean:message bundle="hrbase" key="hrbase.bd.bdcodesetting"/></title>
    <tiles:insert page="/layout/head.jsp">
      <tiles:put name="title" value=" "/>
      <tiles:put name="jspName" value=""/>
    </tiles:insert>
   
     <STYLE type="text/css">
       #input_field {width:220}
       #input_text {width:60}  
   
     </STYLE>
     <script language="javascript" type="text/javascript">
     //此方法用于检查必填项
     function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     }
     }

     function onClickCancel(){
       this.close();
     }
       
     </script>
   
  </head>
  
  <body>
  <html:form  id="bdCode" method="post" action="/hrb/bd/addBdCode" onsubmit="return checkReq(this)" initialForcus="bdCode">
   <table><td class="list_range"></td></table>
  <table id="Table1" cellSpacing="0" cellPadding="0" align="center" width="500" border="0" style="padding-left:8px">
  <tr>
    <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.bd.bdcode"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text" name="bdCode" beanName="webVo" req="true" maxlength="25"/></td>
    <td class="list_range" width="*">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.bd.bdname"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" name="bdName" beanName="webVo" req="true" maxlength="25"/></td>
    <td class="list_range">&nbsp;</td>
  </tr>
   <tr>
     <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.enable"/></td>
     <td class="list_range" colspan="2"><html:multibox styleId="width:100px" name="status" beanName="webVo" value="true"/>
    </tr>
    <tr>
      <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.bd.isAchieveBelong"/></td>
     <td class="list_range" colspan="2"><html:multibox styleId="width:100px" name="achieveBelong" beanName="webVo" value="true"/>
   </tr>
  <tr>
    <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.bd.description"/></td>
    <td class="list_range" colspan="2">
    <html:textarea name="description" beanName="webVo" rows="4" styleId="input_field" req="false" maxlength="250"  />
    </td>
  </tr>
   <tr>
    <td class="list_range">&nbsp;</td>
    <td class="list_range">&nbsp;</td>
    <td class="list_range">&nbsp;</td>
  </tr>
  
</table>
	<table width="300" border="0" align="center">
				<tr >
					<td align="center">
						<html:submit styleId="btnOk" name="submit">
						<bean:message bundle="application" key="global.button.confirm"/>
						</html:submit>
						<html:button styleId="btnCancel" name="btnCancel" onclick="onClickCancel()">
						<bean:message bundle="application" key="global.button.cancel"/>
						</html:button>
					</td>
				</tr>
		</table>

</html:form>
  </body>
</html>
