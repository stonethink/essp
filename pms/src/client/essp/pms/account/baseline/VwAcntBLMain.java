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
    DtoPmsAcnt dtoAccount = null; //传入项目


    /**
     * define common data (globe)
     */

    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件

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
     * 定义界面：定义界面事件
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


    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        super.setParameter(param);
        params = param;
//        dtoAccount  = (DtoPmsAcnt) param.get("DtoPmsAcnt");
        this.vwAcntBLC.setParameter(param);
        this.vwAcntBLLog.setParameter(param);
    }


    /////// 段3，获取数据并刷新画面
    //最外层的workArea不需要刷新自己
    protected void resetUI() {
        this.getSelectedWorkArea().refreshWorkArea();
    }



    /////// 段5，保存数据
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
