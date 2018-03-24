package client.framework.view.vwcomp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import client.framework.common.Constant;
import java.awt.Toolkit;
import java.awt.Point;

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
public class VWJPopupEditor extends JDialog {

    private JPanel panel1 = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private VWJButton okBtn = new VWJButton("rsid.common.ok");
    private VWJButton cancelBtn = new VWJButton("rsid.common.cancel");
    private BorderLayout borderLayout1 = new BorderLayout();
    private FlowLayout flowLayout1 = new FlowLayout();
    private Component comp = null;
    private IVWPopupEditorEvent eventAdapter = null;
    private int iRetValue = Constant.CANCEL;

    public VWJPopupEditor(Frame owner, String title, Component comp) {
        super(owner, title, true);
        this.comp = comp;
        if (comp instanceof IVWPopupEditorEvent) {
            eventAdapter = (IVWPopupEditorEvent) comp;
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

    public VWJPopupEditor(Frame owner, String title, Component comp,
                          int OnlyOkBtn) {
        super(owner, title, true);
        this.comp = comp;
        if (comp instanceof IVWPopupEditorEvent) {
            eventAdapter = (IVWPopupEditorEvent) comp;
        }
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbinit(OnlyOkBtn);
            pack();
            setLocation(getDialogLocation(this));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public VWJPopupEditor(Frame owner, String title, Component comp,
                          IVWPopupEditorEvent eventAdapter) {
        super(owner, title, true);
        this.comp = comp;
        this.eventAdapter = eventAdapter;
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            setLocation(getDialogLocation(this));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public VWJPopupEditor() {
        this((Frame)null, "", null);
    }

    private void jbInit() throws Exception {
        panel1.setLayout(borderLayout1);
        buttonPanel.setLayout(flowLayout1);

        if (comp != null) {
            panel1.add(comp, BorderLayout.CENTER);
        }
        okBtn.addActionListener(new OkBtnActionAdapter());
        cancelBtn.addActionListener(new CancelBtnActionAdapter());

        okBtn.setFont(Constant.DEFAULT_BUTTON_FONT);
        Dimension dim = new Dimension(104, 32);
        okBtn.setPreferredSize(dim);
        okBtn.setSize(dim);
        okBtn.setHorizontalAlignment(SwingConstants.CENTER);
        okBtn.setVerticalAlignment(SwingConstants.CENTER);
        cancelBtn.setFont(Constant.DEFAULT_BUTTON_FONT);
        cancelBtn.setPreferredSize(dim);
        cancelBtn.setSize(dim);
        cancelBtn.setHorizontalAlignment(SwingConstants.CENTER);
        cancelBtn.setVerticalAlignment(SwingConstants.CENTER);

        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);
        panel1.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(panel1);
    }

    private void jbinit(int OnlyOkBtn) throws Exception {
        panel1.setLayout(borderLayout1);
        buttonPanel.setLayout(flowLayout1);

        if (comp != null) {
            panel1.add(comp, BorderLayout.CENTER);
        }
        if (OnlyOkBtn > 0) {
            okBtn.addActionListener(new OkBtnActionAdapter());

            okBtn.setFont(Constant.DEFAULT_BUTTON_FONT);
            Dimension dim = new Dimension(104, 32);
            okBtn.setPreferredSize(dim);
            okBtn.setSize(dim);
            buttonPanel.add(okBtn);
            if (OnlyOkBtn != 1) {
                cancelBtn.addActionListener(new CancelBtnActionAdapter());
                cancelBtn.setFont(Constant.DEFAULT_BUTTON_FONT);
                cancelBtn.setPreferredSize(dim);
                cancelBtn.setSize(dim);
                buttonPanel.add(cancelBtn);
            }
            panel1.add(buttonPanel, BorderLayout.SOUTH);
        }
        getContentPane().add(panel1);
    }

    protected Point getDialogLocation(JDialog dialog) {
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

    public int showConfirm() {
        this.iRetValue = Constant.CANCEL;
        super.show();
        return this.iRetValue;
    }

    public static void main(String[] args) {
        JPanel testPanel = new JPanel();
        testPanel.setBackground(Color.blue);
        testPanel.setSize(800, 600);
        testPanel.setPreferredSize(new Dimension(800, 600));
        VWJPopupEditor dialog = new VWJPopupEditor((Frame)null, "Testing",
            testPanel);
        dialog.setVisible(true);
        //dialog.show();
    }

    public void okBtnActionPerformed(ActionEvent e) {
        if (eventAdapter != null) {
            if (eventAdapter.onClickOK(e)) {
                this.iRetValue = Constant.OK;
                this.dispose();
            }
        } else {
            this.iRetValue = Constant.OK;
            this.dispose();
        }
    }

    public void cancelBtnActionPerformed(ActionEvent e) {
        if (eventAdapter != null) {
            if (eventAdapter.onClickCancel(e)) {
                this.dispose();

            }
        } else {
            this.dispose();

        }
    }

    public JPanel getButtonPanel() {
        return this.buttonPanel;
    }

    public Component getComp(){
        return this.comp;
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

    class OkBtnActionAdapter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            okBtnActionPerformed(e);
        }
    }


    class CancelBtnActionAdapter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            cancelBtnActionPerformed(e);
        }
    }
}
