package client.framework.view.jmscomp;

import java.beans.*;

/**
 * <p>タイトル: JMsComp </p>
 * <p>説明: Javax.Swing継承のオリジナルコンポーネント群</p>
 * <p>著作権: milestone Copyright (c) 2002</p>
 * <p>会社名: マイルストーン株式会社</p>
 * @author 未入力
 * @version 1.0
 */

public class JMsCheckBoxBeanInfo extends SimpleBeanInfo {
    Class beanClass = JMsCheckBox.class;
    String iconColor16x16Filename = "Check32.GIF";
    String iconColor32x32Filename = "Check32.GIF";
    String iconMono16x16Filename;
    String iconMono32x32Filename;

    public JMsCheckBoxBeanInfo() {
    }
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor _keyType = new PropertyDescriptor("keyType", beanClass, "isKeyType", "setKeyType");
            _keyType.setDisplayName("KeyType");
            _keyType.setShortDescription("キー項目の場合にtrueをセットする。");
            PropertyDescriptor[] shiitake = new PropertyDescriptor[] {
	            _keyType};
            return shiitake;
        
}
        catch(IntrospectionException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public java.awt.Image getIcon(int iconKind) {
        switch (iconKind) {
        case BeanInfo.ICON_COLOR_16x16:
              return iconColor16x16Filename != null ? loadImage(iconColor16x16Filename) : null;
        case BeanInfo.ICON_COLOR_32x32:
              return iconColor32x32Filename != null ? loadImage(iconColor32x32Filename) : null;
        case BeanInfo.ICON_MONO_16x16:
              return iconMono16x16Filename != null ? loadImage(iconMono16x16Filename) : null;
        case BeanInfo.ICON_MONO_32x32:
              return iconMono32x32Filename != null ? loadImage(iconMono32x32Filename) : null;
                                }
        return null;
    }
}