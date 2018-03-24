
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%
  String contextPath = request.getContextPath();
  String actionSrc_projectCodeCheck="";
  actionSrc_projectCodeCheck=contextPath+"/projectpre/projectcode/codecheck/ProjectCodeCheck.jsp";
  
  String actionSrc_listCheck = "";
  actionSrc_listCheck = contextPath + "/projectpre/deptcode/codeCheck/DeptCodeCheck.jsp";
  
  String actionSrc_customerInfoCheckList="";
  actionSrc_customerInfoCheckList=contextPath+"/projectpre/customer/infocheck/CustomerInfoCheck.jsp";
 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	 <meta http-equiv="refresh" content="60">
         <tiles:insert page="/layout/head.jsp">
            <tiles:put name="title" value=" "/>
            <tiles:put name="jspName" value=""/>
        </tiles:insert>
		


		<script language="JavaScript" type="text/javascript">
    
    tabCount=2;
    
    var tabMap1=new HashMap();
    var tabMap2=new HashMap();
    var tabMap3=new HashMap();

    initMap();

    function initMap() {
        tabMap1.put("tabPanelId","tabDetail1");
        tabMap1.put("1","projectCodeCheck");
        tabMap2.put("tabPanelId","tabDetail2");
        tabMap2.put("1","deptCodeCheck");
        tabMap3.put("tabPanelId","tabDetail3");
		tabMap3.put("1","customerInfoSetting");
	
    }
    function onCheckData1(){
         invokeSingleMethod("onCheckData",'',tabMap1);
    }
     function onCheckData2(){
         invokeSingleMethod("onCheckData",'',tabMap2);
    }
     function onCheckData3(){
         invokeSingleMethod("onCheckData",'',tabMap3);
    }
   
  
	function onBodyLoad() {
        initButton();
        onTabChange2();
        onTabChange3();
	}
    function refreshIFrame(iframeid,url,typename) {
        iframeid.onRefreshData(url,typename);
    }

    function onRefreshData(code) {
    }
    function getSelectedTabIndex(){
        return getTabIndex(tabDetail2);
    }

  function onTabChange1() {
   
        try {
           renderTabButton(tabDetail,btnPanel);
           projectCodeCheck.disableBtn();
        } catch(e) {
        
        }
    }
      function onTabChange2() {
   
        try {
           renderTabButton(tabDetail2,btnPanel2);
           deptCodeCheck.disableBtn();
        } catch(e) {
        
        }
    }
      function onTabChange3() {
   
        try {
           renderTabButton(tabDetail3,btnPanel3);
           customerInfoSetting.disableBtn();
        } catch(e) {
        
        }
    }
 
    function initButton() {

       addTabButtonByIndex(tabDetail1,1,"checkBtn","<bean:message bundle="application"  key="global.button.check"/>","onCheckData()");
       addTabButtonByIndex(tabDetail2,1,"checkBtn","<bean:message bundle="application"  key="global.button.check"/>","onCheckData2()");
       addTabButtonByIndex(tabDetail3,1,"checkBtn","<bean:message bundle="application"  key="global.button.check"/>","onCheckData3()");
     }
    
   
	</script>

	</head>
	<body >
		<TABLE width="100%" height="100%" CELLPADDING="1" CELLSPACING="0" BORDER="0" >
		<tr height="150">
				<td>
									<IFRAME 
										id="projectCodeCheck" 
										name="projectCodeCheck" 
										SRC="<%=actionSrc_projectCodeCheck%>" 
										tabindex="1" width="100%" 
										height="100%" frameborder="0" MARGINWIDTH="0" 
										MARGINHEIGHT="0" SCROLLING="no">
									</IFRAME>
				</td>
			</tr>
		    
			<tr height="150">
				<td>

									<IFRAME 
										id="deptCodeCheck" 
										name="deptCodeCheck" 
										SRC="<%=actionSrc_listCheck%>" 
										tabindex="1" width="100%" 
										height="100%" frameborder="0" MARGINWIDTH="0" 
										MARGINHEIGHT="0" SCROLLING="no">
									</IFRAME>
							
				</td>
			</tr>
			<tr height="150">
				<td>
									<iframe 
					                   id="customerInfoSetting"
                        			   name="customerInfoSetting"
                       				   SRC="<%=actionSrc_customerInfoCheckList%>"
                       				   tabindex="1"
                       				   width="100%" height="100%"  frameborder="0"  
                       			       MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
									</iframe>
				</td>
			</tr>
		</TABLE>
		<SCRIPT language="JavaScript" type="text/JavaScript" >
		   
		</SCRIPT>
	</body>
</html>
