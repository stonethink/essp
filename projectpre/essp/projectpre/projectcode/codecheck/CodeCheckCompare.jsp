
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:define id="contextPath" value="<%=request.getContextPath()%>"/>
<bean:define id="rid" value='<%=request.getParameter("rid")%>'/>
<%@ page import="server.essp.projectpre.service.constant.Constant" %>
<%@ page import="c2s.essp.common.account.IDtoAccount" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">




<html>
  <head>
  <TITLE><bean:message bundle="projectpre" key="projectCode.ProjectCodeEdit"/></TITLE>
    <tiles:insert page="/layout/head.jsp">
            <tiles:put name="title" value="Project Code Check"/>
            <tiles:put name="jspName" value=""/>
        </tiles:insert>
     <style type="text/css">
      #input_common1{width:110}
      #description{width:690}
      #input_common2{width:150}
    
    </style>
    <script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/queryCustomer.js"></script>
    <script language="javascript" type="text/javascript">
     function onInitTitle(){
     
      try{
	    
        var str0="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectName"/>";
        var str1="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectManager"/>";
		var str2="<bean:message bundle="projectpre" key="projectCode.MasterData.TimeCardSigner"/>";
		var str3="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectDesc"/>";
        var str4="<bean:message bundle="projectpre" key="projectCode.MasterData.DivisionManager"/>";
        var str5="<bean:message bundle="projectpre" key="projectCode.MasterData.Leader"/>";
        var str6="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectExecuteSite"/>";
        var str7="<bean:message bundle="projectpre" key="projectCode.MasterData.CostBelong"/>";
        var str8="<bean:message bundle="projectpre" key="projectCode.MasterData.BizSource"/>";
        var str9="<bean:message bundle="projectpre" key="projectCode.MasterData.Sales"/>";
        var str10="<bean:message bundle="projectpre" key="projectCode.MasterData.ProductName"/>";
        var str11="<bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingStart"/>";
        var str12="<bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingEnd"/>";
        var str13="<bean:message bundle="projectpre" key="projectCode.ProjectData.FactStart"/>";
        var str14="<bean:message bundle="projectpre" key="projectCode.ProjectData.FactEnd"/>";
        var str15="<bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingPerMonth"/>";
        var str16="<bean:message bundle="projectpre" key="projectCode.ProjectData.FactPerMonth"/>";
        var str17="<bean:message bundle="projectpre" key="projectCode.ProjectData.IntendingProjectQuanity"/>";
        
        var str18="<bean:message bundle="projectpre" key="projectCode.Technical.ProjectType"/>";
        var str19="<bean:message bundle="projectpre" key="projectCode.Technical.ProductType"/>";
        var str20="<bean:message bundle="projectpre" key="projectCode.Technical.ProductProperty"/>";
        var str21="<bean:message bundle="projectpre" key="projectCode.Technical.Project"/>";
        var str22="<bean:message bundle="projectpre" key="projectCode.Technical.TechnicalArea"/>";
        var str23="<bean:message bundle="projectpre" key="projectCode.Technical.SupportLanguage"/><bean:message bundle="projectpre" key="projectCode.Technical.Orignal"/>";
        var str24="<bean:message bundle="projectpre" key="projectCode.Technical.SupportLanguage"/><bean:message bundle="projectpre" key="projectCode.Technical.Translation"/>";
        var str25="<bean:message bundle="projectpre" key="projectCode.Technical.DevelopEnvironment"/>";
        var str26="<bean:message bundle="projectpre" key="projectCode.Technical.JobSystem"/>";
        var str27="<bean:message bundle="projectpre" key="projectCode.Technical.DataBase"/>";
        var str28="<bean:message bundle="projectpre" key="projectCode.Technical.Tool"/>";
        var str29="<bean:message bundle="projectpre" key="projectCode.Technical.Network"/>";
        var str30="<bean:message bundle="projectpre" key="projectCode.Technical.ProgramLangage"/>";
        var str31="<bean:message bundle="projectpre" key="projectCode.Technical.Others"/>";
        var str32="<bean:message bundle="projectpre" key="projectCode.Technical.TranslatePublishType"/>";
        var str33="<bean:message bundle="projectpre" key="projectCode.Technical.SoftRequirement"/>";
        var str34="<bean:message bundle="projectpre" key="projectCode.Technical.HardRequirement"/>";
        
        var str35="<bean:message bundle="projectpre" key="projectCode.MasterData.CustomerNo"/>";
        var str36="<bean:message bundle="projectpre" key="projectCode.MasterData.CustomerServiceManager"/>";
        var str37="<bean:message bundle="projectpre" key="projectCode.MasterData.EngageManager"/>";
        var str38="<bean:message bundle="projectpre" key="projectCode.MasterData.CooperateContact"/>";
        var str39="<bean:message bundle="projectpre" key="projectCode.MasterData.PerformContact"/>";
        var str40="<bean:message bundle="projectpre" key="projectCode.MasterData.FinanceContact"/>";
        var str41="<bean:message bundle="projectpre" key="projectCode.MasterData.Name"/>";
        var str42="<bean:message bundle="projectpre" key="projectCode.MasterData.Tel"/>";
        var str43="<bean:message bundle="projectpre" key="projectCode.MasterData.Email"/>";
        
        
        var str="<bean:message bundle="projectpre" key="projectCode.ProjectData.Others"/>";
        var _str="<bean:message bundle="projectpre" key="projectCode.MasterData.ProjectProperty"/>";
        var str_a="<bean:message bundle="projectpre" key="projectCode.MasterData.AchieveBelong"/>";
        var str_b="<bean:message bundle="projectpre" key="projectCode.MasterData.PrimaveraAdapted"/>";
        var str_c="<bean:message bundle="projectpre" key="projectCode.MasterData.BillingBasis"/>";
        var str_biz="<bean:message bundle="projectpre" key="projectCode.MasterData.BizProperty"/>";
        var str_bil="<bean:message bundle="projectpre" key="projectCode.MasterData.BillType"/>";
        
       // var thead_length=table.tHead.rows.length;
		//var tds=table.rows[thead_length-1];
		//var cells=tds.cells;
		//国际化
		var table=document.getElementById("masterTable");
		var rows = table.rows;
		//alert(rows[1].cells[0].innerHTML);
	   
		rows[1].cells[0].innerHTML=rows[1].cells[0].innerHTML.replace("acntFullName",str0);
		rows[2].cells[0].innerHTML=rows[2].cells[0].innerHTML.replace("acntAttribute",_str);
		rows[3].cells[0].innerHTML=rows[3].cells[0].innerHTML.replace("PMName",str1);
		rows[4].cells[0].innerHTML=rows[4].cells[0].innerHTML.replace("TCSName",str2);
		rows[5].cells[0].innerHTML=rows[5].cells[0].innerHTML.replace("acntBrief",str3);
		rows[6].cells[0].innerHTML=rows[6].cells[0].innerHTML.replace("BDName",str4);
		rows[7].cells[0].innerHTML=rows[7].cells[0].innerHTML.replace("leader",str5);
		rows[8].cells[0].innerHTML=rows[8].cells[0].innerHTML.replace("execSite",str6);
		rows[9].cells[0].innerHTML=rows[9].cells[0].innerHTML.replace("costAttachBd",str7);
		rows[10].cells[0].innerHTML=rows[10].cells[0].innerHTML.replace("achieveBelong",str_a);
		rows[11].cells[0].innerHTML=rows[11].cells[0].innerHTML.replace("bizSource",str8);
		rows[12].cells[0].innerHTML=rows[12].cells[0].innerHTML.replace("sales",str9);
		rows[13].cells[0].innerHTML=rows[13].cells[0].innerHTML.replace("productName",str10);
		rows[14].cells[0].innerHTML=rows[14].cells[0].innerHTML.replace("acntPlannedStart",str11);
		rows[15].cells[0].innerHTML=rows[15].cells[0].innerHTML.replace("acntPlannedFinish",str12);
		rows[16].cells[0].innerHTML=rows[16].cells[0].innerHTML.replace("acntActualStart",str13);
		rows[17].cells[0].innerHTML=rows[17].cells[0].innerHTML.replace("acntActualFinish",str14);
		rows[18].cells[0].innerHTML=rows[18].cells[0].innerHTML.replace("estManmonth",str15);
		rows[19].cells[0].innerHTML=rows[19].cells[0].innerHTML.replace("actualManmonth",str16);
		rows[20].cells[0].innerHTML=rows[20].cells[0].innerHTML.replace("estSize",str17);
		rows[21].cells[0].innerHTML=rows[21].cells[0].innerHTML.replace("primaveraAdapted",str_b);
		rows[22].cells[0].innerHTML=rows[22].cells[0].innerHTML.replace("billingBasis",str_c);
		rows[23].cells[0].innerHTML=rows[23].cells[0].innerHTML.replace("bizProperty",str_biz);
		rows[24].cells[0].innerHTML=rows[24].cells[0].innerHTML.replace("billType",str_bil);
		rows[25].cells[0].innerHTML=rows[25].cells[0].innerHTML.replace("otherDesc",str);
		
		table=document.getElementById("techInfoTable");
		rows = table.rows;
		
		rows[1].cells[0].innerHTML=rows[1].cells[0].innerHTML.replace("<%=Constant.PROJECT_TYPE%>",str18);
		rows[2].cells[0].innerHTML=rows[2].cells[0].innerHTML.replace("<%=Constant.PRODUCT_TYPE%>",str19);
		rows[3].cells[0].innerHTML=rows[3].cells[0].innerHTML.replace("<%=Constant.PRODUCT_ATTRIBUTE%>",str20);
		rows[4].cells[0].innerHTML=rows[4].cells[0].innerHTML.replace("<%=Constant.WORK_ITEM%>",str21);
		rows[5].cells[0].innerHTML=rows[5].cells[0].innerHTML.replace("<%=Constant.TECHNICAL_DOMAIN%>",str22);
		rows[6].cells[0].innerHTML=rows[6].cells[0].innerHTML.replace("<%=Constant.ORIGINAL_LANGUAGE%>",str23);
		rows[7].cells[0].innerHTML=rows[7].cells[0].innerHTML.replace("<%=Constant.TRANSLATION_LANGUANGE%>",str24);
		rows[8].cells[0].innerHTML=rows[8].cells[0].innerHTML.replace("<%=Constant.DEVELOPENVIRONMENT%>",str25);
		rows[8].cells[1].innerHTML=rows[8].cells[1].innerHTML.replace("JobSystem", str26);
		rows[9].cells[0].innerHTML=rows[9].cells[0].innerHTML.replace("DataBase", str27);
		rows[10].cells[0].innerHTML=rows[10].cells[0].innerHTML.replace("Tool", str28);
		rows[11].cells[0].innerHTML=rows[11].cells[0].innerHTML.replace("NetWork", str29);
		rows[12].cells[0].innerHTML=rows[12].cells[0].innerHTML.replace("ProgramLangage", str30);
		rows[13].cells[0].innerHTML=rows[13].cells[0].innerHTML.replace("others", str31);
		rows[14].cells[0].innerHTML=rows[14].cells[0].innerHTML.replace("<%=Constant.TRNSLATEPUBLISHTYPE%>",str32);
		rows[14].cells[1].innerHTML=rows[14].cells[1].innerHTML.replace("JobSystem", str26);
		rows[15].cells[0].innerHTML=rows[15].cells[0].innerHTML.replace("DataBase", str27);
		rows[16].cells[0].innerHTML=rows[16].cells[0].innerHTML.replace("Tool", str28);
		rows[17].cells[0].innerHTML=rows[17].cells[0].innerHTML.replace("NetWork", str29);
		rows[18].cells[0].innerHTML=rows[18].cells[0].innerHTML.replace("ProgramLangage", str30);
		rows[19].cells[0].innerHTML=rows[19].cells[0].innerHTML.replace("others", str31);
		rows[20].cells[0].innerHTML=rows[20].cells[0].innerHTML.replace("<%=Constant.HARDREQ%>",str34);
		rows[21].cells[0].innerHTML=rows[21].cells[0].innerHTML.replace("<%=Constant.SOFTREQ%>",str33);
		
		//rows[12].cells[0].innerHTML=rows[12].cells[0].innerHTML.replace("acntPlannedStart",str11);
		//rows[13].cells[0].innerHTML=rows[13].cells[0].innerHTML.replace("acntPlannedFinish",str12);
		//rows[14].cells[0].innerHTML=rows[14].cells[0].innerHTML.replace("acntActualStart",str13);
		//rows[15].cells[0].innerHTML=rows[15].cells[0].innerHTML.replace("acntActualFinish",str14);
		
		table=document.getElementById("customerInfoTable");
		rows = table.rows;
		
		rows[1].cells[0].innerHTML=rows[1].cells[0].innerHTML.replace("customerId",str35);
		rows[2].cells[0].innerHTML=rows[2].cells[0].innerHTML.replace("<%=IDtoAccount.USER_TYPE_CUST_SERVICE_MANAGER%>",str36);
		rows[3].cells[0].innerHTML=rows[3].cells[0].innerHTML.replace("<%=IDtoAccount.USER_TYPE_ENGAGE_MANAGER%>",str37);
		rows[4].cells[0].innerHTML=rows[4].cells[0].innerHTML.replace("<%=IDtoAccount.CUSTOMER_CONTACTOR_CONTRACT%>",str38);
		rows[4].cells[1].innerHTML=rows[4].cells[1].innerHTML.replace("Name", str41);
		rows[5].cells[0].innerHTML=rows[5].cells[0].innerHTML.replace("Tel", str42);
		rows[6].cells[0].innerHTML=rows[6].cells[0].innerHTML.replace("Email", str42);
		rows[7].cells[0].innerHTML=rows[7].cells[0].innerHTML.replace("<%=IDtoAccount.CUSTOMER_CONTACTOR_EXE%>",str39);
		rows[7].cells[1].innerHTML=rows[7].cells[1].innerHTML.replace("Name", str41);
		rows[8].cells[0].innerHTML=rows[8].cells[0].innerHTML.replace("Tel", str42);
		rows[9].cells[0].innerHTML=rows[9].cells[0].innerHTML.replace("Email", str42);
		rows[10].cells[0].innerHTML=rows[10].cells[0].innerHTML.replace("<%=IDtoAccount.CUSTOMER_CONTACTOR_FINANCIAL%>",str40);
		rows[10].cells[1].innerHTML=rows[10].cells[1].innerHTML.replace("Name", str41);
		rows[11].cells[0].innerHTML=rows[11].cells[0].innerHTML.replace("Tel", str42);
		rows[12].cells[0].innerHTML=rows[12].cells[0].innerHTML.replace("Email", str42);
		//rows[7].cells[0].innerHTML=rows[7].cells[0].innerHTML.replace("execSite",str6);
		//rows[7].cells[0].innerHTML=rows[7].cells[0].innerHTML.replace("execSite",str6);
	
	
		}catch(e){}
		
     
     }
     function onChangeColor(){
     try{
     var table=document.getElementById("masterTable");
     var rows = table.rows;
     for(i=1;i<rows.length;i++){
      var tds=rows[i].cells;
      
          if(tds[1].innerText!=tds[2].innerText){
          tds[0].style.color="red";
          tds[2].style.color="red";
          }
     }
      table=document.getElementById("techInfoTable");
      rows = table.rows;
     for(i=1;i<rows.length;i++){
      var tds=rows[i].cells;
       if(i==8||i==14){
          if(tds[2].innerText!=tds[3].innerText){
          tds[1].style.color="red";
          tds[3].style.color="red";
          }
          //alert("ok");
      } else{
          if(tds[1].innerText!=tds[2].innerText){
          tds[0].style.color="red";
          tds[2].style.color="red";
          }
      }
     }
      for(i=9;i<14;i++){
         //alert(rows[i].style.color);
         if(rows[i].cells[2].style.color=="red"){
             rows[8].cells[0].style.color="red";
         }

     }
     for(i=15;i<20;i++){
         if(rows[i].cells[2].style.color=="red"){
             rows[14].cells[0].style.color="red";
         }
     }
    
      table=document.getElementById("customerInfoTable");
      rows = table.rows;
     for(i=1;i<rows.length;i++){
      var tds=rows[i].cells;
      if(i==4||i==7||i==10){
          if(tds[2].innerText!=tds[3].innerText){
           tds[1].style.color="red";
          tds[3].style.color="red";
          }
      }else{
          if(tds[1].innerText!=tds[2].innerText){
           tds[0].style.color="red";
          tds[2].style.color="red";
          }
      }
     }
    for(i=5;i<7;i++){
         if(rows[i].cells[2].style.color=="red"){
             rows[4].cells[0].style.color="red";
         }

     }
     for(i=8;i<10;i++){
         if(rows[i].cells[2].style.color=="red"){
             rows[7].cells[0].style.color="red";
         }

     }
     for(i=11;i<13;i++){
         if(rows[i].cells[2].style.color=="red"){
             rows[10].cells[0].style.color="red";
         }
     }
     }catch(e){}
     
   }
     function onClickSubmit(){
     try {
     if(!confirm("<bean:message bundle="application" key="global.confirm.customerId"/>")){
    	return;
     }
      var rid = "<%=rid%>";
      if(confirm("<bean:message bundle="application" key="global.confirm.check"/>")){
       document.forms[0].action="<%=contextPath%>/project/check/checkChangeProjectApp.do?status=Confirmed&rid="+rid;
       document.forms[0].submit();
       }
       } catch (e){}
   
     }
     function onClickReject(){
       try{
        var rid = "<%=rid%>";
     
        var comment = document.all.comment.value;
        if(comment==null||comment.replace(/(^\s*)|(\s*$)/g, "")=="") {
          alert("<bean:message bundle="application" key="global.fill.Remark"/>");
          document.all.comment.focus();
          return;
        }
          document.forms[0].action="<%=contextPath%>/project/check/checkChangeProjectApp.do?status=Rejected&rid="+rid;
          document.forms[0].submit();
       }catch(e){}
     }
   
     function onClickCancel(){
       this.close();
     }
     //查询客户控件功能
      
 function customerOnClick() {
    try{
      var result = queryCustomer();
      if(result!=null) {	
      document.all.customerId.value=result.id+"---"+result.short_name;	
      }
 	}catch(e){}			

}
  </script>
  </head>
  
  <body >
  <html:form id="ProjectCheck" action="" method="post">
   <table align=center ><tr><td class="list_range"><bean:message bundle="projectpre" key="projectCode.ProjectCodeEdit" /></td></tr></table>
   <table width="100%" border="0" >
   <tr>
   <td class="list_range">
   <bean:message bundle="projectpre" key="projectCode.QueryProject.ProjectCode" />
   </td>
   <td class="list_range">
   <html:text fieldtype="text" styleId="input_common1" name="acntId" beanName="webVo" readonly="true" /> 
   </td>
   <td class="list_range">
   <bean:message bundle="projectpre" key="deptCode.Applicant" />
   </td>
   <td class="list_range"><html:text fieldtype="text" styleId="input_common1" name="applicant" beanName="webVo" readonly="true" /> 
   </td>
   <td class="list_range"><bean:message bundle="projectpre" key="projectCode.ApplyRecordList.ApplyDate" /></td>
   <td class="list_range"><html:text fieldtype="text" styleId="input_common1" name="applicationDate" beanName="webVo" readonly="true" /></td>
   </tr>
    <tr>
        <td valign="bottom" height="5" colspan="6" class="orarowheader" width="100%">
        <bean:message bundle="projectpre"  key="projectCode.CodeApplyDetail.cardTitle.MasterData"/>
        </td>
  
    </tr>
    </table>
    <table border="1" width="100%" id="masterTable" style='word-break:break-all;'>
    <tr>
    <td class="list_range" width="15%">
    <bean:message bundle="projectpre" key="deptCode.Option" />
    </td>
    <td class="list_range" width="40%">
    <bean:message bundle="projectpre" key="deptCode.BeforeChange" />
    </td>
     <td class="list_range" width="40%">
     <bean:message bundle="projectpre" key="deptCode.AfterChange" />
    </td>
    </tr>
    <logic:iterate id="item"
                  name="webVo"
                  property="masterList"
                  scope="request"
                  indexId="a"
   >
   <tr>
   <td class="list_range" width="15%">
   <bean:write name="item" property="option"/>&nbsp;
   </td>
   <td class="list_range" width="40%">
   <bean:write name="item" property="valueBeforeChange"/>&nbsp;
   </td>
   <td class="list_range" width="40%">
   <bean:write name="item" property="valueAfterChange"/>&nbsp;
   </td>
   </tr>
  </logic:iterate>
    
  
   </table>
   <table border="0" width="100%" >
   <tr>
        <td valign="bottom" height="5" colspan="4" class="orarowheader" width="100%">
        <bean:message bundle="projectpre"  key="projectCode.CodeApplyDetail.cardTitle.TechnicalData"/></td>
      
    </tr>
    </table>
    <table border="1" width="100%" id="techInfoTable" style='word-break:break-all;'>
     <tr>
    <td class="list_range" width="30%" colspan="2">
    <bean:message bundle="projectpre" key="deptCode.Option" />
    </td>
    <td class="list_range" width="40%">
    <bean:message bundle="projectpre" key="deptCode.BeforeChange" />
    </td>
     <td class="list_range" width="40%">
     <bean:message bundle="projectpre" key="deptCode.AfterChange" />
    </td>
    </tr>
  <logic:iterate id="item"
                  name="webVo"
                  property="techInfoList"
                  scope="request"
                  indexId="a"
   >
  <%
    if(a.intValue() == 7||a.intValue() == 8){%>
          <tr>
     <td class="list_range" rowspan="6" width="10%">
     <bean:write name="item" property="option"/>&nbsp;
     </td>
     <td class="list_range">
  JobSystem
  </td> 
    
    <td class="list_range"><bean:write name="item" property="jobSystemBefore"/>&nbsp;</td> 
    <td class="list_range"><bean:write name="item" property="jobSystem"/>&nbsp;</td>
    </tr>
     <tr>
     <td class="list_range">
   DataBase
    </td>
    <td class="list_range"><bean:write name="item" property="dataBaseBefore"/>&nbsp;</td> 
    <td class="list_range"><bean:write name="item" property="dataBase"/>&nbsp;</td>
    
    </tr>
    <tr>
     <td class="list_range">
   Tool
    </td>
   
    <td class="list_range"><bean:write name="item" property="toolBefore"/>&nbsp;</td>
    <td class="list_range"><bean:write name="item" property="tool"/>&nbsp;</td>
    </tr>
    <tr>
     <td class="list_range">
   NetWork
    </td>
   
    <td class="list_range"><bean:write name="item" property="netWorkBefore"/>&nbsp;</td>
    <td class="list_range"><bean:write name="item" property="netWork"/>&nbsp;</td>
    </tr>
    <tr>
     <td class="list_range">
   ProgramLangage
    </td>
   
    <td class="list_range"><bean:write name="item" property="programLanguageBefore"/>&nbsp;</td>
    <td class="list_range"><bean:write name="item" property="programLanguage"/>&nbsp;</td>
    </tr>
    <tr>
     <td class="list_range">
   others
    </td>
    
    <td class="list_range"><bean:write name="item" property="othersBefore"/>&nbsp;</td>
    <td class="list_range"><bean:write name="item" property="others"/>&nbsp;</td>
    </tr>
    
    <%}else {%>
    

   <tr >
   <td class="list_range" width="15%" colspan="2">
   <bean:write name="item" property="option"/>&nbsp;
   </td>
   <td class="list_range" width="40%" >
   <bean:write name="item" property="valueBeforeChange"/>&nbsp;
   </td>
   <td class="list_range" width="40%" >
   <bean:write name="item" property="valueAfterChange"/>&nbsp;
   </td>
   </tr>
   <%}%>
  
  </logic:iterate>
    </table>
    <table border="0" width="100%">
    <tr>
        <td valign="bottom" height="5" colspan="4" class="orarowheader" width="100%"><bean:message bundle="projectpre"  key="projectCode.CodeApplyDetail.cardTitle.CustomerData"/></td>
       
    </tr>
    </table>
     <table border="1" width="100%" id="customerInfoTable" style='word-break:break-all;'>
     <tr>
    <td class="list_range" width="25%" colspan="2">
    <bean:message bundle="projectpre" key="deptCode.Option" />
    </td>
    <td class="list_range" width="40%">
    <bean:message bundle="projectpre" key="deptCode.BeforeChange" />
    </td>
     <td class="list_range" width="40%">
     <bean:message bundle="projectpre" key="deptCode.AfterChange" />
    </td>
    </tr>
   <logic:iterate id="item"
                  name="webVo"
                  property="customerInfoList"
                  scope="request"
                  indexId="a"
                  type="server.essp.projectpre.ui.project.check.VbCompare"
                  
   >
  <%if(a.intValue() == 3||a.intValue() == 4||a.intValue() == 5){%>
  
      <tr>
     <td class="list_range" rowspan="3" width="16%">
      <bean:write name="item" property="option"/>&nbsp;
    </td>
    <td class="list_range">
    Name
    </td>
    <td class="list_range"><bean:write name="item" property="nameBefore"/>&nbsp;</td> 
    <td class="list_range"><bean:write name="item" property="name"/>&nbsp;</td>
    </tr>
     <tr>
    <td class="list_range">
   Tel
    </td>
    <td class="list_range"><bean:write name="item" property="telBefore"/>&nbsp;</td>
    <td class="list_range"><bean:write name="item" property="tel"/>&nbsp;</td>
    </tr>
     <tr>
    <td class="list_range">
   Email
    </td>
    <td class="list_range"><bean:write name="item" property="emailBefore"/>&nbsp;</td>
    <td class="list_range"><bean:write name="item" property="email"/>&nbsp;</td>
    </tr>
  
  <%}else if("customerId".equals(item.getOption())){%>
   <tr >
   <td class="list_range" width="15%" colspan="2">
   <bean:write name="item" property="option"/>&nbsp;
   </td>
   <td class="list_range" width="40%" >
   <bean:write name="item" property="valueBeforeChange"/>&nbsp;
   </td>
   <td class="list_range" width="40%" >
   <DIV style="width: 120">
   <html:text name="customerId"
                      beanName="webVo"
                      fieldtype="text"
                      styleId="input_common2"
                      prev="dueDate"
                      req="true"
		              readonly="true"
                      imageSrc="<%=contextPath+"/image/qurey.gif"%>"
                      imageWidth="18"
                      imageOnclick="customerOnClick();"
                      value="<%=item.getValueAfterChange()%>"
         />
         </DIV>
   </td>
   </tr>
    <%} else {%>
   <tr>
   <td class="list_range" width="15%" colspan="2">
   <bean:write name="item" property="option"/>&nbsp;
   </td>
   <td class="list_range" width="40%">
   <bean:write name="item" property="valueBeforeChange"/>&nbsp;
   </td>
   <td class="list_range" width="40%">
   <bean:write name="item" property="valueAfterChange"/>&nbsp;
   </td>
   </tr>
   <%}%>
  </logic:iterate>
   </table>
     <table border="0" width="100%">
     <tr>
     <td class="list_range" width=40 ><bean:message bundle="projectpre" key="projectCode.comment"/></td>
      
     <td><html:textarea name="comment" beanName="webVo" rows="3" styleId="description" req="false" maxlength="500" styleClass="TextAreaStyle" /></td>
     </tr>
     <tr></tr> <tr></tr> <tr></tr> <tr></tr>
     <tr>
     <td align="right" colspan=2>
		<html:button styleId="btnOk" name="btnSubmit" onclick="onClickSubmit()">
			<bean:message bundle="application" key="global.button.confirm" />
		</html:button>
		<html:button styleId="btnReject" name="btnReject" onclick="onClickReject()">
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
