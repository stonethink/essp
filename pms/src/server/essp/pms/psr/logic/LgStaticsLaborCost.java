package server.essp.pms.psr.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import com.wits.util.comDate;

/**
 * <p>Title: 此类用于统计Labor Cost </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LgStaticsLaborCost extends AbstractESSPLogic {
    public Double getTotalCost(Long acntRid) {
        String sql =
            "select sum(t.total_hours) from pms_status_report_laborcost t where t.acnt_rid="
            + acntRid.toString();
        try {
            ResultSet rs = this.getDbAccessor().executeQuery(sql);
            while (rs.next()) {
                return new Double(rs.getDouble(1));
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Double getCostPerWeek(Long acntRid, Date start, Date finish) {
        String sql =
            "select sum(t.total_hours) from pms_status_report_laborcost t "
            + "where t.acnt_rid=" + acntRid.toString() + " and "
            + "to_char(t.begin_period,'yyyy-MM-dd')>='"
            + comDate.dateToString(start) + "' "
            + " and to_char(t.begin_period,'yyyy-MM-dd')<='"
            + comDate.dateToString(finish) + "'";
        try {
            ResultSet rs = this.getDbAccessor().executeQuery(sql);
            while (rs.next()) {
                return new Double(rs.getDouble(1));
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        LgStaticsLaborCost lgstaticslaborcost = new LgStaticsLaborCost();
    }
}
