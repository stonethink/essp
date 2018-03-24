/*
 * $Id: ComponentMap.java,v 1.1 2006/10/10 03:51:01 mingxingzhang Exp $
 *
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 */

package org.jdesktop.swing.form;

import java.awt.Cursor;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.jdesktop.swing.JXDatePicker;
import org.jdesktop.swing.JXImagePanel;
import org.jdesktop.swing.JXListPanel;
import org.jdesktop.swing.JXRadioGroup;
import org.jdesktop.swing.JXTable;
import org.jdesktop.swing.LinkHandler;
import org.jdesktop.swing.data.DataModel;
import org.jdesktop.swing.data.DefaultTableModelExt;
import org.jdesktop.swing.data.EnumeratedMetaData;
import org.jdesktop.swing.data.Link;
import org.jdesktop.swing.data.MetaData;
import org.jdesktop.swing.data.NumberMetaData;
import org.jdesktop.swing.data.StringMetaData;
import org.jdesktop.swing.data.TabularDataModel;
import org.jdesktop.swing.data.TabularMetaData;

/**
 * Choosing strategy for creating components.
 * <p>
 * 
 * Extracted from DefaultFormFactory to have a "pluggable" place for creating
 * custom components. The usage of a ComponentCreator should be viewed as an
 * implementation detail, they don't do much.
 * <p>
 * 
 * The created components have their name property set to metaData.name. This
 * will enable searching for them in a container and facilitates testing.
 * <p>
 * 
 * PENDING: should be factored into an interface and a default implementation.
 * <p>
 * 
 * PENDING: think about task distribution - only visual aspects of the metaData
 * should be handled here.
 * 
 * @author Jeanette Winzenburg
 */
public class ComponentMap {
    private Map creatorMap;

    private ComponentCreator fallBackCreator;

    private List assignables;

    private static ComponentMap instance = new ComponentMap();

    public static ComponentMap getInstance() {
        return instance;
    }

    public static void setInstance(ComponentMap map) {
        instance = map;
    }
    /**
     * creates and returns a component based on the given MetaData.
     * 
     * @param metaData
     * @return
     */
    public JComponent createComponent(MetaData metaData) {
        ComponentCreator creator = getComponentCreator(metaData);
        return creator.createComponent(metaData);
    }

    /**
     * Encapsulates lookup-strategy to find a fitting ComponentCreator.
     * <p>
     * Here:
     * <ol>
     * <li>look-up by metaData.class, if nothing found
     * <li>look-up by metaData.elementClass, if nothing found
     * <li>fallback component
     * </ol>
     * 
     * @param metaData
     * @return
     * @throws NullPointerException
     *             if metaData == null
     */
    protected ComponentCreator getComponentCreator(MetaData metaData) {
        ComponentCreator creator = findByClass(metaData.getClass());
        if (creator == null) {
            creator = findByElementClass(metaData.getElementClass());
        }
        if (creator == null) {
            creator = getFallBackComponentCreator();
        }
        return creator;
    }

    protected ComponentCreator findByElementClass(Class elementClass) {
        elementClass = checkForArray(elementClass);
        List classes = getAssignableClasses();
        for (Iterator iter = classes.iterator(); iter.hasNext();) {
            Class clazz = (Class) iter.next();
            if (clazz.isAssignableFrom(elementClass)) {
                return findByClass(clazz);
            }
        }
        return findByClass(elementClass);
    }

    protected Class checkForArray(Class elementClass) {
        // dirty hack...
        return elementClass.isArray() ? Object[].class : elementClass;
    }

    protected ComponentCreator findByClass(Class elementClass) {
        Object mappedObject = getCreatorMap().get(elementClass);
        return (ComponentCreator) mappedObject;
    }

    protected ComponentCreator getFallBackComponentCreator() {
        if (fallBackCreator == null) {
            fallBackCreator = new LabelCreator();
        }
        return fallBackCreator;
    }

    protected void initCreators(Map map) {
        map.put(TabularMetaData.class, new TableCreator());
        map.put(EnumeratedMetaData.class, new EnumerationCreator());
        map.put(NumberMetaData.class, new NumberCreator());
        ComponentCreator textCreator = new TextCreator();
        map.put(StringMetaData.class, textCreator);
        map.put(String.class, textCreator);
        ComponentCreator booleanCreator = new BooleanCreator();
        map.put(Boolean.class, booleanCreator);
        map.put(boolean.class, booleanCreator);
        map.put(DataModel.class, new FormCreator());
        map.put(DefaultTableModelExt.class, new TableCreator());
        ComponentCreator listCreator = new ListCreator();
        // map.put(XListModel.class, listCreator);
        map.put(ListModel.class, listCreator);
        ComponentCreator listPanelCreator = new ListPanelCreator();
        map.put(List.class, listPanelCreator);
        map.put(Object[].class, listPanelCreator);
        map.put(Link.class, new LinkLabelCreator());
        ImagePanelCreator imagePanelCreator = new ImagePanelCreator();
        map.put(Image.class, imagePanelCreator);
        map.put(Icon.class, imagePanelCreator);
        DatePickerCreator datePickerCreator = new DatePickerCreator();
        map.put(Date.class, datePickerCreator);
        map.put(Calendar.class, datePickerCreator);
    }

    protected void initAssignablesClasses(List assignables) {
        assignables.add(DataModel.class);
        assignables.add(DefaultTableModelExt.class);
        assignables.add(List.class);
        // assignables.add(Image.class);
        // assignables.add(Icon.class);
        // assignables.add(XListModel.class);
    }

    // --------------------- lazy access
    private List getAssignableClasses() {
        if (assignables == null) {
            assignables = new ArrayList();
            initAssignablesClasses(assignables);
        }
        return assignables;
    }

    private Map getCreatorMap() {
        if (creatorMap == null) {
            creatorMap = new HashMap();
            initCreators(creatorMap);
        }
        return creatorMap;
    }

    // ----------------------------- default creators
    
    /**
     */
    public static class ListPanelCreator implements ComponentCreator {

        /**
         * creates and returns a JXListPanel.
         */
        public JComponent createComponent(MetaData metaData) {
            JXListPanel list = new JXListPanel();
            list.setName(metaData.getName());
            list.getList().setName(metaData.getName());
            return list;
        }

        // private void configureList(JList list, MetaData metaData) {
        // list.setName(metaData.getName());
        // setListRenderer(list, metaData);
        // installMetaDataListener(list, metaData);
        // }
        //
        // /**
        // * PENDING: no possibility to remove the listener if
        // * MetaData is removed from model
        // */
        // private void installMetaDataListener(final JList list, final MetaData
        // metaData) {
        // PropertyChangeListener l = new PropertyChangeListener() {
        //
        // public void propertyChange(PropertyChangeEvent evt) {
        // if (DataConstants.RENDER_PROPERTY_NAME.equals(evt.getPropertyName()))
        // {
        // setListRenderer(list, metaData);
        // }
        //              
        // }
        //            
        // };
        // metaData.addPropertyChangeListener(l);
        //          
        // }
        //
        // private void setListRenderer(JList list, MetaData metaData) {
        // Object renderProperty =
        // metaData.getCustomProperty(DataConstants.RENDER_PROPERTY_NAME);
        // // can't do - coupling to JGoodies ValueModel
        // // working for Beans only.
        // // if (renderProperty != null) {
        // // ValueCellRenderer renderer = new ValueCellRenderer();
        // // renderer.setPropertyName(String.valueOf(renderProperty));
        // // list.setCellRenderer(renderer);
        // // }
        // }
    }

    /**
     */
    public static class ListCreator implements ComponentCreator {

        /**
         * creates and returns a JScrollPane containing a JList.
         */
        public JComponent createComponent(MetaData metaData) {
            JList list = new JList();

            // list.setPreferredSize(new Dimension(100, 100));
            JScrollPane pane = new JScrollPane(list);
            pane.setName(metaData.getName());
            list.setName(metaData.getName());
            return pane;
        }
    }

    public static class TableCreator implements ComponentCreator {

        /**
         * creates and returns a JScrollPane containing a JXTable.
         */
        public JComponent createComponent(MetaData metaData) {
            JXTable table = new JXTable();
            JScrollPane pane = new JScrollPane(table);
            table.setName(metaData.getName());
            pane.setName(metaData.getName());
            return pane;
        }
    }

    /**
     * 
     */
    public static class FormCreator implements ComponentCreator {
        
        /**
         * creates and returns a JForm.
         * PENDING: need to wrap into a JScrollPane by default?
         */
        public JComponent createComponent(MetaData metaData) {
            JForm form = new JForm();
            form.setName(metaData.getName());
            // visual debugging...
            // form.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            return form;
        }
    }

    /**
     */
    public static class BooleanCreator implements ComponentCreator {
        
        /**
         * creates and returns a JCheckBox.
         */
        public JComponent createComponent(MetaData metaData) {
            JCheckBox box = new JCheckBox();
            box.setName(metaData.getName());
            // JW: logic state - move to Binding
            box.setEnabled(!metaData.isReadOnly());
            return box;
        }
    }

    /**
     * creates JSpinner if metaData is NumberMetaData, the map's fallback
     * component otherwise.
     * 
     */
    public class NumberCreator implements ComponentCreator {

        public JComponent createComponent(MetaData metaData) {
            if (!(metaData instanceof NumberMetaData)) {
                // Hack: prevents making static... where else to put?
                return getFallBackComponentCreator().createComponent(metaData);
            }
            JComponent comp = createComponent((NumberMetaData) metaData);
            comp.setName(metaData.getName());
            return comp;
        }

        public JComponent createComponent(NumberMetaData numberMetaData) {
            Class fieldClass = numberMetaData.getElementClass();
            Number min = numberMetaData.getMinimum();
            Number max = numberMetaData.getMaximum();
            // JW: edit constraint - move to SpinnerBinding
            if ((min != null) && (max != null)) {
                SpinnerModel spinnerModel = null;
                if ((fieldClass == Integer.class) || (fieldClass == Long.class)
                        || (fieldClass == Short.class)
                        || (fieldClass == int.class)) {
                    spinnerModel = new SpinnerNumberModel(min.intValue(), min
                            .intValue(), max.intValue(), 1);
                } else if ((fieldClass == Float.class)
                        || (fieldClass == Double.class)
                        || (fieldClass == float.class)
                        || (fieldClass == double.class)) {

                    // **@todo aim: need to add precision to NumberMetaData */
                    spinnerModel = new SpinnerNumberModel(min.doubleValue(),
                            min.doubleValue(), max.doubleValue(), .01);
                }
                if (spinnerModel != null) {
                    return new JSpinner(spinnerModel);
                }
            }
            // Hack: prevents making static... where else to put?
            return getFallBackComponentCreator()
                    .createComponent(numberMetaData);
        }
    }

    /**
     */
    public static class TextCreator implements ComponentCreator {
        /**
         * creates and returns a JTextComponent.
         */

        public JComponent createComponent(MetaData metaData) {
            if (metaData instanceof StringMetaData) {
                return createComponent((StringMetaData) metaData);
            }
            int fieldLength = metaData.getDisplayWidth();
            JTextField field = new JTextField((fieldLength > 0) ? fieldLength
                    : 24);
            field.setName(metaData.getName());
            return field;
        }

        public JComponent createComponent(StringMetaData stringMetaData) {
            if (stringMetaData.isMultiLine()) {
                int columns = stringMetaData.getDisplayWidth();
                int rows = Math.min(stringMetaData.getMaxLength() / columns, 5);
                JTextArea area = new JTextArea(rows, columns);
                JScrollPane pane = new JScrollPane(area);
                area.setName(stringMetaData.getName());
                pane.setName(stringMetaData.getName());
                return pane;
            }
            int fieldLength = Math.min(stringMetaData.getDisplayWidth(),
                    stringMetaData.getMaxLength());
            JTextField field = new JTextField(fieldLength);
            field.setName(stringMetaData.getName());
            return field;
        }
    }

    /**
     */
    public static class EnumerationCreator implements ComponentCreator {

        public JComponent createComponent(MetaData metaData) {
            JComponent box = null;
            if (metaData instanceof EnumeratedMetaData) {
                box = createComponent((EnumeratedMetaData) metaData);
            } else {
                box = new JComboBox();
            }
            box.setName(metaData.getName());
            return box;
        }

        public JComponent createComponent(EnumeratedMetaData enumMetaData) {
            Object values[] = enumMetaData.getEnumeration();
            if (values.length > 3) {
                return new JComboBox();
            }
            // JW: constraint... move to RadioGroupBinding
            return new JXRadioGroup(values);
        }
    }

    /**
     * 
     */
    public static class LabelCreator implements ComponentCreator {

        public JComponent createComponent(MetaData metaData) {
            JLabel label = new JLabel();
            label.setName(metaData.getName());
            return label;
        }
    }

    /**
     */
    public static class LinkLabelCreator implements ComponentCreator {

        public JComponent createComponent(MetaData metaData) {
            JLabel label = new JLabel();
            label.setName(metaData.getName());
            label.addMouseListener(new LinkHandler());
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            return label;
        }
    }

    /**
     * 
     */
    public static class ImagePanelCreator implements ComponentCreator {

        public JComponent createComponent(MetaData metaData) {
            JXImagePanel label = new JXImagePanel();
            label.setName(metaData.getName());
            return label;
        }
    }

    /**
     */
    public static class DatePickerCreator implements ComponentCreator {

        public JComponent createComponent(MetaData metaData) {
            JXDatePicker label = new JXDatePicker();
            label.setName(metaData.getName());
            return label;
        }
    }
}
