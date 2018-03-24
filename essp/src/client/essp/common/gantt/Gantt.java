package client.essp.common.gantt;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import client.framework.view.vwcomp.VWJTreeTable;
import java.awt.Point;
import javax.swing.event.ChangeEvent;
import client.framework.view.vwcomp.VWJTable;
import client.framework.model.VMTable2;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.event.MouseWheelListener;
import javax.swing.JScrollBar;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: 甘特图(包括日期title),不含table</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Gantt extends JScrollPane {
    GanttHeader header;
    GanttGraphic ganttGraphic;

    public Gantt(GanttModel model, GanttUI ganttUI) {
        ganttGraphic = new GanttGraphic(model, ganttUI);
        header = new GanttHeader(ganttUI);

        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Gantt(GanttModel model, long minTime, long maxTime) {
        this(model, new DefaultCanttUI(minTime, maxTime));
    }

    public Gantt(VWJTreeTable treeTable, long minTime, long maxTime) {
        this(new TreeCanttModelAdapter(treeTable.getTree()), minTime, maxTime);

        (new TableSetter(treeTable)).set();
    }

    //Component initialization
    private void jbInit() throws Exception {
        this.getViewport().add(ganttGraphic);

        //给甘特图加标题
        JViewport ganttHeadViewport = new JViewport();
        ganttHeadViewport.add(this.header);
        this.setColumnHeader(ganttHeadViewport);

        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    }

    /**
     * <p>Description: </p>
     * 对甘特图和table进行设置,主要是同步滚动
     */
    public class TableSetter {
        JTable table;
        JScrollPane scrollPaneGantt = Gantt.this;

        public TableSetter(JTable table) {
            this.table = table;
        }

        public void set() {
            table.getTableHeader().setPreferredSize(new Dimension(65535, header.getHeight()));
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            scrollPaneGantt.getVerticalScrollBar().setUnitIncrement(table.getRowHeight());

            if (table instanceof VWJTreeTable) {
                setVWJTreeTable();
            }
        }

        private void setVWJTreeTable() {
            VWJTreeTable treeTable = (VWJTreeTable) table;

            treeTable.getTree().addTreeExpansionListener(new TreeExpansionListener() {
                public void treeExpanded(TreeExpansionEvent e) {
                    ganttGraphic.setSize(ganttGraphic.getPreferredSize());
                }

                public void treeCollapsed(TreeExpansionEvent e) {
                    ganttGraphic.setSize(ganttGraphic.getPreferredSize());
                }
            });

            ganttGraphic.addGanttListener(new GanttListener() {
                public void ganttChanged(int type) {
                    if (type == GanttListener.ROW_SELECTED) {
                        ((VWJTreeTable) table).setSelectedRow(ganttGraphic.getSelectedRow());
                    }
                }
            });

            JScrollPane scrollPaneTree = treeTable.getScrollPane();
            scrollPaneTree.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollPaneTree.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            scrollPaneTree.setWheelScrollingEnabled(false);
            scrollPaneTree.addMouseWheelListener(new MouseWheelListener() {
                public void mouseWheelMoved(java.awt.event.MouseWheelEvent e) {
                    scrollByUnits(scrollPaneGantt.getVerticalScrollBar(), e.getWheelRotation(),
                                  e.getScrollAmount());
                }
            });

            setScroll(scrollPaneTree, scrollPaneGantt, treeTable.getRowHeight());
        }

        private void setVWJTable() {}

        private void scrollByUnits(JScrollBar scrollbar, int direction,
                                  int units) {
            int delta = units;

            if (direction > 0) {
                delta *= scrollbar.getUnitIncrement(direction);
            } else {
                delta *= -scrollbar.getUnitIncrement(direction);
            }

            int oldValue = scrollbar.getValue();
            int newValue = oldValue + delta;

            // Check for overflow.
            if (delta > 0 && newValue < oldValue) {
                newValue = scrollbar.getMaximum();
            } else if (delta < 0 && newValue > oldValue) {
                newValue = scrollbar.getMinimum();
            }
            scrollbar.setValue(newValue);
        }

        private void setScroll(JScrollPane tableSP, JScrollPane ganttSP, int rowHeight) {
            if (tableSP != null && ganttSP != null) {
                ganttSP.getVerticalScrollBar().setUnitIncrement(rowHeight / 2);

                tableSP.setVerticalScrollBarPolicy(JScrollPane.
                    VERTICAL_SCROLLBAR_NEVER);
                tableSP.setHorizontalScrollBarPolicy(JScrollPane.
                    HORIZONTAL_SCROLLBAR_ALWAYS);

                ganttSP.setVerticalScrollBarPolicy(JScrollPane.
                    VERTICAL_SCROLLBAR_AS_NEEDED);
                ganttSP.setHorizontalScrollBarPolicy(JScrollPane.
                    HORIZONTAL_SCROLLBAR_ALWAYS);

                JViewport viewPortGannt = ganttSP.getViewport();
                JViewport viewPortTree = tableSP.getViewport();

                viewPortGannt.addChangeListener(new ScrollChangeListener(
                    ganttSP, tableSP));
                viewPortTree.addChangeListener(new ScrollChangeListener(
                    tableSP, ganttSP));
            }
        }

    }


    public class ScrollChangeListener implements javax.swing.event.
        ChangeListener {
        JScrollPane scrollPane;
        JScrollPane listenerScrollPane;

        public ScrollChangeListener(JScrollPane scrollPane,
                                    JScrollPane listenerScrollPane
            ) {
            this.scrollPane = scrollPane;
            this.listenerScrollPane = listenerScrollPane;
        }

        //是listnerScrollPane随 scrollPanel的垂直滚动 而垂直滚动
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == this) { //防死循环
                return;
            }

            Point p = listenerScrollPane.getViewport().getViewPosition();
            p.y = scrollPane.getViewport().getViewPosition().y;
            listenerScrollPane.getViewport().setViewPosition(p);
        }

    }
}
