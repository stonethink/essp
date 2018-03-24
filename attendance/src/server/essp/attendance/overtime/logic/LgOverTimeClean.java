package server.essp.attendance.overtime.logic;

import java.util.*;

import c2s.dto.*;
import c2s.essp.attendance.Constant;
import com.wits.excel.importer.*;
import db.essp.attendance.*;
import net.sf.hibernate.*;
import org.apache.poi.hssf.usermodel.*;
import server.essp.attendance.overtime.viewbean.*;
import server.essp.framework.logic.*;
import server.framework.common.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class LgOverTimeClean extends AbstractESSPLogic {
    public final static int START_ROW = 2;

    /**
     * 加班清除导入数据确认
     *   查询每个人的可调休时间与导入的清除时间相比较
     * @param file FormFile
     * @return AfOverTimeImportInfo
     */
    public List importCleanExcelConfirm(HSSFWorkbook wb) {
        HSSFSheet sheet = wb.getSheetAt(0);

        String[][] config = {{"loginId", SheetImporter.CELL_TYPE_STRING},
                            {"name", SheetImporter.CELL_TYPE_STRING},
                            {"cleanHours", SheetImporter.CELL_TYPE_NUMBER}};

        SheetImporter res = new SheetImporter(sheet, VbOverTimeClean.class, config, START_ROW);
        List lst = res.getDataList();

        int lstRow = lst.size();
        for(int i = 0; i < lstRow; i++) {
            VbOverTimeClean data = (VbOverTimeClean) lst.get(i);

            //format scale
            Double cleanHours = data.getCleanHours();
            if(cleanHours == null) {
                cleanHours = new Double(0);
            }
            BigDecimal big = new BigDecimal(cleanHours);
            cleanHours = big.setScale(2, RoundingMode.HALF_UP).doubleValue();
            data.setCleanHours(cleanHours);

            fillData(data, i);
        }
      return lst;
    }

    /**
     * 填入每个人的可用时间，部门和项目信息
     * @param data VbOverTimeClean
     * @param row int
     */
    private void fillData(VbOverTimeClean data, int row) {
        String loginId = data.getLoginId();
        if(loginId == null || "".equals(loginId)) {
            throw new BusinessException("CO0004", "row: " + (row + START_ROW + 1) + " loginId is required");
        }

        String sql = "select l.login_id, hr.chinese_name as name, u.unit_code as org_id, u.name as org_name, sum(d.hours - d.payed_hours - d.shift_hours) as usable_hours "
                    + "from  upms_loginuser l "
                    + "left join essp_hr_employee_main_t hr on hr.code = l.user_id "
                    + "left join essp_upms_unit u on u.unit_id = hr.dept "
                    + "left join tc_overtime o on o.login_id = l.login_id  "
                    + "and o.status='" + Constant.STATUS_FINISHED + "' "
                    + "left join tc_overtime_detail d on d.overtime_id = o.rid "
                    + "where l.login_id = '" + loginId + "' "
                    + "group by l.login_id, hr.chinese_name, u.unit_code, u.name ";
        List list = getDbAccessor().executeQueryToList(sql, VbOverTimeClean.class);
        if(list == null || list.size() == 0) {
            throw new BusinessException("CO0002", "row: " + (row + START_ROW + 1) + " No such user (" + loginId + "/" + data.getName() + ") in system");
        }
        VbOverTimeClean  vb = (VbOverTimeClean)list.get(0);
        try {
            String[] except = {"cleanHours"};
            DtoUtil.copyProperties(data, vb, except);
        } catch (Exception ex) {
            throw new BusinessException("CO0003", "copy VbOverTimeClean is error", ex);
        }
    }

    /**
     * 执行清除动作
     *   将清除时间分摊到TcOvertimeDetail的payedHours
     * @param list List
     * @return List
     */
    public List executeClean(List list){
         for(int i = 0; i < list.size(); i++) {
             VbOverTimeClean vb = (VbOverTimeClean) list.get(i);
             Double cleanHours = vb.getCleanHours();
             if(cleanHours == null || cleanHours == 0) {
                 continue;
             }
             String loginId = vb.getLoginId();
             double surplusHours = subOverTime(loginId, cleanHours);
             if(surplusHours > 0) {
                 String message = loginId + "(" + vb.getName() +
                                  ") usableHours is not enough";
                 throw new BusinessException("CO0001", message);
             }
             vb.setUsableHours(vb.getUsableHours() - vb.getCleanHours() - surplusHours);
             vb.setCleanHours(surplusHours);
         }
         return list;
    }

    /**
     * subtract over time
     * @param loginId String
     * @param subHours double
     * @param processType String
     * @return double
     */
    private double subOverTime(String loginId, double subHours) {
        List l = getOverTime(loginId);
        if (l == null || l.size() <= 0)
            return subHours;
        for (int i = 0; i < l.size(); i++) {
            TcOvertimeDetail detail = (TcOvertimeDetail) l.get(i);
            double usable = detail.getUsableHours().doubleValue();
            if (usable > 0) {
                if (usable >= subHours) {
                    detail.addPayedHours(subHours);
                    subHours = 0;
                    break;
                } else {
                    subHours -= usable;
                    detail.addPayedHours(usable);
                    continue;
                }
            }
        }
        return subHours;
    }

    /**
     * get over time list
     * @param loginId String
     * @return List
     */
    private List getOverTime(String loginId) {
        List overList = null;
        String hql = "from TcOvertimeDetail ot " +
                      "where ot.tcOvertime.loginId=:loginId " +
                      "and ot.tcOvertime.status =:status " +
                      "and ot.hours - ot.shiftHours - ot.payedHours > 0 " +
                      "order by ot.overtimeDay";
        try {
            Session s = this.getDbAccessor().getSession();
            overList = s.createQuery(hql)
                     .setParameter("loginId",loginId)
                     .setParameter("status",Constant.STATUS_FINISHED)
                     .list();
        } catch (Exception ex) {
           throw new BusinessException("CO00000", "List over time error.", ex);
        }
        return overList;
    }
}
