<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <tiles:insert page="/layout/head.jsp">
      <tiles:put name="title" value=" "/>
      <tiles:put name="jspName" value=""/>
    </tiles:insert>   
     <STYLE type="text/css">
       #input_field {width:100}
       #input_text {width:60}  
   	   #input_area {width:700}
   	   #input_weightField {width:50}
   	   #input_Namefield{width:500}
     </STYLE>     
      <script language="javascript" type="text/javascript">  	
      	function onSaveData(){
      	var startOffset = stepForm.planStartOffset.value;
      	var finishOffset = stepForm.planFinishOffset.value;
      	var seqNum = stepForm.seqNum.value;
	      	if (trim(stepForm.procName.value)==""){
		        alert("<bean:message bundle="timesheet" key="timesheet.template.fillIn"/>"+"<bean:message bundle="timesheet" key="timesheet.template.stepName"/>");
		        document.stepForm.procName.focus();
		        return;
	    	}
	    	if (trim(stepForm.category.value)==""){
		        alert("<bean:message bundle="timesheet" key="timesheet.template.fillIn"/>"+"<bean:message bundle="timesheet" key="timesheet.template.catogory"/>");
		        document.stepForm.category.focus();
		        return;
	    	}
	    	if (trim(seqNum)==""||isNotDigit(seqNum)){
		        alert("<bean:message bundle="timesheet" key="timesheet.template.fillIn"/>"+"<bean:message bundle="timesheet" key="timesheet.template.stepSeq"/>"+"<bean:message bundle="timesheet" key="timesheet.template.number"/>");
		        document.stepForm.seqNum.focus();
		        return;
	    	}
	    	if (trim(startOffset)==""||isNotDigit(startOffset)){
		        alert("<bean:message bundle="timesheet" key="timesheet.template.fillIn"/>"+"<bean:message bundle="timesheet" key="timesheet.template.startOffset"/>"+"<bean:message bundle="timesheet" key="timesheet.template.number"/>");
		        document.stepForm.planStartOffset.focus();
		        return;
	    	}
	    	if (trim(finishOffset)==""||isNotDigit(finishOffset)){
		        alert("<bean:message bundle="timesheet" key="timesheet.template.fillIn"/>"+"<bean:message bundle="timesheet" key="timesheet.template.finishOffset"/>"+"<bean:message bundle="timesheet" key="timesheet.template.number"/>");
		        document.stepForm.planFinishOffset.focus();
		        return;
	    	} 
	    	if(!isFloat(stepForm.procWt.value)){
	    		alert("<bean:message bundle="timesheet" key="timesheet.template.formatWeight"/>");
	    		document.stepForm.procWt.focus();
		        return;
	    	}
	    	if(parseInt(startOffset)>parseInt(finishOffset)){
	    		alert("<bean:message bundle="timesheet" key="timesheet.template.bigger"/>");
	    		return;	    		
	    	}
		       
          	stepForm.action = "<%=contextPath%>/timesheet/template/saveStep.do";
          	stepForm.submit();          	
    	}  
    	
    	function refreshUp(tempId){
			var upFrame=window.parent.stepList;	
			upFrame.location="<%=contextPath%>/timesheet/template/listStep.do?tempId="+tempId;      
        }
    	
    	function trim(str){  //删除左右两端的空格
 			return str.replace(/(^\s*)|(\s*$)/g, "");
		}
		function isNotDigit(string) //数字正则 
        {  
            var patrn=/^[0-9]+$/;             
            if (!patrn.exec(string)) 
                return true; 
            else 
               	return false;    
        }
        function isFloat(string) //数字正则 
        {  
            patrn=/^\d*(\.\d+)?$/;             
            if (string.match(patrn)) 
                return true; 
            else 
               	return false;    
        }
		function onBodyLoad(){
			var tempId=stepForm.tempRid.value;
			refreshUp(tempId);
        }
   </script>   
  </head>
  <body>
    <html:form  name="stepForm" action="" method="post"> 
    <html:hidden beanName="webVo" name="rid" />  
    <html:hidden beanName="webVo" name="tempRid" />  
    <table width=850 border="0">
    <tr>
	    <td class="list_range" width="100"><bean:message bundle="timesheet" key="timesheet.template.catogory"/></td>
	    <td class="list_range" ><html:text styleId="input_field" req="true" fieldtype="text" name="category" beanName="webVo" maxlength="7"/></td>
	    <td class="list_range" width="100"><bean:message bundle="timesheet" key="timesheet.template.stepName"/></td>
	    <td class="list_range" colspan="6"><html:text styleId="input_Namefield" req="true" fieldtype="text"  name="procName" beanName="webVo" maxlength="30"/></td>
	   
 	</tr>
 	<tr>
 		<td class="list_range" width="100" ><bean:message bundle="timesheet" key="timesheet.template.stepSeq"/></td>
	    <td class="list_range" ><html:text styleId="input_field" req="true" fieldtype="text" name="seqNum" beanName="webVo" maxlength="10"/></td> 
	    <td class="list_range" width="100"><bean:message bundle="timesheet" key="timesheet.template.startOffset"/></td>
	    <td class="list_range"><html:text styleId="input_weightField" req="true" fieldtype="text" name="planStartOffset" beanName="webVo" maxlength="10"/></td>
	    <td class="list_range" width="100"><bean:message bundle="timesheet" key="timesheet.template.finishOffset"/></td>
	   	<td class="list_range" width="100"><html:text styleId="input_weightField" req="true" fieldtype="text" name="planFinishOffset" beanName="webVo" maxlength="10"/></td>
	   	<td class="list_range" width="100"><bean:message bundle="timesheet" key="timesheet.template.weight"/></td>
	   	<td class="list_range" width="100"><html:text  styleId="input_weightField" fieldtype="text" name="procWt" beanName="webVo" maxlength="10"/></td>
	   	<td class="list_range" width="120"><bean:message bundle="timesheet" key="timesheet.template.isCorp"/><html:checkbox styleId="CheckBox" name="isCorp"  checkedValue="Y" beanName="webVo" uncheckedValue ="N" defaultValue="N" /></td>
  	</tr>
  	<tr> 
  		<td class="list_range" width="100"><bean:message key="timesheet.template.description" bundle="timesheet"/></td>
  		<td class="list_range" colspan="8"><html:textarea styleId="input_area" name="procDescr" beanName="webVo" rows="8" maxlength="1000"/></td>
  	</tr>
    </table>
    </html:form>     
  
  <SCRIPT language="JavaScript" type="text/JavaScript">	
		var flag='<bean:write name="RefreshUpPage" scope="request"/>';	 
		if(flag=='true'){
			
	        onBodyLoad();
        }
	</SCRIPT>
	</body>
</html>
