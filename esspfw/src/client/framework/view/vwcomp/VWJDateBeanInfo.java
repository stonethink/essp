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
public class VWJDateBeanInfo extends SimpleBeanInfo {
    Class beanClass = VWJDate.class;
    String iconColor16x16Filename;
    String iconColor32x32Filename = "Date32.gif";
    String iconMono16x16Filename;
    String iconMono32x32Filename = "Date32.gif";

    public VWJDateBeanInfo() {
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor _BReshap = new PropertyDescriptor(
                "BReshap", beanClass, "isBReshap", "setBReshap");

            PropertyDescriptor _canSelect = new PropertyDescriptor(
                "canSelect", beanClass, "isCanSelect", "setCanSelect");

            PropertyDescriptor _dtoClass = new PropertyDescriptor(
                "dtoClass", beanClass, "getDtoClass", "setDtoClass");

            PropertyDescriptor _editing = new PropertyDescriptor(
                "editing", beanClass, "isEditing", null);

            PropertyDescriptor _offset = new PropertyDescriptor(
                "offset", beanClass, "getOffset", "setOffset");

            PropertyDescriptor _UICValue = new PropertyDescriptor(
                "UICValue", beanClass, "getUICValue", "setUICValue");

            PropertyDescriptor _validator = new PropertyDescriptor(
                "validator", beanClass, null, "setValidator");

            PropertyDescriptor _validatorResult = new PropertyDescriptor(
                "validatorResult", beanClass, "getValidatorResult", null);

            PropertyDescriptor _valueToDto = new PropertyDescriptor(
                "valueToDto", beanClass, null, "setValueToDto");

            PropertyDescriptor[] pds = new PropertyDescriptor[] {
                                       _BReshap,
                                       _canSelect,
                                       _dtoClass,
                                       _editing,
                                       _offset,
                                       _UICValue,
                                       _validator,
                                       _validatorResult,
                                       _valueToDto
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
