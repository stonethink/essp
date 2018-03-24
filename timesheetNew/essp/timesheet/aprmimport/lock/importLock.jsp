<%@page contentType="text/html; charset=utf-8"%>
<%@include file="/inc/pagedef.jsp"%>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value="APRM Import Lock"/>
  <tiles:put name="jspName" value="."/>
</tiles:insert>
<style type="text">
  #input_date
  {
  width:180px;FONT-SIZE: 12px;
  }
</style>
<script language="javascript">
function getMyDATE(dateName){
     try{
    	var date = document.getElementById(dateName);
    	date.focus();
    	getDATE(date);
    	} catch(e){}
     }
var currentRowObj;
var listRid;
function onSearch(){
  var bDate=document.getElementsByName("searchBeginDate")[0].value;
  var eDate=document.getElementsByName("searchEndDate")[0].value;
  var add='<%=request.getContextPath()%>/timesheet/aprmimport/lock/ListImportLock.do?beginDate='+bDate+'&endDate='+eDate;
  searchForm.action=add;
  searchForm.submit();
  //btnAdd.disabled=false;
  
}

function onTimeOutSearch(){
  var bDate=document.getElementsByName("searchBeginDate")[0].value;
  var eDate=document.getElementsByName("searchEndDate")[0].value;
  var add='<%=request.getContextPath()%>/timesheet/aprmimport/lock/ListImportLock.do?beginDate='+bDate+'&endDate='+eDate;
  searchForm.action=add;
  setTimeout(function() {searchForm.submit();}, 200)
  //btnAdd.disabled=false;
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
<html:tabpanel id="OverTimeList" width="98%">
  <%-- card title--%>
  <html:tabtitles>
    <html:tabtitle selected="true" width="80">
      <center>
        <a class="tabs_title"><bean:message bundle="timesheet" key="importLock.common.list"/></a>
      </center>
    </html:tabtitle>
  </html:tabtitles>
  <html:tabbuttons>
  <table>
  <tr>
  <td width="150" >
  <html:text name="searchBeginDate"
                      		beanName="searchVo"
                      		fieldtype="dateyyyymmdd"
                      		styleId="input_date"
                      		imageSrc="<%=request.getContextPath()+"/image/cal.png"%>"
                      		imageWidth="18"
                      		imageOnclick="getMyDATE('searchBeginDate')"
                      		maxlength="10" 
                      		scope="session"
                      		ondblclick="getDATE(this)"
                            />
                           
    </td>
    <td width="1" >
    ~
     </td>
   <td width="150" >
  	<html:text name="searchEndDate"
                      		beanName="searchVo"
                      		fieldtype="dateyyyymmdd"
                      		styleId="input_date"
                      		imageSrc="<%=request.getContextPath()+"/image/cal.png"%>"
                      		imageWidth="18"
                      		imageOnclick="getMyDATE('searchEndDate')"
                      		maxlength="10" 
                      		scope="session"
                      		ondblclick="getDATE(this)"
                            />
    </td>
    <td width="20" >
    &nbsp;
     </td>
  	<td >
  <input type="button" name="Search" value="<bean:message bundle="application" key="global.button.search"/>"  onclick="onSearch();" class="button" />
  <input id="btnAdd" type="button" name="Add" value="<bean:message bundle="application" key="global.button.new"/>"  onclick="onAdd();" class="button" />
  <input id="btnDelete" type="button" name="Delete" value="<bean:message bundle="application" key="global.button.delete"/>"  onclick="onDelete();" class="button" />
     </td>
   </tr>
   </table>
  </html:tabbuttons>
  <html:tabcontents>
    <html:tabcontent styleClass="wind">
    <html:outborder height="60%" width="98%">
      	<html:form id="searchForm" action="/timesheet/aprmimport/lock/ListImportLock" method="POST" target="searchList"/>
      <iframe 
					id="searchList" 
					name="searchList"
					src="<%=request.getContextPath()%>/timesheet/aprmimport/lock/ListImportLock.do" 
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
                  <IFRAME id="generalFrm" name="generalFrm" SRC="general.jsp"
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
