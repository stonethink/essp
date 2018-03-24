//Account JavaScript Object Define
function accountObj(rid,rel_prj_type,rel_parent_id,acnt_id,acnt_name,acnt_full_name,customer_id,achieve_belong,exec_site,exec_unit_id,cost_attach_bd,biz_source,product_name,acnt_attribute,acnt_type,acnt_anticipated_start,acnt_anticipated_finish,acnt_planned_start,acnt_planned_finish,acnt_actual_start,acnt_actual_finish,est_manmonth,actual_manmonth,acnt_organization,acnt_currency,est_size,acnt_status,acnt_brief,acnt_inner,is_template,import_template_rid,contract_acnt_id) {
  this.rid = rid;
  this.rel_prj_type = rel_prj_type;
  this.rel_parent_id = rel_parent_id;
  this.acnt_id = acnt_id;
  this.acnt_name = acnt_name;
  this.acnt_full_name = acnt_full_name;
  this.customer_id = customer_id;
  this.achieve_belong = achieve_belong;
  this.exec_site = exec_site;
  this.exec_unit_id = exec_unit_id;
  this.cost_attach_bd = cost_attach_bd;
  this.biz_source = biz_source;
  this.product_name = product_name;
  this.acnt_attribute = acnt_attribute;
  this.acnt_type = acnt_type;
  this.acnt_anticipated_start = acnt_anticipated_start;
  this.acnt_anticipated_finish = acnt_anticipated_finish;
  this.acnt_planned_start = acnt_planned_start;
  this.acnt_planned_finish = acnt_planned_finish;
  this.acnt_actual_start = acnt_actual_start;
  this.acnt_actual_finish = acnt_actual_finish;
  this.est_manmonth = est_manmonth;
  this.actual_manmonth = actual_manmonth;
  this.acnt_organization = acnt_organization;
  this.acnt_currency = acnt_currency;
  this.est_size = est_size;
  this.acnt_status = acnt_status;
  this.acnt_brief = acnt_brief;
  this.acnt_inner = acnt_inner;
  this.is_template = is_template;
  this.import_template_rid = import_template_rid;
  this.contract_acnt_id = contract_acnt_id;
}
function queryAccountPerson(conditionMap,personMap){
  var myObj = new Object();
  myObj.acntMap = conditionMap;
  myObj.personMap = personMap;
  return window.showModalDialog("/essp/projectpre/common/queryAccount/QueryAccount.jsp", myObj,"dialogHeight:500px;dialogWidth:435px;dialogLeft:200; dialogTop:100");
}
function queryAccount(conditionMap) {
  return window.showModalDialog("/essp/projectpre/common/queryAccount/QueryAccount.jsp", conditionMap,"dialogHeight:500px;dialogWidth:435px;dialogLeft:200; dialogTop:100");
}
