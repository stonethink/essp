package client.essp.timesheet.account.labor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;

import c2s.dto.*;
import c2s.essp.timesheet.account.DtoAccount;
import c2s.essp.timesheet.labor.DtoLaborResource;
import c2s.essp.timesheet.labor.level.DtoLaborLevel;
import c2s.essp.timesheet.labor.role.DtoLaborRole;
import client.essp.common.humanAllocate.HrAllocate;
import client.essp.common.view.VWTableWorkArea;
import client.framework.common.Constant;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJPopupEditor;
import client.framework.view.vwcomp.VWJText;

import com.wits.util.Parameter;

/**
 * <p>Title: labor resource list</p>
 *
 * <p>Description: 某项目下所有人力资源列表视图</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwLaborList extends VWTableWorkArea {
    private final static String actionId_ListLabors = "FTSListLabors";
    private final static String actionId_DelLabor = "FTSDelLabor";
    private final static String actionId_SaveLabors = "FTSAddLabors";
    private Long accountRid = null;
    private List labors;
    private Vector levelList;
    private Vector roleList;
    private JButton resetBtn;
//    private JButton addBtn;
    private JButton editBtn;
    private JButton delBtn;
    private JButton searchBtn = new JButton();
    private String userLoginIds = "";

    public VwLaborList() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
    }

    /**
     * 初始化
     * @throws Exception
     */
    private void jbInit() throws Exception {
        Object[][] configs = new Object[][] { {"rsid.common.loginId", "loginId",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"rsid.common.name", "name",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"rsid.common.status", "status",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}, {"rsid.common.remark", "remark",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE},{"rsid.timesheet.laborRole", "role",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE},{"rsid.timesheet.laborLevel", "level",
                             VMColumnConfig.UNEDITABLE, new VWJText(),
                             Boolean.FALSE}
        };
        super.jbInit(configs, DtoLaborResource.class);
//      可排序
        this.getTable().setSortable(true);
    }

    /**
     * 事件处理
     */
    private void addUICEvent() {
    	//search
    	searchBtn = this.getButtonPanel().addButton("humanAllocate.gif");
		searchBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processQuery();
			}
		});
        searchBtn.setToolTipText("rsid.timesheet.allocate");
        
        //Add       //delete by lipengxu at 2008-01-24 for tp request 
//        //addBtn = this.getButtonPanel().addAddButton(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                processAddLabor();
//            }
//        });
//        //addBtn.setToolTipText("rsid.common.add");
        //Edit
        editBtn = this.getButtonPanel().addEditButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processEditLabor();
            }
        });
        editBtn.setToolTipText("rsid.common.edit");
        //Delete
        delBtn = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processDelLabor();
            }
        });
        delBtn.setToolTipText("rsid.common.delete");
        //Load
        resetBtn = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
        resetBtn.setToolTipText("rsid.common.refresh");
    }
    /**
	 * 查询操作
	 *
	 */
	private void processQuery() {
		HrAllocate hrAllocate = new HrAllocate();
        hrAllocate.setTitle("Query Human");
        hrAllocate.allocMultiple();
        userLoginIds = hrAllocate.getNewUserIds();
//        comMSG.dispMessageDialog("Add Person");
//        userLoginIds= "WH0607015,WH0607016";
        if(userLoginIds != null && !"".equals(userLoginIds)){
        	addPerson();
        }
	}
	private void addPerson(){
		InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_SaveLabors);
        inputInfo.setInputObj(DtoAccount.DTO_LOGINIDS, userLoginIds);
        inputInfo.setInputObj(DtoAccount.DTO_RID, accountRid);
        ReturnInfo returnInfo = accessData(inputInfo);
        resetUI();
	}
    /**
     * 删除Labor Resource
     */
    private void processDelLabor() {

        DtoLaborResource dto = (DtoLaborResource) getSelectedData();
        int option = comMSG.dispConfirmDialog(
                "error.client.common.deleteRecord");
        if (option != Constant.OK) {
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_DelLabor);
        inputInfo.setInputObj(DtoLaborResource.DTO, dto);
        this.accessData(inputInfo);
        resetUI();
    }

    /**
     * 新增Labor Resource
     */
    private void processAddLabor() {

        //get new dto
        DtoLaborResource dto = new DtoLaborResource();
        dto.setAccountRid(accountRid);
        dto.setOp(IDto.OP_INSERT);
        //init labor detail Component
        VwLaborDetail detail = new VwLaborDetail();
        Parameter param = new Parameter();
        param.put(DtoLaborResource.DTO, dto);
        param.put(DtoLaborLevel.DTO_LIST, levelList);
        param.put(DtoLaborRole.DTO_LIST, roleList);
        detail.setParameter(param);

        //show
        VWJPopupEditor popup = new VWJPopupEditor(this.getParentWindow(),
                                                  VwLaborDetail.ADD_MODEL_TITLE,
                                                  detail, detail);
        int result = popup.showConfirm();

        //refresh labors table
        if (Constant.OK == result) {
            resetUI();
        }
    }

    /**
     * 修改Labor Resource
     */
    private void processEditLabor() {

        //get current row data
        DtoLaborResource dto = (DtoLaborResource) getSelectedData();
        dto.setAccountRid(accountRid);
        dto.setOp(IDto.OP_MODIFY);
        //init labor detail Component
        VwLaborDetail detail = new VwLaborDetail();
        detail.inputLoginId.setEnabled(false);
        Parameter param = new Parameter();
        param.put(DtoLaborResource.DTO, dto);
        param.put(DtoLaborLevel.DTO_LIST, levelList);
        param.put(DtoLaborRole.DTO_LIST, roleList);
        detail.setParameter(param);

        //show
        VWJPopupEditor popup = new VWJPopupEditor(this.getParentWindow(),
                                                  VwLaborDetail.
                                                  EDIT_MODEL_TITLE, detail,
                                                  detail);
        int result = popup.showConfirm();

        //refresh labors table
        if (Constant.OK == result) {
            resetUI();
        }
    }

    /**
     * 参数设置
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        accountRid = (Long) param.get(DtoAccount.DTO_RID);
    }

    /**
     * 刷新UI
     */
    protected void resetUI() {
        if (accountRid == null) {
            resetBtn.setEnabled(false);
            //addBtn.setEnabled(false);
            editBtn.setEnabled(false);
            delBtn.setEnabled(false);
            searchBtn.setEnabled(false);
            return;
        }
        resetBtn.setEnabled(true);
        //addBtn.setEnabled(true);
        editBtn.setEnabled(true);
        delBtn.setEnabled(true);
        searchBtn.setEnabled(true);

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_ListLabors);
        inputInfo.setInputObj(DtoAccount.DTO_RID, accountRid);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if (returnInfo.isError()) {
            return;
        }
        labors = (List) returnInfo.getReturnObj(DtoLaborResource.DTO_LIST);
        levelList = (Vector) returnInfo.getReturnObj(DtoLaborLevel.DTO_LIST);
        roleList = (Vector) returnInfo.getReturnObj(DtoLaborRole.DTO_LIST);
        this.getModel().setRows(labors);
        if (labors != null && labors.size() > 0) {
            this.getTable().setSelectRow(0);
            editBtn.setEnabled(true);
            delBtn.setEnabled(true);
        } else {
            editBtn.setEnabled(false);
            delBtn.setEnabled(false);
        }
    }


}
