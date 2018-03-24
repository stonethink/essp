<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
<title>
welcome
</title>
</head>
<body bgcolor="#ffffff">
<h1>
<script type="text/javascript">
function allocMultiple(oldValue){
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=multi&oldValue="+oldValue , "", "status=yes,width=740,height=450,top=150,left=150");
  setAllocResult(result);
}

function allocSingle(oldValue){
//  alert( "beforeSingleSelect(" + oldValue + ")" );
  var result = window.showModalDialog("/essp/common/humanAllocate/alloc/UserAlloc.do?type=single&oldValue="+oldValue , "", "status=yes,width=481,height=450,top=150,left=150");
  setAllocResult(result);
}

//参数newUserIds为分配后选中的人的id，当有多个时，用逗号","分隔
function setAllocResult(newUserIds){
//  alert( "setAllocResult(" + newUserIds +")" );
  try{
    alloc.calledByJS("newUserIds=" + newUserIds);
  }catch(e){
//    alert( "Exception: alloc.hrAllocOK(" + newUserIds + ")" );
  }
}

</script>
</h1>

<applet
 archive="/essp/common.jar"
 code="client.essp.common.humanAllocate.test.AllocTestApplet"
 name="alloc"
 width="600"
 height="300"
 hspace   = "0"
 vspace   = "0"
 align    = "middle">
</applet>
</body>
</html>
