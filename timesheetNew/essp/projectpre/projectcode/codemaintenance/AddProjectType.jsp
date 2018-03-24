
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<bean:define id="kind" value="<%=request.getParameter("kind")%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title><bean:message bundle="projectpre" key="projectCode.typeMaintenance" /></title>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
		 <STYLE type="text/css">
       #input_field {width:210}
      
     </STYLE>
		<script type="text/javascript" language="javascript">
		 //此方法用于检查必填项
     function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     }else{
	       return true;
	     }  
	   }
   
     function onClickCancel(){
       this.close();
     }
   
     </script>
	</head>
	<body>
		
			<html:form id="Parameter" method="post" action="/codemaintenance/addProjectType.do" onsubmit="return checkReq(this)" initialForcus="compId.kind">
				<input type="hidden" name="kind" value="<%=kind%>" />
				<table width="350"  border="0" align="center" >

					<tr>
						<td class="list_range" >
							&nbsp;
						</td>
						<td class="list_range" >
							&nbsp;
						</td>
					</tr>


					<tr>

						<td class="list_range">
							<bean:message bundle="projectpre" key="projectCode.No" />
						</td>
						<td class="list_range">
							<html:text styleId="input_field" fieldtype="text" name="code" beanName="Parameter" maxlength="25" req="true" />
						</td>

					</tr>
					<tr>

						<td class="list_range">
							<bean:message bundle="projectpre" key="projectCode.Item" />
						</td>
						<td class="list_range">
							<html:text styleId="input_field" fieldtype="text" name="name" beanName="Parameter" maxlength="25" req="true" />
						</td>

					</tr>
					<tr>

						<td class="list_range">
							<bean:message bundle="projectpre" key="projectCode.Sequence" />
						</td>
						<td class="list_range">
							<html:text styleId="input_field" fieldtype="number" fmt="8.0" name="sequence" beanName="Parameter" maxlength="25" req="true" />
						</td>

					</tr>
					<tr>
                       <td class="list_range"><bean:message bundle="projectpre" key="projectCode.Desc"/></td>
                       <td class="list_range" >
                       <html:textarea name="description" beanName="webVo" rows="4" styleId="input_field" req="false" maxlength="250"  />
                       </td>
                    </tr>
					<tr>
						<td class="list_range">
							&nbsp;
						</td>
						<td class="list_range">
							&nbsp;
						</td>
					</tr>

					<tr>
						<td colspan="2" align="center">
                           
							<input class="button" name="submit" type="submit" value="<bean:message key="global.button.submit" bundle="application"/>" />
							&nbsp;&nbsp;&nbsp;
							<input class="button" type="button" name="cancel" value="<bean:message key="global.button.cancel" bundle="application"/>" onClick="onClickCancel()" />
						</td>
					</tr>
				</table>
			</html:form>
		
	</body>
</html>
