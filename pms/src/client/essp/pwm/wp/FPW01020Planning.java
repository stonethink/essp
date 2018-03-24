package client.essp.pwm.wp;

import java.awt.AWTEvent;
import java.io.IOException;

import client.essp.common.view.VWTDWorkArea;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import org.xml.sax.SAXException;
import validator.Validator;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class FPW01020Planning extends VWTDWorkArea {

    /**
     * input parameters
     */
    private String inWpId = "";

    /**
     * define control variable
     */
    private boolean refreshFlag = false;

    /**
     * define common data (globe)
     */

    /////// ��1��������棺������棨����ؼ������ÿؼ����ƣ�������˳�򣩡���������¼�
    private FPW01021WkItem vwPwWpWorkItem; //��һ��CARD
    private FPW01022Checkpoint vwPwWpCheckpoint; //�ڶ���CARD
    private Validator validator = null;

    /**
     * default constructor
     */
    public FPW01020Planning() {
        super(120);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        try {
            validator = new Validator("/client/essp/pwm/wp/validator.xml",
                                      "client/essp/pwm/wp/validator");
            vwPwWpWorkItem = new FPW01021WkItem(validator);
            vwPwWpCheckpoint = new FPW01022Checkpoint(validator);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }


    /**
     * default constructor
     */
    public FPW01020Planning(Validator validator) {
        super(150);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        this.validator = validator;
        vwPwWpWorkItem = new FPW01021WkItem(validator);
        vwPwWpCheckpoint = new FPW01022Checkpoint(validator);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
    }

    //Component initialization
    private void jbInit() throws Exception {
        this.getTopArea().addTab("Work Item", vwPwWpWorkItem);
        this.getDownArea().addTab("Checkpoint", vwPwWpCheckpoint);
    }

    private void addUICEvent() {

    }

    public void setParameter(Parameter param) {
        this.inWpId = (String) (param.get("inWpId"));
        this.refreshFlag = true;
    }

    public void saveWorkArea() {
        vwPwWpWorkItem.saveWorkArea();
        if( vwPwWpWorkItem.isSaveOk() ){
            vwPwWpCheckpoint.saveWorkArea();
        }
    }


    public void saveWorkAreaDirect() {
        vwPwWpWorkItem.saveWorkAreaDirect();
        if( vwPwWpWorkItem.isSaveOk() ){
            vwPwWpCheckpoint.saveWorkAreaDirect();
        }
    }

    //������workArea����Ҫˢ���Լ�
    public void refreshWorkArea() {
        if (refreshFlag == true) {
            refreshFlag = false;

            Parameter param1 = new Parameter();
            param1.put("inWpId", this.inWpId);

            vwPwWpWorkItem.setParameter(param1);

            Parameter param2 = new Parameter();
            param2.put("inWpId", this.inWpId);
            vwPwWpCheckpoint.setParameter(param2);

            //ˢ��Work Area
            vwPwWpWorkItem.refreshWorkArea();
            vwPwWpCheckpoint.refreshWorkArea();
        }
    }

    public boolean isSaveOk() {
        return this.vwPwWpWorkItem.isSaveOk()
            && this.vwPwWpCheckpoint.isSaveOk();
    }

    public static void main(String[] args) {
        FPW01020Planning vwPwWpPlanning = new FPW01020Planning();

        Parameter param = new Parameter();
        param.put("inWpId", "10007");
        vwPwWpPlanning.setParameter(param);
        TestPanel.show(vwPwWpPlanning);
        vwPwWpPlanning.refreshWorkArea();
    }
}
