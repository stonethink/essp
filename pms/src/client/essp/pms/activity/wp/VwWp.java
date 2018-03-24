package client.essp.pms.activity.wp;

import java.awt.AWTEvent;
import java.awt.Dimension;

import c2s.essp.pwm.wp.DtoPwWp;
import client.essp.common.util.TestPanelParam;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.essp.pwm.wp.FPW01000PwWp;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import client.framework.view.vwcomp.IVWAppletParameter;
import com.wits.util.Parameter;
import com.wits.util.ProcessVariant;
import com.wits.util.IVariantListener;
import c2s.essp.common.user.DtoUser;
import client.framework.common.Global;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwWp extends VWTDWorkArea implements IVWAppletParameter, IVariantListener{
   /**
     * define control variable
     */
    private boolean refreshFlag = false;

    /**parameters*/
    //private Long activityRid;

    /**
     * define common data (globe)
     */
    VwWpList vwWpList;
    FPW01000PwWp fPW01000PwWp;

    /**
     * default constructor
     */
    public VwWp() {
        super(250);
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

        vwWpList = new VwWpList();
        this.getTopArea().addTab("Wp",vwWpList);

        fPW01000PwWp = new FPW01000PwWp();

        this.setDownArea(fPW01000PwWp);
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.vwWpList.getTable().addRowSelectedLostListener( new RowSelectedLostListener(){
            public boolean processRowSelectedLost(int oldSelectedRow, Object oldSelectedData) {
                return processRowSelectedLostAccList(oldSelectedRow, oldSelectedData);
            }
        });

        this.vwWpList.getTable().addRowSelectedListener( new RowSelectedListener(){
            public void processRowSelected() {
                processRowSelectedList();
            }
        });

        this.fPW01000PwWp.getGeneralWorkArea().addDataChangedListener(new DataChangedListener(){
            public void processDataChanged(){
                vwWpList.getTable().refreshTable();
            }
        });

        ProcessVariant.addDataListener("account", this);
    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        //activityRid = (Long)param.get("activityRid");

        this.refreshFlag = true;
    }

    public String[][] getParameterInfo() {
        String[][] parameterInfo = { {"entryFunType", "", "entryFunType"}};
        return parameterInfo;
    }

    ///////段3，获取数据并刷新画面
    //最外层的workArea不需要刷新自己
    public void refreshWorkArea() {
        if (refreshFlag == true){
            Parameter param = new Parameter();
            //param.put("inActivityId", this.activityRid);
            param.put("isReadonly", Boolean.FALSE);
            vwWpList.setParameter(param);
            vwWpList.refreshWorkArea();

            this.refreshFlag = false;
        }
    }

    /////// 段4，事件处理
    public boolean processRowSelectedLostAccList(int oldSelectedRow, Object oldSelectedData ) {
        this.getDownArea().getSelectedWorkArea().saveWorkArea();
        return this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public void processRowSelectedList() {
        DtoPwWp wp = (DtoPwWp)this.vwWpList.getSelectedData();
        //Long acntRid = vwWpList.getAccountRid();

        if(wp != null){
            Parameter param1 = new Parameter();
            //param1.put("inActivityId", this.activityRid);
            param1.put("dtoPwWp", wp);
            this.fPW01000PwWp.setParameter(param1);
        }else{
            this.fPW01000PwWp.setParameter(new Parameter());
        }

        this.getDownArea().refreshWorkArea();
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        if (this.refreshFlag == true) {
            vwWpList.saveWorkArea();
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public void dataChanged(String name, Object value) {
        if (name.equals("account")) {
             this.refreshFlag = true;
        }
    }

    public static void main(String[] args) {

//        DtoUser user = new DtoUser();
//        Global.userId = "stone.shi";
//        user.setUserID("stone.shi");
//        user.setUserLoginId("stone.shi");
//        HttpServletRequest request = new HttpServletRequestMock();
//        HttpSession session = request.getSession();
//        session.setAttribute(DtoUser.SESSION_USER, user);
        Parameter param = new Parameter();
        param.put("inAcntRid", new Long(6022));
        param.put("entryFunType", "PmsAccountFun");

        VWWorkArea workArea = new VwWp();
        workArea.setParameter(param);

        TestPanelParam.show(workArea);
        workArea.refreshWorkArea();
    }

}
