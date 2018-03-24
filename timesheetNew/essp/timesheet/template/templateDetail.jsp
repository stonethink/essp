<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>

<%String contextPath = request.getContextPath();
			String actionSrc_stepDetail = "";
			actionSrc_stepDetail = contextPath
					+ "/timesheet/template/stepDetail.jsp";
			%>
<html>
	<head>
		<title>template Detail</title>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>

		<script language="JavaScript" type="text/javascript">  
		var tabCount=2;
		var tabMap=new HashMap();
		var dataRid = null;
        initMap();
        function initMap() {        
          	tabMap.put("tabPanelId","tabDetail");
          	tabMap.put("1","templateGeneral");
          	tabMap.put("2","stepDetail");
        }
        
        //暂时是错误的
        function onAddData(){        	
        	templateList.onAddData(); 
        }
         
        function onDeleteData(){
        	templateList.onDeleteData();
        }
   
        function onSaveData() {
        	templateGeneral.onSaveData();
        }
        
        function onAddStepData() {
        	invokeSingleMethod("onAddData",'',tabMap);
        }
        
        function onDeleteStepData() {
        	invokeSingleMethod("onDeleteData",'',tabMap);
        }  
          
        function onBodyLoad() {
            initButton();
            onTabChange();
        }
        
                    
        function refreshIFrame(iframeid,url,typename) {
            iframeid.onRefreshData(url,typename);
        }         

        function onRefreshData(rid) {          
          try{
          	dataRid = rid; 
          	if(rid==null || rid==""){       
          		//document.all.saveBtn.disabled=true;
          		//document.all.addStepBtn.disabled=true;    
          		//document.all.delStepBtn.disabled=true;      		
		        templateGeneral.location="<%=contextPath%>/timesheet/Blank.jsp";
            }else{
             	templateGeneral.location="<%=contextPath%>/timesheet/template/viewTemplate.do?rid="+rid;             
                stepDetail.location="<%=contextPath%>/timesheet/template/stepDetail.jsp?tempId="+rid;
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
	          	if((dataRid==null || dataRid=="") && document.all.saveBtn != null) {       
	          		document.all.saveBtn.disabled=true;
	          	}
	          	if((dataRid==null || dataRid==""||dataRid=="0") && document.all.addStepBtn != null) {       
	          		document.all.addStepBtn.disabled=true;
	          		document.all.delStepBtn.disabled=true;
	          	}
	          	stepDetail.onTabChange();
            } catch(e) {
            }
          }

          function initButton() {
            addTabButtonByIndex(tabDetail,1,"saveBtn","<bean:message bundle="application" key="global.button.save"/>","onSaveData()");   
                     
            addTabButtonByIndex(tabDetail,2,"addStepBtn","<bean:message bundle="application" key="global.button.new"/>","onAddStepData()");
        	addTabButtonByIndex(tabDetail,2,"delStepBtn","<bean:message bundle="application" key="global.button.delete"/>","onDeleteStepData()");
          }       
          
        function getSelectedTabIndex(){
        	return getTabIndex(tabDetail);
    	}

	</script>

	</head>
	<body BOTTOMMARGIN="0" >
		<TABLE width="100%" height="100%" CELLPADDING="1" CELLSPACING="0" BORDER="0">
			<tr height="20%">
				<td class="wind">
					<iframe id="templateList" src="<%=contextPath%>/timesheet/template/listTemplate.do" scrolling="auto" height="100%" width="100%" marginHeight="0" marginWidth="0" frameborder="0">
					</iframe>
				</td>
			</tr>
			<tr height="*">
				<td>
					<html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%">
						<html:tabtitles selectedindex="1">
							<%--THE 1st tab--%>
							<html:tabtitle width="120">
								<span class="tabs_title"><bean:message bundle="timesheet" key="timesheet.template.general"/></span>
							</html:tabtitle>
							<%--THE 2nd tab--%>
							<html:tabtitle width="120">
								<span class="tabs_title"><bean:message key="timesheet.template.detail" bundle="timesheet"/></span>
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
									<IFRAME id="templateGeneral" SRC="" tabindex="1" width="100%" height="100%" frameborder="0" MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
									</IFRAME>
								</html:outborder>
							</html:tabcontent>
							<%--The 2nd Card--%>
							<html:tabcontent styleClass="wind">
								<html:outborder width="100%" height="100%">
									<IFRAME id="stepDetail" SRC="" tabindex="2" width="100%" height="100%" frameborder="no" border="0" MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
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
