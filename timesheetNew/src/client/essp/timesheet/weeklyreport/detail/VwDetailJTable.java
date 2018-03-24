package client.essp.timesheet.weeklyreport.detail;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import c2s.essp.timesheet.weeklyreport.DtoTimeSheet;
import client.essp.timesheet.weeklyreport.common.ColumnWithListener;
import client.essp.timesheet.weeklyreport.common.ResetRenderListener;
import client.framework.view.vwcomp.*;
import client.essp.timesheet.weeklyreport.common.VWJTwoNumReal;

/**
 * <p>Title: VwDetailJTable</p>
 *
 * <p>Description: ��ʱ����¼Table</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class VwDetailJTable extends VWJTable {

    private List<ColumnWithListener> columnWithListeners = new ArrayList();

    private VMTableDetail myModel;

    private List<ResetRenderListener> resetRenderListeners = new ArrayList();

    public VwDetailJTable(VMTableDetail model) {
        super(model);
        myModel = model;
    }

    public void doLayout() {
        super.doLayout();
        fireColumnWithChanged();
    }

    /**
     * ��Model�������ݣ�����������Render
     * @param data DtoTimeSheet
     */
    public void setData(DtoTimeSheet data) {
        myModel.setData(data);
        resetRender();

        //��������ݣ�ѡ�е�һ��
        if(this.getRowCount() > 0) {
            this.setSelectRow(0);
        }
    }

    /**
     * ����Render
     */
    private void resetRender() {
        int count = this.getColumnModel().getColumnCount();
        //ѭ��������
        for(int i = 0; i < count; i++) {
            TableColumn column = this.getColumnModel().getColumn(i);
            VWJTwoNumReal real = myModel.getWorkHourComp();
            if(myModel.isWorkHourColumn(i)) {
                column.setCellRenderer(new DetailHighLightRowRenderer(real));
                column.setCellEditor(new VWJTsTableEditor(real, myModel));
            } else if(i == myModel.getSumColumnIndex()) {
                column.setCellRenderer(new VWJTableRender(real));
            } else {
                column.setCellRenderer(TextTableCellRender.DEFAULT);
                //Description ����TableCellEditor
                if(VMTableDetail.isDescriptionColumn(i)) {
                	column.setCellEditor(new VWJDescTableEditor());
                }
            }
        }
        fireRenderNeedReset();
    }

    /**
     * �����п������
     * @param l ColumnWithListener
     */
    public void addColumnWithListener(ColumnWithListener l) {
        columnWithListeners.add(l);
    }

    /**
     * �����п�ı��¼�
     */
    private void fireColumnWithChanged() {
        if(getModel().getColumnCount() < 1) {
            return;
        }
        //Status ������
        int[] widths = new int[columnModel.getColumnCount() - 1];
        for (int i = 0; i < widths.length; i++) {
            widths[i] = columnModel.getColumn(i).getWidth();
        }

        for(int i = 0; i < columnWithListeners.size(); i++) {
            columnWithListeners.get(i).columnWidthChanged(widths);
        }
    }

    /**
     * ����Render���������
     * @param l ResetRenderListener
     */
    public void addResetRenderListener(ResetRenderListener l) {
        resetRenderListeners.add(l);
    }

    /**
     * ����Render��Ҫ����
     */
    private void fireRenderNeedReset() {
        Iterator<ResetRenderListener> iter = resetRenderListeners.iterator();
        while(iter.hasNext()) {
            iter.next().resetRender(myModel.getWorkHourComp());
        }
    }



}
