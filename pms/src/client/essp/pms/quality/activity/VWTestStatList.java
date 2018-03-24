package client.essp.pms.quality.activity;
import client.essp.common.view.VWTableWorkArea;
import client.framework.view.vwcomp.VWJText;
import c2s.essp.pms.quality.activity.DtoTestStat;
import client.framework.model.VMColumnConfig;
import java.awt.event.ActionListener;
import client.framework.view.event.RowSelectedListener;
import java.awt.event.ActionEvent;
import c2s.dto.ReturnInfo;
import java.util.List;
import c2s.dto.InputInfo;
import c2s.essp.pms.quality.activity.DtoTestRound;
import com.wits.util.Parameter;
import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import client.framework.view.vwcomp.VWJComboBox;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJReal;

public class VWTestStatList extends VWTableWorkArea {
    public  final static String[] injectedPhaseItems = new String[] {
        "AD", "DD", "RD", "HD", "CUT"};
    public final String ACTIONID_TESTSTAT_LIST =
        "FQuTestStatListAction";
    VWTestInfo vwTestInfo;
    DtoTestRound dtoTestRound;
//     VWJText FDefectNum;

    public VWTestStatList(VWTestInfo vwTestInfo) {
        this.vwTestInfo = vwTestInfo;
        try {
            jbInit();
            addUIEvent();
            resetUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public VWTestStatList() {
        try {
            jbInit();
            addUIEvent();
            resetUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void jbInit() throws Exception {
        VWJComboBox cmbInjectedPhase = new VWJComboBox();
        cmbInjectedPhase.setModel(VMComboBox.toVMComboBox(injectedPhaseItems));
        VWJReal real_0 = new VWJReal();
        real_0.setMaxInputDecimalDigit(0);
        Object[][] configs = new Object[][] { {"Injected Phase",
                             "injectedPhase",
                             VMColumnConfig.EDITABLE, cmbInjectedPhase}, {"Number",
                             "number",
                             VMColumnConfig.EDITABLE, real_0},

        };
        super.jbInit(configs, DtoTestStat.class);

    }

    private void addUIEvent() {

        this.getTable().addRowSelectedListener(new RowSelectedListener() {
            public void processRowSelected() {
                processRowSelectedList();
            }
        });

        //Detail表格数据改变时,更新General的三个栏位
        this.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
//                FDefectNum.setUICValue(dtoTestRound. .getDefectNum());

            }
        });

        this.getButtonPanel().addAddButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionPerformedAdd(e);
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
        resetUI();
    }


    public void processRowSelectedList() {

    }


    public void setParameter(Parameter param) {

        dtoTestRound = (DtoTestRound) param.get("dtoTestRound");

        resetUI();

    }

    public void resetUI() {

        if (dtoTestRound != null && dtoTestRound.getRid() != null) {
            InputInfo inputInfo = new InputInfo();
            inputInfo.setActionId(this.ACTIONID_TESTSTAT_LIST);
            inputInfo.setInputObj("testRid", dtoTestRound.getRid());
            ReturnInfo returnInfo = accessData(inputInfo);
            if (returnInfo.isError() == false) {
                List testStatList = (List) returnInfo.getReturnObj(
                    "testStatList");
                getTable().setRows(testStatList);

            }

        }

    }

    public void actionPerformedDel(ActionEvent e) {
        this.getModel().deleteRow(this.getTable().getSelectedRow());
    }

    public void actionPerformedAdd(ActionEvent e) {
//        DtoTestStat dtoTestStat = new DtoTestStat();

        DtoTestStat testStat = new DtoTestStat();
        testStat.setAcntRid(dtoTestRound.getAcntRid());
        testStat.setInjectedPhase(injectedPhaseItems[0]);
//        testStat.setTestRid(dtoTestRound.getRid());
        this.getModel().addRow(0, testStat);
//        this.getTable().addRow();

//        dtoTestStat.setAcntRid(dtoTestRound.getAcntRid());
//        dtoTestStat.setRid(testStat.getRid());
//        dtoTestStat.setInjectedPhase(testStat.getInjectedPhase());
//        dtoTestStat.setNumber(testStat.getDefectNum());
//        int num =
//        dtoTestRound.getDefectNum().intValue()+testStat.getDefectNum().intValue();
//        dtoTestRound.setDefectNum(new Long(num));
    };


}
