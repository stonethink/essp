package client.essp.tc.weeklyreport;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comFORM;
import client.framework.view.vwcomp.VWJLabel;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTextArea;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VwWeeklyReportGeneral extends VWGeneralWorkArea {
    DtoWeeklyReport dtoWeeklyReport = null;
    Date beginPeriod = null;
    Date endPeriod = null;

    VwBelongTo vwBelongTo = new VwBelongTo();

    VWJLabel lblTue = new VWJLabel();
    VWJLabel lblDescription = new VWJLabel();
    VWJTextArea txaDescription = new VWJTextArea();
    VWJLabel lblComments = new VWJLabel();
    VWJTextArea txaComments = new VWJTextArea();

    VWJLabel lblMon = new VWJLabel();
    VWJLabel lblFri = new VWJLabel();
    VWJLabel lblWen = new VWJLabel();
    VWJLabel lblSta = new VWJLabel();
    VWJLabel lblSun = new VWJLabel();
    VWJLabel lblThu = new VWJLabel();
    VWJReal ralWen = new VWJReal();
    VWJReal ralFri = new VWJReal();
    VWJReal ralTue = new VWJReal();
    VWJReal ralMon = new VWJReal();
    VWJReal ralSun = new VWJReal();
    VWJReal ralSta = new VWJReal();
    VWJReal ralThu = new VWJReal();


    public VwWeeklyReportGeneral() {
        try {
            jbInit();

            setUIComponentName();
            setEnterOrder();
            setTabOrder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //建议在画好页面后再另添代码
    private void jbInit() throws Exception {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(700, 400));

        vwBelongTo.setBounds(new Rectangle(0, 0, 700, 100));
        vwBelongTo.setOpaque(true);

        lblDescription.setText("Job Description");
        lblDescription.setBounds(new Rectangle(26, 175, 120, 20));
        txaDescription.setBounds(new Rectangle(168, 175, 492, 80));

        lblComments.setText("Comments");
        lblComments.setBounds(new Rectangle(26, 269, 120, 20));
        txaComments.setBounds(new Rectangle(168, 269, 492, 80));
        txaComments.setEnabled(false);

        lblSta.setText("Sat.");
        lblSta.setBounds(new Rectangle(17, 30, 35, 17));
        ralSta.setBounds(new Rectangle(45, 30, 45, 17));
        ralSta.setMaxInputDecimalDigit(2);
        ralSta.setMaxInputIntegerDigit(2);

        lblSun.setText("Sun.");
        lblSun.setBounds(new Rectangle(107, 30, 35, 17));
        ralSun.setBounds(new Rectangle(135, 30, 45, 17));
        ralSun.setMaxInputDecimalDigit(2);
        ralSun.setMaxInputIntegerDigit(2);

        lblMon.setText("Mon.");
        lblMon.setBounds(new Rectangle(197, 30, 35, 17));
        ralMon.setBounds(new Rectangle(225, 30, 45, 17));
        ralMon.setMaxInputDecimalDigit(2);
        ralMon.setMaxInputIntegerDigit(2);

        lblTue.setText("Tue.");
        lblTue.setBounds(new Rectangle(287, 30, 35, 17));
        ralTue.setBounds(new Rectangle(315, 30, 45, 17));
        ralTue.setMaxInputDecimalDigit(2);
        ralTue.setMaxInputIntegerDigit(2);

        lblWen.setText("Wed.");
        lblWen.setBounds(new Rectangle(377, 30, 35, 17));
        ralWen.setBounds(new Rectangle(405, 30, 45, 17));
        ralWen.setMaxInputDecimalDigit(2);
        ralWen.setMaxInputIntegerDigit(2);

        lblThu.setText("Thu.");
        lblThu.setBounds(new Rectangle(467, 30, 35, 17));
        ralThu.setBounds(new Rectangle(495, 30, 45, 17));
        ralThu.setMaxInputDecimalDigit(2);
        ralThu.setMaxInputIntegerDigit(2);

        lblFri.setText("Fri.");
        lblFri.setBounds(new Rectangle(557, 30, 35, 17));
        ralFri.setBounds(new Rectangle(585, 30, 45, 17));
        ralFri.setMaxInputDecimalDigit(2);
        ralFri.setMaxInputIntegerDigit(2);

        Border hourBorder = BorderFactory.createTitledBorder("Work Hours");
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(26, 100, 637, 60);
        panel.setBorder(hourBorder);
        panel.add(ralMon);
        panel.add(lblMon);
        panel.add(ralFri);
        panel.add(lblFri);
        panel.add(ralSun);
        panel.add(lblSun);
        panel.add(lblSta);
        panel.add(ralSta);
        panel.add(ralThu);
        panel.add(lblThu);
        panel.add(ralWen);
        panel.add(lblWen);
        panel.add(lblTue);
        panel.add(ralTue);

        this.add(txaDescription);
        this.add(txaComments);
        this.add(lblComments);
        this.add(lblDescription);
        this.add(panel);
        this.add(vwBelongTo);

    }

    private void setUIComponentName() {
        ralSta.setName("satHour");
        ralSun.setName("sunHour");
        ralMon.setName("monHour");
        ralTue.setName("tueHour");
        ralWen.setName("wedHour");
        ralThu.setName("thuHour");
        ralFri.setName("friHour");
        txaDescription.setName("jobDescription");
        txaComments.setName("comments");
    }

    private void setTabOrder() {
        List compList = new ArrayList();
        compList.add(vwBelongTo);
        compList.add(ralSta);
        compList.add(ralSun);
        compList.add(ralMon);
        compList.add(ralTue);
        compList.add(ralWen);
        compList.add(ralThu);
        compList.add(ralFri);
        compList.add(txaDescription);

        comFORM.setTABOrder(this, compList);
    }

    private void setEnterOrder() {
        List compList = new ArrayList();
        compList.add(vwBelongTo);
        compList.add(ralSta);
        compList.add(ralSun);
        compList.add(ralMon);
        compList.add(ralTue);
        compList.add(ralWen);
        compList.add(ralThu);
        compList.add(ralFri);
        compList.add(txaDescription.getTextArea());

        comFORM.setEnterOrder(this, compList);
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);

        this.dtoWeeklyReport = (DtoWeeklyReport) param.get(DtoTcKey.DTO);
        this.beginPeriod = (Date) param.get(DtoTcKey.BEGIN_PERIOD);
        this.endPeriod = (Date) param.get(DtoTcKey.END_PERIOD);
        if (dtoWeeklyReport == null) {
            throw new RuntimeException("Parameter - dtoweeklyreport is null.");
        }
        this.vwBelongTo.setUserId(dtoWeeklyReport.getUserId());
    }

    protected void setEnabledMode() {
        VWJReal[] dayComps = new VWJReal[] {
                             this.ralSun, ralMon, ralTue,
                             ralWen, ralThu, ralFri, ralSta
        };
        for (int i = 0; i < dayComps.length; i++) {
            dayComps[i].setEnabled(false);
        }

        if (beginPeriod != null && endPeriod != null) {
            Calendar day = Calendar.getInstance();
            day.setTime(beginPeriod);

            long maxTime = endPeriod.getTime();
            while (day.getTimeInMillis() <= maxTime) {
                int dayInWeek = day.get(Calendar.DAY_OF_WEEK);
                dayComps[dayInWeek - 1].setEnabled(true);

                day.set(Calendar.DAY_OF_MONTH, day.get(Calendar.DAY_OF_MONTH) + 1);
            }
        }

        this.txaComments.setEnabled(false);
    }

    protected void resetUI() {
        VWUtil.bindDto2UI(dtoWeeklyReport, this);

        setEnabledMode();
    }

    public void convertUI2Dto() {
        VWUtil.convertUI2Dto(dtoWeeklyReport, this);

        //专门设置account /activity /code
        Long acntRid = vwBelongTo.getAcntRid();
        Long activityRid = vwBelongTo.getActivityRid();
        Long codeValueRid = vwBelongTo.getCodeValueRid();

        dtoWeeklyReport.setAcntRid(acntRid);
        String acntName = null;
        if(vwBelongTo.getAcntCode() != null ){
            acntName = vwBelongTo.getAcntCode();
            if( vwBelongTo.getAcntName() != null ){
                acntName += " -- " + vwBelongTo.getAcntName();
            }
        }
        dtoWeeklyReport.setAcntName(acntName);
        if (activityRid == null) {
            dtoWeeklyReport.setActivityRid(null);
            dtoWeeklyReport.setActivityName(null);
            dtoWeeklyReport.setCodeValueRid(codeValueRid);
            dtoWeeklyReport.setCodeValueName(vwBelongTo.getCodeValueName());
            dtoWeeklyReport.setIsActivity("0");
        } else {
            String actvitiyName = null;
            dtoWeeklyReport.setActivityRid(activityRid);
            if (vwBelongTo.getActivityCode() != null) {
                actvitiyName = vwBelongTo.getActivityCode();
                if (vwBelongTo.getActivityName() != null) {
                    actvitiyName += " -- " + vwBelongTo.getActivityName();
                }
            }
            dtoWeeklyReport.setActivityName(actvitiyName);
            dtoWeeklyReport.setCodeValueRid(null);
            dtoWeeklyReport.setCodeValueName(null);
            dtoWeeklyReport.setIsActivity("1");
        }
    }

    public static void main(String args[]) {
        TestPanel.show(new VwWeeklyReportGeneral());
    }
}
