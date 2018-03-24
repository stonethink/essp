package client.essp.common.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.JToolBar;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicToolBarUI;


/**
 *
 * <p>Title: 浮动窗口组件</p>
 * <p>Description: 失去焦点后停留在最上方，可用于拖放弹出窗口 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class VWPopToolBar extends VWWorkArea {
    //
    protected JToolBar toolbar;
    //默认宽度，高度
    public static final int DEFAULT_TB_WITH = 200;
    public static final int DEFAULT_TB_HIGHT = 380;
    //所悬浮父窗体
    private Container parentFrame;
    //默认弹出位置
    private Point popLocation = new Point(400, 300);
    public VWPopToolBar(Point p, Container parent) {
        this(p, parent, "");

    }

    public VWPopToolBar(Point p, Container parent, String title) {
        this(p, parent, title,DEFAULT_TB_WITH,DEFAULT_TB_HIGHT);
    }

    public VWPopToolBar( Container parent, String title) {
        this(null, parent, title,DEFAULT_TB_WITH,DEFAULT_TB_HIGHT);
    }
    public VWPopToolBar( Container parent, String title,int width,int height) {
        this(null, parent, title,width,height);
    }
    public VWPopToolBar(Point p, Container parent, String title,int width,int height) {
        parentFrame = parent;
        try {
            toolbar = new JToolBar(title);
            toolbar.setPreferredSize(new Dimension(width, height));
            if(p != null){
              popLocation = p;
            }
            jbInit();
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    /**
     * 显示窗口
     */
    public void showPopSelect() {
        this.getToolBar().setVisible(true);
        ((NonDockingToolBarUI)this.getToolBar().
         getUI()).
            setFloating(true, null);

    }

    private void jbInit() throws Exception {
        toolbar.setOrientation(JToolBar.VERTICAL);
        toolbar.setLayout(new BorderLayout());
        this.add(toolbar);
        toolbar.setUI(new NonDockingToolBarUI(parentFrame));
       
        NonDockingToolBarUI ui = (NonDockingToolBarUI) toolbar.getUI();
        toolbar.setFloatable(true);
        ui.setFloatingLocation(popLocation.x, popLocation.y);
//        ui.setFloating(true, null);

        toolbar.firePropertyChange("resizable", Boolean.FALSE, Boolean.TRUE);
    }

    public JToolBar getToolBar() {
        return this.toolbar;
    }

    class NonDockingToolBarUI extends BasicToolBarUI {
        private Container parentFrame;
        private Window floatingToolBar;

        /**
         * 关闭时不停靠父窗体
         * @param c Component
         * @param p Point
         * @return boolean
         */
        public boolean canDock(Component c, Point p) {
            return false;
        }

        public NonDockingToolBarUI(Container f) {
            super();
            this.parentFrame = f;
        }

        protected RootPaneContainer createFloatingWindow(JToolBar toolbar) {
            class ToolBarDialog extends JDialog {
                public ToolBarDialog(Frame owner, String title, boolean modal) {
                    super(owner, title, modal);
                }

                public ToolBarDialog(Dialog owner, String title, boolean modal) {
                    super(owner, title, modal);
                }

                // Override createRootPane() to automatically resize
                // the frame when contents change
                protected JRootPane createRootPane() {
                    JRootPane rootPane = new JRootPane() {
                        private boolean packing = false;

                        public void validate() {
                            super.validate();
                            if (!packing) {
                                packing = true;
                                pack();
                                packing = false;
                            }
                        }
                    };
                    rootPane.setOpaque(true);
                    return rootPane;
                }
            }


            JDialog dialog;
            Window window = SwingUtilities.getWindowAncestor(toolbar);

            if (parentFrame instanceof Frame) {
                dialog = new ToolBarDialog((Frame) parentFrame, toolbar.getName(), false);
            } else if (parentFrame instanceof Dialog) {
                dialog = new ToolBarDialog((Dialog) parentFrame,
                                           toolbar.getName(), false);
            } else if (window instanceof Frame) {
                dialog = new ToolBarDialog((Frame) window, toolbar.getName(), false);
            } else if (window instanceof Dialog) {
                dialog = new ToolBarDialog((Dialog) window, toolbar.getName(), false);
            } else {
                dialog = new ToolBarDialog((Frame)null, toolbar.getName(), false);
            }

            dialog.setTitle(toolbar.getName());
            dialog.setResizable(true);
            floatingToolBar = dialog;
            return dialog;
        }
        
        /**
         * adapt jre 1.6
         */
        public void setFloating(boolean b, Point p) {
        	try {
        		super.setFloating(b, p);
        	} catch (Exception e) {
        		
        	}
        	if(floatingToolBar != null && !floatingToolBar.isVisible()) {
        		floatingToolBar.show();
        	}
        }
    }

}

