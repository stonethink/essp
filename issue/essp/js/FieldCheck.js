// File Name : FieldCheck.js
// 
// LSAMJ Framework Client Control Script
// COPYRIGHT 2003-2004 TIS Inc.
// 
// Creation Date 	: 2004/01/08
// Modification Date: $Date: 2005/11/03 08:13:58 $
// Creator 			: YUTAKA YOSHIDA
//

// ****************************************
// JavaScript : fieldtype\u657e\u6395
// 
// fieldtype\u61cf\u60c8\u50aa\u657e\u6395\u505f\u4e04
// \u61f3\u58b3\u5061\u509e\u50e0\u50c3\u50e2\u50cb\u5a2d\u60a2\u50aa\u5c47\u5083\u5f0c\u505f\u5091\u5061\u4e05
// ****************************************
function doFieldCheck(obj) {
//	\u50e5\u50d7\u50e9
//	getNowDate("doFieldCheck()\u5950\u5dd2\u4e05\u5837\u60a2\u507aname: " + obj.name + ", \u5837\u60a2\u507afieldtype: " + obj.fieldtype);	
	// \u6268\u5d01\u681a\u50e0\u50c3\u50e2\u50cb\u50fc\u5114\u50cc\u50aa\u5f36\u5a5c\u58d4
    obj.fielderrorflg="true";
    if(obj.type=="text") { 
		switch (obj.fieldtype){
			case "number":
				checkNumberSys(obj,obj.fmt);
				break;
			case "money":					 // \u65d5\u6087\u5f60\u4e05\u4efacommanumber\u50aa\u5dca\u68a1\u4e05
				checkNumberSys(obj,obj.fmt); // \u50e0\u50c3\u50e2\u50cb\u5dc7\u689d\u5051number\u5072\u6468\u5060\u4e05
				break;
			case "commanumber":
				checkNumberSys(obj,obj.fmt); // \u50e0\u50c3\u50e2\u50cb\u5dc7\u689d\u5051number\u5072\u6468\u5060\u4e05
				break;
			case "numbercode":
				checkCharCodeSys(obj,obj.fmt);
				break;
			case "text":
				checkXferSys(obj,obj.fmt);
				break;
			case "alpha":
				checkAlphaSys(obj,obj.fmt);
				break;
			case "capitalcode":
				checkCapitalCodeSys(obj);
				break;
			case "dateyymmdd":
				checkDateYYMMDDSys(obj,obj.fmt);
				break;
			case "dateyymm":
				checkDateYYMMSys(obj,obj.fmt);
				break;
			case "dateyyyymmdd":
				checkDateYYYYMMDDSys(obj,obj.fmt);
				break;
			case "dateyyyymm":
				checkDateYYYYMMSys(obj,obj.fmt);
				break;
			case "datemmdd":
				checkDateMMDDSys(obj,obj.fmt);
				break;
			case "timehhmm":
				checkTimeHHMMSys(obj);
				break;
			case "timehhmmss":
				checkTimeHHMMSSSys(obj);
				break;
			default:
				return;
		}
    } else if(obj.tagName=="TEXTAREA") {
        var maxlength = obj.getAttribute("maxlength");
        if(maxlength!=null && maxlength!="") {
			var len = toByteLength(obj.value);
			if (maxlength < len) {
				//obj.fielderrorflg=false;
				doErrorMessage(obj, id0013);
			}
        }
    }
	
	// \u6268\u5d01\u681a\u50c4\u5114\u4e55\u5075\u5074\u509c\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04fieldmsg\u61cf\u60c8\u50aa\u5f36\u5a5c\u58d4\u5061\u509e
	if(obj.fielderrorflg=="true"){
		obj.fieldmsg="";
	}
}

// ****************************************
// JavaScript : \u50c4\u5114\u4e55\u66d7\u61cc\u5e2a\u5f35\u68df
// 
// \u50c4\u5114\u4e55\u66d7\u61cc\u5e2a\u5075\u5adf\u6360\u505f\u5070\u5cf4\u5074\u5046\u5f35\u68df\u5071\u5061\u4e05
// ****************************************
function doErrorMessage(obj,message) {
//	getNowDate("\u6268\u5d01\u681a\u50c4\u5114\u4e55\u5071\u5061\u4e05");
	obj.fielderrorflg="false";

	// \u6156\u6230\u5fec\u61fa\u5075\u505f\u5068\u5fdc\u5d0c\u4e04\u651a\u5ba8\u507a\u50c4\u5114\u4e55\u6013\u507c\u5c12\u504a\u5074\u5044\u507a\u5071\u4e04\u5c30\u5d7c\u50d0\u510a\u511e\u50e9\u50be\u50c2\u50e9 
//	obj.style.backgroundColor=Err_COLOR; // \u6112

	// \u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u6820\u505f\u4e04\u510a\u50e2\u50d9\u4e55\u50d5\u50aaInformationArea\u5075\u661e\u5e35
	displayMessageWithFocusSet(message, obj);
}

// ****************************************
// JavaScript : \u60a2\u62a3\u50e0\u50c3\u50e2\u50cb
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051\u60a2\u62a3\u5071\u5041\u509e
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// ****************************************
function checkNumberSys(obj,fmt){
	var num;
	var numLen=new Array(2);
	var format1,format2;
	var fmtLen=new Array(2);
	var	idx
	var	ret;
	var value=obj.value;
	
	if(value!="") {

		var exp = new RegExp("^-?\\.");
		if (exp.test(value)){
			//\u612d\u6462\u66a5\u5e24\u5051 "-.", "."\u507a\u5fdc\u5d0c\u507c\u50c4\u5114\u4e55\u5075
//			\u4e7d\u612d\u6462\u5075.\u507c\u64d6\u6921\u5071\u5052\u5091\u5063\u50ab\u4e7f
			doErrorMessage(obj,id0014);
			return;
		}

		exp = new RegExp("^-+$");
		if (exp.test(value)) {
			// '-'\u507a\u5092\u507c\u50c4\u5114\u4e55
//			\u4e7d-\u507a\u5092\u507c\u64d6\u6921\u5071\u5052\u5091\u5063\u50ab\u4e7f
			doErrorMessage(obj, id0015);
			return;
		}

		if (isNaN(value)) {
			// \u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5063\u5062\u5075\u6337\u508b\u5070\u4e04\u60a2\u5e24\u61e8\u6449\u60c8\u5075\u5050\u5050\u509e
//			doErrorMessage(obj,"\u657f\u598f\u60a2\u5e24\u5072\u5a70\u5d0b-.\u507a\u5092\u64d6\u6921\u58dc\u64fb\u5071\u5061\u4e05");
			doErrorMessage(obj,id0004);
			return;
		}

		if (fmt!="" && fmt!=null){
			format1=fmt;
		}else{
			format1=obj.maxlength+".0";
		}

		//\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u507a\u5bd8\u60a2
		format2=format1.split(".");
		fmtLen[0]=parseInt(format2[0]);
		fmtLen[1]=parseInt(format2[1]);
//		fmtLen[0]=fmtLen[0]-fmtLen[1];
		
		//\u64d6\u6921\u62a3\u507a\u5bd8\u60a2
		temp = new String(obj.value);
		temp = jsUnformatNumberSys(temp);
		num=temp.split(".");
		numLen[0]=parseInt(num[0].length);
		if (num[0].substr(0,1)=="-"){
			numLen[0]--;
		}
		if (num[1]!="" && num[1]!=null){
			numLen[1]=parseInt(num[1].length);
		}else{
			numLen[1]=0;
		}

		//\u5bd8\u60a2\u50e0\u50c3\u50e2\u50cb
		if ((numLen[0]>fmtLen[0]) || (numLen[1]>fmtLen[1])){
//			doErrorMessage(obj,"\u5bd8\u5041\u5086\u509f\u5071\u5061\u4e05");
			doErrorMessage(obj,id0003);
			return;
		}
	}
}

// ****************************************
// JavaScript : \u60a2\u5e24\u50e0\u50c3\u50e2\u50cb
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051\u60a2\u5e24\u5071\u5041\u509e
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// ****************************************
function checkCharCodeSys(obj,fmt){
	var check_obj;
	if(obj.value!=""){
		//\u60a2\u5e24 \u5051\u64d6\u6921\u58dc
		check_obj = new RegExp("[^0-9]","i");
		if(check_obj.test(obj.value)){
//			doErrorMessage(obj,"\u657f\u598f\u60a2\u5e24\u507a\u5092\u64d6\u6921\u58dc\u64fb\u5071\u5061\u4e05");
			doErrorMessage(obj,id0005);
			return;
		}
	}
	return;
}

// ****************************************
// JavaScript : \u64d4\u6645\u50e0\u50c3\u50e2\u50cb
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051\u64d4\u6645\u4e6eYY/MM\u4e6f\u5071\u5041\u509e
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// \u65d5\u6087\u5f60\u4efacheckDateYYMMSys()\u50aa\u5dca\u68a1\u5061\u509e\u5059\u5072
// ****************************************
function checkDate4Sys(obj,fmt){
    var	errFlg=0;
    var value=obj.value;
	if(value!=""){
		//case "dateyymm":
		if (value.length==4){
			//\u64ed\u50e0\u50c3\u50e2\u50cb
			if ((value.substr(0,2)<"00") || (value.substr(0,2)>"99") || (isNaN(value.substr(0,2)))){
				errFlg = 1;
			//\u5be7\u50e0\u50c3\u50e2\u50cb
			}else if ((value.substr(2,2)<"01") || (value.substr(2,2)>"12")){
				errFlg = 1;
			}

		} else if(value.length==5) {
			if ((value.substr(0,2)<"00") || (value.substr(0,2)>"99") || (isNaN(value.substr(0,2)))){
				errFlg = 1;
			//\u5be7\u50e0\u50c3\u50e2\u50cb
			} else if(value.substr(2,1)!="/") {
				errFlg = 1;
			} else if ((value.substr(3,2)<"01") || (value.substr(3,2)>"12")){
				errFlg = 1;
			}
		} else{
			errFlg = 1;
		}
	}	

	if (errFlg==1){
//		doErrorMessage(obj,"\u60d3\u505f\u5044\u64d4\u6645\u50aa\u64d6\u6921\u505f\u5070\u58d3\u505d\u5044\u4e05");
		doErrorMessage(obj,id0006);
		return;
	}
	return;
}

// ****************************************
// JavaScript : \u64d4\u6645\u50e0\u50c3\u50e2\u50cb
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051\u64d4\u6645\u4e6eYY/MM/DD\u4e6f\u5071\u5041\u509e
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// \u65d5\u6087\u5f60\u4efacheckDateYYMMDDSys()\u50aa\u5dca\u68a1\u5061\u509e\u5059\u5072
// ****************************************
function checkDate6Sys(obj,fmt){
	var	errFlg=0;
	var value=obj.value;

	if(value!=""){
		//case "dateyymmdd":
		if (value.length==6){
			//\u64ed\u50e0\u50c3\u50e2\u50cb
			if ((value.substr(0,2)<"00") || (value.substr(0,2)>"99") || (isNaN(value.substr(0,2)))){
				errFlg = 1;
			//\u5be7\u50e0\u50c3\u50e2\u50cb
			}else if ((value.substr(2,2)<"01") || (value.substr(2,2)>"12")){
				errFlg = 1;
			}else{
				//\u64d4\u50e0\u50c3\u50e2\u50cbYYMM\u4efaYYYYMM
				strDte = unformatNengetuSys(value.substr(0,4))
				//if ((value.substr(4,2)<"01") || (value.substr(4,2)>getMonthLastDateSys(strDte))){
				//	errFlg = 1;
				//}
				if ((value.substr(4,2)>="01") && (value.substr(4,2)<=getMonthLastDateSys(strDte))){
					//\u64d4\u6645CheckOK.
				}else{
					errFlg = 1;
				}
			}
		}else{
			errFlg = 1;
		}

		if (errFlg==1){
//			doErrorMessage(obj,"\u60d3\u505f\u5044\u64d4\u6645\u50aa\u64d6\u6921\u505f\u5070\u58d3\u505d\u5044\u4e05");
			doErrorMessage(obj,id0006);
			return;
		}
	}
	return;
}						

// ****************************************
// JavaScript : \u64d4\u6645\u50fc\u50c5\u4e55\u5105\u50e2\u50e9
// 
//	\u64ed\u4fc2\u5bd8\u58d4(YYMM\u4efaYYYYMM)	
// ****************************************
						
function unformatNengetuSys(strNengetu){

	return formatFourYearSys(strNengetu.substr(0,2))+strNengetu.substr(2,2);

}

// ****************************************
// JavaScript : \u64d4\u6645\u50fc\u50c5\u4e55\u5105\u50e2\u50e9
// 
//	\u64ed\u4fc2\u5bd8\u58d4(YY\u4efaYYYY)	
// ****************************************	
				
function formatFourYearSys(strNen){

	var intNen = parseInt(strNen,10);

	if (strNen!= ""){

		if (intNen < 50){
			return "20"+strNen;
		}else{
			return "19"+strNen;
		}

	}else{
		//\u62a3\u5051\u5837\u5052\u6409\u505d\u509f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u66c7\u5ee4\u50aa\u5cf4\u50a2\u5074\u5044
		return strNen;
	}

}

// ****************************************
// JavaScript : // \u5be7\u679b\u64d4\u5ea2\u647c
// 
// \u5ddc\u6395\u505d\u509f\u5068\u5be7\u507a\u679b\u64d4\u50aa\u5ea2\u647c
// ****************************************

function getMonthLastDateSys(strNenGetu){

	//\u5be7\u679b\u64d4
	switch(strNenGetu.substr(4,2)){
	case "01":
	case "03":
	case "05":
	case "07":
	case "08":
	case "10":
	case "12":
		return 31;
		break;
	case "04":
	case "06":
	case "09":
	case "11":
		return 30;
		break;
	case "02":
		//\u5046\u509e\u5046\u64ed\u5071\u5074\u5044
		if (checkUrudosiSys(strNenGetu.substr(0,4))==0){
			return 28;
		//\u5046\u509e\u5046\u64ed
		}else{
			return 29;
		}
	}

}

// ****************************************
// JavaScript : // \u5be7\u679b\u64d4\u5ea2\u647c
// 
// \u5ddc\u6395\u505d\u509f\u5068\u5be7\u507a\u679b\u64d4\u50aa\u5ea2\u647c
// \u50fc\u50bf\u4e55\u5116\u50ea\u50de\u50c0\u50fe\u5051\u4ff5\u4ff5\u4fe2\u4fe2\u507a\u5072\u5052\u507a\u50e0\u50c3\u50e2\u50cb
// \u4e6e\u5046\u509e\u5046\u64ed\u50aa\u5bc1\u5dbc\u505f\u5074\u5044\u4e6f
// ****************************************
function getMonthLastDateMMDDSys(mm){

	//\u5be7\u679b\u64d4
	switch(mm){
	case "01":
	case "03":
	case "05":
	case "07":
	case "08":
	case "10":
	case "12":
		return 31;
		break;
	case "04":
	case "06":
	case "09":
	case "11":
		return 30;
		break;
	case "02":
		return 29;
	}
}

// ****************************************
// JavaScript : // \u5046\u509e\u5046\u64ed\u657e\u6395
// 
// \u5ddc\u6395\u505d\u509f\u5068\u64ed\u5051\u5046\u509e\u5046\u64ed\u5050\u5073\u5046\u5050\u507a\u657e\u6290
// ****************************************
	
function checkUrudosiSys(strNen){
	
	var intNen = parseInt(strNen,10);
	
	if (intNen%4 == 0){
		//\u5046\u509e\u5046\u64ed
		return 1;
	}
	if (intNen%100 == 0){
		//\u5046\u509e\u5046\u64ed\u5071\u5074\u5044
		return 0;
	}
	if (intNen%400 == 0){
		//\u5046\u509e\u5046\u64ed
		return 1;
	}
	
	return 0;

}

// ****************************************
// JavaScript : \u657f\u598f\u5878\u60a2\u50e0\u50c3\u50e2\u50cb
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051\u657f\u598f\u5878\u60a2\u5071\u5041\u509e
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// ****************************************
function checkAlphaSys(obj,fmt){
	var check_obj;
	if(obj.value!=""){
		//\u657f\u598f\u5878\u60a2\u5051\u64d6\u6921\u58dc
		check_obj = new RegExp("[^a-zA-Z0-9()/-]");
		if(check_obj.test(obj.value)){
//			doErrorMessage(obj,"\u657f\u598f\u5878\u60a2\u507a\u5092\u64d6\u6921\u58dc\u64fb\u5071\u5061\u4e05");
			doErrorMessage(obj,id0007);
			return;
		}
	}
	return;
}

// ****************************************
// JavaScript : \u50fc\u5115\u4e55\u50e5\u50c9\u50d7\u50e9\u50e0\u50c3\u50e2\u50cb
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051\u50fc\u5115\u4e55\u5071\u5041\u509e
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// ****************************************
function checkXferSys(obj,fmt){

	var check_obj;
	if (obj.value!="") {
		// \u64d6\u6921\u5041\u509d\u507a\u5fdc\u5d0c
		var bytemode = (window.document.forms[0].text_length_evaluation_mode.value == "byte");
		if (bytemode) {
			// \u50f6\u50c0\u50e9\u657e\u6395\u510c\u4e55\u50ea\u507a\u5fdc\u5d0c
			var maxlength = obj.getAttribute("maxlength");
			var len = toByteLength(obj.value);
//			alert("bytemode len:" + len + " /maxlength:" + maxlength);
			if (maxlength < len) {
				// \u60c2\u5c37\u50f6\u50c0\u50e9\u633f\u50aa\u633b\u504a\u5068\u5fdc\u5d0c
//			doErrorMessage(obj,"\u64d6\u6921\u60c2\u5c37\u633f\u50aa\u633b\u504a\u5070\u5044\u5091\u5061\u4e05");
				doErrorMessage(obj, id0013);
			}
		}
	    return;
    }
	
}

// ****************************************
// JavaScript : \u657f\u598f\u5878\u60a2\u5e24\u5072-\u507a\u50e0\u50c3\u50e2\u50cb
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051\u657f\u598f\u5878\u60a2\u5072\u657f\u598f\u50f4\u50c0\u50fc\u511e\u5071\u5041\u509e
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// ****************************************
function checkCapitalCodeSys(obj){
	
	var check_obj;
	if(obj.value!=""){
		//\u60a2\u5e24 \u5051\u64d6\u6921\u58dc
		check_obj = new RegExp("[^a-zA-Z0-9-]");
		if(check_obj.test(obj.value)){
			doErrorMessage(obj,id0011);
			return;
		}
	}
	return;
}

// ****************************************
// JavaScript : \u5e2a\u5a2b\u507a\u60d3\u6449\u60c8\u50e0\u50c3\u50e2\u50cb\u4e6ehhmm\u4e6f
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051HHMM\u507a\u5e2a\u5a2b\u5b8d\u5e43
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// ****************************************
function checkTimeHHMMSys(obj){
//	alert("FunctionStart : checkTimeHHMMSys");
	
	var	errFlg = 0;
	var value = obj.value;
		
	if(value!=""){
		if(value.length == 4){
			//\u5e2a\u50e0\u50c3\u50e2\u50cb
			if((value.substr(0,2)<"00") || (value.substr(0,2)>"23")){
				errFlg = 1;
			}else{
				//\u6698\u50e0\u50c3\u50e2\u50cb
				if((value.substr(2,2)<"00") || (value.substr(2,2)>"59")){
					errFlg = 1;
				}
			}
		} else if(value.length == 5) {
			if((value.substr(0,2)<"00") || (value.substr(0,2)>"23")){
				errFlg = 1;
			} else if(value.substr(2,1)!=":") {
				errFlg = 1;
			} else if((value.substr(3,2)<"00") || (value.substr(3,2)>"59")){
				errFlg = 1;
			}
			
			
		} else{
			errFlg = 1;
		}
	}
	
	if(errFlg == 1){
		doErrorMessage(obj, id0012);
		return;
	}
	
	return;
}

// ****************************************
// JavaScript : \u5e2a\u5a2b\u507a\u60d3\u6449\u60c8\u50e0\u50c3\u50e2\u50cb\u4e6ehhmmss\u4e6f
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051HHMMSS\u507a\u5e2a\u5a2b\u5b8d\u5e43
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// ****************************************
function checkTimeHHMMSSSys(obj){
	
	var	errFlg = 0;
	var value = obj.value;
	var hh = value.substr(0,2);	//\u5e2a
	var mm = value.substr(2,2);	//\u6698
	var ss = value.substr(4,2);	//\u6698
	
	if(value!=""){
		if(value.length == 6){
			//\u5e2a\u50e0\u50c3\u50e2\u50cb
			if((hh<"00") || (hh>"23")){
				errFlg = 1;
			}else{
				//\u6698\u50e0\u50c3\u50e2\u50cb
				if((mm<"00") || (mm>"59")){
					errFlg = 1;
				}else{
					//\u6629\u50e0\u50c3\u50e2\u50cb
					if((ss<"00") || (ss>"59")){
						errFlg = 1;
					}
				}
			}
		}else{
			errFlg = 1;
		}
	}
	
	if(errFlg == 1){
		doErrorMessage(obj, id0012);
		return;
	}
	
	return;
}

// ****************************************
// JavaScript : \u64d4\u6645\u50e0\u50c3\u50e2\u50cb
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051\u64d4\u6645\u4e6eYY/MM/DD\u4e6f\u5071\u5041\u509e
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// ****************************************
function checkDateYYMMDDSys(obj,fmt){
	//alert("FunctionStart : checkDateYYMMDDSys_ymd "+obj.value);
	
	var	errFlg=0;
	var value=obj.value;
	var yy = value.substr(0,2);	//\u64ed
	var mm = value.substr(2,2);	//\u5be7
	var dd = value.substr(4,2);	//\u64d4
	//alert("yy="+yy+" mm="+mm+" dd="+dd);
	
	if(value!=""){
		//case "dateyymmdd":
		if (value.length==6){
			//\u64ed\u50e0\u50c3\u50e2\u50cb
			if ((yy<"00") || (yy>"99") || (isNaN(yy))){
				errFlg = 1;
			//\u5be7\u50e0\u50c3\u50e2\u50cb
			}else if ((mm<"01") || (mm>"12")){
				errFlg = 1;
			}else{
				//\u64d4\u50e0\u50c3\u50e2\u50cbYYMM\u4efaYYYYMM
				strYYYYMM = unformatNengetuSys(yy+mm)
				if ((dd>="01") && (dd<=getMonthLastDateSys(strYYYYMM))){
					//\u64d4\u6645\u50e0\u50c3\u50e2\u50cbOK.
				}else{
					errFlg = 1;
				}
			}
		}else{
			errFlg = 1;
		}

		if (errFlg==1){
			doErrorMessage(obj,id0006);
			return;
		}
	}
	return;
}			

// ****************************************
// JavaScript : \u64d4\u6645\u50e0\u50c3\u50e2\u50cb
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051\u64d4\u6645\u4e6eYY/MM\u4e6f\u5071\u5041\u509e
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// ****************************************
function checkDateYYMMSys(obj,fmt){
	//alert("FunctionStart : checkDateYYMMSys_ymd");
	
    var	errFlg=0;
    var value=obj.value;
	var yy = value.substr(0,2);	//\u64ed
    var mm = value.substr(2,2);	//\u5be7
	//alert("yy="+yy+" mm="+mm);
    
	if(value!=""){
		//case "dateyymm":
		if (value.length==4){
			//\u64ed\u50e0\u50c3\u50e2\u50cb
			if ((yy<"00") || (yy>"99") || (isNaN(yy))){
				errFlg = 1;
			//\u5be7\u50e0\u50c3\u50e2\u50cb
			}else if ((mm<"01") || (mm>"12")){
				errFlg = 1;
			}

		}else{
			errFlg = 1;
		}
	}	

	if (errFlg==1){
		doErrorMessage(obj,id0006);
		return;
	}
	//alert("FunctionEnd : ");
	return;
}

// ****************************************
// JavaScript : \u64d4\u6645\u50e0\u50c3\u50e2\u50cb
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051\u64d4\u6645\u4e6eYYYY/MM/DD\u4e6f\u5071\u5041\u509e
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// ****************************************
function checkDateYYYYMMDDSys(obj,fmt){
	//alert("FunctionStart : checkDateYYYYMMDDSys_ymd "+ obj.value);
//	getNowDate("FunctionStart : checkDateYYYYMMDDSys_ymd;" + obj.value);
	//alert("obj.value="+obj.value);
	var	errFlg=0;
	var value=obj.value;
	//alert("yyyy="+yyyy+" mm="+mm+" dd="+dd);
	
	if(value!=""){
		//case "dateyyyymmdd":
		if (value.length==8){
			if ((value.substr(0,4)<"1950") || (value.substr(0,4)>"2049") || (isNaN(value.substr(0,4)))){
				errFlg = 1;
			}else if ((value.substr(4,2)<"01") || (value.substr(4,2)>"12")){
				errFlg = 1;
			}else{
				if ((value.substr(6,2)>="01") && (value.substr(6,2)<=getMonthLastDateSys(value.substr(0,4)+value.substr(4,2)))){
				}else{
					errFlg = 1;
				}
			}
		} else if(value.length==10){
			
			if ((value.substr(0,4)<"1950") || (value.substr(0,4)>"2049") || (isNaN(value.substr(0,4)))){
				errFlg = 1;
			} else if(value.substr(4,1)!="/") {
				errFlg = 1;
			} else if((value.substr(5,2)<"01") || (value.substr(5,2)>"12")){
				errFlg = 1;
			} else if(value.substr(7,1)!="/") {
				errFlg = 1;
			} else if((value.substr(8,2)>="01") && 
			  (value.substr(8,2)<=getMonthLastDateSys(value.substr(0,4)+value.substr(5,2)))){
			} else {
				errFlg = 1;
			}
			if(errFlg==0) {
				//obj.value=value.substr(0,4)+value.substr(5,2)+value.substr(8,2);
			}
		}  else{
			errFlg = 1;
		}

		if (errFlg==1){
			doErrorMessage(obj,id0006);
			return;
		}
	}
	return;
}			

// ****************************************
// JavaScript : \u64d4\u6645\u50e0\u50c3\u50e2\u50cb
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051\u64d4\u6645\u4e6eYYYY/MM\u4e6f\u5071\u5041\u509e
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// ****************************************
function checkDateYYYYMMSys(obj,fmt){
	//alert("FunctionStart : checkDateYYYYMMDDSys_ymd "+ obj.value);
//	getNowDate("FunctionStart : checkDateYYYYMMSys_ymd;" + obj.value);
	
	var	errFlg=0;
	var value=obj.value;
	var yyyy = value.substr(0,4);	//\u64ed
	var mm = value.substr(4,2);	//\u5be7
	
	if(value!=""){
		//case "dateyyyymm":
		if (value.length==6){
			if ((yyyy<"1950") || (yyyy>"2049") || (isNaN(yyyy))){
				//\u64ed\u50e0\u50c3\u50e2\u50cb
				errFlg = 1;
			}else if ((mm<"01") || (mm>"12")){
				errFlg = 1;
			}
		}else{
			errFlg = 1;
		}

		if (errFlg==1){
			doErrorMessage(obj,id0006);
			return;
		}
	}
	return;
}			

// ****************************************
// JavaScript : \u64d4\u6645\u50e0\u50c3\u50e2\u50cb
// 
// \u5ddc\u6395\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507avalue\u62a3\u5051\u64d4\u6645\u4e6eMM/DD\u4e6f\u5071\u5041\u509e
// \u5059\u5072\u50aa\u66d0\u5fb9\u505f\u5091\u5061\u4e05
// ****************************************
function checkDateMMDDSys(obj,fmt){
	//alert("FunctionStart : checkDateMMDDSys_ymd "+ obj.value);
//	getNowDate("FunctionStart : checkDateMMDDSys_ymd;" + obj.value);
	
	var	errFlg=0;
	var value=obj.value;
	var mm = value.substr(0,2);	//\u5be7
	var dd = value.substr(2,2);	//\u64d4
		
	if(value!=""){
		//case "datemmdd":
		if (value.length==4){
			//\u5be7\u50e0\u50c3\u50e2\u50cb
			if ((mm<"01") || (mm>"12")){
				errFlg = 1;
			}else{
				//\u64d4\u50e0\u50c3\u50e2\u50cb
				if ((dd>="01") && (dd<=getMonthLastDateMMDDSys(mm))){
					//\u64d4\u50e0\u50c3\u50e2\u50cbOK.
				}else{
					errFlg = 1;
				}
			}
		}else{
			errFlg = 1;
		}

		if (errFlg==1){
			doErrorMessage(obj,id0006);
			return;
		}
	}
	return;
}			
