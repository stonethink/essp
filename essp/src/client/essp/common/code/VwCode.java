package client.essp.common.code;

import java.awt.AWTEvent;

import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.essp.common.code.DtoCode;
import c2s.essp.common.code.DtoKey;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.DataCreateListener;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import client.framework.view.vwcomp.IVWAppletParameter;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.event.StateChangedListener;
import client.essp.common.code.value.VwCodeValueList;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwCode extends VWTDWorkArea
    implements IVWAppletParameter{

    /**
     * define control variable
     */
    private boolean refreshFlag = false;

    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    VwCodeList vwCodeListActivity;
    VwCodeList vwCodeListWbs;
    VwCodeList vwCodeListAcnt;
    VwCodeGeneral vwCodeGeneral;
    VwCodeValueList vwCodeValueList;

    /**
     * default constructor
     */
    public VwCode() {
        super(300);
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
        vwCodeListAcnt = new VwCodeList();
        this.getTopArea().addTab("Account", vwCodeListAcnt);

        vwCodeListActivity = new VwCodeList();
        this.getTopArea().addTab("Activity", vwCodeListActivity);

        vwCodeListWbs = new VwCodeList();
        this.getTopArea().addTab("Wbs", vwCodeListWbs);

        vwCodeGeneral = new VwCodeGeneral();
        this.getDownArea().addTab("General", vwCodeGeneral);

        vwCodeValueList = new VwCodeValueList();
        this.getDownArea().addTab("Code Value", vwCodeValueList);
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.getTopArea().addStateChangedListener(new StateChangedListener(){
            public void processStateChanged(){
                getSelectedTopWorkArea().getTreeTable().fireRowSelected();
            }
        });

        this.vwCodeListAcnt.getTreeTable().addRowSelectedLostListener(new RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow, Object oldSelectedNode){
                return processRowSelectedLostCodeList(oldSelectedRow, oldSelectedNode);
            }
        });

        this.vwCodeListAcnt.getTreeTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedCodeList();
            }
        });


        this.vwCodeListActivity.getTreeTable().addRowSelectedLostListener(new RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow, Object oldSelectedNode){
                return processRowSelectedLostCodeList(oldSelectedRow, oldSelectedNode);
            }
        });

        this.vwCodeListActivity.getTreeTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedCodeList();
            }
        });

        this.vwCodeListWbs.getTreeTable().addRowSelectedLostListener(new RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow, Object oldSelectedNode){
                return processRowSelectedLostCodeList(oldSelectedRow, oldSelectedNode);
            }
        });

        this.vwCodeListWbs.getTreeTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedCodeList();
            }
        });

        this.vwCodeGeneral.addDataChangedListener( new DataChangedListener(){
            public void processDataChanged(){
                getSelectedTopWorkArea().getTreeTable().refreshTree();

                ITreeNode node = VwCode.this.getSelectedTopWorkArea().getSelectedNode();

                if (node != null) {
                    DtoTreeNode treeNode = (DtoTreeNode) node;
                    DtoCode dataBean = (DtoCode) treeNode.getDataBean();
                    if (dataBean.isCode() == true) {
                        vwCodeValueList.refreshRoot(dataBean);
                    }
                }
            }
        });

        this.vwCodeGeneral.addDataCreateListener( new DataCreateListener(){
            public void processDataCreate(){
                 processRowSelectedCodeList();
            }
        });
    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        this.refreshFlag = true;
    }

    public String[][] getParameterInfo() {
        String[][] parameterInfo = {};
        return parameterInfo;
    }

    /////// 段3，获取数据并刷新画面
    public void refreshWorkArea() {
        if (this.refreshFlag == true) {
            Parameter param = new Parameter();
            param.put(DtoKey.TYPE,DtoKey.TYPE_ACTIVITY);
            vwCodeListActivity.setParameter(param);

            Parameter param2 = new Parameter();
            param2.put(DtoKey.TYPE,DtoKey.TYPE_WBS);
            vwCodeListWbs.setParameter(param2);

            Parameter param3 = new Parameter();
            param3.put(DtoKey.TYPE,DtoKey.TYPE_ACNT);
            vwCodeListAcnt.setParameter(param3);

            getSelectedTopWorkArea().refreshWorkArea();
            this.refreshFlag = false;
        }
    }

    /////// 段4，事件处理
    public boolean processRowSelectedLostCodeList(int oldSelectedRow, Object oldSelectedNode){
        this.getDownArea().getSelectedWorkArea().saveWorkArea();
        return this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public void processRowSelectedCodeList() {
        ITreeNode node = getSelectedTopWorkArea().getSelectedNode();

        if (node != null) {
            DtoTreeNode treeNode = (DtoTreeNode) node;
            DtoCode dataBean = (DtoCode) treeNode.getDataBean();
            if( dataBean.isCode() == true ){

                Parameter param = new Parameter();
                param.put(DtoKey.DTO, dataBean);
                vwCodeGeneral.setParameter(param);

                Parameter param2 = new Parameter();
                param2.put(DtoKey.CODE_OBJ, dataBean);
                vwCodeValueList.setParameter(param2);
            }else{
                vwCodeGeneral.setParameter(new Parameter());
                vwCodeValueList.setParameter(new Parameter());
            }
        }else{
            vwCodeGeneral.setParameter(new Parameter());
            vwCodeValueList.setParameter(new Parameter());
        }
        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        getSelectedTopWorkArea().saveWorkArea();
        if( getSelectedTopWorkArea().isSaveOk() == true ){
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public boolean isSaveOk(){
        return getSelectedTopWorkArea().isSaveOk()
            && this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    private VwCodeList getSelectedTopWorkArea() {
        int i = this.getTopArea().getSelectedIndex();
        switch (i) {
        case 0:
            return vwCodeListAcnt;
        case 1:
            return vwCodeListActivity;
        case 2:
            return vwCodeListWbs;
        default:
            throw new RuntimeException("No top work area selected.");
        }
    }

    public static void main(String[] args) {
        VWWorkArea workArea = new VwCode();
        Parameter param = new Parameter();
        workArea.setParameter(param);

        TestPanel.show(workArea);
        workArea.refreshWorkArea();
    }

}
