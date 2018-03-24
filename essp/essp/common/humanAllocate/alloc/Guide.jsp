<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:parameter id="oldValue" name="oldValue"/>
<bean:parameter id="labors" name="labors"/>
<bean:parameter id="type" name="type"/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate User Guide"/>
        <tiles:put name="jspName" value="Guide"/>
</tiles:insert>
</head>
<script language="JavaScript">
 var arguments = new HashMap();
 var isReturnObj;
//定义传入参数对象
function allocUser(loginId,name,domain,type) {
  this.loginId=loginId;
  this.name=name;
  this.domain=domain;
  this.type=type;
}

<%-- 调用IfmGuide中对应的方法 --%>
function deleteit() {
    window.subguide.moveout();
	document.alloc_tool_guide.chSelectall.checked = false;
}
function resetit() {
    window.subguide.resetIt();
}
function checkAllByTh() {
  var isChecked = document.alloc_tool_guide.chSelectall.checked;
  document.alloc_tool_guide.chSelectall.checked = !isChecked;
  checkAll();
}
<%-- 全部选中IfmGuide中的记录 --%>
function checkAll() {
    var chCount = window.subguide.alloc_tool_guide.chRes.length;
	var k = 0;
        //遍历所有记录，除已删除的都设置为选中的
	for(i=1;i<chCount;i++) {
		var j = i;
		var prm_tr = eval("window.subguide.tr"+j);

		if(prm_tr.className != "linedelete") {
		    window.subguide.alloc_tool_guide.chRes[i].checked = document.alloc_tool_guide.chSelectall.checked;
		    if(document.alloc_tool_guide.chSelectall.checked) {
		        prm_tr.className = "line3";
			    k++;
		    } else {
		        prm_tr.className = "line2";
		    }
		}
	}
	//设置共选中了多少条
	if(document.alloc_tool_guide.chSelectall.checked) {
	    window.subguide.alloc_tool_guide.count.value = k;
	} else {
	    window.subguide.alloc_tool_guide.count.value = 0;
	}
}
function checkResultDbClick() {
	if("<%=type%>"=="single") {
		finish();
	}
}
<%--  返回选中人员的LoginId串 --%>
function finish(){
  if("<%=type%>"=="single"&&window.subguide.alloc_tool_guide.count.value>1) {
    alert("<bean:message bundle="application" key="global.select.false"/>");
    return;
  }
  //alert(result);
  //将结果输入到源控件
  //var srcControl = eval("parent.parent.opener.<%=oldValue%>");
  //srcControl.value = result;
  try{
    if(isReturnObj) {
      createObjResult();
    }
    var returnStr = createStrResult();
    if(returnStr != "") {
      window.returnValue = returnStr;
    }
    window.close();
  }catch(e){
    alert(e);
  }
  parent.window.close();
}
//生成String结果
function createStrResult() {
    var result = "";
  var chCount = window.subguide.alloc_tool_guide.chRes.length;
  for(var i = 1 ; i < chCount ; i ++){
    var prm_tr = eval("window.subguide.tr"+i);
    if(prm_tr.className != "linedelete" && window.subguide.alloc_tool_guide.chRes[i].checked) {
      if(result == "")
         result = window.subguide.alloc_tool_guide.chRes[i].value;
      else
         result += "," + window.subguide.alloc_tool_guide.chRes[i].value;
    }
  }
  return result;
}
//生成HashMap结果
function createObjResult() {
  window.dialogArguments.clear();
  var chCount = window.subguide.alloc_tool_guide.chRes.length;
  for(var i = 1 ; i < chCount ; i ++){
    var prm_tr = eval("window.subguide.tr"+i);
    if(prm_tr.className != "linedelete" && window.subguide.alloc_tool_guide.chRes[i].checked) {
         var loginId = window.subguide.alloc_tool_guide.chRes[i].value;
         var name = window.subguide.document.getElementById("tdname"+i).innerText;
         var type = window.subguide.document.getElementById("tdemptype"+i).value;
         var domain = window.subguide.document.getElementById("tddomain"+i).value;
         var aUser = new allocUser(loginId,name,domain,type);
//         alert(aUser.domain);
         window.dialogArguments.put(loginId,aUser);
    }
  }
}
function showParamValues() {
  var obj2Str = "";
  arguments= window.dialogArguments;
  for(var m=0;m<arguments.size();m++) {
    //window.subguide.add(arguments.values()[m].loginId,arguments.values()[m].name,true);
    if(obj2Str == "") {
      obj2Str = arguments.values()[m].loginId;
    } else {
      obj2Str += "," + arguments.values()[m].loginId;
    }
  }
  return obj2Str;
}

function onBodyLoad(){
 arguments= window.dialogArguments;
 isReturnObj=((typeof arguments)=="object");
    try{
      if(isReturnObj) {
        var returnStr = showParamValues();
        window.returnValue = returnStr;
      } else {
        var allOldValues = "<%=oldValue%>";
				try{
        	allOldValues=parent.getOldValue();
        }catch(e){}
        var valueArray = allOldValues.split(",");
        var returnStr = "";
        for(var i = 0; i < valueArray.length;i++) {
          var subValues = valueArray[i].split("*");
          if(subValues.length > 0) {
            if(returnStr == "") {
              returnStr = subValues[0];
            } else {
              returnStr = returnStr +","+ subValues[0];
            }
          }
        }
        window.returnValue = returnStr;
      }
      //alert("<%=oldValue%>");
  }catch(e){
    //alert(e);
  }
}
</script>
<body  leftmargin="0" topmargin="0" class="body" onload="onBodyLoad();">
<form name="alloc_tool_guide" method="post" >
  <table width="100%" border="0" cellspacing="0" cellpadding="0"  bgcolor="#dddddd">
    <tr>
      <td width="64" background="../photo/alloc_tool/alloc_tool_back2.jpg"><img src="../photo/alloc_tool/alloc_tool_logo2.jpg" width="64" height="34"></td>
      <td valign="bottom" background="../photo/alloc_tool/alloc_tool_back2.jpg">
        <table align="center" width="100%" border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td valign="top" align="center" class="list_desc"><bean:message bundle="application" key="Human.card.ConfirmList" /></td>
          </tr>
        </table>
      </td>
      <td width="8"><img src="../photo/alloc_tool/alloc_tool_right.jpg" width="8" height="34"></td>
    </tr>
  </table>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#dddddd">
    <tr>
      <td width="5" valign="top"></td>
      <td valign="top" bgcolor="#94AAD6"><table width="100%" border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td height="352" valign="top" bgcolor="#eeeeee">
              <table width="290" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="20" colspan="2" bgcolor="#dddddd" class="oracelltext" id="itemCount">
                  </td>
                </tr>
                <tr>
                  <td width="290" bgcolor="#999999">
                    <table width="100%" border="0" cellspacing="1" cellpadding="1">
                      <tr>
                        <td bgcolor="#FFFFFF" class="np"><div align="center"></div></td>
                        <td width="30%" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
                          <div align="center"><bean:message bundle="application" key="UserAssignRoles.UserList.loginId" /></div></td>
                        <td width="52%" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
                          <div align="center"><bean:message bundle="application" key="UserAssignRoles.UserList.loginName" /></div></td>
                        <td width="10%" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext" style="cursor:hand" onclick="checkAllByTh()">
                          <div align="center"><bean:message bundle="projectpre" key="projectCode.check" /></div></td>
                      </tr>
                    </table></td>
                  <td width="18" bgcolor="#999999">
                    <table width="100%" border="0" cellspacing="1" cellpadding="1">
                      <tr>
                        <td background="../photo/alloc_tool/alloc_tool_back3.jpg">&nbsp;</td>
                      </tr>
                    </table></td>
                </tr>
              </table>
              <iframe src="IfmGuide.jsp?oldValue=<%=oldValue%>&labors=<%=labors%>&type=<%=type%>"  frameborder="0" scrolling="yes" width="290" height="88%" name="subguide">
              </iframe>
            </td>
          </tr>
        </table></td>
      <td width="5" valign="top"></td>
    </tr>
  </table>
  <table width="99%" border="0" cellspacing="1" cellpadding="1"  bgcolor="#dddddd">
    <tr>
      <td width="30%" class="oracelltext">
        <div align="right">
          <input type="checkbox" name="chSelectall" value="checkbox" onclick="checkAll()">
           <bean:message bundle="projectpre" key="projectCode.selectAll"/></div></td>
      <td>
        <div align="right">
          <html:button name="btn_ok" styleId="button" onclick="finish()">
          <bean:message bundle="application" key="global.button.confirm"/>
          </html:button>
          <html:button name="button" styleId="button" onclick="return resetit()">
          <bean:message bundle="application" key="global.button.reset"/>
          </html:button>
           <html:button name="button" styleId="button" onclick="return deleteit()">
          <bean:message bundle="application" key="global.button.Out"/> 》
          </html:button>
        </div></td>
    </tr>
  </table>
</form>
</body>
</html>
