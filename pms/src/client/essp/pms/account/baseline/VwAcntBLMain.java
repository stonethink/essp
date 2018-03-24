package client.essp.pms.account.baseline;

import java.awt.AWTEvent;
import java.awt.Dimension;

import c2s.essp.pms.account.DtoPmsAcnt;
import client.essp.common.view.VWGeneralWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.view.event.DataChangedListener;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;


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
public class VwAcntBLMain extends VWGeneralWorkArea {
    /**
     * input parameters
     */
    DtoPmsAcnt dtoAccount = null; //������Ŀ


    /**
     * define common data (globe)
     */

    /////// ��1��������棺������棨����ؼ������ÿؼ����ƣ�������˳�򣩡���������¼�

    Parameter params = new Parameter();


    /**
     * @link aggregation
     */
    VwAcntBLLog vwAcntBLLog;
    /**
     * @link aggregation
     */
    VwAcntBLCurrent vwAcntBLC;


    /**
     * default constructor
     */
    public VwAcntBLMain() {

        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }


    /** @link dependency
     * @stereotype run-time*/
    /*# EbsNodeViewManager lnkEbsNodeViewManager; */
    //Component initialization
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(700, 470));

        vwAcntBLLog = new VwAcntBLLog();
        vwAcntBLC = new VwAcntBLCurrent();
        this.addTab("Current Baseline", vwAcntBLC);
        this.addTab("Baseline Histroy", vwAcntBLLog);

    }


    /**
     * ������棺��������¼�
     */
    private void addUICEvent() {

        this.vwAcntBLC.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {

            }
        });


    }

    public void processDataChanged(){
        this.vwAcntBLLog.setParameter(params);
    }


    /////// ��2�����ò�������ȡ�������
    public void setParameter(Parameter param) {
        super.setParameter(param);
        params = param;
//        dtoAccount  = (DtoPmsAcnt) param.get("DtoPmsAcnt");
        this.vwAcntBLC.setParameter(param);
        this.vwAcntBLLog.setParameter(param);
    }


    /////// ��3����ȡ���ݲ�ˢ�»���
    //������workArea����Ҫˢ���Լ�
    protected void resetUI() {
        this.getSelectedWorkArea().refreshWorkArea();
    }



    /////// ��5����������
    public void saveWorkArea() {

    }

    public static void main(String[] args) {
        VWWorkArea workArea = new VwAcntBLMain();
        DtoPmsAcnt pmsAccount = new DtoPmsAcnt();
        pmsAccount.setRid(new Long(4));
        pmsAccount.setPm(true);

        Parameter param = new Parameter();
        param.put( "dtoAccount", pmsAccount );
        workArea.setParameter(param);

        VWWorkArea workArea2 = new VWWorkArea();
        workArea2.addTab("Baseline", workArea);

        TestPanel.show(workArea2);
        workArea.refreshWorkArea();
    }

}
