<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate User Iframe Certain"/>
       <tiles:put name="jspName" value="Function"/>
</tiles:insert>
<STYLE>
.subbody {
       background-color: #eeeeee;
}
.headShowInfo {
       font-weight: 300;
       font-size: 14px;
</STYLE>

<script language="JavaScript">
function searchit() {
      var customerId = document.forms[0].customerId.value;
      var shortName = document.forms[0].shortName.value;
      if((customerId == null||customerId == "")
          &&(shortName == null||shortName == "")) {
        alert("<bean:message bundle="application" key="global.fill.first"/>");
        return false;
      }
      return true;
    //将查询结果添加到下面的Result中
   // window.parent.parent.parent.search_tool_result.add("Rong.Xiao","rongxiao","UUUU1","PG");
/*    window.parent.parent.parent.search_tool_result.add("Hua.Xiao","hua","UUUU2","PG");
window.parent.parent.parent.search_tool_result.add("Bo.Xiao","bo","UUUU3","TL");
    window.parent.parent.parent.search_tool_result.add("1","1","1","TL");
    window.parent.parent.parent.search_tool_result.add("2","2","2","TL");
    window.parent.parent.parent.search_tool_result.add("3","3","3","TL");
    window.parent.parent.parent.search_tool_result.add("4","4","4","TL");
    window.parent.parent.parent.search_tool_result.add("5","5","5","TL");
    */
}
</script>
<!-- 添加查询结果到Result Frame -->
<script type="text/javaScript">
function onBodyLoad(){
<logic:present name="webVo" >
  <logic:iterate id="element" name="webVo">
      window.parent.Search_tool_result.add('<bean:write name="element" filter="false" property="rid" format="#"/>',
                                            '<bean:write name="element" filter="false" property="attribute"/>',
                                            '<bean:write name="element" filter="false" property="customerId"/>',
                                            '<bean:write name="element" filter="false" property="regId"/>',
                                            '<bean:write name="element" filter="false" property="groupId"/>',
                                            '<bean:write name="element" filter="false" property="short_"/>',
                                            '<bean:write name="element" filter="false" property="nameCn"/>',
                                            '<bean:write name="element" filter="false" property="nameEn"/>',
                                            '<bean:write name="element" filter="false" property="belongBd"/>',
                                            '<bean:write name="element" filter="false" property="belongSite"/>',
                                            '<bean:write name="element" filter="false" property="class_"/>',
                                            '<bean:write name="element" filter="false" property="country"/>',
                                            '<bean:write name="element" filter="false" property="createDate" format="yyyy/MM/dd"/>',
                                            '<bean:write name="element" filter="false" property="alterDate" format="yyyy/MM/dd"/>',
                                            '<bean:write name="element" filter="false" property="creator"/>');
  </logic:iterate>
</logic:present>
  //转换限制参数
  var arguments= window.dialogArguments;
  var paramStr ="";
  var showStr = "";
  if(arguments!=null&&arguments.size()>0) {
  for(var m=0;m<arguments.size();m++) {
    if(m==0) {
      paramStr = arguments.keySet()[m]+"='"+arguments.values()[m][0]+"'";
      showStr = arguments.values()[m][1];
    } else {
      paramStr += " and "+arguments.keySet()[m]+"='"+arguments.values()[m][0]+"'";
      showStr += " and "+arguments.values()[m][1];
    }
  }
  }
 // document.getElementById("paramKeys").value = paramStr;
  if(showStr != "") {
   // var showHeadTitle = window.parent.search_tool_head.document.getElementById("showParam");
  //  showHeadTitle.innerText = "Query Param: "+showStr;
  //  showHeadTitle.title = showStr;
  }
}
function onPressEnter() {
   if(event.keyCode == 13) {
     document.getElementById("submit").click();
   }
}
</script>
</head>

<body class="subbody" leftmargin="0" topmargin="0" onload="onBodyLoad();" onkeydown="onPressEnter();">
<html:form  action="/common/querycustomer/queryCustomer" target="_self" method="post" onsubmit="return searchit();">
  <table border="0" cellspacing="1" cellpadding="1">
    <tr>
      <td width="460" background="../photo/search_tool/search_tool_back6.jpg" class="headShowInfo">&nbsp;&nbsp;<bean:message bundle="projectpre" key="projectCode.InputSearchKeys"/></td>
    </tr>
  </table>
  <table width="460" border="0" cellspacing="1" cellpadding="1">
  <tr>
  <TD height="10">
  </TD>
  </tr>
    <tr>
      <td width="16"></td>
      <td width="100" class="list_desc" align="left"><div align="left"><bean:message bundle="projectpre" key="projectCode.MasterData.CustomerNo"/></div></td>
      <td width="15" class="list_desc">&nbsp;</td>
      <td width="150" class="list_desc" align="left">
        <html:text fieldtype="text" name="customerId" beanName="QueryCustomer" styleId=""></html:text>
      </td>
      <td width="96" class="list_desc">&nbsp;</td>
    </tr>
    <tr>
      <td width="16"></td>
      <td width="100" class="list_desc" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message bundle="projectpre" key="projectCode.OR"/></td>
    </tr>
    <tr>
      <td width="16"></td>
      <td width="100" class="list_desc" align="left"><div align="left">
      <bean:message bundle="projectpre" key="customer.ClientShortName"/></div></td>
      <td width="15" class="list_desc">&nbsp;</td>
      <td width="150" class="list_desc" align="left">
        <html:text fieldtype="text" name="shortName" beanName="QueryCustomer" styleId=""></html:text>
      </td>
      <td width="96" class="list_desc">&nbsp;
      <html:submit styleId="" name="submit" onclick="return searchit()">
     <bean:message bundle="application" key="global.button.search"/>
     </html:submit>
      </td>
    </tr>
    <tr>
      <td><input type="hidden" id="paramKeys" name="paramKeys"></td>
    </tr>
  </table>
</html:form>
</body>
</html>
