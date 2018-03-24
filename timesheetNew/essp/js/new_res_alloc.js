//调用选择多个人员页面，自己的页面中必须有afterMultiSelect(value)方法，以获得选择的值
function beforeMultiSelect(oldValue){
  var result = window.open("/essp/hr/alloc/UserAlloc.do?type=multi&oldValue="+oldValue , "", "status=yes,width=740,height=450,top=150,left=150");
}
//调用选择单个人员页面，自己的页面中必须有afterSingleSelect(value)方法，以获得选择的值
function beforeSingleSelect(oldValue){
  window.open("/essp/hr/alloc/UserAlloc.do?type=single&oldValue="+oldValue , "", "status=yes,width=481,height=450,top=150,left=150");
}
//进入ResourcePlanning页面
function onResoucePlan(){
  window.open("/essp/hr/resplan/ResourcePlan.do?accountId=6" , "", "status=yes,width=740,height=450,top=150,left=150");
}

