package client.essp.pms.account.baseline.compare.budget;

import client.essp.common.view.VWTDWorkArea;
import client.essp.common.view.VWWorkArea;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.event.RowSelectedLostListener;
import com.wits.util.Parameter;
import com.wits.util.TestPanel;
import java.awt.AWTEvent;
import java.awt.Dimension;
import c2s.essp.pms.account.DtoAcntKEY;
import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import java.util.List;
import java.util.ArrayList;
import client.essp.common.AccessServer;

public class VwAcntBLBudCom extends VWTDWorkArea{
    static final String actionIdList = "FAcntBLBudAction";
    /**
     * define control variable
     */
//    private boolean refreshFlag = false;

    /**
     * define common data (globe)
     */
    Long acntRid;
    Integer flag1;
    Integer flag2;

    String version;
//    StringBuffer blid;
    private List acntBLBudList=new ArrayList();
    /////// 段1，定义界面：定义界面（定义控件，设置控件名称，光标控制顺序）、定义界面事件
    VwAcntBLBudCurBase vwAcntBLBudCurBase;
    VwAcntBLBudLastBase vwAcntBLBudLastBase;
    /**
     * default constructor
     */
    public VwAcntBLBudCom() {
        super(180);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//Component initialization
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(700, 200));
        this.setHorizontalSplit();
        this.setSplitHeight(350);
        vwAcntBLBudCurBase = new VwAcntBLBudCurBase();
        vwAcntBLBudLastBase = new VwAcntBLBudLastBase();
        this.getTopArea().add(vwAcntBLBudLastBase);
        this.getDownArea().add(vwAcntBLBudCurBase);

    }
    /////// 段2，设置参数：获取传入参数
    public void setParameter(Parameter param) {
        this.acntRid = (Long) param.get(DtoAcntKEY.ACNT_RID);

    }

     public void resetUI(){
               InputInfo inputInfo = new InputInfo();
      inputInfo.setActionId(this.actionIdList);
     inputInfo.setInputObj(DtoAcntKEY.ACNT_RID, this.acntRid);
              ReturnInfo returnInfo =AccessServer.accessData(inputInfo);
              if (returnInfo.isError() == false) {
           acntBLBudList= (List) returnInfo.getReturnObj("BLBud");
           version=(String)returnInfo.getReturnObj("BaseId");
           flag1=(Integer)returnInfo.getReturnObj("flag1");
           flag2=(Integer)returnInfo.getReturnObj("flag2");
            Parameter param=new Parameter();
            param.put("BLBud",acntBLBudList);
            param.put(DtoAcntKEY.ACNT_RID,this.acntRid);
            param.put("BaseId",this.version);
            param.put("flag1",flag1);
             param.put("flag2",flag2);
            vwAcntBLBudCurBase.setParameter(param);
            vwAcntBLBudCurBase.refreshWorkArea();
            vwAcntBLBudLastBase.setParameter(param);
            vwAcntBLBudLastBase.refreshWorkArea();
       }


     }
     public static void main(String[] args) {
         VWWorkArea workArea = new VwAcntBLBudCom();
         Parameter param = new Parameter();
       param.put(DtoAcntKEY.ACNT_RID, new Long(7));

       workArea.setParameter(param);
       VWWorkArea workArea2 = new VWWorkArea();
               workArea2.addTab("BaseLine", workArea);

               TestPanel.show(workArea2);
        workArea.refreshWorkArea();
     }

}
