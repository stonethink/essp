package client.essp.timesheet.account.labor;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import c2s.dto.DtoComboItem;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.labor.DtoLaborResource;
import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.loginId.VWJLoginId;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.*;
import com.wits.util.Parameter;
import c2s.essp.timesheet.labor.level.DtoLaborLevel;
import c2s.essp.timesheet.labor.role.DtoLaborRole;

/**
 * <p>Title: labor resource detail</p>
 *
 * <p>Description: 人力资源明细视图，用于新增，编辑人力资源信息</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwLaborDetail extends VWGeneralWorkArea
        implements IVWPopupEditorEvent {

    public final static String ADD_MODEL_TITLE = "Add Labor Resource";
    public final static String EDIT_MODEL_TITLE = "Edit Labor Resource";

    private final static String actionId_SaveLabor = "FTSSaveLaborResource";
    private final static String actionId_EditLabor = "FTSEditLaborResource";
    private final static String actionId_FindManager = "FTSFindManager";
    private DtoLaborResource dtoLabor = null;
    VWJLabel lblLoginId = new VWJLabel();
    VWJLabel lblStatus = new VWJLabel();
    VWJLabel lblRm = new VWJLabel();
    VWJLabel lblRemark = new VWJLabel();
    VWJLabel lblLevel = new VWJLabel();
    VWJLabel lblRole = new VWJLabel();
    //输入控件
    VWJHrAllocateButton inputLoginId = new VWJHrAllocateButton();
    VWJComboBox inputLevel = new VWJComboBox();
    VWJComboBox inputRole = new VWJComboBox();
    VWJComboBox inputResStatus = new VWJComboBox();
    VWJTextArea inputRemark = new VWJTextArea();
    VWJLoginId inputRm = new VWJLoginId();


    public VwLaborDetail() {
        try {
            jbInit();
            setUIComponentName();
            setTabOrder();
            setEnterOrder();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        addUICEvent();
    }
    /**
     * 给找人控件添加事件
     */
    private void addUICEvent(){
        inputLoginId.getTextComp().addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                processDataChange();
            }
        });
    }
    /**
     * 找人控件值发生变化时触发的事件
     * 查询选择人员的直属主管并显示到RM栏位
     */
    private void processDataChange(){
        if(inputLoginId.getUICValue() == null
                ||"".equals(inputLoginId.getUICValue())){return;}
        VWUtil.clearUI(inputRm);
        String rmId = this.getRMLoginId();
        if(rmId != null && !"".equals(rmId)) {
        	inputRm.setUICValue(rmId);
        }
    }
    /**
     * 初始化UI
     * @throws Exception
     */
    public void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(700, 300));
        this.setLayout(null);
        lblLoginId.setText("rsid.common.loginId");
        lblLoginId.setBounds(new Rectangle(50, 63, 80, 30));

        lblStatus.setText("rsid.common.status");
        lblStatus.setBounds(new Rectangle(50, 136, 80, 30));

        lblRm.setText("rsid.timesheet.resourceManager");
        lblRm.setBounds(new Rectangle(362, 63, 120, 30));
        
        lblRole.setText("rsid.timesheet.laborRole");
        lblRole.setBounds(new Rectangle(50, 100, 80, 30));
        lblLevel.setText("rsid.timesheet.laborLevel");
        lblLevel.setBounds(new Rectangle(362, 100, 80, 30));

        lblRemark.setText("rsid.common.remark");
        lblRemark.setBounds(new Rectangle(50, 173, 108, 30));
        //初始控件
        inputRemark.setBounds(new Rectangle(146, 180, 528, 40));
        inputResStatus.setBounds(new Rectangle(146, 141, 180, 25));
        Vector itemVector = new Vector();
        DtoComboItem item = new DtoComboItem("In", "In");
        itemVector.add(item);
        item = new DtoComboItem("Out", "Out");
        itemVector.add(item);
        VMComboBox resStatusItem = new VMComboBox(itemVector);
        inputResStatus.setModel(resStatusItem);
        inputRm.setBounds(new Rectangle(494, 68, 180, 25));
        inputLoginId.setBounds(new Rectangle(146, 68, 180, 25));
        inputRole.setBounds(new Rectangle(146, 105, 180, 25));
        inputLevel.setBounds(new Rectangle(494, 105, 180, 25));
        

        inputRm.setEnabled(false);
        this.add(lblLoginId);
        this.add(lblRm);
        this.add(inputLoginId);
        this.add(inputRm);
        this.add(lblRole);
        this.add(lblRemark);
        this.add(lblStatus);
        this.add(lblLevel);
        this.add(inputLevel);
        this.add(inputRole);
        this.add(inputResStatus);
        this.add(inputRemark);
    }
    private String getRMLoginId(){
        String loginId = "";
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_FindManager);
        inputInfo.setInputObj("LoginId", inputLoginId.getUICValue());
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(returnInfo.isError() == false) {
            loginId = (String)returnInfo.getReturnObj("RMLoginId");
        }
        return loginId;
    }
    /**
     * 初试化控件内部名称
     */
    private void setUIComponentName() {
        inputLoginId.setName("loginId");
        inputLevel.setName("levelRid");
        inputRole.setName("roleRid");
        inputRm.setName("rm");
        inputResStatus.setName("status");
        inputRemark.setName("remark");
    }
    /**
     * 初始化Tab顺序
     */
    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(inputLoginId.getTextComp());
        compList.add(inputLevel);
        compList.add(inputRole);
        compList.add(inputResStatus);
        compList.add(inputRemark);
        comFORM.setTABOrder(this, compList);
    }
    /**
     * 设置输入顺序
     */
    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(inputLoginId.getTextComp());
        compList.add(inputLevel);
        compList.add(inputRole);
        compList.add(inputResStatus);
        compList.add(inputRemark);
        comFORM.setEnterOrder(this, compList);
    }

    /**
     * 传入参数，激活刷新
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        dtoLabor = (DtoLaborResource) param.get(DtoLaborResource.DTO);
        Vector levelList = (Vector) param.get(DtoLaborLevel.DTO_LIST);
        Vector roleList = (Vector) param.get(DtoLaborRole.DTO_LIST);
        VMComboBox levelItems = new VMComboBox(levelList);
        VMComboBox roleItems = new VMComboBox(roleList);
        inputLevel.setModel(levelItems);
        inputRole.setModel(roleItems);
        resetUI();
        processDataChange();
    }

    /**
     * 刷新UI
     */
    public void resetUI() {
        if(dtoLabor == null || dtoLabor.getRid() == null) return;
        if(dtoLabor.isInsert()) {
//            lblTitle.setText(ADD_MODEL_TITLE);
        } else {
            VWUtil.clearUI(this);
//            lblTitle.setText(EDIT_MODEL_TITLE);
        }
        VWUtil.bindDto2UI(dtoLabor, this);
    }
    private boolean checkError(){
        boolean isError = false;
        if(inputLoginId.getUICValue() == null ||
           "".equals(inputLoginId.getUICValue())){
            comMSG.dispErrorDialog("error.client.VwLaborDetail.inputLoginId");
            return true;
        }
         
        if(inputRm.getUICValue() != null) {
        	String rmId = (String)inputRm.getUICValue();
        	if("".equals(rmId)) {
        		comMSG.dispErrorDialog("error.client.VwLaborDetail.inputRmId");
        		return true;
        	}
        } else {
        	comMSG.dispErrorDialog("error.client.VwLaborDetail.inputRmId");
        	return true;
        }
        if(inputLevel.getUICValue() == null) {
        	comMSG.dispErrorDialog("error.client.VwLaborDetail.inputLevel");
        	return true;
        }
        if(inputRole.getUICValue() == null) {
        	comMSG.dispErrorDialog("error.client.VwLaborDetail.inputRole");
        	return true;
        }
        return isError;
    }
    /**
     * 点击OK事件
     * @param e ActionEvent
     * @return boolean
     */
    public boolean onClickOK(ActionEvent e) {
        if(checkError()){
            return false;
        }
        VWUtil.convertUI2Dto(dtoLabor, this);
        dtoLabor.setName(inputLoginId.getTextComp().getText());
        InputInfo inputInfo = new InputInfo();
        if(dtoLabor.isInsert()){
             inputInfo.setActionId(actionId_SaveLabor);
        } else {
            inputInfo.setActionId(actionId_EditLabor);
        }
        inputInfo.setInputObj(DtoLaborResource.DTO, dtoLabor);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(returnInfo.isError()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 点击Cancel事件
     * @param e ActionEvent
     * @return boolean
     */
    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

}
