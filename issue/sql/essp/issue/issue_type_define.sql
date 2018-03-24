prompt PL/SQL Developer import file
prompt Created on 2005年12月28日 by xiaobo
set feedback off
set define off
prompt Dropping ISSUE_CATEGORY...
drop table ISSUE_CATEGORY cascade constraints;
prompt Dropping ISSUE_CATEGORY_TYPE...
drop table ISSUE_CATEGORY_TYPE cascade constraints;
prompt Dropping ISSUE_PRIORITY...
drop table ISSUE_PRIORITY cascade constraints;
prompt Dropping ISSUE_RISK...
drop table ISSUE_RISK cascade constraints;
prompt Dropping ISSUE_SCOPE...
drop table ISSUE_SCOPE cascade constraints;
prompt Dropping ISSUE_STATUS...
drop table ISSUE_STATUS cascade constraints;
prompt Dropping ISSUE_TYPE...
drop table ISSUE_TYPE cascade constraints;
prompt Creating ISSUE_CATEGORY...
create table ISSUE_CATEGORY
(
  RID           NUMBER,
  TYPE_NAME     VARCHAR2(100) not null,
  CATEGORYNAME  VARCHAR2(100) not null,
  CATEGORYVALUE VARCHAR2(100) not null,
  SEQUENCE      NUMBER,
  DESCRIPTION   VARCHAR2(500),
  RST           CHAR(1),
  RCT           DATE,
  RUT           DATE
)
tablespace ESSPRUN
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ISSUE_CATEGORY
  add primary key (TYPE_NAME,CATEGORYNAME,CATEGORYVALUE)
  using index 
  tablespace ESSPRUN
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating ISSUE_CATEGORY_TYPE...
create table ISSUE_CATEGORY_TYPE
(
  RID          NUMBER,
  TYPE_NAME    VARCHAR2(100) not null,
  CATEGORYNAME VARCHAR2(100) not null,
  SEQUENCE     NUMBER,
  DESCRIPTION  VARCHAR2(500),
  RST          CHAR(1),
  RCT          DATE,
  RUT          DATE
)
tablespace ESSPRUN
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ISSUE_CATEGORY_TYPE
  add primary key (TYPE_NAME,CATEGORYNAME)
  using index 
  tablespace ESSPRUN
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating ISSUE_PRIORITY...
create table ISSUE_PRIORITY
(
  RID         NUMBER,
  TYPE_NAME   VARCHAR2(100) not null,
  PRIORITY    VARCHAR2(100) not null,
  DURATION    NUMBER,
  SEQUENCE    NUMBER,
  DESCRIPTION VARCHAR2(500),
  RST         CHAR(1),
  RCT         DATE,
  RUT         DATE
)
tablespace ESSPRUN
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ISSUE_PRIORITY
  add primary key (TYPE_NAME,PRIORITY)
  using index 
  tablespace ESSPRUN
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating ISSUE_RISK...
create table ISSUE_RISK
(
  RID         NUMBER,
  TYPE_NAME   VARCHAR2(100) not null,
  INFLUENCE   VARCHAR2(100) not null,
  MINLEVEL    NUMBER,
  MAXLEVEL    NUMBER,
  WEIGHT      NUMBER,
  SEQUENCE    NUMBER,
  DESCRIPTION VARCHAR2(500),
  RST         CHAR(1),
  RCT         DATE,
  RUT         DATE
)
tablespace ESSPRUN
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ISSUE_RISK
  add primary key (TYPE_NAME,INFLUENCE)
  using index 
  tablespace ESSPRUN
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating ISSUE_SCOPE...
create table ISSUE_SCOPE
(
  RID         NUMBER,
  TYPE_NAME   VARCHAR2(100) not null,
  SCOPE       VARCHAR2(100) not null,
  VISION      VARCHAR2(10),
  SEQUENCE    NUMBER,
  DESCRIPTION VARCHAR2(500),
  RST         CHAR(1),
  RCT         DATE,
  RUT         DATE
)
tablespace ESSPRUN
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ISSUE_SCOPE
  add primary key (TYPE_NAME,SCOPE)
  using index 
  tablespace ESSPRUN
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating ISSUE_STATUS...
create table ISSUE_STATUS
(
  RID          NUMBER,
  TYPE_NAME    VARCHAR2(100) not null,
  STATUSNAME   VARCHAR2(100) not null,
  BELONGTO     VARCHAR2(100),
  RELATIONDATE VARCHAR2(100),
  SEQUENCE     NUMBER,
  DESCRIPTION  VARCHAR2(500),
  RST          CHAR(1),
  RCT          DATE,
  RUT          DATE
)
tablespace ESSPRUN
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ISSUE_STATUS
  add primary key (TYPE_NAME,STATUSNAME)
  using index 
  tablespace ESSPRUN
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating ISSUE_TYPE...
create table ISSUE_TYPE
(
  RID                    NUMBER,
  TYPE_NAME              VARCHAR2(100) not null,
  SEQUENCE               NUMBER,
  DESCRIPTION            VARCHAR2(500),
  RST                    CHAR(1),
  RCT                    DATE,
  RUT                    DATE,
  SAVE_STATUS_HISTORY    VARCHAR2(5) default 'Y',
  SAVE_INFLUENCE_HISTORY VARCHAR2(5) default 'Y'
)
tablespace ESSPRUN
  pctfree 10
  pctused 40
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table ISSUE_TYPE
  add primary key (TYPE_NAME)
  using index 
  tablespace ESSPRUN
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for ISSUE_CATEGORY...
alter table ISSUE_CATEGORY disable all triggers;
prompt Disabling triggers for ISSUE_CATEGORY_TYPE...
alter table ISSUE_CATEGORY_TYPE disable all triggers;
prompt Disabling triggers for ISSUE_PRIORITY...
alter table ISSUE_PRIORITY disable all triggers;
prompt Disabling triggers for ISSUE_RISK...
alter table ISSUE_RISK disable all triggers;
prompt Disabling triggers for ISSUE_SCOPE...
alter table ISSUE_SCOPE disable all triggers;
prompt Disabling triggers for ISSUE_STATUS...
alter table ISSUE_STATUS disable all triggers;
prompt Disabling triggers for ISSUE_TYPE...
alter table ISSUE_TYPE disable all triggers;
prompt Loading ISSUE_CATEGORY...
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Type', 'Documentation', 301, 'Comments,Messages', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Type', 'Syntax', 302, 'Spelling,punctuation,typos,instruction formats', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Type', 'Build', 303, 'Change management,library,version control', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Inject Stage', 'RD', 101, '需求开发', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'SubProcess', 'Initiating', 101, '项目启动（Project Initiating）', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'SubProcess', 'Planning', 102, '项目计划（Project Planning）', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'SubProcess', 'Executing', 103, '项目执行（Project Executing）', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Inject Stage', 'AD', 102, '架构设计', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'SubProcess', 'Controlling', 104, '项目控制（Project Controlling）', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'SubProcess', 'Closing', 105, '项目结案（Project Closing）', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'SubProcess', 'Initiating', 101, '项目启动（Project Initiating）', 'N', null, to_date('22-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'SubProcess', 'Planning', 102, '项目计划（Project Planning）', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'SubProcess', 'Executing', 103, '项目执行（Project Executing）', 'N', null, to_date('22-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'SubProcess', 'Controlling', 104, '项目控制（Project Controlling）', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'SubProcess', 'Closing', 105, '项目结案（Project Closing）', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Inject Stage', 'HD', 103, '概要设计', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Inject Stage', 'DD', 104, '详细设计', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Inject Stage', 'CUT', 105, '编码及单元测试', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Type', 'Assignment', 304, 'Declaration,duplicate names,scope,limits', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Type', 'Interface', 305, 'procedure calls and references,I/O,user formats', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Type', 'Checking', 306, 'error messages,inadequate checks', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Type', 'Data', 307, 'Structure,content', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Type', 'Function', 308, 'logic,pointers,loops,recursion,computation,function defects', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Type', 'System', 309, 'configuration,timing,memory', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Type', 'Environment', 310, 'design,compile,test,other support system problems', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'CRR Type', 'Modified Req', 101, 'Modified Requirement ', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'CRR Type', 'Added Req', 102, 'Added Requirement', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'CRR Type', 'Deleted Req', 103, 'Deleted Requirement', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Remove Stage', 'RD', 201, '需求开发', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Impact', 'Fatal', 401, ' When there is the happenings of the System Crash, System Hang Up, etc,and the system is thus unable to be operated ', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Impact', 'Error', 402, 'When there is the happenings of the Function/Module Failure, etc, but the system operation is not inflenced ', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Impact', 'Warning', 403, 'When there is the happenings of the Function Error/Warning, Document Inconsistency, etc,but the system operation is not inflenced', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Impact', 'Enhancemen', 304, 'If it belongs to Function/Module Enhancement, New Function', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Impact', 'Others', 490, 'Others', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Remove Stage', 'AD', 202, '构架设计', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Remove Stage', 'HD', 203, '概要设计', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Remove Stage', 'DD', 204, '详细设计', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Remove Stage', 'CUT', 205, '编码及单元测试', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Remove Stage', 'WT', 206, '代码走查', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Remove Stage', 'IT', 207, '集成测试', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Remove Stage', 'RT', 208, '交付测试', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Remove Stage', 'AT', 209, '验收测试', 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Remove Stage', 'Post AT', 210, '验收测试后', 'N', null, null);
commit;
prompt 43 records loaded
prompt Loading ISSUE_CATEGORY_TYPE...
insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'SubProcess', 1, '分类项目问题所发生的子过程域', 'N', null, null);
insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Remove Stage', 2, '缺陷排除阶段', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'CRR Type', 1, null, 'N', null, null);
insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Inject Stage', 1, '缺陷引入阶段', 'N', null, null);
insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'SubProcess', 1, '定义项目问题所发生的过程域', 'N', null, to_date('22-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Type', 3, '缺陷类型标准', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Defect Impact', 4, '缺陷的影响程度', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'));
commit;
prompt 7 records loaded
prompt Loading ISSUE_PRIORITY...
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'NORMAL', 3, 1, 'Normal', 'N', null, to_date('22-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'URGE', 1, 2, 'URGE', 'N', null, to_date('22-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST (ABU)', 'TEST', 1, 3, 'a', 'N', null, to_date('17-11-2005', 'dd-mm-yyyy'));
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST (ABU)', 'ＢＢ', 0, 2, 'ｑｑ', 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST(ABU)', '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890', 0, 1, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST(ABU)', '１', 0, 2, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST(ABU)', 'A', -1, 3, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST(ABU)', 'Ｓ', 0, 7, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST(ABU)', 'Ｑ', 0, 0, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST(ABU)', 'C', 0, 10, '12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890', 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, '12345678901234567890123456789012345678901234567890', 'ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ', 0, 1, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', '3', 0, 1, null, 'X', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', '2', 3, 1, null, 'X', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST_2', 'KKKK', 0, 1, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST_2', 'HHH', 0, 2, null, 'X', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST_2', 'JJJ', 8, 5, null, 'N', null, to_date('28-11-2005', 'dd-mm-yyyy'));
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'NEW_ISSUE', 'PRIORITY_1', 1, 1, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'NEW_ISSUE', 'PRIORITY_2', 2, 2, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'NEW_ISSUE', 'PRIORITY_3', 3, 3, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q＆A', 'NORMAL', 1, 1, 'NORMAL', 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST_1', 'NORMAL', 3, 1, null, 'N', null, to_date('06-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q＆A', 'URGE', 1, 2, 'URGE', 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST_1', 'URGE', 1, 2, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'NORMAL', 3, 1, null, 'N', null, to_date('08-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'URGED', 1, 2, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'NORMAL', 3, 1, 'Normal', 'N', null, to_date('15-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'URGE', 1, 2, null, 'N', null, to_date('15-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'NORMAL', 3, 2, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'URGE', 1, 3, null, 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST_TYPE', 'TNORMAL', 1, 3, null, 'X', null, to_date('16-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST_TYPE', 'TURGE', 1, 2, null, 'X', null, null);
commit;
prompt 31 records loaded
prompt Loading ISSUE_RISK...
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Delivery', 1, 5, 3, 1, null, 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Cost', 1, 5, 1, 3, null, 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Quality', 1, 5, 2, 2, null, 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'Delivery', 1, 5, 3, 1, null, 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q＆A', 'Delivered', 1, 1, 1, 1, null, 'X', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', '3', 1, 5, 5, 5, null, 'X', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', '4', 1, 6, 8, 3, null, 'X', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'Quality', 1, 5, 2, 2, null, 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'Cost', 1, 5, 1, 3, null, 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', '技术难度', 1, 5, 1, 1, null, 'X', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', '对进度影响', 1, 5, 1, 2, null, 'X', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', '对其它模块影响', 1, 5, 1, 3, null, 'X', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', '进度', 1, 5, 1, 6, null, 'X', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Tech. Feasibility', 1, 5, 1, 1, null, 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Cost', 1, 5, 1, 2, null, 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Delivery', 1, 5, 1, 3, null, 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'Tech. Feasibility', 1, 5, 1, 1, null, 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'Cost', 1, 5, 1, 2, null, 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'Delivery', 1, 5, 1, 3, null, 'N', null, null);
commit;
prompt 19 records loaded
prompt Loading ISSUE_SCOPE...
insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Company', 'N', 1, 'Company', 'N', null, null);
insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Customer', 'Y', 2, 'Customer', 'N', null, null);
insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST_TYPE', 'TCompany', 'Y', 1, null, 'X', null, null);
insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'Company', 'N', 1, 'company', 'N', null, null);
insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'Company', 'N', 2, 'company', 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'Customer', 'Y', 2, 'customer', 'N', null, null);
insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'Customer', 'Y', 1, 'customer', 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q＆A', 'Company', 'N', 2, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q＆A', 'Customer', 'Y', 1, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Company', 'N', 1, null, 'N', null, null);
insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Customer', 'Y', 2, null, 'N', null, null);
commit;
prompt 11 records loaded
prompt Loading ISSUE_STATUS...
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Finished', 'Processing', 'Finished Date', 3, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'Received', 'Received', null, 1, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'Assigned', 'Processing', 'Assigned Date', 2, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Delivered', 'Delivered', 'Delivered Date', 4, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Closed', 'Closed', 'Confirm Date', 5, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Rejected', 'Rejected', 'Assigned Date', 11, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Duplation', 'Duplation', 'Assigned Date', 22, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q＆A', 'Received', 'Received', null, 1, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'Delivered', 'Delivered', 'Delivered Date', 4, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'Delivered', 'Delivered', 'Delivered Date', 4, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'Duplation', 'Duplation', 'Assigned Date', 21, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'Closed', 'Closed', 'Confirm Date', 5, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Received', 'Received', null, 1, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Assigned', 'Processing', 'Assigned Date', 2, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'Finished', 'Processing', 'Finished Date', 3, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'TEST_TYPE', 'TRECEIVED', 'Received', null, 2, null, 'X', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Received', 'Received', null, 1, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Delivered', 'Delivered', 'Delivered Date', 4, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Closed', 'Closed', 'Confirm Date', 5, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Duplation', 'Duplation', 'Assigned Date', 22, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Rejected', 'Rejected', 'Assigned Date', 11, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'Rejected', 'Rejected', 'Assigned Date', 11, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'Finished', 'Processing', 'Finished Date', 3, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'Closed', 'Closed', 'Confirm Date', 5, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'Duplation', 'Duplation', 'Assigned Date', 22, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q＆A', 'Rejected', 'Rejected', 'Assigned Date', 11, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q＆A', 'Delivered', 'Delivered', 'Delivered Date', 4, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q＆A', 'Closed', 'Closed', 'Confirm Date', 5, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q＆A', 'Duplation', 'Duplation', 'Assigned Date', 22, null, 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'));
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 'Rejected', 'Rejected', 'Assigned Date', 11, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Assigned', 'Processing', 'Assigned Date', 2, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 'Finished', 'Processing', 'Finished Date', 3, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'Received', 'Received', null, 1, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 'Assigned', 'Processing', 'Assigned Date', 2, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q＆A', 'Assigned', 'Processing', 'Assigned Date', 2, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q＆A', 'Finished', 'Processing', 'Finished Date', 3, null, 'N', null, null);
commit;
prompt 36 records loaded
prompt Loading ISSUE_TYPE...
insert into ISSUE_TYPE (RID, TYPE_NAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT, SAVE_STATUS_HISTORY, SAVE_INFLUENCE_HISTORY)
values (null, 'ISSUE', 1, 'Project Issue', 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'), 'N', 'N');
insert into ISSUE_TYPE (RID, TYPE_NAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT, SAVE_STATUS_HISTORY, SAVE_INFLUENCE_HISTORY)
values (null, 'PPR', 2, 'Project Problem Report', 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'), 'Y', 'Y');
insert into ISSUE_TYPE (RID, TYPE_NAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT, SAVE_STATUS_HISTORY, SAVE_INFLUENCE_HISTORY)
values (null, 'SPR', 3, 'Software Problem Report', 'N', null, to_date('26-12-2005', 'dd-mm-yyyy'), 'Y', 'Y');
insert into ISSUE_TYPE (RID, TYPE_NAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT, SAVE_STATUS_HISTORY, SAVE_INFLUENCE_HISTORY)
values (null, 'CRR', 4, 'Change Request Report', 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'), 'Y', 'Y');
insert into ISSUE_TYPE (RID, TYPE_NAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT, SAVE_STATUS_HISTORY, SAVE_INFLUENCE_HISTORY)
values (null, 'TEST_TYPE', 6, null, 'X', null, to_date('16-12-2005', 'dd-mm-yyyy'), 'Y', 'Y');
insert into ISSUE_TYPE (RID, TYPE_NAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT, SAVE_STATUS_HISTORY, SAVE_INFLUENCE_HISTORY)
values (null, 'Q＆A', 5, 'Question ＆Answer', 'N', null, to_date('23-12-2005', 'dd-mm-yyyy'), 'N', 'N');
commit;
prompt 6 records loaded
prompt Enabling triggers for ISSUE_CATEGORY...
alter table ISSUE_CATEGORY enable all triggers;
prompt Enabling triggers for ISSUE_CATEGORY_TYPE...
alter table ISSUE_CATEGORY_TYPE enable all triggers;
prompt Enabling triggers for ISSUE_PRIORITY...
alter table ISSUE_PRIORITY enable all triggers;
prompt Enabling triggers for ISSUE_RISK...
alter table ISSUE_RISK enable all triggers;
prompt Enabling triggers for ISSUE_SCOPE...
alter table ISSUE_SCOPE enable all triggers;
prompt Enabling triggers for ISSUE_STATUS...
alter table ISSUE_STATUS enable all triggers;
prompt Enabling triggers for ISSUE_TYPE...
alter table ISSUE_TYPE enable all triggers;
set feedback on
set define on
prompt Done.
