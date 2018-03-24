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
             * ����Validator������Ҫ������������һ��������validator��XML�ļ���
             * �ڶ��������Ǵ�����Ϣ�����ļ���
             * ��ע��:1)����������·��������classRootΪ��·����
             *         ��ͬ���ǵ�һ��������Ҫ��"/"��ʼ��
             *       2)�ڶ���������ĩβ��Ҫ����".properties"
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

        //�������
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
