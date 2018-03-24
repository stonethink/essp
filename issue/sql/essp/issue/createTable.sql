create table ISSUE
(
  RID             NUMBER not null,
  ACCOUNT_ID      NUMBER not null,
  TYPE_NAME       VARCHAR2(100) not null,
  PRIORITY        VARCHAR2(100),
  FILLEBY         VARCHAR2(100),
  FILLEDATE       DATE,
  PHONE           VARCHAR2(100),
  FAX             VARCHAR2(100),
  EMAIL           VARCHAR2(100),
  SCOPE          VARCHAR2(100),
  ISSUE_ID        VARCHAR2(100),
  ISSUE_NAME      VARCHAR2(1000),
  DESCRIPTION     VARCHAR2(2000),
  ATTACHMENT      VARCHAR2(100),
  ATTACHMENT_ID   VARCHAR2(100),
  ATTACHMENTDESC  VARCHAR2(500),
  PRINCIPAL       VARCHAR2(100),
  DUEDATE         DATE,
  ISSUE_STATUS    VARCHAR2(100),
  DUPLATION_ISSUE NUMBER,
  RST             CHAR(1),
  RCT             DATE,
  RUT             DATE,
  primary key (RID)
);

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
  RUT           DATE,
  primary key (TYPE_NAME, CATEGORYNAME, CATEGORYVALUE)
);

create table ISSUE_CATEGORY_TYPE
(
  RID          NUMBER,
  TYPE_NAME    VARCHAR2(100) not null,
  CATEGORYNAME VARCHAR2(100) not null,
  SEQUENCE     NUMBER,
  DESCRIPTION  VARCHAR2(500),
  RST          CHAR(1),
  RCT          DATE,
  RUT          DATE,
  primary key (TYPE_NAME, CATEGORYNAME)
);

create table ISSUE_CONCLUSION
(
  RID                 NUMBER not null,
  ACTUAL_INFLUENCE    VARCHAR2(2000),
  SOLVED_DESCRIPTION  VARCHAR2(2000),
  FINISHED_DATE       DATE,
  DELIVERED_DATE      DATE,
  ATTACHMENT          VARCHAR2(100),
  ATTACHMENT_ID       VARCHAR2(100),
  ATTACHMENT_DESC     VARCHAR2(500),
  CLOSURE_STATUS      VARCHAR2(50),
  CONFIRM_DATE        DATE,
  CONFIRM_BY          VARCHAR2(50),
  INSTRUCTION_CLOSURE VARCHAR2(500),
  RST                 CHAR(1),
  RCT                 DATE,
  RUT                 DATE,
  primary key (RID)
);

create table ISSUE_CONCLUSION_UG
(
  RID            NUMBER not null,
  ISSUE_RID      NUMBER,
  URGEDBY        VARCHAR2(100),
  URGETO         VARCHAR2(100),
  URGDATE        DATE,
  DESCRIPTION    VARCHAR2(2000),
  ATTACHMENT     VARCHAR2(100),
  ATTACHMENT_ID  VARCHAR2(100),
  ATTACHMENTDESC VARCHAR2(500),
  RST            CHAR(1),
  RCT            DATE,
  RUT            DATE,
  primary key (RID)
);

create table ISSUE_DISCUSS_REPLY
(
  RID             NUMBER not null,
  TITLE_ID        NUMBER,
  TITLE           VARCHAR2(500),
  DESCRIPTION     VARCHAR2(2000),
  FILLED_DATE     DATE,
  FILLED_BY       VARCHAR2(50),
  ATTACHMENT      VARCHAR2(100),
  ATTACHMENT_ID   VARCHAR2(100),
  ATTACHMENT_DESC VARCHAR2(500),
  RST             CHAR(1),
  RCT             DATE,
  RUT             DATE,
  primary key (RID)
);

create table ISSUE_DISCUSS_TITLE
(
  RID             NUMBER not null,
  ISSUE_RID       NUMBER,
  TITLE           VARCHAR2(500),
  DESCRIPTION     VARCHAR2(2000),
  FILLED_DATE     DATE,
  FILLED_BY       VARCHAR2(50),
  ATTACHMENT      VARCHAR2(100),
  ATTACHMENT_ID   VARCHAR2(100),
  ATTACHMENT_DESC VARCHAR2(500),
  RST             CHAR(1),
  RCT             DATE,
  RUT             DATE,
  primary key (RID)
);

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
  RUT         DATE,
  primary key (TYPE_NAME, PRIORITY)
);

create table ISSUE_REPORTSTATUS
(
  RID            NUMBER not null,
  ACCOUNT_ID     NUMBER,
  ISSUE_TYPE     VARCHAR2(100),
  ISSUE_STATUS   VARCHAR2(100),
  PROCESSINGDATE DATE,
  DELIVERDDATE   DATE,
  CLOSEDDATE     DATE,
  REJECTDATE     DATE,
  DUPLATIONDATE  DATE,
  RST            CHAR(1),
  RCT            DATE,
  RUT            DATE,
  primary key (RID)
);

create table ISSUE_RESOLUTION
(
  RID             NUMBER not null,
  PROBABILITY     NUMBER,
  RISK_LEVEL      NUMBER,
  ASSIGNED_DATE   DATE,
  RESOLUTION      VARCHAR2(2000),
  PLAN_FINISHDATE DATE,
  RESOLUTION_BY   VARCHAR2(500),
  ATTACHMENT      VARCHAR2(100),
  ATTACHMENT_ID   VARCHAR2(100),
  ATTACHMENTDESC  VARCHAR2(500),
  RST             CHAR(1),
  RCT             DATE,
  RUT             DATE,
  primary key (RID)
);

create table ISSUE_RESOLUTION_CATEGORY
(
  RID           NUMBER not null,
  ISSUE_RID     NUMBER,
  CATEGORYNAME  VARCHAR2(100),
  CATEGORYVALUE VARCHAR2(100),
  RST           CHAR(1),
  RCT           DATE,
  RUT           DATE,
  primary key (RID)
);

create table ISSUE_RESOLUTION_INFLUE
(
  RID            NUMBER not null,
  ISSUE_RID      NUMBER,
  INFLUENCE_NAME VARCHAR2(100),
  IMPACT_LEVEL   NUMBER,
  WEIGHT         NUMBER,
  REMARK         NUMBER,
  RST            CHAR(1),
  RCT            DATE,
  RUT            DATE,
  primary key (RID)
);

create table ISSUE_RISK
(
  RID         NUMBER,
  TYPE_NAME   VARCHAR2(100) not null,
  INFLUENCE   VARCHAR2(100) not null,
  MINLEVEL    NUMBER,
  MAXLEVEL    NUMBER,
  WEIGHT      NUMBER,
  SEQUENCE   NUMBER,
  DESCRIPTION VARCHAR2(2000),
  RST         CHAR(1),
  RCT         DATE,
  RUT         DATE,
  primary key (TYPE_NAME, INFLUENCE)
);

create table ISSUE_SCOPE
(
  RID         NUMBER,
  TYPE_NAME   VARCHAR2(100) not null,
  SCOPE       VARCHAR2(100) not null,
  VISION      VARCHAR2(10),
  SEQUENCE   NUMBER,
  DESCRIPTION VARCHAR2(500),
  RST         CHAR(1),
  RCT         DATE,
  RUT         DATE,
  primary key (TYPE_NAME, SCOPE)
);

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
  RUT          DATE,
  primary key (TYPE_NAME, STATUSNAME)
);

create table ISSUE_TYPE
(
  RID         NUMBER,
  TYPE_NAME   VARCHAR2(100) not null,
  SEQUENCE    NUMBER,
  DESCRIPTION VARCHAR2(500),
  RST         CHAR(1),
  RCT         DATE,
  RUT         DATE,
  primary key (TYPE_NAME)
);