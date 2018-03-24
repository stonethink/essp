package client.framework.view.vwcomp;

import java.awt.event.KeyEvent;

import javax.swing.UIManager;
import javax.swing.text.Document;

import c2s.dto.DtoUtil;
import client.framework.view.common.DefaultComp;
import client.framework.view.common.comFORM;
import client.framework.view.event.UndoableEditImpListener;
import client.framework.view.jmscomp.JMsNumber;
import validator.Validator;
import validator.ValidatorResult;

public class VWJNumber extends JMsNumber implements IVWComponent {
    Object dtoBean;
    Validator validator;
    ValidatorResult validatorResult = null;

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

    public VWJNumber() {
        super();
        this.setBorder(UIManager.getBorder("TextField.border"));
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

    public void setUICValue(Object value) {
        if ( value != null && value.equals("") == false ) {
            try {
                Double doubleValue=new Double(value.toString());
                this.setValue( doubleValue.longValue());
            } catch(Exception e) {
                this.setText("");
            }
        } else {
            setText("");
        }
    }

    public Object getUICValue() {
        //System.out.println("VWJNuber getText:["+getText()+"]");
        if (this.getText().trim().equals("")) {
            //return null;
            return new Long(0);
        }
        try {
            return new Long(this.getValue());
        } catch(Exception e) {
            return new Long(0);
        }
    }

    public boolean validateValue() {
        boolean bRtn = true;
        if (dtoBean == null || validator == null) {
            return true;
        }
        String sValue = Long.toString(this.getValue());
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

    public void setValueToDto(Object value) {
        if (dtoBean != null) {
            try {
                DtoUtil.setProperty(dtoBean, getName(), value);
            } catch (Exception e) {
                //
            }
        }
    }


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


    protected void this_keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!validateValue()) {
                setBackground(DefaultComp.BACKGROUND_COLOR_ERROR);
                this.setErrorField(true);
                System.out.println("set component '" + getName() + "' : " + validatorResult.getAllMsg()[0]);
                this.setToolTipText(validatorResult.getAllMsg()[0]);
                comFORM.setFocus(this);
            } else {
                _setBackgroundColor();
                this.setErrorField(false);
                this.setToolTipText("");
                /**
                 * no need transferFocus() ,
                 * this action is implemented by comFORM.setEnterOrder()
                 */
                //this.transferFocus();
            }
        }
    }

    public IVWComponent duplicate() {
        VWJNumber comp = new VWJNumber();
        comp.setName(this.getName());
        comp.setMaxInputIntegerDigit(this.getMaxInputIntegerDigit());
        comp.setDtoClass(this.getDtoClass());
        comp.setValidator(this.validator);
        comp.setFont(this.getFont());
//        comp.setText(this.getText());
        comp.setUICValue( this.getUICValue() );
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
