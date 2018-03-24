package client.essp.tc.weeklyreport;

import java.awt.Dimension;

import c2s.essp.tc.weeklyreport.DtoTcKey;
import com.wits.util.Parameter;
import c2s.essp.common.calendar.WrokCalendarFactory;
import c2s.dto.InputInfo;
import java.awt.event.ActionEvent;

public class VwWeeklyReportOnWeekByHrDetail extends VwWeeklyReport{
    static final String actionIdUpdate = "FTCHrManageWeeklyReportUpdateAction";

    boolean isUpdated = false;

    public VwWeeklyReportOnWeekByHrDetail() {
        super();

        this.setMinimumSize(new Dimension(800,600));
        this.setPreferredSize(new Dimension(800,600));
    }

    protected void preInit() {
        this.vwWeeklyReportList = new VwWeeklyReportList(){
            protected void setButtonVisible() {
                this.btnAdd.setVisible(true);
                this.btnCopy.setVisible(false);
                this.btnInit.setVisible(false);
                this.btnSave.setVisible(true);
                this.btnLock.setVisible(false);
                this.btnDel.setVisible(true);
                this.btnUpdate.setVisible(true);
            }

            protected void setEnableModel() {
                if (isParameterValid == false) {
                    this.btnAdd.setEnabled(false);
                    this.btnDel.setEnabled(false);
                    this.btnUpdate.setEnabled(false);
                    this.btnSave.setVisible(false);

                    setDataEditable(false);
                }else{
                    this.btnAdd.setEnabled(true);
                    this.btnDel.setEnabled(true);
                    this.btnUpdate.setEnabled(true);
                    this.btnSave.setVisible(true);

                    setDataEditable(true);
                }
            }

            protected InputInfo createInputInfoForUpdate() {
                InputInfo inputInfo = super.createInputInfoForUpdate();
                inputInfo.setActionId(VwWeeklyReportOnWeekByHrDetail.this.actionIdUpdate);
                return inputInfo;
            }

            protected void saveData() {
                VwWeeklyReportOnWeekByHrDetail.this.isUpdated = true;

                super.saveData();
            }
        };

        this.workCalendar = WrokCalendarFactory.clientCreate();
    }


    public void setParameter(Parameter param) {
        super.setParameter(param);

        //judge whether the parameter is changed
        boolean parameterChanged = false;
        String newUserId = (String) (param.get(DtoTcKey.USER_ID));
        if ( newUserId == null ) {
            parameterChanged = true;
        } else {
            if (userId == null || userId.equals(newUserId) == false
                    ) {
                parameterChanged = true;
            }
        }

        this.userId = newUserId;

        if( parameterChanged == true ){
            setRefreshFlag();
        }
    }

    public boolean isUpdated(){
        return this.isUpdated;
    }

}
