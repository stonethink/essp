package client.essp.pms.account.baseline.compare.timing;

import client.essp.common.view.VWGeneralWorkArea;
import javax.swing.JPanel;
import client.framework.view.vwcomp.VWJLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import client.framework.view.vwcomp.VWJTable;
import com.wits.util.Parameter;
import c2s.essp.pms.account.DtoAcntTiming;
import java.awt.*;
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
public class VwAcntBaseline extends VWGeneralWorkArea {

    VwAcntBaselineList vwAcntBaselineList;
    JPanel headPanel = new JPanel();
    VWJLabel titleLabel = new VWJLabel();
    DtoAcntTiming dtoAcntTiming;
    private List baselineList;
    public VwAcntBaseline() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        this.setLayout(new BorderLayout());
        vwAcntBaselineList = new VwAcntBaselineList();
//        headPanel.setLayout(null);
        titleLabel.setText("Last Milestone");
//        titleLabel.setBounds(new Rectangle(200, 28));

        this.add(vwAcntBaselineList,BorderLayout.CENTER);
        headPanel.setSize(800,25);
        headPanel.setPreferredSize(new Dimension(350,25));
//        headPanel.setMaximumSize(new Dimension(800,25));
//        headPanel.setMinimumSize(new Dimension(800,25));
        headPanel.add(titleLabel, BorderLayout.CENTER);
        this.add(headPanel,BorderLayout.NORTH);

    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        if(dtoAcntTiming==null)
            dtoAcntTiming=new DtoAcntTiming();
        dtoAcntTiming.setAcntRid((Long)param.get("acntRid"));
        dtoAcntTiming.setBaseLineId((String)param.get("baseLineId"));
        baselineList=(List)param.get("lastBaseLine");
    }

    public void refreshWorkArea(){

        Parameter param = new Parameter();
        param.put("acntRid",dtoAcntTiming.getAcntRid());
        param.put("lastBaseLine",baselineList);
        vwAcntBaselineList.setParameter(param);
        vwAcntBaselineList.refreshWorkArea();
        this.titleLabel.setText(dtoAcntTiming.getBaseLineId()+" Milestone");
        this.titleLabel.setToolTipText(titleLabel.getText());
    }

    public VWJTable getTable(){
        return vwAcntBaselineList.getTable();
    }

}
