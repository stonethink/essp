package client.essp.pms.quality.activity;
import client.essp.common.view.VWGeneralWorkArea;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import client.framework.view.vwcomp.VWJText;
import c2s.essp.pms.quality.activity.DtoTestStat;
import com.wits.util.Parameter;
import c2s.essp.pms.quality.activity.DtoTestRound;
import client.framework.view.vwcomp.VWJDate;
import c2s.dto.ReturnInfo;
import java.util.List;
import c2s.dto.InputInfo;
import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.util.Date;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import client.framework.view.vwcomp.VWJReal;
import java.awt.Rectangle;

public class VWTestInfo extends VWGeneralWorkArea implements
    IVWPopupEditorEvent {
    public final String ACTIONID_TESTSTAT_SAVE = "FQuSaveTestStatAction";

    VWQualityActivityList vwQualityActivityList;

    VWTestRecord vwTestRecord;
    DtoTestRound dtoTestRound;
//    Parameter param;
    public VWTestInfo() {
        try {
            jbInit();
            addUICEvent();
            resetUI();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public VWTestInfo(VWTestRecord vwTestRecord) {
        this.vwTestRecord = vwTestRecord;
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public VWTestInfo(VWQualityActivityList vwQualityActivityList) {
        this.vwQualityActivityList = vwQualityActivityList;
        try {
            jbInit();
            addUICEvent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void jbInit() throws Exception {

        this.setBackground(Color.white);
        this.setLayout(null);
        jLabel1.setText("Test Round");
        jLabel1.setBounds(new Rectangle(49, 20, 71, 20));
        jLabel2.setText("Seq");
        jLabel2.setBounds(new Rectangle(273, 20, 56, 20));
        jLabel3.setText("Start");
        jLabel3.setBounds(new Rectangle(60, 50, 76, 20));
        jLabel4.setText("Finish");
        jLabel4.setBounds(new Rectangle(269, 50, 63, 20));
        jLabel5.setText("Test Case Num(Total)");
        jLabel5.setBounds(new Rectangle(20, 80, 126, 20));
        jLabel6.setText("Test Case Num(Using)");
        jLabel6.setBounds(new Rectangle(242, 80, 131, 20));
        jLabel7.setText("Defect Number(Found)");
        jLabel7.setBounds(new Rectangle(20, 110, 124, 20));
        jLabel8.setText("Defect Number(Removed)");
        jLabel8.setBounds(new Rectangle(237, 110, 135, 20));
        jLabel9.setText("Comment");
        jLabel9.setBounds(new Rectangle(53, 140, 67, 20));
        TestRound.setText("");
        TestRound.setBounds(new Rectangle(144, 20, 76, 20));
        Seq.setText("");
        Seq.setBounds(new Rectangle(373, 20, 77, 20));
        Seq.setMaxInputDecimalDigit(0);
        Start.setText("");
        Start.setBounds(new Rectangle(144, 50, 76, 20));
        Start.setCanSelect(true);
        Finish.setText("");
        Finish.setBounds(new Rectangle(373, 50, 77, 20));
        Finish.setCanSelect(true);
        TotalCaseNum.setText("");
        TotalCaseNum.setBounds(new Rectangle(144, 80, 76, 20));
        TotalCaseNum.setMaxInputDecimalDigit(0);

        UsingTestCaseNum.setText("");
        UsingTestCaseNum.setBounds(new Rectangle(373, 80, 77, 20));
        UsingTestCaseNum.setMaxInputDecimalDigit(0);

        FDefectNum.setText("0");
        FDefectNum.setBounds(new Rectangle(145, 110, 75, 20));
        FDefectNum.setEnabled(false);
        FDefectNum.setMaxInputDecimalDigit(0);

        RDefectNumber.setText("");
        RDefectNumber.setBounds(new Rectangle(373, 110, 77, 20));
        RDefectNumber.setMaxInputDecimalDigit(0);

        Comment.setText("");
        Comment.setBounds(new Rectangle(143, 140, 307, 20));

        testStatPane.setBounds(40, 220, 410, 70);
        testStatPane.setBounds(new Rectangle(19, 173, 450, 140));
        this.add(jLabel1);
        this.add(jLabel2);
        this.add(jLabel3);
        this.add(jLabel4);
        this.add(jLabel5);
        this.add(jLabel6);
        this.add(jLabel7);
        this.add(jLabel8);
        this.add(jLabel9);
        this.add(TestRound);
        this.add(Seq);
        this.add(Finish);
        this.add(TotalCaseNum);
        this.add(UsingTestCaseNum);
        this.add(FDefectNum);
        this.add(RDefectNumber);
        this.add(Comment);
        this.add(Start);
        this.add(testStatPane);
        testStatPane.addTab("Defect Stat.", testStatList);
    }

    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JLabel jLabel9 = new JLabel();
    VWJText TestRound = new VWJText();
    VWJReal Seq = new VWJReal();
    VWJDate Start = new VWJDate();
    VWJDate Finish = new VWJDate();
    VWJReal TotalCaseNum = new VWJReal();
    VWJReal UsingTestCaseNum = new VWJReal();
    VWJReal FDefectNum = new VWJReal();
    VWJReal RDefectNumber = new VWJReal();
    VWJText Comment = new VWJText();
    VWWorkArea testStatPane = new VWWorkArea();
    VWTestStatList testStatList = new VWTestStatList();

    private void addUICEvent() {

        //TestStat表格数据改变时,更新DefectNum栏位
        this.testStatList.getModel().addTableModelListener(new
            TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int fDefectNum = 0;
//             int i = testStatList.getTable().getRowCount();
                List list = testStatList.getModel().getDtoList();
                for (int j = 0; j < list.size(); j++) {
                    DtoTestStat dtoTestStat = (DtoTestStat) list.get(j);
                    if (dtoTestStat.isDelete()) {
                        fDefectNum -= dtoTestStat.getNumber().intValue();
                    } else {
                        if (dtoTestStat.getNumber() != null) {
                            fDefectNum += dtoTestStat.getNumber().intValue();
                        }
                    }
                }
                FDefectNum.setUICValue(new Long(fDefectNum));

            }
        });
    }


    public void setParameter(Parameter param) {
//        this.param = param;
        dtoTestRound = (DtoTestRound) param.get("dtoTestRound");
        resetUI();
    }

    public void resetUI() {

        if (dtoTestRound != null && dtoTestRound.getRid() != null) {
            this.TestRound.setUICValue(dtoTestRound.getTestRound());
            this.Start.setUICValue(dtoTestRound.getStart());
            this.Finish.setUICValue(dtoTestRound.getFinish());
            this.UsingTestCaseNum.setUICValue(dtoTestRound.getUsingTestCase());
            this.FDefectNum.setUICValue(dtoTestRound.getDefectNum());
            this.RDefectNumber.setUICValue(dtoTestRound.getRemovedDefectNum());
            this.Comment.setUICValue(dtoTestRound.getComment());
            this.Seq.setUICValue(dtoTestRound.getSeq());
            this.TotalCaseNum.setUICValue(dtoTestRound.getTotalTestCase());
        }
        Parameter param2 = new Parameter();
        param2.put("dtoTestRound", dtoTestRound);
        testStatList.setParameter(param2);

    }

    public boolean onClickOK(ActionEvent actionEvent) {

        dtoTestRound.setTestRound((String)this.TestRound.getUICValue());

        dtoTestRound.setStart((Date)this.Start.getUICValue());
        dtoTestRound.setFinish((Date)this.Finish.getUICValue());
        dtoTestRound.setTotalTestCase(new Long((String)this.TotalCaseNum.
                                               getUICValue().toString()));

        dtoTestRound.setUsingTestCase(new Long((String)this.UsingTestCaseNum.
                                               getUICValue().toString()));

          dtoTestRound.setRemovedDefectNum(new Long((String)this.RDefectNumber.
                                                    getUICValue().toString()));

          dtoTestRound.setDefectNum(new Long((String)this.FDefectNum.getUICValue().
                                            toString()));
          dtoTestRound.setDefectNum(new Long((String)this.FDefectNum.
                                             getUICValue().toString()));
          dtoTestRound.setComment((String)this.Comment.getUICValue());
          dtoTestRound.setSeq(new Long((String)this.Seq.getUICValue().toString()));



        List dtoTestStatList = testStatList.getModel().getDtoList();

        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(this.ACTIONID_TESTSTAT_SAVE);

        inputInfo.setInputObj("dtoTestStatList", dtoTestStatList);
        if (dtoTestRound != null) {
            inputInfo.setInputObj("dtoTestRound", dtoTestRound);
        }
        ReturnInfo returnInfo = accessData(inputInfo);

        if (returnInfo.isError() == false) {
            this.vwTestRecord.resetUI();
        }

        return true;
    }

    public boolean onClickCancel(ActionEvent actionEvent) {
        return true;
    }


}
