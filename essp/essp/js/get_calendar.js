/*name:\u842c\u5e74\u6b77\u63a7\u4ef6
author:wenhui.ke@enogroup.com
update-date:2003/09/28
update-reason:\u7531\u4e2d\u6587\u6539\u6210\u82f1\u6587\uff0c\u5e76\u6839\u636e\u9700\u8981\u6539\u53d8\u989c\u8272\u98ce\u683c
note:IE6.0\u4e0b\u6e2c\u8a66\u901a\u904e
*/

//\u5275\u5efa\u842c\u5e74\u6b77
popCale = createPopup()
CaleBody = popCale.document.body
CaleBody.style.border="outset 1pt #cccccc"
CaleBody.style.fontSize = "9pt"
CaleBody.style.backgroundColor= "#FFFFFF"
CaleBody.style.cursor="hand"
//\u9f20\u6807\u7684\u9009\u62e9\u6846\u989c\u8272
function caleTDMove(e){
	e.style.borderColor="#000000 #CCCCFF #CCCCFF #CCCCFF"
	}

function caleTDOut(e){
	e.style.border="solid 1pt #ffffff"
	}

function hideCale(){
	popCale.hide()
	}

function yearChange(){  //\u91cd\u5beb\u5e74\u4efd\u7684<SELECT>
	var e=popCale.document.all('yearSel')
	e.options.length=0
	for (i=nYear-4;i<parseInt(nYear)+5;i++){
		e.options.length++
		e.options[e.options.length - 1].value=i
		e.options[e.options.length - 1].text=i
	}
	e.selectedIndex=4
}

function CaleClick(e){  //\u9078\u5b9a\u67d0\u4e00\u5929\u6642
	if(isNaN(parseInt(e.innerText))){	//\u5982\u679c\u662f\u9ede\u64ca"\u4eca\u5929"\u800c\u89f8\u767c
		var d=new Date()
		var vNowMonth = d.getMonth()+1;
		if (vNowMonth.toString().length<2){
			vNowMonth = ""+0+""+vNowMonth;
		}
		var vNowDay = d.getDate();
		if (vNowDay.toString().length<2){
			vNowDay = ""+0+""+vNowDay;
		}
		tarObject.value=d.getFullYear()+''+(vNowMonth)+''+ vNowDay
		e.style.borderColor="#94aad6"
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
		
		try {
			if(tarObject.fieldtype==null || tarObject.fieldtype=="") {
				tarObject.value=nYear+'-'+(vNowMonth)+'-'+vNowDay;
			} else if(tarObject.fieldtype=="dateyyyymmdd") {
				tarObject.value=nYear+''+(vNowMonth)+''+vNowDay;
			} else if(tarObject.fieldtype=="dateyyyymm") {
				tarObject.value=nYear+''+(vNowMonth);
			} else if(tarObject.fieldtype=="dateyymm") {
				tarObject.value=nYear.substring(2,4)+''+(vNowMonth);
			} else if(tarObject.fieldtype=="datemmdd") {
				tarObject.value=(vNowMonth)+''+vNowDay;
			} else if(tarObject.fieldtype=="dateyymmdd") {
				tarObject.value=nYear.substring(2,4)+''+(vNowMonth)+''+vNowDay;
			} 
		} catch(e) {
			tarObject.value=nYear+''+(vNowMonth)+''+vNowDay
		}
		
		
		e.style.borderColor="#FFFFFF"
	}
	popCale.hide();
    try {
        tarObject.onchange();
	} catch(e) {
	}
}

function changeYM(e,n){		//\u6539\u8b8a\u5e74\u4efd\u6216\u6708\u4efd\ufe50\u6216\u9ede>>\u548c<<\u6642\u89f8\u767c
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

function rewriteCale(){		//\u91cd\u5beb\u65e5\u6b77
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
		if(fDay==ftoday){
			newTD.style.color="white"
			newTD.style.backgroundColor="#0a246a"
		}
        newTD.height="20"
		newTD.style.border="solid 1pt #FFFFFF"
		newTD.align='center'
		newTD.onmouseover=Function("caleTDMove(this)")
		newTD.onmouseout=Function("caleTDOut(this)")
		newTD.onclick=Function("CaleClick(this)")

		fDay++
	}

}

function getDATE(s){	//\u8a2d\u5b9a\u90a3\u500b\u63a7\u4ef6\u52a0\u5165\u842c\u5e74\u6b77
    try {
		if(s.fieldtype!=null && s.fieldtype!="" && s.fieldtype!="dateyyyymmdd" && s.fieldtype!="dateyymmdd" && s.fieldtype!="dateyymm" && s.fieldtype!="dateyyyymm" && s.fieldtype!="datemmdd") {
			return;
		}
		
		if(s.readOnly) {
			return;
		}
    } catch(e) {
		
    }
    
	try {
		if(s.fieldtype!=null && s.fieldtype!="") {
			if(doCheckDateFormat(s)==false) {
				return;
			}
		}
	} catch(e) {
	}
    
	tarObject=s;

	var d=new Date();
	nYear=d.getFullYear();
	nMonth=d.getMonth();

	var e=event.srcElement;
	popCale.show(-70,e.clientHeight+5,164,197,e);
	popCale.document.all('monthSel').selectedIndex=nMonth;
	yearChange();
	rewriteCale();

}

var d=new Date()
var nYear=d.getFullYear()
var nMonth=d.getMonth()
var arMonth=new Array('01','02','03','04','05','06','07','08','09','10','11','12')
var qtyDay=(new Date(nYear,parseInt(nMonth)+1,1) - new Date(nYear,nMonth,1))/24/3600/1000
var strCale='<table bgcolor="#FFFFFF" id="whitecell" author="whitecell" border="1"'
    strCale+=' bordercolorlight="#0053a6" bordercolordark="#ffffff" cellpadding="1"'
    strCale+=' cellspacing="0" style="font-size:9pt;height:170;cursor:hand;">'
    strCale+='<tr height="25"><td bgcolor="#94aad6" style="color:#00ffff;border:solid 1pt #94aad6"'
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
    strCale+='<td width=19 height=25 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;"><FONT COLOR="RED">SUN</FONT>'
    strCale+='<td width=19 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;">MON'
    strCale+='<td width=19 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;">TUE'
    strCale+='<td width=19 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;">WED'
    strCale+='<td width=19 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;">THU'
    strCale+='<td width=19 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;">FRI'
    strCale+='<td width=19 style="font-size :9px;font-style : normal;font-family: Arial, Helvetica, sans-serif;"><FONT COLOR="RED">SAT</FONT></td></tr>'
var fDay=1-(new Date(nYear,nMonth,1).getDay())
var ftoday=d.getDate()
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

    strCale+='<tr height="25" align=center bgcolor="#94aad6">'
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
