package client.essp.timesheet.rmmaint;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import c2s.essp.timesheet.rmmaint.DtoRmMaint;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.event.RowSelectedListener;

import com.wits.util.Parameter;
/**
 * 特殊RM维护功能
 * @author wenhaizheng
 *
 */
public class VwRmMaint extends VWTDWorkArea {
	
	private VwRmMaintList list;
	private VwRmMaintGeneral general;
	private JButton delBtn = new JButton();
	private DtoRmMaint dtoRmMaint;
	public VwRmMaint() {
		super(230);
		jbInit();
		addUICEvent();
	}
	private void jbInit() {
		list = new VwRmMaintList();
		general = new VwRmMaintGeneral();
		this.getTopArea().addTab("rsid.timesheet.personList", list);
		this.getDownArea().addTab("rsid.timesheet.general", general);
	}
	public void setParameter(Parameter para) {
		super.setParameter(para);
		list.setParameter(para);
	}

	
	public void refreshWorkArea() {
		list.refreshWorkArea();
	}
	/**
     * 注册UI事件
     */
    private void addUICEvent() {
    	list.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processSelectedUser();
				} 
			}
    	});
    	delBtn = list.getButtonPanel().addButton("del.png");
    	delBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				processDel();
			}
		});
    	delBtn.setEnabled(false);
        delBtn.setToolTipText("rsid.common.delete");
    }
    private void processSelectedUser() {
        dtoRmMaint = (DtoRmMaint) list.getTable().getSelectedData();
        if(dtoRmMaint != null) {
        	delBtn.setEnabled(true);
        } else {
        	delBtn.setEnabled(false);
        }
		Parameter param = new Parameter();
		param.put(DtoRmMaint.DTO, dtoRmMaint);
		general.setParameter(param);
		general.refreshWorkArea();
      
    }
    private void processDel() {
    	list.processDel();
    }
    
   
}
