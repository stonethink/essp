
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%
String contextPath = request.getContextPath();
%>
<script language="JavaScript" type="text/JavaScript">
     function onClickSubmit(){ 
       hrbViewCloumnForm.action = "<%=contextPath%>/hrbase/humanbase/searchCondition.do";
       hrbViewCloumnForm.submit();
     }

     function onClickCancel(){
       this.close();
     }
     function onShowMainData(){
     
     try{
       var masterData = document.getElementById("masterData");
       if(masterData.checked){
          document.forms[0].showMainData.value='checked';
          document.getElementsByName("showEmployeeId")[0].checked=true;
          document.getElementsByName("showEmployeeId")[0].value='checked';
          document.getElementsByName("showEnglishName")[0].checked=true;
          document.getElementsByName("showEnglishName")[0].value='checked';
          document.getElementsByName("showChineseName")[0].checked=true;
          document.getElementsByName("showChineseName")[0].value='checked';
          document.getElementsByName("showTitle")[0].checked=true;
          document.getElementsByName("showTitle")[0].value='checked';
          document.getElementsByName("showRank")[0].checked=true;
          document.getElementsByName("showRank")[0].value='checked';
          document.getElementsByName("showResManagerId")[0].checked=true;
          document.getElementsByName("showResManagerId")[0].value='checked';
          document.getElementsByName("showResManagerName")[0].checked=true;
          document.getElementsByName("showResManagerName")[0].value='checked';
          document.getElementsByName("showSite")[0].checked=true;
          document.getElementsByName("showSite")[0].value='checked';
          document.getElementsByName("showUnitCode")[0].checked=true;
          document.getElementsByName("showUnitCode")[0].value='checked';
          document.getElementsByName("showIsDirect")[0].checked=true;
          document.getElementsByName("showIsDirect")[0].value='checked';
          document.getElementsByName("showInDate")[0].checked=true;
          document.getElementsByName("showInDate")[0].value='checked';
          document.getElementsByName("showOutDate")[0].checked=true;
          document.getElementsByName("showOutDate")[0].value='checked';
          document.getElementsByName("showEffectiveDate")[0].checked=true;
          document.getElementsByName("showEffectiveDate")[0].value='checked';
          document.getElementsByName("showHrAttribute")[0].checked=true;
          document.getElementsByName("showHrAttribute")[0].value='checked';
          document.getElementsByName("showAttribute")[0].checked=true;
          document.getElementsByName("showAttribute")[0].value='checked';
          document.getElementsByName("showIsFormal")[0].checked=true;
          document.getElementsByName("showIsFormal")[0].value='checked';
          document.getElementsByName("showEmail")[0].checked=true;
          document.getElementsByName("showEmail")[0].value='checked';
          document.getElementsByName("showOnJob")[0].checked=true;
          document.getElementsByName("showOnJob")[0].value='checked';
       }else{
          document.forms[0].showMainData.value='';
          document.getElementsByName("showEmployeeId")[0].checked=false;
          document.getElementsByName("showEnglishName")[0].checked=false;
          document.getElementsByName("showChineseName")[0].checked=false;
          document.getElementsByName("showTitle")[0].checked=false;
          document.getElementsByName("showRank")[0].checked=false;
          document.getElementsByName("showResManagerId")[0].checked=false;
          document.getElementsByName("showResManagerName")[0].checked=false;
          document.getElementsByName("showSite")[0].checked=false;
          document.getElementsByName("showUnitCode")[0].checked=false;
          document.getElementsByName("showIsDirect")[0].checked=false;
          document.getElementsByName("showInDate")[0].checked=false;
          document.getElementsByName("showOutDate")[0].checked=false;
          document.getElementsByName("showEffectiveDate")[0].checked=false;
          document.getElementsByName("showHrAttribute")[0].checked=false;
          document.getElementsByName("showAttribute")[0].checked=false;
          document.getElementsByName("showIsFormal")[0].checked=false;
          document.getElementsByName("showEmail")[0].checked=false;
          document.getElementsByName("showOnJob")[0].checked=false;

       }
       //masterData.blur();
       }catch(e){}
     }
  
</script>
<html>
  <head>
    
    <tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value=" " />
		<tiles:put name="jspName" value="" />
	 </tiles:insert>
    <style type="text/css">
      #input_common1{width:50}
      #input_common2{width:80}
      #input_field{width:60}
      #description{width:100%}
    </style>
   
  </head>
  
  <body>
   <html:form id="hrbViewCloumnForm" action="" method="post" >
   <html:hidden name="showMainData" beanName="HrSearchCondition"/>
   <table width="860" border="0">
  <tr>
    <td class="list_range"   width="140">
    <%--
    <html:checkbox styleId="CheckBox" name="masterData" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle" onchange="aaa();"/>
    --%>
    <input type="checkbox" id="masterData" name="masterData" onclick="onShowMainData()" <bean:write name="HrSearchCondition" property="showMainData"/>/>
     <bean:message bundle="hrbase" key="hrbase.humanBase.all" /></td>
    <td class="list_range"   >
      <html:checkbox styleId="CheckBox" name="showEmployeeId" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.employee" /></td>
    <td class="list_range"   >
      <html:checkbox styleId="CheckBox" name="showEnglishName" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.englishName"/>
      </td>
    <td class="list_range"   >
     <html:checkbox styleId="CheckBox" name="showChineseName" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.chineseName"/>
      </td>
    <td class="list_range"   width="200">
   <html:checkbox styleId="CheckBox" name="showTitle" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.title"/>
      </td>
    
  </tr>
  <tr> 
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"   width="200">
   <html:checkbox styleId="CheckBox" name="showRank" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.rank"/>
      </td>
      <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showResManagerId" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.resManagerId"/></td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showResManagerName" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.resManager"/></td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showSite" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.site"/>
      </td>
    
  </tr>
  <tr> 
    <td class="list_range"  >&nbsp;</td>
     <td class="list_range"  >
	<html:checkbox styleId="CheckBox" name="showUnitCode" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.unitCode"/></td>
    <td class="list_range"  >
    
	<html:checkbox styleId="CheckBox" name="showIsDirect" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.direct"/>
      </td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showInDate" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
     <bean:message bundle="hrbase" key="hrbase.humanBase.inDate"/></td>
    <td class="list_range" width="220" >
    <html:checkbox styleId="CheckBox" name="showOutDate" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.outDate"/></td>
    
  </tr>
  <tr> 
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showEffectiveDate" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.effectiveDate"/></td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showHrAttribute" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.hrAttribute"/></td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showAttribute" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.attribute"/></td>
    <td class="list_range"  >
    <html:checkbox styleId="CheckBox" name="showIsFormal" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.formal"/></td>
  </tr>
  <tr>
  <td class="list_range"  >&nbsp;</td>
  	 <td class="list_range"  >
	<html:checkbox styleId="CheckBox" name="showEmail" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.email"/></td>
      <td class="list_range"  >
	<html:checkbox styleId="CheckBox" name="showOnJob" beanName="HrSearchCondition" checkedValue="checked" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="hrbase" key="hrbase.humanBase.isOnJob"/></td>
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
   <logic:notPresent name="HrSearchCondition" scope="session" >
   <script language="JavaScript" type="text/JavaScript">
   document.getElementById("masterData").checked = true;
   onShowMainData();
</script>
</logic:notPresent>
  </body>
</html>

