package client.framework.view.vwcomp;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Component;
import client.framework.common.Constant;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.wits.util.Parameter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VWJWizardEditor extends JDialog {

    private JPanel datePanel=new JPanel();
    private JPanel buttonPanel=new JPanel();
    private VWJButton backBtn=new VWJButton("  Back  ");
    private VWJButton nextBtn=new VWJButton("  Next  ");
    private VWJButton finishBtn=new VWJButton(" Finish ");
    private VWJButton cancelBtn=new VWJButton(" Cancel ");
    private BorderLayout borderLayout1=new BorderLayout();
    private FlowLayout flowLayout1=new FlowLayout();
    private String titleHead;
    private String titleTail;//title的尾部，形如"- Step 1 of 3"
//    private Component preComp=null;
    private Component curComp=null;//当前的卡片
//    private Component nextComp=null;
    private IVWWizardEditorEvent curEventAdapter=null;//当前卡片的事件适配器
//    private int iRetValue = Constant.CANCEL;
//    private Parameter para=new Parameter();//各个卡片之间数据的共享区
    private int compNum=-1;//卡片的个数
    private int curCompIndex=-1;//当前是第几个卡片，取值从0~compNum-1之间
    private Object[][] comps;//每一行存放的都是形如{Component，IVWWizardEditorEvent}的数据
    /**
     * eventKind有四种取值：
     * 1 表示back事件
     * 2 表示next事件
     * 3 表示finish事件
     * 4 表示cancel事件
     */
    private int eventKind=-1;


    public VWJWizardEditor(Frame owner,String title,Object[][] comps ){
        super(owner,title+" - Step 1 of "+comps.length,true);
        this.titleHead=title;
        this.comps=comps;
        if(comps.length>0) {
            this.curComp =(Component)comps[0][0];
            if(comps[0].length>1)
                if(comps[0][1] instanceof IVWWizardEditorEvent)
                 this.curEventAdapter=(IVWWizardEditorEvent)comps[0][1];
        }

        this.compNum=comps.length;
        this.curCompIndex=1;

        try{
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            setLocation(getDialogLocation(this));
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
    public VWJWizardEditor(Frame owner, String title, Component comp) {
        super(owner, title, true);
        this.curComp=comp;
        this.compNum=1;
        this.curCompIndex=1;
        if(comp instanceof IVWWizardEditorEvent){
            curEventAdapter=(IVWWizardEditorEvent)comp;
        }
        try {
           setDefaultCloseOperation(DISPOSE_ON_CLOSE);
           jbInit();
           pack();
           setLocation(getDialogLocation(this));
       } catch (Exception exception) {
           exception.printStackTrace();
       }

    }
    public VWJWizardEditor(Frame owner,String title,Component comp,IVWWizardEditorEvent eventAdapter){
        super(owner,title,true);
        this.curComp=comp;
        this.curEventAdapter=eventAdapter;
        this.compNum=1;
        this.curCompIndex=1;
        try {
          setDefaultCloseOperation(DISPOSE_ON_CLOSE);
          jbInit();
          pack();
          setLocation(getDialogLocation(this));
      } catch (Exception exception) {
          exception.printStackTrace();
      }

    }
    public VWJWizardEditor(){
        this((Frame)null,"",(Component)null);
    }
    public void jbInit(){
        datePanel.setLayout(borderLayout1);
        buttonPanel.setLayout(flowLayout1);

        if(curComp!=null){
           datePanel.add(curComp,BorderLayout.CENTER);
        }

        setButtonEnable();
        backBtn.addActionListener(new BackBtnActionAdapter());
        backBtn.setFont(Constant.DEFAULT_BUTTON_FONT);
        Dimension dim = new Dimension(80, 25);
        backBtn.setPreferredSize(dim);
        backBtn.setSize(dim);
        buttonPanel.add(backBtn);

        nextBtn.addActionListener(new NextBtnActionAdapter());
        nextBtn.setFont(Constant.DEFAULT_BUTTON_FONT);
        nextBtn.setSize(dim);
        nextBtn.setPreferredSize(dim);
        buttonPanel.add(nextBtn);

        finishBtn.addActionListener(new FinishBtnActionAdapter());
        finishBtn.setFont(Constant.DEFAULT_BUTTON_FONT);
        finishBtn.setSize(dim);
        finishBtn.setPreferredSize(dim);
        buttonPanel.add(finishBtn);

        cancelBtn.addActionListener(new CancelBtnActionAdapter());
        cancelBtn.setFont(Constant.DEFAULT_BUTTON_FONT);
        cancelBtn.setSize(dim);
        cancelBtn.setPreferredSize(dim);
        buttonPanel.add(cancelBtn);

        datePanel.add(buttonPanel,BorderLayout.SOUTH);
//        getContentPane().add(datePanel);
        this.setContentPane(datePanel);
        this.pack();
    }
    public Point getDialogLocation(JDialog dialog){
            //Center the window
           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
           Dimension frameSize = dialog.getSize();
           if (frameSize.height > screenSize.height) {
               frameSize.height = screenSize.height;
           }
           if (frameSize.width > screenSize.width) {
               frameSize.width = screenSize.width;
           }
           int x = (screenSize.width - frameSize.width) / 2;
           int y = (screenSize.height - frameSize.height) / 2;
           y = y - y / 4; //提高一点

           return new Point(x, y);
    }
    class BackBtnActionAdapter implements ActionListener{
        public void actionPerformed(ActionEvent e){
            backBtnActionPerformed(e);
        }
    }
    class NextBtnActionAdapter implements ActionListener{
        public void actionPerformed(ActionEvent e){
           nextBtnActionPerformed(e);
       }
    }
   class FinishBtnActionAdapter implements ActionListener{
        public void actionPerformed(ActionEvent e){
            finishBtnActionPerformed(e);
        }
    }
   class CancelBtnActionAdapter implements ActionListener{
        public void actionPerformed(ActionEvent e){
            cancelBtnActionPerformed(e);
            }
        }

    public void backBtnActionPerformed(ActionEvent e){
        if(curEventAdapter!=null){
            if(curEventAdapter.onClickBack(e)){
              eventKind=1;
              handerBtnEvent();
//              pack();
            }
        }else{
            eventKind=1;
            handerBtnEvent();
//            pack();
        }
    }

    public void nextBtnActionPerformed(ActionEvent e){
        if(curEventAdapter!=null){
            if(curEventAdapter.onClickNext(e)){
              eventKind=2;
              handerBtnEvent();
            }
        }else{
              eventKind=2;
              handerBtnEvent();
//              pack();

        }
    }

    public void finishBtnActionPerformed(ActionEvent e){
        if(curEventAdapter!=null){
            if(curEventAdapter.onClickFinish(e)){
              eventKind=3;
//              handerBtnEvent();
              this.dispose();

            }
        }else{
            this.dispose();

        }
    }

    public void cancelBtnActionPerformed(ActionEvent e){
        if(curEventAdapter!=null){
            if(curEventAdapter.onClickCancel(e)){
              eventKind=4;
//              handerBtnEvent();
              this.dispose();

            }
        }else{
            this.dispose();
        }
    }

    public void handerBtnEvent(){
        int next=getNextCompIndex();
        if(next!=-1){
            datePanel.removeAll();
            buttonPanel.removeAll();
            backBtn=null;
            nextBtn=null;
            finishBtn=null;
            cancelBtn=null;
            backBtn=new VWJButton("  Back  ");
            nextBtn=new VWJButton("  Next  ");
            finishBtn=new VWJButton(" Finish ");
            cancelBtn=new VWJButton(" Cancel ");

            titleTail=" - Step "+curCompIndex+" of "+compNum;
            curComp=null;
            curEventAdapter=null;
            curComp=(Component)comps[next-1][0];
            curEventAdapter=(IVWWizardEditorEvent)comps[next-1][1];
            curEventAdapter.refresh();
            jbInit();
            this.setTitle(titleHead+titleTail);
        }

    }
    public int getNextCompIndex(){
        if(eventKind==1){
            curCompIndex=curCompIndex-1;
            if(curCompIndex<1) curCompIndex=1;
            return curCompIndex;
        }else if(eventKind==2){
            curCompIndex=curCompIndex+1;
            if(curCompIndex>compNum)curCompIndex=compNum;
            return curCompIndex;
        }else if(eventKind==3){
            return -1;
        }else if(eventKind==4){
            return -1;
        }
        return -1;
    }
    public void setButtonEnable(){
        if(curCompIndex==1){
            backBtn.setEnabled(false);
            nextBtn.setEnabled(true);
        }else if(curCompIndex==compNum){
            backBtn.setEnabled(true);
            nextBtn.setEnabled(false);
        }else{
           backBtn.setEnabled(true);
           nextBtn.setEnabled(true);
        }
    }
    protected java.awt.Frame getParentWindow() {
        java.awt.Container c = this.getParent();

        while (c != null) {
            if (c instanceof java.awt.Frame) {
                return (java.awt.Frame) c;
            }

            c = c.getParent();
        }

        return null;
    }
//    public void setParameter(Parameter para){
//        this.para=para;
//    }
//    public Parameter getParameter(){
//        return this.para;
//    }
    public static void main(String[] args){
        BorderLayout borderLayout=new BorderLayout();
        VWJLabel label1=new VWJLabel();
        label1.setText("First Panel");
        label1.setSize(new Dimension(104,32));

        VWJLabel label2=new VWJLabel();
        label2.setText("Second Panel");
        label2.setSize(new Dimension(104,32));

        VWJLabel label3=new VWJLabel();
        label3.setText("Third Panel");
        label3.setSize(new Dimension(104,32));

        JPanel panel1=new JPanel();
        panel1.setSize(new Dimension(600,400));
        panel1.setPreferredSize(new Dimension(600,400));
        panel1.setLayout(borderLayout);
        panel1.add(label1,BorderLayout.CENTER);

        JPanel panel2=new JPanel();
        panel2.setSize(new Dimension(600,400));
        panel2.setPreferredSize(new Dimension(600,400));
        panel2.setLayout(borderLayout);
        panel2.add(label2,BorderLayout.CENTER);

        JPanel panel3=new JPanel();
        panel3.setSize(new Dimension(600,400));
        panel3.setPreferredSize(new Dimension(600,400));
        panel3.setLayout(borderLayout);
        panel3.add(label3,BorderLayout.CENTER);

        Object[][] obj=new Object[][]{
                                     {panel1,null},
                                     {panel2,null},
                                     {panel3,null}
                                       };

        VWJWizardEditor wizard=new VWJWizardEditor((Frame)null,"test Wizard",obj);
        wizard.show();

    }
}
