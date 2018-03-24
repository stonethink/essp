package c2s.essp.common.menu;

import c2s.dto.*;

/**
 * <p>Title:描述菜单项 </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DtoMenuItem extends DtoBase {

    public DtoMenuItem() {
    }

    public String getDescription() {
        return description;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getFunctionID() {
        return functionID;
    }

    public String getAppContext() {
        return appContext;
    }

    public String getIcon() {
        return icon;
    }

    public String getIconDown() {
        return iconDown;
    }

    public String getIconOn() {
        return iconOn;
    }

    public boolean  isIsLeaf() {
        return isLeaf;
    }

    public boolean  isIsValid() {
        return isValid;
    }

    public boolean  isIsVisable() {
        return isVisable;
    }

    public int getLayer() {
        return layer;
    }

    public String getName() {
        return name;
    }

    public int getOrderNO() {
        return orderNO;
    }

    public String getType() {
        return type;
    }

    public String getFunctionAddress() {
        return functionAddress;
    }

    public boolean isSelect() {
        return select;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public void setFunctionID(String functionID) {
        this.functionID = functionID;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setIconDown(String iconDown) {
        this.iconDown = iconDown;
    }

    public void setIconOn(String iconOn) {
        this.iconOn = iconOn;
    }

    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public void setIsVisable(boolean isVisable) {
        this.isVisable = isVisable;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrderNO(int orderNO) {
        this.orderNO = orderNO;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAppContext(String appContext) {
        this.appContext = appContext;
    }

    public void setFunctionAddress(String functionAddress) {
        this.functionAddress = functionAddress;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getLinkAddress(){
        if("menu".equals(type))
            return ("/" + appContext + "/" + functionAddress);
        else if(functionAddress != null)
            return functionAddress;
        else
            return "";
    }
    private String functionID;
    private int layer;
    private int orderNO;
    private String name;
    private String fatherID;
    private boolean isValid;
    private boolean isLeaf;
    private String appContext;//应用所在的根目录
    private String functionAddress;
    private String type;
    private String icon;
    private String description;
    private boolean isVisable;
    private String iconOn;
    private String iconDown;
    private boolean select;

    public static final String SYSTEM_TYPE = "system";
    public static final String MENU_TYPE = "menu";
    public static final String JSCRIPT_TYPE = "js";
}
