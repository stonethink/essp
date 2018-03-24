
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>

<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
         <tiles:insert page="/layout/head.jsp">
            <tiles:put name="title" value="Project Code Apply"/>
            <tiles:put name="jspName" value=""/>
        </tiles:insert>


<script language="JavaScript" type="text/javascript">
    var leader="<bean:message bundle="projectpre" key="projectCode.MasterData.Leader"/>";
    var divisionManager="<bean:message bundle="projectpre" key="projectCode.MasterData.DivisionManager"/>";
    var attachment="<bean:message bundle="projectpre" key="projectCode.MasterData.Attachment"/>";
    var applicant="<bean:message bundle="projectpre" key="projectCode.MasterData.Applicant"/>";
    var achieveBelong="<bean:message bundle="projectpre" key="projectCode.ApplyRecordList.AchieveBelong"/>";
    var projectManager="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectManager"/>";
    var niceName="<bean:message bundle="projectpre" key="projectCode.MasterData.NiceName"/>";
    var timeCardSigner="<bean:message bundle="projectpre" key="projectCode.MasterData.TimeCardSigner"/>";
    var projectName="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectName"/>";
    var productName="<bean:message bundle="projectpre" key="projectCode.MasterData.ProductName"/>";
    var projectDesc="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectDesc"/>";
    var projectExecUnit="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectExecUnit"/>";
    var projectExecuteSite="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectExecuteSite"/>";
    var costBelong="<bean:message bundle="projectpre" key="projectCode.MasterData.CostBelong"/>";
    var sales="<bean:message bundle="projectpre" key="projectCode.MasterData.Sales"/>";
    var customerServiceManager="<bean:message bundle="projectpre" key="projectCode.MasterData.CustomerServiceManager"/>";      
    var engageManager="<bean:message bundle="projectpre" key="projectCode.MasterData.EngageManager"/>";
    var bizSource="<bean:message bundle="projectpre" key="projectCode.MasterData.BizSource"/>";
    var parentProject="<bean:message bundle="projectpre" key="projectCode.MasterData.ParentProject"/>";
    var contractAcnt="<bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ProjectBelong"/>";
    var customerInfo="<bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ClientInformation"/>";
    var customerID="<bean:message bundle="projectpre" key="projectCode.MasterData.CustomerNo"/>";
    var short_="<bean:message bundle="projectpre" key="customer.ClientShortName"/>";
    var nameCn="<bean:message bundle="projectpre" key="customer.ChineseFullName"/>";
    var nameEn="<bean:message bundle="projectpre" key="customer.EnglishFullName"/>";
    var groupId="<bean:message bundle="projectpre" key="customer.GroupNo"/>";
    var country="<bean:message bundle="projectpre" key="customer.ClientCountryCode"/>";
    var cusDesc="<bean:message bundle="projectpre" key="customer.CustomerDes"/>";
    var acntId="<bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectCode"/>";
    var primaveraAdapted="<bean:message bundle="projectpre" key="projectCode.MasterData.PrimaveraAdapted"/>";
    var billingBasis="<bean:message bundle="projectpre" key="projectCode.MasterData.BillingBasis"/>";
    var relPrjType="<bean:message bundle="projectpre" key="projectCode.Technical.ProjectType"/>";
     
    var projectType="<bean:message bundle="projectpre" key="projectCode.Technical.ProjectType"/>";
    var productType="<bean:message bundle="projectpre" key="projectCode.Technical.ProductType"/>";
    var productAttribute="<bean:message bundle="projectpre" key="projectCode.Technical.ProductProperty"/>";
    var workItem="<bean:message bundle="projectpre" key="projectCode.Technical.Project"/>";            
    var technicalDomain="<bean:message bundle="projectpre" key="projectCode.Technical.TechnicalArea"/>";
    var supportLanguage="<bean:message bundle="projectpre" key="projectCode.Technical.SupportLanguage"/>";
    var developEnvironment="<bean:message bundle="projectpre" key="projectCode.Technical.DevelopEnvironment"/>";
    var translatePublishType="<bean:message bundle="projectpre" key="projectCode.Technical.TranslatePublishType"/>";
    var hardReq="<bean:message bundle="projectpre" key="projectCode.Technical.HardRequirement"/>";
    var softReq="<bean:message bundle="projectpre" key="projectCode.Technical.SoftRequirement"/>";
    var originalLanguage="<bean:message bundle="projectpre" key="projectCode.LanguageSupport.cardTitle.OriginalLanguage"/>";
    var translationLanguage="<bean:message bundle="projectpre" key="projectCode.LanguageSupport.cardTitle.Translation"/>";
    var jobSystem="<bean:message bundle="projectpre" key="projectCode.Technical.JobSystem"/>";
    var dataBase="<bean:message bundle="projectpre" key="projectCode.Technical.DataBase"/>";
    var tool="<bean:message bundle="projectpre" key="projectCode.Technical.Tool"/>";
    var netWork="<bean:message bundle="projectpre" key="projectCode.Technical.Network"/>";
    var programLanguage="<bean:message bundle="projectpre" key="projectCode.Technical.ProgramLangage"/>";
    var others="<bean:message bundle="projectpre" key="projectCode.Technical.Others"/>";
    
    
    
     
    var projectScheduleAnticipate="<bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ProjectScheduleAnticipate"/>";
    var projectScheduleFact="<bean:message bundle="projectpre" key="projectCode.SelectViewColumns.ProjectScheduleFact"/>";
    var anticipateWorkQuantity="<bean:message bundle="projectpre" key="projectCode.SelectViewColumns.AnticipateWorkQuantity"/>";
    var others="<bean:message bundle="projectpre" key="projectCode.ProjectData.Others"/>";
    var estManmonth="<bean:message bundle="projectpre" key="projectCode.SelectViewColumns.AnticipateManMonth"/>";
         
     
    
    
    
    
   

    function setColumnTitle(){
     try{
      var currentRow;
      var rows=document.getElementById("QueryRecordListTable_table").rows;
      var cells=document.getElementById("QueryRecordListTable_table").rows[0].cells;
      
     for(var i=0;i<cells.length;i++){
       var tempText = cells[i].innerHTML;
       if(tempText.indexOf("Attachment")>=0){
           cells[i].innerHTML=cells[i].innerHTML.replace("Attachment",attachment);
           cells[i].title = cells[i].title.replace("Attachment",attachment);  
        }else if(tempText.indexOf("Applicant")>=0){
           cells[i].innerHTML=cells[i].innerHTML.replace("Applicant",applicant);
           cells[i].title = cells[i].title.replace("Applicant",applicant);  
        }else if(tempText.indexOf("Achieve Belong")>=0){
           cells[i].innerHTML=cells[i].innerHTML.replace("Achieve Belong",achieveBelong);
           cells[i].title = cells[i].title.replace("Achieve Belong",achieveBelong);
        }else if(tempText.indexOf("Leader")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Leader",leader);
          cells[i].title = cells[i].title.replace("Leader",leader);
        }else if(tempText.indexOf("Division Manager")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Division Manager",divisionManager);
          cells[i].title = cells[i].title.replace("Division Manager",divisionManager)
        }else if(tempText.indexOf("Nick Name")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Nick Name",niceName);
          cells[i].title = cells[i].title.replace("Nick Name",niceName);
        }else if(tempText.indexOf("Project Manager")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Project Manager",projectManager);
          cells[i].title = cells[i].title.replace("Project Manager",projectManager);
        }else if(tempText.indexOf("Time Card Signer")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Time Card Signer",timeCardSigner);
          cells[i].title = cells[i].title.replace("Time Card Signer",timeCardSigner);
        }else if(tempText.indexOf("Project Name")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Project Name",projectName);
          cells[i].title = cells[i].title.replace("Project Name",projectName);
        }else if(tempText.indexOf("RelPrjType")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("RelPrjType",relPrjType);
          cells[i].title = cells[i].title.replace("RelPrjType",relPrjType);
          
         
          
        }else if(tempText.indexOf("Product Name")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Product Name",productName);
          cells[i].title = cells[i].title.replace("Product Name",productName);
        }else if(tempText.indexOf("Project Desc")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Project Desc",projectDesc);
          cells[i].title = cells[i].title.replace("Project Desc",projectDesc);
        }else if(tempText.indexOf("Project Exec Unit")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Project Exec Unit",projectExecUnit);
          cells[i].title = cells[i].title.replace("Project Exec Unit",projectExecUnit);
        }else if(tempText.indexOf("Project Execute Site")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Project Execute Site",projectExecuteSite);
          cells[i].title = cells[i].title.replace("Project Execute Site",projectExecuteSite);
        }else if(tempText.indexOf("Cost Belong")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Cost Belong",costBelong);
          cells[i].title = cells[i].title.replace("Cost Belong",costBelong);
        }else if(tempText.indexOf("Sales")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Sales",sales);
          cells[i].title = cells[i].title.replace("Sales",sales);
        }else if(tempText.indexOf("Customer Service Manager")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Customer Service Manager",customerServiceManager);
          cells[i].title = cells[i].title.replace("Customer Service Manager",customerServiceManager);
        }else if(tempText.indexOf("Engage Manager")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Engage Manager",engageManager);
          cells[i].title = cells[i].title.replace("Engage Manager",engageManager);
        }else if(tempText.indexOf("Biz Source")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Biz Source",bizSource);
          cells[i].title = cells[i].title.replace("Biz Source",bizSource);
        }else if(tempText.indexOf("Parent Project")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Parent Project",parentProject);
          cells[i].title = cells[i].title.replace("Parent Project",parentProject);
        }else if(tempText.indexOf("Contract Project")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Contract Project",contractAcnt);
          cells[i].title = cells[i].title.replace("Contract Project",contractAcnt);
        }else if(tempText.indexOf("Customer Info")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Customer Info",customerInfo);
          cells[i].title = cells[i].title.replace("Customer Info",customerInfo);
          for(j=1;j<rows.length;j++){
          rows[j].cells[i].title=rows[j].cells[i].title.replace("CustomerID",customerID);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("NameCn",nameCn);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("NameEn",nameEn);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("GroupId",groupId);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("Country",country);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("CusDesc",cusDesc);
          
          }
        }else if(tempText.indexOf("Acnt Id")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Acnt Id",acntId);
          cells[i].title = cells[i].title.replace("Acnt Id",acntId);
        
        }else if(tempText.indexOf("Project Type")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Project Type",projectType);
          cells[i].title = cells[i].title.replace("Project Type",projectType);
        }else if(tempText.indexOf("Product Type")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Product Type",productType);
          cells[i].title = cells[i].title.replace("Product Type",productType);
        }else if(tempText.indexOf("Product Attribute")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Product Attribute",productAttribute);
          cells[i].title = cells[i].title.replace("Product Attribute",productAttribute);
        }else if(tempText.indexOf("Work Item")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Work Item",workItem);
          cells[i].title = cells[i].title.replace("Work Item",workItem);
        }else if(tempText.indexOf("Technical Domain")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Technical Domain",technicalDomain);
          cells[i].title = cells[i].title.replace("Technical Domain",technicalDomain);
        }else if(tempText.indexOf("Support Language")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Support Language",supportLanguage);
          cells[i].title = cells[i].title.replace("Support Language",supportLanguage);
          for(j=1;j<rows.length;j++){
          var str=rows[j].cells[i].innerHTML;
          if(str.indexOf("OriginalLanguage")>=0){
          rows[j].cells[i].innerHTML=rows[j].cells[i].innerHTML.replace("OriginalLanguage",originalLanguage);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("OriginalLanguage",originalLanguage);
          }
          if(str.indexOf("TranslationLanguage")>0){
          rows[j].cells[i].innerHTML=rows[j].cells[i].innerHTML.replace("TranslationLanguage",translationLanguage);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("TranslationLanguage",translationLanguage);
          }
          }
        }else if(tempText.indexOf("Develop Environment")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Develop Environment",developEnvironment);
          cells[i].title = cells[i].title.replace("Develop Environment",developEnvironment);
          for(j=1;j<rows.length;j++){
          var str=rows[j].cells[i].innerHTML;
          if(str.indexOf("JobSystem")>=0){
          rows[j].cells[i].innerHTML=rows[j].cells[i].innerHTML.replace("JobSystem",jobSystem);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("JobSystem",jobSystem);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("DataBase",dataBase);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("Tool",tool);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("NetWork",netWork);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("ProgramLanguage",programLanguage);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("Others",others);
          }
          }
        }else if(tempText.indexOf("Translate Publish Type")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Translate Publish Type",translatePublishType);
          cells[i].title = cells[i].title.replace("Translate Publish Type",translatePublishType);
           for(j=1;j<rows.length;j++){
          var str=rows[j].cells[i].innerHTML;
          if(str.indexOf("JobSystem")>=0){
          rows[j].cells[i].innerHTML=rows[j].cells[i].innerHTML.replace("JobSystem",jobSystem);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("JobSystem",jobSystem);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("DataBase",dataBase);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("Tool",tool);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("NetWork",netWork);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("ProgramLanguage",programLanguage);
          rows[j].cells[i].title=rows[j].cells[i].title.replace("Others",others);
          }
          }
        }else if(tempText.indexOf("HardReq")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("HardReq",hardReq);
          cells[i].title = cells[i].title.replace("HardReq",hardReq);
        }else if(tempText.indexOf("SoftReq")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("SoftReq",softReq);
          cells[i].title = cells[i].title.replace("SoftReq",softReq);
 

        }else if(tempText.indexOf("Project Schedule Anticipate")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Project Schedule Anticipate",projectScheduleAnticipate);
          cells[i].title = cells[i].title.replace("Project Schedule Anticipate",projectScheduleAnticipate);
        }else if(tempText.indexOf("Project Schedule Fact")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Project Schedule Fact",projectScheduleFact);
          cells[i].title = cells[i].title.replace("Project Schedule Fact",projectScheduleFact);
        }else if(tempText.indexOf("Anticipate Work Quantity")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Anticipate Work Quantity",anticipateWorkQuantity);
          cells[i].title = cells[i].title.replace("Anticipate Work Quantity",anticipateWorkQuantity);
        }else if(tempText.indexOf("Others")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("Others",others);
          cells[i].title = cells[i].title.replace("Others",others);
        }else if(tempText.indexOf("EstManmonth")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("EstManmonth",estManmonth);
          cells[i].title = cells[i].title.replace("EstManmonth",estManmonth);
        } else if(tempText.indexOf("PrimaveraAdapted")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("PrimaveraAdapted",primaveraAdapted);
          cells[i].title = cells[i].title.replace("PrimaveraAdapted",primaveraAdapted);
        } else if(tempText.indexOf("BillingBasis")>=0){
          cells[i].innerHTML=cells[i].innerHTML.replace("BillingBasis",billingBasis);
          cells[i].title = cells[i].title.replace("BillingBasis",billingBasis);
        }
     
        
      }
      }catch(e){}
      
    }
     function rowOnClick(rowObj) {
    try{
	if(rowObj==null){
		
	} else {
	currentRow=rowObj;
	}
	}catch(e){}
   }
 //当进入此页时，自动选中第一行，并国际化表头
	function autoClickFirstRow(){
	   try{
	    var table=document.getElementById("QueryRecordListTable_table");
		var thead_length=table.tHead.rows.length;
		var tds=table.rows[thead_length-1];
		var cells=tds.cells;
		 var firstRow=table.rows[thead_length];
		 rowOnClick(firstRow);
		disableBtn();
	    if(table.rows.length>thead_length){
	       //如果有数据
		   onChangeBackgroundColor(firstRow);
	    } 
	    }catch(e){}
	}
	function disableBtn(){
	var rowObj = currentRow;
	  try{
	   var table=document.getElementById("QueryRecordListTable_table");
	   var thead_length=table.tHead.rows.length;
	   var firstRow=table.rows[thead_length];
	   //alert(firstRow);
	   if(firstRow==null){
	      window.parent.disBtn.disabled=true;
	      window.parent.expBtn.disabled=true;
	   }else{
	     window.parent.disBtn.disabled=false;
	     window.parent.expBtn.disabled=false;
	   }
	   }catch(e){}
	}
	function onExportData(){
	try{
	   //var acntId = currentRow.selfproperty;
	  // var height = 250;
       //var width = 420; 
      // var topis = (screen.height - height) / 2;
      // var leftis = (screen.width - width) / 2;
     //  var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no";
	 //  var url='<%=contextPath%>/project/query/export.do';
	 //  var windowTitle="";
     //  window.showModalDialog(url,windowTitle,option);
		//alert(document.expForm);
		document.expForm.submit();
       }catch(e){
         return;
       }
	}
	function onSeeData(){
	try{
	   var acntId = currentRow.selfproperty;
	   var height = 450;
       var width = 820; 
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no";
	   var url='<%=contextPath%>/project/query/displayInfo.do?acntId='+acntId;
	   var windowTitle="";
       window.open(url,windowTitle,option);
       }catch(e){
         return;
       }
	}
	function onOpenData(rowObj){
	try{
	   var acntId = rowObj.selfproperty;
	   var height = 450;
       var width = 820; 
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no";
	   var url='<%=contextPath%>/project/query/displayInfo.do?acntId='+acntId;
	   var windowTitle="";
       window.open(url,windowTitle,option);
       }catch(e){
         
       }
	}
	</script>

	</head>
	<body style="overflow:auto;" >
  <%
	java.util.Locale locale = (java.util.Locale)session.getAttribute(org.apache.struts.Globals.LOCALE_KEY); 	
  	String _language = locale.toString();	
  %>
   <ec:table 
       tableId="QueryRecordListTable"
       items="webVo"
       var="aRecord" scope="request"
       action="${pageContext.request.contextPath}/project/query/projectQueryList.do"  
       imagePath="${pageContext.request.contextPath}/image/table/compact/*.gif"
       locale="<%=_language%>"
       >

        <ec:row onclick="rowOnClick(this);" ondbclick="onOpenData(this)" selfproperty="${aRecord.acntId}">
        <ec:column  property="hasAttachment" width="70" tooltip="${aRecord.hasAttachment}" title="Attachment" />
             <logic:equal property="showApplicant" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="appName" width="150" tooltip="${aRecord.appName}" title="Applicant" />
             </logic:equal>
             <logic:equal property="showRelPrjType" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="relPrjType" width="150" tooltip="${aRecord.relPrjType}" title="RelPrjType" />
             </logic:equal>
             <logic:equal property="showAcntId" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="acntId" width="100" tooltip="${aRecord.acntId}" title="Acnt Id" /> 
             </logic:equal> 
             <logic:equal property="showAchieveBelong" name="SearchCondition" scope="session"  value="checked">
                <ec:column  property="achieveBelong" width="100" tooltip="${aRecord.achieveBelong}" title="Achieve Belong"/>
             </logic:equal>
             <logic:equal property="showLeader" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="leaderName" width="150" tooltip="${aRecord.leaderName}" title="Leader" />
             </logic:equal>
             <logic:equal property="showDivisionManager" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="bdName" width="150" tooltip="${aRecord.bdName}" title="Division Manager" />
             </logic:equal>
             <logic:equal property="showNickName" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="acntName" width="100" tooltip="${aRecord.acntName}" title="Nick Name" /> 
             </logic:equal>
             <logic:equal property="showProjectManager" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="pmName" width="150" tooltip="${aRecord.pmName}" title="Project Manager" /> 
             </logic:equal> 
             <logic:equal property="showTCSigner" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="tcName" width="150" tooltip="${aRecord.tcName}" title="Time Card Signer" /> 
             </logic:equal>  
             <logic:equal property="showProjName" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="acntFullName" width="100" tooltip="${aRecord.acntFullName}" title="Project Name" /> 
             </logic:equal> 
             <logic:equal property="showProductName" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="productName" width="100" tooltip="${aRecord.productName}" title="Product Name" />
             </logic:equal> 
             <logic:equal property="showProjectDesc" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="acntBrief" width="100" tooltip="${aRecord.acntBrief}" title="Project Desc" />
             </logic:equal> 
             <logic:equal property="showProjectExecUnit" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="execUnitId" width="160" tooltip="${aRecord.execUnitId}" title="Project Exec Unit" />
             </logic:equal> 
             <logic:equal property="showProjectExecuteSite" name="SearchCondition" scope="session" value="checked">
               <ec:column  property="siteName" width="160" tooltip="${aRecord.siteName}" title="Project Execute Site" />
             </logic:equal> 
             <logic:equal property="showCostBelong" name="SearchCondition" scope="session" value="checked">
               <ec:column  property="costAttachBd" width="150" tooltip="${aRecord.costAttachBd}" title="Cost Belong" />
             </logic:equal> 
             <logic:equal property="showSales" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="salesName" width="150" tooltip="${aRecord.salesName}" title="Sales" />
             </logic:equal> 
             <logic:equal property="showCustomerServiceManager" name="SearchCondition" scope="session" value="checked">
               <ec:column  property="serviceManagerName" width="160" tooltip="${aRecord.serviceManagerName}" title="Customer Service Manager" />
             </logic:equal> 
             <logic:equal property="showEngageManager" name="SearchCondition" scope="session" value="checked">
               <ec:column  property="engageManagerName" width="150" tooltip="${aRecord.engageManagerName}" title="Engage Manager" />
             </logic:equal> 
             <logic:equal property="showBizSource" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="bizSource" width="80" tooltip="${aRecord.bizSource}" title="Biz Source" />
             </logic:equal> 
             <logic:equal property="showParentProject" name="SearchCondition" scope="session" value="checked">
               <ec:column  property="relParentId" width="100" tooltip="${aRecord.relParentId}" title="Parent Project" />
             </logic:equal> 
             <logic:equal property="showContractAcnt" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="contractAcntId" width="100" tooltip="${aRecord.contractAcntId}" title="Contract Project" /> 
             </logic:equal>
             <logic:equal property="showCustomerInfo" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="cusShort" width="130" tooltip="${aRecord.customerToolTip}" title="Customer Info" /> 
             </logic:equal>
             
             
             
             <logic:equal property="showProjectType" name="SearchCondition" scope="session" value="checked">
               <ec:column  property="projectTypeName" width="100" tooltip="${aRecord.projectTypeName}" title="Project Type" />
             </logic:equal> 
             <logic:equal property="showProductType" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="productTypeName" width="100"  tooltip="${aRecord.productTypeName}" title="Product Type" />
             </logic:equal> 
             <logic:equal property="showProductAttribute" name="SearchCondition" scope="session" value="checked">
                 <ec:column  property="productAttributeName" width="120" tooltip="${aRecord.productAttributeName}" title="Product Attribute" />
             </logic:equal> 
             <logic:equal property="showWorkItem" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="workItemName" width="100" tooltip="${aRecord.workItemName}" title="Work Item" />
             </logic:equal> 
              <logic:equal property="showTechnicalDomain" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="technicalDomainName" width="120" tooltip="${aRecord.technicalDomainName}" title="Technical Domain" />
             </logic:equal> 
             <logic:equal property="showSupportLanguage" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="supportLanguage" width="120" tooltip="${aRecord.supportLanguage}" title="Support Language" /> 
             </logic:equal> 
             <logic:equal property="showDevelopEnvironment" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="developEnvironment" width="130" tooltip="${aRecord.developEnvironmentToolTip}" title="Develop Environment" />
             </logic:equal> 
             <logic:equal property="showTranslatePublishType" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="translatePublishType" width="135" tooltip="${aRecord.translatePublishTypeToolTip}" title="Translate Publish Type" /> 
             </logic:equal> 
              <logic:equal property="showHardReq" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="hardreq" width="120" tooltip="${aRecord.hardreq}" title="HardReq" />
             </logic:equal> 
             <logic:equal property="showSoftReq" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="softreq" width="120" tooltip="${aRecord.softreq}" title="SoftReq" />
             </logic:equal> 
             
             
             <logic:equal property="showProjectScheduleAnticipate" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="projectScheduleAnticipate" width="170" tooltip="${aRecord.projectScheduleAnticipate}" title="Project Schedule Anticipate" />
             </logic:equal> 
              <logic:equal property="showProjectScheduleFact" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="projectScheduleFact" width="150" tooltip="${aRecord.projectScheduleFact}" title="Project Schedule Fact" /> 
             </logic:equal> 
             <logic:equal property="showAnticipateWorkQuantity" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="anticipateWorkQuantity" width="150" tooltip="${aRecord.anticipateWorkQuantity}" title="Anticipate Work Quantity" />
             </logic:equal> 
             <logic:equal property="showOthers" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="otherDesc" width="100" tooltip="${aRecord.otherDesc}" title="Others" /> 
             </logic:equal> 
             <logic:equal property="showManMonth" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="estManmonthValue" width="130" tooltip="${aRecord.estManmonthValue}" title="EstManmonth" /> 
             </logic:equal> 
             <logic:equal property="showPrimaveraAdapted" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="primaveraAdaptedValue" width="130" tooltip="${aRecord.primaveraAdaptedValue}" title="PrimaveraAdapted" /> 
             </logic:equal> 
             <logic:equal property="showBillingBasis" name="SearchCondition" scope="session" value="checked">
                <ec:column  property="billingBasisValue" width="130" tooltip="${aRecord.billingBasisValue}" title="BillingBasis" /> 
             </logic:equal> 
            
         </ec:row>         
     </ec:table>
     <form name="expForm" action="<%=contextPath%>/project/query/export.do">
     </form>
      <script language="javascript" type="text/javascript">
	    autoClickFirstRow();
        setColumnTitle();
      </script>
	</body>
	
</html>
