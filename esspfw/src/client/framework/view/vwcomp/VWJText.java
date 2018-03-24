package client.framework.view.vwcomp;

import java.awt.event.KeyEvent;

import javax.swing.UIManager;
import javax.swing.text.Document;

import c2s.dto.DtoUtil;
import client.framework.view.common.DefaultComp;
import client.framework.view.common.comFORM;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.UndoableEditImpListener;
import client.framework.view.jmscomp.JMsText;
import validator.Validator;
import validator.ValidatorResult;

public class VWJText extends JMsText implements IVWComponent {
    Object dtoBean;
    Validator validator;
    ValidatorResult validatorResult = null;
    DataChangedListener dataChangedListeners;


    private boolean bReshap = false;
    private int offset = 0;

    public void setBounds(int x, int y, int w, int h) {
        if (isBReshap() == true) {
            int newX = Math.max(x, getOffset());
            super.setBounds(newX, y, w - (newX - x), h);
        } else {
            super.setBounds(x, y, w, h);
        }
    }

    public VWJText() {
        super();
        this.setMaxByteLength(DefaultComp.TEXT_MAX_BYTE_LENGTH);
        this.setBorder(UIManager.getBorder("TextField.border"));
    }

    public void setUICValue(Object value) {
        if (value != null) {
            setText(value.toString());
        } else {
            setText("");
        }
    }

    public Object getUICValue() {
        String sValue = getText();
        return sValue;
    }

    public boolean validateValue() {
//        System.out.println("VWJText: name=" + getName());
        boolean bRtn = true;
        if (dtoBean == null || validator == null || getName() == null) {
            return true;
        }
        Object value = getUICValue();
        setValueToDto(value);
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

    public void setValueToDto(Object value) {
        if (dtoBean != null) {
            try {
                DtoUtil.setProperty(dtoBean, getName(), value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public void this_focusLost(FocusEvent e) {
//        super.this_focusLost(e);
//        //getUICValue();
//    }

    protected void this_keyPressed(KeyEvent e) {
        //当重新输入值时，字体不为红色 ywj
        this.setErrorField(false);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!validateValue()) {
                setBackground(DefaultComp.BACKGROUND_COLOR_ERROR);
                this.setErrorField(true);
                //System.out.println("set component '" + getName() + "' : " + validatorResult.getAllMsg()[0]);
                this.setToolTipText(validatorResult.getAllMsg()[0]);
                comFORM.setFocus(this);
            } else {
                _setBackgroundColor();
                this.setErrorField(false);
                this.setToolTipText(null);
            }
        }
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

//    public void setErrorField(boolean flag) {
//        if (flag) {
//            setForeground(DefaultComp.FOREGROUND_COLOR_ERROR);
//            setBackground(DefaultComp.BACKGROUND_COLOR_ERROR);
//        } else {
//            setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
//            setBackground(DefaultComp.BACKGROUND_COLOR_NORMAL);
//        }
//
//    }

    public void setErrorField(boolean flag) {
        if (this.isEnabled() == true) {
            if (flag) {
                setForeground(DefaultComp.FOREGROUND_COLOR_ERROR);
                setBackground(DefaultComp.BACKGROUND_COLOR_ERROR);
            } else {
                setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
                setBackground(DefaultComp.BACKGROUND_COLOR_NORMAL);
            }
        } else {
            setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
            setBackground(DefaultComp.BACKGROUND_COLOR_DISABLED);
        }
    }


    public void setDtoClass(Class dtoClass) {
        try {
            if (dtoClass != null) {
                this.dtoBean = dtoClass.newInstance();
            }
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

    public IVWComponent duplicate() {
        VWJText comp = new VWJText();
        comp.setName(this.getName());
        comp.setDtoClass(this.getDtoClass());
        comp.setValidator(this.validator);
        //comp.validatorResult=(ValidatorResult)this.validatorResult.duplicate();
        comp.setFont(this.getFont());
//        comp.setText(this.getText());
        return comp;
    }
    
    /**
     * implement undo redo
     */
    public void setDocument(Document doc) {
    	doc.addUndoableEditListener(new UndoableEditImpListener(this));
    	super.setDocument(doc);
    }
}
