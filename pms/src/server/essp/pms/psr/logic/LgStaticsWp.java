package server.essp.pms.psr.logic;

import server.essp.framework.logic.AbstractESSPLogic;
import java.sql.ResultSet;
import java.util.Date;
import com.wits.util.comDate;
import java.sql.*;

/**
 * <p>Title: 此类用于取得WP的统计数据</p>
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
public class LgStaticsWp extends AbstractESSPLogic {

    public Long getCurrentPlanCount(Long acntRid, Date start, Date finish) {
        String sql = "select count(*) from pw_wp t where t.project_id="
                     + acntRid.toString() +
                     " and to_char(t.wp_plan_start,'yyyy-mm-dd')>='" +
                     comDate.dateToString(start) + "' "
                     + "and to_char(t.wp_plan_fihish,'yyyy-mm-dd')<='" +
                     comDate.dateToString(finish) + "'";
        System.out.println(sql);
        try {
            ResultSet rs = this.getDbAccessor().executeQuery(sql);
            while (rs.next()) {
                return new Long(rs.getLong(1));
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Long getCurrentActualCount(Long acntRid, Date start, Date finish) {
        String sql = "select count(*) from pw_wp t where t.project_id="
                     + acntRid.toString() +
                     " and to_char(t.wp_act_start,'yyyy-mm-dd')>='" +
                     comDate.dateToString(start) + "' "
                     + "and to_char(t.wp_act_finish,'yyyy-mm-dd')<='" +
                     comDate.dateToString(finish) + "'";
        System.out.println(sql);
        try {
            ResultSet rs = this.getDbAccessor().executeQuery(sql);
            while (rs.next()) {
                return new Long(rs.getLong(1));
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Long getTotalPlanCount(Long acntRid, Date finish) {
        String sql = "select count(*) from pw_wp t where t.project_id="
                     + acntRid.toString()
                     + " and to_char(t.wp_plan_fihish,'yyyy-mm-dd')<='" +
                     comDate.dateToString(finish) + "'";
        System.out.println(sql);
        try {
            ResultSet rs = this.getDbAccessor().executeQuery(sql);
            while (rs.next()) {
                return new Long(rs.getLong(1));
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Long getTotalActualCount(Long acntRid, Date finish) {
        String sql = "select count(*) from pw_wp t where t.project_id="
                     + acntRid.toString()
                     + " and to_char(t.wp_act_finish,'yyyy-mm-dd')<='" +
                     comDate.dateToString(finish) + "'";
        System.out.println(sql);
        try {
            ResultSet rs = this.getDbAccessor().executeQuery(sql);
            while (rs.next()) {
                return new Long(rs.getLong(1));
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Long getTotalWpCount(Long acntRid) {
        String sql = "select count(*) from pw_wp t where t.project_id="
                     + acntRid.toString();
        System.out.println(sql);
        try {
            ResultSet rs = this.getDbAccessor().executeQuery(sql);
            while (rs.next()) {
                return new Long(rs.getLong(1));
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        LgStaticsWp lgwpstatics = new LgStaticsWp();

    }
}
