<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<script language="JavaScript" type="text/JavaScript">
 function onClickCancel(){
       this.close();
     }
 
    //此方法用于确认申请数据	
     function onConfirmData(){

        if(confirm("<bean:message bundle="application" key="global.confirm.update"/>")){
          var form=document.forms[0];  
          form.applicationStatus.value="Confirmed";
          form.submit();  
          }      
  
  }
  //用户拒绝申请数据
   function onClickReject(){
    try{
     if(document.all.description.value!=null && document.all.description.value.replace(/(^\s*)|(\s*$)/g, "")!=""){      
        var form=document.forms[0];
            form.submit();       
    }else{
        alert("<bean:message bundle="application" key="global.fill.Remark"/>");
       document.all.description.focus();
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
//国际化标题
function onInitTitle(){ 
    try{ 
       var table=document.getElementById("deptTable");	    
        var str0="<bean:message bundle="projectpre" key="deptCode.DepartmentNo" />";
        var str1="<bean:message bundle="projectpre" key="deptCode.DepartmentShortName" />";
		var str2="<bean:message bundle="projectpre" key="deptCode.DepartmentFullName" />";
		var str3="<bean:message bundle="projectpre" key="deptCode.AcheiveBelongUnit" />";
        var str4="<bean:message bundle="projectpre" key="deptCode.DepartmentManager" />";
        var str5="<bean:message bundle="projectpre" key="deptCode.BDManager" />";
        var str6="<bean:message bundle="projectpre" key="projectCode.MasterData.TimeCardSigner" />";
        var str7="<bean:message bundle="projectpre" key="customer.Enable" />";  
        var str="<bean:message bundle="projectpre" key="deptCode.ParentDept" />";     

		var rows = table.rows;
		rows[1].cells[0].innerHTML=rows[1].cells[0].innerHTML.replace("acntId",str0);
		rows[2].cells[0].innerHTML=rows[2].cells[0].innerHTML.replace("acntName",str1);
		rows[3].cells[0].innerHTML=rows[3].cells[0].innerHTML.replace("acntFullName",str2);
		rows[4].cells[0].innerHTML=rows[4].cells[0].innerHTML.replace("achieveBelong",str3);
		rows[5].cells[0].innerHTML=rows[5].cells[0].innerHTML.replace("parentDept",str);
		rows[6].cells[0].innerHTML=rows[6].cells[0].innerHTML.replace("DeptManager",str4);
		rows[7].cells[0].innerHTML=rows[7].cells[0].innerHTML.replace("BDName",str5);
		rows[8].cells[0].innerHTML=rows[8].cells[0].innerHTML.replace("TCSName",str6);
		rows[9].cells[0].innerHTML=rows[9].cells[0].innerHTML.replace("isEnable",str7);
	  }catch(e){}
     }

 //根据变更前后的内容控制行的颜色，若前后不一致则会变红
     function onChangeColor(){
      try{
     var table=document.getElementById("deptTable");
     var rows = table.rows;
     for(i=1;i<rows.length;i++){
      var tds=rows[i].cells;
          if(tds[1].innerText!=tds[2].innerText){  
          tds[0].style.color="red";    
          tds[2].style.color="red";
          }
     }
      }catch(e){}
     }
  
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <TITLE><bean:message bundle="projectpre" key="deptCode.EditedDataConfirm"/></TITLE>
    <tiles:insert page="/layout/head.jsp">
            <tiles:put name="title" value="Project Code Check"/>
            <tiles:put name="jspName" value=""/>
        </tiles:insert>
        <style type="text/css"> 
      #input_common1{width:80}
      #input_common2{width:80}
      #input_common3{width:200}
      #input_common4{width:67}
      #input_field{width:60}
      #description{width:685}
    </style>
  </head>
  
  <body >
   <html:form id="DeptApplication" method="post" action="/dept/check/confirmAlterApp" onsubmit="return onClickSubmit(this)">
   <table align=center><tr><td class="list_range"><bean:message bundle="projectpre" key="deptCode.EditedDataConfirm" /></td></tr></table>
  <table width="100%" border="0">
  <html:hidden name="DMLoginId" beanName="webVo"/>
  <html:hidden name="BDLoginId" beanName="webVo"/>
  <html:hidden name="TCSLoginId" beanName="webVo"/>
  <html:hidden name="applicationStatus" beanName="webVo"/>
				<tr>
					<td class="list_range"> 
						 <bean:message bundle="projectpre" key="customer.ApplyNo"/>
					</td>
					<td class="list_range">
						<html:text fieldtype="text" styleId="input_common1" name="rid" beanName="webVo" readonly="true" />
					</td>
					<td class="list_range">
						<bean:message bundle="projectpre" key="deptCode.Applicant" />
					</td>
					<td class="list_range">
						<html:text fieldtype="text" styleId="input_common1" name="applicantName" beanName="webVo" readonly="true" />
					</td>
					<td class="list_range">
						<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyDate" />
					</td>
					<td class="list_range">
						<html:text fieldtype="text" styleId="input_common1" name="applicationDate" beanName="webVo" readonly="true" />
					</td>
				
				 </tr>
				<tr>
					<td valign="bottom" height="5" colspan="8" class="orarowheader" width="100%">
						<bean:message bundle="projectpre" key="customer.CustomerEditInformation" />
					</td>

				</tr>
			</table>
   <table border="1" width="100%" id="deptTable">
    <tr>
    <td class="list_range" width="15%">
    <bean:message bundle="projectpre" key="deptCode.Option" />&nbsp;
    </td>
    <td class="list_range" width="40%">
    <bean:message bundle="projectpre" key="deptCode.BeforeChange" />&nbsp;
    </td>
     <td class="list_range" width="40%">
     <bean:message bundle="projectpre" key="deptCode.AfterChange" /> &nbsp;
    </td>
    </tr>
    <logic:iterate id="item"
                  name="webVo"
                  property="deptList"
                  scope="request"
                  indexId="a"
   >
   <tr>
   <td class="list_range" width="15%">
   <bean:write name="item" property="option"/>&nbsp;
   </td>
   <td class="list_range" width="40%">
   <bean:write name="item" property="beforeChange"/>&nbsp;
   </td>
   <td class="list_range" width="40%">
   <bean:write name="item" property="afterChange"/>&nbsp;
   </td>
   </tr>
  </logic:iterate>
    
  
   </table>
     <table border="0" width="100%">
     <tr>
     <td class="list_range" width="30" ><bean:message bundle="projectpre" key="customer.ClientRemark"/></td>     
     <td class="list_range"><html:textarea name="remark" beanName="webVo" rows="3" styleId="description" req="false" maxlength="500" styleClass="TextAreaStyle" /></td>
    
     </tr>
     <tr><td class="list_range">&nbsp;</td></tr>
      <tr><td class="list_range">&nbsp;</td></tr>
     <tr>
     <td align="right" colspan="3">
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
	</tr>
  </table>
  </html:form>
   <SCRIPT language="JavaScript" type="text/JavaScript">
     onInitTitle();
     onChangeColor();
   </SCRIPT>
  </body>
</html>
