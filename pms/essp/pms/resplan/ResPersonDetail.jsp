<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/inc/pagedef.jsp" %>
<%int count = 0;%>
<html>
<head>
	<tiles:insert page="/layout/head.jsp">
		        <tiles:put name="title" value="Resource Plan"/>
        <tiles:put name="jspName" value="Detail"/>
</tiles:insert>
</head>
<%--
<script type="text/javascript" language="JavaScript" src="/essp/js/hr_calendar.js">
</script> --%>
<body leftmargin="0" topmargin="0" onkeydown="if(event.keyCode==13) return false;">
<logic:present name="webVo">
<html:form action="/pms/resplan/PersonnelPlanManage" id="frm_experience">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
      <tbody id="tb_exp">
          <%-- 遍历所有时间区间 --%>

        <logic:iterate id="element" name="webVo" property="planDateItem" indexId="index">
          <%count++;%>
        <tr class="<%=index.intValue()%2==0?"oracletdtwo":"oracletdone"%>"
            id="tr<%=index.intValue()+1%>"
          onmousedown="clicktr(<%=index.intValue()+1%>,'<bean:write name="element" property="rid"/>');">
          <input name="hid_tr<%=index.intValue()+1%>td0" type="hidden" value="<bean:write name="element" property="rid" />" width="160">
          <td class="oracelltext" width="25%" id="tr<%=index.intValue()+1%>td1" onfocus="clicktd('date',<%=index.intValue()+1%>,1,170,'begindate',<bean:write name="element" property="rid"/>)">
            <bean:write name="element" property="beginDateStr" />
            <input name="hid_tr<%=index.intValue()+1%>td1" type="hidden" value="<bean:write name="element" property="beginDateStr" />" width="160">
          </td>
          <td width="24%"class="oracelltext" id="tr<%=index.intValue()+1%>td2" onfocus="clicktd( 'date',<%=index.intValue()+1%>,2,170,'enddate',<bean:write name="element" property="rid"/>)">
            <bean:write name="element" property="endDateStr" />
            <input name="hid_tr<%=index.intValue()+1%>td2" type="hidden" value="<bean:write name="element" property="endDateStr" />">
          </td>
          <td width="24%"class="oracelltext"align="right" id="tr<%=index.intValue()+1%>td3" onclick="clicktd('input',<%=index.intValue()+1%>,3,80,'percent',<bean:write name="element" property="rid"/>)" >
            <input name="hid_tr<%=index.intValue()+1%>td3" type="hidden" value="<bean:write name="element" property="percent" />">
            <bean:write name="element" property="percent" />
          </td>
          <td class="oracelltext" align="right"id="tr<%=index.intValue()+1%>td4" onclick="clicktd('input',<%=index.intValue()+1%>,4,80,'hour',<bean:write name="element" property="rid"/>)" >
            <input name="hid_tr<%=index.intValue()+1%>td4" type="hidden" value="<bean:write name="element" property="hour" />">
            <bean:write name="element" property="hour" />
          </td>
          <td width="7%"class="oracelltext"> <input id="tr<%=index.intValue()+1%>but" name="tr<%=index.intValue()+1%>but" type="button" class="button" value="*" onClick="compute(<%=index.intValue()+1%>,<bean:write name="element" property="rid"/>)"></td>
        </tr>
      </logic:iterate>
      </tbody>
    </table>
  <input type="hidden" name="loginId" value="<bean:write name="webVo" property="loginId"/>">
  <input type="hidden" name="acntRid" value="<bean:write name="webVo" property="accountId"/>">
  <input type="hidden" name="resRid" value="<bean:write name="webVo" property="rid"/>">
  <input type="hidden" name="now_have_date_box">

  <input type="hidden" name="ifAdd" value=""> <%-- 记录是否有新增操作 --%>
  <input type="hidden" name="tridAdd" value=""><%-- 记录新增操作在页面的行号 --%>


  <input type="hidden" name="tridDelete" value=""><%-- 记录删除的Rid --%>
  <input type="hidden" name="ifDelete" value=""><%-- 记录是否有删除操作 --%>

  <input type="hidden" name="ifUpdate" value=""><%-- 记录是否有更新操作 --%>
  <!--input type="hidden" name="rid_update" value="" --> <%-- 记录更新的Rid --%>
  <input type="hidden" name="tridUpdate" value="">  <%-- 记录页面更新的行的序号 --%>

  <!--input type="hidden" name="seq_add" value="seqa"-->
  <input type="hidden" name="now_trid">
    <!--
  <input type="hidden" name="now_seq">
  <input type="hidden" name="emp_id" value="">
  <input type="hidden" name="project_id" value="">
    -->
</html:form >
<script language="JavaScript">
var iMaxseq = 100;
var firstclicktr = true;
var lastrid;
var lastdid;
var firstclicktd = true;
var now_have_box = false;
var nowchange = false;
var currentRid = "";//当前选择的日期区间的Rid
var currentResRid = "<bean:write name="webVo" property="rid" />";//当前选择的资源的Rid
//������������һ��button
function foucs_last(prm_trid){
	var prm_last = eval("document.frm_experience."+"tr"+prm_trid+"but");
	prm_last.focus();
}
//选择一行记录
function clicktr(prm_trid,rid) {
    document.frm_experience.now_trid.value = prm_trid;
    //document.frm_experience.now_seq.value = prm_seq;
    currentRid = rid;
    var prm_tr = eval("tr"+prm_trid);
	if(firstclicktr) {
	    prm_tr.className = "line3";
	    firstclicktr = false;
		lastrid  = prm_trid;
	} else if(lastrid%2==0) {
	    var prm_lastr = eval("tr"+lastrid);
		prm_lastr.className = "oracletdtwo";
		prm_tr.className = "line3";
		lastrid = prm_trid;
	} else {
	    var prm_lastr = eval("tr"+lastrid);
		prm_lastr.className = "oracletdone";
		prm_tr.className = "line3";
		lastrid = prm_trid;
	}
}
//选择一行记录中的某一栏
function clicktd(prm_style,prm_trid,prm_tdid,prm_lenth,prm_col_id,prm_rid) {
  if(document.frm_experience.now_have_date_box.value == "true") {
    showText("style",lastrid,lastdid);
    document.frm_experience.now_have_date_box.value = "false";
  }
  if(lastrid == prm_trid) {
    check_if_change();
    prm_td = eval("tr"+prm_trid+"td"+prm_tdid);
    prm_hid_box = eval("document.frm_experience.hid_"+"tr"+prm_trid+"td"+prm_tdid);
    if(firstclicktd) {
      //alert(prm_hid_box.value);
      turntobox(prm_style,prm_trid,prm_tdid,prm_lenth,prm_col_id,prm_rid);
      lastdid = prm_tdid;
      firstclicktd = false;
      now_have_box = true;
    } else {//如果不是第一次点击td
          if(prm_style=="input"&&lastdid==prm_tdid) {//两次点击了同一个td，需要判断当前是否showText
            if((!now_have_box)&&(nowchange)) {
              turntobox(prm_style,prm_trid,prm_tdid,prm_lenth,prm_col_id,prm_rid);
              now_have_box = true;
              nowchange = true;
            }
          } else {//两次点击了不同的td，需要先showText，然后turn to box
             turntobox(prm_style,prm_trid,prm_tdid,prm_lenth,prm_col_id,prm_rid);
             now_have_box = true;
             nowchange = true;
         }
         lastdid = prm_tdid;
       }
     }
//	var focu_last = eval("document.frm_experience."+"tr"+prm_trid+"but");
//	focu_last.focus();

}

var test_foucs = true;
//将所选栏位变为可改写状态
function turntobox(prm_style,prm_trid,prm_tdid,prm_lenth,prm_col_id,prm_rid) {
    prm_td = eval("tr"+prm_trid+"td"+prm_tdid);
	prm_hid_box = eval("document.frm_experience.hid_tr"+prm_trid+"td"+prm_tdid);
	if(prm_style == "input") {
          prm_td.innerHTML = "<input class='sliminput' name='tttt' value = '"+prm_hid_box.value+"' onBlur = 'showText(\""+prm_style+"\",\""+prm_trid+"\",\""+prm_tdid+"\",\""+prm_col_id+"\")' onFocus='this.select()' style='width:"+prm_lenth+"'  onchange='changeit("+prm_trid+","+prm_rid+")'>";
          document.frm_experience.tttt.focus();
          document.frm_experience.tttt.focus();
	    //document.frm_experience.lastbox_value.value = prm_hid_box.value;
	} else if(prm_style == "select") {
          prm_td.innerHTML = "<select class='sliminput' name='tttt' onBlur = 'showText(\""+prm_style+"\",\""+prm_trid+"\",\""+prm_tdid+"\",\""+prm_col_id+"\")' style='width:"+prm_lenth+"' onchange='changeit("+prm_trid+","+prm_rid+")'></select>";
          var oSelect = eval("document.frm_experience.tttt");
          var oS2 = eval("document.frm_experience.s"+prm_col_id)
          for(i=0;i<oS2.options.length;i++) {
            oSelect.options[i] = new Option(oS2.options[i].text,oS2.options[i].value);
          }
          oSelect.value = prm_hid_box.value;
          document.frm_experience.tttt.focus();
	} else {
          //begindate和enddate输入栏位未readonly
          document.frm_experience.now_have_date_box.value = "true";
          prm_td.innerHTML = "<input class='input_readonly' readonly='true' name='tttt' value = '"+prm_hid_box.value+"' onFocus='this.select()' onBlur = 'if(test_foucs == true){showText(\""+prm_style+"\",\""+prm_trid+"\",\""+prm_tdid+"\",\""+prm_col_id+"\")}else{test_foucs = true}' onDBLclick='test_foucs=false;getDATE(document.all(\"tttt\"),\""+prm_trid+"\",\""+prm_tdid+"\",\""+prm_rid+"\")' style='width:"+prm_lenth+"' onchange='changeit("+prm_trid+","+prm_rid+")'>";
          document.frm_experience.tttt.focus();
		//document.frm_experience.tttt.focus();
		//document.frm_experience.lastbox_value.value = prm_hid_box.value;
	}

}

function changeit(prm_id,prm_rid) {
    if(prm_rid <= 0 )
        return;
    document.frm_experience.ifUpdate.value = "update";
    var tridupdate = document.frm_experience.tridUpdate.value
    if(tridupdate == "")
       document.frm_experience.tridUpdate.value = prm_id;
    else if(tridupdate.indexOf(prm_id) == -1 )
       document.frm_experience.tridUpdate.value = document.frm_experience.tridUpdate.value+"-"+prm_id;
   //alert("update trid:" + document.frm_experience.tridUpdate.value);
}
//将栏位变为文本标签状态
function showText(prm_style,prm_trid,prm_tdid,prm_col_id){
  if(prm_style == "select") {
    var prm_td = eval("tr"+prm_trid+"td"+prm_tdid);
    var sValue;
    var oObj = eval("sArr"+prm_col_id);

    sValue = oObj[document.frm_experience.tttt.value]
    //alert(sValue);
    prm_td.innerHTML ="<a id='atr"+prm_trid+"td"+prm_tdid+"'>"+ sValue+"</a><input name='hid_tr"+prm_trid+"td"+prm_tdid+"' value='"+document.frm_experience.tttt.value+"' type='hidden'>";
    alert(" prm_td.innerHTML:"+ prm_td.innerHTML);
    now_have_box = false;
  } else {
    var prm_td = eval("tr"+prm_trid+"td"+prm_tdid);
    try{
      prm_td.innerHTML ="<a id='atr"+prm_trid+"td"+prm_tdid+"'>"+  document.frm_experience.tttt.value+"</a><input name='hid_tr"+prm_trid+"td"+prm_tdid+"' value='"+document.frm_experience.tttt.value+"' type='hidden'>";
    } catch(e){}
    now_have_box = false;
  }


}

  function check_if_change() {
      nowchange = !(nowchange);
	  //alert(nowchange);
  }
  num = <%=count%>;
//增加一行记录
function insert(parent,brother) {
    document.frm_experience.ifAdd.value = "add";
    num++;
    if(document.frm_experience.tridAdd.value == "")
        document.frm_experience.tridAdd.value = num;
    else
        document.frm_experience.tridAdd.value = document.frm_experience.tridAdd.value+"-"+num;
    //alert("add trid:" + document.frm_experience.tridAdd.value);
    var p = document.getElementById(parent);
    var b = document.getElementById(brother);
    var clas='oracletdone';
    if(num%2==0)clas='oracletdtwo';
    var tr = document.createElement("<tr id='tr"+num+"' class='"+clas+"' onmousedown='clicktr("+num+",0)'>");
    var tdSdate = document.createElement("<td height='20' width='25%' class='oracelltext' id='tr"+num+"td1' onfocus='clicktd(\"date\","+num+",1,170,\"begindate\", 0)'></td>");
    var tdFdate = document.createElement("<td height='20' width='24%' class='oracelltext' id='tr"+num+"td2' onfocus='clicktd(\"date\","+num+",2,170,\"enddate\", 0)'></td>");
    var tdPeriod = document.createElement("<td height='20' width='24%'align='right' class='oracelltext' id='tr"+num+"td3' onclick='clicktd(\"input\","+num+",3,80,\"percent\", 0)'></td>");
    var tdHour = document.createElement("<td height='20' width='24%'align='right' class='oracelltext' id='tr"+num+"td4' onclick='clicktd(\"input\","+num+",4,80,\"hour\", 0)'></td>");
    var tdcompt = document.createElement("<td width='7%'class='oracelltext'> </td>");
    var input=document.createElement("<input type='button' class='button' id='tr"+num+"but' value='*' onClick='compute("+num+",0)'>");
    var a=document.createElement("<a id='a"+num+"'></a>");
    p.insertBefore(tr,b);
    tr.appendChild(tdSdate);
    tr.appendChild(tdFdate);
    tr.appendChild(tdPeriod);
    tr.appendChild(tdHour);
    tr.appendChild(tdcompt);
    tdHour.appendChild(a);
    tdcompt.appendChild(input);
}
var firstadd = true;

function add() {
	if(firstadd) {
	    insert('tb_exp','tr1');
		firstadd = false;
	} else {
	    insert('tb_exp','tr'+num);
	}
	if_add_click = true;
	var prm_td1 = eval("tr"+num+"td1");
	var prm_td2 = eval("tr"+num+"td2");
        var prm_td3 = eval("tr"+num+"td3");
        var prm_td4 = eval("tr"+num+"td4");
	prm_td1.innerHTML = "<input name='hid_tr"+num+"td1' type='hidden'>";
	prm_td2.innerHTML = "<input name='hid_tr"+num+"td2' type='hidden'>";
        prm_td3.innerHTML = "<input name='hid_tr"+num+"td3' type='hidden'>";
        prm_td4.innerHTML = "<a id='atr"+num+"td4'></a>"+"<input name='hid_tr"+num+"td4' type='hidden'>";

}
//提交
  function submitit() {
    /*
    var str="";
    var isUpdate = document.frm_experience.ifUpdate.value
    var isDelete = document.frm_experience.ifDelete.value
    if(isUpdate == "update"){
      var tridUpdate = document.frm_experience.tridUpdate.value;
      str = str + "update trid:" + tridUpdate + "\n";
    }
    if(isDelete == "delete"){
      var tridDelete = document.frm_experience.tridDelete.value;
      str = str+ "delete rid:"  +tridDelete+ "\n";
    }
    var isAdd = document.frm_experience.ifAdd.value;
    if(isAdd == "add"){
      var addTr = document.frm_experience.tridAdd.value;
      str = str+ "add trrid:"  +addTr+ "\n";
    }
    alert(str);
    */
    document.frm_experience.submit();
  }
//删除一行记录
  function deletit() {
    var del_tr = document.frm_experience.now_trid.value;
    if(del_tr == null || del_tr == "") {
      alert("Sorry!Please select a reorde to delelet!");
      return false;
    }
    var prm_tr_del = eval("tr"+del_tr);
    document.frm_experience.ifDelete.value = "delete";
    prm_tr_del.style.display = "none";
    if(document.frm_experience.tridDelete.value == "")
       document.frm_experience.tridDelete.value = del_tr;
    else
       document.frm_experience.tridDelete.value = document.frm_experience.tridDelete.value + "-" + del_tr;
    //alert("delete trid:" + document.frm_experience.tridDelete.value);
  }
//根据起止时间和百分比计算小时数并显示
function compute(count,prm_rid){
  var obj1=eval("document.frm_experience.hid_tr"+count+"td1");
  var obj2=eval("document.frm_experience.hid_tr"+count+"td2");
  var obj3=eval("document.frm_experience.hid_tr"+count+"td3");
  var obj4=eval("document.frm_experience.hid_tr"+count+"td4");
  var datefrom=obj1.value;
  var dateto=obj2.value;
  var percent=obj3.value;
  var month1;
  var month2;
  var day1;
  var day2;
  var year1=parseInt(datefrom.substring(0,4));
  var year2=parseInt(dateto.substring(0,4));
  if(datefrom.substring(5,7)>10)
      month1=parseInt(datefrom.substring(5,7))-1;
  else
      month1=parseInt(datefrom.substring(6,7))-1;
  if(dateto.substring(5,7)>10)
     month2=parseInt(dateto.substring(5,7))-1;
  else
     month2=parseInt(dateto.substring(6,7))-1;
  if(datefrom.substring(8)>10)
     day1=parseInt(datefrom.substring(8));
  else
     day1=parseInt(datefrom.substring(9));
  if(dateto.substring(8)>10)
     day2=parseInt(dateto.substring(8));
  else day2=parseInt(dateto.substring(9));
  //alert(year1+"+"+month1+"+"+day1+"--"+year2+"+"+month2+"+"+day2);
  var date1=new Date(year1,month1,day1);
  var date2=new Date(year2,month2,day2);
  //alert("date2.getTime():"+date2.getTime()+"date1.getTime():"+date1.getTime()+"date2.getDate():"+date2.getDate()+"date1.getDate()"+date1.getDate());
  var min=parseFloat(date2.getTime()-date1.getTime());
  var day=min/86400000+1;
  var hour=day*8*percent/100;
  var obj=eval("tr"+count+"td4");
  if(obj==null){
    obj=eval("document.frm_experience.tr"+count+"td4");
    obj.value=hour;
  }
  else
    obj.innerHTML=hour;
    obj4.value=hour;
    changeit(count,prm_rid)
}
</script>
</logic:present>
</body>
</html>
