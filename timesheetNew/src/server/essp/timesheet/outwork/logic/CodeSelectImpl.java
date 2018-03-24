package server.essp.timesheet.outwork.logic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.essp.timesheet.TsAccount;
import db.essp.timesheet.TsCodeValue;
import db.essp.timesheet.TsLaborResource;

import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.UpSelectList;

public class CodeSelectImpl extends SelectContentBase {

	private static ArrayList list = null;

    public Object getDefualt() {
        return new UpSelectList( -1, "-1", "--Please Select Code--", "-1");
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
            	Long typeRid = acnt.getCodeTypeRid();
            	if(typeRid == null) continue;
            	String acntId = acnt.getAccountId();
                List codeList = lg.listCodeByCodeTypeRid(typeRid);
                list.add(new UpSelectList( -1, "", "--Please Select Code--", acntRid.toString()));
				for(int k = 0; k < codeList.size(); k++) {
					TsCodeValue code = (TsCodeValue) codeList.get(k);
					String value = code.getName();
					String description = code.getDescription();
					if(description != null && "".equals(description) == false) {
						value += " -- " + description;
					}
					list.add(new UpSelectList(k, code.getRid().toString(), value, acntRid.toString()));
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
