 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%@ page import="server.essp.projectpre.service.constant.Constant"%>
<bean:define id="kindType" value='<%=request.getParameter("kind")%>'/>

<%
  String contextPath = request.getContextPath();
  String actionSrc_parameterGeneral = "";
  actionSrc_parameterGeneral = contextPath
					+ "/projectpre/parameter/ParameterGeneral.jsp";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	    <title><bean:message bundle="projectpre" key="projectCode.ProjectData.Title"/></title>
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
		tabMap.put("1","parameterGeneral");
    }
    function onAddData(){
  
      parameterList.onAddData();
    }
    function onDeleteData(){
      parameterList.onDeleteData();
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

    function onRefreshData(code) {
      
       if(code==""||code==null){
         parameterGeneral.location="<%=contextPath%>/projectpre/Blank.jsp";
       }else{
        parameterGeneral.location="<%=contextPath%>/parameter/previewParameter.do?kind=<%=kindType%>&code="+code;
        }
      
        onTabChange();
    }
    function getSelectedTabIndex(){
        return getTabIndex(tabDetail);
    }

    function onTabChange() {
        try {
        
           renderTabButton(tabDetail,btnPanel);
           if(getTabIndex(tabDetail)==1){
               parameterList.disableBtn();
           }
        } catch(e) {
        
        }
    }

    function initButton() {
       addTabButtonByIndex(tabDetail,1,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData()");
       
    }
	</script>
	
     </head>
     <%
        String action = "";
     	if(Constant.PROJECT_TYPE.equals(kindType)){
     		action = "/parameter/listProjectType.do";
     	}else if(Constant.PRODUCT_TYPE.equals(kindType)){
     		action = "/parameter/listProductType.do";
     	}else if(Constant.PRODUCT_ATTRIBUTE.equals(kindType)){
     		action = "/parameter/listAttributeType.do";
     	}else if(Constant.WORK_ITEM.equals(kindType)){
     		action = "/parameter/listWorkItem.do";
     	}else if(Constant.TECHNICAL_DOMAIN.equals(kindType)){
     		action = "/parameter/listTechDomain.do";
     	}else if(Constant.ORIGINAL_LANGUAGE.equals(kindType)){
     		action = "/parameter/listOrgLanguage.do";
     	}else if(Constant.TRANSLATION_LANGUANGE.equals(kindType)){
     		action = "/parameter/listTranLanguage.do";
     	}else if(Constant.BUSINESS_TYPE.equals(kindType)){
     		action = "/parameter/listBusinessType.do";
     	}else if(Constant.COUNTRY_CODE.equals(kindType)){
     		action = "/parameter/listCountryCode.do";
     	}		
     
     %>
	<body BOTTOMMARGIN="0">
	<TABLE width="100%" height="100%" CELLPADDING="1" CELLSPACING="0" BORDER="0" >
			<tr height="40%" >
				<td class="wind">
	<iframe 
					id="parameterList" 
					src="<%=contextPath%><%=action%>?kind=<%=kindType%>" 
					scrolling="auto" 
					height="100%" width="100%" 
					marginHeight="0" marginWidth="0" 
					frameborder="0"
					>
					</iframe>
					</td>
			</tr>
		    
			<tr height="60%">
				<td>	
     <html:tabpanel id="tabDetail" onchange="onTabChange()" width="100%">
						<html:tabtitles selectedindex="1">
							<%--THE 1st tab--%>
							<html:tabtitle width="120">
								<span class="tabs_title"><bean:message bundle="projectpre" key="customer.General"/></span>
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
									<IFRAME 
									    id="parameterGeneral" 
									    name="parameterGeneral" 
									    SRC="" 
									    tabindex="1" width="100%" height="100%" 
									    frameborder="0" 
									    MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="auto"
									    >
									</IFRAME>
								</html:outborder>
							</html:tabcontent>
						</html:tabcontents>
					</html:tabpanel>
										</td>
			</tr>
			
		</TABLE>
		 <script type="text/javascript" language="javascript">
           onBodyLoad();
         </script>
	</body>
	 
</html>
