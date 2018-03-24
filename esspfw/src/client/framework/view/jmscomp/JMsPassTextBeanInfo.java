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

public class JMsPassTextBeanInfo extends SimpleBeanInfo {
	Class beanClass = JMsPassText.class;
	String iconColor16x16Filename = "Text32.gif";
	String iconColor32x32Filename = "Text32.gif";
	String iconMono16x16Filename;
	String iconMono32x32Filename;

	public JMsPassTextBeanInfo() {
	}
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			PropertyDescriptor _enabled = new PropertyDescriptor("enabled", beanClass, null, "setEnabled");
			PropertyDescriptor _errorField = new PropertyDescriptor("errorField", beanClass, null, "setErrorField");
			PropertyDescriptor _field_Error = new PropertyDescriptor("field_Error", beanClass, "getField_Error", "setField_Error");
			PropertyDescriptor _maxByteLength = new PropertyDescriptor("maxByteLength", beanClass, "getMaxByteLength", "setMaxByteLength");
			_maxByteLength.setDisplayName("MaxByteLength");
			_maxByteLength.setShortDescription("�ő���͌�����ݒ�");
			PropertyDescriptor[] shiitake = new PropertyDescriptor[] {
				_enabled,
				_errorField,
				_field_Error,
				_maxByteLength};
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