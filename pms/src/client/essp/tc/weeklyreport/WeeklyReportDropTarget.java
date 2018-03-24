package client.essp.tc.weeklyreport;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

import c2s.essp.pwm.wp.IDtoScope;
import c2s.essp.tc.weeklyreport.DtoWeeklyReport;
import client.framework.view.vwcomp.VWJTable;
import c2s.dto.IDto;
import java.awt.Component;
import com.wits.util.StringUtil;

public class WeeklyReportDropTarget implements DropTargetListener {
    private VWJTable tableWeeklyReport;
    private VwWeeklyReportListBase tableWorkArea;

//    public WeeklyReportDropTarget(VWJTable aTable) {
//        tableWeeklyReport = aTable;
//    }

    public WeeklyReportDropTarget(VwWeeklyReportListBase tableWorkArea) {
        tableWeeklyReport = tableWorkArea.getTable();
        this.tableWorkArea = tableWorkArea;
    }
    public DropTarget create() {
        new DropTarget(tableWorkArea, this);
        return new DropTarget(tableWeeklyReport, this);
    }

    public void dragEnter(DropTargetDragEvent event) {
        int a = event.getDropAction();
        if ((a & DnDConstants.ACTION_MOVE) != 0)
            System.out.println("ACTION_MOVE dragEnter\n");
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
            return;
        }
    }

    public void dragExit(DropTargetEvent event) {
    }

    public void dragOver(DropTargetDragEvent event) {
        // you can provide visual feedback here
    }

    public void dropActionChanged(DropTargetDragEvent event) {
        if (!isDragAcceptable(event)) {
            event.rejectDrag();
            return;
        }
    }

    public void drop(DropTargetDropEvent event) {
        if (!isDropAcceptable(event)) {
            event.rejectDrop();
            return;
        }

        int eventType = event.getDropAction();
        if ((eventType & DnDConstants.ACTION_MOVE) == 0) {
            return;
        }

        event.acceptDrop(event.getDropAction());
        Transferable transferable = event.getTransferable();
        VMTableWeeklyReport model = (VMTableWeeklyReport) tableWeeklyReport.getModel();
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        Point p = event.getLocation();
        Component source = ((DropTarget)event.getSource()).getComponent();
        boolean toUpdate = false;
        int dropRow = 0;
        if( source == tableWeeklyReport ){
            dropRow = tableWeeklyReport.rowAtPoint(p);
            if(dropRow >= 0 && dropRow < model.getRowCount() ){
                toUpdate = true;
            }
        }

        for (int i = 0; i < flavors.length; i++) {
            DataFlavor d = flavors[i];
            try {
                if( d.isMimeTypeEqual(DataFlavor.javaSerializedObjectMimeType)){
                    IDtoScope scope = (IDtoScope)transferable.getTransferData(d);

                    DtoWeeklyReport dto ;
                    if(toUpdate){
                        dto = (DtoWeeklyReport) model.getRow(dropRow);

                        dto.setAcntRid(scope.getAcntRid());
                        dto.setActivityRid(scope.getActivityRid());
                        if( scope.getAcntCode() != null ){
                            dto.setAcntName(scope.getAcntCode() + " -- " + StringUtil.nvl(scope.getAcntName()));
                        }else{
                            dto.setAcntName(null);
                        }
                        if( scope.getActivityCode() != null ){
                            dto.setActivityName(scope.getActivityCode() + " -- " + StringUtil.nvl(scope.getActivityName()));
                        }else{
                            dto.setActivityName(null);
                        }
                        dto.setJobDescription(scope.getDescription());

                        dto.setOp(IDto.OP_MODIFY);
                        model.fireTableRowsUpdated(dropRow, dropRow);
                    }else{

                        //add one
                        dto = tableWorkArea.createDto();
                        dto.setAcntRid(scope.getAcntRid());
                        dto.setActivityRid(scope.getActivityRid());
                        if( scope.getAcntCode() != null ){
                            dto.setAcntName(scope.getAcntCode() + " -- " + StringUtil.nvl(scope.getAcntName()));
                        }else{
                            dto.setAcntName(null);
                        }
                        if( scope.getActivityCode() != null ){
                            dto.setActivityName(scope.getActivityCode() + " -- " + StringUtil.nvl(scope.getActivityName()));
                        }else{
                            dto.setActivityName(null);
                        }
                        dto.setJobDescription(scope.getDescription());

                        this.tableWeeklyReport.addRow(dto);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        event.dropComplete(true);
    }

    public boolean isDragAcceptable(DropTargetDragEvent event) {
        // usually, you check the available data flavors here
        // in this program, we accept all flavors
        return (event.getDropAction()
                & DnDConstants.ACTION_COPY_OR_MOVE) != 0;
    }

    public boolean isDropAcceptable(DropTargetDropEvent event) {
        // usually, you check the available data flavors here
        // in this program, we accept all flavors
        if (((event.getDropAction()
              & DnDConstants.ACTION_COPY_OR_MOVE) != 0) == false) {
            return false;
        }

        Point p = event.getLocation();
        Component source = ((DropTarget) event.getSource()).getComponent();
        boolean toUpdate = false;
        int dropRow = 0;
        if (source == tableWeeklyReport) {
            dropRow = tableWeeklyReport.rowAtPoint(p);
            if (dropRow >= 0 && dropRow < tableWeeklyReport.getRowCount()) {
                toUpdate = true;
            }
        }

        if( toUpdate ){
            //select the data
            tableWeeklyReport.setSelectRow(dropRow);

            //alter
            if (tableWorkArea.btnUpdate.isVisible() == false ||
                tableWorkArea.btnUpdate.isEnabled() == false) {
                return false;
            }
        } else {
            //add
            if (tableWorkArea.btnAdd.isVisible() == false ||
                tableWorkArea.btnAdd.isEnabled() == false) {
                return false;
            }
        }

        return true;
    }
}
