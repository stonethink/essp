package server.essp.timecard.timecard.logic;

import c2s.dto.*;
import c2s.essp.timecard.timecard.*;

import com.wits.util.*;
import org.apache.log4j.*;
import server.essp.common.timecard.*;
import java.util.*;
import javax.sql.*;
import server.framework.logic.AbstractBusinessLogic;
import server.framework.common.BusinessException;
import server.framework.hibernate.HBComAccess;

/**
 *
 * <p>Title: HR获取周期内员工工时数据</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author BoXiao
 * @version 1.0
 */
public class FTC01020GeneralLogic extends AbstractBusinessLogic {
    /**
     * 工时表Data_Node
     */

    //private VDData_Node WorkTime;
    static Category      log                   = Category.getInstance("server");
    public static final String INIT_FLAG             = "1";
    public String        gStart;
    public String        gEnd;
    public static final String        NODE_NAME_OF_PROJECT  = "PROJECT";
    public static final String        NODE_NAME_OF_EMPLOYEE = "EMPLOYEE";

    public FTC01020GeneralLogic(){
    }

    public void getWorkTime(TransactionData data) {
        DtoQueryCondition dqc = (DtoQueryCondition) data.getInputInfo()
                                                        .getInputObj("DtoQueryCondition");

        //VDData_Node data = (VDData_Node) vddn.getInput_Obj(NODE_NAME_OF_PROJECT);
        init(dqc);

        try {
            ArrayList arr = getValue(data);
            data.getReturnInfo().setReturnObj("DtoTcTmList", arr);
            data.getReturnInfo().setReturnObj("DtoQueryCondition", dqc);
        } catch (Exception ex) {
            data.getReturnInfo().setError(ex);
        }
    }

  /**
   * 依据传入的DataNode获取周期内工时表数据
   *
   * @param data VDData_Node
   * @throws Exception
   * @return ArrayList
   */
  public ArrayList getValue(TransactionData data) throws Exception {
        //DbAccess  dba = new DbAccess();
        HBComAccess hbAccess = this.getDbAccessor();

        ArrayList arr = new ArrayList();

        String    sql =
            " SELECT t.account_code , t.account_name , m.* FROM TC_TMS m , "
            + " essp_sys_account_t t WHERE t.id = m.TMC_PROJ_ID "
            + " AND  TMC_WEEKLY_START = to_date('" + gStart
            + "','yyyy-mm-dd') " + " AND  TMC_WEEKLY_FINISH = to_date('" + gEnd
            + "','yyyy-mm-dd') ";

        //查询汇总表，收集汇总数据
        RowSet rs = hbAccess.executeQuery(sql);

        while (rs.next()) {
            DtoTcTm dtm = new DtoTcTm();
            dtm.setAccountCode(rs.getString("account_code"));
            dtm.setAccountName(rs.getString("account_name"));
            dtm.setTmcWeeklyFinish(rs.getDate("TMC_WEEKLY_FINISH"));
            dtm.setTmcWeeklyStart(rs.getDate("TMC_WEEKLY_START"));
            dtm.setTmcProjId(new Long(rs.getLong("TMC_PROJ_ID")));
            dtm.setTmcProjSubmitStatus(rs.getString("TMC_PROJ_SUBMIT_STATUS"));
            dtm.setTmcActualWorkHours(rs.getBigDecimal("TMC_ACTUAL_WORK_HOURS"));
            dtm.setTmcAllocatedWorkHours(rs.getBigDecimal("TMC_ALLOCATED_WORK_HOURS"));
            dtm.setTmcPersonalWorkHours(rs.getBigDecimal("TMC_PERSONAL_WORK_HOURS"));
            dtm.setTmcAttenOffsetWork(rs.getBigDecimal("TMC_ATTEN_OFFSET_WORK"));
            dtm.setTmcAttenOvertime(rs.getBigDecimal("TMC_ATTEN_OVERTIME"));
            dtm.setTmcAttenVacation(rs.getBigDecimal("TMC_ATTEN_VACATION"));
            dtm.setTmcAttenShiftAdjustment(rs.getBigDecimal("TMC_ATTEN_SHIFT_ADJUSTMENT"));
            dtm.setTmcAttenPrivateLeave(rs.getBigDecimal("TMC_ATTEN_PRIVATE_LEAVE"));
            dtm.setTmcAttenSickLeave(rs.getBigDecimal("TMC_ATTEN_SICK_LEAVE"));
            dtm.setTmcAttenAbsence(rs.getBigDecimal("TMC_ATTEN_ABSENCE"));
            dtm.setTmcAttenBreakingRules(rs.getBigDecimal("TMC_ATTEN_BREAKING_RULES"));
            dtm.setTmcRecongnizedWorkhours(rs.getBigDecimal("TMC_RECONGNIZED_WORKHOURS"));
            arr.add(dtm);

            sql = " SELECT * FROM TC_TIMECARD "
                  + " WHERE TMC_WEEKLY_START = to_date('" + gStart
                  + "','yyyy-mm-dd') " + " AND TMC_WEEKLY_FINISH = to_date('"
                  + gEnd + "','yyyy-mm-dd') " + " AND TMC_PROJ_ID = "
                  + dtm.getTmcProjId();

            //查询明细表，收集单个项目的明细数据
            RowSet    rs2    = hbAccess.executeQuery(sql);
            ArrayList tmpArr = new ArrayList();

            while (rs2.next()) {
                DtoTcTimecard dtt = new DtoTcTimecard();
                dtt.setTmcWeeklyStart(rs2.getDate("TMC_WEEKLY_START"));
                dtt.setTmcWeeklyFinish(rs2.getDate("TMC_WEEKLY_FINISH"));
                dtt.setTmcProjId(new Long(rs2.getLong("TMC_PROJ_ID")));
                dtt.setTmcProjCode(rs2.getString("TMC_PROJ_CODE"));
                dtt.setTmcProjName(rs2.getString("TMC_PROJ_NAME"));
                dtt.setTmcEmpId(rs2.getString("TMC_EMP_ID"));
                dtt.setTmcEmpCode(rs2.getString("TMC_EMP_CODE"));
                dtt.setTmcEmpName(rs2.getString("TMC_EMP_NAME"));
                dtt.setTmcEmpPositionType(rs2.getString("TMC_EMP_POSITION_TYPE"));
                dtt.setTmcEmpStart(rs2.getDate("TMC_EMP_START"));
                dtt.setTmcEmpFinish(rs2.getDate("TMC_EMP_FINISH"));
                dtt.setTmcPersonalWorkHours(rs2.getBigDecimal("TMC_PERSONAL_WORK_HOURS"));
                dtt.setTmcActualWorkHours(rs2.getBigDecimal("TMC_ACTUAL_WORK_HOURS"));
                dtt.setTmcAllocatedWorkHours(rs2.getBigDecimal("TMC_ALLOCATED_WORK_HOURS"));
                dtt.setTmcAttenOffsetWork(rs2.getBigDecimal("TMC_ATTEN_OFFSET_WORK"));
                dtt.setTmcAttenOvertime(rs2.getBigDecimal("TMC_ATTEN_OVERTIME"));
                dtt.setTmcAttenVacation(rs2.getBigDecimal("TMC_ATTEN_VACATION"));
                dtt.setTmcAttenShiftAdjustment(rs2.getBigDecimal("TMC_ATTEN_SHIFT_ADJUSTMENT"));
                dtt.setTmcAttenPrivateLeave(rs2.getBigDecimal("TMC_ATTEN_PRIVATE_LEAVE"));
                dtt.setTmcAttenSickLeave(rs2.getBigDecimal("TMC_ATTEN_SICK_LEAVE"));
                dtt.setTmcAttenAbsence(rs2.getBigDecimal("TMC_ATTEN_ABSENCE"));
                dtt.setTmcAttenBreakingRules(rs2.getBigDecimal("TMC_ATTEN_BREAKING_RULES"));
                tmpArr.add(dtt);
            }

            dtm.setDtoTcTimeCardList(tmpArr);
        }

        rs.close();

        return arr;
    }

  /**
   * 获取当前数据的锁定状态
   *
   * @param data String
   */
  public void getStatus(DtoQueryCondition data) {
        HBComAccess hbAccess = this.getDbAccessor();
        String   sql = "SELECT TMC_PROJ_SUBMIT_STATUS FROM TC_TMS "
                       + " WHERE TMC_WEEKLY_START = to_date('" + gStart
                       + "','yyyy-mm-dd') "
                       + " AND TMC_WEEKLY_FINISH = to_date('" + gEnd
                       + "','yyyy-mm-dd') ";

        try {
            RowSet rs = hbAccess.executeQuery(sql);

            while (rs.next()) {
                data.setSubmitStatus(rs.getString("TMC_PROJ_SUBMIT_STATUS"));

                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void save(TransactionData data) {
         HBComAccess hbAccess = this.getDbAccessor();
        DtoQueryCondition dqc    = (DtoQueryCondition) data.getInputInfo()
                                                           .getInputObj("DtoQueryCondition");
        String            sStart = comDate.dateToString(dqc.getWeekStart(),"yyyy-MM-dd");
        String            sEnd   = comDate.dateToString(dqc.getWeekEnd(),"yyyy-MM-dd");

        try {
            hbAccess.executeUpdate(" UPDATE TC_TMS SET TMC_PROJ_SUBMIT_STATUS = '"
                              + dqc.getSubmitStatus() + "'"
                              + " WHERE TMC_WEEKLY_START = to_date('" + sStart
                              + "', 'yyyy-mm-dd') "
                              + " AND TMC_WEEKLY_FINISH = to_date('" + sEnd
                              + "', 'yyyy-mm-dd') ");
        } catch (Exception ex) {
            log.debug(ex.toString());
            data.getReturnInfo().setError(ex);
        }
    }

    public void getEmpTime(TransactionData data) {
        DtoQueryCondition dqc = (DtoQueryCondition) data.getInputInfo()
                                                        .getInputObj("DtoQueryCondition");
        init(dqc);

        String sql =
            " select tmc_emp_id , tmc_emp_code, tmc_emp_name , min(t.tmc_emp_start) as TMC_EMP_START , "
                        + " max(t.tmc_emp_finish) as TMC_EMP_FINISH , "
                        + " sum(TMC_PERSONAL_WORK_HOURS) as TMC_PERSONAL_WORK_HOURS,"
                        + " sum(TMC_ACTUAL_WORK_HOURS) as TMC_ACTUAL_WORK_HOURS, "
                        + " sum(TMC_ALLOCATED_WORK_HOURS) as TMC_ALLOCATED_WORK_HOURS, "
                        + " sum(TMC_ATTEN_OFFSET_WORK) as TMC_ATTEN_OFFSET_WORK, "
                        + " sum(TMC_ATTEN_OVERTIME) as TMC_ATTEN_OVERTIME,"
                        + " sum(TMC_ATTEN_VACATION) as TMC_ATTEN_VACATION,"
                        + " sum(TMC_ATTEN_SHIFT_ADJUSTMENT) as TMC_ATTEN_SHIFT_ADJUSTMENT,"
                        + " sum(TMC_ATTEN_PRIVATE_LEAVE) as TMC_ATTEN_PRIVATE_LEAVE,"
                        + " sum(TMC_ATTEN_SICK_LEAVE) as TMC_ATTEN_SICK_LEAVE,"
                        + " sum(TMC_ATTEN_ABSENCE) as TMC_ATTEN_ABSENCE,"
                        + " sum(TMC_ATTEN_BREAKING_RULES) as TMC_ATTEN_BREAKING_RULES"
                        + " from tc_timecard t"
                        + " WHERE tmc_weekly_start = to_date('" + gStart
                        + "','yyyy-mm-dd')"
                        + " AND TMC_WEEKLY_FINISH = to_date('" + gEnd
                        + "','yyyy-mm-dd')"
                        + " Group by t.tmc_emp_id,t.tmc_emp_code,t.tmc_emp_name";

        HBComAccess hbAccess = this.getDbAccessor();

        try {
            RowSet    rs     = hbAccess.executeQuery(sql);
            ArrayList tmpArr = new ArrayList();

            while (rs.next()) {
                DtoTcTimecard dtt = new DtoTcTimecard();
                dtt.setTmcEmpId(rs.getString("tmc_emp_id"));
                dtt.setTmcEmpCode(rs.getString("tmc_emp_code"));
                dtt.setTmcEmpName(rs.getString("tmc_emp_name"));
                dtt.setTmcEmpStart(rs.getDate("TMC_EMP_START"));
                dtt.setTmcEmpFinish(rs.getDate("TMC_EMP_FINISH"));
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
                tmpArr.add(dtt);
            }

            rs.close();

            data.getReturnInfo().setReturnObj("DtoTcTimecardList", tmpArr);
            data.getReturnInfo().setReturnObj("DtoQueryCondition", dqc);
        } catch (Exception ex) {
            log.debug(ex.toString());
            data.getReturnInfo().setError(ex);
        }
    }

    //初始化项目当前周期数据
    public void init(DtoQueryCondition data) {
        java.util.Date startTime = null;
        java.util.Date endTime = null;

        if (data.isInit()) {
            WeekRange wkr = new WeekRange();
            DateItem  di = wkr.getRange(Calendar.getInstance());

            //--此处原是放着周是以哪个星期开始  data.setFieldValue("WeekStart", String.valueOf(wkr.getRange()));
            startTime = new java.util.Date(di.getStart().getTimeInMillis());
            endTime   = new java.util.Date(di.getEnd().getTimeInMillis());

            data.setWeekStart(startTime);
            data.setWeekEnd(endTime);
        } else {
            startTime = data.getWeekStart();
            endTime   = data.getWeekEnd();
        }

        gStart = comDate.dateToString(startTime, "yyyy-MM-dd");
        gEnd   = comDate.dateToString(endTime, "yyyy-MM-dd");

        getStatus(data);
    }

  public void executeLogic(Parameter param) throws BusinessException {
        TransactionData data = (TransactionData)param.get("data");
        String sAction = data.getInputInfo().getFunId();

        if (sAction.equals("getProject")) {
          getWorkTime(data);
        } else if (sAction.equals("save")) {
          save(data);
        } else if (sAction.equals("getEmployee")) {
          getEmpTime(data);
        }

  }
}
