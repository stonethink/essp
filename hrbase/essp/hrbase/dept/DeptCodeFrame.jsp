<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<%
   String contextPath = request.getContextPath();
   
   String pathEnd = "/hrbase/dept/DeptCodeDetail.jsp";
   String actionSrc_deptQueryDetail="";

   actionSrc_deptQueryDetail=contextPath + pathEnd; 
  
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
         tabMap.put("1","deptQueryDetail");
   
       }
       
       function onBodyLoad(){
         initButton();
         onTabChange();
       }
       
       function initButton(){
         addTabButtonByIndex(tabDetail,1,"addBtn","<bean:message bundle="application" key="global.button.new"/>","onAddData()");
         addTabButtonByIndex(tabDetail,1,"searchBtn","<bean:message bundle="application" key="global.button.search"/>","onSearchData()");
       }
       
       function onTabChange(){
         try {
              renderTabButton(tabDetail,btnPanel);
             }catch(e){
             }
       }
       
       function onSearchData(){
         invokeSingleMethod("onSearchData",'',tabMap);
       }
 
       function onAddData(){
         invokeSingleMethod("onAddData",'',tabMap);
       }
       function refreshIFrame(iframeid,url,typename){
         iframeid.onRefreshData(url,typename);
       }
       
       function onRefreshData(){
          projectCodeApply.location="<%=contextPath%>/hrbase/dept/DeptQuery.jsp";
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
                  <html:tabtitle width="120"><span class="tabs_title"><bean:message bundle="hrbase" key="hrbase.dept.deptquery"/></span></html:tabtitle>
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
                        id="deptQueryDetail"
                        name="deptQueryDetail"
                        SRC="<%=actionSrc_deptQueryDetail%>"
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