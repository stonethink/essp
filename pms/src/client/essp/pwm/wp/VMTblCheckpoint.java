package client.essp.pwm.wp;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import c2s.essp.pwm.wp.DtoPwWpchk;
import client.framework.model.VMColumnConfig;
import client.framework.model.VMComboBox;
import client.framework.model.VMTable2;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.vwcomp.VWJText;
import client.image.ComImage;
import validator.Validator;
import validator.ValidatorResult;

public class VMTblCheckpoint extends VMTable2 {


    private static String[] assignByStatus = {"Waiting Check", "Delivered",
                                             "Rejected", "Closed"};
    private static String[] noAssignByStatus = {"Waiting Check", "Delivered"};

    private boolean isAssignBy = false;
    Validator validator = null;
    VWJButton btnLog = new VWJButton();

    /**
     * 记录两条默认的checkpoint，这两条将不能被修改名字，不能被删除
     */
    private Long defaultCheckpoint1;
    private Long defaultCheckpoint2;

    public VMTblCheckpoint(Class dtoClass) {
        //super(tblConfig);
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
            validator = new Validator(
                //"/client/essp/pwm/wp/validator_Wkitem.xml",
                "/client/essp/pwm/wp/validator.xml",
                "client/essp/pwm/wp/validator");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setColumnConfig();
    }

    public VMTblCheckpoint(Class dtoClass, Validator validator, ActionListener actionListener) {
        //super(tblConfig);
        super.setDtoType(dtoClass);

        this.validator = validator;
        btnLog.addActionListener(actionListener);
        setColumnConfig();
    }

    private void setColumnConfig(){
        VWJText txtName = new VWJText();
        VWJDate dteDate = new VWJDate();
        VWJComboBox cmbStatus = new VWJComboBox();

        txtName.setValidator(this.validator);
        dteDate.setDataType("YYYYMMDD");
        dteDate.setCanSelect(true);
        dteDate.setValidator(this.validator);
        VMComboBox vmcomboBox = VMComboBox.toVMComboBox(assignByStatus);
        cmbStatus.setModel(vmcomboBox);
        cmbStatus.setValidator(this.validator);

        btnLog.setIcon(new ImageIcon( ComImage.getImage("update.png")) );

        Object[][] tblConfig = { {"Check Point", "wpchkName", VMColumnConfig.EDITABLE, txtName}
                               , {"Date", "wpchkDate", VMColumnConfig.EDITABLE, dteDate}
                               , {"Status", "wpchkStatus", VMColumnConfig.EDITABLE, cmbStatus}
                               , {"Logs", "Logs", VMColumnConfig.EDITABLE, btnLog}
        };
        this.setColumnConfigs(tblConfig);
    }

    public boolean isCellEditable(int row, int col) {
        DtoPwWpchk dto = (DtoPwWpchk)this.getRow(row);
        if (isDefaultCheckpoint(dto) == true && col == 0) {
            return false;
        } else if (col == 2) {

            if (isAssignBy == false) {

                //被分派者不能修改状态为closed和rejected的checkpoint
                Object value = getValueAt(row, col);
                try {
                    if (value != null && ((String) value).equals("Rejected") == false
                        && ((String) value).equals("Closed") == false) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception ex) {
                    return false;
                }
            } else {
                return true;
            }
        }else if( col == 3){
            if( dto.isInsert() || dto.isDelete() ){
                return false;
            }else{
                return true;
            }
        }else {
            return true;
        }
    }

    public void setValueAt(Object value,
                           int row,
                           int col) {
        if (col == 0 || col == 1) {
            super.setValueAt(value, row, col);
        } else if (col == 2) {
            if (isAssignBy == false) {
                if (value != null && ((String) value).equals("Rejected") == false
                    && ((String) value).equals("Closed") == false) {
                    super.setValueAt(value, row, col);
                } else {
                    comMSG.dispErrorDialog(
                        "You can't modify the status to'Rejected' or 'Closed'.\r\n"
                        + "Only the assigner of the work package can do it.");
                    return;
                }
            } else {
                super.setValueAt(value, row, col);
            }
        } else if (col == 3) {

        }
    }

    public ValidatorResult validateData() {
        ValidatorResult validatorResult = new ValidatorResult();

        for (int i = 0; i < this.getRowCount(); i++) {
            DtoPwWpchk dtoBean = (DtoPwWpchk)this.getRow(i);
            ValidatorResult validatorResultRow = validator.validate(dtoBean);

            String[] msgs = validatorResultRow.getAllMsg();
            if (msgs != null) {
                for (int j = 0; j < msgs.length; j++) {
                    validatorResult.addMsg("Row " + (i + 1) + " ：" + msgs[j]);
                }
            }
        }

        return validatorResult;
    }

    public boolean isDefaultCheckpoint(DtoPwWpchk dto) {
        if (dto != null) {
            if (this.defaultCheckpoint1 != null &&
                this.defaultCheckpoint1.equals(dto.getRid()) == true) {
                return true;
            }
            if (this.defaultCheckpoint2 != null &&
                this.defaultCheckpoint2.equals(dto.getRid()) == true) {
                return true;
            }
        }
        return false;
    }

    public void setAssignBy(boolean isAssignBy) {
        this.isAssignBy = isAssignBy;
//        if( isAssignBy == true ){
//            VMComboBox vmcomboBox = VMComboBox.toVMComboBox( assignByStatus );
//            this.cmbStatus.setModel( vmcomboBox );
//        }else{
//            VMComboBox vmcomboBox = VMComboBox.toVMComboBox( assignByStatus );
//            this.cmbStatus.setModel( vmcomboBox );
//        }
//        this.getColumnConfigByDataName("wpchkStatus").setComponent(cmbStatus);
//        this.cmbStatus.setSelectedIndex(0);
    }

    public Long getDefaultCheckpoint2() {
        return defaultCheckpoint2;
    }

    public Long getDefaultCheckpoint1() {
        return defaultCheckpoint1;
    }

    public void setDefaultCheckpoint1(Long defaultCheckpoint1) {
        this.defaultCheckpoint1 = defaultCheckpoint1;
    }

    public void setDefaultCheckpoint2(Long defaultCheckpoint2) {
        this.defaultCheckpoint2 = defaultCheckpoint2;
    }

}
