<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <title>Site Privilege List</title>
    <tiles:insert page="/layout/head.jsp">
      <tiles:put name="title" value=" "/>
      <tiles:put name="jspName" value=""/>
    </tiles:insert>

     <STYLE type="text/css">
        #input_field {width:160}
        #input_area {width:700}
     </STYLE>
  <script language="javascript" type="text/javascript">  	
      
      	function onSaveData(){ 
      		var tempName= tempForm.templateName.value;     
      		var tempCode= tempForm.templateCode.value;
      		if (trim(tempName)==""){
		        alert("<bean:message bundle="timesheet" key="timesheet.template.formatName"/>");
		        document.tempForm.templateName.focus();
		        return;
	    	}
	    	if (trim(tempCode)==""){
		        alert("<bean:message bundle="timesheet" key="timesheet.template.fillIn"/>"+"<bean:message bundle="timesheet" key="timesheet.template.code"/>");
		        document.tempForm.templateCode.focus();
		        return;
	    	}              
          	tempForm.action = "<%=contextPath%>/timesheet/template/saveTemplate.do";
          	tempForm.submit();    
    	} 
    	
    	function trim(str){  //删除左右两端的空格
 			return str.replace(/(^\s*)|(\s*$)/g, "");
		}
		
		function refreshUp(){
			var upFrame=window.parent.templateList;
			upFrame.location="<%=contextPath%>/timesheet/template/listTemplate.do"       
        }
        function onBodyLoad(){
			refreshUp();
        }
   </script>
  </head>
  <body>

    <html:form  name="tempForm" action="/timesheet/template/saveTemplate.do" method="post"> 
    <html:hidden beanName="webVo" name="rid" />
    <table width="800" border="0">
    <tr>
	    <td class="list_range" width="90"><bean:message bundle="timesheet" key="timesheet.template.code"/></td>
	    <td class="list_range" width="160"><html:text styleId="input_field" req="true" fieldtype="text"  name="templateCode" beanName="webVo" maxlength="12"/></td>
 		<td class="list_range" width="90"><bean:message bundle="timesheet" key="timesheet.template.name"/></td>
	    <td class="list_range" width="160"><html:text styleId="input_field" req="true" fieldtype="text" name="templateName" beanName="webVo" maxlength="60"/></td>
	 	<td class="list_range" width="200"><bean:message bundle="timesheet" key="timesheet.template.activity"/>
		    <html:checkbox styleId="CheckBox" name="rst"  checkedValue="N" beanName="webVo" uncheckedValue ="D" defaultValue="N" />
		</td>
 	</tr> 
  	<tr>
	    <td class="list_range" width="90"><bean:message bundle="timesheet" key="timesheet.template.description"/></td>
	    <td class="list_range" colspan="4"><html:textarea styleId="input_area" name="description" beanName="webVo" rows="6" maxlength="250"/></td>
  	</tr>
    <tr><td class="list_range" width="90"><bean:message bundle="timesheet" key="timesheet.template.method"/></td>
    <td align="left" colspan="4">
    <TABLE width="100%">
	    <logic:iterate id="item"              
	                  name="webVo"
	                  property="methodMap"
	                  scope="request"
	                  indexId="a">
		    <tr>
			    <td class="list_range" align="left">
			     <input name="type" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="methodId"/>' <bean:write name="item" property="status"/> />
			     <bean:write name="item" property="methodName"/>          
			    </td>  
		    </tr>   
	    </logic:iterate>
	</table>
	</td>
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
