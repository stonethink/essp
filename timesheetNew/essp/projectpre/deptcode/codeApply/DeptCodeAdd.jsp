<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%@ include file="/layout/dynamicLoginId.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>" />
<bean:define id="rid" name="webVo" property="rid" scope="request" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
		<title><bean:message bundle="projectpre" key="deptCode.DeptCodeApply.Title"/></title>
		<style type="text/css">
      #input_common1{width:260}
      #input_common2{width:130}
      #input_common3{width:637}
      #input_common4{width:120}
      #rewt{width:637}
      #input_field{width:60}
      #description{width:100%}
    </style>
<script type="text/JavaScript" src="<%=contextPath%>/js/humanAllocate.js"></script>   
<script type="text/javascript" language="JavaScript" src="<%=contextPath%>/js/queryAccount.js"></script>
<script language="JavaScript" type="text/JavaScript">
//点击部门查询控件所执行的操作
  function accountOnClick() {
    try{
     var form=document.forms[0];
     if(form.applicationStatus.value=="Submitted") return;
     var paramMap = new HashMap();
     var personMap = new HashMap();
     paramMap.put("is_acnt",new Array("0",""));
     var result = queryAccount(paramMap);
     if(result!=null){
     var str = result.acnt_id+"---"+result.acnt_name;
     document.all.parentDept.value=str;
     }
     }catch(e){}
  } 

function getQueryUserElementArray() {
		var names = new Array(document.getElementsByName("deptManager")[0],document.getElementsByName("BDMName")[0],document.getElementsByName("TCSName")[0]);
		var loginId = new Array(document.forms[0].DMLoginId,document.forms[0].BDLoginId,document.forms[0].TCSLoginId);
		return new Array(names,loginId);
	}
 //用于查部门经理 
    function allocateHr() {
      try{
      var form=document.forms[0];
      if(form.applicationStatus.value=="Submitted") return;      
      var param = new HashMap();
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
      if(result!=""){  
      document.getElementsByName("deptManager")[0].value = param.values()[0].name;
      document.forms[0].DMLoginId.value = param.values()[0].loginId;
      document.forms[0].DMDomain.value=param.values()[0].domain;
      }   
      }catch(e){}
    }
   
//用于查BD主管 
    function allocateHrBD() {
     try{
      var form=document.forms[0];
      if(form.applicationStatus.value=="Submitted") return;      
      var param = new HashMap();
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
       if(result!=""){  
       document.getElementsByName("BDMName")[0].value = param.values()[0].name;
       document.forms[0].BDLoginId.value = param.values()[0].loginId;
       
      }   
      }catch(e){} 
    }
    
	
//用于查工时签核者
 function allocateHrTC() {
     try{
      var form=document.forms[0];
      if(form.applicationStatus.value=="Submitted") return;      
      var param = new HashMap();
      //var oldValue = new allocUser("","","","");
      var result = allocSingleInAD(param,"","Allocate single user in AD","");
       if(result!=""){  
      document.getElementsByName("TCSName")[0].value = param.values()[0].name;
      document.forms[0].TCSLoginId.value = param.values()[0].loginId;
      }  
      }catch(e){}    
    }

 //用于提交数据
	  function onSubmitData(){ 
	  
	   var form=document.forms[0];
         if(!checkReq(form)){
         	return;
          }
	   if(confirm("<bean:message bundle="application" key="global.confirm.submit"/>")){ 
	      form.action = form.action + "?applicationStatus=Submitted";     
          form.submit();        
      }else{
      submit_flug=false;
      }
    }
    
    	
      //此方法用于检查必填项
    function checkReq(form){
     if(!submitForm(form)){
	 	return false;
      }else{
     	return true;
	     }     
    }
     
     //此方法用于保存数据	
       function onSaveData(){
        
        var form=document.forms[0];
         if(!checkReq(form)){
         	return;
          }
          form.applicationStatus.value="UnSubmit";
          form.submit();
    
  }
  //如果状态值为提交，则会灰掉相应栏位
   function onInit(){
      try{
       var status="<bean:write name="webVo" property="applicationStatus" scope="request" />"
       if(status=="Submitted"){
        window.parent.saveBtn.disabled="true";
        window.parent.submitBtn.disabled="true";
      	document.all.rid.disabled="ture";
      	document.all.acntId.disabled="true";
      	document.all.applicantName.disabled="ture";
      	document.all.applicationStatus.disabled="ture";
      	document.all.deptManager.disabled="ture";
     	document.all.BDMName.disabled="ture";
      	document.all.acntName.disabled="ture";
      	document.all.acntFullName.disabled="ture";
     	document.all.TCSName.disabled="ture";
      	document.all.achieveBelong.disabled="ture";
      	document.all.acntAttribute.disabled="ture";
      	document.all.achieveBelong.disabled="ture"; 
      	document.all.parentDept.disabled="true";
         }else{    	
      	   window.parent.saveBtn.disabled=false;
      	   window.parent.submitBtn.disabled=false;
      	} 
       }catch(e){} 
   }
    
  
    //此方法用于当下帧中的数据发生改变时同步上帧中的数据
      function refreshUp(){
         try{
            var deptManager=document.getElementById("deptManager").value;
        	var acntName=document.getElementById("acntName").value;
        	var applicationStatus=document.getElementById("applicationStatus").value;            
            var rid="<%=rid%>";
        	var upFrame=window.parent.frames[0];
            var table=upFrame.document.getElementById("deptCodeList_table");
        	for(i=1;i<table.rows.length;i++){ 	
        		if(table.rows[i].selfproperty==rid){        				    
        			var tds=table.rows[i].cells;	
        			tds[1].innerText=deptManager;
        			tds[1].title=deptManager;
        		    tds[2].innerText=acntName;
        			tds[2].title=acntName;
         			tds[4].innerText=applicationStatus;
        			tds[4].title=applicationStatus;       			
        		}
        	}
        	if(applicationStatus=="Submitted") {
        	      var obj = window.parent;
        	      obj.parent.deleteBtn.disabled=true;
        	}
        	
       	}catch(e){
     	}
        }
        //控制备注栏位显示与否
       function Status(){
         try{
         var hidTitle=document.getElementById("hidTitle");
         var hidContext=document.getElementById("hidContext");
         if(document.all.applicationStatus.value=="Rejected"){
         hidTitle.style.visibility="visible";
         hidContext.style.visibility="visible";
         }else{
          hidTitle.style.visibility="hidden";
          hidContext.style.visibility="hidden";
       }
       }catch(e){}
     }
     
  </script>
	</head>

	<body  >
	 <html:form id="DeptApplication" method="post" action="/dept/apply/updateDeptApp" onsubmit="return onClickSubmit(this)">
		<form method="POST" action="sample">
		
		<table style="padding-left:8px">
		<html:hidden name="DMLoginId" beanName="webVo"/>
		<html:hidden name="BDLoginId" beanName="webVo"/>
		<html:hidden name="DMDomain" beanName="webVo"/>
		<html:hidden name="TCSLoginId" beanName="webVo"/>
		
		
				<tr>
					<td class="list_range" width="80">
						<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyNo"/>
					</td>
					<td class="list_range" >
						<html:text styleId="input_common4" name="rid" beanName="webVo" fieldtype="text" readonly="true" maxlength="6" />
					</td>
					
					<td class="list_range" width="120">
						<bean:message bundle="projectpre" key="projectCode.MasterData.Applicant"/>
					</td>
					<td class="list_range" >
						<html:text styleId="input_common4" name="applicantName" beanName="webVo" fieldtype="text"  readonly="true"  maxlength="6"/>
					</td>
                    
					<td class="list_range" width="120">
						<bean:message bundle="projectpre" key="projectCode.MasterData.StatusCode"/>
					</td>
					<td class="list_range">
					<input name="applicationStatus" value=<bean:write name="webVo" property="applicationStatus"/> next="" prev="" msg="" req="false" sreq="false" class=" Xtext Nreq Display" id="input_common4" onblur="doBlur();" onfocus="doFocus();" readonly="readonly" fieldtype="text" fmt="" type="text" maxlength="6" fielderrorflg="" fieldmsg="" defaultvalue="true" />
						<%--html:text styleId="input_common4" name="applicationStatus" beanName="webVo" fieldtype="text" readonly="true" maxlength="6" /--%>
					</td>
				</tr>
			
				<tr>
				<td class="list_range" >
						<bean:message bundle="projectpre" key="deptCode.DepartmentNo"/>
					</td>
					<td class="list_range" >
						<html:text styleId="input_common4" name="acntId" beanName="webVo" fieldtype="text" maxlength="25" req="true" />
					</td>
				 
				
				
				<td class="list_range" >
						<bean:message bundle="projectpre" key="deptCode.BDManager"/>
				</td>
				<td class="list_range" width="50">
						<html:text name="BDMName"
						 beanName="webVo" 
						 maxlength="25"
						 fieldtype="text" 
						 req="true"
						 styleId="input_common4" 
					     prev="dueDate"  
					     imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
					     imageWidth="18"
						 imageOnclick="allocateHrBD()" />
				</td>		
				 
				   <td class="list_range">
						<bean:message bundle="projectpre" key="deptCode.AcheiveBelongUnit"/>
				 </td>
						<td class="list_range">
						
				   <html:select name="achieveBelong" styleId="input_common4" beanName="webVo"  req="false" >          
                       <html:optionsCollection name="webVo" property="belongBdList"/>
                  </html:select>	
					</td>
				</tr>
			
				<tr>
					<td class="list_range" >
			           <bean:message bundle="projectpre" key="deptCode.MasterAttribute"/>
					</td>
					<td class="list_range" >
					    <html:text styleId="input_common4" name="acntAttribute" beanName="webVo" fieldtype="text" maxlength="10" readonly="true"/>				
					</td>                    										
					
					<td class="list_range" >
						<bean:message bundle="projectpre" key="projectCode.MasterData.TimeCardSigner"/>
					</td>
					<td class="list_range" >
						<html:text name="TCSName" 
						beanName="webVo" 
						maxlength="25"
						req="true" 
						fieldtype="text" 
						styleId="input_common4" 
						prev="dueDate"  
						imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
						imageWidth="18" 
						imageOnclick="allocateHrTC()" />
					</td>
					<td class="list_range" >
						<bean:message bundle="projectpre" key="deptCode.DepartmentManager"/>
				</td>
				<td class="list_range" >
						<html:text name="deptManager"
						 beanName="webVo" 
						 maxlength="25"
					     req="true"				
						 fieldtype="text" 
						 styleId="input_common4"
						 prev="dueDate" 						 
						 imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>"					  
			             imageWidth="18" 
					     imageOnclick="allocateHr()" />
				</td>
				</tr>
				<tr>
				<td class="list_range"   >
					<bean:message bundle="projectpre" key="deptCode.ParentDept" />
					</td>
					<td class="list_range" width="410" colspan="5">
					<html:text name="parentDept"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common3"
                      prev="dueDate"
                      req="true"
		              readonly="true"
                      imageSrc="<%=contextPath+"/image/qurey.gif"%>"
                      imageWidth="18"
                      imageOnclick="accountOnClick();"
                      
         			/> 
					</td>
				</tr>	
				<tr>
				<td class="list_range" >
			            <bean:message bundle="projectpre" key="deptCode.DepartmentShortName"/>
					</td>
					<td class="list_range" colspan="5">
						<html:text styleId="input_common3" name="acntName" beanName="webVo" fieldtype="text" maxlength="25" req="true" />
					</td>
									              											
				</tr>
					<tr>
					<td class="list_range" >
						<bean:message bundle="projectpre" key="deptCode.DepartmentFullName"/>
					</td>
					<td class="list_range" colspan="5">
						<html:text styleId="input_common3" name="acntFullName" beanName="webVo" fieldtype="text" maxlength="250" />
					</td>
				</tr>
				<tr>
				<td class="list_range" width="100" >
				<div id="hidTitle" >
				<bean:message bundle="projectpre" key="customer.ClientRemark"/>
				</div>
				</td>     
               <td class="list_range" colspan="5">
               <div id="hidContext">
               <html:textarea name="remark" beanName="webVo" rows="3" styleId="rewt" readonly="true" maxlength="500" styleClass="TextAreaStyle" />
              </div>
              </td>
              </tr>
			</table>		
	</html:form>
	<SCRIPT language="JavaScript" type="text/JavaScript">
	  onInit();
	  refreshUp();
	  Status();
	</SCRIPT>
	</body>
</html>
