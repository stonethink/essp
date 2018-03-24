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

    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件

    /**
     * default constructor
     */
    public FPW01050Review() {
        super();

        addUICEvent();

        try {
            /**
             * 构造Validator，它需要两个参数。第一个是配置validator的XML文件，
             * 第二个参数是错误信息配置文件。
             * 请注意:1)这两个参数路径都是以classRoot为根路径，
             *         不同的是第一个参数需要以"/"开始。
             *       2)第二个参数的末尾不要加上".properties"
             *
             */
            validator = new Validator(
                //"/client/essp/pwm/wp/validator_PwWpReview.xml",
                "/client/essp/pwm/wp/validator.xml",
                "client/essp/pwm/wp/validator");

            /*
             * 安装validator
             */
            VWUtil.installValidator(this, validator, dtoPwWprev.getClass());

            /**
             * 从UI获取DTO的初始值
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

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.inWpId = StringUtil.nvl(param.get("inWpId"));
    }

    /////// 段3，获取数据并刷新画面
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


    /////// 段4，事件处理
    public void actionPerformedSave(ActionEvent e) {
        if (checkModified()) {
            if (validateData() == true) {
                saveData();
            }
        }
    }

    ///假如不在validate.xml中配置一个控件的话，按enter键会报异常。
    //另外，缺少验证范围的validator
    public void keyPressedNumWpDefectRmn() {
        //        long wpDefectRmn = numWpDefectRmn.getValue();
        //        if (wpDefectRmn < 0) {
        //            comMSG.dispErrorDialog( "遗留缺陷数须大于等于0.");
        //            numWpDefectRmn.requestFocus();
        //        }
    }

    public void keyPressedRalWprevWkyield() {
        //        double wprevWkyield = ralWprevWkyield.getValue();
        //        if (wprevWkyield < 0 || wprevWkyield > 100) {
        //            comMSG.dispErrorDialog( "过程效益须大于等于0.");
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
//            comMSG.dispErrorDialog("品质评分须大于等于0且小于等于100.");
//            numWprevQuality.requestFocus();
//        }
    }

    public void keyPressedNumWprevTime() {
//        long wprevTime = numWprevTime.getValue();
//
//        if ( (wprevTime < 0) || (wprevTime > 100)) {
//            comMSG.dispErrorDialog("时间评分须大于等于0且小于等于100.");
//            numWprevTime.requestFocus();
//        }
    }

    public void keyPressedNumWprevCost() {
//        long wprevCost = numWprevCost.getValue();
//
//        if ( (wprevCost < 0) || (wprevCost > 100)) {
//            comMSG.dispErrorDialog("成本评分须大于等于0且小于等于100.");
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
            //过程效益(Work Yield）=已排除缺陷/（已排除缺陷+遗留缺陷）
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
        //个人工作绩效的综合评分（Performance）= 0.5*Time+0.3*Quality+0.2*Cost
        long wprevTime = numWprevTime.getValue();
        long wprevQuality = numWprevQuality.getValue();
        long wprevCost = numWprevCost.getValue();
        long wprevPerformance = (long) ((0.5 * wprevTime)
                                        + (0.3 * wprevQuality) +
                                        (0.2 * wprevCost));
        this.dpnWprevPerformance.setValue(wprevPerformance);
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the review of work package?") == true) {
                if (validateData() == true) {
                    isSaveOk = saveData();
                }
            }else{

                //用户选择"不保存",认为isSaveOk=true
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
     * @return boolean 被改动过时返回true，否则返回false
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
//            comMSG.dispErrorDialog("遗留缺陷数须大于等于0.");
//            numWpDefectRmn.requestFocus();
//
//            return false;
//        }
//
//        double wprevWkyield = ralWprevWkyield.getValue();
//
//        if ( (wprevWkyield < 0) || (wprevWkyield > 100)) {
//            comMSG.dispErrorDialog("过程效益须大于等于0.");
//            ralWprevWkyield.requestFocus();
//
//            return false;
//        }
//
//        long wprevQuality = numWprevQuality.getValue();
//
//        if ( (wprevQuality < 0) || (wprevQuality > 100)) {
//            comMSG.dispErrorDialog("品质评分须大于等于0且小于等于100.");
//            numWprevQuality.requestFocus();
//
//            return false;
//        }
//
//        long wprevCost = numWprevCost.getValue();
//
//        if ( (wprevCost < 0) || (wprevCost > 100)) {
//            comMSG.dispErrorDialog("成本评分须大于等于0且小于等于100.");
//            numWprevCost.requestFocus();
//
//            return false;
//        }
//
//        long wprevTime = numWprevTime.getValue();
//
//        if ( (wprevTime < 0) || (wprevTime > 100)) {
//            comMSG.dispErrorDialog("时间评分须大于等于0且小于等于100.");
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
