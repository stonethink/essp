package server.essp.common.humanAllocate.logic;

import java.util.ArrayList;
import java.util.List;

import c2s.essp.common.user.DtoUserInfo;

import server.essp.common.ldap.LDAPUtil;
import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.UpSelectList;

public class GroupSelectImpl extends SelectContentBase {

	private static ArrayList list = null;

	public Object getDefualt() {
		return new UpSelectList(-1, "", "--Please Select Group--", "-1");
	}

	public ArrayList getList() {
		return list;
	}

	public void getData() throws Exception {
		list = new ArrayList();
		List domainList = LDAPUtil.getDomainIdList();
		LDAPUtil ldap = new LDAPUtil();
		for(int i = 0; i < domainList.size(); i ++) {
			String domain = (String) domainList.get(i);
			List groupList = ldap.findGroup(domain, "W", true);
			for (int j = 0; j < groupList.size(); j++) {
				DtoUserInfo group = (DtoUserInfo) groupList.get(j);
				String value = group.getOrganization();
				String name = group.getUserName();
				list.add(new UpSelectList(j, value, name, domain));
			}
		}
	}
}
