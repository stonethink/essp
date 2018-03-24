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

public class JMsTextBeanInfo extends SimpleBeanInfo {
    Class beanClass = JMsText.class;
    String iconColor16x16Filename = "Text32.gif";
    String iconColor32x32Filename = "Text32.gif";
    String iconMono16x16Filename;
    String iconMono32x32Filename;

    public JMsTextBeanInfo() {
    }
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor _convUpperCase = new PropertyDescriptor("convUpperCase", beanClass, "isConvUpperCase", "setConvUpperCase");
            _convUpperCase.setDisplayName("ConvUpperCase");
            _convUpperCase.setShortDescription("カーソルを失ったタイミングで表示文字を大文字に変換するかを設定します。");
            PropertyDescriptor _enabled = new PropertyDescriptor("enabled", beanClass, null, "setEnabled");
            PropertyDescriptor _errorField = new PropertyDescriptor("errorField", beanClass, null, "setErrorField");
            PropertyDescriptor _field_Error = new PropertyDescriptor("field_Error", beanClass, "getField_Error", "setField_Error");
            PropertyDescriptor _input2ByteOk = new PropertyDescriptor("input2ByteOk", beanClass, "isInput2ByteOk", "setInput2ByteOk");
            _input2ByteOk.setDisplayName("Input2ByteOk");
            _input2ByteOk.setShortDescription("Input2ByteOk");
            PropertyDescriptor _inputChar = new PropertyDescriptor("inputChar", beanClass, "getInputChar", "setInputChar");
            _inputChar.setDisplayName("InputChar");
            _inputChar.setShortDescription("InputChar");
            _inputChar.setPropertyEditorClass(client.framework.view.jmscomp.InputCharText.class);
            PropertyDescriptor _inputMode = new PropertyDescriptor("inputMode", beanClass, "getInputMode", "setInputMode");
            _inputMode.setDisplayName("InputMode");
            _inputMode.setShortDescription("カーソル時のデフォルトIMEを設定します。");
            _inputMode.setPropertyEditorClass(client.framework.view.jmscomp.InputMode.class);
            PropertyDescriptor _keyType = new PropertyDescriptor("keyType", beanClass, "isKeyType", "setKeyType");
            _keyType.setDisplayName("KeyType");
            _keyType.setShortDescription("キー項目の場合trueをセット");
            PropertyDescriptor _maxByteLength = new PropertyDescriptor("maxByteLength", beanClass, "getMaxByteLength", "setMaxByteLength");
            _maxByteLength.setDisplayName("MaxByteLength");
            _maxByteLength.setShortDescription("MaxByteLength");
            PropertyDescriptor _maxNextFoucs = new PropertyDescriptor("maxNextFoucs", beanClass, "isMaxNextFoucs", "setMaxNextFoucs");
            _maxNextFoucs.setDisplayName("maxNextFoucs");
            _maxNextFoucs.setShortDescription("桁数入力された際に次のフォーカスに移動する");
            PropertyDescriptor _text = new PropertyDescriptor("text", beanClass, null, "setText");
            PropertyDescriptor[] shiitake = new PropertyDescriptor[] {
	            _convUpperCase,
	            _enabled,
	            _errorField,
	            _field_Error,
	            _input2ByteOk,
	            _inputChar,
	            _inputMode,
	            _keyType,
	            _maxByteLength,
	            _maxNextFoucs,
	            _text};
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
