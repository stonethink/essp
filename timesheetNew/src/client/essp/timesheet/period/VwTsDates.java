package client.essp.timesheet.period;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;
import client.essp.common.view.VWTDWorkArea;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.*;

import com.wits.util.Parameter;

public class VwTsDates extends VWTDWorkArea implements IVWPopupEditorEvent{
	private VwTsDatesCreator topCreator;
	private VwTsDatesList downList;
	private VWJButton createBtn = new VWJButton();
	private JButton addBtn = new JButton();
	private JButton delBtn = new JButton();
	private VwTsDatesAdd vwTsDatesAdd;
	public VwTsDates() {
		super(300);
		jbInit();
		addUICEvent();
	}
	private void jbInit() {
		this.setHorizontalSplit();
		topCreator = new VwTsDatesCreator();
		downList = new VwTsDatesList();
		this.getTopArea().addTab("rsid.timesheet.bcTsDates", topCreator);
		this.getDownArea().addTab("rsid.timesheet.tsDatesList", downList);
		
	}
	public void setParameter(Parameter para) {
		super.setParameter(para);
		downList.setParameter(para);
		downList.refreshWorkArea();
		topCreator.setParameter(para);
		topCreator.refreshWorkArea();
	}
	private void addUICEvent() {
		createBtn.setText("rsid.timesheet.batchCreate");
		createBtn.setHorizontalAlignment(SwingConstants.CENTER);
		createBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processBatchCreate();
			}
		});
		topCreator.getButtonPanel().add(createBtn);
		addBtn = downList.getButtonPanel().addButton("add.png");
		addBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processAdd();
			}
		});
        addBtn.setToolTipText("rsid.common.add");
		delBtn = downList.getButtonPanel().addButton("del.png");
		delBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processDel();
			}
		});
		delBtn.setEnabled(false);
        delBtn.setToolTipText("rsid.common.delete");
		downList.getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		downList.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processSelectedPeriod();
				}
            }
        });
	}
	 /**
     * 获取当前选中的所有数据
     * @return List
     */
    private List getSelectedRowDataList() {
        List rowList = new ArrayList();
        int[] rows = downList.getTable().getSelectedRows();
        for (int row : rows) {
            rowList.add(downList.getModel().getRow(row));
        }
        return rowList;
    }
	private void processSelectedPeriod() {
		DtoTimeSheetPeriod dtoPeriod = (DtoTimeSheetPeriod)
		                                  downList.getTable().getSelectedData();
		if(dtoPeriod != null) {
			delBtn.setEnabled(true);
		} else {
			delBtn.setEnabled(false);
		}
	}
	private void processAdd() {
		  vwTsDatesAdd = new VwTsDatesAdd();
		  vwTsDatesAdd.setPreferredSize(new Dimension(400, 60));
	      Parameter para = new Parameter();
	      vwTsDatesAdd.setParameter(para);
	      vwTsDatesAdd.refreshWorkArea();
	      VWJPopupEditor popupEditor = new VWJPopupEditor(getParentWindow(),
	              "Add Timesheet Period", vwTsDatesAdd, this);
	      popupEditor.showConfirm();
	}
	private void processDel() {
		int op = comMSG.dispConfirmDialog("error.client.VwTsDates.confirmDel");
		if(op != Constant.OK){
			return;
		}
		Parameter para = new Parameter();
		para.put(DtoTimeSheetPeriod.DTO_TsDastesList, getSelectedRowDataList());
		downList.setParameter(para);
		downList.processDel();
		downList.resetUI();
		topCreator.resetUI();
		processSelectedPeriod();
	}
	private void processBatchCreate() {
		if(!topCreator.processBatchCreate()){
			return;
		}
		downList.resetUI();
		topCreator.resetUI();
	}
	public boolean onClickCancel(ActionEvent arg0) {
		return true;
	}
	public boolean onClickOK(ActionEvent arg0) {
		vwTsDatesAdd.processAdd();
		downList.resetUI();
		topCreator.resetUI();
		vwTsDatesAdd.resetUI();
		return false;
	}
	
}
