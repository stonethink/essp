package client.essp.pms.account.baseline.compare.budget;


import client.framework.model.VMColumnConfig;
import c2s.essp.cbs.budget.DtoBudgetLog;
import c2s.essp.pms.account.DtoAcntKEY;
import com.wits.util.Parameter;
import client.essp.common.view.VWTableWorkArea;
import java.util.ArrayList;
import java.util.List;
import client.essp.common.view.VWWorkArea;
import com.wits.util.TestPanel;
import client.framework.view.vwcomp.VWJReal;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import client.framework.view.vwcomp.NodeViewManager;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class VwAcntBLBudCurList extends VWTableWorkArea{
    static final String actionIdList = "FAcntBLBudAction";
    /**
    * define common data (globe)
    */
   Long acntRid;
   Integer flag1;
   Integer flag2;
    private List acntBLBudList=new ArrayList();
    public VwAcntBLBudCurList() {
          VWJReal number = new VWJReal();
          number.setMaxInputDecimalDigit(2);
          number.setMaxInputIntegerDigit(8);

           Object[][] configs = new Object[][] {
                                {"PM","changeBugetPm",VMColumnConfig.UNEDITABLE, number},
                                {"AMT","changeBugetAmt",VMColumnConfig.UNEDITABLE, number},
           };
        try {
          super.jbInit(configs,DtoBudgetLog.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    //本workArea不需要外界的parameter
   public void setParameter(Parameter param) {
       super.setParameter(param);
       this.acntRid = (Long) (param.get(DtoAcntKEY.ACNT_RID));
       this.acntBLBudList=(List)param.get("BLBud");
       this.flag1=(Integer)param.get("flag1");
       this.flag2=(Integer)param.get("flag2");

   }
   public class BudTableCellRenderer extends DefaultTableCellRenderer{
       public Component getTableCellRendererComponent(
           JTable table,
           Object value,
           boolean isSelected,
           boolean isFocused,
           int row, int column) {

           Component component = super.getTableCellRendererComponent(
               table, value, isSelected, isFocused, row, column);
           JLabel vj = (JLabel) component;
           vj.setHorizontalAlignment(SwingConstants.RIGHT);
           component = (Component) vj;
           component.setForeground(Color.black);
           component.setBackground(Color.white);
           if (table.getSelectedRow() == row) {
               component.setBackground(NodeViewManager.TableSelectFocusBack);
               component.setForeground(NodeViewManager.TableSelectFore);
           } else if (row % 2 == 1) {
               component.setBackground(NodeViewManager.TableLineEvenBack);
               component.setForeground(NodeViewManager.TableLineFore);
           } else {
               component.setBackground(NodeViewManager.TableLineOddBack);
               component.setForeground(NodeViewManager.TableLineFore);
           }

               component.setForeground(Color.red);
               return component;


       }
   }

       //页面刷新－－－－－
       protected void resetUI() {

           DefaultTableCellRenderer renderer = new BudTableCellRenderer();
           if (flag1.intValue() == 0)
           this.getTable().getColumnModel().getColumn(0).setCellRenderer(
               renderer);
          if(flag2.intValue() == 1)
           this.getTable().getColumnModel().getColumn(1).setCellRenderer(
               renderer);
           getTable().setRows(acntBLBudList);
       }


       public static void main(String args[]) {
           VWWorkArea w1 = new VWWorkArea();
           VWWorkArea workArea = new VwAcntBLBudCurList();
           w1.addTab("BaseLine", workArea);
           TestPanel.show(w1);

           Parameter param = new Parameter();
           param.put(DtoAcntKEY.ACNT_RID, new Long(7));
           workArea.setParameter(param);
           workArea.refreshWorkArea();
       }
   }




