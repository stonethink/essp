package client.essp.pms.pbs;

import java.awt.AWTEvent;

import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.essp.pms.pbs.DtoPmsPbs;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import client.framework.view.vwcomp.IVWAppletParameter;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.event.DataCreateListener;
import com.wits.util.ProcessVariant;
import com.wits.util.IVariantListener;
import c2s.essp.pms.account.DtoAcntKEY;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwPmsPbs extends VWTDWorkArea
    implements IVWAppletParameter, IVariantListener{

    /**
     * define control variable
     */
    private boolean refreshFlag = false;

    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    VwPmsPbsList vwPmsPbsList;
    VwPbsGeneral vwPbsGeneral;
    VwPmsPbsFilesList vwPmsPbsFilesList;
    VwPmsPbsAssignmentList vwPmsPbsAssignmentList;
    private String entryFunType;

    /**
     * default constructor
     */
    public VwPmsPbs() {
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
        vwPmsPbsList = new VwPmsPbsList();
        this.getTopArea().addTab("Pbs", vwPmsPbsList);

        vwPbsGeneral = new VwPbsGeneral();
        this.getDownArea().addTab("General", vwPbsGeneral, true);
        //vwPbsGeneral.setBorder( BorderFactory.createEmptyBorder());

        vwPmsPbsFilesList = new VwPmsPbsFilesList();
        this.getDownArea().addTab("Files", vwPmsPbsFilesList);

        vwPmsPbsAssignmentList = new VwPmsPbsAssignmentList();
        this.getDownArea().addTab("Assignment", vwPmsPbsAssignmentList);
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.vwPmsPbsList.getTreeTable().addRowSelectedLostListener(new RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow, Object oldSelectedNode){
                return processRowSelectedLostPbsList(oldSelectedRow, oldSelectedNode);
            }
        });

        this.vwPmsPbsList.getTreeTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedPbsList();
            }
        });

        this.vwPbsGeneral.addDataChangedListener( new DataChangedListener(){
            public void processDataChanged(){
                vwPmsPbsList.getTreeTable().refreshTree();
            }
        });

        this.vwPbsGeneral.addDataCreateListener( new DataCreateListener(){
            public void processDataCreate(){
                 processRowSelectedPbsList();
            }
        });

        ProcessVariant.addDataListener("account", this);
    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        //mod by xr 2006/06/26
        //增加界面菜单的参数,SEPG可任意修改,其他菜单保持不变
        entryFunType = (String) param.get("entryFunType");
        if (entryFunType == null || entryFunType.length() == 0) {
            entryFunType = DtoAcntKEY.PMS_ACCOUNT_FUN;
        }

        this.refreshFlag = true;
    }

    public String[][] getParameterInfo() {
        String[][] parameterInfo = { {"inAcntRid", "", "inAcntRid"}
                                   ,{"entryFunType", "", "entryFunType"}
        };
        return parameterInfo;
    }

    /////// 段3，获取数据并刷新画面
    //最外层的workArea不需要刷新自己
    public void refreshWorkArea() {
        if (this.refreshFlag == true) {
            Parameter param = new Parameter();
            param.put("entryFunType",entryFunType);
            vwPmsPbsList.setParameter(param);
            vwPmsPbsList.refreshWorkArea();

            this.refreshFlag = false;
        }
    }

    /////// 段4，事件处理
    public boolean processRowSelectedLostPbsList(int oldSelectedRow, Object oldSelectedNode){
        this.getDownArea().getSelectedWorkArea().saveWorkArea();
        return this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public void processRowSelectedPbsList() {
        ITreeNode node = VwPmsPbs.this.vwPmsPbsList.getSelectedNode();

        if (node != null) {
            DtoTreeNode treeNode = (DtoTreeNode) node;
            DtoPmsPbs dataBean = (DtoPmsPbs) treeNode.getDataBean();
            Boolean isReadonly ;
            if( dataBean.isReadonly() ){
                isReadonly = Boolean.TRUE;
            }else{
                isReadonly = Boolean.FALSE;
            }

            Parameter param = new Parameter();
            param.put("dto", dataBean);
            vwPbsGeneral.setParameter(param);

            Parameter param2 = new Parameter();
            param2.put("acntRid", dataBean.getAcntRid());
            param2.put("pbsRid", dataBean.getPbsRid());
            param2.put("isReadonly", isReadonly);
            vwPmsPbsFilesList.setParameter(param2);

            Parameter param3 = new Parameter();
            param3.put("acntRid", dataBean.getAcntRid());
            param3.put("pbsRid", dataBean.getPbsRid());
            param3.put("isReadonly", isReadonly);
            vwPmsPbsAssignmentList.setParameter(param3);
        }else{
            vwPbsGeneral.setParameter(new Parameter());
            vwPmsPbsFilesList.setParameter(new Parameter());
            vwPmsPbsAssignmentList.setParameter(new Parameter());
        }

        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }

    public void dataChanged(String name, Object value) {
        if (name.equals("account")) {
             this.refreshFlag = true;
        }
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        vwPmsPbsList.saveWorkArea();
        if( vwPmsPbsList.isSaveOk() == true ){
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public boolean isSaveOk(){
        return vwPmsPbsList.isSaveOk()
            && this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public static void main(String[] args) {
        VWWorkArea workArea = new VwPmsPbs();
        Parameter param = new Parameter();
        param.put("entryFunType", "PM");
        workArea.setParameter(param);

        TestPanel.show(workArea);
        workArea.refreshWorkArea();
    }

}
