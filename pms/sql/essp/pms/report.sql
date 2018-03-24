select t.TYPE,
       t.ACTIVITY_RID,
       t.NAME,
       t.PLANNED_START,
       t.PLANNED_FINISH,
       t.ACTUAL_START,
       t.ACTUAL_FINISH,
       t.ACTIVITY_PLANNED_START,
       t.ACTIVITY_ACTUAL_START 
from project_weekly_report t 
where t.ACNT_RID='1'
  and (PLANNED_START is not null and PLANNED_FINISH is not null)
  and not(
          (PLANNED_FINISH < to_date('2005-9-12','yyyy-MM-dd')) or 
          (PLANNED_START > to_date('2005-9-23','yyyy-MM-dd')) 
          )
   or not(
          (ACTUAL_FINISH < to_date('2005-9-12','yyyy-MM-dd')) or 
          (ACTUAL_START > to_date('2005-9-23','yyyy-MM-dd')) 
         )   
UNION 
select t.TYPE,
       t.ACTIVITY_RID,
       t.NAME,
       t.PLANNED_START,
       t.PLANNED_FINISH,
       t.ACTUAL_START,
       t.ACTUAL_FINISH,
       t.ACTIVITY_PLANNED_START,
       t.ACTIVITY_ACTUAL_START 
from project_weekly_report t 
where t.ACNT_RID = '1' 
  and t.TYPE='A'
  and t.ACTIVITY_RID in (select ACTIVITY_RID 
                         from project_weekly_report 
                         where ACNT_RID = '1' 
                           and TYPE='P'
                           and (PLANNED_START is null and PLANNED_FINISH is null)
                           and not(
                                   (PLANNED_FINISH < to_date('2005-9-12','yyyy-MM-dd')) or 
                                   (PLANNED_START > to_date('2005-9-23','yyyy-MM-dd')) 
                                   )  
                            or not(
                                   (ACTUAL_FINISH < to_date('2005-9-12','yyyy-MM-dd')) or 
                                   (ACTUAL_START > to_date('2005-9-23','yyyy-MM-dd')) 
                                   ) 
                         )        
order by ACTIVITY_PLANNED_START,ACTIVITY_ACTUAL_START,ACTIVITY_RID,type,PLANNED_START,PLANNED_FINISH 