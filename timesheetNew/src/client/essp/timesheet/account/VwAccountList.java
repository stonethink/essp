package client.essp.timesheet.account;

import client.essp.common.loginId.VWJLoginId;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import c2s.essp.timesheet.account.DtoAccount;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import java.util.List;
import com.wits.util.Parameter;
import client.essp.timesheet.account.common.VWJIconAccountTableRender;

/**
 * <p>Title: project list</p>
 *
 * <p>Description: 项目列表视图， 显示当前用户为PM的状态正常的项目</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwAccountList extends VWTableWorkArea {

    private final static String actionId_listProjects = "FTSListAccounts";

    private List accountList;
    
    private JButton refreshBtn = new JButton();

    public VwAccountList() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
    }

    private void jbInit() throws Exception {
        Object[][] configs = new Object[][] { {"rsid.common.code", "accountId",
                             VMColumnConfig.UNEDITABLE, null,
                             Boolean.FALSE}, {"rsid.common.name", "accountName",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"rsid.common.manager", "manager",
                             VMColumnConfig.UNEDITABLE, new VWJLoginId(),
                             Boolean.FALSE}, {"rsid.timesheet.organization", "orgCode",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.TRUE}, {"rsid.timesheet.achieveBelong", "achieveBelong",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.TRUE}, {"rsid.timesheet.plannedStart", "plannedStart",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.FALSE}, {"rsid.timesheet.plannedFinish", "plannedFinish",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.FALSE}, {"rsid.timesheet.actualStart", "actualStart",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.TRUE}, {"rsid.timesheet.actualFinish", "actualFinish",
                             VMColumnConfig.UNEDITABLE, new VWJDate(),
                             Boolean.TRUE}, {"rsid.common.type", "accountType",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"rsid.timesheet.estWorkhours", "estWorkHours",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"rsid.timesheet.actAggregateHours", "actAggregateHours",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE},{"rsid.timesheet.closed", "statusName",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.TRUE}, {"rsid.timesheet.jobCodeType", "codeType",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}


        };
        super.jbInit(configs, DtoAccount.class);
//      可排序
        this.getTable().setSortable(true);
        table.setDefaultRenderer(Object.class, new VWJIconAccountTableRender());
        table.setRenderAndEditor();
    }

    /**
     * 事件处理
     */
    private void addUICEvent() {

        //Display
        TableColumnChooseDisplay chooseDisplay =
                new TableColumnChooseDisplay(this.getTable(), this);
        JButton button = chooseDisplay.getDisplayButton();
        this.getButtonPanel().addButton(button);
       

        //Load
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
        refreshBtn.setToolTipText("rsid.common.refresh");

    }

    /**
     * 刷新界面
     */
    protected void resetUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_listProjects);
        inputInfo.setInputObj(DtoAccount.DTO_PMO, false);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(returnInfo.isError()) return;
        accountList = (List) returnInfo.getReturnObj(DtoAccount.DTO_LIST);
        this.getModel().setRows(accountList);
        if(accountList != null && accountList.size() > 0) {
            this.getTable().setSelectRow(0);
        }
    }

    /**
     * 激活刷新
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
    }


}
