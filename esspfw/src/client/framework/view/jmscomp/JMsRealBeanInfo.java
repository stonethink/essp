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

public class JMsRealBeanInfo extends SimpleBeanInfo {
	Class beanClass = JMsReal.class;
	String iconColor16x16Filename = "Doublee32.gif";
	String iconColor32x32Filename = "Doublee32.gif";
	String iconMono16x16Filename;
	String iconMono32x32Filename;

	public JMsRealBeanInfo() {
	}
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			PropertyDescriptor _enabled = new PropertyDescriptor("enabled", beanClass, null, "setEnabled");
			PropertyDescriptor _errorField = new PropertyDescriptor("errorField", beanClass, null, "setErrorField");
			PropertyDescriptor _field_Error = new PropertyDescriptor("field_Error", beanClass, "getField_Error", "setField_Error");
			PropertyDescriptor _maxInputDecimalDigit = new PropertyDescriptor("maxInputDecimalDigit", beanClass, "getMaxInputDecimalDigit", "setMaxInputDecimalDigit");
			PropertyDescriptor _maxInputIntegerDigit = new PropertyDescriptor("maxInputIntegerDigit", beanClass, "getMaxInputIntegerDigit", "setMaxInputIntegerDigit");
			PropertyDescriptor _text = new PropertyDescriptor("text", beanClass, null, "setText");
			PropertyDescriptor _text2 = new PropertyDescriptor("text2", beanClass, null, "setText2");
			PropertyDescriptor _value = new PropertyDescriptor("value", beanClass, "getValue", null);
//            PropertyDescriptor _value = new PropertyDescriptor("value", beanClass, null, "setValue");
			PropertyDescriptor[] shiitake = new PropertyDescriptor[] {
				_enabled,
				_errorField,
				_field_Error,
				_maxInputDecimalDigit,
				_maxInputIntegerDigit,
				_text,
				_text2,
				_value,
				_value};
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
