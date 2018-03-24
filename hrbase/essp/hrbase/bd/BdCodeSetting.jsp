<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%
  String contextPath = request.getContextPath();
 
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
		tabMap2.put("1","bdCodeGeneral");
	
    }
    function onAddData(){
  
      bdCodeList.onAddData();
    }
     function onDelData(){
  
      bdCodeList.onDelData();
    }
    function onSaveData() {
        invokeSingleMethod("onSaveData",'',tabMap2);
	}
  
	function onBodyLoad() {
        initButton();
        onTabChange();
	}
    function refreshIFrame(iframeid,url,typename) {
        iframeid.onRefreshData(url,typename);
    }

    function onRefreshData(code) {
       //alert(code);
       if(code==""||code==null){
          bdCodeGeneral.location="<%=contextPath%>/hrbase/Blank.jsp";
          
       }else{
        bdCodeGeneral.location= "<%=contextPath%>/hrb/bd/previewBdCode.do?code="+code;
        }
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
      addTabButtonByIndex(tabDetail2,1,"saveBtn","<bean:message bundle="application" key="global.button.save"/>","onSaveData()");
      
     }
    
   
	</script>

	</head>
	<body >
		<TABLE width="100%" height="100%" CELLPADDING="1" CELLSPACING="0" BORDER="0" >
			<tr height="40%" >
				<td class="wind">
				  
					<iframe 
					id="bdCodeList" 
					src="<%=contextPath%>/hrb/bd/listAllBdCode.do" 
					scrolling="yes" 
					height="100%" width="100%" 
					marginHeight="0" marginWidth="0" 
					frameborder="0"
					>
					</iframe>
				
				</td>
			</tr>
		    
			<tr height="*">
				<td>

					<html:tabpanel id="tabDetail2" onchange="onTabChange()" width="100%">
						<html:tabtitles selectedindex="1">
							<%--THE 1st tab--%>
							<html:tabtitle width="120">
								<span class="tabs_title"><bean:message bundle="hrbase" key="hrbase.general"/></span>
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
									    id="bdCodeGeneral" 
									    name="bdCodeGeneral" 
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
		<SCRIPT language="JavaScript" type="text/JavaScript" >
		   onBodyLoad();
		   window.saveBtn.disabled=true;
		</SCRIPT>
	</body>
</html>
