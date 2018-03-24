package client.essp.timesheet.workscope;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.workscope.DtoAccount;
import client.essp.common.view.VWTableWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import c2s.dto.DtoBase;
import client.essp.timesheet.account.common.VWJIconAccountTableRender;
/**
 * <p>Title:VwAccountList </p>
 *
 * <p>Description:项目列表卡片 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public class VwAccountList extends VWTableWorkArea {

    private final static String actionId_GetProjectList = "FTSAccountList";
    private List projectList;
    private JButton btnRefresh;
    private String scopeModel;
    private String loginId;
    
    public VwAccountList() {
        //Table与List(Tree)数据绑定配置
        Object[][] configs = new Object[][] { {"rsid.common.name", "showName",
                             VMColumnConfig.UNEDITABLE, null}};
        try {
            super.jbInit(configs, DtoBase.class);
            //不显示表头
            getTable().getTableHeader().setPreferredSize(new Dimension(100, 0));
            //设置第一列显示图标
            table.setDefaultRenderer(Object.class, new VWJIconAccountTableRender());
            table.setRenderAndEditor();

            addUICEvent();
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    /**
     * 定义界面事件
     */
    private void addUICEvent() {
        //刷新
        btnRefresh = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
        btnRefresh.setToolTipText("rsid.common.refresh");
    }

    /**
     * 设置参数
     * @param p Parameter
     */
    public void setParameter(Parameter p) {
        super.setParameter(p);
        scopeModel = (String) p.get(DtoAccount.SCOPE_MODEL);
        loginId = (String) p.get(DtoAccount.SCOPE_LOGIN_ID);
    }

    /**
     * 重置UI
     */
    protected void resetUI() {
    	if(DtoAccount.SCOPE_MODEL_PARAM.equals(scopeModel)) {
    		if(loginId == null || "".equals(loginId) ) {
    			getTable().setRows(new ArrayList());
    			return;
    		}
    	}
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionId_GetProjectList);
        inputInfo.setInputObj(DtoAccount.SCOPE_LOGIN_ID, loginId);
        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            projectList = (List) returnInfo.
                          getReturnObj(DtoAccount.KEY_PROJECT_LIST);
            getTable().setRows(projectList);
        }
        if(projectList != null && projectList.size()>0){
            getTable().setSelectRow(0);
        }
    }

}
