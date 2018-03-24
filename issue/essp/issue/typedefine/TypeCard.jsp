<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>

<bean:parameter id="selectedTabIndex" name="selectedTabIndex"/>
<bean:parameter id="detailSize" name="detailSize" />
<bean:define  id="contextPath" value="<%=request.getContextPath()%>" />
<html>
<head>
 <tiles:insert page="/layout/head.jsp">
        <tiles:put name="title" value=" CRM V2.1" />
        <tiles:put name="jspName" value="" />
 </tiles:insert>
	<script language="JavaScript" type="text/javascript">
    tabCount=2;
    var tabMap=new HashMap();
    initMap();

    function initMap() {
        tabMap=new HashMap();
        tabMap.put("tabPanelId","tabDetail");
		tabMap.put("1","issueTypeGeneral");
		tabMap.put("2","issueTypePriority");
        tabMap.put("3","issueTypeStatus");
        tabMap.put("4","issueTypeScope");
        tabMap.put("5","issueTypeCategory");
        tabMap.put("6","issueTypeRisk");
    }

	function onBodyLoad() {
        initButton();
        onTabChange();
	}

	function onAddData(param) {
        invokeSingleMethod("onAddData",param,tabMap);
	}

	function onAddCategoryValue(param) {
        invokeSingleMethod("onAddCategoryValue",param,tabMap);
	}
	function onDeleteData(param) {
	    invokeSingleMethod("onDeleteData",param,tabMap);
	}

	function onEditData(param) {
		invokeSingleMethod("onEditData",param,tabMap);
	}

    function refreshIFrame(iframeid,url,typename) {
        iframeid.onRefreshData(url,typename);
    }

    function onRefreshData(typename) {
        refreshIFrame(issueTypeGeneral,"<%=contextPath%>/issue/typedefine/issueTypeUpdateForward.do",typename);
        refreshIFrame(issueTypePriority,"<%=contextPath%>/issue/typedefine/priority/issuePriorityList.do",typename);
        refreshIFrame(issueTypeStatus,"<%=contextPath%>/issue/typedefine/status/statusListAction.do",typename);
        refreshIFrame(issueTypeScope,"<%=contextPath%>/issue/typedefine/scope/scopeListAction.do",typename);
        refreshIFrame(issueTypeCategory,"<%=contextPath%>/issue/typedefine/category/categoryListAction.do",typename);
        refreshIFrame(issueTypeRisk,"<%=contextPath%>/issue/typedefine/risk/riskListAction.do",typename);
    }

    function onSaveData(param) {
		invokeSingleMethod("onSaveData",param,tabMap);
    }

    function getSelectedTabIndex(){
        return getTabIndex(tabDetail);
    }

    function onTabChange() {
        try {
        	renderTabButton(tabDetail,btnPanel);

			var tabIndex = getTabIndex(tabDetail);
            if(tabIndex==1) {
                saveBtn.disabled=<%="0".equals(detailSize)?"true":"false"%>;
            } else if(tabIndex==2) {
                addBtn.disabled=<%="0".equals(detailSize)?"true":"false"%>;
                editBtn.disabled=<%="0".equals(detailSize)?"true":"false"%>;
                deleteBtn.disabled=<%="0".equals(detailSize)?"true":"false"%>;
            } else if(tabIndex == 4){
               addBtn.disabled=<%="0".equals(detailSize)?"true":"false"%>;
               editBtn.disabled=<%="0".equals(detailSize)?"true":"false"%>;
               deleteBtn.disabled=<%="0".equals(detailSize)?"true":"false"%>;
            } else if(tabIndex == 5){
                addBtn.disabled=<%="0".equals(detailSize)?"true":"false"%>;
                addValueBtn.disabled=<%="0".equals(detailSize)?"true":"false"%>;
                editBtn.disabled=<%="0".equals(detailSize)?"true":"false"%>;
                deleteBtn.disabled=<%="0".equals(detailSize)?"true":"false"%>;
            }
        } catch(e) {
            alert( "onTabChange" + e );
        }
    }

    function initButton() {
        <%--issue type list's button--%>
		addTabButtonByIndex(tabDetail,1,"saveBtn","Save","onSaveData()");
		<%--priority's buttons--%>
        addTabButtonByIndex(tabDetail,2,"addBtn","Add","onAddData()");
		addTabButtonByIndex(tabDetail,2,"editBtn","Edit","onEditData()");
		addTabButtonByIndex(tabDetail,2,"deleteBtn","Delete","onDeleteData()");
        <%-- status's buttons--%>
		addTabButtonByIndex(tabDetail,3,"addBtn","Add","onAddData()");
		addTabButtonByIndex(tabDetail,3,"editBtn","Edit","onEditData()");
		addTabButtonByIndex(tabDetail,3,"deleteBtn","Delete","onDeleteData()");
        <%-- scope's buttons --%>
        addTabButtonByIndex(tabDetail,4,"addBtn","Add","onAddData()");
		addTabButtonByIndex(tabDetail,4,"editBtn","Edit","onEditData()");
		addTabButtonByIndex(tabDetail,4,"deleteBtn","Delete","onDeleteData()");
        <%-- category's buttons --%>
        addTabButtonByIndex(tabDetail,5,"addBtn","Add","onAddData()");
        addTabButtonByIndex(tabDetail,5,"addValueBtn","Add Value","onAddCategoryValue()","width:75px");
		addTabButtonByIndex(tabDetail,5,"editBtn","Edit","onEditData()");
		addTabButtonByIndex(tabDetail,5,"deleteBtn","Delete","onDeleteData()");
        <%-- risk' buttons--%>
		addTabButtonByIndex(tabDetail,6,"addBtn","Add","onAddData()");
		addTabButtonByIndex(tabDetail,6,"editBtn","Edit","onEditData()");
		addTabButtonByIndex(tabDetail,6,"deleteBtn","Delete","onDeleteData()");
   }
	</script>
</head>
<body onload="onBodyLoad()">
    <%--
    <%
    out.println(selectedTabIndex);
    out.println(detailSize);
    out.println(contextPath);
    %>
    --%>
<%--ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd --%>
<table cellspacing="0" cellpadding="0" height="90%" width="100%">
  <tr>
    <td>
<html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%" >
	<html:tabtitles  selectedindex="<%=selectedTabIndex%>"  >
         <%--THE 1st tab--%>
         <html:tabtitle width="60"><span class="tabs_title">General</span></html:tabtitle>
         <%--THE 2nd tab--%>
         <html:tabtitle width="60"><span class="tabs_title">Priority</span></html:tabtitle>
	     <%--THE 3rd tab--%>
         <html:tabtitle width="60"><span class="tabs_title">Status</span></html:tabtitle>
         <%--THE 4th tab--%>
         <html:tabtitle width="60"><span class="tabs_title">Scope</span></html:tabtitle>
         <%--THE 5th tab--%>
         <html:tabtitle width="60"><span class="tabs_title">Category</span></html:tabtitle>
         <%--THE 6th tab--%>
         <html:tabtitle width="60"><span class="tabs_title">Influence</span></html:tabtitle>
    </html:tabtitles>
    <html:tabbuttons>
    	<div id="btnPanel">
    	</div>
    </html:tabbuttons>
	<html:tabcontents>
            <%--THE 1st Card--%>
            <html:tabcontent styleClass="wind">
                <html:outborder width="100%" height="100%">
                        <IFRAME
                        id="issueTypeGeneral"
                        name="issueTypeGeneral"
                        SRC="<%=contextPath%>/issue/typedefine/blank.jsp"
                        tabindex="1"
                        width="100%" height="100%" frameborder="no" border="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="yes">
                        </IFRAME>
                </html:outborder>
            </html:tabcontent>
            <%--The 2nd Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%">
                <IFRAME id="issueTypePriority"
                        SRC="<%=contextPath%>/issue/typedefine/blank.jsp"
                        tabindex="2"
                        width="100%" height="100%" frameborder="no" border="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="yes">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>

            <%--The 3rd Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%">
                <IFRAME id="issueTypeStatus"
                        SRC="<%=contextPath%>/issue/typedefine/blank.jsp"
                        tabindex="3"
                        width="100%" height="100%" frameborder="no" border="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="yes">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
            <%--The 4th Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%">
                <IFRAME id="issueTypeScope"
                        SRC="<%=contextPath%>/issue/typedefine/blank.jsp"
                        tabindex="4"
                        width="100%" height="100%" frameborder="no" border="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="yes">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
            <%--The 5th Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%">
                <IFRAME id="issueTypeCategory"
                        SRC="<%=contextPath%>/issue/typedefine/blank.jsp"
                        tabindex="5"
                        width="100%" height="100%" frameborder="no" border="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="yes">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
            <%--The 6th Card--%>
            <html:tabcontent styleClass="wind">
              <html:outborder width="100%" height="100%">
                <IFRAME id="issueTypeRisk"
                        SRC="<%=contextPath%>/issue/typedefine/blank.jsp"
                        tabindex="6"
                        width="100%" height="100%" frameborder="no" border="0"
                        MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="yes">
                        </IFRAME>
              </html:outborder>
            </html:tabcontent>
	</html:tabcontents>
</html:tabpanel>
    </td>
  </tr>
</table>
<%----------------------------------------------------------------------------%>
</body>
</html>
