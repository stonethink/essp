package client.essp.timesheet.admin.preference;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;

import c2s.dto.*;
import c2s.essp.timesheet.preference.DtoPreference;
import client.essp.common.loginId.VWJLoginId;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.model.VMComboBox;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;
import com.wits.util.comDate;

public class VwPreference extends VWGeneralWorkArea {

    private final static String actionId_Load = "FTSLoadPreference";
    private final static String actionId_Save = "FTSSavePreference";
    private JButton saveBtn = new JButton();
    private JButton refreshBtn = new JButton();
    private final static Vector monthDays;

    private DtoPreference dtoPreference;

    public VwPreference() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
    }

    private void jbInit(){
        this.setLayout(null);
        
        lblmonthStartDay.setText("rsid.timesheet.monthStartDay");
        lblmonthStartDay.setBounds(new Rectangle(22, 10, 400, 22));
        cmbMonthStartDay.setBounds(new Rectangle(540, 10, 47, 22));
        cmbMonthStartDay.setName("monthStartDay");
       
        cmbMonthStartDay.setModel(new VMComboBox(monthDays));
        
        lblmonthStartDayTwo.setText("rsid.timesheet.monthStartDayTwo");
        lblmonthStartDayTwo.setBounds(new Rectangle(22, 40, 400, 22));
        cmbMonthStartDayTwo.setBounds(new Rectangle(540, 40, 47, 22));
        cmbMonthStartDayTwo.setName("monthStartDayTwo");

        cmbMonthStartDayTwo.setModel(new VMComboBox(monthDays));

        lblRuu.setText("rsid.common.ruu");
        lblRuu.setBounds(new Rectangle(21, 70, 130, 22));
        txtRuu.setBounds(new Rectangle(160, 70, 140, 22));
        txtRuu.setName("ruu");
        txtRuu.setEnabled(false);
        
        lblRut.setText("rsid.common.rut");
        lblRut.setBounds(new Rectangle(310, 70, 130, 22));
        txtRut.setBounds(new Rectangle(450, 70, 140, 22));
        txtRut.setName("rut");
        

        this.add(cmbMonthStartDay);
        this.add(lblmonthStartDay);
        this.add(cmbMonthStartDayTwo);
        this.add(lblmonthStartDayTwo);

        this.add(lblRuu);
        this.add(lblRut);
        this.add(txtRuu);
        this.add(txtRut);

    }

    private void addUICEvent() {
        //保存
        saveBtn = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processSave();
            }
        });
        saveBtn.setToolTipText("rsid.common.save");

        //刷新
        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
        refreshBtn.setToolTipText("rsid.common.refresh");
    }


    /**
     * 设置参数
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
    }

    /**
     * 刷新UI
     */
    protected void resetUI() {
        processLoad();
        VWUtil.bindDto2UI(dtoPreference, this);
        txtRut.setText(comDate.dateToString(dtoPreference.getRut(), "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 加载数据
     */
    private void processLoad() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionId_Load);
        inputInfo.setInputObj(DtoPreference.DTO_SITE, DtoPreference.DTO_SITE_GLOBAL);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(returnInfo.isError()) {
            dtoPreference = new DtoPreference();
        } else {
            dtoPreference = (DtoPreference)
                            returnInfo.getReturnObj(DtoPreference.DTO);
        }
    }

    /**
     * 保存数据
     */
    private void processSave() {
        InputInfo inputInfo = new InputInfo();
        VWUtil.convertUI2Dto(dtoPreference, this);
        dtoPreference.setSite(DtoPreference.DTO_SITE_GLOBAL);
        inputInfo = new InputInfo();
        inputInfo.setInputObj(DtoPreference.DTO, dtoPreference);
        inputInfo.setActionId(actionId_Save);
        ReturnInfo returnInfoSave = this.accessData(inputInfo);
        if(!returnInfoSave.isError()) {
        	resetUI();//保存后刷新页面
        	comMSG.dispMessageDialog("rsid.common.saveComplete");
        }
    }

    VWJLabel lblmonthStartDay = new VWJLabel();
    VWJComboBox cmbMonthStartDay = new VWJComboBox();
    VWJLabel lblmonthStartDayTwo = new VWJLabel();
    VWJComboBox cmbMonthStartDayTwo = new VWJComboBox();
    VWJLabel lblRuu = new VWJLabel();
    VWJLabel lblRut = new VWJLabel();
    VWJLoginId txtRuu = new VWJLoginId();
    VWJLabel txtRut = new VWJLabel();

    static {
        monthDays = new Vector();
        for(int i = 1; i <= 28; i++) {
            monthDays.add(new DtoComboItem(String.valueOf(i), Long.valueOf(i)));
        }
        
    }

}
