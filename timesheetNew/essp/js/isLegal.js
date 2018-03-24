function TrimLeft(str){
	if(str.charAt(0) == " "){
		str = str.slice(1);
		str = TrimLeft(str); 
	}
 	return str;
}


function TrimRight(str){
	//alert("str.charAt( str.length - 1 )=" + str.charAt( str.length - 1 )+".");
	if(str.charAt( str.length - 1 ) == " "){
		str = str.slice(0,str.length - 1);
		str = TrimRight(str); 
	}
 	return str;
}




function resizeWindow(){
	var aw = screen.availWidth; 
	                            
	var ah = screen.availHeight;
	                            
	window.moveTo(0, 0);        
	                            
	window.resizeTo(aw, ah);    
	
}

 //檢查輸入
 	function show(seq,rows,formId){
		for ( var i=0; i<rows; i++){ 
			var obj = seq+i;
			if(eval( obj + ".style.display=='block';" )){
				eval( obj + ".style.display='none';" );
				formId.ImgDetail.src="/tse/images/lplus.gif";
				
			} else {
				eval( obj + ".style.display='block';" );
				formId.ImgDetail.src="/tse/images/lminus.gif";
			}
		}
	}



function checkQuoter(obj){
	if(obj.value.indexOf("'")>=0){
		alert("此欄位不能含引號。");
		obj.focus();
		return false;
	}
	return true;	
}

function isBetween(val,lo,hi){   //檢查val是否在lo與hi之間
 if ((val < lo) || (val > hi)){ return(false);}
 else { return(true);}
}
 
function isDigit(theNum){    //檢查theNum是否為數字
 var theMask = "0123456789";
 if (isEmpty(theNum)) return(false);
 else if(theMask.indexOf(theNum) == -1) return(false);
 return(true);
}
 
 
 
function isEmpty(str){   //檢查str是否為空
 if((str == null)||(str.length == 0)) return (true);
 else return(false);
}
 

function isInt(str){     //檢查str是否為整數
	if(str==""){
		return (false);
	}
	else{
		for(i=0;i<str.length;i++){
		var chr = str.charAt(i);
			if(!(chr>='0' && chr<='9')){
				return (false);
			}
		}
	}	
	return (true);
}
 
function isDate(theStr){     //檢查theStr是否為時間格式yyyymmdd，以及其合法性
 if(theStr.length!=8){ 
 alert("非法日期，請重新輸入(yyyymmdd)！");
 return (false);}
 else {
  var y = theStr.substring(0,4);
  var m = theStr.substring(4,6);
  var d = theStr.substring(6,8);
  var maxDays = 31;
  if(isDateInt(m)==false||isDateInt(d)==false||isDateInt(y)==false){
   alert("日期中含有非數字與分界符的其它字符，請重新輸入(yyyymmdd)！");
   return(false);
  }
  else if (y.length < 4){ alert("年數應以四位數表示！");return(false);}
  else if (!isBetween(m,1,12)){ alert("月份應在1~12之間！");return(false);}
  if(m.length!=2){ alert("請在月份前加零！");return(false);}
  else if (m==4||m==6||m==9||m==11)maxDays = 30;
  else if (m==2){
   if(y%4>0)maxDays = 28;
   else if(y%100==0&&y%400>0)maxDays = 28;
   else maxDays = 29;
  }
  if(isBetween(d,1,maxDays)==false){ alert("本月只有"+maxDays+"天！請確認後重新輸入！");return(false);}
  if(d.length!=2){ alert("請在日期前加零！"); return(false);}
  return(true);
 
 }
}


function isDate1(theStr){     //檢查theStr是否為時間格式yyyy/mm/dd，以及其合法性
 if(theStr.length!=8){ 
 alert("非法日期，請重新輸入(yyyy/mm/dd)！");
 return (false);}
 else {
  var y = theStr.substring(0,the1st);
  var m = theStr.substring(the1st+1,the2nd);
  var d = theStr.substring(the2nd+1,theStr.length);
  var maxDays = 31;
  if(isDateInt(m)==false||isDateInt(d)==false||isDateInt(y)==false){
   alert("日期中含有非數字與分界符的其它字符，請重新輸入(yyyy/mm/dd)！");
   return(false);
  }
  else if (y.length < 4){ alert("年數應以四位數表示！");return(false);}
  else if (!isBetween(m,1,12)){ alert("月份應在1~12之間！");return(false);}
  if(m.length!=2){ alert("請在月份前加零！");return(false);}
  else if (m==4||m==6||m==9||m==11)maxDays = 30;
  else if (m==2){
   if(y%4>0)maxDays = 28;
   else if(y%100==0&&y%400>0)maxDays = 28;
   else maxDays = 29;
  }
  if(isBetween(d,1,maxDays)==false){ alert("本月只有"+maxDays+"天！請確認後重新輸入！");return(false);}
  if(d.length!=2){ alert("請在日期前加零！"); return(false);}
  return(true);
 
 }
}



function isDecimal(str,i,j){           //檢查str是否為數字（其整數位不可超過i,小數位不可超過j）
	var dot = str.indexOf(".");
	var dot_last = str.lastIndexOf(".");
	var str_f = "";
	var str_b = "";
	
	if ( dot != -1 ){
		str_f = str.substring(0,dot);
	} else {
		str_f = str;
	}
	
	if ( dot_last != -1 ){
		str_b = str.substring(dot+1);
	} else {
		str_b = str;
	}
	
	if( isInt( str_f ) == false ){
		alert("不是合法的數字");
		return false;
	} else if ( isInt( str_b ) == false ){
		alert("不是合法的數字");
		return false;
	} else if ( dot != dot_last ){
		alert("不是合法的數字");
		return false;
	} else if(dot==0 || dot_last==0){
		alert("不是合法的數字");
		return false;
	}
	
	if ( str_f == str_b ){
		if ( j != 0 ) {
			alert("小數位必須為"+j+"位");
			return false;
		}
	}
	
	
	if(str_f.length>i){
		alert("整數位不能超過"+i+"位");
		return false;
	}
	if(dot!=-1 && str_b.length!=j){
		alert("小數位必須為"+j+"位");
		return false;			
	}	
	return true;
}


function isYear(str){       //檢查str是否為合法的年份數

	if(str.length!=4){
		alert("請完整填寫所有的日期,年份必須為四位數字!");
		return false;
	}
	
	for(i=0 ;i<str.length;i++){
		var chr = str.charAt(i);
		if(!(chr>=0 && chr<=9 )){
			alert("年份必須為四位數字!");
			return false;
		}
	}
	
	if((str.charAt(0)<1)||(str.charAt(0)>2)){
		alert("年份第一位必須為數字1或2!");
		return false;
	}
	return true;
}

function isLegalDate(year,month,day){   //檢查時間的合法性，year,month,day分別表示年份，月份及日期

	if(((month==4)||(month==6)||(month==9)||(month==11))&&(day==31)){
		alert("該月沒有31日");
		return false;
	}
	
	if((((year % 4)!=0)||(year%100==0)) && (month==2) && (day>28)){
		alert("該月只有28天！");
		return false;
	}
	
	if((month==2) && (day>29)){
		alert("該月只有29天！");
		return false;
	}
	return true;
}

function isTel(str){    //檢查str是否為合法的電話或傳真號
	for(i=0;i<str.length;i++){
	var chr = str.charAt(i);
		if(!(chr>=0 && chr<=9 || chr=="-")){
			alert("電話、傳真必須為數字!");
			return false;
		}
	}
	return true;
}


function isDateInt(theStr){
 var flag = true;
 if (isEmpty(theStr)) { flag = false; }
 else
 { for (var i = 0; i < theStr.length;i++){
   if (isDigit(theStr.substring(i,i+1)) == false){
   flag = false; break;
   } 
  }
 return (flag);
 }
}

function isEmail(theStr){
	var atIndex = theStr.indexOf('@');
	var dotIndex = theStr.indexOf('.',atIndex);
	var flag = true;
	var theSub = theStr.substring(0,dotIndex+1);
	if((atIndex < 1)||(atIndex != theStr.lastIndexOf('@'))||(dotIndex < atIndex + 2)||(theStr.length <= theSub.length))
	{ flag = false;	}
	else	{ flag =true;	}
	return(flag);
}


function isReal(theStr,decLen){
	var dot1st = theStr.indexOf('.');
	var dot2nd = theStr.lastIndexOf('.');
	var OK = true;
	if (isEmpty(theStr)) return false;
	if (dot1st == -1){
		if (!isDateInt(theStr)) return(false);
		else return(true);
	}
	else if (dot1st != dot2nd) return (false);
	else if (dot1st == 0) return (false);
	else {
		var intPart = theStr.substring(0,dot1st);
		var decPart = theStr.substring(dot2nd+1);
		if (decPart.length > decLen) return(false);
		else if (!isDateInt(intPart) || !isDateInt(decPart)) return (false);
		else if (isEmpty(decPart)) return (false);
		else return (true);
	}
}

function isNegative(theStr){
	var theSign = theStr.charAt(0);
	if(theSign=='-'){return true;}
	else{return false;}
	
}

function StrCom(str,len,type)
 {
    
     var Sspace = " ";
     var Strs = str;
     var strlength=0;
     var i;   
      for ( i=0;i<str.length;i++)
                {
                        if(str.charCodeAt(i)>=1000)
                                strlength += 2;
                        else
                                strlength += 1;
                }
    
     
   for ( i=0;i<(len-strlength);i++)
   {
   Strs = Strs + Sspace;
   }
  
   return(Strs);
 }
