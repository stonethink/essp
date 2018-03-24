package client.essp.pms.account.pcb;

import java.awt.AWTEvent;
import java.awt.Dimension;


import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.pcb.DtoPcbItem;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import com.wits.util.Parameter;



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
public class VwAccountPCB extends VWTDWorkArea {
    private boolean refreshFlag = false;

    /**
     * define common data (globe)
     */
    Long acntRid;

    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    VwAccountPCBItemList vwAccountPCBItemList;
    VwAccountPCBParameterList vwAccountPCBParameterList;

    /**
     * default constructor
     */
    public VwAccountPCB() {
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

        vwAccountPCBItemList = new VwAccountPCBItemList();
        vwAccountPCBParameterList = new VwAccountPCBParameterList();

        this.getTopArea().addTab("Items", vwAccountPCBItemList);
        this.getDownArea().addTab("Parameter", vwAccountPCBParameterList);
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.vwAccountPCBItemList.getTable().addRowSelectedLostListener(new
            RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedData) {
                return processRowSelectedLostList(oldSelectedRow,
                                                  oldSelectedData);
            }
        });

        this.vwAccountPCBItemList.getTable().addRowSelectedListener(new
            RowSelectedListener() {
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
            vwAccountPCBItemList.setParameter(param);
            vwAccountPCBItemList.refreshWorkArea();

            this.refreshFlag = false;
        }
    }

    /////// 段4，事件处理
    public boolean processRowSelectedLostList(int oldSelectedRow,
                                              Object oldSelectedData) {
        this.vwAccountPCBParameterList.saveWorkArea();
        return this.vwAccountPCBParameterList.isSaveOk();
    }

    public void processRowSelectedList() {
        DtoPcbItem dataBean = (DtoPcbItem)this.vwAccountPCBItemList.
                              getSelectedData();
        Long paramItemRid = null;
         Long paramAcntRid = null;

        if (dataBean != null) {
            paramItemRid = dataBean.getRid();
            paramAcntRid = dataBean.getAcntRid();
        }
        Parameter param = new Parameter();
        param.put(DtoAcntKEY.ACNT_RID, paramAcntRid);
        param.put("itemRid", paramItemRid);
        vwAccountPCBParameterList.setParameter(param);

        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        vwAccountPCBItemList.saveWorkArea();
        if (vwAccountPCBItemList.isSaveOk() == true) {
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public boolean isSaveOk() {
        return vwAccountPCBItemList.isSaveOk()
            && this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public static void main(String[] args) {

    }

}
