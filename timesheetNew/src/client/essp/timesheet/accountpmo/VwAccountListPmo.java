package client.essp.timesheet.accountpmo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.account.DtoAccount;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.essp.common.loginId.VWJLoginId;
import client.essp.common.view.VWTableWorkArea;
import client.essp.timesheet.account.common.VWJIconAccountTableRender;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;

public class VwAccountListPmo extends VWTableWorkArea {
	
	private final static String actionId_queryAccounts = "FTSQueryAccounts";

    private List accountList;
    private DtoAccount dto;
    
    private JButton refreshBtn = new JButton();

    public VwAccountListPmo() {
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
                             Boolean.FALSE},
                             {"rsid.timesheet.actAggregateHours", "actAggregateHours",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE},
                            {"rsid.timesheet.closed", "statusName",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.TRUE},
                            {"rsid.timesheet.jobCodeType", "codeType",
                            VMColumnConfig.UNEDITABLE, new VWJText(),
                            Boolean.FALSE}


        };
        super.jbInit(configs, DtoAccount.class);
//      可排序
        this.getTable().setSortable(true);
        //设置第一列显示图标
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
    	if(dto == null) {
    		return;
    	}
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_queryAccounts);
        inputInfo.setInputObj(DtoAccount.DTO_CONDITION, dto);
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
        dto = (DtoAccount) param.get(DtoAccount.DTO_CONDITION);
    }

	

	

}
