
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
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=single&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr , "","dialogHeight:500px;dialogWidth:481px;dialogLeft:200; dialogTop:100");
   setAllocResult(result);
}
//��JSPҳ����ѡ������
function allocSingleInJsp(oldValue,acntRid,reqStr){
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=single&oldValue="+oldValue+"&acntRid="+acntRid+"&requestStr="+reqStr , "","dialogHeight:500px;dialogWidth:481px;dialogLeft:200; dialogTop:100");
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
