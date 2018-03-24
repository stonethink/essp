
//Customer JavaScript Object Define
function customerObj(rid,attribute,id,reg_id,group_rid,short_name,name_cn,name_en,belong_bd,belong_site,class_id,country,create_date,alter_date,creator) {
  this.rid = rid;                     //????
  this.attribute = attribute;         //????
  this.id = id;                       //????
  this.reg_id = reg_id;               //?????
  this.group_rid = group_rid;         //??????
  this.short_name = short_name;       //????
  this.name_cn = name_cn;             //????
  this.name_en = name_en;             //????
  this.belong_bd = belong_bd;         //??BD
  this.belong_site = belong_site;     //??SITE
  this.class_id = class_id;           //?????
  this.country = country;             //?????
  this.create_date = create_date;     //????
  this.alter_date = alter_date;       //????
  this.creator =creator;              //???
}

function queryCustomer(conditionMap) {
  return window.showModalDialog("/essp/projectpre/common/queryCustomer/QueryCustomer.jsp", conditionMap,"dialogHeight:500px;dialogWidth:435px;dialogLeft:200; dialogTop:100");
}
