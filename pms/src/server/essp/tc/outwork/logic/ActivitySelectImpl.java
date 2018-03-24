package server.essp.tc.outwork.logic;

import server.framework.taglib.util.UpSelectList;
import java.util.ArrayList;
import java.sql.SQLException;
import server.framework.taglib.util.SelectContentBase;
import server.essp.pwm.wp.logic.LGGetActivityList;
import c2s.essp.pwm.wp.DtoActivityInfo;
import itf.orgnization.OrgnizationFactory;
import java.util.List;
import itf.orgnization.IOrgnizationUtil;

public class ActivitySelectImpl extends SelectContentBase {


    private static ArrayList list = null;

    public ActivitySelectImpl() {
    }

    public Object getDefualt() {
        return new UpSelectList( -1, "-1", "--Please Select Activity--", "-1");
    }

    public ArrayList getList() {
        return list;
    }


    public void getData() throws SQLException {
        list = new ArrayList();

        String departs[] = this.orgIds.split(",");
        for (int i = 0; i < departs.length; i++) {
            //根据OrgId来查对应的部门下面的项目
            IOrgnizationUtil orgUtil = OrgnizationFactory.create();
            List acntList = (List) orgUtil.getAcntListInOrgs("'" + departs[i] + "'");
            for (int j = 0; j < acntList.size(); j++) {
                String acntId = acntList.get(j).toString();
                //根据项目ID得到该项目下面的活动
                LGGetActivityList getActivityList = new LGGetActivityList();
                List activity = null;
                try {
                    activity = getActivityList.getActivityList(acntId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                list.add(new UpSelectList( -1, "", "--Please Select Activity--", acntId.toString()));
                for (int k = 0; k < activity.size(); k++) {
                    DtoActivityInfo dtoActivityInfo = (DtoActivityInfo) activity.get(k);
                    list.add(new UpSelectList(k, dtoActivityInfo.getActivityId().toString(),
                                              dtoActivityInfo.getClnitem(), acntId.toString()));
                }
            }

        }
//For Test
//        list.add(new UpSelectList(0, "Activity_code", "Activity 1", "0"));
//        list.add(new UpSelectList(1, "Activity_code", "Activity 2", "0"));
//        list.add(new UpSelectList(2, "Activity_code", "Activity 3", "1"));
//        list.add(new UpSelectList(3, "Activity_code", "Activity 4", "2"));
    }

}
