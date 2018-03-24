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

    /////// ��1��������棺������棨����ؼ������ÿؼ����ƣ�������˳�򣩡���������¼�
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
     * ������棺��������¼�
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

    /////// ��2�����ò�������ȡ�������
    public void setParameter(Parameter param) {
        this.acntRid = (Long) param.get(DtoAcntKEY.ACNT_RID);
        this.refreshFlag = true;
    }

    /////// ��3����ȡ���ݲ�ˢ�»���
    //������workArea����Ҫˢ���Լ�
    public void refreshWorkArea() {
        if (this.refreshFlag == true) {
            Parameter param = new Parameter();
            param.put(DtoAcntKEY.ACNT_RID, this.acntRid);
            vwAcntNoneLaborResList.setParameter(param);
            vwAcntNoneLaborResList.refreshWorkArea();

            this.refreshFlag = false;
        }
    }

    /////// ��4���¼�����
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

    /////// ��5����������
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
