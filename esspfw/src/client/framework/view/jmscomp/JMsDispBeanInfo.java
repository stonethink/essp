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

public class JMsDispBeanInfo extends SimpleBeanInfo {
	Class beanClass = JMsDisp.class;
	String iconColor16x16Filename = "Disp32.GIF";
	String iconColor32x32Filename = "Disp32.GIF";
	String iconMono16x16Filename;
	String iconMono32x32Filename;

	public JMsDispBeanInfo() {
	}
	public PropertyDescriptor[] getPropertyDescriptors() {
		PropertyDescriptor[] shiitake = new PropertyDescriptor[] { };
		return shiitake

;
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
	public BeanInfo[] getAdditionalBeanInfo() {
		Class superclass = beanClass.getSuperclass();
		try {
			BeanInfo superBeanInfo = Introspector.getBeanInfo(superclass);
			return new BeanInfo[] { superBeanInfo };
		}
		catch(IntrospectionException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}