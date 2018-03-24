<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%
   String contextPath = request.getContextPath();

   String actionSrc_customerInfoChangeDetail="";
   
  actionSrc_customerInfoChangeDetail=contextPath+"/projectpre/customer/infochange/CustomerInfoChangeDetail.jsp";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <tiles:insert page="/layout/head.jsp">
       <tiles:put name="title" value=" "/>
       <tiles:put name="jspName" value=""/>
     </tiles:insert>
      <script type="text/javascript" language="javascript">
       tabCount=2;
       var tabMap=new HashMap();
       
       initMap();
       
       function initMap(){
         tabMap=new HashMap();
         tabMap.put("tabPanelId","tabDetail");
         tabMap.put("1","customerInfoChangeDetail");
   
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
              customerInfoChangeDetail.changeList.disableBtn();
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
          projectCodeApply.location="<%=contextPath%>/projectpre/customer/infoadd/CustomerInfoChangeDetail.jsp";
       }
       
       function getSelectedTabIndex(){
         return getTabIndex(tabDetail);
       }
       
     </script>
  </head>
  
  <body BOTTOMMARGIN="0">
    <TABLE width="100%" height="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
       <tr >
         <td >
           <html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%">
             <html:tabtitles selectedindex="1">
                <%--THE 1st tab--%>
                  <html:tabtitle width="150"><span class="tabs_title"><bean:message bundle="projectpre" key="customer.CustomerInfoChange.cardTitle.AppliedCustomerCode"/></span></html:tabtitle>
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
                        id="customerInfoChangeDetail"
                        name="customerInfoChangeDetail"
                        SRC="<%=actionSrc_customerInfoChangeDetail%>"
                        tabindex="1"
                        width="100%" height="100%" frameborder="0"
                        MARGINWIDTH="1" MARGINHEIGHT="0" SCROLLING="no">
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
