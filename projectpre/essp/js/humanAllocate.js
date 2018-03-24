
var appRoot="";
var appletInstance=null;
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
//在Applet中选多个人
function allocMultiple(oldValue,acntRid,reqStr){
  //var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=multi&oldValue="+oldValue , "", "status=yes,width=740,height=450,top=150,left=150");
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=multi&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  setAllocResult(result);
}
//在JSP页面中选多个人
function allocMultipleInJsp(oldValue,acntRid,reqStr){
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=multi&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  return result;
}
//在Applet中选单个人
function allocSingle(oldValue,acntRid,reqStr){
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=single&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
   setAllocResult(result);
}
//在JSP页面中选单个人
function allocSingleInJsp(oldValue,acntRid,reqStr){
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=single&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  return result;
}
//在AD中选单个人
function allocSingleInAD(param,acntRid,reqStr,oldValue,showKeyPerson){
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=single&requestType=ad&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr+"&showKeyPerson="+showKeyPerson , param,"dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  setAllocResult(result);
  return result;
}
//在AD中选多个人
function allocMultipleInAD(param,acntRid,reqStr,oldValue,showKeyPerson){
  var result= window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=multi&requestType=ad&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr+"&showKeyPerson="+showKeyPerson , param,"dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  setAllocResult(result);
  return result;
}
//在AD和DB中选单个人
function allocSingleInALL(param,acntRid,reqStr,oldValue,showKeyPerson){
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=single&requestType=all&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr+"&showKeyPerson="+showKeyPerson , param,"dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  return result;
}
//在ADD和DB中选多个人
function allocMultipleInALL(param,acntRid,reqStr,oldValue,showKeyPerson){
  var result= window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=multi&requestType=all&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr+"&showKeyPerson="+showKeyPerson , param,"dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  return result;
}

//参数newUserIds为分配后选中的人的id，当有多个时，用逗号","分隔


function setAllocResult(newUserIds){
//  alert( "setAllocResult(" + newUserIds +")" );
  try{
    appletInstance.calledByJS("newUserIds=" + newUserIds);
  }catch(e){
//    alert( "Exception: alloc.hrAllocOK(" + newUserIds + ")" );
  }
}

//定义传入参数对象
function allocUser(loginId,name,domain,type) {
  this.loginId=loginId;
  this.name=name;
  this.domain=domain;
  this.type=type;
}
