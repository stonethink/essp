<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate User Iframe OBS"/>
        <tiles:put name="jspName" value="Function"/>
</tiles:insert>
        <style>
#sele_width {
	font-family: "Arial", "Helvetica", "sans-serif";
	font-size: 12px;
        width: 200;
}
        </style>
</head>
<script language="JavaScript">
function searchit(form) {
    var sValue = form.selectOrg.value;
    var sValue2 = lTrim(sValue);
    if((sValue2 == null)||(sValue2 == "")||sValue2=="Please Select") {
     alert("<bean:message bundle="application" key="global.select.OBS"/>");
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
function onPressEnter() {
   if(event.keyCode == 13) {
     document.getElementById("subSearch").click();
   }
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
<body class="subbody" leftmargin="0" topmargin="0" onkeydown="onPressEnter();">
<html:form action="/common/humanAllocate/alloc/UserAllocByOBS" onsubmit="return searchit(this);">
<table border="0" cellspacing="1" cellpadding="1">
    <tr>
      <td width="460" background="../photo/alloc_tool/alloc_tool_back6.jpg" class="allocname">&nbsp;&nbsp;
        <bean:message bundle="application" key="Human.card.AllocateOrganize"/>: </td>
    </tr>
  </table>
<table width="443" border="0" cellspacing="1" cellpadding="1">
    <tr>
      <td height="45"></td>
    </tr>
  </table>
  <center>
  <table width="350" align="center" border="0" cellspacing="1" cellpadding="1">
    <tr>
      <td width="90" class="list_desc"><bean:message bundle="application" key="Human.card.Organizeation"/></td>
      <td width="160" class="list_desc">
        <html:select beanName="webVo" name="selectOrg" styleId="sele_width">
          <option>Please Select</option>
          <html:optionsCollection name="webVo" property="orgnizations">
          </html:optionsCollection>
        </html:select>
	  </td><td></td>
          </tr>
          <tr><td></td> <bean:define id="isIncludeSubOrg" name="webVo" property="isIncludeSubOrg"/>
          <%
             String isChecked = "";
             if("true".equals(isIncludeSubOrg)) {
               isChecked = "CHECKED";
             }
          %>
      <td width="200" align="right" class="list_desc"><input id="chk_IncludeSubOrg" type="checkbox" name="isIncludeSubOrg" <%=isChecked%> value="<%=isIncludeSubOrg%>" onclick="javascript:if(this.checked) {this.value = 'true';} else {this.value = 'false';}"><bean:message bundle="application" key="Human.card.IncludeSubOBS"/></td>
      <td align="left" class="list_desc">
          <html:submit name="subSearch" styleId="" >
          <bean:message bundle="application" key="global.button.search"/> 
          </html:submit>
      </td>
    </tr>
    </table>
    </center>
</html:form>
</body>
