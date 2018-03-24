<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp"%>
<html>
	<head>
		<tiles:insert page="/layout/head.jsp">
			<tiles:put name="title" value="Search User Iframe AD Group" />
			<tiles:put name="jspName" value="Function" />
		</tiles:insert>
	</head>
	<script language="javascript" src="<%=request.getContextPath()%>/js/hashmap.js"></script>
	<script language="JavaScript">
function searchit() {
      var groupCode = document.forms[0].empMail.value;
      if(groupCode == null || groupCode == "" || groupCode == "-1") {
         alert("<bean:message bundle="application" key="global.fill.first"/>");
         document.forms[0].groupCode.focus();
        return false;
      }
      return true;
}
function onPressEnter() {
   if(event.keyCode == 13) {
     document.getElementById("submit").click();
   }
}
</script>
	<%-- 添加查询结果到Result Frame --%>
	<logic:present name="webVo" scope="request">
		<logic:iterate id="element" name="webVo" property="detail" type="c2s.essp.common.user.DtoUserBase">
			<script language="JavaScript">
     //add(prm_loginid,prm_name,prm_code,prm_post)
      window.parent.parent.alloc_tool_result.add('<bean:write name="element" property="userLoginId"/>',
                                                 '<bean:write name="element" property="userName"/>',
                                                 '<bean:write name="element" property="userType"/>',
                                                 '<bean:write name="element" property="domain"/>');
    </script>
		</logic:iterate>
	</logic:present>
	<body class="subbody" leftmargin="0" topmargin="0" onkeydown="onPressEnter();">
		<html:form action="/common/humanAllocate/alloc/UserAllocInADGroup" target="_self" method="post" onsubmit="return searchit();">
			<table border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td width="460" background="../photo/alloc_tool/alloc_tool_back6.jpg" class="allocname">
						&nbsp;&nbsp;<bean:message bundle="application" key="Human.card.AllocateTarget" />:
					</td>
				</tr>
			</table>
			<table>
			<tr>
			<TD>
			<table width="300" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td width="16"></td>
					<td width="100" class="list_desc">
						<div align="left">
							<bean:message bundle="application" key="UserAssignRoles.UserList.domain" />
						</div>
					</td>
					<td width="5" class="list_desc"></td>
					<td width="150" class="list_desc">
						<ws:select id="input_common" property="selectDomain" type="server.essp.common.humanAllocate.logic.DomainSelectImpl" req="true" onchange="selectDomainSelectChangeEventgroup_code('null');" orgIds="" style="background-color:#FFFACD;width:155" />
					</td>
				</tr>
				<tr>
					<td width="16"></td>
					<td width="127" class="list_desc">
					</td>
					<td width="5" class="list_desc"></td>
					<td width="150" class="list_desc">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="16"></td>
					<td width="100" class="list_desc">
						<div align="left">
							<bean:message bundle="application" key="Human.card.Group" />
						</div>
					</td>
					<td width="15" class="list_desc"></td>
					<td width="150" class="list_desc">
						<ws:upselect id="group_code" property="empMail" up="selectDomain" type="server.essp.common.humanAllocate.logic.GroupSelectImpl" req="true" orgIds="" style="background-color:#FFFACD;width:155" />
					</td>
				</tr>
				<tr>
					<td width="16"></td>
					<td width="100" class="list_desc">
					</td>
					<td width="5" class="list_desc"></td>
					<td width="150" class="list_desc">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="16"></td>
					<td width="100" class="list_desc">
						<div align="left">
							
						</div>
					</td>
					<td width="5" class="list_desc"></td>
					<td width="150" class="list_desc">
						
					</td>
				</tr>
			</table>
			</TD>
			<TD width="20">&nbsp;</TD>
			<TD>
			<table width="130" border="0" cellspacing="1" cellpadding="1" align="right">
				<TR>
				    <td width="26"></td>
				    <td class="list_desc" align="right"></td>
				</TR>
				<TR>
				    <td class="list_desc" align="right">&nbsp;</td>
				</TR>
				<TR>
				    <td width="26"></td>
				    <td class="list_desc" align="right">
						
					</td>
				</TR>
				<TR>
				    <td class="list_desc" align="right">&nbsp;</td>
				</TR>
				<TR>
				    <td width="26"></td>
					<td class="list_desc" align="right">
				<html:submit styleId="" name="submit" onclick="return searchit()">
                    <bean:message bundle="application" key="global.button.search"/>
                  </html:submit>
					</td>
				</TR>
			</table>
			</TD>
			</tr>
			</table>
		</html:form>
	</body>
</html>
