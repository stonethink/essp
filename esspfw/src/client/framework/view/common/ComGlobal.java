package client.framework.view.common;

import java.awt.*;

public class ComGlobal
{
	public static	String		g_sTermID		 = null; // �[���h�c
	public static	String		g_sMessage		 = null;
	public static	java.awt.Frame	g_clsFrame		 = null;
	public static	java.awt.Panel	g_clsCurrentPanel	 = null;
	public static	java.awt.Panel	gClsCurrentPanelNative = null;
	public static	String		g_sMenuID		 = null; // ���݁A�I�𒆂̃��j���[�h�c

	//++********************************
	//	�_�C�A���O���
	//--********************************
	public static	Dialog	g_clsDialogInput		= null;	// ���̓T�u��ʃ_�C�A���O
	public static	Dialog	g_clsDialogMsg			= null;	// ���b�Z�[�W�_�C�A���O
	public static	Dialog	g_clsDialogFaitalError		= null;	// �p���s�ʒm�_�C�A���O
	public static	Dialog	g_clsDialogFaitalErrorServer	= null;	// �ʐM�p���s�ʒm�_�C�A���O
	public static	Dialog	g_clsDialogCalender 		= null;	// �J�����_�[�_�C�A���O
        public static	Dialog	g_clsDialogCodeSeek		= null;	// �R�[�h�����_�C�A���O
	public static	Dialog	g_clsDialogSubForm		= null;	// �T�u��ʃ_�C�A���O
	public static	Dialog	gClsDialogSubForm2		= null;	// �T�u��ʃ_�C�A���O�Q
	public static	Dialog	gClsDialogSubForm3		= null;	// �T�u��ʃ_�C�A���O�R
	public static	Dialog	g_clsDialogProcessYN		= null;	// �������_�C�A���O�Q
	public static	String	g_sProcessYN			= "";	// �������_�C�A���O�Q
	public static	Dialog	g_clsDialogProcess		= null;	// �������_�C�A���O�Q
	public static	Dialog	g_clsDialogPrint		= null;	// ����_�C�A���O


	//++********************************
	//	���͎ҏ��
	//--********************************
	public static	String	g_sOPDATE			= "";	// ������
	public static	String	g_sOPDATEYM			= "";	// ���N�����N��
	public static	String	gSOperFormId			= "";	// �t�H�[���h�c

        //add by yery
        public static   String          g_sBATOPDATE        = "";       //  �o�b�`�����N��
        public static   String          g_sKKKYM            = "";       //  ���|�����N��
        //�o�b�`�p
        public static   String          gSBatId               = "";       //  �o�b�`�v���O����ID
        public static   String          gSBatTanCd            = "";       //  �o�b�`�錾�S����
        public static   String          gSBatStartDate        = "";       //  �o�b�`�J�n��
        public static   String          gSBatStartTime        = "";       //  �o�b�`�J�n����

}
