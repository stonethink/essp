<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate User Iframe OBS"/>
        <tiles:put name="jspName" value="Function"/>
</tiles:insert>
</head>
<script language="JavaScript">
function searchit(form) {
    var sValue = form.organization.value;
    var sValue2 = lTrim(sValue);
    if((sValue2 == null)||(sValue2 == "")) {
      alert("Please input the OBS information first!");
      return false;
    }
    return true;
    //将查询结果添加到下面的Result中
    //add(prm_loginid,prm_name,prm_code,prm_post)
    //window.parent.parent.parent.alloc_tool_result.add("Rong.Xiao","rongxiao","UUUU1","PG");
    //document.alloc_obs.submit();
}
function lTrim(str) {
    if(str.charAt(0) == " ") {
	    str = str.slice(1);
		str = lTrim(str);
	}
	return str;
}
</script>
<%-- 添加查询结果到Result Frame --%>
  <logic:iterate id="element" name="webVo" property="detail" type="server.essp.common.humanAllocate.viewbean.VbGeneralUser">
    <script language="JavaScript">
     //add(prm_loginid,prm_name,prm_code,prm_post)
      window.parent.parent.alloc_tool_result.add('<bean:write name="element" property="loginid"/>',
                                                 '<bean:write name="element" property="name"/>',
                                                 '<bean:write name="element" property="code"/>',
                                                 '<bean:write name="element" property="sex"/>');
    </script>
  </logic:iterate>
<body class="subbody" leftmargin="0" topmargin="0">
<html:form action="/common/humanAllocate/alloc/UserAllocByOBS" onsubmit="return searchit(this);">
<table width="443" border="0" cellspacing="1" cellpadding="1">
    <tr>
      <td height="20"></td>
    </tr>
  </table>
  <table width="443" border="0" cellspacing="1" cellpadding="1">
    <tr>
      <td width="16"></td>
      <td width="127" class="list_desc"><div align="">Organization</div></td>
      <td width="15" class="list_desc">&nbsp;</td>
      <td width="200" class="list_desc">
        <html:select name="organization" styleId="sele">
          <option >--Select Organization--</option>
          <html:optionsCollection name="webVo" property="orgnizations">
          </html:optionsCollection>
        </html:select>
	  </td>
      <td width="96" class="list_desc">&nbsp;</td>
    </tr>
    <tr>
      <td></td>
      <td height="30" colspan="3" valign="bottom" class="list_desc"><div align="right">
          <input type="submit" class="bottom" value="Search" style="width:60" >
          &nbsp;&nbsp;&nbsp; </div></td>
      <td class="list_desc">&nbsp;</td>
    </tr>
</html:form>
</body>
