
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
//��Applet��ѡ�����
function allocMultiple(oldValue,acntRid,reqStr){
  //var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=multi&oldValue="+oldValue , "", "status=yes,width=740,height=450,top=150,left=150");
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=multi&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  setAllocResult(result);
}
//��JSPҳ����ѡ�����
function allocMultipleInJsp(oldValue,acntRid,reqStr){
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=multi&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  return result;
}
//��Applet��ѡ������
function allocSingle(oldValue,acntRid,reqStr){
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=single&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
   setAllocResult(result);
}
//��JSPҳ����ѡ������
function allocSingleInJsp(oldValue,acntRid,reqStr){
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=single&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr , "","dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  return result;
}
//��AD��ѡ������
function allocSingleInAD(param,acntRid,reqStr,oldValue,showKeyPerson){
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=single&requestType=ad&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr+"&showKeyPerson="+showKeyPerson , param,"dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  setAllocResult(result);
  return result;
}
//��AD��ѡ�����
function allocMultipleInAD(param,acntRid,reqStr,oldValue,showKeyPerson){
  var result= window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=multi&requestType=ad&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr+"&showKeyPerson="+showKeyPerson , param,"dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  setAllocResult(result);
  return result;
}
//��AD��DB��ѡ������
function allocSingleInALL(param,acntRid,reqStr,oldValue,showKeyPerson){
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=single&requestType=all&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr+"&showKeyPerson="+showKeyPerson , param,"dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  return result;
}
//��ADD��DB��ѡ�����
function allocMultipleInALL(param,acntRid,reqStr,oldValue,showKeyPerson){
  var result= window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=multi&requestType=all&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr+"&showKeyPerson="+showKeyPerson , param,"dialogHeight:500px;dialogWidth:740px;dialogLeft:200; dialogTop:100");
  return result;
}

//����newUserIdsΪ�����ѡ�е��˵�id�����ж��ʱ���ö���","�ָ�


function setAllocResult(newUserIds){
//  alert( "setAllocResult(" + newUserIds +")" );
  try{
    appletInstance.calledByJS("newUserIds=" + newUserIds);
  }catch(e){
//    alert( "Exception: alloc.hrAllocOK(" + newUserIds + ")" );
  }
}

//���崫���������
function allocUser(loginId,name,domain,type) {
  this.loginId=loginId;
  this.name=name;
  this.domain=domain;
  this.type=type;
}
