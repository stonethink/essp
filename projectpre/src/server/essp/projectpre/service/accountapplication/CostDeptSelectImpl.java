
package server.essp.projectpre.service.accountapplication;

import java.util.ArrayList;
import java.util.List;

import server.essp.projectpre.db.Bd;
import server.essp.projectpre.service.bd.BdServiceImpl;
import server.essp.projectpre.service.bd.IBdService;
import server.framework.taglib.util.SelectContentBase;
import server.framework.taglib.util.SelectList;

public class CostDeptSelectImpl extends SelectContentBase {
    private static ArrayList list = null;
    public void getData() throws Exception {
        //获得业绩归属，成本归属，业务来源
        IBdService  bdLogic = new BdServiceImpl();
        List bdList = bdLogic.listAllEabled();
         list = new ArrayList();
        Bd bd = new Bd();
        list.add(getDefualt());
       for(int i = 0; i<bdList.size();i++) {
           bd = (Bd) bdList.get(i);
           list.add(new SelectList(i, bd.getBdCode(), bd.getBdName()));
          
       }
    }

    

    public ArrayList getList() {
        return list;
    }

    public Object getDefualt() {
         return new SelectList( -1, "-1", "--please select--");
    }

}
