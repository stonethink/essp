
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%
   String contextPath = request.getContextPath();

   String actionSrc_projectCodeApply="";
   
   String isFinance = request.getParameter("isFinance");
   isFinance = (isFinance != null) ? isFinance : "";
   
   actionSrc_projectCodeApply=contextPath+"/projectpre/projectcode/codeapply/CodeApplyDetail.jsp?isFinance=" + isFinance;
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
         tabMap.put("tabPanelId","tabDetail");
         tabMap.put("1","projectCodeApply");
         
       }
       
       function onBodyLoad(){
         initButton();
         onTabChange();
       }
       
       function initButton(){
         
         addTabButtonByIndex(tabDetail,1,"addBtn","<bean:message bundle="application"  key="global.button.add"/>","onAddData()");
         addTabButtonByIndex(tabDetail,1,"deleteBtn","<bean:message bundle="application"  key="global.button.delete"/>","onDeleteData()");
       }
       
       function onTabChange(){
         try {
              renderTabButton(tabDetail,btnPanel);
             if(getTabIndex(tabDetail)==1){
              projectCodeApply. masterData.disableBtn();
              projectCodeApply.applyRecordList.disableBtn();
             }
             }catch(e){
             }
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
          projectCodeApply.location="<%=contextPath%>/projectpre/projectcode/codeapply/CodeApplyDetail.jsp";
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
                  <html:tabtitle width="110">
                     <span class="tabs_title">
                       <bean:message bundle="projectpre"  key="projectCode.ProjectCodeApply.cardTitle.ProjectCodeApply"/>
                     </span>
                  </html:tabtitle>
             </html:tabtitles>
             
             <html:tabbuttons>
               <div id="btnPanel">
               </div>
             </html:tabbuttons>
             
             <html:tabcontents>
               <%--THE 1st Card--%>
               <html:tabcontent >
                 <html:outborder width="100%" height="100%" outLine="no">
                    <IFRAME
                        id="projectCodeApply"
                        name="projectCodeApply"
                        SRC="<%=actionSrc_projectCodeApply%>"
                        tabindex="1"
                        width="100%" height="100%" frameborder="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
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
