
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
    
    function onConfirmData(){
      exceptionList.onConfirmData();
    }
  	function onCancelData() {
      exceptionList.onCancelData();
  	}
    function refreshIFrame(iframeid,url,typename) {
        iframeid.onRefreshData(url,typename);
    }

    function onRefreshData(rid) {
       //alert(rid);
       if(rid==null || rid==""){
          exceptionGeneral.location="<%=contextPath%>/timesheet/Blank.jsp";
       }else{
        //alert("<%=contextPath%>/project/maintenance/previewBdCode.do?code="+rid);
          exceptionGeneral.location= "<%=contextPath%>/timesheet/syncexception/viewException.do?rid="+rid;
        }
    }  
	</script>

	</head>
	<body >
		<TABLE width="100%" height="100%" CELLPADDING="1" CELLSPACING="0" BORDER="0" >
			<tr height="40%" >
				<td class="wind">
				  
					<iframe 
					id="exceptionList" 
					src="<%=contextPath%>/timesheet/syncexception/listException.do" 
					scrolling="auto" 
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
								<span class="tabs_title"><bean:message bundle="timesheet" key="timesheet.syncexception.general"/></span>
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
									    id="exceptionGeneral" 
									    name="exceptionGeneral" 
									    SRC="" 
									    tabindex="1" width="100%" height="100%" 
									    frameborder="0" 
									    MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no"
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
		 
		</SCRIPT>
	</body>
</html>
