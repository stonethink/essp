package client.essp.common.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * <p>Title: </p>
 * <p>Description: 创建一个VWJPanel对象时，创建它里面的控件。下面可以：
 *   1： 创建时准备数据， 并给控件设值；
 *    或
 *   2： 创建对象时不准备数据，待使用对象时再准备数据。
 *       initialize()方法用来在使用时为对象准备数据，并给控件设值
 *   当一个VWJPanel对象要放在卡片中时，而且数据可以自己搞定，建议用 2 方法。
 * </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */

public class VWJPanel extends JPanel {
    VWButtonPanel btnPanel = new VWButtonPanel();
//    String title = "";

    public static final int NO_CARD = 0;
    public static final int CARD = 1;

    public VWJPanel() {
        this.setLayout(new BorderLayout());
    }

    //准备数据，并初始化界面
    public void initialize() {

    }

    public VWButtonPanel getButtonPanel() {
        return btnPanel;
    }

    public void addPanel( VWJPanel panel ){
        this.addPanel( panel, false );
    }

    /**
     *
     * @param panel VWJPanel
     * @param bScroll boolean  当panel显示不下时，是否出现滚动条
     */
    public void addPanel( VWJPanel panel , boolean bScroll ){
        if( bScroll == true ){
            this.add(VWScrollPanel.addScroll(panel), BorderLayout.CENTER );
        }else{
            this.add( panel, BorderLayout.CENTER );
        }
    }

//
//    public String getTitle(){
//        return this.title;
//    }
//
//    public void setTitle( String title ){
//        this.title = title;
//    }

}
