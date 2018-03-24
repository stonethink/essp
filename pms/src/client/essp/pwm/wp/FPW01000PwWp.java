package client.essp.pwm.wp;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.io.IOException;

import c2s.essp.pwm.wp.DtoPwWp;
import client.essp.common.util.TestPanelParam;
import client.essp.common.view.VWWorkArea;
import client.framework.common.Global;
import client.framework.view.event.DataCreateListener;
import com.wits.util.Parameter;
import org.xml.sax.SAXException;
import validator.Validator;
import client.framework.view.event.DataChangedListener;
import c2s.dto.DtoUtil;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class FPW01000PwWp extends VWWorkArea {

    /**
     * input parameters
     */
    private DtoPwWp dtoPwWp;
    private Long inActivityId;

    /**
     * define control variable
     */
    private boolean isRefresh = false;

    /**
     * define common data (globe)
     */
    //private IVWWorkArea currCard; //��ǰ�Ŀ�Ƭ
    private FPW01010General vwPwWpGeneral = null;
    private FPW01020Planning vwPwWpPlanning = null;
    private FPW01040Summary vwPwWpSummary = null;
    private FPW01050Review vwPwWpReview = null;
    private Validator validator = null;


    /**
     * default constructor
     */
    public FPW01000PwWp() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            validator = new Validator("/client/essp/pwm/wp/validator.xml",
                                      "client/essp/pwm/wp/validator");
            vwPwWpGeneral = new FPW01010General(validator);
            vwPwWpPlanning = new FPW01020Planning(validator);
            vwPwWpSummary = new FPW01040Summary(validator);
            vwPwWpReview = new FPW01050Review(validator);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }

        try {
            jbInit();
            addUICEvent();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * ���쾲̬����
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(730, 520));

        this.addTab("General", vwPwWpGeneral, true);
        this.addTab("Planning", vwPwWpPlanning, false);
        this.addTab("Summary", vwPwWpSummary, true);
        this.addTab("Review", vwPwWpReview, true);

        setEnableMode();
    }

    private void addUICEvent() {
        /*this.addStateChangedListener(new StateChangedListener() {
            public void processStateChanged() {
                processStateChangedThis();
            }
                 });
         */


        vwPwWpGeneral.addDataCreateListener(new DataCreateListener() {
            public void processDataCreate() {
                processDataCreateVWPwWpGeneral();
            }
        });
        vwPwWpGeneral.addDataChangedListener(new DataChangedListener() {
            public void processDataChanged() {
                processDataChangedVWPwWpGeneral();
            }
        });


    }

    /**
     * setParameter
     * @param param Parameter
     */
    public void setParameter(Parameter param) {
        this.dtoPwWp = (DtoPwWp) param.get("dtoPwWp");
        this.inActivityId = (Long) (param.get("inActivityId"));

        this.isRefresh = true;
    }



    private boolean isCreateWp() {
        return this.vwPwWpGeneral.isCreateWp();
    }

    /////// ��3����ȡ���ݲ�ˢ�»���
    /**
     * refreshWorkArea
     */
    public void refreshWorkArea() {
        if (isRefresh == true) {
            //����һ����Ƭ�����
            log.info("3: fpw01000PwWp.refreshworkArea - acitivityrid is " + inActivityId
                + "\r\n dtoPwWp is " + DtoUtil.dumpBean(dtoPwWp));
            this.vwPwWpGeneral.setParameter(dtoPwWp, inActivityId);

            if (isCreateWp() == false) {
                String wpId = this.vwPwWpGeneral.getDto().getRid().toString();
                Parameter param2 = new Parameter();
                param2.put("inWpId", wpId);
                this.vwPwWpPlanning.setParameter(param2);

                Parameter param3 = new Parameter();
                param3.put("inWpId", wpId);
                this.vwPwWpSummary.setParameter(param3);

                //same with summary
                this.vwPwWpReview.setParameter(param3);
            }else{

                //������wp��ѡ�е�һ����Ƭ
                this.setSelectedIndex(0);
            }


            setEnableMode();

            this.getSelectedWorkArea().refreshWorkArea();
        }
    }

    /*
         public void processStateChangedThis() {
        int selIndex = this.getSelectedIndex();
        if (selIndex < 0) {
            selIndex = 0;
        }
        this.setSelectedCard(selIndex);
         }
     */

    public void processDataChangedVWPwWpGeneral() {
        //ˢ��summary
        String wpId = this.vwPwWpGeneral.getDto().getRid().toString();

        Parameter param4 = new Parameter();
        param4.put("inWpId", wpId);
        vwPwWpSummary.setParameter(param4);
    }

    public void processDataCreateVWPwWpGeneral() {
        //���¼����½�WP�󷢳�
        //�½�WP�ɹ���,ʹ�ÿ����޸�planning,summary,review
        setEnableMode();

        String wpId = this.vwPwWpGeneral.getDto().getRid().toString();
        //set parameter
        Parameter param2 = new Parameter();
        param2.put("inWpId", wpId);
        vwPwWpPlanning.setParameter(param2);

        Parameter param4 = new Parameter();
        param4.put("inWpId", wpId);
        vwPwWpSummary.setParameter(param4);

        Parameter param5 = new Parameter();
        param5.put("inWpId", wpId);
        vwPwWpReview.setParameter(param5);
    }

    /**
     * setEnableMode
     */
    private void setEnableMode() {
        //������½�WP,��ô�Ͳ����޸�planning,summary,review
        if (isCreateWp() == true) {
            setEnabledAt(0, true);
            setEnabledAt(1, false);
            setEnabledAt(2, false);
            setEnabledAt(3, false);
        } else {
            setEnabledAt(0, true);
            setEnabledAt(1, true);
            setEnabledAt(2, true);
            setEnabledAt(3, true);
        }
    }


    public void saveWorkArea() {
        this.getSelectedWorkArea().saveWorkArea();
    }

    public void saveWorkAreaDirect() {
        int i = getSelectedIndex();
        switch(i){
        case 0:
            vwPwWpGeneral.saveWorkAreaDirect();
            break;
        case 1:
            vwPwWpPlanning.saveWorkAreaDirect();
            break;
        case 2:
            vwPwWpReview.saveWorkAreaDirect();
            break;
        case 3:
            vwPwWpSummary.saveWorkAreaDirect();
            break;
        }
    }

    public boolean isSaveOk() {
        return this.getSelectedWorkArea().isSaveOk();
    }


    //  /**
     //   * Event handle
     //   */
    //  private void processStateChanged_this() {
    //    setSelectedCard(getSelectedIndex());
    //  }
    //���õ�ǰ��ʾ��detail panel
    /*private void setSelectedCard(int index) {
        switch (index) {
        case 0:
            this.currCard = vwPwWpGeneral;
            break;
        case 1:
            this.currCard = vwPwWpPlanning;
            break;
        case 2:
            this.currCard = vwPwWpSummary;
            break;
        case 3:
            this.currCard = vwPwWpReview;
            break;
        default:
            this.currCard = vwPwWpGeneral;
        }

        this.currCard.refreshWorkArea();
         }
     */

    public FPW01010General getGeneralWorkArea() {
        return this.vwPwWpGeneral;
    }

    public static void main(String[] args) {
        FPW01000PwWp vwPwWp = new FPW01000PwWp();

        Parameter param = new Parameter();
        param.put("dtoPwWp", new DtoPwWp());
        param.put("activityRid", new Long(10060));

        Global.userId = "stone.shi";
        Global.userName = "stone.shi";
        Global.todayDateStr = "20051018";
        Global.todayDatePattern = "yyyyMMdd";
        vwPwWp.setParameter(param);
        vwPwWp.refreshWorkArea();

        vwPwWp.setParameter(param);
        TestPanelParam.show(vwPwWp);
    }
}
