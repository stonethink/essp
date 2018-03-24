<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%String context = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <head>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>   
<STYLE type="text/css">
    #input_field {width:30}
    #input_text {width:60}  
   
</STYLE>
<SCRIPT language="javascript" type="text/javascript">
function onClickRadioBox(i){
try{
if (i ==1){  
    document.forms[0].length1.readOnly=false;
    document.forms[0].length.readOnly=false;
    document.forms[0].firstChar.readOnly=false;
    document.forms[0].firstChar1.readOnly=false;
    //alert(document.all.codingMethod[0].disabled);
   document.all.codingMethod[0].disabled=false;
   document.all.codingMethod[1].disabled=false;
   document.all.codingMethod1[0].disabled=false;
    document.all.codingMethod1[1].disabled=false;
    // alert(document.all.codingMethod[0].disabled);    
    document.forms[0].currentSeq.readOnly=false;
    document.forms[0].currentSeq1.readOnly=false;
    document.forms[0].length.focus();
    document.forms[0].length1.focus();
    document.forms[0].firstChar1.focus();
    document.forms[0].firstChar.focus();
    document.forms[0].currentSeq.focus();
    document.forms[0].currentSeq1.focus();
    document.forms[0].currentSeq1.blur();

 } 

 else{
    document.forms[0].length1.readOnly=true;
    document.forms[0].length.readOnly=true;
    document.forms[0].firstChar.readOnly=true;
    document.forms[0].firstChar1.readOnly=true;
    // alert(document.all.codingMethod[0].disabled);
     document.all.codingMethod[0].disabled=true;
     document.all.codingMethod[1].disabled=true;
     document.all.codingMethod1[0].disabled=true;
     document.all.codingMethod1[1].disabled=true;
   //  alert(document.all.codingMethod[0].disabled); 
    document.forms[0].currentSeq.readOnly=true;
    document.forms[0].currentSeq1.readOnly=true;
    document.forms[0].length.focus();
    document.forms[0].length1.focus();
    document.forms[0].firstChar1.focus();
    document.forms[0].firstChar.focus();
    document.forms[0].currentSeq.focus();
    document.forms[0].currentSeq1.focus();
    document.forms[0].currentSeq1.blur();

 } 
}catch(e){}
}

  
//此方法用于检查必填项
     function checkReq(form){   
	     if(!submitForm(form)){	     
    	 	return false;    	 
	     }else{
	     	return true;
	     }	     
     }

     //此方法用于提交数据	
     function onSaveData(){ 
     try{ 
       var form=document.forms[0];  
       var maxLength = document.all.length.value;
       var maxLength1 = document.all.length1.value;      
         if((document.all.length.value < document.all.currentSeq.value.length) || (document.all.length1.value < document.all.currentSeq1.value.length)){ 
          alert("<bean:message bundle="application" key="global.code.beyond"/>");
          return;   
        }else if(maxLength>8 || maxLength1>8){
         alert("<bean:message bundle="application" key="global.code.long"/>");
         return;
        }else{
          var form=document.forms[0];
          if(!checkReq(form)){
          	return;
         }
          form.submit();
     }
     }catch(e){}
   }
     function onCheck(){
    if(document.all.codingGenerate[0].checked){
        onClickRadioBox(1);
     }
     else{
        onClickRadioBox(2);
    }
     }
</SCRIPT>
  </head>
 
    <body style="overflow-x:auto;overflow-y:hidden;padding-right:10px">
     <html:form  id="IdSetting" method="post" action="/customer/maintenance/updateIdSetting" onsubmit="return onClickSubmit(this)" initialForcus="length" >
        <table  width="65%" border="0" >
                <tr>
				   <td class="list_range" >&nbsp;</td>
					<td class="list_range" >&nbsp;</td>
					<td class="list_range" >&nbsp;</td>
					<td class="list_range">&nbsp;</td>
					<td class="list_range" >&nbsp;</td>
				</tr>
        <tr>
           
		    <td class="list_range" colspan="4">
		    <html:radiobutton styleId=""  name="codingGenerate"  beanName="webVo" value="SystemCode" onclick="onClickRadioBox(1)"  /><bean:message bundle="projectpre" key="customer.SystemCode"/>		
		    <html:radiobutton styleId=""  name="codingGenerate"  beanName="webVo" value="ManCode"  onclick="onClickRadioBox(2)"  /><bean:message bundle="projectpre" key="customer.ManualCode"/></td>
		</tr>	
		<tr>
		   
		    <td class="list_range" width="100"> <bean:message bundle="projectpre" key="customer.GroupCode1"/> </td>
		      <td class="list_range" width="40">   <bean:message bundle="projectpre" key="customer.length"/></td>
		    <td class="list_range" width="60">
				<html:text styleId="input_field" fieldtype="text" name="length" beanName="webVo" maxlength="2"  req="true" />					
			</td>
			<td class="list_range" width="160" >&nbsp;</td>
		    <td class="list_range" width="70"><bean:message bundle="projectpre" key="customer.FirstLetter"/></td>
		    <td class="list_range">
				<html:text styleId="input_field" fieldtype="text" name="firstChar" beanName="webVo" maxlength="2"  req="true"/>					
			</td>
		</tr>
		<tr>
		  
		    <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientCode"/></td>
		     <td class="list_range" width="40">   <bean:message bundle="projectpre" key="customer.length"/></td>
		    <td class="list_range">
				<html:text styleId="input_field" fieldtype="text" name="length1" beanName="webVo" maxlength="2"  req="true"/>					
			</td>
			<td class="list_range" width="160" >&nbsp;</td>
		    <td class="list_range"><bean:message bundle="projectpre" key="customer.FirstLetter"/></td>
		    <td class="list_range">
				<html:text styleId="input_field" fieldtype="text" name="firstChar1" beanName="webVo" maxlength="2" req="true" />					
			</td>
		</tr>
		
		<tr>
		   
		    <td class="list_range" width="180"><bean:message bundle="projectpre" key="customer.GroupCodeStyle"/>	
		    </td>
		    <td class="list_range" colspan="4">
		    <html:radiobutton styleId=""  name="codingMethod"  beanName="webVo" value="YearMonthSerialNumber"  /><bean:message bundle="projectpre" key="customer.YearandMonthSerialNumber"/>
		    <html:radiobutton styleId=""  name="codingMethod"  beanName="webVo" value="SerialNumber"  /> <bean:message bundle="projectpre" key="customer.SerialNumber"/></td>
		</tr>
		<tr>
		   

		    <td class="list_range" ><bean:message bundle="projectpre" key="customer.ClientCodeStyle"/>	
            </td>
            <td class="list_range" colspan="4">

		    <html:radiobutton styleId=""  name="codingMethod1"  beanName="webVo" value="YearMonthSerialNumber"  /> <bean:message bundle="projectpre" key="customer.YearandMonthSerialNumber"/>
		     <html:radiobutton styleId=""  name="codingMethod1"  beanName="webVo" value="SerialNumber"  /><bean:message bundle="projectpre" key="customer.SerialNumber"/></td>
		</tr>
	   </table>
	   <table>
		<tr>
		   
		    <td class="list_range" width="180" ><bean:message bundle="projectpre" key="customer.CurrentNumberOfSystemGroupCode"/></td>	
		    <td class="list_range">
				<html:text styleId="input_text" fieldtype="text" name="currentSeq" beanName="webVo"   maxlength="8" req="true" /> 
			</td>	
		</tr>
		<tr>
		   
		    <td class="list_range" ><bean:message bundle="projectpre" key="customer.CurrentnumberOfSystemClientCode"/></td>	
		    <td class="list_range">
				<html:text styleId="input_text" fieldtype="text" name="currentSeq1" beanName="webVo"   maxlength="8" req="true" /> 
			</td>	
		</tr>
</table>
 <script type="text/javascript" language="javascript">
    onCheck();
  </script>
</html:form>
  </body>
</html>
