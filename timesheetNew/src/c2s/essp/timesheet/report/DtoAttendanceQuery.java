/*
 * Created on 2008-6-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package c2s.essp.timesheet.report;

import java.util.Date;

import c2s.dto.DtoBase;

public class DtoAttendanceQuery extends DtoBase{
       public static String DTO_ANNUAL_LEAVE="Annual-Leave";
       public static String DTO_MEDICAL_LEAVE="Medical-Leave";
       public static String DTO_MARRIAGE_LEAVE="Marriage-Leave";
       public static String DTO_PRIVATE_LAEAVE ="Private-Leave";
       public static String DTO_SHIFT_ADJUSTMENT="Shift-Adjustment";
       public static String DTO_SICK_LEAVE="Sick-Leave";
       public static String DTO_FUNERAL_LEAVE="Funeral-Leave";
       public static String DTO_MATERNITY_LEAVE="Maternity-Leave";
       public static String DTO_LACTATION_LEAVE="Lactation-Leave";
       public static String DTO_WORK_INJURY_LEAVE="WorkInjury-Leave";
       public static String DTO_OHTERS_DEDUCT="Others-Deduct";
       public static String DTO_OHTHERS_NONDEDUCT="Others-Nondeduct";
       public static String DTO_ABSENTEEISM = "Absenteeism";
       
       public final static String DTO_BEGIN_DATE="beginDate";
       public final static String DTO_END_DATE="endDate";
       public final static String DTO_SITE="site";
       public final static String DTO_RESULT_LIST = "resultList";
       public final static String DTO_QUERY="dtoQuery";
       private Date begin;
       private Date end;
       private String site;
       
        public String getSite() {
        return site;
        }
        public void setSite(String site) {
            this.site = site;
        }
        public Date getBegin() {
            return begin;
        }
        public void setBegin(Date begin) {
            this.begin = begin;
        }
        public Date getEnd() {
            return end;
        }
        public void setEnd(Date end) {
            this.end = end;
        }
       
}
