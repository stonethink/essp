package client.essp.pms.account.baseline.compare.timing;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJTable;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import c2s.essp.pms.account.DtoAcntTiming;
import com.wits.util.Parameter;
import javax.swing.JPanel;
import client.framework.view.vwcomp.VWJLabel;
import java.awt.Dimension;
import java.util.List;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwAcntMilestone extends VWGeneralWorkArea {
    VwAcntMilestoneList vwAcntMilestoneList;
   JPanel headPanel = new JPanel();
   VWJLabel titleLabel = new VWJLabel();
   DtoAcntTiming dtoAcntTiming;
   private List currentMilestoneList;
   public VwAcntMilestone() {
       try {
           jbInit();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
   }

   public void jbInit() throws Exception {
       this.setLayout(new BorderLayout());
       vwAcntMilestoneList = new VwAcntMilestoneList();
//       headPanel.setLayout(null);
       titleLabel.setText("Current Milestone");
//       titleLabel.setBounds(new Rectangle(133, 0, 111, 28));

       this.add(vwAcntMilestoneList,BorderLayout.CENTER);
        headPanel.setSize(800,25);
        headPanel.setPreferredSize(new Dimension(350,25));
//       headPanel.setMaximumSize(new Dimension(800,25));
//       headPanel.setMinimumSize(new Dimension(800,25));
        headPanel.add(titleLabel, BorderLayout.CENTER);
       this.add(headPanel,BorderLayout.NORTH);
    }

   public void setParameter(Parameter param){
       super.setParameter(param);
       if(dtoAcntTiming==null)
           dtoAcntTiming=new DtoAcntTiming();
       dtoAcntTiming.setAcntRid((Long)param.get("acntRid"));
       //System.out.println(param.get("currentMilestone"));
       currentMilestoneList=(List)param.get("currentMilestone");
   }

   public void refreshWorkArea(){

       Parameter param = new Parameter();
       param.put("acntRid",dtoAcntTiming.getAcntRid());
       param.put("currentMilestone",currentMilestoneList);
       vwAcntMilestoneList.setParameter(param);
       vwAcntMilestoneList.refreshWorkArea();
       this.titleLabel.setToolTipText(titleLabel.getText());
   }

   public VWJTable getTable(){
       return vwAcntMilestoneList.getTable();
   }

}
