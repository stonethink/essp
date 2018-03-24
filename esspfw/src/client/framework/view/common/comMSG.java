package client.framework.view.common;

import java.awt.*;
import client.framework.view.jmscomp.*;
import javax.swing.JOptionPane;
import client.framework.common.Constant;
import javax.swing.JFrame;
import client.framework.common.Global;

/**
 *<BR>
 *�@�T�v<BR>
 *<BR>
 *�@�@���ʃ��W���[���i���b�Z�[�W�֘A�j<BR>
 *<BR>
 *�@�ύX����<BR>
 *<BR>
 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
 *�@�@00.00�@�@2002/06/11�@�󗈁@�K�O�@�@�V�K�쐬<BR>
 *<BR>
 */
public class comMSG
{
	private static Color dlgSeekColor = new Color( 255, 255, 196 );
	private static Color dlgMsgColor  = new Color( 255, 255, 255 );

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�m�F��ʕ\��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/11�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param   prm_sMsg�@�@IN�@�F�@�\�����b�Z�[�W
	 * @return  1�@�F�@�n�j<BR>���̑��@�F�@�L�����Z��
	 */
	public static int dispConfirmDialog( String prm_sMsg ) {
//		dlgOKCancel	clsDialog;
//		Frame		clsFrame;
		int		iRet=0;

//		ComGlobal.g_clsCurrentPanel = null;
//
//		//++****************************
//		//	�_�C�A���O�\����Ԃɐݒ�
//		//--****************************
//		ComGlobal.g_clsCurrentPanel = ComGlobal.gClsCurrentPanelNative;
//		clsFrame = comFORM.getBaseFrame();
//		clsDialog = new dlgOKCancel( clsFrame, "", true, prm_sMsg );
//		clsDialog.getContentPane().setBackground( dlgMsgColor );
//		ComGlobal.g_clsDialogMsg = clsDialog;
//
//		//++****************************
//		//	�_�C�A���O��\��
//		//--****************************
//		try {
//			iRet = clsDialog.showDialog();
//		} finally {
//			if ( clsDialog.isVisible() == true ) {
//				clsDialog.dispose();
//			}
//		}

                int result=JOptionPane.showConfirmDialog(getParentWindow(),prm_sMsg,"Confirm Message",JOptionPane.OK_CANCEL_OPTION );
                if(result==JOptionPane.OK_OPTION ) {
                    iRet=Constant.OK;
                } else {
                    iRet=Constant.CANCEL;
                }

		return iRet;
	}

        /**
         *
         * @param prm_sMsg String
         * @return int Constant.CANCEL : Cancel
         *             Constant.ALWAYS_CANCEL : Cancel AlWays
         *             Constant.OK : OK
         *             Constant.ALWAYS_OK : OK Always
         *
         */
        public static int dispConfirmDialogByPrompt( String prm_sMsg) {
            dlgOKCancel	clsDialog;
            Frame		clsFrame;
            int		iRet;

            ComGlobal.g_clsCurrentPanel = null;

            //++****************************
            //	�_�C�A���O�\����Ԃɐݒ�
            //--****************************
            ComGlobal.g_clsCurrentPanel = ComGlobal.gClsCurrentPanelNative;
            clsFrame = comFORM.getBaseFrame();
            clsDialog = new dlgOKCancel( clsFrame, "Prompt Message", true, prm_sMsg,false );
            clsDialog.getContentPane().setBackground( dlgMsgColor );
            ComGlobal.g_clsDialogMsg = clsDialog;

            //++****************************
            //	�_�C�A���O��\��
            //--****************************
            try {
                iRet = clsDialog.showDialog();

            } finally {
                if ( clsDialog.isVisible() == true ) {
                    clsDialog.dispose();
                }
            }
            return iRet;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�m�F��ʕ\��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/11�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param   prm_sMsg�@�@IN�@�F�@�\�����b�Z�[�W
	 * @return  1�@�F�@�n�j<BR>���̑��@�F�@�L�����Z��
	 */
	public static int dispDialogProcessYN( String prm_sMsg ) {
//		dlgProcessYN	clsDialog;
//		Frame		clsFrame;
		int		iRet=0;

//		ComGlobal.g_clsCurrentPanel = null;
//
//		//++****************************
//		//	�_�C�A���O�\����Ԃɐݒ�
//		//--****************************
//		ComGlobal.g_clsCurrentPanel = ComGlobal.gClsCurrentPanelNative;
//		clsFrame  = comFORM.getBaseFrame();
//		clsDialog = new dlgProcessYN( clsFrame, "", true, prm_sMsg );
//		clsDialog.getContentPane().setBackground( dlgMsgColor );
//		ComGlobal.g_clsDialogProcessYN = clsDialog;
//
//		//++****************************
//		//	�_�C�A���O��\��
//		//--****************************
//		try {
//			iRet = clsDialog.showDialog();
//		} finally {
//		}

                int result=JOptionPane.showConfirmDialog(null,prm_sMsg,"",JOptionPane.YES_NO_OPTION );
                if(result==JOptionPane.YES_OPTION ) {
                    iRet=Constant.OK;
                } else {
                    iRet=Constant.CANCEL;
                }
		return iRet;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�G���[��ʕ\��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/11�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param   prm_sMsg�@�@�@IN�@�F�@�\�����b�Z�[�W
	 * @param   prm_clsExcept�@IN�@�F�@��O�N���X
	 * @return  �Ȃ�
	 */
	public static void dispErrorDialog( String prm_sMsg, Exception prm_clsExcept ) {
		dispErrorDialog( prm_sMsg + "\n\n" + prm_clsExcept.toString() );
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�G���[��ʕ\��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/11�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param   prm_sMsg�@�@IN�@�F�@�\�����b�Z�[�W
	 * @return  �Ȃ�
	 */
	public static void dispErrorDialog( String prm_sMsg ) {
//		dlgOK		clsDialog;
//		Frame		clsFrame;
//
//		ComGlobal.g_clsCurrentPanel = null;
//		//++****************************
//		//	�_�C�A���O�\����Ԃɐݒ�
//		//--****************************
//		ComGlobal.g_clsCurrentPanel = ComGlobal.gClsCurrentPanelNative;
//		clsFrame = comFORM.getBaseFrame();
//		clsDialog = new dlgOK( clsFrame, true, "������Ϣ", prm_sMsg );
//		clsDialog.getContentPane().setBackground( dlgMsgColor );
//		ComGlobal.g_clsDialogMsg = clsDialog;
//
//		//++****************************
//		//	�_�C�A���O��\��
//		//--****************************
//		try {
//			clsDialog.showDialog();
//		} catch(Exception e) {
//			e.printStackTrace();
//			return;
// 		} finally {
//			if ( clsDialog.isVisible() == true ) {
//				clsDialog.dispose();
//			}
//		}
//		return;

                JOptionPane.showMessageDialog(getParentWindow(), prm_sMsg, "Error Message",
                                                  JOptionPane.ERROR_MESSAGE);
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�p���s�\�ȃG���[��ʃ_�C�A���O�\��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.01�@�@2002/07/15�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	prm_sErrInfoFormID�@�@IN�@�F�@��ʂh�c
	 * @param	prm_sErrInfoModuleID�@IN�@�F�@���W���[���h�c
	 * @param	prm_sErrInfoIF�@�@�@�@IN�@�F�@�h�e��
	 * @param	prm_sErrInfoOperatio�@IN�@�F�@�I�y���[�V������
	 * @param	prm_sRetInfoMessage�@IN�@�F�@���b�Z�[�W
	 * @return  �Ȃ�
	 */
	public static void dispFaitalDialog(
		String		prm_sErrInfoFormID,
		String		prm_sErrInfoModuleID,
		String		prm_sErrInfoIF,
		String		prm_sErrInfoOperatio,
		String		prm_sInfoMessage,
		Exception	prm_clsExcept
	) {
		dlgFaitalError	clsDialog;
		Frame		clsFrame;

		ComGlobal.g_clsCurrentPanel = null;

		//++****************************
		//	�_�C�A���O�\����Ԃɐݒ�
		//--****************************
		ComGlobal.g_clsCurrentPanel = ComGlobal.gClsCurrentPanelNative;
		clsFrame  = comFORM.getBaseFrame();
		clsDialog = new dlgFaitalError(	clsFrame,
						"�V�X�e���G���[",
						true,
						prm_sErrInfoFormID,
						prm_sErrInfoModuleID,
						prm_sErrInfoIF,
						prm_sErrInfoOperatio,
						prm_sInfoMessage,
						prm_clsExcept
						);
		ComGlobal.g_clsDialogFaitalError = clsDialog;
		clsDialog.getContentPane().setBackground( DefaultComp.BACKGROUND_COLOR_PANEL );

		//++****************************
		//	�_�C�A���O��\��
		//--****************************
		try {
			clsDialog.showDialog();
		} finally {
			if ( clsDialog.isVisible() == true ) {
				clsDialog.dispose();
			}
		}
		return;
	}



	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�J�����_�[�_�C�A���O�\��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.01�@�@2002/07/17�@���Y�@���F�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	prm_sErrInfoFormID�@�@IN�@�F�@��ʂh�c
	 * @param	prm_sErrInfoModuleID�@IN�@�F�@���W���[���h�c
	 * @param	prm_sErrInfoIF�@�@�@�@IN�@�F�@�h�e��
	 * @param	prm_sErrInfoOperatio�@IN�@�F�@�I�y���[�V������
	 * @param	prm_sRetInfoMessage�@IN�@�F�@���b�Z�[�W
	 * @return  �Ȃ�
	 */
	public static void dispCalenderDialog( JMsDate prm_datecomp ) {
		dlgClarender	clsDialog;
		Frame		clsFrame;
		String		sReturnDate = "";

		//++****************************
		//	�g�p�s�̏ꍇ�����I��
		//--****************************
		if ( prm_datecomp.isEnabled() == false ) {
			return;
		}

		ComGlobal.g_clsCurrentPanel = null;

		//++****************************
		//	�_�C�A���O�\����Ԃɐݒ�
		//--****************************
		ComGlobal.g_clsCurrentPanel = ComGlobal.gClsCurrentPanelNative;
		clsFrame  = comFORM.getBaseFrame();
		clsDialog = new dlgClarender(clsFrame, "Calender", true);
		clsDialog.getContentPane().setBackground( dlgSeekColor );
		ComGlobal.g_clsDialogCalender = clsDialog;

		//++****************************
		//	�_�C�A���O��\��
		//--****************************
		try {
			sReturnDate = clsDialog.showDialog( prm_datecomp );
		} finally {
			if ( clsDialog.isVisible() == true ) {
				clsDialog.dispose();
			}
		}

		prm_datecomp.setText(sReturnDate);
		comFORM.setFocus( prm_datecomp );
		return;
	}

        public static void dispMessageDialog( Component comp, String prm_sMsg ) {
            JOptionPane.showMessageDialog(comp,prm_sMsg);
        }

        public static void dispMessageDialog( String prm_sMsg ) {
            JOptionPane.showMessageDialog(getParentWindow(),prm_sMsg);
        }

        static Component getParentWindow(){
            if( Global.applet != null ){
                return Global.applet;
            }else{
                return null;
            }
        }

        public static void main(String[] args) {
            dispConfirmDialog("hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello\nhellohellohellohello\n");
        }
}
