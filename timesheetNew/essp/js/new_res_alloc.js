//����ѡ������Աҳ�棬�Լ���ҳ���б�����afterMultiSelect(value)�������Ի��ѡ���ֵ
function beforeMultiSelect(oldValue){
  var result = window.open("/essp/hr/alloc/UserAlloc.do?type=multi&oldValue="+oldValue , "", "status=yes,width=740,height=450,top=150,left=150");
}
//����ѡ�񵥸���Աҳ�棬�Լ���ҳ���б�����afterSingleSelect(value)�������Ի��ѡ���ֵ
function beforeSingleSelect(oldValue){
  window.open("/essp/hr/alloc/UserAlloc.do?type=single&oldValue="+oldValue , "", "status=yes,width=481,height=450,top=150,left=150");
}
//����ResourcePlanningҳ��
function onResoucePlan(){
  window.open("/essp/hr/resplan/ResourcePlan.do?accountId=6" , "", "status=yes,width=740,height=450,top=150,left=150");
}

