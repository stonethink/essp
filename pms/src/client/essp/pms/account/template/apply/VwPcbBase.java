package client.essp.pms.account.template.apply;

import com.wits.util.Parameter;
import client.essp.common.view.VWTDWorkArea;
import java.awt.Dimension;
import java.awt.AWTEvent;
import client.framework.view.event.RowSelectedListener;
import c2s.essp.pms.account.pcb.DtoPcbItem;

public class VwPcbBase extends VWTDWorkArea {
    private VwPcbItemList VwPcbItem;
    private VwPcbParameterList VwPcbParameter;
    private Parameter para ;

    public VwPcbBase(){
        super(180);
       enableEvents(AWTEvent.WINDOW_EVENT_MASK);

       try {
           JbInit();
           addUIEvent();
       } catch (Exception e) {
           e.printStackTrace();
       }


    }
    public void JbInit(){
        this.setPreferredSize(new Dimension(700, 470));
        this.setHorizontalSplit();
        para = new Parameter();
        VwPcbItem = new VwPcbItemList();
        VwPcbParameter = new VwPcbParameterList();

        this.getTopArea().addTab("Item List",VwPcbItem);
        this.getDownArea().addTab("Parameter List",VwPcbParameter);

    }
    public void addUIEvent(){
        this.VwPcbItem.getTable().addRowSelectedListener(new RowSelectedListener(){
           public void processRowSelected(){
               itemListRowSelected();
           }
        });
//        this.VwPcbParameter.getTable().addRowSelectedListener(new RowSelectedListener(){
//           public void processRowSelected(){
//
//           }
//        });
    }
    public void itemListRowSelected(){
        DtoPcbItem dtoPcbItem =(DtoPcbItem) VwPcbItem.getTable().getSelectedData();
        if(dtoPcbItem == null){
            return;
        }
        Long itemRid = dtoPcbItem.getRid();
        if( itemRid!= null){
            para.put("itemRid",itemRid);
        }
        VwPcbParameter.setParameter(para);
        VwPcbParameter.resetUI();
       // VwPcbParameter.refreshWorkArea();

    }
    public void setParameter(Parameter para){
        super.setParameter(para);
        this.para = para;
        VwPcbItem.setParameter(para);
        //VwPcbParameter.setParameter(para);
    }
    public void resetUI(){
        VwPcbItem.resetUI();
        //VwPcbParameter.resetUI();
    }
    public void refresh(){
        VwPcbItem.resetUI();
       // VwPcbParameter.resetUI();
    }
}
