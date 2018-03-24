
package server.essp.projectpre.service.accountapplication;

import java.util.ArrayList;
import java.util.List;

import server.essp.projectpre.db.Acnt;
import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.account.AccountServiceImpl;
import server.essp.projectpre.service.account.IAccountService;
import server.essp.projectpre.service.bd.BdServiceImpl;
import server.essp.projectpre.service.bd.IBdService;
import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.UpSelectList;

public class ExecUnitImpl extends SelectContentBase {
    private static ArrayList list = null;
    public void getData() throws Exception {
        list = new ArrayList();
        IBdService  bdLogic = new BdServiceImpl();
        List bdList = bdLogic.listAllEabled();
        Bd bd = new Bd();
        list.add(getDefualt());
        for(int i = 0; i<bdList.size();i++) {
            bd = (Bd) bdList.get(i);
            IAccountService logic =new AccountServiceImpl();
            List acntList = logic.listDeptByCostBelong(bd.getBdCode());
            list.add(new UpSelectList( -1, "-1", "--please select--", bd.getBdCode()));
            for(int j=0; j<acntList.size(); j++){
                Acnt acnt = (Acnt)acntList.get(j);
                if(acnt!=null){
                   list.add(new UpSelectList( j, acnt.getAcntId()+"---"+acnt.getAcntName(), acnt.getAcntId()+"---"+acnt.getAcntName(), bd.getBdCode()));
                }
            }
       }
    }

    public ArrayList getList() {
        return list;
    }

    public Object getDefualt() {
        return new UpSelectList( -1, "-1", "--please select--", "-1");
    }

}
