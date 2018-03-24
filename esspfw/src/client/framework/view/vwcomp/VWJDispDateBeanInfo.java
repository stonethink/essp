package client.framework.view.vwcomp;

import java.awt.Image;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * <p>Title: </p>
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
public class VWJDispDateBeanInfo extends SimpleBeanInfo {
    Class beanClass = VWJDispDate.class;
    String iconColor16x16Filename;
    String iconColor32x32Filename = "DispDate32.GIF";
    String iconMono16x16Filename;
    String iconMono32x32Filename = "DispDate32.GIF";

    public VWJDispDateBeanInfo() {
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor _errorField = new PropertyDescriptor(
                "errorField", beanClass, null, "setErrorField");

            PropertyDescriptor[] pds = new PropertyDescriptor[] {
                                       _errorField
            };
            return pds;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Image getIcon(int iconKind) {
        switch (iconKind) {
        case BeanInfo.ICON_COLOR_16x16:
            return ((iconColor16x16Filename != null)
                    ? loadImage(iconColor16x16Filename) : null);

        case BeanInfo.ICON_COLOR_32x32:
            return ((iconColor32x32Filename != null)
                    ? loadImage(iconColor32x32Filename) : null);

        case BeanInfo.ICON_MONO_16x16:
            return ((iconMono16x16Filename != null)
                    ? loadImage(iconMono16x16Filename) : null);

        case BeanInfo.ICON_MONO_32x32:
            return ((iconMono32x32Filename != null)
                    ? loadImage(iconMono32x32Filename) : null);
        }

        return null;
    }
}
