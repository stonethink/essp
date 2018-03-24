package client.essp.pms.quality.activity;

import client.essp.common.view.VWTableWorkArea;
import c2s.essp.pms.quality.activity.DtoQualityActivity;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJText;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import client.framework.view.vwcomp.VWUtil;
import javax.swing.table.TableColumnModel;
import javax.swing.table.JTableHeader;
import client.framework.view.vwcomp.VWJReal;
import client.essp.common.view.VWAccountLabel;
import itf.account.AccountFactory;
import c2s.essp.common.account.IDtoAccount;
import c2s.essp.common.account.IAccountModel;


public class VWQualityActivityList extends VWTableWorkArea {
    public final String ACTIONID_QUALITYACTIVITY_LIST =
        "FQuQualityActivityListAction";
    VWAccountLabel accountLabel = null;
    private final String ACTIONID_GENERAL_SAVE = "FQuGeneralSaveAction";

    public List qalist;
    public VWQualityActivityList() {
        try {
            jbInit();
            addUIEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUIEvent() {
        accountLabel = new VWAccountLabel();
        this.getButtonPanel().add(accountLabel);

        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetUI();
            }
        });
    }


    public void jbInit() throws Exception {

        VWJReal real_2 = new VWJReal();
        real_2.setMaxInputDecimalDigit(2);
        VWJReal real_0 = new VWJReal();
        real_0.setMaxInputDecimalDigit(0);

        //设置标题栏位
        Object[][] configs = new Object[][] { {"Type", "type",
                             VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"ActivityID", "code", VMColumnConfig.UNEDITABLE,
                             new VWJText()}, {"ActivityName", "name",
                             VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"P-Size",
                             "planSize", VMColumnConfig.UNEDITABLE, real_0},
                             {"P-Density", "planDensity",
                             VMColumnConfig.UNEDITABLE, real_2},
                             {"P-Defect Rate", "planDefectRate",
                             VMColumnConfig.UNEDITABLE, real_2},
                             {"A-Size",
                             "actualSize", VMColumnConfig.UNEDITABLE,
                             real_0}, {"A-Density", "actualDensity",
                             VMColumnConfig.UNEDITABLE, real_2},
                             {"A-Defect Rate", "actualDefectRate",
                             VMColumnConfig.UNEDITABLE, real_2},
                             {"Density Units", "densityUnits",
                             VMColumnConfig.UNEDITABLE, new VWJText()},
                             {"Defect Rate Units", "defectRateUnits",
                             VMColumnConfig.UNEDITABLE, new VWJText()},
        };
        super.jbInit(configs, DtoQualityActivity.class);
        //调整列的宽度
        JTableHeader header = this.getTable().getTableHeader();
        TableColumnModel tcModel = header.getColumnModel();
//        tcModel.getColumn(5).setPreferredWidth(40);
        tcModel.getColumn(0).setPreferredWidth(65);
        tcModel.getColumn(2).setPreferredWidth(150);
        tcModel.getColumn(3).setPreferredWidth(60);
        tcModel.getColumn(4).setPreferredWidth(60);
        tcModel.getColumn(5).setPreferredWidth(60);
        tcModel.getColumn(6).setPreferredWidth(60);
        tcModel.getColumn(7).setPreferredWidth(60);
        tcModel.getColumn(8).setPreferredWidth(60);

    }

    public void resetUI() {
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_QUALITYACTIVITY_LIST);
        ReturnInfo returnInfo = accessData(inputInfo);
        if (returnInfo.isError() == false) {
            qalist = (List) returnInfo.getReturnObj("qaList");
            IDtoAccount accountDto = (IDtoAccount) returnInfo.getReturnObj("accountDto");
            getTable().setRows(qalist);
            if (accountDto != null) {
                IAccountModel model = VWAccountLabel.createtDefaultModel(
                 accountDto.getId(),accountDto.getName());
                accountLabel.setModel(model);
            }
        }
    }

    public void refreshWorkArea() {
        VWUtil.clearUI(this);
        resetUI();
    }


    public void updateQualityactivity(Long defectNum, Double actualDefectRate) {
        DtoQualityActivity dtoQualityActivity = (DtoQualityActivity)this.
                                                getTable().getSelectedData();
        dtoQualityActivity.setActualDefectNum(defectNum);
        dtoQualityActivity.setActualDefectRate(actualDefectRate);
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_GENERAL_SAVE);
        inputInfo.setInputObj("dtoQualityActivity",
                              dtoQualityActivity);
        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            this.getTable().repaint();
        }

    }
}
