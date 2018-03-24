<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate User Iframe Certain"/>
       <tiles:put name="jspName" value="Function"/>
</tiles:insert>
</head>
<script language="JavaScript">
function searchit() {
      var loginId = document.forms[0].loginId.value;
      var empName = document.forms[0].empName.value;
      var empCode = document.forms[0].empCode.value;
      if((loginId == null||loginId == "")
          &&(empName == null||empName == "")
          &&(empCode == null||empCode == "")) {
        alert("<bean:message bundle="application" key="global.fill.first"/>");
        return false;
      }
      return true;
    //将查询结果添加到下面的Result中
   // window.parent.parent.parent.alloc_tool_result.add("Rong.Xiao","rongxiao","UUUU1","PG");
/*    window.parent.parent.parent.alloc_tool_result.add("Hua.Xiao","hua","UUUU2","PG");
window.parent.parent.parent.alloc_tool_result.add("Bo.Xiao","bo","UUUU3","TL");
    window.parent.parent.parent.alloc_tool_result.add("1","1","1","TL");
    window.parent.parent.parent.alloc_tool_result.add("2","2","2","TL");
    window.parent.parent.parent.alloc_tool_result.add("3","3","3","TL");
    window.parent.parent.parent.alloc_tool_result.add("4","4","4","TL");
    window.parent.parent.parent.alloc_tool_result.add("5","5","5","TL");
    */
}
function onPressEnter() {
   if(event.keyCode == 13) {
     document.getElementById("submit").click();
   }
}
</script>
<%-- 添加查询结果到Result Frame --%>
<logic:present name="webVo" scope="request">
  <logic:iterate id="element" name="webVo" type="server.essp.common.humanAllocate.viewbean.VbGeneralUser" scope="request">
    <script language="JavaScript">
     //add(prm_loginid,prm_name,prm_code,prm_post)
      window.parent.parent.alloc_tool_result.add('<bean:write name="element" property="loginid"/>',
                                                 '<bean:write name="element" property="name"/>',
                                                 '<bean:write name="element" property="code"/>',
                                                 '<bean:write name="element" property="sex"/>');
    </script>
  </logic:iterate>
</logic:present>
<body class="subbody" leftmargin="0" topmargin="0" onkeydown="onPressEnter();">
<html:form  action="/common/humanAllocate/alloc/UserAllocDirect" target="_self" method="post" onsubmit="return searchit();">
  <table border="0" cellspacing="1" cellpadding="1">
    <tr>
      <td width="460" background="../photo/alloc_tool/alloc_tool_back6.jpg" class="allocname">&nbsp;&nbsp;<bean:message bundle="application" key="Human.card.AllocateTarget" />:</td>
    </tr>
  </table>
  <table width="460" border="0" cellspacing="1" cellpadding="1">
    <tr>
      <td width="16"></td>
      <td width="100" class="list_desc" align="left"><div align="left"><bean:message bundle="application" key="UserAssignRoles.UserList.loginId" /></div></td>
      <td width="15" class="list_desc">&nbsp;</td>
      <td width="150" class="list_desc" align="left">
        <html:text fieldtype="text" name="loginId" beanName="userAllocDirectForm" styleId=""></html:text>
      </td>
      <td width="96" class="list_desc">&nbsp;</td>
    </tr>
    <tr>
      <td width="16"></td>
      <td width="100" class="list_desc" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message bundle="projectpre" key="projectCode.OR" /></td>
    </tr>
    <tr>
      <td width="16"></td>
      <td width="100" class="list_desc" align="left"><div align="left"><bean:message bundle="application" key="Human.card.EmployeeName" /></div></td>
      <td width="15" class="list_desc">&nbsp;</td>
      <td width="150" class="list_desc" align="left">
        <html:text fieldtype="text" name="empName" beanName="userAllocDirectForm" styleId=""></html:text>
      </td>
      <td width="96" class="list_desc">&nbsp;</td>
    </tr>
    <tr>
      <td width="16"></td>
      <td width="100" class="list_desc" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message bundle="projectpre" key="projectCode.OR" /></td>
    </tr>
    <tr>
      <td width="16"></td>
      <td width="100" class="list_desc" align="left"><div align="left"><bean:message bundle="application" key="Human.card.EmployeeCode" /></div></td>
      <td width="15" class="list_desc">&nbsp;</td>
      <td width="150" class="list_desc" align="left">
        <html:text fieldtype="text" name="empCode" beanName="userAllocDirectForm" styleId=""></html:text>
      </td>
      <td width="96" class="list_desc">&nbsp;
     <html:submit styleId="" name="submit" onclick="return searchit()">
               <bean:message bundle="application" key="global.button.search"/>
        </html:submit>
          </td>
    </tr>
  </table>
</html:form>
</body>
</html>
