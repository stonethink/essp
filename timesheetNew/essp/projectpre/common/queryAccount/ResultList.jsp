<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" IssueTypeDefine"/>
  <tiles:put name="jspName" value="Result"/>
</tiles:insert>
</head>
<body class="subbody" leftmargin="0" topmargin="0" style="overflow-x:hidden;">
<form name="bot_left_frm" method="post">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" id="hidtable">
    <tr>
      <td>
	  <input type="hidden" name="count_all" value="0" size="3"><!-- 一共有多少个 -->
      <input type="hidden" name="count_check" value="0" size="3"><!-- 选择的数量 -->
        <input type="hidden" name="result_check" value="1">
		</td>
    </tr>
  </table>
  <table width="397" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td bgcolor="#cccccc">
        <table width="100%" border="0" cellspacing="1" cellpadding="0" style="table-layout:fixed">
		<tbody id="tbodyresult">

		</tbody>
        </table>
      </td>
    </tr>
  </table>


</form>
<script language="JavaScript">
hidtable.style.display = "none";
var last;
var firstag = true;
<%-- 点击每行事件 --%>
function onClickRow(prm_id) {
   var prm_tr = eval("tr"+prm_id);
   var prm_td = eval("td"+prm_id);
   //现在需要知道一共有多少个
   var iAll = parseInt(document.bot_left_frm.count_all.value);
   var j = iAll+1-prm_id;
   var count = document.bot_left_frm.count_all.value;
   var icount = parseInt(count);
   var prm_check = eval("document.bot_left_frm.result_check["+j+"]");
   for(var i = 1;i <= iAll;i++) {
       var c_tr = eval("tr"+i);
       var c_check = eval("document.bot_left_frm.result_check["+i+"]");
       c_tr.className = "line2";
       c_check.checked = false;
   }
   //修改checkbox的checked
   if(prm_check.checked == false) {//�޸�checkbox��checked
       prm_check.checked = true;
   } else {
       prm_check.checked = false;
   }
   //添加 arrow
   if(firstag) {
       prm_td.innerText = "4";
       firstag = false;
       last = prm_td;
   } else {
     last.innerText = "";
     prm_td.innerText = "4";
     last = prm_td;
   }
   //修改底色
   if(prm_tr.className == "line2") {
      prm_tr.className = "line3";
   } else {
       prm_tr.className = "line2";
   }

  //下面修改当前选中的个数
  var slen = document.bot_left_frm.result_check.length;
  var m = 0;
  var now_check = document.bot_left_frm.count_check.value;
  var inow_check = 0;

  for(i=1;i<slen;i++) {
    if(document.bot_left_frm.result_check[i].checked == true) {
      m++;
      inow_check++;
    }
  }
  var sOut;
  if(m==0) {
    sOut = "No item selected";
  } else if(m==1) {
    sOut = "1 item selected";
  } else {
    sOut = m+" items selected";
  }
  window.parent.parent.tdItem.innerText = sOut;
  document.bot_left_frm.count_check.value = inow_check;
  window.parent.parent.search_tool_result.chSelectall.checked = false;
}

<%-- 点击checkbox事件 --%>
function click_check(prm_id) {
    var iAll = parseInt(document.bot_left_frm.count_all.value);
    var j = iAll+1-prm_id;
	var count_all = document.bot_left_frm.count_all.value;
	var prm_check = eval("document.bot_left_frm.result_check["+j+"]");
	var if_checked = prm_check.checked;
	prm_check.checked = !(if_checked);
}

var num = 0;
<%-- 插入新的记录 --%>
function insert(parent,brother){
    num++;
    var p=document.getElementById(parent);
    var b=document.getElementById(brother);
    var tr=document.createElement('<tr id="tr'+num+'" onClick="onClickRow('+num+')" class="line2">' );
    var tdflag=document.createElement('<td width="21px" bgcolor="#FFFFFF" class="np" height="20" id="td'+num+'"></td>');
    var tdcheck=document.createElement('<td width="36px" class="oracelltext" id="tdcheck'+num+'"></td>');
    var tdloginid = document.createElement('<td width="110px" class="oracelltext" id="tdloginid'+num+'" style="WORD-BREAK: break-all; white-space:nowrap;"></td>');
    var tdname = document.createElement('<td width="222px" class="oracelltext" id="tdname'+num+'" style="WORD-BREAK: break-all; white-space:nowrap;"></td>');
    p.insertBefore(tr,b);

    tr.appendChild(tdflag);
    tr.appendChild(tdcheck);
    tr.appendChild(tdloginid);
    tr.appendChild(tdname);
}
<%-- 在tbodyresult添加新记录 --%>
function add(prm_loginid,prm_name){
  insert('tbodyresult','tr'+num);
  var tdcheck = eval("tdcheck"+num);
  var tdloginid = eval("tdloginid"+num);
  var tdname = eval("tdname"+num);
  tdcheck.innerHTML = "<div align='center'><input type='checkbox' name='result_check' value='"+prm_loginid+"' onClick='click_check("+num+")'></div>";
  tdname.innerText = prm_name;
  tdloginid.innerText = prm_loginid;
  //修改总数量
  var sCount = document.bot_left_frm.count_all.value;
  var iCount = parseInt(sCount);
  iCount++;
  document.bot_left_frm.count_all.value = iCount;
}
<%-- 设置Name上的Tip --%>
function setNameTitle(title) {
var tdname = eval("tdname"+num);
tdname.title = title;
}
<%--  删除选中的记录 --%>
function deleteit() {
    var sAll = document.bot_left_frm.count_all.value;
    var iAll = parseInt(sAll);

    for(i=1;i<=iAll;i++) {
      var prm_box = eval("document.bot_left_frm.result_check["+i+"]");
      var j = iAll+1-i;
      var prm_tr = eval("tr"+j);
      if(prm_box.checked) {
        prm_box.checked = false;
        prm_tr.className = "linedelete";
        prm_tr.style.display = "none";
      }
    }
    document.bot_left_frm.count_check.value = 0;
    window.parent.parent.tdItem.innerText = "No item selected";
    window.parent.parent.search_tool_result.chSelectall.checked = false;
}
</script>
</body>
</html>
