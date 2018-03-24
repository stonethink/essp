/*name:\u842C年\u6B77控件
author:wenhui.ke@enogroup.com
update-date:2003/09/28
update-reason:由中文改成英文，并根据需要改变颜色风格
note:IE6.0下\u6E2C\u8A66通\u904E
*/

//\u5275建\u842C年\u6B77
popCale = createPopup()
CaleBody = popCale.document.body
CaleBody.style.border="outset 1pt #cccccc"
CaleBody.style.fontSize = "9pt"
CaleBody.style.backgroundColor= "#FFFFFF"
CaleBody.style.cursor="hand"

//jie.chen需要的全局变量，如有疑问请mailto: jie.chen@enogroup.com  节假日不予接待
var jackie_sign = false;
var jackie_prm_id;
var boxName;  //wg add
var jackie_prm_tdid;
var prm_seq_all;

//鼠标的选择框颜色
function caleTDMove(e){
	e.style.borderColor="#000000 #CCCCFF #CCCCFF #CCCCFF"
	}

function caleTDOut(e){
	e.style.border="solid 1pt #ffffff"
	}

function hideCale(){
	popCale.hide()
	}

function yearChange(){  //重\u5BEB年份的<SELECT>
	var e=popCale.document.all('yearSel')
	e.options.length=0
	for (i=nYear-50;i<parseInt(nYear)+11;i++){
		e.options.length++
		e.options[e.options.length - 1].value=i
		e.options[e.options.length - 1].text=i
	}
	e.selectedIndex=50
}

function CaleClick(e){  //\u9078定某一天\u6642
	if(isNaN(parseInt(e.innerText))){	//如果是\u9EDE\u64CA"今天"而\u89F8\u767C
		var d=new Date()
		var vNowMonth = d.getMonth()+1;
		if (vNowMonth.toString().length<2){
			vNowMonth = ""+0+""+vNowMonth;
		}
		var vNowDay = d.getDate();
		if (vNowDay.toString().length<2){
			vNowDay = ""+0+""+vNowDay;
		}
		tarObject.value=d.getFullYear()+'-'+(vNowMonth)+'-'+ vNowDay
		//alert("dd");
		document.all.if_update.value = "update";
		document.all.seq_update.value = document.all.seq_update.value+"-"+prm_seq_all;
		document.all.trid_update.value = document.all.trid_update.value+"-"+jackie_prm_id;
		
		e.style.borderColor="#94aad6"
		if(jackie_sign) {
		    tarObject.select();
            //document.general.calendarFlag.value = "false";  //wg
		    tarObject.onBlur = showText("style",jackie_prm_id,jackie_prm_tdid);
		}
	}
	else{
		var vNowMonth = parseInt(nMonth)+1;
		if (vNowMonth.toString().length<2){
			vNowMonth = ""+0+""+vNowMonth;
		}
		var vNowDay = parseInt(e.innerText);
		if (vNowDay.toString().length<2){
			vNowDay = ""+0+""+vNowDay;
		}
		document.all.if_update.value = "update";
		document.all.seq_update.value = document.all.seq_update.value+"-"+prm_seq_all;
		document.all.trid_update.value = document.all.trid_update.value+"-"+jackie_prm_id;
		tarObject.value=nYear+'-'+(vNowMonth)+'-'+vNowDay
		e.style.borderColor="#FFFFFF"
		if(jackie_sign) {
		    tarObject.select();
            //document.general.calendarFlag.value = "false";  //wg
		    tarObject.onBlur = showText("style",jackie_prm_id,jackie_prm_tdid);
		}
	}
	document.all.now_have_date_box.value = "false";
	//alert("cc");
	popCale.hide()
}

function changeYM(e,n){		//改\u8B8A年份或月份\uFE50或\u9EDE>>和<<\u6642\u89F8\u767C
	if (e.tagName=='SELECT'){
		if(e.value.length==4){
			nYear=e.value;
			yearChange()
		}
		else{
			nMonth=e.value
		}
	}
	else{nMonth=parseInt(nMonth)+n
		switch(nMonth){
		case 12:nYear++;nMonth=0;
			yearChange()
			popCale.document.all('monthSel').selectedIndex=0;
			break;
		case -1:nYear--;nMonth=11;
			yearChange()
			popCale.document.all('monthSel').selectedIndex=11;
			break;
		default:popCale.document.all('monthSel').selectedIndex=nMonth;
			break;
		}
	}
	rewriteCale()
}

function rewriteCale(){		//重\u5BEB日\u6B77
	var newTb,newTR,newTD
	newTb=popCale.document.all('whitecell').tBodies[0]
	for (i=0;i<=newTb.rows.length;i++){
		newTb.deleteRow(2)
	}
	qtyDay=(new Date(nYear,parseInt(nMonth)+1,1) - new Date(nYear,nMonth,1))/24/3600/1000
	fDay=1-(new Date(nYear,nMonth,1).getDay())
	for (i=0;i<42;i++){
		if (i % 7==0){
			newTR=newTb.insertRow(newTb.rows.length - 2)
		}
		newTD=newTR.insertCell()
		if (fDay>0 && fDay<=qtyDay){
			newTD.innerText=fDay
		}
		if (i % 7==0){
			newTD.style.color="red"
		}else if (i % 7==6){
			newTD.style.color="red"
		}else{
		}

		newTD.style.border="solid 1pt #FFFFFF"
		newTD.align='center'
		newTD.onmouseover=Function("caleTDMove(this)")
		newTD.onmouseout=Function("caleTDOut(this)")
		newTD.onclick=Function("CaleClick(this)")

		fDay++
	}
}

function getDATE(s,prm_id,prm_tdid,prm_sql){	//\u8A2D定那\u500B控件加入\u842C年\u6B77
    //alert("c");
	//document.general.calendarFlag.value = "true";
	//boxName = s.name;   //wg add
        tarObject=s;
    prm_seq_all = prm_sql;
	var d=new Date()
	nYear=d.getFullYear();
	nMonth=d.getMonth();

	var e=event.srcElement
	popCale.show(-70,e.clientHeight+5,164,170,e)
	popCale.document.all('monthSel').selectedIndex=nMonth
	yearChange()
	rewriteCale()

	if(prm_id!=null) {
        jackie_sign = true;
		jackie_prm_id = prm_id;
		jackie_prm_tdid = prm_tdid;
	}
}

var d=new Date()
var nYear=d.getFullYear()
var nMonth=d.getMonth()
var arMonth=new Array('01','02','03','04','05','06','07','08','09','10','11','12')
var qtyDay=(new Date(nYear,parseInt(nMonth)+1,1) - new Date(nYear,nMonth,1))/24/3600/1000
var strCale='<table bgcolor="#FFFFFF" id="whitecell" author="whitecell" border="1"'
    strCale+=' bordercolorlight="#0053a6" bordercolordark="#ffffff" cellpadding="1"'
    strCale+=' cellspacing="0" style="font-size:9pt;height:170;cursor:hand;">'
    strCale+='<tr height="20"><td bgcolor="#94aad6" style="color:#00ffff;border:solid 1pt #94aad6"'
    strCale+=' onclick="parent.changeYM(this,-1)"><<</td>'
    strCale+='<td colspan=5 bgcolor="#94aad6" align="center" style="border:solid 1pt #94aad6">'
    strCale+='<select id="yearSel" onchange="parent.changeYM(this)" style="font:9pt;"></select>'

    strCale+='<select id="monthSel" onchange="parent.changeYM(this)" style="font:9pt;">'
	for (i=0;i<12;i++){
		strCale+='<option value='+i+(i==nMonth+1?' selected':'')+'>'+arMonth[i]+'</option>'
	}
    strCale+='</select></td><td bgcolor="#94aad6" style="color:#00ffff;border:solid 1pt #94aad6"'
    strCale+=' onclick="parent.changeYM(this,1)">>></td></tr>'
    strCale+='<tr align=center >'
    strCale+='<td width=18 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;"><FONT COLOR="RED">SUN</FONT>'
    strCale+='<td width=18 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;">MON'
    strCale+='<td width=18 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;">TUE'
    strCale+='<td width=18 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;">WED'
    strCale+='<td width=18 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;">THU'
    strCale+='<td width=18 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;">FRI'
    strCale+='<td width=18 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;"><FONT COLOR="RED">SAT</FONT></td></tr>'
var fDay=1-(new Date(nYear,nMonth,1).getDay())
for (i=0;i<42;i++){

	if (i % 7==0){
		strCale+='<tr align=center>'
		strCale+='<td bgcolor="#ffFFFF" COLOR="RED" style="border:solid 1pt #FFFFFF" onmouseover="parent.caleTDMove(this)"'
		strCale+=' onmouseout="parent.caleTDOut(this)" onclick="parent.CaleClick(this)"><FONT COLOR="RED">'
		strCale+=(fDay>0&&fDay<=qtyDay?fDay:'')+'</FONT></td>'

	}else if (i % 7==6){
		strCale+='<td bgcolor="#ffFFFF" COLOR="RED" style="border:solid 1pt #FFFFFF" onmouseover="parent.caleTDMove(this)"'
		strCale+=' onmouseout="parent.caleTDOut(this)" onclick="parent.CaleClick(this)"><FONT COLOR="RED">'
		strCale+=(fDay>0&&fDay<=qtyDay?fDay:'')+'</FONT></td>'
		strCale+='</tr>'
	}else{
		strCale+='<td style="border:solid 1pt #FFFFFF" onmouseover="parent.caleTDMove(this)"'
		strCale+=' onmouseout="parent.caleTDOut(this)" onclick="parent.CaleClick(this)">'
		strCale+=(fDay>0&&fDay<=qtyDay?fDay:'')+'</td>'

	}

	fDay++
}

    strCale+='<tr align=center bgcolor="#94aad6">'
    strCale+='<td style="border:1pt solid #94aad6">&nbsp;'
    strCale+='<td colspan=2 style="color: #FFFFFF; font-family: Arial, Helvetica, sans-serif;font-size: 12px;font-style: normal;font-weight: bold;border:1pt solid #94aad6" onmouseover="parent.caleTDMove(this)"'
    strCale+=' onmouseout="this.style.borderColor=\'#94aad6\'"'
    strCale+=' onclick="parent.CaleClick(this)">Today'
    strCale+='<td style="border:1pt solid #94aad6">&nbsp;'
    strCale+='<td colspan=2 style="color: #FFFFFF;  font-family: Arial, Helvetica, sans-serif;font-size: 12px;font-style: normal;font-weight: bold;border:1pt solid #94aad6"'
    strCale+=' onclick="this.style.borderColor=\'#94aad6\';parent.hideCale();"'
    strCale+=' onmouseover="parent.caleTDMove(this)"'
    strCale+=' onmouseout="this.style.borderColor=\'#94aad6\'">Close'
    strCale+='<td style="border:1pt solid #94aad6">&nbsp;</td><tr>'
    strCale+='</table>'
    CaleBody.innerHTML=strCale
    yearChange()