
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
     
     function onConfirm(){
        var effectiveBegin = document.getElementById("effectiveBegin").value;
        var effectiveEnd =document.getElementById("effectiveEnd").value; 
        if(effectiveBegin>effectiveEnd){
         alert("<bean:message bundle="application" key="global.effective.dateError"/>");
         document.getElementById("effectiveBegin").focus();
         return;
         }
       AfQueryData.submit();
     }

     function onClickCancel(){
       this.close();
     }
     
      //此方法用于检查必填项
     function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     } else {
	     	return true;
	     }
     }
     //用于提交数据
   function onClickSubmit(){
     var form=document.forms[0];
        if(!checkReq(form)){
        	return;
        }
         var effectiveBegin = document.getElementById("effectiveBegin").value;
         var effectiveEnd =document.getElementById("effectiveEnd").value; 
         if(effectiveBegin>effectiveEnd){
          alert("<bean:message bundle="application" key="global.effective.dateError"/>");
          document.getElementById("effectiveBegin").focus();
          submit_flug=false;
         } else {
          AfQueryData.submit();
         }
         
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
 
 <html:form id="AfQueryData" action="/dept/updateDept" method="post" onsubmit="return onClickSubmit(this)" >
 <table align=center><tr><td class="list_range"><STRONG><bean:message bundle="hrbase" key="hrbase.dept.deptupdate" /></STRONG></td></tr><tr height="20"><td></td></tr></table>
 <table width="750" order="0" style="padding-left:8px">
		<html:hidden name="bdId" beanName="webVo"/>
		<html:hidden name="tsId" beanName="webVo"/>
		<html:hidden name="dmId" beanName="webVo"/>	
  <tr>
    <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.deptcode"/>
    </td>
    <td class="list_range" >
    <html:text styleId="input_common1" name="unitCode"  beanName="webVo" req="true" fieldtype="text" readonly="true"  maxlength="10"/>
	</td>
	<td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.isBl"/>
    </td>
    <td class="list_range" >
    <html:checkbox name="isBl" beanName="webVo" styleId="isBl" checkedValue="1" defaultValue="0" uncheckedValue="0"/>
	</td>
	</tr>
	<tr>
	<td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.acheivebelongunit"/>
    </td>
	 <td class="list_range">
	<html:select name="belongBd" styleId="input_common1" beanName="webVo" >
	<html:optionsCollection name="webVo" property="bdList"></html:optionsCollection>
	</html:select>
	</td>
	<td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.CostBelongUnit"/>
    </td>
	 <td class="list_range">
	<html:select name="costBelongUnit" styleId="input_common1" beanName="webVo" req="true">
	<html:optionsCollection name="webVo" property="costBelongUnitList"></html:optionsCollection>
	</html:select>
	</td>
	
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
		req="true"
        imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
        imageWidth="18"
		imageOnclick="allocateHrDMName()" />
		</td>
	<td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.tcsinger"/>
    </td>
   <td class="list_range" width="100">
	<html:text 
	   name="tsName"
	   maxlength="25"
	   beanName="webVo" 
	   fieldtype="text" 
	   styleId="input_common1" 
	   prev="dueDate" 
	   readonly="true" 
	   req="true"
	   imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
       imageWidth="18"
	   imageOnclick="allocateHrTC()" />
   </td>
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
	    req="true"
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
	<html:select name="belongSite" styleId="input_common1" beanName="webVo" req="true">
	<html:optionsCollection name="webVo" property="siteList"></html:optionsCollection>
	</html:select>
	</td>
	  <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.parentdeptcode"/>
    </td>
    <td class="list_range">
    <html:select name="parentUnitCode" styleId="input_common1" beanName="webVo" req="true">
	<html:optionsCollection name="webVo" property="parentUnitList"></html:optionsCollection>
	</html:select>
	</td>
 </tr>
 <tr >
   <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.effBegin"/></td>
    <td class="list_range" width="100" >
     <html:text name="effectiveBegin"
           beanName="webVo"
           fieldtype="dateyyyymmdd"
           styleId="input_common1"
           imageSrc="<%=contextPath+"/image/cal.png"%>"
           imageOnclick="getMyDATE('effectiveBegin')"
           imageWidth="18"
           maxlength="10" 
           req="true"
           ondblclick="getDATE(this)"
         />
	</td>
	
	 <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.effEnd"/></td>
    <td class="list_range" width="100" >
     <html:text name="effectiveEnd"
           beanName="webVo"
           fieldtype="dateyyyymmdd"
           styleId="input_common1"
           imageSrc="<%=contextPath+"/image/cal.png"%>"
           imageOnclick="getMyDATE('effectiveEnd')"
           imageWidth="18"
           maxlength="10" 
           req="true"
           ondblclick="getDATE(this)"
         />
	</td>
	</tr>
   <tr>
   <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.deptname"/>
    </td>
    <td class="list_range" colspan="4" >
    <html:text styleId="input_common2" name="unitName"  beanName="webVo" req="true" fieldtype="text" readonly="false"  maxlength="200"/>
	</td>
   </tr>
   <tr>
   <td class="list_range" width="150"><bean:message bundle="hrbase" key="hrbase.dept.deptfullname"/>
    </td>
    <td class="list_range" colspan="4">
    <html:text styleId="input_common2" name="unitFullName"  beanName="webVo" fieldtype="text" readonly="false"  maxlength="200"/>
	</td>
   </tr>
   <tr height="60"><td></td></tr>
</table>
<table align="center" width="700">
  <tr align="right">
		<td >
		<html:button styleId="btnOk" name="btn" onclick="onClickSubmit()">
		<bean:message bundle="application" key="global.button.confirm"/>
		</html:button>
		<html:button styleId="btnCancel" name="btnCancel" onclick="onClickCancel()">
			<bean:message bundle="application" key="global.button.cancel"/>
		</html:button>
		</td>
		<td width="60"></td>
	</tr>
</table>
</html:form>
    </body>
</html>
