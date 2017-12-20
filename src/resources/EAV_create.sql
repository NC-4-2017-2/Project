begin
  execute immediate 'drop table OBJTYPE cascade constraint';
  exception
  when others then null;
end;
/
CREATE TABLE OBJTYPE
(
  OBJECT_TYPE_ID NUMBER(20) NOT NULL ENABLE,
  PARENT_ID      NUMBER(20),
  CODE           VARCHAR2(20) ,
  NAME           VARCHAR2(200 BYTE),
  DESCRIPTION    VARCHAR2(1000 BYTE),
  CONSTRAINT CON_OBJECT_TYPE_ID PRIMARY KEY (OBJECT_TYPE_ID),
  CONSTRAINT CON_PARENT_ID  FOREIGN KEY (PARENT_ID) REFERENCES OBJTYPE (OBJECT_TYPE_ID) ON DELETE CASCADE enable
);

-----
begin
  execute immediate 'drop table OBJECTS cascade constraint';
  exception
  when others then null;
end;
/

CREATE TABLE OBJECTS (
  OBJECT_ID      NUMBER(20) NOT NULL,
  PARENT_ID      NUMBER(20),
  OBJECT_TYPE_ID NUMBER(20) NOT NULL,
  NAME           VARCHAR2(2000 BYTE),
  DESCRIPTION    VARCHAR2(4000 BYTE),
  OBJECT_VERSION NUMBER(20) NOT NULL,
  CONSTRAINT CON_OBJECTS_ID PRIMARY KEY (OBJECT_ID),
  CONSTRAINT CON_PARENTS_ID FOREIGN KEY (PARENT_ID) REFERENCES OBJECTS (OBJECT_ID) ON DELETE CASCADE,
  CONSTRAINT CON_OBJ_TYPE_ID FOREIGN KEY (OBJECT_TYPE_ID) REFERENCES OBJTYPE (OBJECT_TYPE_ID)
);

-----
begin
  execute immediate 'drop table OBJREFERENCE cascade constraint';
  exception
  when others then null;
end;
/

CREATE TABLE OBJREFERENCE
(
  ATTR_ID   NUMBER(20) NOT NULL,
  REFERENCE NUMBER(20) NOT NULL,
  OBJECT_ID NUMBER(20) NOT NULL,
  CONSTRAINT CON_OBJREFERENCE_PK PRIMARY KEY (ATTR_ID,REFERENCE,OBJECT_ID),
  CONSTRAINT CON_REFERENCE FOREIGN KEY (REFERENCE) REFERENCES OBJECTS (OBJECT_ID) ON DELETE CASCADE,
  CONSTRAINT CON_ROBJECT_ID FOREIGN KEY (OBJECT_ID) REFERENCES OBJECTS (OBJECT_ID) ON DELETE CASCADE,
  CONSTRAINT CON_RATTR_ID FOREIGN KEY (ATTR_ID) REFERENCES ATTRTYPE (ATTR_ID) ON DELETE CASCADE
);

-----
begin
  execute immediate 'drop table ATTRTYPE cascade constraint';
  exception
  when others then null;
end;
/

  CREATE TABLE ATTRTYPE (
  ATTR_ID      		NUMBER(20) NOT NULL,
  OBJECT_TYPE_ID 		NUMBER(20) NOT NULL,
  OBJECT_TYPE_ID_REF 	NUMBER(20),
  CODE         		VARCHAR2(20),
  NAME         		VARCHAR2(200 BYTE),
  CONSTRAINT CON_ATTR_ID PRIMARY KEY (ATTR_ID),
  CONSTRAINT CON_ATTR_OBJECT_TYPE_ID FOREIGN KEY (OBJECT_TYPE_ID) REFERENCES OBJTYPE (OBJECT_TYPE_ID),
  CONSTRAINT CON_ATTR_OBJECT_TYPE_ID_REF FOREIGN KEY (OBJECT_TYPE_ID_REF) REFERENCES OBJTYPE (OBJECT_TYPE_ID)
);
-----
begin
  execute immediate 'drop table ATTRIBUTES cascade constraint';
  exception
  when others then null;
end;
/

CREATE TABLE ATTRIBUTES
(
  ATTR_ID    NUMBER(20) NOT NULL,
  OBJECT_ID  NUMBER(20) NOT NULL,
  VALUE      VARCHAR2(4000 BYTE),
  DATE_VALUE DATE,
  LIST_VALUE_ID NUMBER(20),
  CONSTRAINT CON_AOBJECT_ID FOREIGN KEY (OBJECT_ID) REFERENCES OBJECTS (OBJECT_ID) ON DELETE CASCADE,
  CONSTRAINT CON_AATTR_ID FOREIGN KEY (ATTR_ID) REFERENCES ATTRTYPE (ATTR_ID) ON DELETE CASCADE
);

-----
  begin
  execute immediate 'drop table LISTVALUE cascade constraint';
  exception
  when others then null;
end;
/

create table LISTVALUE
(
  ATTR_ID       NUMBER(20) NOT NULL,
  LIST_VALUE_ID NUMBER(20) NOT NULL,
  VALUE      VARCHAR2(4000 BYTE),
  CONSTRAINT CON_LATT_ID FOREIGN KEY (ATTR_ID) REFERENCES ATTRTYPE (ATTR_ID) ON DELETE CASCADE
);




/*ObjTypes*/
INSERT INTO OBJTYPE (OBJECT_TYPE_ID,PARENT_ID,CODE,NAME,DESCRIPTION) VALUES (1,NULL,'USER','User profile',NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID,PARENT_ID,CODE,NAME,DESCRIPTION) VALUES (2,NULL,'PROJECT','Project',NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID,PARENT_ID,CODE,NAME,DESCRIPTION) VALUES (3,NULL,'TASK','Task',NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID,PARENT_ID,CODE,NAME,DESCRIPTION) VALUES (4,NULL,'BUSINESS_TRIP','Business Trip',NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID,PARENT_ID,CODE,NAME,DESCRIPTION) VALUES (5,NULL,'VACATION','Vacation',NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID,PARENT_ID,CODE,NAME,DESCRIPTION) VALUES (6,NULL,'WORKING_DAY','Working Day',NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID,PARENT_ID,CODE,NAME,DESCRIPTION) VALUES (7,NULL,'SPRINT','Sprint',NULL);
INSERT INTO OBJTYPE (OBJECT_TYPE_ID,PARENT_ID,CODE,NAME,DESCRIPTION) VALUES (8,NULL,'WORK_PERIOD','Work period',NULL);


/*AttrTypes*/
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (1,1,NULL,'LAST_NAME','Last name');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (2,1,NULL,'FIRST_NAME','First name');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (3,1,NULL,'EMAIL','Email');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (4,1,NULL,'BIRTH_DATE','Birth date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (5,1,NULL,'HIRE_DATE','Hire date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (6,1,NULL,'PHONE_NUMBER','Phone number');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (7,1,NULL,'PHOTO','Phone');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (8,1,NULL,'JOB_TITLE','Job');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (9,1,NULL,'USER_PROJECT_STATUS','User project status');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (10,1,NULL,'LOGIN','Login');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (11,1,NULL,'PASSWORD','Password');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (12,1,NULL,'ROLE','Role');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (13,1,NULL,'USER_STATUS','User status in system');


INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (14,2,NULL,'PROJECT_NAME','Project name');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (15,2,NULL,'START_DATE','Project start date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (16,2,NULL,'END_DATE','Project end date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (17,2,NULL,'PROJECT_STATUS','Project status');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (18,2,1,'MANAGES_PROJECT','PM manages the project');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (19,2,1,'WORKS_ON_PROJECT','Employee works on the project');


INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (20,3,NULL,'TASK_NAME','Task name');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (21,3,NULL,'TASK_TYPE','Task type');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (22,3,NULL,'START_DATE','Task start date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (23,3,NULL,'END_DATE','Task end date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (24,3,NULL,'PLANNED_END_DATE','Planned end task');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (25,3,NULL,'TASK_PRIORITY','Task priority');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (26,3,NULL,'TASK_STATUS','Task status');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (27,3,NULL,'DESCRIPTION','Task description');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (28,3,NULL,'REOPEN_COUNTER','Reopen counter');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (29,3,NULL,'COMMENT','Task comment');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (30,3,1,'TASK_AUTHOR','Author of the task');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (31,3,1,'TASK_BELONGS_TO','Employee who is responsible for this task');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (32,3,2,'TASK_PROJECT','Task project');


INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (33,4,NULL,'COUNTRY','Country for the trip');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (34,4,NULL,'START_DATE','Trip start date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (35,4,NULL,'END_DATE','Trip end date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (36,4,NULL,'TRIP_STATUS','Trip status');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (37,4,1,'TRIP_USER','User for business trip');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (38,4,1,'TRIP_AUTHOR','Business trip author');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (39,4,2,'TRIP_PROJECT','Business trip project');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (40,4,1,'TRIP_PM_APPROVE','PM who approves business trip');


INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (41,5,NULL,'START_DATE','Vacation start date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (42,5,NULL,'END_DATE','Vacation end date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (43,5,NULL,'PM_APPROVE_STATUS','PM approve status');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (44,5,NULL,'LM_APPROVE_STATUS','LM approve status');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (45,5,1,'VACATION_AUTHOR','User who assigned vacation');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (46,5,2,'VACATION_PROJECT','Project which vacation belongs to');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (47,5,1,'PM_ID_APPROVE','PM who approved vacation');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (48,5,1,'LM_ID_APPROVE','LM who approved vacation');


INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (49,6,NULL,'DATE','Working day date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (50,6,NULL,'WEEK_NUMBER','Working day week number');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (51,6,NULL,'WORKING_HOURS','Working day worked hours');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (52,6,NULL,'WORKING_HOURS_STATUS','Working day status');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (53,6,1,'WORKING_HOURS_AUTHOR','Employee who writes his own working hours');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (54,6,1,'PM_APPROVE','PM who approves working hours');


INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (55,7,NULL,'SPRINT_NAME','Sprint name');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (56,7,NULL,'START_DATE','Sprint start date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (57,7,NULL,'END_DATE','Sprint end date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (58,7,NULL,'PLANNED_END_DATE','Planned end date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (59,7,NULL,'SPRINT_STATUS','Status of sprint');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (60,7,2,'SPRINT_PROJECT','Project of this sprint');


INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (61,8,NULL,'WP_START_DATE','Work period start date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (62,8,NULL,'WP_END_DATE','Work period end date');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (63,8,NULL,'WP_STATUS','Work period status');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (64,8,1,'WP_USER','Work period user');
INSERT INTO ATTRTYPE (ATTR_ID,OBJECT_TYPE_ID,OBJECT_TYPE_ID_REF,CODE,NAME) VALUES (65,8,2,'WP_PROJECT','Project of this working period');


INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (8,0,'PROJECT_MANAGER');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (8,1,'LINE_MANAGER');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (8,2,'SOFTWARE_ENGINEER');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (9,0,'WORKING');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (9,1,'TRANSIT');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (12,0,'ROLE_ADMIN');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (12,1,'ROLE_PM');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (12,2,'ROLE_LM');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (12,3,'ROLE_SE');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (13,0,'WORKING');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (13,1,'FIRED');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (17,0,'OPENED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (17,1,'CLOSED');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (21,0,'REQUEST_TASK');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (21,1,'PROJECT_TASK');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (25,0,'CRITICAL');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (25,1,'HIGH');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (25,2,'NORMAL');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (25,3,'LOW');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (26,0,'OPENED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (26,1,'CLOSED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (26,2,'REOPENED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (26,3,'READY_FOR_TESTING');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (36,0,'APPROVED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (36,1,'DISAPPROVED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (36,2,'WAITING_FOR_APPROVAL');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (43,0,'APPROVED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (43,1,'DISAPPROVED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (36,2,'WAITING_FOR_APPROVAL');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (44,0,'APPROVED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (44,1,'DISAPPROVED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (36,2,'WAITING_FOR_APPROVAL');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (52,0,'APPROVED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (52,1,'DISAPPROVED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (36,2,'WAITING_FOR_APPROVAL');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (59,0,'OPENED');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (59,1,'CLOSED');

INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (63,0,'WORKING');
INSERT INTO LISTVALUE (ATTR_ID,LIST_VALUE_ID,VALUE) VALUES (63,1,'FIRED');