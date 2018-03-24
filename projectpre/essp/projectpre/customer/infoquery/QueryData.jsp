<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <tiles:insert page="/layout/head.jsp">
       <tiles:put name="title" value=" "/>
       <tiles:put name="jspName" value=""/>
     </tiles:insert>
      <style type="text/css">
      #input_common1{width:120}
      #input_common2{width:597}
      #input_common3{width:160}
      #input_common4{width:273}
      #input_field{width:60}
      #description{width:600}
    </style>
     <script type="text/javascript" language="javascript">
     function onSearch(){
       AfQueryData.submit();

      //var downFrame=window.parent.queryList;
      //downFrame.location="/essp/customer/query/customerQueryList.do"
     }
     
     function checkAttribute(obj){
      try{
       var objGroup = document.getElementsByName("attribute")[0];
       var objCompany = document.getElementsByName("attribute")[1];
       var objAddSub = document.getElementById("addSub");
       
       if (obj == "group") {
           if (objGroup.checked) {
              objCompany.checked = false;
              objAddSub.disabled = false;
           } 
       }
       if (obj == "company") {
           if (objCompany.checked) {
              objGroup.checked = false;
              objAddSub.checked = false;
              objAddSub.disabled = true;
           } else {
              objAddSub.disabled = false;
           }
       }
       }catch(e){} 
     }
     

     </script>
  </head>
  
  <body>
 
 <html:form id="AfQueryData" action="/customer/query/customerQueryList" method="post" target ="queryList">
<table width="800" border="0" style="padding-left:8px">
  <tr>
    <td class="list_range" width="150"><bean:message bundle="projectpre" key="customer.ClientNo"/>
    </td>
    <td class="list_range" >
    <html:text styleId="input_common1" name="customerId" beanName="" fieldtype="text" readonly="false"  maxlength="20"/>
	</td>
	    <td class="list_range" width="150"><bean:message bundle="projectpre" key="customer.ClientShortName"/>
    </td>
    <td class="list_range" >
    <html:text styleId="input_common1" name="short_" beanName="" fieldtype="text" readonly="false"  maxlength="20"/>
	</td>
	
    <td class="list_range" ><input type="checkbox" name="attribute" value="Group" onclick="checkAttribute('group')"> <bean:message bundle="projectpre" key="customer.CustomerAttributeGroup"/></td>
    <td class="list_range"><input type="checkbox" name="attribute" value="Company" onclick="checkAttribute('company')"> <bean:message bundle="projectpre" key="customer.CustomerAttributeCompany"/></td>
  </tr>
  <tr>
    <td class="list_range" colspan="3">
    <input type="checkbox" name="addSub" value="ture"> 
    <bean:message bundle="projectpre" key="customer.IncludeAllSubsidiary"/></td>
    <td class="list_range" colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"> <bean:message bundle="projectpre" key="customer.BD"/>
    </td>
    
    <td class="list_range">
	<html:select name="belongBd" styleId="input_common1" beanName="webVo" >
	<logic:notPresent name="webVo" property="bdList">
	<option value="" >--please select--</option>
	</logic:notPresent>
	<logic:present name="webVo" property="bdList">
	<html:optionsCollection name="webVo" property="bdList"></html:optionsCollection>
	</logic:present>
	</html:select></td>
    <td class="list_range" width="150"> <bean:message bundle="projectpre" key="customer.Site"/>
    </td>
    
   <td class="list_range">
	<html:select name="belongSite" styleId="input_common1" beanName="webVo">
	<logic:notPresent name="webVo" property="siteList">
	<option value="" >--please select--</option>
	</logic:notPresent>
	<logic:present name="webVo" property="siteList">
	<html:optionsCollection name="webVo" property="siteList"></html:optionsCollection>
	</logic:present>
	</html:select></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientClassCode"/>
    </td>
    
    <td class="list_range">
	<html:select name="class_" styleId="input_common1" beanName="webVo" >
	<logic:notPresent name="webVo" property="parameterClassList">
	<option value="" >--please select--</option>
	</logic:notPresent>
	<logic:present name="webVo" property="parameterClassList">
	<html:optionsCollection name="webVo" property="parameterClassList"></html:optionsCollection>
	</logic:present>
	</html:select></td>
    <td class="list_range" width="150"><bean:message bundle="projectpre" key="customer.ClientCountryCode"/>
    </td>
    
    <td class="list_range">
	<html:select name="country" styleId="input_common1" beanName="webVo" >
	<logic:notPresent name="webVo" property="parameterCountryList">
	<option value="" >--please select--</option>
	</logic:notPresent>
	<logic:present name="webVo" property="parameterCountryList">
    <html:optionsCollection name="webVo" property="parameterCountryList"></html:optionsCollection>
    </logic:present>
	</html:select></td>
    <td class="list_range">&nbsp;</td>
  </tr>
</table>
</html:form>
    </body>
</html>
