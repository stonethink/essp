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

 //�ˬd��J
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
		alert("����줣��t�޸��C");
		obj.focus();
		return false;
	}
	return true;	
}

function isBetween(val,lo,hi){   //�ˬdval�O�_�blo�Phi����
 if ((val < lo) || (val > hi)){ return(false);}
 else { return(true);}
}
 
function isDigit(theNum){    //�ˬdtheNum�O�_���Ʀr
 var theMask = "0123456789";
 if (isEmpty(theNum)) return(false);
 else if(theMask.indexOf(theNum) == -1) return(false);
 return(true);
}
 
 
 
function isEmpty(str){   //�ˬdstr�O�_����
 if((str == null)||(str.length == 0)) return (true);
 else return(false);
}
 

function isInt(str){     //�ˬdstr�O�_�����
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
 
function isDate(theStr){     //�ˬdtheStr�O�_���ɶ��榡yyyymmdd�A�H�Ψ�X�k��
 if(theStr.length!=8){ 
 alert("�D�k����A�Э��s��J(yyyymmdd)�I");
 return (false);}
 else {
  var y = theStr.substring(0,4);
  var m = theStr.substring(4,6);
  var d = theStr.substring(6,8);
  var maxDays = 31;
  if(isDateInt(m)==false||isDateInt(d)==false||isDateInt(y)==false){
   alert("������t���D�Ʀr�P���ɲŪ��䥦�r�šA�Э��s��J(yyyymmdd)�I");
   return(false);
  }
  else if (y.length < 4){ alert("�~�����H�|��ƪ�ܡI");return(false);}
  else if (!isBetween(m,1,12)){ alert("������b1~12�����I");return(false);}
  if(m.length!=2){ alert("�Цb����e�[�s�I");return(false);}
  else if (m==4||m==6||m==9||m==11)maxDays = 30;
  else if (m==2){
   if(y%4>0)maxDays = 28;
   else if(y%100==0&&y%400>0)maxDays = 28;
   else maxDays = 29;
  }
  if(isBetween(d,1,maxDays)==false){ alert("����u��"+maxDays+"�ѡI�нT�{�᭫�s��J�I");return(false);}
  if(d.length!=2){ alert("�Цb����e�[�s�I"); return(false);}
  return(true);
 
 }
}


function isDate1(theStr){     //�ˬdtheStr�O�_���ɶ��榡yyyy/mm/dd�A�H�Ψ�X�k��
 if(theStr.length!=8){ 
 alert("�D�k����A�Э��s��J(yyyy/mm/dd)�I");
 return (false);}
 else {
  var y = theStr.substring(0,the1st);
  var m = theStr.substring(the1st+1,the2nd);
  var d = theStr.substring(the2nd+1,theStr.length);
  var maxDays = 31;
  if(isDateInt(m)==false||isDateInt(d)==false||isDateInt(y)==false){
   alert("������t���D�Ʀr�P���ɲŪ��䥦�r�šA�Э��s��J(yyyy/mm/dd)�I");
   return(false);
  }
  else if (y.length < 4){ alert("�~�����H�|��ƪ�ܡI");return(false);}
  else if (!isBetween(m,1,12)){ alert("������b1~12�����I");return(false);}
  if(m.length!=2){ alert("�Цb����e�[�s�I");return(false);}
  else if (m==4||m==6||m==9||m==11)maxDays = 30;
  else if (m==2){
   if(y%4>0)maxDays = 28;
   else if(y%100==0&&y%400>0)maxDays = 28;
   else maxDays = 29;
  }
  if(isBetween(d,1,maxDays)==false){ alert("����u��"+maxDays+"�ѡI�нT�{�᭫�s��J�I");return(false);}
  if(d.length!=2){ alert("�Цb����e�[�s�I"); return(false);}
  return(true);
 
 }
}



function isDecimal(str,i,j){           //�ˬdstr�O�_���Ʀr�]���Ʀ줣�i�W�Li,�p�Ʀ줣�i�W�Lj�^
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
		alert("���O�X�k���Ʀr");
		return false;
	} else if ( isInt( str_b ) == false ){
		alert("���O�X�k���Ʀr");
		return false;
	} else if ( dot != dot_last ){
		alert("���O�X�k���Ʀr");
		return false;
	} else if(dot==0 || dot_last==0){
		alert("���O�X�k���Ʀr");
		return false;
	}
	
	if ( str_f == str_b ){
		if ( j != 0 ) {
			alert("�p�Ʀ쥲����"+j+"��");
			return false;
		}
	}
	
	
	if(str_f.length>i){
		alert("��Ʀ줣��W�L"+i+"��");
		return false;
	}
	if(dot!=-1 && str_b.length!=j){
		alert("�p�Ʀ쥲����"+j+"��");
		return false;			
	}	
	return true;
}


function isYear(str){       //�ˬdstr�O�_���X�k���~����

	if(str.length!=4){
		alert("�Ч����g�Ҧ������,�~���������|��Ʀr!");
		return false;
	}
	
	for(i=0 ;i<str.length;i++){
		var chr = str.charAt(i);
		if(!(chr>=0 && chr<=9 )){
			alert("�~���������|��Ʀr!");
			return false;
		}
	}
	
	if((str.charAt(0)<1)||(str.charAt(0)>2)){
		alert("�~���Ĥ@�쥲�����Ʀr1��2!");
		return false;
	}
	return true;
}

function isLegalDate(year,month,day){   //�ˬd�ɶ����X�k�ʡAyear,month,day���O��ܦ~���A����Τ��

	if(((month==4)||(month==6)||(month==9)||(month==11))&&(day==31)){
		alert("�Ӥ�S��31��");
		return false;
	}
	
	if((((year % 4)!=0)||(year%100==0)) && (month==2) && (day>28)){
		alert("�Ӥ�u��28�ѡI");
		return false;
	}
	
	if((month==2) && (day>29)){
		alert("�Ӥ�u��29�ѡI");
		return false;
	}
	return true;
}

function isTel(str){    //�ˬdstr�O�_���X�k���q�ܩζǯu��
	for(i=0;i<str.length;i++){
	var chr = str.charAt(i);
		if(!(chr>=0 && chr<=9 || chr=="-")){
			alert("�q�ܡB�ǯu�������Ʀr!");
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
