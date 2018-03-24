// File Name : Format.js
// 
// LSAMJ Framework Client Control Script
// COPYRIGHT 2003-2003 TIS Inc.
// 
// Creation Date 	: 2003/12/25
// Modification Date: $Date: 2005/11/03 08:13:58 $
// Creator 			: YUTAKA YOSHIDA
//

// ****************************************
// JavaScript : \u50e5\u50c9\u50d7\u50e9\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5f35\u68df\u657e\u6395
// 
// \u50e5\u50c9\u50d7\u50e9\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5f35\u68df\u50aa\u61cf\u60c8\u62a3\u5071\u657e\u6290\u505f\u4e04
// \u61f3\u5fbe\u507a\u5f35\u68df\u50aa\u5c47\u5083\u5f0c\u505f\u5091\u5061\u4e05
// ****************************************
function jsDoFormatSys(obj) {
//	getNowDate("jsDoFormatSys()\u5950\u5dd2");	
	var res;
	switch (obj.fieldtype){
	case "number":
		if (obj.value=="" && obj.defaultvalue=="false") {
			res=obj.value;
		} else {
			res=jsFormatNumberSys(obj.value,obj.fmt);
		}
		break;
	case "money":				// \u65d5\u6087\u5f60\u4e05\u4efacommanumber\u50aa\u5dca\u68a1\u4e05
		if (obj.value=="" && obj.defaultvalue=="false") {
			res=obj.value;
		} else {
			res=jsFormatMoneySys(obj.value,obj.fmt);
		}
		break;
	case "commanumber":			// "money"\u507a\u6219\u50a2\u509d\u5075\u5dca\u68a1\u4e05
		if (obj.value=="" && obj.defaultvalue=="false") {
			res=obj.value;
		} else {
			res=jsFormatMoneySys(obj.value,obj.fmt);
		}
		break;
	case "dateyymmdd":
		res=jsFormatDateYYMMDDSys(obj.value);
		break;
	case "dateyymm":
		res=jsFormatDateYYMMSys(obj.value);
		break;
	case "dateyyyymmdd":
		res=jsFormatDateYYYYMMDDSys(obj.value);
		break;
	case "dateyyyymm":
		res=jsFormatDateYYYYMMSys(obj.value);
		break;
	case "datemmdd":
		res=jsFormatDateMMDDSys(obj.value);
		break;
	case "timehhmm":
		res=jsFormatTimeHHMMSys(obj.value);
		break;
	case "timehhmmss":
		res=jsFormatTimeHHMMSSSys(obj.value);
		break;
	case "capitalcode":
		res=jsFormatCapitalSys(obj.value);
		break;
	default:
		res=obj.value;
	}
	return((res=="")?"":res);
}


// ****************************************
// JavaScript : \u60a2\u62a3\u5a35\u5094\u5f35\u68df
// 
// \u60a2\u62a3\u5a35\u5094\u5f35\u68df\u50aa\u5cf4\u5074\u5044\u5091\u5061\u4e05
// ****************************************
function jsRndSys(type, num, position){
	switch (type){
	case "0":	// \u6117\u509d\u5e6a\u5070
		num=num-0.5;
		return num.toFixed(position);
	case "5":	// \u5dd0\u5e6a\u5c72\u64d6
		return num.toFixed(position);
	case "9":	// \u6117\u509d\u5fcb\u5058
		num=num+0.5;
		return num.toFixed(position);
	default:
		return num;
	}
}

// ****************************************
// JavaScript : \u60a2\u62a3\u50e8\u4e55\u50de\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9
// 
// \u60a2\u62a3\u50e8\u4e55\u50de\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5074\u5044\u5091\u5061\u4e05
// ****************************************
function jsFormatNumberSys(num,format) {
	var fmt;		//\u50fc\u50c5\u4e55\u5105\u50e2\u50e9	"\u60cd\u60a2\u6679.\u5f6b\u60a2\u6679\u60a2\u62a3"
	var number;		//\u64d6\u6921\u62a3		"\u60cd\u60a2\u6679.\u5f6b\u60a2\u6679\u60a2\u62a3"
	var num_bk;     //\u5f36\u5a5c\u62a3
	var	decilen;	//\u64d6\u6921\u62a3		\u5f6b\u60a2\u6679\u5bd8\u60a2
	var cnt;

	if (num=="" || num=="0") {
		num = "0";
		num_bk = num;
		//\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u4e02\u60cd\u60a2\u6679\u4e12\u5f6b\u60a2\u6679\u6117\u509d\u5f0c\u505f
	    fmt=format.split(".");	
	    if(0<fmt[1]){
	   		for (cnt=1; cnt<fmt[1]; cnt++)	
	    		num=num + "0";// \u5066\u507a\u5d4e\u6698\u5069\u5057\u4fbd\u50aa\u5a97\u5094\u509e
				//\u60cd\u60a2\u6679\u5072\u5f6b\u60a2\u6679\u50aa\u5be2\u5d0c
		return (num_bk + '.' + num);
		}	
		return num;	
	}else if (isNaN(num)) {
		return num;
	}else{
		//\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u4e02\u60cd\u60a2\u6679\u4e12\u5f6b\u60a2\u6679\u6117\u509d\u5f0c\u505f
		fmt=format.split(".");
		//\u5f6b\u60a2\u5bd8\u5041\u5086\u509f\u5e2a\u4e04\u5041\u5086\u509f\u6698\u50aa\u5dd0\u5e6a\u5c72\u64d6
// \u4eed 2003/11/26 YUTAKA YOSHIDA \u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5bd8\u60a2\u5062\u509f\u61f3\u58b3\u4e05
		num = String(jsRndSys("5", Number(num), Number(fmt[1])))
		//\u64d6\u6921\u62a3\u4e02\u60cd\u60a2\u6679\u4e12\u5f6b\u60a2\u6679\u6117\u509d\u5f0c\u505f
		number=num.split(".");
		//\u5f6b\u60a2\u6679\u50db\u5118\u5a97\u66c7\u5ee4
		if (number[1]!="" && number[1]!=null){
			decilen=parseInt(number[1].length);
			if (decilen<fmt[1])
				for (cnt=0; cnt<fmt[1]-decilen; cnt++)	number[1]=number[1] + "0";
		}
		else
			if (fmt[1]!=0)
				for (cnt=1,number[1]="0"; cnt<fmt[1]; cnt++)	number[1]=number[1] + "0";
		//\u60cd\u60a2\u6679\u5072\u5f6b\u60a2\u6679\u50aa\u5be2\u5d0c
		if (number[1]!="" && number[1]!=null)
			return (number[0] + '.' + number[1]);
		else
			return (number[0]);
	}
}

// ****************************************
// JavaScript : \u60a2\u62a3\u50e8\u4e55\u50de\u507a\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9
// 
// \u60a2\u62a3\u50e8\u4e55\u50de\u507a\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5074\u5044\u5091\u5061\u4e05
// ****************************************
function jsUnformatNumberSys(val) {
	if(val=="")	return "";
	else	return val.replace(/,/g,"");
}

// ****************************************
// JavaScript : \u6360\u58ff\u50e8\u4e55\u50de\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9
// 
// \u6360\u58ff\u50e8\u4e55\u50de\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5074\u5044\u5091\u5061\u4e05
// ****************************************
function jsFormatMoneySys(num,format) {
	var fmt;				//\u50fc\u50c5\u4e55\u5105\u50e2\u50e9	"\u60cd\u60a2\u6679.\u5f6b\u60a2\u6679\u60a2\u62a3"
	var number;				//\u64d6\u6921\u62a3		"\u60cd\u60a2\u6679.\u5f6b\u60a2\u6679\u60a2\u62a3"
	var num_bk;             //\u5f36\u5a5c\u62a3
	var	decilen;			//\u64d6\u6921\u62a3		\u5f6b\u60a2\u6679\u5bd8\u60a2
	var tmpStr1,tmpStr2;	//\u64d6\u6921\u62a3		\u60cd\u60a2\u6679\u50c7\u511e\u5105\u66c7\u5ee4
	var cnt;
	var strNotZero;

if (num=="" || num=="0") {
		num = "0";
		num_bk = num;
		//\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u4e02\u60cd\u60a2\u6679\u4e12\u5f6b\u60a2\u6679\u6117\u509d\u5f0c\u505f
	    fmt=format.split(".");	
	    if(0<fmt[1]){
	   		for (cnt=1; cnt<fmt[1]; cnt++)	
	    		num=num + "0";// \u5066\u507a\u5d4e\u6698\u5069\u5057\u4fbd\u50aa\u5a97\u5094\u509e
				//\u60cd\u60a2\u6679\u5072\u5f6b\u60a2\u6679\u50aa\u5be2\u5d0c
		return (num_bk + '.' + num);
		}	
		return num;	
	}else if (isNaN(num)) {
		return num;
	}else{
		//\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u4e02\u60cd\u60a2\u6679\u4e12\u5f6b\u60a2\u6679\u6117\u509d\u5f0c\u505f
		fmt=format.split(".");
		if (fmt[1].substr(1,1).toUpperCase()=="Z"){
			strNotZero = fmt[1];
			fmt[1] = fmt[1].length;
		}else	strNotZero = "";
		//\u5f6b\u60a2\u5bd8\u5041\u5086\u509f\u5e2a\u4e04\u5041\u5086\u509f\u6698\u50aa\u5dd0\u5e6a\u5c72\u64d6
// \u4eed 2003/11/26 YUTAKA YOSHIDA \u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5bd8\u60a2\u5062\u509f\u61f3\u58b3\u4e05
// 		num = String(jsRndSys("5", Number(num), Number(fmt[1])+1))
		num = String(jsRndSys("5", Number(num), Number(fmt[1])))
		//\u64d6\u6921\u62a3\u4e02\u60cd\u60a2\u6679\u4e12\u5f6b\u60a2\u6679\u6117\u509d\u5f0c\u505f
		number=num.split(".");
		//\u60cd\u60a2\u6679\u50c7\u511e\u5105\u66c7\u5ee4
		tmpStr1 = number[0];
		while (tmpStr1 != (tmpStr2 = tmpStr1.replace(/^([+-]?\d+)(\d\d\d)/,"$1,$2")))	tmpStr1 = tmpStr2;
		//\u5f6b\u60a2\u6679\u50db\u5118\u5a97\u66c7\u5ee4
		if (number[1]!="" && number[1]!=null){
			if (strNotZero == ""){
				decilen=parseInt(number[1].length);
				if (decilen<fmt[1])
					for (cnt=0; cnt<fmt[1]-decilen; cnt++)	number[1]=number[1] + "0";
			}
			else
				while(number[1].substr(number[1].length-1,1) == "0")	number[1] = number[1].substr(0,number[1].length-1)
		}
		else
			if (strNotZero == "")
				if (fmt[1]!=0)
					for (cnt=1,number[1]="0"; cnt<fmt[1]; cnt++)	number[1]=number[1] + "0";
		//\u60cd\u60a2\u6679\u5072\u5f6b\u60a2\u6679\u50aa\u5be2\u5d0c
		if (number[1]!="" && number[1]!=null)
			return (tmpStr1 + '.' + number[1]);
		else
			return (tmpStr1);
	}
}

// ****************************************
// JavaScript : \u6360\u58ff\u50e8\u4e55\u50de\u507a\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9
// 
// \u6360\u58ff\u50e8\u4e55\u50de\u507a\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5074\u5044\u5091\u5061\u4e05
// ****************************************
function jsUnformatMoneySys(val) {
	if(val=="")	return "";
	else	return val.replace(/,/g,"");
}

/************************************************************/
/*	\u64ed\u4fc2\u5bd8\u58d4(YY\u4efaYYYY)															*/
/*	autor: masuda																	*/
/*	2003/08/14																	*/
/*	IN		\u64d4\u6645(YY)																*/
/*	OUT		4\u5bd8\u58d4\u5d6a\u5092\u64d4\u6645(YYYY)											*/
/************************************************************/
function jsYear4Sys(year2){
	var returnNum;

	if(year2 != ""){
		// YY\u5075\u509b\u506d\u50702000\u64ed\u6219\u50501900\u64ed\u6219\u5050\u657e\u6395\u5061\u509e
		if( parseInt(year2) < 50 ){
			returnNum = "20" + year2;
		}else{
			returnNum = "19" + year2;
		}
	}else{
		// \u62a3\u5051\u5837\u5052\u6409\u505d\u509f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u66c7\u5ee4\u50aa\u5cf4\u50a2\u5074\u5044
		returnNum = year2;
	}
	return returnNum;
}


/************************************************************/
/*	6\u5bd8\u64d4\u6645\u4efa\u64ed4\u5bd8\u58d4\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9(YY/MM/DD\u4efaYYYYMMDD)			*/
/*	autor: masuda																	*/
/*	2003/08/14																	*/
/*	IN		\u64d4\u6645(YY/MM/DD)													*/
/*	OUT		\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5d6a\u5092\u64d4\u6645(YYYYMMDD)							*/
/************************************************************/
function jsUnformatDate6Sys(date){
	var returnNum;
	var date8;
	
	if( date != "" ){
		// YY/MM/DD\u4efaYYYY/MM/DD \u66c4\u59fa
		date8 = jsYear4Sys(date.substr(0, 2)) + date.substr(2, 6);
		// \u50d7\u5114\u50e2\u50d4\u510f\u50aa\u5f4d\u5ac0
		returnNum = jsUnformatDateSys(date8);
	}else{
		// \u62a3\u5051\u5837\u5052\u6409\u505d\u509f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u50a2\u5074\u5044
		returnNum = date;
	}
	return returnNum;
}



/************************************************************/
/*	4\u5bd8\u64d4\u6645\u4efa\u64ed4\u5bd8\u58d4\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9(YY/MM\u4efaYYYYMM)					*/
/*	autor: masuda																	*/
/*	2004/02/05																	*/
/*	IN		\u64d4\u6645(YY/MM)															*/
/*	OUT		\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5d6a\u5092\u64d4\u6645(YYYYMM)								*/
/************************************************************/
function jsUnformatDate4Sys(date){
	var returnNum;
	var date6;
	
	if( date != "" ){
		// YY/MM\u4efaYYYY/MM \u66c4\u59fa
		date6 = jsYear4Sys(date.substr(0, 2)) + date.substr(2, 3);
		// \u50d7\u5114\u50e2\u50d4\u510f\u50aa\u5f4d\u5ac0
		returnNum = jsUnformatDateSys(date6);
	}else{
		// \u62a3\u5051\u5837\u5052\u6409\u505d\u509f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u50a2\u5074\u5044
		returnNum = date;
	}
	return returnNum;
}


// ************************************************************
// MMDD\u64d4\u6645\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u4e6eMMDD\u4efaMM/DD\u4e6f
// autor: Takaaki Tanaka
// 2004/08/10
// ************************************************************
function jsFormatDateMMDDSys(date) {
	//alert("FunctionStart : jsFormatDateMMDDSys_ymd");
	
	var returnDate;
	
	if(date==""){
		returnDate = "";
	}else if ((date.length == 4) && (date.match(/\d+/))){
		// \u64d4\u6645\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5046
		// MMDD\u4efaMM/DD
		returnDate = date.replace(/(\d\d)(\d\d)/,"$1\/$2");
	}else{
		// \u62a3\u5051\u6644\u60d3\u4e04\u5091\u5068\u507c\u5837\u5052\u6409\u505d\u509f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u50a2\u5074\u5044
		returnDate =  date;
	}
	return returnDate;
}

/************************************************************/
/*	8\u5bd8\u64d4\u6645\u4efa6\u5bd8\u64d4\u6645\u50fc\u50c5\u4e55\u5105\u50e2\u50e9(YYYYMMDD\u4efaYY/MM/DD)				*/
/*																					*/
/*	autor: masuda																	*/
/*	2003/08/14																	*/
/*	IN		\u64d4\u6645(YYYYMMDD)													*/
/*	OUT		\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5d6a\u5092\u64d4\u6645(YY/MM/DD)								*/
/************************************************************/
function jsFormatDate8toDate6Sys(date){
	var returnNum;
	var strFmt;
	
	if( date != "" ){
		// \u64d4\u6645\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5046
		// YYYYMMDD\u4efaYYMMDD
		strFmt = date.substr(2, 7);
		// YYMMDD\u4efaYY/MM/DD
		returnNum = jsFormat6DateSys(strFmt);
	}else{
		// \u62a3\u5051\u5837\u5052\u6409\u505d\u509f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u50a2\u5074\u5044
		returnNum = date;
	}
	return returnNum;
}

// ************************************************************
// YYYYMMDD\u64d4\u6645\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u4e6eYYYYMMDD\u4efaYYYY/MM/DD\u4e6f
// autor: Takaaki Tanaka
// 2004/08/10
// ************************************************************
function jsFormatDateYYYYMMDDSys(date) {
	//alert("FunctionStart : jsFormatDateYYYYMMDDSys_ymd");
	
	var returnDate;
	
	if(date==""){
		returnDate = "";
	}else if ((date.length == 8) && (date.match(/\d+/))){
		// \u64d4\u6645\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5046
		// YYYYMMDD\u4efaYYYY/MM/DD
		returnDate = date.replace(/(\d\d\d\d)(\d\d)(\d\d)/,"$1\/$2\/$3");
	}else{
		// \u62a3\u5051\u6644\u60d3\u4e04\u5091\u5068\u507c\u5837\u5052\u6409\u505d\u509f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u50a2\u5074\u5044
		returnDate =  date;
	}
	return returnDate;
}

// ************************************************************
// YYYYMM\u64d4\u6645\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u4e6eYYYYMM\u4efaYYYY/MM\u4e6f
// autor: Takaaki Tanaka
// 2004/08/10
// ************************************************************
function jsFormatDateYYYYMMSys(date) {
	//alert("FunctionStart : jsFormatDateYYYYMMSys_ymd");
	
	var returnDate;
	
	if(date==""){
		returnDate = "";
	}else if ((date.length == 6) && (date.match(/\d+/))){
		// \u64d4\u6645\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5046
		// YYYYMM\u4efaYYYY/MM
		returnDate = date.replace(/(\d\d\d\d)(\d\d)/,"$1\/$2");
	}else{
		// \u62a3\u5051\u6644\u60d3\u4e04\u5091\u5068\u507c\u5837\u5052\u6409\u505d\u509f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u50a2\u5074\u5044
		returnDate =  date;
	}
	return returnDate;
}

/************************************************************/
/*	\u4fc7\u5bd8\u64d4\u6645\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u4e6eYYMMDD\u4efaYY/MM/DD\u4e6f								*/
/*	autor: masuda																	*/
/*	2003/08/14																	*/
/*	IN		\u64d4\u6645(YY/MM/DD)													*/
/*	OUT		\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5d6a\u5092\u64d4\u6645(YY/MM/DD)		
/*  \u65d5\u6087\u5f60\u4efajsFormatDateYYMMDDSys()\u50aa\u5dca\u68a1\u5061\u509e						*/
/************************************************************/
function jsFormat6DateSys(date) {
	var returnNum;
	
	if(date==""){
		returnNum = "";
	}else if ((date.length == 6) && (date.match(/\d+/))){
		// \u64d4\u6645\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5046
		// YYMMDD\u4efaYY/MM/DD		
		returnNum = date.replace(/(\d\d)(\d\d)(\d\d)/,"$1\/$2\/$3");
	}else{
		// \u62a3\u5051\u6644\u60d3\u4e04\u5091\u5068\u507c\u5837\u5052\u6409\u505d\u509f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u50a2\u5074\u5044
		returnNum =  date;
	}
	return returnNum;
} 

// ************************************************************
// \u4fc7\u5bd8\u64d4\u6645\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u4e6eYYMMDD\u4efaYY/MM/DD\u4e6f
// autor: Takaaki Tanaka
// 2004/07/31
// ************************************************************
function jsFormatDateYYMMDDSys(date) {
	//alert("FunctionStart : jsFormatDateYYMMDDSys_ymd");
	
	var returnDate;
	
	if(date==""){
		returnDate = "";
	}else if ((date.length == 6) && (date.match(/\d+/))){
		// \u64d4\u6645\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5046
		// YYMMDD\u4efaYY/MM/DD
		returnDate = date.replace(/(\d\d)(\d\d)(\d\d)/,"$1\/$2\/$3");
	}else{
		// \u62a3\u5051\u6644\u60d3\u4e04\u5091\u5068\u507c\u5837\u5052\u6409\u505d\u509f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u50a2\u5074\u5044
		returnDate =  date;
	}
	return returnDate;
}

// ****************************************
// JavaScript : \u64d4\u6645\u50e8\u4e55\u50de(\u4fc2\u5bd8:YYMM)\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9
// 
// \u64d4\u6645\u50e8\u4e55\u50de(\u4fc2\u5bd8:YYMM)\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5074\u5044\u5091\u5061\u4e05
// \u65d5\u6087\u5f60\u4efajsFormatDateYYMMSys()\u50aa\u5dca\u68a1\u5061\u509e
// ****************************************
function jsFormat4DateSys(val) {
	if(val=="")	return "";
	else
		if ((val.length==4) && (val.match(/\d+/)))	return val.replace(/(\d\d)(\d\d)/,"$1\/$2");
		else	return val;
} 

// ****************************************
// \u4fc2\u5bd8\u64d4\u6645\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u4e6eYYMM\u4efaYY/MM\u4e6f
// autor: Takaaki Tanaka
// 2004/07/31
// ****************************************
function jsFormatDateYYMMSys(val) {
	//alert("FunctionStart : jsFormatDateYYMMSys_ymd");
	
	if(val==""){
		return "";
	}else{
		if((val.length==4) && (val.match(/\d+/))){
			return val.replace(/(\d\d)(\d\d)/,"$1\/$2");
		}else{
			return val;
		}
	}
}

/************************************************************/
/*	\u64d4\u6645\u50e8\u4e55\u50de\u507a\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u4e6e\u50d7\u5114\u50e2\u50d4\u510f\u5f4d\u5ac0\u4e6f								*/
/*	autor: masuda																	*/
/*	2003/08/14																	*/
/*	IN		\u64d4\u6645(YY/MM/DD or YYYY/MM/DD)								*/
/*	OUT		\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5d6a\u5092\u64d4\u6645(YYMMDD or YYYYMMDD)			*/
/************************************************************/
function jsUnformatDateSys(date) {
	var date;
	var returnNum;

	if(date == ""){
		returnNum = "";
	}else{
		returnNum = date.replace(/\//g,"");
	}
	return returnNum;
}


// **********************************************
// JavaScript : field\u61cf\u60c8\u5051date4, date6\u507a\u5fdc\u5d0c
//				\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5f35\u68df\u5c7b\u5075\u60e3\u694b4\u5bd8\u58d4
// IN : YY/MM/DD or YY/MM
// OUT : YYYYMMDD or YYYYMM
// autor : \u61b9\u63f7\u6941
// 2004/02/04
// **********************************************
function doDateUnformat() {
//	\u50e5\u50d7\u50e9
//	alert("doDateUnformat()\u5950\u5dd2");

	var doc = window.document.forms[0];
	var i, ii;
	var fieldtype;
	var fieldValue;
	var returnValue;
	
	ii = doc.all.tags("INPUT").length;
	
	for(i=0; i<ii; i++){
		fieldtype = doc.elements(i).fieldtype;
		
		if(fieldtype != null){
			// field\u61cf\u60c8\u5051"dateyymmdd"\u507a\u5fdc\u5d0c
			if(fieldtype == "dateyymmdd"){
				fieldValue = doc.elements(i).value;
				// \u5d4d\u5843\u507a\u50d7\u5101\u4e55\u50d7\u50aa\u5f4d\u5ac0
				fieldValue = Trim(fieldValue);
		
				// YY/MM/DD\u4efaYYYYMMDD \u66c4\u59fa
				returnValue = jsUnformatDate6Sys(fieldValue);
				// \u50fc\u50bf\u4e55\u5116\u50ea\u507avalue\u62a3\u5075\u50d9\u50e2\u50e9
				doc.elements(i).value = returnValue;
			
			// field\u61cf\u60c8\u5051"dateyymm"\u507a\u5fdc\u5d0c
			}else if(fieldtype == "dateyymm"){
				fieldValue = doc.elements(i).value;
				// \u5d4d\u5843\u507a\u50d7\u5101\u4e55\u50d7\u50aa\u5f4d\u5ac0
				fieldValue = Trim(fieldValue);

				// YY/MM\u4efaYYYYMM \u66c4\u59fa
				returnValue = jsUnformatDate4Sys(fieldValue);
				// \u50fc\u50bf\u4e55\u5116\u50ea\u507avalue\u62a3\u5075\u50d9\u50e2\u50e9
				doc.elements(i).value = returnValue;
			}
		}
	}
}



/************************************************************/
/*	Trim\u50e3\u4e55\u5116\u4e6e\u5d4d\u5843\u507a\u50d7\u5101\u4e55\u50d7\u50aa\u50c7\u50e2\u50e9\u4e6f					*/
/*	Trim whitespace from left and right sides of s.
/*															*/
/************************************************************/
function Trim(s) {
	return s.replace( /^\s*/, "" ).replace( /\s*$/, "" );
}


// ****************************************
// JavaScript : \u5e2a\u5a2b\u50e8\u4e55\u50de\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9
// 
// \u5e2a\u5a2b\u50e8\u4e55\u50de\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5074\u5044\u5091\u5061\u4e05
// ****************************************
function jsFormatTimeSys(val) {
	if(val=="") return "";
	else {
		var theMS = Date.parse("1/1/80 "+val);
		var theDate = new Date(theMS); 
		return theDate.toLocaleString().slice(10,23);
	}
}

// ****************************************
// JavaScript : \u5e2a\u5a2b\u50e8\u4e55\u50de\u507a\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9
// 
// \u5e2a\u5a2b\u50e8\u4e55\u50de\u507a\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5074\u5044\u5091\u5061\u4e05
// ****************************************
function jsUnformatTimeSys(val) {

	var returnTime;

	if(val == ""){
		return val;
	}else{
		returnTime = val.replace(/\:/g,"");
	}
	return returnTime;
}


// ****************************************
// JavaScript : \u50e5\u50c9\u50d7\u50e9\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5f35\u68df
// 
// \u50e5\u50c9\u50d7\u50e9\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5f35\u68df\u50aa\u5cf4\u5074\u5044\u5091\u5061\u4e05
// ****************************************
function jsDoUnformatSys(obj) {
	var res;
	switch (obj.fieldtype){
	case "number":
		res=jsUnformatNumberSys(obj.value);
		break;
	case "money":			// \u65d5\u6087\u5f60\u4e05\u4efacommanumber\u50aa\u5dca\u68a1\u4e05
		res=jsUnformatMoneySys(obj.value);
		break;
	case "commanumber":		// "money"\u507a\u6219\u50a2\u509d\u5075\u5dca\u68a1\u4e05
		res=jsUnformatMoneySys(obj.value);
		break;
	case "dateyymmdd":
	case "dateyymm":
	case "dateyyyymmdd":
	case "dateyyyymm":
	case "datemmdd":
		res=jsUnformatDateSys(obj.value);
		break;
	case "timehhmm":
	case "timehhmmss":
		res=jsUnformatTimeSys(obj.value);
		break;
	default:
		res=obj.value;
	}
	return((res=="")?"":res);
}

// ****************************************
// JavaScript : \u5f6b\u66a5\u5e24\u50e8\u4e55\u50de\u507a\u621d\u66a5\u5e24\u66c4\u59fa
// ****************************************
function jsFormatCapitalSys(value) {
	return value.toUpperCase();
}

// **********************************************
// jsFormatTimeHHMMSys
// JavaScript : \u5e2a\u5a2b\u661e\u5e35\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5f35\u68df\u4e6eHHMM\u4e6f
// autor : Takaaki Tanaka
// 2004/08/11
// **********************************************
function jsFormatTimeHHMMSys(value){
	
	var returnTime;
	
	if(value == ""){
		return value;
	}else if((value.length == 4) && (value.match(/\d+/))){
		returnTime = value.replace(/(\d\d)(\d\d)/,"$1:$2");
	}else{
		// \u62a3\u5051\u6644\u60d3\u4e04\u5091\u5068\u507c\u5837\u5052\u6409\u505d\u509f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u50a2\u5074\u5044
		returnTime =  value;
	}
	return returnTime;
}

// **********************************************
// jsFormatTimeHHMMSSSys
// JavaScript : \u5e2a\u5a2b\u661e\u5e35\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5f35\u68df\u4e6eHHMMSS\u4e6f
// autor : Takaaki Tanaka
// 2004/08/11
// **********************************************
function jsFormatTimeHHMMSSSys(value){
	
	var returnTime;
	
	if(value == ""){
		return value;
	}else if((value.length == 6) && (value.match(/\d+/))){
		returnTime = value.replace(/(\d\d)(\d\d)(\d\d)/,"$1:$2:$3");
	}else{
		// \u62a3\u5051\u6644\u60d3\u4e04\u5091\u5068\u507c\u5837\u5052\u6409\u505d\u509f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u50a2\u5074\u5044
		returnTime =  value;
	}
	return returnTime;
}
