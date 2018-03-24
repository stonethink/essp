package client.essp.pms.account.baseline.compare.timing;

import java.awt.Dimension;
import java.util.List;

import c2s.dto.InputInfo;
import c2s.dto.ReturnInfo;
import c2s.essp.pms.account.DtoAcntTiming;
import client.essp.common.AccessServer;
import client.essp.common.view.VWTDWorkArea;
import client.framework.view.event.RowSelectedListener;
import client.framework.view.vwcomp.VWJTable;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VwAcntBLTiming extends VWTDWorkArea {
    static final String actionTimingList = "FAcntBaseLineTimingListAction";
    VwAcntMilestone  vwAcntMilestone;
    VwAcntBaseline   vwAcntBaseline;
    List baselineList;
    List milestoneList;
    private DtoAcntTiming dtoAcntTiming;


    public VwAcntBLTiming(){
        super(180);
        dtoAcntTiming=new DtoAcntTiming();
        this.setPreferredSize(new Dimension(700, 200));
        this.setHorizontalSplit();
        this.setSplitHeight(350);
        vwAcntMilestone=new VwAcntMilestone();
        vwAcntBaseline=new VwAcntBaseline();

        this.getTopArea().add(vwAcntBaseline);
        this.getDownArea().add(vwAcntMilestone);
//        this.getTopArea().addTab("Baseline",vwAcntBaseline);
//        this.getDownArea().addTab("Milestone",vwAcntMilestone);

       addUIEvent();
    }
    public void addUIEvent(){
           vwAcntBaseline.getTable().addRowSelectedListener(new RowSelectedListener(){
            public void processRowSelected() {
                processRowSelectedBl(vwAcntBaseline.getTable(),vwAcntMilestone.getTable());
            }
          });
          vwAcntMilestone.getTable().addRowSelectedListener(new RowSelectedListener(){
               public void processRowSelected(){
                   processRowSelectedBl(vwAcntMilestone.getTable(),vwAcntBaseline.getTable());
               }
          });
    }

    public void setParameter(Parameter param){
        super.setParameter(param);
        if(param.get("acntRid")!=null)
           this.dtoAcntTiming.setAcntRid((Long)param.get("acntRid"));
    }
    public void resetUI(){
        InputInfo inputInfo = new InputInfo();
        inputInfo.setActionId(actionTimingList);
        inputInfo.setInputObj("acntRid",dtoAcntTiming.getAcntRid());

        ReturnInfo returnInfo=AccessServer.accessData(inputInfo);
        if(returnInfo.isError()==false){
             milestoneList=(List)returnInfo.getReturnObj("currentMilestone");
            baselineList=(List)returnInfo.getReturnObj("lastBaseLine");
            String baseLineId=(String)returnInfo.getReturnObj("baseLineId");
            Parameter param=new Parameter();
            param.put("currentMilestone",milestoneList);
            param.put("lastBaseLine",baselineList);
            param.put("acntRid",dtoAcntTiming.getAcntRid());
            param.put("baseLineId",baseLineId);
            vwAcntBaseline.setParameter(param);
            vwAcntBaseline.refreshWorkArea();
            vwAcntMilestone.setParameter(param);
            vwAcntMilestone.refreshWorkArea();

        }
    }

    public void processRowSelectedBl(VWJTable initTable,VWJTable passTable){
       int initRow= initTable.getSelectedRow();
       int passRow=passTable.getSelectedRow();
       if(initRow!=passRow){
           passTable.setSelectRow(initRow);
           passTable.fireSelected();
       }

   }
}
