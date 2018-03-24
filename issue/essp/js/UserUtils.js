// File Name : UserUtils.js
//
// CommerceCube Framework Client Control Script
// COPYRIGHT 2003-2004 TIS Inc.
//
// Creation Date 	: 2003/12/25
// Modification Date: $Date: 2005/11/03 08:13:58 $
// Creator 			: YUTAKA YOSHIDA, RYO MASUDA
//


// ************************************************* //
// ************************************************* //
// \u5110\u4e55\u50d3\u5051\u5804\u5e46\u505f\u5070\u5dca\u68a1\u5061\u509e\u5a2d\u60a2\u50aa\u597f\u64fa\u5061\u509ejs\u50fc\u50bd\u50c0\u5116\u5071\u5061\u4e05//
// ************************************************* //
// ************************************************* //

// ****************************************
// JavaScript : \u50d2\u50fd\u5108\u50e2\u50e9\u50aa\u5cf4\u5074\u5046\u5a2d\u60a2
//
// FooterButtonTag\u507aonclick\u61cf\u60c8\u5075\u5a70\u5f0e\u5061\u509e\u4e05
// \u5091\u5068\u4e04\u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u50c9\u4e55\u58b4\u58d3\u5e2a\u5075\u5095\u5c47\u5083\u5f0c\u505d\u509f\u509e\u4e05
//
// autor ryo masuda
// ****************************************
function submitForm(currForm) {
    var target=null;
    //alert("submit_flug="+submit_flug);
    if(!submit_flug) {
        try {
			target = getPreFocusObject();
			if(target!=null){
				if(doPreFocusFieldCheck(target) == "false") {
					return false;
				}
			}
        } catch(e) {
        }

		if(!doReqCheck()){
			return false;
		}

        // \u50df\u50c0\u50be\u5118\u50cc\u57f5\u62b2\u5fe3\u66ec\u611d\u6395
		setDialogPosition(currForm);

        submit_flug = true;
        // \u590b\u67fa\u60c2\u5c7c\u68b7\u60c2
        doOpenWaitFillter();
        // \u50d9\u5117\u50cb\u50e9\u50de\u50cc\u507a\u67cd\u5ca0\u58d4
	    //var objs = document.all.tags("SELECT");
	    //for(i=0;i<objs.length;i++) {
	    //    objs(i).disabled = true;
	    //}

        return true;
    }
	return false;
}

function doSubmit(currForm) {
//	alert("doSubmit()\u5950\u5dd2 : " + obj.name);

	//var doc = window.document.forms[0];
	var target;
	var url = null;
	var suffix = null;
	var context = null;

    alert("submit_flug="+submit_flug);
	// \u50d2\u50fd\u5108\u50e2\u50e9\u62de\u507a\u5fdc\u5d0c\u507c\u5f35\u68df\u50aa\u5cf4\u50a2\u5074\u5044
	if(!submit_flug) {
		//	\u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u50c9\u4e55\u5e5a\u5cf4\u5e2a\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u5051\u6449\u5068\u506d\u5070\u5044\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u50aa\u5ea2\u647c
		target = getPreFocusObject();
//		getNowDate("\u6348\u615c\u507a\u5d01\u681a\u67e4\u4e17 " + target.name);

		// \u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507a\u5ea2\u647c\u5075\u5e50\u6515\u505f\u5068\u5fdc\u5d0c\u507c\u6268\u5d01\u681a\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u50a2\u5074\u5044
		if(target!=null){
			// \u6348\u615c\u507a\u5d01\u681a\u5075\u61f3\u505f\u5070\u6268\u5d01\u681a\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u5046
			if(doPreFocusFieldCheck(target) == "false"){
				// \u651a\u5ba8\u6013\u50aa\u66c4\u5ccf\u505f\u4e04\u6156\u6230\u5fec\u61fa\u5075\u5061\u509e
//				obj.style.background='#00FFFF';
				obj.select();
				return false;
			}
		}

		// \u5110\u4e55\u50d3\u50d7\u50cb\u5115\u50fe\u50e9\u68a1\u5a2d\u60a2\u5c47\u5083\u5f0c\u505f
		// \u6161\u590b\u67fa\u5adf\u6360\u5f35\u68df
		var usasRet = userScriptAllScreen();
		if (usasRet == STOP_SUBMIT) {
			// \u5f35\u68df\u5be2\u58e5\u5075\u509b\u509d\u50d2\u50fd\u5108\u50e2\u50e9\u62de\u5ded\u505f\u5068\u5044\u5fdc\u5d0c
            alert("STOP_SUBMIT 1");
			return false;
		}

		// \u590b\u67fa\u66bf\u5f35\u68df
		try{
			var usRet = userScript();
			if (usRet == STOP_SUBMIT) {
				// \u5f35\u68df\u5be2\u58e5\u5075\u509b\u509d\u50d2\u50fd\u5108\u50e2\u50e9\u62de\u5ded\u505f\u5068\u5044\u5fdc\u5d0c
                alert("STOP_SUBMIT 2");
				return false;
			}
		}catch(e){
			// \u5066\u507a\u5091\u5091\u5f35\u68df\u50aa\u5e5a\u5cf4\u5061\u509e
            alert("error STOP_SUBMIT 2");
            return false;
		}

		// \u50c4\u5114\u4e55\u5dc6\u50e0\u50c3\u50e2\u50cb
//		if(!doLeftErrorCheck()){
			// \u50c4\u5114\u4e55\u5dc6\u5051\u61da\u5d7c\u505f\u5068\u5fdc\u5d0c\u507c\u4e04\u5f35\u68df\u50aa\u61d5\u5cf4\u505f\u5074\u5044
//			return;
//		}

//	\u50e5\u50d7\u50e9
//		alert("reqcheck\u61cf\u60c8: " + obj.reqcheck);
		// \u6601\u6075\u5d01\u681a\u50e0\u50c3\u50e2\u50cb
		//if(obj.reqcheck=="true"){
			if(!doReqCheck()){
//				getNowDate("\u6601\u6075\u5d01\u681a\u50c4\u5114\u4e55\u5041\u509d");
				// \u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50c4\u5114\u4e55\u5051\u5041\u506d\u5068\u5fdc\u5d0c\u507c\u4e04\u5f35\u68df\u50aa\u61d5\u5cf4\u505f\u5074\u5044
                alert("check error");
				return false;
			}
		//}

//	\u50e5\u50d7\u50e9 +++++++++++++++++++++++++++++++++++++++++++
//		getNowDate("\u50d2\u50fd\u5108\u50e2\u50e9\u505d\u509f\u5091\u505f\u5068\u4e6e\u50df\u5108\u4e55\u4e6f");
//		return;
//\u4e02++++++++++++++++++++++++++++++++++++++++++++++++++

		// \u6156\u6230\u50df\u50c0\u50be\u5118\u50cc\u661e\u5e35
		// No\u5051\u6156\u6230\u505d\u509f\u5068\u5fdc\u5d0c\u507c\u4e04\u5f35\u68df\u50aa\u61d5\u5cf4\u505f\u5074\u5044
		if((currForm.message!="") && (currForm.message!=null)) {
			if(!doCheckDialogMessage(currForm)) {
				// \u50fc\u50c5\u4e55\u50c7\u50d7\u5051\u6449\u5068\u506d\u5070\u5044\u5068\u5d01\u681a\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u6820\u5061
				if(target!=null){
					target.focus();
				}
				return false;
			}
		}else {
			// \u6156\u6230\u50df\u50c0\u50be\u5118\u50cc\u50aa\u661e\u5e35\u505f\u5074\u5044\u5fdc\u5d0c
			// \u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u50c9\u4e55\u58b4\u58d3\u50c0\u5100\u511e\u50e9\u5074\u509c\u4e04\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5046
			if(event.type=="keydown"){
				// \u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5f35\u68df\u50aa\u5cf4\u5046
				if(target!=null){
//					getNowDate("\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5044\u5091\u5061\u4e05");
					target.value=jsDoFormatSys(target);
				}
			}
		}

		// \u5110\u4e55\u50d3\u50d7\u50cb\u5115\u50fe\u50e9\u68a1\u5a2d\u60a2\u5c47\u5083\u5f0c\u505f
		// \u6161\u590b\u67fa\u5adf\u6360\u5f35\u68df
		var upssasRet = userPreSubmitScriptAllScreen();
		if (upssasRet == STOP_SUBMIT) {
			// \u5f35\u68df\u5be2\u58e5\u5075\u509b\u509d\u50d2\u50fd\u5108\u50e2\u50e9\u62de\u5ded\u505f\u5068\u5044\u5fdc\u5d0c
			return false;
		}

		// \u590b\u67fa\u66bf\u5f35\u68df
		try{
			var upssRet = userPreSubmitScript();
			if (upssRet == STOP_SUBMIT) {
				// \u5f35\u68df\u5be2\u58e5\u5075\u509b\u509d\u50d2\u50fd\u5108\u50e2\u50e9\u62de\u5ded\u505f\u5068\u5044\u5fdc\u5d0c
				return false;
			}
		}catch(e){
			// \u5066\u507a\u5091\u5091\u5f35\u68df\u50aa\u5e5a\u5cf4\u5061\u509e
            return false;
		}

		// \u50d2\u50fd\u5108\u50e2\u50e9\u50fc\u5114\u50cc\u50aa\u68eb\u5070\u509e
//	\u50e5\u50d7\u50e9
//		alert("submit_flg: " + true);
		submit_flug = true;

		// \u50df\u50c0\u50be\u5118\u50cc\u57f5\u62b2\u5fe3\u66ec\u611d\u6395
		setDialogPosition(currForm);

		// \u590b\u67fa\u60c2\u5c7c\u68b7\u60c2
		doOpenWaitFillter();

//		alert(doc.action + "\u5075\u61f3\u505f\u5070\u50d2\u50fd\u5108\u50e2\u50e9\u505f\u5091\u5061\u4e05");
//		getNowDate(doc.action + "\u5075\u61f3\u505f\u5070\u50d2\u50fd\u5108\u50e2\u50e9\u505f\u5091\u5061\u4e05");
		// \u50d2\u50fd\u5108\u50e2\u50e9
		currForm.submit();

		// \u50d9\u5117\u50cb\u50e9\u50de\u50cc\u507a\u67cd\u5ca0\u58d4
	    //var objs = document.all.tags("SELECT");
	    //for(i=0;i<objs.length;i++) {
	    //    objs(i).disabled = true;
	    //}

        return true;

	}
}


// ******************************************
// JavaScript : \u5110\u4e55\u50d3\u6488\u5e3a\u507a\u5f35\u68df\u50aa\u5cf4\u5046\u580a\u507a\u5a2d\u60a2
//
// \u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u5103\u50de\u511e\u58b4\u58d3\u5e2a\u5075\u61b1\u509e\u5f35\u68df\u4e05
// \u6601\u6075\u5d01\u681a\u50e0\u50c3\u50e2\u50cb\u507a\u615c\u5075\u5c47\u5083\u5f0c\u505d\u509f\u509e\u4e05
// \u6161\u590b\u67fa\u5075\u63d4\u68a1\u505d\u509f\u509e\u4e05
//
// autor ryo masuda
// 2004/08/25
// ******************************************
function userScriptAllScreen(){
//	alert("userScriptAllScreen()\u5e5a\u5cf4");
}


// ******************************************
// JavaScript : \u5110\u4e55\u50d3\u6488\u5e3a\u507a\u5f35\u68df\u50aa\u5cf4\u5046\u580a\u507a\u5a2d\u60a2
//
// \u50d2\u50fd\u5108\u50e2\u50e9\u507a\u6348\u615c\u5075\u61b1\u509e\u5f35\u68df\u4e05
// \u6156\u6230\u50df\u50c0\u50be\u5118\u50cc\u661e\u5e35\u5c7b\u5075\u5c47\u5083\u5f0c\u505d\u509f\u509e\u4e05
// \u6161\u590b\u67fa\u5075\u63d4\u68a1\u505d\u509f\u509e\u4e05
//
// autor ryo masuda
// 2004/08/25
// ******************************************
function userPreSubmitScriptAllScreen(){
//	alert("userPreSubmitScriptAllScreen()\u5e5a\u5cf4");
}


// ******************************************
// JavaScript : ForkAction\u66fd\u5e43\u5071\u5dca\u68a1\u4e05
// \u5837\u60a2\u507a\u5fe3\u66ec\u5050\u509c\u50cb\u50c4\u5115\u50d7\u50e9\u5115\u511e\u50cc\u50aa\u60d7\u60c9\u505f\u4e04\u50df\u50c0\u50be\u5118\u50cc\u50aa\u5950\u5054\u4e05
//
// \u590b\u67faFork\u5e2a\u5075\u5d8c\u60c9\u5061\u509e\u597aDialogOpener.jsp\u64aa\u5071\u5e5a\u61b0\u5061\u509e
// JavaScript\u64aa\u5071\u5dca\u68a1\u4e05
//
// \u5837\u60a2
// myQueryString:	\u50d2\u4e55\u50f6\u508a\u61b2\u6023\u505f\u5068\u5044\u50cb\u50c4\u5115\u50d7\u50e9\u5115\u511e\u50cc
// superForms:		Fork\u5c26\u590b\u67fa\u507aform
// forkActionPath:	\u57ec\u5be9\u5071\u5e5a\u61b0\u505f\u5068ForkAction\u507a\u50f7\u50d7
// dialogOption:	\u50df\u50c0\u50be\u5118\u50cc\u5075\u6409\u5061\u50c6\u50fe\u50d4\u5111\u511e
//
// \u6820\u509d\u62a3
// returnvalue:		\u50df\u50c0\u50be\u5118\u50cc\u5050\u509c\u507a\u6820\u509d\u62a3
//
// autor ryo masuda
// 2005/02/03
// ******************************************
function doOpenForkDialog(myQueryString, superForms, forkActionPath, dialogOption){
//	alert("doOpenForkDialog()\u5950\u5dd2");
	var action;
	var query;
	var servlet;
	var returnvalue;

	// Fork\u612d\u507aAction\u507a\u50f7\u50d7\u50aa\u5ea2\u647c
	action = "?forward_path=" + superForms.forward_path.value;
	// hidden\u50de\u50cc\u5075\u597f\u64fa\u505d\u509f\u5070\u5044\u509e\u50cb\u50c4\u5115\u50d7\u50e9\u5115\u511e\u50cc\u50aa\u5ea2\u647c
	query = superForms.querystring.value;

	// \u50cb\u50c4\u5115\u50d7\u50e9\u5115\u511e\u50cc\u50aa\u617b\u5092\u68eb\u5070\u509e
	myQueryString = action + "&" + myQueryString;

	if(query!=undefined ||query!="" || query!=null){
		myQueryString = myQueryString + "&" + query;
	}

	// GET\u507a\u5068\u5094\u4e04\u50c9\u510d\u50e2\u50d4\u510f\u50d0\u511e\u50e9\u5118\u4e55\u5116
	myQueryString += "&random=" + new Date().getTime() + Math.floor(Math.random() * 1000);
// \u50e5\u50d7\u50e9
//	alert("queryString: " + myquerystring);

	// ForkAction\u507a\u50f7\u50d7
	// hidden\u5d01\u681a\u5050\u509c\u50d0\u511e\u50e5\u50c9\u50d7\u50e9\u50f7\u50d7\u50aa\u5ea2\u647c
	var context = superForms.context_path.value;
	servlet = context + forkActionPath;
// \u50e5\u50d7\u50e9
//	alert("forkActionPath: " + servlet);

	// \u50df\u50c0\u50be\u5118\u50cc\u50c6\u4e55\u50fe\u511e
	returnvalue = window.showModelessDialog(servlet + myQueryString, window, dialogOption);

	// \u50df\u50c0\u50be\u5118\u50cc\u5050\u509c\u507a\u6820\u509d\u62a3
	return returnvalue;
}


// *******************************************************************
// JavaScript : \u50df\u50c0\u50be\u5118\u50cc\u50c6\u4e55\u50fe\u511e\u5e2a\u5075\u6601\u68ab\u5074\u50c6\u50fe\u50d4\u5111\u511e\u50aa\u60d7\u60c9
//
// showModalDialog, showModelessDialog\u507a\u50c6\u4e55\u50fe\u511e\u5e2a\u5075\u5dca\u68a1\u4e05
// \u590b\u67fa\u50d2\u50c0\u50d8\u6395\u5a8a\u5051CommerceCube\u5075\u5d0c\u50a2\u5074\u5044\u5fdc\u5d0c\u4e04\u5059\u507a\u5a2d\u60a2\u50aa\u5114\u50e2\u50fa\u511e\u50cc\u505f\u5068\u5a2d\u60a2\u50aa\u68a1\u5804
// \u5061\u509e\u5059\u5072\u5051\u6737\u5091\u505f\u5044\u4e05
//
// dialogDisplayHeight\u507c\u50df\u50c0\u50be\u5118\u50cc\u50aa\u62de\u58b0\u5075\u661e\u5e35\u505d\u5063\u509e\u5068\u5094\u507a\u5bc1\u5dbc\u5075\u5dca\u68a1\u5061\u509e\u4e05
//
// \u693a\u504a\u507d\u4e041024\u5003768\u50d2\u50c0\u50d8\u507a\u50df\u50c0\u50be\u5118\u50cc\u50aa\u661e\u5e35\u505d\u5063\u509e\u5fdc\u5d0c\u4e04
// \u50c2\u50bf\u511e\u50ea\u50c2\u58d3\u507a\u50de\u50d7\u50cb\u50f6\u4e55\u5051\u5840\u509f\u5070\u505f\u5091\u5046\u507a\u5071\u4e04
// \u5e5a\u5d7a\u507a\u50df\u50c0\u50be\u5118\u50cc\u507a\u5d05\u505d\u507c740px\u5075\u505f\u5074\u5057\u509f\u507d\u5074\u509c\u5074\u5044\u4e05
// \u5066\u507a\u5fdc\u5d0c\u4e04dialogDisplayHeight\u5075\u507c"768", dialogHeight\u5075\u507c"740"
// \u50aa\u5ddc\u6395\u5061\u509e\u4e05
//
// \u5837\u60a2
// dialogDisplayWidth:	\u50df\u50c0\u50be\u5118\u50cc\u661e\u5e35\u57f5\u62b2\u5bc1\u5dbc\u5075\u5dca\u68a1\u5061\u509e\u50df\u50c0\u50be\u5118\u50cc\u507a\u5d05\u505d
// dialogDisplayHeight:	\u50df\u50c0\u50be\u5118\u50cc\u661e\u5e35\u57f5\u62b2\u5bc1\u5dbc\u5075\u5dca\u68a1\u5061\u509e\u50df\u50c0\u50be\u5118\u50cc\u507a\u5d05\u505d
// dialogWidth:			\u5e5a\u5d7a\u5075\u661e\u5e35\u5061\u509e\u50df\u50c0\u50be\u5118\u50cc\u507a\u58b6\u6686
// dialogHeight:		\u5e5a\u5d7a\u5075\u661e\u5e35\u5061\u509e\u50df\u50c0\u50be\u5118\u50cc\u507a\u5d05\u505d
// status:				\u50d7\u50e5\u4e55\u50de\u50d7\u50f6\u4e55\u507a\u661e\u5e35\u4e5b\u65d5\u661e\u5e35\u4e6eyes/no\u4e6f
// help:				\u50ff\u5116\u50fe\u50be\u50c0\u50d0\u511e\u507a\u661e\u5e35\u4e5b\u65d5\u661e\u5e35\u4e6eyes/no\u4e6f
// scroll:				\u50d7\u50cb\u5118\u4e55\u5116\u5061\u509e\u4e5b\u505f\u5074\u5044\u4e6eyes/no\u4e6f
//
// \u6820\u509d\u62a3
// option:				\u60d7\u60c9\u505f\u5068\u50df\u50c0\u50be\u5118\u50cc\u50c6\u50fe\u50d4\u5111\u511e
//
// autor ryo masuda
// 2005/02/03
// *******************************************************************
function doCreateDialogOption(dialogDisplayWidth, dialogDisplayHeight, dialogWidth, dialogHeight, status, help, scroll){
//	alert("doCreateDialogOption()\u5950\u5dd2");
	var option;

	// \u590b\u67fa\u661e\u5e35\u57f5\u62b2\u507a\u5bdb\u6395
	var leftis = (screen.width - dialogDisplayWidth) / 2;
	var topis = (screen.height - dialogDisplayHeight) / 2;
	// \u50df\u50c0\u50be\u5118\u50cc\u50c6\u50fe\u50d4\u5111\u511e\u507a\u60d7\u60c9
	var dialogLeft = "dialogLeft:" + leftis + "px;";
	var dialogTop = "dialogTop:" + topis + "px;";
	var dialogHeight = "dialogHeight:" + dialogHeight + "px;";
	var dialogWidth = "dialogWidth:" + dialogWidth + "px;";
	var dialogOption = "status:" + status + ";help:" + help + ";scroll:" + scroll + ";";

	option = dialogLeft + dialogTop + dialogHeight + dialogWidth + dialogOption;
// \u50e5\u50d7\u50e9
//	alert("option: " + option);

	return option;
}


// *******************************************************************
// JavaScript : 1024\u590b\u67fa\u507a\u50df\u50c0\u50be\u5118\u50cc\u50c6\u4e55\u50fe\u511e\u5e2a\u5075\u6601\u68ab\u5074\u50c6\u50fe\u50d4\u5111\u511e\u60d7\u60c9
//
// showModalDialog, showModelessDialog\u507a\u50c6\u4e55\u50fe\u511e\u5e2a\u5075\u5dca\u68a1\u4e05
// CommerceCube\u5071\u6395\u5a8a\u5061\u509e1024\u5003768\u50d2\u50c0\u50d8\u507a\u50df\u50c0\u50be\u5118\u50cc\u50c6\u50fe\u50d4\u5111\u511e\u4e05
// \u50c2\u50bf\u511e\u50ea\u50c2\u58d3\u507a\u50de\u50d7\u50cb\u50f6\u4e55\u5051\u5840\u509f\u5070\u505f\u5091\u5046\u507a\u5071\u4e04
// \u5e5a\u5d7a\u507a\u50df\u50c0\u50be\u5118\u50cc\u507a\u5d05\u505d\u507c740px\u5072\u505f\u5070\u5044\u509e\u4e05
//
// \u50d7\u50e5\u4e55\u50de\u50d7\u50f6\u4e55\u65d5\u661e\u5e35\u4e04\u50ff\u5116\u50fe\u50be\u50c0\u50d0\u511e\u65d5\u661e\u5e35\u4e04\u50d7\u50cb\u5118\u4e55\u5116\u5074\u505f
//
// \u6820\u509d\u62a3
// option:				\u60d7\u60c9\u505f\u5068\u50df\u50c0\u50be\u5118\u50cc\u50c6\u50fe\u50d4\u5111\u511e
//
// autor ryo masuda
// 2005/02/03
// *******************************************************************
function doCreateDialogOption1024(){
	var option;

	option = doCreateDialogOption("1024", "768", "1024", "740", "no", "no", "no");
	return option;
}


// *******************************************************************
// JavaScript : 800\u590b\u67fa\u507a\u50df\u50c0\u50be\u5118\u50cc\u50c6\u4e55\u50fe\u511e\u5e2a\u5075\u6601\u68ab\u5074\u50c6\u50fe\u50d4\u5111\u511e\u60d7\u60c9
//
// showModalDialog, showModelessDialog\u507a\u50c6\u4e55\u50fe\u511e\u5e2a\u5075\u5dca\u68a1\u4e05
// CommerceCube\u5071\u6395\u5a8a\u5061\u509e800\u5003600\u50d2\u50c0\u50d8\u507a\u50df\u50c0\u50be\u5118\u50cc\u50c6\u50fe\u50d4\u5111\u511e\u4e05
//
// \u50d7\u50e5\u4e55\u50de\u50d7\u50f6\u4e55\u65d5\u661e\u5e35\u4e04\u50ff\u5116\u50fe\u50be\u50c0\u50d0\u511e\u65d5\u661e\u5e35\u4e04\u50d7\u50cb\u5118\u4e55\u5116\u5074\u505f
//
// \u6820\u509d\u62a3
// option:				\u60d7\u60c9\u505f\u5068\u50df\u50c0\u50be\u5118\u50cc\u50c6\u50fe\u50d4\u5111\u511e
//
// autor ryo masuda
// 2005/02/03
// *******************************************************************
function doCreateDialogOption800(){
	var option;

	option = doCreateDialogOption("800", "600", "800", "600", "no", "no", "no");
	return option;
}


// *******************************************************************
// JavaScript : 600\u590b\u67fa\u507a\u50df\u50c0\u50be\u5118\u50cc\u50c6\u4e55\u50fe\u511e\u5e2a\u5075\u6601\u68ab\u5074\u50c6\u50fe\u50d4\u5111\u511e\u60d7\u60c9
//
// showModalDialog, showModelessDialog\u507a\u50c6\u4e55\u50fe\u511e\u5e2a\u5075\u5dca\u68a1\u4e05
// CommerceCube\u5071\u6395\u5a8a\u5061\u509e600\u5003600\u50d2\u50c0\u50d8\u507a\u50df\u50c0\u50be\u5118\u50cc\u50c6\u50fe\u50d4\u5111\u511e\u4e05
//
// \u50d7\u50e5\u4e55\u50de\u50d7\u50f6\u4e55\u65d5\u661e\u5e35\u4e04\u50ff\u5116\u50fe\u50be\u50c0\u50d0\u511e\u65d5\u661e\u5e35\u4e04\u50d7\u50cb\u5118\u4e55\u5116\u5074\u505f
//
// \u6820\u509d\u62a3
// option:				\u60d7\u60c9\u505f\u5068\u50df\u50c0\u50be\u5118\u50cc\u50c6\u50fe\u50d4\u5111\u511e
//
// autor ryo masuda
// 2005/02/03
// *******************************************************************
function doCreateDialogOption600(){
	var option;

	option = doCreateDialogOption("600", "600", "600", "600", "no", "no", "no");
	return option;
}


// ****************************************
// JavaScript : \u5ddc\u6395\u505f\u5068\u5117\u50d0\u4e55\u50ea\u507a\u5cf4\u6193\u64d6\u5f35\u68df
//
// \u5ddc\u6395\u505f\u5068\u5117\u50d0\u4e55\u50ea\u507a\u5cf4\u6193\u64d6\u5f35\u68df\u50aa\u5cf4\u5046\u4e05
// Autor : YUTAKA YOSHIDA
//
// input: target \u6193\u64d6\u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u50aa\u5ddc\u6395\u4e05
// ****************************************
function doInsertLine(target) {

	var	windoc = window.document;
	var form = windoc.forms[0];

	// \u6348\u615c\u507a\u50fc\u50c5\u4e55\u50c7\u50d7\u57f5\u62b2\u67e4\u5fa7\u507a\u5ea2\u647c\u4e05
	var focus_name = form.elements["focus_name"].value;
	if(focus_name == "" || focus_name == null) {
		return;
	}

// # Debug.
//	alert("\u6348\u615c\u507a\u50fc\u50c5\u4e55\u50c7\u50d7\u57f5\u62b2\u67e4\u5fa7:" + focus_name);

	// \u6193\u64d6\u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u507a\u50c0\u511e\u50e8\u50e2\u50cb\u50d7\u50aa\u5ea2\u647c\u4e05
	var index = Number(form.elements["list_start_index"].value);

// # Debug.
//	alert("\u6193\u64d6\u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u507a\u50c0\u511e\u50e8\u50e2\u50cb\u50d7:" + index);

	// \u6193\u64d6\u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u507a\u50fc\u50c5\u4e55\u50c7\u50d7\u57f5\u62b2\u5050\u509c\u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u50aa\u5ea2\u647c
	var targetObj = windoc.all.item(focus_name);

	// \u67e7\u5d76\u50c4\u5115\u50be\u64aa\u57f2\u595c\u5071\u507a\u5f35\u68df\u4e04\u6193\u64d6\u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u5051\u5d0c\u62b3\u505f\u5070\u5044\u5074\u5044\u5fdc\u5d0c\u507a\u5f35\u68df\u50aa\u67cd\u5ca0\u58d4
	if(targetObj != null && !Boolean(targetObj.length)) {
		return;
	}
	if(targetObj[index].parentElement.parentElement.id != target) {
		return;
	}

// # Debug.
//	alert("\u6193\u64d6\u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u507a\u50fc\u50c5\u4e55\u50c7\u50d7\u57f5\u62b2\u5050\u509c\u61f3\u5fbe\u507a\u67e7\u5d76\u67e4:" + targetObj[index].parentElement.parentElement.id);

	// \u67e7\u5d76\u5cf4\u507a\u5d5f\u621d\u62a3\u507a\u5ea2\u647c\u4e05
	var code = "var max_index = window." + target + ".all.item('CHILD_DIV_ID').length";
	eval(code);

// # Debug.
//	alert("\u67e7\u5d76\u5cf4\u507a\u5d5f\u621d\u62a3:" + max_index);

	// \u5d5f\u5ed4\u5cf4\u5075\u64d6\u6921\u5051\u5041\u509e\u5fdc\u5d0c\u507c\u5e5a\u5cf4\u6644\u58dc\u4e05
	if(doCheckActiveLineForList(target,max_index-1)){
		alert("\u5d5f\u5ed4\u5cf4\u5075\u64d6\u6921\u5051\u5041\u509e\u5068\u5094\u4e04\u5cf4\u6193\u64d6\u5071\u5052\u5091\u5063\u50ab\u4e05");
		return;
	}

// # Debug.
//	alert("\u5d5f\u5ed4\u5cf4\u5075\u64d6\u6921\u507c\u5074\u5044\u4e05");

	// \u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u5075\u50e8\u4e55\u50de\u5051\u67cd\u5044\u5fdc\u5d0c\u507c\u6193\u64d6\u6644\u58dc\u4e05
	if(!doCheckActiveLineForList(target,index)){
		alert("\u5117\u50d0\u4e55\u50ea\u5075\u50e8\u4e55\u50de\u5051\u61da\u5d7c\u505f\u5074\u5044\u5068\u5094\u4e04\u5cf4\u6193\u64d6\u5071\u5052\u5091\u5063\u50ab\u4e05");
		return;
	}

// # Debug.
//	alert("\u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u5075\u50e8\u4e55\u50de\u5051\u61da\u5d7c\u4e05");

	// \u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u5051\u5d5f\u5ed4\u5cf4\u507a\u5fdc\u5d0c\u507c\u6193\u64d6\u6644\u58dc\u4e05
	if(index==max_index-1){
		alert("\u5d5f\u5ed4\u5cf4\u507a\u5117\u50d0\u4e55\u50ea\u507a\u5068\u5094\u4e04\u5cf4\u6193\u64d6\u5071\u5052\u5091\u5063\u50ab\u4e05");
		return;
	}

// # Debug.
//	alert("\u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u5051\u5d5f\u5ed4\u5cf4\u5071\u507c\u5074\u5044\u4e05");

	// \u5c30\u5d7c\u5cf4\u5050\u509c\u6873\u5ca0\u5cf4\u5091\u5071\u50aa\u58d3\u66fd\u508a\u50d7\u5114\u50c0\u50ea\u4e05
	var j;
	for (j=max_index-1;j>index; j--) {
		doLineCopyForInsert(target,j-1);
	}

// # Debug.
//	alert("\u5c30\u5d7c\u5cf4\u5050\u509c\u6873\u5ca0\u5cf4\u5091\u5071\u507a\u62a3\u50aa\u58d3\u66fd\u508a\u50d7\u5114\u50c0\u50ea\u59f0\u6906");

	// \u6193\u64d6\u61f3\u5fbe\u5cf4\u50aa\u50cb\u5115\u50be\u4e05
	doClearLine(target, index);

// # Debug.
//	alert("\u6193\u64d6\u61f3\u5fbe\u5cf4\u50aa\u50cb\u5115\u50be\u50aa\u59f0\u6906");

	// \u50c7\u4e55\u50dc\u5116\u4e04\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u6193\u64d6\u5117\u50d0\u4e55\u50ea\u5075\u63d4\u68a1\u4e05
	doSelectFocus(target,index);

// # Debug.
//	alert("\u50c7\u4e55\u50dc\u5116\u4e04\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u6193\u64d6\u5117\u50d0\u4e55\u50ea\u5075\u63d4\u68a1\u59f0\u6906");

	// \u6193\u64d6\u5cf4\u507a\u5f36\u5a5c\u62a3\u611d\u6395
	doIntializeRecordField(target,index);

// # Debug.
//	alert("\u6193\u64d6\u5cf4\u507a\u5f36\u5a5c\u62a3\u611d\u6395\u59f0\u6906");
}

// ****************************************
// JavaScript : \u5cf4\u6193\u64d6\u4e12\u5cf4\u5d8d\u5f4d\u6131\u68a1\u4e04\u67e7\u5d76\u6873\u5ca0\u5cf4\u657e\u6395
//
// \u61f3\u5fbe\u5cf4\u5075\u61f3\u505f\u5070\u4e04
// \u5d5f\u5f36\u507a\u65b2readonly\u4e04\u64d6\u6921\u6601\u6075\u5d01\u681a\u5075\u64d6\u6921\u5051\u5041\u509f\u507d\u6873\u5ca0\u5cf4\u5072\u505f\u5070\u5092\u5074\u5061\u4e05
// \u6873\u5ca0\u5cf4\u5071\u5041\u509f\u507dtrue\u4e04\u5066\u5046\u5071\u5074\u5057\u509f\u507dfalse\u50aa\u66c9\u5061\u4e05
// Autor : YUTAKA YOSHIDA
//
// input: target \u6193\u64d6\u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u50aa\u5ddc\u6395\u4e05
// input: lineno \u6193\u64d6\u61f3\u5fbe\u5cf4
// ****************************************
function doCheckActiveLineForList(target,lineno){

	// \u61f3\u5fbe\u67e7\u5d76\u62de\u5061\u508b\u5070\u507a\u5cf4\u68ab\u617a\u50aa\u5ea2\u647c\u4e05
	var checkLineObj = getLineObjects(target, lineno, "INPUT");

// # Debug.
//	alert("\u61f3\u5fbe\u67e7\u5d76\u62de\u5061\u508b\u5070\u507a\u5cf4\u68ab\u617a\u50aa\u5ea2\u647c\u59f0\u6906");

	var i,ii;
	ii=checkLineObj.length;

// # Debug.
//	alert("\u5cf4\u68ab\u617a\u60a2:"+ii);

	// \u61f3\u5fbe\u5072\u5074\u509e\u5cf4\u507a\u64d6\u6921\u5d01\u681a\u5075\u61f3\u505f\u5070\u4e04\u612d\u6462\u5050\u509c\u50e0\u50c3\u50e2\u50cb\u5061\u509e
	for(i=0;i<ii;i++){
		if(checkLineObj[i].readOnly == false && checkLineObj[i].req == "true") {
			if(checkLineObj[i].value!="") {
				return true;
			}else{
				return false;
			}
		}
	}
}

// ****************************************
// JavaScript : \u5cf4\u50aa\u58d3\u66fd\u508a\u50d0\u50fa\u4e55\u505f\u5091\u5061\u4e05
//
// \u67e7\u5d76\u5cf4\u62de\u507a\u50e8\u4e55\u50de\u50aa\u58d3\u66fd\u5075\u50d7\u5114\u50c0\u50ea\u505f\u5091\u5061\u4e05
// Autor : YUTAKA YOSHIDA
//
// input: target \u6193\u64d6\u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u50aa\u5ddc\u6395\u4e05
// input: lineno \u6193\u64d6\u61f3\u5fbe\u5cf4
// return:Nothing
// ****************************************
function doLineCopyForInsert(target,lineno){

	// \u6193\u64d6\u61f3\u5fbe\u5cf4\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u5072\u4e04\u5e2b\u6193\u64d6\u61f3\u5fbe\u5cf4\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507a\u5ea2\u647c\u4e05
	var tmpPre1 = getLineObjects(target, lineno,   "INPUT");
	var tmpAft1 = getLineObjects(target, lineno+1, "INPUT");

	// \u6193\u64d6\u61f3\u5fbe\u5cf4\u507a\u50c7\u5114\u5109\u60a2\u50aa\u5ea2\u647c\u4e05
	var ii;
	ii=tmpPre1.length;

	// \u68ab\u617a\u62a3\u507a\u50d0\u50fa\u4e55\u4e05
	var i;
	var tmpPre,tmpAft;
	for(i=0;i<ii;i++){
		tmpPre=tmpPre1(i);
		tmpAft=tmpAft1(i);
		// \u68ab\u617a\u5eac\u6936\u507a\u657e\u6395\u4e05\u5c30\u5fec\u4e04\u50e5\u50c9\u50d7\u50e9\u5103\u50e2\u50cb\u50d7\u507a\u5fdc\u5d0c\u507a\u5092\u4e05
		if(tmpPre.type == "text") {
			// \u50fc\u50bf\u4e55\u5116\u50ea\u50de\u50c0\u50fe\u5051index\u507a\u5fdc\u5d0c\u507c\u62a3\u50aa\u50d0\u50fa\u4e55\u505f\u5074\u5044\u4e05
			if(tmpPre.fieldtype != "index") {
				tmpAft.value=tmpPre.value;
			}
		}else{
			tmpAft.value=tmpPre.value;
		}
	}
}

// ****************************************
// JavaScript : \u5ddc\u6395\u67e7\u5d76\u5cf4\u507a\u50cb\u5115\u50be\u50aa\u5cf4\u5044\u5091\u5061\u4e05
//
// \u5ddc\u6395\u505f\u5068\u50c0\u511e\u50e8\u50e2\u50cb\u50d7\u507a\u67e7\u5d76\u5cf4\u50aa\u50cb\u5115\u50be\u505f\u5091\u5061\u4e05
// Autor : YUTAKA YOSHIDA
//
// input: target \u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u50aa\u5ddc\u6395\u4e05
// input: lineno \u61f3\u5fbe\u5cf4
// ****************************************
function doClearLine(target,lineno){

	// \u61f3\u5fbe\u5cf4\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507a\u5ea2\u647c\u4e05
	var tmpObj = getLineObjects(target, lineno,   "INPUT");

	// \u61f3\u5fbe\u5cf4\u507a\u50c7\u5114\u5109\u60a2\u50aa\u5ea2\u647c\u4e05
	var ii;
	ii=tmpObj.length;

	// \u68ab\u617a\u62a3\u507a\u5d8d\u5f4d\u4e05
	var i;
	for(i=0;i<ii;i++){
		// \u68ab\u617a\u5eac\u6936\u507a\u657e\u6395\u4e05\u5c30\u5fec\u4e04\u50e5\u50c9\u50d7\u50e9\u5103\u50e2\u50cb\u50d7\u507a\u5fdc\u5d0c\u507a\u5092\u4e05
		if(tmpObj(i).type == "text") {
			// \u50fc\u50bf\u4e55\u5116\u50ea\u50de\u50c0\u50fe\u5051index\u507a\u5fdc\u5d0c\u507c\u62a3\u50aa\u5d8d\u5f4d\u505f\u5074\u5044\u4e05
			if(tmpObj(i).fieldtype != "index") {
				tmpObj(i).value="";
			}
		}else{
			tmpObj(i).value="";
		}
	}
}

// ****************************************
// JavaScript : \u67e7\u5d76\u4e12\u5ddc\u6395\u5117\u50d0\u4e55\u50eafocus\u63d4\u68a1
//
// \u5ddc\u6395\u505f\u5068\u67e7\u5d76\u67e4\u507a\u5cf4\u50c0\u511e\u50e8\u50e2\u50cb\u50d7\u507a\u5d5f\u5f36\u507a
// \u65b2readonly\u4e04\u64d6\u6921\u6601\u6075\u5d01\u681a\u508a\u507a\u50fc\u50c5\u4e55\u50c7\u50d7\u63d4\u68a1\u50aa\u5cf4\u5046\u4e05
// Autor : YUTAKA YOSHIDA
//
// input: target \u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u50aa\u5ddc\u6395\u4e05
// input: lineno \u61f3\u5fbe\u5cf4
// ****************************************
function doSelectFocus(target, lineno){

	// \u61f3\u5fbe\u5cf4\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507a\u5ea2\u647c\u4e05
	var recObjs = getLineObjects(target, lineno,   "INPUT");

	// \u61f3\u5fbe\u5cf4\u507a\u50c7\u5114\u5109\u60a2\u50aa\u5ea2\u647c\u4e05
	var ii = recObjs.length;

	// \u68ab\u617a\u62a3\u507a\u5d8d\u5f4d\u4e05
	var i;
	for(i=0;i<ii;i++){
		if(recObjs[i].readOnly == false && recObjs[i].req == "true") {
			// \u61f3\u5fbe\u5d01\u681a\u67e4\u5fa7\u507a\u5ea2\u647c
			var objname = String(recObjs(i).name);
			// \u61f3\u5fbe\u5d01\u681a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507a\u5ea2\u647c
			var item_obj;
			code="item_obj = window.document.all." + objname + "(" + lineno + ")";
			eval(code);
			item_obj.select();
			item_obj.focus();
			return;
		}
	}

}

// ****************************************
// JavaScript : \u67e7\u5d76\u5cf4\u507a\u5f36\u5a5c\u58d4
//
// \u5ddc\u6395\u505f\u5068\u67e7\u5d76\u5cf4\u5075\u5f36\u5a5c\u62a3\u50aa\u6219\u64d6\u505f\u5091\u5061\u4e05
// Autor : YUTAKA YOSHIDA
//
// input: target \u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u50aa\u5ddc\u6395\u4e05
// input: lineno \u61f3\u5fbe\u5cf4
// ****************************************
function doIntializeRecordField(target, lineno){

	// \u61f3\u5fbe\u5cf4\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507a\u5ea2\u647c\u4e05
	var tmpObj = getLineObjects(target, lineno, "INPUT");

	// \u61f3\u5fbe\u5cf4\u507a\u50c7\u5114\u5109\u60a2\u50aa\u5ea2\u647c\u4e05
	var ii = tmpObj.length;

	// \u68ab\u617a\u62a3\u507a\u5f36\u5a5c\u58d4\u4e05
	var i;
	for(i=0;i<ii;i++){
		if(tmpObj(i).type == "text") {
			if(tmpObj(i).fieldtype != "index") {
				tmpObj(i).value=jsDoFormatSys(tmpObj(i));
			}
		}else{
			tmpObj(i).value="";
		}
	}
}

// ****************************************
// JavaScript : \u5cf4\u5d8d\u5f4d\u5f35\u68df
//
// \u5ddc\u6395\u505f\u5068\u5117\u50d0\u4e55\u50ea\u507a\u5cf4\u5d8d\u5f4d\u5f35\u68df\u50aa\u5cf4\u5046\u4e05
// Autor : YUTAKA YOSHIDA
//
// input: target \u5d8d\u5f4d\u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u50aa\u5ddc\u6395\u4e05
// ****************************************
function doDeleteLine(target) {

	var	windoc = window.document;
	var form = windoc.forms[0];

	// \u6348\u615c\u507a\u50fc\u50c5\u4e55\u50c7\u50d7\u57f5\u62b2\u67e4\u5fa7\u507a\u5ea2\u647c\u4e05
	var focus_name = form.elements["focus_name"].value;
	if(focus_name == "" || focus_name == null) {
		return;
	}

// # Debug.
//	alert("\u6348\u615c\u507a\u50fc\u50c5\u4e55\u50c7\u50d7\u57f5\u62b2\u67e4\u5fa7:" + focus_name);

	// \u6193\u64d6\u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u507a\u50c0\u511e\u50e8\u50e2\u50cb\u50d7\u50aa\u5ea2\u647c\u4e05
	var index = Number(form.elements["list_start_index"].value);

// # Debug.
//	alert("\u6193\u64d6\u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u507a\u50c0\u511e\u50e8\u50e2\u50cb\u50d7:" + index);

	// \u6193\u64d6\u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u507a\u50fc\u50c5\u4e55\u50c7\u50d7\u57f5\u62b2\u5050\u509c\u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u50aa\u5ea2\u647c
	var targetObj = windoc.all.item(focus_name);

	// \u6193\u64d6\u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u507a\u50fc\u50c5\u4e55\u50c7\u50d7\u57f5\u62b2\u5050\u509c\u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u50aa\u5ea2\u647c
	// \u67e7\u5d76\u50c4\u5115\u50be\u64aa\u57f2\u595c\u5071\u507a\u5f35\u68df\u4e04\u6193\u64d6\u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u5051\u5d0c\u62b3\u505f\u5070\u5044\u5074\u5044\u5fdc\u5d0c\u507a\u5f35\u68df\u50aa\u67cd\u5ca0\u58d4
	if(targetObj != null && !Boolean(targetObj.length)) {
		return;
	}
	if(targetObj[index].parentElement.parentElement.id != target) {
		return;
	}


// # Debug.
//	alert("\u6193\u64d6\u61f3\u5fbe\u5117\u50d0\u4e55\u50ea\u507a\u50fc\u50c5\u4e55\u50c7\u50d7\u57f5\u62b2\u5050\u509c\u61f3\u5fbe\u507a\u67e7\u5d76\u67e4:" + targetObj[index].parentElement.parentElement.id);

	// \u67e7\u5d76\u5cf4\u507a\u5d5f\u621d\u62a3\u507a\u5ea2\u647c\u4e05
	var code = "var max_index = window." + target + ".all.item('CHILD_DIV_ID').length";
	eval(code);

// # Debug.
//	alert("\u67e7\u5d76\u5cf4\u507a\u5d5f\u621d\u62a3:" + max_index);

	//\u5c30\u5d7c\u5cf4\u5050\u509c\u6873\u5ca0\u5cf4\u5091\u50711\u5cf4\u5062\u506e\u5fcb\u5075\u50d4\u50fc\u50e9\u4e05
	var j;
	for (j=index; j <=max_index+1; j++) {
		if(j==max_index-1){
			doClearLine(target,j);
			break;
		} else {
			doLineCopyForDelete(target,j);
		}
	}

// # Debug.
//	alert("\u5c30\u5d7c\u5cf4\u5050\u509c\u6873\u5ca0\u5cf4\u5091\u5071\u507a\u62a3\u50aa\u5fcb\u5075\u508a\u50d7\u5114\u50c0\u50ea\u59f0\u6906");

	// \u50c7\u4e55\u50dc\u5116\u4e04\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u5d8d\u5f4d\u5117\u50d0\u4e55\u50ea\u5075\u63d4\u68a1\u4e05
	doSelectFocus(target,index);

// # Debug.
//	alert("\u50c7\u4e55\u50dc\u5116\u4e04\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u5d8d\u5f4d\u5117\u50d0\u4e55\u50ea\u5075\u63d4\u68a1\u59f0\u6906");

	// \u5cf4\u5d8d\u5f4d\u5c7b\u507a\u5d5f\u5ed4\u5cf4\u508a\u507a\u5f36\u5a5c\u62a3\u611d\u6395
	doIntializeRecordField(target,max_index-1);
// # Debug.
//	alert("\u5cf4\u5d8d\u5f4d\u5c7b\u507a\u5d5f\u5ed4\u5cf4\u508a\u507a\u5f36\u5a5c\u62a3\u611d\u6395\u59f0\u6906");
}

// ****************************************
// JavaScript : \u5cf4\u50aa\u5fcb\u66fd\u508a\u50d0\u50fa\u4e55\u505f\u5091\u5061\u4e05
//
// \u67e7\u5d76\u5cf4\u62de\u507a\u50e8\u4e55\u50de\u50aa\u5fcb\u66fd\u5075\u50d7\u5114\u50c0\u50ea\u505f\u5091\u5061\u4e05
// Autor : YUTAKA YOSHIDA
//
// input: target \u6193\u64d6\u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u50aa\u5ddc\u6395\u4e05
// input: lineno \u6193\u64d6\u61f3\u5fbe\u5cf4
// return:Nothing
// ****************************************
function doLineCopyForDelete(target,lineno){

	// \u61f3\u5fbe\u67e7\u5d76\u62de\u5061\u508b\u5070\u507a\u5cf4\u68ab\u617a\u50aa\u5ea2\u647c\u4e05

	// \u6193\u64d6\u61f3\u5fbe\u5cf4\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u5072\u4e04\u5e2b\u6193\u64d6\u61f3\u5fbe\u5cf4\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507a\u5ea2\u647c\u4e05
	var tmpPre1 = getLineObjects(target, lineno,   "INPUT");
	var tmpAft1	= getLineObjects(target, lineno+1,   "INPUT");

	// \u5d8d\u5f4d\u61f3\u5fbe\u5cf4\u507a\u50c7\u5114\u5109\u60a2\u50aa\u5ea2\u647c\u4e05
	var ii = tmpAft1.length;

	// \u68ab\u617a\u62a3\u507a\u50d0\u50fa\u4e55\u4e05
	var i;
	var tmpPre,tmpAft;
	for(i=0;i<ii;i++){
		tmpPre=tmpPre1(i);
		tmpAft=tmpAft1(i);
		// \u68ab\u617a\u5eac\u6936\u507a\u657e\u6395\u4e05\u5c30\u5fec\u4e04\u50e5\u50c9\u50d7\u50e9\u5103\u50e2\u50cb\u50d7\u507a\u5fdc\u5d0c\u507a\u5092\u4e05
		if(tmpAft.type == "text") {
			// \u50fc\u50bf\u4e55\u5116\u50ea\u50de\u50c0\u50fe\u5051index\u507a\u5fdc\u5d0c\u507c\u62a3\u50aa\u50d0\u50fa\u4e55\u505f\u5074\u5044\u4e05
			if(tmpAft.fieldtype != "index") {
				tmpPre.value=tmpAft.value;
			}
		}else{
			tmpPre.value=tmpAft.value;
		}
	}
}

// ****************************************
// JavaScript : \u5ddc\u6395\u61f3\u5fbe\u5cf4\u507a\u50de\u50cc\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u50aa\u5ea2\u647c\u505f\u5091\u5061\u4e05
//
// Autor : YUTAKA YOSHIDA
//
// input: target \u61f3\u5fbe\u507a\u67e7\u5d76\u67e4\u50aa\u5ddc\u6395\u4e05
// input: index  \u61f3\u5fbe\u507a\u67e7\u5d76\u5cf4\u50c0\u511e\u50e8\u50e2\u50cb\u50d7\u50aa\u5ddc\u6395\u4e05
// input: tags   \u61f3\u5fbe\u507a\u67e7\u5d76\u5cf4\u507a\u5ea2\u647c\u505f\u5068\u5044\u50de\u50cc\u67e4\u50aa\u5ddc\u6395\u4e05
//               \u4e6e\u4ef8name\u61cf\u60c8\u5072\u507c\u5818\u5046\u4e05\u4e6f
// ****************************************
function getLineObjects(target, index, tags) {

	var lineObjs;
	var code = "lineObjs = window." + target + ".all.CHILD_DIV_ID("+index+")";
	eval(code);

// # Debug.
//	alert(code);

	var tagObjs;
	code = "tagObjs = lineObjs.all.tags('" + tags + "')";
	eval(code);

// # Debug.
//	alert(code);

	return tagObjs;
}

// ****************************************
// JavaScript : \u67e7\u5d76\u6679\u66c4\u5ccf\u50e8\u4e55\u50de\u507a\u650b\u5a5e\u657e\u6395
//             \u4e6e\u615c\u5d01\u4e04\u5e2b\u5d01\u5103\u50de\u511e\u6131\u68a1\u4e6f
//
// \u4ecf\u4ecf\u5c30\u5d7c\u5dca\u68a1\u505f\u5070\u5044\u5091\u5063\u50ab
// ****************************************
// \u67b9\u5dca\u68a1\u507a\u5068\u5094\u4e04\u5d8d\u5f4d(\u50d0\u510a\u511e\u50e9\u50be\u50c2\u50e9)
/*
function dojudgeChangeListValue(obj) {
	var message    = "\u66c4\u5ccf\u505d\u509f\u5068\u50e8\u4e55\u50de\u507c\u650b\u5a5e\u505d\u509f\u5091\u5061\u5051\u4e04\u509b\u50a0\u505f\u5044\u5071\u5061\u5050\u4e20";
	var change_flg = window.document.forms[0].elements["change_flg"].value;
	var form       = window.document.forms[0];
	var bool;
	var focusObj;

	if(change_flg == "true") {
		bool = confirm(message);
	}else{
		focusObj = document.activeElement;
		doSetChangeValueFlg(focusObj);
		change_flg = form.elements["change_flg"].value;
		if(change_flg == "true") {
			bool = confirm(message);
		}else{
			bool = true;
		}
	}
	if(bool) {
		form.elements["change_flg"].value = "";
		form.elements["cancel_flg"].value = "true";
	}else{
		form.elements["cancel_flg"].value = "false";
	}
}
*/
