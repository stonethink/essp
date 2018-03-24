<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>

<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <TITLE><bean:message bundle="projectpre" key="customer.ClientAddConfirm"/></TITLE>
    <tiles:insert page="/layout/head.jsp">
       <tiles:put name="title" value=""/>
       <tiles:put name="jspName" value=""/>
     </tiles:insert>
    <style type="text/css">
      #input_common1{width:120}
      #input_common2{width:469}
      #input_common3{width:250}
      #input_common4{width:410}
      #input_field{width:60}
      #rewt{width:469}
    </style>
  <script language="JavaScript" type="text/JavaScript">
     function onClickCancel(){
       this.close();
     }
 
    //此方法用于确认申请数据	
     function onConfirmData(){
       try{
       var codingGenerate = "<bean:write name="codingGenerate"/>";
       if(codingGenerate != "ManCode"){    
         if(confirm("<bean:message bundle="application" key="global.confirm.check"/>")){     
          var form=document.forms[0];    
          form.applicationStatus.value="Confirmed";
          form.submit();  
          }      
      }else {
       if (document.all.customerId.value != null && document.all.customerId.value != "") { 
         if(confirm("<bean:message bundle="application" key="global.confirm.check"/>")) {   
          var form=document.forms[0];    
          form.applicationStatus.value="Confirmed";
          form.submit();  
          }      
         }else{
          alert("<bean:message bundle="application" key="global.fill.CusId"/>");
          document.all.customerId.focus();
           return;
         }
      }
     }catch(e){}
     }
    
  
  //用户拒绝申请数据
   function onClickReject(){
     if(document.all.description.value!=null && document.all.description.value.replace(/(^\s*)|(\s*$)/g, "")!=""){      
        var form=document.forms[0];   
            form.submit();       
    }else{
        alert("<bean:message bundle="application" key="global.fill.Remark"/>");
        document.all.description.focus();
        return;
    }
   }
    //检查必填栏位
      function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     } else {
	        return true;
	     }    
     }
     //根据编码生成方式控制客户编码栏位只读与否
    function onCheck(){
    try{
     var codingGenerate = "<bean:write name="codingGenerate"/>";
     if(codingGenerate != "SystemCode"){             
      document.forms[0].customerId.readonly="false";  
      document.forms[0].customerId.focus();
      document.forms[0].customerId.blur();     
    }else{
       document.forms[0].customerId.readOnly="true"; 
       document.forms[0].customerId.focus();
       document.forms[0].customerId.blur();     
    }
    }catch(e){}
   }
     
  </script>
  </head>
  <body>
	<html:form id="CustomerApplication" method="post" action="/customer/check/confirmAddApp" onsubmit="return onClickSubmit(this)"  >
	<table align=center><tr><td class="list_range"><bean:message bundle="projectpre" key="customer.ClientAddConfirm" /></td></tr></table>
 	<table width="650" border="0" style="padding-left:8px">
 	<html:hidden name="applicationStatus" beanName="webVo"/>
 	<!-- <tr>
					<td class="list_range" width="10">&nbsp;</td>
					<td class="list_range" width="75">&nbsp;</td>
					<td class="list_range" width="15%">&nbsp;</td>
					<td class="list_range" width="20">&nbsp;</td>
					<td class="list_range" width="40">&nbsp;</td>
					<td class="list_range" width="*">&nbsp;</td>
	</tr>-->
	
      <tr>
      <td class="list_range">&nbsp;</td>     
		<td class="list_range" width="150">
        <bean:message bundle="projectpre" key="customer.ApplyNo"/>   
       </td>     
       <td class="list_range" width="150" >
      <html:text styleId="input_common1" name="rid" beanName="webVo" fieldtype="text" readonly="true"  maxlength="10"/>
       </td>
       <td class="list_range" ></td>
      <td class="list_range" width="150">
      <bean:message bundle="projectpre" key="customer.UniformCode/RegisteredNumberofLicence"/>
      </td>
       <td class="list_range" width="150">
        <html:text styleId="input_common1" name="regId" beanName="webVo"  readonly="true" fieldtype="text"  maxlength="12" />
        </td>
      </tr>
      
     
      <tr>
       <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientNo"/>
        </td>
        <td class="list_range" >
        <html:text styleId="input_common1" name="customerId" beanName="webVo" fieldtype="text"  maxlength="10"/>
        </td>
         <td class="list_range" width="40"></td>
          <td class="list_range" width="80" >
         <bean:message bundle="projectpre" key="customer.Attribute"/>
         </td>  
         <td class="list_range" width="100">
		 <html:text styleId="input_common1" name="attribute" beanName="webVo" fieldtype="text"  maxlength="10" readonly="true"/>
	    </td>
       
      </tr>
      <logic:equal name="groupNo" value="true"> 
      <tr>
        <td class="list_range" >&nbsp;</td>     
        <td class="list_range" width="80" >
         <bean:message bundle="projectpre" key="customer.GroupNo"/>
         </td>  
         <td class="list_range" width="100">
		 <html:text styleId="input_common1" name="groupId" beanName="webVo" fieldtype="text"  maxlength="10" readonly="true"/>
	    </td>	    
	    </tr>
	    </logic:equal>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientShortName"/>
        </td>
        <td class="list_range" colspan="4">
		<html:text styleId="input_common2" name="customerShort" readonly="true" beanName="webVo" maxlength="25" fieldtype="text"  maxlength="20"/></td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ChineseFullName"/>
        </td>
        <td class="list_range" colspan="4">
		<html:text styleId="input_common2" name="customerNameCn" beanName="webVo" fieldtype="text" readonly="true" maxlength="25"/></td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.EnglishFullName"/>
        </td>
        <td class="list_range" colspan="4">
        <html:text styleId="input_common2" name="customerNameEn" readonly="true" beanName="webVo" fieldtype="text" maxlength="25" /></td>
      </tr>
    <tr>
        <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientClassCode"/>
        </td>
		 <td class="list_range">  
          <html:text styleId="input_common1" name="customerClass" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/>
         </td>
          <td class="list_range" ></td>
         <td class="list_range"><bean:message bundle="projectpre" key="customer.ClientCountryCode"/>
        </td>
         <td class="list_range">
          <html:text styleId="input_common1" name="customerCountry" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/>
         </td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range"><bean:message bundle="projectpre" key="customer.BelongedBD"/></td>
        <td class="list_range"> 
          <html:text styleId="input_common1" name="belongBd" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/>
         </td>
          <td class="list_range"  ></td>
        <td class="list_range" >   
		&nbsp;<bean:message bundle="projectpre" key="customer.BelongedSite"/>
		</td>	
		 <td class="list_range">
          <html:text styleId="input_common1" name="belongSite" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/>
         </td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range">
             <bean:message bundle="projectpre" key="customer.CustomerDes"/>
        </td>
         <td class="list_range" colspan="4">
              <html:textarea name="custDescription" beanName="webVo" rows="3" readonly="true" styleId="rewt"  maxlength="250" styleClass="TextAreaStyle" />
         </td>
      </tr>
      <tr>
      <td class="list_range" >&nbsp;</td>
        <td class="list_range">
          <bean:message bundle="projectpre" key="customer.ClientRemark"/>
        </td>
         <td class="list_range" colspan="4">
              <html:textarea name="description" beanName="webVo" rows="3" styleId="rewt" req="true" maxlength="250" styleClass="TextAreaStyle" />
         </td>
      </tr>
	  <tr height="10">
	  <td class="list_range" >&nbsp;</td>
	  </tr>
	   <tr>
	   <td class="list_range" >&nbsp;</td>	    
	     <td class="list_range" width="80" >
		     <bean:message bundle="projectpre" key="customer.CreateDate"/></td>
		     <td>
		     <html:text styleId="input_common1" name="applicationDate" beanName="webVo" fieldtype="text" readonly="true"  maxlength="4"/></td> 
		 <td class="list_range" width="40"></td> 
		   <td class="list_range" width="150" >
		    <bean:message bundle="projectpre" key="customer.Applicant"/> </td>
		    <td> 
		    <html:text styleId="input_common1" name="applicantName" beanName="webVo" fieldtype="text" readonly="true"  maxlength="4"/>
         </td>
	  </tr>
    </table>
     <table border="0" width="650">
     <tr></tr>
      <tr></tr>
       <tr></tr>
     <tr>
     <td align="right">	   
	 <html:button styleId="btnSave" name="btnSave" onclick="onConfirmData()">
		<bean:message bundle="application" key="global.button.confirm" />
	</html:button>		
	  <html:button styleId="btnSubmit" name="btnSubmit" onclick="onClickReject()">
			<bean:message bundle="application" key="global.button.reject" />
	</html:button>	
	<html:button styleId="btnCancel" name="btnCancel" onclick="onClickCancel()">
		<bean:message bundle="application" key="global.button.cancel" />
	</html:button>	  		
	 </td>
	<td class="list_range" width="26" ></td>
	</tr>
  </table>	
    <script type="text/javascript" language="javascript">
     onCheck();
   </script>
  
    </html:form>
  </body>
</html>
