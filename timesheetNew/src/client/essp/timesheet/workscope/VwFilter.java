package client.essp.timesheet.workscope;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;

import c2s.essp.timesheet.workscope.DtoActivityFilter;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWUtil;

public class VwFilter extends VWGeneralWorkArea {
	
	private VWJCheckBox chkNotStart;
	private VWJCheckBox chkInProgress;
	private VWJCheckBox chkCompleted;
	private DtoActivityFilter dtoFilter;
	
	
	public VwFilter() {
		try {
            jbInit();
            setUIComponentName();
            setEnterOrder();
            setTabOrder();
            resetUI();
        } catch (Exception ex) {
            log.error(ex);
        }
	}

	/**
     * 初始化UI
     * @throws Exception
     */
    private void jbInit() throws Exception {
    	this.setLayout(null);
    	setPreferredSize(new Dimension(250, 120));
    	chkNotStart = new VWJCheckBox();
    	chkInProgress = new VWJCheckBox();
    	chkCompleted = new VWJCheckBox();
    	
    	chkNotStart.setText("rsid.timesheet.activity.notStart");
    	chkNotStart.setBounds(new Rectangle(35, 24, 200, 20));
    	
    	chkInProgress.setText("rsid.timesheet.activity.inProgress");
    	chkInProgress.setBounds(new Rectangle(35, 54, 200, 20));
    	
    	chkCompleted.setText("rsid.timesheet.activity.completed");
    	chkCompleted.setBounds(new Rectangle(35, 84, 200, 20));
    	
    	this.add(chkNotStart);
    	this.add(chkInProgress);
    	this.add(chkCompleted);
    }
    
    /**
     * 设置控件名称
     */
    private void setUIComponentName() {
    	chkNotStart.setName("notStart");
    	chkInProgress.setName("inProgress");
    	chkCompleted.setName("completed");
    }

    /**
     * 设置Tab键跳转顺序
     */
    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(chkNotStart);
        compList.add(chkInProgress);
        compList.add(chkCompleted);
        comFORM.setTABOrder(this, compList);
    }

    /**
     * 设置Enter键跳转顺序
     */
    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(chkNotStart);
        compList.add(chkInProgress);
        compList.add(chkCompleted);
        comFORM.setEnterOrder(this, compList);
    }
    
    public static void main(String[] args) {
    	VwFilter filter = new VwFilter();
    	DtoActivityFilter dto = new DtoActivityFilter();
    	dto.setInProgress(false);
    	VWUtil.bindDto2UI(dto, filter);
    	TestPanel.show(filter);
    }
}
