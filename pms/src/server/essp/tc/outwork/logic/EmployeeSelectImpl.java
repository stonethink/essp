package server.essp.tc.outwork.logic;

import java.util.ArrayList;
import java.sql.SQLException;
import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.UpSelectList;
import itf.orgnization.OrgnizationFactory;
import java.util.List;
import server.essp.pms.account.logic.LgAccountUtilImpl;
import itf.orgnization.IOrgnizationUtil;
import c2s.essp.common.account.IDtoAccount;
import server.essp.pms.account.labor.logic.LgAccountLaborRes;
import c2s.essp.pms.account.DtoAcntLaborResDetail;
import db.essp.pms.LaborResource;

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
            IOrgnizationUtil orgUtil = OrgnizationFactory.create();
            List acntList=(List)orgUtil.getAcntListInOrgs("'" + departs[i] + "'");
            for(int j=0;j<acntList.size();j++){
                Long acntId=(Long)acntList.get(j);
//                IDtoAccount idtoa = new LgAccountUtilImpl().getAccountByRID(acntId);
                //list.add(new UpSelectList(j,Integer.toString(j),idtoa.getDisplayName(),Integer.toString(i)));
                //根据项目ID得到该项目下面的员工
                LgAccountLaborRes lgAccountLabors=new LgAccountLaborRes();
                List labors=lgAccountLabors.listLaborRes(acntId);
                list.add(new UpSelectList( -1, "", "--Please Select Employee--", acntId.toString()));
                for(int k=0;k<labors.size();k++){
                    LaborResource resDetail=(LaborResource)labors.get(k);
                    list.add(new UpSelectList(k,resDetail.getLoginId(),resDetail.getEmpName(),acntId.toString()));
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
