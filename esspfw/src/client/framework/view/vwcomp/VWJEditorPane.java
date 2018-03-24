package client.framework.view.vwcomp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.*;

import com.wits.util.TestPanel;
import validator.Validator;
import validator.ValidatorResult;
import client.framework.view.common.DefaultComp;
import c2s.dto.DtoUtil;

public class VWJEditorPane extends JEditorPane implements IVWComponent {
    private JScrollPane editorScrollPane;
    private Validator validator;
    private ValidatorResult validatorResult;
    private boolean bReshap = false;
    private int offset = 0;
    private Object dtoBean;

    public VWJEditorPane() {
        this.setEditable(false);
        this.setContentType("text/html");
    }

    public JScrollPane getShower() {
        editorScrollPane = new JScrollPane(this);
        editorScrollPane.setBounds(this.getBounds());
        editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        editorScrollPane.setPreferredSize(this.getPreferredSize());
        editorScrollPane.setMinimumSize(this.getMinimumSize());
        return editorScrollPane;
    }

    public void setUICValue(Object value) {
        if (value != null) {
            setText(value.toString());
        } else {
            setText("");
        }
    }

    public void setVerticalScrollBarPolicy(int policy) {
        editorScrollPane.setVerticalScrollBarPolicy(policy);
    }

    public Object getUICValue() {
        return getText();
    }

    public void setDtoClass(Class dtoClass) {
        try {
            this.dtoBean = dtoClass.newInstance();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        }
    }

    public Class getDtoClass() {
        if (dtoBean == null) {
            return null;
        }
        return dtoBean.getClass();
    }


    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public boolean validateValue() {
        boolean bRtn = true;
        if (dtoBean == null || validator == null) {
            return true;
        }
        String sValue = this.getText();
        setValueToDto(sValue);
        validatorResult = validator.validate(dtoBean, this.getName());
        bRtn = validatorResult.isValid();
        return bRtn;

    }

    public void setValueToDto(Object value) {
        if (dtoBean != null) {
            try {
                DtoUtil.setProperty(dtoBean, getName(), value);
            } catch (Exception e) {
                //
            }
        }
    }


    public ValidatorResult getValidatorResult() {
        return validatorResult;
    }

    public void setErrorField(boolean flag) {
        if (flag) {
            this.setForeground(DefaultComp.FOREGROUND_COLOR_ERROR);
        } else {
            this.setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
        }

        if (this.isEnabled() == true) {
            if (flag) {
                this.setBackground(DefaultComp.BACKGROUND_COLOR_ERROR);
            } else {
                this.setBackground(DefaultComp.BACKGROUND_COLOR_NORMAL);
            }
        } else {
            this.setBackground(DefaultComp.BACKGROUND_COLOR_DISABLED);
        }
    }

    public IVWComponent duplicate() {
        VWJEditorPane comp = new VWJEditorPane();
        comp.setName(this.getName());
        comp.setDtoClass(this.getDtoClass());
        comp.setValidator(this.validator);
        comp.setFont(this.getFont());
        comp.setText(this.getText());
        return comp;
    }

    public boolean isBReshap() {
        return bReshap;
    }

    public void setBReshap(boolean bReshap) {
        this.bReshap = bReshap;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getHorizontalAlignment() {
        return SwingConstants.LEFT;
    }

    public static void main(String[] args) {
        JPanel testShow = new JPanel();
        JLabel jLabel1 = new JLabel();
        VWJEditorPane vwjeditorpane = new VWJEditorPane();
        testShow.setLayout(new BorderLayout());
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel1.setText("Test HTML Viewer");
        jLabel1.setBounds(new Rectangle(134, 21, 128, 129));
        vwjeditorpane.setPreferredSize(new Dimension(10, 10));
        vwjeditorpane.setUICValue("<html><body bgcolor='ffff00'>"
                                  + "<SELECT name='type'><OPTION value='朋友' "
                                  + "selected='selected'>-朋友-</OPTION>"
                                  + "<OPTION value='同事'>-同事-</OPTION>"
                                  + "</SELECT>Participate in developing and "
                                  + "recordingtest cases (in terms of inputs, "
                                  +
                                  "expected results, and evaluation criteria),"
                                  +
                " test procedures, and test data for conducting"
                                  +
                                  " CSCI/HWCI integration and testing. Record "
                                  +
                "software-related information in appropriate "
                                  +
                "software development files (SDFs). ..</body>"
                                  + "</html>");
//        testShow.add(jLabel1);
        testShow.add(vwjeditorpane, BorderLayout.CENTER);
        testShow.add(vwjeditorpane.getShower());
        TestPanel.show(testShow);

    }

}
