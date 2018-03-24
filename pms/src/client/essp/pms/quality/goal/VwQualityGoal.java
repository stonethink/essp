package client.essp.pms.quality.goal;


import java.awt.AWTEvent;
import java.awt.Dimension;
import c2s.essp.common.code.DtoKey;
import c2s.essp.pms.quality.goal.DtoQualityGoal;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import com.wits.util.Parameter;
import javax.swing.event.*;
import client.framework.view.event.*;
import com.wits.util.IVariantListener;
import com.wits.util.ProcessVariant;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwQualityGoal extends VWTDWorkArea implements IVariantListener {
    /**
     * input parameters
     */
//    String entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN; //PmsAccountFun, EbsAccountFun,SepgAccountFun


    /**
     * define common data (globe)
     */
    VwQualityGoalList vwQualityGoalList;
    VwQualityGoalGeneral vwQualityGoalGeneral;
    VwQualityGoalReleaseList vwQualityGoalRelease;
    private boolean refreshFlag = false;
    private Long acntRid;
    /**
     * default constructor
     */
    public VwQualityGoal() {
        super(200);
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
        vwQualityGoalList = new VwQualityGoalList();
        this.getTopArea().addTab("QualityGoal", vwQualityGoalList);

        vwQualityGoalGeneral = new VwQualityGoalGeneral();
        vwQualityGoalGeneral.setParentWorkArea(this.getDownArea());

        vwQualityGoalRelease = new VwQualityGoalReleaseList();
        this.getDownArea().addTab("General", vwQualityGoalGeneral);
        this.getDownArea().addTab("Release", vwQualityGoalRelease);

//        isEmp = true;
    }

    /**
     * ������棺��������¼�
     */
    private void addUICEvent() {
        this.vwQualityGoalList.getTable().addRowSelectedLostListener(new
            RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedData) {
                return processRowSelectedLostAccList(oldSelectedRow,
                    oldSelectedData);
            }
        });
        this.vwQualityGoalList.getTable().addRowSelectedListener(new
            RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedAccList();
            }
        });
        this.vwQualityGoalRelease.getTable().getModel().addTableModelListener(new
            TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                vwQualityGoalGeneral.fireNeedRefresh();
            }
        });
        vwQualityGoalGeneral.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                vwQualityGoalList.getTable().refreshTable();
            }
        });
        ProcessVariant.addDataListener("account", this);

    }

    /////// ��2�����ò�������ȡ�������
    public void setParameter(Parameter param) {

        this.acntRid = (Long) param.get("acntRid");
    }

    public void dataChanged(String varName, Object data) {
        if (varName.equals("account")) {
            Long acntRid = null;
            if (data instanceof String) {
                acntRid = new Long((String) data);
            } else if (data instanceof Long) {
                acntRid = (Long) data;
            }
            if (acntRid != null) {
                if (this.acntRid == null ||
                    acntRid.longValue() != this.acntRid.longValue()) {
                    this.acntRid = acntRid;
                    this.refreshFlag = true;
                    ProcessVariant.set("accountChanged", Boolean.TRUE);
                }
            }
        }

    }

    ///////��3����ȡ���ݲ�ˢ�»���
    //������workArea����Ҫˢ���Լ�
    public void refreshWorkArea() {
        if (refreshFlag) {
            Parameter param = new Parameter();
            param.put("acntRid", this.acntRid);
            vwQualityGoalList.setParameter(param);
            vwQualityGoalList.refreshWorkArea();
            refreshFlag = false;
        }
    }

    /////// ��4���¼�����
    public boolean processRowSelectedLostAccList(int oldSelectedRow,
                                                 Object oldSelectedData) {
        this.getDownArea().getSelectedWorkArea().saveWorkArea();
        return this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public void processRowSelectedAccList() {

        DtoQualityGoal dataBean = (DtoQualityGoal) vwQualityGoalList.
                                  getSelectedData();
        Long acntRid = null;
        if (dataBean != null) {
            acntRid = dataBean.getAcntRid();
        }

        Parameter param = new Parameter();
        param.put(DtoKey.ACNT_RID, acntRid);
        param.put(DtoQualityGoal.DTO_QUALITY_GOAL, dataBean);

        this.vwQualityGoalRelease.setParameter(param);
        vwQualityGoalRelease.refreshWorkArea();
        param.put("qualityGoalReleaseList",
                  vwQualityGoalRelease);
        this.vwQualityGoalGeneral.setParameter(param);
        vwQualityGoalGeneral.refreshWorkArea();
    }


    /////// ��5����������
    public void saveWorkArea() {
        vwQualityGoalList.saveWorkArea();
        this.getDownArea().getSelectedWorkArea().saveWorkArea();
    }

    public static void main(String[] args) {
//
//        Global.todayDateStr = "2005-12-03";
//        Global.todayDatePattern = "yyyy-MM-dd";
//        Global.userId = "stone.shi";
//        DtoUser user = new DtoUser();
//        user.setUserID(Global.userId);
//        user.setUserLoginId(Global.userId);
//        HttpServletRequest request = new HttpServletRequestMock();
//        HttpSession session = request.getSession();
//        session.setAttribute(DtoUser.SESSION_USER, user);
//
//        VWWorkArea workArea = new VwQualityGoal();
//        Parameter param = new Parameter();
//        param.put("acntRid", new Long(6022));
//        workArea.setParameter(param);
//
//        workArea.refreshWorkArea();
//        TestPanel.show(workArea);

    }
}
