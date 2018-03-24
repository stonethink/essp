package client.framework.view.vwcomp;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.UIManager;
import javax.swing.text.Document;

import c2s.dto.DtoUtil;
import client.framework.common.Constant;
import client.framework.view.common.DefaultComp;
import client.framework.view.common.comFORM;
import client.framework.view.event.UndoableEditImpListener;
import client.framework.view.jmscomp.JMsDate;
import validator.Validator;
import validator.ValidatorResult;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:  Wistron ITS Wuhan SDC</p>
 * @author not attributable
 * @version 1.0
 */

public class VWJDate extends JMsDate implements IVWComponent {
    Object dtoBean;
    Validator validator;
    private boolean _bCanSelect = false;
    private java.util.Date refDate = null;
    private boolean isEditing = false;
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

    public VWJDate() {
        super();
        this.setBorder(UIManager.getBorder("TextField.border"));
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                onMouseClicked(e);
            }
        }
        ); try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setUICValue(Object value) {
        String sValue = "";
        if (value instanceof java.util.Date) {
            refDate = (java.util.Date) value;

            //modify by xh
//      if (this.getDataType().equals(Constant.DATE_YYYYMMDD)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
//        sValue = sdf.format( (java.util.Date) value, new StringBuffer(),
//                             new java.text.FieldPosition(0)).toString();
//      } else if (this.getDataType().equals(Constant.DATE_YYMMDD)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyMMdd");
//        sValue = sdf.format( (java.util.Date) value, new StringBuffer(),
//                             new java.text.FieldPosition(0)).toString();
//      } else if (this.getDataType().equals(Constant.DATE_YYYYMM)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMM");
//        sValue = sdf.format( (java.util.Date) value, new StringBuffer(),
//                             new java.text.FieldPosition(0)).toString();
//      } else if (this.getDataType().equals(Constant.DATE_HHMMSS)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HHmmss");
//        sValue = sdf.format( (java.util.Date) value, new StringBuffer(),
//                             new java.text.FieldPosition(0)).toString();
//      } else if (this.getDataType().equals(Constant.DATE_HHMM)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HHmm");
//        sValue = sdf.format( (java.util.Date) value, new StringBuffer(),
//                             new java.text.FieldPosition(0)).toString();
//      } else if (this.getDataType().equals(Constant.DATE_DD)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd");
//        sValue = sdf.format( (java.util.Date) value, new StringBuffer(),
//                             new java.text.FieldPosition(0)).toString();
//      } else if (this.getDataType().equals(Constant.DATE_MM)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM");
//        sValue = sdf.format( (java.util.Date) value, new StringBuffer(),
//                             new java.text.FieldPosition(0)).toString();
//      }
            String realDataType = this.getRealDataType(this.getDataType());
            if (realDataType.equals("") == false) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(realDataType);
                sValue = sdf.format((java.util.Date) value, new StringBuffer(),
                                    new java.text.FieldPosition(0)).toString();
            }

            setValueText(sValue);
            //setText(sValue);
        } else if (value instanceof String) {
            //System.out.println("set String:" + (String) value);
            setValueText((String) value);
        } else {
            if (value != null) {
                setText(value.toString());
            } else {
                setText("");
            }
        }
    }

    public Object getUICValue() {
        if (this.getValueText().trim().equals("") == false) {
            //modify by xh
//      if (this.getDataType().equals(Constant.DATE_YYYYMMDD)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
//        return sdf.parse(this.getValueText(), new java.text.ParsePosition(0));
//      } else if(this.getDataType().equals(Constant.DATE_YYMMDD)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyMMdd");
//        return sdf.parse(this.getValueText(), new java.text.ParsePosition(0));
//      } else if(this.getDataType().equals(Constant.DATE_YYYYMM)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMM");
//        return sdf.parse(this.getValueText(), new java.text.ParsePosition(0));
//      } else if (this.getDataType().equals(Constant.DATE_HHMMSS)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HHmmss");
//        return sdf.parse(this.getValueText(), new java.text.ParsePosition(0));
//      } else if (this.getDataType().equals(Constant.DATE_HHMM)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HHmm");
//        return sdf.parse(this.getValueText(), new java.text.ParsePosition(0));
//      } else if (this.getDataType().equals(Constant.DATE_DD)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd");
//        return sdf.parse(this.getValueText(), new java.text.ParsePosition(0));
//      } else if (this.getDataType().equals(Constant.DATE_MM)) {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM");
//        return sdf.parse(this.getValueText(), new java.text.ParsePosition(0));
//      }
            String realDataType = this.getRealDataType(this.getDataType());
            if (realDataType.equals("") == false) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(realDataType);
                return sdf.parse(this.getValueText(), new java.text.ParsePosition(0));
            }
        }
        return (java.util.Date)null;
    }

    private String getRealDataType(String dataType) {
        String realDataType = "";
        if (this.getDataType().equals(Constant.DATE_YYYYMMDD)) {
            realDataType = "yyyyMMdd";
        } else if (this.getDataType().equals(Constant.DATE_YYMMDD)) {
            realDataType = "yyMMdd";
        } else if (this.getDataType().equals(Constant.DATE_YYYYMM)) {
            realDataType = "yyyyMM";
        } else if (this.getDataType().equals(Constant.DATE_HHMMSS)) {
            realDataType = "HHmmss";
        } else if (this.getDataType().equals(Constant.DATE_HHMM)) {
            realDataType = "HHmm";
        } else if (this.getDataType().equals(Constant.DATE_DD)) {
            realDataType = "dd";
        } else if (this.getDataType().equals(Constant.DATE_MM)) {
            realDataType = "MM";
        }

        return realDataType;
    }

    public boolean validateValue() {
        boolean bRtn = true;

        //先检查输入的格式是否正确
        String dateStr = this.getValueText();
        if (dateStr.equals("") == false) {
            try {
                String realDataType = this.getRealDataType(this.getDataType());
                if (realDataType.equals("") == false) {
                    SimpleDateFormat sdf = new SimpleDateFormat(realDataType);
                    java.util.Date utilDate = sdf.parse(dateStr, new java.text.ParsePosition(0));
                    if (utilDate == null) {
                        bRtn = false;
                    } else {//防止出现2007/02/99的问题
                    	this.setUICValue(utilDate);
                    }
                }
            } catch (Exception ex) {
                bRtn = false;
            }
        }

        if (bRtn == false) {
            validatorResult = new ValidatorResult();
            validatorResult.addMsg(this.getName(), "It's not a date.");
            return false;
        }

        if (dtoBean == null || validator == null) {
            return true;
        }
//    String sValue = getValueText();
        Object value = this.getUICValue();
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

    public boolean isCanSelect() {
        return _bCanSelect;
    }

    public void setCanSelect(boolean isCanSelect) {
        _bCanSelect = isCanSelect;
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

    void onMouseClicked(MouseEvent e) {
        if ( isEnabled() && isCanSelect() && e.getClickCount() == 2) {
            isEditing = true;
            java.awt.Frame owner = this.getParentWindow();
            VWJCalendar calendar = new VWJCalendar(owner, "", true);
            String returnDate = calendar.showDialog(this);
            this.setValueText(returnDate);
            //System.out.println("valueText: "+this.getValueText());
            comFORM.setFocus(this);
            isEditing = false;
        }
    }
    
    /**
     * reset UICValue 防止出现2007/02/99的问题
     */
    public void this_focusLost(FocusEvent e) {
    	super.this_focusLost(e);
    	//防止出现2007/02/99的问题
        this.setUICValue(this.getUICValue());
    }

    protected void onKeyPressed(KeyEvent e) {
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
            }
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

    public boolean isEditing() {
        return isEditing;
    }

    public IVWComponent duplicate() {
        VWJDate comp = new VWJDate();
        comp.setName(this.getName());
        comp.setDtoClass(this.getDtoClass());
        comp.setValidator(this.validator);
        //comp.validatorResult = (ValidatorResult)this.validatorResult.duplicate();
        comp.setDataType(this.getDataType());
        comp.setFont(this.getFont());
//        comp.setText(this.getText());
        comp.setCanSelect(this.isCanSelect());
        return comp;
    }

    private void jbInit() throws Exception {

    }
    
    /**
     * implement undo redo
     */
    public void setDocument(Document doc) {
    	doc.addUndoableEditListener(new UndoableEditImpListener(this));
    	super.setDocument(doc);
    }
}
