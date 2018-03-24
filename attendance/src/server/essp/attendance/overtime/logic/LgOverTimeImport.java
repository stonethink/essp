package server.essp.attendance.overtime.logic;

import java.util.*;

import c2s.essp.attendance.Constant;
import c2s.essp.common.account.*;
import db.essp.attendance.*;
import itf.account.*;
import org.apache.poi.hssf.usermodel.*;
import server.essp.attendance.overtime.viewbean.*;
import server.essp.common.excelUtil.*;
import server.essp.framework.logic.*;
import server.framework.common.*;
import itf.hr.HrFactory;
import com.wits.excel.importer.SheetImporter;
import c2s.essp.common.user.DtoUser;
import com.wits.util.StringUtil;

public class LgOverTimeImport extends AbstractESSPLogic {
    public final static String COMMENTS = "Import by HR";
    public final static int START_ROW = 2;

    /**
     * 加班导入
     * @param file FormFile
     * @return AfOverTimeImportInfo
     */
    public VbOverTimeImportInfo importExcel(HSSFWorkbook wb) {
        VbOverTimeImportInfo reInfo = new VbOverTimeImportInfo();
        HSSFSheet sheet = wb.getSheetAt(0);

        String[][] config = {{"acntId", SheetImporter.CELL_TYPE_STRING},
                            {"loginId", SheetImporter.CELL_TYPE_STRING},
                            {"name", SheetImporter.CELL_TYPE_STRING},
                            {"beginDate", SheetImporter.CELL_TYPE_DATE},
                            {"endDate", SheetImporter.CELL_TYPE_DATE},
                            {"hours", SheetImporter.CELL_TYPE_NUMBER},
                            {"desc", SheetImporter.CELL_TYPE_STRING}};

        SheetImporter res = new SheetImporter(sheet, VbOverTimeSheet.class, config, START_ROW);
        List lst = res.getDataList();

        int lstRow = lst.size();
        int saveRow = 0;
        double totalHours = 0;
        for(int i = 0; i < lstRow; i++) {
            VbOverTimeSheet data = (VbOverTimeSheet) lst.get(i);
            TcOvertime overTime = exchangeBean(data, i);
            totalHours += overTime.getActualTotalHours().doubleValue();
            saveOverTime(overTime);
            saveRow++;
        }
        reInfo.setTotalRows(new Long(lstRow));
        reInfo.setImportedRows(new Long(saveRow));
        reInfo.setTotalHours(new Double(totalHours));
        reInfo.setRemark("successful");
        return reInfo;
    }

    /**
     * 读取每一行数据
     * @param row HSSFRow
     * @return TcOvertime
     */
    private TcOvertime exchangeBean(VbOverTimeSheet data, int row) {
        TcOvertime overTime = new Tc();


        //get values
        String acntId = StringUtil.nvl(data.getAcntId());
        String loginId = StringUtil.nvl(data.getLoginId());
        String name = data.getName();
        Date beginDate = data.getBeginDate();
        Date endDate = data.getEndDate();
        Double hours = data.getHours();
        String desc = data.getDesc();
        //loginId为空异常
        if (loginId == null || "".equals(loginId)) {
            String show = "row: " + (row + START_ROW + 1) + " loginId is required";
            throw new BusinessException("IMP0001", show);
        }
        //检查loginId是否存在
        DtoUser user = HrFactory.create().findResouce(loginId);
        if (user == null) {
            String show = "row: " + (row + START_ROW + 1) + " " + loginId + " doesn't exist in system";
            throw new BusinessException("IMP0006", show);
        } else {
            loginId = user.getUserLoginId();
        }


        //时间为空异常
        if(beginDate == null || endDate == null) {
            String show = "row: " + (row + START_ROW + 1) + " " + name + "("+loginId+") 's over time invalid between "
                          + beginDate + " and " + endDate;
            throw new BusinessException("IMP0002", show);
        }

        //加班时间必须大于0
        if(hours <= 0) {
            String show = "row: " + (row + START_ROW + 1) + " " + name + "("+loginId+") 's over time hours must be positive";
            throw new BusinessException("IMP0003", show);
        }


        //加班区间冲突异常
        if (isPeriodJoin(loginId, beginDate, endDate)) {
            String show = "row: " + (row + START_ROW + 1) + " " + name + "("+loginId+") had overtime between "
                          + beginDate + " and " + endDate;
            throw new BusinessException("IMP0004", show);
        }

        //用Account Id 获取 Account Rid
        Long acntRid = null;
        try {
            IAccountUtil lgAcnt = AccountFactory.create();
            acntRid = lgAcnt.getAccountByCode(acntId).getRid();
        } catch(Exception e) {
            throw new BusinessException("IMP0005", "row: " + (row + START_ROW + 1) + " " +"get account "+acntId+" error", e);

        overTime.setAcntRid(acntRid);
        overTime.setLoginId(loginId);
        overTime.setDatetimeStart(beginDate);
        overTime.setDatetimeFinish(endDate);
        overTime.setTotalHours(hours);
        overTime.setActualDatetimeStart(beginDate);
        overTime.setActualDatetimeFinish(endDate);
        overTime.setActualTotalHours(hours);
        overTime.setCause(desc);
        overTime.setActualIsEachDay(Boolean.FALSE);
        overTime.setStatus(Constant.STATUS_FINISHED);
        overTime.setComments(COMMENTS);
        return overTime;
    }

    /**
     * 保存加班数据, 包括以下记录：
     * 1. TcOvertime
     * 2. TcOvertimeDetail
     * 3. TcOvertimeLog
     * @param overTime TcOvertime
     */
    private void saveOverTime(TcOvertime overTime) {
        this.getDbAccessor().save(overTime);
        Date begin = overTime.getActualDatetimeStart();
        Date end = overTime.getActualDatetimeFinish();
        Double hours = overTime.getActualTotalHours();

        TcOvertimeDetail detail = new TcOvertimeDetail();
        detail.setOvertimeDay(begin);
        detail.setShiftHours(new Double(0));
        detail.setPayedHours(new Double(0));
        detail.setHours(hours);
        detail.setTcOvertime(overTime);
        this.getDbAccessor().save(detail);

        TcOvertimeLog overTimeLog = new TcOvertimeLog();
        overTimeLog.setLoginId(this.getUser().getUserLoginId());
        overTimeLog.setTcOvertime(overTime);
        overTimeLog.setDecision(COMMENTS);
        overTimeLog.setDatetimeStart(begin);
        overTimeLog.setDatetimeFinish(end);
        overTimeLog.setTotalHours(hours);
        overTimeLog.setIsEachDay(Boolean.FALSE);
        overTimeLog.setLogDate(new Date());
        this.getDbAccessor().save(overTimeLog);
    }

    /**
     * 判断某员工在此区间内是否已有加班存在
     * @param loginId String
     * @param begin Date
     * @param end Date
     * @return boolean
     */
    private boolean isPeriodJoin(String loginId, Date begin, Date end) {
        String hql = "from TcOvertime t where t.loginId =:loginId "
                     + "and t.actualDatetimeStart <= :endDate "
                     + "and t.actualDatetimeFinish >= :beginDate "
                     + "and t.status = :status";
        List list = null;
        try {
            list = this.getDbAccessor().getSession().createQuery(hql)
                   .setString("loginId", loginId)
                   .setParameter("beginDate", begin)
                   .setParameter("endDate", end)
                   .setParameter("status", Constant.STATUS_FINISHED)
                   .list();
        } catch (Exception ex) {
            throw new BusinessException("IMP0006", "check over time period overlap error", ex);
        }
        if(list!=null&&list.size()>0){
            return true;
        }else{
            return false;
        }
    }
}
