
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

public class InputMode extends PropertyEditorSupport {

	public InputMode() {
	}
	private static String[] tagStrings = { "�w��Ȃ�", "�Ђ炪��", "���p�J�i", "�S�p�p��", "���ړ���", };
	public String[] getTags() {
		return tagStrings;
	}
	public String getJavaInitializationString() {
		return "\"" + getAsText() + "\"";
	}
	public void setAsText(String text) throws IllegalArgumentException {
		setValue(text);
	}
}