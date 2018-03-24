package client.framework.view.vwcomp;


/**
 * @author Stone 20050306
 *
 */
import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import c2s.dto.ConvDataType;
import c2s.dto.DtoUtil;
import c2s.dto.IDto;
import c2s.dto.ITreeNode;
import client.framework.common.Constant;
import org.apache.log4j.Category;
import validator.Validator;
import validator.ValidatorResult;
import javax.swing.JComboBox;
import client.framework.model.VMComboBox;
import c2s.dto.DtoComboItem;
import java.util.HashSet;
import javax.swing.JPanel;
import client.framework.common.treeTable.JTreeTable;
import client.framework.common.treeTable.TreeTableModelAdapter;


/**
 * @url element://model:project::esspfw/jdt:e_class:src:client.framework.view.vwcomp.VWJTableEditor
 * @url element://model:project::esspfw/jdt:e_class:src:client.framework.view.vwcomp.VWJTableRender
 */
public class VWUtil {
    static Category log = Category.getInstance("clinet");
    public static final String SET_CHANGE_FLAG = "SCF";
    public static final String SET_INSERT_FLAG = "SIF";
    public static final String SET_DELETE_FLAG = "SDF";
    public static final String NOT_SET_FLAG = "NSF";

    public static void bindDto2UI(IDto dtoNode, Component component) {
        if (dtoNode == null || component == null) {
            return;
        }

        try {
            //modfiy by stone for DataNode define 20050824
            Object dataBean = null;
            if (dtoNode instanceof ITreeNode) {
                dataBean = ((ITreeNode) dtoNode).getDataBean();
            } else {
                dataBean = dtoNode;
            }

            String[] propertyNames = DtoUtil.getPropertyNames(dataBean);

            for (int i = 0; i < propertyNames.length; i++) {
                String fldName = propertyNames[i];
                Object fldValue = DtoUtil.getProperty(dataBean, fldName);
                IVWComponent vwc = findComponent(component, fldName);
                if (vwc != null) {
//                    vwc.setDtoClass(dtoBean.getClass().getName());
                    if(vwc.isEnabled()) {
                        vwc.setUICValue(fldValue);
                    } else {
                        vwc.setEnabled(true);
                        vwc.setUICValue(fldValue);
                        vwc.setEnabled(false);
                    }
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("", e);
        }

    }

    public static void convertUI2Dto(IDto dtoNode, Component component) {
        convertUI2Dto(dtoNode, component, SET_CHANGE_FLAG);
    }

    public static void convertUI2Dto(IDto dtoNode, Component component,
                                     String[] ignorProperties) {
        convertUI2Dto(dtoNode,component,SET_CHANGE_FLAG,ignorProperties);
    }

    public static void convertUI2Dto(IDto dtoNode, Component component,
                                     String flag) {
        convertUI2Dto(dtoNode,component,flag,null);
    }

    public static void convertUI2Dto(IDto dtoNode, Component component,
                                     String flag, String[] ignorProperties) {
        if (dtoNode == null || component == null) {
            return;
        }

        HashSet propSet=new HashSet();
        if(ignorProperties!=null) {
            for(int i=0;i<ignorProperties.length;i++) {
                propSet.add(ignorProperties[i]);
            }
        }

        try {
            //modfiy by stone for DataNode define 20050824
            Object dataBean = null;
            if (dtoNode instanceof ITreeNode) {
                dataBean = ((ITreeNode) dtoNode).getDataBean();
            } else {
                dataBean = dtoNode;
            }

            String[] propertyNames = DtoUtil.getPropertyNames(dataBean);

            for (int i = 0; i < propertyNames.length; i++) {
                String fldName = propertyNames[i];
                if(propSet.contains(fldName)) {
                    continue;
                }
                Class fldType = DtoUtil.getPropertyType(dataBean, fldName);
                IVWComponent vwc = findComponent(component, fldName);
                //if (vwc != null && vwc.isEnabled()) {
                if (vwc != null ) {
                    try {
                        Object fldValue = vwc.getUICValue();
                        if( vwc.isEnabled() ){

                            if (SET_INSERT_FLAG.equals(flag)) {
                                dtoNode.setOp(IDto.OP_INSERT);
                            } else if (SET_DELETE_FLAG.equals(flag)) {
                                dtoNode.setOp(IDto.OP_DELETE);
                            } else if (SET_CHANGE_FLAG.equals(flag)) {
                                Object dtoValue = DtoUtil.getProperty(dataBean,
                                    fldName);
                                if (!((fldValue == null ||
                                       (fldValue instanceof String && ((String) fldValue).trim().equals(""))
                                      ) &&
                                      (dtoValue == null ||
                                       (dtoValue instanceof String && ((String) dtoValue).trim().equals(""))
                                      )
                                    )) { //value in component and value in dto are not both empty(null or "")

                                    Object bizValue = ConvDataType.toOtherType(
                                        fldValue,
                                        fldType);

                                    if (!((bizValue == null ||
                                           (bizValue instanceof String &&
                                            ((String) bizValue).trim().equals(""))) &&
                                          (dtoValue == null ||
                                           (dtoValue instanceof String &&
                                            ((String) dtoValue).trim().equals(""))))) {

                                        if (dtoValue != null && bizValue != null) {
                                            if (dtoValue instanceof Number &&
                                                bizValue instanceof Number) {
                                                Number number1 = (Number) dtoValue;
                                                Number number2 = (Number) bizValue;
                                                if (number1.doubleValue() !=
                                                    number2.doubleValue()) {
                                                    setOpModify(dtoNode);
                                                }
                                            } else if (dtoValue instanceof java.
                                                util.Date &&
                                                bizValue instanceof java.util.
                                                Date) {
                                            	VWJDate dateComp;
                                            	if(vwc instanceof VWJDatePanel) {
                                            		dateComp = ((VWJDatePanel)vwc).getDateComp();
                                            	} else {
                                            		dateComp = (VWJDate) vwc;
                                            	}
                                                if (dateComp.getDataType().equals(
                                                    Constant.DATE_YYYYMMDD)) {
                                                    String pattern = "yyyyMMdd";
                                                    java.text.SimpleDateFormat sdf = new
                                                        java.text.
                                                        SimpleDateFormat(
                                                        pattern);
                                                    String date1 = sdf.format(
                                                        dtoValue,
                                                        new StringBuffer(""),
                                                        new java.text.
                                                        FieldPosition(0)).
                                                        toString();
                                                    String date2 = sdf.format(
                                                        bizValue,
                                                        new StringBuffer(""),
                                                        new java.text.
                                                        FieldPosition(0)).
                                                        toString();
                                                    if (!date1.equals(date2)) {
                                                        setOpModify(dtoNode);
                                                    }
                                                } else if (dateComp.getDataType().
                                                    equals(Constant.
                                                    DATE_HHMM)) {
                                                    String pattern = "HHmm";
                                                    java.text.SimpleDateFormat sdf = new
                                                        java.text.
                                                        SimpleDateFormat(
                                                        pattern);
                                                    String date1 = sdf.format(
                                                        dtoValue,
                                                        new StringBuffer(""),
                                                        new java.text.
                                                        FieldPosition(0)).
                                                        toString();
                                                    String date2 = sdf.format(
                                                        bizValue,
                                                        new StringBuffer(""),
                                                        new java.text.
                                                        FieldPosition(0)).
                                                        toString();
                                                    if (!date1.equals(date2)) {
                                                        setOpModify(dtoNode);
                                                    }

                                                }
                                            } else if (!dtoValue.equals(bizValue)) {
                                                setOpModify(dtoNode);
                                            }
                                        } else if (dtoValue == null && bizValue != null) {
                                            if (vwc instanceof VWJReal) {
                                                VWJReal realComp = (VWJReal) vwc;
                                                if (realComp.getText().trim().equals("") == false &&
                                                    Double.parseDouble(realComp.
                                                    getText()) != 0) {
                                                    setOpModify(dtoNode);
                                                }
                                            } else if (vwc instanceof VWJNumber) {
                                                VWJNumber numbComp = (VWJNumber)
                                                    vwc;
                                                if (numbComp.getText().trim().equals("") == false &&
                                                    Double.parseDouble(numbComp.
                                                    getText()) != 0) {
                                                    setOpModify(dtoNode);
                                                }
                                            } else {
                                                setOpModify(dtoNode);
                                            }
                                        } else {
                                            setOpModify(dtoNode);
                                        }
                                    }
                                } else {

                                    //对VWJDate控件作单独检查。因为当vwjdate有输入（即有改变时），但输入的不合法时，getUICValue()的值为null，
                                    //这不能反映真实的输入，所以当getValueText()不为空，而dtoValue为null时，设置状态"M".
                                    if (vwc instanceof VWJDate) {
                                        VWJDate dateComp = (VWJDate) vwc;
                                        if (dateComp.getValueText().trim().equals("") == false) {
                                            setOpModify(dtoNode);
                                        }
                                    }
                                    
                                    if(vwc instanceof VWJDatePanel) {
                                    	VWJDate dateComp = ((VWJDatePanel) vwc).getDateComp();
                                        if (dateComp.getValueText().trim().equals("") == false) {
                                            setOpModify(dtoNode);
                                        }
                                    }
                                }

//              if (dtoValue != null) {
//                if (!dtoValue.equals(bizValue)) {
//                   setOpModify(dtoBean);
//                }
//              } else if (bizValue != null) {
//                setOpModify(dtoBean);
//              }
                            } else if (NOT_SET_FLAG.equals(flag)) { ///add
                                dtoNode.setOp(IDto.OP_NOCHANGE);
                            }
                        }

                        DtoUtil.setProperty(dataBean, fldName, fldValue);
                    } catch (Exception ee) {
                        //ee.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void setOpModify(IDto dto) {
        dto.setOp(IDto.OP_MODIFY);
    }

    private static IVWComponent findComponent(Component component, String name) {
        if (component instanceof IVWComponent) {
            String currName = ((IVWComponent) component).getName();
            if (currName == null) {
                return null;
            }
            if (currName.equals(name)) {
                return (IVWComponent) component;
            }
        }
        if (component instanceof Container) {
            int iCount = ((Container) component).getComponentCount();
            for (int i = 0; i < iCount; i++) {
                Component subComponent = ((Container) component).getComponent(i);
                IVWComponent Ret = null;
                Ret = findComponent(subComponent, name);
                if (Ret != null) {
                    return Ret;
                }
            }
        }
        return null;
    }


    /**
     * 安装Validator对象到控件中
     * @param component Component
     * @param validator Validator
     */
    public static void installValidator(Component component,
                                        Validator validator,
                                        Class dtoClass) {
        if (component instanceof IVWComponent) {
            IVWComponent ivwComponent = (IVWComponent) component;
            ivwComponent.setValidator(validator);
            ivwComponent.setDtoClass(dtoClass);
        }
        if (component instanceof Container) {
            int iCount = ((Container) component).getComponentCount();
            for (int i = 0; i < iCount; i++) {
                Component subComponent = ((Container) component).getComponent(i);
                installValidator(subComponent, validator, dtoClass);
            }
        }

    }

    public static void clearErrorField(Component component) {
        if (component instanceof IVWComponent) {
            IVWComponent ivwComponent = (IVWComponent) component;
//            if (component.isEnabled() == true) {
//                component.setBackground(DefaultComp.BACKGROUND_COLOR_ENABLED);
//            } else {
//                component.setBackground(DefaultComp.BACKGROUND_COLOR_DISABLED);
//                component.setForeground(DefaultComp.FOREGROUND_COLOR_NORMAL);
//            }

            ivwComponent.setErrorField(false);
            ivwComponent.setToolTipText("");
        }
        if (component instanceof Container) {
            int iCount = ((Container) component).getComponentCount();
            for (int i = 0; i < iCount; i++) {
                Component subComponent = ((Container) component).getComponent(i);
                clearErrorField(subComponent);
            }
        }
    }

    /**
     * 将画面的每个控件text清空, error消除
     * @param component Component -- 一个workArea
     */
    public static void clearUI(Component component) {
        if (component instanceof IVWComponent) {
            IVWComponent ivwComponent = (IVWComponent) component;

            ivwComponent.setErrorField(false);
            ivwComponent.setToolTipText("");
            ivwComponent.setUICValue("");
        }
        //if (component instanceof Container) {
        if (component instanceof JPanel) {
            int iCount = ((Container) component).getComponentCount();
            for (int i = 0; i < iCount; i++) {
                Component subComponent = ((Container) component).getComponent(i);
                clearUI(subComponent);
            }
        }
    }

    /**
     * 将画面的每个控件text清空, error消除
     * @param component Component -- 一个workArea
     */
    public static void setUIEnabled(Component component, boolean isEnabled) {
        if (component instanceof IVWComponent) {
            IVWComponent ivwComponent = (IVWComponent) component;

            ivwComponent.setEnabled(isEnabled);
        }
        if (component instanceof Container) {
            int iCount = ((Container) component).getComponentCount();
            for (int i = 0; i < iCount; i++) {
                Component subComponent = ((Container) component).getComponent(i);
                setUIEnabled(subComponent, isEnabled);
            }
        }
    }


//    public static ValidatorResult validate(IVWComponent component,
//                                           String formName,
//                                           Validator validator) {
//        String name=component.getName();
//        Object value=component.getUICValue();
//        ValidatorResult result = validator.validate(value,formName,name);
//        clearErrorField((Component)component);
//        if (!result.isValid(name)) {
//            component.setErrorField(true);
//            component.setToolTipText(result.getMsg(name)[0]);
//        }
//        return result;
//    }

    private static Component compareComp(Component comp1, Component comp2) {
        Component ret;
        java.awt.Rectangle position1 = comp1.getBounds();
        java.awt.Rectangle position2 = comp2.getBounds();

        if (position1.getY() > position2.getY()) {
            ret = comp2;
        } else if (position1.getY() == position2.getY() &&
                   position1.getX() > position2.getX()) {
            ret = comp2;
        } else {
            ret = comp1;
        }
        log.debug("compare(" + comp1.getName() + "," + comp2.getName() +
                  ")=" + ret.getName());
        return ret;
    }

    public static ValidatorResult validate(Component component, IDto dtoNode,
                                           Validator validator) {
        java.awt.Rectangle position = null;
        Component firstComp = null;
        List errorComps = new ArrayList();

        //modfiy by stone for DataNode define 20050824
        Object dataBean = null;
        if (dtoNode instanceof ITreeNode) {
            dataBean = ((ITreeNode) dtoNode).getDataBean();
        } else {
            dataBean = dtoNode;
        }

        ValidatorResult result = validator.validate(dataBean);
//    if (dtoNode instanceof ITreeNode) {
//      ((ITreeNode) dtoNode).setValidatorResult(result);
//    }
        clearErrorField(component);
        if (!result.isValid()) {
            Class cls = dataBean.getClass();
            Field[] flds = cls.getDeclaredFields();
            for (int i = 0; i < flds.length; i++) {
                Field fld = flds[i];
                String fldName = fld.getName();

                IVWComponent vwc = findComponent(component, fldName);
                if (vwc != null) {
                    if (!result.isValid(fldName)) {
                        Component comp = (Component) vwc;
                        if (firstComp == null) {
                            firstComp = comp;
                        } else {
                            firstComp = compareComp(firstComp, comp);
                        }

                        vwc.setErrorField(true);
                        log.info("set component '" + fldName + "' : " +
                                 result.getMsg(fldName)[0]);
                        vwc.setToolTipText(result.getMsg(fldName)[0]);
                    }
                }
            }
        }
        if (firstComp != null) {
            firstComp.requestFocus();
        }
        return result;
    }

    public static void setErrorField(Component component,
                                     ValidatorResult result) {
        String[] fields = result.getErrorFields();
        if (fields != null) {
            for (int i = 0; i < fields.length; i++) {
                IVWComponent vwc = findComponent(component, fields[i]);
                if (vwc != null) {
                    vwc.setErrorField(true);
                    vwc.setToolTipText(result.getMsg(fields[i])[0]);
                }
            }
        }
    }

    public static void setTableRender(JTable table) {
        NodeViewManager nodeViewManager;
        if (table instanceof IVWJTableViewManager) {
            nodeViewManager = ((IVWJTableViewManager) table).getNodeViewManager();
        } else {
            nodeViewManager = new NodeViewManager();
        }

        TableModel model = table.getModel();
        if (model instanceof client.framework.model.IColumnConfig) {
            client.framework.model.IColumnConfig newModel = (client.framework.
                    model.
                    IColumnConfig) model;
            List cfgs = newModel.getColumnConfigs();

            int ColumnCount = table.getColumnCount();
            TableColumnModel columnModel = table.getColumnModel();
            for (int i = 0; i < ColumnCount; i++) {
                TableColumn column = columnModel.getColumn(i);
                client.framework.model.VMColumnConfig columnConfig = (client.
                        framework.
                        model.VMColumnConfig) cfgs.get(i);
                Component comp = columnConfig.getComponent();
                if (comp != null &&
                    comp instanceof client.framework.view.vwcomp.IVWComponent) {
                    //System.out.println("set column"+table.getColumnName(i)+" on "+vwComp);

                    if (comp instanceof JComboBox) {
                        comp = new VWJText();
                    }

                    VWJTableRender render = new VWJTableRender(comp,
                            nodeViewManager);
                    column.setCellRenderer(render);
                } else {//add by lipengxu
                	column.setCellRenderer(table.getDefaultRenderer(model.getColumnClass(i)));
                }
            }

        }
    }

    public static void setTableEditor(JTable table) {
        TableModel model = table.getModel();
        if (model instanceof client.framework.model.IColumnConfig) {
            client.framework.model.IColumnConfig newModel = (client.framework.
                    model.
                    IColumnConfig) model;
            List cfgs = newModel.getColumnConfigs();

            int ColumnCount = table.getColumnCount();
            TableColumnModel columnModel = table.getColumnModel();
            for (int i = 0; i < ColumnCount; i++) {
                TableColumn column = columnModel.getColumn(i);
                client.framework.model.VMColumnConfig columnConfig = (client.
                        framework.
                        model.VMColumnConfig) cfgs.get(i);
                if (columnConfig.getComponent() != null &&
                    columnConfig.getComponent() instanceof client.framework.
                    view.vwcomp.
                    IVWComponent) {
                    //System.out.println("set column"+table.getColumnName(i)+" on "+vwComp);
                    Component comp = columnConfig.getComponent();
                    if (comp instanceof IVWComponent) {
                        ((IVWComponent) comp).setDtoClass(newModel.getDtoType());
                    }
                    VWJTableEditor editor = new VWJTableEditor(comp);
                    column.setCellEditor(editor);
                }
            }

        }
    }

    public static void setTableEditor(JTreeTable treeTable) {
//        System.out.println("Model-1的名字为:"+treeTable.getModel().getClass().getName());
        TreeTableModelAdapter model=(TreeTableModelAdapter)treeTable.getModel();
//        TreeTableModel model =(TreeTableModel) treeTable.getModel();
        //注：TreeTableMode继承的是TreeModel
        //    TreeTableModelAdapter是TableMode的子类
        //在使用的时候，树表真正使用的Model是VMTreeTableModelAdapter

//        System.out.println("Model-2的名字为:"+model.getClass().getName());
        //VMTreeTableModelAdapter也实现了IColumnConfig接口
        if (model instanceof client.framework.model.IColumnConfig) {
            client.framework.model.IColumnConfig newModel = (client.framework.
                    model.
                    IColumnConfig) model;
            List cfgs = newModel.getColumnConfigs();

            int ColumnCount = treeTable.getColumnCount();
            TableColumnModel columnModel = treeTable.getColumnModel();
            for (int i = 0; i < ColumnCount; i++) {
                TableColumn column = columnModel.getColumn(i);
                client.framework.model.VMColumnConfig columnConfig = (client.
                        framework.
                        model.VMColumnConfig) cfgs.get(i);
                if (columnConfig.getComponent() != null &&
                    columnConfig.getComponent() instanceof client.framework.
                    view.vwcomp.
                    IVWComponent) {
                    //System.out.println("set column"+table.getColumnName(i)+" on "+vwComp);
                    Component comp = columnConfig.getComponent();
                    if (comp instanceof IVWComponent) {
                        ((IVWComponent) comp).setDtoClass(newModel.getDtoType());
                    }
                   VWJTableEditor editor=new VWJTableEditor(comp);
                    column.setCellEditor(editor);
                }
            }

        }
    }

    public static void setTreeTableEditor(JTreeTable treeTable) {
//        System.out.println("Model-1的名字为:"+treeTable.getModel().getClass().getName());
    TreeTableModelAdapter model=(TreeTableModelAdapter)treeTable.getModel();
//        TreeTableModel model =(TreeTableModel) treeTable.getModel();
    //注：TreeTableMode继承的是TreeModel
    //    TreeTableModelAdapter是TableMode的子类
    //在使用的时候，树表真正使用的Model是VMTreeTableModelAdapter

//        System.out.println("Model-2的名字为:"+model.getClass().getName());
    //VMTreeTableModelAdapter也实现了IColumnConfig接口
    if (model instanceof client.framework.model.IColumnConfig) {
        client.framework.model.IColumnConfig newModel = (client.framework.
                model.
                IColumnConfig) model;
        List cfgs = newModel.getColumnConfigs();

        int ColumnCount = treeTable.getColumnCount();
        TableColumnModel columnModel = treeTable.getColumnModel();
        for (int i = 0; i < ColumnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            client.framework.model.VMColumnConfig columnConfig = (client.
                    framework.
                    model.VMColumnConfig) cfgs.get(i);
            if (columnConfig.getComponent() != null &&
                columnConfig.getComponent() instanceof client.framework.
                view.vwcomp.
                IVWComponent) {
                //System.out.println("set column"+table.getColumnName(i)+" on "+vwComp);
                Component comp = columnConfig.getComponent();
                if (comp instanceof IVWComponent) {
                    ((IVWComponent) comp).setDtoClass(newModel.getDtoType());
                }
               VWJTreeTableEditor editor=new VWJTreeTableEditor(comp,treeTable);
                column.setCellEditor(editor);
            }
        }

    }
}


    public static void initComboBox(VWJComboBox comboBox, VMComboBox model,
                                    String defaultName, Object defaultValue) {
        if (model != null && defaultValue != null) {
            VMComboBox managersCopy = (VMComboBox) DtoUtil.deepClone(model);
            Object findedItem=managersCopy.findItemByValue(defaultValue);
            if (findedItem == null) {
                if(managersCopy.findItemByName("")==null ||
                   managersCopy.findItemByValue("")==null) {
                    DtoComboItem item = managersCopy.insertElementAt(
                        defaultName,
                        defaultValue, 0);
                    managersCopy.setSelectedItem(item);
                } else {
                    DtoComboItem item = managersCopy.insertElementAt(
                        defaultName,
                        defaultValue, 1);
                    managersCopy.setSelectedItem(item);
                }

            } else {
                managersCopy.setSelectedItem(findedItem);
            }
            comboBox.setModel(managersCopy);
        } else if (defaultValue != null) {
            VMComboBox managersCopy = new VMComboBox();
            DtoComboItem item = managersCopy.addElement(defaultName,
                    defaultValue);
            managersCopy.setSelectedItem(item);
            comboBox.setModel(managersCopy);
        } else if (model != null) {
            VMComboBox managersCopy = (VMComboBox) DtoUtil.deepClone(model);
            comboBox.setModel(managersCopy);
        }
    }

    public static void initComboBox(VWJComboBox comboBox, VMComboBox model,
                                    String defaultName, Object defaultValue,
                                    Object defaultRelation) {
        if (model != null && defaultValue != null) {
            VMComboBox managersCopy = (VMComboBox) DtoUtil.deepClone(model);
            Object findedItem=managersCopy.findItemByValue(defaultValue);
            if (findedItem == null) {
                if(managersCopy.findItemByName("")==null ||
                   managersCopy.findItemByValue("")==null) {
                    DtoComboItem item = managersCopy.insertElementAt(
                        defaultName,
                        defaultValue, 0);
                    managersCopy.setSelectedItem(item);
                } else {
                    DtoComboItem item = managersCopy.insertElementAt(
                        defaultName,
                        defaultValue, 1);
                    managersCopy.setSelectedItem(item);
                }
            } else {
                managersCopy.setSelectedItem(findedItem);
            }
            comboBox.setModel(managersCopy);
        } else if (defaultValue != null) {
            VMComboBox managersCopy = new VMComboBox();
            DtoComboItem item = managersCopy.addElement(defaultName,
                    defaultValue, defaultRelation);
            managersCopy.setSelectedItem(item);
            comboBox.setModel(managersCopy);
        } else if (model != null) {
            VMComboBox managersCopy = (VMComboBox) DtoUtil.deepClone(model);
            comboBox.setModel(managersCopy);
        }
    }
}
