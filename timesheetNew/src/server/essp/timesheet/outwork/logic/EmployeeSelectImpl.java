package server.essp.timesheet.outwork.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.UpSelectList;
import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsLaborResource;

public class EmployeeSelectImpl extends SelectContentBase{

    private static ArrayList list = null;

    public EmployeeSelectImpl() {
    }

    public Object getDefualt() {
        return new UpSelectList( -1, "-1", "--Please Select Employee--", "-1");
    }

    public ArrayList getList() {
        return list;
    }


    public void getData() throws SQLException {
        list = new ArrayList();
        String departs[]=this.orgIds.split(",");
        for(int i=0;i<departs.length;i++){
            //根据OrgId来查对应的部门下面的项目
        	LgAccount lg = new LgAccount();
            List acntList = lg.listAccountByDeptId(departs[i]);
            for(int j=0;j<acntList.size();j++){
            	TsAccount acnt = (TsAccount) acntList.get(j);
                //根据项目ID得到该项目下面的员工
            	Long acntRid = acnt.getRid();
            	String acntId = acnt.getAccountId();
                List laborList = lg.listLaborByAcntRid(acntRid);
                list.add(new UpSelectList( -1, "", "--Please Select Employee--", acntRid.toString()));
				for(int k = 0; k < laborList.size(); k++) {
					TsLaborResource labor = (TsLaborResource) laborList.get(k);
					list.add(new UpSelectList(k,labor.getLoginId(), labor.getName(), acntRid.toString()));
				}
            }

        }

//Test Data
//        list.add(new UpSelectList(0, "empLoginName", "Employee 1", "0"));
//        list.add(new UpSelectList(1, "empLoginName", "Employee 2", "0"));
//        list.add(new UpSelectList(2, "empLoginName", "Employee 3", "1"));
//        list.add(new UpSelectList(3, "empLoginName", "Employee 4", "1"));
    }

}
