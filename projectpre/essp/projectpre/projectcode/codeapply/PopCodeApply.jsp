
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%
   String contextPath = request.getContextPath();

   String actionSrc_masterData="";
   String actionSrc_technicalData="";
   String actionSrc_projectData="";
   String status = "";
   String confirmCheck = "";
   String appType = request.getParameter("appType");
   if(request.getParameter("status")!=null){
    status = request.getParameter("status");
    }
    if(request.getParameter("confirmCheck")!=null){
    confirmCheck = request.getParameter("confirmCheck");
   }
   if(appType.equals("check")) {
    if(confirmCheck.equals("true")){
     String acntId = request.getParameter("acntId");
     String str = acntId+"---ACNT";
     actionSrc_masterData=contextPath+"/project/apply/previewProjectAcntApp.do?acntId="+acntId+"&accessType=create&appType=confirm&confirmCheck=true";
     actionSrc_technicalData=contextPath+"/project/apply/previewTechInfoApp.do?accessType=create&view="+str;
     actionSrc_projectData=contextPath+"/project/apply/previewCustomerInfoApp.do?view="+str;
    } else {
     String rid = request.getParameter("rid");
     actionSrc_masterData=contextPath+"/project/apply/previewProjectAcntApp.do?rid="+rid+"&accessType=edit&appType=check";
     actionSrc_technicalData=contextPath+"/project/apply/previewTechInfoApp.do?rid="+rid;
     actionSrc_projectData=contextPath+"/project/apply/previewCustomerInfoApp.do?rid="+rid+"&appType=check";
    }
  } else if(appType.equals("confirm")){ 
     String acntId = request.getParameter("acntId");
     String str = acntId+"---ACNT";
     actionSrc_masterData=contextPath+"/project/apply/previewProjectAcntApp.do?acntId="+acntId+"&accessType=create&appType=confirm&confirmStatus="+status;
     actionSrc_technicalData=contextPath+"/project/apply/previewTechInfoApp.do?accessType=create&view="+str;
     actionSrc_projectData=contextPath+"/project/apply/previewCustomerInfoApp.do?view="+str;
  }else {
     String isFinance = request.getParameter("isFinance");
     actionSrc_masterData=contextPath+"/project/apply/previewAddProjectAcntApp.do?accessType=create&appType="+appType+"&isFinance=" + isFinance;
     actionSrc_technicalData=contextPath+"/project/apply/previewAddTechInfoApp.do";
     actionSrc_projectData=contextPath+"/projectpre/projectcode/codeapply/ProjectData.jsp";
 
  }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <%if(appType.equals("change")){%>
       <TITLE><bean:message bundle="projectpre" key="projectCode.ProjectCodeChange.cardTitle.ProjectCodeApplye"/></TITLE>
  <%}else if(appType.equals("check")){%>
     <TITLE><bean:message bundle="projectpre" key="projectCode.ProjectCodeCheck.cardTitle.ProjectCodeCheck"/></TITLE>
  <%}else if(appType.equals("confirm")){%>
     <TITLE><bean:message bundle="projectpre" key="projectCode.PopCodeConfirm.ProjectCodeConfirmApply"/></TITLE>
  <%}else{%>
      <TITLE><bean:message bundle="projectpre" key="projectCode.ProjectCodeApply.cardTitle.ProjectCodeApply"/></TITLE>
   <%}%>
     <tiles:insert page="/layout/head.jsp">
       <tiles:put name="title" value="Project Code Apply"/>
       <tiles:put name="jspName" value=""/>
     </tiles:insert>
     
     <script type="text/javascript" language="javascript">
       tabCount=2;
       var tabMap=new HashMap();
       var appType= "<%=appType%>";
       var status = "<%=status%>";
       initMap();
       
       function initMap(){
         tabMap=new HashMap();
         tabMap.put("tabPanelId","tabDetail");
         tabMap.put("1","masterData");
         tabMap.put("2","technicalData");
         tabMap.put("3","projectData");
       }
       
       function onBodyLoad(){   
         initButton();
         onTabChange();
    
         
       }
       
       function initButton(){
      
       if(appType=="change") {
         addTabButtonByIndex(tabDetail,1,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData()");
         addTabButtonByIndex(tabDetail,1,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onSubmitData()");
         addTabButtonByIndex(tabDetail,1,"cancelBtn","<bean:message bundle="application" key="global.button.cancel" />","onCancel()");
         addTabButtonByIndex(tabDetail,2,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData1()");
         addTabButtonByIndex(tabDetail,2,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onSubmitData1()");
         addTabButtonByIndex(tabDetail,2,"cancelBtn","<bean:message bundle="application" key="global.button.cancel" />","onCancel1()");
         addTabButtonByIndex(tabDetail,3,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData1()");
         addTabButtonByIndex(tabDetail,3,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onSubmitData1()");
         addTabButtonByIndex(tabDetail,3,"cancelBtn","<bean:message bundle="application" key="global.button.cancel" />","onCancel1()");
         } else if(appType=="add"){
         addTabButtonByIndex(tabDetail,1,"copyBtn","<bean:message bundle="application"  key="global.button.copy"/>","onCopyData()");
         addTabButtonByIndex(tabDetail,1,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData()");
         addTabButtonByIndex(tabDetail,1,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onSubmitData()");
         addTabButtonByIndex(tabDetail,1,"cancelBtn","<bean:message bundle="application" key="global.button.cancel" />","onCancel()");
         addTabButtonByIndex(tabDetail,2,"copyBtn","<bean:message bundle="application"  key="global.button.copy"/>","onCopyData1()");
         addTabButtonByIndex(tabDetail,2,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData1()");
         addTabButtonByIndex(tabDetail,2,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onSubmitData1()");
         addTabButtonByIndex(tabDetail,2,"cancelBtn","<bean:message bundle="application" key="global.button.cancel" />","onCancel1()");
         addTabButtonByIndex(tabDetail,3,"copyBtn","<bean:message bundle="application"  key="global.button.copy"/>","onCopyData1()");
         addTabButtonByIndex(tabDetail,3,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData1()");
         addTabButtonByIndex(tabDetail,3,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onSubmitData1()");
         addTabButtonByIndex(tabDetail,3,"cancelBtn","<bean:message bundle="application" key="global.button.cancel" />","onCancel1()");
         } else if(appType=="confirm"){
         addTabButtonByIndex(tabDetail,1,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onConfirmSubmit()");
         addTabButtonByIndex(tabDetail,1,"cancelBtn","<bean:message bundle="application" key="global.button.cancel" />","onCancel()");
         addTabButtonByIndex(tabDetail,2,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onConfirmSubmit1()");
         addTabButtonByIndex(tabDetail,2,"cancelBtn","<bean:message bundle="application" key="global.button.cancel" />","onCancel1()");
         addTabButtonByIndex(tabDetail,3,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onConfirmSubmit1()");
         addTabButtonByIndex(tabDetail,3,"cancelBtn","<bean:message bundle="application" key="global.button.cancel" />","onCancel1()");
         } else {
           addTabButtonByIndex(tabDetail,1,"confirmBtn","<bean:message bundle="application" key="global.button.confirm" />","onConfirm()");
           addTabButtonByIndex(tabDetail,1,"rejectBtn","<bean:message bundle="application" key="global.button.reject" />","onReject()");
           addTabButtonByIndex(tabDetail,1,"cancelBtn","<bean:message bundle="application" key="global.button.cancel" />","onCancel()");
           addTabButtonByIndex(tabDetail,2,"confirmBtn","<bean:message bundle="application" key="global.button.confirm" />","onConfirm1()");
           addTabButtonByIndex(tabDetail,2,"rejectBtn","<bean:message bundle="application" key="global.button.reject" />","onReject1()");
           addTabButtonByIndex(tabDetail,2,"cancelBtn","<bean:message bundle="application" key="global.button.cancel" />","onCancel1()");
           addTabButtonByIndex(tabDetail,3,"confirmBtn","<bean:message bundle="application" key="global.button.confirm" />","onConfirm1()");
           addTabButtonByIndex(tabDetail,3,"rejectBtn","<bean:message bundle="application" key="global.button.reject" />","onReject1()");
           addTabButtonByIndex(tabDetail,3,"cancelBtn","<bean:message bundle="application" key="global.button.cancel" />","onCancel1()");
         }
       
       }
       
       function onTabChange(){
         try {
              renderTabButton(tabDetail,btnPanel);
              if(appType=="add"){
              copyBtn.style.width="100px";
              }
              if(appType=="confirm"&&status=="Submitted"){
              submitBtn.disabled=true;
              }
              if(appType=="confirm"||appType=="check"){
              technicalData.disableTech();
              projectData.disableCustomer();
              }
             }catch(e){
             }
       }
       function onConfirmSubmit(){
   
          invokeSingleMethod("onConfirmSubmit",'',tabMap);
       }
       function onConfirmSubmit1(){
   		  masterData.onConfirmSubmit();
       }
       function onSaveData(){
          invokeSingleMethod("onSaveData",'',tabMap);
       }
       function onSaveData1(){
      	  masterData.onSaveData();
       }
       function onSubmitData(){
         invokeSingleMethod("onSubmitData",'',tabMap);
       }
        function onSubmitData1(){
         masterData.onSubmitData();
       }
        function onConfirm(){
         invokeSingleMethod("onConfirm",'',tabMap);
       }
       function onConfirm1(){
       	 masterData.onConfirm();
       }
        function onCopyData(){
         invokeSingleMethod("onCopyData",'',tabMap);
       }
        function onCopyData1(){
         masterData.onCopyData();
       }
        function onCancel(){
         invokeSingleMethod("onCancel",'',tabMap);
       }
        function onCancel1(){
         masterData.onCancel();
       }
        function onReject(){
         invokeSingleMethod("onReject",'',tabMap);
       }
       function onReject1(){
      	 masterData.onReject();
       }
       function refreshIFrame(iframeid,url,typename){
         iframeid.onRefreshData(url,typename);
       }
       
       function onRefreshData(){
          masterData.location="<%=contextPath%>/projectpre/projectcode/codeapply/masterData.jsp";
          technicalData.location="<%=contextPath%>/projectpre/projectcode/codeapply/technicalData.jsp";
          projectData.location="<%=contextPath%>/projectpre/projectcode/codeapply/projectData.jsp";
       }
       
       function getSelectedTabIndex(){
         return getTabIndex(tabDetail);
       }
       
     </script>
  </head>
  
  <body  BOTTOMMARGIN="0" topmargin="0" leftmargin="0" rightmargin="0">
    <table cellspacing="0" cellpadding="1" height="100%" width="100%">
       <tr height="100%">
         <td height="100%">
           <html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%">
             <html:tabtitles selectedindex="1">
                <%--THE 1st tab--%>
                  <html:tabtitle width="100"><span class="tabs_title"><bean:message bundle="projectpre"  key="projectCode.CodeApplyDetail.cardTitle.MasterData"/></span></html:tabtitle>
                <%--THE 2nd tab--%>
                  <html:tabtitle width="120"><span class="tabs_title"><bean:message bundle="projectpre"  key="projectCode.CodeApplyDetail.cardTitle.TechnicalData"/></span></html:tabtitle>
                <%--THE 3nd tab--%>
                  <html:tabtitle width="100"><span class="tabs_title"><bean:message bundle="projectpre"  key="projectCode.CodeApplyDetail.cardTitle.CustomerData"/></span></html:tabtitle>
             </html:tabtitles>
             
             <html:tabbuttons>
               <div id="btnPanel">
               </div>
             </html:tabbuttons>
             
             <html:tabcontents>
               <%--THE 1st Card--%>
               <html:tabcontent >
                 <html:outborder width="100%" height="100%" >
                    <IFRAME
                        id="masterData"
                        name="masterData"
                        SRC="<%=actionSrc_masterData%>"
                        tabindex="1"
                        width="100%" height="100%" frameborder="0"
                        MARGINWIDTH="1" MARGINHEIGHT="1" SCROLLING="auto">
                    </IFRAME>
                 </html:outborder>
               </html:tabcontent> 
               <%--THE 2st Card--%>
               <html:tabcontent>
                 <html:outborder width="100%" height="100%" >
                   <IFrame
                     id="technicalData"
                     name="technicalData"
                     src="<%=actionSrc_technicalData%>"
                     tabindex="2"
                     width="100%" height="100%" frameborder="0" 
                     marginwidth="1" marginheight="0" SCROLLING="auto">
                   </IFrame>
                 </html:outborder>
               </html:tabcontent>
               <%--THE 3st Card--%>
               <html:tabcontent>
                 <html:outborder width="100%" height="100%">
                   <IFrame
                     id="projectData"
                     name="projectData"
                     src="<%=actionSrc_projectData%>"
                     tabindex="3"
                     width="100%" height="100%" frameborder="0" 
                     marginwidth="1" marginheight="0" SCROLLING="auto">
                   </IFrame>
                 </html:outborder>
               </html:tabcontent>
             </html:tabcontents>
           </html:tabpanel>
         </td>
       </tr>
    </table>
    <script language="JavaScript" type="text/JavaScript">
      onBodyLoad();
</script>
  </body>
</html>
