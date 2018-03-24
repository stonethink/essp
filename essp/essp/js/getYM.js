/*name:獲取年月控件
author:liuzxit
design-date:2002/12/19
note:在IE5.5下測試通過
*/

var tarObject
var d=new Date()
sYear=d.getFullYear()
sMonth=d.getMonth() + 1

popup = window.createPopup()
popBody = popup.document.body
popBody.style.border="outset 1pt #cccccc"
popBody.style.fontSize = "9pt"
popBody.style.backgroundColor= "#FFFFFF"
popBody.style.cursor="hand"

var strPop='<table id="yMonth" author="liuzxit" border="1" bordercolorlight="#0053a6" bordercolordark="#FFFFFF" cellpadding="1" cellspacing="0" style="font-size:9pt;">'
strPop+='<th width="28" bgcolor="#94aad6" onclick="parent.yearRoll(-4)" style="color:#00ffff;"><<'
strPop+='<th width="28" bgcolor="#94aad6" style="color:#ffffff" onclick="parent.selectClicked(this)">'+(sYear - 2)
strPop+='<th width="28" bgcolor="#94aad6" style="color:#ffffff" onclick="parent.selectClicked(this)">'+(sYear - 1)
strPop+='<th width="28" bgcolor="#ffffff" style="color:#ff0000" onclick="parent.selectClicked(this)">'+sYear
strPop+='<th width="28" bgcolor="#94aad6" style="color:#ffffff" onclick="parent.selectClicked(this)">'+(sYear + 1)
strPop+='<th width="28" bgcolor="#94aad6" onclick="parent.yearRoll(4)" style="color:#00ffff;">>></th>'
strPop+='<tr align="center"><td style="border:solid 2pt #c6c6c6;" onmouseover="parent.tdMove(this)" onmouseout="parent.tdOut(this)" onclick="parent.selectClicked(this)">01'
strPop+='<td style="border:solid 2pt #c6c6c6;" onmouseover="parent.tdMove(this)" onmouseout="parent.tdOut(this)" onclick="parent.selectClicked(this)">02'
strPop+='<td style="border:solid 2pt #c6c6c6;" onmouseover="parent.tdMove(this)" onmouseout="parent.tdOut(this)" onclick="parent.selectClicked(this)">03'
strPop+='<td style="border:solid 2pt #c6c6c6;" onmouseover="parent.tdMove(this)" onmouseout="parent.tdOut(this)" onclick="parent.selectClicked(this)">04'
strPop+='<td style="border:solid 2pt #c6c6c6;" onmouseover="parent.tdMove(this)" onmouseout="parent.tdOut(this)" onclick="parent.selectClicked(this)">05'
strPop+='<td style="border:solid 2pt #c6c6c6;" onmouseover="parent.tdMove(this)" onmouseout="parent.tdOut(this)" onclick="parent.selectClicked(this)">06</td></tr>'
strPop+='<tr align="center"><td style="border:solid 2pt #c6c6c6;" onmouseover="parent.tdMove(this)" onmouseout="parent.tdOut(this)" onclick="parent.selectClicked(this)">07'
strPop+='<td style="border:solid 2pt #c6c6c6;" onmouseover="parent.tdMove(this)" onmouseout="parent.tdOut(this)" onclick="parent.selectClicked(this)">08'
strPop+='<td style="border:solid 2pt #c6c6c6;" onmouseover="parent.tdMove(this)" onmouseout="parent.tdOut(this)" onclick="parent.selectClicked(this)">09'
strPop+='<td style="border:solid 2pt #c6c6c6;" onmouseover="parent.tdMove(this)" onmouseout="parent.tdOut(this)" onclick="parent.selectClicked(this)">10'
strPop+='<td style="border:solid 2pt #c6c6c6;" onmouseover="parent.tdMove(this)" onmouseout="parent.tdOut(this)" onclick="parent.selectClicked(this)">11'
strPop+='<td style="border:solid 2pt #c6c6c6;" onmouseover="parent.tdMove(this)" onmouseout="parent.tdOut(this)" onclick="parent.selectClicked(this)">12</td></tr>'
strPop+='<tr align="center" bgcolor="#94aad6" style="color:#ffffff"><td colspan=3 onclick="parent.selectClicked(this)">本年月'
strPop+='<td colspan=3 onclick="parent.hidePop();">关闭</td></tr>'
strPop+='</table>'
popBody.innerHTML=strPop

function tdMove(e){e.style.border="outset 2pt #ffffff"}
function tdOut(e){e.style.border="solid 2pt #c6c6c6"}
function yearRoll(n){
	var e=popup.document.all('yMonth')
	e.cells[1].innerText = parseInt(e.cells[1].innerText) + parseInt(n)
	e.cells[2].innerText = parseInt(e.cells[2].innerText) + parseInt(n)
	e.cells[3].innerText = parseInt(e.cells[3].innerText) + parseInt(n)
	e.cells[4].innerText = parseInt(e.cells[4].innerText) + parseInt(n)
	var reg=/(\d{4})(\-)(\d{1,2})/
	var r=reg.exec(tarObject.value)
	tarObject.value=(parseInt(r[1])+n)+'-'+r[3]
}

function selectClicked(e){
	var p=e.parentElement
	switch(p.rowIndex){
		case 0:for(var i=1;i<5;i++){
			p.cells[i].style.backgroundColor='#94aad6';
			p.cells[i].style.color='#ffffff';
			}
			e.style.backgroundColor='#ffffff';
			e.style.color='#ff0000';
			tarObject.value=tarObject.value.replace(/(\d{4})(\-)(\d{1,2})/,e.innerText+'-$3');
			break;
		case 3:tarObject.value=sYear+'-'+sMonth;
			popup.hide();
			break;
		default:
			tarObject.value=tarObject.value.replace(/(\d{4})(\-)(\d{1,2})/,'$1-'+e.innerText);
			e.style.border="solid 1pt #c6c6c6"
			popup.hide();
			break;
	}
}

function hidePop(){popup.hide()}
function getYM(s){
	tarObject=s
	if (s.value=='')s.value=sYear+'-'+sMonth
	
	var reg=/(\d{4})(\-)(\d{1,2})/
	var r=reg.exec(s.value)
	if (r==null){s.value=sYear+'-'+sMonth;
		r=reg.exec(s.value)}
	var e=popup.document.all('yMonth').rows[0]
	for (var i=1;i<5;i++){
		if (e.cells[i].style.color=='#ff0000'){
			s.value=e.cells[i].innerText+'-'+r[3]
			yearRoll(r[1] - e.cells[i].innerText);
			break;
		}
	}
	var e=event.srcElement
	popup.show(-70,e.clientHeight+5,196,84,e)
}
