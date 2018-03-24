
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="server.essp.projectpre.service.constant.Constant" %>
<bean:define id="accessType" value='<%=request.getParameter("accessType")%>'/>
<%
String path = request.getContextPath();
%>

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
      #input_common3{width:120}
      #input_field{width:60}
      #description{width:100%}
    </style>
     <script language="JavaScript" type="text/JavaScript">
    
     
     //根据类型分别将技术资料中的多选框设置为不可用
  function disableCheckBox(type){
   try{
  var types = document.forms[0].elements[type];
  if(types==null){
        return;
      }
      if(!types.length){
  
        var obj = document.getElementById(type);
      
        obj.disabled=true;
        //alert(type);
        return;
      }
       for( i=0;i<types.length;i++){
          // alert(i);
          // alert(types[i].checked);
      	types[i].disabled=true;
     }
     }catch(e){}
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
          disableTech();
    }
    }
    
      //设置技术卡片资料控件为不可用
  function disableTech(){
    
    try{
  
  //alert("type:"+type);
  //alert(typeValue.cells[5].innerText);
  // var status = "";
  //if(type=="rowObj"){status=typeValue.cells[5].innerText;}
  //else if(type=="status"){status=typeValue;}
  //alert(status);
  // if(status=="Submitted"){
   //alert("enter");
   
   
     var obj = document.forms[0];
     //alert(obj);
    
    
     var productType = disableCheckBox("<%=Constant.PRODUCT_TYPE%>");
     var productAttribute = disableCheckBox("<%=Constant.PRODUCT_ATTRIBUTE%>"); 
     var workItem = disableCheckBox("<%=Constant.WORK_ITEM%>"); 
     var technicalDomain = disableCheckBox("<%=Constant.TECHNICAL_DOMAIN%>");
     var originalLanguage = disableCheckBox("<%=Constant.ORIGINAL_LANGUAGE%>");
     var translationLanguage = disableCheckBox("<%=Constant.TRANSLATION_LANGUANGE%>");
  
  	 obj.elements["projectType"].disabled=true;
     obj.elements["developJobSystem"].disabled=true;
     obj.elements["developDataBase"].disabled=true;
     obj.elements["developTool"].disabled=true;
     obj.elements["developNetWork"].disabled=true;
     obj.elements["developProgramLanguage"].disabled=true;
     obj.elements["developOthers"].disabled=true;
     obj.elements["typeJobSystem"].disabled=true;
     obj.elements["typeDataBase"].disabled=true;
     obj.elements["typeTool"].disabled=true;
     obj.elements["typeNetWork"].disabled=true;
     obj.elements["typeProgramLanguage"].disabled=true;
     obj.elements["typeOthers"].disabled=true;
     obj.elements["hardReq"].disabled=true;
     obj.elements["softReq"].disabled=true;
    // }
     
   }catch(e){}
  }
     
  //找人功能   
     
  function allocate(personType){
   try{
    var applicationStatus=document.getElementById("applicationStatus").value;
    if(applicationStatus=="Submitted"){return;}
    param = new HashMap();
    var result = allocSingleInAD(param,"","","")
    if(param!=""&&personType=="PM"){
       document.all.PMName.value=param.values()[0].name;
       document.forms[0].PMId.value=param.values()[0].loginId;
    }
    if(param!=""&&personType=="TCS"){
       document.all.TCSName.value=param.values()[0].name;
       document.forms[0].TCSId.value=param.values()[0].loginId;
    }
    }catch(e){}
  }
  
 
  </script>
  </head>
  
  <body >
  <html:form id="" action="" method="post">
   <table width="800" border="0">
     	<tr>	
     	<td class="list_range" >
		<bean:message bundle="projectpre" key="projectCode.Technical.ProjectType"/>
		</td>
		<td class="list_range">
			<html:select name="projectType"  styleId="input_common3" beanName="webVo"  req="true">
				<logic:present name="webVo" property="projectTypeList">
	               <html:optionsCollection name="webVo" property="projectTypeList"></html:optionsCollection>
	            </logic:present>
			</html:select>
			</td>
  </tr>
   	<tr>	
   <logic:iterate id="item"
                  name="webVo"
                  property="productTypeList"
                  scope="request"
                  indexId="a"
   >
   <%if(a.intValue() == 0){%>
   		<td class="list_range" ><bean:message bundle="projectpre" key="projectCode.Technical.ProductType"/></td>
   		<td class="list_range" >
   		<input name="<%=Constant.PRODUCT_TYPE%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   		<bean:write name="item" property="name"/></td>
   <%}else if(a.intValue()==1||(a.intValue()+1)%5 != 1){%>
   	   <td class="list_range" >
   	   <input name="<%=Constant.PRODUCT_TYPE%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   	  <bean:write name="item" property="name"/></td>
   <%}else {%>
   	</tr>
   	<tr>	
        <td class="list_range"></td>
   		 <td class="list_range" >
   		 <input name="<%=Constant.PRODUCT_TYPE%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   		 <bean:write name="item" property="name"/></td>
   <%}%>
   
  </logic:iterate>
  </tr>
  <tr>	
   <logic:iterate id="item"
                  name="webVo"
                  property="productAttribute"
                  scope="request"
                  indexId="a"
   >
   <%if(a.intValue() == 0){%>
   		<td class="list_range" ><bean:message bundle="projectpre" key="projectCode.Technical.ProductProperty"/></td>
   		<td class="list_range" >
   		<input name="<%=Constant.PRODUCT_ATTRIBUTE%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   		<bean:write name="item" property="name"/></td>
   <%}else if(a.intValue()==1||(a.intValue()+1)%5 != 1){%>
   	   <td class="list_range" >
   	   <input name="<%=Constant.PRODUCT_ATTRIBUTE%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   	   <bean:write name="item" property="name"/></td>
   <%}else {%>
   	</tr>
   	<tr>	
        <td class="list_range"></td>
   		 <td class="list_range" >
   		 <input name="<%=Constant.PRODUCT_ATTRIBUTE%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   		 <bean:write name="item" property="name"/></td>
   <%}%>
   
  </logic:iterate>
  </tr>
  <tr>	
   <logic:iterate id="item"
                  name="webVo"
                  property="workItem"
                  scope="request"
                  indexId="a"
   >
   <%if(a.intValue() == 0){%>
   		<td class="list_range" ><bean:message bundle="projectpre" key="projectCode.Technical.Project"/></td>
   		<td class="list_range" >
   		<input name="<%=Constant.WORK_ITEM%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   		<bean:write name="item" property="name"/></td>
   <%}else if(a.intValue()==1||(a.intValue()+1)%5 != 1){%>
   	   <td class="list_range" >
   	   <input name="<%=Constant.WORK_ITEM%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   	   <bean:write name="item" property="name"/></td>
   <%}else {%>
   	</tr>
   	<tr>	
        <td class="list_range"></td>
   		 <td class="list_range" >
   		 <input name="<%=Constant.WORK_ITEM%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   		 <bean:write name="item" property="name"/></td>
   <%}%>
   
  </logic:iterate>
  </tr>
  <tr>	
   <logic:iterate id="item"
                  name="webVo"
                  property="technicalDomain"
                  scope="request"
                  indexId="a"
   >
   <%if(a.intValue() == 0){%>
   		<td class="list_range" ><bean:message bundle="projectpre" key="projectCode.Technical.TechnicalArea"/></td>
   		<td class="list_range" >
   		<input name="<%=Constant.TECHNICAL_DOMAIN%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   		<bean:write name="item" property="name"/></td>
   <%}else if(a.intValue()==1||(a.intValue()+1)%5 != 1){%>
   	   <td class="list_range" >
   	   <input name="<%=Constant.TECHNICAL_DOMAIN%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   	   <bean:write name="item" property="name"/></td>
   <%}else {%>
   	</tr>
   	<tr>	
        <td class="list_range"></td>
   		 <td class="list_range" >
   		 <input name="<%=Constant.TECHNICAL_DOMAIN%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   		 <bean:write name="item" property="name"/></td>
   <%}%>
   
  </logic:iterate>
  </tr>
 
 <tr>	
   <logic:iterate id="item"
                  name="webVo"
                  property="originalLanguage"
                  scope="request"
                  indexId="a"
   >
   <%if(a.intValue() == 0){%>
   		<td class="list_range" ><bean:message bundle="projectpre" key="projectCode.Technical.SupportLanguage"/><br>
   		<bean:message bundle="projectpre" key="projectCode.Technical.Orignal"/><br>
   		</td>
   		<td class="list_range" >
   		<input name="OriginalLanguage" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   		<bean:write name="item" property="name"/></td>
   <%}else if(a.intValue()==1||(a.intValue()+1)%5 != 1){%>
   	   <td class="list_range" >
   	   <input name="OriginalLanguage" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   	   <bean:write name="item" property="name"/></td>
   <%}else {%>
   	</tr>
   	<tr>	
        <td class="list_range"></td>
   		 <td class="list_range" >
   		 <input name="OriginalLanguage" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   		<bean:write name="item" property="name"/></td>
   <%}%>
   
  </logic:iterate>
  </tr>
  <tr>	
   <logic:iterate id="item"
                  name="webVo"
                  property="translationLanguage"
                  scope="request"
                  indexId="a"
   >
   <%if(a.intValue() == 0){%>
   		<td class="list_range" ><bean:message bundle="projectpre" key="projectCode.Technical.SupportLanguage"/><br>
   		<bean:message bundle="projectpre" key="projectCode.Technical.Translation"/><br>
   		</td>
   		<td class="list_range" >
   		<input name="<%=Constant.TRANSLATION_LANGUANGE%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   		<bean:write name="item" property="name"/></td>
   <%}else if(a.intValue()==1||(a.intValue()+1)%5 != 1){%>
   	   <td class="list_range" >
   	   <input name="<%=Constant.TRANSLATION_LANGUANGE%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   	   <bean:write name="item" property="name"/></td>
   <%}else {%>
   	</tr>
   	<tr>	
        <td class="list_range"></td>
   		 <td class="list_range" >
   		 <input name="<%=Constant.TRANSLATION_LANGUANGE%>" class="CheckBoxStyle" type="checkbox" value='<bean:write name="item" property="code"/>' <bean:write name="item" property="status"/> />
   		 <bean:write name="item" property="name"/></td>
   <%}%>
   
  </logic:iterate>
  </tr>
</table>
	<table width="800" border="0">
  <tr>
    <td class="list_range"   width="19%">&nbsp;</td>
    <td class="list_range"   >&nbsp;</td>
    <td class="list_range"   >&nbsp;</td>
    <td class="list_range"   >&nbsp;</td>
    <td class="list_range"   >&nbsp;</td>
    <td class="list_range"   >&nbsp;</td>
    <td class="list_range"   >&nbsp;</td>
	<td class="list_range"   width="*">&nbsp;</td>

  </tr>
  <tr>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.DevelopEnvironment"/></td>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.JobSystem"/></td>
    <td class="list_range"  ><html:text styleId="input_common2" name="developJobSystem" beanName="webVo" fieldtype="text" maxlength="25"/></td>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.DataBase"/></td>
    <td class="list_range"  ><html:text styleId="input_common2" name="developDataBase" beanName="webVo" fieldtype="text" maxlength="25"/></td>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.Tool"/></td>
    <td class="list_range"  ><html:text styleId="input_common2" name="developTool" beanName="webVo" fieldtype="text" maxlength="25"/></td>
    <td class="list_range"   width="*">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.Network"/></td>
    <td class="list_range"  ><html:text styleId="input_common2" name="developNetWork" beanName="webVo" fieldtype="text" maxlength="25"/></td>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.ProgramLangage"/></td>
    <td class="list_range"  ><html:text styleId="input_common2" name="developProgramLanguage" beanName="webVo" fieldtype="text" maxlength="25"/></td>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.Others"/></td>
    <td class="list_range"  ><html:text styleId="input_common2" name="developOthers" beanName="webVo" fieldtype="text" maxlength="25"/></td>
   <td class="list_range"   width="*">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  >&nbsp;</td>
  <td class="list_range"   width="*">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"  >
     <bean:message bundle="projectpre" key="projectCode.Technical.TranslatePublishType"/></td>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.JobSystem"/></td>
    <td class="list_range"  ><html:text styleId="input_common2" name="typeJobSystem" beanName="webVo" fieldtype="text" maxlength="25"/></td>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.DataBase"/></td>
    <td class="list_range"  ><html:text styleId="input_common2" name="typeDataBase" beanName="webVo" fieldtype="text" maxlength="25"/></td>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.Tool"/></td>
    <td class="list_range"  ><html:text styleId="input_common2" name="typeTool" beanName="webVo" fieldtype="text" maxlength="25"/></td>
    <td class="list_range"   width="*">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"  >&nbsp;</td>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.Network"/></td>
    <td class="list_range"  ><html:text styleId="input_common2" name="typeNetWork" beanName="webVo" fieldtype="text" maxlength="25"/></td>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.ProgramLangage"/></td>
    <td class="list_range"  ><html:text styleId="input_common2" name="typeProgramLanguage" beanName="webVo" fieldtype="text" maxlength="25"/></td>
    <td class="list_range"  ><bean:message bundle="projectpre" key="projectCode.Technical.Others"/></td>
    <td class="list_range"  ><html:text styleId="input_common2" name="typeOthers" beanName="webVo" fieldtype="text" maxlength="25"/></td>
    <td class="list_range"   width="*">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range">&nbsp;</td>
    <td class="list_range"  colspan="7">&nbsp;</td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.Technical.HardRequirement"/></td>
    <td class="list_range" colspan="7">
      <html:textarea name="hardReq" beanName="webVo" rows="3" styleId="description" req="false" maxlength="500" styleClass="TextAreaStyle" />
    </td>
  </tr>
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.Technical.SoftRequirement"/></td>
    <td class="list_range" colspan="7">
      <html:textarea name="softReq" beanName="webVo" rows="3" styleId="description" req="false" maxlength="500" styleClass="TextAreaStyle" />
    </td>
  </tr>
</table>
	</html:form>
	<SCRIPT language="JavaScript" type="text/JavaScript">
	  onLoadBody();
	</SCRIPT>
  </body>
 
</html>
