package client.essp.timesheet.period;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.essp.common.view.VWGeneralWorkArea;
import client.essp.timesheet.approval.TsPeriodChangeListener;
import client.framework.view.vwcomp.VWButtonGroup;
import client.framework.view.vwcomp.VWJRadioButton;

/**
 * <p>Title: VWTsPeriodComponent</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VWTsPeriodComponent extends VWGeneralWorkArea {

    public VwTsPeriod vwTsPeriod;
    private VWJRadioButton btnAnyPeriod;
    private VWJRadioButton btnTsPeriod;
    private VWJRadioButton btnMonthPeriod;
    private VWButtonGroup grpPeriod;
    public int TYPE = 0;

    public VWTsPeriodComponent() {
        try {
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }
        addUICEvent();

    }

    /**
     * 初始化UI
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setLayout(null);

        vwTsPeriod = new VwTsPeriod();
        btnAnyPeriod = new VWJRadioButton();
        btnTsPeriod = new VWJRadioButton();
        btnMonthPeriod = new VWJRadioButton();
        grpPeriod = new VWButtonGroup();
        
        vwTsPeriod.setBounds(new Rectangle(375, 2, 320, 25));
        vwTsPeriod.setLableVisiable(false);
        btnAnyPeriod.setBounds(new Rectangle(10, 2, 100, 20));
        btnAnyPeriod.setSelected(true);
        btnTsPeriod.setBounds(new Rectangle(120, 2, 150, 20));
        btnMonthPeriod.setBounds(new Rectangle(270, 2, 155, 20));
        btnAnyPeriod.setText("rsid.timesheet.anyPeriod");
        btnTsPeriod.setText("rsid.timesheet.tsPeriod");
        btnMonthPeriod.setText("rsid.timesheet.monthPeriod");
        grpPeriod.add(btnAnyPeriod);
        grpPeriod.add(btnTsPeriod);
        grpPeriod.add(btnMonthPeriod);
        vwTsPeriod.setParameter(null);

        this.add(vwTsPeriod);
        this.add(btnAnyPeriod);
        this.add(btnTsPeriod);
        this.add(btnMonthPeriod);
    }

    /**
     * 事件处理
     */
    private void addUICEvent() {
        btnAnyPeriod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TYPE = VwTsPeriod.PERIOD_MODEL_ANY;
                vwTsPeriod.setPeriodMode(VwTsPeriod.PERIOD_MODEL_ANY);
            }
        });

        btnTsPeriod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TYPE = VwTsPeriod.PERIOD_MODEL_TS;
                vwTsPeriod.setPeriodMode(VwTsPeriod.PERIOD_MODEL_TS);
            }
        });

        btnMonthPeriod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TYPE = VwTsPeriod.PERIOD_MODEL_MONTH;
                vwTsPeriod.setPeriodMode(VwTsPeriod.PERIOD_MODEL_MONTH);
            }
        });
    }

    /**
     * 刷新界面
     */
    public void refreshWorkArea() {
        vwTsPeriod.refreshWorkArea();
    }


    public void setMultiFlag(boolean m) {
        vwTsPeriod.setMultiFlag(m);
    }

    public void addTsChangeListener(TsPeriodChangeListener l) {
       vwTsPeriod.addTsChangeListener(l);
   }

}
