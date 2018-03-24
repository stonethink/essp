package client.essp.pwm.workbench.workscope;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pwm.wp.DtoWSAccount;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.TestPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.wits.util.Parameter;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.essp.pms.account.DtoPmsAcnt;
import client.essp.pms.account.VwAcnt;

public class VwAccountList extends VWTableWorkArea
    implements  IVWPopupEditorEvent{
    static final String actionId = "FPWGetWorkScope";
    static final String actionAcnt = "FPWGetAccount";

    private List scopeList;

    /**
     * default constructor
     */
    public VwAccountList() {
        Object[][] configs = new Object[][] { {"Account",
                             "scopeInfo", VMColumnConfig.UNEDITABLE, new VWJText()}
        };
        try {
            super.jbInit(configs, DtoWSAccount.class);
            getTable().getTableHeader().setPreferredSize(new Dimension(100, 0)); //不显示表头
            addUICEvent();
        } catch (Exception ex) {
            log.error(ex);
        }

        setMinimumSize(new Dimension(200, 77));
        setPreferredSize(new Dimension(200, 77));

    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {
        //Refresh
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedLoad(e);
            }
        });

        //拖放事件
        (new ScopeDragSource(getTable())).create();

        getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if( e.getClickCount() == 2 ){
                    mouseClickedAccount();
                }
            }
        });

    }

    public void mouseClickedAccount(){
        DtoWSAccount dtoWSAccount = (DtoWSAccount)this.getSelectedData();
        if (dtoWSAccount == null) {
            return;
        }
        DtoPmsAcnt dtoPms = null;
        VwAcnt account = new  VwAcnt();
        Parameter param = new  Parameter();
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionAcnt);
        inputInfo.setInputObj(DtoAcntKEY.ACNT_RID, dtoWSAccount.getAcntRid());
        ReturnInfo returnInfo = accessData(inputInfo);
        if(returnInfo.isError() == false ){
            dtoPms = (DtoPmsAcnt)returnInfo.getReturnObj("DtoAccount");
        }
        param.put("dtoAccount", dtoPms);
        account.setParameter(param);
        account.refreshWorkArea();
        VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(),
            "Account"
            , account, this);
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
        InputInfo inputInfo = new InputInfo();

        inputInfo.setActionId(this.actionId);
        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            scopeList = (List) returnInfo.getReturnObj("scopeList");
            getTable().setRows(scopeList);

            fireDataChanged();
        }
    }

    public List getScopeList(){
        return this.scopeList;
    }

    /////// 段4，事件处理
    public void actionPerformedLoad(ActionEvent e) {
        resetUI();
    }

    public static void main(String args[]) {
        VwAccountList w = new VwAccountList();
        w.setParameter(null);
        w.refreshWorkArea();

        VWWorkArea w2 = new VWWorkArea();
        w2.addTab("Account", w);
        TestPanel.show(w2);
    }
}
