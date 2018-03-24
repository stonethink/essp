<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%@ include file="/layout/dynamicLoginId.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<TITLE><bean:message bundle="projectpre" key="deptCode.DeptCodeModify.cardTitle.DepartmentEditAppply" /></TITLE>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
		<title>Dept Code Apply</title>
		<style type="text/css"> 
      #input_common1{width:235}
      #input_common2{width:120}
      #input_common3{width:470}
      #input_common4{width:67}
      #input_field{width:60}
      #description{width:100%}
    </style>
		<script type="text/JavaScript" src="<%=contextPath%>/js/humanAllocate.js"></script>
		<script type="text/javascript" language="JavaScript" src="<%=contextPath%>/js/queryAccount.js"></script>
		<script language="javascript" type="text/javascript">
		//点击部门查询控件所执行的操作
  function accountOnClick() {
    try{
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
     function onClickSubmit(){
     try{
     if(document.all.applicationStatus.value!="Submitted"){
         var form=document.forms[0];
         if(!checkReq(form)){
         	return;
          } 
         if(confirm("<bean:message bundle="application" key="global.confirm.submit"/>")){                
          form.applicationStatus.value="Submitted";
          form.submit();
     }else{
     submit_flug=false;
     }  
     } 
     }catch(e){}
 }
 //用于取消操作
 function onClickCancel(){
      this.close();
     }
 //检查必填栏位
 function checkReq(form){
	     if(!submitForm(form)){
    	 	return false;
	     } else {
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
    //根据选择的部门代号显示相应数据的操作
      function SelectAcntId(){
       try{
       if(document.all.acntId.value==null ||document.all.acntId.value==""){
       document.forms[0].action="<%=contextPath%>/dept/change/initialDeptApp.do";
       document.forms[0].submit();
      }else{
       document.forms[0].action="<%=contextPath%>/dept/change/selectAcntId.do";
       document.forms[0].submit();
     }
     }catch(e){}
     }
     
    
  </script>

	</head>

	<body>

		<html:form id="DeptApplication" method="post" action="/dept/change/addDeptApp" onsubmit="return onClickSubmit(this)">
			<html:hidden name="DMLoginId" beanName="webVo" />
			<html:hidden name="BDLoginId" beanName="webVo" />
			<html:hidden name="TCSLoginId" beanName="webVo" />
			<html:hidden name="applicationStatus" beanName="webVo" />
			<html:hidden name="applicantName" beanName="webVo" />
			<html:hidden name="rid" beanName="webVo" />
			<html:hidden name="applicantId" beanName="webVo" />
			<table align=center>
				<tr>
					<td class="list_range" width="120">
						<bean:message bundle="projectpre" key="deptCode.DeptCodeModify.cardTitle.DepartmentEditAppply" />
					</td>
				</tr>
			</table>
			<table width="700" align="center">
				<tr>

					<td class="list_range" width="100">
						<bean:message bundle="projectpre" key="deptCode.DepartmentNo" />
					</td>
					<td class="list_range" width="80">
						<html:select name="acntId" styleId="input_common2" beanName="webVo" req="true" onchange="SelectAcntId();">
							<html:optionsCollection name="webVo" property="acntIdList" />
						</html:select>
					</td>

					<td class="list_range" width="100">
						<bean:message bundle="projectpre" key="deptCode.MasterAttribute" />
					</td>
					<td class="list_range" width="100">
						<html:text styleId="input_common2" name="acntAttribute" beanName="webVo" fieldtype="text" maxlength="6" req="true" readonly="true" />
					</td>

				</tr>
				<tr>
					<td class="list_range">
						<bean:message bundle="projectpre" key="deptCode.DepartmentManager" />
					</td>
					<td class="list_range" width="100">
						<html:text name="deptManager" beanName="webVo" fieldtype="text" req="true" styleId="input_common2" prev="dueDate" imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" imageWidth="18" imageOnclick="allocateHr()" />
					</td>

					<td class="list_range" width="100">
						<bean:message bundle="projectpre" key="projectCode.MasterData.TimeCardSigner" />
					</td>
					<td class="list_range" width="100">
						<html:text name="TCSName" beanName="webVo" fieldtype="text" req="true" styleId="input_common2" prev="dueDate" imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" imageWidth="18" imageOnclick="allocateHrTC()" />
					</td>
				</tr>
				<tr>
					<td class="list_range" width="100">
						<bean:message bundle="projectpre" key="deptCode.BDManager" />
					</td>
					<td class="list_range" width="100">
						<html:text name="BDMName" beanName="webVo" fieldtype="text" req="true" styleId="input_common2" readonly="false" prev="dueDate" imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" imageWidth="18" imageOnclick="allocateHrBD()" />
					</td>
					<td class="list_range" width="100">
						<bean:message bundle="projectpre" key="deptCode.AcheiveBelongUnit" />
					</td>
					<td class="list_range" width="100">
						<html:select name="achieveBelong" styleId="input_common2" beanName="webVo" req="false">
							<html:optionsCollection name="webVo" property="belongBdList" />
						</html:select>
				</tr>
				<tr>
					<td class="list_range">
						<bean:message bundle="projectpre" key="deptCode.ParentDept" />
					</td>
					<td class="list_range" width="410" colspan="3">
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


					<td class="list_range">
						<bean:message bundle="projectpre" key="deptCode.DepartmentShortName" />
					</td>
					<td class="list_range" colspan="3">
						<html:text styleId="input_common3" name="acntName" beanName="webVo" fieldtype="text" maxlength="25" req="true" />
					</td>



				</tr>
				<tr>
					<td class="list_range">
						<bean:message bundle="projectpre" key="deptCode.DepartmentFullName" />
					</td>
					<td class="list_range" colspan="3">
						<html:text styleId="input_common3" name="acntFullName" beanName="webVo" fieldtype="text" maxlength="250" />
					</td>
				</tr>
				<tr>
					<td class="list_range">
						<bean:message bundle="projectpre" key="customer.Enable" />
					</td>
					<td class="list_range" colspan="2">
						<html:multibox styleId="width:100px" name="status" beanName="webVo" value="1"/>
					</td>
				</tr>

				<tr>
					<td height="50">
						&nbsp;
					</td>
				</tr>
			</table>
			<table width="650" border="0" align="center">
				<tr>

					<td align="right">
						<html:button styleId="btnSave" name="btnSave" onclick="onSaveData()">
							<bean:message bundle="application" key="global.button.save" />
						</html:button>
						<html:button styleId="btnSubmit" name="btnSubmit" onclick="onClickSubmit()">
							<bean:message bundle="application" key="global.button.submit" />
						</html:button>
						<html:button styleId="btnCancel" name="btnCancel" onclick="onClickCancel()">
							<bean:message bundle="application" key="global.button.cancel" />
						</html:button>
					</td>
					<td width="70"></td>
				</tr>
			</table>
		</html:form>
	</body>
</html>
