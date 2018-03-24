package client.essp.pms.gantt;

import client.essp.pms.activity.VwActivityList;
import client.essp.common.view.VWWorkArea;
import com.wits.util.TestPanel;
import com.wits.util.Parameter;

public class VwWbsGanttList extends VwActivityList{
    static final String actionIdList = "FPbsActivityGanttListAction";

    public String getActionIdList(){
        return this.actionIdList;
    }

    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            LookAndFeel lf=UIManager.getLookAndFeel();
//
//            UISetting.settingUIManager();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        VWWorkArea workArea0 = new VWWorkArea();
        VWWorkArea workArea = new VwWbsGanttList();

        workArea0.addTab("Activity", workArea);
        TestPanel.show(workArea0);

        Parameter param = new Parameter();
        param.put("inAcntRid", new Long(1));
        workArea.setParameter(param);

        workArea.refreshWorkArea();
    }

}
