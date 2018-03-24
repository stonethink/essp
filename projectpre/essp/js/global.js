// File Name : sysjava.js
//modified by XR 2006/03/22,该JS文件名(sysjava.js)可能会被误认为病毒,所以文件名改为改global.js

// ****************************************
// JavaScript : Global Property
// ****************************************
// ACCESS_DENY \u7528\u4e8e\u63a7\u5236\u4e3b\u753b\u9762\u7684\u5f53\u524d\u663e\u793a\u6a21\u5f0f\u3002\u5f53\u5f39\u51fa\u5b50\u753b\u9762\u65f6\uff0c\u5c06\u4e3b\u753b\u9762\u7684ACCESS_DENY\u8bbe\u4e3atrue\uff0c
// \u4e4b\u540e\u4e3b\u753b\u9762\u62d2\u7edd\u64cd\u4f5c\uff0c\u76f4\u5230\u5b50\u753b\u9762\u88ab\u5173\u95ed\u65f6\uff0c\u5c06ACCESS_DENY\u8bbe\u4e3afalse.
var ACCESS_DENY=false;
var submit_flug=false;
var onfocus_value=null;
/** user\u6488\u5e3a\u507a\u50d7\u50cb\u5115\u50fe\u50e9\u507a\u5be2\u58e5\u4e04\u50d2\u50fd\u5108\u50e2\u50e9\u50aa\u62de\u5ded\u505f\u5068\u5044\u5fdc\u5d0c\u5075\u5a2d\u60a2\u5071\u66c9\u5061\u508b\u5052\u6395\u60a2. */
var STOP_SUBMIT = "STOP_SUBMIT";

// +++ \u58d3\u5a70\u507a\u510a\u50e2\u50d9\u4e55\u50d5\u507cAppMessage.js\u509b\u509d\u5ea2\u647c\u5061\u509e +++ //
//var wait_message = "\u505f\u507d\u509c\u5054\u504d\u61f8\u506a\u5054\u5069\u505d\u5044";
//var reqcheck_message = "\u6601\u6075\u5d01\u681a\u5051\u64d6\u6921\u505d\u509f\u5070\u5044\u5091\u5063\u50ab\u4e05";

// \u50e5\u50d7\u50e9
// alert("sysjava.js\u6489\u5092\u5d2c\u5092\u5950\u5dd2\u4e05");

// ****************************************
// JavaScript : exception handler
//
// ****************************************
function jsExceptionHandlerSys(error,file_name,function_name) {

	var header = "SystemException : ";

	var info_file = "";
	if(file_name != null || file_name != "") {
		info_file = "\n" + "target file : " + file_name;
	}

	var info_function = "";
	if(function_name != null || function_name != "") {
	 	info_function = "\n" + "function name : " + function_name;
	}

	alert(header + error.description + "\n"
	             + info_file
	             + info_function);
}

// ****************************************
// CSS Script Behaviors : event handler
//
// Input(text),Select\u50de\u50cc\u507a
// onkeydown\u50c0\u5100\u511e\u50e9\u5f35\u68df
// ****************************************
function doKeydownProcess(obj){
//	\u50e5\u50d7\u50e9
//	alert("doKeydownProcess");
	//\u5c44\u66bf\u5103\u50de\u511e\u5051\u61da\u5d7c\u5061\u509e\u5fdc\u5d0c\u507a\u4e04\u50d4\u5111\u4e55\u50e9\u50c7\u50e2\u50e9\u50c9\u4e55\u50aa\u60c2\u5c7c
	//alert("event.keyCode="+event.keyCode);
	//event.keyCode==9 is TAB_KEY
	if((event.keyCode==13)){

		// next,prev\u61cf\u60c8\u5075\u5ddc\u6395\u505d\u509f\u5068\u5d01\u681a\u5075\u580f\u6466
		//if(obj!=null && obj.tagName!="TEXTAREA") {
		if(obj!=null) {
			if(obj.tagName=="BUTTON" || (obj.tagName=="INPUT" && obj.type=="submit") || (obj.tagName=="INPUT" && obj.type=="button")) {
				obj.click();
			} else if( obj.tagName=="TEXTAREA") {
				obj.value=obj.value+"\r\n";

				var e = obj;
				var r =e.createTextRange();
				r.moveStart('character',e.value.length);
				r.collapse(true);
				r.select();

				//event.keyCode="";
			} else {
				doMoveCursor(obj);
				event.keyCode="";
			}
		}
		//}
		// \u5f36\u5a5c\u58d4

	}
}

// ****************************************
// JavaScript : Enter,TabKey\u5d01\u681a\u580f\u6466
//
// next,prev\u61cf\u60c8\u5075\u5ddc\u6395\u505d\u509f\u5068\u5d01\u681a\u5075\u580f\u6466\u505f\u5091\u5061\u4e05
// ****************************************
function doMoveCursor(obj){
//	getNowDate("doMoveCursor()\u5950\u5dd2: " + obj.name);
	if((event.keyCode==13)||(event.keyCode==9)){
		var move;
		var split_prefix;
		var shift = event.shiftKey;
		// \u50c0\u5100\u511e\u50e9\u657e\u6395
		if(shift) {
			move = obj.prev;
			split_prefix = "-";
		}else{
			move = obj.next;
			split_prefix = "+";
		}
		// next,prev\u62a3\u507a\u50e0\u50c3\u50e2\u50cb
		if(move == "" || move == null) {
			return;
		}
		try {
			if(window.eval(move)==null && window.eval(move+move)!=null) {
				move=move+move;
			}

			if(window.eval(move)==null) {
				return;
			}
		} catch(e1) {
		}
		// \u50c7\u4e55\u50dc\u5116\u507a\u5c30\u5d7c\u57f5\u62b2\u50aa\u5ddc\u6395\u4e05
		window.document.forms[0].elements["focus_name"].value=move;
		var nextid = move.split(split_prefix);
		var disflg = null;
		var tmpObj = null;
		do {
			tmpObj = document.all.item(nextid[0]);
			if(tmpObj.type!="select-one" && document.all(nextid[0]).length){
				var row = getLineNoSys(obj);
				// \u6468\u5826\u5cf4\u64aa\u507a\u580f\u6466
				if((nextid[1]==null)&&(row!=-1)){
					nowobj = tmpObj(nextid[0])(row);
				// \u5e2b\u507a\u5cf4\u508a\u507a\u580f\u6466
				}else{
					// \u5bc1\u5dbc\u505f\u5068\u5cf4\u65a3\u5d0b\u5051\u67e7\u5d76\u5cf4\u507a\u6596\u57fb\u595c\u507a\u5fdc\u5d0c\u507c\u6157\u580f\u615c\u507a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u5051
					// \u67e7\u5d76\u5071\u5074\u5044\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u5072\u657e\u6290\u505f\u5070\u4e04\u67e7\u5d76\u507a\u5950\u5dd2\u5cf4\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u611d\u6395\u5061\u509e
					var rowlength = tmpObj.length;
					if(shift) {
						if( row-Number(nextid[1]) < 0 ){
							nowobj = tmpObj(nextid[0])(rowlength-Number(nextid[1]));
						// \u6157\u580f\u615c\u507a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u5051\u67e7\u5d76\u507a\u5d01\u681a\u507a\u5fdc\u5d0c
						}else{
							//\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u5051\u6468\u5826\u50f7\u5114\u50cc\u5114\u5109\u5050\u5073\u5046\u5050\u598b\u64e3\u5061\u509e.
							if (!isMoveCursorParagramChange(obj, "prev")) {
								//\u6468\u5826\u50f7\u5114\u50cc\u5114\u5109\u507a\u5fdc\u5d0c.
								nowobj = tmpObj(nextid[0])(row-Number(nextid[1]));
							} else {
								//\u580f\u6466\u5061\u509e\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9ID\u5051\u580e\u5074\u509e\u5fdc\u5d0c\u6601\u5062\u5d5f\u5ed4\u651d\u694d\u681a\u508a.
								//\u5066\u5046\u5071\u5074\u5044\u5fdc\u5d0c\u507c\u651d\u694d\u5bc1\u5dbc\u507a\u62a3\u50aa\u6580\u586e.
								//\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507a\u6219\u661eId\u5ea2\u647c
								var tmpObjRepId = null;
								if (document.all(nextid[0]).length) {
									tmpObjRepId = tmpObj(nextid[0])(0).id;
								} else {
									tmpObjRepId = tmpObj.id;
								}
								if (tmpObjRepId == obj.id) {
									nowobj = tmpObj(nextid[0])(row-Number(nextid[1]));
								} else {
									nowobj = tmpObj(nextid[0])(rowlength-Number(nextid[1]));
								}
							}
						}
					}else{
						if( row+Number(nextid[1]) >= rowlength ){
							nowobj = tmpObj(nextid[0])(0);
						// \u6157\u580f\u615c\u507a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u5051\u67e7\u5d76\u507a\u5d01\u681a\u507a\u5fdc\u5d0c
						}else{
							//\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u5051\u6468\u5826\u50f7\u5114\u50cc\u5114\u5109\u5050\u5073\u5046\u5050\u598b\u64e3\u5061\u509e.
							if (!isMoveCursorParagramChange(obj, "next")) {
								//\u6468\u5826\u50f7\u5114\u50cc\u5114\u5109\u507a\u5fdc\u5d0c.
								nowobj = tmpObj(nextid[0])(row+Number(nextid[1]));
							} else {
								//\u580f\u6466\u5061\u509e\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9ID\u5051\u580e\u5074\u509e\u5fdc\u5d0c\u6601\u50620\u651d\u694d\u681a\u508a.
								//\u5066\u5046\u5071\u5074\u5044\u5fdc\u5d0c\u507c\u651d\u694d\u5bc1\u5dbc\u507a\u62a3\u50aa\u6580\u586e.
								//\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507a\u6219\u661eId\u5ea2\u647c
								var tmpObjRepId = null;
								if (document.all(nextid[0]).length) {
									tmpObjRepId = tmpObj(nextid[0])(0).id;
								} else {
									tmpObjRepId = tmpObj.id;
								}
								if (tmpObjRepId == obj.id) {
									nowobj = tmpObj(nextid[0])(row+Number(nextid[1]));
								} else {
									nowobj = tmpObj(nextid[0])(0);
								}
							}
						}
					}
				}
			}else{
				nowobj=tmpObj;
			}
		} while (disflg);
		if (nowobj.disabled) {
			// \u50c7\u4e55\u50dc\u5116\u50c0\u511e\u5071\u5052\u5074\u5044\u5fec\u61fa\u5071\u5041\u509f\u507d\u50d7\u50c9\u50e2\u50fe\u505f\u5070\u5d5e\u5a63\u5c47\u5083\u5f0c\u505f.
//			alert(nowobj.name +" \u507cdisable");
			doMoveCursor(nowobj);
		} else {
			// \u50c7\u4e55\u50dc\u5116\u50c0\u511e\u5071\u5052\u509e\u5074\u509c\u5061\u509e.
//			alert(nowobj.name +" \u507cdisable\u5071\u5074\u5044");
			nowobj.focus();
		}
	}
}

// ****************************************
// JavaScript : \u6468\u5826\u50f7\u5114\u50cc\u5114\u5109\u657e\u66bf
//
// \u5c30\u5d7c\u60c2\u5c7c\u62de\u507a\u50de\u50fd\u60c2\u5c7c\u5051\u6468\u5826\u50f7\u5114\u50cc\u5114\u5109\u64aa\u507a\u5095\u507a\u5050\u65b2\u5050\u50aa\u657e\u66bf\u5061\u509e.
// \u5837\u60a2\u4e02\u4e17obj \u6449\u5969\u5f35\u68df\u62deObject
//         toMove next:next\u60c2\u5c7c\u507a\u657e\u6395\u4e02prev:prev\u60c2\u5c7c\u507a\u657e\u6395
// \u6820\u509d\u62a3\u4e17true\u4e02\u580e\u5074\u509e\u50f7\u5114\u50cc\u5114\u5109\u4e02false\u4e17\u6468\u5826\u50f7\u5114\u50cc\u5114\u5109
//
// 2004/09/29
// autor Osamu Tamaki
// ****************************************
function isMoveCursorParagramChange(obj, toMove) {
//	getNowDate("isMoveCursorParagramChange()\u5950\u5dd2: " + obj.name + " toMove: " + toMove);
	if (!document.all(obj.id).length) {
		//\u5e3a\u6698\u5051\u5c30\u5d7c\u651d\u694d\u5071\u507c\u5074\u5044\u5fdc\u5d0c\u629c\u68ca\u580f\u6466.
		return true;
	}
	//\u5e3a\u6698\u5051\u5c30\u5d7c\u651d\u694d\u507a\u5fdc\u5d0c\u507c\u5e3a\u6698\u5072\u6468\u5060\u629c\u68ca\u507a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u50aa\u5c02\u5d95\u5061\u509e.
	var move;
	var nextid;
	if (toMove=="next") {
	// "next"\u507a\u5fdc\u5d0c
// \u50e5\u50d7\u50e9
//	getNowDate("next");
		move = obj.next;
		nextid = move.split("+");
		if (nextid[1]==null) {
			//+\u5051\u506e\u5044\u5070\u5044\u5074\u5044\u5fdc\u5d0c\u507cnext\u5051singleObject\u5050\u598b\u64e3
//			getNowDate("next\u61cf\u60c8\u5075+\u5051\u6645\u5044\u5070\u5044\u5074\u5044");
			tmpObj = document.all.item(nextid[0]);
			if (tmpObj.length) {
				//\u651d\u694dObject\u507a\u5fdc\u5d0c\u507c\u6468\u5060\u50f7\u5114\u50cc\u5114\u5109\u508a\u580f\u6466.
				return false;
			}
			//singleObject\u507a\u5fdc\u5d0c\u507c\u629c\u68ca\u580f\u6466.
			return true;
		}
		//+\u5051\u506e\u5044\u5070\u5044\u509e\u5fdc\u5d0c\u507cprev\u50aaColumn0\u5091\u5071\u5c02\u5d95\u505f\u5070next\u5072\u644d\u505f\u5044\u5050\u5073\u5046\u5050\u657e\u66bf\u5061\u509e.
//		getNowDate("next\u61cf\u60c8\u5075+\u5051\u6645\u5044\u5070\u5044\u509e");
		var row = getLineNoSys(obj);
		var movePrev = obj.prev;
		var nowobj = obj;
//		getNowDate("row: " + row);

		while (true) {
			// \u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507aprev\u61cf\u60c8\u5051\u5a70\u5f0e\u505d\u509f\u5070\u5044\u509e\u5050\u50e0\u50c3\u50e2\u50cb
			if(movePrev==null || movePrev==""){
				// \u6644\u60d3\u5074\u5fdc\u5d0c\u507c\u4e04\u50be\u5114\u4e55\u50e9\u5071\u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u661e\u5e35\u5061\u509e
				alert(fwid1001);
				return;
			}

			var previd = movePrev.split("-");
			if (previd[1]==null) {
				//-\u5051\u506e\u5044\u5070\u5044\u5074\u5044\u5fdc\u5d0c\u507cprev\u5051singleObject\u5050\u598b\u64e3.
//				getNowDate("prev\u61cf\u60c8\u5075-\u5051\u506e\u5044\u5070\u5044\u5074\u5044");
				tmpObjPrev = document.all.item(previd[0]);
// \u50e5\u50d7\u50e9
//				getNowDate("\u5d01\u681a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9: " + tmpObjPrev.type);

				if (tmpObjPrev.length && (tmpObjPrev.type!="select-one")) {
					//\u651d\u694dObject\u507a\u5fdc\u5d0c\u507c\u5095\u5046\u5826\u506e\u615c\u507aprev\u50aa\u598b\u64e3.
//					getNowDate("\u580f\u6466\u612d\u507a\u5d01\u681a\u507c\u651d\u694dObject");
					nowobj = tmpObjPrev(previd[0])(row);
					movePrev = nowobj.prev;
				} else {
					//SingleObject\u507a\u5fdc\u5d0c\u507cprev\u5051next\u5072\u644d\u505f\u5044\u5050\u598b\u64e3.
//					getNowDate("\u580f\u6466\u612d\u507a\u5d01\u681a\u507cSingleObject");
					if ((nowobj.id == nextid[0]) || (nowobj.name == nextid[0])) {
						return false;
					} else {
						return true;
					}
				}
			} else {
				//-\u5051\u506e\u5044\u5070\u5044\u5068\u5fdc\u5d0c\u507cprev\u5051next\u5072\u644d\u505f\u5044\u5050\u598b\u64e3.
//				getNowDate("prev\u61cf\u60c8\u5075-\u5051\u506e\u5044\u5070\u5044\u509e");
				if ((nowobj.id == nextid[0]) || (nowobj.name == nextid[0])) {
					return false;
				} else {
					return true;
				}
			}
		}
	} else {
	// "prev"\u507a\u5fdc\u5d0c
// \u50e5\u50d7\u50e9
//	getNowDate("prev");
		move = obj.prev;
		nextid = move.split("-");
		if (nextid[1]==null) {
			//-\u5051\u506e\u5044\u5070\u5044\u5074\u5044\u5fdc\u5d0c\u507cprev\u5051singleObject\u5050\u598b\u64e3
//			getNowDate("prev\u61cf\u60c8\u5075-\u5051\u6645\u5044\u5070\u5044\u509e");
			tmpObj = document.all.item(nextid[0]);
			if (tmpObj.length) {
				//\u651d\u694dObject\u507a\u5fdc\u5d0c\u507c\u6468\u5060\u50f7\u5114\u50cc\u5114\u5109\u508a\u580f\u6466.
				return false;
			}
			//singleObject\u507a\u5fdc\u5d0c\u507c\u629c\u68ca\u580f\u6466.
			return true;
		}
		//-\u5051\u506e\u5044\u5070\u5044\u509e\u5fdc\u5d0c\u507cnext\u50aaColumnMax\u5091\u5071\u5c02\u5d95\u505f\u5070prev\u5072\u644d\u505f\u5044\u5050\u5073\u5046\u5050\u657e\u66bf\u5061\u509e.
//		getNowDate("prev\u61cf\u60c8\u5075-\u5051\u6645\u5044\u5070\u5044\u509e");
		var row = getLineNoSys(obj);
		var moveNext = obj.next;
		var nowobj = obj;
//		getNowDate("row: " + row);

		while (true) {
			// \u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507anext\u61cf\u60c8\u5051\u5a70\u5f0e\u505d\u509f\u5070\u5044\u509e\u5050\u50e0\u50c3\u50e2\u50cb
			if(moveNext==null || moveNext==""){
				// \u6644\u60d3\u5074\u5fdc\u5d0c\u507c\u4e04\u50be\u5114\u4e55\u50e9\u5071\u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u661e\u5e35\u5061\u509e
				alert(fwid1002);
				return;
			}

			var postid = moveNext.split("+");
			if (postid[1]==null) {
				//+\u5051\u506e\u5044\u5070\u5044\u5074\u5044\u5fdc\u5d0c\u507cnext\u5051singleObject\u5050\u598b\u64e3.
				tmpObjNext = document.all.item(postid[0]);
// \u50e5\u50d7\u50e9
//				getNowDate("\u5d01\u681a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9: " + tmpObjNext.type);

				if (tmpObjNext.length && (tmpObjNext.type!="select-one")) {
					//\u651d\u694dObject\u507a\u5fdc\u5d0c\u507c\u5095\u5046\u5826\u506e\u5c7b\u507anext\u50aa\u598b\u64e3.
//					getNowDate("\u580f\u6466\u612d\u507a\u5d01\u681a\u507c\u651d\u694dObject");
					nowobj = tmpObjNext(postid[0])(row);
					moveNext = nowobj.next;
				} else {
					//SingleObject\u507a\u5fdc\u5d0c\u507cnext\u5051prev\u5072\u644d\u505f\u5044\u5050\u598b\u64e3.
//					getNowDate("\u580f\u6466\u612d\u507a\u5d01\u681a\u507cSingleObject");
					if ((nowobj.id == nextid[0]) || (nowobj.name == nextid[0])) {
						return false;
					} else {
						return true;
					}
				}
			} else {
				//\u4e84\u5051\u506e\u5044\u5070\u5044\u5068\u5fdc\u5d0c\u507cnext\u5051prev\u5072\u644d\u505f\u5044\u5050\u598b\u64e3.
				if ((nowobj.id == nextid[0]) || (nowobj.name == nextid[0])) {
					return false;
				} else {
					return true;
				}
			}
		}
	}
// \u50e5\u50d7\u50e9
//	getNowDate("isMoveCursorParagramChange()\u5ed4\u6906");
}

// ****************************************
// JavaScript : onblur\u5e2a\u4e04\u651a\u5ba8\u6013\u66c4\u59fa
//
// onfocus Event\u5071\u66c4\u5ccf\u505d\u509f\u5068
// \u651a\u5ba8\u6013\u50aa\u5c26\u5075\u6820\u505f\u5091\u5061\u4e05
// ****************************************
function SetBgcolor(obj){

	// \u5114\u50d5\u50c6\u5103\u50de\u511e\u4e04\u50e0\u50c3\u50e2\u50cb\u5103\u50e2\u50cb\u50d7\u507a\u5fdc\u5d0c
	if(obj.type == "checkbox" || obj.type == "radio"){
		// \u651a\u5ba8\u6013\u50aa\u4e04\u590b\u67fa\u507a\u651a\u5ba8\u6013\u5075\u5d0c\u50a2\u5063\u509e
		obj.style.background=Back_COLOR;
	}else if(obj.type == "password"){
		// \u50f7\u50d7\u511a\u4e55\u50ea\u50de\u50cc\u507a\u5fdc\u5d0c
		obj.style.background=Password_COLOR;

		if(obj.req!=null && obj.req == "true") {
			obj.style.background=Req_COLOR;
		}
	}else {
		// \u5a5b\u5075\u50c4\u5114\u4e55\u507a\u5fdc\u5d0c\u507c\u6013\u61fc\u504a\u50aa\u5cf4\u50a2\u5074\u5044
		// \u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e85\u50d5(obj.msg)\u5051\u611d\u6395\u505d\u509f\u5070\u5044\u509e\u5d01\u681a\u50aa\u50c4\u5114\u4e55\u5072\u657e\u6290\u5061\u509e
		// \u510a\u50e2\u50d9\u4e55\u50d5\u50c4\u5115\u50be\u5075\u61f3\u505f\u5070\u507c\u4e04\u651a\u5ba8\u50aa\u6112\u5054\u505f\u5074\u5044\u4e05InformationAreaTag\u5d8c\u60c9\u5091\u5071\u507a\u61f3\u5f35\u6915\u6704
		// \u50d2\u4e55\u50f6\u61c1\u5071\u507aActionErrors\u5051\u611d\u6395\u505d\u509f\u5070\u5044\u509e\u5d01\u681a\u4e04JavaScript\u50c4\u5114\u4e55\u507c\u61f3\u5fbe\u595c
		styleid = obj.style;
		if((obj.msg != null && obj.msg != "")) {
			styleid.background=Err_COLOR;
		}
		//else if((obj.req == "true") && (obj.readOnly != true)){
		//如果是必填项,则底色一律为黄色 mod by XR 2006/11/1
		else if( obj.req == "true" ){
		
			styleid.background=Req_COLOR;
		}else if((obj.sreq == "true") && (obj.readOnly != true)){
			styleid.background=Sreq_COLOR;
		}else if(obj.readOnly == true){
			styleid.background=Display_COLOR;
		}else{
			styleid.background=Nreq_COLOR;
		}
	}
}


// ****************************************
// CSS Script Behaviors : event handler
//
// Input(text)\u50de\u50cc\u507aonfocus\u50c0\u5100\u511e\u50e9\u5f35\u68df
// ****************************************
function doFocusProcess(obj){
//	getNowDate("doFocusProcess()\u5950\u5dd2\u4e05" + "\u5837\u60a2\u507aname: " + obj.name);
//	alert("doFocusProcess()\u5950\u5dd2\u4e05" + "\u5837\u60a2\u507aname: " + obj.name);

	var wdoc = window.document;
	var wfrm = window.document.forms[0];

	//\u50d0\u5117\u50cb\u50d4\u5111\u511e\u507a\u5fdc\u5d0c\u50d0\u5117\u50cb\u50d4\u5111\u511e\u65a3\u5d0b\u50aa\u50d9\u50e2\u50e9\u5061\u509e
	wfrm.elements["list_start_index"].value=getLineNoSys(obj);

	// 2003/12/05 YUTAKA YOSHIDA \u50d2\u50fd\u590b\u67fa\u61f3\u58b3\u635b\u58db\u4e05
	//\u5c30\u5d7c\u57f5\u62b2\u507a\u67e4\u5fa7\u50aa\u66d0\u61da
	wfrm.elements["focus_name"].value=obj.name;

	// ***********
	// \u5adf\u6360\u507a\u5f35\u68df
	// ***********

	// \u510a\u50e2\u50d9\u4e55\u50d5\u50c4\u5115\u50be\u50aa\u5f36\u5a5c\u58d4
	doWriteInformation("");

	// \u50c0\u511e\u50fc\u50c5\u510a\u4e55\u50d4\u5111\u511e\u50c4\u5115\u50be\u507a\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u661e\u5e35
	//var infoarea = window.document.forms[1].infomation_area;
	//if (infoarea.msg != null && infoarea.msg != "") {
		// \u50c0\u511e\u50fc\u50c5\u510a\u4e55\u50d4\u5111\u511e\u50c4\u5115\u50be\u5075\u5d01\u681a\u5075\u660d\u506f\u5050\u5074\u5044\u510a\u50e2\u50d9\u4e55\u50d5\u5051\u5041\u509c\u5050\u5060\u5094\u611d\u6395\u505d\u509f\u5070\u5044\u509e\u5fdc\u5d0c
	//	doWriteInformation(infoarea.msg);
	//}

	// \u50d2\u4e55\u50f6\u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e85\u50d5\u50aa\u661e\u5e35
	if(obj.msg != null && obj.msg != "") {
		doWriteInformation(obj.msg);
	}

	// \u5d01\u681a\u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e85\u50d5\u50aa\u661e\u5e35
	if(obj.fieldmsg != null && obj.fieldmsg != "") {
		doWriteInformation(obj.fieldmsg);
	}


	// ***********************
	// \u50e5\u50c9\u50d7\u50e9\u50de\u50cc\u507a\u5fdc\u5d0c\u507a\u5f35\u68df
	// ***********************
	if(obj.type == "text"){

		//\u5c30\u5d7c\u62a3\u50aa\u50cc\u5118\u4e55\u50f6\u5116\u66c4\u60a2\u5075\u66d0\u61da
		//	alert("\u5c30\u5d7c\u62a3: " + obj.value);
			onfocus_value=obj.value;
		//	alert("\u66d0\u61da\u505f\u5068\u62a3\u4e17 " + onfocus_value);

		// \u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5f35\u68df\u50aa\u5cf4\u5046
		// \u61f3\u5fbe\u5051readonly\u5071\u5041\u509e\u5fdc\u5d0c\u4e04\u67cd\u5e07\u4e05
		if(obj.readOnly!=true) {
			// \u5d01\u681a\u507a\u50c4\u5114\u4e55\u50fc\u5114\u50cc\u5051\u68eb\u506d\u5070\u5044\u509e\u5fdc\u5d0c\u4e04\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u507c\u5cf4\u50a2\u5074\u5044
			if(obj.fielderrorflg!="false"){
//				getNowDate("\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u505f\u5091\u5061");
				obj.value= jsDoUnformatSys(obj);
			}
		}
		// \u651a\u5ba8\u6013\u50aa\u66c4\u5ccf\u505f\u4e04\u6156\u6230\u5fec\u61fa\u5075\u5061\u509e
		obj.style.background=Selected_COLOR;
		if (!obj.readOnly) {
			// \u6489\u5ea2\u6131\u68a1\u5071\u5074\u5044\u5fdc\u5d0c\u507c\u6156\u6230\u5fec\u61fa\u5075\u5061\u509e
			obj.select();
		}
	}

	// ***********************
	// \u50d9\u5117\u50cb\u50e9\u50de\u50cc\u507a\u5fdc\u5d0c\u507a\u5f35\u68df
	// ***********************
	if(obj.tagName == "SELECT"){
		//\u5c30\u5d7c\u62a3\u50aa\u50cc\u5118\u4e55\u50f6\u5116\u66c4\u60a2\u5075\u66d0\u61da
		var selectedName = obj.name;
//	\u50e5\u50d7\u50e9
//		alert("\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507aname\u61cf\u60c8: " + selectedName);
		var selectedIndex = obj.selectedIndex;
//	\u50e5\u50d7\u50e9
//		alert("\u6156\u6230\u505d\u509f\u5070\u5044\u509e\u5d01\u681a\u507a\u5d01\u681a\u65a3\u5d0b\u4e17 " + selectedIndex);
		var selectedValue = eval("obj.options[" + selectedIndex + "].value");
//	\u50e5\u50d7\u50e9
//		alert("\u5c30\u5d7c\u62a3: " + selectedValue);
		onfocus_value=selectedValue;
//	\u50e5\u50d7\u50e9
//		alert("\u66d0\u61da\u505f\u5068\u62a3\u4e17 " + onfocus_value);

		// \u651a\u5ba8\u6013\u50aa\u66c4\u5ccf
		obj.style.background=Selected_COLOR;
	}

	// *******************************
	// \u50e0\u50c3\u50e2\u50cb\u5103\u50e2\u50cb\u50d7\u50de\u50cc\u507a\u5fdc\u5d0c\u507a\u5f35\u68df
	// *******************************
	if(obj.type == "checkbox"){
		// \u651a\u5ba8\u6013\u50aa\u66c4\u5ccf
		//obj.style.background=Selected_COLOR;
	}

	// *******************************
	// \u5114\u50d5\u50c6\u5103\u50de\u511e\u50de\u50cc\u507a\u5fdc\u5d0c\u507a\u5f35\u68df
	// *******************************
	if(obj.type == "radio"){
		// \u651a\u5ba8\u6013\u50aa\u66c4\u5ccf
		obj.style.background=Selected_COLOR;
	}

	// *******************************
	// \u50f7\u50d7\u511a\u4e55\u50ea\u50de\u50cc\u507a\u5fdc\u5d0c\u507a\u5f35\u68df
	// *******************************
	if(obj.type == "password"){
		// \u651a\u5ba8\u6013\u50aa\u66c4\u5ccf\u505f\u4e04\u6156\u6230\u5fec\u61fa\u5075\u5061\u509e
		obj.style.background=Selected_COLOR;
		if (!obj.readOnly) {
			// \u6489\u5ea2\u6131\u68a1\u5071\u5074\u5044\u5fdc\u5d0c\u507c\u6156\u6230\u5fec\u61fa\u5075\u5061\u509e
			obj.select();
		}
	}

	if(obj.tagName == "TEXTAREA"){
		obj.style.background=Selected_COLOR;
	}
}


// ****************************************
// CSS Script Behaviors : event handler
//
// Input(text)\u50de\u50cc\u507aonblur\u50c0\u5100\u511e\u50e9\u5f35\u68df
// ****************************************
function doBlurProcess(obj){
//	getNowDate("doBlurProcess()\u5950\u5dd2\u4e05" + "\u5837\u60a2\u507aname: " + obj.name);
//	alert("doBlurProcess()\u5950\u5dd2\u4e05" + "\u5837\u60a2\u507aname: " + obj.name);
	var wdoc = window.document;

	// ***********************
	// \u50e5\u50c9\u50d7\u50e9\u50de\u50cc\u507a\u5fdc\u5d0c\u507a\u5f35\u68df
	// ***********************
	if(obj.type=="text"){
		// \u61f3\u5fbe\u5051readonly\u5071\u5041\u509e\u5fdc\u5d0c\u4e04\u67cd\u5e07\u4e05
		if(obj.readOnly != true) {
			// \u61cf\u60c8fieldtype\u5075\u61f3\u58b3\u5061\u509e\u6268\u5d01\u681a\u50e0\u50c3\u50e2\u50cb\u5f35\u68df
//	\u50e5\u50d7\u50e9
//			alert("name: " + obj.name + " \u507a\u6268\u5d01\u681a\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u5044\u5091\u5061\u4e05");
			doFieldCheck(obj);
			// \u6268\u5d01\u681a\u50c4\u5114\u4e55\u5051\u61da\u5d7c\u505f\u5068\u5fdc\u5d0c\u4e04\u5f35\u68df\u50aa\u62de\u6290\u5061\u509e
			if(obj.fielderrorflg=="false"){
				return;
			}

			// \u50fc\u50c5\u4e55\u50c7\u50d7\u6573\u5057text\u507a\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5f35\u68df
//			getNowDate("\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u505f\u5091\u5061\u4e05");
			obj.value=jsDoFormatSys(obj);
		}
		// text\u66a5\u5e24\u6013\u4e12\u651a\u5ba8\u6013\u507a\u611d\u6395
		SetBgcolor(obj);
		// \u67e7\u5d76\u6679\u66c4\u5ccf\u50fc\u5114\u50cc\u60c2\u5c7c\u4e05
		doSetChangeValueFlg(obj);
	}


	// ******************************
	// \u50d9\u5117\u50cb\u50e9\u5103\u50e2\u50cb\u50d7\u50de\u50cc\u507a\u5fdc\u5d0c\u507a\u5f35\u68df
	// ******************************
	if(obj.tagName=="SELECT"){
		// \u67e7\u5d76\u6679\u66c4\u5ccf\u50fc\u5114\u50cc\u60c2\u5c7c\u4e05
		doSetChangeValueFlg(obj);
		// \u651a\u5ba8\u6013\u507a\u611d\u6395
		SetBgcolor(obj);
	}

	// *******************************
	// \u50e0\u50c3\u50e2\u50cb\u5103\u50e2\u50cb\u50d7\u50de\u50cc\u507a\u5fdc\u5d0c\u507a\u5f35\u68df
	// *******************************
	if(obj.type == "checkbox"){
		// \u651a\u5ba8\u6013\u507a\u611d\u6395
		//SetBgcolor(obj);
	}

	// *******************************
	// \u5114\u50d5\u50c6\u5103\u50de\u511e\u50de\u50cc\u507a\u5fdc\u5d0c\u507a\u5f35\u68df
	// *******************************
	if(obj.type == "radio"){
		// \u651a\u5ba8\u6013\u507a\u611d\u6395
		SetBgcolor(obj);
	}

	// *******************************
	// \u50f7\u50d7\u511a\u4e55\u50ea\u50de\u50cc\u507a\u5fdc\u5d0c\u507a\u5f35\u68df
	// *******************************
	if(obj.type == "password"){
		// \u651a\u5ba8\u6013\u507a\u611d\u6395
		SetBgcolor(obj);
	}

	if(obj.tagName=="TEXTAREA"){
		if(obj.readOnly != true) {
			doFieldCheck(obj);
			if(obj.fielderrorflg=="false"){
				return;
			}

			obj.value=jsDoFormatSys(obj);
		}

		SetBgcolor(obj);

		doSetChangeValueFlg(obj);
	}
}


// ****************************************
// CSS Script Behaviors : event handler
//
// Input(button)\u50de\u50cc\u507aonclick\u50c0\u5100\u511e\u50e9\u5f35\u68df
// \u5c30\u5d7c\u507c\u5dca\u68a1\u505f\u5070\u5044\u5074\u5044
// ****************************************
function doButtonClickProcess(obj){
}


// ****************************************
// CSS Script Behaviors : event handler
//
// Input(radio)\u50de\u50cc\u507aonclick\u50c0\u5100\u511e\u50e9\u5f35\u68df
// \u5c30\u5d7c\u507c\u5dca\u68a1\u505f\u5070\u5044\u5074\u5044
// ****************************************
function doRadioClickProcess(obj){
}


// ****************************************
// JavaScript : \u67e7\u5d76 \u6873\u5ca0\u5cf4\u657e\u6395
//
// \u61f3\u5fbe\u5cf4\u5075\u61f3\u505f\u5070\u4e04
// \u5d5f\u5f36\u507a\u65b2readonly\u4e04\u64d6\u6921\u6601\u6075\u5d01\u681a\u5075\u64d6\u6921\u5051\u5041\u509f\u507d\u6873\u5ca0\u5cf4\u5072\u505f\u5070\u5092\u5074\u5061\u4e05
// \u6873\u5ca0\u5cf4\u5071\u5041\u509f\u507dtrue\u4e04\u5066\u5046\u5071\u5074\u5057\u509f\u507dfalse\u50aa\u66c9\u5061\u4e05
// ****************************************
function doCheckActiveLine(divobj) {
//	\u50e5\u50d7\u50e9
//	alert("doCheckActiveLine()\u5950\u5dd2\u4e05\u5837\u60a2\u4e17 " + lineno);

	var checkLineObj;
	var i,ii;

	checkLineObj=divobj.all.tags("INPUT");
//	\u50e5\u50d7\u50e9
//	alert("checkLineObj\u4e17 " + checkLineObj);
	ii=checkLineObj.length;

//	\u50e5\u50d7\u50e9
//	alert("\u5826\u5cf4\u507a\u5d01\u681a\u60a2\u4e17 " + ii);

	// \u61f3\u5fbe\u5072\u5074\u509e\u5cf4\u507a\u64d6\u6921\u5d01\u681a\u5075\u61f3\u505f\u5070\u4e04\u612d\u6462\u5050\u509c\u50e0\u50c3\u50e2\u50cb\u5061\u509e
	for(i=0;i<ii;i++){
		if (!checkLineObj[i].readOnly && checkLineObj[i].req == "true") {
			// \u6489\u5ea2\u6131\u68a1\u5050\u506e\u6601\u6075\u507a\u5fdc\u5d0c
			var type= checkLineObj[i].fieldtype;
			var value = checkLineObj[i].value;
			if (type == "number" || type == "commanumber" || type == "money") {
				// \u60a2\u62a3\u5b86\u50fc\u50bf\u4e55\u5116\u50ea\u507a\u5fdc\u5d0c
				if (value == null || value =="" || parseFloat(value) == 0) {
					// \u64d6\u6921\u50eb\u50d4\u4e040\u507c\u67b9\u64d6\u6921\u5072\u5092\u5074\u505f\u5070\u4e04\u67cd\u5ca0\u5cf4
					return false;
				} else {
					return true;
				}
			} else {
				// \u60a2\u62a3\u5b86\u5071\u5074\u5044\u5fdc\u5d0c
				if(checkLineObj[i].value!=="") {
					return true;
				}else{
					return false;
				}
			}
		}
	}
}

// ****************************************
// JavaScript : \u67e7\u5d76\u4e12\u5ddc\u6395\u5117\u50d0\u4e55\u50eafocus\u63d4\u68a1
//
// \u5ddc\u6395\u505f\u5068\u50c0\u511e\u50e8\u50e2\u50cb\u50d7\u507a\u5117\u50d0\u4e55\u50ea\u507a\u5d5f\u5f36\u507a\u65b2readonly\u4e04
// \u64d6\u6921\u6601\u6075\u5d01\u681a\u50aa\u6156\u6230\u4e04\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u63d4\u68a1\u5061\u509e\u4e05
// ****************************************
function doRecSelectFocus(lineno){
	var recObj;
	var i,ii;
	recObjs=window.DIV_RECORD(lineno).all.tags("INPUT");
	ii=recObjs.length;
	for(i=0;i<ii;i++){
		if(recObjs(i).readOnly != true && recObjs(i).req == "on") {
			objname=String(recObjs(i).name);
			cstr="window.document.all." + objname + "(" + lineno + ").select()";
			eval(cstr);
			cstr="window.document.all." + objname + "(" + lineno + ").focus()";
			eval(cstr);
			return;
		}
	}
}

// ****************************************
// JavaScript : \u67e7\u5d76\u4e12\u66c4\u5ccf\u50fc\u5114\u50cc\u611d\u6395
//
// \u50c0\u5100\u511e\u50e9\u656a\u60d7\u57f5\u62b2\u507a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u50aa\u5ea2\u647c\u4e04
// focus\u5e2a\u5072\u62a3\u5051\u66c4\u50a2\u506d\u5070\u5044\u509e\u5fdc\u5d0c\u4e04hidden\u50de\u50cc\u5075\u611d\u6395\u5061\u509e\u4e05
//
// \u4e82change_flg\u4e83
// \u615c\u5d01\u4e12\u5e2b\u5d01\u5f35\u68df\u507a\u5d7a\u5075\u4e04\u67e4\u5d76\u6679\u507a\u62a3\u5051\u66c4\u5ccf\u505d\u509f\u5070\u5044\u5068\u5fdc\u5d0c
// \u62a3\u50aa\u650b\u5a5e\u5061\u509e\u5050\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u661e\u5e35\u5061\u509e\u6601\u68ab\u5051\u5041\u509d\u4e04\u5066\u507a\u5ddc\u6617\u5072\u5074\u509e\u4e05
// ****************************************
// \u5826\u590b\u67fa\u4e12\u668b\u60a2\u67e7\u5d76\u5075\u61f3\u58b3\u5061\u509e\u5fdc\u5d0c\u4e04\u5059\u507a\u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u5075\u507c\u4e04
// \u5837\u60a2\u5072\u505f\u5070\u4e04\u67e7\u5d76\u6679\u507a\u621d\u699eDIV\u50de\u50cc\u507aID\u67e4\u5fa7\u50aa\u688c\u504a\u509e\u6601\u68ab\u5051\u5041\u509e\u4e05
// \u5059\u509f\u507c\u50c7\u50d7\u50de\u5109\u50de\u50cc\u61c1\u5071\u5095\u5ceb\u6902\u5061\u509e\u508b\u5052\u5069\u50a0\u5046\u4e05
function doSetChangeValueFlg(eventObj) {
	// \u67e7\u5d76\u6679\u5117\u50d0\u4e55\u50ea\u6268\u57f5\u507aDIV\u50de\u50ccID\u67e4\u5fa7\u4e05
	var parent_div_id = "DIV_RECORD";
	var beforeVal;
	var afterVal;

	// \u67e7\u5d76\u50c4\u5115\u50be\u64aa\u57f2\u595c\u5071\u507c\u5f35\u68df\u505f\u5074\u5044
	if(eventObj.parentElement.id != parent_div_id) {
		return;
	}

	// ********************
	// \u50e5\u50c9\u50d7\u50e9\u507a\u5fdc\u5d0c\u507a\u5f35\u68df
	// ********************
	if(eventObj.type=="text" || eventObj.tagName=="TEXTAREA"){
		beforeVal = onfocus_value;
		afterVal  = eventObj.value;
		if(beforeVal != afterVal) {
			window.document.forms[0].elements["change_flg"].value = "true";
		}
	}

	// ***************************
	// \u50d9\u5117\u50cb\u50e9\u5103\u50e2\u50cb\u50d7\u507a\u5fdc\u5d0c\u507a\u5f35\u68df
	// ***************************
	if(eventObj.tagName=="SELECT"){
		// \u50fc\u50c5\u4e55\u50c7\u50d7\u50c0\u511e\u5e2a\u507a\u62a3
		beforeVal = onfocus_value;

		// \u5c30\u5d7c\u6156\u6230\u505d\u509f\u5070\u5044\u509e\u62a3\u50aa\u5ea2\u647c
		var selectedIndex = eventObj.selectedIndex;
//	\u50e5\u50d7\u50e9
//		alert("\u6156\u6230\u505d\u509f\u5070\u5044\u509e\u5d01\u681a\u507a\u5d01\u681a\u65a3\u5d0b\u4e17 " + selectedIndex);
		afterVal = eval("eventObj.options[" + selectedIndex + "].value");

		// \u6909\u5e70\u50aa\u65be\u5991
		if(beforeVal != afterVal) {
			window.document.forms[0].elements["change_flg"].value = "true";
		}
	}
}

// ****************************************
// JavaScript : \u67e7\u5d76\u4e12\u50c7\u4e55\u50dc\u5116\u5cf4\u507a\u5cf4\u65a3\u5d0b\u5ea2\u647c
//
// \u5ddc\u6395\u505f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507a\u5cf4\u65a3\u5d0b\u50aa\u5ea2\u647c\u5061\u509e\u4e05
// ****************************************
function getLineNoSys(obj){
	var i,col;
	var tmpObj,tmpNum;
	var linno=-1;

	tmpObj=document.all.item(obj.name);
	col=tmpObj.length;
	if(col){
		tmpNum=obj.uniqueNumber;
		for(i=0;i<col;i++){
			if (tmpNum==tmpObj(i).uniqueNumber){
				linno=i;
				break;
			}
		}
	}
//	\u50e5\u50d7\u50e9
//	alert("\u5cf4No.: " + linno);
	return linno;
}


// ****************************************
// JavaScript : \u50d2\u50fd\u5108\u50e2\u50e9\u62de\u590b\u67fa\u60c2\u5c7c\u68b7\u60c2
//
// ****************************************
function doOpenWaitFillter() {
//	\u50e5\u50d7\u50e9
//	alert("doOpenWaitFillter()\u5950\u5dd2");

	var doc = window.document;
	var information = null

    // \u504d\u62a6\u509c\u5063\u50c0\u510a\u4e55\u50d5\u507a\u597c\u621d\u4e04\u57f5\u62b2\u580f\u6466
/*
    var img_w = 200;
    var img_h = 100;
    var styleid = document.all("WAIT_IMG");
	styleid.style.width  = img_w;
	styleid.style.height = img_h;
	styleid.style.left   = (800 - img_w) / 2;
	styleid.style.top    = (600 - img_h) / 2;

	// \u50fc\u50bf\u5116\u50de\u4e55\u50c0\u510a\u4e55\u50d5\u507a\u597c\u621d
	document.all("FILTER_IMG").style.height = 600;
	document.all("FILTER_IMG").style.width  = 800;
*/

	// \u50c0\u511e\u50fc\u50c5\u510a\u4e55\u50d4\u5111\u511e\u50c4\u5115\u50be\u5075\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u661e\u5e35
	doWriteInformation(id0001);
}


// ****************************************
// JavaScript : window\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u5051\u50e8\u4e55\u50de\u50aa
//              \u5118\u4e55\u50ea\u505f\u5ed4\u50a2\u506d\u5070\u5044\u509e\u5fdc\u5d0c\u4e04
//          \u4e02\u4e02true\u50aa\u66c9\u5061\u4e05
// 			\u4e02\u4e02\u5118\u4e55\u50ea\u505f\u5ed4\u50a2\u506d\u5070\u5044\u5074\u5044\u5fdc\u5d0c\u507c
//\u4e02\u4e02\u4e02\u4e02\u4e02\u4e02\u4e02 false\u50aa\u66c9\u5061\u4e05
//
// ****************************************
function checkWindowState() {
	var state = window.document.readyState;
//	\u50e5\u50d7\u50e9
//	alert(state);

	if(state != "complete") {
		return false;
	}
	return true;
}


// ****************************************
// JavaScript : \u6161\u5d01\u681a\u6601\u6075\u50e0\u50c3\u50e2\u50cb
//
// req\u61cf\u60c8\u5051on\u5071\u5041\u509e\u6161\u5d01\u681a\u507a\u6601\u6075\u50e0\u50c3\u50e2\u50cb
// \u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50c4\u5114\u4e55\u5051\u5d0c\u506d\u5068\u5fdc\u5d0c\u507c\u4e04false\u50aa\u66c9\u5061\u4e05
// ****************************************
function doReqCheck(){
//	\u50e5\u50d7\u50e9
//	alert("doReqCheck()\u5950\u5dd2\u4e05");
//	getNowDate("doReqCheck()\u5950\u5dd2\u4e05");

	var i;
	var array;
	var array_length;
	var check_flg;
	var element;

	// req="true"\u5072\u5ddc\u6395\u505f\u5070\u5041\u509e\u5d01\u681a\u67e4\u50aa\u651d\u694d\u5071\u5ea2\u647c
	array = getRequiredProperties();

	array_length = array.length;
//	\u50e5\u50d7\u50e9
//	alert("\u6601\u6075\u5d01\u681a\u60a2\u4e17 " + array_length);

	for(i=0;i<array_length;i++){
		element = array[i];
//	\u50e5\u50d7\u50e9
//		alert("\u61f3\u5fbe\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u4e17 " + element);
		// \u597a\u5d01\u681a\u5075\u61f3\u505f\u5070\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u5046
		if(!doCheckElement(element)){
			// \u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50c4\u5114\u4e55\u5051\u5041\u506d\u5068\u5fdc\u5d0c\u507c\u5ed4\u6906
			return false;
		}
	}
	return true;
}



// ****************************************
// JavaScript : \u597a\u5d01\u681a\u507a\u6601\u6075\u50e0\u50c3\u50e2\u50cb
//
// req\u61cf\u60c8\u5051on\u5071\u5041\u509e\u5d01\u681a\u5075\u61f3\u505f\u5070\u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u5046
// \u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50c4\u5114\u4e55\u5051\u5041\u506d\u5068\u5fdc\u5d0c\u507c\u4e04false\u50aa\u66c9\u5061\u4e05
// ****************************************
function doCheckElement(obj){

//	\u50e5\u50d7\u50e9
//	alert("doCheckElement()\u5950\u5dd2");
//	getNowDate("doCheckElement()\u5950\u5dd2");

	// \u5837\u60a2\u5072\u505f\u5070\u6409\u505d\u509f\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u5051\u67e7\u5d76\u6679\u507a\u5095\u507a\u5050\u657e\u6395
	if(obj.length){
		// \u61f3\u5fbe\u5051\u50d9\u5117\u50cb\u50e9\u5103\u50e2\u50cb\u50d7\u5074\u509c\u4e04\u50d9\u5117\u50cb\u50e9\u5103\u50e2\u50cb\u50d7\u68a1\u507a\u5a2d\u60a2\u50aa\u5c47\u5083\u5f0c\u5061
		if(obj.tagName=="SELECT"){
			return doReqCheckSelectTag(obj);
		}
		else{
			// \u67e4\u5d76\u6679\u507a\u5d01\u681a\u5075\u61f3\u5061\u509e\u6601\u6075\u50e0\u50c3\u50e2\u50cb
			return doIterateCheck(obj);
		}
	}
	else{
		// \u61f3\u5fbe\u5051\u50d9\u5117\u50cb\u50e9\u5103\u50e2\u50cb\u50d7\u5074\u509c\u4e04\u50d9\u5117\u50cb\u50e9\u5103\u50e2\u50cb\u50d7\u68a1\u507a\u5a2d\u60a2\u50aa\u5c47\u5083\u5f0c\u5061
		if(obj.tagName=="SELECT"){
			return doReqCheckSelectTag(obj);
		}
		else{
			// \u50ff\u50e2\u50df\u4e55\u5d01\u681a\u5075\u61f3\u5061\u509e\u6601\u6075\u50e0\u50c3\u50e2\u50cb
			return doHeaderCheck(obj);
		}
	}
}


// ****************************************
// JavaScript : \u67e7\u5d76\u5d01\u681a\u507a\u6601\u6075\u50e0\u50c3\u50e2\u50cb
//
// req\u61cf\u60c8\u5051on\u5071\u5041\u509e\u5d01\u681a\u5075\u61f3\u505f\u5070\u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u5046
// \u6601\u6075\u50e0\u50c3\u50e2\u50cb\u5075\u5837\u506d\u5050\u5050\u506d\u5068\u5fdc\u5d0c\u507cfalse
// \u5066\u509f\u57f2\u595c\u507ctrue\u4e6e\u61f3\u5fbe\u5071\u5074\u5044\u5d01\u681a\u507a\u5fdc\u5d0c\u5095true\u4e6f
// ****************************************
function doIterateCheck(obj){
//	alert("doIterateCheck()");
	var length = obj.length;
	var element;
	var i;
	var check_flg = true;
	var active_flg = true;

//	\u50e5\u50d7\u50e9
//	alert("\u67e7\u5d76\u5d01\u681a\u507a\u68ab\u617a\u60a2\u4e17 " + length);

	for(i=0;i<length;i++){
		element = obj[i];
//	\u50e5\u50d7\u50e9
//		alert("\u5d01\u681a\u4e17 " + element.name + " \u507c\u67e7\u5d76\u6679\u507a" + i +" \u5cf4\u681a\u5071\u5061\u4e05");

		if (element.disabled==false){
			// \u61f3\u5fbe\u5051\u6873\u5ca0\u5cf4\u5050\u5073\u5046\u5050\u657e\u6395
			var parentDiv = getParentDiv(element);
			active_flg = doCheckActiveLine(parentDiv);
//	\u50e5\u50d7\u50e9
//			alert("\u6873\u5ca0\u5cf4\u5071\u5061\u5050\u4e20\u4e17 " + active_flg);

			if(active_flg){
				// \u61f3\u5fbe\u5051\u6873\u5ca0\u5cf4\u5074\u507a\u5071\u4e04\u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u5044\u5091\u5061\u4e05
				// \u61f3\u5fbe\u5051\u50d9\u5117\u50cb\u50e9\u5103\u50e2\u50cb\u50d7\u507a\u5fdc\u5d0c\u4e04\u50d9\u5117\u50cb\u50e9\u5103\u50e2\u50cb\u50d7\u68a1\u507a\u5a2d\u60a2\u50aa\u5c47\u5083\u5f0c\u5061
				if(element.tagName=="SELECT"){
					check_flg = doReqCheckIterateSelectTag(element);
				}
				// \u50e5\u50c9\u50d7\u50e9\u50de\u50cc\u507a\u5fdc\u5d0c
				else if(element.type=="text"||element.type=="textarea"){
//	\u50e5\u50d7\u50e9
//					alert(i + "\u5cf4\u681a\u507a\u5d01\u681a\u507a\u62a3\u507c\u4e17 " + element.value);
					if(element.value.replace(/(^\s*)|(\s*$)/g, "")=="" || element.value==null){
						check_flg = false;
					}else if(element.type=="text" ||element.type=="textarea"){
						// \u64d6\u6921\u62a3\u5051\u61da\u5d7c\u505f\u5068\u5fdc\u5d0c\u4e04\u50e5\u50c9\u50d7\u50e9\u50de\u50cc\u5074\u509c\u61e8\u6449\u60c8\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u5044\u5091\u5061\u4e05
						check_flg = doReqcheckValidate(element);
					}
				}
			}
			else{
				// \u6873\u5ca0\u5cf4\u5071\u5074\u5044\u5fdc\u5d0c\u507c\u61f3\u5fbe\u595c
				check_flg = true;
			}
		}
		else{
			// \u5d01\u681a\u5051\u67cd\u5ca0\u58d4\u505d\u509f\u5070\u5044\u5068\u5fdc\u5d0c\u507c\u61f3\u5fbe\u595c
			check_flg = true;
		}

//	\u50e5\u50d7\u50e9
//		alert("check_flg\u4e17 " + check_flg);

		// \u50c4\u5114\u4e55\u5051\u656a\u5c12\u505d\u509f\u5068\u629c\u5952\u5071\u5ed4\u6906
		// \u50c4\u5114\u4e55\u507a\u5d01\u681a\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u6449\u5070\u509e
		if(check_flg==false){
			// \u510a\u50e2\u50d9\u4e55\u50d5\u507a\u661e\u5e35
//			alert(reqcheck_message);
			doWriteErrorMessage(id0002, element);
			element.focus();
			return false;
		}
	}

	return true;
}


// ****************************************
// JavaScript : \u50ff\u50e2\u50df\u4e55\u5d01\u681a\u507a\u6601\u6075\u50e0\u50c3\u50e2\u50cb
//
// req\u61cf\u60c8\u5051on\u5071\u5041\u509e\u5d01\u681a\u5075\u61f3\u505f\u5070\u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u5046
// \u6601\u6075\u50e0\u50c3\u50e2\u50cb\u5075\u5837\u506d\u5050\u5050\u506d\u5068\u5fdc\u5d0c\u507cfalse
// \u5066\u509f\u57f2\u595c\u507ctrue\u4e6e\u61f3\u5fbe\u5071\u5074\u5044\u5d01\u681a\u507a\u5fdc\u5d0c\u5095true\u4e6f
// ****************************************
function doHeaderCheck(obj){
//	alert("doHeaderCheck()\u5950\u5dd2");
	var check_flg = true;
//	\u50e5\u50d7\u50e9


	if (obj.disabled==false){

		// \u61f3\u5fbe\u5051text\u507a\u5fdc\u5d0c
		if(obj.type=="text" ||obj.type=="textarea"){
			if(obj.value.replace(/(^\s*)|(\s*$)/g, "")=="" || obj.value==null){
				check_flg = false;
			}
			else if(obj.type=="text" ||obj.type=="textarea"){
				// \u64d6\u6921\u62a3\u5051\u61da\u5d7c\u505f\u5068\u5fdc\u5d0c\u4e04\u50e5\u50c9\u50d7\u50e9\u50de\u50cc\u5074\u509c\u61e8\u6449\u60c8\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u5044\u5091\u5061\u4e05
				check_flg = doReqcheckValidate(obj);
			}
		}

		if(obj.type=="password"){
			if(obj.value=="" || obj.value==null){
				check_flg = false;
			}
			else if(obj.type=="text"){
				check_flg = true;
			}
		}
	}
	else{
		// \u5d01\u681a\u5051\u67cd\u5ca0\u58d4\u505d\u509f\u5070\u5044\u509e\u5fdc\u5d0c\u507c\u61f3\u5fbe\u595c
		check_flg = true;
	}

//	\u50e5\u50d7\u50e9
//	alert("\u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50c4\u5114\u4e55\u5071\u5041\u509f\u507dfalse\u4e17 " + check_flg);
	// \u50c4\u5114\u4e55\u5051\u656a\u5c12\u505d\u509f\u5068\u629c\u5952\u5071\u5ed4\u6906
	// \u50c4\u5114\u4e55\u507a\u5d01\u681a\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u6449\u5070\u509e
	if(check_flg==false){
//		alert(reqcheck_message);
		doWriteErrorMessage(id0002, obj);
		obj.focus();
	}

	return check_flg;
}



// ********************************************
// JavaScript: \u6601\u6075\u5d01\u681a\u50e0\u50c3\u50e2\u50cb\u5e2a\u507a\u61e8\u6449\u60c8\u50e0\u50c3\u50e2\u50cb
//
// \u6601\u6075\u50e0\u50c3\u50e2\u50cb\u507a\u5d7a\u5075\u4e04\u64d6\u6921\u62a3\u5051\u5b3b\u5071\u5074\u5044\u5072\u5052\u5075\u5cf4\u5046\u61e8\u6449\u60c8\u50e0\u50c3\u50e2\u50cb
//
// autor ryo masuda
// ********************************************
function doReqcheckValidate(obj){
//	\u50e5\u50d7\u50e9
//	alert("doReqcheckValidate()\u5950\u5dd2");

	var num;
	var target = obj.value;

//	\u50e5\u50d7\u50e9
//	alert("fieldtype: " + obj.fieldtype);

	// fieldtype\u5075\u509b\u506d\u5070\u50e0\u50c3\u50e2\u50cb\u50aa\u61fc\u504a\u509e
	switch(obj.fieldtype){
		case "number":
		case "money":
		case "commanumber":
//	\u50e5\u50d7\u50e9
//			alert("number\u5b86\u4e04\u5095\u505f\u5054\u507cmoney\u5b86\u5071\u5061\u4e05");
			// fieldtype\u5051number\u5b86\u4e04money\u5b86\u507a\u5fdc\u5d0c

			// \u665c\u6466\u5f6b\u60a2\u5075\u66c4\u59fa
			num = parseFloat(target);

			// \u4fbd\u507a\u5fdc\u5d0c\u507c\u67b9\u64d6\u6921\u5072\u5092\u5074\u5061
			if(num==0){
				return false;
			}
			else{
				return true;
			}
			break;

		default:
			return true;
			break;
	}

}


// ********************************************
// JavaScript: \u50d9\u5117\u50cb\u50e9\u5103\u50e2\u50cb\u50d7\u68a1\u6601\u6075\u50e0\u50c3\u50e2\u50cb\u5a2d\u60a2
//
// \u61f3\u5fbe\u507a\u50d9\u5117\u50cb\u50e9\u5103\u50e2\u50cb\u50d7\u5071\u6156\u6230\u505d\u509f\u5068\u62a3\u5051null\u5074\u509c
// \u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50c4\u5114\u4e55
// \u50c4\u5114\u4e55\u5e2a\u507c\u4e04false\u50aa\u66c9\u5061\u4e05
//
// 2004/08/06
// autor ryo masuda
// ********************************************
function doReqCheckSelectTag(obj){
//	alert("doReqCheckSelectTag()\u5950\u5dd2");
	var check_flg = false;

//	\u50e5\u50d7\u50e9
//	alert("\u50d9\u5117\u50cb\u50e9\u50de\u50cc\u507aname\u61cf\u60c8\u62a3: " + obj.name);
	var selectedIndex = obj.selectedIndex;
//	\u50e5\u50d7\u50e9
//	alert("\u6156\u6230\u505d\u509f\u5070\u5044\u509e\u5d01\u681a\u507a\u5d01\u681a\u65a3\u5d0b\u4e17 " + selectedIndex);
	var selectedValue = eval("obj.options[" + selectedIndex + "].value");
//	\u50e5\u50d7\u50e9
//	alert("\u6156\u6230\u505d\u509f\u5070\u5044\u509e\u5d01\u681a\u507a\u62a3: " + selectedValue);

	// \u6156\u6230\u505d\u509f\u5068\u62a3\u5051"null"\u5074\u509c\u4e04\u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50c4\u5114\u4e55
	if(selectedValue == "null" || selectedValue==""){
//		alert("\u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50c4\u5114\u4e55\u5071\u5061\u4e05");
		// \u510a\u50e2\u50d9\u4e55\u50d5\u507a\u661e\u5e35
//		alert(reqcheck_message);
		doWriteErrorMessage(id0002, obj);
		obj.focus();
		return false;
	}
	else{
		return true;
	}
}


// ********************************************
// JavaScript: \u67e4\u5d76\u6679\u50d9\u5117\u50cb\u50e9\u5103\u50e2\u50cb\u50d7\u68a1\u6601\u6075\u50e0\u50c3\u50e2\u50cb\u5a2d\u60a2
//
// \u61f3\u5fbe\u507a\u50d9\u5117\u50cb\u50e9\u5103\u50e2\u50cb\u50d7\u5071\u6156\u6230\u505d\u509f\u5068\u62a3\u5051null\u5074\u509c
// \u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50c4\u5114\u4e55
// \u50c4\u5114\u4e55\u5e2a\u507c\u4e04false\u50aa\u66c9\u5061\u4e05
//
// 2004/08/06
// autor ryo masuda
// ********************************************
function doReqCheckIterateSelectTag(obj){
//	alert("doReqCheckIterateSelectTag()\u5950\u5dd2");

//	\u50e5\u50d7\u50e9
//	alert("\u50d9\u5117\u50cb\u50e9\u50de\u50cc\u507aname\u61cf\u60c8\u62a3: " + obj.name);
	var selectedIndex = obj.selectedIndex;
//	\u50e5\u50d7\u50e9
//	alert("\u6156\u6230\u505d\u509f\u5070\u5044\u509e\u5d01\u681a\u507a\u5d01\u681a\u65a3\u5d0b\u4e17 " + selectedIndex);
	var selectedValue = eval("obj.options[" + selectedIndex + "].value");
//	\u50e5\u50d7\u50e9
//	alert("\u6156\u6230\u505d\u509f\u5070\u5044\u509e\u5d01\u681a\u507a\u62a3: " + selectedValue);

	// \u6156\u6230\u505d\u509f\u5068\u62a3\u5051"null"\u5074\u509c\u4e04\u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50c4\u5114\u4e55
	if(selectedValue == "null"){
//		alert("\u6601\u6075\u50e0\u50c3\u50e2\u50cb\u50c4\u5114\u4e55\u5071\u5061\u4e05");
		return false;
	}
	else{
		return true;
	}
}


// ********************************************
// JavaScript: \u50c4\u5114\u4e55\u5dc6\u50e0\u50c3\u50e2\u50cb\u5a2d\u60a2
//
// \u50ff\u50e2\u50df\u4e55\u6679\u68a1\u5a2d\u60a2\u5072\u67e4\u5d76\u6679\u68a1\u5a2d\u60a2\u5075\u5f35\u68df\u50aa\u57fe\u5ff3\u5061\u509e
// \u50c4\u5114\u4e55\u5dc6\u5051\u61da\u5d7c\u505f\u5068\u5fdc\u5d0c\u4e04\u6820\u509d\u62a3\u507cfalse.
// \u50c4\u5114\u4e55\u5dc6\u5051\u61da\u5d7c\u505f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u6820\u509d\u62a3\u507ctrue.
//
// 2004/07/13
// autor ryo masuda
// ********************************************
function doLeftErrorCheck(){
//	alert("doLeftErrorCheck");
//	getNowDate("doLeftErrorCheck");

	// \u50ff\u50e2\u50df\u4e55\u6679\u507a\u50c4\u5114\u4e55\u5dc6\u50e0\u50c3\u50e2\u50cb
	if(!doHeaderLeftErrorCheck()){
		return false;
	}

	// \u67e4\u5d76\u6679\u507a\u50c4\u5114\u4e55\u5dc6\u50e0\u50c3\u50e2\u50cb
	if(!doIterateLeftErrorCheck()){
		return false;
	}

//	getNowDate("doErrorCheck()\u5ed4\u6906");
	return true;
}




// ********************************************
// JavaScript: \u50ff\u50e2\u50df\u4e55\u6679\u68a1\u50c4\u5114\u4e55\u5dc6\u50e0\u50c3\u50e2\u50cb\u5a2d\u60a2
//
// fielderrorflg="false"\u507a\u5095\u507a\u50aa\u50c4\u5114\u4e55\u5072\u5092\u5074\u5061\u4e05
// \u50c4\u5114\u4e55\u50aa\u5c02\u5f0c\u505f\u5068\u5fdc\u5d0c\u4e04\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u661e\u5e35\u505f\u5f35\u68df\u50aa\u5ed4\u6906\u5061\u509e
// \u50c4\u5114\u4e55\u5dc6\u5051\u61da\u5d7c\u505f\u5068\u5fdc\u5d0c\u4e04\u6820\u509d\u62a3\u507cfalse.
// \u50c4\u5114\u4e55\u5dc6\u5051\u61da\u5d7c\u505f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u6820\u509d\u62a3\u507ctrue.
//
// 2004/07/13
// autor ryo masuda
// ********************************************
function doHeaderLeftErrorCheck(){
//	alert("doHeaderLeftErrorCheck");
	var inputtag=window.document.forms[0].all.tags("INPUT");
	var doc=window.document.forms[0];
	var i, ii;
	var targettag, errorflg, elementsName;

	ii=inputtag.length;

	for(i=0; i<ii; i++){
		targettag = inputtag[i];
		// \u50e5\u50c9\u50d7\u50e9\u50de\u50cc\u57f2\u595c\u507c\u61f3\u5fbe\u595c
		if(targettag.type == "text"){
			elementsName = targettag.name;
			// \u67e4\u5d76\u6679\u5075\u5a2d\u505f\u5070\u507c\u67cd\u5e07
			if(!doc.all(elementsName).length){
				// \u50ff\u50e2\u50df\u4e55\u6679\u507a\u5fdc\u5d0c
				errorflg = targettag.fielderrorflg;
				if(errorflg!=null || errorflg!=""){
					if(errorflg=="false"){
						doWriteErrorMessage(id0009, targettag);
//						alert("\u50c4\u5114\u4e55\u5dc6\u5051\u5041\u509d\u5091\u5061");
						targettag.focus();
						return false;
					}
				}
			}
		}
	}
	return true;
}


// ********************************************
// JavaScript: \u67e4\u5d76\u6679\u68a1\u50c4\u5114\u4e55\u5dc6\u50e0\u50c3\u50e2\u50cb\u5a2d\u60a2
//
// fielderrorflg="false"\u507a\u5095\u507a\u50aa\u50c4\u5114\u4e55\u5072\u5092\u5074\u5061\u4e05
// \u50c4\u5114\u4e55\u50aa\u5c02\u5f0c\u505f\u5068\u5fdc\u5d0c\u4e04\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u661e\u5e35\u505f\u5f35\u68df\u50aa\u5ed4\u6906\u5061\u509e
// \u50c4\u5114\u4e55\u5dc6\u5051\u61da\u5d7c\u505f\u5068\u5fdc\u5d0c\u4e04\u6820\u509d\u62a3\u507cfalse.
// \u50c4\u5114\u4e55\u5dc6\u5051\u61da\u5d7c\u505f\u5074\u5050\u506d\u5068\u5fdc\u5d0c\u4e04\u6820\u509d\u62a3\u507ctrue.
//
// 2004/07/13
// autor ryo masuda
// ********************************************
function doIterateLeftErrorCheck(){
//	alert("doIterateErrorCheck()\u5950\u5dd2");

	var divobj = window.CHILD_DIV_ID;
	var i, ii, j;
	var targetline, targetlength, targettag;
	var errorflg;


	// DIV\u507aNULL\u50e0\u50c3\u50e2\u50cb
	if(divobj == null){
//	\u50e5\u50d7\u50e9
//		alert(DIV_NAME + "\u507c\u61da\u5d7c\u505f\u5091\u5063\u50ab");
		return true;
	}

	// \u67e7\u5d76\u5cf4\u507a\u5cf4\u60a2
	ii = divobj.length;
//	\u50e5\u50d7\u50e9
//	alert("\u67e7\u5d76\u507a\u5cf4\u60a2: " + ii);

	for(i=0; i<ii; i++){
		// \u6873\u5ca0\u5cf4\u5050\u50e0\u50c3\u50e2\u50cb\u5061\u509e
		if(doCheckActiveLine(divobj)){
// \u50e5\u50d7\u50e9
//			alert((i+1) + " \u5cf4\u681a\u507c\u6873\u5ca0\u5cf4\u5071\u5061\u4e05");
			targetline = divobj(i).all.tags("INPUT");
			targetlength = targetline.length;
// \u50e5\u50d7\u50e9
//			alert("\u6873\u5ca0\u5cf4\u507a\u5d01\u681a\u60a2: " + targetlength);
			for(j=0; j<targetlength; j++){
				targettag = targetline[j];
				// \u50e5\u50c9\u50d7\u50e9\u50de\u50cc\u57f2\u595c\u507c\u61f3\u5fbe\u595c
				if(targettag.type=="text"){
					errorflg = targettag.fielderrorflg;
					if(errorflg!=null || errorflg!=""){
						if(errorflg=="false"){
							doWriteErrorMessage(id0009, targettag);
//							alert("\u50c4\u5114\u4e55\u5dc6\u5051\u5041\u509d\u5091\u5061");
							targettag.focus();
							return false;
						}
					}
				}
			}
		}
	}
	return true;
}


// ********************************************
// JavaScript: focus_name\u5075\u597f\u64fa\u505d\u509f\u5070\u5044\u509e\u5d01\u681a\u5075\u61f3\u505f\u5070
// \u6268\u5d01\u681a\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u5046\u5a2d\u60a2
//
// \u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u50c9\u4e55\u58b4\u58d3\u5e2a\u507a\u6644\u5b36\u5d0c\u61f3\u58b3
// \u61f3\u5fbe\u5072\u5074\u509e\u5d01\u681a\u507afielderrorflg\u61cf\u60c8\u507a\u62a3\u50aa\u66c9\u5061
//
// \u4e82\u5dca\u68a1\u693a\u4e83
//	\u5c47\u5083\u5f0c\u505f\u5c26\u5071\u4e04
//	\u5059\u507a\u5a2d\u60a2\u507a\u6820\u509d\u62a3\u5051false\u5074\u509c\u5f35\u68df\u50aa\u62de\u6290\u5061\u509e\u5f35\u68df\u50aa\u5a70\u5f0e\u5061\u509e
//
// 2004/07/16
// autor ryo masuda
// ********************************************
function doPreFocusFieldCheck(target){
//	getNowDate("doPreFocusFieldCheck()\u5950\u5dd2");
//	alert("doPreFocusFieldCheck()\u5950\u5dd2");

	// \u61f3\u5fbe\u5051readonly\u5071\u5041\u509e\u5fdc\u5d0c\u4e04\u67cd\u5e07\u4e05

	if(target==null || target.readOnly == true) {
//\u4e02\u50e5\u50d7\u50e9
//		alert("readonly=true\u5071\u5061");
		return "true";
	}

	// \u5105\u50c2\u50d7\u50cb\u5115\u50e2\u50cb\u5071\u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u5103\u50de\u511e\u50aa\u58b4\u58d3\u505f\u5068\u5fdc\u5d0c
	// \u61f3\u5fbe\u507afielderrorflg\u5051"true"\u507a\u5fdc\u5d0c\u507c\u4e04\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5046
	if(event.type=="click" && target.fielderrorflg=="true"){
//		getNowDate("\u50be\u511e\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5044\u5091\u5061\u4e05");
		target.value= jsDoUnformatSys(target);
	}


//	\u50e5\u50d7\u50e9
//	getNowDate("name: " + target.name + ", value: " + target.value + " \u507a\u6268\u5d01\u681a\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u5044\u5091\u5061\u4e05");
//	getNowDate("onfocus_value: " + onfocus_value);
	// \u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u58b4\u58d3\u5e2a\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u5051\u6449\u5068\u506d\u5070\u5044\u5068\u5d01\u681a\u5075\u61f3\u505f\u5070\u6268\u5d01\u681a\u50e0\u50c3\u50e2\u50cb\u50aa\u5cf4\u5046
	doFieldCheck(target);

	// \u5105\u50c2\u50d7\u50cb\u5115\u50e2\u50cb\u5071\u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u5103\u50de\u511e\u50aa\u58b4\u58d3\u505f\u5068\u5fdc\u5d0c
	// \u6268\u5d01\u681a\u50c4\u5114\u4e55\u5051\u61da\u5d7c\u505f\u5074\u5044\u5fdc\u5d0c\u5075\u507a\u5092\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5f35\u68df\u50aa\u5cf4\u5046
	if(event.type=="click"){
		if(target.fielderrorflg=="true"){
			// \u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u5f35\u68df\u50aa\u5cf4\u5046
//			getNowDate("\u50fc\u50c5\u4e55\u5105\u50e2\u50e9\u50aa\u5cf4\u5044\u5091\u5061\u4e05");
			target.value=jsDoFormatSys(target);
		}
	}

//	getNowDate("doPreFocusFieldCheck()\u5ed4\u6906 fielderrorflg:" + target.fielderrorflg);

	// \u61f3\u5fbe\u5072\u5074\u509e\u5d01\u681a\u507afielderrorflg\u61cf\u60c8\u507a\u62a3\u50aa\u66c9\u5061
	return target.fielderrorflg;
}


// ***************************************************
// JavaScript: \u6348\u615c\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u507a\u5041\u506d\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u50aa\u5ea2\u647c
// \u5ea1\u5075\u4e04\u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u50c9\u4e55\u5e5a\u5cf4\u5e2a\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u5051\u6449\u5068\u506d\u5070\u5044\u509e
// \u5d01\u681a\u507a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u50aa\u5ea2\u647c\u5061\u509e\u5d7a\u5075\u5dca\u68a1\u5061\u509e\u4e05
//
// \u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u507a\u5ea2\u647c\u5075\u5e50\u6515\u505f\u5068\u5fdc\u5d0c\u507cnull\u50aa\u66c9\u5061\u4e05
//
// 2004/09/23
// autor ryo masuda
// ***************************************************
function getPreFocusObject(){
//	getNowDate("getPreFocusObject()\u5950\u5dd2");
	var doc = window.document.forms[0];
	var focusname;
	var prefocus;
	var index;
	var target;

	// \u50c9\u4e55\u58b4\u58d3\u5e2a\u5075focus_name\u5075\u597f\u64fa\u505d\u509f\u5070\u5044\u509e\u62a3\u50aa\u5ea2\u647c
	focusname = doc.focus_name.value;
//	getNowDate("focus_name: " + focusname);
	// focus_name\u5075\u62a3\u5051\u64d6\u506d\u5070\u5044\u5074\u5044\u5e2a\u4e04\u6268\u5d01\u681a\u50e0\u50c3\u50e2\u50cb\u507c\u5cf4\u50a2\u5074\u5044
	if(focusname == null || focusname == "" || focusname == "infomation_area"){
		return null;
	}
	// \u61f3\u5fbe\u507a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u50aa\u5ea2\u647c
	prefocus = eval("window.document.forms[0]." + focusname);
//	getNowDate("prefocus: " + prefocus);

	// \u50ff\u50e2\u50df\u4e55\u6679\u5050\u67e7\u5d76\u6679\u5050\u507a\u657e\u6395
	if(prefocus.length){
		// \u67e7\u5d76\u507aindex\u50aa\u5ea2\u647c
		index = doc.list_start_index.value;

		if(index < 0){
			// \u61f3\u5fbe\u507a\u5d01\u681a\u5051\u50ff\u50e2\u50df\u4e55\u6679\u507a\u5fdc\u5d0c\u4e6e\u5a4e\u676e\u63d1\u5075\u4e04\u5059\u507a\u5fe6\u5be9\u5071\u507cselect\u50de\u50cc\u505f\u5050\u5074\u5044\u507c\u5062\u4e6f
			// \u61f3\u5fbe\u507a\u5d01\u681a\u5051selectBox\u507a\u5fdc\u5d0c
			if(prefocus.tagName=="SELECT"){
				target = prefocus;
			}else{
				alert("FWError:getPreFocusObject()\u5071\u6644\u60d3\u5074\u5f35\u68df\u5051\u5cf4\u50a2\u509f\u5091\u505f\u5068\u4e05\u50d4\u50d7\u50e5\u5109\u5a17\u68df\u5e70\u5075\u6962\u68c8\u505f\u5070\u5054\u5069\u505d\u5044\u4e05");
			}
		}else{
			// \u61f3\u5fbe\u507a\u5d01\u681a\u5051\u67e4\u5d76\u6679\u507a\u5fdc\u5d0c
//	\u50e5\u50d7\u50e9
//			getNowDate("\u67e7\u5d76\u507aindex: " + index);
			target = prefocus[index];
		}
	}else {
		// \u61f3\u5fbe\u507a\u5d01\u681a\u5051\u50ff\u50e2\u50df\u4e55\u6679\u507a\u5fdc\u5d0c
		target = prefocus;
	}
//	getNowDate("\u61f3\u5fbe\u5d01\u681a\u507a\u62a3: " + target.value);

	return target;
}



// ********************************************
// JavaScript: Information Area\u5075\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u5f42\u5052\u5d2c\u5093\u5a2d\u60a2
// \u5837\u60a2\u5072\u505f\u5070\u6409\u505d\u509f\u5068\u66a5\u5e24\u694d\u50aa\u5f42\u5052\u5d2c\u5093
//
// 2004/07/16
// autor ryo masuda
// ********************************************
function doWriteInformation(message){
	var doc = window.document;

	// \u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u5f42\u5052\u5d2c\u5093
	//doc.forms["footer_form"].elements["infomation_area"].value=message;
}

// ********************************************
// JavaScript: Information Area\u5075\u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u5f42\u5052\u5d2c\u5093\u5a2d\u60a2
// \u6468\u5e2a\u5075\u4e04\u50c4\u5114\u4e55\u5051\u5a72\u5059\u506d\u5068\u5d01\u681a\u507afieldmsg\u61cf\u60c8\u5075\u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u5f42\u5052\u5d2c\u5093\u580a\u4e04
// \u5066\u507a\u5d01\u681a\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u50c0\u511e\u505f\u5068\u5d7a\u5075Information Area\u5075\u510a\u50e2\u50d9\u4e55\u50d5\u5051\u661e\u5e35\u505d\u509f\u509e
// \u5837\u60a2 message	\u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e55\u50d5
// \u5837\u60a2 obj		\u50c4\u5114\u4e55\u5051\u5a72\u5059\u506d\u5068\u5d01\u681a\u507a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9
//
// 2004/08/02
// autor ryo masuda
// ********************************************
function doWriteErrorMessage(message, obj){
	var doc = window.document;

	//msg\u61cf\u60c8\u5075\u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u50d9\u50e2\u50e9\u5061\u509e
	obj.fieldmsg=message;
	// \u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u5f42\u5052\u5d2c\u5093
    alert(message);
	doWriteInformation(message);
}

// ********************************************
// JavaScript: \u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e55\u50d5\u507a\u661e\u5e35(InformationArea)\u5072\u5adf\u5075
// \u5ddc\u6395\u507a\u5d01\u681a\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u50d9\u50e2\u50e9\u5061\u509e\u4e05
//
// \u5aee\u5837\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u580f\u6466\u505d\u5063\u509e\u507a\u5071\u4e04\u5dca\u68a1\u66fd\u6704\u5075\u507c\u62f2\u5804\u5061\u509e\u6601\u68ab\u5051\u5041\u509e\u4e05
//
// \u5837\u60a2 message	\u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e55\u50d5
// \u5837\u60a2 obj		\u50c4\u5114\u4e55\u5051\u5a72\u5059\u506d\u5068\u5d01\u681a\u507a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9
//
// 2005/01/31
// autor ryo masuda
// ********************************************
function displayMessageWithFocusSet(message, obj){
	var doc = window.document;

	// Information Area\u5075\u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u5f42\u5052\u5d2c\u5093
	doWriteErrorMessage(message, obj);
	// focus\u50aa\u50d9\u50e2\u50e9\u5061\u509e
	obj.focus();
}

// ********************************************
// JavaScript: \u604a\u590b\u67fa\u5072\u5adf\u5075\u50df\u50c0\u50be\u5118\u50cc\u50aa\u66b5\u5060\u509e
//
// 2004/11/25
// autor ryo masuda
// ********************************************
function closeParentWindow(){
	window.dialogArguments.close();
}

/*****************************************************************/
// ForkWindow\u68a1\u5a2d\u60a2
// \u597aFork\u612d\u5075\u58b3\u5060\u5068ForkDialogOpener\u50aa\u5950\u5054\u4e05
//
//\u5837\u60a2
//\u4e12target: DialogOpener\u507a\u5ddc\u6395
//\u4e12path: Fork\u612d\u507aInitAction\u507a\u50f7\u50d7
//\u4e12query: JSP\u5fcb\u5075\u61da\u5d7c\u5061\u509e\u5d01\u681a\u57f2\u595c\u507a\u5095\u507a\u50aa\u50d2\u4e55\u50f6\u508a\u6409\u505f\u5068\u5044\u5fdc\u5d0c\u5075\u5a70\u5f0e\u4e6e\u5fa3\u68ef\u58dc\u4e6f\u4e05
//			\u4e82\u5f42\u5e43\u4e83arg0=\u62a30&arg1=\u62a31&arg2=\u62a32
//
// 2004/11/25
// autor ryo masuda
/*****************************************************************/
function doForkWindow(target, path, query){
	var windowname;
	var contextpath;
	var targetpath;

//	alert("target:" + target + " path:" + path + " query:" + query);

	// \u5837\u60a2\u50e0\u50c3\u50e2\u50cb
	// querystring\u507c\u5fa3\u68ef\u58dc
	if(target=="" || target==null) {
		// \u6644\u60d3\u5074\u5fdc\u5d0c\u507c\u4e04\u50be\u5114\u4e55\u50e9\u5071\u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u661e\u5e35\u5061\u509e
		alert(fwid0001);
		return;
	}
	if(path=="" || path==null) {
		// \u6644\u60d3\u5074\u5fdc\u5d0c\u507c\u4e04\u50be\u5114\u4e55\u50e9\u5071\u50c4\u5114\u4e55\u510a\u50e2\u50d9\u4e55\u50d5\u50aa\u661e\u5e35\u5061\u509e
		alert(fwid0001);
		return;
	}

	// \u5837\u60a2path\u50aahidden\u50de\u50cc\u5075\u50d9\u50e2\u50e9
	window.document.forms[0].forward_path.value=path;
	// \u5837\u60a2query\u50aahidden\u50de\u50cc\u5075\u50d9\u50e2\u50e9
	if(query==undefined || query==null || query==""){
//		alert("query\u507c\u67b9\u6395\u5a8a\u5071\u5061\u4e05");
	}else{
//		alert("query\u5051\u5837\u60a2\u5072\u505f\u5070\u6409\u505d\u509f\u5091\u505f\u5068\u4e05");
		window.document.forms[0].querystring.value=query;
	}

	// \u50d0\u511e\u50e5\u50c9\u50d7\u50e9\u50f7\u50d7\u50aa\u6645\u58db
	contextpath = window.document.forms[0].context_path.value;
	targetpath = contextpath + target;

	// \u590b\u67fa\u50aa\u5826\u5804\u5075\u647f\u6395\u5061\u509e\u5068\u5094\u5075windowname\u50aa\u5d8c\u60c9 \u50f7\u50d7\u4e84\u5e2a\u5a2b \u4e84 \u68ce\u60a2
	windowname = path.replace(/\//g,"") + (new Date()).getTime() + "X" +  Math.floor(Math.random()*1000);

	// \u50c2\u50bf\u511e\u50ea\u50c2\u50aa\u50c6\u4e55\u50fe\u511e\u4e05
//	var option = "left=" + screen.height + ",top=" + screen.width + ",width=1,height=1";
	var option = "left=0,top=0,width=1,height=1";
//	alert(targetpath + ":" + windowname + ":" + option);
	var winObj = window.open(targetpath, windowname, option);
}

// ********************************************
// JavaScript: \u50fc\u50c5\u4e55\u50cb\u66fd\u5e43\u5071\u5950\u5044\u5068\u50c2\u50c0\u511e\u50ea\u50c2\u50aa
// \u66b5\u5060\u509e\u5d7a\u5075\u5c47\u5083\u5f0c\u5061\u5a2d\u60a2
// \u50fc\u50c5\u4e55\u50cb\u66b5\u5060\u509e\u50be\u50cb\u50d4\u5111\u511e\u508a\u507a\u50d2\u50fd\u5108\u50e2\u50e9\u50aa\u5cf4\u5046
// 2004/11/25
// autor Takeshi SAKAKIBARA
// ********************************************
function doForkClose(obj) {
	var doc = window.document.forms[0];
	var closePath = "/ForkCloseAction";
	var target;

	if(submit_flug) {
		// \u50d2\u50fd\u5108\u50e2\u50e9\u62de\u507a\u5fdc\u5d0c\u507c\u5f35\u68df\u50aa\u5cf4\u50a2\u5074\u5044
		return;
	}
	//	\u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u50c9\u4e55\u5e5a\u5cf4\u5e2a\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u5051\u6449\u5068\u506d\u5070\u5044\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u50aa\u5ea2\u647c
	target = getPreFocusObject();

	// \u6156\u6230\u50df\u50c0\u50be\u5118\u50cc
	if((obj.message!="") && (obj.message!=null)) {
		// \u50de\u50cc\u5075message\u5051\u5a70\u5f0e\u505d\u509f\u5070\u5044\u5068\u5fdc\u5d0c
		if(!doCheckDialogMessage(obj)) {
			// \u50df\u50c0\u50be\u5118\u50cc\u5071NO\u5051\u6156\u6230\u505d\u509f\u5068
			if(target!=null){
				// \u50fc\u50c5\u4e55\u50c7\u50d7\u5051\u6449\u5068\u506d\u5070\u5044\u5068\u5d01\u681a\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u6820\u5061
				target.focus();
			}
			return;
		}
	}

	// \u50d2\u50fd\u5108\u50e2\u50e9\u50fc\u5114\u50cc\u50aa\u68eb\u5070\u509e
	submit_flug = true;

	// \u590b\u67fa\u60c2\u5c7c\u68b7\u60c2
	doOpenWaitFillter();

	// hidden\u50de\u50cc\u509b\u509d\u4e04\u50d0\u511e\u50e5\u50c9\u50d7\u50e9\u50f7\u50d7\u50aa\u5ea2\u647c\u5061\u509e
	context = doc.context_path.value;
	// hidden\u50de\u50cc\u509b\u509d\u4e04\u6119\u65dc\u5dd5\u50aa\u5ea2\u647c\u5061\u509e
	suffix = doc.url_suffix.value;
	// form\u507aaction\u61cf\u60c8\u5075\u50d9\u50e2\u50e9\u5061\u509eulr\u50aa\u60d7\u60c9
	url = context + closePath + suffix;
	// form\u50de\u50cc\u507aaction\u61cf\u60c8\u5075\u50d2\u50fd\u5108\u50e2\u50e9\u612d\u507a\u50f7\u50d7\u50aa\u50d9\u50e2\u50e9
	doc.action = url;

	// \u50d2\u50fd\u5108\u50e2\u50e9
	doc.submit();

	// \u50d9\u5117\u50cb\u50e9\u50de\u50cc\u507a\u67cd\u5ca0\u58d4
    //var objs = document.all.tags("SELECT");
    //for(i=0;i<objs.length;i++) {
    //    objs(i).disabled = true;
    //}
}

// ********************************************
// JavaScript: \u590b\u67fa\u6157\u580f\u66fd\u5e43\u507aBackAction\u5075
// \u50d2\u50fd\u5108\u50e2\u50e9\u5061\u509e\u5a2d\u60a2
//
// 2004/12/10
// autor RYO MASUDA
// ********************************************
function doBack(obj){
	var doc = window.document.forms[0];
	// hidden\u5050\u509cBackAction\u507a\u50f7\u50d7\u50aa\u5ea2\u647c\u5061\u509e
	var backPath = doc.back_path.value;

	if(submit_flug) {
		// \u50d2\u50fd\u5108\u50e2\u50e9\u62de\u507a\u5fdc\u5d0c\u507c\u5f35\u68df\u50aa\u5cf4\u50a2\u5074\u5044
		return;
	}
	//	\u50fc\u50bd\u511e\u50cb\u50d4\u5111\u511e\u50c9\u4e55\u5e5a\u5cf4\u5e2a\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u5051\u6449\u5068\u506d\u5070\u5044\u5068\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u50aa\u5ea2\u647c
	target = getPreFocusObject();

	// \u6156\u6230\u50df\u50c0\u50be\u5118\u50cc
	if((obj.message!="") && (obj.message!=null)) {
		// \u50de\u50cc\u5075message\u5051\u5a70\u5f0e\u505d\u509f\u5070\u5044\u5068\u5fdc\u5d0c
		if(!doCheckDialogMessage(obj)) {
			// \u50df\u50c0\u50be\u5118\u50cc\u5071NO\u5051\u6156\u6230\u505d\u509f\u5068
			if(target!=null){
				// \u50fc\u50c5\u4e55\u50c7\u50d7\u5051\u6449\u5068\u506d\u5070\u5044\u5068\u5d01\u681a\u5075\u50fc\u50c5\u4e55\u50c7\u50d7\u50aa\u6820\u5061
				target.focus();
			}
			return;
		}
	}

	// \u50d2\u50fd\u5108\u50e2\u50e9\u50fc\u5114\u50cc\u50aa\u68eb\u5070\u509e
	submit_flug = true;

	// \u590b\u67fa\u60c2\u5c7c\u68b7\u60c2
	doOpenWaitFillter();

	// hidden\u50de\u50cc\u509b\u509d\u4e04\u50d0\u511e\u50e5\u50c9\u50d7\u50e9\u50f7\u50d7\u50aa\u5ea2\u647c\u5061\u509e
	context = doc.context_path.value;
	// hidden\u50de\u50cc\u509b\u509d\u4e04\u6119\u65dc\u5dd5\u50aa\u5ea2\u647c\u5061\u509e
	suffix = doc.url_suffix.value;
	// form\u507aaction\u61cf\u60c8\u5075\u50d9\u50e2\u50e9\u5061\u509eulr\u50aa\u60d7\u60c9
	url = context + backPath + suffix;
	// form\u50de\u50cc\u507aaction\u61cf\u60c8\u5075\u50d2\u50fd\u5108\u50e2\u50e9\u612d\u507a\u50f7\u50d7\u50aa\u50d9\u50e2\u50e9
	doc.action = url;

	// \u50d2\u50fd\u5108\u50e2\u50e9
	doc.submit();

	// \u50d9\u5117\u50cb\u50e9\u50de\u50cc\u507a\u67cd\u5ca0\u58d4
    //var objs = document.all.tags("SELECT");
    //for(i=0;i<objs.length;i++) {
    //    objs(i).disabled = true;
    //}
}

// ********************************************
// JavaScript: \u598b\u64e3\u50df\u50c0\u50be\u5118\u50cc\u50aa\u5f0c\u6921\u505f\u5068\u5c7b\u507a\u4e04
// \u5e5a\u5d7a\u5075\u50d2\u50fd\u5108\u50e2\u50e9\u5061\u509e\u5a2d\u60a2
//
// 2005/1/27
// autor Takeshi SAKAKIBARA
// ********************************************
function doSubmitAfterConfirm(path){
	var doc = window.document.forms[0];
	// \u57f2\u58d3\u507a\u5e5a\u61b0\u507cdoBack\u509b\u509d\u68f3\u68a1.
	if(submit_flug) {
		// \u50d2\u50fd\u5108\u50e2\u50e9\u62de\u507a\u5fdc\u5d0c\u507c\u5f35\u68df\u50aa\u5cf4\u50a2\u5074\u5044
		return;
	}
	// \u50d2\u50fd\u5108\u50e2\u50e9\u50fc\u5114\u50cc\u50aa\u68eb\u5070\u509e
	submit_flug = true;

	// \u590b\u67fa\u60c2\u5c7c\u68b7\u60c2
	doOpenWaitFillter();

	// hidden\u50de\u50cc\u509b\u509d\u4e04\u50d0\u511e\u50e5\u50c9\u50d7\u50e9\u50f7\u50d7\u50aa\u5ea2\u647c\u5061\u509e
	context = doc.context_path.value;
	// hidden\u50de\u50cc\u509b\u509d\u4e04\u6119\u65dc\u5dd5\u50aa\u5ea2\u647c\u5061\u509e
	suffix = doc.url_suffix.value;
	// form\u507aaction\u61cf\u60c8\u5075\u50d9\u50e2\u50e9\u5061\u509eulr\u50aa\u60d7\u60c9
	url = context + path + suffix;
	// form\u50de\u50cc\u507aaction\u61cf\u60c8\u5075\u50d2\u50fd\u5108\u50e2\u50e9\u612d\u507a\u50f7\u50d7\u50aa\u50d9\u50e2\u50e9
	doc.action = url;

	// \u50d2\u50fd\u5108\u50e2\u50e9
	doc.submit();

	// \u50d9\u5117\u50cb\u50e9\u50de\u50cc\u507a\u67cd\u5ca0\u58d4
    //var objs = document.all.tags("SELECT");
    //for(i=0;i<objs.length;i++) {
    //    objs(i).disabled = true;
    //}
}

// ********************************************
// JavaScript: \u50f6\u50c0\u50e9\u59fa\u5dbc\u507a\u66a5\u5e24\u694d\u633f\u50aa\u66c9\u505f\u5091\u5061.
// UTF-16\u5071\u657e\u6395\u505f\u5091\u5061
// \u5837\u60a2
// \u4e12str: \u5bc1\u5dbc\u61f3\u5fbe\u507a\u66a5\u5e24\u694d
// 2005/2/10
// author Takeshi SAKAKIBARA
// ********************************************
function toByteLength(str) {
	var size=getStringLength(str);
//    var asciiFloor   = unescape("%00");
//    var asciiCeiling = unescape("%7E");
//    var utf16HanKanaFloor   = String.fromCharCode(65377);
//    var utf16HanKanaCeiling = String.fromCharCode(65439);

//    var size = 0;
//    for (var i = 0; i < str.length; i++) {
//        var target = str.charAt(i);
//        if (asciiFloor <= target && target <= asciiCeiling) {
//            size++;
//        } else if (utf16HanKanaFloor <= target && target <= utf16HanKanaCeiling) {
//            size++;
//        } else {
//            size += 2;
//        }
//    }
    return size;
}

//get string lenth
function getStringLength(str){
    var stringLength = 0;
    if(str!=null){
		var strLength = str.length;
		for(var i=0;i<strLength;i++){
			var charAtI = str.charAt(i);
			if(charAtI >= '!' && charAtI <= '~'){
				stringLength++;
			}else{
				stringLength += 2;
			}
		}
	}
	return stringLength;
}


// ************************************************
// JavaScript: \u50c0\u5100\u511e\u50e9\u656a\u60d7\u5e2a\u507a\u5103\u50de\u511e\u58b4\u58d3\u50aa\u67cd\u5ca0\u58d4\u5061\u509e
// \u5837\u60a2
// event: onKeyDown\u644d\u507a\u50c0\u5100\u511e\u50e9\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9
//
// 2005/2/21
// author RYO MASUDA
// ************************************************
function doKeyCancell(event){
// \u50e5\u50d7\u50e9
//	getNowDate("doKeyCancell()\u5950\u5dd2");
//	alert("\u58b4\u58d3\u505d\u509f\u5068\u50c9\u4e55\u507akeycode: " + event.keyCode +"\n" + "\u58b4\u58d3\u505d\u509f\u5068\u50c9\u4e55: " + String.fromCharCode(event.keyCode));

	// \u5103\u50de\u511e\u58b4\u58d3\u50aa\u67cd\u5ca0\u58d4
	event.keyCode=0;
	event.returnValue=false;
}

// ****************************************
// JavaScript : \u5c30\u5e2a\u63f0\u5071\u507a\u50df\u50c0\u50be\u5118\u50cc\u507a
//              \u57f5\u62b2\u5fe3\u66ec\u50aa\u611d\u6395\u4e05
// Autor : YUTAKA YOSHIDA
// input: obj form\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u4e05
// ****************************************
function setDialogPosition(obj) {
	var offset = getOffset();
	var left = window.screenLeft - offset[0];
	var top  = window.screenTop - offset[1];
	window.dialogLeft = left;
	window.dialogTop = top;
}

// ****************************************
// JavaScript : \u50df\u50c0\u50be\u5118\u50cc\u57f5\u62b2\u5ddc\u6395\u507a\u50d8\u5117\u5075
//              \u61f3\u5061\u509e\u50c6\u50fc\u50d9\u50e2\u50e9\u50aa\u66c9\u5061\u4e05
// Autor : YUTAKA YOSHIDA
//
// return : \u651d\u694d\u5b86\u4e05\u68ab\u617a\u507a\u5dd2\u5094\u5050\u509c
//          [0] : left\u62a3
//          [1] : top\u62a3
// ****************************************
function getOffset() {
	var os = navigator.userAgent.toUpperCase();
	// \u5c30\u5fec\u507c\u4e04win2000\u5072xp\u507a\u5092\u5074\u507a\u5071\u4e04\u5c4c\u6395\u4e05
	var offset = new Array(2);
	offset[0] = 3;
	if(os.indexOf("WINDOWS NT 5.0") >= 0) {
		offset[1] = 22;
	}else if(os.indexOf("WINDOWS NT 5.1") >= 0) {
		offset[1] = 29
	}else{
		offset[0] = 0;
		offset[1] = 0;
	}
	return offset;
}

// ********************************************
// \u5837\u60a2\u507a\u50c6\u50fd\u50d5\u50c3\u50cb\u50e9\u50aa\u5a37\u5093\u4e04Iterate\u507a\u5f0c\u6921\u505f\u5068DIV\u50de\u50cc\u50aa\u66c9\u5061.
// Iterate\u5075\u50f1\u50d7\u50e9\u505d\u509f\u5070\u5044\u5074\u5044\u5fdc\u5d0c\u507cnull\u50aa\u66c9\u5061.
// 2005/3/15
// autor Takeshi SAKAKIBARA
// ********************************************
function getParentDiv(obj) {
	var parent = obj.parentNode;
	if (parent != null) {
		// \u604a\u5051null\u5071\u5074\u5044
		if (parent.tagName == "DIV" && parent.id == "CHILD_DIV_ID") {
			// \u604a\u5051div\u50de\u50cc\u5069\u506d\u5068
			return parent;
		} else {
		    // \u604a\u5051div\u5071\u5074\u5044 \u4f40 \u504d\u507d\u5041\u506a\u5096\u50ab
			return getParentDiv(parent);
		}
	} else {
		// \u604a\u5051null\u5069\u506d\u5068
		return null;
	}
}

// ********************************************
// JavaScript: \u5c30\u5d7c\u5e2a\u5a2b\u50aa\u5ea2\u647c\u505f\u4e04
// \u510a\u50e2\u50d9\u4e55\u50d5\u50c4\u5115\u50be\u5075\u50df\u511e\u50fe\u5061\u509e\u5a2d\u60a2
//
// \u50f6\u50cc\u5ecb\u60d3\u4e12\u50e5\u50d7\u50e9\u507a\u5d7a\u5075\u5dca\u68a1
// ********************************************
function getNowDate(fName){
var infoObj;
//	\u50e5\u50d7\u50e9
//	alert("getNowDate()\u5950\u5dd2");
	nowDate = new Date();
	H = nowDate.getHours();
	M = nowDate.getMinutes();
	S = nowDate.getSeconds();
	MS = nowDate.getMilliseconds();

	dateMessage = "\u4ee0" + fName + "  " + H + ":" + M + ":" + S + "," + MS;
//	alert(dateMessage);
	//infoObj = window.document.forms["footer_form"].elements["TEXT_AREA"];
	//infoObj.value = infoObj.value + "\n" + dateMessage;

}

function disableHistoryBack() {
	window.history.forward(1);
}

// openModalWindow\u5728\u4e3b\u7a97\u53e3\u4e2d\u8c03\u7528
function openModalWindow(url,optionStr) {
    setAccessable(false);
    window.open(url,"_blank",optionStr);
}

function closeModalWindow() {
    opener.setAccessable(true);
    window.close();
}

function setAccessable(accessFlag) {
    if(accessFlag==true) {
		ACCESS_DENY=false;
    } else {
        ACCESS_DENY=true;
    }
}

function isAccessable() {
    if(ACCESS_DENY==true) {
        return false;
    } else {
        return true;
    }
}

function submitModal(dataForm,hiddenForm) {
	var itemCount=dataForm.elements.length;
    var i=0;
    var currItem;
    var urlStr="";
    for(i=0;i<itemCount;i++) {
		currItem=dataForm.elements[i];
        if(i==0) {
			urlStr=currItem.name+"="+currItem.value;
		} else {
			urlStr=urlStr+"&"+currItem.name+"="+currItem.value;
		}
	}
    urlStr=dataForm.action+"?"+urlStr;
    hiddenForm.actionURL.value=urlStr;
    hiddenForm.result.value="OK";
}

function copyFormData(toFrom,fromForm) {
	var itemCount=fromForm.elements.length;
	var toItemCount=toFrom.elements.length;
    var i=0;
    var j=0;
    var currItem;
    var toItem;

    for(i=0;i<itemCount;i++) {
		currItem=fromForm.elements[i];

		for(j=0;j<toItemCount;j++) {
			toItem=toFrom.elements[j];
			try {
				if(toItem.name==currItem.name) {
					toItem.value=currItem.value;
					break;
				}
			} catch(ex) {
			}
		}
	}
}

function alertError( ex ) {
    if(ex) {
        var errStr="Error Message：\t\t\t\t\t\n";
        for(i in ex) {
            errStr+=i+":"+ex[i]+"\n";
        }
        alert(errStr);
    }
}

