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
public class VWJLabelBeanInfo extends SimpleBeanInfo {
    Class beanClass = VWJLabel.class;
    String iconColor16x16Filename;
    String iconColor32x32Filename = "Label32.gif";
    String iconMono16x16Filename;
    String iconMono32x32Filename = "Label32.gif";

    public VWJLabelBeanInfo() {
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        PropertyDescriptor[] pds = new PropertyDescriptor[] {};
        return pds;
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
