
package client.essp.pwm.workbench.workitem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JPanel;

import c2s.essp.pwm.workbench.DtoKey;
import client.essp.common.view.VWWorkArea;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJCheckBox;
import client.framework.view.vwcomp.VWJLabel;
import com.wits.util.Parameter;
import com.wits.util.comDate;
import com.wits.util.TestPanel;
import javax.swing.JButton;
import java.awt.event.*;

public class VwWorkItem extends VWWorkArea {
    //parameter
    Date selectedDate = null;


    /**
     * define control variable
     */
    private boolean refreshFlag = false;
    private boolean isParameterValid = false;

    public VwWorkItemList vwWorkItemList;
    VWJLabel lblSelDate = new VWJLabel();
    VWJLabel lblnull = new VWJLabel();
    VWJButton btnDailyReport = new VWJButton();
//    VWJCheckBox checkBoxHavaReport = new VWJCheckBox();
    JButton btnSave = null;
    JButton btnLoad = null;
    JButton btnDel = null;
    JButton btnInit = null;

    JButton btnCustom = null;

    /**
     * default constructor
     */
    public VwWorkItem() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addUICEvent();
        setEnabledMode();
        setButtonVisible();
    }


    //Component initialization
    private void jbInit() throws Exception {
        vwWorkItemList = new VwWorkItemList();
        //vwWorkItemList = new VwWorkItemListTest();

        //lblSelDate.setText("2003-11-20");
        //JPanel panelSelDate = new JPanel();
        //panelSelDate.add(lblSelDate);

        //btnDailyReport.setMargin(new Insets(0, 2, 0, 10));
        btnDailyReport.setText(" Daily Report ");

//        checkBoxHavaReport.setEnabled(false);
//        checkBoxHavaReport.setText("Have delivered Dialy Reports");

        JPanel panelCheck = new JPanel();
        FlowLayout flowLayoutCheck = new FlowLayout();
        flowLayoutCheck.setAlignment(FlowLayout.RIGHT);
        panelCheck.setLayout(flowLayoutCheck);
        panelCheck.setPreferredSize(new Dimension(379, 35));
//        panelCheck.add(checkBoxHavaReport, null);

        JPanel panelDailyReport = new JPanel();
        FlowLayout flowLayout2 = new FlowLayout();
        flowLayout2.setAlignment(FlowLayout.RIGHT);
        flowLayout2.setHgap(0);
        panelDailyReport.setLayout(flowLayout2);
        panelDailyReport.add(btnDailyReport, null);
        panelDailyReport.add(panelCheck, null);

        //this.add(panelSelDate, BorderLayout.NORTH);
        this.add(vwWorkItemList, BorderLayout.CENTER);
        this.add(panelDailyReport, BorderLayout.SOUTH);
    }

    /**
     * 定义界面：定义界面事件
     */
    private void addUICEvent() {

        this.getButtonPanel().add(lblSelDate);
        this.getButtonPanel().add(lblnull);

        btnInit = new JButton("Init");
        btnInit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vwWorkItemList.initWorkItemFromScope();
            }
        });
        //this.getButtonPanel().add(btnInit);

        btnCustom = this.getButtonPanel().addButton("addExist.gif");
        btnCustom.setToolTipText("Customize");
        btnCustom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vwWorkItemList.actionPerformedCustom(e);
            }
        });

        btnDel = this.getButtonPanel().addDelButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vwWorkItemList.actionPerformedDel(e);
            }
        });

        btnSave = this.getButtonPanel().addSaveButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vwWorkItemList.actionPerformedSave(e);
            }
        });

        btnLoad = this.getButtonPanel().addLoadButton(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vwWorkItemList.actionPerformedLoad(e);
            }
        });

        this.btnDailyReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vwWorkItemList.actionPerformedBtnDailyReport(e);
            }
        });

        this.lblSelDate.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if( isParameterValid == true ){
                    vwWorkItemList.mouseClickedLblSelDate(e);
                }
            }
        });
    }

    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter para) {
        Date selectedDate = (Date)para.get(DtoKey.SELECTED_DATE);
        String sDate = "";
        if( selectedDate == null ){
            isParameterValid = false;
            sDate = "No date were selected! Please select one date in the left calendar.";
        }else{
            isParameterValid = true;
            sDate = comDate.dateToString(selectedDate, "yyyy-MM-dd");
        }
        this.lblSelDate.setText(sDate);
        this.lblnull.setText("                                       ");

        this.vwWorkItemList.setParameter(para);
        this.refreshFlag = true;
    }

    /////// 段3，获取数据并刷新画面
    public void refreshWorkArea() {
        if( refreshFlag == true ){

            setEnabledMode();
            setButtonVisible();

            this.vwWorkItemList.refreshWorkArea();
        }
    }

    private void setEnabledMode(){
        if( isParameterValid == true ){
            this.btnDailyReport.setEnabled(true);
        }else{
            this.btnDailyReport.setEnabled(false);
        }
    }

    private void setButtonVisible() {
        if (isParameterValid == true ) {
            this.btnDel.setVisible(true);
            this.btnSave.setVisible(true);
            this.btnCustom.setVisible(true);
        } else {
            this.btnDel.setVisible(false);
            this.btnSave.setVisible(false);
            this.btnCustom.setVisible(false);

        }
    }

    /////// 段4，事件处理
    public void actionPerformedRepeat(ActionEvent e) {
    }

    public void saveWorkArea(){
        this.vwWorkItemList.saveWorkArea();
    }

    public boolean isSaveOk(){
        return this.vwWorkItemList.isSaveOk();
    }

    public static void main(String args[]){
        VwWorkItem w = new VwWorkItem();
        w.setParameter(new Parameter());
        w.refreshWorkArea();

        VWWorkArea w2 = new VWWorkArea();
        w2.addTab("daily report", w);
        TestPanel.show(w2);
    }

}
