package client.essp.pms.account.baseline.compare.budget;

import client.essp.common.view.VWGeneralWorkArea;
import javax.swing.JPanel;
import client.framework.view.vwcomp.VWJLabel;
import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import com.wits.util.Parameter;
import c2s.essp.pms.account.DtoAcntKEY;
import client.framework.view.vwcomp.VWJTable;


public class VwAcntBLBudCurBase extends VWGeneralWorkArea{
    VwAcntBLBudCurList vwAcntBLBudCurList;
    JPanel headPanel = new JPanel();
   VWJLabel titleLabel = new VWJLabel();
   Long acntRid;
   Integer flag1;
   Integer flag2;
   private List acntBLBudList=new ArrayList();
    public VwAcntBLBudCurBase() {
        try {
          jbInit();
      } catch (Exception ex) {
          ex.printStackTrace();
      }
    }
    public void jbInit() throws Exception {
        this.setLayout(new BorderLayout());
        vwAcntBLBudCurList=new VwAcntBLBudCurList();
         titleLabel.setText("Current Budget");
         this.add(vwAcntBLBudCurList,BorderLayout.CENTER);
         headPanel.setSize(800,25);
        headPanel.setPreferredSize(new Dimension(350,25));
        headPanel.add(titleLabel, BorderLayout.CENTER);
       this.add(headPanel,BorderLayout.NORTH);
    }
    public void setParameter(Parameter param){
        super.setParameter(param);
        this.acntRid = (Long) (param.get(DtoAcntKEY.ACNT_RID));
       this.acntBLBudList=(List)param.get("BLBud");

       this.flag1=(Integer)param.get("flag1");
       this.flag2=(Integer)param.get("flag2");
    }
    public void refreshWorkArea(){
         Parameter param = new Parameter();
         param.put("BLBud",acntBLBudList);
            param.put(DtoAcntKEY.ACNT_RID,this.acntRid);
            param.put("flag1",flag1);
             param.put("flag2",flag2);
            vwAcntBLBudCurList.setParameter(param);
            vwAcntBLBudCurList.refreshWorkArea();
    }
     public VWJTable getTable(){
         return vwAcntBLBudCurList.getTable();
     }
}
