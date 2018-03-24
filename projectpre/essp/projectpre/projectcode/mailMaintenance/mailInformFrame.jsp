<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%
String contextPath = request.getContextPath();
%>

<%
   String actionSrc_PrivilegeDetail="";
   actionSrc_PrivilegeDetail=contextPath+"/projectpre/projectcode/mailMaintenance/mailInformDetail.jsp";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <TITLE>Mail Inform Frame</TITLE>
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
		tabMap.put("1","PrivilegeDetail");
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

        PrivilegeDetail.location="<%=contextPath%>/projectpre/projectcode/mailMaintenance/mailInformDetail.jsp";
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

      addTabButtonByIndex(tabDetail,1,"addBtn","<bean:message bundle="application" key="global.button.new"/>","onAddData()");
      addTabButtonByIndex(tabDetail,1,"deleteBtn","<bean:message bundle="application" key="global.button.delete"/>","onDeleteData()");
    }
	</script>


  </head>

  <body  BOTTOMMARGIN="0">

    <table cellspacing="0" cellpadding="0" height="100%" width="100%" >
  <tr height="100%">
    <td>
<html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%" >
	<html:tabtitles  selectedindex="1">
         <%--THE 1st tab--%>
         <html:tabtitle width="120"><span class="tabs_title">User List</span></html:tabtitle>
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
                        id="PrivilegeDetail"
                        name="PrivilegeDetail"
                        SRC="<%=actionSrc_PrivilegeDetail%>"
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
  <SCRIPT language="JavaScript" type="text/JavaScript">
         onBodyLoad();
     </SCRIPT>
</html>
