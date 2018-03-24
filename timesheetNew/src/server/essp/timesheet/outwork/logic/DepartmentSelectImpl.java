package server.essp.timesheet.outwork.logic;

import java.util.ArrayList;
import java.util.List;

import c2s.essp.common.user.DtoUser;
import db.essp.timesheet.TsOutworkerPrivilege;

import itf.orgnization.IOrgnizationUtil;
import itf.orgnization.OrgnizationFactory;
import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.SelectList;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Robin
 * @version 1.0
 */
public class DepartmentSelectImpl extends SelectContentBase {
    private static ArrayList list = null;
    public DepartmentSelectImpl() {
    }

    public Object getDefualt() {
        return new SelectList( -1, "", "--Please Select Department--");
    }

    public ArrayList getList() {
        return list;
    }

//For Test
//    public void getData() throws SQLException {
//
//        list = new ArrayList();
//        list.add(new SelectList(0, "0", "Engineer Div 1"));
//        list.add(new SelectList(1, "1", "Engineer Div 2"));
//    }

    public void getData() throws Exception {
        	LgAccount lg = new LgAccount();
            list = new ArrayList();
            List privilegeList = lg.listPrivilegeDeptByLoginId(getLoginId());
            for (int i = 0; i < privilegeList.size(); i++) {
            	TsOutworkerPrivilege bean = (TsOutworkerPrivilege)privilegeList.get(i);
                list.add(new SelectList(i, bean.getAcntId(), bean.getAcntName()));
            }
    }
    
    private String getLoginId() {
		DtoUser user = (DtoUser)this.getSession().getAttribute(DtoUser.SESSION_USER);
		return user.getUserLoginId();
	}
}
