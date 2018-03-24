package server.essp.tc.outwork.logic;

import java.util.ArrayList;

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
        if (orgIds != null && !orgIds.equals("")) {
            IOrgnizationUtil orgUtil = OrgnizationFactory.create();
            list = new ArrayList();
            String departs[] = orgIds.split(",");
            for (int i = 0; i < departs.length; i++) {
                list.add(new SelectList(i, departs[i], orgUtil.getOrgName(departs[i])));
            }
        }
    }


}
