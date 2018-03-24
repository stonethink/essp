
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%
   String contextPath = request.getContextPath();
   

   String actionSrc_queryData="";
   String actionSrc_queryList="";
   String listPathEnd = "/projectpre/customer/infoquery/QueryList.jsp";
   
   actionSrc_queryData=contextPath+"/customer/query/customerQueryData.do";
   
   actionSrc_queryList = contextPath + listPathEnd;
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
         tabMap.put("1","queryData");
     
       }
       
       function onBodyLoad(){
         initButton();
         onTabChange();
       }
       
       function initButton(){
        
       }
       
       function onTabChange(){
         try {
              renderTabButton(tabDetail,btnPanel);
             }catch(e){
             }
       }
       
       function onSearchData(){
         queryData.onSearch();
       }
       
        function onSaveData(){
         queryData.onSave();     
       }
       function refreshIFrame(iframeid,url,typename){
         iframeid.onRefreshData(url,typename);
       }
       
       function onRefreshData(){
          addData.location="<%=contextPath%>/projectpre/customer/infoquery/QueryData.jsp";
     
       }
       
       function getSelectedTabIndex(){
         return getTabIndex(tabDetail);
       }
    
       
     </script>
  </head>
  
  <body BOTTOMMARGIN="0">
    <table cellspacing="0" cellpadding="1" height="100%" width="100%">
       
       
       <tr height="25%" >
		 <td class="wind">
		   
			     <IFRAME
                        id="queryData"
                        SRC="<%=actionSrc_queryData%>"
                        tabindex="1"
                        width="100%" height="100%" frameborder="0"
                        MARGINWIDTH="1" MARGINHEIGHT="1" SCROLLING="no">
                    </IFRAME>
		 </td>
	   </tr>
	   <tr height="75%">
         <td>
           <html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%">
             <html:tabtitles selectedindex="1">
                <%--THE 1st tab--%>
                  <html:tabtitle width="100"><span class="tabs_title"><bean:message bundle="projectpre" key="customer.CustomerInfoQueryDetail.cardTitle.QueryList"/></span></html:tabtitle>
                
             </html:tabtitles>
             
             <html:tabbuttons>
               <div id="btnPanel">
               </div>
             </html:tabbuttons>
             
             <html:tabcontents>
               <%--THE 1st Card--%>
               <html:tabcontent >
                 <html:outborder width="100%" height="99%">
                  <iframe 
		            id="queryList"
		            name="queryList" 
			     	src="" 
			    	scrolling="auto" 
			    	height="100%" width="100%" 
				    marginHeight="1" marginWidth="1" 
				    frameborder="0"
			      >
			     </iframe>	
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
