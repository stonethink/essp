<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<script language="JavaScript" type="text/JavaScript">
//用于取消操作
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
     if(document.all.description.value!=null && document.all.description.value.replace(/(^\s*)|(\s*$)/g, "")!="" ){      
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
     
     //初始化各个栏位的标题
     function onInitTitle(){ 
      try{ 
       var table=document.getElementById("alterTable");	    
        var str0="<bean:message bundle="projectpre" key="customer.UniformCode/RegisteredNumberofLicence" />";
        var str1="<bean:message bundle="projectpre" key="customer.GroupNo"/>";
		var str2="<bean:message bundle="projectpre" key="customer.ClientNo" />";
		var str3="<bean:message bundle="projectpre" key="customer.ClientShortName" />";
		var str4="<bean:message bundle="projectpre" key="customer.ChineseFullName" />";
        var str5="<bean:message bundle="projectpre" key="customer.EnglishFullName" />";
        var str6="<bean:message bundle="projectpre" key="customer.BelongedBD" />";
        var str7="<bean:message bundle="projectpre" key="customer.BelongedSite" />";
        var str8="<bean:message bundle="projectpre" key="customer.ClientClassCode" />";
        var str9="<bean:message bundle="projectpre" key="customer.ClientCountryCode" />";
        var str10="<bean:message bundle="projectpre" key="customer.CustomerDes" />";
        
		//国际化
		var rows = table.rows;
		rows[1].cells[0].innerHTML=rows[1].cells[0].innerHTML.replace("regId",str0);
		rows[2].cells[0].innerHTML=rows[2].cells[0].innerHTML.replace("groupId",str1);
		rows[3].cells[0].innerHTML=rows[3].cells[0].innerHTML.replace("customerId",str2);
		rows[4].cells[0].innerHTML=rows[4].cells[0].innerHTML.replace("customerShort",str3);
		rows[5].cells[0].innerHTML=rows[5].cells[0].innerHTML.replace("customerNameCn",str4);
		rows[6].cells[0].innerHTML=rows[6].cells[0].innerHTML.replace("customerNameEn",str5);
		rows[7].cells[0].innerHTML=rows[7].cells[0].innerHTML.replace("BelongBD",str6);
	    rows[8].cells[0].innerHTML=rows[8].cells[0].innerHTML.replace("BelongSite",str7);
		rows[9].cells[0].innerHTML=rows[9].cells[0].innerHTML.replace("customerClass",str8);
		rows[10].cells[0].innerHTML=rows[10].cells[0].innerHTML.replace("customerCountry",str9);
        rows[11].cells[0].innerHTML=rows[11].cells[0].innerHTML.replace("customerDes",str10);  
    }catch(e){}
  }
  
  
     //根据变更前后的数据控制栏位的颜色
     function onChangeColor(){
     var table=document.getElementById("alterTable");
     var rows = table.rows;
     for(i=1;i<rows.length;i++){
      var tds=rows[i].cells;
          if(tds[1].innerText!=tds[2].innerText){  
          tds[0].style.color="red";    
          tds[2].style.color="red";
          }
     }
     
     }
</script>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<TITLE><bean:message bundle="projectpre" key="customer.CustomerEditConfirm" /></TITLE>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value="" />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
		    
 <style type="text/css">
      #input_common1{width:80}
      #gs{width:690}
    </style>   
    
	</head>

	<body>
		<html:form id="CustomerApplication" method="post" action="/customer/check/confirmAlterApp" onsubmit="return onClickSubmit(this)"  >
			<table align=center><tr>
				<td class="list_range">
					<bean:message bundle="projectpre" key="customer.CustomerEditConfirm" />
				</td></tr>
			</table>
			<table width="100%" border="0">
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
			
	<table border="1" width="100%" id="alterTable">
	<tr>
		<td class="list_range" width="30%">
			<bean:message bundle="projectpre" key="deptCode.Option" />
		</td>
		<td class="list_range" width="35%">
			<bean:message bundle="projectpre" key="deptCode.BeforeChange" />&nbsp;
		</td>
		<td class="list_range" width="35%">
			<bean:message bundle="projectpre" key="deptCode.AfterChange" />&nbsp;
		</td>
	</tr>
	 <logic:iterate id="item"
           name="webVo"
           property="alterList"
           scope="request"
           indexId="a"
   >
   <tr>
   <td class="list_range" width="30%">
   <bean:write name="item" property="option"/>
   </td>
   <td class="list_range" width="35%">
   <bean:write name="item" property="beforeChange"/>&nbsp;
   </td>
   <td class="list_range" width="35%">
   <bean:write name="item" property="afterChange"/>&nbsp;
   </td>
   </tr>
  </logic:iterate>
			</table>
			<table border="0" width="100%">
				<tr>
					<td class="list_range" width=40>
						<bean:message bundle="projectpre" key="customer.ClientRemark" />
					</td>
					<td>
						<html:textarea name="description" beanName="webVo" rows="3" styleId="gs" req="true" maxlength="250" styleClass="TextAreaStyle" />
					</td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr>
		<td align="right" colspan="2">
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
		
   <script type="text/javascript" language="javascript">
    onInitTitle();
    onChangeColor();
    </script>
	   </html:form>
	</body>
</html>
