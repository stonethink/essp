package client.essp.pwm.workbench.workscope;

import c2s.essp.pwm.wp.DtoWSAccount;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.event.RowSelectedListener;
import com.wits.util.Parameter;

public class VwWorkScope extends VWTDWorkArea {

    private boolean refreshFlag = false;
    /**
     * define common data (globe)
     */
    public VwAccountList vwAccountList;
    private VwActivityList vwActivityList;
    private VwWPList vwWPList;

    /**
     * default constructor
     */
    public VwWorkScope() {
        super(100);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    //Component initialization
    private void jbInit() throws Exception {
//        this.setPreferredSize(new Dimension(700, 470));
        //this.setPreferredSize(new Dimension(210, 400));

        vwAccountList = new VwAccountList();
        //vwAccountList = new VwAccountListTest();
        this.getTopArea().addTab("Account", vwAccountList);

        vwWPList = new VwWPList();
        this.getDownArea().addTab("Work Package", vwWPList);

        vwActivityList = new VwActivityList();
        this.getDownArea().addTab("Activity", vwActivityList);
    }


    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.vwAccountList.getTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedScopeItem();
            }
        });
    }

    /////// 段4，事件处理
    public void processRowSelectedScopeItem() {
        DtoWSAccount account = (DtoWSAccount)this.vwAccountList.getSelectedData();
        if (account != null) {
            Parameter para = new Parameter();
            para.put("activityList", account.getActivityList());
            vwActivityList.setParameter(para);

            Parameter para2 = new Parameter();
            para2.put("wpList", account.getWpList());
            vwWPList.setParameter(para2);
        } else {
            vwActivityList.setParameter(new Parameter());
            vwWPList.setParameter(new Parameter());
        }

        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }

    public void setParameter(Parameter p){
        this.refreshFlag = true;
        this.vwAccountList.setParameter(p);
    }

    public void refreshWorkArea(){
        if( refreshFlag == true ){
            this.vwAccountList.refreshWorkArea();
            refreshFlag = false;
        }
    }
}
