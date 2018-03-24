package client.essp.timesheet.activity.steps;

import client.essp.common.view.VWTDWorkArea;
import java.awt.Dimension;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.framework.view.event.RowSelectedListener;
import com.wits.util.Parameter;
import c2s.essp.timesheet.activity.DtoActivityStep;
import client.framework.view.event.DataChangedListener;
/**
 * <p>Title: VwSteps</p>
 *
 * <p>Description:Steps最外面的框架卡片 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwSteps extends VWTDWorkArea implements DataChangedListener {
    DtoActivityStep dtoStep;
    VwStepsLeftBench vwLeftBench = new VwStepsLeftBench();
    VwStepsRightBench vwRightBench = new VwStepsRightBench();

    public VwSteps() {
        super(300);
        try {
            jbInit();
            addUICEvent();
        } catch (Exception e) {
            log.error(e);
        }
    }

    //初始化
    private void jbInit() throws Exception {
        this.setHorizontalSplit();
        this.setPreferredSize(new Dimension(650, 300));
        vwLeftBench = new VwStepsLeftBench();
        vwLeftBench.setPreferredSize(new Dimension(300, 300));
        this.getTopArea().addTab("rsid.timesheet.steps", vwLeftBench);

        vwRightBench = new VwStepsRightBench();
        vwRightBench.setPreferredSize(new Dimension(350, 300));
        this.getDownArea().addTab("", vwRightBench);
    }

    //单击事件
    private void addUICEvent() {
        vwLeftBench.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false) {
					processRowSelectedAccList();
				}
            }
        });
    }

   //参数设置
    public void setParameter(Parameter parameter) {
        vwLeftBench.setParameter(parameter);
    }

    public void refreshWorkArea() {
        vwLeftBench.refreshWorkArea();
    }

    //初始化右边栏位的TITLE
    public void processRowSelectedAccList() {
        dtoStep = (DtoActivityStep) vwLeftBench.getTable().
                  getSelectedData();
        if (dtoStep != null) {
            this.vwRightBench.setParameter(dtoStep);
            this.getDownArea().setTabTitle(0, dtoStep.getName());
        } else {
            this.vwRightBench.setParameter((DtoActivityStep)null);
            this.getDownArea().setTabTitle(0, "");
        }
        vwRightBench.refreshWorkArea();
    }

    public void processDataChanged() {
        vwLeftBench.processDataChanged();
    }
}
