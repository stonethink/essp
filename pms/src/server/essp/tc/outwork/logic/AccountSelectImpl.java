package server.essp.tc.outwork.logic;

import server.framework.taglib.util.SelectContentBase;
import java.util.ArrayList;
import java.sql.SQLException;
import server.framework.taglib.util.UpSelectList;
import itf.orgnization.OrgnizationFactory;
import itf.orgnization.IOrgnizationUtil;
import java.util.List;
import server.essp.pms.account.logic.LgAccountUtilImpl;
import c2s.essp.common.account.IDtoAccount;

public class AccountSelectImpl extends SelectContentBase {


    private static ArrayList list = null;

    public AccountSelectImpl() {
    }

    public Object getDefualt() {
        return new UpSelectList( -1, "-1", "--Please Select Account--", "-1");
    }

    public ArrayList getList() {
        return list;
    }


    public void getData() throws Exception {
        list = new ArrayList();
        String departs[]=this.orgIds.split(",");
        for(int i=0;i<departs.length;i++){
            //根据OrgId来查对应的部门下面的项目
            IOrgnizationUtil orgUtil = OrgnizationFactory.create();
            List acntList=(List)orgUtil.getAcntListInOrgs("'" + departs[i] + "'");
            list.add(new UpSelectList( -1, "", "--Please Select Account--", departs[i]));
            for(int j=0;j<acntList.size();j++){
                Long acntId=(Long)acntList.get(j);
                IDtoAccount idtoa = new LgAccountUtilImpl().getAccountByRID(acntId);
                list.add(new UpSelectList(j,acntId.toString(),idtoa.getDisplayName(),departs[i]));
            }

        }

//Test Data
//        list.add(new UpSelectList(0, "0", "Account 1", "0"));
//        list.add(new UpSelectList(1, "1", "Account 2", "0"));
//        list.add(new UpSelectList(2, "2", "Account 3", "1"));
//        list.add(new UpSelectList(3, "3", "Account 4", "1"));
    }

}
