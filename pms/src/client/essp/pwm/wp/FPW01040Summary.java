package client.essp.pwm.wp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.JButton;

import c2s.dto.FileInfo;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pwm.wp.DtoPwWpsum;
import client.framework.model.VMComboBox;
import client.framework.view.common.comFORM;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWUtil;
import com.wits.util.Parameter;
import com.wits.util.StringUtil;
import com.wits.util.TestPanel;
import org.xml.sax.SAXException;
import validator.Validator;
import validator.ValidatorResult;
import c2s.dto.DtoUtil;

public class FPW01040Summary extends FPW01040SummaryBase {
    private String actionId = "FPW01040SummaryAction";

    //input parameters
    private String inWpId = "";

    //define control variable
    private boolean isSaveOk = true;

    //define common data (globe)
    private DtoPwWpsum dtoPwWpsum = new DtoPwWpsum();
    private Validator validator;
    private Vector selTechtype = new Vector();
    JButton btnSave;
    FileInfo fileInfo = null;

    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件

    /**
     * default constructor
     */
    public FPW01040Summary() {
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
                //"/client/essp/pwm/wp/validator_PwWpSummary.xml",
                "/client/essp/pwm/wp/validator.xml",
                "client/essp/pwm/wp/validator");

            /*
             * 安装validator
             */
            VWUtil.installValidator(this, validator, dtoPwWpsum.getClass());

            /**
             * 从UI获取DTO的初始值
             */
            VWUtil.convertUI2Dto(dtoPwWpsum, this, VWUtil.NOT_SET_FLAG);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }


        setEnabledModel();
        setButtonVisible();
    }

    public FPW01040Summary(Validator validator) {
        super();

        addUICEvent();

        this.validator = validator;
        VWUtil.installValidator(this, validator, dtoPwWpsum.getClass());
        VWUtil.convertUI2Dto(dtoPwWpsum, this, VWUtil.NOT_SET_FLAG);

        setEnabledModel();
        setButtonVisible();
    }

    private void addUICEvent() {
        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedSave(e);
            }
        });

        ralWpSizeAct.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                focusLostRalWpSizeAct();
            }

            public void focusGained(FocusEvent e) {
            }
        });

        numWpDensityAct.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                focusLostNumWpDensityAct();
            }

            public void focusGained(FocusEvent e) {
            }
        });

        numWpDefectAct.addFocusListener(new FocusListener() {
            public void focusLost(FocusEvent e) {
                focusLostNumWpDefectAct();
            }

            public void focusGained(FocusEvent e) {
            }
        });
    }

    /////// 段2，设置参数：获取传入参数

    /**
     * setParameter
     * @param param Parameter
     */
    public void setParameter(Parameter param) {
        super.setParameter(param);
        this.inWpId = StringUtil.nvl(param.get("inWpId"));
     }

     /////// 段3，获取数据并刷新画面
     protected void resetUI() {
         InputInfo inputInfo = new InputInfo();
         inputInfo.setInputObj("inWpId", new Long(inWpId));

         inputInfo.setFunId("SELECT");
         inputInfo.setActionId(this.actionId);

         ReturnInfo returnInfo = accessData(inputInfo);

         if (returnInfo.isError() == false) {
             this.selTechtype = (Vector) returnInfo.getReturnObj("sel_Techtype");
             setTechtype();


             this.dtoPwWpsum = (DtoPwWpsum) returnInfo.getReturnObj("dtoPwWpsum");
             txtWpFilename.setAcntCode(dtoPwWpsum.getAcntCode());
             VWUtil.bindDto2UI(dtoPwWpsum, this);

             caculateValues();

             comFORM.setFocus(this.cmbWpTechtype);
         }

         setEnabledModel();
         setButtonVisible();
     }

    private void setEnabledModel(){
        if( StringUtil.nvl(this.inWpId).equals("") == true ){
            VWUtil.setUIEnabled(this, false);
        }else{
            VWUtil.setUIEnabled(this, true);
        }
    }

    private void setButtonVisible(){
        if( StringUtil.nvl(this.inWpId).equals("") == true ){
            btnSave.setEnabled(false);
        }else{
            btnSave.setEnabled( true);
        }
    }

    private void setTechtype() {
        if (this.selTechtype != null) {
            VMComboBox vmComboBox = new VMComboBox(this.selTechtype.toArray());
            vmComboBox.addNullElement();
            this.cmbWpTechtype.setVMComboBox(vmComboBox);
        }
    }

    /////// 段4，事件处理

    /**
     * Event handle
     */
    public void actionPerformedSave(ActionEvent e) {
        log.info(txtWpFilename.paramString());
        if (checkModified()) {
            log.info(DtoUtil.dumpBean(this.dtoPwWpsum));
            if (validateData() == true) {
                saveData();
            }
        }
    }

    public void focusLostRalWpSizeAct() {
        caculateWpSizeVari();
        caculateWpProductivityAct();
        caculateWpDensityrateAct();
        caculateWpDefectrateAct();
    }

    public void focusLostNumWpDensityAct() {
        caculateWpDensityrateAct();
        caculateWpDensityVari();
    }

    public void focusLostNumWpDefectAct() {
        caculateWpDefectrateAct();
        caculateWpDefectVari();
    }

    private void caculateValues() {
        caculateWpSizeVari();
        caculateWpWkhrVari();
        caculateWpProductivityVari();
        caculateWpDensityrateVari();
        caculateWpDefectrateVari();
        caculateWpDensityVari();
        caculateWpDefectVari();
    }

    private void caculateWpSizeVari() {
        //size variance
        double wpSizePlan = ralWpSizePlan.getValue();
        double wpSizeAct = ralWpSizeAct.getValue();
        double wpSizeVari = wpSizePlan - wpSizeAct;
        this.ralWpSizeVari.setValue(wpSizeVari);
    }

    private void caculateWpWkhrVari() {
        //size variance
        double wpPlanWkhr = ralWpPlanWkhr.getValue();
        double wpActWkhr = ralWpActWkhr.getValue();
        double wpVariWkhr = wpPlanWkhr - wpActWkhr;
        this.ralWpVariWkhr.setValue(wpVariWkhr);
    }


    private void caculateWpProductivityAct() {
        //( Productivity actual )=(  size actual ) * 1000 / ( Work Hours actual )
        double wpSizeAct = ralWpSizeAct.getValue();
        double wpActWkhr = ralWpActWkhr.getValue();
        double wpProductivityAct = 0;

        if (wpActWkhr != 0) {
            wpProductivityAct = (wpSizeAct * 1000) / wpActWkhr;
        }
        BigDecimal bWpProductivityAct = new BigDecimal(wpProductivityAct);
        bWpProductivityAct = bWpProductivityAct.setScale(2,
            BigDecimal.ROUND_CEILING);
        this.ralWpProductivityAct.setValue(bWpProductivityAct.doubleValue());

        caculateWpProductivityVari();
    }

    private void caculateWpProductivityVari() {
        //Productivity vari
        double wpProductivityPlan = ralWpProductivityPlan.getValue();
        double wpProductivityAct = ralWpProductivityAct.getValue();
        double wpProductivityVari = wpProductivityPlan
                                    - wpProductivityAct;
        this.ralWpProductivityVari.setValue(wpProductivityVari);
    }

    private void caculateWpDensityrateAct() {
        //（Density Rate  Actual) = ( Density Actual ) / ( Size Actual )
        double wpSizeAct = ralWpSizeAct.getValue();
        long wpDensityAct = this.numWpDensityAct.getValue();
        double wpDensityrateAct = 0;

        if (wpSizeAct != 0) {
            wpDensityrateAct = wpDensityAct / wpSizeAct;
        }

        this.ralWpDensityrateAct.setValue(wpDensityrateAct);
        caculateWpDensityrateVari();
    }

    private void caculateWpDensityrateVari() {
        //density rate vari
        double wpDensityratePlan = ralWpDensityratePlan.getValue();
        double wpDensityrateAct = ralWpDensityrateAct.getValue();
        double wpDensityrateVari = wpDensityratePlan - wpDensityrateAct;
        this.ralWpDenstityrateVari.setValue(wpDensityrateVari);
    }

    private void caculateWpDefectrateAct() {
        //(Defect Rate ACT ) =  ( Defect ACT) / ( Size ACT )
        double wpSizeAct = ralWpSizeAct.getValue();
        long wpDefectAct = this.numWpDefectAct.getValue();
        double wpDefectrateAct = 0;

        if (wpSizeAct != 0) {
            wpDefectrateAct = wpDefectAct / wpSizeAct;
        }

        this.ralWpDefectrateAct.setValue(wpDefectrateAct);
        caculateWpDefectrateVari();
    }

    private void caculateWpDefectrateVari() {
        //DEFECT rate vari
        double wpDefectratePlan = ralWpDefectratePlan.getValue();
        double wpDefectrateAct = ralWpDefectrateAct.getValue();
        double wpDefectrateVari = wpDefectratePlan - wpDefectrateAct;
        this.ralWpDefectrateVari.setValue(wpDefectrateVari);
    }

    private void caculateWpDensityVari() {
        //desity vari
        long wpDensityPlan = dpnWpDensityPlan.getValue();
        long wpDensityAct = numWpDensityAct.getValue();
        long wpDensityVari = wpDensityPlan - wpDensityAct;
        this.dpnWpDensityVari.setValue(wpDensityVari);
    }

    private void caculateWpDefectVari() {
        //defect vari
        long wpDefectPlan = dpnWpDefectPlan.getValue();
        long wpDefectAct = numWpDefectAct.getValue();
        long wpDefectVari = wpDefectPlan - wpDefectAct;
        this.dpnWpDefectVari.setValue(wpDefectVari);
    }

    /////// 段5，保存数据
    public void saveWorkArea() {
        if (checkModified()) {
            isSaveOk = false;
            if (confirmSaveWorkArea("Do you save the summary of work package?") == true) {
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
     * @return boolean 被改动过时返回true，否则返回false
     */
    public boolean checkModified() {
        VWUtil.convertUI2Dto(dtoPwWpsum, this);

        return dtoPwWpsum.isChanged();
    }

    public boolean validateData() {
        boolean bValid = true;
        ValidatorResult result = VWUtil.validate(this, dtoPwWpsum, validator);
        bValid = result.isValid();
        if (bValid == false) {
            StringBuffer msg = new StringBuffer();

            for (int i = 0; i < result.getAllMsg().length; i++) {
                msg.append(result.getAllMsg()[i]);
                msg.append("\r\n");
            }

            comMSG.dispErrorDialog(msg.toString());
            return false;
        }

        long wpDefectAct = numWpDefectAct.getValue();

        long wpDefectRmv = numWpDefectRmv.getValue();

        if (wpDefectRmv > wpDefectAct) {
            comMSG.dispErrorDialog("被消除了的缺陷数不能大于实际的缺陷数.");
            numWpDefectAct.setErrorField(true);
            numWpDefectRmv.setErrorField(true);
            numWpDefectAct.requestFocus();

            return false;
        }

        return true;
    }

    private boolean saveData() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.actionId);
        inputInfo.setFunId("UPDATE");
        inputInfo.setInputObj("dtoPwWpsum", dtoPwWpsum);
        inputInfo.setInputObj("inWpId", new Long(this.inWpId) );

        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            this.dtoPwWpsum = (DtoPwWpsum) returnInfo.getReturnObj("dtoPwWpsum");
            VWUtil.bindDto2UI(this.dtoPwWpsum, this);

            return true;
        }else{
            return false;
        }
    }

    public static void main(String[] args) {
        FPW01040Summary _VWPwWpSummary = new FPW01040Summary();

        Parameter param = new Parameter();
        param.put("inWpId", "21042");
        _VWPwWpSummary.setParameter(param);

        _VWPwWpSummary.refreshWorkArea();
        TestPanel.show(_VWPwWpSummary);
    }
}
