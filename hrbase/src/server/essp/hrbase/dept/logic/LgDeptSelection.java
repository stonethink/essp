package server.essp.hrbase.dept.logic;

import java.util.List;

import db.essp.hrbase.HrbSite;
import db.essp.hrbase.HrbUnitBase;

import server.essp.framework.logic.AbstractESSPLogic;

public class LgDeptSelection extends AbstractESSPLogic {
	
	public List listSiteDepts(String site) {
		String sql = "select t.unit_code, t.unit_name from hrb_unit_base t "
			+ "where t.belong_site = '" + site + "'";
		return this.getDbAccessor().executeQueryToList(sql, HrbUnitBase.class);
	}

}
