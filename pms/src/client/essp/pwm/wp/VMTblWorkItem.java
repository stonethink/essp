package client.essp.pwm.wp;

import java.math.BigDecimal;

import c2s.essp.pwm.wp.DtoPwWkitem;

import client.framework.model.VMColumnConfig;
import client.framework.model.VMTable2;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJDisp;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.comDate;
import validator.Validator;
import validator.ValidatorResult;
import java.util.Date;
import c2s.essp.pwm.PwmUtil;
import client.essp.common.loginId.VWJLoginId;

public class VMTblWorkItem extends VMTable2 {


    private Validator validator = null;

    public VMTblWorkItem(Class dtoClass) {
        //        super(tblConfig);
        super.setDtoType(dtoClass);

        try {
            /**
             * 构造Validator，它需要两个参数。第一个是配置validator的XML文件，
             * 第二个参数是错误信息配置文件。
             * 请注意:1)这两个参数路径都是以classRoot为根路径，
             *         不同的是第一个参数需要以"/"开始。
             *       2)第二个参数的末尾不要加上".properties"
             *
             */
            validator = new Validator("/client/essp/pwm/wp/validator.xml",
                                      "client/essp/pwm/wp/validator");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setColumnConfig();
    }

    public VMTblWorkItem(Class dtoClass, Validator validator) {
        //        super(tblConfig);
        super.setDtoType(dtoClass);

        this.validator = validator;
        setColumnConfig();
    }

    private void setColumnConfig() {
        VWJText vwjtext = new VWJText();
         VWJDisp vwjdisp = new VWJDisp();
         VWJDate vwjdate1 = new VWJDate();
         VWJDate vwjdate2 = new VWJDate();
         VWJDate vwjdate3 = new VWJDate();
         VWJReal vwjreal = new VWJReal();

         vwjdate1.setDataType("YYYYMMDD");
         vwjdate1.setCanSelect(true);
         vwjdate2.setDataType("HHMM");
         vwjdate3.setDataType("HHMM");
         vwjreal.setMaxInputIntegerDigit(4);
         vwjreal.setMaxInputDecimalDigit(2);

         vwjtext.setValidator(validator);
         vwjdate1.setValidator(validator);
         vwjdate2.setValidator(validator);
        vwjdate3.setValidator(validator);


        Object[][] tblConfig = { {"Work Item", "wkitemName", VMColumnConfig.EDITABLE, vwjtext}
                               , {"Owner", "wkitemOwner", VMColumnConfig.UNEDITABLE, new VWJLoginId()}
                               , {"Date", "wkitemDate", VMColumnConfig.EDITABLE, vwjdate1}
                               , {"Start Time", "wkitemStarttime", VMColumnConfig.EDITABLE, vwjdate2}
                               , {"Finish Time", "wkitemFinishtime", VMColumnConfig.EDITABLE, vwjdate3}
                               , {"Work Hours", "wkitemWkhours", VMColumnConfig.UNEDITABLE, vwjreal}
        };

        super.setColumnConfigs(tblConfig);
    }

    public void setValueAt(Object value,
                           int row,
                           int col) {
        String sColName = this.getColumnName(col);

        if (sColName.equals("Start Time") || (sColName.equals("Finish Time"))) {
            super.setValueAt(value, row, col);

            DtoPwWkitem dto = (DtoPwWkitem)this.getRow(row);
            BigDecimal wkhr = PwmUtil.computeWorkHours(dto);

            super.setValueAt(wkhr, row, 5);
        } /*else if (sColName.equals("Work Hours") == true) {

        } */else {
            super.setValueAt(value, row, col);
        }
    }

    public ValidatorResult validateData() {
        ValidatorResult validatorResult = new ValidatorResult();

        for (int i = 0; i < this.getRowCount(); i++) {
            DtoPwWkitem dtoBean = (DtoPwWkitem)this.getRow(i);
            ValidatorResult validatorResultRow = validator.validate(dtoBean);

            String[] msgs = validatorResultRow.getAllMsg();
            if (msgs != null) {
                for (int j = 0; j < msgs.length; j++) {
                    validatorResult.addMsg("Row " + (i + 1) + " :" + msgs[j]);
                }
            }
        }

        //关联检查
        for (int i = 0; i < this.getRowCount(); i++) {

            DtoPwWkitem dtoBean = (DtoPwWkitem)this.getRow(i);
            Date start = dtoBean.getWkitemStarttime();
            Date finish = dtoBean.getWkitemFinishtime();
            if (start != null && finish != null) {
                int sH = start.getHours();
                int sM = start.getMinutes();
                int fH = finish.getHours();
                int fM = finish.getMinutes();

                if (sH * 60 + sM > fH * 60 + fM ) {
                    validatorResult.addMsg("Row " + (i + 1) +
                                           " :start time > finish time.");

                }
            }
        }

        return validatorResult;
    }


}
