package client.essp.pwm.wp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pwm.wp.DtoPwWprev;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import com.wits.util.TestPanel;
import com.wits.util.comDate;
import org.xml.sax.SAXException;
import validator.Validator;
import validator.ValidatorResult;

public class FPW01050Review extends FPW01050ReviewBase {
    private String actionId = "FPW01050ReviewAction";

    //input parameters
    private String inWpId = "";

    //define control variable
    private boolean isSaveOk = true;

    //define common data (globe)
    private DtoPwWprev dtoPwWprev = new DtoPwWprev();
    private Validator validator;
    JButton btnSave;

    /////// ��1��������棺������棨����ؼ������ÿؼ����ƣ�������˳�򣩡���������¼�

    /**
     * default constructor
     */
    public FPW01050Review() {
        super();

        addUICEvent();

        try {
            /**
             * ����Validator������Ҫ������������һ��������validator��XML�ļ���
             * �ڶ��������Ǵ�����Ϣ�����ļ���
             * ��ע��:1)����������·��������classRootΪ��·����
             *         ��ͬ���ǵ�һ��������Ҫ��"/"��ʼ��
             *       2)�ڶ���������ĩβ��Ҫ����".properties"
             *
             */
            validator = new Validator(
                //"/client/essp/pwm/wp/validator_PwWpReview.xml",
                "/client/essp/pwm/wp/validator.xml",
                "client/essp/pwm/wp/validator");

            /*
             * ��װvalidator
             */
            VWUtil.installValidator(this, validator, dtoPwWprev.getClass());

            /**
             * ��UI��ȡDTO�ĳ�ʼֵ
             */
            VWUtil.convertUI2Dto(dtoPwWprev, this, VWUtil.NOT_SET_FLAG);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }

        setEnabledModel();
        setButtonVisible();
    }

    public FPW01050Review(Validator validator) {
        super();
        addUICEvent();

        this.validator = validator;
        VWUtil.installValidator(this, validator, dtoPwWprev.getClass());
        VWUtil.convertUI2Dto(dtoPwWprev, this, VWUtil.NOT_SET_FLAG);

        setEnabledModel();
        setButtonVisible();
    }

    private void addUICEvent() {
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });

        numWpDefectRmn.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    keyPressedNumWpDefectRmn();
                }
            }
        });

        numWpDefectRmn.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                focusLostNumWpDefectRmn();
            }

            public void focusGained(FocusEvent e) {
            }
        });

        ralWprevWkyield.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    keyPressedRalWprevWkyield();
                }
            }
        });

        numWprevQuality.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                focusLostNumWprevQuality();
            }

            public void focusGained(FocusEvent e) {
            }
        });

        numWprevQuality.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    keyPressedNumWpRevQuality();
                }
            }
        });

        numWprevTime.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                focusLostNumWprevTime();
            }

            public void focusGained(FocusEvent e) {
            }
        });

        numWprevTime.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    keyPressedNumWprevTime();
                }
            }
        });

        numWprevCost.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                focusLostNumWprevCost();
            }

            public void focusGained(FocusEvent e) {
            }
        });
        numWprevCost.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    keyPressedNumWprevCost();
                }
            }
        });
    }

    /////// ��2�����ò�������ȡ�������
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.inWpId = StringUtil.nvl(param.get("inWpId"));
    }

    /////// ��3����ȡ���ݲ�ˢ�»���
    protected void resetUI() {
        setEnabledModel();
        setButtonVisible();

        InputInfo inputInfo = new InputInfo();
        inputInfo.setFunId("SELECT");
        inputInfo.setActionId(this.actionId);
        try {
            inputInfo.setInputObj("inWpId", new Long(this.inWpId));
        } catch (NumberFormatException ex) {
            return;
        }

        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            this.dtoPwWprev = (DtoPwWprev) returnInfo.getReturnObj("dtoPwWprev");

            VWUtil.bindDto2UI(dtoPwWprev, this);
            caculateValues();

//                comFORM.setFocus( this.numWpDefectRmn );
            this.numWpDefectRmn.requestFocus();

            setEnabledModel();
            setButtonVisible();
        }
    }

    private void setEnabledModel(){
        if( StringUtil.nvl(this.inWpId).equals("") == true
            || this.dtoPwWprev.isIsAssignBy() == false){
            VWUtil.setUIEnabled(this, false);
        }else{
            VWUtil.setUIEnabled(this, true);
        }
    }

    private void setButtonVisible(){
        if( StringUtil.nvl(this.inWpId).equals("") == true
            || this.dtoPwWprev.isIsAssignBy() == false){
            btnSave.setEnabled(false);
        }else{
            btnSave.setEnabled( true);
        }
    }


    /////// ��4���¼�����
    public void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            if (validateData() == true) {
                saveData();
            }
        }
    }

    ///���粻��validate.xml������һ���ؼ��Ļ�����enter���ᱨ�쳣��
    //���⣬ȱ����֤��Χ��validator
    public void keyPressedNumWpDefectRmn() {
        //        long wpDefectRmn = numWpDefectRmn.getValue();
        //        if (wpDefectRmn < 0) {
        //            comMSG.dispErrorDialog( "����ȱ��������ڵ���0.");
        //            numWpDefectRmn.requestFocus();
        //        }
    }

    public void keyPressedRalWprevWkyield() {
        //        double wprevWkyield = ralWprevWkyield.getValue();
        //        if (wprevWkyield < 0 || wprevWkyield > 100) {
        //            comMSG.dispErrorDialog( "����Ч������ڵ���0.");
        //            ralWprevWkyield.requestFocus();
        //        }
        //        ValidatorResult result = validator.validate( dtoPwWprev, "wprevWkyield" );
        //        this.dtoPwWprev.setWprevWkyield( new BigDecimal( this.ralWprevWkyield.getValue() ) );
        //        ValidatorResult result = validator.validate( dtoPwWprev, "wprevWkyield" );
        //
        //        if( result.isValid() == false ){
        //            comMSG.dispErrorDialog( result.getAllMsg()[0]);
        //            ralWprevWkyield.requestFocus();
        //        }
    }

    public void keyPressedNumWpRevQuality() {
//        long wprevQuality = numWprevQuality.getValue();
//
//        if ( (wprevQuality < 0) || (wprevQuality > 100)) {
//            comMSG.dispErrorDialog("Ʒ����������ڵ���0��С�ڵ���100.");
//            numWprevQuality.requestFocus();
//        }
    }

    public void keyPressedNumWprevTime() {
//        long wprevTime = numWprevTime.getValue();
//
//        if ( (wprevTime < 0) || (wprevTime > 100)) {
//            comMSG.dispErrorDialog("ʱ����������ڵ���0��С�ڵ���100.");
//            numWprevTime.requestFocus();
//        }
    }

    public void keyPressedNumWprevCost() {
//        long wprevCost = numWprevCost.getValue();
//
//        if ( (wprevCost < 0) || (wprevCost > 100)) {
//            comMSG.dispErrorDialog("�ɱ���������ڵ���0��С�ڵ���100.");
//            numWprevCost.requestFocus();
//        }
    }

    public void focusLostNumWpDefectRmn() {
        caculateWprevWkyield();
    }

    public void focusLostNumWprevQuality() {
        caculateWprevPerformance();
    }

    public void focusLostNumWprevTime() {
        caculateWprevPerformance();
    }

    public void focusLostNumWprevCost() {
        caculateWprevPerformance();
    }

    private void caculateValues() {
        caculateWprevWkyield();
        caculateWpRaviFinish();
        caculateWpVariWkhr();
    }

    private void caculateWprevWkyield() {
        if (this.ralWprevWkyield.getText().equals("") == true) {
            //����Ч��(Work Yield��=���ų�ȱ��/�����ų�ȱ��+����ȱ�ݣ�
            double wpDefectRmv = dpnWpDefectRmv.getValue();
            double wpDefectRmn = numWpDefectRmn.getValue();
            double wprevWkyield = ralWprevWkyield.getValue();

            if ((wpDefectRmv + wpDefectRmn) != 0) {
                wprevWkyield = wpDefectRmv / (wpDefectRmv + wpDefectRmn);
            }

            this.ralWprevWkyield.setValue(wprevWkyield);
        }
    }

    private void caculateWpRaviFinish() {
        String wpPlanFinish = dpdWpPlanFinish.getValueText();
        String wpActFinish = dpdWpActFinish.getValueText();
        Date date1 = comDate.toDate(wpPlanFinish);
        Date date2 = comDate.toDate(wpActFinish);
        long wpVariFinish = 0;

        if ((date1 != null) && (date2 != null)) {
            long i = date1.getTime() - date2.getTime();
            wpVariFinish = i / (1000 * 60 * 60 * 24);
        }

        this.dpnWpVariFinish.setValue(wpVariFinish);
    }

    private void caculateWpVariWkhr() {
        double wpPlanWkhr = dpnWpPlanWkhr.getValue();
        double wpActWkhr = dpnWpActWkhr.getValue();
        double wpVariWkhr = wpPlanWkhr - wpActWkhr;
        dpnWpVariWkhr.setValue(wpVariWkhr);
    }

    private void caculateWprevPerformance() {
        //���˹�����Ч���ۺ����֣�Performance��= 0.5*Time+0.3*Quality+0.2*Cost
        long wprevTime = numWprevTime.getValue();
        long wprevQuality = numWprevQuality.getValue();
        long wprevCost = numWprevCost.getValue();
        long wprevPerformance = (long) ((0.5 * wprevTime)
                                        + (0.3 * wprevQuality) +
                                        (0.2 * wprevCost));
        this.dpnWprevPerformance.setValue(wprevPerformance);
    }

    /////// ��5����������
    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the review of work package?") == true) {
                if (validateData() == true) {
                    isSaveOk = saveData();
                }
            }else{

                //�û�ѡ��"������",��ΪisSaveOk=true
                isSaveOk = true;
                resetUI();
            }
        }else{
            isSaveOk = true;
        }
    }

    public void saveWorkAreaDirect() {
        if (checkModified()) {
            isSaveOk = false;
            if (validateData() == true) {
                isSaveOk = saveData();
            }
        } else {
            isSaveOk = true;
        }
    }

    public boolean isSaveOk(){
        return this.isSaveOk;
    }


    /**
     *
     * @return boolean ���Ķ���ʱ����true�����򷵻�false
     */
    public boolean checkModified() {
        VWUtil.convertUI2Dto(dtoPwWprev, this);

        return dtoPwWprev.isChanged();
    }

    public boolean validateData() {
        boolean bValid = true;
        ValidatorResult result = VWUtil.validate(this, dtoPwWprev, validator);
        bValid = result.isValid();
        if (bValid == false) {
            StringBuffer msg = new StringBuffer();
            for (int i = 0; i < result.getAllMsg().length; i++) {
                msg.append(result.getAllMsg()[i]);
                msg.append("\r\n");
            }
            comMSG.dispErrorDialog(msg.toString());
        }

        return bValid;

//        long wpDefectRmn = numWpDefectRmn.getValue();
//
//        if (wpDefectRmn < 0) {
//            comMSG.dispErrorDialog("����ȱ��������ڵ���0.");
//            numWpDefectRmn.requestFocus();
//
//            return false;
//        }
//
//        double wprevWkyield = ralWprevWkyield.getValue();
//
//        if ( (wprevWkyield < 0) || (wprevWkyield > 100)) {
//            comMSG.dispErrorDialog("����Ч������ڵ���0.");
//            ralWprevWkyield.requestFocus();
//
//            return false;
//        }
//
//        long wprevQuality = numWprevQuality.getValue();
//
//        if ( (wprevQuality < 0) || (wprevQuality > 100)) {
//            comMSG.dispErrorDialog("Ʒ����������ڵ���0��С�ڵ���100.");
//            numWprevQuality.requestFocus();
//
//            return false;
//        }
//
//        long wprevCost = numWprevCost.getValue();
//
//        if ( (wprevCost < 0) || (wprevCost > 100)) {
//            comMSG.dispErrorDialog("�ɱ���������ڵ���0��С�ڵ���100.");
//            numWprevCost.requestFocus();
//
//            return false;
//        }
//
//        long wprevTime = numWprevTime.getValue();
//
//        if ( (wprevTime < 0) || (wprevTime > 100)) {
//            comMSG.dispErrorDialog("ʱ����������ڵ���0��С�ڵ���100.");
//            numWprevTime.requestFocus();
//
//            return false;
//        }

//        return bRet;
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setFunId("UPDATE");
        inputInfo.setInputObj("dtoPwWprev", dtoPwWprev);
        inputInfo.setActionId(this.actionId);

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            this.dtoPwWprev = (DtoPwWprev) returnInfo.getReturnObj("dtoPwWprev");
            VWUtil.bindDto2UI(this.dtoPwWprev, this);
//            caculateValues();

            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        FPW01050Review _VWPwWpReview = new FPW01050Review();

        Parameter param = new Parameter();
        param.put("inWpId", "509");
        _VWPwWpReview.setParameter(param);

        _VWPwWpReview.refreshWorkArea();
        TestPanel.show(_VWPwWpReview);
    }
}
