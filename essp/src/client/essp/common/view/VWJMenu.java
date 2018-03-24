package client.essp.common.view;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;

public class VWJMenu
    extends JPanel {
  Menu menu = null;
  JScrollPane scrollPane = new JScrollPane();
  JTree menuTree = new JTree();
  BorderLayout borderLayout1 = new BorderLayout();
  DefaultTreeModel model = null;
  Cursor oldCursor=null;
  Cursor currCursor=new Cursor(Cursor.HAND_CURSOR);
  public VWJMenu() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    menuTree.setRootVisible(false);
    menuTree.setShowsRootHandles(true);
    menuTree.addMouseMotionListener(new VWJMouseMotionAdapter(this));
    menuTree.addMouseListener(new VWJMouseAdapter(this));
    menuTree.setLargeModel(false);
    menuTree.putClientProperty("JTree.lineStyle", "Angled");

    DefaultTreeCellRenderer renderer =
        new DefaultTreeCellRenderer();
    renderer.setLeafIcon(null);
    renderer.setClosedIcon(null);
    renderer.setOpenIcon(null);
    menuTree.setBackground(new Color(235, 235, 220));
    menuTree.setCellRenderer(renderer);
    menuTree.getSelectionModel().setSelectionMode(
        TreeSelectionModel.SINGLE_TREE_SELECTION);

    scrollPane.getViewport().add(menuTree);
    this.add(scrollPane, BorderLayout.CENTER);
  }

  public void setMenu(Menu menu) {
    this.menu = menu;
    if (this.menu != null) {
      DefaultMutableTreeNode menuRootNode = new DefaultMutableTreeNode(new
          MenuNode(menu));
      createTree(menuRootNode, menu);
      model = new DefaultTreeModel(menuRootNode);
      menuTree.setModel(model);
      menuTree.updateUI();
    }
  }

  private void createTree(DefaultMutableTreeNode node, MenuItem menu) {
    if (menu instanceof Menu) {
      for (int i = 0; i < ( (Menu) menu).getItemCount(); i++) {
        MenuItem submenu = ( (Menu) menu).getItem(i);
        DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(new
            MenuNode(submenu));
        node.add(subNode);
        createTree(subNode, submenu);
      }
    }
  }

  public Menu getMenu() {
    return this.menu;
  }

  class MenuNode {
    MenuItem menuitem = null;
    public MenuNode(MenuItem menuitem) {
      this.menuitem = menuitem;
    }

    public String toString() {
      return menuitem.getLabel();
    }

    public void execute() {
      if (! (menuitem instanceof Menu)) {
        ActionListener[] actionListeners = menuitem.getActionListeners();
        System.out.println(menuitem.getLabel() + " invoked! ActionCommand=" +
                           menuitem.getActionCommand());

        if (actionListeners != null) {
          for (int i = 0; i < actionListeners.length; i++) {
            actionListeners[i].actionPerformed(new ActionEvent(menuitem, 0, ""));
          }
        }
      }
    }
  }

  void onMousePressed(MouseEvent e) {
    int selRow = menuTree.getRowForLocation(e.getX(), e.getY());
    TreePath selPath = menuTree.getPathForLocation(e.getX(), e.getY());
    if (selRow != -1) {
      if (e.getClickCount() == 1) {
        DefaultMutableTreeNode itemNode = (DefaultMutableTreeNode) selPath.
            getLastPathComponent();
        Object userObject = itemNode.getUserObject();
        if (userObject instanceof MenuNode) {
          ( (MenuNode) userObject).execute();
        }
      }
    }

  }

  void onMouseEntered(MouseEvent e) {
    oldCursor=this.getCursor();
  }

  void onMouseExited(MouseEvent e) {
    this.setCursor(oldCursor);
  }

  void onMouseMoved(MouseEvent e) {
    int selRow = menuTree.getRowForLocation(e.getX(), e.getY());
    TreePath selPath = menuTree.getPathForLocation(e.getX(), e.getY());
    if (selRow != -1) {
      this.setCursor(currCursor);
    } else {
      this.setCursor(oldCursor);
    }
  }

  public static void main(String[] args) {
      try {
          UISetting.settingUIManager();
      } catch (Exception e) {
          e.printStackTrace();
      }
///-------menu
      Menu menu0 = new Menu("root");
      Menu menu1 = new Menu("Administrator");
    MenuItem menu11 = new MenuItem("Leave");
    menu11.setActionCommand("Leave");
    MenuItem menu12 = new MenuItem("Overtime");
    menu12.setActionCommand("Overtime");
    MenuItem menu13 = new MenuItem("Outgoing");
    menu13.setActionCommand("Outgoing");
    MenuItem menu14 = new MenuItem("Purchase");
    menu14.setActionCommand("Purchase");
    menu1.add(menu11);
    menu1.add(menu12);
    menu1.add(menu13);
    menu1.add(menu14);
    Menu menu2 = new Menu("Report");
    MenuItem menu21 = new MenuItem("Attendance");
    menu21.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.out.println("test");
        }
    });
    menu2.add(menu21);
      menu0.add(menu1);
    menu0.add(menu2);

      JFrame frame = new JFrame();
      frame.setTitle("VWJPage");
      VWJMenu page = new VWJMenu();
      page.setMenu(menu0);
      page.setSize(new Dimension(600,400));
      page.setPreferredSize(new Dimension(600,400));
      page.setMaximumSize(new Dimension(600,400));
      page.setMinimumSize(new Dimension(600,400));

//    page.setMenu(menu);
      frame.setContentPane(page);

      frame.pack();
      frame.show();

  }

}

class VWJMouseAdapter
    extends java.awt.event.MouseAdapter {
  VWJMenu adaptee;

  VWJMouseAdapter(VWJMenu adaptee) {
    this.adaptee = adaptee;
  }

  public void mousePressed(MouseEvent e) {
    adaptee.onMousePressed(e);
  }
  public void mouseEntered(MouseEvent e) {
    adaptee.onMouseEntered(e);
  }
  public void mouseExited(MouseEvent e) {
    adaptee.onMouseExited(e);
  }
}

class VWJMouseMotionAdapter extends java.awt.event.MouseMotionAdapter {
  VWJMenu adaptee;

  VWJMouseMotionAdapter(VWJMenu adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseMoved(MouseEvent e) {
    adaptee.onMouseMoved(e);
  }
}
