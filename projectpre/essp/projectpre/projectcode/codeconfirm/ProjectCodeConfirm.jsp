
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%
String contextPath = request.getContextPath();
%>

<%
   
   String actionSrc_projectCodeFrame="";
   
   String pathEnd="/projectpre/projectcode/codeconfirm/ProjectCodeFrame.jsp";
 
   actionSrc_projectCodeFrame=contextPath + pathEnd; 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <tiles:insert page="/layout/head.jsp">
    <tiles:put name="title" value="Project Code Confirm"/>
    <tiles:put name="jspName" value=""/>
    </tiles:insert>
    
  
    <script language="JavaScript" type="text/javascript">
    
     tabCount=2;
       var tabMap=new HashMap();
      
       initMap();
       
       function initMap(){
         tabMap=new HashMap();
         tabMap.put("tabPanelId","tabDetail");
         tabMap.put("1","projectCodeFrame");
   
       }
       
       function onBodyLoad(){
         initButton();
         onTabChange();
       }
       
    function initButton() {
     addTabButtonByIndex(tabDetail,1,"searchBtn","<bean:message bundle="application"  key="global.button.search"/>","onSearchData()");
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
       
        function refreshIFrame(iframeid,url,typename){
         iframeid.onRefreshData(url,typename);
       }
       
       function onRefreshData(){
          projectCodeApply.location="<%=contextPath%>";
       }
       
       function getSelectedTabIndex(){
         return getTabIndex(tabDetail);
       }
       
	</script>
    
    
  </head>
  
  <body  BOTTOMMARGIN="0">
    
    <table cellspacing="0" cellpadding="0" height="100%" width="100%" >
  <tr height="100%">
    <td>
<html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%" >
	<html:tabtitles  selectedindex="1"  >
         <%--THE 1st tab--%>
         <html:tabtitle width="120"><span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.ProjectCodeComfirm.cardTitle.ProjectConfirmApply"/></span></html:tabtitle>
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
                        id="projectCodeFrame"
                        name="projectCodeFrame"
                        SRC="<%=actionSrc_projectCodeFrame%>"
                        tabindex="1"
                        width="100%" height="100%"  frameborder="0"  
                        MARGINWIDTH="1" MARGINHEIGHT="0" SCROLLING="auto">
                        </IFRAME>
                </html:outborder>
            </html:tabcontent>
	</html:tabcontents>
</html:tabpanel>
    </td>
  </tr>

</table>
<script language="JavaScript" type="text/javascript">
    onBodyLoad();
</script>
    
  </body>
</html>
