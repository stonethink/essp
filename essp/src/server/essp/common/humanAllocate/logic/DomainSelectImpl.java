package server.essp.common.humanAllocate.logic;

import java.util.ArrayList;
import java.util.List;

import server.essp.common.ldap.LDAPUtil;
import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.UpSelectList;

public class DomainSelectImpl extends SelectContentBase {

	private static ArrayList list = null;

	public Object getDefualt() {
		return new UpSelectList(-1, "", "--Please Select Domain--", "-1");
	}

	public ArrayList getList() {
		return list;
	}

	public void getData() throws Exception {
		list = new ArrayList();
		List domainList = LDAPUtil.getDomainIdList();
		for (int j = 0; j < domainList.size(); j++) {
			String domain = (String) domainList.get(j);
			list.add(new UpSelectList(j, domain, domain, ""));
			System.out.println("$$$$$$$$$$$$ " + domain);
		}
	}
}
