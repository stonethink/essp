
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
 
    <style type="text/css">
      #input_common1{width:110}
      #input_common2{width:130}
      #input_common3{width:112}
      #input_common4{width:388}
      #input_field{width:60}
      #description{width:100%}
    </style>
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/humanAllocate.js"></script>
  <script language="JavaScript" type="text/JavaScript">
   function getQueryUserElementArray() {
		var names = new Array(document.getElementById("bdManager"),
		                      document.getElementById("pmName"));
		var loginIds = new Array(document.getElementById("BDMId"),
		                         document.getElementById("PMId"));
		return new Array(names, loginIds);
	}
    function onSearchData(){
    try{
    //alert(document.all.applicationDateBegin.value);
      var beginDate = document.all.applicationDateBegin.value;
      var endDate = document.all.applicationDateEnd.value;
      //alert(beginDate+"~"+endDate);
      if(beginDate!=""&&endDate!=""&&beginDate>endDate){
         alert("<bean:message bundle="application" key="global.fill.dateError"/>");
         return;
      }
  
      AfProjectQuery.action = "<%=contextPath%>/project/query/projectQueryList.do";
      AfProjectQuery.target = "recordList";
      AfProjectQuery.submit();
      }catch(e){return;}
    }
    function onOpenData(){
       var height = 400;
       var width = 850; 
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
	   var url='<%=contextPath%>/projectpre/projectcode/codequery/SelectViewColumns.jsp';
	   var windowTitle="";
       window.open(url,windowTitle,option);
    }
    function onInit(){
      try{
       var options = document.all.supportLanguge.options;
       var OriginalLanguage = "<bean:message bundle="projectpre" key="projectCode.LanguageSupport.cardTitle.OriginalLanguage"/>";
       var TranslationLanguage = "<bean:message bundle="projectpre" key="projectCode.LanguageSupport.cardTitle.Translation"/>"; 
     for(i=1;i<options.length;i++){
       var tempText = options[i].innerHTML;
       if(tempText.indexOf("OriginalLanguage")>=0){
           options[i].innerHTML=options[i].innerHTML.replace("OriginalLanguage",OriginalLanguage);
           
        }else if(tempText.indexOf("TranslationLanguage")>=0){
           options[i].innerHTML=options[i].innerHTML.replace("TranslationLanguage",TranslationLanguage);
   
       }
    
     }
     }catch(e){}
    
    }
    function allocate(personType){
     try{
    param = new HashMap();
    var result = allocSingleInAD(param,"","","")
    if(param!=""&&personType=="AP"){
       document.all.applicant.value=param.values()[0].name;
       document.forms[0].applicantId.value=param.values()[0].loginId;
    }
    if(param!=""&&personType=="BD"){
       document.all.bdManager.value=param.values()[0].name;
       document.forms[0].BDMId.value=param.values()[0].loginId;
    }
    if(param!=""&&personType=="PM"){
       document.all.pmName.value=param.values()[0].name;
       document.forms[0].PMId.value=param.values()[0].loginId;
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
      	document.all.billType.value="";
       } else { 
       	obj.value='0';
      	document.all.billType.value=null;
      	hidBillTitle.style.visibility="hidden";
      	hidBillContent.style.visibility="hidden";
       }
     }
  </script>
  </head>
  <body >
  <html:form id="AfProjectQuery" action="" method="post" >
   <html:hidden name="showApplicant" beanName="webVo" value="1111111"/>
   <html:hidden name="showAchieveBelong" beanName="webVo" value="22222"/>
   <html:hidden name="applicantId" beanName="webVo" />
   <html:hidden name="BDMId" beanName="webVo" />
   <html:hidden name="PMId" beanName="webVo" />
   
<table width="800" border="0" style="padding-left:8px">
  <tr> 
    <td class="list_range" width="130"><bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectCode"/></td>
    <td class="list_range" width="50"> <html:text styleId="input_common2" fieldtype="text" name="acntId" beanName=""  maxlength="10"/> 
    </td>
   
    <td class="list_range"   colspan="2">
    <html:checkbox styleId="CheckBox" name="includeClosedProject" beanName="" checkedValue="true" uncheckedValue ="" defaultValue="" styleClass="CheckBoxStyle"/>
      <bean:message bundle="projectpre" key="projectCode.QueryProject.ExistProjectCode"/></td>
      
      <td class="list_range" width="120"> 
      <bean:message bundle="projectpre" key="projectCode.MasterData.ProjectManager"/>
      </td>
      	<td class="list_range" width="80">
					<html:text name="pmName" 
					           beanName="" 
					           fieldtype="text" 
					           styleId="input_common1" 
					           prev="dueDate" 
					           req="false" 
					           imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
					           imageWidth="18" 
					           imageOnclick="allocate('PM')"  />
				</td>
      </tr>
     
      <tr>
    <td class="list_range" >
    <bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectCodeApplyDate"/>
    &nbsp;
    <bean:message bundle="projectpre" key="projectCode.QueryProject.Start"/>
    </td>
    <td class="list_range" width="50">
    <html:text name="applicationDateBegin"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common3"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATE('applicationDateBegin')"
                      maxlength="10" 
                      ondblclick="getDATE(this)"
                      
         />
    <%--html:text styleId="input_common2" fieldtype="dateyyyymmdd" name="applicationDateBegin" beanName="" readonly="false" ondblclick="getDATE(this)" /--%>
    </td>
    <td class="list_range" width="120"> 
       <bean:message bundle="projectpre" key="projectCode.QueryProject.End"/>
    </td>
    <td class="list_range" width="30">
    <html:text name="applicationDateEnd"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common3"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATE('applicationDateEnd')"
                      maxlength="10" 
                      ondblclick="getDATE(this)"
                      
         />
    <%--html:text styleId="input_common2" fieldtype="dateyyyymmdd" name="applicationDateEnd" beanName="" readonly="false" ondblclick="getDATE(this)" /--%>
    </td>
    <td class="list_range" width="120"> 
      <bean:message bundle="projectpre" key="projectCode.QueryProject.MainPropjectCode"/>
    </td>
    <td class="list_range" width="100" ><html:text styleId="input_common2" name="relParentId" beanName="" fieldtype="text"   maxlength="20"/>
    </td>
    <td class="list_range" width="30"></td>
  </tr>
  
			<tr>
				<td class="list_range" >
					<bean:message bundle="projectpre" key="projectCode.QueryProject.Applicant"/>
				</td>
				<td class="list_range" width="100">
					<html:text name="applicant" beanName=""  fieldtype="text"  styleId="input_common2" maxlength="20"/>
				</td>
				
				<td class="list_range"  >
					<bean:message bundle="projectpre" key="projectCode.QueryProject.AcheiveBelong"/>
				</td>
				<td class="list_range"  width="100">
					<html:text styleId="input_common2" name="achieveBelong" beanName="" fieldtype="text"  maxlength="20" />
				</td>
				<td class="list_range" width="60">
					<bean:message bundle="projectpre" key="projectCode.QueryProject.DivisionManager"/>
				</td>
				<td class="list_range" width="80">
					<html:text name="bdManager" 
					           beanName="" 
					           fieldtype="text" 
					           styleId="input_common1" 
					           prev="dueDate" 
					           req="false" 
					           imageSrc="<%=contextPath+"/image/humanAllocate.gif"%>" 
					           imageWidth="18" 
					           imageOnclick="allocate('BD')"  />
				</td>
			</tr>
			<tr>
			<td class="list_range">
			<bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingStart"/>
			</td>
			<td class="list_range">
			<html:text name="acntPlannedStart"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common3"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATE('acntPlannedStart')"
                      maxlength="10" 
                      ondblclick="getDATE(this)"
            />
			</td>
			<td class="list_range">
			<bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingEnd"/>
			</td>
			<td class="list_range">
			 <html:text name="acntPlannedFinish"
                      beanName="webVo"
                      fieldtype="dateyyyymmdd"
                      styleId="input_common3"
                      imageSrc="<%=contextPath+"/image/cal.png"%>"
                      imageWidth="18"
                      imageOnclick="getMyDATE('acntPlannedFinish')"
                      maxlength="10" 
                      ondblclick="getDATE(this)"
            />
			</td>
			<td class="list_range">
			<bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectExecuteFocus"/>
			</td>
			<td class="list_range">
			 <html:select name="execSite" styleId="input_common2" beanName=""  req="false">
	                <logic:present name="webVo" property="execSiteList">
	                 <html:optionsCollection name="webVo" property="execSiteList"></html:optionsCollection>
	                </logic:present>
	             </html:select>
			</td>
			</tr>
			<tr>
			<td class="list_range">
				<bean:message bundle="projectpre" key="projectCode.MasterData.PrimaveraAdapted"/>
			</td>
			<td class="list_range">
			<html:select name="primaveraAdapted" styleId="input_common2" beanName=""  req="false">
				<option value="">--please select--</option>
				<option value="1">Y</option>
				<option value="0">N</option>
			</html:select>
				<%--html:checkbox checkedValue="1" defaultValue="0" uncheckedValue="0" beanName="" name="primaveraAdapted"></html:checkbox--%>
			</td>
			<td class="list_range">
				<bean:message bundle="projectpre" key="projectCode.MasterData.BillingBasis"/>
			</td>
			<td class="list_range">
			<html:select name="billingBasis" styleId="input_common2" beanName=""  req="false">
				<option value="">--please select--</option>
				<option value="1">Y</option>
				<option value="0">N</option>
			</html:select>
				<%--html:checkbox checkedValue="1" defaultValue="0" uncheckedValue="0" beanName="" name="billingBasis"></html:checkbox--%>
			</td>
			<td class="list_range">
			<bean:message bundle="projectpre" key="projectCode.MasterData.BizProperty"/>
			</td>
			<td class="list_range">
			 <html:select name="bizProperty" styleId="input_common2" beanName=""  req="false">
	                <logic:present name="webVo" property="bizPropertyList">
	                 <html:optionsCollection name="webVo" property="bizPropertyList"></html:optionsCollection>
	                </logic:present>
	             </html:select>
			</td>
			</tr>
			<tr>
			<td class="list_range">
			<div id="hidBillTitle">
			<bean:message bundle="projectpre" key="projectCode.MasterData.BillType"/>
			</div>
			</td>
			<td class="list_range">
			<div id="hidBillContent">
			 <html:select name="billType" styleId="input_common2" beanName=""  req="false">
	                <logic:present name="webVo" property="billTypeList">
	                 <html:optionsCollection name="webVo" property="billTypeList"></html:optionsCollection>
	                </logic:present>
	             </html:select>
	        </div>
			</td>
			<td class="list_range">
			<bean:message bundle="projectpre" key="projectCode.Technical.ProjectType"/>
			</td>
			<td class="list_range" colspan="3">
			 <html:select name="relPrjType" styleId="input_common2" beanName=""  req="false">
	                <option value="">--please select--</option>
					<option value="Master">Master</option>
					<option value="Sub">Sub</option>
					<option value="Related">Related</option>
					<option value="Finance">Finance</option>
	             </html:select>
			</td>
			</tr>
			<tr>
				<td class="list_range" >
					<bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectExecuteUnit"/>
				</td>
				<td class="list_range" colspan="3">
				 <html:select name="execUnitId" styleId="input_common4" beanName=""  req="false">
	                <logic:present name="webVo" property="execUnitList">
	                 <html:optionsCollection name="webVo" property="execUnitList"></html:optionsCollection>
	                </logic:present>
	             </html:select>
					<!-- html:text styleId="input_common2" name="execUnitId" beanName="" fieldtype="text" maxlength="20" /-->
				</td>
				
				
				<td class="list_range" >
					<bean:message bundle="projectpre" key="projectCode.QueryProject.CostBelongUnit"/>
				</td>
				<td class="list_range" >
					<html:select name="costAttachBd"  styleId="input_common2" beanName=""  req="false" >
					  <logic:present name="webVo" property="costAttachBdList">
	                     <html:optionsCollection name="webVo" property="costAttachBdList"></html:optionsCollection>
	                </logic:present>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="list_range" >
					<bean:message bundle="projectpre" key="projectCode.QueryProject.BusinessResource"/>
				</td>
				<td class="list_range">
					<html:select name="bizSource"  styleId="input_common2" beanName=""  req="false">
					  <logic:present name="webVo" property="bizSourceList">
	                     <html:optionsCollection name="webVo" property="bizSourceList"></html:optionsCollection>
	                  </logic:present>
					</html:select>
				</td>
				
				<td class="list_range" >
					<bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ClientInformation"/>
				</td>
				<td class="list_range" >
					<html:text styleId="input_common2" name="customerId" beanName="" fieldtype="text" maxlength="10" />
				</td>
				<td class="list_range" >
					<bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectAttribute"/>
				</td>
				<td class="list_range">
					<html:select name="acntAttribute"  styleId="input_common2" beanName=""  req="false">
					  <logic:present name="webVo" property="acntAttributeList">
	                     <html:optionsCollection name="webVo" property="acntAttributeList"></html:optionsCollection>
	                  </logic:present>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="list_range" >
					<bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectType"/>
				</td>
				<td class="list_range">
					<html:select name="acntType"  styleId="input_common2" beanName=""  req="false">
					  <logic:present name="webVo" property="acntTypeList">
	                     <html:optionsCollection name="webVo" property="acntTypeList"></html:optionsCollection>
	                  </logic:present>
					</html:select>
				</td>
				
				<td class="list_range"  >
					<bean:message bundle="projectpre" key="projectCode.QueryProject.ProductType"/>
				</td>
				<td class="list_range">
					<html:select name="productType"  styleId="input_common2" beanName=""  req="false">
					  <logic:present name="webVo" property="productTypeList">
	                     <html:optionsCollection name="webVo" property="productTypeList"></html:optionsCollection>
	                  </logic:present>
					</html:select>
				</td>
				<td class="list_range" >
					<bean:message bundle="projectpre" key="projectCode.QueryProject.ProductAttribute"/>
				</td>
				<td class="list_range">
					<html:select name="productProperty"  styleId="input_common2" beanName=""  req="false">
					  <logic:present name="webVo" property="productPropertyList">
	                     <html:optionsCollection name="webVo" property="productPropertyList"></html:optionsCollection>
	                  </logic:present>
					</html:select>
				</td>
			</tr>
			<tr>
				<td class="list_range" >
					<bean:message bundle="projectpre" key="projectCode.QueryProject.WorkItem"/>
				</td>
				<td class="list_range">
					<html:select name="workItem"  styleId="input_common2" beanName=""  req="false">
					  <logic:present name="webVo" property="workItemList">
	                     <html:optionsCollection name="webVo" property="workItemList"></html:optionsCollection>
	                  </logic:present>
					</html:select>
				</td>
			
				<td class="list_range"  >
					<bean:message bundle="projectpre" key="projectCode.QueryProject.TechnicalArea"/>
				</td>
				<td class="list_range">
					<html:select name="technicalArea"  styleId="input_common2" beanName=""  req="false">
					  <logic:present name="webVo" property="technicalAreaList">
	                     <html:optionsCollection name="webVo" property="technicalAreaList"></html:optionsCollection>
	                  </logic:present>
					</html:select>
				</td>
				<td class="list_range" >
					<bean:message bundle="projectpre" key="projectCode.QueryProject.SupportLanguge"/>
				</td>
				<td class="list_range">
					<html:select name="supportLanguge"  styleId="input_common2" beanName=""  req="false">
					  <logic:present name="webVo" property="supportLangugeList">
	                     <html:optionsCollection name="webVo" property="supportLangugeList"></html:optionsCollection>
	                  </logic:present>
					</html:select>
				</td>
			</tr>
		</table>
		</html:form>
		<SCRIPT language="JavaScript" type="text/JavaScript">
		    onInit();
		</SCRIPT>
  </body>
</html>

