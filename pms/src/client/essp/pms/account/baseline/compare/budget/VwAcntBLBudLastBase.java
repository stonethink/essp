package client.essp.pms.account.baseline.compare.budget;

import client.essp.common.view.VWGeneralWorkArea;
import javax.swing.JPanel;
import java.util.List;
import client.framework.view.vwcomp.VWJLabel;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import com.wits.util.Parameter;
import c2s.essp.pms.account.DtoAcntKEY;
import client.framework.view.vwcomp.VWJTable;

public class VwAcntBLBudLastBase extends VWGeneralWorkArea{
    VwAcntBLBudLastList vwAcntBLBudLastList;
    JPanel headPanel = new JPanel();
   VWJLabel titleLabel = new VWJLabel();
   Long acntRid;
   Integer flag1;
   Integer flag2;
   private List acntBLBudList=new ArrayList();
    String version;
    public VwAcntBLBudLastBase() {
        try {
         jbInit();
     } catch (Exception ex) {
         ex.printStackTrace();
     }
    }
    public void jbInit() throws Exception {
        this.setLayout(new BorderLayout());
        vwAcntBLBudLastList=new VwAcntBLBudLastList();
        titleLabel.setText("BaseLine");
        this.add(vwAcntBLBudLastList,BorderLayout.CENTER);
        headPanel.setSize(800,25);
       headPanel.setPreferredSize(new Dimension(350,25));
       headPanel.add(titleLabel, BorderLayout.CENTER);
      this.add(headPanel,BorderLayout.NORTH);
    }
    public void setParameter(Parameter param){
       super.setParameter(param);
       this.acntRid = (Long) (param.get(DtoAcntKEY.ACNT_RID));
      this.acntBLBudList=(List)param.get("BLBud");
      this.version=(String)param.get("BaseId");
      this.flag1=(Integer)param.get("flag1");
       this.flag2=(Integer)param.get("flag2");
   }
   public void refreshWorkArea(){
       Parameter param = new Parameter();
       param.put("BLBud", acntBLBudList);
       param.put(DtoAcntKEY.ACNT_RID, this.acntRid);
       param.put("BaseId",this.version);
       param.put("flag1",flag1);
       param.put("flag2",flag2);
       vwAcntBLBudLastList.setParameter(param);
       if(this.version!=null){
        this.titleLabel.setText(this.version+" Budget");
        this.titleLabel.setToolTipText(this.version+" Budget");}
        vwAcntBLBudLastList.refreshWorkArea();
   }
    public VWJTable getTable(){
        return vwAcntBLBudLastList.getTable();
    }
}
