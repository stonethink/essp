<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Result"/>
        <tiles:put name="jspName" value="Result"/>
</tiles:insert>
</head>
<body leftmargin="0" topmargin="0">
<script type="text/javascript" language="JavaScript" src="<%=request.getContextPath()%>/js/queryCustomer.js"></script>
<script language="JavaScript">
var up_down = 1;
var resultMap = new HashMap();
<%--  设置帧上下覆盖 --%>
function goup() {
  up_down++;
  if(up_down==2) {
    window.top.content.rows = "30,*";
    tdgoup.innerText = "";
    window.result.tdbotleft.height = 270;
  } else {
    window.top.content.rows = "130,300";
    tdgodown.innerHTML = '<a href="#" onclick="godown()" class="linkup">6</a>';
  }
}
function godown() {
  up_down--;
  if(up_down==1) {
    window.top.content.rows = "130,300";
    tdgoup.innerHTML = '<a href="#" onclick="goup()" class="linkup">5</a>';
    window.result.tdbotleft.height = 135;
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
        //如果行显示，表示需要被选中，如果不显示，则不选中
        if(prm_tr.style.display!="none") {
            prm_box.checked = true;
            prm_tr.className = "line3";
            check_count++;
        }
      }
      if(check_count==1) {
        tdItem.innerText = "1 <bean:message bundle="application" key="Human.card.ItemSelected"/>";
      } else {
        tdItem.innerText = check_count + "  <bean:message bundle="application" key="Human.card.ItemsSelected"/>";
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
      if(prm_tr.style.display!="none") {
        prm_box.checked = false;
        prm_tr.className = "line2";
        check_count++;
      }
    }
    tdItem.innerText = "<bean:message bundle="application" key="Human.card.NoItem"/>";
    window.result.frm21.bot_left_frm.count_check.value = 0;
  }
}
<%-- 选中所有记录 --%>
function checkall() {
  checkAllTds(document.search_tool_result.chSelectall.checked);
}

<%-- 添加查询结果 --%>
function add(rid,attribute,customer_Id,reg_id,group_rid,short_name,name_cn,name_en,belong_bd,belong_site,class_id,country,create_date,alter_date,creator) {
    var sAll = window.result.frm21.bot_left_frm.count_all.value;
	var iAll = parseInt(sAll);
	for(i=1;i<=iAll;i++) {
	    var prm_tr = eval("window.result.frm21.tr"+i);
		var j = iAll+1-i;
		var prm_box = eval("window.result.frm21.bot_left_frm.result_check["+j+"]");
		//alert(i);
		if((prm_tr.className!="linedelete")&&(prm_box.value==customer_Id)) {
		    //prm_tr.style.display = "none";
			//alert(prm_tr.style.display);
			//alert(prm_tr.className);
		    //以下防止了查询结果中出现重复结果
			return;
		}
	}
    //分别添加左边和右边
    var newCustomer = new customerObj(rid,attribute,customer_Id,reg_id,group_rid,short_name,name_cn,name_en,belong_bd,belong_site,class_id,country,create_date,alter_date,creator);
    resultMap.put(customer_Id,newCustomer);
    window.result.frm21.add(customer_Id,short_name);
    var strTitle = "Customer Id:"+customer_Id+", \nShort Name:"+short_name+", \nGroup Id:"+group_rid+", \nName_CN:"+name_cn
                            +", \nName_EN:"+name_en+", \nReg_Id:"+reg_id+", \nBelong_BD:"+belong_bd+", \nBelong_Site:"+belong_site
                            +", \nClass_Id:"+class_id+", \nCountry:"+country+", \nCreate_Date:"+create_date+", \nAlter_Date:"+alter_date
                            +", \nCreator:"+creator+", \nAttribute:"+attribute;
     window.result.frm21.setNameTitle(strTitle);
}
<%-- 删除选中记录 --%>
function deleteit() {
    window.result.frm21.deleteit();
}

function finish(){
  var count = window.result.frm21.bot_left_frm.count_all.value;
  var countChecked = window.result.frm21.bot_left_frm.count_check.value;
  if(countChecked>1) {
    alert("You should not choose more than one customer!");
    return false;
  }
  var result ;
  for(var i = 1;i <=count; i ++){
    var prm_box = eval("window.result.frm21.bot_left_frm.result_check["+i+"]");
    if(prm_box.checked==true){
      var tdloginid = eval("window.result.frm21.tdloginid"+(count-i+1));
      result = resultMap.get(tdloginid.innerHTML);
    }
  }
  try{
    //parent.opener.afterSingleSelect(result);
    window.returnValue=result;
    window.close();
  }catch(e){
    alert(e);
  }
  return result;
}

</script>

<form name="search_tool_result">
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
        <td width="50%" class="howmany" align="center">
        <html:button name="button" styleId="button" onclick="return finish()">
         <bean:message bundle="application" key="global.button.confirm"/>
        </html:button>
      <td width="17%" align="center">
      <html:button name="button" styleId="button" onclick="return deleteit()" >
      <bean:message bundle="application" key="global.button.clear"/>
      </html:button>
    
    </tr>
  </table>

  <div id="Layer1" class="divtype" style="position:absolute; z-index:0; left: 7px; visibility: visible; top: 26px; height:73%; width=98%;">
        <iframe name=result
          src="IfmResult.jsp"
          frameBorder=0 width="98%"
          height="99%"
          scrolling="no"
          align="left">
        </iframe>
</div>

</form>
</body>
</html>
