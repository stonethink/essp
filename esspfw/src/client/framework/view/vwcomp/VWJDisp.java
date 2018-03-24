package client.framework.view.vwcomp;

import javax.swing.UIManager;
import javax.swing.text.Document;

import c2s.dto.DtoUtil;
import client.framework.view.event.DataChangedListener;
import client.framework.view.event.UndoableEditImpListener;
import client.framework.view.jmscomp.JMsDisp;
import validator.Validator;
import validator.ValidatorResult;
import client.framework.view.common.DefaultComp;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */

public class VWJDisp extends JMsDisp implements IVWComponent {
    Object dtoBean;
    Validator validator;

    DataChangedListener dataChangedListeners;


    private boolean bReshap = false;
    private int offset = 0;
    public boolean isBReshap() {
        return bReshap;
    }

    public int getOffset() {
        return offset;
    }

    public void setBounds(int x, int y, int w, int h) {
        if (isBReshap() == true) {
            int newX = Math.max(x, getOffset());
            super.setBounds(newX, y, w - (newX - x), h);
        } else {
            super.setBounds(x, y, w, h);
        }
    }

    public VWJDisp() {
        super();
        this.setMaxByteLength(DefaultComp.TEXT_MAX_BYTE_LENGTH);
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
        String sValue = "";
//    sValue = dataFormat(value,dataFormat);
        if (value != null) {
            setText(value.toString());
        } else {
            setText("");
        }
    }

    public boolean validateValue() {
        return true;
    }

    public ValidatorResult getValidatorResult() {
        return null;
    }

    public Object getUICValue() {
        String sValue = getText();
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
    }

    public void setEnabled ( boolean isEnabled ){
        super.setEnabled(false);
    }


    public IVWComponent duplicate() {
        VWJDisp comp = new VWJDisp();
        comp.setName(this.getName());
        comp.setDtoClass(this.getDtoClass());
        comp.setValidator(this.validator);
        comp.setFont(this.getFont());
        comp.setText(this.getText());
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
