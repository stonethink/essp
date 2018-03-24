package server.essp.hrbase.site.logic;

import java.util.List;

import db.essp.hrbase.HrbSite;

import server.essp.framework.logic.AbstractESSPLogic;

public class LgSiteSelection extends AbstractESSPLogic {
	
	public List listEnableSites(String loginId) {
		String sql = "select t.name,t.description "
						+ "from hrb_site t "
						+ "left join hrb_site_privilege p on t.name = p.site_name "
						+ "where t.is_enable = '1' and p.login_id='" + loginId + "' "
						+ "order by t.name";
		return this.getDbAccessor().executeQueryToList(sql, HrbSite.class);
	}
	
	public List listEnableSites() {
		String sql = "select t.name,t.description "
						+ "from hrb_site t "
						+ "where t.is_enable = '1' "
						+ "order by t.name";
		return this.getDbAccessor().executeQueryToList(sql, HrbSite.class);
	}

}
