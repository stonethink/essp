/*
 * Created on 2008-4-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package client.essp.timesheet.report.attvariant;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.timesheet.report.DtoAttVariant;
import c2s.essp.timesheet.report.DtoAttVariantQuery;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import com.wits.util.Parameter;

public class VwSendMail extends VWGeneralWorkArea implements IVWPopupEditorEvent{
    private List<DtoAttVariant> leaveList;
    private List<DtoAttVariant> otList;
    private JButton selectAllBtn = new JButton();
    private JButton selectNonBtn = new JButton();
    private JButton selectDiffBtn = new JButton();
    private JButton sendBtn = new JButton();
    private VwAttVariantOtList vwOtList = new VwAttVariantOtList();
    private VwAttVariantLeaveList vwLeaveList = new VwAttVariantLeaveList();
    private final static String acntionId_Send = "FTSAttVaraintSendMails";
    
    public VwSendMail() {
        try {
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }
        addUICEvent();
    }

    //初始化
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(650, 650));
        vwLeaveList = new VwAttVariantLeaveList();
        addTab("rsid.timesheet.attVariantLeaveHours",vwLeaveList);

        vwOtList = new VwAttVariantOtList();
        addTab("rsid.timesheet.attVariantOvertimeHours",vwOtList);
        
     }
    
      private void addUICEvent() {
        this.getButtonPanel().addButton(selectAllBtn);
        selectAllBtn.setText("rsid.timesheet.selectAll");
        selectAllBtn.setToolTipText("rsid.timesheet.selectAll");
        selectAllBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                processSelectAll();
            }
        });
        
        this.getButtonPanel().addButton(selectNonBtn);
        selectNonBtn.setText("rsid.timesheet.selectNon");
        selectNonBtn.setToolTipText("rsid.timesheet.selectNon");
        selectNonBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                processSelectNon();
            }
        });
        
        this.getButtonPanel().addButton(selectDiffBtn);
        selectDiffBtn.setText("rsid.timesheet.selectDiff");
        selectDiffBtn.setToolTipText("rsid.timesheet.selectDiff");
        selectDiffBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                processSelectDiff();
            }
        });
        
        this.getButtonPanel().addButton(sendBtn);
        sendBtn.setText("rsid.timesheet.send");
        sendBtn.setToolTipText("rsid.timesheet.send");
        sendBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                processSend();
            }
        });
        
    }
    
     //全选
    private void processSelectAll() {
        for(DtoAttVariant dto : leaveList) {
            dto.setChecked(true);
        }
        for(DtoAttVariant dto : otList) {
            dto.setChecked(true);
        }
        resetUI();
    }
    
    //全不选
    private void processSelectNon() {
        for(DtoAttVariant dto : leaveList) {
            dto.setChecked(false);
        }
        for(DtoAttVariant dto : otList) {
            dto.setChecked(false);
        }
        resetUI();
    }
    
    //选有差异的记录
    private void processSelectDiff() {
        for(DtoAttVariant dto : leaveList) {
            if(dto.isBalanced()){
                dto.setChecked(true);
            } else {
                dto.setChecked(false);
            }
        }
        
        for(DtoAttVariant dto : otList) {
            if(dto.isBalanced()){
                dto.setChecked(true);
            } else {
                dto.setChecked(false);
            }
        }
        resetUI();
    }
    
    //发邮件
    private void processSend() {
        List sendList = new ArrayList();
        for(DtoAttVariant dto : leaveList) {
            if(dto.isChecked()) {
                sendList.add(dto);
            }
        }
        for(DtoAttVariant dto : otList) {
            if(dto.isChecked()) {
                sendList.add(dto);
            }
        }
        if(sendList.size() == 0) {
            return;
        }
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(acntionId_Send);
        inputInfo.setInputObj(DtoAttVariantQuery.DTO_SEND_LIST, sendList);
        ReturnInfo returnInfo = this.accessData(inputInfo);
        if(!returnInfo.isError()){
           comMSG.dispMessageDialog("rsid.timesheet.sendOver");
        }
    }
    
    
    /**
     * 参数设置
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        leaveList = (List)param.get(DtoAttVariantQuery.DTO_LEAVE_LIST);
        otList = (List)param.get(DtoAttVariantQuery.DTO_OVERTIME_LIST);
        vwLeaveList.setParameter(param);
        vwOtList.setParameter(param);
    }
    
    public void resetUI(){
        vwLeaveList.resetUI();
        vwOtList.resetUI();
    }
   
    public boolean onClickCancel(ActionEvent e) {
        return true;
    }
    public boolean onClickOK(ActionEvent e) {
        return true;
    }
}



