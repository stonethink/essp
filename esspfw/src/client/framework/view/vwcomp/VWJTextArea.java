package client.framework.view.vwcomp;

import javax.swing.*;
import validator.Validator;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.UndoableEditImpListener;
import client.framework.view.common.comFORM;
import client.framework.view.common.DefaultComp;
import c2s.dto.DtoUtil;
import validator.ValidatorResult;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import javax.swing.text.Document;
import client.framework.view.jmscomp.InputDocument;

public class VWJTextArea extends JScrollPane implements IVWComponent {
    private JTextArea wrappedComp = null;

    Object dtoBean;
    Validator validator;
    ValidatorResult validatorResult = null;

    DataChangedListener dataChangedListeners;


    private boolean bReshap = false;
    private int offset = 0;

    public VWJTextArea() {
        this(null, null, 0, 0);
    }

    public VWJTextArea(String text) {
        this(null, text, 0, 0);
    }

    public VWJTextArea(Document doc) {
        this(doc, null, 0, 0);
    }

    public VWJTextArea(int rows, int columns) {
        this(null, null, rows, columns);
    }

    public VWJTextArea(String text, int rows, int columns) {
        this(null, text, rows, columns);
    }

    public VWJTextArea(Document doc, String text, int rows, int columns) {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void setBounds(int x, int y, int w, int h) {
//        if (isBReshap() == true) {
//            int newX = Math.max(x, getOffset());
//            super.setBounds(newX, y, w - (newX - x), h);
//            wrappedComp.setBounds(0, 0, w - (newX - x), h);
//        } else {
//            super.setBounds(x, y, w, h);
//            wrappedComp.setBounds(0, 0, w, h);
//        }
//    }

    private void createTextArea() {
        if (wrappedComp == null) {
            wrappedComp = new JTextArea();
            Document doc = wrappedComp.getDocument();
            if(doc != null) {
            	doc.addUndoableEditListener(new UndoableEditImpListener(wrappedComp));
            }
            
            wrappedComp.setLineWrap(true);
            wrappedComp.setBorder(UIManager.getBorder("TextArea.border"));
            wrappedComp.setBounds(0, 0, this.getWidth(), this.getHeight());
            wrappedComp.setDisabledTextColor( DefaultComp.DISABLED_FONT_COLOR );

            this.setBorder( UIManager.getBorder("TextArea.ScrollPane.border") );

            wrappedComp.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    this_focusGained(e);
                }

                public void focusLost(FocusEvent e) {
                    this_focusLost(e);
                }
            });

            wrappedComp.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    this_keyPressed(e);
                }
            });
            _setBackgroundColor();
        }
    }

    private void jbInit() throws Exception {
        createTextArea();
        this.getViewport().setView(wrappedComp);
    }

    public void setLineWrap(boolean wrap) {
        wrappedComp.setLineWrap(wrap);
    }

    public boolean getLineWrap() {
        return wrappedComp.getLineWrap();
    }

    public synchronized void addFocusListener(FocusListener l) {
        createTextArea();
        wrappedComp.addFocusListener(l);
    }

    public synchronized void addKeyListener(KeyListener l) {
        createTextArea();
        wrappedComp.addKeyListener(l);
    }

    public void setBackground(Color bg) {
        super.setBackground(bg);
        createTextArea();
        wrappedComp.setBackground(bg);
    }

    public void setForeground(Color fg) {
        super.setForeground(fg);
        createTextArea();
        wrappedComp.setForeground(fg);
    }

    public void _setBackgroundColor(
            ) {
        if (this.isEnabled() == true) {
            this.setBackground(DefaultComp.BACKGROUND_COLOR_ENABLED);
            this.wrappedComp.setBackground(DefaultComp.BACKGROUND_COLOR_ENABLED);
        } else {
            this.setBackground(DefaultComp.BACKGROUND_COLOR_DISABLED);
            this.setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
            this.wrappedComp.setBackground(DefaultComp.BACKGROUND_COLOR_DISABLED);
            this.wrappedComp.setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
        }
    }

    public void setEnabled(boolean prm_bValue) {
        super.setEnabled(prm_bValue);
        createTextArea();
        wrappedComp.setEnabled(prm_bValue);
        _setBackgroundColor();
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

    public void setBReshap(boolean bReshap) {
        this.bReshap = bReshap;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setUICValue(Object value) {
        createTextArea();
        if (value != null) {
            wrappedComp.setText(value.toString());
        } else {
            wrappedComp.setText("");
        }
    }

    public boolean validateValue() {
        createTextArea();
        boolean bRtn = true;
        if (dtoBean == null || validator == null) {
            return true;
        }
        String sValue = wrappedComp.getText();
        setValueToDto(sValue);
        validatorResult = validator.validate(dtoBean, this.getName());
        bRtn = validatorResult.isValid();

        return bRtn;
    }

    public ValidatorResult getValidatorResult() {
        return validatorResult;
    }

    public boolean isBReshap() {
        return bReshap;
    }

    public int getOffset() {
        return offset;
    }

    public Object getUICValue() {
        createTextArea();
//        boolean bRtn = true;
//        ValidatorResult result = null;
        String sValue = wrappedComp.getText().trim();
//        if (this.checkModified() == true) {
//            if (validator != null && this.getName()!=null && dtoBean!=null) {
//                result = validator.validate(dtoBean, this.getName());
//                bRtn = result.isValid();
//            }
//
//            if (bRtn) {
//                setValueToDto(sValue);
//                _setBackgroundColor();
//                fireDataChanged();
//                this.clearModified();
//            } else {
//                setBackground(DefaultComp.BACKGROUND_COLOR_ERROR);
//                if (result != null && result.getMsg() != null) {
//                    this.setToolTipText(result.getMsg(this.getName())[0]);
//                }
//                this.requestFocus();
//            }
//        }
        return sValue;
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

//  public Object dataFormat(Object value, String dataFormat) {
//    Object obj = null;
//    obj = value;
//    return obj;
//  }

    public void this_focusGained(FocusEvent e) {
        setBackground( DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE );
    }

    public void this_focusLost(FocusEvent e) {
        //super.this_focusLost(e);
        //getUICValue();
        _setBackgroundColor();
    }

    public void addDataChangedListener(DataChangedListener listener) {
        this.dataChangedListeners = listener;
    }

//通知所有监听者
    public void fireDataChanged() {
        if (this.dataChangedListeners != null) {
            this.dataChangedListeners.processDataChanged();
        }
    }

    public void setErrorField(boolean flag) {
        createTextArea();
        if (flag) {
            wrappedComp.setForeground(DefaultComp.FOREGROUND_COLOR_ERROR);
        } else {
            wrappedComp.setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
        }

        if (this.isEnabled() == true) {
            if (flag) {
                wrappedComp.setBackground(DefaultComp.BACKGROUND_COLOR_ERROR);
            } else {
                wrappedComp.setBackground(DefaultComp.BACKGROUND_COLOR_NORMAL);
            }
        } else {
            wrappedComp.setBackground(DefaultComp.BACKGROUND_COLOR_DISABLED);
        }
    }


    /**
     * @deprecated This component is a JScrollPane. So use it directly!!!
     * @return JScrollPane
     */
    public JScrollPane getScrollPane() {
        return this;
    }

    /**
     * @deprecated
     * @return JTextArea
     */
    public JTextArea getTextArea() {
        createTextArea();
        return wrappedComp;
    }

    public void requestFocus() {
        createTextArea();
        comFORM.setFocus(wrappedComp);
    }

    protected void this_keyPressed(KeyEvent e) {
        createTextArea();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!validateValue()) {
                wrappedComp.setBackground(DefaultComp.BACKGROUND_COLOR_ERROR);
                this.setErrorField(true);
                this.setToolTipText(validatorResult.getAllMsg()[0]);
                comFORM.setFocus(this);
            } else {
                //_setBackgroundColor();
                this.setErrorField(false);
                this.setToolTipText("");
            }
        }
    }

    public void setFont(Font font) {
        super.setFont(font);
        createTextArea();
        wrappedComp.setFont(font);
    }

    /**
     * Text maxLength
     *        add by lipengxu 2007-08-20
     * @param length int
     */
    public void setMaxByteLength(int length) {
        createTextArea();
        setDocument(new InputDocument(length, InputDocument.ALL, true));
    }

    public void setText(String text) {
        createTextArea();
        wrappedComp.setText(text);
    }
    
    public void setDocument(Document doc) {
    	doc.addUndoableEditListener(new UndoableEditImpListener(wrappedComp));
    	wrappedComp.setDocument(doc);
    }

    public String getText() {
        createTextArea();
        return wrappedComp.getText();
    }

    public int getHorizontalAlignment() {
        return SwingConstants.LEFT;
    }

    public IVWComponent duplicate() {
        createTextArea();
        VWJTextArea comp = new VWJTextArea();
        comp.setName(this.getName());
        comp.setDtoClass(this.getDtoClass());
        comp.setValidator(this.validator);
        //comp.validatorResult = (ValidatorResult)this.validatorResult.duplicate();
        comp.setFont(this.getFont());
        comp.setText(this.getText());
        return comp;
    }
}
