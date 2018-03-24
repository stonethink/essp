package client.essp.pwm.wpList;

import client.essp.common.view.VWGeneralWorkArea;
import client.framework.view.vwcomp.VWJComboBox;
import java.awt.event.ActionEvent;
import client.framework.model.VMComboBox;
import client.framework.view.vwcomp.VWJLabel;
import client.essp.common.humanAllocate.VWJHrAllocateButton;
import client.essp.common.humanAllocate.HrAllocate;
import client.framework.view.vwcomp.VWJTable;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import client.framework.view.vwcomp.IVWPopupEditorEvent;
import java.util.List;
import java.util.ArrayList;
import c2s.essp.pwm.wp.DtoPwWp;
import java.util.Date;
import client.framework.view.vwcomp.VWJDate;
import client.framework.view.common.comMSG;
import com.wits.util.comDate;

public class VwWpFilter extends VWGeneralWorkArea
      implements IVWPopupEditorEvent{
    private VWJComboBox conditionSel = new VWJComboBox();
    final static String[] FILTER_CONDITION = {"All WP",
                                             "Unclosed WP",
                                             "Closed WP",
                                             "Assign By",
                                             "Workers",
                                             "Plan Period"};
    VWJLabel lblSelect = new  VWJLabel();
    VWJLabel lblAssignBy = new  VWJLabel();
    VWJLabel lblPlanStart = new  VWJLabel();
    VWJLabel lblPlanEnd = new  VWJLabel();
    VWJDate PlanStart = new VWJDate();
    VWJDate PlanEnd = new VWJDate();

    VWJHrAllocateButton wpperson = new VWJHrAllocateButton(HrAllocate.ALLOC_MULTIPLE);
    private VWJTable table;
    private String currentFilter;
    private VwWpList vwWpList;
    VWJLabel filterLbl = new VWJLabel();

    public VwWpFilter(VWJTable table, VwWpList wpList,
                            VWJLabel currentFilterLbl){
        this.table = table;
        this.vwWpList = wpList;
        this.filterLbl = currentFilterLbl;
        this.currentFilter = currentFilterLbl.getText();
        init();
    }

    public VwWpFilter(VWJTable table, VwWpList wpList){
        this(table,wpList,null);
    }


    public void init() {
        this.setLayout(null);
        lblSelect.setText("Please select filter condition");
        lblSelect.setSize(200, 20);
        lblSelect.setLocation(50, 10);
        this.add(lblSelect);

        VMComboBox conditionSelModel = VMComboBox.toVMComboBox(VwWpFilter.
            FILTER_CONDITION, VwWpFilter.FILTER_CONDITION);
        conditionSel.setModel(conditionSelModel);
        conditionSel.setSize(200, 20);
        conditionSel.setLocation(50, 35);
        this.add(conditionSel);

        lblAssignBy.setText("Assign By");
        lblAssignBy.setSize(60, 20);
        lblAssignBy.setLocation(50, 65);
        lblAssignBy.setVisible(false);
        this.add(lblAssignBy);

        wpperson.setSize(140, 20);
        wpperson.setLocation(110, 65);
        wpperson.setVisible(false);
        this.add(wpperson);

        lblPlanStart.setText("Plan Start");
        lblPlanStart.setSize(60, 20);
        lblPlanStart.setLocation(50, 65);
        lblPlanStart.setVisible(false);
        this.add(lblPlanStart);

        PlanStart.setSize(140, 20);
        PlanStart.setLocation(110, 65);
        PlanStart.setCanSelect(true);
        PlanStart.setVisible(false);
        this.add(PlanStart);

        lblPlanEnd.setText("Plan End");
        lblPlanEnd.setSize(60, 20);
        lblPlanEnd.setLocation(50, 90);
        lblPlanEnd.setVisible(false);
        this.add(lblPlanEnd);

        PlanEnd.setSize(140, 20);
        PlanEnd.setLocation(110, 90);
        PlanEnd.setCanSelect(true);
        PlanEnd.setVisible(false);
        this.add(PlanEnd);

        if (this.currentFilter != null && this.currentFilter.length() > 0) {
            conditionSel.setSelectedItem(this.currentFilter);
            int index = 0;
            for (int i = 0; i < FILTER_CONDITION.length; i++) {
                if (this.currentFilter.equals(FILTER_CONDITION[i])) {
                    index = i;
                    break;
                }
            }
            conditionSel.setSelectedIndex(index);
            if(currentFilter.equals(FILTER_CONDITION[3])) {
                lblAssignBy.setVisible(true);
                wpperson.setVisible(true);
                lblAssignBy.setText("Assign By");
                wpperson.requestFocus();
            }else if(currentFilter.equals(FILTER_CONDITION[4])){
                lblAssignBy.setVisible(true);
                wpperson.setVisible(true);
                lblAssignBy.setText("Workers");
                wpperson.requestFocus();
            }else if(currentFilter.equals(FILTER_CONDITION[5])) {
                lblPlanStart.setVisible(true);
                lblPlanEnd.setVisible(true);
                PlanStart.setVisible(true);
                PlanEnd.setVisible(true);
                PlanStart.requestFocus();
            }
        } else {
            conditionSel.setSelectedIndex(0);
            currentFilter = FILTER_CONDITION[0];
        }

        conditionSel.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                currentFilter = conditionSel.getUICValue().toString();
                if (currentFilter.equals(FILTER_CONDITION[3])) {
                    //输入Assign By
                    lblAssignBy.setVisible(true);
                    lblAssignBy.setText("Assign By");
                    wpperson.setVisible(true);
                    wpperson.requestFocus();
                    lblPlanStart.setVisible(false);
                    lblPlanEnd.setVisible(false);
                    PlanStart.setVisible(false);
                    PlanEnd.setVisible(false);
                } else if(currentFilter.equals(FILTER_CONDITION[4])){
                    //输入Workers
                    lblAssignBy.setVisible(true);
                    lblAssignBy.setText("Workers");
                    wpperson.setVisible(true);
                    wpperson.requestFocus();
                    lblPlanStart.setVisible(false);
                    lblPlanEnd.setVisible(false);
                    PlanStart.setVisible(false);
                    PlanEnd.setVisible(false);
                }else if (currentFilter.equals(FILTER_CONDITION[5])) {
                    //输入计划起止时间
                    lblPlanStart.setVisible(true);
                    lblPlanEnd.setVisible(true);
                    PlanStart.setVisible(true);
                    PlanEnd.setVisible(true);
                    PlanStart.requestFocus();
                    lblAssignBy.setVisible(false);
                    wpperson.setVisible(false);
                } else {
                    lblPlanStart.setVisible(false);
                    lblPlanEnd.setVisible(false);
                    PlanStart.setVisible(false);
                    PlanEnd.setVisible(false);
                    lblAssignBy.setVisible(false);
                    wpperson.setVisible(false);
                }
            } //Method end
        });
    }

    public boolean onClickOK(ActionEvent e) {
      List wpLists= new ArrayList();
      List filterList = new ArrayList();
      String toolText = currentFilter;
      if (currentFilter.equals(FILTER_CONDITION[0])) {
          //选取的是所有的Activity
          wpLists = vwWpList.wpList;
          filterList = wpLists;
      } else if (currentFilter.equals(FILTER_CONDITION[1])) {
          //选取Unclosed Activity
          wpLists = vwWpList.wpList;
          for (int i = 0; i < wpLists.size(); i++) {
              DtoPwWp dto = (DtoPwWp) wpLists.get(i);
              if(!(dto.getWpStatus()).equals("Closed")){
                  filterList.add(dto);
              }
          }
      } else if (currentFilter.equals(FILTER_CONDITION[2])) {
          //选取Closed Activity
          wpLists = vwWpList.wpList;
          for (int i = 0; i < wpLists.size(); i++) {
              DtoPwWp dto = (DtoPwWp) wpLists.get(i);
              if((dto.getWpStatus()).equals("Closed")){
                  filterList.add(dto);
              }
          }
      } else if (currentFilter.equals(FILTER_CONDITION[3])) {
          //选的是根据Assign By来查找
          wpLists = vwWpList.wpList;
          String assignBy = (String)wpperson.getUICValue();
          if(assignBy == null || assignBy.equals("")){
              comMSG.dispErrorDialog("Please input assign by!");
              return false;
          }
          for (int i = 0; i < wpLists.size(); i++) {
              DtoPwWp dto = (DtoPwWp) wpLists.get(i);
              if((dto.getWpAssignby()).equals(assignBy)){
                 filterList.add(dto);
              }
          }
          toolText = toolText + ": "+assignBy;
      } else if (currentFilter.equals(FILTER_CONDITION[4])) {
          //根据Workers来过滤
          wpLists = vwWpList.wpList;
          String Workers = (String)wpperson.getUICValue();
          if(Workers == null || Workers.equals("")){
              comMSG.dispErrorDialog("Please input workers!");
              return false;
          }
          String[] worker = Workers.split(",");
          for (int i = 0; i < wpLists.size(); i++) {
              DtoPwWp dto = (DtoPwWp) wpLists.get(i);
              for(int j =0; j<worker.length; j++){
                  if ((dto.getWpWorker()).equals(worker[j]) || (dto.getWpWorker()).contains(worker[j])) {
                      filterList.add(dto);
                      break;
                  }
              }
          }
          toolText = toolText +": "+Workers;
      }else if (currentFilter.equals(FILTER_CONDITION[5])) {
          //根据计划时间范围来过滤
          wpLists = vwWpList.wpList;
          Date planStart = (Date)PlanStart.getUICValue();
          Date planEnd = (Date)PlanEnd.getUICValue();
          if(planStart == null || planEnd == null){
              comMSG.dispErrorDialog("Please input planStart or planEnd!");
              return false;
          }
          if(planStart.after(planEnd)){
              comMSG.dispErrorDialog(" The plan start  cannot be bigger than the plan finish.!");
              return false;
          }
          for (int i = 0; i < wpLists.size(); i++) {
              DtoPwWp dto = (DtoPwWp) wpLists.get(i);
              if(dto.getWpPlanStart() == null || dto.getWpPlanFihish() == null)
                  continue;
              if(!(dto.getWpPlanFihish()).before(planStart)
                  && !(dto.getWpPlanStart()).after(planEnd)){
                  filterList.add(dto);
              }
          }
          toolText = toolText +": "+(String)comDate.dateToString(planStart,"yyyy-MM-dd")+
                     " ~ "+(String)comDate.dateToString(planEnd,"yyyy-MM-dd");
      }
      table.setRows(filterList);
      filterLbl.setText(currentFilter);
      filterLbl.setToolTipText("Filter by:"+toolText);
      return true;
    }

    public boolean onClickCancel(ActionEvent e) {
        return true;
    }

}
