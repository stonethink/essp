package client.essp.timecard.worktime;

import client.essp.common.view.VWWorkArea;

import com.wits.util.TestPanel;

import java.awt.Dimension;


public class FTC01030Main extends VWWorkArea {
    private boolean         refreshFlag     = false;
    private FTC01030Initial fTC01030Initial = new FTC01030Initial(); //第一个CARD,总共一张卡片

    public FTC01030Main() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(800, 505));
        this.addTab("Set Work Time", fTC01030Initial);
    }

    public void saveWorkArea() {
        fTC01030Initial.saveWorkArea();
    }

    public void refreshWorkArea() {
        if (this.refreshFlag == true) {
            fTC01030Initial.setRefreshFlag(true);
            fTC01030Initial.refreshWorkArea();
        }
    }

    public static void main(String[] args) {
        FTC01030Main fTC01030Main = new FTC01030Main();
        TestPanel.show(fTC01030Main);
    }

    public void setRefreshFlag(boolean refreshFlag) {
        this.refreshFlag = refreshFlag;
    }
}
