
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%
   String contextPath = request.getContextPath();

   String actionSrc_projectCodeCheck="";
   
  actionSrc_projectCodeCheck=contextPath+"/project/check/listProjectAppCheck.do";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 
     <tiles:insert page="/layout/head.jsp">
       <tiles:put name="title" value="Project Code Check"/>
       <tiles:put name="jspName" value=""/>
     </tiles:insert>
     
     <script type="text/javascript" language="javascript">
       tabCount=2;
       var tabMap=new HashMap();
       
       initMap();
       
       function initMap(){
         tabMap.put("tabPanelId","tabDetail");
         tabMap.put("1","projectCodeCheck");
         
       }
       
       function onBodyLoad(){
         initButton();
         onTabChange();
       }
       
       function initButton(){
         addTabButtonByIndex(tabDetail,1,"checkBtn","<bean:message bundle="application"  key="global.button.check"/>","onCheckData()");
         //addTabButtonByIndex(tabDetail,1,"addBtn","Add","onAddData()");
         //addTabButtonByIndex(tabDetail,1,"deleteBtn","Delete","onDeleteData()");
       }
       
       function onTabChange(){
         try {
              renderTabButton(tabDetail,btnPanel);
              projectCodeCheck.disableBtn();
             }catch(e){
             }
       }
       
       function onCheckData(){
         invokeSingleMethod("onCheckData",'',tabMap);
       }
       
       function onAddData(){
         invokeSingleMethod("onAddData",'',tabMap);
       }
       
       function onDeleteData(){
         invokeSingleMethod("onDeleteData",'',tabMap);
       }
       
       function refreshIFrame(iframeid,url,typename){
         iframeid.onRefreshData(url,typename);
       }
       
       function onRefreshData(){
          projectCodeCheck.location="<%=contextPath%>/project/check/listProjectAppCheck.do";
       }
       
       function getSelectedTabIndex(){
         return getTabIndex(tabDetail);
       }
       
     </script>
  </head>
  
  <body  BOTTOMMARGIN="0">
    <TABLE width="100%" height="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
       <tr >
         <td >
           <html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%">
             <html:tabtitles selectedindex="1">
                <%--THE 1st tab--%>
                  <html:tabtitle width="110"><span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.ProjectCodeCheck.cardTitle.ProjectCodeCheck"/></span></html:tabtitle>
             </html:tabtitles>
             
             <html:tabbuttons>
               <div id="btnPanel">
               </div>
             </html:tabbuttons>
             
             <html:tabcontents>
               <%--THE 1st Card--%>
               <html:tabcontent >
                 <html:outborder width="100%" height="100%" outLine="yes">
                    <IFRAME
                        id="projectCodeCheck"
                        name="projectCodeCheck"
                        SRC="<%=actionSrc_projectCodeCheck%>"
                        tabindex="1" 
                        width="100%" height="100%" frameborder="0"
                        MARGINWIDTH="1" MARGINHEIGHT="0" SCROLLING="yes">
                    </IFRAME>
                 </html:outborder>
               </html:tabcontent> 
             </html:tabcontents>
           </html:tabpanel>
         </td>
       </tr>
    </table>
  </body>
  <script language="JavaScript" type="text/javascript">
     onBodyLoad();
</script>
</html>
