package server.essp.hrbase.dept.logic;

import java.util.ArrayList;
import java.util.List;

import server.essp.hrbase.site.logic.LgSiteSelection;
import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.UpSelectList;
import db.essp.hrbase.HrbSite;
import db.essp.hrbase.HrbUnitBase;

public class DeptSelectImpl extends SelectContentBase {

	private static ArrayList list = null;

	public Object getDefualt() {
		return new UpSelectList(-1, "", "--Please Select Dept--", "-1");
	}

	public ArrayList getList() {
		return list;
	}

	public void getData() throws Exception {
		list = new ArrayList();
		LgSiteSelection lgSite = new LgSiteSelection();
		List siteList = lgSite.listEnableSites();
		LgDeptSelection lg = new LgDeptSelection();
		for(int i = 0; i < siteList.size(); i ++) {
			HrbSite site = (HrbSite) siteList.get(i);
			String siteName = site.getName();
			List deptList = lg.listSiteDepts(siteName);
			list.add(new UpSelectList(-1, "", "--Please Select Dept--", siteName));
			for (int j = 0; j < deptList.size(); j++) {
				HrbUnitBase dept = (HrbUnitBase) deptList.get(j);
				String code = dept.getUnitCode();
				String name = dept.getUnitName();
				String showName = code;
				if(name != null && "".equals(name) == false) {
					showName += " -- " + name;
				}
				list.add(new UpSelectList(j, code, showName, siteName));
			}
		}
	}
}
