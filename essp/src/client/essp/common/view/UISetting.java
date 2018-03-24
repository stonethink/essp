package client.essp.common.view;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import client.framework.view.common.DefaultComp;
import client.framework.view.vwcomp.VWJLabel;
import javax.swing.JLabel;
import client.framework.view.vwcomp.VWJButton;
import client.framework.view.vwcomp.VWJTreeCellRender;
import javax.swing.JComponent;

public class UISetting {

  static ImageIcon theLogo;
  public static String FONT_NAME="宋体";

  public static final Border DEFAULT_BORDER = BorderFactory.createEtchedBorder();

  public static ImageIcon getLogo(){
    if(theLogo == null){
      theLogo = new ImageIcon( UISetting.class.getResource("logo.jpg"));
    }
    return theLogo;
  }

  public static void settingUIManager(){
      Border scrollPaneBorder = null;

      try {
          scrollPaneBorder = BorderFactory.createBevelBorder(
              BevelBorder.LOWERED,
              Color.lightGray,
              Color.lightGray,
              new Color(115, 114, 105),
              new Color(165, 163, 151));
             System.out.println("look and Feel:" + UIManager.getLookAndFeel());
             UIManager.setLookAndFeel(
              "client.essp.common.view.ui.MLWindowsLookAndFeel");
//             UIManager.setLookAndFeel(
//              "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

      }
      catch (Exception ex) {
          ex.printStackTrace();
      }
      UIManager.put("OptionPane.cancelButtonText","Cancel");
      UIManager.put("OptionPane.noButtonText","No");
      UIManager.put("OptionPane.okButtonText","OK");
      UIManager.put("OptionPane.titleText","Select a option");
      UIManager.put("OptionPane.yesButtonText","Yes");
      UIManager.put("OptionPane.messageDialogTitle","Message");

      UIManager.put("OptionPane.questionIcon", new ImageIcon());
      UIManager.put("OptionPane.messageIcon", new ImageIcon());
      UIManager.put("OptionPane.warningIcon", new ImageIcon());
      UIManager.put("OptionPane.informationIcon", new ImageIcon());

//      UIManager.put("Panel.background",
//                    new javax.swing.plaf.ColorUIResource(239 ,235 ,219 ));
      UIManager.put("Panel.background",new Color(255,255,255));

      UIManager.put("OptionPane.background",
                    new javax.swing.plaf.ColorUIResource(255, 255, 255));
      UIManager.put("RadioButton.background",new Color(255, 255, 255));
      UIManager.put("CheckBox.background",
                    new javax.swing.plaf.ColorUIResource(255, 255, 255));
      UIManager.put("ScrollPane.background",
                    new javax.swing.plaf.ColorUIResource(255, 255, 255));
      UIManager.put("SplitPane.background",
                    new javax.swing.plaf.ColorUIResource(255, 255, 255));
//      UIManager.put("SplitPane.border",
//                    null);
      UIManager.put("TabbedPane.foreground",
                    new javax.swing.plaf.ColorUIResource(0, 0, 0));
//      UIManager.put("TabbedPane.background",new javax.swing.plaf.ColorUIResource(0x33,0x66,0x99));
      UIManager.put("TabbedPane.background",new javax.swing.plaf.ColorUIResource(204,204,204));
      UIManager.put("TabbedPane.selectHighlight",
                    new javax.swing.plaf.ColorUIResource(255, 255, 255));
//    UIManager.put("TabbedPane.selected",new javax.swing.plaf.ColorUIResource(0x42,0x61,0x94));
//    UIManager.put("TabbedPane.background",new javax.swing.plaf.ColorUIResource(0x89,0x8e,0xd2));
      UIManager.put("TabbedPane.focus",
                    new javax.swing.plaf.ColorUIResource(0x33, 0x66, 0x99));
      UIManager.put("TabbedPane.shadow",
                    new javax.swing.plaf.ColorUIResource(0x11, 0x33, 0x66));
      UIManager.put("TabbedPane.highlight",
                    new javax.swing.plaf.ColorUIResource(0x33, 0x66, 0x99));
//    UIManager.put("TabbedPane.tabAreaBackground",new javax.swing.plaf.ColorUIResource(0x94,0xaa,0xd6));
      UIManager.put("TabbedPane.selected",
                    new javax.swing.plaf.ColorUIResource(148, 170, 214)); //选中的颜色
      UIManager.put("Viewport.background",
                    new javax.swing.plaf.ColorUIResource(255, 255, 255));
//      UIManager.put("TextField.font",
//                    new javax.swing.plaf.FontUIResource("dialog",0,12));
//      UIManager.put("Table.font",
//                    new javax.swing.plaf.FontUIResource("dialog",0,12));
      //设置字体成宋体字， 这样，简、繁、日文都可以正确显示。
      UIManager.put("TextField.font",
                    new javax.swing.plaf.FontUIResource(FONT_NAME,0,12));
      UIManager.put("TextField.font",
                    new javax.swing.plaf.FontUIResource(FONT_NAME,0,12));
      UIManager.put("Label.font",
                    new javax.swing.plaf.FontUIResource(FONT_NAME,0,12));
      UIManager.put("List.font",
                    new javax.swing.plaf.FontUIResource(FONT_NAME,0,12));
      UIManager.put("TextArea.font",
                    new javax.swing.plaf.FontUIResource(FONT_NAME,0,12));
      UIManager.put("TextField.font",
                    new javax.swing.plaf.FontUIResource(FONT_NAME,0,12));
      UIManager.put("Tree.font",
                    new javax.swing.plaf.FontUIResource(FONT_NAME,0,12));
      UIManager.put("Button.font",
                    new javax.swing.plaf.FontUIResource(FONT_NAME,0,11));
      UIManager.put("CheckBox.font",
                    new javax.swing.plaf.FontUIResource(FONT_NAME,0,12));
      UIManager.put("ComboBox.font",
                    new javax.swing.plaf.FontUIResource(FONT_NAME,0,12));
      UIManager.put("RadioButton.font",
                    new javax.swing.plaf.FontUIResource(FONT_NAME,0,12));
      UIManager.put("TabbedPane.font",
                    new javax.swing.plaf.FontUIResource(FONT_NAME,0,12));

      UIManager.put("Button.background",
                    new javax.swing.plaf.ColorUIResource(0xd6, 0xe7, 0xef));
      UIManager.put("Button.foreground",
                    new javax.swing.plaf.ColorUIResource(0x00, 0x00, 0x66));

      //border
      UIManager.put("ScrollPane.border",scrollPaneBorder );
      UIManager.put("TextArea.border",
                    BorderFactory.createEmptyBorder());
      UIManager.put("TextArea.ScrollPane.border",DEFAULT_BORDER);
      UIManager.put("CheckBox.border",DEFAULT_BORDER);
      UIManager.put("TextField.border",DEFAULT_BORDER);
      UIManager.put("Label.border",BorderFactory.createEmptyBorder());
      UIManager.put("ComboBox.border",DEFAULT_BORDER);
      UIManager.put("ComboBox.background", DefaultComp.BACKGROUND_COLOR_NORMAL);
      UIManager.put("ComboBox.foreground", DefaultComp.FOREGROUND_COLOR_NORMAL);
      UIManager.put("ComboBox.disabledForeground", DefaultComp.FOREGROUND_COLOR_NORMAL);


//    UIManager.put("Viewport.background",new javax.swing.plaf.ColorUIResource(0,102,204));

  }

  public static void main(String[] args) {
    try {
      //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      settingUIManager();
      UIDefaults uiDefs = UIManager.getLookAndFeelDefaults();
      Object[] keys = uiDefs.keySet().toArray();
      System.out.println("begin:");
      for(int i=0; i< keys.length; i++){
        System.out.println("[" + keys[i] + "]=" + uiDefs.get(keys[i]) );
      }
      System.out.println(UIManager.getLookAndFeel().getDefaults().getUI(new JLabel()));
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}
