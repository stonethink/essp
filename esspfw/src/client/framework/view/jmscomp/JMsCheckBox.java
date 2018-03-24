package client.framework.view.jmscomp;

import java.awt.*;
import javax.swing.JCheckBox;
import javax.swing.BorderFactory;
import java.awt.event.*;
import client.framework.view.common.*;

/**
 * <p>�^�C�g��: JMsComp </p>
 * <p>����: Javax.Swing�p���̃I���W�i���R���|�[�l���g�Q</p>
 * <p>���쌠: milestone Copyright (c) 2002</p>
 * <p>��Ж�: �}�C���X�g�[���������</p>
 * @author ������
 * @version 1.0
 */

public class JMsCheckBox extends JCheckBox {
	BorderLayout borderLayout1 = new BorderLayout();
    private boolean keyType;
    private boolean  pbSelect_Save = false;
	public JMsCheckBox() {
		try {
			jbInit();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			initBeanUser();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

	}


	/**
	 *<BR>
	 *�@�^�C�v�@�F�@������<BR>
	 *�@�������@�F�@�����l�ݒ菈��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	private void jbInit() throws Exception {
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				this_keyPressed(e);
			}
		});
		this.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(FocusEvent e) {
				this_focusGained(e);
			}
			public void focusLost(FocusEvent e) {
				this_focusLost(e);
			}
		});
		this.setLayout(borderLayout1);
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@������<BR>
	 *�@�������@�F�@���[�U�����l�ݒ菈��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	private void initBeanUser() throws Exception {

		//++****************************
		//	�������ݒ�
		//--****************************
		setFont( DefaultComp.CHECK_BOX_FONT );
	}

	void this_focusGained(FocusEvent e) {
		setBackground( DefaultComp.BACKGROUND_COLOR_INPUT_ACTIVE );
	}

	void this_focusLost(FocusEvent e) {
		setBackground( DefaultComp.BACKGROUND_COLOR_ENABLED );
	}

        /**
         *<BR>
         *�@�^�C�v�@�F�@�C�x���g<BR>
         *�@�������@�F�@�L�[�������̏���<BR>
         *�@���@�l�@�F�@Enter�L�[�����Ńt�B�[���h�ړ�<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
         *   00.01   2004/11/30  Yery        Enter�C�x���g�̏������폜
         *<BR>
         */
	void this_keyPressed(KeyEvent e) {
          //������ENTER�C�x���g�Ŗ��𔭐�����̂ŁA�폜����͂��ł��BcomFORM.setEnterOrder()���Q�Ƃ�������
//		if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
//			this.transferFocus();
//		}

	}
    public void setKeyType(boolean keyType) {
        this.keyType = keyType;
    }
    public boolean isKeyType() {
        return keyType;
    }

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[�֐�<BR>
	 *�@�������@�F�@�L�[���ڕύX�`�F�b�N���\�b�h<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2003/03/24�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public boolean checkModified(
	) {
		boolean		bState;

		bState	= isSelected();
		if ( pbSelect_Save != bState ) {
			return true;
		} else {
            return false;
        }
	}

	public void clearModified(
	) {
		pbSelect_Save	= isSelected();
	}

}
