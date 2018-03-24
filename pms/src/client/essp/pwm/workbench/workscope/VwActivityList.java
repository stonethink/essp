package client.essp.pwm.workbench.workscope;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import c2s.dto.ITreeNode;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.wbs.DtoKEY;
import c2s.essp.pwm.wp.DtoWSActivity;
import client.essp.common.view.VWTableWorkArea;
import client.essp.pms.activity.VwActivityArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;

public class VwActivityList extends VWTableWorkArea
       implements  IVWPopupEditorEvent{
    static final String actionAcnt = "FPWGetActivityArea";
    private List activityList;

    /**
     * default constructor
     */
    public VwActivityList() {
        Object[][] configs = new Object[][] { {"Activity",
                             "scopeInfo", VMColumnConfig.UNEDITABLE, new VWJText()}
        };
        try {
            super.jbInit(configs, DtoWSActivity.class);
            getTable().getTableHeader().setPreferredSize(new Dimension(100, 0)); //不显示表头
        } catch (Exception ex) {
            log.error(ex);
        }

        //setPreferredSize(new Dimension(200, 10));

        //拖放事件
        (new ScopeDragSource(getTable())).create();

        getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if( e.getClickCount() == 2 ){
                    mouseClickedActivity();
                }
            }
        });

    }

    public void mouseClickedActivity(){
        DtoWSActivity dtoWSActivity = (DtoWSActivity)this.getSelectedData();
        if (dtoWSActivity == null) {
            return;
        }
        Parameter param = new  Parameter();
        VwActivityArea activity = new  VwActivityArea();
        ITreeNode node = null;
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionAcnt);
        inputInfo.setInputObj(DtoAcntKEY.ACNT_RID, dtoWSActivity.getAcntRid());
        inputInfo.setInputObj("Activity", dtoWSActivity.getActivityRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if(returnInfo.isError() == false ){
            node = (ITreeNode)returnInfo.getReturnObj(DtoKEY.WBSTREE);
        }
        param.put("ActivityTree", node);
        activity.setParameter(param);
        activity.refreshWorkArea();
        VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(),
            "Activity"
            , activity, this);
        popupEditor.show();

    }

    public boolean onClickOK(ActionEvent e) {
        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }


    /////// 段3，获取数据并刷新画面
    protected void resetUI() {
        getTable().setRows(activityList);
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        activityList = (List)param.get("activityList");
        if( activityList == null ){
            activityList = new ArrayList();
        }
    }

}
