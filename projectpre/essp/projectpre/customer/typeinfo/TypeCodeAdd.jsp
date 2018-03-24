
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <TITLE><bean:message bundle="projectpre" key="customer.ClientClassCodeAdd"/></TITLE>
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
          alert("Save Type Code in General");
        }
       
          function onClickSubmit(){
       this.close();
     }
     function onClickReject(){
       this.close();
     }
     function onClickCancel(){
       this.close();
       
     }
     
      //此方法用于检查必填项
     function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     }
	     
     }

    
     </script>
   
  </head>
  
  <body>
   <html:form id="ClassCode" method="post" action="/pc/addClassCode" onsubmit="return checkReq(this)" initialForcus="id.code">
   <table align=center><td class="list_range"><bean:message bundle="projectpre" key="customer.ClientClassCodeAdd" /></td></table>
  <table id="Table1" cellSpacing="0" cellPadding="0" width="100%" border="0" style="padding-left:8px">	
  <tr>
    <td class="list_range" width="120"><bean:message bundle="projectpre" key="customer.BusinessType"/></td>
    <td class="list_range" width="60"><html:text styleId="input_field" fieldtype="text" req="true" name="id.code" beanName="webVo" maxlength="50"/></td>
    <td class="list_range" width="*">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="customer.ChineseInstruction"/></td>
    <td class="list_range"><html:text styleId="input_field" fieldtype="text" req="true" name="name" beanName="webVo"/></td>
    <td class="list_range">&nbsp;</td>
  </tr>
   <tr>
     <td class="list_range"><bean:message bundle="projectpre" key="customer.Enable"/></td>
     <td class="list_range" colspan="2"><input type="checkbox"/>
   </tr>
</table>
	<table width="650" border="0" align="center">
				<tr>
					<td width="38%"></td>
					<td >
						<input class="button" name="submit" type="submit" value="<bean:message key="global.button.submit" bundle="application"/>" />
						
						<html:button styleId="btnCancel" name="btnCancel" onclick="onClickCancel()">
							<bean:message bundle="application" key="global.button.cancel" />
						</html:button>
					</td>
				</tr>
		</table>
</html:form>
  </body>
</html>
