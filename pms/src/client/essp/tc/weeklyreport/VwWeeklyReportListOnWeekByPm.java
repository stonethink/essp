package client.essp.tc.weeklyreport;

import c2s.dto.InputInfo;
import c2s.essp.tc.weeklyreport.DtoTcKey;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import client.framework.model.VMColumnConfig;
import client.framework.view.vwcomp.VWJReal;
import client.framework.view.vwcomp.VWJText;
import com.wits.util.Parameter;
import java.awt.Color;
import client.framework.view.vwcomp.NodeViewManager;

public class VwWeeklyReportListOnWeekByPm extends VwWeeklyReportListBase {
    public static final String actionIdList = "FTCPmManageWeeklyReportListAction";
    public static final String actionIdUpdate = "FTCPmManageWeeklyReportUpdateAction";

    Long acntRid = null;
    String confirmStatus = null;

    public VwWeeklyReportListOnWeekByPm() {
        model = new VMTableWeeklyReport(getConfigs());
        table = new TableWeeklyReport(model, new WeeklyReportOnWeekNodeViewManager());

        initUI();
    }

    private static Object[][] getConfigs() {
        VWJText text = new VWJText();
        VWJReal real = new VWJReal();
        real.setMaxInputIntegerDigit(2);
        real.setMaxInputDecimalDigit(2);

        Object[][] configs = new Object[][] { {"Account", "acntName", VMColumnConfig.UNEDITABLE, text}, {"Activity", "activityDisp", VMColumnConfig.UNEDITABLE,
                             text}, {"Job Description", "jobDescription", VMColumnConfig.EDITABLE, text},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SATURDAY], "satHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.SUNDAY], "sunHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.MONDAY], "monHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.TUESDAY], "tueHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.WEDNESDAY], "wedHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.THURSDAY], "thuHour", VMColumnConfig.EDITABLE, real},
                             {VMTableWeeklyReport.WEEK_TITLE[DtoWeeklyReport.FRIDAY], "friHour", VMColumnConfig.EDITABLE, real}, {VMTableWeeklyReport.SUMMARY,
                             "sumHour", VMColumnConfig.UNEDITABLE, real},
                             {"Comments", "comments", VMColumnConfig.EDITABLE, text},
        };

        return configs;
    }

    public void setParameter(Parameter param) {
        super.setParameter(param);

        acntRid = (Long) param.get(DtoTcKey.ACNT_RID);
        if (acntRid == null) {
            this.isParameterValid = false;
        }

        confirmStatus = (String)param.get(DtoTcKey.CONFIRM_STATUS);
    }

    protected InputInfo createInputInfoForList() {
        InputInfo inputInfo = super.createInputInfoForList();
        inputInfo.setInputObj(DtoTcKey.ACNT_RID, acntRid);
        inputInfo.setActionId(actionIdList);
        return inputInfo;
    }

    protected InputInfo createInputInfoForUpdate() {
        InputInfo inputInfo = super.createInputInfoForUpdate();

        inputInfo.setActionId(this.actionIdUpdate);
        inputInfo.setInputObj(DtoTcKey.ACNT_RID, acntRid);
        return inputInfo;
    }

    protected Parameter createParameterForGeneralWorkArea(DtoWeeklyReport dto) {
        Parameter param = super.createParameterForGeneralWorkArea(dto);
        param.put(DtoTcKey.DTO, dto);
        param.put(DtoTcKey.ACNT_RID, acntRid);
        return param;
    }

    protected void setButtonVisible() {
        this.btnCopy.setVisible(false);
        this.btnInit.setVisible(false);
        this.btnLock.setVisible(false);
    }

    protected void setEnableModel() {
        if (isParameterValid == false) {
            this.btnAdd.setEnabled(false);
            this.btnSave.setEnabled(false);
            this.btnDel.setEnabled(false);
            this.btnUpdate.setEnabled(false);

            setDataEditable(false);
            return;
        }

        if (DtoWeeklyReport.STATUS_CONFIRMED.equals(this.confirmStatus)
            || DtoWeeklyReport.STATUS_UNLOCK.equals(this.confirmStatus)
        || DtoWeeklyReport.STATUS_NONE.equals(this.confirmStatus)
                ) {
            //如果周报没有被锁定或已被pm确认，那么pm不能修改
            this.btnAdd.setEnabled(false);
            this.btnSave.setEnabled(false);
            this.btnDel.setEnabled(false);
            this.btnUpdate.setEnabled(false);

            setDataEditable(false);
        } else {

            //对于weekly report sum信息，不能修改
            DtoWeeklyReport dto = (DtoWeeklyReport) getSelectedData();
            if (dto != null) {
                if (dto.isWeeklyReportSum()) {
                    this.btnAdd.setEnabled(true);
                    this.btnSave.setEnabled(true);
                    this.btnDel.setEnabled(false);
                    this.btnUpdate.setEnabled(false);

                    setDataEditable(false);
                }else{
                    this.btnAdd.setEnabled(true);
                    this.btnSave.setEnabled(true);
                    this.btnDel.setEnabled(true);
                    this.btnUpdate.setEnabled(true);

                    setDataEditable(true);
                }
            } else {
                this.btnAdd.setEnabled(true);
                this.btnSave.setEnabled(true);
                this.btnDel.setEnabled(false);
                this.btnUpdate.setEnabled(false);

                setDataEditable(false);
            }
        }
     }

    protected VwWeeklyReportGeneral getVwWeeklyReportGeneral() {
        if (vwWeeklyReportGeneral == null) {
            vwWeeklyReportGeneral = new VwWeeklyReportGeneralByPm();
        }

        return this.vwWeeklyReportGeneral;
    }

    public void setConfirmStatus(String confirmStatus){
        this.confirmStatus = confirmStatus;
        setEnableModel();
    }


    public class WeeklyReportOnWeekNodeViewManager extends NodeViewManager{
        Color c = new Color(200, 200, 200);
        public DtoWeeklyReport getWDto(){
            return (DtoWeeklyReport)getDto();
        }

        public Color getOddBackground() {
            if( getWDto() != null && getWDto().isWeeklyReportSum() ){
                return c;
            }else{
                return super.getOddBackground();
            }
        }

        public Color getEvenBackground() {
            if( getWDto() != null && getWDto().isWeeklyReportSum() ){
                return c;
            }else{
                return super.getEvenBackground();
            }
        }

    }

}
