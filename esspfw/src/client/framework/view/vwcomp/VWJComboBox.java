package client.framework.view.vwcomp;

import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.UIManager;

import c2s.dto.DtoComboItem;
import c2s.dto.DtoUtil;
import client.framework.model.VMComboBox;
import client.framework.view.common.DefaultComp;
import client.framework.view.common.comFORM;
import client.framework.view.jmscomp.JMsComboBox;
import validator.Validator;
import validator.ValidatorResult;
import javax.swing.SwingConstants;

public class VWJComboBox extends JMsComboBox implements IVWComponent {
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


  public VWJComboBox() {
    super();
    this.setBorder(UIManager.getBorder("ComboBox.border"));
    this.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        this_keyPressed(e);
      }
    });

    this.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        this_focusGained(e);
      }

      public void focusLost(FocusEvent e) {
        this_focusLost(e);
      }
    });

  }


  /**
   * 如果DtoComboItem.itemValue == value，则选中它
   * @param value Object
   */
  /*
  public void setUICValue(Object value) {
    ComboBoxModel model = this.getModel();
    if (!(model instanceof VMComboBox)) {
      this.setSelectedItem(value);
      return;
    }

    VMComboBox vmComboBox = (VMComboBox) model;
    Object obj = vmComboBox.findItemByValue(value);
    if (obj != null || (value == null && obj == null)) { //当找不到对应的定义时不进行赋值
      this.setSelectedItem(obj);
    }else{
        this.setSelectedItem(value);
    }
  }
*/

  public void setUICValue(Object value) {
        this.setUICValue( value == null?"":value.toString(), value);
  }

  public void setUICValue(String name, Object value) {
      this.setUICValue(name, value, value);
  }

  public void setUICValue(String name, Object value, Object relation) {
      //如果enabled为false,那么给comboBox设setSelectedItem是设不上去的.
      //所以这里判断这种情况
      boolean isEnabled = this.isEnabled();
      if (isEnabled == false) {
          setEnabled(true);
      }

      if (!(getModel() instanceof VMComboBox)) {
          this.setSelectedItem(value);
      }else{

          VMComboBox model = (VMComboBox)this.getModel();
          if (model != null) {

              //空字符串的与null等（这样做是为了使下拉框中的NullElement选中）
              if (value != null && value instanceof String
                  && ((String) value).trim().equals("") == true) {
                  value = null;
              }

              Object findedItem = model.findItemByValue(value);
              if (findedItem == null) {
                  //先复原
                  VMComboBox modelCopy = new VMComboBox(model.getObjectVector());
                  modelCopy.setSelectedItem(modelCopy.getSelectedItem());

                  //再加入不存在的item
                  DtoComboItem item = modelCopy.insertNotExistElementAt(name, value, relation, modelCopy.getSize());

                  this.setModel(modelCopy);
                  modelCopy.setSelectedItem(item);
              } else {
                  //先复原
                  VMComboBox modelCopy = new VMComboBox(model.getObjectVector());
                  modelCopy.setSelectedItem(modelCopy.getSelectedItem());

                  this.setModel(modelCopy);
                  modelCopy.setSelectedItem(findedItem);
              }
          } else {
              VMComboBox modelCopy = new VMComboBox();
              //加入不存在的item
              DtoComboItem item = modelCopy.insertNotExistElementAt(name,
                      value, relation, 0);
              modelCopy.setSelectedItem(modelCopy.getSelectedItem());

              this.setModel(modelCopy);
              modelCopy.setSelectedItem(item);
          }
      }

      setToolTip();

      //还原
      if (isEnabled == false) {
          setEnabled(false);
      }
  }

  /**
   * 返回被选中的DtoComboItem的itemValue
   * @return Object
   */
  public Object getUICValue() {
    ComboBoxModel model = this.getModel();
    if (!(model instanceof VMComboBox)) {
        this.setEnabled(false);
      return this.getSelectedItem();
    }

    Object obj = this.getSelectedItem();
    if (obj != null && obj instanceof DtoComboItem) {
      return ((DtoComboItem) obj).getItemValue();
    }
    return obj;
  }

  public void getValueFromUI() {
  }

  protected void this_keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      if (!validateValue()) {
        this.setErrorField(true);
        System.out.println("set component '" + getName() + "' : " +
                           validatorResult.getAllMsg()[0]);
        this.setToolTipText(validatorResult.getAllMsg()[0]);
        comFORM.setFocus(this);
      } else {
        _setBackgroundColor();
        this.setErrorField(false);
        this.setToolTipText(null);
      }
    }
  }

  void this_focusGained(FocusEvent e) {
    this.setBackground(DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE);
  }

  public void this_focusLost(FocusEvent e) {
    this.setBackground(DefaultComp.BACKGROUND_COLOR_NORMAL);
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

  public int getHorizontalAlignment(){
      return SwingConstants.LEFT;
  }

  public IVWComponent duplicate() {
    VWJComboBox comp = new VWJComboBox();
    comp.setName(this.getName());
    comp.setDtoClass(this.getDtoClass());
    comp.setValidator(this.validator);
    //comp.validatorResult=(ValidatorResult)this.validatorResult.duplicate();
    comp.setFont(this.getFont());

    Vector objectList = new Vector();
    for (int i = 0; i < ((VMComboBox)getModel()).getObjectVector().size(); i++) {
      objectList.add(((VMComboBox)getModel()).getObjectVector().get(i));
    }
    ComboBoxModel model = new VMComboBox(objectList);

    comp.setModel(model);
    return comp;
  }

  public void setVMComboBox(VMComboBox vmComboBox) {
    this.setModel(vmComboBox);
  }

  public void setVMComboBox(Object[] itemValues) {
    this.setModel(VMComboBox.toVMComboBox((String[]) itemValues));
  }

  public void setVMComboBox(Vector v) {
    this.setModel(VMComboBox.toVMComboBox((String[]) v.toArray()));
  }


  public void setSelectedItem(Object anObject) {
      super.setSelectedItem(anObject);
      setToolTip();
  }

  private void setToolTip(){
      String toolTipText = null;
      Object selObj = this.getSelectedItem();
      if( selObj != null ){
          if( selObj instanceof DtoComboItem ){
              toolTipText = ((DtoComboItem)selObj).getItemName();
          }else{
              toolTipText = selObj.toString();
          }
      }

      this.setToolTipText(toolTipText);
  }

}
