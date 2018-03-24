<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ include file="/layout/dynamicLoginId.jsp" %>
<%@ page import="server.essp.projectpre.service.constant.Constant" %>
<%@ page import="c2s.essp.common.user.DtoUser" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="accessType" value='<%=request.getParameter("accessType")%>'/>
<bean:define id="appType" value='<%=request.getParameter("appType")%>'/>
<bean:define id="confirmStatus" value='<%=request.getParameter("confirmStatus")%>'/>
<bean:define id="acntId" value='<%=request.getParameter("acntId")%>'/>
<bean:define id="confirmCheck" value='<%=request.getParameter("confirmCheck")%>'/>
<%
    String parentExecSite = "";
    if(request.getAttribute("parentExecSite")!=null){
       parentExecSite = (String)request.getAttribute("parentExecSite");
    }
    DtoUser user = (DtoUser)request.getSession().getAttribute(DtoUser.SESSION_USER);
    String userLoginId = user.getUserLoginId();
    //List rolesList = user.getRoles();
    //String isFinance = "false";
    //int size = rolesList.size();
    //DtoRole dtoRole = null;
   // for(int i = 0;i<size;i++){  
    //	dtoRole = (DtoRole)rolesList.get(i);
    //	if(DtoRole.ROLE_XFI.equals(dtoRole.getRoleId())){
    //		isFinance = "true";
    //	}
   // }
    String isFinance = request.getParameter("isFinance");
    isFinance = "true".equalsIgnoreCase(isFinance) ? isFinance : "false"; 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <tiles:insert page="/layout/head.jsp">
		<tiles:put name="title" value=" " />
		<tiles:put name="jspName" value="" />
	 </tiles:insert>
    <title><bean:message bundle="projectpre" key="projectCode.CodeApplyDetail.cardTitle.MasterData"/></title>
    <style type="text/css">
      #input_common1{width:70}
      #input_common2{width:120}
      #input_common3{width:120}
      #input_common4{width:140}
      #input_common5{width:706}
      #input_common6{width:418}
      #input_common7{width:90}
      #input_common8{width:160}
      #input_common9{width:161}
      #input_common10{width:102}
      #input_field{width:160}
      #description{width:706}
      #description1{width:655}
      #input_sp{width:140;background-color:#FFFACD}
    </style>
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/humanAllocate.js"></script>
  <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/queryAccount.js"></script>
  <script language="JavaScript" type="text/JavaScript">

	function getQueryUserElementArray() {
		var names = new Array(document.getElementById("PMName"),
		                      //document.getElementById("TCSName"),
		                      document.getElementById("BDMName"),
		                      document.getElementById("leaderName"),
		                      document.getElementById("salesName"));
		var loginIds = new Array(document.getElementById("PMId"),
								 //document.getElementById("TCSId"),
								 document.getElementById("BDId"),
								 document.getElementById("leaderId"),
								 document.getElementById("salesId"));
		var domains = new Array(document.getElementById("PMDomain"),
								//document.getElementById("TCSDomain"),
								document.getElementById("BDDomain"),
								document.getElementById("leaderDomain"),
								document.getElementById("salesDomain"));
		return new Array(names, loginIds, domains);
	}
  //点击专案查询控件所执行的操作
 
  function accountOnClick() {
    try{
    var accessType = "<%=accessType%>";
     var appType = "<%=appType%>";
    if(appType=="confirm"){return;}
    if(accessType!="create"){
    var applicationStatus=document.getElementById("applicationStatus").value;
    if(applicationStatus=="Submitted"){return;}
   }
     var paramMap = new HashMap();
     var personMap = new HashMap();
     paramMap.put("is_acnt",new Array("1",""));
     var result = queryAccount(paramMap);
     if(result!=null){
     var str = result.acnt_id+"---"+result.acnt_name;
     document.all.parentProject.value=str;
     document.all.contractAcntId.value=result.contract_acnt_id;
     document.all.parentExecSite.value=result.exec_site;
     }
     }catch(e){}
  } 
  //根据功能模式来控制页面控件可用否
  
  function disableBtn(appType, isFinance){
    var accessType = "<%=accessType%>";
    var status="<bean:write name="webVo" property="applicationStatus" scope="request" />";
    //alert(status);
    //alert(appType);
    //alert(accessType);
    if(appType!="change"&&isFinance!="true"){ 
    
       if(document.all.relPrjType[0].checked){
      	    onClickRadio(1);
      	 } else{
      	    onClickRadio(2);
      	 }
    }else if(appType!="change"&&isFinance=="true"){
    	if(document.all.relPrjType.checked){
    	    onClickRadio(2);
    	}
    }
    //alert("normal");
  
      	
      	 //document.all.acntBrief.focus();
         //document.all.acntBrief.blur();
         
 
  try{
       
      	if(status=="Submitted"){
        //alert(window.parent.submitBtn);
     	window.parent.saveBtn.disabled=true;
      	window.parent.submitBtn.disabled=true;
      	if(appType!="change"&&isFinance!=null&&isFinance!="true"){
      	document.all.relPrjType[0].disabled=true;
      	document.all.relPrjType[1].disabled=true;
      	document.all.relPrjType[2].disabled=true;
      	document.all.parentProject.disabled=true;
      	} else if(appType!="change"&&isFinance!=null&&isFinance=="true"){
      	   document.all.relPrjType.disabled=true;
      	   document.all.parentProject.disabled=true;
      	}
      	
      	document.all.acntName.disabled=true;
      	document.all.PMName.disabled=true;
      	document.all.acntFullName.disabled=true;
      	//document.all.TCSName.disabled=true;
      	document.all.acntBrief.disabled=true;
      	document.all.achieveBelong.disabled=true;
      	document.all.BDMName.disabled=true;
      	document.all.leaderName.disabled=true;
      	document.all.execSite.disabled=true;
      	document.all.execUnitName.disabled=true;
      	document.all.costAttachBd.disabled=true;
      	document.all.bizSource.disabled=true;
      	document.all.salesName.disabled=true;
      	document.all.acntAttribute.disabled=true
      	document.all.productName.disabled=true;
      	document.all.acntPlannedStart.disabled=true;
      	document.all.acntPlannedFinish.disabled=true;
      	document.all.estManmonth.disabled=true;
      	document.all.estSize.disabled=true;
      	document.all.acntActualStart.disabled=true;
      	document.all.acntActualFinish.disabled=true;
      	document.all.actualManmonth.disabled=true;
      	document.all.otherDesc.disabled=true;
      	document.all.primaveraAdapted.disabled=true;
      	document.all.billingBasis.disabled=true;
      	document.all.bizProperty.disabled=true;
      	document.all.billType.disabled=true;
      	
      	parent.technicalData.disableTech();
      	parent.projectData.disableCustomer();
   
      	}
      	else if(status=="UnSubmit"||status=="Rejected"){
      	   //alert("ok");
      	   window.parent.saveBtn.disabled=false;
      	   window.parent.submitBtn.disabled=false;
      	} 

        if(appType=="change") {
        //document.all.achieveBelong.disabled=true;
        document.all.acntName.disabled=true;
        document.all.execSite.disabled=true;
        }
     }catch(e){}
  }
  
 
  
   //此方法用于检查必填项
     function checkReq(form){

	     if(!submitForm(form)){
	       
    	 	return false;
	    } else {
	       
              return true;	
           
	    }
	     
     }
     //将技术资料卡片中的类型与选中的选项的code拼接成字符串
     
     function onReadyCheckBox(type) {
    
      try{
       var types = parent.technicalData.document.forms[0].elements[type];
     
      if(types==null){
      //alert("null");
        return type;
      }
      if(!types.length){
  
        var obj = parent.technicalData.document.getElementById(type);
        if(obj.checked){
        type = type+","+obj.value;
        }
        //alert(type);
        return type;
      }
      //alert(type+":"+types.length);
      for( i=0;i<types.length;i++){
           //alert(i);
          // alert(types[i].checked);
      	if(types[i].checked == true){
      	    type = type+","+types[i].value;
     	}
     }
     
     return type;
     }catch(e){}
      
     }
     
     function onCheckDate(){
     try{
        var planStart = document.all.acntPlannedStart.value;
        var planFinish = document.all.acntPlannedFinish.value;
        var actualStart = document.all.acntActualStart.value;
        var actualFinish = document.all.acntActualFinish.value;
        if(planStart!=""&&planFinish!=""&&planStart>planFinish){
           alert("<bean:message bundle="application" key="global.planned.dateError"/>");
           return false;
        }
        if(actualStart!=""&&actualFinish!=""&&actualStart>actualFinish){
           alert("<bean:message bundle="application" key="global.actual.dateError"/>");
           return false;
        }
        return true;
        }catch(e){}
     }
    //保存申请
    
    function onSaveData(){
       
    try{
   
     var contract = parent.projectData.document.forms[0].elements["contract"].value; 
     var contractTel = parent.projectData.document.forms[0].elements["contractTel"].value;
     var contractEmail = parent.projectData.document.forms[0].elements["contractEmail"].value;
     var executive = parent.projectData.document.forms[0].elements["executive"].value;
     var executiveTel = parent.projectData.document.forms[0].elements["executiveTel"].value;
     var executiveEmail = parent.projectData.document.forms[0].elements["executiveEmail"].value;
     var financial = parent.projectData.document.forms[0].elements["financial"].value;
     var financialTel = parent.projectData.document.forms[0].elements["financialTel"].value;
     var financialEmail = parent.projectData.document.forms[0].elements["financialEmail"].value;
     var customerId = parent.projectData.document.forms[0].elements["customerId"].value;
     var custServiceManagerName = parent.projectData.document.forms[0].elements["custServiceManager"].value;
     var engageManagerName = parent.projectData.document.forms[0].elements["engageManager"].value;
     var custServiceManagerId = parent.projectData.document.forms[0].elements["custServiceManagerId"].value;
     var engageManagerId = parent.projectData.document.forms[0].elements["engageManagerId"].value;
  
	 
     var developJobSystem = parent.technicalData.document.forms[0].elements["developJobSystem"].value;
     var developDataBase = parent.technicalData.document.forms[0].elements["developDataBase"].value;
     var developTool = parent.technicalData.document.forms[0].elements["developTool"].value;
     var developNetWork = parent.technicalData.document.forms[0].elements["developNetWork"].value;
     var developProgramLanguage = parent.technicalData.document.forms[0].elements["developProgramLanguage"].value;
     var developOthers = parent.technicalData.document.forms[0].elements["developOthers"].value;
     var typeJobSystem = parent.technicalData.document.forms[0].elements["typeJobSystem"].value;
     var typeDataBase = parent.technicalData.document.forms[0].elements["typeDataBase"].value;
     var typeTool = parent.technicalData.document.forms[0].elements["typeTool"].value;
     var typeNetWork = parent.technicalData.document.forms[0].elements["typeNetWork"].value;
     var typeProgramLanguage = parent.technicalData.document.forms[0].elements["typeProgramLanguage"].value;
     var typeOthers = parent.technicalData.document.forms[0].elements["typeOthers"].value;
     var hardReq = parent.technicalData.document.forms[0].elements["hardReq"].value;
     var softReq = parent.technicalData.document.forms[0].elements["softReq"].value;
     
     var projectType = parent.technicalData.document.forms[0].elements["projectType"].value;
     var productType = onReadyCheckBox("<%=Constant.PRODUCT_TYPE%>");
     var productAttribute = onReadyCheckBox("<%=Constant.PRODUCT_ATTRIBUTE%>"); 
     var workItem = onReadyCheckBox("<%=Constant.WORK_ITEM%>"); 
     var technicalDomain = onReadyCheckBox("<%=Constant.TECHNICAL_DOMAIN%>");
     var originalLanguage = onReadyCheckBox("<%=Constant.ORIGINAL_LANGUAGE%>");
     var translationLanguage = onReadyCheckBox("<%=Constant.TRANSLATION_LANGUANGE%>");
     //return;

    // alert(info);
      
      var accessType = "<%=accessType%>";
      var appType = "<%=appType%>";
      var isFinance = "";
      if(accessType=="create"){
          isFinance = "<%=isFinance%>";
      } else if(accessType=="edit"){
          isFinance = "<%=request.getAttribute("isFinance")%>";
      }
      var form=document.forms[0];
      var parentExecSite = document.all.parentExecSite.value; 
      var currentExecSite = document.all.execSite.value;
      var costAttachBd = document.all.costAttachBd.value;
      var execUnitName = document.all.execUnitName.value;
      if(appType!="change"&&isFinance!="true"){
      	 if(document.all.relPrjType[1].checked){ 
      	    if(currentExecSite!=""&&parentExecSite!=""&&currentExecSite==parentExecSite){
             alert("<bean:message bundle="application" key="global.select.execSiteError"/>");
             return;
             }
         }
      }else if(appType!="change"&&isFinance=="true"){
          if(currentExecSite!=""&&parentExecSite!=""&&currentExecSite==parentExecSite){
             alert("<bean:message bundle="application" key="global.select.execSiteError"/>");
             return;
             }
      } 
     
    
      if(projectType==""){
         alert("<bean:message bundle="application" key="global.fill.projectType"/>");
         return;
      }
      if(customerId==null||customerId==""){
            alert("<bean:message bundle="application" key="global.fill.customerId"/>");
            return;      
          }
      if(document.all.billingBasis.checked){
      	if(document.all.billType.value==null||document.all.billType.value==""){
      		alert("<bean:message bundle="application" key="global.confirm.require"/>");
      		document.all.billType.focus();
      		return;
      	} 
      }    
      if(costAttachBd=="-1"){
      	alert("<bean:message bundle="application" key="global.confirm.require"/>");
      	document.all.costAttachBd.focus();
      	return;
      }
      if(execUnitName=="-1"){
      	alert("<bean:message bundle="application" key="global.confirm.require"/>");
      	
      	document.all.execUnitName.focus();
      	return;
      }
      if(!onCheckDate()){
         return;
      }
           if(!checkReq(form)){
            	return;
          }
           document.all.achieveBelong.disabled=false;
           document.all.acntName.disabled=false;
           document.all.execSite.disabled=false;
           
            form.contract.value=contract;
            form.contractTel.value=contractTel;
            form.contractEmail.value=contractEmail;
            form.executive.value=executive;
            form.executiveTel.value=executiveTel;
            form.executiveEmail.value=executiveEmail;
            form.financial.value=financial;
            form.financialTel.value=financialTel;
            form.financialEmail.value=financialEmail;
            form.customerId.value=customerId;
            form.custServiceManagerName.value=custServiceManagerName;
            form.engageManagerName.value=engageManagerName;
            form.custServiceManagerId.value=custServiceManagerId;
            form.engageManagerId.value=engageManagerId;
            form.developJobSystem.value=developJobSystem;
            form.developDataBase.value=developDataBase;
            form.developTool.value=developTool;
            form.developNetWork.value=developNetWork;
            form.developProgramLanguage.value=developProgramLanguage;
            form.developOthers.value=developOthers;
            form.typeJobSystem.value=typeJobSystem;
            form.typeDataBase.value=typeDataBase;
            form.typeTool.value=typeTool;
            form.typeNetWork.value=typeNetWork;
            form.typeProgramLanguage.value=typeProgramLanguage;
            form.typeOthers.value=typeOthers;
            form.hardReq.value=hardReq;
            form.softReq.value=softReq;
            form.projectType.value=projectType;
            form.productType.value=productType;
            form.productAttribute.value=productAttribute;
            form.workItem.value=workItem;
            form.technicalDomain.value=technicalDomain;
            form.originalLanguage.value=originalLanguage;
            form.translationLanguage.value=translationLanguage; 
      
      if(accessType=="create") {
          form.action="<%=contextPath%>/project/apply/addProjectAcntApp.do?appType="+appType;
      } else if(accessType=="edit"){
          form.action="<%=contextPath%>/project/apply/updateProjectAcntApp.do?accessType=edit&appType="+appType;
      }
     
       form.submit();
       }catch(e){}
    }
     //提交申请
     
    function onSubmitData() {
     try{
     
     var contract = parent.projectData.document.forms[0].elements["contract"].value; 
     var contractTel = parent.projectData.document.forms[0].elements["contractTel"].value;
     var contractEmail = parent.projectData.document.forms[0].elements["contractEmail"].value;
     var executive = parent.projectData.document.forms[0].elements["executive"].value;
     var executiveTel = parent.projectData.document.forms[0].elements["executiveTel"].value;
     var executiveEmail = parent.projectData.document.forms[0].elements["executiveEmail"].value;
     var financial = parent.projectData.document.forms[0].elements["financial"].value;
     var financialTel = parent.projectData.document.forms[0].elements["financialTel"].value;
     var financialEmail = parent.projectData.document.forms[0].elements["financialEmail"].value;
     var customerId = parent.projectData.document.forms[0].elements["customerId"].value;
     var custServiceManagerName = parent.projectData.document.forms[0].elements["custServiceManager"].value;
     var engageManagerName = parent.projectData.document.forms[0].elements["engageManager"].value;
     var custServiceManagerId = parent.projectData.document.forms[0].elements["custServiceManagerId"].value;
     var engageManagerId = parent.projectData.document.forms[0].elements["engageManagerId"].value;
     
     
     var developJobSystem = parent.technicalData.document.forms[0].elements["developJobSystem"].value;
     var developDataBase = parent.technicalData.document.forms[0].elements["developDataBase"].value;
     var developTool = parent.technicalData.document.forms[0].elements["developTool"].value;
     var developNetWork = parent.technicalData.document.forms[0].elements["developNetWork"].value;
     var developProgramLanguage = parent.technicalData.document.forms[0].elements["developProgramLanguage"].value;
     var developOthers = parent.technicalData.document.forms[0].elements["developOthers"].value;
     var typeJobSystem = parent.technicalData.document.forms[0].elements["typeJobSystem"].value;
     var typeDataBase = parent.technicalData.document.forms[0].elements["typeDataBase"].value;
     var typeTool = parent.technicalData.document.forms[0].elements["typeTool"].value;
     var typeNetWork = parent.technicalData.document.forms[0].elements["typeNetWork"].value;
     var typeProgramLanguage = parent.technicalData.document.forms[0].elements["typeProgramLanguage"].value;
     var typeOthers = parent.technicalData.document.forms[0].elements["typeOthers"].value;
     var hardReq = parent.technicalData.document.forms[0].elements["hardReq"].value;
     var softReq = parent.technicalData.document.forms[0].elements["softReq"].value;
    
     var projectType = parent.technicalData.document.forms[0].elements["projectType"].value;
     var productType = onReadyCheckBox("<%=Constant.PRODUCT_TYPE%>");
     var productAttribute = onReadyCheckBox("<%=Constant.PRODUCT_ATTRIBUTE%>");
     var workItem = onReadyCheckBox("<%=Constant.WORK_ITEM%>");
     var technicalDomain = onReadyCheckBox("<%=Constant.TECHNICAL_DOMAIN%>"); 
     var originalLanguage = onReadyCheckBox("<%=Constant.ORIGINAL_LANGUAGE%>");
     var translationLanguage = onReadyCheckBox("<%=Constant.TRANSLATION_LANGUANGE%>");
     
      var parentExecSite = document.all.parentExecSite.value;
      var currentExecSite = document.all.execSite.value;
      var appType = "<%=appType%>";
      var accessType = "<%=accessType%>";
      var isFinance = "";
      if(accessType=="create"){
          isFinance = "<%=isFinance%>";
      } else if(accessType=="edit"){
          isFinance = "<%=request.getAttribute("isFinance")%>";
      }
      var costAttachBd = document.all.costAttachBd.value;
      var execUnitName = document.all.execUnitName.value;
      if(appType!="change"&&isFinance!="true"){
      	 if(document.all.relPrjType[1].checked){ 
      	    if(currentExecSite!=""&&parentExecSite!=""&&currentExecSite==parentExecSite){
             alert("<bean:message bundle="application" key="global.select.execSiteError"/>");
             return;
             }
         }
      } else if(appType!="change"&&isFinance=="true"){
         if(currentExecSite!=""&&parentExecSite!=""&&currentExecSite==parentExecSite){
             alert("<bean:message bundle="application" key="global.select.execSiteError"/>");
             return;
             }
      } 
     if(projectType==""){
         alert("<bean:message bundle="application" key="global.fill.projectType"/>");
         return;
      }
     
       if(customerId==null||customerId==""){
            alert("<bean:message bundle="application" key="global.fill.customerId"/>");
            return;
       }
       if(document.all.billingBasis.checked){
      	if(document.all.billType.value==null||document.all.billType.value==""){
      		alert("<bean:message bundle="application" key="global.confirm.require"/>");
      		document.all.billType.focus();
      		return;
      	} 
      }
       if(costAttachBd=="-1"){
      	alert("<bean:message bundle="application" key="global.confirm.require"/>");
      	document.all.costAttachBd.focus();
      	return;
      }
      if(execUnitName=="-1"){
      	alert("<bean:message bundle="application" key="global.confirm.require"/>");
      	
      	document.all.execUnitName.focus();
      	return;
      }
       if(!onCheckDate()){
         return;
       }   
         if(!checkReq(form)){
        	return;
         }
     if(confirm("<bean:message bundle="application" key="global.confirm.submit"/>")) {
         var form=document.forms[0];
         document.all.achieveBelong.disabled=false;
         document.all.acntName.disabled=false;
         document.all.execSite.disabled=false;
           form.contract.value=contract;
           form.contractTel.value=contractTel;
           form.contractEmail.value=contractEmail;
           form.executive.value=executive;
           form.executiveTel.value=executiveTel;
           form.executiveEmail.value=executiveEmail;
           form.financial.value=financial;     
           form.financialTel.value=financialTel;
           form.financialEmail.value=financialEmail;
           form.customerId.value=customerId;
           form.custServiceManagerName.value=custServiceManagerName;
           form.engageManagerName.value=engageManagerName;
           form.custServiceManagerId.value=custServiceManagerId;
           form.engageManagerId.value=engageManagerId;
           form.developJobSystem.value=developJobSystem;
           form.developDataBase.value=developDataBase;
           form.developTool.value=developTool;
           form.developNetWork.value=developNetWork;
           form.developProgramLanguage.value=developProgramLanguage;
           form.developOthers.value=developOthers;
           form.typeJobSystem.value=typeJobSystem;
           form.typeDataBase.value=typeDataBase;
           form.typeTool.value=typeTool;
           form.typeNetWork.value=typeNetWork;
           form.typeProgramLanguage.value=typeProgramLanguage;
           form.typeOthers.value=typeOthers;
           form.hardReq.value=hardReq;
           form.softReq.value=softReq;
           form.projectType.value=projectType;
           form.productType.value=productType;
           form.productAttribute.value=productAttribute;
           form.workItem.value=workItem;
           form.technicalDomain.value=technicalDomain;
           form.originalLanguage.value=originalLanguage;
           form.translationLanguage.value=translationLanguage;
         
          if(accessType=="create") {
               form.action="<%=contextPath%>/project/apply/addProjectAcntApp.do?appType="+appType;
               form.createSubmit.value="Submitted";
          } else if(accessType=="edit"){
               form.action="<%=contextPath%>/project/apply/updateProjectAcntApp.do?applicationStatus=Submitted&accessType=edit&appType="+appType;
          }
          if(appType!="change"){
          	//if(confirm("<bean:message bundle="application" key="global.confirm.upload"/>")){
           
            //window.showModalDialog("/essp/projectpre/projectcode/codeapply/UploadBase.jsp", null,"dialogHeight:250px;dialogWidth:400px;dialogLeft:200; dialogTop:100");
             	openUpload();
          	//}
          }
          //alert("action execute after this");
         // submit_flug=false;
         // return;
          form.submit();
        } else {
            submit_flug=false;
            return;
        }
        }catch(e){}
    }
    
    function openUpload(){
       var PMName = document.all.PMName.value;
       var BDMName = document.all.BDMName.value;
       var leaderName = document.all.leaderName.value;
       var param = new HashMap();
       param.put("PMName",PMName);
       param.put("BDMName",BDMName);
       param.put("leaderName",leaderName);
       //alert(PMName);
       var height = 400;
       var width = 400; 
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "dialogHeight=" + height + "px"  +", dialogWidth=" + width + "px" +", dialogTop=" + topis + "px" +", dialogLeft=" + leftis + "px"  +", status=yes";
	   var url='<%=contextPath%>/projectpre/projectcode/codeapply/UploadBase.jsp';
	   var windowTitle="";
       window.showModalDialog(url, param ,option);
    
    }
    
    //此方法用于当下帧中的数据发生改变时同步上帧中的数据
      function refreshUp(){
         try{
            var accessType = "<%=accessType%>";
            if(accessType=="create"){return;}
            var appType = "<%=appType%>";
            
        	var acntName=document.getElementById("acntName").value;
        	var PMName=document.getElementById("PMName").value;
        	var achieveBelong=document.getElementById("achieveBelong").value;
        	var applicationStatus=document.getElementById("applicationStatus").value;
        	var rid="<bean:write name="webVo" property="rid" scope="request" />";
        	var upFrame=window.parent.frames[0];
        	if(appType=="change") {
        	    var table=upFrame.document.getElementById("changeAppList_table");
        	    for(i=1;i<table.rows.length;i++){
        		  if(table.rows[i].selfproperty==rid){
        			var tds=table.rows[i].cells;
        			tds[2].innerText=acntName;
        			tds[2].title=acntName;
        			tds[3].innerText=PMName;
        			tds[3].title=PMName;
        			tds[4].innerText=achieveBelong;
        			tds[4].title=achieveBelong;	
        			tds[6].innerText=applicationStatus;
        			tds[6].title=applicationStatus;	
        				
        		}
        	  }
           } else if(appType=="add"){
        	var table=upFrame.document.getElementById("applyRecordList_table");
        	for(i=1;i<table.rows.length;i++){
        		if(table.rows[i].selfproperty==rid){
        			var tds=table.rows[i].cells;
        			tds[1].innerText=acntName;
        			tds[1].title=acntName;
        			tds[2].innerText=PMName;
        			tds[2].title=PMName;
        			tds[3].innerText=achieveBelong;
        			tds[3].title=achieveBelong;	
        			tds[5].innerText=applicationStatus;
        			tds[5].title=applicationStatus;	
        				
        		}
        	 }
          }
        	if(applicationStatus=="Submitted") {
        	
        	      var obj = window.parent;
        	      obj.parent.deleteBtn.disabled=true;
        	   
        	}
        	}catch(e){}

        }
    //选择从属，联属专案类型时显示选择父专案代码控件
    
    function onClickRadio(index){
    
    try{ 
      var hidTitle=document.getElementById("hidTitle");
      var hidContext=document.getElementById("hidContext");
      var hidContractAcnt=document.getElementById("hidContractAcnt");
      var hidContractAcntText=document.getElementById("hidContractAcntText");
      if(index==1){
      	    hidTitle.style.visibility="hidden";
      	    hidContext.style.visibility="hidden"; 
      	    hidContractAcnt.style.visibility="hidden";
      	    hidContractAcntText.style.visibility="hidden";
      	    document.all.parentProject.disabled=true;
      	 
      	    
       }else{
        	hidTitle.style.visibility="visible";
      	    hidContext.style.visibility="visible";
      	    hidContractAcnt.style.visibility="visible";
      	    hidContractAcntText.style.visibility="visible";
      	    document.all.parentProject.disabled=false;
       }
       }catch(e){}
    }
    //初始化页面
    
    function onInit(){
    try{
         var appType = "<%=appType%>"
         var accessType = "<%=accessType%>";
         var confirmCheck = "<%=confirmCheck%>";
         var confirmStatus = "<%=confirmStatus%>";
         var parentExecSite = "<%=parentExecSite%>";
         var isFinance = "";
         if(accessType=="create"&&appType!="confirm"){
          	isFinance = "<%=isFinance%>";
      	 } else if(accessType=="edit"||appType=="confirm"){
            isFinance = "<%=request.getAttribute("isFinance")%>";
         }
         if(parentExecSite!=null){
           document.all.parentExecSite.value=parentExecSite;
         }
       
        // if(appType=="change") {
        //    hidMaster.style.visibility="visible";
      	//    hidMasterText.style.visibility="visible";
       //  } else {
      	//   hidMaster.style.visibility="hidden";
      	//   hidMasterText.style.visibility="hidden";
      //	 }
      if(appType!="change"&&isFinance!="true"){
      	 if(document.all.relPrjType[0].checked){
      	    onClickRadio(1);
      	 } else{
      	    onClickRadio(2);
      	 }
      }
       onClickChk(document.all.billingBasis);
      	 if(appType!="check"&&appType!="confirm") {
      	 	disableBtn(appType, isFinance);
      	 } else {
      		setReadonly(appType, isFinance);
      	 }
      	
    }catch(e){}
      
    }
    //复核时，各栏位变为只读
    
    function setReadonly(appType, isFinance) {
       try{
        if(isFinance!="true"){
            document.all.relPrjType[0].disabled=true;
      		document.all.relPrjType[1].disabled=true;
      		document.all.relPrjType[2].disabled=true;
      	}else {
      	    document.all.relPrjType.disabled=true;
      	} 
      	
      	document.all.parentProject.disabled=true;
      	document.all.acntName.disabled=true;
      	document.all.PMName.disabled=true;
      	document.all.acntFullName.disabled=true;
      	//document.all.TCSName.disabled=true;
      	document.all.acntBrief.disabled=true;
      	document.all.achieveBelong.disabled=true;
      	document.all.BDMName.disabled=true;
      	document.all.leaderName.disabled=true;
      	document.all.execSite.disabled=true;
      	document.all.execUnitName.disabled=true;
      	document.all.costAttachBd.disabled=true;
      	document.all.bizSource.disabled=true;
      	document.all.salesName.disabled=true;
      	document.all.acntAttribute.disabled=true
      	document.all.productName.disabled=true;
      	document.all.acntPlannedStart.disabled=true;
      	document.all.acntPlannedFinish.disabled=true;
      	document.all.estManmonth.disabled=true;
      	document.all.estSize.disabled=true;
      	document.all.acntActualStart.disabled=true;
      	document.all.acntActualFinish.disabled=true;
      	document.all.actualManmonth.disabled=true;
      	document.all.otherDesc.disabled=true;
      	document.all.primaveraAdapted.disabled=true;
      	document.all.billingBasis.disabled=true;
      	document.all.bizProperty.disabled=true;
      	document.all.billType.disabled=true;
    
      	parent.technicalData.disableTech();
      	parent.projectData.disableCustomer();
      	} catch (e){}
       if(appType=="confirm"){
         try{
         var confirmStatus = "<%=confirmStatus%>";
         var confirmCheck = "<%=confirmCheck%>";
          if(confirmStatus=="Submitted") {
              window.parent.submitBtn.disabled=true;
          } else {
               if(confirmCheck!="true") {
          	      document.all.acntActualStart.disabled=false;
      	 	      document.all.acntActualFinish.disabled=false;
      	 	      document.all.actualManmonth.disabled=false;
      	 	    }
          }
          }catch(e){}
       }
    }
    //变更申请中选择专案代码并显示相关资料
    
    function onSelectAcnt() {
    
       try{
         var obj = window.parent;
         var str = document.all.acntId.value; 
         if(str!=null&&str!=""){
    
         window.location="<%=contextPath%>/project/apply/previewAddProjectAcntApp.do?accessType=create&appType=change&view="+str;
         obj.technicalData.location="<%=contextPath%>/project/apply/previewTechInfoApp.do?accessType=create&view="+str;
         obj.projectData.location="<%=contextPath%>/project/apply/previewCustomerInfoApp.do?view="+str;
         } 
         if(str=="") {
     
         window.location="<%=contextPath%>/project/apply/previewAddProjectAcntApp.do?accessType=create&appType=change";
         obj.technicalData.location="<%=contextPath%>/project/apply/previewAddTechInfoApp.do";
         obj.projectData.location="<%=contextPath%>/projectpre/projectcode/codeapply/ProjectData.jsp";
         }
         }catch(e){}
    }
    function onConfirm() {
    try{
    var acntId = "<%=acntId%>";
    var confirmCheck = "<%=confirmCheck%>";
    if(confirmCheck!="true"&&!confirm("<bean:message bundle="application" key="global.confirm.customerId"/>")){
    	return;
    }
    var customerId = parent.projectData.document.forms[0].elements["customerId"].value;
    //alert(customerId);
    document.forms[0].customerId.value=customerId;
      
    if(confirm("<bean:message bundle="application" key="global.confirm.check"/>")){
      if(confirmCheck!="true"){
        document.forms[0].action="<%=contextPath%>/project/check/checkProjectApp.do?status=Confirmed";
        document.forms[0].submit();
      } else {
      //alert("ok");
       document.forms[0].action="<%=contextPath%>/project/check/checkConfirmProjectApp.do?status=Confirmed&acntId="+acntId;
       document.forms[0].submit();
      }   
    }
    }catch(e){}
   }
    function onCancel() {
     try{
       this.parent.close();
       }catch(e){}
    }
    //复制专案功能
    
    function onCopyData() {
    try{
     var loginId = "<%=userLoginId%>";
     var isFinance = "<%=isFinance%>";
     var paramMap = new HashMap();
     var personMap = new HashMap();
     paramMap.put("is_acnt",new Array("1",""));
     personMap.put("Applicant", loginId);
     //var site = document.all.execSite.value;
     //alert(site);
     var result = null;
     if(isFinance=="true"){//如果当前登陆者为财务人员则不限制其复制专案的范围，即可以复制所有专案
     //alert("ok");
     	result = queryAccount(paramMap);
     } else {
     	result = queryAccountPerson(paramMap, personMap);
     }
     
   if(result!=null){
     var obj = window.parent;
     var rid = result.rid;
     //alert(rid);
     obj.masterData.location="<%=contextPath%>/project/apply/previewProjectAcntApp.do?rid="+rid+"&accessType=create&appType=add&selectAcnt=true&isFinance=" + isFinance;
     obj.technicalData.location="<%=contextPath%>/project/apply/previewTechInfoApp.do?rid="+rid+"&selectAcnt=true";
     obj.projectData.location="<%=contextPath%>/project/apply/previewCustomerInfoApp.do?rid="+rid+"&selectAcnt=true";
     }
     }catch(e){}
    
    }
    function onReject() {
      try{
       //alert("reject");
       // alert(document.all.comment.req);
       // document.all.comment.req="true";
       // alert(document.all.comment.req);
       // document.all.comment.focus();
       // document.all.comment.blur();
        
      // var form = document.forms[0];
       // if(!checkReq(form)){
       //  	return;
       //   }
        var acntId = "<%=acntId%>";
        var confirmCheck = "<%=confirmCheck%>";
        var comment = document.all.comment.value;
        if(comment==null||comment.replace(/(^\s*)|(\s*$)/g, "")=="") {
          alert("<bean:message bundle="application" key="global.fill.Remark"/>");
          document.all.comment.focus();
          return;
        }
         if(confirmCheck!="true"){
          document.forms[0].action="<%=contextPath%>/project/check/checkProjectApp.do?status=Rejected";
          document.forms[0].submit();
          }else {
          //alert("confirmrejected");
          document.forms[0].action="<%=contextPath%>/project/check/checkConfirmProjectApp.do?status=Rejected&acntId="+acntId;
          document.forms[0].submit();
          }
          }catch(e){}
    }
    //点击找人控件所执行的操作
    
    function allocate(personType){
    try{
    var accessType = "<%=accessType%>";
    var appType = "<%=appType%>";
    if(appType=="confirm"){return;}
    if(accessType!="create"){
    var applicationStatus=document.getElementById("applicationStatus").value;
    if(applicationStatus=="Submitted"){return;}
    }
    param = new HashMap();
    var result = allocSingleInAD(param,"","","")
    if(param!=""&&personType=="PM"){
       document.all.PMName.value=param.values()[0].name;
       document.forms[0].PMId.value=param.values()[0].loginId;
       document.forms[0].PMDomain.value=param.values()[0].domain;
     
    }
    if(param!=""&&personType=="TCS"){
       document.all.TCSName.value=param.values()[0].name;
       document.forms[0].TCSId.value=param.values()[0].loginId;
       document.forms[0].TCSDomain.value=param.values()[0].domain;
    }
     if(param!=""&&personType=="BD"){
       document.all.BDMName.value=param.values()[0].name;
       document.forms[0].BDId.value=param.values()[0].loginId;
       document.forms[0].BDDomain.value=param.values()[0].domain;
    }
     if(param!=""&&personType=="Leader"){
       document.all.leaderName.value=param.values()[0].name;
       document.forms[0].leaderId.value=param.values()[0].loginId;
       document.forms[0].leaderDomain.value=param.values()[0].domain;
    }
     if(param!=""&&personType=="Sales"){
       document.all.salesName.value=param.values()[0].name;
       document.forms[0].salesId.value=param.values()[0].loginId;
       document.forms[0].salesDomain.value=param.values()[0].domain;
    }
    }catch(e){}
    
    }
     function onConfirmSubmit(){
     try{
     var acntActualStart = document.all.acntActualStart.value;
     var acntActualFinish = document.all.acntActualFinish.value;
     var actualManmonth = document.all.actualManmonth.value;
     var fillWarn ="<bean:message bundle="application" key="global.confirm.fill"/>";
   	 if(acntActualStart == null || Trim(acntActualStart) == "" ){
   	 	alert(fillWarn);
   	 	document.all.acntActualStart.focus();
   	 	return;
   	 } else if(acntActualFinish == null || Trim(acntActualFinish) == "") {
   	 	alert(fillWarn);
   	 	document.all.acntActualFinish.focus();
   	 	return;
   	 } else if(actualManmonth == null || Trim(actualManmonth) == "") {
   		 alert(fillWarn);
   	 	 document.all.actualManmonth.focus();
   	 	 return;
   	 }
   	 if(!onCheckDate()){
   	 	 document.all.acntActualStart.focus();
         return;
       }
     //alert("ok");
      var acntId = "<%=acntId%>";
      //alert(acntId);
     if(confirm("<bean:message bundle="application" key="global.confirm.submit"/>")){
       document.forms[0].action="<%=contextPath%>/project/confirm/confirmProjectApp.do?acntId="+acntId;
       document.forms[0].submit();
       }
       }catch(e){}
     
     }
     function getMyDATE(dateName){
     try{
     	var accessType = "<%=accessType%>";
    	var appType = "<%=appType%>";
    	if(appType=="confirm"){return;}
    	if(accessType!="create"){
    		var applicationStatus=document.getElementById("applicationStatus").value;
   		 if(applicationStatus=="Submitted"){return;}
    	}
    	var date = document.getElementById(dateName);
    	date.focus();
    	getDATE(date);
    	} catch(e){}
     }
     function getMyDATEConfirm(dateName){
     try{
     	var accessType = "<%=accessType%>";
    	var appType = "<%=appType%>";
    	//if(appType=="confirm"){return;}
    	var confirmStatus = "<%=confirmStatus%>";
          if(confirmStatus=="Submitted") {
              return;
          }
    	if(accessType!="create"){
    		var applicationStatus=document.getElementById("applicationStatus").value;
   		 if(applicationStatus=="Submitted"){return;}
    	}
    	var date = document.getElementById(dateName);
    	date.focus();
    	getDATE(date);
    	} catch(e){}
     }
     function onClickChk(obj) {
      var hidBillTitle=document.getElementById("hidBillTitle");
      var hidBillContent=document.getElementById("hidBillContent");
       if(obj.checked) { 
       	obj.value='1';
       	hidBillTitle.style.visibility="visible";
      	hidBillContent.style.visibility="visible";
      	document.all.billType.req="true";
      	document.all.billType.focus();
      	document.all.billType.blur();
        obj.focus();
        obj.blur();
       } else { 
       	obj.value='0';
      	document.all.billType.req="false";
      	document.all.billType.focus();
      	document.all.billType.blur();
      	document.all.billType.value="";
      	hidBillTitle.style.visibility="hidden";
      	hidBillContent.style.visibility="hidden";
       }
     }
  </script>
  </head>
  <body >
  <html:form id="ProjectAcntApp" action="" method="post" >
  <html:hidden name="projectType" beanName="webVo"/>
  <html:hidden name="productType" beanName="webVo"/>
  <html:hidden name="productAttribute" beanName="webVo"/>
  <html:hidden name="workItem" beanName="webVo"/>
  <html:hidden name="technicalDomain" beanName="webVo"/>
  <html:hidden name="originalLanguage" beanName="webVo"/>
  <html:hidden name="translationLanguage" beanName="webVo"/>
  <html:hidden name="developJobSystem" beanName="webVo"/>
  <html:hidden name="developDataBase" beanName="webVo"/>
  <html:hidden name="developTool" beanName="webVo"/>
  <html:hidden name="developNetWork" beanName="webVo"/>
  <html:hidden name="developProgramLanguage" beanName="webVo"/>
  <html:hidden name="developOthers" beanName="webVo"/>
  <html:hidden name="typeJobSystem" beanName="webVo"/>
  <html:hidden name="typeDataBase" beanName="webVo"/>
  <html:hidden name="typeTool" beanName="webVo"/>
  <html:hidden name="typeNetWork" beanName="webVo"/>
  <html:hidden name="typeProgramLanguage" beanName="webVo"/>
  <html:hidden name="typeOthers" beanName="webVo"/>
  <html:hidden name="hardReq" beanName="webVo"/>
  <html:hidden name="softReq" beanName="webVo"/>
  <html:hidden name="customerId" beanName="webVo"/>
  <html:hidden name="contract" beanName="webVo"/>
  <html:hidden name="contractTel" beanName="webVo"/>
  <html:hidden name="contractEmail" beanName="webVo"/>
  <html:hidden name="executive" beanName="webVo"/>
  <html:hidden name="executiveTel" beanName="webVo"/>
  <html:hidden name="executiveEmail" beanName="webVo"/>
  <html:hidden name="financial" beanName="webVo"/>
  <html:hidden name="financialTel" beanName="webVo"/>
  <html:hidden name="financialEmail" beanName="webVo"/>
  <html:hidden name="PMId" beanName="webVo"/>
  <html:hidden name="PMDomain" beanName="webVo"/>
  <html:hidden name="TCSId" beanName="webVo"/>
  <html:hidden name="TCSDomain" beanName="webVo"/>
  <html:hidden name="BDId" beanName="webVo"/>
  <html:hidden name="BDDomain" beanName="webVo"/>
  <html:hidden name="leaderId" beanName="webVo"/>
  <html:hidden name="leaderDomain" beanName="webVo"/>
  <html:hidden name="salesId" beanName="webVo"/>
  <html:hidden name="salesDomain" beanName="webVo"/>
  <html:hidden name="custServiceManagerName" beanName="webVo"/>
  <html:hidden name="engageManagerName" beanName="webVo"/>
  <html:hidden name="custServiceManagerId" beanName="webVo"/>
  <html:hidden name="custServiceManagerDomain" beanName="webVo"/>
  <html:hidden name="engageManagerId" beanName="webVo"/>
  <html:hidden name="engageManagerDomain" beanName="webVo"/>
  <html:hidden name="createSubmit" beanName="webVo"/>
  <html:hidden name="parentExecSite"/>

  
  <table width="790" border="0" >
  <%
  if(!request.getParameter("accessType").equals("create")){
  %>
  <tr><td class="list_range" width="60"><bean:message bundle="projectpre" key="projectCode.MasterData.ApplyNo"/></td>
  <td class="list_range" width="50"><html:text styleId="input_common7" name="rid" beanName="webVo" fieldtype="text" readonly="true" maxlength="8"/></td>
  <td class="list_range"></td>
  <td class="list_range" width="50"><bean:message bundle="projectpre" key="projectCode.MasterData.Applicant"/></td>
  <td class="list_range" width="50"><html:text styleId="input_common7" name="applicantName" beanName="webVo" fieldtype="text"  readonly="true"  maxlength="25"/></td>
  <td class="list_range"></td>
  <td class="list_range" width="70"><bean:message bundle="projectpre" key="projectCode.MasterData.StatusCode"/></td>
  <td class="list_range" ><html:text styleId="input_common7" name="applicationStatus" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/></td>
  <%}%>
  
     <%if(request.getParameter("accessType").equals("create")&&request.getParameter("appType").equals("change")) {%>
     <td class="list_range" width="73">
     <bean:message bundle="projectpre" key="projectCode.MasterData.ProjectCode"/></td>
  <td class="list_range">
           <html:select name="acntId" styleId="input_common9" beanName="webVo"  req="true" onchange="onSelectAcnt();">
                <html:optionsCollection name="webVo" property="acntIdList"/>
        </html:select>
      </td>
     <%}else if(request.getParameter("accessType").equals("edit")&&request.getParameter("appType").equals("change")){%>
      <td class="list_range" width="80">
     <bean:message bundle="projectpre" key="projectCode.MasterData.ProjectCode"/></td>
  <td class="list_range">
     <html:text styleId="input_common7" name="acntId" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/>
     </td>
     <%}%>
     
      <td class="list_range" width="80"></td>
  </tr>
  </table>
  <%
  if(!request.getParameter("appType").equals("change")
     &&!request.getParameter("appType").equals("confirm")
     &&request.getParameter("accessType").equals("create")){
  		if("true".equals(isFinance)){
  %>
  <table id="radioTable" width="580" border="0" >
    <tr>
    <td class="list_range" >
         <html:radiobutton styleId=""  name="relPrjType"  beanName="webVo" value="Finance" onclick="onClickRadio(2)" />
         </td>
     <td class="list_range" >
        <bean:message bundle="projectpre" key="projectCode.MasterData.FinProject"/> 
    </td>
    
     <td class="list_range" width="70">
       <div id="hidTitle">
        <bean:message bundle="projectpre" key="projectCode.MasterData.ParentProject"/>
       </div>
     </td>
   
    <td class="list_range" width="100">
        <div id="hidContext">
        <html:text name="parentProject"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common8"
                      prev="dueDate"
                      req="true"
		              readonly="true"
                      imageSrc="<%=contextPath+"/image/qurey.gif"%>"
                      imageWidth="18"
                      imageOnclick="accountOnClick();"
                      
         /> </div>
         </td>
   
    <td class="list_range">
    <div id="hidContractAcnt">
    <bean:message bundle="projectpre" key="projectCode.MasterData.MasterProject"/>
     </div>
       </td>
   
    <td class="list_range">
    <div id="hidContractAcntText">
    <html:text styleId="input_common7" name="contractAcntId" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/>
   </div>
    </td>
  </tr>
  </table>
  <%} else {%>
  <table id="radioTable" width="810" border="0" >
    <tr>
    <td class="list_range">
     <html:radiobutton styleId=""  name="relPrjType"  beanName="webVo" value="Master" onclick="onClickRadio(1)"/>
     </td>
     <td class="list_range" >
     <bean:message bundle="projectpre" key="projectCode.MasterData.MasterProject"/> 
    </td>
    <td class="list_range" >
      <html:radiobutton styleId=""  name="relPrjType"  beanName="webVo" value="Sub" onclick="onClickRadio(2)"/>
      </td>
     <td class="list_range" >
      <bean:message bundle="projectpre" key="projectCode.MasterData.SubProject"/>  
    </td>
    <td class="list_range" >
         <html:radiobutton styleId=""  name="relPrjType"  beanName="webVo" value="Related" onclick="onClickRadio(2)"/>
         </td>
     <td class="list_range" >
        <bean:message bundle="projectpre" key="projectCode.MasterData.RelProject"/> 
    </td>
    <td class="list_range" >
         </td>
     <td class="list_range" >
    </td>
    
     <td class="list_range" width="70">
       <div id="hidTitle">
        <bean:message bundle="projectpre" key="projectCode.MasterData.ParentProject"/>
       </div>
     </td>
   
    <td class="list_range" width="100">
        <div id="hidContext">
        <html:text name="parentProject"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common8"
                      prev="dueDate"
                      req="true"
		              readonly="true"
                      imageSrc="<%=contextPath+"/image/qurey.gif"%>"
                      imageWidth="18"
                      imageOnclick="accountOnClick();"
                      
         /> </div>
         </td>
   
    <td class="list_range">
    <div id="hidContractAcnt">
    <bean:message bundle="projectpre" key="projectCode.MasterData.MasterProject"/>
     </div>
       </td>
   
    <td class="list_range">
    <div id="hidContractAcntText">
    <html:text styleId="input_common7" name="contractAcntId" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/>
   </div>
    </td>
  </tr>
  </table>
  <%}
  } else if(request.getParameter("appType").equals("confirm")
     &&request.getParameter("accessType").equals("create")){
     if("true".equals(request.getAttribute("isFinance"))){
     %>
     <table id="radioTable" width="580" border="0" >
    <tr>
    <td class="list_range" >
         <html:radiobutton styleId=""  name="relPrjType"  beanName="webVo" value="Finance" onclick="onClickRadio(2)"/>
         </td>
     <td class="list_range" >
        <bean:message bundle="projectpre" key="projectCode.MasterData.FinProject"/> 
    </td>
    
     <td class="list_range" width="70">
       <div id="hidTitle">
        <bean:message bundle="projectpre" key="projectCode.MasterData.ParentProject"/>
       </div>
     </td>
   
    <td class="list_range" width="100">
        <div id="hidContext">
        <html:text name="parentProject"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common8"
                      prev="dueDate"
                      req="true"
		              readonly="true"
                      imageSrc="<%=contextPath+"/image/qurey.gif"%>"
                      imageWidth="18"
                      imageOnclick="accountOnClick();"
                      
         /> </div>
         </td>
   
    <td class="list_range">
    <div id="hidContractAcnt">
    <bean:message bundle="projectpre" key="projectCode.MasterData.MasterProject"/>
     </div>
       </td>
   
    <td class="list_range">
    <div id="hidContractAcntText">
    <html:text styleId="input_common7" name="contractAcntId" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/>
   </div>
    </td>
  </tr>
  </table>
  <%} else {%>
  <table id="radioTable" width="810" border="0" >
    <tr>
    <td class="list_range">
     <html:radiobutton styleId=""  name="relPrjType"  beanName="webVo" value="Master" onclick="onClickRadio(1)"/>
     </td>
     <td class="list_range" >
     <bean:message bundle="projectpre" key="projectCode.MasterData.MasterProject"/> 
    </td>
    <td class="list_range" >
      <html:radiobutton styleId=""  name="relPrjType"  beanName="webVo" value="Sub" onclick="onClickRadio(2)"/>
      </td>
     <td class="list_range" >
      <bean:message bundle="projectpre" key="projectCode.MasterData.SubProject"/>  
    </td>
    <td class="list_range" >
         <html:radiobutton styleId=""  name="relPrjType"  beanName="webVo" value="Related" onclick="onClickRadio(2)"/>
         </td>
     <td class="list_range" >
        <bean:message bundle="projectpre" key="projectCode.MasterData.RelProject"/> 
    </td>
    <td class="list_range" >
         </td>
     <td class="list_range" >
    </td>
    
     <td class="list_range" width="70">
       <div id="hidTitle">
        <bean:message bundle="projectpre" key="projectCode.MasterData.ParentProject"/>
       </div>
     </td>
   
    <td class="list_range" width="100">
        <div id="hidContext">
        <html:text name="parentProject"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common8"
                      prev="dueDate"
                      req="true"
		              readonly="true"
                      imageSrc="<%=contextPath+"/image/qurey.gif"%>"
                      imageWidth="18"
                      imageOnclick="accountOnClick();"
                      
         /> </div>
         </td>
   
    <td class="list_range">
    <div id="hidContractAcnt">
    <bean:message bundle="projectpre" key="projectCode.MasterData.MasterProject"/>
     </div>
       </td>
   
    <td class="list_range">
    <div id="hidContractAcntText">
    <html:text styleId="input_common7" name="contractAcntId" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/>
   </div>
    </td>
  </tr>
  </table>
  <%}
  }%>
  <%
  if(!request.getParameter("appType").equals("change")&&request.getParameter("accessType").equals("edit")){
  		if("true".equals(request.getAttribute("isFinance"))){
  %>
   <table id="radioTable" width="580" border="0" >
    <tr>
    <td class="list_range" >
         <html:radiobutton styleId=""  name="relPrjType"  beanName="webVo" value="Finance" onclick="onClickRadio(2)"/>
         </td>
     <td class="list_range" >
        <bean:message bundle="projectpre" key="projectCode.MasterData.FinProject"/> 
    </td>
    
     <td class="list_range" width="70">
       <div id="hidTitle">
        <bean:message bundle="projectpre" key="projectCode.MasterData.ParentProject"/>
       </div>
     </td>
   
    <td class="list_range" width="100">
        <div id="hidContext">
        <html:text name="parentProject"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common8"
                      prev="dueDate"
                      req="true"
		              readonly="true"
                      imageSrc="<%=contextPath+"/image/qurey.gif"%>"
                      imageWidth="18"
                      imageOnclick="accountOnClick();"
                      
         /> </div>
         </td>
   
    <td class="list_range">
    <div id="hidContractAcnt">
    <bean:message bundle="projectpre" key="projectCode.MasterData.MasterProject"/>
     </div>
       </td>
   
    <td class="list_range">
    <div id="hidContractAcntText">
    <html:text styleId="input_common7" name="contractAcntId" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/>
   </div>
    </td>
  </tr>
  </table>
  <%} else {%>
   <table id="radioTable" width="810" border="0" >
    <tr>
    <td class="list_range">
     <html:radiobutton styleId=""  name="relPrjType"  beanName="webVo" value="Master" onclick="onClickRadio(1)"/>
     </td>
     <td class="list_range" >
     <bean:message bundle="projectpre" key="projectCode.MasterData.MasterProject"/> 
    </td>
    <td class="list_range" >
      <html:radiobutton styleId=""  name="relPrjType"  beanName="webVo" value="Sub" onclick="onClickRadio(2)"/>
      </td>
     <td class="list_range" >
      <bean:message bundle="projectpre" key="projectCode.MasterData.SubProject"/>  
    </td>
    <td class="list_range" >
         <html:radiobutton styleId=""  name="relPrjType"  beanName="webVo" value="Related" onclick="onClickRadio(2)"/>
         </td>
     <td class="list_range" >
        <bean:message bundle="projectpre" key="projectCode.MasterData.RelProject"/> 
    </td>
    <td class="list_range" >
         </td>
     <td class="list_range" >
    </td>
    
     <td class="list_range" width="70">
       <div id="hidTitle">
        <bean:message bundle="projectpre" key="projectCode.MasterData.ParentProject"/>
       </div>
     </td>
   
    <td class="list_range" width="100">
        <div id="hidContext">
        <html:text name="parentProject"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common8"
                      prev="dueDate"
                      req="true"
		              readonly="true"
                      imageSrc="<%=contextPath+"/image/qurey.gif"%>"
                      imageWidth="18"
                      imageOnclick="accountOnClick();"
                      
         /> </div>
         </td>
   
    <td class="list_range">
    <div id="hidContractAcnt">
    <bean:message bundle="projectpre" key="projectCode.MasterData.MasterProject"/>
     </div>
       </td>
   
    <td class="list_range">
    <div id="hidContractAcntText">
    <html:text styleId="input_common7" name="contractAcntId" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/>
   </div>
    </td>
  </tr>
  </table>
  <%}
  }%>
  <table width="810" border="0" >
  <tr>
  
    <td class="list_range" width="120"> <bean:message bundle="projectpre" key="projectCode.MasterData.NiceName"/></td>
    <td class="list_range" width="188">
    <html:text styleId="input_field" name="acntName" beanName="webVo" fieldtype="text" req="true" maxlength="25" />
    </td>
    
    <td class="list_range" width="100"> <bean:message bundle="projectpre" key="projectCode.MasterData.ProjectManager"/></td>
    <td class="list_range" width="70">
       <html:text name="PMName"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common3"
                      prev="dueDate"
                      req="true"
                      imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>"
                      imageWidth="18"
                      imageOnclick="allocate('PM')"
                      
                    
         />
    </td>
    <td class="list_range" width="130">&nbsp;&nbsp;&nbsp;
    <%--bean:message bundle="projectpre" key="projectCode.MasterData.TimeCardSigner"/--%>
    </td>
    <td class="list_range" >
    <%--html:text name="TCSName"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common3"
                      prev="dueDate"
                      req="true"
                      imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>"
                      imageWidth="18"
                      imageOnclick="allocate('TCS')"
                      
         /--%>
         &nbsp;
    </td>
    <td class="list_range" width="35">&nbsp;</td>
    
    

   
  </tr>
  <tr>
   
    <td class="list_range" ><bean:message bundle="projectpre" key="projectCode.MasterData.ProjectName"/> </td>
    <td class="list_range" colspan="6">
      <html:text styleId="input_common5" name="acntFullName" beanName="webVo" req="true" fieldtype="text" maxlength="250"/>
    </td>
    
    
  
      
    
  </tr>
 
  <tr>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.MasterData.ProjectDesc"/> </td>
    <td class="list_range" colspan="6">
      <html:textarea name="acntBrief" beanName="webVo" rows="2" styleId="description" req="true" maxlength="2000"  />
    </td>
  </tr>
   </table>
  <table width="820" border="0">
  <tr>
    <td class="list_range" >
    <bean:message bundle="projectpre" key="projectCode.MasterData.AchieveBelong"/>
     </td>
    <td class="list_range" width="140">
        <html:select name="achieveBelong" styleId="input_common2" beanName="webVo"  req="true" >
           
            <html:optionsCollection name="webVo" property="achieveBelongList"/>
        </html:select>
    </td>
    <td class="list_range" width="120"><bean:message bundle="projectpre" key="projectCode.BD" /></td>
    <td class="list_range" width="50">
    <html:text name="BDMName" 
               beanName="webVo" 
               fieldtype="text" 
               styleId="input_common3" 
               prev="dueDate" 
               req="true" 
               imageSrc='<%=contextPath+"/image/humanAllocate.gif"%>' 
               imageWidth="18" 
               imageOnclick="allocate('BD')"  
               /></td>
    <td class="list_range" width="135"><bean:message bundle="projectpre" key="projectCode.MasterData.Leader"/> </td>
    <td class="list_range" width="50">
       
         <html:text name="leaderName"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common3"
                      prev="dueDate"
                      req="true"
                      imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>"
                      imageWidth="18"
                      imageOnclick="allocate('Leader')"
         />
    </td>
       <td class="list_range" width="35">&nbsp;</td>
  </tr>
  <tr>
  
  </tr>
 <td class="list_range" ><bean:message bundle="projectpre" key="projectCode.MasterData.BizProperty"/></td>
    <td class="list_range">
        <html:select name="bizProperty" styleId="input_common2" beanName="webVo"  req="true" >
            <html:optionsCollection name="webVo" property="bizPropertyList"/>
        </html:select>
        </td>
        <td class="list_range" > 
    <bean:message bundle="projectpre" key="projectCode.MasterData.BillingBasis"/>
    </td>
    <td class="list_range" width="150">
    <input type="checkbox" id="billingBasis" name="billingBasis" onclick="onClickChk(this)" <bean:write name="webVo" property="chkBillingBasis"/>/>
         <!-- html:checkbox styleId="CheckBox" name="billingBasis"  beanName="webVo" checkedValue="1" uncheckedValue ="0" defaultValue="0" styleClass="CheckBoxStyle"/-->
      </td>
      	<td class="list_range" >
      	<div id="hidBillTitle">
      	<bean:message bundle="projectpre" key="projectCode.MasterData.BillType"/>
      	</div>
      	</td>
    <td class="list_range">
    <div id="hidBillContent">
        <html:select name="billType" styleId="input_sp" beanName="webVo"  >
            <html:optionsCollection name="webVo" property="billTypeList"/>
        </html:select>
     </div>
     </td>
  <tr>
    <td class="list_range" ><bean:message bundle="projectpre" key="projectCode.MasterData.ProjectExecuteSite"/> </td>
    <td class="list_range">
        <html:select name="execSite" styleId="input_common2" beanName="webVo"  req="true" >
            
            <html:optionsCollection name="webVo" property="execSiteList"/>
        </html:select>
        </td>
    <td class="list_range" width="120">
    <bean:message bundle="projectpre" key="projectCode.MasterData.PrimaveraAdapted"/>
    </td>
    <td class="list_range" colspan="4">
    <html:checkbox styleId="CheckBox" name="primaveraAdapted" beanName="webVo" checkedValue="1" uncheckedValue ="0" defaultValue="0" styleClass="CheckBoxStyle"/>
    </td>
   </tr>
   <tr>
   <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.MasterData.CostBelong"/>
   </td>
   <td class="list_range">
    <bean:define id="cost" name="webVo" property="costAttachBd" scope="request" type="java.lang.String"/>
        <ws:select id="costAttachBd" 
                   property="costAttachBd"
                   type="server.essp.projectpre.service.accountapplication.CostDeptSelectImpl" 
                   onchange="costAttachBdSelectChangeEventexecUnitName('null');" 
				   req="true" 
                   style="width:120px;background-color:#FFFACD"
                   default="<%=cost%>"
                   />
   </td>
   <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.MasterData.ProjectExecUnit"/>
   </td>
   <td class="list_range" colspan="4">
   <bean:define id="execUnit" name="webVo" property="execUnitName" scope="request" type="java.lang.String"/>
    <ws:upselect id="execUnitName" 
                 property="execUnitName" 
                 up="costAttachBd" 
                 type="server.essp.projectpre.service.accountapplication.ExecUnitImpl" 
                 req="true"
                 style="width:419px;background-color:#FFFACD"
                 default="<%=execUnit%>"
                 />
   </td>
   </tr>
   
  <tr>
  <td class="list_range">
       <bean:message bundle="projectpre" key="projectCode.MasterData.BizSource"/>
    </td>
    <td class="list_range">
      <html:select name="bizSource" styleId="input_common2" beanName="webVo"  req="false" >
            
            <html:optionsCollection name="webVo" property="achieveBelongList"/>
        </html:select>
    </td>
    <td class="list_range">
       <bean:message bundle="projectpre" key="projectCode.MasterData.Sales"/>
    </td>
     <td class="list_range" width="50">
       <html:text name="salesName"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common3"
                      prev="dueDate"
                      req="false"
                      imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>"
                      imageWidth="18"
                      imageOnclick="allocate('Sales')"
                    
         />
    </td>
    <td class="list_range" > 
    <bean:message bundle="projectpre" key="projectCode.MasterData.ProjectProperty"/>
    </td>
    <td class="list_range" >
    <html:select name="acntAttribute" styleId="input_common4" beanName="webVo"  req="true" >
            <html:optionsCollection name="webVo" property="acntAttributeList"/>
        </html:select> 
        </td>
  </tr>
  
  

  <tr>
    <td class="list_range" > <bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingStart"/></td>
    <td class="list_range" width="100">
    <html:text name="acntPlannedStart"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common10"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATE('acntPlannedStart')"
                      maxlength="10" 
                      req="true"
                      ondblclick="getDATE(this)"
                      
         />
    <%--html:text styleId="input_common2" fieldtype="dateyyyymmdd" name="acntPlannedStart" beanName="webVo"  maxlength="10" ondblclick="getDATE(this)"/--%>
    </td>
    <td class="list_range" ><bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingEnd"/></td>
    <td class="list_range" width="100">
    <html:text name="acntPlannedFinish"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common3"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATE('acntPlannedFinish')"
                      maxlength="10" 
                      req="true"
                      ondblclick="getDATE(this)"
                      
         />
    <%--html:text styleId="input_common4" fieldtype="dateyyyymmdd" name="acntPlannedFinish" beanName="webVo"  maxlength="10" ondblclick="getDATE(this)"/--%>
    </td>
    <td class="list_range" > <bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingPerMonth"/></td>
    <td class="list_range">
    <html:text styleId="input_common4" name="estManmonth" beanName="webVo" fieldtype="text" req="true" maxlength="11"/>
    </td>
  
  
   

  </tr>
  <tr>
  <td class="list_range" width="250">
  <bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingProjectQuanity"/>
  </td>
  <td class="list_range">
  <html:text styleId="input_common2" name="estSize" beanName="webVo" fieldtype="text"  maxlength="10"/>
  </td>
   <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.MasterData.ProductName"/>
  </td>
  <td class="list_range" colspan="4">
  <html:text styleId="input_common6" name="productName" beanName="webVo" fieldtype="text"  maxlength="25"/>
  </td>
  </tr>
  <tr>
    <td class="list_range" > <bean:message bundle="projectpre" key="projectCode.ProjectData.FactStart"/></td>
    <td class="list_range" width="100">
    <div id="hidDate">
    <html:text name="acntActualStart"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common10"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATEConfirm('acntActualStart')"
                      maxlength="10" 
                      ondblclick="getDATE(this)"
                      
         />
         </div>
    <%--html:text styleId="input_common2" fieldtype="dateyyyymmdd" name="acntActualStart" beanName="webVo"  maxlength="11" ondblclick="getDATE(this)"/--%>
    
    </td>
    <td class="list_range"><bean:message bundle="projectpre" key="projectCode.ProjectData.FactEnd"/> </td>
    <td class="list_range" width="100">
    <html:text name="acntActualFinish"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common3"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATEConfirm('acntActualFinish')"
                      maxlength="10" 
                      ondblclick="getDATE(this)"
                      
         />
    <%--html:text styleId="input_common4" fieldtype="dateyyyymmdd" name="acntActualFinish" beanName="webVo"  maxlength="11" ondblclick="getDATE(this)"/--%>
    </td>
    <td class="list_range"> <bean:message bundle="projectpre" key="projectCode.ProjectData.FactPerMonth"/></td>
    <td class="list_range"><html:text styleId="input_common4" name="actualManmonth" beanName="webVo" fieldtype="text"  maxlength="11"/>
    </td>
  </tr>
  <tr>
  <td class="list_range"><bean:message bundle="projectpre" key="projectCode.ProjectData.Others"/></td>
  <td class="list_range" colspan="6"> 
  <html:textarea name="otherDesc" beanName="webVo" rows="2" styleId="description1" req="false" maxlength="2000"  />
  </td>
  </tr>
 <logic:equal name="showComment" scope="request" value="true">
 <logic:equal name="readOnly" scope="request" value="true">
 <tr>
   <td class="list_range">
   <%--div id="hidComment"--%>
   <bean:message bundle="projectpre" key="customer.ClientRemark"/>
   <%--/div--%>
   </td>
   <td class="list_range" colspan="6">
   <%--div id="hidCommentText"--%>
   <html:textarea name="comment" beanName="webVo" rows="2" styleId="description1" req="false" maxlength="500" readonly="true" />
   <%--/div%--%>
   </td>
  </tr> 
 </logic:equal>
 <logic:equal name="readOnly" scope="request" value="false">
 <tr>
   <td class="list_range">
   <%--div id="hidComment"--%>
   <bean:message bundle="projectpre" key="customer.ClientRemark"/>
   <%--/div--%>
   </td>
   <td class="list_range" colspan="6">
   <%--div id="hidCommentText"--%>
   <html:textarea name="comment" beanName="webVo" rows="2" styleId="description1" req="false" maxlength="500" readonly="false" />
   <%--/div%--%>
   </td>
  </tr> 
 
 </logic:equal>
 </logic:equal>
  
 
</table>
</html:form>

  </body>
  <script language="JavaScript" type="text/JavaScript">
    onInit();
    refreshUp();
</script>
</html>
