<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:parameter id="oldValue" name="oldValue"/>
<bean:parameter id="labors" name="labors"/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate User Guide"/>
        <tiles:put name="jspName" value="Guide"/>
</tiles:insert>
</head>
<script language="JavaScript">
<%-- 调用IfmGuide中对应的方法 --%>
function deleteit() {
    window.subguide.moveout();
	document.alloc_tool_guide.chSelectall.checked = false;
}
function resetit() {
    window.subguide.resetIt();
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
<%--  返回选中人员的LoginId串 --%>
function finish(){
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
  //alert(result);
  //将结果输入到源控件
  //var srcControl = eval("parent.parent.opener.<%=oldValue%>");
  //srcControl.value = result;
  try{
      window.returnValue=result;
      window.close();
  }catch(e){
    //alert(e);
  }
  parent.window.close();
}
function onBodyLoad(){
    try{
      window.returnValue="<%=oldValue%>";
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
        <table width="100%" border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td valign="top" class="list_desc">Now in Your Account :</td>
          </tr>
        </table>
      </td>
      <td width="14"><img src="../photo/alloc_tool/alloc_tool_right.jpg" width="14" height="34"></td>
    </tr>
  </table>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#dddddd">
    <tr>
      <td width="5" valign="top"></td>
      <td valign="top" bgcolor="#94AAD6"><table width="100%" border="0" cellpadding="1" cellspacing="1">
          <tr>
            <td height="352" valign="top" bgcolor="#eeeeee">
              <table width="245" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td height="20" colspan="2" bgcolor="#dddddd" class="oracelltext" id="itemCount">
                  </td>
                </tr>
                <tr>
                  <td width="227" bgcolor="#999999">
                    <table width="100%" border="0" cellspacing="1" cellpadding="1">
                      <tr>
                        <td bgcolor="#FFFFFF" class="np"><div align="center"></div></td>
                        <td width="38%" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
                          <div align="center">LoginId</div></td>
                        <td width="36%" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
                          <div align="center">Name</div></td>
                        <td width="18%" background="../photo/alloc_tool/alloc_tool_back3.jpg" class="oracelltext">
                          <div align="center">Check</div></td>
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
              <iframe src="IfmGuide.jsp?oldValue=<%=oldValue%>&labors=<%=labors%>"  frameborder="0" scrolling="yes" width="245" height="88%" name="subguide">
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
          Select All</div></td>
      <td>
        <div align="right">
          <input type="button" name="btn_ok" value="Finish" class="button" style="width:50" onclick="finish()">
          <input type="button" value="Reset" class="button" style="width:50" onclick="return resetit()">
          <input type="button" value="Out 》" class="button" style="width:50" onclick="return deleteit()">
        </div></td>
    </tr>
  </table>
</form>
</body>
</html>
