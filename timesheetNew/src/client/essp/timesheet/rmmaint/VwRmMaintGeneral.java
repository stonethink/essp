package client.essp.timesheet.rmmaint;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.wits.util.Parameter;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.rmmaint.DtoRmMaint;
import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJText;
import client.framework.view.vwcomp.VWUtil;
/**
 * 
 * @author wenhaizheng
 *
 */
public class VwRmMaintGeneral extends VWGeneralWorkArea {
	private VWJLabel lblLoginId = new VWJLabel();
	private VWJText inputLoginId = new VWJText();
	private VWJLabel lblName = new VWJLabel();
	private VWJText inputName = new VWJText();
	private VWJLabel lblDept = new VWJLabel();
	private VWJText inputDept = new VWJText();
	private VWJLabel lblRmId = new VWJLabel();
	private VWJHrAllocateButton inputRmId = new VWJHrAllocateButton();
	private JButton saveBtn = new JButton();
//	private JButton delBtn = new JButton();
	private DtoRmMaint dtoRmMaint;
	private final static String actionId_GetRM = "FTSGetRM";
	private final static String actionId_SaveRM = "FTSSaveRM";
	
	
	public VwRmMaintGeneral() {
		jbInit();
		addUICEvent();
	}
	private void jbInit() {
		this.setLayout(null);
		lblLoginId.setText("rsid.timesheet.empId");
		lblLoginId.setBounds(new Rectangle(30,20,80,20));
		inputLoginId.setBounds(new Rectangle(130,20,120,20));
		inputLoginId.setEnabled(false);
		
		lblName.setText("rsid.timesheet.empName");
		lblName.setBounds(new Rectangle(280,20,100,20));
		inputName.setBounds(new Rectangle(380,20,155,20));
		inputName.setEnabled(false);
		
		lblDept.setText("rsid.timesheet.dept");
		lblDept.setBounds(new Rectangle(30,70,80,20));
		inputDept.setBounds(new Rectangle(130,70,120,20));
		inputDept.setEnabled(false);
		
		lblRmId.setText("rsid.timesheet.rmId");
		lblRmId.setBounds(new Rectangle(280,70,80,20));
		inputRmId.setBounds(new Rectangle(380,70,155,20));
		inputRmId.setEnabled(false);
		
		this.add(lblLoginId);
		this.add(inputLoginId);
		this.add(lblName);
		this.add(inputName);
		this.add(lblDept);
		this.add(inputDept);
		this.add(lblRmId);
		this.add(inputRmId);
		
		
	}
	private void addUICEvent(){
		saveBtn = this.getButtonPanel().addSaveButton(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processSave();
			}
		});
		saveBtn.setEnabled(false);
        saveBtn.setToolTipText("rsid.common.save");
//		delBtn = this.getButtonPanel().addButton("del.png");
//		delBtn.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				processDel();
//			}
//		});
	}
	private void processSave() {
		if(inputRmId.getUICValue() == null) {
			comMSG.dispErrorDialog("error.client.VwRmMaint.inputRmId");
			return;
		}
		InputInfo inputInfo = new InputInfo();
		inputInfo.setActionId(actionId_SaveRM);
		inputInfo.setInputObj(DtoRmMaint.DTO_LOGINID, 
				              inputLoginId.getUICValue());
		inputInfo.setInputObj(DtoRmMaint.DTO_RMID, inputRmId.getUICValue());
		ReturnInfo returnInfo = accessData(inputInfo);
		if(!returnInfo.isError()) {
			comMSG.dispMessageDialog("rsid.common.saveComplete");
        }
	}
	
	public void setParameter(Parameter para) {
		super.setParameter(para);
		dtoRmMaint = (DtoRmMaint) para.get(DtoRmMaint.DTO);
	}
	/**
	 *  Ë¢ÐÂ½çÃæ
	 */
	protected void resetUI() {
		if(dtoRmMaint != null) {
			inputLoginId.setUICValue(dtoRmMaint.getLoginId());
			inputName.setUICValue(dtoRmMaint.getName());
			inputDept.setUICValue(dtoRmMaint.getDept());
			InputInfo inputInfo = new InputInfo();
	        inputInfo.setActionId(actionId_GetRM);
	        inputInfo.setInputObj(DtoRmMaint.DTO_LOGINID, dtoRmMaint.getLoginId());
	        ReturnInfo returnInfo = accessData(inputInfo);
	        String rmId = "";
	        if(!returnInfo.isError()) {
	        	rmId = (String) returnInfo.getReturnObj(DtoRmMaint.DTO_RMID);
	        }
	        inputRmId.setEnabled(true);
	        inputRmId.setUICValue(rmId);
	        saveBtn.setEnabled(true);
		} else {
			inputRmId.setEnabled(false);
			saveBtn.setEnabled(false);
		}
		
	}
	
}
