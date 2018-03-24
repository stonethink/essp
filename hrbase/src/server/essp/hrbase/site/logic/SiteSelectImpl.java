package server.essp.hrbase.site.logic;

import java.util.ArrayList;
import java.util.List;

import c2s.essp.common.user.DtoUser;

import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.UpSelectList;
import db.essp.hrbase.HrbSite;

public class SiteSelectImpl extends SelectContentBase {

	private static ArrayList list = null;

	public Object getDefualt() {
		return new UpSelectList(-1, "", "--Please Select Site--", "-1");
	}

	public ArrayList getList() {
		return list;
	}

	public void getData() throws Exception {
		list = new ArrayList();
		LgSiteSelection lg = new LgSiteSelection();
		List siteList = lg.listEnableSites(getLoginId());
		String all = "";
		for (int j = 0; j < siteList.size(); j++) {
			HrbSite site = (HrbSite) siteList.get(j);
			String name = site.getName();
			if(all.equals("")) {
				all = name;
			} else {
				all += "," + name;
			}
			String description = site.getDescription();
			String showName = name;
			if(description != null && "".equals(description) == false) {
				showName += " -- " + description;
			}
			list.add(new UpSelectList(j, name, showName, ""));
		}
		if(this.getOrgIds() != null && "true".equals(this.getOrgIds())) {
			list.add(0, new UpSelectList(-1, all, "--Please Select Site--", "-1"));
		} else {
			list.add(0, new UpSelectList(-1, "-1", "--Please Select Site--", "-1"));
		}
	}
	
	private String getLoginId() {
		DtoUser user = (DtoUser)this.getSession().getAttribute(DtoUser.SESSION_USER);
		return user.getUserLoginId();
	}
}
