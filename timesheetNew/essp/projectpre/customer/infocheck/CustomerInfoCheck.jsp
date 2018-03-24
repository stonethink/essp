
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%
String contextPath = request.getContextPath();
%>

<%
   
   String actionSrc_customerInfoCheckList="";
   
  actionSrc_customerInfoCheckList=contextPath+"/customer/check/listAllApp.do";
  
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <tiles:insert page="/layout/head.jsp">
    <tiles:put name="title" value=" "/>
    <tiles:put name="jspName" value=""/>
    </tiles:insert>
    
    <script language="JavaScript" type="text/javascript">
    
    tabCount=2;
    var tabMap=new HashMap();
    initMap();

    function initMap() {
        tabMap=new HashMap();
        tabMap.put("tabPanelId","tabDetail");
		tabMap.put("1","customerInfoSetting");
		
    }
 
	function onBodyLoad() {
        initButton();
        onTabChange();
	}
    function refreshIFrame(iframeid,url,typename) {
        iframeid.onRefreshData(url,typename);
    }

    function onRefreshData() {
       
            
        onTabChange();
    }
    function getSelectedTabIndex(){
        return getTabIndex(tabDetail);
    }

    function onTabChange() {
        try {
        
           renderTabButton(tabDetail,btnPanel);
           customerInfoSetting.disableBtn();
        } catch(e) {
            //alertError( e );
        }
    }
  
      function onCheckData(){
         invokeSingleMethod("onCheckData",'',tabMap);
       }
       function initButton(){
         addTabButtonByIndex(tabDetail,1,"checkBtn","<bean:message bundle="application"  key="global.button.check"/>","onCheckData()");
         
       }
	</script>
    
    
  </head>
  
  <body    BOTTOMMARGIN="0">
    
    <table cellspacing="0" cellpadding="0" height="100%" width="100%" >
  <tr height="100%">
    <td>
<html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%" >
	<html:tabtitles  selectedindex="1"  >
         <%--THE 1st tab--%>
         <html:tabtitle width="145"><span class="tabs_title"><bean:message bundle="projectpre" key="customer.CustomerInfoCheck.cardTitle.Clientinfomationchecking"/></span></html:tabtitle>      
    </html:tabtitles>
    <html:tabbuttons>
    	<div id="btnPanel">
    	</div>
    </html:tabbuttons>
	<html:tabcontents>
            <%--THE 1st Card--%>
            <html:tabcontent >
                <html:outborder width="100%" height="100%">
                        <IFRAME
                        id="customerInfoSetting"
                        name="customerInfoSetting"
                        SRC="<%=actionSrc_customerInfoCheckList%>"
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
    
  </body>
  <script language="JavaScript" type="text/javascript">
onBodyLoad();
</script>
</html>
