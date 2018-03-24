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
}
</STYLE>

<script language="JavaScript">
function searchit() {
      var customerId = document.forms[0].accountId.value;
      var shortName = document.forms[0].accountName.value;
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
  var acntBrief = '<bean:write name="element" property="acntBrief"/>';
 // alert(acntBrief);
  while(acntBrief.indexOf("&lt;br&gt;")>=0){
    acntBrief = acntBrief.replace("&lt;br&gt;", "\r\n");
   }
  //alert(acntBrief);
  
  
      window.parent.Search_tool_result.add('<bean:write name="element" filter="false" property="rid" format="#"/>',
                                            '<bean:write name="element" filter="false" property="relPrjType"/>',
                                            '<bean:write name="element" filter="false" property="relParentId"/>',
                                            '<bean:write name="element" filter="false" property="acntId"/>',
                                            '<bean:write name="element" filter="false" property="acntName"/>',
                                            '<bean:write name="element" filter="false" property="acntFullName"/>',
                                            '<bean:write name="element" filter="false" property="customerId"/>',
                                            '<bean:write name="element" filter="false" property="achieveBelong"/>',
                                            '<bean:write name="element" filter="false" property="execSite"/>',
                                            '<bean:write name="element" filter="false" property="execUnitId"/>',
                                            '<bean:write name="element" filter="false" property="costAttachBd"/>',
                                            '<bean:write name="element" filter="false" property="bizSource"/>',
                                            '<bean:write name="element" filter="false" property="productName"/>',
                                            '<bean:write name="element" filter="false" property="acntAttribute"/>',
                                            '<bean:write name="element" filter="false" property="acntType"/>',
                                            '<bean:write name="element" filter="false" property="acntAnticipatedStart" format="yyyy/MM/dd"/>',
                                            '<bean:write name="element" filter="false" property="acntAnticipatedFinish" format="yyyy/MM/dd"/>',
                                            '<bean:write name="element" filter="false" property="acntPlannedStart" format="yyyy/MM/dd"/>',
                                            '<bean:write name="element" filter="false" property="acntPlannedFinish" format="yyyy/MM/dd"/>',
                                            '<bean:write name="element" filter="false" property="acntActualStart" format="yyyy/MM/dd"/>',
                                            '<bean:write name="element" filter="false" property="acntActualFinish" format="yyyy/MM/dd"/>',
                                            '<bean:write name="element" filter="false" property="estManmonth" format="#"/>',
                                            '<bean:write name="element" filter="false" property="actualManmonth" format="#"/>',
                                            '<bean:write name="element" filter="false" property="acntOrganization"/>',
                                            '<bean:write name="element" filter="false" property="acntCurrency"/>',
                                            '<bean:write name="element" filter="false" property="estSize"  format="#"/>',
                                            '<bean:write name="element" filter="false" property="acntStatus"/>',
                                            acntBrief,
                                            '<bean:write name="element" filter="false" property="acntInner"/>',
                                            '<bean:write name="element" filter="false" property="isTemplate"/>',
                                            '<bean:write name="element" filter="false" property="importTemplateRid" format=""/>',
                                            '<bean:write name="element" filter="false" property="contractAcntId"/>');
  </logic:iterate>
</logic:present>
  //转换限制参数
  var arguments;
  var personArg;
  var myObj = window.dialogArguments;
  if(myObj.acntMap!=null){
   //alert("new");
   arguments= myObj.acntMap;
   personArg = myObj.personMap;
  } else {
  //alert("old");
   arguments = window.dialogArguments;
  }
  //alert(arguments.keySet()[0]);
  //alert(arguments);
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
  var personStr = "";
  if(personArg!=null&&personArg.size()>0){
    for(var n=0;n<personArg.size();n++){
        if(n==0){
         personStr = "(c.personType='"+personArg.keySet()[n]+"'"+ " and "+"c.loginId='"+personArg.values()[n]+"')";
        } else {
         personStr += " or "+"(c.personType='"+personArg.keySet()[n]+"'"+ " and "+"c.loginId='"+personArg.values()[n]+"')";
        }
    }
    //alert(personStr);
  }
  document.getElementById("paramKeys").value = paramStr;
  document.getElementById("personKeys").value = personStr;
 // if(showStr != "") {
  //  var showHeadTitle = window.parent.search_tool_head.document.getElementById("showParam");
  //  showHeadTitle.innerText = "Query Param: "+showStr;
  //  showHeadTitle.title = showStr;
 // }
}
function onPressEnter() {
   if(event.keyCode == 13) {
      document.getElementById("submit").click();
   }
}
</script>
</head>

<body class="subbody" leftmargin="0" topmargin="0" onload="onBodyLoad();" onkeydown="onPressEnter();">
<html:form  action="/common/queryaccount/queryAccount" target="_self" method="post" onsubmit="return searchit();">
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
      <td width="100" class="list_desc" align="left"><div align="left"><bean:message bundle="projectpre" key="projectCode.projectId"/></div></td>
      <td width="15" class="list_desc">&nbsp;</td>
      <td width="150" class="list_desc" align="left">
        <html:text fieldtype="text" name="accountId" beanName="QueryAccount" styleId=""></html:text>
      </td>
      <td width="96" class="list_desc">&nbsp;</td>
    </tr>
    <tr>
      <td width="16"></td>
      <td width="100" class="list_desc" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<bean:message bundle="projectpre" key="projectCode.OR"/></td>
    </tr>
    <tr>
      <td width="16"></td>
      <td width="100" class="list_desc" align="left"><div align="left"><bean:message bundle="projectpre" key="projectCode.projectName"/></div></td>
      <td width="15" class="list_desc">&nbsp;</td>
      <td width="150" class="list_desc" align="left">
        <html:text fieldtype="text" name="accountName" beanName="QueryAccount" styleId=""></html:text>
      </td>
      <td width="96" class="list_desc">&nbsp;
     <html:submit styleId="" name="submit" onclick="return searchit()">
     <bean:message bundle="application" key="global.button.search"/>
     </html:submit></td>
    </tr>
    <tr>
      <td><input type="hidden" id="paramKeys" name="paramKeys"></td>
      <td><input type="hidden" id="personKeys" name="personKeys"></td>
    </tr>
  </table>
</html:form>
</body>
</html>
