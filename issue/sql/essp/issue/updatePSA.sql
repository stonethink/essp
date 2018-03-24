-- add cmorliere
insert into pms_keypesonal(rid,acnt_rid,type_name,login_id,user_name,organization,email,enable,rst)
values(113,3686,'Customer','cmorliere','C MORLIERE','PSA','christophe.morliere@mpsa.com','Yes','N')

insert into essp_sys_project_customer(id,accountid,title,user_name,name,organization,email,is_manager,password)
values(13081,3686,'','cmorliere','C MORLIERE','PSA','christophe.morliere@mpsa.com','Yes','cmorliere')

-- add billoir
insert into pms_keypesonal(rid,acnt_rid,type_name,login_id,user_name,organization,email,enable,rst)
values(114,3686,'Customer','billoir','BILLOIR','PSA','joel.billoir@mpsa.com','Yes','N')

insert into essp_sys_project_customer(id,accountid,title,user_name,name,organization,email,is_manager,password)
values(13082,3686,'','billoir','BILLOIR','PSA','joel.billoir@mpsa.com','Yes','billoir')

-- add aseguin
insert into pms_keypesonal(rid,acnt_rid,type_name,login_id,user_name,organization,email,enable,rst)
values(115,3686,'Customer','aseguin','A Seguin','PSA','agathe.seguin@mpsa.com','Yes','N')

insert into essp_sys_project_customer(id,accountid,title,user_name,name,organization,email,is_manager,password)
values(13083,3686,'','aseguin','A Seguin','PSA','agathe.seguin@mpsa.com','Yes','aseguin')

-- add check
insert into pms_keypesonal(rid,acnt_rid,type_name,login_id,user_name,organization,email,enable,rst)
values(116,3686,'Customer','check','Check HARIS','PSA','check.haris@mpsa.com','Yes','N')

insert into essp_sys_project_customer(id,accountid,title,user_name,name,organization,email,is_manager,password)
values(13088,3686,'','check','Check HARIS','PSA','check.haris@mpsa.com','Yes','check')

-- add iaimi
insert into pms_keypesonal(rid,acnt_rid,type_name,login_id,user_name,organization,email,enable,rst)
values(119,2841,'Customer','iaimi','I. AIMI','PSA','isabelle.aimi@mpsa.com','Yes','N')

insert into essp_sys_project_customer(id,accountid,title,user_name,name,organization,email,is_manager,password)
values(13087,2841,'','iaimi','I. AIMI','PSA','isabelle.aimi@mpsa.com','Yes','iaimi')

-- add laurent
insert into pms_keypesonal(rid,acnt_rid,type_name,login_id,user_name,organization,email,enable,rst)
values(118,4798,'Customer','laurent','Laurent Gagneux','PSA','laurent.gagneux@mpsa.com','Yes','N')

insert into essp_sys_project_customer(id,accountid,title,user_name,name,organization,email,is_manager,password)
values(13086,4798,'','laurent','Laurent Gagneux','PSA','laurent.gagneux@mpsa.com','Yes','laurent')

-- add yann
insert into pms_keypesonal(rid,acnt_rid,type_name,login_id,user_name,organization,email,enable,rst)
values(117,4798,'Customer','yann','Yann Berthaux','PSA','yann.berthaux@mpsa.com','Yes','N')

insert into essp_sys_project_customer(id,accountid,title,user_name,name,organization,email,is_manager,password)
values(13085,4798,'','yann','Yann Berthaux','PSA','yann.berthaux@mpsa.com','Yes','yann')

update essp_crm_soft_client t set t.clientname='hua.li' where t.clientname='Li Hua';
update essp_crm_soft_client t set t.clientname='hua.li' where t.clientname='Hua Li';
update essp_crm_soft_client t set t.clientname='cmorliere' where t.clientname='C MORLIERE';

update issue t set t.filleby='cmorliere' where t.filleby='C MORLIERE'
update issue t set t.filleby='checkharis' where t.filleby='Check HARIS'
update issue t set t.filleby='psa' where t.filleby='PSA'
update issue t set t.filleby='cmorliere' where t.filleby='Christophe MORLIERE'
update issue t set t.filleby='cmorliere' where t.filleby='C MORLIERE / S LANDRIEUX'
update issue t set t.filleby='cmorliere' where t.filleby='C MORLIERE / C HARIS'
update issue t set t.filleby='aseguin' where t.filleby='A Seguin'
update issue t set t.filleby='aseguin' where t.filleby='A. Seguin'
update issue t set t.filleby='aseguin' where t.filleby='A SEGUIN / C MORLIERE'
update issue t set t.filleby='iaimi' where t.filleby='I. AIMI'
update issue t set t.filleby='laurent' where t.filleby='Laurent Gagneux'
update issue t set t.filleby='yann' where t.filleby='Yann Berthaux'
update issue t set t.filleby='shanqing.xiong' where t.filleby like 'ShanQing Xiong%'
update issue t set t.filleby='billoir' where t.filleby='BILLOIR'

update issue t set t.filleby='cmorliere' where t.filleby='S LANDRIEUX / C MORLIERE'
update issue t set t.filleby='cmorliere' where t.filleby='A E QOQUI / C MORLIERE'
update issue t set t.filleby='cmorliere' where t.filleby='C MORLIERE/BILLOIR'
update issue t set t.filleby='yann' where t.filleby like 'Laurent Gagneux%Yann Berthaux'
update issue t set t.filleby='yann' where t.filleby='Yann Berthaux/laurent Gagneux'
update issue t set t.filleby='shanqing.xiong' where t.filleby='Shanqing Xiong'
update issue t set t.filleby='shanqing.xiong' where t.filleby='Shanqingxiong'

update issue t set t.filleby='yann' where t.filleby='Laurent Gagneux / yann Berthaux'
update issue t set t.filleby='aseguin' where t.filleby='Agathe Seguin'
update issue t set t.filleby='laurent' where t.filleby='laurent.gagneux'
update issue t set t.principal='hua.li' where t.principal='HUA LI'
update issue t set t.principal='cmorliere' where t.principal='C MORLIERE'
update issue t set t.principal='laurent' where t.principal='Laurent Gagneux'
update issue t set t.principal='hua.yang' where t.principal='Hua Yang'
update issue t set t.principal='shanqing.xiong' where t.principal='shanqingxiong'
update issue t set t.principal='yann' where t.principal='Yann Berthaux'

update issue t set t.scope='Customer' where rid >185

update issue_resolution r
   set r.resolution_by_customer = 'laurent'
 where r.rid in (select s.rid from issue s where s.principal = 'laurent')
 
update issue s set s.principal = s.filleby where s.principal = 'laurent'

update issue_resolution r
   set r.resolution_by_customer = 'yann'
 where r.rid in (select s.rid from issue s where s.principal = 'yann')
 
update issue s set s.principal = s.filleby where s.principal = 'yann'