
var appRoot="";
var appletInstance=null;
//var oldData = new Array();
//var account_id = 'null';
//var res_id = 31; //xiong = 41;
//var newData;
function setAppRoot(rootUrl) {
	appRoot=rootUrl;
}

function getAppRoot() {
	return appRoot;
}

function setAppletInstance(applet1) {
	appletInstance=applet1;
	//alert("applet1 is "+applet1);
}

function getAppletInstance() {
	return appletInstance;
}

function setNewData(newData){
	try{
		appletInstance.calledByJS("newData="+newData);
	}
	catch(ex){        
        	alert(ex);
	}
}

function HrAlloc(account_id, res_id, oldDataStr){
	//eval( "oldData = " + oldDataStr);
	window.open(appRoot+"/hr/alloc_tool/alloc_tool.jsp?res_id=99&alloc_code=null&oldData=" + oldDataStr, ""	, "status=yes,width=740,height=450,top=150,left=150");
	return ;
}
