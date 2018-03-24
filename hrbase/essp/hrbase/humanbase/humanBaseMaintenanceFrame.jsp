<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<html>
	<head>
		<tiles:insert page="/layout/head.jsp">
		  <tiles:put name="title" value="Human Base Info"/>
		  <tiles:put name="jspName" value="humanBaseMaintenanceFrame"/>
		</tiles:insert>
	<style>
		#narrow_date {
			width:72;
		}
		#input_common {
			width:155;
		}
	</style>
	<script type="text/javascript" language="JavaScript">
	function delHumanBase(){
   searchList.delHumanBase();
}

function editHumanBase(){
 searchList.editHumanBase();
}

function viewHumanBase(){
  searchList.viewHumanBase();
}

function listEditingLog() {
  searchList.listEditingLog();
}
	
		function addHumanBase() {
		  var url='<%=request.getContextPath()%>/hrbase/humanbase/addHumanBase.jsp';
		  var leftPos=(screen.width-750)/2;
		  var topPos=(screen.height-400)/2;
		  var pos='width=750,height=400,left='+leftPos+',top='+topPos;
		  window.open(url, 'popupnav', pos);
		}
		
		function listAddingLog() {
		  var url='<%=request.getContextPath()%>/hrbase/humanbase/listAddingLog.do';
		  var leftPos=(screen.width-750)/2;
		  var topPos=(screen.height-400)/2;
		  var pos='width=800,height=450,left='+leftPos+',top='+topPos;
		  window.open(url, 'popupnav', pos);
		}
		
function onExport() {
	var form = document.forms[0];
	form.action="<%=request.getContextPath()%>/hrbase/humanbase/humanBaseExport.do";
	form.target="searchList";
	form.submit();
	
}
function onQuery() {
	var form = document.forms[0];
	form.action="<%=request.getContextPath()%>/hrbase/humanbase/humanBaseQuery.do";
	form.target="searchList";
	form.submit();
}
function onOpenData(){
       var height = 200;
       var width = 850; 
       var topis = (screen.height - height) / 2;
       var leftis = (screen.width - width) / 2;
       var option = "height=" + height + "px"  +", width=" + width + "px" +", top=" + topis + "px" +", left=" + leftis + "px"  +", toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no";
	   var url='<%=request.getContextPath()%>/hrbase/humanbase/SelectViewColumns.jsp';
	   var windowTitle="";
       window.open(url,windowTitle,option);
}
	</script>
	</head>
	<body>

		<html:tabpanel id="condition" width="100%">
			<%-- card title--%>
			<html:tabtitles>
				<html:tabtitle selected="true" width="230">
					<center>
						<a class="tabs_title"><bean:message bundle="hrbase" key="hrbase.humanBase.title.query"/></a>
					</center>
				</html:tabtitle>
			</html:tabtitles>
			<%-- card buttons--%>
			<html:form id="searchHrb" action="/hrbase/humanbase/humanBaseQuery.do" method="POST" target="searchList">
				<html:tabbuttons>
				<input type="button" value='<bean:message bundle="application"  key="global.button.open"/>' class="button" onClick="onOpenData();"/>
				<input type="button" value='<bean:message bundle="application" key="global.button.new"/>' class="button" onClick="addHumanBase();"/>
				<input type="button" value='<bean:message bundle="hrbase" key="hrbase.humanBase.btn.addLog"/>' class="button" onClick="listAddingLog();"/>
				<input type="button" value='<bean:message bundle="application" key="global.button.search"/>' class="button" onClick="onQuery();"/>
				<input type="button" value='<bean:message bundle="application" key="global.button.export"/>' class="button" onClick="onExport();"/>
        		<input type="reset" value='<bean:message bundle="application" key="global.button.reset"/>' name="Reset" class="button">
				</html:tabbuttons>
				<html:tabcontents>
					<html:tabcontent styleClass="wind">
						<html:outborder height="190" width="100%">
							<table width="90%">
								
								<tr>
									<td width="20"></td>
									<td width="120" align="left" class="list_range">
										<bean:message bundle="hrbase" key="hrbase.humanBase.employee" />
									</td>
									<td>
										<html:text name="employee" beanName="hrbSearch" styleId="" fieldtype="text" prev="" next="rm" />
									</td>
									<td width="60">
									</td>
									<td align="left" class="list_range" width="120">
										<bean:message bundle="hrbase" key="hrbase.humanBase.resManager" />
									</td>
									<td>
										<html:text name="rm" beanName="hrbSearch" styleId="" fieldtype="text" prev="employee" next="unitCode" />
									</td>
								</tr>
								
								<tr>
									<td width="20"></td>
									<td width="120" align="left" class="list_range">
										<bean:message bundle="hrbase" key="hrbase.dept.deptcode" />
									</td>
									<td>
										<html:text name="unitCode" beanName="hrbSearch" styleId="" fieldtype="text" prev="rm" next="mail" />
									</td>
									<td width="60">
									</td>
									<td align="left" class="list_range" width="120">
										<bean:message bundle="hrbase" key="hrbase.humanBase.email" />
									</td>
									<td>
										<html:text name="email" beanName="hrbSearch" styleId="" fieldtype="text" prev="unitCode" next="title" />
									</td>
								</tr>
								
								<tr>
									<td width="20"></td>
									<td class="list_range" width="120">
										<bean:message bundle="hrbase" key="hrbase.humanBase.title" />
									</td>
									<td width="120">
										<html:text name="title" beanName="hrbSearch" styleId="" fieldtype="text" prev="rm" next="site" />
									</td>
									<td width="60">
									</td>
									<td class="list_range" width="120">
										<bean:message bundle="hrbase" key="hrbase.humanBase.rank"/>
									</td>
									<td width="120" class="list_range">
										<html:text name="rank" beanName="hrbSearch" styleId="" fieldtype="text" prev="rm" next="site" />
									</td>
								</tr>
								
								<tr>
									<td width="20"></td>
									<td class="list_range" width="120">
										<bean:message bundle="hrbase" key="hrbase.humanBase.site"/>
									</td>
									<td>
										<ws:select id="input_common" property="site" type="server.essp.hrbase.site.logic.SiteSelectImpl" onchange="siteSelectChangeEventaccount('null');" orgIds="true"/>
									</td>
									<td width="20"></td>
									<td width="60" class="list_range"><bean:message bundle="hrbase" key="hrbase.humanBase.direct"/></td>
									<td>
										<html:select name="isDirect" styleId="input_common" beanName="hrbSearch" req="false">
											<option value="">
												Select All
											</option>
											<option value="D">
												Direct
											</option>
											<option value="I">
												Indirect
											</option>
										</html:select>
									</td>
								</tr>
								
								<tr>
									<td width="20"></td>
									<td class="list_range" width="120">
										<bean:message bundle="hrbase" key="hrbase.humanBase.inDate"/>
									</td>
									<td>
										<table border="0" cellspacing="0" cellpadding="0" align="left">
											<tr><td><html:text name="inDateBegin" beanName="hrbSearch" fieldtype="dateyyyymmdd" styleId="narrow_date" prev="" next="" ondblclick="getDATE(this);" defaultvalue="true" /></td>
												<td>~</td>
												<td><html:text name="inDateEnd" beanName="hrbSearch" fieldtype="dateyyyymmdd" styleId="narrow_date" prev="" next="" ondblclick="getDATE(this);" defaultvalue="true" /></td>
												</tr>
										</table>
									</td>
									<td width="20"></td>
									<td width="60" class="list_range"><bean:message bundle="hrbase" key="hrbase.humanBase.outDate"/></td>
									<td>
										<table border="0" cellspacing="0" cellpadding="0" align="left">
											<tr>
												<td><html:text name="outDateBegin" beanName="hrbSearch" fieldtype="dateyyyymmdd" styleId="narrow_date" prev="" next="" ondblclick="getDATE(this);" defaultvalue="true" /></td>
												<td>~</td>
												<td><html:text name="outDateEnd" beanName="hrbSearch" fieldtype="dateyyyymmdd" styleId="narrow_date" prev="" next="" ondblclick="getDATE(this);" defaultvalue="true" /></td>
											</tr>
										</table>
									</td>
								</tr>
								
								<tr>
									<td width="20"></td>
									<td class="list_range" width="120">
										<bean:message bundle="hrbase" key="hrbase.humanBase.attribute"/>
									</td>
									<td>
										<html:text name="attribute" beanName="hrbSearch" styleId="" fieldtype="text" prev="outDateEnd" next="formal" />
									</td>
									<td width="20"></td>
									<td width="60" class="list_range"><bean:message bundle="hrbase" key="hrbase.humanBase.formal"/></td>
									<td>
										<html:select name="formal" styleId="input_common" beanName="hrbSearch" req="false">
											<option value="">
												Select All
											</option>
											<option value="1">
												Formal
											</option>
											<option value="0">
												Informal
											</option>
										</html:select>
									</td>
								</tr>
								<tr>
								<td width="20"></td>
								<td class="list_range"><bean:message bundle="hrbase" key="hrbase.humanBase.isOnJob"/></td>
								<td>
									<html:select name="onJob" styleId="input_common" beanName="hrbSearch" req="false">
											<option value="">
												Select All
											</option>
											<option value="1" selected="selected">
												Y
											</option>
											<option value="0">
												N
											</option>
										</html:select>
								</td>
								</tr>
							</table>
						</html:outborder>
					
									<br/>
<center>
     <html:tabpanel id="hrbList" width="100%">
    <%-- card title--%>
    <html:tabtitles>
        <html:tabtitle selected="true" width="90">
            <center><a class="tabs_title"><bean:message bundle="hrbase" key="hrbase.humanBase.title.result"/></a></center>
        </html:tabtitle>
    </html:tabtitles>
    <%-- card buttons--%>
    <html:tabbuttons>
        <input type="button" value='<bean:message bundle="application" key="global.button.display"/>' class="button" onClick="viewHumanBase();" id="btnView" disabled/>
        <input type="button" value='<bean:message bundle="application" key="global.button.edit"/>' class="button" onClick="editHumanBase();" id="btnEdit" disabled/>
        <input type="button" value='<bean:message bundle="hrbase" key="hrbase.humanBase.btn.editLog"/>' class="button" onClick="listEditingLog();" id="btnEditingLog" disabled/>
        <input type="button" value='<bean:message bundle="application" key="global.button.delete"/>' class="button" onClick="delHumanBase();" id="btnDelete" disabled/>
    </html:tabbuttons>
    <html:outborder height="60%" width="100%">
     <iframe 
					id="searchList" 
					name="searchList"
					src="" 
					scrolling="auto" 
					height="100%" width="100%" 
					marginHeight="1" marginWidth="1" 
					frameborder="0"
					>
					</iframe>
  
     </html:outborder>
     
   </html:tabpanel>
</center>
							
					</html:tabcontent>
				</html:tabcontents>
			</html:form>
		</html:tabpanel>
	</body>
</html>
