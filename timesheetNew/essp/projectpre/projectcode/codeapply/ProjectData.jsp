
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ include file="/layout/dynamicLoginId.jsp" %>
<bean:define id="appType" value='<%=request.getParameter("appType")%>'/>
<%
String path = request.getContextPath();
%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value=" " />
		<tiles:put name="jspName" value="" />
	 </tiles:insert>
    <title>Project Data</title>
    <style type="text/css">
      #input_common1{width:130}
      #input_common2{width:150}
      #input_field{width:60}
      #description{width:100%}
    </style>
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/humanAllocate.js"></script>
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/queryCustomer.js"></script>
      <script language="JavaScript" type="text/JavaScript">
      function getQueryUserElementArray() {
		var names = new Array(document.getElementById("custServiceManager"),
		                      document.getElementById("engageManager"));
		var loginIds = new Array(document.getElementById("custServiceManagerId"),
								 document.getElementById("engageManagerId"));
		var domains = new Array(document.getElementById("custServiceManagerDomain"),
								document.getElementById("engageManagerDomain"));
		return new Array(names, loginIds, domains);
	}
  function onLoadBody(){
   var obj;
    try{
        obj = parent.applyRecordList.getCurrentRow();
    } catch(e){
      try{
        obj = parent.changeAppList.getCurrentRow();
        }catch(e){
         return;
        }
    }
    //alert(obj.cells[5].innerText);
    //alert(obj.cells.length);
    if(obj==null){return;}
    if(obj.cells[obj.cells.length-1].innerText=="Submitted"){
          disableCustomer();
    }
  
  }
      
      
       //设置客户资料卡片控件为不可用
  function disableCustomer(){
   try{
   var type = "<%=appType%>";
  var obj = document.forms[0];
     obj.elements["contract"].disabled=true; 
     obj.elements["contractTel"].disabled=true;
     obj.elements["contractEmail"].disabled=true;
     obj.elements["executive"].disabled=true;
     obj.elements["executiveTel"].disabled=true;
     obj.elements["executiveEmail"].disabled=true;
     obj.elements["financial"].disabled=true;
     obj.elements["financialTel"].disabled=true;
     obj.elements["financialEmail"].disabled=true;
     
     obj.elements["custServiceManager"].disabled=true;
     obj.elements["engageManager"].disabled=true;
     obj.elements["custServiceManagerId"].disabled=true;
     obj.elements["engageManagerId"].disabled=true;
     if(type=="check"){
        obj.elements["customerId"].disabled=false;
     }else{
        obj.elements["customerId"].disabled=true;
     }
  }catch(e){}
  }
      
 //查询客户控件功能
      
 function customerOnClick() {
    try{
     if(document.all.customerId.disabled){return;}
      var result = queryCustomer();
      if(result!=null) {	
      document.all.customerId.value=result.id+"---"+result.short_name;	
      }
 	}catch(e){}			

}
//找人功能

function allocate(personType){
  try{
    if(document.all.custServiceManager.disabled||document.all.engageManager.disbaled){return;}
    param = new HashMap();
    var result = allocSingleInAD(param,"","","")
    if(param!=""&&personType=="CSM"){
       document.all.custServiceManager.value=param.values()[0].name;
       document.forms[0].custServiceManagerId.value=param.values()[0].loginId;
       document.forms[0].custServiceManagerDomain.value=param.values()[0].domain;
    }
    if(param!=""&&personType=="EM"){
       document.all.engageManager.value=param.values()[0].name;
       document.forms[0].engageManagerId.value=param.values()[0].loginId;
       document.forms[0].engageManagerDomain.value=param.values()[0].domain;
    }
    }catch(e){}
    
    }
  
  </script>
  </head>
  
  <body >
    
    <html:form action="" method="post">
    <html:hidden name="custServiceManagerId" beanName="webVo"/>
    <html:hidden name="custServiceManagerDomain" beanName="webVo"/>
    <html:hidden name="engageManagerId" beanName="webVo"/>
    <html:hidden name="engageManagerDomain" beanName="webVo"/>
<table width="100%" border="0">
  <tr> 
    <td class="list_range" width="180">
    <bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ClientInformation"/>
    <!--bean:message bundle="projectpre" key="projectCode.MasterData.CustomerNo"/--></td>
    <td class="list_range" width="60">
    <%if(!"check".equals(appType)){
    %>
    <html:text name="customerId"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common2"
                      prev="dueDate"
                      req="true"
         />
         <%}else{%>
         <html:text name="customerId"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common2"
                      prev="dueDate"
                      req="true"
		              readonly="true"
                      imageSrc="<%=contextPath+"/image/qurey.gif"%>"
                      imageWidth="18"
                      imageOnclick="customerOnClick();"
                      
         />
         <%}%>
         </td>
        
    <td class="list_range" width="190"><bean:message bundle="projectpre" key="projectCode.MasterData.CustomerServiceManager"/></td>
    <td class="list_range" width="60"> 
    <html:text name="custServiceManager"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common2"
                      prev="dueDate"
                      imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>"
                      imageWidth="18"
                      imageOnclick="allocate('CSM')"
                   
         />
         </td>
        
    <td class="list_range" width="160"><bean:message bundle="projectpre" key="projectCode.MasterData.EngageManager"/></td>
    <td class="list_range" width="60">
    <html:text name="engageManager"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common2"
                      prev="dueDate"
                      imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>"
                      imageWidth="18"
                      imageOnclick="allocate('EM')"
                  
         />
         </td>
   
 

  </tr>

  <tr>
    <td class="list_range" width="112"><bean:message bundle="projectpre" key="projectCode.MasterData.CooperateContact"/> </td>
    <td class="list_range" width="120">
      <html:text name="contract" styleId="input_common2" beanName="webVo" fieldtype="text"  maxlength="25"/>
    </td>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.MasterData.Tel"/> </td>
    <td class="list_range" width="120">
      <html:text name="contractTel" styleId="input_common2" beanName="webVo" fieldtype="text"  maxlength="25"/>
    </td>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.MasterData.Email"/> </td>
    <td class="list_range">
       <html:text name="contractEmail" styleId="input_common2" beanName="webVo" fieldtype="text"  maxlength="25"/>
    </td>
 
   
  </tr>
  <tr>
    <td class="list_range" width="112"><bean:message bundle="projectpre" key="projectCode.MasterData.PerformContact"/> </td>
    <td class="list_range">
      <html:text name="executive" styleId="input_common2" beanName="webVo" fieldtype="text"  maxlength="25"/>
    </td>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.MasterData.Tel"/> </td>
    <td class="list_range">
       <html:text name="executiveTel" styleId="input_common2" beanName="webVo" fieldtype="text"  maxlength="25"/>
    </td>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.MasterData.Email"/> </td>
    <td class="list_range">
      <html:text name="executiveEmail" styleId="input_common2" beanName="webVo" fieldtype="text"  maxlength="25"/>
    </td>

    
    
  </tr>
  <tr>
    <td class="list_range" width="112"><bean:message bundle="projectpre" key="projectCode.MasterData.FinanceContact"/> </td>
    <td class="list_range">
       <html:text name="financial" styleId="input_common2" beanName="webVo" fieldtype="text"  maxlength="25"/>
    </td>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.MasterData.Tel"/> </td>
    <td class="list_range">
      <html:text name="financialTel" styleId="input_common2" beanName="webVo" fieldtype="text"  maxlength="25"/>
    </td>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.MasterData.Email"/> </td>
    <td class="list_range">
       <html:text name="financialEmail" styleId="input_common2" beanName="webVo" fieldtype="text"  maxlength="25"/>
    </td>

  
 
  </tr>
 
</table>
</html:form>
   <SCRIPT language="JavaScript" type="text/JavaScript">
    onLoadBody();
   </SCRIPT>
  </body>
</html>
