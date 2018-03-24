
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%
   String contextPath = request.getContextPath();

   String actionSrc_deptCodeEdit="";
   
  actionSrc_deptCodeEdit=contextPath+"/projectpre/deptcode/codeEdit/DeptCodeEdit.jsp";
  
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <tiles:insert page="/layout/head.jsp">
       <tiles:put name="title" value=""/>
       <tiles:put name="jspName" value=""/>
     </tiles:insert>
     
     <script type="text/javascript" language="javascript">
       tabCount=2;
       var tabMap=new HashMap();      
       initMap();
       
       function initMap(){
         tabMap=new HashMap();
         tabMap.put("tabPanelId","tabDetail");
         tabMap.put("1","deptCodeEdit");
       }
       
       function onBodyLoad(){
         initButton();
         onTabChange();
       }
       
       function initButton(){
         addTabButtonByIndex(tabDetail,1,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData()");
         addTabButtonByIndex(tabDetail,1,"submitBtn","<bean:message bundle="application"  key="global.button.submit"/>","onSubmitData()");
       }
       
       function onTabChange(){
         try {
              renderTabButton(tabDetail,btnPanel);
              deptEditList.disableBtn();
             }catch(e){
             }
       }
       
       function onSaveData(){
         //invokeSingleMethod("onEditData()",'',tabMap);
          deptCodeEdit.onSaveData();
       }
       
       function onSubmitData(){
         //invokeSingleMethod("onSearchData()",'',tabMap);
          deptCodeEdit.onSubmitData();
       }
       
       
       function onDeleteData(){
         //invokeSingleMethod("onDeleteData",'',tabMap);
        deptEditList.onDeleteData();
       }
       function onAddData(){
         //invokeSingleMethod("onDeleteData",'',tabMap);
        deptEditList.onAddData();
      }
       function refreshIFrame(iframeid,url,typename){
         iframeid.onRefreshData(url,typename);
       }
       
       function onRefreshData(rid){
          if(rid==""||rid==null){
          deptCodeEdit.location="<%=contextPath%>/projectpre/Blank.jsp";
       }else{
          deptCodeEdit.location="<%=contextPath%>/dept/change/displayDeptApp.do?rid="+rid;
       }
         onTabChange();
          
       }
       
       function getSelectedTabIndex(){
         return getTabIndex(tabDetail);
       }
       
     </script>
  </head>
  
  <body BOTTOMMARGIN="0">
    <table cellspacing="0" cellpadding="1" height="100%" width="100%">
       <tr height="40%" >
		 <td class="wind">
		    <iframe 
				id="deptEditList" 
				src="<%=contextPath%>/dept/change/listAllDeptApp.do" 
				scrolling="yes" 
				height="100%" width="100%" 
				marginHeight="1" marginWidth="1" 
				frameborder="0"
			    >
			</iframe>	
		 </td>
	   </tr>
       <tr height="60%">
         <td>
           <html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%">
             <html:tabtitles selectedindex="1">
                <%--THE 1st tab--%>
                  <html:tabtitle width="120"><span class="tabs_title"><bean:message bundle="projectpre" key="deptCode.DeptEditShow.cardTitle.DeptEditInformation"/></span></html:tabtitle>               
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
                        id="deptCodeEdit"
                        name="deptCodeEdit"
                        SRC=""
                        tabindex="1"
                        width="100%" height="100%" frameborder="0"
                        MARGINWIDTH="1" MARGINHEIGHT="1" SCROLLING="auto">
                    </IFRAME>
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
