package client.essp.timecard.timecard.assurancebyhr;

import client.essp.common.view.VWWorkArea;
import client.framework.view.IVWWorkArea;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.StateChangedListener;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;

import java.awt.AWTEvent;
import java.awt.Dimension;


public class FTC01020Main extends VWWorkArea {
    private boolean          refreshFlag      = false;
    private IVWWorkArea      currCard; //当前的卡片
    private FTC01020Project  fTC01020Project  = new FTC01020Project(); //第一张CARD,总共两张卡片
    private FTC01021Employee fTC01021Employee = new FTC01021Employee(); //第二张CARD,总共两张卡片

    //    String prj_Name;
    //    String wk_Period;
    public FTC01020Main() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(800, 505));
        this.addStateChangedListener(new StateChangedListener() {
                public void processStateChanged() {
                    processStateChangedMain();
                }
            });

        fTC01020Project.addDataChangedListener(new DataChangedListener() {
                public void processDataChanged() {
                    Parameter para = new Parameter();
                    para.put("ISINIT", new Boolean(false));
                    para.put("START", fTC01020Project.getStartDate());
                    para.put("END", fTC01020Project.getFinishDate());
                    para.put("ISPROMPT", fTC01020Project.isPrompt());
                    para.put("ISALWAYSOK", fTC01020Project.isAlwaysOk());
                    para.put("ISALLOWCHANGES", fTC01020Project.isAllowChanges());
                    fTC01021Employee.setParameter(para);
                }
            });

        fTC01021Employee.addDataChangedListener(new DataChangedListener() {
                public void processDataChanged() {
                    Parameter para = new Parameter();
                    para.put("ISINIT", new Boolean(false));
                    para.put("START", fTC01021Employee.getStartDate());
                    para.put("END", fTC01021Employee.getFinishDate());
                    para.put("ISPROMPT", fTC01021Employee.isPrompt());
                    para.put("ISALWAYSOK", fTC01021Employee.isAlwaysOk());
                    fTC01020Project.setParameter(para);
                }
            });

        this.addTab("Assurance By Project", fTC01020Project);
        this.addTab("Assurance By Employee", fTC01021Employee);

        setSelectedCard(0);
    }

    private void processStateChangedMain() {
        setSelectedCard(getSelectedIndex());
    }

    //设置当前显示的detail panel
    private void setSelectedCard(int index) {
        if (index == 0) {
            currCard = fTC01020Project;
        } else if (index == 1) {
            currCard = fTC01021Employee;
        }
    }

    public void saveWorkArea() {
        currCard.saveWorkArea();
    }

    public void refreshWorkArea() {
        if (refreshFlag == true) {
            refreshFlag = false;

            Parameter para = new Parameter();
            para.put("ISINIT", new Boolean(true));
            currCard.setParameter(para);
            currCard.refreshWorkArea();
        }
    }

    public static void main(String[] args) {
        FTC01020Main fTC01020Main = new FTC01020Main();
        TestPanel.show(fTC01020Main);
    }

    public void setRefreshFlag(boolean refreshFlag) {
        this.refreshFlag = refreshFlag;
    }
}
