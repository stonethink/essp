
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%
  String contextPath = request.getContextPath();
  String actionSrc_AreaCodeGeneral = "";
  actionSrc_AreaCodeGeneral = contextPath
					+ "/projectpre/customer/typeinfo/AreaCodeGeneral.jsp";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
         <tiles:insert page="/layout/head.jsp">
            <tiles:put name="title" value=" "/>
            <tiles:put name="jspName" value=""/>
        </tiles:insert>
		


		<script language="JavaScript" type="text/javascript">
    
    tabCount=2;
    var tabMap2=new HashMap();
    initMap();

    function initMap() {
        tabMap2=new HashMap();
        tabMap2.put("tabPanelId","tabDetail2");
		tabMap2.put("1","areaCodeGeneral");
    }
    function onAddData(){
  
      areaCodeList.onAddData();
    }
    
     function onDeleteData(){
      areaCodeList.onDeleteData();
    }
    function onSaveData() {
        invokeSingleMethod("onSaveData",'',tabMap2);
	}
	 function onSubmitData() {
        invokeSingleMethod("onSubmitData",'',tabMap2);
	}

	function onBodyLoad() {
        initButton();
        onTabChange();
	}
    function refreshIFrame(iframeid,url,typename) {
        iframeid.onRefreshData(url,typename);
    }

    function onRefreshData(url,typename) {
       
        areaCodeGeneral.location="<%=contextPath%>/projectpre/customer/typeinfo/AreaCodeGeneral.jsp";
        onTabChange();
    }
    function getSelectedTabIndex(){
        return getTabIndex(tabDetail2);
    }

    function onTabChange() {
        try {
        
           renderTabButton(tabDetail2,btnPanel);
           
        } catch(e) {
        
        }
    }

    function initButton() {
      addTabButtonByIndex(tabDetail2,1,"saveBtn","<bean:message bundle="application"  key="global.button.save"/>","onSaveData()");
      
    }
	</script>

	</head>
	<body>
		<TABLE width="100%" height="100%" CELLPADDING="1" CELLSPACING="0" BORDER="0" >
			<tr height="40%" >
				<td class="wind">
				  
					<iframe 
					id="areaCodeList" 
					src="<%=contextPath%>\projectpre\customer\typeinfo\AreaCodeList.jsp" 
					scrolling="yes" 
					height="100%" width="100%" 
					marginHeight="0" marginWidth="0" 
					frameborder="0"
					>
					</iframe>
				
				</td>
			</tr>
		     <tr>
			  <td height="3px"></td>
			</tr>
			<tr height="*">
				<td>

					<html:tabpanel id="tabDetail2" onchange="onTabChange()" width="100%">
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
								<html:outborder width="100%" height="100%">
									<IFRAME 
									    id="areaCodeGeneral" 
									    name="areaCodeGeneral" 
									    SRC="<%=actionSrc_AreaCodeGeneral%>" 
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
			<tr>
			  <td height="5px"></td>
			</tr>
		</TABLE>
	<script type="text/javascript" language="javascript">
     onBodyLoad();
     </script>	
	</body>
</html>
