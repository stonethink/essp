package server.essp.timesheet.account.dao;

import javax.sql.RowSet;

import server.essp.framework.logic.AbstractESSPLogic;

public class LgAccountPrivilege extends AbstractESSPLogic {
	
	/**
	 * 获取用户(loginId)对那些执行单位下专案具有查询权限
	 * 		依赖ProjectPre中的pp_acnt_query_privilege和pp_acnt两张表
	 * @param loginId
	 * @return privilegeOu String
	 */
	public String getPrivilegeUnit(String loginId) {
		String privilegeOu = null;
		String sql = "select a.acnt_id "
						+ "from pp_acnt_query_privilege p "
						+ "left join pp_acnt a on p.acnt_rid = a.rid "
						+ "where p.org_privilege='1' and upper(p.login_id) = upper('"
						+ loginId + "') order by a.acnt_id";
		try {
			RowSet rs = this.getDbAccessor().executeQuery(sql);
			while(rs.next()) {
				if(privilegeOu == null) {
					privilegeOu = rs.getString(1);
				} else {
					privilegeOu += "," + rs.getString(1);
				}
			}
		} catch (Exception e) {
			log.warn("Timesheet Account LgAccountPrivilege query error", e);
		}
		return privilegeOu;
	}

}
