package server.essp.timecard.timecard.logic;

import c2s.dto.*;
import c2s.essp.timecard.timecard.*;

import com.wits.db.*;
import com.wits.util.*;

import essp.tables.*;

import net.sf.hibernate.*;

import org.apache.log4j.*;

import server.essp.common.timecard.*;
import server.essp.timecard.timecard.data.*;
import server.essp.timecard.worktime.data.*;
import server.framework.hibernate.*;
import server.framework.logic.AbstractBusinessLogic;

import java.util.*;
import java.util.ArrayList;

import javax.sql.*;
import server.framework.common.BusinessException;

/**
 *
 * <p>Title: 项目周期工时表数据获取</p>
 * <p>Description: 项目的周期数据获取逻辑Bean</p>
 * 输入周期起止，查询数据库中是否有数据，如果有则直接从数据库获取，如果没有将查询日报数据库、项目数据及差勤数据
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class FTC01010GeneralLogic extends AbstractBusinessLogic  {
    public static final String DISP_WORKHOUR    = "DtoTcTimecardList";
    public static final String DISP_DAILYREPORT = "DtoPwWkitemList";
    public static final String DISP_CHANGE      = "DtoQueryCondition";
    public static final String INIT_FLAG        = "1";
    static Category      log = Category.getInstance("server");
    static String[][] sIWorkHour = {
                                       { DISP_WORKHOUR, "FTC01010.I01" }
                                   };
    private String    gStart;
    private String    gEnd;

    //当前周期初始化状态值为未审核
    private String  status      = ProjectTimeCard.STATUS_OPEN;
    private float   recongized = 0;
    private boolean isHasData = false;
    private boolean pmFlag;
    HBComAccess hbAccess ;

    /**
     * 工作时间明细数据,里面全是Row，每一Row代表EmpTimeCard对象
     */
    ArrayList empTimeCards = new ArrayList();

    /**
     * 工作时间日报数据
     */
    ArrayList dailyReports = new ArrayList();

    public FTC01010GeneralLogic() {
    }

    /**
     * 获取工时数据<br>
     * 1.首先判断是否有当前周期，当前项目的数据<br>
     * 2.如果有数据，将重新获取，然后重新计算<br>
     * 3.如果没有数据，将(1)获取项目中的所有人；(2)获取这些人的所有范围内的日报，计算工时；(3)获得员工所有差勤记录，计算后，记录到数据库中<br>
     * 4.返回数据库中数据
     * @param data VDView_Data
     */
    public void getValue(TransactionData data) {
        log.debug("TimeCard Servlet-Pro:IN ");

        DtoQueryCondition dqc = (DtoQueryCondition) data.getInputInfo()
                                                        .getInputObj("DtoQueryCondition");
        ProjectTimeCard   ptc = initProjectTimeCard(dqc);

        try {

            //0.判断是否为项目经理，如果是项目经理，需要重新获取数据；否则只读取记录表
            if (pmFlag) {
                int       i = 0;

                ArrayList htb = new ArrayList();

                //1.断是否有当前周期，当前项目的数据
                if (isSubmit(dqc)) {
                    //2.1如果有数据，直接从数据库获取
                    empTimeCards = getData(dqc);

                    //2.2将日报数据合并到已有数据中
                    htb = getReportData(dqc);

                    //2.3获取日报明细
                    dailyReports = getDailyReport(empTimeCards, 1);

                    i = empTimeCards.size();
                } else {
                    //3.1获取员工列表及日报数据
                    htb = getEmployess(String.valueOf(ptc.getProjID()),
                                       comDate.dateToString(ptc.getWeeklyStart(),"yyyy-MM-dd"),
                                       comDate.dateToString(ptc
                                                            .getWeeklyFinish(),"yyyy-MM-dd"));

                    i = 0;
                }

                if (htb.size() != 0) {
                    //3.2获取差勤数据
                    //3.3转换成DataNode
                    ptc.setEmpTimeCard(htb);
                    ptc.getSum();
                    ptc.setProjSubmitStatus(this.status);
                    ptc.setRecongnizedWorkHours(recongized);
                    empTimeCards.removeAll(empTimeCards);

                    for (i = 0; i < htb.size(); i++) {
                        empTimeCards.add(i,
                                        ((EmpTimeCard) htb.get(i)).getVDData());

                        //vddn_detail.getRow(i).setOP(VDBase.OP_INSERT);
                    }
                }

                empTimeCards.add(i, ptc.getVDData());

                //3.4设置工时和日报明细
                //vddn_detail = vddn_detail;
                if (htb.size() != 0) {
                    dailyReports = getDailyReport(empTimeCards, 1);
                }

                //4.将结果返回
                dqc.setSubmitStatus(this.status);
            } else {
                empTimeCards = getValue(dqc);
                dailyReports = getDailyReport(empTimeCards, 1);
            }

            data.getReturnInfo().setReturnObj(DISP_WORKHOUR, empTimeCards);
            data.getReturnInfo().setReturnObj(DISP_DAILYREPORT, dailyReports);
            data.getReturnInfo().setReturnObj(DISP_CHANGE, dqc);

        } catch (Exception ex) {
                log.error(ex);
                data.getReturnInfo().setError(ex);
        }
    }

    /**
     * 获取员工数据
     * @param data VDView_Data
     */
    public void getSepacialEmpValue(TransactionData data) {
        DtoQueryCondition dqc       = (DtoQueryCondition) data.getInputInfo()
                                                              .getInputObj("DtoQueryCondition");
        String            projectID = String.valueOf(dqc.getProjectID());
        String[]          empIDs    = (String[]) data.getInputInfo()
                                                     .getInputObj("EMPLOYEELIST");
        String            start     = comDate.dateToString(dqc.getWeekStart(),
                                                           "yyyy-MM-dd");
        String            end       = comDate.dateToString(dqc.getWeekEnd(),
                                                           "yyyy-MM-dd");

        ArrayList         htb = null;

        try {

            htb = getEmployess(projectID, start, end, empIDs);

            for (int i = 0; i < htb.size(); i++) {
                empTimeCards.add(i, ((EmpTimeCard) htb.get(i)).getVDData());
            }

            dailyReports = getDailyReport(empTimeCards, 0);
            data.getReturnInfo().setReturnObj(DISP_WORKHOUR, empTimeCards);
            data.getReturnInfo().setReturnObj(DISP_DAILYREPORT, dailyReports);
            data.getReturnInfo().setReturnObj(DISP_CHANGE, dqc);

        } catch (Exception ex) {
                log.error(ex);
                data.getReturnInfo().setError(ex);
        }
    }

    //判断是否存在当前周期中的项目数据
    private boolean isSubmit(DtoQueryCondition data) throws Exception {
        //HBComAccess dba = new HBComAccess();
        String sql = "SELECT * FROM TC_TIMECARD "
                     + " WHERE TMC_WEEKLY_START = to_date('" + gStart
                     + "','yyyy-mm-dd') "
                     + " AND TMC_WEEKLY_FINISH = to_date('" + gEnd
                     + "','yyyy-mm-dd') " + " AND TMC_PROJ_ID = "
                     + data.getProjectID();

        //dba.executeq
        RowSet rs = hbAccess.executeQuery(sql);

        while (rs.next()) {
            return true;
        }

        return false;
    }

    //获取当前周期的TimeCard数据
    public ArrayList getData(DtoQueryCondition data) throws Exception {
        Session se = hbAccess.getSession();

        String  sql = ""
                      + " FROM essp.tables.TcTimecard WHERE TMC_WEEKLY_START = :TMC_WEEKLY_START "
                      + " AND TMC_WEEKLY_FINISH = :TMC_WEEKLY_FINISH "
                      + " AND TMC_PROJ_ID = :TMC_PROJ_ID ";

        Query qur = se.createQuery(sql);
        qur.setDate("TMC_WEEKLY_START", data.getWeekStart());
        qur.setDate("TMC_WEEKLY_FINISH", data.getWeekEnd());
        qur.setLong("TMC_PROJ_ID", data.getProjectID());

        List      timecard    = qur.list();
        ArrayList outTimeCard = new ArrayList();

        for (int i = 0; i < timecard.size(); i++) {
            DtoTcTimecard dtt = new DtoTcTimecard();

            try {
                DtoUtil.copyProperties(dtt,
                                       ((TcTimecard) timecard.get(i))
                                       .getComp_id());
            } catch (Exception ex) {
               ex.printStackTrace();
            }

            try {
                DtoUtil.copyProperties(dtt, (TcTimecard) timecard.get(i));
            } catch (Exception ex1) {
               ex1.printStackTrace();
            }

            outTimeCard.add(dtt);
        }

        return outTimeCard;
    }

  /**
   * 获取日报数据，放于临时DataNode中
   *
   * @param data VDData_Node
   * @return ArrayList
   * @throws Exception
   */
  public ArrayList getReportData(DtoQueryCondition data)
                            throws Exception {
        ArrayList arry = getEmployess(String.valueOf(data.getProjectID()),
                                      comDate.dateToString(data.getWeekStart(),"yyyy-MM-dd"),
                                      comDate.dateToString(data.getWeekEnd(),"yyyy-MM-dd"));

        ArrayList dataBase = new ArrayList(); //数据库中的工时数据
        ArrayList tmpArry  = new ArrayList(); //存放未入入到工时数据中的日报

        //DataBase = arry;
        int i;

        //DataBase = arry;
        int     j;
        boolean isInDaily = false;

        if (empTimeCards.size() == 0) {
            dataBase = arry;
        } else { //有旧有数据时

            for (i = 0; i < empTimeCards.size(); i++) {
                EmpTimeCard   tmpCard = new EmpTimeCard();
                DtoTcTimecard node = (DtoTcTimecard) empTimeCards.get(i);
                tmpCard.setVDData(node);
                dataBase.add(i, tmpCard);
            }

            for (i = 0; i < arry.size(); i++) {
                isInDaily = false;

                EmpTimeCard emptc = new EmpTimeCard();
                emptc = (EmpTimeCard) arry.get(i);

                for (j = 0; j < empTimeCards.size(); j++) {
                    EmpTimeCard dailytc = new EmpTimeCard();

                    //依据VDData设置
                    dailytc.setVDData((DtoTcTimecard) empTimeCards.get(j));

                    //将数据设置到i中
                    if (dailytc.isSameEmp(emptc)) {
                        dailytc.merge(emptc);
                        isInDaily = true;

                        break;
                    }
                }

                //判断哪个员工日报数据未有放入到系统中
                if (!isInDaily) {
                    //设置新加入的数据状态为insert
                    emptc.setOP(DtoTcTimecard.OP_INSERT);
                    tmpArry.add(emptc);
                }
            }

            int iCount = dataBase.size();

            for (i = 0; i < tmpArry.size(); i++) {
                dataBase.add(iCount + i, tmpArry.get(i));
            }
        }

        return dataBase;
    }

    //初始化项目当前周期数据
    public ProjectTimeCard initProjectTimeCard(DtoQueryCondition data) {
        java.util.Date startTime = null;
        java.util.Date endTime = null;

        if (data.isInit()) {
            WeekRange wkr = new WeekRange();
            DateItem  di = wkr.getRange(Calendar.getInstance());

            //--此处原是放着周是以哪个星期开始   data.setFieldValue("WeekStart", String.valueOf(wkr.getRange()));
            startTime = new java.util.Date(di.getStart().getTimeInMillis());
            endTime   = new java.util.Date(di.getEnd().getTimeInMillis());

            data.setWeekStart(startTime);
            data.setWeekEnd(endTime);
        } else {
            startTime = data.getWeekStart();
            endTime   = data.getWeekEnd();
        }

        String projectID = String.valueOf(data.getProjectID());
        pmFlag = data.isPM();
        log.debug("ProjectID:" + projectID);
        log.debug("Start:" + startTime.toString());
        log.debug("End:" + endTime.toString());
        log.debug("PM Flag:" + pmFlag);

        gStart = comDate.dateToString(startTime, "yyyy-MM-dd");
        gEnd   = comDate.dateToString(endTime, "yyyy-MM-dd");

        ProjectTimeCard projcard = new ProjectTimeCard();
        projcard.setProjID(Integer.parseInt(projectID));

        //将java.sql.Data对象转换为java.util.Calendar对象
        Calendar cStart = Calendar.getInstance();
        cStart.setTimeInMillis(startTime.getTime());

        Calendar cEnd = Calendar.getInstance();
        cEnd.setTimeInMillis(endTime.getTime());

        //////////////////////////////////////////////////////////////
        //   将项目数据初始化                                         //
        //   包括起止时间及是否有旧数据                                //
        /////////////////////////////////////////////////////////////
        projcard.setWeeklyStart(EmpTimeCard.coverToSQLDate(startTime));
        projcard.setWeeklyFinish(EmpTimeCard.coverToSQLDate(endTime));

        //获取是否可以更新记录
        getStatus(String.valueOf(projcard.getProjID()),
                  comDate.dateToString(projcard.getWeeklyStart(),"yyyy-MM-dd"),
                  comDate.dateToString(projcard.getWeeklyFinish(),"yyyy-MM-dd"));

        data.setSubmitStatus(status);
        data.setRecWorkHours(new java.math.BigDecimal(recongized));

        if ( ! isHasData ){
          projcard.setOP(DtoTcTimecard.OP_INSERT);
        }

        return projcard;
    }

    //获取员工列表，及日报内容，并采用EmpTimeCard类，以员工内部代号做为索引
    public ArrayList getEmployess(String projID,
                                  String start,
                                  String end) throws Exception {
      //修改--将日报的范围获取进行修改，取日报记录的年月日与传入的字符串比较，都采用yyyy-MM-dd格式
//        String sql = " select m.T1 , "
//                     + " ( select a.account_code from essp_sys_account_t a where a.id = m.t1) as account_code ,"
//                     + " ( select a.account_name from essp_sys_account_t a where a.id = m.t1 ) as account_name ,"
//                     + " m.t2, "
//                     + " ( select e.emp_code from essp_hr_employee_main_t e where e.code = m.t2 ) as emp_code, "
//                     + " ( select e.name from essp_hr_employee_main_t e where e.code = m.t2 ) as emp_name,"
//                     + " ( select e.position_level from essp_hr_employee_main_t e where e.code = m.t2 ) as position_level ,"
//                     + " ( select c.name_english from essp_hr_code_t c where c.code in ( select e.position_level from essp_hr_employee_main_t e where e.code = m.t2 )) as position_name , "
//                     + " m.t3 , m.t4,m.t5 " + " from ( "
//                     + " select t.project_id as t1 , t.wkitem_owner as t2 , sum(t.wkitem_wkhours) as t3 , min(t.wkitem_date ) as t4, max(t.wkitem_date ) as t5 "
//                     + " from pw_wkitem t where WKITEM_DATE BETWEEN to_date('"
//                     + start + "','yyyy-mm-dd') AND to_date('" + end
//                     + "','yyyy-mm-dd') "
//                     + " GROUP BY PROJECT_ID,WKITEM_OWNER  ) m "
//                     + " WHERE m.t1 = " + projID;

      String sql = " select m.T1 , "
           + " ( select a.account_code from essp_sys_account_t a where a.id = m.t1) as account_code ,"
           + " ( select a.account_name from essp_sys_account_t a where a.id = m.t1 ) as account_name ,"
           + " m.t2, "
           + " ( select e.emp_code from essp_hr_employee_main_t e where e.code = m.t2 ) as emp_code, "
           + " ( select e.name from essp_hr_employee_main_t e where e.code = m.t2 ) as emp_name,"
           + " ( select e.position_level from essp_hr_employee_main_t e where e.code = m.t2 ) as position_level ,"
           + " ( select c.name_english from essp_hr_code_t c where c.code in ( select e.position_level from essp_hr_employee_main_t e where e.code = m.t2 )) as position_name , "
           + " m.t3 , m.t4,m.t5 " + " from ( "
           + " select t.project_id as t1 , t.wkitem_owner as t2 , sum(t.wkitem_wkhours) as t3 , min(t.wkitem_date ) as t4, max(t.wkitem_date ) as t5 "
           + " from pw_wkitem t where ( to_char(WKITEM_DATE ,'yyyy-mm-dd') BETWEEN '" + start + "' AND '" + end + "' )"
           + " AND t.WKITEM_ISDLRPT = '1' "
           + " GROUP BY PROJECT_ID,WKITEM_OWNER  ) m "
           + " WHERE m.t1 = " + projID;


        log.debug("Search emplist " + sql);

        return getEmployessBySQL(sql, start, end);
    }

  /**
   * 依据特定员工获取员工日报资料及差勤资料
   *
   * @param projID String
   * @param start String
   * @param end String
   * @param empID String[]
   * @return ArrayList
   * @throws Exception
   */
  public ArrayList getEmployess(String   projID,
                                  String   start,
                                  String   end,
                                  String[] empID) throws Exception {
        String sql = "";

        if (empID.length != 0) {
           //修改--将日报的范围获取进行修改，取日报记录的年月日与传入的字符串比较，都采用yyyy-MM-dd格式
//            sql = " select " + projID + " as accountid , "
//                  + " 2  as accountcode," + " 3 as accountname ,"
//                  + " e.code , " + " e.emp_code as emp_code, "
//                  + " e.name as emp_name,"
//                  + " e.position_level as position_level ,"
//                  + " ( select c.name_english from essp_hr_code_t c where c.code = e.position_level ) as position_name , "
//                  + " ( select sum(t.wkitem_wkhours)  "
//                  + " from pw_wkitem t where t.project_id = " + projID
//                  + " AND t.wkitem_owner = e.code AND "
//                  + " WKITEM_DATE BETWEEN to_date('" + start
//                  + "','yyyy-mm-dd') AND to_date('" + end + "','yyyy-mm-dd') "
//                  + " GROUP BY PROJECT_ID,WKITEM_OWNER ) as t3 ,"
//                  + " ( select min(t.wkitem_date ) "
//                  + " from pw_wkitem t where t.project_id = " + projID
//                  + " AND t.wkitem_owner = e.code AND "
//                  + " WKITEM_DATE BETWEEN to_date('" + start
//                  + "','yyyy-mm-dd') AND to_date('" + end + "','yyyy-mm-dd') "
//                  + " GROUP BY PROJECT_ID,WKITEM_OWNER ) as t4 ,"
//                  + " ( select max(t.wkitem_date ) "
//                  + " from pw_wkitem t where t.project_id = " + projID
//                  + " AND t.wkitem_owner = e.code AND "
//                  + " WKITEM_DATE BETWEEN to_date('" + start
//                  + "','yyyy-mm-dd') AND to_date('" + end + "','yyyy-mm-dd') "
//                  + " GROUP BY PROJECT_ID,WKITEM_OWNER ) as t5"
//                  + " FROM essp_hr_employee_main_t e  " + " WHERE e.code in ( ";

           sql = " select " + projID + " as accountid , "
      + " 2  as accountcode," + " 3 as accountname ,"
      + " e.code , " + " e.emp_code as emp_code, "
      + " e.name as emp_name,"
      + " e.position_level as position_level ,"
      + " ( select c.name_english from essp_hr_code_t c where c.code = e.position_level ) as position_name , "
      + " ( select sum(t.wkitem_wkhours)  "
      + " from pw_wkitem t where t.project_id = " + projID
      + " AND t.wkitem_owner = e.code AND "
      + " to_char( WKITEM_DATE , 'yyyy-mm-dd') BETWEEN '" + start + "' AND '" + end + "'"
      + " GROUP BY PROJECT_ID,WKITEM_OWNER ) as t3 ,"
      + " ( select min(t.wkitem_date ) "
      + " from pw_wkitem t where t.project_id = " + projID
      + " AND t.wkitem_owner = e.code AND "
      + " to_char( WKITEM_DATE , 'yyyy-mm-dd') BETWEEN '" + start + "' AND '" + end + "'"
      + " GROUP BY PROJECT_ID,WKITEM_OWNER ) as t4 ,"
      + " ( select max(t.wkitem_date ) "
      + " from pw_wkitem t where t.project_id = " + projID
      + " AND t.wkitem_owner = e.code AND "
      + " to_char( WKITEM_DATE , 'yyyy-mm-dd') BETWEEN '" + start + "' AND '" + end + "'"
      + " AND t.WKITEM_ISDLRPT = '1' "
      + " GROUP BY PROJECT_ID,WKITEM_OWNER ) as t5"
      + " FROM essp_hr_employee_main_t e  " + " WHERE e.code in ( ";

            for (int i = 0; i < empID.length; i++) {
                sql = sql + " '" + empID[i] + "' ";

                if (i == (empID.length - 1)) {
                    sql = sql + " ) ";
                } else {
                    sql = sql + " , ";
                }
            }

            System.out.println("Emps SQL :" + sql);

            return getEmployessBySQL(sql, start, end);
        }

        return null;
    }

  /**
   * 通过SQL语句获取员工日报数据
   *
   * @param sql String
   * @param start String
   * @param end String
   * @return ArrayList
   * @throws Exception
   */
  public ArrayList getEmployessBySQL(String sql,
                                       String start,
                                       String end) throws Exception {
        //HBComAccess dba = new HBComAccess();
        ArrayList tmpStr = new ArrayList();
        int       iRet = 0;

        RowSet    rs = hbAccess.executeQuery(sql);

        while (rs.next()) {
            EmpTimeCard etc = new EmpTimeCard();
            etc.setWeeklyStart(comDate.toSQLDate(start,"yyyy-MM-dd"));
            etc.setWeeklyFinish(comDate.toSQLDate(end,"yyyy-MM-dd"));
            etc.setProjID(rs.getInt(1));
            etc.setProjCode(rs.getString(2));
            etc.setProjName(rs.getString(3));
            etc.setEmpID(rs.getString(4));
            etc.setEmpCode(rs.getString(5));
            etc.setEmpName(rs.getString(6));
            etc.setEmpPositionType(rs.getString(8));
            etc.setActualWorkHours(rs.getFloat(9));
            etc.setPersonalWorkHours(rs.getFloat(9));

            etc.setEmpStart(rs.getDate(10));
            etc.setEmpFinish(rs.getDate(11));

            //取员工数据时，如果是新增数据设置其新增
            etc.setOP(DtoTcTimecard.OP_INSERT);

            tmpStr.add(iRet, etc);

            //获取员工差勤数据
            getAttendance(etc);

            //汇总员工差勤数据
            etc.sumAttendance();

            iRet++;
        }

        return tmpStr;
    }

  /**
   * 获取日报数据 日报依据员工代号、项目代号及周期时间获取所有符合条件的记录
   * 以员工代号做为key，下面全用rows记录此员工的工作日报记录
   *
   * @param detail VDData_Node
   * @param iOffert int
   * @return VDData_Node
   * @throws Exception
   */
  private ArrayList getDailyReport(ArrayList detail,
                                     int       iOffert)
                              throws Exception {
        ArrayList vddreport = new ArrayList();
        DbAccess  dba = new DbAccess();

        for (int i = 0; i < (detail.size() - iOffert); i++) {
            ArrayList     drpt = new ArrayList();
            DtoTcTimecard dtt = (DtoTcTimecard) detail.get(i);
//修改--将日报的范围获取进行修改，取日报记录的年月日与传入的字符串比较，都采用yyyy-MM-dd格式
//            String        sql =
//                " select i.project_id,i.wp_id,i.wkitem_owner,i.wkitem_date, "
//                + " i.wkitem_starttime,i.wkitem_finishtime , "
//                + " i.wkitem_wkhours , i.WKITEM_NAME , "
//                + " (select w.wp_code from pw_wp w  where i.wp_id = w.rid) as wp_code, "
//                + " (select wp_name from pw_wp w  where i.wp_id = w.rid) as wp_name ,e.name  "
//                + " from pw_wkitem i, essp_hr_employee_main_t e where e.code = i.wkitem_owner "
//                + " and i.project_id = " + dtt.getTmcProjId()
//                + " and i.wkitem_owner = '" + dtt.getTmcEmpId() + "' "
//                + " and i.wkitem_date Between to_date('"
//                + comDate.dateToString(dtt.getTmcWeeklyStart(),"yyyy-MM-dd")
//                + "','yyyy-mm-dd') and  to_date('"
//                + comDate.dateToString(dtt.getTmcWeeklyFinish(),"yyyy-MM-dd")
//                + "','yyyy-mm-dd') ";

            String sql =
                " select i.project_id,i.wp_id,i.wkitem_owner,i.wkitem_date, "
                + " i.wkitem_starttime,i.wkitem_finishtime , "
                + " i.wkitem_wkhours , i.WKITEM_NAME , "
                + " (select w.wp_code from pw_wp w  where i.wp_id = w.rid) as wp_code, "
                + " (select wp_name from pw_wp w  where i.wp_id = w.rid) as wp_name ,e.name  "
                + " from pw_wkitem i, essp_hr_employee_main_t e where e.code = i.wkitem_owner "
                + " and i.project_id = " + dtt.getTmcProjId()
                + " and i.wkitem_owner = '" + dtt.getTmcEmpId() + "' "
                + " and to_char( i.wkitem_date , 'yyyy-mm-dd') Between '"
                + comDate.dateToString(dtt.getTmcWeeklyStart(), "yyyy-MM-dd")
                + "' and '"
                + comDate.dateToString(dtt.getTmcWeeklyFinish(), "yyyy-MM-dd")
                + "' and i.WKITEM_ISDLRPT = '1' ";


            log.debug("Search Dailyreport " + sql);

            RowSet rs = dba.executeQuery(sql);

            while (rs.next()) {
                DtoPwWkitem dtk = new DtoPwWkitem();
                dtk.setProjectId(new Long(rs.getLong("project_id")));
                dtk.setWpId(new Long(rs.getLong("wp_id")));
                dtk.setWkitemName(rs.getString("WKITEM_NAME"));
                dtk.setWkitemOwner(rs.getString("wkitem_owner"));
                dtk.setWkitemDate(rs.getDate("wkitem_date"));
                dtk.setWkitemStarttime(rs.getDate("wkitem_starttime"));
                dtk.setWkitemFinishtime(rs.getDate("wkitem_finishtime"));
                dtk.setWkitemWkhours(rs.getBigDecimal("wkitem_wkhours"));
                dtk.setWpCode(rs.getString("wp_code"));
                dtk.setWpName(rs.getString("wp_name"));
                dtk.setEmpName(rs.getString("name"));
                drpt.add(dtk);
            }

            rs.close();

            vddreport.add(drpt);
        }

        return vddreport;
    }

    //保存数据，包括汇总数据及明细数据
    public void save(TransactionData data) {
        ArrayList         arr = (ArrayList) data.getInputInfo().getInputObj("DtoTcTimecardList");
        DtoQueryCondition dqc = (DtoQueryCondition) data.getInputInfo()
                                                        .getInputObj("DtoQueryCondition");

        //数据中至少有一汇总数据
        if (arr.size() < 1) {
            data.getReturnInfo().setError(true);
            data.getReturnInfo().setReturnCode("E1001");
            data.getReturnInfo().setReturnMessage("NONE Data to save!");

            return;
        }

        try {

            int iRet = arr.size();

            //VDData_Node vddn = data.getRow(iRet - 1);
            if (dqc.getSubmitStatus().equals(ProjectTimeCard.STATUS_SUBMIT)) {
                data.getReturnInfo().setError(true);
                data.getReturnInfo().setReturnCode("E1002");
                data.getReturnInfo().setReturnMessage("Data Can't be modified!");

                return;
            }

            DtoTcTimecard dtt = (DtoTcTimecard) arr.get(iRet - 1);
            TcTm          ttm = new TcTm();
            TcTmPK        ttp = new TcTmPK();
            ttp.setTmcProjId(dtt.getTmcProjId());
            ttp.setTmcWeeklyStart(dtt.getTmcWeeklyStart());
            ttp.setTmcWeeklyFinish(dtt.getTmcWeeklyFinish());
            ttm.setComp_id(ttp);

            try {
                DtoUtil.copyProperties(ttm, dtt);
            } catch (Exception ex2) {
                ex2.printStackTrace();
            }

            //            //保存汇总数据
            //            if ( dtt.isInsert() ){
            //              hbAccess.save(ttm);
            //            } else if (dtt.isModify()){
            //              hbAccess.update(ttm);
            //            }

            ttm.setTmcProjSubmitStatus(dqc.getSubmitStatus());
            ttm.setTmcRecongnizedWorkhours(dqc.getRecWorkHours());
            if (dtt.isInsert()){
              hbAccess.save(ttm);
            } else {
              hbAccess.update(ttm);
            }
            //保存明细
            for (int i = 0; i < (iRet - 1); i++) {
                DtoTcTimecard dtttmp = (DtoTcTimecard) arr.get(i);

                TcTimecard    ttc  = new TcTimecard();
                TcTimecardPK  ttcp = new TcTimecardPK();

                ttcp.setTmcEmpId(dtttmp.getTmcEmpId());
                ttcp.setTmcProjId(dtttmp.getTmcProjId());
                ttcp.setTmcWeeklyStart(dtttmp.getTmcWeeklyStart());
                ttcp.setTmcWeeklyFinish(dtttmp.getTmcWeeklyFinish());

                ttc.setComp_id(ttcp);

                try {
                    DtoUtil.copyProperties(ttc, dtttmp);
                } catch (Exception ex3) {
                   ex3.printStackTrace();
                }

                if (dtttmp.isDelete()) {
                    hbAccess.delete(ttc);
                } else if (dtttmp.isInsert()) {
                    hbAccess.save(ttc);
                } else if (dtttmp.isModify()) {
                    hbAccess.update(ttc);
                }
            }


        } catch (Exception ex) {
              log.debug("TimeCard Save  error " + ex.toString());
              data.getReturnInfo().setError(ex);

        }
    }

  /**
   * 获取当前数据的锁定状态
   *
   * @param projectID String
   * @param start String
   * @param end String
   */
  public void getStatus(String projectID,
                          String start,
                          String end) {
        DbAccess dba = new DbAccess();

        try {
            String sql =
                " SELECT TMC_PROJ_SUBMIT_STATUS , TMC_RECONGNIZED_WORKHOURS FROM TC_TMS "
                                + " WHERE TMC_PROJ_ID = " + projectID
                                + " AND TMC_WEEKLY_START = to_date('" + start
                                + "','yyyy-mm-dd') "
                                + " AND TMC_WEEKLY_FINISH = to_date('" + end
                                + "','yyyy-mm-dd')";
            RowSet rs = dba.executeQuery(sql);

            while (rs.next()) {
                isHasData   = true;
                status      = rs.getString("TMC_PROJ_SUBMIT_STATUS");
                recongized = rs.getFloat("TMC_RECONGNIZED_WORKHOURS");
            }

            rs.close();
        } catch (Exception ex) {
            log.debug(ex.toString());
        }
    }

  /**
   * 依据员工获取其差勤数据
   *
   * @param eptc EmpTimeCard
   * @throws Exception
   */
  public void getAttendance(EmpTimeCard eptc) throws Exception {
        //        HBComAccess dba = new HBComAccess();
        ArrayList attends = new ArrayList();
        WorkTime  wk = new WorkTime();

//修改--差勤数据将时间比较改成'yyyy-MM-dd'格式比较
//        String    sql = " select * from essp_admin_attendance t "
//                        + " where not ( t.datefrom > to_date('"
//                        + comDate.dateToString(eptc.getWeeklyFinish(),"yyyy-MM-dd")
//                        + " ','yyyy-mm-dd') " + " or t.dateto < to_date('"
//                        + comDate.dateToString(eptc.getWeeklyStart(),"yyyy-MM-dd")
//                        + "','yyyy-mm-dd'))"
//                        + " and upper( t.leavetype ) in (upper('Shift-Adjustment'), "
//                        + " upper('Overtime'),upper('Private Leave'),upper('paid leave'), "
//                        + " upper('Annual-Leave'),upper('Sick Leave')) AND t.userid = '"
//                        + eptc.getEmpID() + "'" + " AND t.account = "
//                        + eptc.getProjID();

        String    sql = " select * from essp_admin_attendance t "
                + " where not ( to_char( t.datefrom, 'yyyy-mm-dd') > '"
                + comDate.dateToString(eptc.getWeeklyFinish(),"yyyy-MM-dd")
                + " ' " + " or to_char( t.dateto, 'yyyy-mm-dd') < '"
                + comDate.dateToString(eptc.getWeeklyStart(),"yyyy-MM-dd")
                + "' )"
                + " and upper( t.leavetype ) in (upper('Shift-Adjustment'), "
                + " upper('Overtime'),upper('Private Leave'),upper('paid leave'), "
                + " upper('Annual-Leave'),upper('Sick Leave')) AND t.userid = '"
                + eptc.getEmpID() + "'" + " AND t.account = "
                + eptc.getProjID();


        log.debug("Search EmpAtten SQL " + sql);

        RowSet rs = hbAccess.executeQuery(sql);

        int    i = 0;

        while (rs.next()) {
            System.out.println("UserID" + rs.getString(1));
            System.out.println("LevelType" + rs.getString(2));

            java.sql.Date              tmpDate = rs.getDate("datefrom");
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(sdf.format(tmpDate, new StringBuffer(0),
                                          new java.text.FieldPosition(0))
                                  .toString());

            java.sql.Timestamp tStart = new java.sql.Timestamp(tmpDate.getTime());
            System.out.println(sdf.format(tStart, new StringBuffer(0),
                                          new java.text.FieldPosition(0))
                                  .toString());

            tmpDate = rs.getDate("dateto");
            System.out.println(sdf.format(tmpDate, new StringBuffer(0),
                                          new java.text.FieldPosition(0))
                                  .toString());

            java.sql.Timestamp tEnd = new java.sql.Timestamp(tmpDate.getTime());
            System.out.println(sdf.format(tEnd, new StringBuffer(0),
                                          new java.text.FieldPosition(0))
                                  .toString());

            Calendar cStart = GregorianCalendar.getInstance();
            cStart.setTimeInMillis(eptc.getWeeklyStart().getTime());

            Calendar cEnd = GregorianCalendar.getInstance();
            cEnd.setTimeInMillis(eptc.getWeeklyFinish().getTime());

            Attendance atten = Attendance.getInstance(tStart, tEnd, cStart, cEnd);
            atten.setAttendType(rs.getString("leavetype"));

            if (rs.getString("leavetype").toLowerCase().equals("overtime")) {
                atten.setWeeklyTime(rs.getFloat("TIME"));
            } else {
                atten.getWeeklyTime(wk.getItemstoStrings());
            }

            attends.add(i, atten);
            i++;
        }

        eptc.setAttendances(attends);
    }

  /**
   * 依据传入的DataNode获取周期内工时表数据
   *
   * @param data VDData_Node
   * @throws Exception
   * @return ArrayList
   */
  public ArrayList getValue(DtoQueryCondition data) throws Exception {
        Session se = hbAccess.getSession();

        //查询明细表，收集单个项目的明细数据
        String sql = "SELECT "
                     + " FROM essp.tables.TcTimecard WHERE TMC_WEEKLY_START = :TMC_WEEKLY_START "
                     + " AND TMC_WEEKLY_FINISH = :TMC_WEEKLY_FINISH "
                     + " AND TMC_PROJ_ID = :TMC_PROJ_ID ";

        Query  qur = se.createQuery(sql);
        qur.setDate("TMC_WEEKLY_START", data.getWeekStart());
        qur.setDate("TMC_WEEKLY_FINISH", data.getWeekEnd());
        qur.setLong("TMC_PROJ_ID", data.getProjectID());

        List      timecard     = qur.list();
        ArrayList activityList = new ArrayList();

        for (int i = 0; i < timecard.size(); i++) {
            DtoTcTimecard dtt = new DtoTcTimecard();

            try {
                DtoUtil.copyProperties(dtt,
                                       ((TcTimecard) timecard.get(i))
                                       .getComp_id());
            } catch (Exception ex) {
               ex.printStackTrace();
            }

            try {
                DtoUtil.copyProperties(dtt, (TcTimecard) timecard.get(i));
            } catch (Exception ex1) {
               ex1.printStackTrace();
            }

            activityList.add(dtt);
        }

        sql = " SELECT t.account_code , t.account_name , m.* FROM TC_TMS m , essp_sys_account_t t "
              + " WHERE t.id = m.TMC_PROJ_ID  "
              + " AND TMC_WEEKLY_START = to_date('"
              + comDate.dateToString(data.getWeekStart(),"yyyy-MM-dd") + "','yyyy-mm-dd')"
              + " AND TMC_WEEKLY_FINISH = to_date('"
              + comDate.dateToString(data.getWeekEnd(),"yyyy-MM-dd") + "','yyyy-mm-dd')"
              + " AND m.TMC_PROJ_ID = " + data.getProjectID();

        //HBComAccess dba = new HBComAccess();
        RowSet        rs  = hbAccess.executeQuery(sql);
        DtoTcTimecard dtt = new DtoTcTimecard();

        //查询汇总表，收集汇总数据
        while (rs.next()) {
            dtt.setTmcProjCode(rs.getString("account_code"));
            dtt.setTmcProjName(rs.getString("account_name"));
            dtt.setTmcWeeklyStart(rs.getDate("TMC_WEEKLY_START"));
            dtt.setTmcWeeklyFinish(rs.getDate("TMC_WEEKLY_FINISH"));
            dtt.setTmcProjId(new Long(rs.getLong("TMC_PROJ_ID")));
            dtt.setTmcPersonalWorkHours(rs.getBigDecimal("TMC_PERSONAL_WORK_HOURS"));
            dtt.setTmcActualWorkHours(rs.getBigDecimal("TMC_ACTUAL_WORK_HOURS"));
            dtt.setTmcAllocatedWorkHours(rs.getBigDecimal("TMC_ALLOCATED_WORK_HOURS"));
            dtt.setTmcAttenOffsetWork(rs.getBigDecimal("TMC_ATTEN_OFFSET_WORK"));
            dtt.setTmcAttenOvertime(rs.getBigDecimal("TMC_ATTEN_OVERTIME"));
            dtt.setTmcAttenVacation(rs.getBigDecimal("TMC_ATTEN_VACATION"));
            dtt.setTmcAttenShiftAdjustment(rs.getBigDecimal("TMC_ATTEN_SHIFT_ADJUSTMENT"));
            dtt.setTmcAttenPrivateLeave(rs.getBigDecimal("TMC_ATTEN_PRIVATE_LEAVE"));
            dtt.setTmcAttenSickLeave(rs.getBigDecimal("TMC_ATTEN_SICK_LEAVE"));
            dtt.setTmcAttenAbsence(rs.getBigDecimal("TMC_ATTEN_ABSENCE"));
            dtt.setTmcAttenBreakingRules(rs.getBigDecimal("TMC_ATTEN_BREAKING_RULES"));
            activityList.add(dtt);
        }

        rs.close();

        return (ArrayList) activityList;
    }

    public ArrayList getEmpTimeCards() {
        return empTimeCards;
    }

    public ArrayList getDailyReports() {
        return dailyReports;
    }

    public void setEmpTimeCards(ArrayList empTimeCards) {
        this.empTimeCards = empTimeCards;
    }

    public void setDailyReports(ArrayList dailyReports) {
        this.dailyReports = dailyReports;
    }

  public void executeLogic(Parameter param) throws BusinessException {
      TransactionData data = (TransactionData) param.get("data");
      hbAccess = getDbAccessor();

      String sAction = data.getInputInfo().getFunId();

      if (sAction.equals("get")) {
          log.debug("TimeCard Servlet:GET ");
          getValue(data);
      } else if (sAction.equals("save")) {
          save(data);
      } else if (sAction.equals("add")) {
          getSepacialEmpValue(data);
      }

  }

}
