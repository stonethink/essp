package client.framework.view.vwcomp;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import client.framework.model.VMColumnConfig;
import client.framework.model.VMTreeTableModel;

import c2s.dto.ITreeNode;
import c2s.dto.DtoUtil;
import java.awt.Color;
import java.util.List;


/**
 * <p>Title: Tree & Table Component</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: enovation</p>
 * @author yery
 * @version 1.0
 */

public class VWJTreeCellRender extends DefaultTreeCellRenderer {

    NodeViewManager nodeViewManager;
    String treeColumnName;

    public VWJTreeCellRender(NodeViewManager nodeViewManager) {
        this(nodeViewManager, null);
    }

    public VWJTreeCellRender(NodeViewManager nodeViewManager,
                             String treeColumnName) {
        this.nodeViewManager = nodeViewManager;
        this.treeColumnName = treeColumnName;
    }

    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        TreePath treepathByRow = tree.getPathForRow(row);
        if (treepathByRow != null) {
            ITreeNode node = (ITreeNode) (treepathByRow.getLastPathComponent());
            if (node != null && node instanceof ITreeNode) {
                ITreeNode treeNode = (ITreeNode) node;

                nodeViewManager.setNode(treeNode);
                nodeViewManager.setDataValue(value);
                nodeViewManager.setDataName(treeColumnName);

                String text = "";
                if (treeColumnName == null) {
                    if (value != null) {
                        text = value.toString();
                    }
                } else {
                    try {
                        text = (String) DtoUtil.getProperty(treeNode.
                            getDataBean(), treeColumnName);
                    } catch (Exception ex) {
                    }
                }
                if (text == null || text.equals("")) {
                    text = " ";
                }
                this.setText(text);
                this.setToolTipText(text);
                Dimension retDimension = this.getPreferredSize();
                if (text != null && text.equals("Configuration Management")) {
                    System.out.println("ok!");
                }
                if (text != null) {
                    int width = text.getBytes().length * 20 + 300;
                    if (retDimension.getWidth() < width) {
                        Dimension newDim = new Dimension(800,
                            (int) retDimension.getHeight());
                        tree.setPreferredSize(newDim);
                        tree.setMaximumSize(new Dimension(1024,(int) retDimension.getHeight()));
                        tree.setSize(newDim);
                        this.setPreferredSize(newDim);
                        this.setSize(newDim);
                    }
                } else if (retDimension.getWidth() < 300) {
                    Dimension newDim = new Dimension(800,
                        (int) retDimension.getHeight());
                    tree.setPreferredSize(newDim);
                    tree.setMaximumSize(new Dimension(1024,(int) retDimension.getHeight()));
                    tree.setSize(newDim);
                    this.setPreferredSize(newDim);
                    this.setSize(newDim);
                }

                this.setOpaque(true);
                this.setForeground(nodeViewManager.getForeground());
                this.setBackground(nodeViewManager.getBackground());

//    Dimension dim=this.getPreferredSize();
//    dim.height=JTreeTable.ROW_HEIGHT;
//    Dimension dim=new Dimension(tree.getSize().width,JTreeTable.ROW_HEIGHT);
//    this.setPreferredSize(dim);

                if (nodeViewManager.getIcon() != null) {
                    super.setIcon(nodeViewManager.getIcon());
                }
                if (row % 2 == 1) {
                    this.setBackground(nodeViewManager.getEvenBackground());
                } else {
                	this.setBackground(nodeViewManager.getOddBackground());
                }
                if (selected) {
                    //retComp.setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
                    this.setForeground(nodeViewManager.getSelectForeground());
                    this.setBackground(nodeViewManager.getSelectBackground());
                    //add by Chenxuxi
                }

                this.setFont(nodeViewManager.getTextFont());

            }

        }
        //this.setBackground(java.awt.Color.BLUE);
        //tree.setBackground(java.awt.Color.YELLOW);

        return this;
    }
    
//    public Dimension getPreferredSize() {
//        Dimension retDimension = super.getPreferredSize();
//
//        if (retDimension != null) {
//            retDimension = new Dimension(retDimension.width + 3,
//                                         retDimension.height);
//        }
//        return retDimension;
//    }
}
