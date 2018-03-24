<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="/inc/pagedef.jsp"%>
<logic:present parameter="labors">
  <bean:parameter id="labors" name="labors"/>
</logic:present>
<html>
<head>
<tiles:insert page="/layout/head.jsp">
  <tiles:put name="title" value=" IssueTypeDefine"/>
  <tiles:put name="jspName" value="Result"/>
</tiles:insert>
</head>
<%int k = 10;%>
<body class="subbody" leftmargin="0" topmargin="0" onload="onBodyLoad()">
<form name="bot_left_frm" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="hidtable">
  <tr>
    <td>
      <input type="hidden" name="count_all" value="0" size="3">
      <!-- 一共有多少个 -->
      <input type="hidden" name="count_check" value="0" size="3">
      <!-- 选择的数量 -->
      <input type="hidden" name="result_check" value="1">
    </td>
  </tr>
</table>
<table width="397" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td bgcolor="#cccccc">
      <table width="100%" border="0" cellspacing="1" cellpadding="0" style="table-layout:fixed">
        <tbody id="tbodyresult">        </tbody>
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
   //右边对应的该条记录其他信息
//   var prm_bro_tr = eval("window.parent.frm22.tr"+prm_id);
   var prm_td = eval("td"+prm_id);
   //现在需要知道一共有多少个
   var iAll = parseInt(document.bot_left_frm.count_all.value);
    <logic:present parameter="oldValue">
       for(var i = 1;i <= iAll ;i ++){
         var tmp_prm_check = eval("document.bot_left_frm.result_check["+i+"]");
         tmp_prm_check.checked=false;
         var tmp_prm_tr = eval("tr"+i);
//         var tmp_prm_bro_tr = eval("window.parent.frm22.tr"+i);
         tmp_prm_tr.className = "line2";
//         tmp_prm_bro_tr.className = "line2"
       }
    </logic:present>
   var j = iAll+1-prm_id;
   var count = document.bot_left_frm.count_all.value;
   var icount = parseInt(count);
   var prm_check = eval("document.bot_left_frm.result_check["+j+"]");
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
//	  prm_bro_tr.className = "line3";
   } else {
       prm_tr.className = "line2";
//	   prm_bro_tr.className = "line2";
   }
//下面修改当前选中的个数
onSelectedItemCountChanged();

}

function onSelectedItemCountChanged() {

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
    sOut = "<bean:message bundle="application" key="Human.card.NoItem"/>";
  } else if(m==1) {
    sOut = "1 <bean:message bundle="application" key="Human.card.ItemSelected"/>";
  } else {
    sOut = m+" <bean:message bundle="application" key="Human.card.ItemsSelected"/>";
  }
  window.parent.parent.tdItem.innerText = sOut;
  document.bot_left_frm.count_check.value = inow_check;
  window.parent.parent.alloc_tool_result.chSelectall.checked = false;
}

<%-- 双击行事件 --%>
function onDblClickRow(prm_id) {
  var sAll = document.bot_left_frm.count_all.value;
  var iAll = parseInt(sAll);
  var i = iAll+1-prm_id;
  var prm_box = eval("document.bot_left_frm.result_check["+i+"]");
  var prm_tr = eval("tr"+prm_id);
  var sAll2 = window.top.alloc_tool_guide.subguide.alloc_tool_guide.allitem.value;
  var iAll2 = parseInt(sAll2);
  //向左边Guide中添加新记录，调用IfmGuide.add()方法，传入参数loginid,name
  var prm_td = eval("tdloginid"+prm_id);
//        alert("shangmian");
  var tdemptype = eval("tdemptype"+prm_id);
  var tddomain = eval("tddomain"+prm_id);
  var tdname = eval("tdname"+prm_id);
  window.top.alloc_tool_guide.subguide.add(prm_box.value,prm_td.innerText,tdemptype.value,tddomain.value,true);
  window.top.alloc_tool_guide.subguide.setNameTitle(tdname.title);
  //“删除”该记录
  prm_box.checked = false;
  prm_tr.className = "linedelete";
//        prm_bro_tr.className = "linedelete";
  prm_tr.style.display = "none";
//        prm_bro_tr.style.display = "none";
 //下面修改当前选中的个数
 onSelectedItemCountChanged();
 window.top.alloc_tool_guide.checkResultDbClick();
}

<%-- 点击checkbox事件 --%>
function click_check(prm_id) {
    var iAll = parseInt(document.bot_left_frm.count_all.value);
    <logic:present parameter="oldValue">
       for(var i = 1;i <= iAll ;i ++){
         var tmp_prm_check = eval("document.bot_left_frm.result_check["+i+"]");
         tmp_prm_check.checked=false;
         var prm_tr = eval("tr"+i);
//         var prm_bro_tr = eval("window.parent.frm22.tr"+i);
         prm_tr.className = "line2";
//         prm_bro_tr.className = "line2"
       }
    </logic:present>
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
    var tr=document.createElement('<tr id="tr'+num+'" ondblclick="onDblClickRow('+num+')" onClick="onClickRow('+num+')" class="line2">' );
    var tdflag=document.createElement('<td width="21px" bgcolor="#FFFFFF" class="np" height="20" id="td'+num+'"></td>');
    var tdcheck=document.createElement('<td width="36px" class="oracelltext" id="tdcheck'+num+'"></td>');
    var tdloginid = document.createElement('<td width="110px" class="oracelltext" id="tdloginid'+num+'" style="WORD-BREAK: break-all; white-space:nowrap;"></td>');
    var tdname = document.createElement('<td width="222px" class="oracelltext" id="tdname'+num+'" style="WORD-BREAK: break-all; white-space:nowrap;"></td>');
    var tdemptype = document.createElement('<td width="1" class="tableDataEvenNoDisplay" id="tdemptype'+num+'"></td>');
    var tddomain = document.createElement('<td width="1" class="tableDataEvenNoDisplay" id="tddomain'+num+'"></td>');
    p.insertBefore(tr,b);

    tr.appendChild(tdflag);
    tr.appendChild(tdcheck);
    tr.appendChild(tdloginid);
    tr.appendChild(tdname);
    tr.appendChild(tdemptype);
    tr.appendChild(tddomain);
}
<%-- 在tbodyresult添加新记录 --%>
function add(prm_loginid,prm_name,emptype,domain){
  if(checkExist(prm_loginid)) {
    return;
  }
  insert('tbodyresult','tr'+num);
  var tdcheck = eval("tdcheck"+num);
  var tdloginid = eval("tdloginid"+num);
  var tdname = eval("tdname"+num);
  var tdemptype = eval("tdemptype"+num);
  var tddomain = eval("tddomain"+num);
  tdcheck.innerHTML = "<div align='center'><input type='checkbox' name='result_check' value='"+prm_name+"' onClick='click_check("+num+")'></div>";
  tdname.innerText = prm_name;
  tdloginid.innerText = prm_loginid;
  tdemptype.value = emptype;
  tddomain.value = domain;
  //修改总数量
  var sCount = document.bot_left_frm.count_all.value;
  var iCount = parseInt(sCount);
  iCount++;
  document.bot_left_frm.count_all.value = iCount;
}
function checkExist(loginId) {
  var sReslen = document.bot_left_frm.count_all.value;
  var iReslen = parseInt(sReslen);
  var iiCount = 0;
  for(l=1;l<=iReslen;l++) {
    var trl = iReslen+1-l;
    var prm_box2 = eval("document.bot_left_frm.result_check["+l+"]");
    var tdloginid = eval("tdloginid"+trl+".innerText");
    var prm_tr2 = eval("tr"+trl);
    //如果右边Result中已存在该记录，设置其为可见
    var value2 = prm_box2.value;
    //alert("in:" + value2 + "   equal:" + (name1==value2));
    if((loginId==tdloginid)&&(prm_tr2.className="linedelete")) {
      prm_box2.checked = false;
      prm_tr2.className = "line2";
      prm_tr2.style.display = "block";
      return true;
    }
  }
  return false;
}
<%-- 设置Name上的Tip --%>
function setNameTitle(title) {
var tdname = eval("tdname"+num);
tdname.title = title;
}
<%-- 从右边Result向左边Guide添加选中的记录 --%>
function init() {
    var sAll = document.bot_left_frm.count_all.value;
    var iAll = parseInt(sAll);
    //遍历所有记录
    for(i=1;i<=iAll;i++) {
      var prm_box = eval("document.bot_left_frm.result_check["+i+"]");
      var j = iAll+1-i;
      var prm_tr = eval("tr"+j);
//      var prm_bro_tr = eval("window.parent.frm22.tr"+j);
      //如果选中，判断左边Guide是否有相同的记录
      if(prm_box.checked){
	var sAll2 = window.top.alloc_tool_guide.subguide.alloc_tool_guide.allitem.value;
	var iAll2 = parseInt(sAll2);
//	for(k=1;k<=iAll2;k++) {
//          var prm_tr2 = eval("window.top.alloc_tool_guide.subguide.tr"+k);
//          var prm_box2 = eval("window.top.alloc_tool_guide.subguide.alloc_tool_guide.chRes["+k+"]");
//          var iprm_box = prm_box.value;
//          var iprm_box2 = prm_box2.value;
//          //若已存在选中该条记录
//          if((prm_tr2.className!="linedelete")&&(iprm_box == iprm_box2)) {
//            alert("This item is already in your confirm list!");
//            prm_tr.click();
//            return false;
//          }
//        }
        //向左边Guide中添加新记录，调用IfmGuide.add()方法，传入参数loginid,name
        var prm_td = eval("tdloginid"+j);
//        alert("shangmian");
        var tdemptype = eval("tdemptype"+j);
        var tddomain = eval("tddomain"+j);
        var tdname = eval("tdname"+j);
        window.top.alloc_tool_guide.subguide.add(prm_box.value,prm_td.innerText,tdemptype.value,tddomain.value,true);
        window.top.alloc_tool_guide.subguide.setNameTitle(tdname.title);
        //“删除”该记录
        prm_box.checked = false;
        prm_tr.className = "linedelete";
//        prm_bro_tr.className = "linedelete";
        prm_tr.style.display = "none";
//        prm_bro_tr.style.display = "none";
      }
    }
    document.bot_left_frm.count_check.value = 0;//nowcheck =0;
    window.parent.parent.tdItem.innerText = "No item selected";
    window.parent.parent.alloc_tool_result.chSelectall.checked = false;
}
<%--  删除选中的记录 --%>
function deleteit() {
    var sAll = document.bot_left_frm.count_all.value;
    var iAll = parseInt(sAll);

    for(i=1;i<=iAll;i++) {
      var prm_box = eval("document.bot_left_frm.result_check["+i+"]");
      var j = iAll+1-i;
      var prm_tr = eval("tr"+j);
//      var prm_bro_tr = eval("window.parent.frm22.tr"+j);
      if(prm_box.checked) {
        prm_box.checked = false;
        prm_tr.className = "linedelete";
//        prm_bro_tr.className = "linedelete";
        prm_tr.style.display = "none";
        //alert(prm_tr.style.display);
//        prm_bro_tr.style.display = "none";
      }
    }
    document.bot_left_frm.count_check.value = 0;
    window.parent.parent.tdItem.innerText = "<bean:message bundle="application" key="Human.card.NoItem"/>";
    window.parent.parent.alloc_tool_result.chSelectall.checked = false;
}

<%--  删除选中的记录 --%>
function removeAll() {
    var sAll = document.bot_left_frm.count_all.value;
    var iAll = parseInt(sAll);

    for(i=1;i<=iAll;i++) {
      var prm_box = eval("document.bot_left_frm.result_check["+i+"]");
      var j = iAll+1-i;
      var prm_tr = eval("tr"+j);
      prm_box.checked = false;
      prm_tr.className = "linedelete";
      prm_tr.style.display = "none";
    }
    document.bot_left_frm.count_check.value = 0;
    window.parent.parent.tdItem.innerText = "No item selected";
    window.parent.parent.alloc_tool_result.chSelectall.checked = false;
}

<%-- 从调用页面的oldValue控件获取旧信息并初始化 --%>
function onBodyLoad(){
  try{
    var labors = '<bean:write name="labors"/>';
    var laborArray = labors.split(",");
    for(var j = 0 ;j < laborArray.length; j ++){
      if(laborArray[j]!=""){
        var subLaborArray = laborArray[j].split("*");
        if(subLaborArray.length == 4) {
          add(subLaborArray[0],subLaborArray[1],subLaborArray[2],subLaborArray[3],false);
          var strTitle = "LoginId:"+subLaborArray[0]+",\nName:"+subLaborArray[1]
          + ",\nType:"+subLaborArray[2]+",\nDomain:"+subLaborArray[3];
          setNameTitle(strTitle);
        }
      }
    }
  }catch(e){
    //alert("ifmGuide body load " + e);
  }
}
</script>
</body>
</html>
