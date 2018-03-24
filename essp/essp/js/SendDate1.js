function show_calendar_detail(){

	var rets;
	show_calendar('dateform.alldate');
	//rets = dateform.alldate.value;
	show_default_date();
};
function show_default_date(){
	var vNowDay = gNow.getDate();
	var vNowMonth = gNow.getMonth();
	var vNowYear = gNow.getFullYear();
	if (vNowDay.toString().length<2)
	{
		vNowDay = ""+0+""+vNowDay;
		}else{
		vNowDay= vNowDay;
		}
	//dateform.alldate.value = RetString;
	/*dateform.bdate_ty.selectedindex = vNowYear - 2002;
	//alert("dateform.bdate_ty.selectedindex is " + dateform.bdate_ty.selectedindex);
	//var vNowYear1 = dateform.bdate_ty.selectedindex -0;
	dateform.bdate_ty.options[dateform.bdate_ty.selectedindex].selected = true;
	dateform.bdate_mm.selectedindex = vNowMonth - 0;
	dateform.bdate_mm.options[dateform.bdate_mm.selectedindex].selected = true;
	dateform.bdate_dd.selectedindex = vNowDay - 1;
	dateform.bdate_dd.options[dateform.bdate_dd.selectedindex].selected = true;
	//alert("rets is " + rets);
	dateform.edate_ty.selectedindex = vNowYear - 2002;
	dateform.edate_ty.options[dateform.edate_ty.selectedindex].selected = true;
	dateform.edate_mm.selectedindex = vNowMonth - 0;
	dateform.edate_mm.options[dateform.edate_mm.selectedindex].selected = true;
	dateform.edate_dd.selectedindex = vNowDay - 1;
	dateform.edate_dd.options[dateform.edate_dd.selectedindex].selected = true;*/
	}

function show_detail( RetString ){

	var rets;
	
	datesel.innerHTML = "  "+RetString.substr(0,4)+"-"+RetString.substr(4,2)+"-"+RetString.substr(6,2)+"  ";
	sel_days.todate.value = RetString;
	
	gNow = new Date();
	var vNowMonth1;
	var vNowDay = gNow.getDate();
	var vNowMonth = gNow.getMonth()+1;
	var vNowYear = gNow.getFullYear();
	if (vNowMonth.toString().length<2)
	{
		vNowMonth1 = ""+0+""+vNowMonth;
		}else{
		vNowMonth1= vNowMonth;
		}
	if (vNowDay.toString().length<2)
	{
		vNowDay = ""+0+""+vNowDay;
		}else{
		vNowDay= vNowDay;
		}
	var today = ""+vNowYear +""+ vNowMonth1 +""+ vNowDay;
	sel_days.today_day.value = today;
	var datetype = ChgSTypeD.datetype.value;
	if (RetString < today)
		newimg.innerHTML = "";
	else if (RetString == today)
		newimg.innerHTML = "<input type=button class=button name=Submit  onclick=window.open('/essp/servlet/com.enovation.essp.calendar.action.Temcln_a0?datetype="+datetype+"&clndate="+RetString+"','aa','width=560,height=360,top=250,left=250') value='New Task'>";
	else if (RetString > today)
		newimg.innerHTML = "<input type=button class=button name=Submit  onclick=window.open('/essp/servlet/com.enovation.essp.calendar.action.Temcln_a0?datetype="+datetype+"&clndate="+RetString+"','aa','width=560,height=360,top=250,left=250') value='New Task'>";

	
	//prtimg.innerHTML = "<img src=../../essp/image/print.gif border=0 style='cursor:hand' onclick=window.open('/essp/servlet/com.enovation.essp.calendar.action.Temcln_p?datetype="+datetype+"&clndate="+RetString+"','printer','width=760,height=540,menubar=yes,scrollbars=yes,toolbar,resizable,location=no')>";
	ChgSTypeD.clndate.value = RetString;
	//dateform.alldate.value = RetString;
	/*dateform.bdate_ty.selectedindex = RetString.substring(0,4) - 2002;
	dateform.bdate_ty.options[dateform.bdate_ty.selectedindex].selected = true;
	dateform.bdate_mm.selectedindex = RetString.substring(4,6) - 1;
	dateform.bdate_mm.options[dateform.bdate_mm.selectedindex].selected = true;
	dateform.bdate_dd.selectedindex = RetString.substring(6,8) - 1;
	dateform.bdate_dd.options[dateform.bdate_dd.selectedindex].selected = true;
	//alert("rets is " + rets);
	dateform.edate_ty.selectedindex = RetString.substring(0,4) - 2002;
	dateform.edate_ty.options[dateform.edate_ty.selectedindex].selected = true;
	dateform.edate_mm.selectedindex = RetString.substring(4,6) - 1;
	dateform.edate_mm.options[dateform.edate_mm.selectedindex].selected = true;
	dateform.edate_dd.selectedindex = RetString.substring(6,8) - 1;
	dateform.edate_dd.options[dateform.edate_dd.selectedindex].selected = true;*/
};

function show_detail_last( RetString ){
	var datetype = ChgSTypeD.datetype.value;
	
	var last_year = RetString.substring(0,4);
	var last_month = RetString.substring(4,6) ;
	last_month = (last_month.toString().length < 2) ? "0" + last_month : last_month;
	last_month = last_month - 0 + 1;
	if (last_month == 1) {
			last_month = 12;
			last_year = last_year - 1;
		}else{
		last_month = last_month - 0 - 1 ;
		
		}
		
	last_month = (last_month.toString().length < 2) ? "0" + last_month : last_month;
	//getdate_ty = last_year;
	//getdate_mm = last_month;
	var allday = ChgSTypeD.clndate.value;
	
	allday = allday.substring(6,8);
	var my_num_day = Calendar_get_daysofmonth( last_month-1, last_year );
	if (allday > my_num_day){
		allday = my_num_day;
		}
	RetString = last_year +""+ last_month +""+ allday;
	//alert("RetString is",allday);
	datesel.innerHTML = "  "+RetString.substr(0,4)+"-"+RetString.substr(4,2)+"-"+RetString.substr(6,2)+"  ";
	sel_days.todate.value = RetString;
	
	gNow = new Date();
	var vNowMonth1;
	var vNowDay = gNow.getDate();
	var vNowMonth = gNow.getMonth()+1;
	var vNowYear = gNow.getFullYear();
	if (vNowMonth.toString().length<2)
	{
		vNowMonth1 = ""+0+""+vNowMonth;
		}else{
		vNowMonth1= vNowMonth;
		}
	if (vNowDay.toString().length<2)
	{
		vNowDay = ""+0+""+vNowDay;
		}else{
		vNowDay= vNowDay;
		}
	var today = ""+vNowYear +""+ vNowMonth1 +""+ vNowDay;
	sel_days.today_day.value = today;
	if (RetString < today)
		newimg.innerHTML = "";
	else if (RetString == today)
		newimg.innerHTML = "<input type=button class=button name=Submit onclick=window.open('/essp/servlet/com.enovation.essp.calendar.action.Temcln_a0?datetype="+datetype+"&clndate="+RetString+"','aa','width=560,height=360,top=250,left=250')  value='New Task'>";
	else if (RetString > today)
		newimg.innerHTML = "<input type=button class=button name=Submit onclick=window.open('/essp/servlet/com.enovation.essp.calendar.action.Temcln_a0?datetype="+datetype+"&clndate="+RetString+"','aa','width=560,height=360,top=250,left=250')  value='New Task'>";

	
	//prtimg.innerHTML = "<img src=../../essp/image/print.gif border=0 style='cursor:hand' onclick=window.open('/essp/servlet/com.enovation.essp.calendar.action.Temcln_p?datetype="+datetype+"&clndate="+RetString+"','printer','width=760,height=540,menubar=yes,scrollbars=yes,toolbar,resizable,location=no')>";
	
	
	
	ChgSTypeD.clndate.value = last_year +""+ last_month +""+ allday;
	
}

function show_detail_next( RetString ){
	var datetype = ChgSTypeD.datetype.value;
	
	var next_year = RetString.substring(0,4);
	var next_month = RetString.substring(4,6);
	 next_month = (next_month.toString().length < 2) ? "0" + next_month : next_month;
	next_month = next_month - 0 + 1;
	if ( next_month == 12 ) {
			next_month = 1;
			next_year = next_year - 0 + 1;
		}else{
			next_month = next_month - 0 + 1;
			
		}
	next_month = (next_month.toString().length < 2) ? "0" + next_month : next_month;
	//getdate_ty = next_year;
	//getdate_mm = next_month;
	var allday = ChgSTypeD.clndate.value;
	allday = allday.substring(6,8);
	var my_num_day = Calendar_get_daysofmonth( next_month-1, next_year );
	if (allday > my_num_day){
		allday = my_num_day;
		}
	RetString = next_year +""+ next_month +""+ allday;
	datesel.innerHTML = "  "+RetString.substr(0,4)+"-"+RetString.substr(4,2)+"-"+RetString.substr(6,2)+"  ";
	sel_days.todate.value = RetString;
	
	gNow = new Date();
	var vNowMonth1;
	var vNowDay = gNow.getDate();
	var vNowMonth = gNow.getMonth()+1;
	var vNowYear = gNow.getFullYear();
	if (vNowMonth.toString().length<2)
	{
		vNowMonth1 = ""+0+""+vNowMonth;
		}else{
		vNowMonth1= vNowMonth;
		}
	if (vNowDay.toString().length<2)
	{
		vNowDay = ""+0+""+vNowDay;
		}else{
		vNowDay= vNowDay;
		}
	var today = ""+vNowYear +""+ vNowMonth1 +""+ vNowDay;
	sel_days.today_day.value = today;
	if (RetString < today)
		newimg.innerHTML = "";
	else if (RetString == today)
		newimg.innerHTML = "<input type=button class=button name=Submit onclick=window.open('/essp/servlet/com.enovation.essp.calendar.action.Temcln_a0?datetype="+datetype+"&clndate="+RetString+"','aa','width=560,height=360,top=250,left=250')  value='New Task'>";
	else if (RetString > today)
		newimg.innerHTML = "<input type=button class=button name=Submit onclick=window.open('/essp/servlet/com.enovation.essp.calendar.action.Temcln_a0?datetype="+datetype+"&clndate="+RetString+"','aa','width=560,height=360,top=250,left=250')  value='New Task'>";

	
	//prtimg.innerHTML = "<img src=../../essp/image/print.gif border=0 style='cursor:hand' onclick=window.open('/essp/servlet/com.enovation.essp.calendar.action.Temcln_p?datetype="+datetype+"&clndate="+RetString+"','printer','width=760,height=540,menubar=yes,scrollbars=yes,toolbar,resizable,location=no')>";
	
	
	ChgSTypeD.clndate.value = "" + next_year +""+ next_month +""+ allday;
	
}

function show_detail_today( RetString ){
	var datetype = ChgSTypeD.datetype.value;
	
	var rets;
	gNow = new Date();
	var vNowMonth1;
	var vNowDay = gNow.getDate();
	var vNowMonth = gNow.getMonth()+1;
	var vNowYear = gNow.getFullYear();
	if (vNowMonth.toString().length<2)
	{
		vNowMonth1 = ""+0+""+vNowMonth;
		}else{
		vNowMonth1= vNowMonth;
		}
	if (vNowDay.toString().length<2)
	{
		vNowDay = ""+0+""+vNowDay;
		}else{
		vNowDay= vNowDay;
		}
	RetString = vNowYear +""+ vNowMonth1 +""+ vNowDay;
	datesel.innerHTML = "  "+vNowYear+"-"+ vNowMonth1 +"-"+ vNowDay +"  ";
	newimg.innerHTML = "";
	newimg.innerHTML = "<input type=button class=button name=Submit onclick=window.open('/essp/servlet/com.enovation.essp.calendar.action.Temcln_a0?datetype="+datetype+"&clndate="+RetString+"','aa','width=560,height=360,top=250,left=250')  value='New Task'>";	
	ChgSTypeD.clndate.value = vNowYear +""+ vNowMonth1 +""+ vNowDay;
	//dateform.alldate.value = RetString;
	/*dateform.bdate_ty.selectedindex = RetString.substring(6,10) - 2002;
	dateform.bdate_ty.options[dateform.bdate_ty.selectedindex].selected = true;
	dateform.bdate_mm.selectedindex = RetString.substring(10,11) - 1;
	dateform.bdate_mm.options[dateform.bdate_mm.selectedindex].selected = true;
	dateform.bdate_dd.selectedindex = RetString.substring(11,13) - 1;
	dateform.bdate_dd.options[dateform.bdate_dd.selectedindex].selected = true;
	//alert("rets is " + rets);
	dateform.edate_ty.selectedindex = RetString.substring(6,10) - 2002;
	dateform.edate_ty.options[dateform.edate_ty.selectedindex].selected = true;
	dateform.edate_mm.selectedindex = RetString.substring(10,11) - 1;
	dateform.edate_mm.options[dateform.edate_mm.selectedindex].selected = true;
	dateform.edate_dd.selectedindex = RetString.substring(11,13) - 1;
	dateform.edate_dd.options[dateform.edate_dd.selectedindex].selected = true;*/
};
function show_detail_month( RetString ){
	
	
	/*dateform.bdate_ty.selectedindex = RetString.substring(0,4) - 2002 ;
	dateform.bdate_ty.options[dateform.bdate_ty.selectedindex].selected = true;
	dateform.bdate_mm.selectedindex = RetString.substring(4,6) - 1;
	dateform.bdate_mm.options[dateform.bdate_mm.selectedindex].selected = true;
	dateform.bdate_dd.selectedindex = 0;
	dateform.bdate_dd.options[dateform.bdate_dd.selectedindex].selected = true;
	
	dateform.edate_ty.selectedindex = RetString.substring(0,4) - 2002 ;
	dateform.edate_ty.options[dateform.edate_ty.selectedindex].selected = true;
	dateform.edate_mm.selectedindex = RetString.substring(4,6) - 1;
	dateform.edate_mm.options[dateform.edate_mm.selectedindex].selected = true;
	
	myyear = RetString.substring(0,4) - 0;
	var my_num_day = Calendar_get_daysofmonth( dateform.edate_mm.selectedindex, myyear );
	var my = my_num_day - 1;
	dateform.edate_dd.selectedindex = my_num_day - 1;
	dateform.edate_dd.options[my].selected = true;*/
}

function show_detail_week1( RetString ){
	var my_day = RetString.substring(6,8) - 0;
	var my_num_day_next = 7 - my_day;
	var my_year = RetString.substring(0,4) - 0;
	var my_month = RetString.substring(4,6) - 0;
	var my_year_last = my_year - 0;
	var my_month_last = my_month - 0;
	var my2 = 1;
//	alert("my_month is " + my_month);
	if ( my_day > 0 ){
  		if (my_month_last < 2) {
			my_month_last = 12;
			my_year_last = my_year - 1;
		}else{
		my_month_last = my_month_last -1;
		}
		my2 = Calendar_get_daysofmonth( my_month_last-1, my_year_last );
		my2 = my2 - my_day + 1;
  	} 
	//alert("my_year_last is " + my_year_last);
	
	/*dateform.bdate_ty.selectedindex = my_year_last - 2002;
	dateform.bdate_ty.options[dateform.bdate_ty.selectedindex].selected = true;
	dateform.bdate_mm.selectedindex = my_month_last - 1;
//	alert("dateform.bdate_mm.selectedindex is " + dateform.bdate_mm.selectedindex);
	dateform.bdate_mm.options[dateform.bdate_mm.selectedindex].selected = true;
	dateform.bdate_dd.selectedindex = my2 - 1;
	dateform.bdate_dd.options[dateform.bdate_dd.selectedindex].selected = true;*/
  //else {	
	var my = my_num_day_next - 0;
	/*dateform.edate_dd.selectedindex = my - 1;
//	alert("my is " + dateform.edate_dd.selectedindex);
	dateform.edate_dd.options[dateform.edate_dd.selectedindex].selected = true;
	dateform.edate_mm.selectedindex = my_month - 1;
	dateform.edate_mm.options[dateform.edate_mm.selectedindex].selected = true;
	dateform.edate_ty.selectedindex = my_year - 2002;
	dateform.edate_ty.options[dateform.edate_ty.selectedindex].selected = true;*/
}

function show_detail_week2( RetString ){
	/*dateform.bdate_ty.selectedindex = RetString.substring(0,4) - 2002;
	dateform.bdate_ty.options[dateform.bdate_ty.selectedindex].selected = true;
	dateform.bdate_mm.selectedindex = RetString.substring(4,6) - 1;
	dateform.bdate_mm.options[dateform.bdate_mm.selectedindex].selected = true;
	dateform.bdate_dd.selectedindex = RetString.substring(6,8) - 1;
	dateform.bdate_dd.options[dateform.bdate_dd.selectedindex].selected = true;*/

	var myyear =  RetString.substring(0,4) - 0;
	var mymonth =  RetString.substring(4,6) - 0 ;
	var myday =  RetString.substring(6,8) - 0;
	var my_num_day = Calendar_get_daysofmonth( mymonth-1, myyear );
	var my = myday  + 6;
	if ( my > my_num_day ){
  
		if (mymonth > 11) {
			mymonth = 1;
			myyear = myyear + 1;
		}else{
			mymonth = mymonth + 1;
		}
		my = my - my_num_day  ;
		//alert("my is " + my);
  	} 
  //else {	
	
	/*dateform.edate_dd.selectedindex = my - 1;
	//alert("my is " + dateform.edate_dd.selectedindex);
	dateform.edate_dd.options[dateform.edate_dd.selectedindex].selected = true;
	dateform.edate_mm.selectedindex = mymonth - 1;
	dateform.edate_mm.options[dateform.edate_mm.selectedindex].selected = true;
	dateform.edate_ty.selectedindex = myyear - 2002;
	dateform.edate_ty.options[dateform.edate_ty.selectedindex].selected = true;*/
}
function format_data(p_day) {
	var vData,vData1;
	var vMonth = 1 + this.gMonth;
	vMonth = (vMonth.toString().length < 2) ? "0" + vMonth : vMonth;
	var vMon = Calendar.get_month(this.gMonth).substr(0,3).toUpperCase();
	var vFMon = Calendar.get_month(this.gMonth).toUpperCase();
	var vY4 = new String(this.gYear);
	var vY2 = new String(this.gYear.substr(2,2));
	var vDD = (p_day.toString().length < 2) ? "0" + p_day : p_day;

	switch (this.gFormat) {
		case "MM\/DD\/YYYY" :
			vData = vMonth + "\/" + vDD + "\/" + vY4;
			break;
		case "MM\/DD\/YY" :
			vData = vMonth + "\/" + vDD + "\/" + vY2;
			break;
		case "MM-DD-YYYY" :
			vData = vMonth + "-" + vDD + "-" + vY4;
			break;
		case "YYYY-MM-DD" :
			vData = vY4 + "-" + vMonth + "-" + vDD;
			break;
		case "MM-DD-YY" :
			vData = vMonth + "-" + vDD + "-" + vY2;
			break;
		case "DD\/MON\/YYYY" :
			vData = vDD + "\/" + vMon + "\/" + vY4;
			break;
		case "DD\/MON\/YY" :
			vData = vDD + "\/" + vMon + "\/" + vY2;
			break;
		case "DD-MON-YYYY" :
			vData = vDD + "-" + vMon + "-" + vY4;
			break;
		case "DD-MON-YY" :
			vData = vDD + "-" + vMon + "-" + vY2;
			break;
		case "DD\/MONTH\/YYYY" :
			vData = vDD + "\/" + vFMon + "\/" + vY4;
			break;
		case "DD\/MONTH\/YY" :
			vData = vDD + "\/" + vFMon + "\/" + vY2;
			break;
		case "DD-MONTH-YYYY" :
			vData = vDD + "-" + vFMon + "-" + vY4;
			break;
		case "DD-MONTH-YY" :
			vData = vDD + "-" + vFMon + "-" + vY2;
			break;
		case "DD\/MM\/YYYY" :
			vData = vDD + "\/" + vMonth + "\/" + vY4;
			break;
		case "DD\/MM\/YY" :
			vData = vDD + "\/" + vMonth + "\/" + vY2;
			break;
		case "DD-MM-YYYY" :
			vData = vDD + "-" + vMonth + "-" + vY4;
			break;
		case "DD-MM-YY" :
			vData = vDD + "-" + vMonth + "-" + vY2;
			break;
		default :
			vData = vMonth + "\/" + vDD + "\/" + vY4;
	}
	vData1 =vY4 + vMonth + vDD;
	return vData1;
}
