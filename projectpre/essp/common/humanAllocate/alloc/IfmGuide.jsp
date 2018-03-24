<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<bean:parameter id="oldValue" name="oldValue"/>
<bean:parameter id="labors" name="labors"/>
<bean:parameter id="type" name="type"/>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Allocate User Guide Iframe"/>
        <tiles:put name="jspName" value="Guide"/>
</tiles:insert>
</head>
<body  leftmargin="0" topmargin="0" onload="onBodyLoad()" class="subbody">
<script language="JavaScript">
var last;
var firstclick = true;
var nowcheck;//当前选中的行
var num=1;//放置的位置
var havebox = false;
var if_change = true;

<%-- 点击每行事件 --%>
function onClickRow(prm_id) {
    var prm_tr = eval("tr"+prm_id);//当前tr名
    var prm_td = eval("td"+prm_id);
    var i = parseInt(prm_id);//当前控件序号
    //点击时控制checkbox是否被选中
    var ifchecked = document.alloc_tool_guide.chRes[i].checked;
    document.alloc_tool_guide.chRes[i].checked = !(ifchecked);
    ifTrChange(prm_id);
    var sAllCount = document.alloc_tool_guide.chRes.length;//当前有多少个控件
    if("<%=type%>"=="single") {
      for(var m=1;m<sAllCount;m++) {
        if(m!=i) {
          document.alloc_tool_guide.chRes[m].checked = false;
           prm_tr = eval("tr"+m);
           prm_tr.className = "line2";
        }
      }
      if(ifchecked) {
        document.alloc_tool_guide.count.value = 0;
      } else {
        document.alloc_tool_guide.count.value = 1;
      }
    }
    var sCount = document.alloc_tool_guide.count.value;//当前多少控件被选中
    var itemCount = parseInt(sCount);
    var bCheckall = window.parent.alloc_tool_guide.chSelectall.checked;//selectall是否被选中
    if((bCheckall)||(itemCount<sAllCount-1)) {//如果不是所有的控件被选中，selectall selected应该是false
	   window.parent.alloc_tool_guide.chSelectall.checked = false;
    }
    //下面控制第一列是否显示 ARROW
    if(firstclick) {
      prm_td.innerText = "4";
      firstclick = false;
      last = prm_td;
    } else {
      last.innerText = "";
      prm_td.innerText = "4";
      last = prm_td;
    }
    //下面要对下拉框进行处理了
    nowcheck = prm_tr;//当前被选中的行
}

<%-- 双击行事件 --%>
function onDblClickRow(prm_id) {
      prm_td = eval("tdname"+prm_id);
      var prm_tdemptype = eval("tdemptype"+prm_id);
      var prm_tddomain = eval("tddomain"+prm_id);
        var prm_tr = eval("tr"+prm_id);
        var prm_box = eval("document.alloc_tool_guide.chRes["+prm_id+"]");
        var svalue = prm_box.value;
        document.alloc_tool_guide.chRes[prm_id].checked = false;
        prm_tr.className = "linedelete";
        //alert("move out: "+svalue+"  "+prm_tr.className);
        prm_tr.style.display = "none";
        var name1=prm_td.innerHTML;
        //alert("out:" + name1);
	//下面需要在右边添加这个resource
	var sReslen = window.top.alloc_tool_result.result.frm21.bot_left_frm.count_all.value;
	var iReslen = parseInt(sReslen);
//	var iiCount = 0;
//	for(l=1;l<=iReslen;l++) {
//	    var trl = iReslen+1-l;
//            var prm_box2 = eval("window.top.alloc_tool_result.result.frm21.bot_left_frm.result_check["+l+"]");
//            var prm_tr2 = eval("window.top.alloc_tool_result.result.frm21.tr"+trl);
////            var prm_tr3 = eval("window.top.alloc_tool_result.result.frm22.tr"+trl);
//            //如果右边Result中已存在该记录，设置其为可见
//            var value2 = prm_box2.value;
//            //alert("in:" + value2 + "   equal:" + (name1==value2));
//            if((name1==value2)&&(prm_tr2.className="linedelete")) {
//              //alert("存在！")
//              prm_box2.checked = false;
//              prm_tr2.className = "line2";
////              prm_tr3.className = "line2";
//              prm_tr2.style.display = "";
////              prm_tr3.style.display = "";
//              //window.top.alloc_tool_result.result.frm21.add(svalue,prm_td.innerText);
//              //window.top.alloc_tool_result.result.frm22.add("","","");
//              //window.top.alloc_tool_result.result.frm21.bot_left_frm.count_all.value = iReslen+1;
//              break;
//            } else {
//              iiCount++;
//            }
//          }
//          //如果右边Result中没有该记录，新增记录
//          if(iiCount == iReslen) {
            window.top.alloc_tool_result.result.frm21.add(svalue,prm_td.innerText,prm_tdemptype.value,prm_tddomain.value);
            window.top.alloc_tool_result.result.frm21.setNameTitle(prm_td.title);
//            window.top.alloc_tool_result.result.frm22.add("","","");
            //window.top.alloc_tool_result.result.frm21.bot_left_frm.count_all.value = iReslen+1;
//          }
      var sAll = document.alloc_tool_guide.howmanyitem.value;
      var iAll = parseInt(sAll);
      var iNow = iAll - 1;
      //判断左边实际还有多少条记录
      showLog(iNow);

      document.alloc_tool_guide.howmanyitem.value = iNow;
      document.alloc_tool_guide.count.value = (document.alloc_tool_guide.count.value-1);
}

//选中一行，改变底色
function ifTrChange(prm_id) {//tr是否变色
    var prm_tr = eval("tr"+prm_id);
    var sCount = document.alloc_tool_guide.count.value;
    var itemCount = parseInt(sCount);
    //下面控制的是行是否变色
    if(prm_tr.className=="line2") {
      prm_tr.className = "line3";
      itemCount++;
    } else {
      prm_tr.className = "line2";
      itemCount--;
    }
    //同时保持被选中的个数与点击实际情况一致
    document.alloc_tool_guide.count.value = itemCount;
}

function onClickCheck(prm_id) {
    //点击CHECKBOX的时候同时触发了CLICKTR事件，CHECKBOX的checked 值与实际情况相反，因此要再修正一次
	var i = parseInt(prm_id);
	var ifchecked = document.alloc_tool_guide.chRes[i].checked;
	document.alloc_tool_guide.chRes[i].checked = !(ifchecked);
}


function if_now_change() {
    if_change = !(if_change);
}

//在页面上插入一行记录
function insert(parent,brother){
  var p=document.getElementById(parent);
  var b=document.getElementById(brother);
  var tr=document.createElement('<tr id="tr'+num+'" ondblclick="onDblClickRow('+num+')" onClick="onClickRow('+num+')" class="line2">' );
  //alert(num);
  //插入行的行号
  var tdflag=document.createElement('<td bgcolor="#FFFFFF" class="np" id="td'+num+'"></td>');
  var tdloginid = document.createElement('<td width="30%" class="oracelltext" id="tdloginid'+num+'" style="WORD-BREAK: break-all; white-space:nowrap;"></td>');
  var tdname=document.createElement('<td width="51%" class="oracelltext" id="tdname'+num+'" style="WORD-BREAK: break-all; white-space:nowrap;"></td>');
  var tdcheck = document.createElement('<td width="12%" id="tdcheck'+num+'"></td>');
  var tdemptype = document.createElement('<td width="0" id="tdemptype'+num+'" class="tableDataEvenNoDisplay"></td>');
  var tddomain = document.createElement('<td width="0" id="tddomain'+num+'" class="tableDataEvenNoDisplay"></td>');
  p.insertBefore(tr,b);

  tr.appendChild(tdflag);
  tr.appendChild(tdloginid);
  tr.appendChild(tdname);
  tr.appendChild(tdcheck);
  tr.appendChild(tdemptype);
  tr.appendChild(tddomain);
}
//向tbodyguide中新增一个人的记录，prm_name:新增人的姓名,prm_loginid新增人的登录Id
function add(prm_name,prm_loginid,emptype,domain,checked){
    var iLen = document.alloc_tool_guide.chRes.length;
    //如果是单人模式&&新增行需要选中，取消其它选中行
    if("<%=type%>"=="single"&&checked) {
      for(var m=1;m<iLen;m++) {
        if(document.alloc_tool_guide.chRes[m].checked) {
          document.alloc_tool_guide.chRes[m].checked = false;
          var tmp_prm_tr = eval("tr"+m);
          if(tmp_prm_tr.className == "line3") {
            tmp_prm_tr.className = "line2";
          }

        }
      }
      document.alloc_tool_guide.count.value = 0;
    }
    for(var i = 1;i < iLen ;i ++){
        try{
            var tr = eval("tr"+i);
            if(tr != null){
                var loginId = eval("tdloginid"+i+".innerHTML");
                if(loginId == prm_loginid) {
                  if(tr.style.display=="none") {
                    tr.style.display = "block";
                     //下面要增加计数器的值
                    itemCountAddOne(true);
                  }
                  if(checked){
                    document.alloc_tool_guide.chRes[i].checked = true;
                    tr.className="line3";
                    var count = document.alloc_tool_guide.count.value;
                    document.alloc_tool_guide.count.value = (count + 1);
                  }else {
                    tr.className="line2";
                  }
                  return;
                }
            }
        }catch(e){
        }
    }
	insert('tbodyguide','tr'+num);
//    alert(prm_name+':,num:'+num);
        var tr = eval("tr"+num);
	var tdname = eval("tdname"+num);
	var tdloginid = eval("tdloginid"+num);
	var tdcheck = eval("tdcheck"+num);
        var tdemptype = eval("tdemptype"+num);
        var tddomain = eval("tddomain"+num);
        tdemptype.value = emptype;
        tddomain.value = domain;

	tdname.innerText = prm_name;
        tdloginid.innerText = prm_loginid;
        if(checked){
           tr.className="line3";
           tdcheck.innerHTML = "<div align='center'><input type='checkbox' name='chRes' value='"+prm_loginid+"' checked onClick='onClickCheck("+num+")'></div>";
           var count = document.alloc_tool_guide.count.value;
           document.alloc_tool_guide.count.value = (count + 1);
         }else{
          tr.className="line2";
          tdcheck.innerHTML = "<div align='center'><input type='checkbox' name='chRes' value='"+prm_loginid+"' onClick='onClickCheck("+num+")'></div>";
        }
        num++;

	//下面要增加计数器的值
	itemCountAddOne(false);
}

function itemCountAddOne(isOnlyShow) {
  if(!isOnlyShow) {
    var nowCount = document.alloc_tool_guide.allitem.value;
    var inowCount = parseInt(nowCount);
    inowCount++;
    document.alloc_tool_guide.allitem.value = inowCount;
  }
  var nowShow = document.alloc_tool_guide.howmanyitem.value;
  var iNowshow = parseInt(nowShow);
  iNowshow++
  document.alloc_tool_guide.howmanyitem.value = iNowshow;
  //下面控制显示的数值
  showLog(iNowshow);
}

<%-- 设置Name上的Tip --%>
function setNameTitle(title) {
  var lastTdNum = num-1;
  var tdname = eval("tdname"+lastTdNum);
//  alert(tdname.value);
  tdname.title = title;
}
//将选中记录从Guide移动到Result中
function moveout() {
    //判断是否已选择记录
    var sNowselect = document.alloc_tool_guide.count.value;
    var iNowselect = parseInt(sNowselect);
    if(iNowselect<1) {
      alert("<bean:message bundle="application" key="global.select.first"/>");
      return false;
    }

    var iLen = document.alloc_tool_guide.chRes.length;
    var iOut = 0;
    //遍历左边Guide中的记录，若选中设置为不可见
    for(i=1;i<iLen;i++) {
      prm_td = eval("tdname"+i);
      var prm_tdemptype = eval("tdemptype"+i);
      var prm_tddomain = eval("tddomain"+i);
      var prm_tr = eval("tr"+i);
      if(document.alloc_tool_guide.chRes[i].checked && prm_tr.style.display != "none") {
        var prm_box = eval("document.alloc_tool_guide.chRes["+i+"]");
        var svalue = prm_box.value;
        document.alloc_tool_guide.chRes[i].checked = false;
        iOut++;
        prm_tr.className = "linedelete";
        //alert("move out: "+svalue+"  "+prm_tr.className);
        prm_tr.style.display = "none";
        var name1=prm_td.innerHTML;
        //alert("out:" + name1);
	//下面需要在右边添加这个resource
	var sReslen = window.top.alloc_tool_result.result.frm21.bot_left_frm.count_all.value;
	var iReslen = parseInt(sReslen);
//	var iiCount = 0;
//	for(l=1;l<=iReslen;l++) {
//	    var trl = iReslen+1-l;
//            var prm_box2 = eval("window.top.alloc_tool_result.result.frm21.bot_left_frm.result_check["+l+"]");
//            var prm_tr2 = eval("window.top.alloc_tool_result.result.frm21.tr"+trl);
////            var prm_tr3 = eval("window.top.alloc_tool_result.result.frm22.tr"+trl);
//            //如果右边Result中已存在该记录，设置其为可见
//            var value2 = prm_box2.value;
//            //alert("in:" + value2 + "   equal:" + (name1==value2));
//            if((name1==value2)&&(prm_tr2.className="linedelete")) {
//              //alert("存在！")
//              prm_box2.checked = false;
//              prm_tr2.className = "line2";
////              prm_tr3.className = "line2";
//              prm_tr2.style.display = "";
////              prm_tr3.style.display = "";
//              //window.top.alloc_tool_result.result.frm21.add(svalue,prm_td.innerText);
//              //window.top.alloc_tool_result.result.frm22.add("","","");
//              //window.top.alloc_tool_result.result.frm21.bot_left_frm.count_all.value = iReslen+1;
//              break;
//            } else {
//              iiCount++;
//            }
//          }
//          //如果右边Result中没有该记录，新增记录
//          if(iiCount == iReslen) {
            window.top.alloc_tool_result.result.frm21.add(svalue,prm_td.innerText,prm_tdemptype.value,prm_tddomain.value);
            window.top.alloc_tool_result.result.frm21.setNameTitle(prm_td.title);
//            window.top.alloc_tool_result.result.frm22.add("","","");
            //window.top.alloc_tool_result.result.frm21.bot_left_frm.count_all.value = iReslen+1;
//          }

        }
      }
      var sAll = document.alloc_tool_guide.howmanyitem.value;
      var iAll = parseInt(sAll);
      var iNow = iAll - iOut;
      //判断左边实际还有多少条记录
      showLog(iNow);

      document.alloc_tool_guide.howmanyitem.value = iNow;
      document.alloc_tool_guide.count.value = 0;
}

function resetIt() {
    var ifreset = confirm("<bean:message bundle="application" key="global.reset.check"/>");
	if(!(ifreset)) {
	    return false
	}
    //进入页面时记录的条数
    var sReset = document.alloc_tool_guide.iReset.value;
    var iReset = parseInt(sReset);
    var sAll = document.alloc_tool_guide.allitem.value;
    var iAll = parseInt(sAll);

    for(i=1;i<=iAll;i++) {
      var prm_tr = eval("tr"+i);
      if(prm_tr==null)
        break;
      var prm_box = eval("document.alloc_tool_guide.chRes["+i+"]");
      if(i<=iReset) {
        prm_tr.style.display = "block";
        prm_tr.className = "line2";
        prm_box.checked = false;
      } else {
        prm_tr.style.display = "none";
        prm_tr.className = "linedelete";
        prm_box.checked = false;
      }
    }
//    //下面修改显示的值
    document.alloc_tool_guide.count.value = 0;
    document.alloc_tool_guide.howmanyitem.value = document.alloc_tool_guide.iReset.value;
    //var iNow = parseInt(document.alloc_tool_guide.howmanyitem.value);
    //showLog(iNow);
    //chackall

    window.parent.alloc_tool_guide.chSelectall.checked = false;
    onBodyLoad();
}
//根据记录数量显示标题（有几条记录）
function showLog(count){
    if(count==0) {
      window.parent.itemCount.innerHTML = "&nbsp;<span class='np'>2</span>&nbsp; <bean:message bundle="application" key="Human.card.ThereIs"/> <strong><bean:message bundle="application" key="Human.card.No"/></strong> <bean:message bundle="application" key="Human.card.Iteminlist"/>";
    } else if(count==1){
      window.parent.itemCount.innerHTML = "&nbsp;<span class='np'>2</span>&nbsp;<bean:message bundle="application" key="Human.card.Thereis"/>  <strong>1</strong><bean:message bundle="application" key="Human.card.Iteminlist"/>";
    } else {
      window.parent.itemCount.innerHTML = "&nbsp;<span class='np'>2</span>&nbsp;<bean:message bundle="application" key="Human.card.Thereare"/><strong>"+count+"</strong><bean:message bundle="application" key="Human.card.Itemsinlist"/>";
    }
}
<%-- 从调用页面的oldValue控件获取旧信息并初始化 --%>
function onBodyLoad(){
  try{
    //var iNow = parseInt(document.alloc_tool_guide.iReset.value);
    //showLog(iNow);
    //alert(parent.parent.opener.<%=oldValue%>.value);
    //var srcControl = eval("parent.parent.opener.<%=oldValue%>");
    //alert('<%=oldValue%>');
    var labors = '<%=labors.trim()%>';
    var oldData = '<%=oldValue.trim()%>';
    var laborArray = labors.split(",");
    var oldDataArray = oldData.split(",");
    var arguments = window.dialogArguments;
//    document.alloc_tool_guide.count.value = oldDataArray.length;
    var sum = 0
    //先添加旧的人员，再增加（Account中的人员－旧的人员）
    for(var i = 0 ;i < oldDataArray.length;i ++){
      if(oldDataArray[i]!=""){
        var oldSubDataArray = oldDataArray[i].split("*");
        if(oldSubDataArray.length == 4) {
          add(oldSubDataArray[1],oldSubDataArray[0],oldSubDataArray[2],oldSubDataArray[3],true);
          var strTitle = "LoginId:"+oldSubDataArray[0]+",\nName:"+oldSubDataArray[1]
          + ",\nType:"+oldSubDataArray[2]+",\nDomain:"+oldSubDataArray[3];
          setNameTitle(strTitle);
          sum ++;
        }
      }
    }
    document.alloc_tool_guide.count.value = sum;
    if(arguments!=null&&arguments!="") {
      for(var m=0;m<arguments.size();m++) {
        var loginId = arguments.values()[m].loginId;
        var name = arguments.values()[m].name;
        var type = arguments.values()[m].type;
        var domain = arguments.values()[m].domain;
        add(name,loginId,type,domain,true);
        var strTitle = "LoginId:"+loginId+",\nName:"+name
        + ",\nType:"+type+",\nDomain:"+domain;
        setNameTitle(strTitle);
        sum ++;
      }
    }
//    for(var j = 0 ;j < laborArray.length; j ++){
//      var existed = false;
//      for(var k = 0; k < oldDataArray.length;k ++){
//        if(laborArray[j] == oldDataArray[k]){
//          existed = true;
//          break;
//        }
//      }
//      if(!existed && laborArray[j]!=""){
//        var subLaborArray = laborArray[j].split("*");
//        if(subLaborArray.length == 4) {
//          window.top.alloc_tool_result.result.frm21.add(subLaborArray[0],subLaborArray[1],subLaborArray[2],subLaborArray[3],false);
//          var strTitle = "LoginId:"+subLaborArray[0]+",\nName:"+subLaborArray[1]
//          + ",\nType:"+subLaborArray[2]+",\nDomain:"+subLaborArray[3];
//          window.top.alloc_tool_result.result.frm21.setNameTitle(strTitle);
////          sum ++;
//        }
//      }
//    }
    alloc_tool_guide.iReset.value=sum;
    showLog(sum);
  }catch(e){
    //alert("ifmGuide body load " + e);
  }
}
</script>

<form name="alloc_tool_guide" method="post" action="">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" id="hidtable">
    <tr>
      <td>
        <input type="hidden" name="count" value="0" size="3"> <!-- 前check的个数 -->
        <input type="hidden" name="allitem" value="0" size="3"> <!-- 当前共有多少 -->
        <input type="hidden" name="iReset" value="0" size="3"><!-- 进入本页面的时候有多少 -->
        <input type="hidden" name="howmanyitem" value="0" size="3"> <!-- 当前有多少显示的check -->
        <input type="hidden" name="chRes" value="0" size="3"> <!-- 防止控件只有一个，无实际用途 -->
      </td>
    </tr>
  </table>
  <table width="273" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td bgcolor="#cccccc">
	  <table width="100%" border="0" cellspacing="1" cellpadding="1" style="table-layout:fixed">
            <tbody id="tbodyguide">

            </tbody>
        </table>
      </td>
    </tr>
  </table>
</form>
</body>
</html>
