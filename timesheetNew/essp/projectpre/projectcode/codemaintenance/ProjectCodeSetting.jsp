
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="server.essp.projectpre.service.constant.Constant"%>
<%@ page import="server.essp.projectpre.ui.project.maintenance.AfProjectId"%>
<%
    // AfProjectId af = (AfProjectId)request.getAttribute("webVo"); 
    // String codingMethod = Constant.YEAR_MONTH_SERIAL_NUMBER;
   //  if (af.getCodingMethod()!=null){
   //    codingMethod = af.getCodingMethod();
   // }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <TITLE><bean:message bundle="projectpre"  key="projectCode.ProjectCodeMaintenance.cardTitle.ProjectCodeSetting"/></TITLE>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
		

		<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

<STYLE type="text/css">
    #input_field {width:30}
    #input_text {width:60}  
   
</STYLE>

<SCRIPT language="javascript" type="text/javascript">
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
    // alert("ok");
     var codeLength = document.all.currentSeq.value.length;
     var maxLength = document.all.length.value;
    if(codeLength>maxLength) {
         alert("<bean:message bundle="application"  key="global.code.beyond"/>");
    } else if(maxLength>8){
         alert("<bean:message bundle="application"  key="global.code.long"/>");
    } else {
          var form=document.forms[0];
          if(!checkReq(form)){
          	return;
         }
          form.submit();
     }
     }catch(e){}
   }
//function onSee(){
//   if(document.all.codingMethod[0].value="YearMonthSerialNumber"){
//    document.all.codingMethod[0].checked=true;
//    }
 //   else{
//     document.all.codingMethod[1].checked=true;
 //   }
  //alert(document.all.codingMethod[0].value);
 // alert(document.all.codingMethod[1].value);
//}
</SCRIPT>
	</head>

	<body style="overflow-x:auto;overflow-y:hidden;padding-right:10px"  >
		<html:form id="ProjectId" method="post" action="/project/maintenance/addOrUpdateProjectSetting" onsubmit="return checkReq(this)" initialForcus="length">
			<table id="Table1" cellSpacing="0" cellPadding="0" width="70%" border="0" style="padding-left:8px">
				<tr>
					
					<td class="list_range" width="25%">&nbsp;</td>
					<td class="list_range" width="40%">&nbsp;</td>
					<td class="list_range" width="60%">&nbsp;</td>
					<td class="list_range" >&nbsp;</td>
					<td class="list_range" width="*">&nbsp;</td>
				</tr>
				<tr>
					
					<td class="list_range"><bean:message bundle="projectpre"  key="projectCode.ProjectCodeSetting.ProjectCodeLength"/></td>
					<td class="list_range" colspan="3">
						<html:text styleId="input_field" fieldtype="text" name="length" beanName="webVo" req="true" maxlength="2" />
						
					 (<bean:message bundle="projectpre"  key="projectCode.ProjectCodeSetting.ExcludeCityTrailCode"/>)</td>
					<td class="list_range" >&nbsp;</td>
				</tr>
				<tr>
				
				</tr>
				<tr>
					
					<td class="list_range"><bean:message bundle="projectpre" key="projectCode.ProjectCodeSetting.ProjectCodeCodingMethod" /></td>
					<td class="list_range">
						  <html:radiobutton styleId=""  name="codingMethod"  beanName="webVo" value="YearMonthSerialNumber"  next="currentSeq" prev="length" />
						<bean:message bundle="projectpre"  key="projectCode.ProjectCodeSetting.YearMonthSerialNumber"/>
					</td>
					<td class="list_range" colspan="2">
						 <html:radiobutton styleId=""  name="codingMethod"  beanName="webVo" value="SerialNumber"  next="currentSeq" prev="length"/>
						<bean:message bundle="projectpre"  key="projectCode.ProjectCodeSetting.SerialNumber"/>
					</td>

					<td class="list_range">&nbsp;</td>
				</tr>
				<tr>
					
					<td class="list_range"><bean:message bundle="projectpre"  key="projectCode.ProjectCodeSetting.CurrentUsingNumberBySystem"/></td>
					<td class="list_range" colspan="3">
						 <html:text styleId="input_text" fieldtype="text"  name="currentSeq" beanName="webVo"  req="true"  maxlength="8"/> 
					</td>
					<td class="list_range">&nbsp;</td>
				</tr>
				
			</table>
		</html:form>
	</body>
</html>
