package client.essp.ebs;

import java.awt.AWTEvent;
import java.awt.Dimension;

import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.essp.ebs.DtoEbsEbs;
import c2s.essp.ebs.IDtoEbsAcnt;
import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.IVWAppletParameter;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwEbs extends VWTDWorkArea implements IVWAppletParameter {
    /**
     * input parameters
     */
    String entryFunType = "EbsMaintain"; //EbsMaintain, EbsMaintainHQ, EbsView

    /**
     * define control variable
     */
    private boolean refreshFlag = false;

    /**
     * define common data (globe)
     */
    VwEbsList vwEbsList;
    private VwEbsGroup vwEbsGroup;
    private VwEbsAcnt vwEbsAcnt;


    /**
     * default constructor
     */
    public VwEbs() {
        super(200);
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

        vwEbsList = new VwEbsList();
        this.getTopArea().addTab("Ebs", vwEbsList);

        vwEbsGroup = new VwEbsGroup();
        vwEbsAcnt = new VwEbsAcnt();
        this.getDownArea().addTab("General", vwEbsGroup, true);
    }


    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        this.vwEbsList.getTreeTable().addRowSelectedLostListener(new
            RowSelectedLostListener() {
            public boolean processRowSelectedLost(int oldSelectedRow,
                                                  Object oldSelectedNode) {
                return processRowSelectedLostList(oldSelectedRow,
                                                  oldSelectedNode);
            }

        });

        //RowSelected
        this.vwEbsList.getTreeTable().addRowSelectedListener(new
            RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedEbsList();
            }
        });

        this.vwEbsGroup.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                vwEbsList.getTreeTable().refreshTree();
            }
        });

        this.vwEbsAcnt.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                vwEbsList.getTreeTable().refreshTree();
            }
        });
    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter para) {
        entryFunType = (String) para.get("entryFunType");
        log.debug("entryFunType=" + entryFunType);
        if (entryFunType == null || entryFunType.length() == 0) {
            entryFunType = "EbsMaintain";
        }

        this.refreshFlag = true;
    }

    public String[][] getParameterInfo() {
        String[][] parameterInfo = { {"entryFunType", "", "entryFunType"}};
        return parameterInfo;
    }

    /////// 段3，获取数据并刷新画面
    public void refreshWorkArea() {
        if (refreshFlag == true) {

            Parameter para = new Parameter();
            para.put("entryFunType", this.entryFunType);
            vwEbsList.setParameter(para);
            vwEbsList.refreshWorkArea();

            this.refreshFlag = false;
        }
    }

    /////// 段4，事件处理
    public boolean processRowSelectedLostList(int oldSelectedRow,
                                              Object oldSelectedNode) {
        this.getDownArea().getSelectedWorkArea().saveWorkArea();
        return this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public void processRowSelectedEbsList() {
        Parameter param = new Parameter();
        ITreeNode node = VwEbs.this.vwEbsList.getSelectedNode();
        if (node != null) {
            DtoTreeNode treeNode = (DtoTreeNode) node;
            IDtoEbsAcnt dataBean = (IDtoEbsAcnt) treeNode.getDataBean();
            if (dataBean.isEbs()) {
                if (this.getDownArea().getSelectedWorkArea() != vwEbsGroup) {
                    VwEbs.this.getDownArea().replaceTab(VwEbs.this.
                        getDownArea().getSelectedIndex(), vwEbsGroup, true );
                }

                param.put("entryFunType", this.entryFunType);
                param.put("dto", dataBean);
                vwEbsGroup.setParameter(param);
            } else if (dataBean.isAcnt()) {
                if (this.getDownArea().getSelectedWorkArea() != vwEbsAcnt) {
                    VwEbs.this.getDownArea().replaceTab(VwEbs.this.
                        getDownArea().getSelectedIndex(), vwEbsAcnt, true);
                }

                ITreeNode parentNode = treeNode.getParent();
                Long parentId = null;
                if (parentNode != null) {
                    DtoEbsEbs parentDataBean = (DtoEbsEbs) parentNode.
                                               getDataBean();
                    parentId = parentDataBean.getRid();
                }

                param.put("dto", dataBean);
                param.put("parentId", parentId);
                param.put("entryFunType", this.entryFunType);
                vwEbsAcnt.setParameter(param);
            }
        } else {
            vwEbsGroup.setParameter(param);
            vwEbsAcnt.setParameter(param);
        }

        this.getDownArea().getSelectedWorkArea().refreshWorkArea();
    }


    /////// 段5，保存数据
    public void saveWorkArea() {
        vwEbsList.saveWorkArea();
        if (vwEbsList.isSaveOk() == true) {
            this.getDownArea().getSelectedWorkArea().saveWorkArea();
        }
    }

    public boolean isSaveOk() {
        return vwEbsList.isSaveOk()
            && this.getDownArea().getSelectedWorkArea().isSaveOk();
    }

    public static void main(String[] args) {
        VWWorkArea workArea = new VwEbs();
        Parameter para = new Parameter();
        para.put("entryFunType", "EbsMaintain");
//        para.put("entryFunType","EbsMaintainHQ");
//        para.put("entryFunType", "EbsView");

        workArea.setParameter(para);
        workArea.refreshWorkArea();
        TestPanel.show(workArea);

    }

}
