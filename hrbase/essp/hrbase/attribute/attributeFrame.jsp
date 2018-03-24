<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/inc/pagedef.jsp"%>
<html>
	<head>
	<tiles:insert page="/layout/head.jsp">
	  <tiles:put name="title" value="Human Attribute List" />
	  <tiles:put name="jspName" value="siteList"/>
	</tiles:insert>
	<script language="javascript">
	var currentRow;
	var listRid;
	function onSearch(){
	  var add='<%=request.getContextPath()%>/hrbase/hrbAttribute/hrbAttList.do';
	  searchForm.action=add;
	  searchForm.submit();
	}
	
	function onTimeoutSearch() {
		setTimeout("onSearch()", 500);
	}
	
	function onAdd() {
		searchList.onAdd();
	}
	
	function onDelete() {
		searchList.onDelete();
	}	
	</script>
	</head>
	<body>
	<html:tabpanel id="HrbAttributeList" width="98%">
	  <%-- card title--%>
	  <html:tabtitles>
	    <html:tabtitle selected="true" width="80">
	      <center>
	        <a class="tabs_title"><bean:message bundle="hrbase" key="hrbase.attrbute.hrbAttList"/></a>
	      </center>
	    </html:tabtitle>
	  </html:tabtitles>
	  <html:tabbuttons>
	  <input type="button" name="Search" value="<bean:message bundle="application" key="global.button.refresh"/>"  onclick="onSearch();" class="button" />
	  <input id="btnAdd" type="button" name="Add" value="<bean:message bundle="application" key="global.button.new"/>"  onclick="onAdd();" class="button" />
	  <%--input id="btnDelete" type="button" name="Delete" value="<bean:message bundle="application" key="global.button.delete"/>"  onclick="onDelete();" class="button" / --%>
	  </html:tabbuttons>
	  <html:tabcontents>
	    <html:tabcontent styleClass="wind">
	    <html:outborder height="50%" width="98%">
	 <html:form id="searchForm" action="/hrbase/hrbAttribute/hrbAttList" method="POST" target="searchList"/>
	  <iframe 
			id="searchList" 
			name="searchList"
			src="<%=request.getContextPath()%>/hrbase/hrbAttribute/hrbAttList.do" 
			scrolling="auto" 
			height="100%" width="100%" 
			marginHeight="0" marginWidth="0" 
			frameborder="0"
		>
	  </iframe>
	
	     </html:outborder>
	     <html:form id="generalForm" action="" method="POST" target="generalFrm" />
	     <html:form id="deleteForm" action="" method="POST"/>
	     <table align="center" width="100%" cellSpacing="0" cellPadding="0" >
	         <tr ><td height="150">
	                  <IFRAME id="generalFrm" name="generalFrm" SRC=""
	                  width="100%" height="100%" frameborder="no" border="0"
	                  MARGINWIDTH="0" MARGINHEIGHT="0" SCROLLING="no">
	                  </IFRAME>
						</td>
				  </tr>
	            </table>
	    </html:tabcontent>
	  </html:tabcontents>
	</html:tabpanel>
	
	</body>
	</html>
