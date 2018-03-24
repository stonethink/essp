
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="server.essp.projectpre.service.constant.Constant"%>
<%String contextPath = request.getContextPath();%>

<%
			String actionSrc_projectType = "";
			String actionSrc_productType = "";
			String actionSrc_productAttribute = "";
			String actionSrc_workItem = "";
			String actionSrc_techDocmain = "";
			String actionSrc_originalLanguage = "";
			String actionSrc_translation = "";

			actionSrc_projectType = contextPath
					+ "/projectpre/parameter/parameterCard.jsp?kind="+Constant.PROJECT_TYPE;
			actionSrc_productType = contextPath
					+ "/projectpre/parameter/parameterCard.jsp?kind="+Constant.PRODUCT_TYPE;
			actionSrc_productAttribute = contextPath
					+ "/projectpre/parameter/parameterCard.jsp?kind="+Constant.PRODUCT_ATTRIBUTE;
			actionSrc_workItem = contextPath
					+ "/projectpre/parameter/parameterCard.jsp?kind="+Constant.WORK_ITEM ;
			actionSrc_techDocmain = contextPath
					+ "/projectpre/parameter/parameterCard.jsp?kind="+Constant.TECHNICAL_DOMAIN ;
			actionSrc_originalLanguage = contextPath
					+ "/projectpre/parameter/parameterCard.jsp?kind="+Constant.ORIGINAL_LANGUAGE;
			actionSrc_translation = contextPath
					+ "/projectpre/parameter/parameterCard.jsp?kind="+Constant.TRANSLATION_LANGUANGE ;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <TITLE><bean:message bundle="projectpre" key="projectCode.ProductMaintenace.Title"/></TITLE>
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
        tabMap.put("tabPanelId","tabDetail");
		tabMap.put("1","projectType");
		tabMap.put("2","productType");
		tabMap.put("3","productAttribute");
		tabMap.put("4","workItem");
		tabMap.put("5","techDocmain");
		tabMap.put("6","originalLanguage");
		tabMap.put("7","translation");
    }
    function onAddData() {
        invokeSingleMethod("onAddData",'',tabMap);
	}
    function onDeleteData(){
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
       
        projectType.location=actionSrc_projectType;
        productType.location=actionSrc_productType;
        productAttribute.location=actionSrc_productAttribute;
        workItem.location=actionSrc_workItem;
        techDocmain.location=actionSrc_techDocmain;
        originalLanguage.location=actionSrc_originalLanguage;
        translation.location=actionSrc_translation;
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
             projectType.parameterList.autoClickFirstRow();
             projectType.parameterList.disableBtn("projectType");
           }else if(getTabIndex(tabDetail)==2){
             //alert("call productType");
             productType.parameterList.autoClickFirstRow();
             productType.parameterList.disableBtn("projectType");
           }else if(getTabIndex(tabDetail)==3){
             //alert("call productAttribute");
             productAttribute.parameterList.autoClickFirstRow();
             productAttribute.parameterList.disableBtn("projectType");
           }else if(getTabIndex(tabDetail)==4){
             //alert("call workItem");
             workItem.parameterList.autoClickFirstRow();
             workItem.parameterList.disableBtn("projectType");
           }else if(getTabIndex(tabDetail)==5){
             //alert("call techDocmain");
             techDocmain.parameterList.autoClickFirstRow();
             techDocmain.parameterList.disableBtn("projectType");
           }else if(getTabIndex(tabDetail)==6){
             //alert("call originalLanguage");
             originalLanguage.parameterList.autoClickFirstRow();
             originalLanguage.parameterList.disableBtn("projectType");
           }else if(getTabIndex(tabDetail)==7){
             //alert("call translation");
             translation.parameterList.autoClickFirstRow();
             translation.parameterList.disableBtn("projectType");
           }
           
        } catch(e) {
            //alertError( e );
        }
    }

    function initButton() {
      addTabButtonByIndex(tabDetail,1,"addBtn","<bean:message bundle="application"  key="global.button.new"/>","onAddData()");
      addTabButtonByIndex(tabDetail,1,"deleteBtn","<bean:message bundle="application"  key="global.button.delete"/>","onDeleteData()");
      addTabButtonByIndex(tabDetail,2,"addBtn","<bean:message bundle="application"  key="global.button.new"/>","onAddData()");
      addTabButtonByIndex(tabDetail,2,"deleteBtn","<bean:message bundle="application"  key="global.button.delete"/>","onDeleteData()");
      addTabButtonByIndex(tabDetail,3,"addBtn","<bean:message bundle="application"  key="global.button.new"/>","onAddData()");
      addTabButtonByIndex(tabDetail,3,"deleteBtn","<bean:message bundle="application"  key="global.button.delete"/>","onDeleteData()");
      addTabButtonByIndex(tabDetail,4,"addBtn","<bean:message bundle="application"  key="global.button.new"/>","onAddData()");
      addTabButtonByIndex(tabDetail,4,"deleteBtn","<bean:message bundle="application"  key="global.button.delete"/>","onDeleteData()");
      addTabButtonByIndex(tabDetail,5,"addBtn","<bean:message bundle="application"  key="global.button.new"/>","onAddData()");
      addTabButtonByIndex(tabDetail,5,"deleteBtn","<bean:message bundle="application"  key="global.button.delete"/>","onDeleteData()");
      addTabButtonByIndex(tabDetail,6,"addBtn","<bean:message bundle="application"  key="global.button.new"/>","onAddData()");
      addTabButtonByIndex(tabDetail,6,"deleteBtn","<bean:message bundle="application"  key="global.button.delete"/>","onDeleteData()");
      addTabButtonByIndex(tabDetail,7,"addBtn","<bean:message bundle="application"  key="global.button.new"/>","onAddData()");
      addTabButtonByIndex(tabDetail,7,"deleteBtn","<bean:message bundle="application"  key="global.button.delete"/>","onDeleteData()");
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
         <html:tabtitle width="60"><span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.productMaintenance.cardTitle.ProjectType"/></span></html:tabtitle>
         <%--THE 2nd tab--%>
         <html:tabtitle width="70"><span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.productMaintenance.cardTitle.ProductType"/></span></html:tabtitle>
         <%--THE 3st tab--%>
         <html:tabtitle width="100"><span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.productMaintenance.cardTitle.ProductAttribute"/></span></html:tabtitle>
         <%--THE 4th tab--%>
         <html:tabtitle width="60"><span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.productMaintenance.cardTitle.WorkItem"/></span></html:tabtitle>
         <%--THE 5th tab--%>
         <html:tabtitle width="100"><span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.productMaintenance.cardTitle.TechDomain"/></span></html:tabtitle>
         <%--THE 6th tab--%>
         <html:tabtitle width="100"><span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.LanguageSupport.cardTitle.OriginalLanguage"/></span></html:tabtitle> 
         <%--THE 7th tab--%>
         <html:tabtitle width="70"><span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.LanguageSupport.cardTitle.Translation"/></span></html:tabtitle>        
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
                        id="projectType"
                        name="projectType"
                        SRC="<%=actionSrc_projectType%>"
                        tabindex="1"
                        width="100%" height="100%"  frameborder="0"  
                        MARGINWIDTH="1" MARGINHEIGHT="0" SCROLLING="no">
                        </IFRAME>
                </html:outborder>
            </html:tabcontent>
            <%--The 2nd Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%" outLine="no">
                <IFRAME id="productType"
                        name="productType"
                        SRC="<%=actionSrc_productType%>"
                        tabindex="2"
                        width="100%" height="100%" frameborder="0" 
                        MARGINWIDTH="1" MARGINHEIGHT="0" SCROLLING="no">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
            <%--The 3st Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%" outLine="no">
                <IFRAME id="productAttribute"
                        name="productAttribute"
                        SRC="<%=actionSrc_productAttribute%>"
                        tabindex="2"
                        width="100%" height="100%" frameborder="0" 
                        MARGINWIDTH="1" MARGINHEIGHT="0" SCROLLING="no">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
            <%--The 4th Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%" outLine="no">
                <IFRAME id="workItem"
                        name="workItem"
                        SRC="<%=actionSrc_workItem%>"
                        tabindex="2"
                        width="100%" height="100%" frameborder="0" 
                        MARGINWIDTH="1" MARGINHEIGHT="0" SCROLLING="no">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
            <%--The 5th Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%" outLine="no">
                <IFRAME id="techDocmain"
                        name="techDocmain"
                        SRC="<%=actionSrc_techDocmain%>"
                        tabindex="2"
                        width="100%" height="100%" frameborder="0" 
                        MARGINWIDTH="1" MARGINHEIGHT="0" SCROLLING="no">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
            <%--The 6th Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%" outLine="no">
                <IFRAME id="originalLanguage"
                        name="originalLanguage"
                        SRC="<%=actionSrc_originalLanguage%>"
                        tabindex="2"
                        width="100%" height="100%" frameborder="0" 
                        MARGINWIDTH="1" MARGINHEIGHT="0" SCROLLING="no">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
             <%--The 7th Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%" outLine="no">
                <IFRAME id="translation"
                        name="translation"
                        SRC="<%=actionSrc_translation%>"
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
