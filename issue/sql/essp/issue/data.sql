insert into ISSUE (RID, ACCOUNT_ID, TYPE_NAME, PRIORITY, FILLEBY, FILLEDATE, PHONE, FAX, SCOPE, ISSUE_ID, ISSUE_NAME, DESCRIPTION, ATTACHMENT, ATTACHMENTDESC, PRINCIPAL, DUEDATE, ISSUE_STATUS, DUPLATION_ISSUE, RST, RCT, RUT)
values (2, 123, 'ISSUE', 'NORMAL', null, to_date('01-09-2005 10:04:51', 'dd-mm-yyyy hh24:mi:ss'), null, null, null, null, null, null, null, null, 'pppp', null, null, null, null, null, null);

insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Schedule', 'Schedule', 2, null, 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Delivery', 'Delivery', 21, null, 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Cost', 'Cost', 22, null, 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Quality', 'Quality', 1, null, 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Human Resource', 'Human Resource', 1, null, 'N', null, null);
insert into ISSUE_CATEGORY (RID, TYPE_NAME, CATEGORYNAME, CATEGORYVALUE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Communication', 'Communication', 1, null, 'N', null, null);

insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Schedule', 1, 'Schedule', 'N', null, null);
insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Delivery', 2, 'Delivery', 'N', null, null);
insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Cost', 3, 'Cost', 'N', null, null);
insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Quality', 4, 'Quality', 'N', null, null);
insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Human Resource', 5, 'Human Resource', 'N', null, null);
insert into ISSUE_CATEGORY_TYPE (RID, TYPE_NAME, CATEGORYNAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Communication', 6, 'Communication', 'N', null, null);

insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'NORMAL', 3, 1, 'NORMAL', 'N', null, null);
insert into ISSUE_PRIORITY (RID, TYPE_NAME, PRIORITY, DURATION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'URGE', 1, 2, 'URGE', 'N', null, to_date('18-10-2005 09:49:59', 'dd-mm-yyyy hh24:mi:ss'));

insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Delivery', 1, 5, 3, 1, 'Delivery', 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Cost', 1, 5, 2, 2, 'Cost', 'N', null, null);
insert into ISSUE_RISK (RID, TYPE_NAME, INFLUENCE, MINLEVEL, MAXLEVEL, WEIGHT, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Quality', 1, 5, 2, 3, 'Quality', 'N', null, null);

insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Company', 'N', 1, 'Company', 'N', null, null);
insert into ISSUE_SCOPE (RID, TYPE_NAME, SCOPE, VISION, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Customer', 'Y', 2, 'Customer', 'N', null, null);

insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Received', 'Processing', 'Assigned Date', 1, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Assigned', 'Processing', 'Assigned Date', 1, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Finished', 'Processing', 'Assigned Date', 1, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Delivered', 'Delivered', 'Assigned Date', 1, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Closed', 'Closed', 'Assigned Date', 1, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Rejected', 'Rejected', 'Assigned Date', 1, null, 'N', null, null);
insert into ISSUE_STATUS (RID, TYPE_NAME, STATUSNAME, BELONGTO, RELATIONDATE, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 'Duplation', 'Duplation', 'Assigned Date', 1, null, 'N', null, null);

insert into ISSUE_TYPE (RID, TYPE_NAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'ISSUE', 1, 'Issue', 'N', null, to_date('25-10-2005 11:04:51', 'dd-mm-yyyy hh24:mi:ss'));
insert into ISSUE_TYPE (RID, TYPE_NAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'PPR', 3, 'Project Problem Report', 'N', null, null);
insert into ISSUE_TYPE (RID, TYPE_NAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'SPR', 4, 'Software Problem Report', 'N', null, null);
insert into ISSUE_TYPE (RID, TYPE_NAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'CRR', 5, 'Customer Problem Report', 'N', null, null);
insert into ISSUE_TYPE (RID, TYPE_NAME, SEQUENCE, DESCRIPTION, RST, RCT, RUT)
values (null, 'Q&A', 2, 'Q&A', 'N', null, to_date('18-10-2005 09:42:07', 'dd-mm-yyyy hh24:mi:ss'));

commit;

