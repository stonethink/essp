// File Name : AppMessage.js
//

var id1000 = "id1000";
var id2000 = "id2000";

var id0001="Wait a moment please!";
var id0002="Please input required item!";
var id0003="Data surpasses the limit!";
var id0004="Please input Number or '-' or '.'!";
var id0005="Please input Number";
var id0006="Error on format of date!";
var id0007="Please input Number or English letter!";
var id0008="Only Number or '-' can be inputed!";
var id0009="id0009";
var id0010="id0010";
var id0011="id0011";
var id0012="Error on format of time!";
var id0013="Data has oversteped the  maxlength";
var id0014="'.' not accepted";
var id0015="'-' not accepted!";

// ****************************************
// JavaScript : 確認ダイアログ出力
// attention : IE5.0以上推奨
// ****************************************
function doCheckDialogMessage(obj) {
	var result = false;
	try {
        var code = "result = confirm(id"+ obj.message +");";
        eval(code);
    }catch(e){
		jsExceptionHandlerSys(e,"AppMessage.js","doCheckDialogMessage");
    }
	return result;
}