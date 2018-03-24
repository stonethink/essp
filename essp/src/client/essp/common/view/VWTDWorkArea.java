package client.essp.common.view;

import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import org.apache.log4j.Category;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class VWTDWorkArea extends VWWorkArea {
    protected static Category log = Category.getInstance("client");
    private JSplitPane jSplitPane1;
    private VWWorkArea topArea;
    private VWWorkArea downArea;

    /**
     * @param splitHieght int  上半部分的高度
     */
    public VWTDWorkArea(int topHieght) {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSplitHeight(topHieght);
    }

    private void jbInit() throws Exception {
        this.setLayout(new BorderLayout());

        topArea = new VWWorkArea();
        downArea = new VWWorkArea();

        jSplitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        this.add(jSplitPane1, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(downArea, BorderLayout.CENTER);
        jSplitPane1.setBottomComponent(bottomPanel);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(topArea, BorderLayout.CENTER);
        jSplitPane1.setTopComponent(topPanel);

        this.setBorder( BorderFactory.createEmptyBorder() );
        jSplitPane1.setBorder( BorderFactory.createEmptyBorder() );
    }

    public void setVerticalSplit() {
        jSplitPane1.setOrientation( JSplitPane.VERTICAL_SPLIT );
    }

    public void setHorizontalSplit(){
        jSplitPane1.setOrientation( JSplitPane.HORIZONTAL_SPLIT );
    }

    public void setSplitHeight(int headAreaHeight) {
        jSplitPane1.setDividerLocation(headAreaHeight);
    }

    public JSplitPane getSplitPane(){
        return this.jSplitPane1;
    }

    public void setTopArea(VWWorkArea p) {
        if( p!= null ){
            ((JPanel) jSplitPane1.getTopComponent()).remove(topArea);
            ((JPanel) jSplitPane1.getTopComponent()).add(p, BorderLayout.CENTER);
            topArea = p;

            this.updateUI();
        }
    }

    public VWWorkArea getTopArea() {
        return topArea;
    }

    public void setDownArea(VWWorkArea p) {
        if(p!=null) {
            //p.setBorder( BorderFactory.createEmptyBorder() );
            ((JPanel)jSplitPane1.getBottomComponent()).remove(downArea);
            ((JPanel)jSplitPane1.getBottomComponent()).add(p, BorderLayout.CENTER);
            downArea = p;

            this.updateUI();
        }
    }

    public VWWorkArea getDownArea() {
        return downArea;
    }

    //设置可让分隔区域单击收缩功能,并设置分隔条的宽度为8
    //modified by:Robin 2006.9.7
    public void setOneTouchExpandable(){
        jSplitPane1.setOneTouchExpandable(true);
        jSplitPane1.setDividerSize(8);
    }
}
