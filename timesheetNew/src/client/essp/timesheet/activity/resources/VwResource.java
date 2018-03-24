package client.essp.timesheet.activity.resources;

import java.awt.Dimension;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.essp.common.view.VWTDWorkArea;
import c2s.essp.timesheet.activity.DtoActivityKey;
import c2s.essp.timesheet.activity.DtoResourceAssignment;
import client.framework.view.event.RowSelectedListener;
import com.wits.util.Parameter;
import client.framework.view.event.DataChangedListener;
/**
 * <p>Title: VwResource</p>
 *
 * <p>Description: Resource�������ܵĿ�Ƭ</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author TBH
 * @version 1.0
 */
public class VwResource extends VWTDWorkArea implements DataChangedListener{
    DtoResourceAssignment dtoResource;
    VwResourceLeftBench vwLeftBench = null;
    VwResourceRightBench vwRightBench = null;
    public Boolean isPrimaryResource = false;

    public VwResource() {
         super(450);
        try {
            jbInit();
        } catch (Exception ex) {
        }
       addUICEvent();
    }

     //��ʼ��
      private void jbInit() throws Exception {
          this.setHorizontalSplit();
          this.setPreferredSize(new Dimension(650, 300));
          vwLeftBench = new VwResourceLeftBench();
          vwLeftBench.setPreferredSize(new Dimension(420,300));
          this.getTopArea().addTab("rsid.timesheet.assignedResources", vwLeftBench);

          vwRightBench = new VwResourceRightBench();
          vwRightBench.setPreferredSize(new Dimension(200,300));
          this.getDownArea().addTab("", vwRightBench);
     }
      
     //�����¼�
     private void addUICEvent() {
         vwLeftBench.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
 			public void valueChanged(ListSelectionEvent e) {
 				if(e.getValueIsAdjusting() == false) {
 					processRowSelectedAccList();
 				}
              }
        });
     }

    //��������
    public void setParameter(Parameter parameter) {
        vwLeftBench.setParameter(parameter);
        parameter.put(DtoActivityKey.DTO_ISPRIMARY_RESOURCE,isPrimaryResource);
        vwRightBench.setParameter(parameter);

    }
   //ˢ��
    public void refreshWorkArea() {
        vwLeftBench.refreshWorkArea();

    }

    //����ĳһ��ʱ�������¼�
    public void processRowSelectedAccList() {
       dtoResource = (DtoResourceAssignment)vwLeftBench.getTable().
                                           getSelectedData();
       if (dtoResource != null) {
          this.vwRightBench.setResDto(dtoResource);
          this.getDownArea().setTabTitle(0,dtoResource.getResourceName());
       } else {
          this.vwRightBench.setResDto((DtoResourceAssignment)null);
          this.getDownArea().setTabTitle(0, "");
       }
       isPrimaryResource = vwLeftBench.isPrimaryResource;
       Parameter parameter = new Parameter();
       parameter.put(DtoActivityKey.DTO_ISPRIMARY_RESOURCE,isPrimaryResource);
       vwRightBench.setParameter(parameter);
       vwRightBench.refreshWorkArea();
    }

    public void processDataChanged() {
        vwRightBench.processDataChanged();
    }

}


