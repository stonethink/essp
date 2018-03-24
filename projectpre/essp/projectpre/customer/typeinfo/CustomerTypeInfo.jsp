
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="server.essp.projectpre.service.constant.Constant"%>
<%String contextPath = request.getContextPath();%>

<%
   
   String actionSrc_typeCodeSetting="";
   String actionSrc_areaCodeSetting="";
   
  actionSrc_typeCodeSetting=contextPath+"/projectpre/parameter/parameterCard.jsp?kind="+Constant.BUSINESS_TYPE;
  actionSrc_areaCodeSetting=contextPath+"/projectpre/parameter/parameterCard.jsp?kind="+Constant.COUNTRY_CODE;
   
  
  
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <TITLE><bean:message bundle="projectpre" key="projectCode.ProjectData.Title"/></TITLE>
    <tiles:insert page="/layout/head.jsp">
    <tiles:put name="title" value=" "/>
    <tiles:put name="jspName" value=""/>
    </tiles:insert>
    
   
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    
    <script language="JavaScript" type="text/javascript">
    
    tabCount=2;
    var tabMap=new HashMap();
    initMap();

    function initMap() {
        tabMap=new HashMap();
        tabMap.put("tabPanelId","tabDetail");
		tabMap.put("1","businessType");
		tabMap.put("2","countryCode");
    }
    function onAddData() {
        invokeSingleMethod("onAddData",'',tabMap);
	}
     function onDeleteData() {
        invokeSingleMethod("onDeleteData",'',tabMap);
	}
    
	function onBodyLoad() {
        initButton();
        onTabChange();
	}
    function refreshIFrame(iframeid,url,typename) {
        iframeid.onRefreshData(url,typename);
    }

    function onRefreshData() {
       
        projectType.location=actionSrc_typeCodeSetting;
        productType.location=actionSrc_areaCodeSetting;
        onTabChange();
    }
    function getSelectedTabIndex(){
        return getTabIndex(tabDetail);
    }

    function onTabChange() {
        try {
           renderTabButton(tabDetail,btnPanel);
           if(getTabIndex(tabDetail)==1){
             //alert("call projectType");
             businessType.parameterList.autoClickFirstRow();
             businessType.parameterList.disableBtn();
           }else if(getTabIndex(tabDetail)==2){
             //alert("call productType");
             countryCode.parameterList.autoClickFirstRow();
             countryCode.parameterList.disableBtn();
           }
        } catch(e) {
            //alertError( e );
        }
    }

    function initButton() {
      addTabButtonByIndex(tabDetail,1,"addBtn","<bean:message bundle="application"  key="global.button.new"/>","onAddData()");
      addTabButtonByIndex(tabDetail,2,"addBtn","<bean:message bundle="application"  key="global.button.new"/>","onAddData()");
      addTabButtonByIndex(tabDetail,1,"deleteBtn","<bean:message bundle="application"  key="global.button.delete"/>","onDeleteData()");
      addTabButtonByIndex(tabDetail,2,"deleteBtn","<bean:message bundle="application"  key="global.button.delete"/>","onDeleteData()");
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
         <html:tabtitle width="120"><span class="tabs_title"><bean:message bundle="projectpre" key="customer.CustomerTypeInfo.cardTitle.TypeCodeSetting"/></span></html:tabtitle>
         <%--THE 2nd tab--%>
         <html:tabtitle width="120"><span class="tabs_title"><bean:message bundle="projectpre" key="customer.CustomerTypeInfo.cardTitle.AreaCodeSetting"/></span></html:tabtitle>
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
                        id="businessType"
                        name="businessType"
                        SRC="<%=actionSrc_typeCodeSetting%>"
                        tabindex="1"
                        width="100%" height="100%"  frameborder="0"  
                        MARGINWIDTH="1" MARGINHEIGHT="0" SCROLLING="no">
                        </IFRAME>
                </html:outborder>
            </html:tabcontent>
            <%--The 2nd Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%" outLine="no">
                <IFRAME id="countryCode"
                        name="countryCode"
                        SRC="<%=actionSrc_areaCodeSetting%>"
                        tabindex="2"
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
