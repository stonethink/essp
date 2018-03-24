package client.framework.view.jmscomp;

import java.awt.*;
import client.framework.view.common.*;

/**
 * <p>�^�C�g��: JMsComp </p>
 * <p>����: Javax.Swing�p���̃I���W�i���R���|�[�l���g�Q</p>
 * <p>���쌠: milestone Copyright (c) 2002</p>
 * <p>��Ж�: �}�C���X�g�[���������</p>
 * @author ������
 * @version 1.0
 */

public class JMsDisp extends JMsText {
	BorderLayout borderLayout1 = new BorderLayout();

	public JMsDisp() {
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
		//	��Ԑݒ�
		//--****************************
		setEnabled ( false );

		//++****************************
		//	�������ݒ�
		//--****************************
		setFont( DefaultComp.DISP_DATA_FONT );

		//++****************************
		//	�F�ݒ�
		//--****************************
		setBackground ( DefaultComp.DISP_DATA_BACKGROUND_COLOR );
		this.setDisabledTextColor ( DefaultComp.DISP_DATA_INACT_FOREGROUND_COLOR );
   }

   /**
    *<BR>
    *�@�^�C�v�@�F�@<BR>
    *�@�������@�F�@�ő�o�C�g�ɂ��A���̕\���������I�ɐ؂��܂��B<BR>
    *�@���@�l�@�F�@<BR>
    *<BR>
    *�@�ύX����<BR>
    *<BR>
    *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
    *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
    *�@�@00.00�@�@2004/12/17�@Yery �@�@�V�K�쐬<BR>
    *<BR>
    */
   public void setText(String text) {
     String sNewText="";
     if(text==null) {
       sNewText="";
     }

     if(text != null && text.equals("")==false) {
       int iMaxLength = this.getMaxByteLength();
       if(iMaxLength>0) {
         byte[] bText = text.getBytes();
         if (bText.length > iMaxLength) {
           sNewText = new String(bText, 0, iMaxLength);
         }
         else {
           sNewText = text;
         }
       } else {
         sNewText = text;
       }
     }

     super.setText(sNewText);
   }

}
