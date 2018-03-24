package server.essp.hrbase.attributegroup.logic;

import java.util.ArrayList;
import java.util.List;

import server.essp.hrbase.site.logic.LgSiteSelection;
import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.UpSelectList;
import db.essp.hrbase.HrbAttributeGroup;
import db.essp.hrbase.HrbSite;

public class HrAttSelectImpl extends SelectContentBase {

	private static ArrayList list = null;

	public Object getDefualt() {
		return new UpSelectList(-1, "", "--Please Select HR Attribute--", "-1");
	}

	public ArrayList getList() {
		return list;
	}

	public void getData() throws Exception {
		list = new ArrayList();
		LgSiteSelection lgSite = new LgSiteSelection();
		List siteList = lgSite.listEnableSites();
		LgAttSelection lg = new LgAttSelection();
		for(int i = 0; i < siteList.size(); i ++) {
			HrbSite site = (HrbSite) siteList.get(i);
			String siteName = site.getName();
			List attGrpList = lg.listSiteHrAttributes(siteName);
			list.add(new UpSelectList(-1, "-1", "--Please Select HR Attribute--", siteName));
			for (int j = 0; j < attGrpList.size(); j++) {
				HrbAttributeGroup att = (HrbAttributeGroup) attGrpList.get(j);
				String rid = att.getRid().toString();
				String code = att.getCode();
				String name = att.getDescription();
				String showName = code;
				if(name != null && "".equals(name) == false) {
					showName += " -- " + name;
				}
				list.add(new UpSelectList(j, rid, showName, siteName));
			}
		}
	}
}
