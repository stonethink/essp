package client.essp.tc.weeklyreport;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Date;

import javax.swing.SwingConstants;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.dto.InputInfo;

public class VwPeriod extends VWGeneralWorkArea {
    public final static String actionIdInit = "FTCWeeklyReportInitAction";

    protected VWJLabel dspWorker = new VWJLabel();
    protected VWJLabel dspDept = new VWJLabel();
    protected VWJLabel dteBeginPeriod = new VWJLabel();
    protected VWJLabel dteEndPeriod = new VWJLabel();

    protected VWJLabel lblWorker = new VWJLabel();
    protected VWJLabel lblDept = new VWJLabel();
    protected VWJLabel lblPeriod = new VWJLabel();
    VWJLabel lblAnd = new VWJLabel();

    String userId;

    public VwPeriod() {
        try {
            jbInit();

            initPeriod();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setLayout(null);
        //setBorder(BorderFactory.createLineBorder(Color.blue));
        this.setMinimumSize(new Dimension( -1, 26));
        this.setPreferredSize(new Dimension( -1, 26));
        this.setMaximumSize(new Dimension( -1, 26));

        lblWorker.setText("Worker: ");
        lblWorker.setBounds(new Rectangle(10, 10, 50, 20));
        dspWorker.setBounds(new Rectangle(59, 10, 102, 20));
        //dspWorker.setText("stone.shi");

        lblDept.setText("Dept: ");
        lblDept.setBounds(new Rectangle(188, 10, 43, 20));
        dspDept.setBounds(new Rectangle(230, 10, 160, 20));
        //dspDept.setText("wistronits(һ)");

        lblPeriod.setText("Period: ");
        lblPeriod.setBounds(new Rectangle(422, 10, 49, 20));
        dteBeginPeriod.setBounds(new Rectangle(473, 10, 68, 20));
        dteEndPeriod.setBounds(new Rectangle(555, 10, 68, 20));
        //dteBeginPeriod.setText("2005/12/06");
        //dteEndPeriod.setText("2005/12/06");
        dteEndPeriod.setHorizontalAlignment(SwingConstants.CENTER);
        dteBeginPeriod.setHorizontalAlignment(SwingConstants.CENTER);

        lblAnd.setText("~");
        lblAnd.setBounds(new Rectangle(540, 20, 14, 10));
        lblAnd.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lblWorker, null);
        this.add(dspWorker, null);
        this.add(lblDept, null);
        this.add(lblPeriod, null);
        this.add(dspDept, null);
        this.add(dteBeginPeriod, null);
        this.add(lblAnd);
        this.add(dteEndPeriod, null);
    }

    private void initPeriod() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionIdInit);
        inputInfo.setInputObj(DtoTcKey.USER_ID, this.userId);

        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (returnInfo.isError() == false) {
            //String userId = (String) returnInfo.getReturnObj(DtoTcKey.USER_ID);

            String dept = (String) returnInfo.getReturnObj(DtoTcKey.DEPT);
            this.setDept(dept);
            this.setWorker(userId);
        }
    }

    void refresh(){
        initPeriod();
    }

    public void setWorker(String worker) {
        this.dspWorker.setText(worker);
    }

    public void setDept(String dept) {
        this.dspDept.setText(dept);
    }

    public void setParameter(Date beginPeriod, Date endPeriod) {
        setBeginPeriod(beginPeriod);
        setEndPeriod(endPeriod);
    }

    public void setBeginPeriod(Date beginPeriod) {
        String str = comDate.dateToString(beginPeriod);
        this.dteBeginPeriod.setText(str);
    }

    public void setEndPeriod(Date endPeriod) {
        String str = comDate.dateToString(endPeriod);
        this.dteEndPeriod.setText(str);
    }

    public void setUserId(String userId){
        this.userId = userId;
        initPeriod();
    }

    public static void main(String args[]) {
        TestPanel.show(new VwPeriod());
    }
}
