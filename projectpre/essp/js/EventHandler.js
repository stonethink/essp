// **************************************** //
// HTC\u5074\u505f\u5071\u50c0\u5100\u511e\u50e9\u50aa\u50f4\u511e\u50ea\u5115\u511e\u50cc\u5061\u509e		
// 
// \u5ea2\u647c\u5071\u5052\u5074\u5044\u50c0\u5100\u511e\u50e9\u507c\u4e04\u57f2\u58d3\u507a\u509b\u5046\u5075\u5061\u509e
// onBlur \u4f40 \u50de\u50cc\u507aonblur\u61cf\u60c8\u5075\u5ddc\u6395
// onLoad \u4f40 body\u50de\u50cc\u507aonload\u61cf\u60c8\u5075\u5ddc\u6395
// onfocus \u4f40 \u50de\u50cc\u507aonfocus\u61cf\u60c8\u5075\u5ddc\u6395
// **************************************** //	



//alert("EventHandler.js\u507a\u6489\u5092\u5d2c\u5092\u5950\u5dd2");
//document.attachEvent ('onclick', doClick);
document.attachEvent ('onkeydown', doKeydown);
document.attachEvent ('onkeypress', doKeypress);
//document.attachEvent ('oncontextmenu', doContext);
//alert("EventHandler.js\u507a\u6489\u5092\u5d2c\u5092\u62de");



// ********************************* //
// onkeydown\u50c0\u5100\u511e\u50e9\u5071\u5c47\u507d\u509f\u509e\u5a2d\u60a2	 //
// onkeydown handler				 //
// ********************************* //
function doKeydown() {
//	alert("doKeydown()\u5950\u5dd2");
//	getNowDate("onkeydown\u50c0\u5100\u511e\u50e9\u5f35\u68df\u5950\u5dd2");

	// \u50c0\u5100\u511e\u50e9\u5051\u5a72\u5059\u506d\u5068\u50c4\u5117\u510a\u511e\u50e9\u507a\u61cf\u60c8\u507a\u62a3\u50aa\u5ea2\u647c
	var tagName = event.srcElement.tagName;
//	alert("\u50de\u50cc\u507a\u5eac\u6936: " + tagName);
	var tagType = event.srcElement.type;
//	alert("\u50de\u50cc\u507aType: " + tagType);
	var eventObject = event.srcElement;
//	alert("\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9: " + eventObject);
	
	// \u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u5051\u58b4\u58d3\u505d\u509f\u5068\u5e2a\u507a\u60c2\u5c7c\u4e6e\u6161\u5070\u507a\u50de\u50cc\u5075\u63d4\u68a1\u4e6f
	var ev=event;
	var wdoc=window.document;
	
	// \u58b4\u58d3\u505d\u509f\u5068\u50c9\u4e55\u507akeycode
//	alert("\u58b4\u58d3\u505d\u509f\u5068\u50c9\u4e55\u507akeycode: " + ev.keyCode);
//	alert("\u58b4\u58d3\u505d\u509f\u5068\u50c9\u4e55: " + String.fromCharCode(ev.keyCode));

	// \u5101\u4e55\u50d5\u507a\u5118\u4e55\u50ea\u62de,\u50d2\u50fd\u5108\u50e2\u50e9\u62de\u5075\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5068\u509cTab\u50c9\u4e55\u507a\u50e8\u50fc\u50c5\u5116\u50e9\u5a61\u64fb\u50aa\u6386\u5ded\u5061\u509e
//	\u50e5\u50d7\u50e9
//	alert("submit_flug: " + submit_flug);
	if(submit_flug || !checkWindowState()){
		ev.returnValue=false;
	} else{
		// \u50d2\u50fd\u5108\u50e2\u50e9\u62de\u5071\u507c\u5074\u5044\u5072\u5052\u507a\u60c2\u5c7c
		/*
		// shift\u50c9\u4e55\u58b4\u58d3\u5e2a
		if (ev.shiftKey==true){
			switch (ev.keyCode) {
				case 9:   // TAB\u507a\u50e8\u50fc\u50c5\u5116\u50e9\u5a61\u64fb\u50aa\u5ded\u5094\u509e
					ev.returnValue=false;
					break;
				case 112: // PF13
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF13);
					break;
				case 113: // PF14
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF14);
					break;
				case 114: // PF15
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF15);
					break;
				case 115: // PF16
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF16);
					break;
				case 116: // PF17
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF17);
					break;
				case 117: // PF18
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF18);
					break;
				case 118: // PF19
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF19);
					break;
				case 119: // PF20
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF20);
					break;
				case 120: // PF21
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF21);
					break;
				case 121: // PF22
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF22);
					break;
				case 122: // PF23
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF23);
					break;
				case 123: // PF24
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF24);
					break;
			}			
		}
		else {
			switch (ev.keyCode) {
				case 9:   // TAB\u507a\u50e8\u50fc\u50c5\u5116\u50e9\u5a61\u64fb\u50aa\u5ded\u5094\u509e
					ev.returnValue=false;
					break;
				case 112: // PF1
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF1);
					break;
				case 113: // PF2
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF2);
					break;
				case 114: // PF3
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF3);
					break;
				case 115: // PF4
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF4);
					break;
				case 116: // PF5
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF5);
					break;
				case 117: // PF6
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF6);
					break;
				case 118: // PF7
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF7);
					break;
				case 119: // PF8
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF8);
					break;
				case 120: // PF9
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF9);
					break;
				case 121: // PF10
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF10);
					break;
				case 122: // PF11
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF11);
					break;
				case 123: // PF12
					doKeyCancell(ev);
					doFunctionControl(wdoc, wdoc.forms["footer_form"].PF12);
					break;
			}		
		}
        */

		
		// text\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
		if(tagType == "text"){
//			alert("\u50e5\u50c9\u50d7\u50e9\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");

			// next, prev\u61cf\u60c8\u5075\u5eec\u506d\u5070\u4e04\u50fc\u50c5\u4e55\u50c7\u50d7\u580f\u6466
			doKeydownProcess(eventObject);
		}
		
		// button\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
		if(tagType == "button"){
//			alert("\u5103\u50de\u511e\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
			// next, prev\u61cf\u60c8\u5075\u5eec\u506d\u5070\u4e04\u50c7\u4e55\u50dc\u5116\u580f\u6466
			doKeydownProcess(eventObject);
		}

		// select\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
		// tagName\u507c\u621d\u66a5\u5e24
		if(tagName == "SELECT"){
//			alert("\u50d9\u5117\u50cb\u50e9\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
			doKeydownProcess(eventObject);
		}

		// checkbox\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
		if(tagType == "checkbox"){
//			alert("\u50e0\u50c3\u50e2\u50cb\u5103\u50e2\u50cb\u50d7\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
			// next, prev\u61cf\u60c8\u5075\u5eec\u506d\u5070\u4e04\u50c7\u4e55\u50dc\u5116\u580f\u6466
			doKeydownProcess(eventObject);
		}

		// radio\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
		if(tagType == "radio"){
//			alert("\u5114\u50d5\u50c6\u5103\u50de\u511e\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
			// next, prev\u61cf\u60c8\u5075\u5eec\u506d\u5070\u4e04\u50c7\u4e55\u50dc\u5116\u580f\u6466
			doKeydownProcess(eventObject);
		}

		// password\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
		if(tagType == "password"){
//			alert("\u50f7\u50d7\u511a\u4e55\u50ea\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
			// next, prev\u61cf\u60c8\u5075\u5eec\u506d\u5070\u4e04\u50c7\u4e55\u50dc\u5116\u580f\u6466
			doKeydownProcess(eventObject);
		}
		
		if(tagType=="submit") {
			doKeydownProcess(eventObject);
		}
	
		// password\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
		if(tagName == "TEXTAREA"){
//			alert("\u50f7\u50d7\u511a\u4e55\u50ea\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
			// next, prev\u61cf\u60c8\u5075\u5eec\u506d\u5070\u4e04\u50c7\u4e55\u50dc\u5116\u580f\u6466
			doKeydownProcess(eventObject);
		}
	}
//	getNowDate("onkeydown\u50c0\u5100\u511e\u50e9\u5f35\u68df\u5ed4\u6906");
}


// ****************************************
// JavaScript : 
// body\u50de\u50cc\u5071onKeyDown\u5051\u656a\u60d7\u505f\u5068\u5d7a\u5075\u5c47\u507d\u509f\u509e
// onkeydown handler
// 
// doFunctionControl(doc, pf)
// wdoc: window.document
// pf: \u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u50c9\u4e55\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9
// ****************************************
function doFunctionControl(doc, pf){
//	\u50e5\u50d7\u50e9
//	alert("doFunctionControl()\u5950\u5dd2");

	// \u6395\u5a8a\u505f\u5070\u5044\u5074\u5044\u50fc\u50e2\u50de\u4e55\u5103\u50de\u511e\u50aa\u58b4\u58d3\u505d\u509f\u5068\u5fdc\u5d0c\u507c\u4e04\u5f35\u68df\u50aa\u5cf4\u50a2\u5074\u5044
	if(pf==null){
//		alert("\u6395\u5a8a\u505d\u509f\u5070\u5044\u5091\u5063\u50ab");
		return;
	}

	if((pf.value!="")&&(pf.value!=null)&&!pf.disabled) {
		// \u50d2\u50fd\u5108\u50e2\u50e9\u612d\u507a\u50f7\u50d7\u5051\u5b3b\u4e04\u5095\u505f\u5054\u507c\u67cd\u5ca0\u58d4\u505d\u509f\u5070\u5044\u5068\u5fdc\u5d0c\u507c\u50d2\u50fd\u5108\u50e2\u50e9\u5f35\u68df\u50aa\u5cf4\u50a2\u5074\u5044
		if((pf.path!="")&&(pf.path!=null)) {
			// \u50d2\u50fd\u5108\u50e2\u50e9\u5a2d\u60a2\u50aa\u5c47\u5083\u5f0c\u5061
			doSubmit(pf);
		}else{
			// \u50d7\u50cb\u5115\u50fe\u50e9\u507a\u6466\u63d1\u5e5a\u5cf4
			if((pf.scriptstr!="")&&(pf.scriptstr!=null)) {
				eval(pf.scriptstr);
			}	
		}
	}
}

// ********************************* //
// onkeypress\u50c0\u5100\u511e\u50e9\u5071\u5c47\u507d\u509f\u509e\u5a2d\u60a2	 //
// onkeypress handler				 //
// ********************************* //
function doKeypress(){
//	alert("doKeypress()\u5950\u5dd2");
	
	// Enterkey\u507a\u50e8\u50fc\u50c5\u5116\u50e9\u5a61\u64fb\u50aa\u5ded\u5094\u509e
	if(event.keyCode==13) event.returnValue=false;
}


// ************************************ //
// oncontextmenu\u50c0\u5100\u511e\u50e9\u5071\u5c47\u507d\u509f\u509e\u5a2d\u60a2	//
// oncontextmenu handler				//
// ************************************	//
function doContext() {
//	alert("doContext()\u5950\u5dd2");
	
	// \u5843\u50cb\u5115\u50e2\u50cb\u507a\u50e8\u50fc\u50c5\u5116\u50e9\u5a61\u64fb\u50aa\u5ded\u5094\u509e
	event.returnValue=false;
}


// ******************************** //
// onload\u50c0\u5100\u511e\u50e9\u5071\u5c47\u507d\u509f\u509e\u5a2d\u60a2		//
// onload handler					//
// ********************************	//
function doLoad() {
//	alert("doLoad()\u5950\u5dd2");
	
	// Confrim\u50df\u50c0\u50be\u5118\u50cc\u507a\u5e5a\u5cf4
	doConfirmSubmit();
	// \u50df\u50c0\u50be\u5118\u50cc\u507a\u5e5a\u5cf4
	doMessageDialog();
	// \u50c0\u511e\u50fc\u50c5\u510a\u4e55\u50d4\u5111\u511e\u50c4\u5115\u50be\u507a\u5f36\u5a5c\u58d4
	initInformationArea();
	// \u5e2b\u50fc\u50c5\u4e55\u50c7\u50d7\u508a
	doNextFocus();
	// \u66bf\u50c2\u50bf\u511e\u50ea\u50c2\u50c6\u4e55\u50fe\u511e
	doWindowOpen();
	
	//\u5110\u4e55\u50d3\u5e5a\u61b0\u5f36\u5a5cLoad\u5a2d\u60a2\u507a\u5e5a\u5cf4
	try {
		doUserLoad();
	} catch (e) {
		//\u5110\u4e55\u50d3\u507a\u5118\u4e55\u50ea\u5a2d\u60a2\u5051\u61da\u5d7c\u505f\u5074\u5044\u5fdc\u5d0c\u507c\u58d7\u5095\u505f\u5074\u5044.
	}
}

// ****************************************
// \u50c0\u511e\u50fc\u50c5\u510a\u4e55\u50d4\u5111\u511e\u50c4\u5115\u50be\u507a\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u5f36\u5a5c\u58d4
// ****************************************
function initInformationArea() {
    /*
	var infoarea = window.document.forms[1].infomation_area;
	if (infoarea.value != null && infoarea.value != "") {
		// \u50c0\u511e\u50fc\u50c5\u510a\u4e55\u50d4\u5111\u511e\u50c4\u5115\u50be\u5075\u5d01\u681a\u5075\u660d\u506f\u5050\u5074\u5044\u510a\u50e2\u50d9\u4e55\u50d5\u5051\u5041\u509c\u5050\u5060\u5094\u611d\u6395\u505d\u509f\u5070\u5044\u509e\u5fdc\u5d0c
		infoarea.msg = infoarea.value;
	}
    */
}

// ***************************** //
// onclick\u50c0\u5100\u511e\u50e9\u5071\u5c47\u507d\u509f\u509e\u5a2d\u60a2	 //
// onclick handler				 //
// \u5c30\u5d7c\u4e04\u5dca\u68a1\u505f\u5070\u5044\u5074\u5044
// ***************************** //
function doClick() {
//	alert("doClick()\u5950\u5dd2");
	
	// \u50c0\u5100\u511e\u50e9\u5051\u5a72\u5059\u506d\u5068\u50c4\u5117\u510a\u511e\u50e9\u507a\u61cf\u60c8\u507a\u62a3\u50aa\u5ea2\u647c
//	var tagName = event.srcElement.tagName;
//	alert("\u50de\u50cc\u507a\u5eac\u6936: " + tagName);
	var tagType = event.srcElement.type;
//	alert("\u50de\u50cc\u507aType: " + tagType);
	var eventObject = event.srcElement;
//  alert("\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9: " + eventObject);
	
	// \u50d2\u50fd\u5108\u50e2\u50e9\u62de\u4e04\u5091\u5068\u507c\u5101\u4e55\u50d5\u507a\u5118\u4e55\u50ea\u62de\u5074\u509c\u4e04\u5f35\u68df\u50aa\u6386\u5ded\u5061\u509e
    //alert("submit_flug: " + submit_flug);
	if(submit_flug || !checkWindowState()){
		return;
	}
	
	// button\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
	if(tagType == "button"){
//		alert("\u5103\u50de\u511e\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
		doButtonClickProcess(eventObject);
	}
	
	// radio\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
	if(tagType == "radio"){
//		alert("\u5114\u50d5\u50c6\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
		doRadioClickProcess(eventObject);
	}	
}


// ***************************** //
// onfocus\u50c0\u5100\u511e\u50e9\u5071\u5c47\u507d\u509f\u509e\u5a2d\u60a2	 //
// onfocus handler				 //
// ***************************** //
function doFocus() {
//	alert("doFocus()\u5950\u5dd2");
	
	// \u50c0\u5100\u511e\u50e9\u5051\u5a72\u5059\u506d\u5068\u50c4\u5117\u510a\u511e\u50e9\u507a\u61cf\u60c8\u507a\u62a3\u50aa\u5ea2\u647c
	var tagName = event.srcElement.tagName;
//	alert("\u50de\u50cc\u507a\u5eac\u6936: " + tagName);
	var tagType = event.srcElement.type;
//	alert("\u50de\u50cc\u507aType: " + tagType);
	var eventObject = event.srcElement;
//  alert("\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9: " + eventObject);
	
	// \u50d2\u50fd\u5108\u50e2\u50e9\u62de\u4e04\u5091\u5068\u507c\u5101\u4e55\u50d5\u507a\u5118\u4e55\u50ea\u62de\u5074\u509c\u4e04\u5f35\u68df\u50aa\u6386\u5ded\u5061\u509e
    //alert("submit_flug: " + submit_flug);
	if(submit_flug || !checkWindowState()){
		return;
	}
	
	// text\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
	if(tagType == "text"){
//		alert("\u50e5\u50c9\u50d7\u50e9\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
		doFocusProcess(eventObject);
	}

//	// button\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
//	if(tagType == "button"){
//		alert("\u5103\u50de\u511e\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
////		doFocusProcess(eventObject);
//	}

	// radio\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
	if(tagType == "radio"){
//		alert("\u5114\u50d5\u50c6\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
		doFocusProcess(eventObject);
	}

	// password\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
	if(tagType == "password"){
//		alert("\u50f7\u50d7\u511a\u4e55\u50ea\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
		doFocusProcess(eventObject);
	}
	
	// select\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
	// tagName\u507c\u621d\u66a5\u5e24
	if(tagName == "SELECT"){
//		alert("\u50d9\u5117\u50cb\u50e9\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
		doFocusProcess(eventObject);
	}
	
	// checkbox\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
	if(tagType == "checkbox"){
//		alert("\u50e0\u50c3\u50e2\u50cb\u5103\u50e2\u50cb\u50d7\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
		doFocusProcess(eventObject);
	}

	if(tagName == "TEXTAREA"){
		doFocusProcess(eventObject);
	}
}


// ***************************** //
// onblur\u50c0\u5100\u511e\u50e9\u5071\u5c47\u507d\u509f\u509e\u5a2d\u60a2	 //
// onblur handler				 //
// ***************************** //
function doBlur() {
//	alert("doBlur()\u5950\u5dd2");
//	getNowDate("onblur\u50c0\u5100\u511e\u50e9\u5f35\u68df\u5950\u5dd2");

	// \u50c0\u5100\u511e\u50e9\u5051\u5a72\u5059\u506d\u5068\u50c4\u5117\u510a\u511e\u50e9\u507a\u61cf\u60c8\u507a\u62a3\u50aa\u5ea2\u647c
	var tagName = event.srcElement.tagName;
//	alert("\u50de\u50cc\u507a\u5eac\u6936: " + tagName);
	var tagType = event.srcElement.type;
//	alert("\u50de\u50cc\u507aType: " + tagType);
	var eventObject = event.srcElement;
//  alert("\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9: " + eventObject);
	
	// \u5101\u4e55\u50d5\u507a\u5118\u4e55\u50ea\u62de\u5074\u509c\u4e04\u5f35\u68df\u50aa\u6386\u5ded\u5061\u509e
	if(submit_flug || !checkWindowState()){
		return;
	}	
	
	// text\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
	if(tagType == "text"){
//		alert("\u50e5\u50c9\u50d7\u50e9\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
		doBlurProcess(eventObject);
	}

	// radio\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
	if(tagType == "radio"){
//		alert("\u5114\u50d5\u50c6\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
		doBlurProcess(eventObject);
	}

	// password\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
	if(tagType == "password"){
//		alert("\u50f7\u50d7\u511a\u4e55\u50ea\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
		doBlurProcess(eventObject);
	}

	// select\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
	// tagName\u507c\u621d\u66a5\u5e24
	if(tagName == "SELECT"){
//		alert("\u50d9\u5117\u50cb\u50e9\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
		doBlurProcess(eventObject);
	}

	// checkbox\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7
	if(tagType == "checkbox"){
//		alert("\u50e0\u50c3\u50e2\u50cb\u5103\u50e2\u50cb\u50d7\u50de\u50cc\u5071\u50c0\u5100\u511e\u50e9\u5051\u656a\u60d7\u505f\u5091\u505f\u5068\u4e05");
		doBlurProcess(eventObject);
	}

	if(tagName == "TEXTAREA"){
		doBlurProcess(eventObject);
	}

//	getNowDate("onblur\u50c0\u5100\u511e\u50e9\u5f35\u68df\u5ed4\u6906");
}


// ************************ //
// \u50c0\u5100\u511e\u50e9\u507adetach			//
// \u4ecf\u5dca\u68a1\u505f\u5074\u5044\u4ecf			//
// ************************ //
function detachEventsys(event_obj){

//	alert("detachEventsys()\u507a\u6489\u5092\u5d2c\u5092\u5950\u5dd2");
	document.detachEvent ('onclick', doClick);
//	alert("detachEventsys()\u507a\u6489\u5092\u5d2c\u5092\u5ed4\u6906");
}
