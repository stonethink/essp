package client.framework.model;

import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import c2s.dto.DtoComboItem;
import java.io.Serializable;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Wistron ITS Wuhan SDC</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class VMComboBox extends DefaultComboBoxModel implements  Serializable {
    private String defaultItemName = "-- select--";
    private Object defaultItemValue = null;
    Vector objectVector;

    public VMComboBox() {
        super();
        objectVector = new Vector();
    }

    public VMComboBox(Object[] itemValues) {
        super(itemValues);
        objectVector = new Vector();
        for (int i = 0; i < itemValues.length; i++) {
            addExistObject(itemValues[i]);
        }
    }

    public VMComboBox(Vector v) {
        super(v.toArray());
        objectVector = new Vector();
        for (int i = 0; i < v.size(); i++) {
            addExistObject(v.get(i));
        }
    }

    public DtoComboItem addNullElement() {
        return addNullElement(defaultItemName, defaultItemValue);
    }

    public DtoComboItem addNullElement(String name,Object value) {
        //DtoComboItem item = new DtoComboItem(defaultItemName, defaultItemValue, defaultItemValue);
        //super.insertElementAt(item, 0);
        //super.setSelectedItem(item);
        //return item;

        return this.insertElementAt(name, value, 0);
    }

    public DtoComboItem addElement(String name,Object value) {
        //DtoComboItem item = new DtoComboItem(name, value);
        //super.addElement(item);
        //return item;

        return this.insertElementAt(name, value, objectVector.size());
    }

    public DtoComboItem addElement(String name,Object value,Object relation) {
        //DtoComboItem item = new DtoComboItem(name, value,relation);
        //super.addElement(item);
        //return item;

        return this.insertElementAt(name, value, relation, this.getSize());
    }

    public DtoComboItem insertElementAt(String name,Object value,int index) {
        //DtoComboItem item = new DtoComboItem(name, value);
        //super.insertElementAt(item, index);
        //return item;

        return this.insertElementAt(name, value, value, index);
    }

    public DtoComboItem insertElementAt(String name,Object value,Object relation,int index) {
        DtoComboItem item = new DtoComboItem(name, value,relation);
        super.insertElementAt(item, index);

        addExistObject(item, index);
        return item;
    }

    public DtoComboItem insertNotExistElementAt(String name,Object value,Object relation,int index) {
        DtoComboItem item = new DtoComboItem(name, value,relation);
        super.insertElementAt(item, index);

        return item;
    }

    private void addExistObject(Object object){
        addExistObject(object,objectVector.size());
    }

    private void addExistObject(Object object, int index){
        objectVector.insertElementAt(object,index);
    }


    public Vector getObjectVector(){
        return this.objectVector;
    }

    public Object findItemByName(Object target) {
        Object objValue = null;
        Object targetValue = null;

        for (int i = 0; i < getSize(); i++) {
            Object obj = getElementAt(i);

            if (obj != null) {
                if (obj instanceof DtoComboItem) {
                    objValue = ((DtoComboItem) obj).getItemName();
                } else {
                    objValue = obj;
                }
            }else{
                objValue = null;
            }

            if (target != null) {
                if (target instanceof DtoComboItem) {
                    targetValue = ((DtoComboItem) target).getItemName();
                } else {
                    targetValue = target;
                }
            }else{
                targetValue = null;
            }

            if (objValue != null) {
                if (objValue.equals(targetValue)) {
                    return obj;
                }
            } else {
                if (targetValue == null) {
                    return obj;
                }
            }
        }

        return null;

    }

    public Object findItemByValue(Object target) {
        Object objValue = null;
        Object targetValue = null;

        for (int i = 0; i < getSize(); i++) {
            Object obj = getElementAt(i);

            if (obj != null) {
                if (obj instanceof DtoComboItem) {
                    objValue = ((DtoComboItem) obj).getItemValue();
                } else {
                    objValue = obj;
                }
            }else{
                objValue = null;
            }

            if (target != null) {
                if (target instanceof DtoComboItem) {
                    targetValue = ((DtoComboItem) target).getItemValue();
                } else {
                    targetValue = target;
                }
            }else{
                targetValue = null;
            }

            if (objValue != null) {
                if (objValue.equals(targetValue)) {
                    return obj;
                }
            } else {
                if (targetValue == null) {
                    return obj;
                }
            }
        }

        return null;
    }

    public static VMComboBox toVMComboBox(String[] aName) {
        return VMComboBox.toVMComboBox(aName, aName);
    }

    public static VMComboBox toVMComboBox(String[] aName, Object[] aValue) {
        return VMComboBox.toVMComboBox(aName, aValue, null);
    }

    public static VMComboBox toVMComboBox(String[] aName, Object[] aValue, Object relateValue) {
        VMComboBox vmComboBox = new VMComboBox();
        if (aName == null || aValue == null || aName.length != aValue.length) {
            return vmComboBox;
        }

        for (int i = 0; i < aName.length; i++) {
            vmComboBox.insertElementAt(aName[i], aValue[i], relateValue, i);
        }

        return vmComboBox;
    }

}
