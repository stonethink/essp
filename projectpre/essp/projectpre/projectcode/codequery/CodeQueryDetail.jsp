
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%String contextPath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value="Project Code Apply" />
			<tiles:put name="jspName" value="" />
		</tiles:insert>

		<script type="text/javascript" language="javascript">
       tabCount=2;
       var tabMap=new HashMap();
       
       initMap();
       
       function initMap(){
         tabMap=new HashMap();
         tabMap.put("tabPanelId","tabDetail");
         tabMap.put("1","projectCodeSearch");
       
       }
       
       function onBodyLoad(){
         initButton();
         onTabChange();
       }
       
       function initButton(){
          addTabButtonByIndex(tabDetail,1,"disBtn","<bean:message bundle="application"  key="global.button.display"/>","onSeeData()");
          addTabButtonByIndex(tabDetail,1,"expBtn","<bean:message bundle="application"  key="global.button.export"/>","onExportData()");
          
       }
       
       function onTabChange(){
         try {
              renderTabButton(tabDetail,btnPanel);
             }catch(e){
             }
       }
       function onSeeData(){
         recordList.onSeeData();
       }
       function onExportData(){
         recordList.onExportData();
       }
       function onSearchData(){
 
         projectCodeSearch.onSearchData();
       }
       function onOpenData(){
         projectCodeSearch.onOpenData();
       }    
       function refreshIFrame(iframeid,url,typename){
         iframeid.onRefreshData(url,typename);
       }
       
       function onRefreshData(){
          
       }
       
       function getSelectedTabIndex(){
         return getTabIndex(tabDetail);
       }
       
     </script>
	</head>

	<body  BOTTOMMARGIN="0">
		<table cellspacing="0" cellpadding="1" height="100%" width="100%">
			<tr height="45%">
				<td class="wind">
					<iframe id="projectCodeSearch" 
					src="<%=contextPath%>/project/query/projectQueryPre.do" 
					scrolling="auto" 
					height="100%" 
					width="100%" marginHeight="1" marginWidth="1" frameborder="0">
					</iframe>
				</td>
			</tr>
			<tr height="55%">
				<td>
					<html:tabpanel id="tabDetail"  width="100%">
						<html:tabtitles selectedindex="1">
							<%--THE 1st tab--%>
							<html:tabtitle width="100">
								<span class="tabs_title"><bean:message bundle="projectpre" key="projectCode.CodeQueryDetail.cardTitle.QueryResult"/></span>
							</html:tabtitle>
						</html:tabtitles>

						<html:tabbuttons>
							<div id="btnPanel">
							</div>
						</html:tabbuttons>

						<html:tabcontents>
							<%--THE 1st Card--%>
							<html:tabcontent>
								<html:outborder width="100%" height="98%">
									<IFRAME id="recordList" name="recordList" SRC="" tabindex="1" width="100%" height="100%" frameborder="0" marginHeight="1" marginWidth="1" scrolling="auto">
									</IFRAME>
								</html:outborder>
								<html:outborder width="100%" height="2%" outLine="no">
						
								</html:outborder>
							</html:tabcontent>
						</html:tabcontents>
					</html:tabpanel>
				</td>
			</tr>
		</table>
		<SCRIPT language="JavaScript" type="text/JavaScript">
		   onBodyLoad();
		   window.disBtn.disabled=true;
		   window.expBtn.disabled=true;
		</SCRIPT>
	</body>
</html>
