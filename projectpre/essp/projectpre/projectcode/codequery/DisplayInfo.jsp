<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<bean:define id="contextPath" value="<%=request.getContextPath()%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<TITLE><bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectDetail" /></TITLE>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
<style media=print>
.noPrint{display:none;}
.PageNext{page-break-after: avoid; page-break-before: avoid;}
</style>

<SCRIPT language="JavaScript" type="text/JavaScript">
   function onClickClose(){
	   window.close(); 	  
   }
   function onClickPrint(){
     if(confirm("<bean:message bundle="application" key="global.confirm.print"/>")){
        window.print();
     } else {
        return;
     }
   }
function window.onafterprint(){
     try{
      //alert("ok");
       var acntId = document.all.acntId.value;
      //alert(acntId);
       window.location="<%=contextPath%>/project/query/setPrinted.do?acntId="+acntId;
       } catch(e){}
   }
   
   
   
</SCRIPT>


	</head>

	<body>
	<html:hidden name="isPrinted" beanName="webVo"/>
	<html:hidden name="acntId" beanName="webVo"/>
    <logic:equal name="webVo" property="isPrinted" value="1">
    <font id="printFont" color="red" class="list_range" >
       <bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectPrinted" />
       </font>
    </logic:equal>
    <table align=center><tr><td class="list_range"><bean:message bundle="hrbase" key="hrbase.dept.deptdetail" /></td></tr></table>
		<table width="600" border="1" cellpadding="1" cellspacing="1" align="center" style='word-break:break-all;'>
			<tr>
				
				<td width="100" class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Applicant"/>
				</td>
				<td class="list_range" colspan="3">
					<bean:write name="webVo" property="applicantName"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.QueryProject.CloseStatus"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="applicationStatus"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectCode"/>
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="acntId"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.NiceName"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="acntName"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.CodeCheckDetail.ProcessType"/>
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="relPrjType"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectName"/>

				</td>
				<td width="160" class="list_range" colspan="3">
					<bean:write name="webVo" property="acntFullName"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectDesc"/>
				</td>
				<td colspan="3" class="list_range">
					<bean:write name="webVo" property="acntBrief"/>&nbsp;
				</td>

			</tr>
			<tr>
			    <td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.ProjectManager"/>
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="PMName"/>&nbsp;
				</td>
				<td class="list_range" >
					<bean:message bundle="projectpre" key="projectCode.MasterData.TimeCardSigner"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="TCSName"/>&nbsp;
				</td>
				
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.BD"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="BDMName"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Leader"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="leaderName"/>&nbsp;
				</td>
				
			</tr>
			
			<tr>
			    <td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Sales"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="salesName"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectProperty"/>
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="acntAttribute"/>&nbsp;
				</td>
			</tr>
			<tr>
			<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.CostBelong"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="costAttachBd"/>&nbsp;
				</td>
				
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectExecUnit"/>
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="execUnitId"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectExecuteSite"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="execSite"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.BizSource"/>
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="bizSource"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.ProductName"/>
				</td>
				<td class="list_range" colspan="3">
					<bean:write name="webVo" property="productName"/>&nbsp;
				</td>
				
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.AchieveBelong"/>
				</td>
				<td class="list_range" class="list_range">
					<bean:write name="webVo" property="achieveBelong"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingProjectQuanity"/>
					&nbsp;&nbsp;
					<bean:message bundle="projectpre" key="projectCode.ProjectData.WordCount"/>
				
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="estSize"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingStart"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="acntPlannedStart"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingEnd"/>
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="acntPlannedFinish"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingPerMonth"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="estManmonth"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.ProjectData.FactPerMonth"/>
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="actualManmonth"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.ProjectData.FactStart"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="acntActualStart"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.ProjectData.FactEnd"/>
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="acntActualFinish"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.PrimaveraAdapted"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="primaveraAdapted"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.BillingBasis"/>
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="billingBasis"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.BizProperty"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="bizProperty"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.BillType"/>
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="billType"/>&nbsp;
				</td>
			</tr>
			 <tr>
    <td class="list_range" colspan="2">
    <bean:message bundle="projectpre" key="projectCode.Technical.ProjectType"/>
    </td>
    <td colspan="3" class="list_range"><bean:write name="webVo" property="projectType"/>&nbsp;</td>
    
    </tr>
    <tr>
    <td class="list_range" colspan="2">
    <bean:message bundle="projectpre" key="projectCode.Technical.ProductType"/>
    </td>
    <td colspan="3" class="list_range"><bean:write name="webVo" property="productType"/>&nbsp;</td>
    
    </tr>
    <tr>
    <td class="list_range" colspan="2">
    <bean:message bundle="projectpre" key="projectCode.Technical.ProductProperty"/>
    </td>
    <td colspan="3" class="list_range"><bean:write name="webVo" property="productAttribute"/>&nbsp;</td>
    
    </tr>
       <tr>
     <td class="list_range" colspan="2">
  <bean:message bundle="projectpre" key="projectCode.Technical.Project"/>
    </td>
    <td colspan="3" class="list_range"><bean:write name="webVo" property="workItem"/>&nbsp;</td> 
   
    </tr>
      <tr>
     <td class="list_range" colspan="2">
  <bean:message bundle="projectpre" key="projectCode.Technical.TechnicalArea"/> 
    </td>
    <td colspan="3" class="list_range"><bean:write name="webVo" property="technicalDomain"/>&nbsp;</td> 
    
    </tr>
      <tr>
     <td class="list_range" colspan="2">
      <bean:message bundle="projectpre" key="projectCode.Technical.SupportLanguage"/>&nbsp;
   	  <bean:message bundle="projectpre" key="projectCode.Technical.Orignal"/> 
    </td>
    <td colspan="3" class="list_range"><bean:write name="webVo" property="originalLanguage"/>&nbsp;</td> 
   
    </tr>
      <tr>
     <td class="list_range" colspan="2">
        <bean:message bundle="projectpre" key="projectCode.Technical.SupportLanguage"/>&nbsp;
   		<bean:message bundle="projectpre" key="projectCode.Technical.Translation"/>  
    </td>
    <td colspan="3" class="list_range"><bean:write name="webVo" property="translationLanguage"/>&nbsp;</td> 
    
    </tr>
      <tr>
     <td class="list_range" rowspan="6" width="70">
     <bean:message bundle="projectpre" key="projectCode.Technical.DevelopEnvironment"/>
     </td>
     <td class="list_range">
  <bean:message bundle="projectpre" key="projectCode.Technical.JobSystem"/>
  </td> 
    
    <td colspan="3" class="list_range"><bean:write name="webVo" property="devJobSystem"/>&nbsp;</td> 
   
    </tr>
     <tr>
     <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.Technical.DataBase"/>
    </td>
    <td colspan="3" class="list_range"><bean:write name="webVo" property="devDataBase"/>&nbsp;</td> 
   
    
    </tr>
    <tr>
     <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.Technical.Tool"/>
    </td>
   
    <td colspan="3" class="list_range"><bean:write name="webVo" property="devTool"/>&nbsp;</td>
   
    </tr>
    <tr>
     <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.Technical.Network"/>
    </td>
   
    <td colspan="3" class="list_range"><bean:write name="webVo" property="devNetWork"/>&nbsp;</td>
    
    </tr>
    <tr>
     <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.Technical.ProgramLangage"/>
    </td>
   
    <td colspan="3" class="list_range"><bean:write name="webVo" property="devProgramLanguage"/>&nbsp;</td>
   
    </tr>
    <tr>
     <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.Technical.Others"/>
    </td>
    
    <td colspan="3" class="list_range"><bean:write name="webVo" property="devOthers"/>&nbsp;</td>
   
    </tr>
      <tr>
     <td class="list_range" rowspan="6">
  <bean:message bundle="projectpre" key="projectCode.Technical.TranslatePublishType"/>   
    </td>
    <td class="list_range">
    <bean:message bundle="projectpre" key="projectCode.Technical.JobSystem"/>
    </td> 
    <td colspan="3" class="list_range"><bean:write name="webVo" property="tranJobSystem"/>&nbsp;</td>
   
    </tr>
    <tr>
     <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.Technical.DataBase"/>
    </td>
   
    <td colspan="3" class="list_range"><bean:write name="webVo" property="tranDataBase"/>&nbsp;</td>
   
    </tr>
    <tr>
     <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.Technical.Tool"/>
    </td>
   
    <td colspan="3" class="list_range"><bean:write name="webVo" property="tranTool"/>&nbsp;</td>
   
    </tr>
    <tr>
     <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.Technical.Network"/>
    </td>
    
    <td colspan="3" class="list_range"><bean:write name="webVo" property="tranNetWork"/>&nbsp;</td>
  
    </tr>
    <tr>
     <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.Technical.ProgramLangage"/>
    </td>
  
    <td colspan="3" class="list_range"><bean:write name="webVo" property="tranProgramLanguage"/>&nbsp;</td>
   
    </tr>
    <tr>
     <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.Technical.Others"/>
    </td>
  
    <td colspan="3" class="list_range"><bean:write name="webVo" property="tranOthers"/>&nbsp;</td>
    
    </tr>
    
      <tr>
     <td class="list_range" colspan="2">
    <bean:message bundle="projectpre" key="projectCode.Technical.HardRequirement"/>
    </td>
    <td colspan="3" class="list_range"><bean:write name="webVo" property="hardReq"/>&nbsp;</td> 
   
    </tr>
      <tr>
     <td class="list_range" colspan="2">
   <bean:message bundle="projectpre" key="projectCode.Technical.SoftRequirement"/>
    </td>
    <td colspan="3" class="list_range"><bean:write name="webVo" property="softReq"/>&nbsp;</td> 
   
    </tr>
    <tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.CustomerNo"/>
				</td>
				<td class="list_range" colspan="3">
					<bean:write name="webVo" property="customerId"/>&nbsp;
				</td>
				
			</tr>
			<tr>
				<td class="list_range" colspan="2">
					<bean:message bundle="projectpre" key="projectCode.MasterData.EngageManager"/>
				</td>
				<td class="list_range">
					<bean:write name="webVo" property="engageManagerName"/>&nbsp;
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.CustomerServiceManager"/>
				</td>
				<td width="160" class="list_range">
					<bean:write name="webVo" property="custServiceManagerName"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" rowspan="3" width="70">
					<bean:message bundle="projectpre" key="projectCode.MasterData.CooperateContact"/>
				</td>
				<td class="list_range" width="70">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Name"/>
				</td>
				<td colspan="3" class="list_range">
					<bean:write name="webVo" property="contract"/>&nbsp;
				</td>

			</tr>
			<tr>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Tel"/>
				</td>
				<td colspan="3" class="list_range">
					<bean:write name="webVo" property="contractTel"/>&nbsp;
				</td>

			</tr>
			<tr>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Email"/>
				</td>
				<td colspan="3" class="list_range">
					<bean:write name="webVo" property="contractEmail"/>&nbsp;
				</td>

			</tr>
			<tr>
				<td class="list_range" rowspan="3">
					<bean:message bundle="projectpre" key="projectCode.MasterData.PerformContact"/>
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Name"/>
				</td>
				<td colspan="3" class="list_range">
					<bean:write name="webVo" property="executive"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Tel"/>
				</td>
				<td colspan="3" class="list_range">
					<bean:write name="webVo" property="executiveTel"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Email"/>
				</td>

				<td colspan="3" class="list_range">
					<bean:write name="webVo" property="executiveEmail"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range" rowspan="3">
					<bean:message bundle="projectpre" key="projectCode.MasterData.FinanceContact"/>
				</td>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Name"/>
				</td>
				<td colspan="3" class="list_range">
					<bean:write name="webVo" property="financial"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Tel"/>
				</td>
				<td colspan="3" class="list_range">
					<bean:write name="webVo" property="financialTel"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td class="list_range">
					<bean:message bundle="projectpre" key="projectCode.MasterData.Email"/>
				</td>

				<td colspan="3" class="list_range">
					<bean:write name="webVo" property="financialEmail"/>&nbsp;
				</td>
			</tr>
			<tr>
		</table>
		<table align="center">
		<tr>
		<td>
	
		
		<input class="noPrint" style="BORDER-RIGHT: #104a7b 1px solid; BORDER-TOP: #afc4d5 1px solid; FONT-SIZE: 11px; BACKGROUND: #d6e7ef; BORDER-LEFT: #afc4d5 1px solid; CURSOR: hand; COLOR: #000066; BORDER-BOTTOM: #104a7b 1px solid; FONT-FAMILY: Arial, Helvetica, sans-serif; HEIGHT: 19px; font-weight : bold;TEXT-DECORATION: none" type="button" name="button_print" value="<bean:message bundle="application" key="global.button.print" />" onclick="javascript:printIt();">&nbsp;&nbsp;
		<input class="noPrint" style="BORDER-RIGHT: #104a7b 1px solid; BORDER-TOP: #afc4d5 1px solid; FONT-SIZE: 11px; BACKGROUND: #d6e7ef; BORDER-LEFT: #afc4d5 1px solid; CURSOR: hand; COLOR: #000066; BORDER-BOTTOM: #104a7b 1px solid; FONT-FAMILY: Arial, Helvetica, sans-serif; HEIGHT: 19px; font-weight : bold;TEXT-DECORATION: none" type="button" name="button_close" value="<bean:message bundle="application" key="global.button.close" />" onclick="javascript:onClickClose();">
		
		<%--html:button styleId="noPrint" name="printInfo" onclick="onClickPrint()">
			<bean:message bundle="application" key="global.button.print" />
		</html:button>
        <html:button styleId="noPrint" name="closeCancel" onclick="onClickClose()">
			<bean:message bundle="application" key="global.button.close" />
		</html:button--%>
		</td>
		</tr>
		</table>
		<script language="JavaScript" type="text/JavaScript">
	   function printIt(){ 
	   try{
	     if(document.all.isPrinted.value=="1"){
	   		if(confirm("<bean:message bundle="application" key="global.confirm.printDup"/>")){
	   		try{
	   		    document.all.printFont.style.display="none";
	   		    }catch(e){}
	   			window.print();
	   		}
	     } else{ 
		  if(confirm("<bean:message bundle="application" key="global.confirm.print"/>"))
		  { 
		     try{
	    		document.all.printFont.style.display="none";
	    		} catch(e){}
　		　       window.print();
　		　}
        }
	  }catch(e){} 
      }
</script>
	</body>
</html>
