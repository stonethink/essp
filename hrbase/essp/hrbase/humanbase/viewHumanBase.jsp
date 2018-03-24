<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<html>
	<head>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value="View Human Base Info" />
			<tiles:put name="jspName" value="viewHumanBase" />
		</tiles:insert>
		<script type="text/javascript" language="javascript"> 
	     function onClickPrint(){
	      	if(confirm("<bean:message bundle="application" key="global.confirm.print"/>")){
	        	window.print();
	      	} else {
	        	return;
	     	}
	   	}
     	</script>
	</head>
	<body bgcolor="#ffffff" onload="this.focus();">
		<center class="form_title">
			<bean:message bundle="hrbase" key="hrbase.humanBase.title.view"/>
		</center>
		<center>
			<html:form id="viewForm" action="/hrbase/humanbase/viewHumanBase" method="POST">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr height="20">
						<td height="20"></td>
					</tr>
					<tr>
						<td width="20"></td>
						<td width="120" align="left" class="list_range">
							<bean:message bundle="hrbase" key="hrbase.humanBase.employeeId"/>
						</td>
						<td>
							<bean:write name="webVo" property="employeeId"/>
						</td>
						<td width="60">&nbsp;</td>
						<td align="left" class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.englishName"/>
						</td>
						<td>
							<bean:write name="webVo" property="englishName"/>
						</td>
						<td width="60">&nbsp;</td>
						<td align="left" class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.chineseName"/>
						</td>
						<td>
							<bean:write name="webVo" property="chineseName"/>
						</td>
					</tr>

					<tr height="20">
						<td height="20"></td>
					</tr>
					<tr>
						<td width="20"></td>
						<td width="120" align="left" class="list_range">
							<bean:message bundle="hrbase" key="hrbase.humanBase.title"/>
						</td>
						<td>
							<bean:write name="webVo" property="title"/>
						</td>
						<td width="60">&nbsp;</td>
						<td align="left" class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.rank"/>
						</td>
						<td>
							<bean:write name="webVo" property="rank"/>
						</td>
						<td width="60">&nbsp;</td>
						<td align="left" class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.resManager"/>
						</td>
						<td>
							<bean:write name="webVo" property="resManagerName"/>
						</td>
					</tr>
					<tr height="20">
						<td height="20"></td>

					</tr>
					<tr>
						<td width="20"></td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.site"/>
						</td>
						<td width="120">
							<bean:write name="webVo" property="site"/>
						</td>
						<td width="60">&nbsp;</td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.dept.deptcode"/>
						</td>
						<td width="120">
							<bean:write name="webVo" property="unitCode"/>
						</td>
						<td width="60">&nbsp;</td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.direct"/>
						</td>
						<td width="120">
							<bean:write name="webVo" property="isDirectName"/>
						</td>
					</tr>
					<tr height="20">
						<td height="20"></td>
					</tr>

					<tr>
						<td width="20"></td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.inDate"/>
						</td>
						<td>
							<bean:write name="webVo" property="inDate" format="yyyy-MM-dd"/>
						</td>
						<td width="20">&nbsp;</td>
						<td width="60" class="list_range">
							<bean:message bundle="hrbase" key="hrbase.humanBase.outDate"/>
						</td>
						<td>
							<bean:write name="webVo" property="outDate" format="yyyy-MM-dd"/>
						</td>
						<td width="20">&nbsp;</td>
						<td width="60" class="list_range">
							<bean:message bundle="hrbase" key="hrbase.humanBase.effectiveDate"/>
						</td>
						<td>
							<bean:write name="webVo" property="effectiveDate" format="yyyy-MM-dd"/>
						</td>
					</tr>

					<tr height="20">
						<td height="20"></td>
					</tr>
					
					<tr>
						<td width="20"></td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.hrAttribute"/>
						</td>
						<td>
							<bean:write name="webVo" property="hrAttribute"/>
						</td>
						<td width="20">&nbsp;</td>
						<td width="60" class="list_range">
							<bean:message bundle="hrbase" key="hrbase.humanBase.attribute"/>
						</td>
						<td>
							<bean:write name="webVo" property="attribute"/>
						</td>
						<td width="20">&nbsp;</td>
						<td width="60" class="list_range">
							<bean:message bundle="hrbase" key="hrbase.humanBase.formal"/>
						</td>
						<td>
							<bean:write name="webVo" property="isFormalName"/>
						</td>
					</tr>

					<tr height="20">
						<td height="20"></td>
					</tr>

					<tr>
						<td width="20"></td>
						<td class="list_range" width="120">
							<bean:message bundle="hrbase" key="hrbase.humanBase.email"/>
						</td>
						<td>
							<bean:write name="webVo" property="email"/>
						</td>
						<td width="20">&nbsp;</td>
						<td width="60" class="list_range">
						</td>
						<td>
						</td>
						<td width="20">&nbsp;</td>
						<td width="60" class="list_range">
						</td>
						<td>
						</td>
					</tr>
				</table>
				<br>
				<br>
				<html:button styleId="noPrint" name="printInfo" onclick="onClickPrint()">
					<bean:message bundle="application" key="global.button.print" />
				</html:button>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value='<bean:message bundle="application" key="global.button.close"/>' name="close" class="button" onClick="window.close();">
			</html:form>
		</center>
	</body>
</html>
