package client.framework.view.vwcomp;

import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import client.framework.model.VMComboBox;
import client.framework.view.common.DefaultComp;
import client.framework.view.common.comFORM;
import client.framework.view.jmscomp.JMsComboBox;
import validator.Validator;
import validator.ValidatorResult;
import javax.swing.SwingConstants;

public class VWJEditComboBox extends JMsComboBox implements IVWComponent {
    private Object dtoBean;
    private Validator validator;
    private ValidatorResult validatorResult = null;

    private boolean bReshap = false;
    private int offset = 0;
    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setBReshap(boolean bReshap) {
        this.bReshap = bReshap;
    }

    public void setBounds(int x, int y, int w, int h) {
        if (isBReshap() == true) {
            int newX = Math.max(x, getOffset());
            super.setBounds(newX, y, w - (newX - x), h);
        } else {
            super.setBounds(x, y, w, h);
        }
    }

    public VWJEditComboBox() {
        super();
        this.setEditable(true);
        this.setEditor( new VWJComboBoxEditor() );
        this.setBorder(UIManager.getBorder("ComboBox.border"));

        //当按下keypressed键时，会触发this.getEditor().getEditorComponent()的keypressed事件
        //而不会触发this.addKeyListener的keypressed事件
//        this.addKeyListener( new KeyAdapter(){
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    System.out.println("for test - VWJEditComboBox.keyPressed");
//                }
//            }
//        });

        this.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    editor_keyPressed(e);
                }
            }
        });

        /*
        this.getEditor().getEditorComponent().addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(FocusEvent e) {
                editor_focusGained(e);
            }

            public void focusLost(FocusEvent e) {
                editor_focusLost(e);
            }
        });
        */

    }

    public void setUICValue(Object value) {
        //如果enabled为false,那么给comboBox设setSelectedItem是设不上去的.
        //所以这里判断这种情况
        boolean isEnabled= this.isEnabled();
        if( isEnabled == false ){
            setEnabled(true);
        }

        ComboBoxModel model = this.getModel();
        if (model instanceof VMComboBox) {

            VMComboBox vmComboBox = (VMComboBox) model;
            Object obj = vmComboBox.findItemByValue(value);
            if( obj == null ){
                this.setSelectedItem(value);
            }else{
                this.setSelectedItem(obj);
            }
        }

        //还原
        if( isEnabled == false ){
            setEnabled(false);
        }
    }

    public Object getUICValue() {
        ComboBoxModel model = this.getModel();
        if (model instanceof VMComboBox) {

            VMComboBox vmComboBox = (VMComboBox) model;
            String display = ((JTextComponent)this.getEditor().
                              getEditorComponent()).getText();
            Object obj = vmComboBox.findItemByName(display);
            if (obj != null && obj instanceof DtoComboItem) {
                return ((DtoComboItem) obj).getItemValue();
            }
        }

        return null;
    }

    /*
    void editor_focusGained(FocusEvent e) {
        this.getEditor().getEditorComponent().setBackground(DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE);
    }

    public void editor_focusLost(FocusEvent e) {
        this.getEditor().getEditorComponent().setBackground(DefaultComp.BACKGROUND_COLOR_NORMAL);
    }
    */

    protected void editor_keyPressed(KeyEvent e) {
        if (!validateValue()) {
            this.setErrorField(true);
            //System.out.println("set component '" + getName() + "' : " + validatorResult.getAllMsg()[0]);
            this.setToolTipText(validatorResult.getAllMsg()[0]);
            comFORM.setFocus(this);
        } else {
            _setBackgroundColor();
            this.setErrorField(false);
            this.setToolTipText("");
        }
    }

    public void setErrorField(boolean flag) {
        if (this.isEnabled() == true) {
            if (flag) {
                setForeground(DefaultComp.FOREGROUND_COLOR_ERROR);

                getEditor().getEditorComponent().setForeground(DefaultComp.FOREGROUND_COLOR_ERROR);
                getEditor().getEditorComponent().setBackground(DefaultComp.BACKGROUND_COLOR_ERROR);
            } else {
                setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);

                getEditor().getEditorComponent().setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
                getEditor().getEditorComponent().setBackground(DefaultComp.BACKGROUND_COLOR_NORMAL);
            }

        } else {
            setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
            setBackground(DefaultComp.BACKGROUND_COLOR_DISABLED);
            getEditor().getEditorComponent().setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
            getEditor().getEditorComponent().setBackground(DefaultComp.BACKGROUND_COLOR_DISABLED);
        }

    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if( enabled == true ){
            setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
        }else{
            setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
            setBackground(DefaultComp.BACKGROUND_COLOR_DISABLED);
        }
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

    public boolean validateValue() {
        boolean bRtn = true;
        if (dtoBean == null || validator == null) {
            return true;
        }

        //先检查输入的值是否在下拉列表中存在
        /*
        ComboBoxModel model = this.getModel();
        String display = ((JTextComponent) (this.getEditor().getEditorComponent())).getText();
        boolean isExist = false;
        for (int i = 0; i < model.getSize(); i++) {
            Object item = model.getElementAt(i);
            if (item != null) {
                if (item instanceof DtoComboItem) {
                    if (display.equals(((DtoComboItem) item).getItemName()) == true) {
                        isExist = true;
                        break;
                    }
                } else {
                    if (display.equals(item) == true) {
                        isExist = true;
                        break;
                    }
                }
            }
        }
        */

        /*if (isExist == false) {
            validatorResult = new ValidatorResult();
            validatorResult.addMsg(this.getName(), "The input is not in the list.");
            bRtn = false;
        } else {*/
            Object value = getUICValue();
            setValueToDto(value);
            validatorResult = validator.validate(dtoBean, this.getName());
            bRtn = validatorResult.isValid();
        /*}*/

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

  public int getHorizontalAlignment(){
      return SwingConstants.LEFT;
  }

  public void setSelectedItem(Object anObject) {
      String toolTipText = "";

      if (anObject != null) {
          if (anObject instanceof DtoComboItem) {
              toolTipText = ((DtoComboItem) anObject).getItemName();
          } else {
              toolTipText = anObject.toString();
          }
      }
      this.setToolTipText(toolTipText);

      super.setSelectedItem(anObject);
  }

    public IVWComponent duplicate() {
        VWJEditComboBox comp = new VWJEditComboBox();
        comp.setName(this.getName());
        comp.setDtoClass(this.getDtoClass());
        comp.setValidator(this.validator);
        //comp.validatorResult=(ValidatorResult)this.validatorResult.duplicate();
        comp.setFont(this.getFont());

        Vector objectList = new Vector();
        for (int i = 0; i < getModel().getSize(); i++) {
            objectList.add(getModel().getElementAt(i));
        }
        ComboBoxModel model = new VMComboBox(objectList);

        comp.setModel(model);
        return comp;
    }
}
