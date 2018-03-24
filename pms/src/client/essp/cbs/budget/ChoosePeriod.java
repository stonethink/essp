package client.essp.cbs.budget;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Date;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.Parameter;

public class ChoosePeriod extends VWGeneralWorkArea implements
    IVWPopupEditorEvent{
    private boolean isOK = true;
    public ChoosePeriod() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void jbInit() throws Exception {
        this.setLayout(null);
        setPreferredSize(new Dimension(350,50));

        lbBegin.setText("Begin Date");
        lbBegin.setBounds(new Rectangle(18, 20, 67, 22));
        lbEnd.setText("End Date");
        lbEnd.setBounds(new Rectangle(201, 20, 62, 23));

        inputBegin.setCanSelect(true);
        inputBegin.setDataType(VWJDate._DATA_PRO_YM);
        inputBegin.setBounds(new Rectangle(89, 20, 80, 20));

        inputEnd.setBounds(new Rectangle(261, 20, 80, 20));
        inputEnd.setCanSelect(true);
        inputEnd.setDataType(VWJDate._DATA_PRO_YM);
        this.add(lbBegin);
        this.add(inputBegin);
        this.add(inputEnd);
        this.add(lbEnd);
    }

    VWJLabel lbBegin = new VWJLabel();
    VWJLabel lbEnd = new VWJLabel();
    VWJDate inputBegin = new VWJDate();
    VWJDate inputEnd = new VWJDate();

    public Date getBeginDate(){
        return (Date) inputBegin.getUICValue();
    }

    public Date getEndDate(){
        return (Date) inputEnd.getUICValue();
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        Object beginDate =  param.get("beginDate");
        Object endDate =  param.get("endDate");
        inputBegin.setUICValue(beginDate);
        inputEnd.setUICValue(endDate);
        isOK = false;
    }
    public boolean isOK(){
        return isOK;
    }
    public boolean onClickOK(ActionEvent e) {
        Date beginDate = (Date) inputBegin.getUICValue();
        Date endDate = (Date) inputEnd.getUICValue();
        if(beginDate == null || endDate == null){
            isOK = false;
            comMSG.dispErrorDialog("Please fill begin and end date!");
            return false;
        }
        if(beginDate.getTime() > endDate.getTime()){
            isOK = false;
            comMSG.dispErrorDialog("The beginDate can not be larger than endDate!");
            return false;
        }
        isOK = true;
        return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        isOK = false;
        return true;
    }
}
