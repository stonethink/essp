package client.essp.pwm.workbench.period;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJNumber;
import client.framework.view.vwcomp.VWJRadioButton;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import client.framework.common.Global;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Enovation</p>
 * @author Xuxi.Chen
 * @version 1.0
 */
public class VwPeriodDef extends VWWorkArea {
    Date startDate;

    VWJLabel lblStartFrom = new VWJLabel();
    VWJRadioButton radRepTimes = new VWJRadioButton();
    VWJRadioButton radRepRange = new VWJRadioButton();
    VWJNumber numRepTimes = new VWJNumber();
    VWJDate dteEndDate = new VWJDate();

    VWJRadioButton radDay = new VWJRadioButton();
    VWJRadioButton radWeek = new VWJRadioButton();

    JPanel jPanelModeMain = new JPanel();
    VwPeriodDateDef jPanelPeriodDateDef1 = new VwPeriodDateDef();
    VwPeriodWeekDef jPanelPeriodWeekDef1 = new VwPeriodWeekDef();
    IPeriodDef periodDef;

    public VwPeriodDef() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        initUI();
    }

    private void jbInit() throws Exception {
        this.setLayout(new BorderLayout());

        lblStartFrom.setText("Start date: ");
        lblStartFrom.setPreferredSize(new Dimension(250, 20));

        radRepTimes.setText("Repeat");
        radRepRange.setRolloverEnabled(false);
        radRepRange.setText("End Date:");

        numRepTimes.setPreferredSize(new Dimension(90, 20));
        numRepTimes.setMaxInputIntegerDigit(4);
        numRepTimes.setValue(5);

        JLabel jLabelTimes = new JLabel();
        jLabelTimes.setText("time(s)");

        dteEndDate.setPreferredSize(new Dimension(130, 22));
        dteEndDate.setCanSelect(true);

        radDay.setText("Date");
        radWeek.setText("Week");

        JPanel jPanelPerildic = new JPanel();
        jPanelPerildic.setLayout(new BorderLayout());
        Border border1 = BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151));
        TitledBorder titledBorder2 = new TitledBorder(border1, "Periodic Mode");
        jPanelPerildic.setBorder(titledBorder2);

        jPanelModeMain.setLayout(new BorderLayout());
        jPanelPerildic.add(jPanelModeMain, BorderLayout.CENTER);

        JPanel jPanelChooseDayWeek = new JPanel();
        jPanelChooseDayWeek.setLayout(new GridBagLayout());
        jPanelChooseDayWeek.setPreferredSize(new Dimension(90, 100));
        jPanelChooseDayWeek.add(radDay, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanelChooseDayWeek.add(radWeek, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                    , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        JPanel jPanel8 = new JPanel();
        Border border3 = BorderFactory.createEmptyBorder();
        Border border4 = BorderFactory.createCompoundBorder(border3, border1);
        jPanel8.setBorder(border4);
        jPanel8.setPreferredSize(new Dimension(3, 14));

        JPanel jPanel5 = new JPanel();
        jPanel5.setLayout(new BorderLayout());
        jPanel5.add(jPanelChooseDayWeek, BorderLayout.CENTER);
        jPanel5.add(jPanel8, BorderLayout.EAST);
        jPanelPerildic.add(jPanel5, BorderLayout.WEST);


        JPanel jPanel3 = new JPanel();
        FlowLayout flowLayout1 = new FlowLayout();
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setHgap(1);
        flowLayout1.setVgap(1);
        jPanel3.add(lblStartFrom, null);
//        jPanel3.add(dteStartDate, null);

        JPanel jPanel4 = new JPanel();
        jPanel4.setLayout(new GridBagLayout());
        jPanel4.add(radRepTimes, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel4.add(radRepRange, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel4.add(numRepTimes, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel4.add(jLabelTimes, new GridBagConstraints(2, 1, 2, 1, 0.0, 0.0
            , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel4.add(dteEndDate, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
            , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));


        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new BorderLayout());
        TitledBorder titledBorder3 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(165, 163, 151)), "Repeat Range");
        jPanel2.setBorder(titledBorder3);
        jPanel2.add(jPanel4, BorderLayout.CENTER);
        jPanel2.add(jPanel3, BorderLayout.WEST);

        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(this.radDay);
        buttonGroup1.add(this.radWeek);

        ButtonGroup buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(this.radRepTimes);
        buttonGroup2.add(this.radRepRange);

        this.add(jPanelPerildic, BorderLayout.CENTER);
        this.add(jPanel2, BorderLayout.SOUTH);
    }

    private void addUICEvent(){
		radDay.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 actionPerformedRadDay();
             }
         });

        radWeek.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 actionPerformedRadWeek();
             }
         });

        radRepTimes.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 actionPerformedRepTimes();
             }
         });

        radRepRange.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 actionPerformedRepRange();
             }
         });
    }

    private void initUI() {
        setStartDate(Global.todayDate);
        actionPerformedRadDay();
        actionPerformedRepTimes();
    }

    protected void actionPerformedRadDay(){
        radDay.setSelected(true);

        this.jPanelModeMain.removeAll();
        this.jPanelModeMain.add(this.jPanelPeriodDateDef1, BorderLayout.WEST);

        periodDef = jPanelPeriodDateDef1;

        jPanelModeMain.updateUI();
    }

    protected void actionPerformedRadWeek() {
        radWeek.setSelected(true);

        this.jPanelModeMain.removeAll();
        this.jPanelModeMain.add(this.jPanelPeriodWeekDef1, BorderLayout.WEST);

        periodDef = jPanelPeriodWeekDef1;

        jPanelModeMain.updateUI();
    }

    protected void actionPerformedRepRange(){
        radRepRange.setSelected(true);

        numRepTimes.setUICValue(null);
        numRepTimes.setEnabled(false);
        dteEndDate.setUICValue(startDate);
        dteEndDate.setEnabled(true);
    }

    protected void actionPerformedRepTimes(){
        radRepTimes.setSelected(true);

        numRepTimes.setEnabled(true);
        numRepTimes.setUICValue(new Long(5));
        dteEndDate.setUICValue(null);
        dteEndDate.setEnabled(false);
    }

	public Iterator getPeriodList(){
		if( radRepTimes.isSelected() == true ){
			long repTimes = numRepTimes.getValue();
			return periodDef.getPeriodList( startDate, repTimes );
		}else{
			Date endDate = (Date)dteEndDate.getUICValue();
			return periodDef.getPeriodList( startDate, endDate );
		}
	}

    public void setStartDate(Date startDate){
        this.startDate = startDate;
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        int day = c.get(Calendar.DAY_OF_WEEK);
        String sDay = VwPeriodWeekDef.WEEK_DAY_NAMES[day-1];
        String str = comDate.dateToString(startDate, "yyyy-MM-dd");
        lblStartFrom.setText("Start from today:" + str + "(" + sDay + ")");
    }

    public static void main(String args[]) {
        TestPanel.show(new VwPeriodDef());
    }

}

