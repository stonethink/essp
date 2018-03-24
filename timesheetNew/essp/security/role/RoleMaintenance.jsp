<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%
String contextPath = request.getContextPath();
%>

<%

   String actionSrc_RoleSeting="";
   actionSrc_RoleSeting=contextPath+"/security/role/RoleSeting.jsp";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <TITLE><bean:message bundle="application"  key="RoleDefine.RoleMaintenance.title"/></TITLE>
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
		tabMap.put("1","RoleSeting");

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
    function onSubmitData() {
      invokeSingleMethod("onSubmitData",'',tabMap);
    }
    function onBodyLoad() {
      initButton();
      onTabChange();
    }
    function refreshIFrame(iframeid,url,typename) {
      iframeid.onRefreshData(url,typename);
    }

    function onRefreshData() {

      RoleSeting.location="<%=contextPath%>/security/role/RoleSeting.jsp";
      onTabChange();
    }
    function getSelectedTabIndex(){
      return getTabIndex(tabDetail);
    }

    function onTabChange() {
      try {

        renderTabButton(tabDetail,btnPanel);

      } catch(e) {
        //alertError( e );
      }
    }

    function initButton() {

      addTabButtonByIndex(tabDetail,1,"addBtn","<bean:message bundle="application"  key="global.button.new"/>","onAddData()");
      addTabButtonByIndex(tabDetail,1,"delBtn","<bean:message bundle="application"  key="global.button.delete"/>","onDelData()");

    }
	</script>


  </head>

  <body   onload="onBodyLoad()" BOTTOMMARGIN="0">

    <table cellspacing="0" cellpadding="0" height="100%" width="100%" >
  <tr height="100%">
    <td>
<html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%" >
	<html:tabtitles  selectedindex="1">
         <%--THE 1st tab--%>
         <html:tabtitle width="120"><span class="tabs_title"><bean:message bundle="application"  key="RoleDefine.RoleMaintenance.tabtitle"/></span></html:tabtitle>
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
                        id="RoleSeting"
                        name="RoleSeting"
                        SRC="<%=actionSrc_RoleSeting%>"
                        tabindex="1"
                        width="100%" height="100%"  frameborder="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
                        </IFRAME>
                </html:outborder>
            </html:tabcontent>
	</html:tabcontents>
</html:tabpanel>
    </td>
  </tr>

</table>

  </body>
</html>
