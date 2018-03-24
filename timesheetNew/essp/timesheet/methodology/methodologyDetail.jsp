
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%String contextPath = request.getContextPath();
//String actionSrc_general = "";
	//		actionSrc_general = contextPath
		//			+ "/timesheet/methodology/methodologyGeneral.jsp";%>
<html>
	<head>
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
          tabMap.put("1","methodGeneral");
         }
         
       //
		function onAddData(){     
			methodList.onAddData();     
     	}
	                 
          function onSaveData() {
            methodGeneral.onSaveData();           
          }
    
		  	function onCancelData() {
		      methodList.onCancelData();
		  	}
		  	
		  function onBodyLoad() {
            initButton();
            onTabChange();
          }
		function refreshIFrame(iframeid,url,typename) {
		        iframeid.onRefreshData(url,typename);
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
          
		function onRefreshData(rid) {		    
		       if(rid==null ){
		          methodGeneral.location="<%=contextPath%>/timesheet/Blank.jsp";
		       }else{		 
		          methodGeneral.location= "<%=contextPath%>/timesheet/methodology/viewMethod.do?rid="+rid;
		        }  
		    }     
    
          function initButton() {
            addTabButtonByIndex(tabDetail,1,"saveBtn","<bean:message bundle="application" key="global.button.save"/>","onSaveData()");
          }
	</script>
	</head>
	<body>
		<TABLE width="100%" height="100%" CELLPADDING="1" CELLSPACING="0" BORDER="0">
			<tr height="40%">
				<td class="wind">
					<iframe id="methodList" src="<%=contextPath%>/timesheet/methodology/listMethod.do" scrolling="auto" height="100%" width="100%" marginHeight="0" marginWidth="0" frameborder="0">
					</iframe>
				</td>
			</tr>
			<tr height="*">
				<td>
					<html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%">
						<html:tabtitles selectedindex="1">
							<%--THE 1st tab--%>
							<html:tabtitle width="120">
								<span class="tabs_title"><bean:message key="timesheet.template.general" bundle="timesheet"/></span>
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
									<IFRAME id="methodGeneral" name="methodGeneral" SRC="" tabindex="1" width="100%" height="100%" frameborder="0" MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
									</IFRAME>
								</html:outborder>
							</html:tabcontent>
						</html:tabcontents>
					</html:tabpanel>
				</td>
			</tr>
		</TABLE>
		<SCRIPT language="JavaScript" type="text/JavaScript">		 
        	 onBodyLoad();
		</SCRIPT>
	</body>
</html>
