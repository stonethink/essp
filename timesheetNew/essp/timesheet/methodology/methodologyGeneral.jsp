
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
       #input_field {width:400}
       #input_text {width:60}  
   	   #input_area {width:630}
     </STYLE>
     
      <script language="javascript" type="text/javascript">  	
      	function onSaveData(){ 
      		var name= methodForm.name.value;
      	    if (trim(name)==""){
		        alert("<bean:message bundle="timesheet" key="timesheet.template.formatName"/>");
		        document.methodForm.name.focus();
		        return;
	    	}   
          	methodForm.action = "<%=contextPath%>/timesheet/methodology/saveMethod.do";
          	methodForm.submit();          	
    	}  
    	function trim(str){  //删除左右两端的空格
 			return str.replace(/(^\s*)|(\s*$)/g, "");
		}
		
        function refreshUp(){
       		var upFrame=window.parent.methodList;			
			upFrame.location="<%=contextPath%>/timesheet/methodology/listMethod.do";         	
        }
        function onBodyLoad(){
			refreshUp();
        }
   	</script>
  </head>
  
  <body >
  	<html:form name="methodForm" method="post" action="/timesheet/methodology/saveMethod.do" >
  		<html:hidden beanName="webVo" name="rid" />
	  	<table id="Table1" cellSpacing="0" cellPadding="0" width="600" border="0" style="padding-left:8px">
	  	<tr>
		    <td class="list_range" width="90"><bean:message bundle="timesheet" key="timesheet.template.name"/></td>
		    <td class="list_range" width="400"><html:text styleId="input_field" req="true" fieldtype="text"  name="name" beanName="webVo" maxlength="25" /></td>
	 	</tr>
	 	<tr>
		    <td class="list_range" width="90"><bean:message bundle="timesheet" key="timesheet.template.description"/></td>
		    <td class="list_range" width="400"><html:textarea styleId="input_field"  name="description" beanName="webVo" rows="8" maxlength="250"/></td>
	  	</tr>
	  	<tr>
	  	<td class="list_range" width="90"><bean:message bundle="timesheet" key="timesheet.template.activity"/></td>
		  	<td > 
		  		<html:checkbox styleId="CheckBox" name="rst"  checkedValue="N" beanName="webVo" uncheckedValue ="D" defaultValue="N" />
			</td>
		</tr>
		</table>
	</html:form>
  </body>
	<SCRIPT language="JavaScript" type="text/JavaScript">	
		var flag='<bean:write name="RefreshUpPage" scope="request"/>';	 
		if(flag=='true'){
	        onBodyLoad();
        }
	</SCRIPT>
</html>
