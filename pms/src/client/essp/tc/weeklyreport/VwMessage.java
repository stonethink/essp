package client.essp.tc.weeklyreport;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;

import c2s.essp.tc.weeklyreport.DtoHoursOnWeek;
import c2s.essp.tc.weeklyreport.TcConstant;
import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJTextArea;
import com.wits.util.TestPanel;

public class VwMessage extends VWGeneralWorkArea {
    protected VWJTextArea lblWorker = null;

    IAllocateHourSupply allocateHourSupply = null;
    private boolean[] periodBitmap = null;
    private boolean[] workDayBitmap = null;
    private StringBuffer checkMessage = new StringBuffer();
    private StringBuffer warnMsgs = new StringBuffer();
    private StringBuffer errorMsgs = new StringBuffer();
    private int warnNum = 0;
    private int errorNum = 0;
    public VwMessage() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setLayout(null);
        setMaximumSize(new Dimension(500,130));
        setPreferredSize(new Dimension(500,130));
        setMinimumSize(new Dimension(500,130));

//        setBorder(BorderFactory.createLineBorder(Color.blue));
        setBorder(null);

        lblWorker = new VWJTextArea(){
            public void this_focusGained(FocusEvent e) {
            }
        };

        lblWorker.setBounds(new Rectangle(10, 10, 500, 130));
//        lblWorker.setBorder(BorderFactory.createLineBorder(Color.blue));
        lblWorker.setBorder(null);
        lblWorker.setBackground(Color.white);
        lblWorker.setForeground(Color.red);

        this.add(lblWorker, null);
    }

    private void clear(){
        errorMsgs.delete(0, errorMsgs.length());
        warnMsgs.delete(0, warnMsgs.length());
        errorNum = 0;
        warnNum = 0;
    }
    public void refresh(){
        //清除上一次的信息
        clear();
        //Message分waring和error两种显示
        StringBuffer sb = new StringBuffer();

        if(allocateHourSupply==null || periodBitmap==null || workDayBitmap==null){
            lblWorker.setText(null);
            setCheckMessage("");
            return;
        }

        String[] daysArray = new String[] {"Saturday", "Sunday", "Monday"
                             , "Tuesday", "Wedensday", "Thursday", "Friday"};
        for (int i = 0; i < periodBitmap.length; i++) {
            if (periodBitmap[i]) {
                String warnMsg = getWarnMessage(i);
                if (warnMsg.length()>0) {
                    warnMsgs.append("\r\n\t" + daysArray[i] + ": ");
                    warnMsgs.append(warnMsg);
                    warnNum++;
                }
                String errorMsg = getErrorMessage(i);
                if(errorMsg.length()>0) {
                    errorMsgs.append("\r\n\t" + daysArray[i] + ": ");
                    errorMsgs.append(errorMsg);
                    errorNum ++;
                }
            }
        }

        if( warnNum > 0 ){
            warnMsgs.insert(0, warnNum + " warnings: ");
        }
        if(errorNum > 0){
            errorMsgs.insert(0, errorNum + " errors: ");
        }
        sb.append(warnMsgs);
        sb.append(errorMsgs);
        if(sb.length() > 0 ){
            lblWorker.setText(sb.toString());
            lblWorker.setForeground(Color.red);
            setCheckMessage(sb.toString());
        }else{
            lblWorker.setText(null);
            setCheckMessage("");
        }
    }
    //计算某天所填周报时间与标准工时差
    private BigDecimal getMinus(int day) {
        BigDecimal rightHour = null;
        if (workDayBitmap[day]) {
            rightHour = standardHour;
        }else{
            rightHour = bigDecimal0;
        }

        BigDecimal leftHour = null;
        if (allocateHourSupply.getAllocateHour(day) != null) {
            leftHour = allocateHourSupply.getAllocateHour(day)[1].setScale(2,BigDecimal.ROUND_UP);
        } else {
            leftHour = bigDecimal0;
        }

        BigDecimal minus = leftHour.subtract(rightHour).setScale(2,BigDecimal.ROUND_UP);
        return minus;
    }
    //当每天时间小于标准工作时间时,显示waining
    private String getErrorMessage(int day){
        String msg = "";
        BigDecimal minus = getMinus(day);
        int c = minus.compareTo(bigDecimal0);
        if (c > 0) {
            msg += (minus.doubleValue() + "\thours bigger than standard hour.");
        }
        return msg;
    }
    //当每天时间大于标准工作时间时,显示waining
    private String getWarnMessage(int day){
        String msg = "";
        BigDecimal minus = getMinus(day);
        int c = minus.compareTo(bigDecimal0);
        if (c < 0) {
            msg += (minus.doubleValue() + "\thours smaller than standard hour.");
        }
        return msg;
    }


    public void setAllocateHourSupply(IAllocateHourSupply allocateHourSupply) {
        this.allocateHourSupply = allocateHourSupply;
    }
    public void setPeriodBitmap(boolean[] periodBitmap) {
        this.periodBitmap = periodBitmap;
    }
    public void setWorkDayBitmap(boolean[] workDayBitmap) {
        this.workDayBitmap = workDayBitmap;
    }

    public StringBuffer getCheckMessage(){
        return this.checkMessage;
    }

    public StringBuffer getErrorMsgs() {
        return errorMsgs;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public StringBuffer getWarnMsgs() {
        return warnMsgs;
    }

    public int getWarnNum() {
        return warnNum;
    }

    private void setCheckMessage(String msg){
        this.checkMessage.delete(0, checkMessage.length());
        this.checkMessage.append(msg);
    }

    private BigDecimal standardHour = new BigDecimal(TcConstant.STANDARD_HOUR_ONE_DAY).setScale(2,BigDecimal.ROUND_HALF_UP);
    private BigDecimal bigDecimal0 = new BigDecimal(0).setScale(2,BigDecimal.ROUND_HALF_UP);
    public static void main(String args[]) {
        VwMessage w = new VwMessage();
        TestPanel.show(w);

        //test data
        DtoHoursOnWeek hoursOnWeek = new DtoHoursOnWeek();
        boolean[] periodBitmap = new boolean[]{true, true, true, true, true, true, true};
        boolean[] workDayBitmap = new boolean[]{true, true, true, true, true, true, true};
//        hoursOnWeek.setSatHour(new BigDecimal(8));
//        hoursOnWeek.setSunHour(new BigDecimal(8));
//        hoursOnWeek.setFriHour(new BigDecimal(8));

        w.setPeriodBitmap(periodBitmap);
        w.setAllocateHourSupply(new IAllocateHourSupply(){public BigDecimal[] getAllocateHour(int day){return null;}});
        w.setWorkDayBitmap(workDayBitmap);
        w.refresh();
    }
}
