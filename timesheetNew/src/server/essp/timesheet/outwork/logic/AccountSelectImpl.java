package server.essp.timesheet.outwork.logic;

import server.framework.taglib.util.SelectContentBase;
import java.util.ArrayList;
import java.sql.SQLException;
import server.framework.taglib.util.UpSelectList;
import itf.orgnization.OrgnizationFactory;
import itf.orgnization.IOrgnizationUtil;
import java.util.List;
import c2s.essp.common.account.IDtoAccount;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsLaborResource;

public class AccountSelectImpl extends SelectContentBase {

	private static ArrayList list = null;

	public AccountSelectImpl() {
	}

	public Object getDefualt() {
		return new UpSelectList(-1, "-1", "--Please Select Account--", "-1");
	}

	public ArrayList getList() {
		return list;
	}

	public void getData() throws Exception {
		list = new ArrayList();
		String departs[] = this.orgIds.split(",");
		for (int i = 0; i < departs.length; i++) {
			// 根据OrgId来查对应的部门下面的项目
			LgAccount lg = new LgAccount();
			List acntList = lg.listAccountByDeptId(departs[i]);
			list.add(new UpSelectList(-1, "", "--Please Select Account--",
					departs[i]));
			for (int j = 0; j < acntList.size(); j++) {
				TsAccount acnt = (TsAccount) acntList.get(j);
				Long acntRid = acnt.getRid();
				String value = acnt.getAccountId();
				String acntName = acnt.getAccountName();
				if(acntName != null && "".equals(acntName) == false) {
					acntName = acntName.replaceAll("\"", "\\\\\"");
					value += " -- " + acntName;
				}
				list.add(new UpSelectList(j, acntRid.toString(), value, departs[i]));
			}
		}
	}

}
