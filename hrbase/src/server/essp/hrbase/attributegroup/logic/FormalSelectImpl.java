package server.essp.hrbase.attributegroup.logic;

import java.util.ArrayList;
import java.util.List;

import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.UpSelectList;
import db.essp.hrbase.HrbAttributeGroup;

public class FormalSelectImpl extends SelectContentBase {

	private static ArrayList list = null;

	public Object getDefualt() {
		return new UpSelectList(-1, "", "--Please Select Formal--", "-1");
	}

	public ArrayList getList() {
		return list;
	}

	public void getData() throws Exception {
		list = new ArrayList();
		LgAttSelection lg = new LgAttSelection();

			List attGrpList = lg.listFormals();
			list.add(new UpSelectList(-1, "", "--Please Select Formal--", "-1"));
			for (int j = 0; j < attGrpList.size(); j++) {
				HrbAttributeGroup att = (HrbAttributeGroup) attGrpList.get(j);
				String rid = att.getRid().toString();
				String code = att.getIsFormal();
				String showName = code;
				if("1".equals(code)) {
					showName = "Y";
				} else if("0".equals(code)) {
					showName = "N";
				} else {
					showName = "";
				}
				list.add(new UpSelectList(j, code, showName, rid));
			}

	}
}
