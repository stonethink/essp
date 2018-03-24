
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%
   String contextPath = request.getContextPath();

   String actionSrc_masterData="";
   String actionSrc_technicalData="";
   String actionSrc_projectData="";
   
  actionSrc_masterData=contextPath+"/projectpre/projectcode/codechange/MasterData.jsp";
  actionSrc_technicalData=contextPath+"/projectpre/projectcode/codechange/TechnicalData.jsp";
  actionSrc_projectData=contextPath+"/projectpre/projectcode/codeapply/ProjectData.jsp";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <tiles:insert page="/layout/head.jsp">
       <tiles:put name="title" value="Project Code Apply"/>
       <tiles:put name="jspName" value=""/>
     </tiles:insert>
     
     <script type="text/javascript" language="javascript">
       tabCount=2;
       var tabMap=new HashMap();
       
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
          addTabButtonByIndex(tabDetail,1,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData()");
          addTabButtonByIndex(tabDetail,1,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onSubmitData()");
          addTabButtonByIndex(tabDetail,2,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData1()");
          addTabButtonByIndex(tabDetail,2,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onSubmitData1()");
          addTabButtonByIndex(tabDetail,3,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData1()");
          addTabButtonByIndex(tabDetail,3,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onSubmitData1()");
     
       }
       
       function onTabChange(rowObj){
         try {
              renderTabButton(tabDetail,btnPanel);
              masterData.disableBtn('change');
              changeAppList.disableBtn(rowObj);
             }catch(e){
             }
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
         function onAddData(){
       
         changeAppList.onAddData();
       }
       
       function onDeleteData(){
         changeAppList.onDeleteData()
       }
       
       
       function refreshIFrame(iframeid,url,typename){
         iframeid.onRefreshData(url,typename);
       }
       
       function onRefreshData(rid, rowObj){
         if(rid==""||rid==null){
          masterData.location="<%=contextPath%>/projectpre/Blank.jsp";
          technicalData.location="<%=contextPath%>/projectpre/Blank.jsp";
          projectData.location="<%=contextPath%>/projectpre/Blank.jsp";
          } else {
          
          masterData.location="<%=contextPath%>/project/apply/previewProjectAcntApp.do?rid="+rid+"&accessType=edit&appType=change";
          technicalData.location="<%=contextPath%>/project/apply/previewTechInfoApp.do?rid="+rid;
          projectData.location="<%=contextPath%>/project/apply/previewCustomerInfoApp.do?rid="+rid;
          }
         
          
          onTabChange(rowObj);
       
       }
      
       function getSelectedTabIndex(){
         return getTabIndex(tabDetail);
       }
       
     </script>
  </head>
  
  <body  BOTTOMMARGIN="0">
    <table cellspacing="0" cellpadding="1" height="100%" width="100%">
       <tr height="28%" >
		 <td class="wind">
		    <iframe 
				id="changeAppList" 
				src="<%=contextPath%>/project/apply/listAllProjectAcntApp.do?appType=change" 
				scrolling="yes" 
				height="100%" width="100%" 
				marginHeight="1" marginWidth="1" 
				frameborder="0"
			    >
			</iframe>	
		 </td>
	   </tr>
       <tr height="72%">
         <td>
           <html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%">
             <html:tabtitles selectedindex="1">
                <%--THE 1st tab--%>
                  <html:tabtitle width="100"><span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.CodeChangeDetail.cardTitle.MasterData"/></span></html:tabtitle>
                <%--THE 2nd tab--%>
                  <html:tabtitle width="120"><span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.CodeChangeDetail.cardTitle.TechnicalData"/></span></html:tabtitle>
                <%--THE 3nd tab--%>
                  <html:tabtitle width="100"><span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.CodeApplyDetail.cardTitle.CustomerData"/></span></html:tabtitle>
             </html:tabtitles>
             
             <html:tabbuttons>
               <div id="btnPanel">
               </div>
             </html:tabbuttons>
             
             <html:tabcontents>
               <%--THE 1st Card--%>
               <html:tabcontent >
                 <html:outborder width="100%" height="99%">
                    <IFRAME
                        id="masterData"
                        name="masterData"
                        SRC=""
                        tabindex="1"
                        width="100%" height="100%" frameborder="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="auto">
                    </IFRAME>
                 </html:outborder>
               </html:tabcontent> 
               <%--THE 2st Card--%>
               <html:tabcontent>
                 <html:outborder width="100%" height="99%">
                   <IFrame
                     id="technicalData"
                     name="technicalData"
                     src=""
                     tabindex="2"
                     width="100%" height="100%" frameborder="0" 
                     marginwidth="0" marginheight="0" SCROLLING="auto">
                   </IFrame>
                 </html:outborder>
               </html:tabcontent>
               <%--THE 3st Card--%>
               <html:tabcontent>
                 <html:outborder width="100%" height="99%">
                   <IFrame
                     id="projectData"
                     name="projectData"
                     src=""
                     tabindex="3"
                     width="100%" height="100%" frameborder="0" 
                     marginwidth="0" marginheight="0" SCROLLING="auto">
                   </IFrame>
                 </html:outborder>
               </html:tabcontent>
             </html:tabcontents>
           </html:tabpanel>
         </td>
       </tr>
    </table>
    <script type="text/javascript" language="javascript">
       onBodyLoad();
    </script>
  </body>
</html>
