<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>

<%String contextPath = request.getContextPath();
			String actionSrc_PrivilegeList = "";
			actionSrc_PrivilegeList = contextPath
					+ "/projectpre/mailprivilege/PrivilegeList.jsp";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Privilege Detail</title>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>

		<script language="JavaScript" type="text/javascript">

        tabCount=2;
        var tabMap2=new HashMap();
        initMap();

        function initMap() {
          tabMap=new HashMap();
          tabMap.put("tabPanelId","tabDetail");
          tabMap.put("1","RoleGeneral");
        }
        
        function onAddData(){
          UserList.onAddData();
         }
         
          function onDeleteData(){
          UserList.onDeleteData();
         }
   
          function onSaveData() {
            PrivilegeList.onSaveData();
          }
          
          function onBodyLoad() {
            initButton();
            onTabChange();
          }
          
          function refreshIFrame(iframeid,url,typename) {
            iframeid.onRefreshData(url,typename);
          }         

          function onRefreshData(LoginId, loginName, domain) {
          try{
            if(LoginId == null){
             PrivilegeList.location="<%=contextPath%>/projectpre/Blank.jsp";
            }else{
             PrivilegeList.location="<%=contextPath%>/mailprivilege/privilegeList.do?loginId="+LoginId+"&loginName="+loginName+"&domain="+domain;
            }
            onTabChange();
            }catch(e){}
          }
          
          function getSelectedTabIndex(){
            return getTabIndex(tabDetail);
          }

          function onTabChange() {
            try {
              renderTabButton(tabDetail,btnPanel);
            } catch(e) {
            }
          }

          function initButton() {
            addTabButtonByIndex(tabDetail,1,"saveBtn","<bean:message bundle="application" key="global.button.save"/>","onSaveData()");
          }
         
	</script>

	</head>
	<body BOTTOMMARGIN="0">
		<TABLE width="100%" height="100%" CELLPADDING="1" CELLSPACING="0" BORDER="0">
			<tr height="50%">
				<td class="wind">
					<iframe id="UserList" src="<%=contextPath%>/mailprivilege/userList.do" scrolling="auto" height="100%" width="100%" marginHeight="0" marginWidth="0" frameborder="0">
					</iframe>
				</td>
			</tr>
			<tr height="*">
				<td>
					<html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%">
						<html:tabtitles selectedindex="1">
							<%--THE 1st tab--%>
							<html:tabtitle width="120">
								<span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.MailPrivilege.detail"/></span>
							</html:tabtitle>
						</html:tabtitles>
						<html:tabbuttons>
							<div id="btnPanel">
							</div>
						</html:tabbuttons>
						<html:tabcontents>
							<%--THE 1st Card--%>
							<html:tabcontent styleClass="wind">
								<html:outborder width="100%" height="99%">
									<IFRAME id="PrivilegeList" name="PrivilegeList" SRC="<%=actionSrc_PrivilegeList%>" tabindex="1" width="100%" height="100%" frameborder="0" MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="auto">
									</IFRAME>
								</html:outborder>
							</html:tabcontent>
						</html:tabcontents>
					</html:tabpanel>
				</td>
			</tr>
		</TABLE>

	</body>
	<SCRIPT language="JavaScript" type="text/JavaScript">
         onBodyLoad();
     </SCRIPT>
</html>
