delete from essp_upms_function where FUNCTION_ID='M819000000';
insert into essp_upms_function(FUNCTION_ID,LAYER,ORDER_NO,NAME,FATHER_ID,ISVALID,ISLEAF,FUNCTIONADDRESS,TYPE,ISVISIABLE,SYSTEM)
values ('M819000000',3,9,'Issue Type Define','M810000000',1,1,'@{AppBase}/issue/typedefine/issueTypeList.do','process',1,'essp');
         
delete from essp_upms_user_fun where user_id='000000' and fun_id='M819000000';
insert into essp_upms_user_fun(user_id,fun_id) values('000000','M819000000');

delete from essp_upms_function where FUNCTION_ID='M920000000';
insert into essp_upms_function(FUNCTION_ID,LAYER,ORDER_NO,NAME,FATHER_ID,ISVALID,ISLEAF,FUNCTIONADDRESS,TYPE,ICON,ISVISIABLE,ICON_ON,ICON_DOWN,SYSTEM)
values ('M920000000',1,16,'Issue','M000000000',1,0,'@{AppBase}/Left_l.jsp?SysFuncID=M920000000','menu','image/crm.gif',1,'image/crm_On.gif','image/crm_On.gif','essp');

delete from essp_upms_function where FUNCTION_ID='M921000000';
insert into essp_upms_function(FUNCTION_ID,LAYER,ORDER_NO,NAME,FATHER_ID,ISVALID,ISLEAF,FUNCTIONADDRESS,TYPE,ISVISIABLE,SYSTEM)
values ('M921000000',2,1,'Issue Management','M920000000',1,0,'','process',1,'essp');

delete from essp_upms_function where FUNCTION_ID='M921100000';
insert into essp_upms_function(FUNCTION_ID,LAYER,ORDER_NO,NAME,FATHER_ID,ISVALID,ISLEAF,FUNCTIONADDRESS,TYPE,ISVISIABLE,SYSTEM)
values ('M921100000',3,1,'Issue Stat','M921000000',1,1,'@{AppBase}/issue/stat/issueStat.do','process',1,'essp');

delete from essp_upms_function where FUNCTION_ID='M921200000';
insert into essp_upms_function(FUNCTION_ID,LAYER,ORDER_NO,NAME,FATHER_ID,ISVALID,ISLEAF,FUNCTIONADDRESS,TYPE,ISVISIABLE,SYSTEM)
values ('M921200000',3,2,'Issue List','M921000000',1,1,'@{AppBase}/issue/issue/issueList.do','process',1,'essp');

delete from essp_upms_function where FUNCTION_ID='M922000000';
insert into essp_upms_function(FUNCTION_ID,LAYER,ORDER_NO,NAME,FATHER_ID,ISVALID,ISLEAF,FUNCTIONADDRESS,TYPE,ISVISIABLE,SYSTEM)
values ('M922000000',2,2,'Communication','M920000000',1,0,'','process',1,'essp');

delete from essp_upms_function where FUNCTION_ID='M922100000';
insert into essp_upms_function(FUNCTION_ID,LAYER,ORDER_NO,NAME,FATHER_ID,ISVALID,ISLEAF,FUNCTIONADDRESS,TYPE,ISVISIABLE,SYSTEM)
values ('M922100000',3,1,'Meeting','M922000000',1,1,'@{AppBase}/communication/meeting/main.do?FunctionId=QUERY&role=PM&','process',1,'essp');

delete from essp_upms_function where FUNCTION_ID='M922200000';
insert into essp_upms_function(FUNCTION_ID,LAYER,ORDER_NO,NAME,FATHER_ID,ISVALID,ISLEAF,FUNCTIONADDRESS,TYPE,ISVISIABLE,SYSTEM)
values ('M922200000',3,2,'Visit','M922000000',1,1,'@{AppBase}/communication/visit/query.do?FunctionId=QUERY&role=CUSTOMER&','process',1,'essp');