package client.essp.pms.account.noneLabor;

import java.awt.AWTEvent;
import java.awt.Dimension;

import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoNoneLaborRes;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwAcntNoneLaborRes extends VWTDWorkArea {

    /**
     * define control variable
     */
    private boolean refreshFlag = false;

    /**
     * define common data (globe)
     */
    Long acntRid;

    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    VwAcntNoneLaborResList vwAcntNoneLaborResList;
    VwAcntNoneLaborResItemList vwAcntNoneLaborResItemList;

    /**
     * default constructor
     */
    public VwAcntNoneLaborRes() {
//        super(350);
        super(180);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    //Component initialization
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(700, 470));
        this.setHorizontalSplit();

        vwAcntNoneLaborResList = new VwAcntNoneLaborResList();
        vwAcntNoneLaborResItemList = new VwAcntNoneLaborResItemList();

        this.getTopArea().addTab("Environment", vwAcntNoneLaborResList);
        this.getDownArea().addTab("Items", vwAcntNoneLaborResItemList);
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.vwAcntNoneLaborResList.getTable().addRowSelectedLostListener(new RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow, Object oldSelectedData) {
                return processRowSelectedLostList(oldSelectedRow, oldSelectedData);
            }
        });

        this.vwAcntNoneLaborResList.getTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedList();
            }
        });
    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        this.acntRid = (Long) param.get(DtoAcntKEY.ACNT_RID);
        this.refreshFlag = true;
    }

    /////// 段3，获取数据并刷新画面
    //最外层的workArea不需要刷新自己
    public void refreshWorkArea() {
        if (this.refreshFlag == true) {
            Parameter param = new Parameter();
            param.put(DtoAcntKEY.ACNT_RID, this.acntRid);
            vwAcntNoneLaborResList.setParameter(param);
            vwAcntNoneLaborResList.refreshWorkArea();

            this.refreshFlag = false;
        }
    }

    /////// 段4，事件处理
    public boolean processRowSelectedLostList(int oldSelectedRow, Object oldSelectedData){
        this.vwAcntNoneLaborResItemList.saveWorkArea();
        return this.vwAcntNoneLaborResItemList.isSaveOk();
    }

    public void processRowSelectedList() {
        DtoNoneLaborRes dataBean = (DtoNoneLaborRes)this.vwAcntNoneLaborResList.getSelectedData();
        Long paramAcntRid = null;
        Long paramEnvRid = null;
        if (dataBean != null) {
            paramAcntRid = dataBean.getAcntRid();
            paramEnvRid = dataBean.getRid();
        }

        Parameter param = new Parameter();
        param.put(DtoAcntKEY.ACNT_RID, paramAcntRid);
        param.put(DtoAcntKEY.ENV_RID, paramEnvRid);
        vwAcntNoneLaborResItemList.setParameter(param);

        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        vwAcntNoneLaborResList.saveWorkArea();
        if( vwAcntNoneLaborResList.isSaveOk() == true ){
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public boolean isSaveOk(){
        return vwAcntNoneLaborResList.isSaveOk()
            && this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public static void main(String[] args) {
        VWWorkArea workArea = new VwAcntNoneLaborRes();
        Parameter param = new Parameter();
        param.put(DtoAcntKEY.ACNT_RID, new Long(7));

        workArea.setParameter(param);

        VWWorkArea workArea2 = new VWWorkArea();
        workArea2.addTab("NoneLaborResource", workArea);

        TestPanel.show(workArea2);
        workArea.refreshWorkArea();
    }

}
