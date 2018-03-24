package client.framework.view.jmscomp;

import java.beans.*;

/**
 * <p>�^�C�g��: JMsComp </p>
 * <p>����: Javax.Swing�p���̃I���W�i���R���|�[�l���g�Q</p>
 * <p>���쌠: milestone Copyright (c) 2002</p>
 * <p>��Ж�: �}�C���X�g�[���������</p>
 * @author ������
 * @version 1.0
 */

public class JMsRealBeanInfoBeanInfo extends SimpleBeanInfo {
	Class beanClass = JMsRealBeanInfo.class;
	String iconColor16x16Filename = "Doublee32.gif";
	String iconColor32x32Filename = "Doublee32.gif";
	String iconMono16x16Filename;
	String iconMono32x32Filename;

	public JMsRealBeanInfoBeanInfo() {
	}
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			PropertyDescriptor _propertyDescriptors = new PropertyDescriptor("propertyDescriptors", beanClass, "getPropertyDescriptors", null);
			PropertyDescriptor[] shiitake = new PropertyDescriptor[] {
				_propertyDescriptors};
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