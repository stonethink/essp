package server.essp.timesheet.outwork.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.UpSelectList;
import c2s.essp.timesheet.workscope.DtoActivity;
import db.essp.timesheet.TsAccount;

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
        try {
        for (int i = 0; i < departs.length; i++) {
//        	根据OrgId来查对应的部门下面的项目
        	LgAccount lg = new LgAccount();
            List acntList = lg.listAccountByDeptId(departs[i]);
            for(int j=0;j<acntList.size();j++){
            	TsAccount acnt = (TsAccount) acntList.get(j);
                //根据项目ID得到该项目下面的员工
            	Long acntRid = acnt.getRid();
            	String acntId = acnt.getAccountId();
            	LgActivity lgActivity = new LgActivity();
                List activities = lgActivity.listActivityByAcntId(acntId);
                list.add(new UpSelectList( -1, "", "--Please Select Activity--", acntRid.toString()));
				for(int k = 0; k < activities.size(); k++) {
					DtoActivity dto = (DtoActivity) activities.get(k);
					String actName = dto.getName();
					if(actName == null) {
						actName = "";
					}
					actName = actName.replaceAll("\"", "\\\\\"");
					list.add(new UpSelectList(k, dto.getId().toString(), dto.getCode() + " - " + actName, acntRid.toString()));
				}
            }

        }
        } catch(Exception e) {
        	e.printStackTrace();
        }
//For Test
//        list.add(new UpSelectList(0, "Activity_code", "Activity 1", "0"));
//        list.add(new UpSelectList(1, "Activity_code", "Activity 2", "0"));
//        list.add(new UpSelectList(2, "Activity_code", "Activity 3", "1"));
//        list.add(new UpSelectList(3, "Activity_code", "Activity 4", "2"));
    }
    
    public static void main(String[] args) {
    	String t = "sssf \"test\"";
    	System.out.println("\"");
    	System.out.println("\\\"");
    	System.out.println(t);
    	t = t.replaceAll("\"", "\\\\\"");
    	System.out.println(t);
    }

}
