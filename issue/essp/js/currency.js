//去掉数字中的逗号
function removeComma(str){
     var num;
     if(str==null){
     	num="";
     }else{
     	num=str+"";
     }
     if(num!=null&&num!=""){
     	var str_length=num.length;
		while(num.indexOf(",")>=0){
				num=num.replace(",","");
        	}
		}
     return num;
 }

//保留两位小数
function moneychange(value){
    	var sign = "";
		var rtValue = "";
		var temp = removeComma(value);
        var integerValue='0';
        var fractionValue='00';
		if(temp==""){
            return '0.00';
		}else if(isNaN(temp)){
			return value;
        }else{
            if(temp!=null&&temp.length>1&&(temp.charAt(0)=="+"||temp.charAt(0)=="-")){
            	sign = temp.charAt(0);
         		temp = temp.substring(1,temp.length);
        	}
        	if(temp.indexOf(".")>=0){
        		temp = (Math.round(temp*100))/100;
				var d = new Array();
            	d=(temp+"").split(".");
                integerValue=d[0]+"";
                if(d.length==2){
            		fractionValue=d[1]+"";
                }
            	if(fractionValue.length<2){
                 	fractionValue=fractionValue+"0";
            	}
			}else{
        		integerValue=temp;
			}
		}
//整数部分加逗号
        var integerSize = integerValue.length;
		while(integerSize>3){
			rtValue = "," + integerValue.substr(integerSize-3, 3) +""+ rtValue;
			integerSize = integerSize - 3;
			integerValue = integerValue.substr(0,integerSize);
		}
		rtValue = sign+integerValue + rtValue + "." + fractionValue;
        return rtValue;
}

//保留一位小数
function moneyFormat1(value){
    	var sign = "";
		var rtValue = "";
		var temp = removeComma(value);
        var integerValue='0';
        var fractionValue='0';
		if(temp==""){
            return '0.0';
		}else if(isNaN(temp)){
			return value;
        }else{
            if(temp!=null&&temp.length>1&&(temp.charAt(0)=="+"||temp.charAt(0)=="-")){
            	sign = temp.charAt(0);
         		temp = temp.substring(1,temp.length);
        	}
        	if(temp.indexOf(".")>=0){
        		temp = (Math.round(temp*10))/10;
				var d = new Array();
            	d=(temp+"").split(".");
                integerValue=d[0]+"";
                if(d.length==2){
            		fractionValue=d[1]+"";
                }
//            	if(fractionValue.length<2){
//                 	fractionValue=fractionValue+"0";
//            	}
			}else{
        		integerValue=temp;
			}
		}
//整数部分加逗号
        var integerSize = integerValue.length;
		while(integerSize>3){
			rtValue = "," + integerValue.substr(integerSize-3, 3) +""+ rtValue;
			integerSize = integerSize - 3;
			integerValue = integerValue.substr(0,integerSize);
		}
		rtValue = sign+integerValue + rtValue + "." + fractionValue;
        return rtValue;
}

//保留到整数
function moneyFormat0(value){
    	var sign = "";
		var rtValue = "";
		var temp = removeComma(value);
		if(temp==""){
            return '0';
		}else if(isNaN(temp)){
			return value;
        }else{
            if(temp!=null&&temp.length>1&&(temp.charAt(0)=="+"||temp.charAt(0)=="-")){
            	sign = temp.charAt(0);
         		temp = temp.substring(1,temp.length);
        	}
            temp=Math.round(temp)+'';
		}
//加逗号
        var tempSize = temp.length;
		while(tempSize>3){
			rtValue = "," + temp.substr(tempSize-3, 3) +""+ rtValue;
			tempSize = tempSize - 3;
			temp = temp.substr(0,tempSize);
		}
//        alert(temp);
		rtValue = sign+temp+rtValue;
        return rtValue;
}

//如果不是数字则返回0.00
function moneyformat(money){
    var rmoney = moneychange(money);
    if(isNaN(removeComma(rmoney))){
    return "0.00";
    }else{
    return rmoney;
    }
}

function getZero(iLen){
	var sRet = "";
	var i = 0;
	for ( i = 0 ; i<iLen;i++){
		sRet = sRet + "0";
	}
	
	return sRet;
}

//保留小数，依据第二个参数进行保留
function moneychange2value(value,iLen){
	var sign = "";
	var rtValue = "";
	var temp = removeComma(value);
        	var integerValue='0';
        	var fractionValue=getZero(iLen);
	if(temp==""){
            		return '0.' + getZero(iLen);
	}else if(isNaN(temp)){
		return value;
        	}else{
            		if(temp!=null&&temp.length>1&&(temp.charAt(0)=="+"||temp.charAt(0)=="-")){
            			sign = temp.charAt(0);
         			temp = temp.substring(1,temp.length);
        		}
        		if(temp.indexOf(".")>=0){
        			temp = (Math.round(temp*Math.pow(10,iLen)))/Math.pow(10,iLen);
        			
			var d = new Array();
            			d=(temp+"").split(".");
                			integerValue=d[0]+"";
                		
                			if(d.length==2){
                				
                				while( d[1].length<iLen){
                					//alert(d[1].length);
                					d[1] = d[1] + "0";
                				}
            				fractionValue=d[1]+"";
                			}
            		
            			if(fractionValue.length<2){
                 				fractionValue=fractionValue+"0";
	            		}
		}else{
        			integerValue=temp;
		}
	}
//整数部分加逗号
        	var integerSize = integerValue.length;
	
	while(integerSize>3){
		rtValue = "," + integerValue.substr(integerSize-3, 3) +""+ rtValue;
		integerSize = integerSize - 3;
		integerValue = integerValue.substr(0,integerSize);
	}
	rtValue = sign+integerValue + rtValue + "." + fractionValue;
	return rtValue;
}

