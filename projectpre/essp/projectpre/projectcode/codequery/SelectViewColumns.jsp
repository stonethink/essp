
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%
String contextPath = request.getContextPath();
%>
<script language="JavaScript" type="text/JavaScript">
     function onClickSubmit(){ 
      // var masterData = document.forms[0].elements["testCheckBox"];
       //var masterData = document.getElementsByName("CheckBox");
       //alert(masterData[0].checked );
       //alert(masterData[1].checked);
       //if(masterData[0].checked == true){
          //alert("ok");
          //alert(window.opener.document.AfProjectQuery.showApplicant.value);
         //window.opener.document.AfProjectQuery.showApplicant.value="checked";
      // }else{
        //  window.opener.document.AfProjectQuery.showApplicant.value="";
       //}
       //if(masterData[1].checked == true){
         // window.opener.document.AfProjectQuery.showAchieveBelong.value="checked";
      // }else{
         // window.opener.document.AfProjectQuery.showAchieveBelong.value="";
       //}
       //this.close();
      var ViewCloumnForm = document.getElementById("ViewCloumnForm");
       ViewCloumnForm.action = "<%=contextPath%>/project/query/searchCondition.do";
       //alert(window.opener.searchFrm);
       //var parentWindow = window.opener;
       //var searchFrm = parentWindow.document.getElementById("searchFrm");
       //ViewCloumnForm.target = searchFrm;
//       alert(window.opener.searchFrm);
      // ViewCloumnForm.target = "_opener";
       ViewCloumnForm.submit();
       //this.close();
     }

     function onClickCancel(){
       this.close();
     }
     function onShowMainData(){
     
     try{
       var masterData = document.getElementById("masterData");
       if(masterData.checked){
          document.forms[0].showMainData.value='checked';
          document.getElementsByName("showApplicant")[0].checked=true;
          document.getElementsByName("showApplicant")[0].value='checked';
          document.getElementsByName("showAchieveBelong")[0].checked=true;
          document.getElementsByName("showAchieveBelong")[0].value='checked';
          document.getElementsByName("showLeader")[0].checked=true;
          document.getElementsByName("showLeader")[0].value='checked';
          document.getElementsByName("showDivisionManager")[0].checked=true;
          document.getElementsByName("showDivisionManager")[0].value='checked';
          document.getElementsByName("showNickName")[0].checked=true;
          document.getElementsByName("showNickName")[0].value='checked';
          document.getElementsByName("showProjectManager")[0].checked=true;
          document.getElementsByName("showProjectManager")[0].value='checked';
          document.getElementsByName("showTCSigner")[0].checked=true;
          document.getElementsByName("showTCSigner")[0].value='checked';
          document.getElementsByName("showProjName")[0].checked=true;
          document.getElementsByName("showProjName")[0].value='checked';
          document.getElementsByName("showProductName")[0].checked=true;
          document.getElementsByName("showProductName")[0].value='checked';
          document.getElementsByName("showProjectDesc")[0].checked=true;
          document.getElementsByName("showProjectDesc")[0].value='checked';
          document.getElementsByName("showProjectExecUnit")[0].checked=true;
          document.getElementsByName("showProjectExecUnit")[0].value='checked';
          document.getElementsByName("showProjectExecuteSite")[0].checked=true;
          document.getElementsByName("showProjectExecuteSite")[0].value='checked';
          document.getElementsByName("showCostBelong")[0].checked=true;
          document.getElementsByName("showCostBelong")[0].value='checked';
          document.getElementsByName("showSales")[0].checked=true;
          document.getElementsByName("showSales")[0].value='checked';
          document.getElementsByName("showCustomerServiceManager")[0].checked=true;
          document.getElementsByName("showCustomerServiceManager")[0].value='checked';
          document.getElementsByName("showEngageManager")[0].checked=true;
          document.getElementsByName("showEngageManager")[0].value='checked';
          document.getElementsByName("showBizSource")[0].checked=true;
          document.getElementsByName("showBizSource")[0].value='checked';
          document.getElementsByName("showParentProject")[0].checked=true;
          document.getElementsByName("showParentProject")[0].value='checked';
          document.getElementsByName("showContractAcnt")[0].checked=true;
          document.getElementsByName("showContractAcnt")[0].value='checked';
          document.getElementsByName("showCustomerInfo")[0].checked=true;
          document.getElementsByName("showCustomerInfo")[0].value='checked';
          document.getElementsByName("showAcntId")[0].checked=true;
          document.getElementsByName("showAcntId")[0].value='checked';
          document.getElementsByName("showPrimaveraAdapted")[0].checked=true;
          document.getElementsByName("showPrimaveraAdapted")[0].value='checked';
          document.getElementsByName("showBillingBasis")[0].checked=true;
          document.getElementsByName("showBillingBasis")[0].value='checked';
          document.getElementsByName("showRelPrjType")[0].checked=true;
          document.getElementsByName("showRelPrjType")[0].value='checked';
          
       }else{
          document.forms[0].showMainData.value='';
          document.getElementsByName("showApplicant")[0].checked=false;
          document.getElementsByName("showAchieveBelong")[0].checked=false;
          document.getElementsByName("showLeader")[0].checked=false;
          document.getElementsByName("showDivisionManager")[0].checked=false;
          document.getElementsByName("showNickName")[0].checked=false;
          document.getElementsByName("showProjectManager")[0].checked=false;
          document.getElementsByName("showTCSigner")[0].checked=false;
          document.getElementsByName("showProjName")[0].checked=false;
          document.getElementsByName("showProductName")[0].checked=false;
          document.getElementsByName("showProjectDesc")[0].checked=false;
          document.getElementsByName("showProjectExecUnit")[0].checked=false;
          document.getElementsByName("showProjectExecuteSite")[0].checked=false;
          document.getElementsByName("showCostBelong")[0].checked=false;
          document.getElementsByName("showSales")[0].checked=false;
          document.getElementsByName("showCustomerServiceManager")[0].checked=false;
          document.getElementsByName("showEngageManager")[0].checked=false;
          document.getElementsByName("showBizSource")[0].checked=false;
          document.getElementsByName("showParentProject")[0].checked=false;
          document.getElementsByName("showContractAcnt")[0].checked=false;
          document.getElementsByName("showCustomerInfo")[0].checked=false;
          document.getElementsByName("showAcntId")[0].checked=false;
          document.getElementsByName("showPrimaveraAdapted")[0].checked=false;
          document.getElementsByName("showBillingBasis")[0].checked=false;
          document.getElementsByName("showRelPrjType")[0].checked=false;
        
          
          
       }

       //masterData.blur();
     
       }catch(e){}
     }
     function onShowTechData(){
     try{
     var techData = document.getElementById("techData");
     if(techData.checked){
          document.forms[0].showTechData.value='checked';
          document.getElementsByName("showProjectType")[0].checked=true;
          document.getElementsByName("showProjectType")[0].value='checked';
          document.getElementsByName("showProductType")[0].checked=true;
          document.getElementsByName("showProductType")[0].value='checked';
          document.getElementsByName("showProductAttribute")[0].checked=true;
          document.getElementsByName("showProductAttribute")[0].value='checked';
          document.getElementsByName("showWorkItem")[0].checked=true;
          document.getElementsByName("showWorkItem")[0].value='checked';
          document.getElementsByName("showTechnicalDomain")[0].checked=true;
          document.getElementsByName("showTechnicalDomain")[0].value='checked';
          document.getElementsByName("showSupportLanguage")[0].checked=true;
          document.getElementsByName("showSupportLanguage")[0].value='checked';
          document.getElementsByName("showDevelopEnvironment")[0].checked=true;
          document.getElementsByName("showDevelopEnvironment")[0].value='checked';
          document.getElementsByName("showTranslatePublishType")[0].checked=true;
          document.getElementsByName("showTranslatePublishType")[0].value='checked';
          document.getElementsByName("showHardReq")[0].checked=true;
          document.getElementsByName("showHardReq")[0].value='checked';
          document.getElementsByName("showSoftReq")[0].checked=true;
          document.getElementsByName("showSoftReq")[0].value='checked'; 
     }else{
          document.forms[0].showTechData.value='';
          document.getElementsByName("showProjectType")[0].checked=false;
          document.getElementsByName("showProductType")[0].checked=false;
          document.getElementsByName("showProductAttribute")[0].checked=false;
          document.getElementsByName("showWorkItem")[0].checked=false;
          document.getElementsByName("showTechnicalDomain")[0].checked=false;
          document.getElementsByName("showSupportLanguage")[0].checked=false;
          document.getElementsByName("showDevelopEnvironment")[0].checked=false;
          document.getElementsByName("showTranslatePublishType")[0].checked=false;
          document.getElementsByName("showHardReq")[0].checked=false;
          document.getElementsByName("showSoftReq")[0].checked=false;
     
     }
        
     }catch(e){}
     }
     function onShowProjectData(){
      try{
        var techData = document.getElementById("projectData");
          
        if(techData.checked){
          document.forms[0].showProjectData.value='checked';
          document.getElementsByName("showProjectScheduleAnticipate")[0].checked=true;
          document.getElementsByName("showProjectScheduleAnticipate")[0].value='checked';
          document.getElementsByName("showProjectScheduleFact")[0].checked=true;
          document.getElementsByName("showProjectScheduleFact")[0].value='checked';
          document.getElementsByName("showAnticipateWorkQuantity")[0].checked=true;
          document.getElementsByName("showAnticipateWorkQuantity")[0].value='checked';
          document.getElementsByName("showOthers")[0].checked=true;
          document.getElementsByName("showOthers")[0].value='checked';
          document.getElementsByName("showManMonth")[0].checked=true;
          document.getElementsByName("showManMonth")[0].value='checked';
        } else {
          document.forms[0].showProjectData.value='';
          document.getElementsByName("showProjectScheduleAnticipate")[0].checked=false;
          document.getElementsByName("showProjectScheduleFact")[0].checked=false;
          document.getElementsByName("showAnticipateWorkQuantity")[0].checked=false;
          document.getElementsByName("showOthers")[0].checked=false;
  		  document.getElementsByName("showManMonth")[0].checked=false;
        }
      }catch(e){}
     }
</script>
<html>
  <head>
    
    <tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value=" " />
		<tiles:put name="jspName" value="" />
	 </tiles:insert>
    <title>Technical Data</title>
    <style type="text/css">
      #input_common1{width:50}
      #input_common2{width:80}
      #input_field{width:60}
      #description{width:100%}
    </style>
   
  </head>
  
  <body>
   <html:form id="ViewCloumnForm" action="" method="post" >
   <html:hidden name="showMainData" beanName="SearchCondition"/>
   <html:hidden name="showTechData" beanName="SearchCondition"/>
   <html:hidden name="showProjectData" beanName="SearchCondition"/>
   <table width="860" border="0">
  <tr>
    <td class="list_range"   width="140">
    <%--
    <html:checkbox styleId="CheckBox" name="masterData" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle" onchange="aaa();"/>
    --%>
    <input type="checkbox" id="masterData" name="masterData" onclick="onShowMainData()" <bean:write name="SearchCondition" property="showMainData"/>/>
     <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.MasterData"/></td>
    <td class="list_range"   >
      <html:checkbox styleId="CheckBox" name="showApplicant" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.Applicant"/></td>
    <td class="list_range"   >
      <html:checkbox styleId="CheckBox" name="showAcntId" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectCode"/>
      </td>
    <td class="list_range"   >
     <html:checkbox styleId="CheckBox" name="showAchieveBelong" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.AcheiveBelong"/>
    
    
      </td>
    <td class="list_range"   width="200">
   <html:checkbox styleId="CheckBox" name="showProjectManager" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.ProjectManager"/>
      </td>
    
  </tr>
  <tr> 
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showNickName" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.MasterData.NiceName"/></td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showProjName" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ProjectFullName"/>
      </td>
    <td class="list_range"  >
	<html:checkbox styleId="CheckBox" name="showTCSigner" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.TimeCardSigner"/></td>
    <td class="list_range"  >
    
	<html:checkbox styleId="CheckBox" name="showDivisionManager" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.MasterData.DivisionManager"/>
      </td>
   
    
  </tr>
  <tr> 
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showProductName" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ProductName"/></td>
    <td class="list_range" width="220" >
    <html:checkbox styleId="CheckBox" name="showProjectDesc" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ProjectContentShortName"/></td>
    <td class="list_range" width="200" >
	<html:checkbox styleId="CheckBox" name="showProjectExecUnit" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectExecuteUnit"/></td>
    <td class="list_range"  >
	<html:checkbox styleId="CheckBox" name="showProjectExecuteSite" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectExecuteFocus"/></td>
    
  </tr>
  <tr> 
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showCostBelong" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.CostBelongUnit"/></td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showSales" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.BusinessDeputy"/></td>
    <td class="list_range"  >
	<html:checkbox styleId="CheckBox" name="showCustomerServiceManager" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ServeMajordomo"/></td>
    <td class="list_range"  >
	<html:checkbox styleId="CheckBox" name="showEngageManager" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.SaleSupportManager"/></td>

  </tr>
  <tr> 
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showBizSource" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.BusinessResource"/></td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showParentProject" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.MainProject"/></td>
    <td class="list_range"  >
	<html:checkbox styleId="CheckBox" name="showContractAcnt" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ProjectBelong"/></td>
    <td class="list_range"  >
	<html:checkbox styleId="CheckBox" name="showCustomerInfo" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ClientInformation"/></td>
   
  </tr>
  <tr>
  <td class="list_range"  >&nbsp;</td>
  <td class="list_range"  >
  <html:checkbox styleId="CheckBox" name="showLeader" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.MasterData.Leader"/>
      </td>
      <td class="list_range"  >
  <html:checkbox styleId="CheckBox" name="showPrimaveraAdapted" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.MasterData.PrimaveraAdapted"/>
      </td>
      <td class="list_range"  >
  <html:checkbox styleId="CheckBox" name="showBillingBasis" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.MasterData.BillingBasis"/>
      </td>
       <td class="list_range"  >
  <html:checkbox styleId="CheckBox" name="showRelPrjType" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectType"/>
      </td>
  </tr>
  <tr> 
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
   
    <td class="list_range" >&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"  >
    <input type="checkbox" id="techData" name="techData" onclick="onShowTechData()" <bean:write name="SearchCondition" property="showTechData"/>/>
     <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.TechnicalData"/></td>
    <td class="list_range"   >
    <html:checkbox styleId="CheckBox" name="showProjectType" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectType"/></td>
    <td class="list_range"   >
    <html:checkbox styleId="CheckBox" name="showProductType" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.ProductType"/></td>
    <td class="list_range"   >
    <html:checkbox styleId="CheckBox" name="showProductAttribute" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.ProductAttribute"/></td>
    <td class="list_range"  >
   <html:checkbox styleId="CheckBox" name="showWorkItem" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.WorkItem"/></td>
   
  </tr>
  <tr> 
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showTechnicalDomain" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
       <bean:message bundle="projectpre" key="projectCode.QueryProject.TechnicalArea"/></td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showSupportLanguage" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.Technical.SupportLanguage"/></td>
    <td class="list_range"  >
	<html:checkbox styleId="CheckBox" name="showDevelopEnvironment" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.Technical.DevelopEnvironment"/></td>
    <td class="list_range"  >
	<html:checkbox styleId="CheckBox" name="showTranslatePublishType" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.Technical.TranslatePublishType"/></td>
   
  </tr>
  <tr> 
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showHardReq" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.Technical.HardRequirement"/></td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showSoftReq" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.Technical.SoftRequirement"/></td>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range" >&nbsp;</td>
  
  </tr>
  
  <tr> 
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
   
  </tr>
  <tr>
    <td class="list_range"   >
    <input type="checkbox" id="projectData" name="projectData" onclick="onShowProjectData()" <bean:write name="SearchCondition" property="showProjectData"/>/>
     <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ProjectData"/></td>
    <td class="list_range"   width="230">
    <html:checkbox styleId="CheckBox" name="showProjectScheduleAnticipate" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ProjectScheduleAnticipate"/></td>
    <td class="list_range"   >
    <html:checkbox styleId="CheckBox" name="showProjectScheduleFact" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
     <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ProjectScheduleFact"/></td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showAnticipateWorkQuantity" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.AnticipateWorkQuantity"/></td>
    <td class="list_range"   >
   <html:checkbox styleId="CheckBox" name="showOthers" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.ProjectData.Others"/></td>
  </tr>
  <tr>
  <td class="list_range"  >&nbsp;</td>
  <td class="list_range" >
  <html:checkbox styleId="CheckBox" name="showManMonth" beanName="SearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.AnticipateManMonth"/>
  </td>
  <td class="list_range"  >&nbsp;</td>
  <td class="list_range"  >&nbsp;</td>
  <td class="list_range"  >&nbsp;</td>
  </tr>
</table>
<table border="0" width="860">
      <tr><td></td></tr>
      <tr></tr>
      <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
      <tr>
     <td align="right">
			<html:button styleId="btnOk" name="btnSubmit" onclick="onClickSubmit()">
			<bean:message bundle="application" key="global.button.confirm" />
			</html:button>
			<html:button styleId="btnCancel" name="btnCancel" onclick="onClickCancel()">
			<bean:message bundle="application" key="global.button.cancel" />
			</html:button>
			</td>
			<td class="list_range"  width="20">&nbsp;</td>
			</tr>
    	</table> 
   </html:form>
   <logic:notPresent name="SearchCondition" scope="session" >
   <script language="JavaScript" type="text/JavaScript">
   document.getElementById("masterData").checked = true;
   document.getElementById("techData").checked = true;
   document.getElementById("projectData").checked = true;
   onShowMainData();
   onShowTechData();
   onShowProjectData();
</script>
</logic:notPresent>
  </body>
</html>

