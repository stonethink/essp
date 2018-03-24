<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%
String contextPath = request.getContextPath();
%>

<%

   String actionSrc_bdCodeSetting="";
  actionSrc_bdCodeSetting=contextPath+"/hrbase/bd/BdCodeSetting.jsp";
  
  
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <TITLE><bean:message bundle="hrbase" key="hrbase.bd.bdcodesetting"/></TITLE>
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
		tabMap.put("1","bdCodeSetting");
		
		
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
        onTabChange();
    }
    function getSelectedTabIndex(){
        return getTabIndex(tabDetail);
    }

    function onTabChange() {
        try {
        
           renderTabButton(tabDetail,btnPanel);
          
           if(getTabIndex(tabDetail)==1){
             bdCodeSetting.bdCodeList.autoClickFirstRow();
             bdCodeSetting.bdCodeList.disableBtn();
           }
          
        } catch(e) {
            //alertError( e );
        }
    }

    function initButton() {
     
      addTabButtonByIndex(tabDetail,1,"addBtn","<bean:message bundle="application" key="global.button.new"/>","onAddData()");
      addTabButtonByIndex(tabDetail,1,"delBtn","<bean:message bundle="application" key="global.button.delete"/>","onDelData()");
     
    }
	</script>
    
    
  </head>
  
  <body   BOTTOMMARGIN="0">
    
    <table cellspacing="0" cellpadding="0" height="100%" width="100%" >
  <tr height="100%">
    <td>
<html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%" >
	<html:tabtitles  selectedindex="1"  >
         <%--THE 1rd tab--%>
         <html:tabtitle width="120"><span class="tabs_title"><bean:message bundle="hrbase" key="hrbase.bd.bdcodesetting"/></span></html:tabtitle>
          </html:tabtitles>
    <html:tabbuttons>
    	<div id="btnPanel">
    	</div>
    </html:tabbuttons>
	<html:tabcontents>
             <%--The 1rd Card--%>
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
