<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%@ include file="/layout/dynamicLoginId.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <tiles:insert page="/layout/head.jsp">
       <tiles:put name="title" value=" "/>
       <tiles:put name="jspName" value=""/>
     </tiles:insert>
      <style type="text/css">
      #input_common1{width:120}
      #input_common2{width:490}
      #input_common3{width:160}
      #input_common4{width:273}
      #input_field{width:60}
      #description{width:600}
    </style>
    <script type="text/JavaScript" src="<%=contextPath%>/js/humanAllocate.js"></script>
     <script type="text/javascript" language="javascript">
     //用于查人
    function allocateHrDMName(){
     try{
      var form=document.forms[0];     
      var param = new HashMap();
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
      if(result!=""){  
      document.getElementsByName("dmName")[0].value = param.values()[0].name;
      document.forms[0].dmId.value = param.values()[0].loginId;
      }
      }catch(e){}    
    }
    //用于查人
    function allocateHrBD() {
     try{
      var form=document.forms[0];
      var param = new HashMap();
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
       if(result!=""){  
       document.getElementsByName("bdName")[0].value = param.values()[0].name;
       document.forms[0].bdId.value = param.values()[0].loginId;
      }
      }catch(e){}    
    }

   //用于查人
   function allocateHrTC() {
    try{
      var form=document.forms[0];
      var param = new HashMap();
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
       if(result!=""){  
       document.getElementsByName("tsName")[0].value = param.values()[0].name;
        document.forms[0].tsId.value = param.values()[0].loginId;
      }   
      }catch(e){}   
    }
     
      function onSearch(){
        AfQueryData.submit();
      }
     
      function onAddData(){
		 var height = 400;
         var width = 750;
         var topis = (screen.height - height) / 2;
         var leftis = (screen.width - width) / 2;
         var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
		 var url="<%=request.getContextPath()%>/dept/initAddDeptQuery.do";
		 var windowTitle="";
         window.open(url,windowTitle,option);
		}
		
		function getMyDATE(dateName){
        try{
    	var date = document.getElementById(dateName);
    	date.focus();
    	getDATE(date);
    	} catch(e){}
     }
     </script>
  </head>
  
  <body>
 
 <html:form id="AfQueryData" action="/dept/query/deptList" method="post" target="deptCodeList" >
<table width="750" order="0" style="padding-left:8px">
		<html:hidden name="bdId" beanName="webVo"/>
		<html:hidden name="tsId" beanName="webVo"/>
		<html:hidden name="dmId" beanName="webVo"/>
  <tr>
    <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.deptcode"/>
    </td>
    <td class="list_range" >
    <html:text styleId="input_common1" name="unitCode"  beanName="webVo"  fieldtype="text" readonly="false"  maxlength="20"/>
	</td>
	<td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.acheivebelongunit"/>
    </td>
	 <td class="list_range">
	<html:select name="belongBd" styleId="input_common1" beanName="webVo" >
	<html:optionsCollection name="webVo" property="bdList"></html:optionsCollection>
	</html:select></td>
	</tr>
	<tr>
     <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.deptmanager"/>
    </td>
    <td class="list_range" width="100">
	<html:text 
		name="dmName"
		maxlength="25"
		beanName="webVo" 
	    fieldtype="text" 
		styleId="input_common1" 
		prev="dueDate" 
		readonly="true" 
		req="false"
        imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
        imageWidth="18"
		imageOnclick="allocateHrDMName()" />
		</td>
	<td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.CostBelongUnit"/>
    </td>
    <td class="list_range">
	<html:select name="costBelongUnit" styleId="input_common1" beanName="webVo" >
	<html:optionsCollection name="webVo" property="costBelongUnitList"></html:optionsCollection>
	</html:select></td>
   </tr>
   <tr>
   <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.bdmanager"/>
    </td>
	    <td class="list_range" width="100">
		<html:text 
		name="bdName"
	    maxlength="25"
	    beanName="webVo" 
		fieldtype="text" 
	    styleId="input_common1" 
	    prev="dueDate"
	    readonly="true" 
	    req="false"
		imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
		imageWidth="18"
	    imageOnclick="allocateHrBD()" />
	</td>
	<td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.projattribute"/>
    </td>
    <td class="list_range" colspan="6">
    <html:text styleId="input_common1" name="acntAttribute"  beanName="webVo" fieldtype="text" readonly="true"  maxlength="20"/>
	</td>
    </tr>
     <tr>
    <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.belongsite"/>
    </td>
	 <td class="list_range">
	<html:select name="belongSite" styleId="input_common1" beanName="webVo">
	<html:optionsCollection name="webVo" property="siteList"></html:optionsCollection>
	</html:select>
	</td>
	  <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.parentdeptcode"/>
    </td>
    <td class="list_range">
    <html:select name="parentUnitCode" styleId="input_common1" beanName="webVo" >
	<html:optionsCollection name="webVo" property="parentUnitList"></html:optionsCollection>
	</html:select>
	</td>
 </tr>
 <tr >
   <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.syncexception.effectiveDate"/></td>
    <td class="list_range" width="100" colspan="3">
    <table border="0" cellspacing="0" cellpadding="0" align="left">
    <tr>
    <td>
     <html:text name="effectiveBegin"
           beanName="webVo"
           maxlength="10"
           fieldtype="dateyyyymmdd"
           styleId="input_common1"
           imageSrc="<%=contextPath+"/image/cal.png"%>"
           imageOnclick="getMyDATE('effectiveBegin')"
           maxlength="10" 
           req="false"
           ondblclick="getDATE(this)"
         />
         </td>
	 <td width="1" >
    ~
     </td>
     <td>
      <html:text name="effectiveEnd"
            beanName="webVo"
            maxlength="10"
            fieldtype="dateyyyymmdd"
            styleId="input_common1"
            imageSrc="<%=contextPath+"/image/cal.png"%>"
            imageOnclick="getMyDATE('effectiveEnd')"
            maxlength="10" 
            req="false"
            ondblclick="getDATE(this)"
         />
	</td>
	</tr>
	</table>
	</td>
</tr>
   <tr>
   <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.deptname"/>
    </td>
    <td class="list_range" colspan="4" >
    <html:text styleId="input_common2" name="unitName"  beanName="webVo"  fieldtype="text" readonly="false"  maxlength="200"/>
	</td>
   </tr>
   <tr>
   <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.deptfullname"/>
    </td>
    <td class="list_range" colspan="4">
    <html:text styleId="input_common2" name="unitFullName"  beanName="webVo"  fieldtype="text" readonly="false"  maxlength="200"/>
	</td>
   </tr>
</table>
</html:form>
    </body>
</html>
