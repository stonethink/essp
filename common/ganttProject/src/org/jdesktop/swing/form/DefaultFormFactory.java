/*
 * $Id: DefaultFormFactory.java,v 1.1 2006/10/10 03:51:01 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.form;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.jdesktop.swing.binding.Binding;
import org.jdesktop.swing.binding.BindingMap;
import org.jdesktop.swing.data.DataModel;
import org.jdesktop.swing.data.MetaData;

/**
 * note:technically this should probably be in a FormUI, or
 * initialized from a FormUI.
 *
 * @author Amy Fowler
 * @version 1.0
 */

public class DefaultFormFactory extends FormFactory {
    private static Icon requiredIcon;

    static {
        URL url = BindingBorder.class.getResource("resources/asterisk.8x8.png");
        requiredIcon = new ImageIcon(url);
    }

    private Insets labelInsets;
    private Insets nestedFormLabelInsets;
    private Insets componentInsets;

    public DefaultFormFactory() {
        labelInsets = new Insets(4, 5, 5, 4);
        nestedFormLabelInsets = new Insets(12, 5, 5, 4);
        componentInsets = new Insets(1, 4, 4, 4);
    }
    
    /**
     * Delegates to current ComponentMap.
     */
    public JComponent createComponent(MetaData metaData) {
        if (isNonVisual(metaData)) {
          return null;
        }
        JComponent component = ComponentMap.getInstance().createComponent(metaData);
        // should not happen - ComponentMap has a fallback..
        if (component == null) {
          throw new IllegalStateException("some error in ComponentMap");
        }
        return component;
      }

     /**
      * delegates to current BindingMap.
      */
      public Binding createBinding(DataModel model, String fieldName,
          JComponent component) {
        if (component == null) {
          return null;
        }
        if (component instanceof JScrollPane) {
          component =
            (JComponent) ((JScrollPane) component).getViewport().getView();
        }
        return BindingMap.getInstance().createBinding(component, model, fieldName);
      }



//    public JComponent createComponent(MetaData metaData) {
//        if (isNonVisual(metaData)) {
//            return null;
//          }
//
//        Class fieldClass = metaData.getElementClass();
//
//        if (fieldClass.isAssignableFrom(DataModel.class)) {
//            return new JForm();
//        }
//
//        if (metaData.isReadOnly()) {
//            return new JLabel();
//
//        }
//        else if (metaData instanceof EnumeratedMetaData) {
//            EnumeratedMetaData enumMetaData = (EnumeratedMetaData) metaData;
//            Object values[] = enumMetaData.getEnumeration();
//            if (values.length > 3) {
//                return new JComboBox(values);
//            }
//            return new JXRadioGroup(values);
//        }
//        else if (metaData instanceof NumberMetaData) {
//            NumberMetaData numberMetaData = (NumberMetaData) metaData;
//            Number min = numberMetaData.getMinimum();
//            Number max = numberMetaData.getMaximum();
//
//            if (min != null && max != null) {
//                SpinnerModel spinnerModel = null;
//                if (fieldClass == Integer.class || fieldClass == Long.class ||
//                    fieldClass == Short.class || fieldClass == int.class) {
//                    spinnerModel = new SpinnerNumberModel(min.intValue(),
//                        min.intValue(), max.intValue(), 1);
//                }
//                else if (fieldClass == Float.class || fieldClass == Double.class ||
//                         fieldClass == float.class || fieldClass == double.class) {
//                    //**@todo aim: need to add precision to NumberMetaData */
//                    spinnerModel = new SpinnerNumberModel(min.doubleValue(),
//                        min.doubleValue(), max.doubleValue(), .01);
//                }
//                if (spinnerModel != null) {
//                    return new JSpinner(spinnerModel);
//                }
//
//            }
//        } else if (metaData instanceof StringMetaData) {
//            StringMetaData stringMetaData = (StringMetaData) metaData;
//            if (stringMetaData.isMultiLine()) {
//                int columns = stringMetaData.getDisplayWidth();
//                int rows = Math.min(stringMetaData.getMaxLength()/columns, 5);
//                return new JTextArea(rows, columns);
//            }
//            int fieldLength = Math.min(stringMetaData.getDisplayWidth(),
//                                       stringMetaData.getMaxLength());
//            return new JTextField(fieldLength);
//        }
//
//        // return editors based on type
//
//        if (fieldClass == Boolean.class || fieldClass == boolean.class) {
//            JCheckBox checkbox = new JCheckBox();
//            return checkbox;
//
//        }
//        else if (fieldClass.isArray() || fieldClass.equals(List.class)) {
//            return new JXListPanel();
//
//        }
//        else if (fieldClass.equals(String.class)) {
//            return new JTextField(24);
//
//        }
//        else if (fieldClass.equals(Date.class) ||
//                 fieldClass.equals(Calendar.class)) {
//            // create a date picker control.
//            return new JXDatePicker();
//
//        }
//        else if (fieldClass.equals(Image.class) ||
//                 fieldClass.equals(Icon.class)) {
//            return new JXImagePanel();
//        }
//	else if (fieldClass.equals(Link.class)) {
//	    JLabel label = new JLabel();
//	    label.addMouseListener(new LinkHandler());
//	    label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//
//	    return label;
//	}
//
//        // fallback...
//        return new JLabel();
//    }
//
//    public Binding createBinding(DataModel model, String fieldName, JComponent component) {
//        /**@todo aim: temporary until factory is created */
//	    Binding binding = null;
//
//        if (component instanceof JForm) {
//            return new FormBinding((JForm)component, model, fieldName);
//        }
//        if (component instanceof JCheckBox) {
//            binding = new BooleanBinding((JCheckBox) component, model, fieldName);
//        }
//        if (component instanceof JXRadioGroup) {
//            binding = new RadioBinding((JXRadioGroup)component, model, fieldName);
//        }
//        if (component instanceof JLabel) {
//            return new LabelBinding((JLabel)component, model, fieldName);
//        }
//        if (component instanceof JTextComponent) {
//            binding = new TextBinding((JTextComponent) component, model, fieldName);
//            int iconPosition = component instanceof JTextArea?
//                SwingConstants.NORTH_EAST : SwingConstants.WEST;
//            BindingBorder bborder = new BindingBorder(binding, iconPosition);
//            Insets insets = bborder.getBorderInsets(component);
//            Dimension prefSize = component.getPreferredSize();
//            prefSize.width += (insets.left + insets.right);
//            component.setPreferredSize(prefSize);
//            component.setBorder(new CompoundBorder(component.getBorder(),bborder));
//            return binding;
//        }
//        if (component instanceof JComboBox) {
//            binding = new ComboBoxBinding((JComboBox)component, model, fieldName);
//        }
//        if (component instanceof JSpinner) {
//            binding = new SpinnerBinding( (JSpinner) component, model,
//                                         fieldName);
//        }
//        if (component instanceof JXDatePicker) {
//            return new DatePickerBinding( (JXDatePicker) component, model,
//                                         fieldName);
//        }
//        if (component instanceof JXImagePanel) {
//            return new ImagePanelBinding( (JXImagePanel) component, model,
//                                         fieldName);
//        }
//        if (component instanceof JXListPanel) {
//            return new ListBinding( ( (JXListPanel) component).getList(), model,
//                                   fieldName);
//        }
//        doAddBindingBorder(component, binding);
//        return binding;
//    }

    private void doAddBindingBorder(JComponent component, Binding binding) {
//        component.setBorder(new CompoundBorder(new BindingBorder(binding),
//                                               component.getBorder()));
    }

    public void addComponent(JComponent parent, JComponent component,
                             MetaData metaData) {
        GridBagLayout gridbag = initializeLayout(parent);

        // Add label
        JLabel label;
        

        String labelText = metaData.getLabel();
        if ((labelText != null) && (labelText.length() > 0)) {
            labelText += ":";
        } else {
            labelText = "";
        }
        if (metaData.isRequired()) {
            label = new JLabel(labelText, requiredIcon,
                               JLabel.CENTER);
            label.setHorizontalTextPosition(JLabel.TRAILING);
        }
        else {
            label = new JLabel(labelText);
        }
        Font boldFont = label.getFont().deriveFont(Font.BOLD);
        label.setFont(boldFont);
        label.setHorizontalAlignment(JLabel.RIGHT);

        Rectangle bounds = label.getBounds();
        Rectangle iconRect = new Rectangle();
        Rectangle textRect = new Rectangle();
        /*
        String value = SwingUtilities.layoutCompoundLabel(label,
            label.getFontMetrics(label.getFont()),
            label.getText(),
            label.getIcon(),
            label.getVerticalAlignment(),
            label.getHorizontalAlignment(),
            label.getVerticalTextPosition(),
            label.getHorizontalTextPosition(),
            label.getBounds(),
            iconRect, textRect,
            label.getIconTextGap());
        System.out.println("label bounds=" + bounds.x + "," + bounds.y +
                           " " + bounds.width + "x" + bounds.height +
                           " textRect=" + textRect.x + "," + textRect.y +
                           " " + textRect.width + "x" + textRect.height +
                           " value=" + value);
            */

        GridBagConstraints c = new GridBagConstraints();

        Integer gridY = (Integer) parent.getClientProperty(
            "Form.gridY");
        if (gridY == null) {
            gridY = new Integer(0);
        }

        c.gridy = gridY.intValue();
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.NORTHEAST;
        if (component instanceof JForm) {
            // add a little space above nested form's label to set it apart
            c.insets = nestedFormLabelInsets;
        }
        else {
            c.insets = labelInsets;
        }
        gridbag.setConstraints(label, c);
        parent.add(label);

        // Now add component
        if (component instanceof JForm) {
            // if nested form, put it below label
            c.gridy++;
            c.gridwidth = 2;
            c.insets = new Insets(0, 25, 0, 0);
        }
        else {
            // if not a nested form, put to the right of label
            c.gridwidth = 1;
            c.gridx = 1;
            c.insets = (component instanceof JLabel ? labelInsets :
                        componentInsets);
        }
        c.anchor = GridBagConstraints.NORTHWEST;

        gridbag.setConstraints(component, c);
        parent.add(component);

        gridY = new Integer(c.gridy+1);
        parent.putClientProperty("Form.gridY", gridY);
    }

    private GridBagLayout initializeLayout(JComponent parent) {
        LayoutManager layout = parent.getLayout();
        if (!(layout instanceof GridBagLayout)) {
            layout = new GridBagLayout();
            parent.setLayout(layout);
        }
        return (GridBagLayout)layout;
    }


}
