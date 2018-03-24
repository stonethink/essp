package client.essp.pms.account.baseline.compare.timing;

import javax.swing.table.DefaultTableCellRenderer;
import java.util.List;
import java.awt.Component;
import javax.swing.JTable;
import c2s.essp.pms.account.DtoAcntTiming;
import java.awt.Color;
import client.framework.view.vwcomp.NodeViewManager;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
public class VwAcntBLTableCellRenderer extends DefaultTableCellRenderer {
    private List objList;
    public VwAcntBLTableCellRenderer() {
    }
    public VwAcntBLTableCellRenderer(List objList){
      this.objList=objList;
    }
    public Component getTableCellRendererComponent(
                                                    JTable table,
                                                    Object value,
                                                    boolean isSelected,
                                                    boolean isFocused,
                                                    int row,int column){
            Component component=super.getTableCellRendererComponent(
                                table,value,isSelected,isFocused,row,column);
            if(value instanceof Date){  //Date型数据居中显示
                JLabel jl = (JLabel) component;
                jl.setHorizontalAlignment(SwingConstants.CENTER);
                component = (Component) jl;
            }else{
                JLabel jl = (JLabel) component;
                jl.setHorizontalAlignment(SwingConstants.LEFT);
                component = (Component) jl;

            }
            if(this.objList==null)return component;
            if(row>objList.size())return component;

           DtoAcntTiming dtoAcntTiming=(DtoAcntTiming)objList.get(row) ;
           int flag=dtoAcntTiming.getFlag();
           component.setForeground(Color.black);
           component.setBackground(Color.white);

           if(table.getSelectedRow()==row){
               component.setBackground(NodeViewManager.TableSelectFocusBack);
               component.setForeground(NodeViewManager.TableSelectFore);
           }else if(row%2==1){
               component.setBackground(NodeViewManager.TableLineEvenBack);
               component.setForeground(NodeViewManager.TableLineFore);
           }else{
               component.setBackground(NodeViewManager.TableLineOddBack);
               component.setForeground(NodeViewManager.TableLineFore);
           }
           if(flag==0){
               component.setForeground(Color.red);
               return component;
           }else if(flag==1){
               //component.setBackground(Color.blue);
               //component.setForeground(new Color(148,239,12));
               //component.setForeground(new Color(255,199,60));
               component.setForeground(new Color(63,199,63));
               return component;
           }else{
               return component;
           }
    }
}
