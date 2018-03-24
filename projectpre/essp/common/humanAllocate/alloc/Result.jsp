<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="server.essp.common.ldap.LDAPConfig" %>
<%@ include file="/inc/pagedef.jsp" %>
<%-- 判断选择多个还单个人 --%>
<logic:present parameter="labors">
  <bean:parameter id="labors" name="labors" />
</logic:present>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate Multi Users"/>
        <tiles:put name="jspName" value="Result"/>
</tiles:insert>
</head>
<logic:present parameter="oldValue">
    <body leftmargin="0" topmargin="0" onload="onBodyLoad();">
</logic:present>
<logic:notPresent parameter="oldValue">
    <body leftmargin="0" topmargin="0">
</logic:notPresent>
<script language="JavaScript">
var up_down = 1;
var default_domain = '<%=LDAPConfig.getDefaultDomainId()%>';
<%--  设置帧上下覆盖 --%>
function goup() {
  up_down++;
  if(up_down==2) {
    window.top.content.rows = "30,*";
    tdgoup.innerText = "";
    //window.result.tdbotright.height = 285;
    window.result.tdbotleft.height = 270;
  } else {
    window.top.content.rows = "190,210";
    tdgodown.innerHTML = '<a href="#" onclick="godown()" class="linkup">6</a>';
//    window.result.tdbotright.height = 20;
  }
}
function godown() {
  up_down--;
  if(up_down==1) {
    window.top.content.rows = "190,210";
    tdgoup.innerHTML = '<a href="#" onclick="goup()" class="linkup">5</a>';
    window.result.tdbotleft.height = 135;
//    window.result.tdbotright.height = 150;
  } else {
    window.top.content.rows = "*,30";
    tdgodown.innerText = "";
  }
}
function checkAllTds(isItchecked) {
  //如果当前点击选中所有的items
  if(isItchecked) {
      var slen = window.result.frm21.bot_left_frm.result_check.length;
      var prm_box;
      var check_count = 0;
      for(i=1;i<slen;i++) {
        prm_box = eval("window.result.frm21.bot_left_frm.result_check["+i+"]");
        var j = slen-i;
        var prm_tr = eval("window.result.frm21.tr"+j);
//        var prm_bro_tr = eval("window.result.frm22.tr"+j);
        //如果行显示，表示需要被选中，如果不显示，则不选中
        if(prm_tr.style.display!="none") {
            prm_box.checked = true;
            prm_tr.className = "line3";
//            prm_bro_tr.className = "line3";
            check_count++;
        }
      }
      if(check_count==1) {
        tdItem.innerText = "1 <bean:message bundle="application" key="Human.card.ItemSelected"/>";
      } else {
        tdItem.innerText = check_count + " <bean:message bundle="application" key="Human.card.ItemsSelected"/>";
      }
      window.result.frm21.bot_left_frm.count_check.value = check_count;
  } else {
    var slen = window.result.frm21.bot_left_frm.result_check.length;
    var prm_box;
    var check_count = 0;
    for(i=1;i<slen;i++) {
      prm_box = eval("window.result.frm21.bot_left_frm.result_check["+i+"]");
      var j = slen-i;
      var prm_tr = eval("window.result.frm21.tr"+j);
//      var prm_bro_tr = eval("window.result.frm22.tr"+j);
      if(prm_tr.style.display!="none") {
        prm_box.checked = false;
        prm_tr.className = "line2";
//        prm_bro_tr.className = "line2";
        check_count++;
      }
    }
    tdItem.innerText = "<bean:message bundle="application" key="Human.card.NoItem"/>";
    window.result.frm21.bot_left_frm.count_check.value = 0;
  }
}
<%-- 选中所有记录 --%>
function checkall() {
  checkAllTds(document.alloc_tool_result.chSelectall.checked);
}

<%-- 添加查询结果，只添加loginid,name,code(type)和sex(domain)  --%>
function add(prm_loginid,prm_name,prm_para1,prm_para2) {
  if(prm_loginid == null || prm_loginid == "") {
    return;
  }
    var sAll = window.result.frm21.bot_left_frm.count_all.value;
	var iAll = parseInt(sAll);
	for(i=1;i<=iAll;i++) {
	    var prm_tr = eval("window.result.frm21.tr"+i);
		var j = iAll+1-i;
		var prm_box = eval("window.result.frm21.bot_left_frm.result_check["+j+"]");
		//alert(i);
		if((prm_tr.className!="linedelete")&&(prm_box.value==prm_name)) {
		    //prm_tr.style.display = "none";
			//alert(prm_tr.style.display);
			//alert(prm_tr.className);
		    //以下防止了查询结果中出现重复结果
			return;
		}
	}
        //分别添加左边和右边
    var strTitle = "";
    if(prm_para2.toLowerCase()!="male" && prm_para2.toLowerCase() != "female") {
      window.result.frm21.add(prm_loginid,prm_name,prm_para1,prm_para2);
      strTitle = "LoginId:"+prm_loginid+", \nName:"+prm_name+", \nType:"+prm_para1+", \nDomain:"+prm_para2;
    } else {
      window.result.frm21.add(prm_loginid,prm_name,"EMP",default_domain);
      strTitle = "LoginId:"+prm_loginid+", \nName:"+prm_name+", \nCode:"+prm_para1+", \nSex:"+prm_para2;
    }
     window.result.frm21.setNameTitle(strTitle);
//    window.result.frm22.add(prm_code,prm_name,prm_sex);
}
<%-- 添加查询结果，包含其他信息   --%>
function add_select(prm_loginid,prm_name,prm_code,prm_sex,sPostRank,sProSkill1,sProSkill2,sRank1,sOperate1,sRank2,sLanguage,sRank3,sManage,sRank4,type_list) {
    var sAll = window.result.frm21.bot_left_frm.count_all.value;
	var iAll = parseInt(sAll);
	for(i=1;i<=iAll;i++) {
	    var prm_tr = eval("window.result.frm21.tr"+i);
		var j = iAll+1-i;
		var prm_box = eval("window.result.frm21.bot_left_frm.result_check["+j+"]");
		//alert(i);
		if((prm_tr.className!="linedelete")&&(prm_box.value==prm_name)) {
		    //prm_tr.style.display = "none";
		    //alert(prm_tr.style.display);
		    //alert(prm_tr.className);
		    //以下防止了查询结果中出现重复结果
			return;
		}
	}
	window.result.frm21.add(prm_loginid,prm_name,"EMP",default_domain);
        var strTitle = "LgoninId:"+prm_loginid+", \nName:"+prm_name+", \nCode:"+prm_code+", \nSex:"+prm_sex+", \nPostRank:"
                                  +sPostRank+", \nProSkill:"+sProSkill1+"--"+sProSkill2+"--"+sRank1+", \nOperate1:"
                                  +sOperate1+"--"+sRank2+", \nLanguage:"+sLanguage+"--"+sRank3+", \nManage:"+sManage+"--"+sRank4;
        var re = /undefined/g;
        strTitle = strTitle.replace(re,"");
        window.result.frm21.setNameTitle(strTitle);
//	window.result.frm22.add_select(prm_code,prm_name,prm_sex,sPostRank,sProSkill1+" "+sProSkill2+" "+sRank1,sOperate1+" "+sRank2,sLanguage+" "+sRank3,sManage+" "+sRank4,type_list);
//	window.result.frm12.add(type_list);
}
<%-- 将选中记录放到左边Guide --%>
function exportit() {
    window.result.frm21.init();
}
<%-- 删除选中记录 --%>
function deleteit() {
    window.result.frm21.deleteit();
}
<logic:present parameter="oldValue">
function finish(){
  var count = window.result.frm21.bot_left_frm.count_all.value;
  var result = '<bean:write name="oldValue"/>';
  for(var i = 1;i <=count; i ++){
    var prm_box = eval("window.result.frm21.bot_left_frm.result_check["+i+"]");
    if(prm_box.checked==true){
      var tdloginid = eval("window.result.frm21.tdloginid"+(count-i+1));
      result = tdloginid.innerHTML;
    }
  }
  if(result == null)
    result = "";
  //将结果输入到源控件
  //var srcControl = eval("parent.parent.opener.<bean:write name="oldValue"/>");
  //srcControl.value = result;
  try{
    //parent.opener.afterSingleSelect(result);
    window.returnValue=result;
    window.close();
  }catch(e){
    alert(e);
  }
  return result;
}

function onBodyLoad(){
    try{
      window.returnValue="<bean:write name="oldValue"/>";
  }catch(e){
    //alert(e);
  }
}

</logic:present>
</script>

<form name="alloc_tool_result">
  <table class="tablecenter" CELLPADDING="0" CELLSPACING="0" width="98%"  align="center">
    <tr>
      <!--Title of card Start-->
      <td width="10"><img id="img1" src="../images/now_left.jpg" width="10" height="23"></td>
      <td id="td1" width="100" valign="bottom" background="../images/now_back.jpg" class="list_desc">
        <div align="center" style="CURSOR: hand">
        <bean:message bundle="projectpre" key="projectCode.SearchResult"/></div></td>
      <td width="10"><img id="img2" src="../images/now_right.jpg" width="10" height="23"></td>
        <!--Title of card End -->

      <td class="oracelltext"></td>
      <td class="oracelltext"><div align="right">
          <input type="checkbox" name="chSelectall" value="checkbox" onclick="checkall()">
           <bean:message bundle="projectpre" key="projectCode.selectAll"/>
           </div>
      </td>
      <td width="70" align="right">
        <table width="50%" border="0" align="center" cellpadding="1" cellspacing="1">
          <tr>
            <td height="10" class="np" align="center" id="tdgoup"><a href="#" onclick="goup()" class="linkup">5</a></td>
            <td width="3"></td>
            <td class="np" align="center" id="tdgodown"><a href="#" onclick="godown()" class="linkup">6</a></td>
            <td width="5"></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>

  <table width="100%" height="74%" border="0" cellpadding="0" cellspacing="0">
    <tr bordercolordark="#0033CC" width="100%" height="100%"  border="0" >
      <td width="1%" height="481" align="left" bordercolor="#003366"></td>
      <td width="99%" align="left" valign="top">
        <table width="98%" height="100%" border="0" cellpadding="1" cellspacing="0">
          <tr>
            <td bgcolor="#94AAD6"><table width="100%" height="100%" border="0" cellpadding="1" cellspacing="0">
          </tr>
          <tr>
            <td bgcolor="#FFFFFF">&nbsp;</td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</td>
</tr>
</table>
  <table width="100%" border="0" cellpadding="1" cellspacing="1">
    <tr>
      <td width="33%" align="center" class="howmany" id="tdItem"><bean:message bundle="application" key="Human.card.NoItem"/></td>
      <logic:present parameter="oldValue">
        <td width="52%" class="howmany"><input name="in" type="button" class="button" style="width:60" onclick="return finish()" value="Finish"></td>
      </logic:present>
      <logic:notPresent parameter="oldValue">
        <td width="52%" class="howmany">
        <html:button name="button" styleId="button" onclick="return exportit()" >
        《 <bean:message bundle="application" key="global.button.In"/>
      </html:button>
        </td>
      </logic:notPresent>
      <td width="16%" >
      <html:button name="button" styleId="button" onclick="return deleteit()" >
      <bean:message bundle="application" key="global.button.clear"/>
      </html:button>
      </td>
    </tr>
  </table>

  <div id="Layer1" class="divtype" style="position:absolute; z-index:0; left: 7px; visibility: visible; top: 26px; height:73%; width=98%;">
      <logic:notPresent parameter="labors">
        <iframe name=result
          src="IfmResult.jsp"
          frameBorder=0 width="98%"
          height="99%"
          scrolling="no"
          align="left">
        </iframe>
       </logic:notPresent>
       <logic:present parameter="labors">
         <iframe name=result
           src="IfmResult.jsp?labors=<bean:write name="labors"/>"
           frameBorder=0 width="98%"
           height="99%"
           scrolling="no"
           align="left">
         </iframe>
       </logic:present>
    </td></tr>
  </table>
</div>

</form>
</body>
</html>
