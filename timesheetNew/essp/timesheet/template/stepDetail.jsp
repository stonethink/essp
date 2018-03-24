<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<%String contextPath = request.getContextPath();%>
<bean:parameter id="tempId" name="tempId"/>
<html>
	<head>
		<title>template Detail</title>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value=" " />
			<tiles:put name="jspName" value="" />
		</tiles:insert>
		<script language="JavaScript" type="text/javascript">
		var dataRid = null;
         function onAddData(){
        	stepList.onAddData();
         }
   
         function onSaveData() {
             stepGeneral.onSaveData();
         }
          
          function onDeleteData(){
          	stepList.onDeleteData();
          }
          
         function onBodyLoad() {
            initButton();
         }
          
          function initButton() {
            addTabButtonByIndex(tabDetail,1,"saveStepBtn","<bean:message bundle="application" key="global.button.save"/>","onSaveData()");
          }
          
          function onRefreshData(rid) {
          try{   
          	dataRid=rid;
          	if(rid==null || rid==""){          
          		stepGeneral.location="<%=contextPath%>/timesheet/Blank.jsp";
      		}else{  
             	stepGeneral.location="<%=contextPath%>/timesheet/template/viewStep.do?rid="+rid+"&tempId="+<%=tempId%>;           
            }
            onTabChange();
            }catch(e){}
          }
          
          function onTabChange() {
            try {
              	renderTabButton(tabDetail,btnPanel);                            
              	if((dataRid==null || dataRid=="")&&window.parent.delStepBtn!=null){     
              		window.parent.delStepBtn.disabled=true;              
              		document.all.saveStepBtn.disabled=true;
              	}else{
              		window.parent.delStepBtn.disabled=false;              
              		document.all.saveStepBtn.disabled=false;
              	}
            } catch(e) {
            }
          }       

      
	</script>
	</head>
	<body BOTTOMMARGIN="0">
		<TABLE width="100%" height="100%" CELLPADDING="1" CELLSPACING="0" BORDER="0">
			<tr height="30%">
				<td class="wind">
					<iframe id="stepList" src="<%=contextPath%>/timesheet/template/listStep.do?tempId=<%=tempId%>" scrolling="auto" height="100%" width="100%" marginHeight="0" marginWidth="0" frameborder="0">
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
						</html:tabtitles>
						<html:tabbuttons>
							<div id="btnPanel">
							</div>
						</html:tabbuttons>
						<html:tabcontents>
							<%--THE 1st Card--%>
							<html:tabcontent styleClass="wind">
								<html:outborder width="100%" height="99%">
									<IFRAME id="stepGeneral" name="stepGeneral" SRC="" tabindex="1" width="100%" height="100%" frameborder="0" MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
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
