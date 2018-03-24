package client.essp.tc.hrmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;

import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Global;
import client.framework.view.vwcomp.VWJButton;
import client.image.ComImage;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import netscape.javascript.JSObject;

public class VWJButtonTimecardExcel extends VWJButton {
    static final String actionId = "FTcExcelAction";

    ITimecardExcelParameter parameter;
    public VWJButtonTimecardExcel(ITimecardExcelParameter parameter) {
        this.parameter = parameter;

        this.setIcon(new ImageIcon( ComImage.getImage("export.png")));
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                accessData();
            }
        });
    }

    public int accessData() {
        JSObject win = JSObject.getWindow(Global.applet);

        String url = "http://localhost:8080/essp/hr/outExcel.jsp";
        win.eval(getScript(url, parameter));
        return 0;
    }

    String getScript(String url, ITimecardExcelParameter parameter){
        StringBuffer sb = new StringBuffer("window.open(\"");
        sb.append(url);
        sb.append("?");
        sb.append("acntRid=");
        sb.append(parameter.getAcntRid().longValue());
        sb.append("&beginPeriod=");
        sb.append(comDate.dateToString(parameter.getBeginPeriod(), "yyyyMMdd"));
        sb.append("&endPeriod=");
        sb.append(comDate.dateToString(parameter.getEndPeriod(), "yyyyMMdd"));
        sb.append("&periodType=");
        sb.append(parameter.getType());
        sb.append(" \" , \"\", \"toolbar =yes, menubar=yes, scrollbars=yes, resizable=yes, status=yes,width=800,height=600,top=10,left=10\");");

        return sb.toString();
    }


    public static void main(String args[]) {
        VWWorkArea w = new VWWorkArea();
        w.setLayout(null);

        ITimecardExcelParameter p = new ITimecardExcelParameter() {
            public Long getAcntRid() {
                return new Long(1);
            }

            public Date getBeginPeriod() {
                return new Date(105,10,25);
            }

            public Date getEndPeriod() {
                return new Date(106,0,18);
            }

            public String getType() {
                return DtoWeeklyReportSumByHr.ON_WEEK;
            }
        };
        VWJButtonTimecardExcel btnExcel = new VWJButtonTimecardExcel(p);
        btnExcel.setBounds(100, 100, 100, 20);
        w.add(btnExcel);

        System.out.println( btnExcel.getScript("http://localhost:8080/essp/hr/outExcel.jsp",p) );

        TestPanel.show(w);
    }

}
