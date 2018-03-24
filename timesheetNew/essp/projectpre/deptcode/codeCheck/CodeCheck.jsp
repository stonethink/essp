<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<TITLE><bean:message bundle="projectpre" key="deptCode.DeptCodeCheck.cardTitle.ListCheck"/></TITLE>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
		<title><bean:message bundle="projectpre" key="deptCode.DeptCodeApply.Title"/></title>
		<style type="text/css">
      #input_common1{width:50}
      #input_common2{width:130}
      #input_common3{width:605}
      #input_common4{width:67}
      #input_field{width:60}
      #description{width:609}
    </style>
	<script language="JavaScript" type="text/JavaScript">
	 function onClickCancel(){
       this.close();
     }
 
    //此方法用于确认申请数据	
     function onConfirmData(){
         if(confirm("<bean:message bundle="application" key="global.confirm.check"/>")) {   
          var form=document.forms[0];    
          form.applicationStatus.value="Confirmed";
          form.submit();  
          }      
     
 
      }

  //用户拒绝申请数据
   function onClickReject(){
     try{
     if(document.all.remark.value!=null &&  document.all.remark.value.replace(/(^\s*)|(\s*$)/g, "")!="" ){      
        var form=document.forms[0];
            form.submit();       
    }else{
        alert("<bean:message bundle="application" key="global.fill.Remark"/>");
       document.all.remark.focus();
        return;
    }
    }catch(e){}
   }
    //检查必填栏位
      function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     } else {
	       return true;
	     }    
     }
     
    
  </script>
	</head>
 
	<body >
	    <html:form id="deptApplication" method="post" action="/dept/check/confirmAddApp" onsubmit="return onClickSubmit(this)"  >
		<table align=center><tr><td class="list_range"><bean:message bundle="projectpre" key="deptCode.DeptAddConfirm" /></td></tr></table>
			<table  style="padding-left:8px">
			<html:hidden name="DMLoginId" beanName="webVo"/>
           	<html:hidden name="BDLoginId" beanName="webVo"/>
	        <html:hidden name="TCSLoginId" beanName="webVo"/>
	        <html:hidden name="applicationStatus" beanName="webVo"/>
	        <html:hidden name="applicantId" beanName="webVo"/>
			<tr>				 
		      <td class="list_range" width="100">
                <bean:message bundle="projectpre" key="customer.ApplyNo"/>   
              </td>
             <td class="list_range" >
               <html:text styleId="input_common2" name="rid" beanName="webVo" fieldtype="text" readonly="true"  maxlength="12"/>
             </td>
					<td class="list_range" width="100">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Applicant"/>
					</td>
					<td class="list_range" width="100"> 
					<html:text styleId="input_common2" name="applicantName" beanName="webVo" fieldtype="text"  readonly="true"  maxlength="25"/>
					</td>
					   <td class="list_range" width="100" >
						<bean:message bundle="projectpre" key="deptCode.AcheiveBelongUnit"/>
					</td>
					<td class="list_range" width="100">
							<html:text styleId="input_common2" name="achieveBelong" beanName="webVo" fieldtype="text" maxlength="25" readonly="true"/>
					</td>					
					
				</tr>
			
				<tr>
					<td class="list_range" width="100">
			           <bean:message bundle="projectpre" key="deptCode.MasterAttribute"/>
					</td>
					<td class="list_range" width="40">
						<html:text styleId="input_common2" name="acntAttribute" beanName="webVo" fieldtype="text" readonly="true" maxlength="25" />
					</td>
					<td class="list_range">
						<bean:message bundle="projectpre" key="projectCode.MasterData.TimeCardSigner"/>
					</td>
					<td class="list_range" width="70">
						<html:text styleId="input_common2" name="TCSName" readonly="true" beanName="webVo" fieldtype="text" maxlength="25" />
					</td>
					
					<td class="list_range" width="80">
						<bean:message bundle="projectpre" key="deptCode.BDManager"/>
					</td>
					<td class="list_range" width="60">
						<html:text styleId="input_common2" name="BDMName" readonly="true" beanName="webVo" fieldtype="text" maxlength="25" />
					</td>
				</tr>
			
				<tr>
					<td class="list_range" width="100">
			            <bean:message bundle="projectpre" key="deptCode.DepartmentNo"/>
					</td>
					<td class="list_range" >
						<html:text styleId="input_common2" name="acntId"  beanName="webVo" fieldtype="text" maxlength="25" readonly="true"/>
					</td>
					
                 
					<td class="list_range" width="100">
						<bean:message bundle="projectpre" key="deptCode.DepartmentManager"/>
					</td>
					<td class="list_range" width="100">
						<html:text styleId="input_common2" name="deptManager" readonly="true" beanName="webVo" fieldtype="text" maxlength="25" />
					</td>
					
				</tr>
				<tr>
				<td class="list_range" width="100">
			           <bean:message bundle="projectpre" key="deptCode.ParentDept" />
					</td>
					<td class="list_range" colspan="9" >
						<html:text styleId="input_common3" name="parentDept" readonly="true" beanName="webVo" fieldtype="text" maxlength="25" />
					</td>
								
				</tr>
		        <tr>
				<td class="list_range" width="100">
			            <bean:message bundle="projectpre" key="deptCode.DepartmentShortName"/>
					</td>
					<td class="list_range" colspan="9" >
						<html:text styleId="input_common3" name="acntName" readonly="true" beanName="webVo" fieldtype="text" maxlength="25" />
					</td>
								
				</tr>
		        
				<tr>
				<td class="list_range" width="100">
						<bean:message bundle="projectpre" key="deptCode.DepartmentFullName"/>
					</td>
					<td class="list_range" colspan="9" >
						<html:text styleId="input_common3" name="acntFullName" beanName="webVo"  readonly="true" fieldtype="text" maxlength="25" />
					</td>
								
				</tr>
				<tr>
				</tr>
			</table>
     <table border="0" width="100%">
      <tr>
     <td class="list_range" width="101" ><bean:message bundle="projectpre" key="customer.ClientRemark"/></td>     
     <td class="list_range" colspan="9"><html:textarea name="remark" beanName="webVo" rows="3" styleId="input_common3"  maxlength="500" styleClass="TextAreaStyle" /></td>
   
     </tr>
     <tr><td class="list_range">&nbsp;</td></tr>
     <tr><td class="list_range">&nbsp;</td></tr>
     <tr>
     <td align="right" colspan="10">
		<html:button styleId="btnSave" name="btnSave" onclick="onConfirmData()">
		<bean:message bundle="application" key="global.button.confirm" />
	</html:button>		
	  <html:button styleId="btnSubmit" name="btnSubmit" onclick="onClickReject()">
			<bean:message bundle="application" key="global.button.reject" />
	</html:button>	
	<html:button styleId="btnCancel" name="btnCancel" onclick="onClickCancel()">
		<bean:message bundle="application" key="global.button.cancel" />
	</html:button>	</td>
	<td width="10"></td>			
	 
	</tr>
   </table>	
   </html:form>		
	</body>
</html>
