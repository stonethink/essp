
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="server.essp.projectpre.service.constant.Constant"%>
<%
String contextPath = request.getContextPath();
%>

<%
   
   String actionSrc_projectCodeSetting="";
   String actionSrc_areaCodeSetting="";
   String actionSrc_bdCodeSetting="";
   String actionSrc_projectTypeSetting=""; 
    
   
  actionSrc_projectCodeSetting=contextPath+"/project/maintenance/displayProjectId.do?kind="+Constant.PROJECT_CODE;
  actionSrc_areaCodeSetting=contextPath+"/projectpre/projectcode/codemaintenance/AreaCodeSetting.jsp";
  actionSrc_bdCodeSetting=contextPath+"/projectpre/projectcode/codemaintenance/BdCodeSetting.jsp";
  actionSrc_projectTypeSetting=contextPath+"/projectpre/projectcode/codemaintenance/ProjectTypeSetting.jsp";
  
  
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <TITLE><bean:message bundle="projectpre" key="projectCode.ProjectCodeMaintenance.Title"/></TITLE>
    <tiles:insert page="/layout/head.jsp">
    <tiles:put name="title" value=" "/>
    <tiles:put name="jspName" value=""/>
    </tiles:insert>
    
   
  
    <script language="JavaScript" type="text/javascript">
    
    tabCount=2;
    var tabMap=new HashMap();
    initMap();

    function initMap() {
        tabMap.put("tabPanelId","tabDetail");
		tabMap.put("1","projectCodeSetting");
		tabMap.put("2","areaCodeSetting");
		tabMap.put("3","bdCodeSetting");
		tabMap.put("4","projectTypeSetting");
		
    }
    function onAddData() {
        invokeSingleMethod("onAddData",'',tabMap);
	}
	
	function onDelData() {
        invokeSingleMethod("onDelData",'',tabMap);
	}
	function onSaveData() {
        invokeSingleMethod("onSaveData",'',tabMap);
	}
  
	function onBodyLoad() {
        initButton();
        onTabChange();
	}
    function refreshIFrame(iframeid,url,typename) {
        iframeid.onRefreshData(url,typename);
    }

    function onRefreshData() {
       
        projectCodeSetting.location="<%=contextPath%>/project/maintenance/displayProjectId.do?kind="+<%=Constant.PROJECT_CODE%>;
        areaCodeSetting.location="<%=contextPath%>/projectpre/projectcode/codemaintenance/AreaCodeSetting.jsp";
        onTabChange();
    }
    function getSelectedTabIndex(){
        return getTabIndex(tabDetail);
    }

    function onTabChange() {
        try {
        
           renderTabButton(tabDetail,btnPanel);
           if(getTabIndex(tabDetail)==2){
           areaCodeSetting.areaCodeList.autoClickFirstRow();
           areaCodeSetting.areaCodeList.disableBtn();
           }
           if(getTabIndex(tabDetail)==3){
             bdCodeSetting.bdCodeList.autoClickFirstRow();
             bdCodeSetting.bdCodeList.disableBtn();
           }
           if(getTabIndex(tabDetail)==4){
             projectTypeSetting.projectTypeList.autoClickFirstRow();
             projectTypeSetting.projectTypeList.disableBtn();
           }
        } catch(e) {
            //alertError( e );
        }
    }

    function initButton() {
      addTabButtonByIndex(tabDetail,1,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData()");   
      addTabButtonByIndex(tabDetail,2,"addBtn","<bean:message key="global.button.new" bundle="application"/>","onAddData()");
      addTabButtonByIndex(tabDetail,2,"delBtn","<bean:message key="global.button.delete" bundle="application"/>","onDelData()");
      addTabButtonByIndex(tabDetail,3,"addBtn","<bean:message key="global.button.new" bundle="application"/>","onAddData()");
      addTabButtonByIndex(tabDetail,3,"delBtn","<bean:message key="global.button.delete" bundle="application"/>","onDelData()");
      addTabButtonByIndex(tabDetail,4,"addBtn","<bean:message key="global.button.new" bundle="application"/>","onAddData()");
      addTabButtonByIndex(tabDetail,4,"delBtn","<bean:message key="global.button.delete" bundle="application"/>","onDelData()");
    }
	</script>
    
    
  </head>
  
  <body   BOTTOMMARGIN="0">
    
    <table cellspacing="0" cellpadding="0" height="100%" width="100%" >
  <tr height="100%">
    <td>
<html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%" >
	<html:tabtitles  selectedindex="1"  >
         <%--THE 1st tab--%>
         <html:tabtitle width="120"><span class="tabs_title"><bean:message bundle="projectpre"  key="projectCode.ProjectCodeMaintenance.cardTitle.ProjectCodeSetting"/></span></html:tabtitle>
         <%--THE 2nd tab--%>
         <html:tabtitle width="120"><span class="tabs_title"><bean:message bundle="projectpre"  key="projectCode.ProjectCodeMaintenance.cardTitle.AreaCodeSetting"/></span></html:tabtitle>
         <%--THE 3rd tab--%>
         <html:tabtitle width="120"><span class="tabs_title"><bean:message bundle="projectpre"  key="projectCode.BDCodeSetting"/></span></html:tabtitle>
         <%--THE 4rd tab--%>
         <html:tabtitle width="150"><span class="tabs_title"><bean:message bundle="projectpre"  key="projectCode.typeMaintenance"/></span></html:tabtitle>
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
                        id="projectCodeSetting"
                        name="projectCodeSetting"
                        SRC="<%=actionSrc_projectCodeSetting%>"
                        tabindex="1"
                        width="100%" height="100%"  frameborder="0"  
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="auto">
                        </IFRAME>
                </html:outborder>
            </html:tabcontent>
            <%--The 2nd Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%" outLine="no">
                <IFRAME id="areaCodeSetting"
                        name="areaCodeSetting"
                        SRC="<%=actionSrc_areaCodeSetting%>"
                        tabindex="2"
                        width="100%" height="100%" frameborder="0" 
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
             <%--The 3rd Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%" outLine="no">
                <IFRAME id="bdCodeSetting"
                        name="bdCodeSetting"
                        SRC="<%=actionSrc_bdCodeSetting%>"
                        tabindex="2"
                        width="100%" height="100%" frameborder="0" 
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
             <%--The 4rd Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%" outLine="no">
                <IFRAME id="projectTypeSetting"
                        name="projectTypeSetting"
                        SRC="<%=actionSrc_projectTypeSetting%>"
                        tabindex="2"
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
