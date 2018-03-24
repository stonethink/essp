<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ include file="/layout/dynamicLoginId.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>


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
      #input_common5{width:687}
      #input_common6{width:400}
      #input_common7{width:90}
      #input_common8{width:160}
      #input_common9{width:161}
      #input_common10{width:102}
      #input_field{width:160}
      #description{width:687}
      #description1{width:630}
      #input_sp{width:140;background-color:#FFFACD}
    </style>
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/humanAllocate.js"></script>
  <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/queryAccount.js"></script>
  <%
  String opType = null;
  boolean isError = false;
  if(request.getAttribute("opType") != null) {
  		opType = (String)request.getAttribute("opType");
  }
  if(request.getAttribute("Message") != null) {
  	    isError = true;
  }
  %>
  <script language="JavaScript" type="text/JavaScript">
    function init() {
    	var opType = "<%=opType%>";
    	var isError = <%=isError%>;
    	if(opType == null || opType == "null")return;
    	if(opType == "save" && isError) {
    	  var msgBeforeClose = "<%=request.getAttribute("Message")%>";
         if(msgBeforeClose!="null" && msgBeforeClose!=null && msgBeforeClose!=""){
            alert("<bean:message bundle="application" key="global.carry.fail"/>");
         }
    	} else if(opType == "save" && !isError) {
    		alert("<bean:message bundle="application" key="global.update.success"/>");
    	}
    }

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
    //保存
    
    function onSaveData(){
    try{
      if(document.all.acntId.value==null||document.all.acntId.value==""){
           alert("<bean:message bundle="application" key="global.fill.select"/>");
           submit_flug=false;
           return;
      }
       
      var form=document.forms[0];
      var costAttachBd = document.all.costAttachBd.value;
      var execUnitName = document.all.execUnitName.value;
      if(costAttachBd=="-1"){
      	alert("<bean:message bundle="application" key="global.confirm.require"/>");
      	document.all.costAttachBd.focus();
      	submit_flug=false;
      	return;
      }
      if(execUnitName=="-1"){
      	alert("<bean:message bundle="application" key="global.confirm.require"/>");
      	
      	document.all.execUnitName.focus();
      	submit_flug=false;
      	return;
      }
      if(!onCheckDate()){
      	 submit_flug=false;
         return;
      }
      
      if(!checkReq(form)){
           	submit_flug=false;
            return;
      }
      if(!confirm("<bean:message bundle="application" key="global.confirm.update"/>")){
          submit_flug=false;
          return;
      }
       form.submit();
       
       }catch(e){}
    }
    function onReopenData() {
    if(document.all.acntId.value==null||document.all.acntId.value==""){
           alert("<bean:message bundle="application" key="global.fill.select"/>");
           submit_flug=false;
           return;
      }
    	if(document.all.acntStatus.value == "N"){
    		alert("<bean:message bundle="application" key="global.update.noReopen"/>");
    		submit_flug=false;
    		return;
    	}
    	if(!confirm("<bean:message bundle="application" key="global.confirm.reopen"/>")){
          submit_flug=false;
          return;
      	}
    	var acntId = document.all.acntId.value;
    	window.location="<%=contextPath%>/project/edit/reopenProject.do?acntId="+acntId;
    }
    //此方法用于检查必填项
     function checkReq(form){
	     if(!submitForm(form)){
    	 	  return false;
	    } else {
              return true;	
	    }
     }
    
    //复制专案功能
    
    function onSelectData() {
    try{
     var paramMap = new HashMap();
     var personMap = new HashMap();
     paramMap.put("is_acnt",new Array("1",""));
     var result = null;
     result = queryAccount(paramMap);
   if(result!=null){
     var rid = result.rid;
     window.location="<%=contextPath%>/project/edit/selectProjectEdit.do?rid="+rid;
     }
     }catch(e){}
    
    }
   
    //点击找人控件所执行的操作
    
    function allocate(personType){
    try{
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
     function getMyDATE(dateName){
     try{
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
  <html:form id="ProjectEdit" action="/project/edit/projectEdit" method="post" >
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
  <html:hidden name="parentExecSite"/>
  
  <table width="790" border="0" >
 
  <tr><td class="list_range" width="80"><bean:message bundle="projectpre" key="projectCode.MasterData.ProjectCode"/></td>
  <td class="list_range" width="50"><html:text styleId="input_common7" name="acntId" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/></td>
  <td class="list_range"></td>
  <td class="list_range" width="50"><bean:message bundle="projectpre" key="projectCode.MasterData.Applicant"/></td>
  <td class="list_range" width="50"><html:text styleId="input_common7" name="applicantName" beanName="webVo" fieldtype="text"  readonly="true"  maxlength="25"/></td>
  <td class="list_range"></td>
  <td class="list_range" width="70"><bean:message bundle="projectpre" key="projectCode.MasterData.StatusCode"/></td>
  <td class="list_range" ><html:text styleId="input_common7" name="acntStatus" beanName="webVo" fieldtype="text" readonly="true"  maxlength="25"/></td>
  </tr>
  </table>
  
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
    <td class="list_range" width="46">&nbsp;</td>
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
    <td class="list_range" width="120">
        <html:select name="achieveBelong" styleId="input_common2" beanName="webVo"  req="true" >
           
            <html:optionsCollection name="webVo" property="achieveBelongList"/>
        </html:select>
    </td>
    <td class="list_range" width="100"><bean:message bundle="projectpre" key="projectCode.BD" /></td>
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
    <td class="list_range" ><bean:message bundle="projectpre" key="projectCode.MasterData.Leader"/> </td>
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
                 req="true"
                 type="server.essp.projectpre.service.accountapplication.ExecUnitImpl" 
                 style="width:400px;background-color:#FFFACD"
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
  <td class="list_range" width="125">
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
    <html:text name="acntActualStart"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common10"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATE('acntActualStart')"
                      maxlength="10" 
                      ondblclick="getDATE(this)"
                      
         />
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
                      imageOnclick="getMyDATE('acntActualFinish')"
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
    
        <td class="list_range" > 
    <bean:message bundle="projectpre" key="projectCode.MasterData.BillingBasis"/>
    </td>
  <td class="list_range" >
         <input type="checkbox" id="billingBasis" name="billingBasis" onclick="onClickChk(this)" <bean:write name="webVo" property="chkBillingBasis"/>/>
      </td>
      <td class="list_range" > 
    <div id="hidBillTitle">
      	<bean:message bundle="projectpre" key="projectCode.MasterData.BillType"/>
      	</div>
    </td>
    <td class="list_range" >
    <div id="hidBillContent">
    <html:select name="billType" styleId="input_sp" beanName="webVo"  >
            <html:optionsCollection name="webVo" property="billTypeList"/>
        </html:select> 
    </div>
        </td>
  </tr>
  <tr>
  <td class="list_range" >
    <bean:message bundle="projectpre" key="projectCode.MasterData.PrimaveraAdapted"/>
    </td>
    <td class="list_range" >
    <html:checkbox styleId="CheckBox" name="primaveraAdapted" beanName="webVo" checkedValue="1" uncheckedValue ="0" defaultValue="0" styleClass="CheckBoxStyle"/>
        </td>
  <td class="list_range">
       <bean:message bundle="projectpre" key="projectCode.MasterData.BizProperty"/>
    </td>
    <td class="list_range" >
      <html:select name="bizProperty" styleId="input_common4" beanName="webVo" req="true" >
            <html:optionsCollection name="webVo" property="bizPropertyList"/>
        </html:select>
    </td>
  </tr>
  <tr>
  <td class="list_range"><bean:message bundle="projectpre" key="projectCode.ProjectData.Others"/></td>
  <td class="list_range" colspan="6"> 
  <html:textarea name="otherDesc" beanName="webVo" rows="2" styleId="description1" req="false" maxlength="2000"  />
  </td>
  </tr>
</table>
</html:form>

  </body>
  
 <script language="JavaScript" type="text/JavaScript">
 init();
 onClickChk(document.all.billingBasis);
 </script>
</html>
