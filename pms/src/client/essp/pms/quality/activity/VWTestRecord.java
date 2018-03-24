package client.essp.pms.quality.activity;
import client.essp.common.view.VWTableWorkArea;
import c2s.dto.ReturnInfo;
import javax.servlet.http.HttpSession;
import java.awt.event.ActionEvent;
import client.framework.view.vwcomp.VWJText;
import c2s.essp.common.user.DtoUser;
import c2s.essp.pms.quality.activity.DtoQualityActivity;
import junitpack.HttpServletRequestMock;
import java.awt.event.ActionListener;
import client.framework.common.Global;
import client.essp.common.view.VWWorkArea;
import c2s.dto.InputInfo;
import javax.servlet.http.HttpServletRequest;
import client.framework.model.VMColumnConfig;
import java.util.List;
import client.framework.view.event.RowSelectedListener;
import com.wits.util.Parameter;
import client.framework.view.vwcomp.VWJPopupEditor;
import c2s.essp.pms.quality.activity.DtoTestRound;
import client.framework.common.Constant;
import client.framework.view.common.comMSG;
import client.framework.view.vwcomp.*;
import java.util.*;

public class VWTestRecord extends VWTableWorkArea {

    DtoQualityActivity dtoQualityActivity;
    public final String ACTIONID_QUALITYACTIVITY_GENERAL =
        "FQuQualityActivityGeneralAction";
    public final String ACTIONID_TESTROUND_DEL = "FQuQualityActivityTestDelAction";
//    DtoTestRound dtoTestRound ;
    public VWTestRecord() {

        try {
            jbInit();
            addUIEvent();
            resetUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void addUIEvent() {

        this.getTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedList();
            }
        });

        this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
            }
        });

        this.getButtonPanel().addEditButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedEdit(e);
            }
        });

        this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedDel(e);
            }
        });

        this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedRefresh(e);
            }
        });

    }


    public void actionPerformedRefresh(ActionEvent e) {
        this.resetUI();
    }


    public void processRowSelectedList() {

    }

    public void jbInit() throws Exception {

        VWJReal real_0 = new VWJReal();
        real_0.setMaxInputDecimalDigit(0);

        //设置标题栏位
        Object[][] configs = new Object[][] { {"Test Round", "testRound",
                             VMColumnConfig.UNEDITABLE, new VWJText()}, {"Start",
                             "start",
                             VMColumnConfig.UNEDITABLE, new VWJDate()},
                             {"ActivityName", "activityName",
                             VMColumnConfig.UNEDITABLE,
                             new VWJText()}, {"Finish", "finish",
                             VMColumnConfig.UNEDITABLE, new VWJDate()},
                             {"Test Case Num(Total)", "totalTestCase",
                             VMColumnConfig.UNEDITABLE, real_0},
                             {"Test Case Num(Using)", "usingTestCase",
                             VMColumnConfig.UNEDITABLE, real_0},
                             {"Defect Number(Found)",
                             "defectNum", VMColumnConfig.UNEDITABLE, real_0},
                             {"Defect Number(Removed)", "removedDefectNum",
                             VMColumnConfig.UNEDITABLE, real_0},
                             {"Comment", "comment",
                             VMColumnConfig.UNEDITABLE, new VWJText()}, {"Seq",
                             "seq",
                             VMColumnConfig.UNEDITABLE, new VWJText()},

        };
        super.jbInit(configs, DtoTestRound.class);

    }

    public void actionPerformedEdit(ActionEvent e) {
        VWTestInfo popWindow = new VWTestInfo(this);
        DtoTestRound dtoTestRound = (DtoTestRound)this.getTable().
                                    getSelectedData();
        Parameter param = new Parameter();
        param.put("dtoTestRound", dtoTestRound);
        popWindow.setParameter(param);
        popWindow.refreshWorkArea();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(),
                                                "Test Round", popWindow);
        pop.setSize(500, 400);
        pop.show();

    }

    public void actionPerformedDel(ActionEvent e) {
        int f = comMSG.dispConfirmDialog("Confirm to delete selected record!");
        if (f == Constant.OK) {
            DtoTestRound dtoTestRound = (DtoTestRound)this.getTable().
                                        getSelectedData();
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.ACTIONID_TESTROUND_DEL);

            inputInfo.setInputObj("dtoTestRound", dtoTestRound);

            ReturnInfo returnInfo = accessData(inputInfo);

            if (returnInfo.isError() == false) {
                this.getModel().deleteRow(this.getTable().getSelectedRow());
            }
        }




    }


    public void actionPerformedAdd(ActionEvent e) {
//        DtoTestRound activity = (DtoTestRound)this.getTable().addRow();
        VWTestInfo popWindow = new VWTestInfo(this);
//         DtoTestRound dtoTest = new DtoTestRound();
        DtoTestRound dtoTestRound = new DtoTestRound();
        dtoTestRound.setAcntRid(this.dtoQualityActivity.getAcntRid());
        dtoTestRound.setQulityRid(this.dtoQualityActivity.getActivityRid());
        Parameter param = new Parameter();
        param.put("dtoTestRound", dtoTestRound);

//        DtoTestRound dtoTestRound = (DtoTestRound) activity;
//        param.put("dtoTestRound", dtoTestRound);
        //param.put("jobCodeOptions", jobCodeOptions);
        popWindow.setParameter(param);
        popWindow.refreshWorkArea();
        VWJPopupEditor pop = new VWJPopupEditor(this.getParentWindow(),
                                                "Test Round", popWindow);
        pop.setSize(500, 400);
        pop.show();

    }

    public void setParameter(Parameter param) {
        super.setParameter(param);
        dtoQualityActivity = (DtoQualityActivity) param.get("qaParameter");
//        resetUI();
    }

    public void resetUI() {

        if (dtoQualityActivity != null) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.ACTIONID_QUALITYACTIVITY_GENERAL);
            inputInfo.setInputObj("qaGeneral", this.dtoQualityActivity);
            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                List testUser = (List) returnInfo.getReturnObj("testList");
                this.getTable().setRows(testUser);
            }
        } else {
            this.getTable().setRows(new ArrayList());
        }
    }

//    static{
//       Global.todayDateStr = "2005-12-03";
//       Global.todayDatePattern = "yyyy-MM-dd";
//       Global.userId = "stone.shi";
//       DtoUser user = new DtoUser();
//       user.setUserID(Global.userId);
//       user.setUserLoginId(Global.userId);
//       HttpServletRequest request = new HttpServletRequestMock();
//       HttpSession session = request.getSession();
//       session.setAttribute(DtoUser.SESSION_USER, user);
//   }

    public static void main(String[] args) {



    }

}
