package client.essp.common.gantt;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import c2s.dto.ITreeNode;
import javax.swing.tree.TreeModel;
import client.framework.model.VMTreeTableModel;
import c2s.essp.common.gantt.ITreeGanttDto;
import c2s.essp.common.gantt.IGanttDto;


public class TreeCanttModelAdapter implements GanttModel {
    JTree tree;

    List ganttModelListenerList = new ArrayList();

    public TreeCanttModelAdapter( JTree tree ) {
        this.tree = tree;
    }

    public int getRowCount() {
        return tree.getRowCount();
    }

    public int getRowStyle(int row) {
        ITreeGanttDto dto = getDto(row);
        if (dto != null) {
            return dto.getStyle();
        } else {
            return IGanttDto.NONE_STYLE;
        }
    }

    public long getRowBeginTime(int row) {
        ITreeGanttDto dto = getDto(row);
        if (dto != null) {
            return dto.getBeginTime();
        } else {
            return 0;
        }
    }

    public long getRowEndTime(int row) {
        ITreeGanttDto dto = getDto(row);
        if (dto != null) {
            return dto.getEndTime();
        } else {
            return 0;
        }
    }

    public double getProcessRate(int row) {
        ITreeGanttDto dto = getDto(row);
        if (dto != null) {
            return dto.getProcessRate();
        } else {
            return 0;
        }
    }

    public int getLinkCount(int srcRow) {
        ITreeGanttDto dto = getDto(srcRow);
        if (dto != null) {
            return dto.getLinkCount();
        } else {
            return 0;
        }
    }

    public int getLinkRow(int srcRow, int i) {

        ITreeGanttDto dto = getDto(srcRow);
        if (dto != null) {
            ITreeNode relationNode = dto.getLinkNode(i);
            if( relationNode != null ){
                return rowForNode( relationNode );
            }
        }

        return -1;
    }

    public int getLinkType(int srcRow, int i) {
        ITreeGanttDto dto = getDto(srcRow);
        if (dto != null) {
            return dto.getLinkType(i);
        } else {
            return IGanttDto.NONE_STYLE;
        }
    }

    private ITreeGanttDto getDto(int row) {
        Object obj = nodeForRow(row);
        if( obj != null ){
            Object dto = ( (ITreeNode)obj ).getDataBean();
            return (ITreeGanttDto)dto;
        }else{
            return null;
        }
    }

    protected Object nodeForRow(int row) {
        TreePath treePath = tree.getPathForRow(row);
        if( treePath != null ){
            return treePath.getLastPathComponent();
        }else{
            return null;
        }
    }

    protected int rowForNode(ITreeNode node){
        TreeModel treeModel = tree.getModel();
        if( treeModel instanceof VMTreeTableModel ){
            ITreeNode[] nodes = ((VMTreeTableModel)treeModel).getPathToRoot(node);
            if( nodes != null ){
                TreePath treePath = new TreePath(nodes);
                return tree.getRowForPath(treePath);
            }
        }

        return -1;
    }

    public void addGanttModelListener(GanttListener l){
        this.ganttModelListenerList.add(l);
    }

    public void removeGanttModelListener(GanttListener l){
        this.ganttModelListenerList.remove(l);
    }

    public void fireGanttChanged(int eventType){
        for( int i = 0 ; i < this.ganttModelListenerList.size(); i++ ){
            GanttListener l = (GanttListener)this.ganttModelListenerList.get(i);
            l.ganttChanged(eventType);
        }
    }
}
