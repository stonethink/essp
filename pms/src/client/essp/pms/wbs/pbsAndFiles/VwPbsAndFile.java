package client.essp.pms.wbs.pbsAndFiles;

import java.awt.AWTEvent;
import java.awt.Dimension;

import c2s.essp.pms.pbs.pbsAndFiles.DtoPbsAssign;
import c2s.essp.pms.wbs.DtoWbsActivity;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.view.event.RowSelectedListener;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.event.RowSelectedLostListener;
import c2s.dto.ITreeNode;
import c2s.essp.pms.wbs.DtoKEY;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwPbsAndFile extends VWTDWorkArea {

    /**
     * define control variable
     */
    private boolean refreshFlag = false;

    /**
     * define common data (globe)
     */
    Long acntRid;
    Long joinType;
    Long joinRid;
    boolean isReadonly;

    /////// ��1��������棺������棨����ؼ������ÿؼ����ƣ�������˳�򣩡���������¼�
    VwPbsList vwPbsList;
    VwFileList vwFileList;

    /**
     * default constructor
     */
    public VwPbsAndFile() {
        super(250);
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

        vwPbsList = new VwPbsList();
        vwFileList = new VwFileList();

        this.getTopArea().addTab("Pbs", vwPbsList);
        this.getDownArea().addTab("Files", vwFileList);
    }


    /**
     * ������棺��������¼�
     */
    private void addUICEvent() {
        this.vwPbsList.getTable().addRowSelectedLostListener(new RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow, Object oldSelectedData) {
                return processRowSelectedLostPbsList(oldSelectedRow, oldSelectedData);
            }
        });

        this.vwPbsList.getTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedPbsList();
            }
        });
    }

    /////// ��2�����ò�������ȡ�������
    public void setParameter(Parameter param) {
        ITreeNode treeNode = (ITreeNode) param.get(DtoKEY.WBSTREE);
        DtoWbsActivity dtoActivity = (DtoWbsActivity) treeNode.getDataBean();
        boolean isParameterValid = true;

        if (dtoActivity == null) {
            this.acntRid = null;
            this.joinRid = null;
            this.joinType = null;
            isReadonly = true;
            isParameterValid = false;
        }else{
            acntRid = dtoActivity.getAcntRid();
            isReadonly = dtoActivity.isReadonly();
            if( dtoActivity.isActivity() ){
                joinRid = dtoActivity.getActivityRid();
                joinType = DtoPbsAssign.JOINTYPEACT;
            }else{
                joinRid = dtoActivity.getWbsRid();
                joinType = DtoPbsAssign.JOINTYPEWBS;
            }

            isParameterValid = true;
        }

        Parameter param2 = new Parameter();
        if (isParameterValid == true) {
            param2.put("acntRid", this.acntRid);
            param2.put("joinType", this.joinType);
            param2.put("joinRid", this.joinRid);
            param2.put("isReadonly", new Boolean(this.isReadonly));
        }
        vwPbsList.setParameter(param2);

        this.refreshFlag = true;
    }

    /////// ��3����ȡ���ݲ�ˢ�»���
    //������workArea����Ҫˢ���Լ�
    public void refreshWorkArea() {
        if (this.refreshFlag == true) {

            vwPbsList.refreshWorkArea();
            this.refreshFlag = false;
        }
    }

    /////// ��4���¼�����
    public boolean processRowSelectedLostPbsList(int oldSelectedRow, Object oldSelectedData) {
        this.getDownArea().getSelectedWorkArea().saveWorkArea();
        return this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public void processRowSelectedPbsList() {
        DtoPbsAssign dataBean = (DtoPbsAssign)this.vwPbsList.getSelectedData();
        Parameter param = new Parameter();
        if( dataBean != null ){
            param.put("acntRid", dataBean.getAcntRid());
            param.put("pbsRid", dataBean.getPbsRid());
            param.put("isReadonly", new Boolean(isReadonly));
        }
        vwFileList.setParameter(param);

        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }

    /////// ��5����������
    public void saveWorkArea() {
        vwPbsList.saveWorkArea();
        if( vwPbsList.isSaveOk() == true ){
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public static void main(String[] args) {
        VWWorkArea workArea = new VwPbsAndFile();
        Parameter param = new Parameter();
        DtoWbsActivity wbs = new DtoWbsActivity();
        wbs.setAcntRid(new Long(7));
        wbs.setWbs(false);
        wbs.setActivityRid(new Long(1));
        param.put("wbs", wbs);

        workArea.setParameter(param);

        VWWorkArea workArea2 = new VWWorkArea();
        workArea2.addTab("Pbs", workArea);

        TestPanel.show(workArea2);
        workArea.refreshWorkArea();
    }

}
