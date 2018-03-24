package client.essp.tc.hrmanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import c2s.essp.tc.weeklyreport.DtoWeeklyReportSumByHr;
import client.essp.common.TableUitl.TableColumnWidthSetter;
import client.essp.common.view.VWTableWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.model.VMColumnConfig;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJDisp;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJTable;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import client.essp.tc.common.TableListener;
import client.framework.common.Constant;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.table.TableColumn;
import client.essp.tc.common.RowRendererTwoNumber;
import client.framework.view.vwcomp.VWJNumber;
import client.essp.common.TableUitl.TableColumnChooseDisplay;
import client.framework.view.vwcomp.VWJButton;

public class VwTcListBaseTest extends VwTcListBase {

    protected ReturnInfo accessData(InputInfo inputInfo) {
        ReturnInfo returnInfo = new ReturnInfo();

        List dtoList = new ArrayList();

        dtoList.add(getDto1());
        dtoList.add(getDto2());
        dtoList.add(getDto3());
        dtoList.add(getDto4());
        dtoList.add(getDto5());

        returnInfo.setReturnObj(DtoTcKey.WEEKLY_REPORT_LIST, dtoList);
        return returnInfo;
    }

    DtoWeeklyReportSumByHr getDto1(){
        DtoWeeklyReportSumByHr dto = new DtoWeeklyReportSumByHr();
        dto.setLocked("true");
        dto.setUserId("stone.shi");
        dto.setBeginPeriod(new Date(105,11,3));
        dto.setEndPeriod(new Date(105,11,9));
        dto.setRealBeginPeriod(new Date(105,11,3));
        dto.setRealEndPeriod(new Date(105,11,9));
        dto.setStandardHour(new BigDecimal(40));
        dto.setActualHour(new BigDecimal(40));
        dto.setActualHourConfirmed(new BigDecimal(30));

        dto.setOvertimeSum(new BigDecimal(8));
        dto.setOvertimeSumConfirmed(new BigDecimal(0));

        dto.setLeaveSum(new BigDecimal(8));
        dto.setLeaveSumConfirmed(new BigDecimal(0));
        dto.setLeaveOfPayAll(new BigDecimal(0));
        dto.setLeaveOfPayAllConfirmed(new BigDecimal(0));
        dto.setLeaveOfPayHalf(new BigDecimal(0));
        dto.setLeaveOfPayHalfConfirmed(new BigDecimal(0));
        dto.setLeaveOfPayNone(new BigDecimal(8));
        dto.setLeaveOfPayNoneConfirmed(new BigDecimal(0));

        dto.setAbsent(new BigDecimal(0));
        dto.setViolat(new Long(99));
        return dto;
    }

    DtoWeeklyReportSumByHr getDto2(){
        DtoWeeklyReportSumByHr dto = new DtoWeeklyReportSumByHr();
        dto.setLocked("true");
        dto.setUserId("stone.shi");
        dto.setBeginPeriod(new Date(105,11,3));
        dto.setEndPeriod(new Date(105,11,9));
        dto.setRealBeginPeriod(new Date(105,11,3));
        dto.setRealEndPeriod(new Date(105,11,9));
        dto.setStandardHour(new BigDecimal(40));
        dto.setActualHour(new BigDecimal(40));
        dto.setActualHourConfirmed(new BigDecimal(30));

        dto.setOvertimeSum(new BigDecimal(8));
        dto.setOvertimeSumConfirmed(new BigDecimal(8));

        dto.setLeaveSum(new BigDecimal(8));
        dto.setLeaveSumConfirmed(new BigDecimal(0));
        dto.setLeaveOfPayAll(new BigDecimal(0));
        dto.setLeaveOfPayAllConfirmed(new BigDecimal(0));
        dto.setLeaveOfPayHalf(new BigDecimal(0));
        dto.setLeaveOfPayHalfConfirmed(new BigDecimal(0));
        dto.setLeaveOfPayNone(new BigDecimal(8));
        dto.setLeaveOfPayNoneConfirmed(new BigDecimal(0));

        dto.setAbsent(new BigDecimal(0));
        dto.setViolat(new Long(99));
        return dto;
    }

    DtoWeeklyReportSumByHr getDto3(){
        DtoWeeklyReportSumByHr dto = new DtoWeeklyReportSumByHr();
        dto.setLocked("true");
        dto.setUserId("stone.shi");
        dto.setBeginPeriod(new Date(105,11,3));
        dto.setEndPeriod(new Date(105,11,9));
        dto.setRealBeginPeriod(new Date(105,11,3));
        dto.setRealEndPeriod(new Date(105,11,9));
        dto.setStandardHour(new BigDecimal(40));
        dto.setActualHour(new BigDecimal(40));
        dto.setActualHourConfirmed(new BigDecimal(30));

        dto.setOvertimeSum(new BigDecimal(8));
        dto.setOvertimeSumConfirmed(new BigDecimal(8));

        dto.setLeaveSum(new BigDecimal(8));
        dto.setLeaveSumConfirmed(new BigDecimal(0));
        dto.setLeaveOfPayAll(new BigDecimal(4));
        dto.setLeaveOfPayAllConfirmed(new BigDecimal(0));
        dto.setLeaveOfPayHalf(new BigDecimal(3));
        dto.setLeaveOfPayHalfConfirmed(new BigDecimal(0));
        dto.setLeaveOfPayNone(new BigDecimal(1));
        dto.setLeaveOfPayNoneConfirmed(new BigDecimal(0));

        dto.setAbsent(new BigDecimal(0));
        dto.setViolat(new Long(99));
        return dto;
    }

    DtoWeeklyReportSumByHr getDto4(){
        DtoWeeklyReportSumByHr dto = new DtoWeeklyReportSumByHr();
        dto.setLocked("true");
        dto.setUserId("stone.shi");
        dto.setBeginPeriod(new Date(105,11,3));
        dto.setEndPeriod(new Date(105,11,9));
        dto.setRealBeginPeriod(new Date(105,11,3));
        dto.setRealEndPeriod(new Date(105,11,9));
        dto.setStandardHour(new BigDecimal(40));
        dto.setActualHour(new BigDecimal(40));
        dto.setActualHourConfirmed(new BigDecimal(30));

        dto.setOvertimeSum(new BigDecimal(8));
        dto.setOvertimeSumConfirmed(new BigDecimal(8));

        dto.setLeaveSum(new BigDecimal(8));
        dto.setLeaveSumConfirmed(new BigDecimal(8));
        dto.setLeaveOfPayAll(new BigDecimal(4));
        dto.setLeaveOfPayAllConfirmed(new BigDecimal(4));
        dto.setLeaveOfPayHalf(new BigDecimal(3));
        dto.setLeaveOfPayHalfConfirmed(new BigDecimal(3));
        dto.setLeaveOfPayNone(new BigDecimal(1));
        dto.setLeaveOfPayNoneConfirmed(new BigDecimal(1));

        dto.setAbsent(new BigDecimal(0));
        dto.setViolat(new Long(99));
        return dto;
    }

    DtoWeeklyReportSumByHr getDto5(){
        DtoWeeklyReportSumByHr dto = new DtoWeeklyReportSumByHr();
        dto.setLocked("true");
        dto.setUserId("stone.shi");
        dto.setBeginPeriod(new Date(105,11,3));
        dto.setEndPeriod(new Date(105,11,9));
        dto.setRealBeginPeriod(new Date(105,11,3));
        dto.setRealEndPeriod(new Date(105,11,9));
        dto.setStandardHour(new BigDecimal(40));
        dto.setActualHour(new BigDecimal(40));
        dto.setActualHourConfirmed(new BigDecimal(30));

        dto.setOvertimeSum(new BigDecimal(8));
        dto.setOvertimeSumConfirmed(new BigDecimal(8));

        dto.setLeaveSum(new BigDecimal(8));
        dto.setLeaveSumConfirmed(new BigDecimal(8));
        dto.setLeaveOfPayAll(new BigDecimal(4));
        dto.setLeaveOfPayAllConfirmed(new BigDecimal(4));
        dto.setLeaveOfPayHalf(new BigDecimal(3));
        dto.setLeaveOfPayHalfConfirmed(new BigDecimal(3));
        dto.setLeaveOfPayNone(new BigDecimal(1));
        dto.setLeaveOfPayNoneConfirmed(new BigDecimal(1));

        dto.setAbsent(new BigDecimal(3));
        dto.setViolat(new Long(99));
        return dto;
    }


    public static void main(String[] args) {
        VWWorkArea w1 = new VWWorkArea();
        VwTcListBaseTest workArea = new VwTcListBaseTest();

        w1.addTab("Weekly Report", workArea);
        TestPanel.show(w1);

        Parameter param = new Parameter();
        param.put(DtoTcKey.BEGIN_PERIOD, new Date(105,11,3));
        param.put(DtoTcKey.END_PERIOD, new Date(105,11,9));
        param.put(DtoTcKey.ACNT_RID, new Long(1));
        workArea.setParameter(param);
        workArea.refreshWorkArea();
    }

}
