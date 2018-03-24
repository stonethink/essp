/*
 * $Id: BindingMap.java,v 1.1 2006/10/10 03:51:00 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.binding;

import java.awt.Dimension;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.text.JTextComponent;

import org.jdesktop.swing.JXDatePicker;
import org.jdesktop.swing.JXImagePanel;
import org.jdesktop.swing.JXListPanel;
import org.jdesktop.swing.JXRadioGroup;
import org.jdesktop.swing.data.DataModel;
import org.jdesktop.swing.form.BindingBorder;
import org.jdesktop.swing.form.JForm;

/**
 * Choosing strategy for creating Bindings.<p>
 *
 * Extracted from DefaultFormFactory to have a 
 * "pluggable" place for creating custom bindings. The usage
 * of a BindingCreator should be viewed as an implementation
 * detail, they don't do much.
 *
 * PENDING: there's a implicit coupling to ComponentMap -
 * the BindingMap assumes that the ComponentMap did a
 * good enough job when choosing components. <p>
 *
 * PENDING: should be factored into an interface and
 * a default implementation.<p>
 *
 * PENDING: really want to configure the component here?
 *
 * @author Jeanette Winzenburg
 */
public class BindingMap {

  private Map bindingMap;
  private static BindingMap instance;
  
  
  public static BindingMap getInstance() {
    // this is not thread safe...  
    if (instance == null) {
      instance = new BindingMap();
    }
    return instance;
  }

  public static void setInstance(BindingMap bindingMap) {
    instance = bindingMap;
  }

  /**
   * Creates and returns Binding between the
   * component and a field of the DataModel.
   *  
   *  PENDING: null return value? Or better 
   *  throw BindingException if no appropriate creator found?
   *  
   * @param component
   * @param model
   * @param fieldName
   * @return 
   * 
   * @throws NullPointerException if any of the parameters is null.
   */
  public Binding createBinding(JComponent component, DataModel model, String fieldName) {
    BindingCreator creator = getBindingCreator(component);
    if (creator != null) {
        return creator.createBinding(component, model, fieldName);
    }
    return null;
  }
  
  /** encapsulates lookup strategy to find an appropriate 
   * BindingCreator for the given component. <p>
   * 
   * Here:
   * <ol>
   * <li> look-up by component class
   * <li> look-up by assignables to component class
   * </ol>
   * 
   *  
   * @param component
   * @return a BindingCreator which can create a binding to
   *   the component or null if none is found.
   */
  protected BindingCreator getBindingCreator(JComponent component) {
    // PENDING: implement better lookup...
    BindingCreator creator =
      (BindingCreator) getBindingMap().get(component.getClass());
    if (creator == null) {
      creator = findByAssignable(component.getClass());
    }
    return creator;
  }

  protected BindingCreator findByAssignable(Class componentClass) {
    Set keys = getBindingMap().keySet();
    for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
      Class element = (Class) iter.next();
      if (element.isAssignableFrom(componentClass)) {
        return (BindingCreator) getBindingMap().get(element);
      }
    }
    return null;
  }

  protected Map getBindingMap() {
    if (bindingMap == null) {
      bindingMap = new HashMap();
      initBindingMap(bindingMap);
    }
    return bindingMap;
  }

  protected void initBindingMap(Map map) {
    map.put(JForm.class, new FormBindingCreator());
    map.put(JXRadioGroup.class, new RadioGroupBindingCreator());
    map.put(JLabel.class, new LabelBindingCreator());
    map.put(JCheckBox.class, new CheckBoxBindingCreator());
    BindingCreator textBindingCreator = new TextBindingCreator();
    map.put(JTextComponent.class, textBindingCreator);
    map.put(JComboBox.class, new ComboBoxBindingCreator());
    BindingCreator tableBindingCreator = new TableBindingCreator();
    map.put(JTable.class, tableBindingCreator);
    BindingCreator listBindingCreator = new ListBindingCreator();
    map.put(JList.class, listBindingCreator);
    map.put(JXListPanel.class, new ListPanelBindingCreator());
    map.put(JSpinner.class, new SpinnerBindingCreator());
    map.put(JXImagePanel.class, new ImagePanelBindingCreator());
    map.put(JXDatePicker.class, new DatePickerBindingCreator());
  }

//-------------------------------- BindingCreators  

  /**
   */
  public static class ListBindingCreator implements BindingCreator {

    public Binding createBinding(JComponent component, DataModel dataModel,
        String fieldName) {
      return new ListBinding((JList) component, dataModel, fieldName);
      
    }
  }

  /**
   */
  public static class ListPanelBindingCreator implements BindingCreator {

    public Binding createBinding(JComponent component, DataModel dataModel,
        String fieldName) {
      return new ListBinding(((JXListPanel) component).getList(), dataModel, fieldName);
      
    }
  }
  /**
   */
  public class TableBindingCreator implements BindingCreator {

    public Binding createBinding(JComponent component, DataModel dataModel,
        String fieldName) {
      return new TableBinding((JTable) component, dataModel, fieldName);
    }
  }

  /**
   */
  public static class FormBindingCreator implements BindingCreator {

    public Binding createBinding(JComponent component, DataModel dataModel,
        String fieldName) {
      return new FormBinding((JForm) component, dataModel, fieldName);
    }
  }

  /**
   */
  public static class TextBindingCreator implements BindingCreator {

    public Binding createBinding(JComponent component, DataModel dataModel,
        String fieldName) {
      Binding binding = doCreateBinding((JTextComponent) component,
                          dataModel, fieldName);
      configureComponent(component, binding);
      return binding;
    }

    protected Binding doCreateBinding(JTextComponent component,
        DataModel dataModel, String fieldName) {
      Binding binding = new TextBinding(component, dataModel, fieldName);
      return binding;
    }

    /**
     * PENDING: it's a view issue, should not be done here?.
     * @param component
     * @param binding
     */
    protected void configureComponent(JComponent component, Binding binding) {
      int iconPosition = (component instanceof JTextArea)
                           ? SwingConstants.NORTH_EAST : SwingConstants.WEST;
      BindingBorder bborder = new BindingBorder(binding, iconPosition);
//      Insets insets = bborder.getBorderInsets(component);
//      Dimension prefSize = component.getPreferredSize();
//      prefSize.width += (insets.left + insets.right);
//      // JW: arrgghhh... never do, prevents correct resizing.
//      component.setPreferredSize(prefSize);
      component.setBorder(new CompoundBorder(component.getBorder(), bborder));
    }
  }

  /**
   */
  public static class LabelBindingCreator implements BindingCreator {

    public Binding createBinding(JComponent component, DataModel dataModel,
        String fieldName) {
      return new LabelBinding((JLabel) component, dataModel, fieldName);
    }
  }

  /**
   */
  public static class ImagePanelBindingCreator implements BindingCreator {

    public Binding createBinding(JComponent component, DataModel dataModel,
        String fieldName) {
      return new ImagePanelBinding((JXImagePanel) component, dataModel, fieldName);
    }
  }

  /**
   */
  public static class DatePickerBindingCreator implements BindingCreator {

    public Binding createBinding(JComponent component, DataModel dataModel,
        String fieldName) {
      return new DatePickerBinding((JXDatePicker) component, dataModel, fieldName);
    }
  }

  /**
   */
  public static abstract class RequiredBindingCreator implements BindingCreator {

    protected void doAddBindingBorder(JComponent component, Binding binding) {
      component.setBorder(new CompoundBorder(new BindingBorder(binding),
          component.getBorder()));
    }
  }

  /**
   */
  public static class ComboBoxBindingCreator extends RequiredBindingCreator {

    public Binding createBinding(JComponent component, DataModel dataModel,
        String fieldName) {
      Binding binding = new ComboBoxBinding((JComboBox) component, dataModel,
                          fieldName);
      doAddBindingBorder(component, binding);
      return binding;
    }
  }

  /**
   */
  public static class RadioGroupBindingCreator extends RequiredBindingCreator {

    public Binding createBinding(JComponent component, DataModel dataModel,
        String fieldName) {
      Binding binding = new RadioBinding((JXRadioGroup) component, dataModel,
                          fieldName);
      doAddBindingBorder(component, binding);
      return binding;
    }
  }

  /**
   */
  public static class CheckBoxBindingCreator extends RequiredBindingCreator {

    public Binding createBinding(JComponent component, DataModel dataModel,
        String fieldName) {
      Binding binding = new BooleanBinding((JCheckBox) component, dataModel,
                          fieldName);
      doAddBindingBorder(component, binding);
      return binding;
    }
  }

  /**
   */
  public static class SpinnerBindingCreator extends RequiredBindingCreator {

    public Binding createBinding(JComponent component, DataModel dataModel,
        String fieldName) {
      Binding binding = new SpinnerBinding((JSpinner) component, dataModel,
                          fieldName);
      doAddBindingBorder(component, binding);
      return binding;
    }
  }
}
