package c2s.dto;

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
public class DtoComboItem implements Serializable {
    private String itemName;
    private Object itemValue;
    private Object itemRelation;
    private Class valueType = String.class;
    private boolean bNeVType = true;

    public DtoComboItem() {
        this.itemName = "";
        this.itemValue = null;
    }

    public DtoComboItem(Object itemValue) {
        //this.itemName = itemName;
        //this.itemValue = itemValue;
        this.bNeVType = true;
        if (itemValue != null) {
            this.valueType = itemValue.getClass();
        }
        setItemValue(itemValue);
        this.itemRelation = itemValue;
    }

    public DtoComboItem(String itemName, Object itemValue) {
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.itemRelation = itemValue;
        if (itemValue != null) {
            this.valueType = itemValue.getClass();
        }
        this.bNeVType = false;
    }

    public DtoComboItem(String itemName, Object itemValue, Object itemRelation) {
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.itemRelation = itemRelation;
        if (itemValue != null) {
            this.valueType = itemValue.getClass();
        }
        this.bNeVType = false;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
        if (bNeVType) {
            try {
                this.itemValue = ConvDataType.toOtherType(itemName, valueType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setItemValue(Object itemValue) {
        this.itemValue = itemValue;
        if (itemValue != null) {
            this.valueType = itemValue.getClass();
        }
        if (bNeVType) {
            try {
                this.itemName = itemValue.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getItemName() {
        return itemName;
    }

    public Object getItemValue() {
        return itemValue;
    }

    public void setNeVType(boolean bNeVType) {
        this.bNeVType = bNeVType;
    }

    public boolean getNeVType() {
        return this.bNeVType;
    }

    public String toString() {
        return this.itemName;
    }

    public boolean equals(Object other) {
        if (! (other instanceof DtoComboItem)) {
            return false;
        }
        DtoComboItem castOther = (DtoComboItem) other;

        boolean bRtn = false;
        if (getItemValue() == null) {
            if (castOther.getItemValue() == null) {
                return true;
            }
        } else {
            bRtn = getItemValue().equals(castOther.getItemValue());
        }
        return bRtn;
    }

    public boolean equalsName(String otherName) {
        boolean bRtn = false;
        if (getItemName() == null) {
            if (otherName == null) {
                return true;
            }
        } else {
            bRtn = getItemName().equals(otherName);
        }
        return bRtn;
    }

    public boolean equalsValue(Object other) {
        boolean bRtn = false;
        if (getItemValue() == null) {
            if (other == null) {
                return true;
            }
        } else {
            bRtn = getItemValue().equals(other);
        }
        return bRtn;
    }

    public boolean equalsRelation(Object other) {
        boolean bRtn = false;
        if (getItemRelation() == null) {
            if (other == null) {
                return true;
            }
        } else {
            bRtn = getItemRelation().equals(other);
        }
        return bRtn;
    }

    public Object getItemRelation() {
        return itemRelation;
    }

    public void setItemRelation(Object itemRelation) {
        this.itemRelation = itemRelation;
    }

}
