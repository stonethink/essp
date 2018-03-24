package client.essp.timecard.timecard.weeklyreport;

import client.essp.common.view.VWWorkArea;
import client.framework.view.IVWWorkArea;

import com.wits.util.Parameter;
import com.wits.util.TestPanel;

import java.awt.AWTEvent;
import java.awt.Dimension;

import java.util.ArrayList;


public class FTC01010Main extends VWWorkArea {
    private FTC01010PM  fTC01010PM  = new FTC01010PM(); //第一个CARD,总共一张卡片
    private boolean     refreshFlag = true;
    private IVWWorkArea currCard; //当前的卡片
    private String      pm          = "000194";

    //    String prjID = "";
    //    String prjAccount = "";
    //    String prjName = "";
    private ArrayList prjList = null;

    public FTC01010Main() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        try {
            jbInit();

            //刷新第一张卡片
            //            currCard.refreshWorkArea();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(800, 505));
        this.addTab("PM Assurance", fTC01010PM);
        currCard = fTC01010PM;
    }

    public void setParameter(Parameter para) {
        //        this.prjID = StringUtil.nvl(param.get("PRJID"));
        //        this.prjAccount = StringUtil.nvl(param.get("PRJACCOUNT"));
        //        this.prjName = StringUtil.nvl(param.get("PRJNAME"));
        if (para.get("prjList") != null) {
            this.prjList = (ArrayList) para.get("prjList");
        } else {
            this.prjList = new ArrayList();
        }

        this.refreshFlag = true;
    }

    public void saveWorkArea() {
        currCard.saveWorkArea();
    }

    public void refreshWorkArea() {
        if (refreshFlag == true) {
            refreshFlag = false;

            Parameter para = new Parameter();

            //            para.put("PRJID",prjID);
            //            para.put("PRJACCOUNT",prjAccount);
            //            para.put("PRJNAME",prjName);
            para.put("prjList", prjList);

            if (prjList.size() > 0) {
                currCard.setParameter(para);
                currCard.refreshWorkArea();
            } else {
                fTC01010PM.getBtAdd().setEnabled(false);
                fTC01010PM.getBtDel().setEnabled(false);
                fTC01010PM.getBtLastPeriod().setEnabled(false);
                fTC01010PM.getBtNextPeriod().setEnabled(false);
                fTC01010PM.getBtReload().setEnabled(false);
                fTC01010PM.getBtSave().setEnabled(false);
                fTC01010PM.getTxtRecWkHours().setEnabled(false);
                fTC01010PM.getCbxPrjList().setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        FTC01010Main fTC01010Main = new FTC01010Main();
        Parameter    para = new Parameter();

        //        para.put("prjList","000001");
        //        para.put("PRJID", "807");
        //        para.put("PRJACCOUNT", "test project name");
        //        para.put("PRJNAME", "test_project_name");
        fTC01010Main.setParameter(para);
        fTC01010Main.refreshWorkArea();
        TestPanel.show(fTC01010Main);
    }
}
