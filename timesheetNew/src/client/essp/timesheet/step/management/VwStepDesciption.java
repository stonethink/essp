package client.essp.timesheet.step.management;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import c2s.essp.timesheet.step.management.DtoStep;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.event.DataChangedListener;
import client.framework.view.vwcomp.VWJTextArea;
import client.framework.view.vwcomp.VWUtil;

import com.wits.util.Parameter;

/**
 * <p>Title: Step Description</p>
 *
 * <p>Description: Step's Description</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author Robin
 * @version 1.0
 */
public class VwStepDesciption extends VWGeneralWorkArea {

    VWJTextArea desc = new VWJTextArea();
    DtoStep step;
    public VWJTextArea getDesc(){
    	return this.desc;
    }
   
    public VwStepDesciption() {
        try {
            jbInit();
        } catch (Exception e) {
            log.error(e);
        }
        addUICEvent();
    }

    /**
     * 初始化界面
     * @throws Exception
     */
    private void jbInit() throws Exception {
        desc.setName("procDescr");
        this.add(desc);
    }

    /**
     * 事件处理
     */
    private void addUICEvent() {
    	desc.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				VWUtil.convertUI2Dto(step, VwStepDesciption.this);
				desc.setValueToDto(step);
			}});
    	desc.addPropertyChangeListener("text", new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				VWUtil.convertUI2Dto(step, VwStepDesciption.this);
				
			}});
    	desc.addDataChangedListener(new DataChangedListener(){

			public void processDataChanged() {
				VWUtil.convertUI2Dto(step, VwStepDesciption.this);
			}});
    }
    
    
	protected void resetUI() {
		VWUtil.bindDto2UI(step, this);
		
    	this.fireDataChanged();
	}

	public void setParameter(Parameter param){
    	step=(DtoStep)param.get("STEP");
    	super.setParameter(param);
    }

}
