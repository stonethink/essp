package client.framework.view.common;

import java.net.URLEncoder;
import java.text.*;
import java.io.OutputStreamWriter;
import java.util.Vector;
import java.util.ResourceBundle;
import java.util.Locale;
import java.util.Enumeration;
import client.framework.view.jmscomp.*;


/**
 *<BR>
 *�@�T�v<BR>
 *<BR>
 *�@�@���ʃ��W���[���i�f�[�^�֘A�j<BR>
 *<BR>
 *�@�ύX����<BR>
 *<BR>
 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
 *�@�@00.00�@�@2004/06/21�@Tsukamoto�@�@�V�K�쐬<BR>
 *<BR>
 */
public class ComDATA {
       public static final String FILENAME="ftp";

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@����������^����<BR>
	 *�@���@�l�@�F�@�w�蕶���񂪐����^�����񂩂𔻒肷��B<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2004/06/21�@Tsukamoto�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	sParam�@�@IN�@�F�@���蕶����
	 * @return	true�@�F�@�n�j<BR>false�@�F�@�G���[
	 */
	public static boolean checkNumber( String sParam2 ) {
		Long	lNum;
		String sParam = sParam2.trim();

		try {
			lNum	= Long.valueOf( sParam );
		} catch ( Exception clsExcept ) {
			return false;
		}

		return true;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@����������^����<BR>
	 *�@���@�l�@�F�@�w�蕶���񂪎����^�����񂩂𔻒肷��B<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2004/06/21�@Tsukamoto�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	sParam�@�@IN�@�F�@���蕶����
	 * @return	true�@�F�@�n�j<BR>false�@�F�@�G���[
	 */
	public static boolean checkReal( String sParam2 ) {
		Double	dNum;
		String sParam = sParam2.trim();

		try {
			dNum	=  Double.valueOf( sParam );
		} catch ( Exception clsExcept ) {
			return false;
		}

		return true;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@����������<BR>
	 *�@���@�l�@�F�@�w�蕶���񂪎w�蕶�������𔻒肷��B<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2004/06/21�@Tsukamoto�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	sParam �@�@IN�@�F�@���蕶����
	 * @param	prm_iLength�@IN�@�F�@������
	 * @return	true�@�F�@�n�j<BR>false�@�F�@�G���[
	 */
	public static boolean checkMustLength( String sPara, int iLength ) {
		try {
			if ( sPara.trim().length() != iLength ) {
				return false;
			}
		} catch ( Exception clsExcept ) {
			return false;
		}

		return true;

	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�K�{���͔���<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2004/06/21�@Tsukamoto�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	sParam�@�@�@IN�@�F�@���蕶����
	 * @return	true�@�F�@�n�j<BR>false�@�F�@�G���[
	 */
	public static boolean checkMustInput( String sPara ) {
		try {
			if ( sPara.trim().length() == 0 ) {
				return false;
			} else {
				return true;
			}
		} catch ( Exception clsExcept ) {
			return false;
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�E�X�y�[�X�폜����<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2004/06/21�@Tsukamoto�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	sParam�@�@�@IN�@�F�@����������
	 * @return	�������ʕ�����
	 */
	public static String trimRight( String sParam ) {
		String	sStr;
		int	iLen;
		boolean	bNextFlag;

		sStr = sParam;

		try {
			bNextFlag	= true;
			while ( bNextFlag == true ) {
				bNextFlag	= false;
				iLen = sStr.length();
				if ( iLen > 0 ) {
					if (( sStr.lastIndexOf( " " )  == iLen - 1 )
					||  ( sStr.lastIndexOf( "�@" ) == iLen - 1 )
					) {
						bNextFlag	= true;
						sStr = sStr.substring( 0, iLen - 1 );
					}
				}
			}
			return sStr;
		} catch ( Exception clsExcept ) {
			return "";
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@���X�y�[�X�폜����<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2004/06/21�@Tsukamoto�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	sParam�@�@�@IN�@�F�@����������
	 * @return	�������ʕ�����
	 */
	public static String trimLeft( String sParam ) {
		String	sStr;
		boolean	bNextFlag;

		sStr = sParam;

		try {
			bNextFlag = true;
			while ( bNextFlag == true ) {
				bNextFlag = false;
				if (( sStr.indexOf( " " )  == 0 )
				||  ( sStr.indexOf( "�@" ) == 0 )
				) {
					bNextFlag = true;
					sStr = sStr.substring( 1 );
				}
			}
			return sStr;
		} catch ( Exception clsExcept ) {
			return "";
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@���l�f�[�^�[�����ߏ���<BR>
	 *�@���@�l�@�F�@���l�f�[�^���K�v�����ɖ����Ȃ��ꍇ������[�����߂���<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/07/19�@���Y�@���F�@�@�V�K�쐬<BR>
	 *�@�@00.00�@�@2003/01/06�@�󗈁@�K�O�@�@parmeter��String�ɕύX<BR>
	 *<BR>
	 * @param	iValue		:���l�f�[�^
	 * @param	beam		:�K�v����
	 * @return	sValue		:�[�����ߌ�f�[�^
	 */
	public static String  zeroFormat( String sValue2, int beam ){
		int iLength;
		String sValue=sValue2;

		iLength = sValue.length();
		for(int i = iLength; i < beam; i++){
			sValue = 0 + sValue;
		}

		return sValue;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@���l�f�[�^�[�����ߏ���<BR>
	 *�@���@�l�@�F�@���l�f�[�^���K�v�����ɖ����Ȃ��ꍇ������[�����߂���<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/07/19�@���Y�@���F�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	iValue		:���l�f�[�^
	 * @param	beam		:�K�v����
	 * @return	sValue		:�[�����ߌ�f�[�^
	 */
	public static String  zeroFormat( int iValue, int beam ){
		String sValue = new String();
		int iLength;

		sValue = String.valueOf(iValue);
		iLength = sValue.length();
		for(int i = iLength; i < beam; i++){
			sValue = 0 + sValue;
		}
		return sValue;
	}


	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@���l�f�[�^�[�����ߏ���<BR>
	 *�@���@�l�@�F�@���l�f�[�^���K�v�����ɖ����Ȃ��ꍇ������[�����߂���<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/07/19�@���Y�@���F�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	iValue		:���l�f�[�^
	 * @param	beam		:�K�v����
	 * @return	sValue		:�[�����ߌ�f�[�^
	 */
	public static String  zeroFormat( long lValue, int beam){
		String sValue = new String();
		int iLength;

		sValue = String.valueOf(lValue);
		iLength = sValue.length();
		for(int i = iLength; i < beam; i++){
			sValue = 0 + sValue;
		}
		return sValue;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�����f�[�^�X�y�[�X���ߏ���<BR>
	 *�@���@�l�@�F�@�����f�[�^���K�v�����ɖ����Ȃ��ꍇ�A�X�y�[�X���߂���<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2004/09/02�@����@�_�i�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	prm_sValue	:�����f�[�^
	 * @param	prm_ibeam	:�K�v����
	 * @return	sValue		:�X�y�[�X���ߌ�f�[�^
	 */

	public static String  spaceFormat( String sValue2, int ibeam ){
		int iLength;
		String sValue=sValue2;

		byte[] bValue = sValue.getBytes();
		iLength = bValue.length;
		for(int i = iLength; i < ibeam; i++){
			sValue = sValue + " ";
		}

		return sValue;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�R�[�h���͔��菈��<BR>
	 *�@���@�l�@�F�@�G���[�����b�Z�[�W��\�������A�Y���t�B�[���h�Ƀt�H�[�J�X��ݒ�<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/12/26�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	�Ȃ�
	 * @return	true�@�F�@�n�j<BR>false�@�F�@�G���[����
	 */
	public static boolean checkInput( JMsText jmsText, String msg ) {
		String	sCheckStr;	// ���蕶����
		int	iMaxLength;	// �ő啶����
		boolean	bRet;		// �߂�l

		//++************************
		//	��������
		//--************************
		sCheckStr	= jmsText.getText().trim();
		iMaxLength	= jmsText.getMaxByteLength();
		if ( sCheckStr.trim().length() > 0 ) {
			bRet = ComDATA.checkMustLength( sCheckStr, iMaxLength );
			if ( bRet == false ) {
				jmsText.setErrorField( true );
				comMSG.dispErrorDialog( msg + "�� " + String.valueOf( iMaxLength ) + " ���K�{���͂ł��B" );
				comFORM.setFocus( jmsText );
				return false;
			}
		}

		return true;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�R�[�h���͔��菈���i���t�j<BR>
	 *�@���@�l�@�F�@�G���[�����b�Z�[�W��\�������A�Y���t�B�[���h�Ƀt�H�[�J�X��ݒ�<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/12/26�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	�Ȃ�
	 * @return	true�@�F�@�n�j<BR>false�@�F�@�G���[����
	 */
	public static boolean checkInput( JMsDate jmsDate, String msg ) {
		int iRet ; //����p

		iRet = jmsDate.checkValue();
		if ( iRet < 0 ) {
			jmsDate.setErrorField( true );
			comMSG.dispErrorDialog( msg + "�̓��͒l���s���ł��B" );
			comFORM.setFocus( jmsDate );
			return false;
		}
		return true;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@name = value �����ݒ菈��<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/13�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	prm_name�@�@�@ IN�@�F�@name��
	 * @param	prm_value�@�@�@IN�@�F�@value��
	 * @param	prm_sep�@�@�@IN�@�F�@
	 * @return	prm_name=
	 */
	public static String encString(String name, String value ) {
		String sep;
		String enc = "SJIS";
		sep = "&";

		try {
			StringBuffer sb = new StringBuffer();
			sb.append( URLEncoder.encode(name, enc) + "=" + URLEncoder.encode(value,enc) + sep );
			return sb.toString();
		} catch(Exception e) {
			return "";
		}
	}


	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@name = value �����ݒ菈��<BR>
	 *�@���@�l�@�F�@�z��^�ŃN���C�A���g�T�[�o�ԒʐM���s���Ƃ��Ɏg�p
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/13�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	prm_name�@�@�@ IN�@�F�@name��
	 * @param	prm_value�@�@�@IN�@�F�@value��
	 * @param	prm_sep�@�@�@IN�@�F�@
	 * @return	prm_name=
	 */
	public static String encStringAry(String name, int count, String value ) {
		String sep;
		String enc = "SJIS";
		sep = "&";

		try {
			StringBuffer sb = new StringBuffer();
			sb.append( URLEncoder.encode(name, enc) +
				   "[" + String.valueOf( count ) + "]" +
				   "=" + URLEncoder.encode(value, enc) + sep );
			return sb.toString();
		} catch(Exception e) {
			return "";
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@name = value �����ݒ菈��<BR>
	 *�@���@�l�@�F�@�z��^�ŃN���C�A���g�T�[�o�ԒʐM���s���Ƃ��Ɏg�p
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/12/30�@YangChuan�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	name�@�@�@ IN�@�F�@name��
	 * @param	value�@�@�@IN�@�F�@value��
	 * @param	sep�@�@�@IN�@�F�@
	 * @return	name=
	 */
	public static String encStringAry2(String name, int count, int count1, String value ) {
		String sep;
		String enc = "SJIS";
		sep = "&";

		try {
			StringBuffer sb = new StringBuffer();
			sb.append( URLEncoder.encode(name, enc) +
				   "[" + String.valueOf( count ) + "]" +
				   "[" + String.valueOf( count1 ) + "]" +
				   "=" + URLEncoder.encode(value, enc) + sep );
			return sb.toString();
		} catch(Exception e) {
			return "";
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@name = value �����ݒ菈��<BR>
	 *�@���@�l�@�F�@�z��^�ŃN���C�A���g�T�[�o�ԒʐM���s���Ƃ��Ɏg�p
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/06/13�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	name�@�@�@ IN�@�F�@name��
	 * @param	value�@�@�@IN�@�F�@value��
	 * @param	sep�@�@�@IN�@�F�@
	 * @return	name=
	 */
	public static String encStringAry( String name, int count ) {
		return name + "[" + String.valueOf( count ) + "]";
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@name = value �����ݒ菈��<BR>
	 *�@���@�l�@�F�@�z��^�ŃN���C�A���g�T�[�o�ԒʐM���s���Ƃ��Ɏg�p
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/12/30�@YangChuan�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	name�@�@�@ IN�@�F�@name��
	 * @param	value�@�@�@IN�@�F�@value��
	 * @param	sep�@�@�@IN�@�F�@
	 * @return	name=
	 */
	public static String encStringAry2( String name, int count, int count1 ) {
		return name + "[" + String.valueOf( count ) + "]" + "[" + String.valueOf( count1 ) + "]";
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[�֐�<BR>
	 *�@�������@�F�@�����񐔒l��ύX����<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public static String tofromatNumber (String sParam ) {
		String	sStr1;
		String	sStr2;
		double	dvalue;
		StringBuffer sbuff = new StringBuffer();
		FieldPosition fpos = new FieldPosition(DecimalFormat.INTEGER_FIELD);

		if ( sParam.equals( "" ) == false ) {

			dvalue	= Long.parseLong( sParam );
			DecimalFormat df = new DecimalFormat();

			df.setMinimumFractionDigits( 0 );
			df.format( dvalue, sbuff, fpos );

			return sbuff.toString();
		} else {
			return "";
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[�֐�<BR>
	 *�@�������@�F�@�����񐔒l��ύX����<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public static String tofromatReal (String sParam , int   iDegit) {
		String	sStr1;
		String	sStr2;
		double	dvalue;
		StringBuffer sbuff = new StringBuffer();
		FieldPosition fpos = new FieldPosition(DecimalFormat.INTEGER_FIELD);

		if ( sParam.equals( "" ) == false ) {

			dvalue	= Double.parseDouble( sParam );
			DecimalFormat df = new DecimalFormat();

			df.setMinimumFractionDigits( iDegit );
			df.format( dvalue, sbuff, fpos );

			return sbuff.toString();
		} else {
			return "";
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@null�ϊ�����<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.01�@�@2002/08/06�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public static final String nvl(String sprm) {
		if (sprm == null) {
			return "";
		} else {
			return sprm;
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�[������<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2003/03/05�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	dNum�@�@�@�@ IN�@�F�@�����l
	 * @param	sHassuKbn�@�@IN�@�F�@�[���敪(1:�؎̂āA2:�l�̌ܓ��A3:�؂�グ)
	 * @return	��������
	 */
	public static long calcHasu( double dNum, String sHassuKbn ) {
		String	sKbn;

		sKbn	= sHassuKbn.trim();
		if ( sKbn.equals( "2" ) == true ) {
			//	�l�̌ܓ�
			return round( dNum );
		} else if ( sKbn.equals( "1" ) == true ) {
			//	�؂�̂�
			return roundDown( dNum );
		} else if ( sKbn.equals( "3" ) == true ) {
			//	�؂�グ
			return roundUp( dNum );
		} else {
			//	�l�̌ܓ�
			return round( dNum );
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�l�̌ܓ�����<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2000/10/31�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	dNum�@�@IN�@�F�@�����l
	 * @return	��������
	 */
	public static long round( double dNum ) {
		long	lResult;
		double	dDbl;

		if ( dNum < 0 ) {
			dDbl = ( -1 ) * dNum;
			dDbl = dDbl + 0.5;
			lResult = (int)dDbl;
			lResult = ( -1 ) * lResult;
		} else {
			lResult = Math.round( dNum );
		}
		return lResult;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�؂�̂ď���<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2000/10/31�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	dNum�@�@IN�@�F�@�����l
	 * @return	��������
	 */
	public static long roundDown( double dNum ) {
		if ( dNum < 0 ) {
			return (long)Math.ceil( dNum );
		} else {
			return (long)Math.floor( dNum );
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�؂�グ����<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2000/10/31�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	dNum�@�@IN�@�F�@�����l
	 * @return	��������
	 */
	public static long roundUp( double dNum ) {
		if ( dNum < 0 ) {
			return (long)Math.floor( dNum );
		} else {
			return (long)Math.ceil( dNum );
		}
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�[����������<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2003/01/11�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	dNum�@�@IN�@�F�@�����l
	 * @return	��������
	 */
	public static double pow( double dNum, long lKeta ) {
		double dTemp;
		double dAns;
		dTemp = Math.pow( 10 , ( double )lKeta );
		dAns = ( double )ComDATA.round( dNum * dTemp ) / dTemp;
		return dAns;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@���������s����<BR>
	 *�@���@�l�@�F�@
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/08/21�@���Y�@���F�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	string�@�@�@ IN�@�F ���s������������@
	 * @param	berm  �@�@�@ IN�@�F ���s���镶����
	 * @return	sbString		OUT :�@���s������
	 */
	public static String newLine( String string, int berm ){
		int  i;
		int  iEndCountUp = 0;
		long lLinrLength = 0;
		long lCharLength = 0;
		StringBuffer sbString = new StringBuffer( string );

		lCharLength = string.length();
		lLinrLength = lCharLength/berm;

		for(i=1; i <= lLinrLength; i++){
			sbString.insert( (berm*i+iEndCountUp), "\n" );
			iEndCountUp++;
		}
		return String.valueOf( sbString );
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@���������s����<BR>
	 *�@���@�l�@�F�@��O������ɉ��s�������s��
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/08/21�@���Y�@���F�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param	exception	IN�@�F ���s��������O������@
	 * @param	berm  �@�@�@ IN�@�F ���s���镶����
	 * @return	sbString		OUT :�@���s������
	 */
	public static String newLine( Exception exception, int berm ){
		return newLine( exception.toString(), berm );
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[�֐�<BR>
	 *�@�������@�F�@�����񐔒l��ύX����<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
	 *<BR>
	 */
	public static String tofromatDate (String sDate2 ) {
		String sDate=sDate2;
		String	sResult;
		sDate = sDate + "        ";
		sResult	= sDate.substring( 0, 4 )
				+ "/"
				+ sDate.substring( 4, 6 )
				+ "/"
				+ sDate.substring( 6, 8 )
				;

		return sResult;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�����a��ɕϊ�<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2003/02/06�@���Y�@���F�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param   sSeireki      :����
	 */
	public static String toWaReki(String sSeireki){

		int	iStrCount = 0;	//�擾������
		int	iYear	  = 0;	//����
		int	iWareki	  = 0;	//�a��
		String	sWareki	  = "";	//�a��
		String	sNengou	  = "";	//�N��

		//��̏ꍇ�iNULL�����l�j�́A""��Ԃ�
		if ( ComDATA.nvl( sSeireki ).equals( "" ) == true ) {
			return "";
		}

		//���������擾
		iStrCount = sSeireki.length();

		//��������4������菭�Ȃ���΃G���[
		if( iStrCount < 4){
			return sSeireki;
		}

		//������擾
		iYear = Integer.parseInt( sSeireki.substring( 0, 4 ));
		if( DefaultCommon.WAREKI_HEISEI < iYear ) {
			sNengou = String.valueOf( DefaultCommon.NENGOU_HEISEI );
			iWareki = iYear - DefaultCommon.WAREKI_HEISEI;
		} else if( DefaultCommon.WAREKI_SHOUWA < iYear ) {
			sNengou = String.valueOf( DefaultCommon.NENGOU_SHOUWA );
			iWareki = iYear - DefaultCommon.WAREKI_SHOUWA;
		} else if( DefaultCommon.WAREKI_TAISHOU < iYear ) {
			sNengou = String.valueOf( DefaultCommon.NENGOU_TAISHOU );
			iWareki = iYear - DefaultCommon.WAREKI_TAISHOU;
		} else {
			sNengou = String.valueOf( DefaultCommon.NENGOU_DEFAULT );
			iWareki = iYear - DefaultCommon.WAREKI_DEFAULT;
		}
		sWareki = sNengou + ComDATA.zeroFormat(String.valueOf( iWareki ), 2);
		if( iStrCount >= 5 ){
			sWareki = sWareki + sSeireki.substring(4);
		}

		return sWareki;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@�a��𐼗�ɕϊ�<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2003/02/06�@���Y�@���F�@�@�V�K�쐬<BR>
	 *<BR>
	 * @param   sWareki      :�a��
	 */
	public static String toSeiReki( String sWareki ){

		int iStrCount	 = 0;	//�擾������
		int iFlg	 = 0;	//�擪�����񔻒�t���O
		char cNengou	 = ' ';	//�N��
		int iFirstYear	 = 0;	//���N
		int iSeireki	 = 0;	//����
		String  sSeireki = "";	//����

		//��̏ꍇ�iNULL�����l�j�́A""��Ԃ�
		if ( ComDATA.nvl( sWareki ).equals( "" ) == true ) {
			return "";
		}

		//���������擾
		iStrCount = sWareki.length();

		if( iStrCount <= 0 ){
			return sWareki;
		}

		//�擪�����ȊO�ɕ������܂܂�Ă�����G���[
		for( int i=1;  i < iStrCount; i++ ){
			if( Character.isDigit( sWareki.charAt(i)) == false ){
				return sWareki;
			}
		}

		//�擪�������擾
		cNengou = sWareki.toUpperCase().charAt(0);
		if ( Character.isDigit( cNengou ) == true ){
			if( iStrCount < 2){
				return sWareki;
			}
			//�擪���������l�̏ꍇ�f�t�H���g�̐���l���g�p
				iFirstYear  = DefaultCommon.WAREKI_DEFAULT;
			iFlg = 0;
		} else {
			//�擪�����ƔN�����r�����N�̐�����擾�Y�����Ȃ��ꍇ�f�t�H���g
			switch( cNengou ){
				case DefaultCommon.NENGOU_HEISEI:
					iFirstYear  = DefaultCommon.WAREKI_HEISEI;
					break;
				case DefaultCommon.NENGOU_SHOUWA:
					iFirstYear = DefaultCommon.WAREKI_SHOUWA;
					break;
				case DefaultCommon.NENGOU_TAISHOU:
					iFirstYear = DefaultCommon.WAREKI_TAISHOU;
					break;
				default:
					iFirstYear = DefaultCommon.WAREKI_DEFAULT;
					break;
			}
			iFlg = 1;
		}
		//������o��
		iSeireki = iFirstYear + Integer.parseInt( sWareki.substring( 0+iFlg, 2+iFlg ));
		sSeireki = String.valueOf( iSeireki );
		//���������݂���ꍇ�͂�����Ȃ���
		if( iStrCount >= 2+iFlg ){
			sSeireki = sSeireki + sWareki.substring( 2+iFlg );
		}
		return sSeireki;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���R�[�h�ҏW����<BR>
	 *�@�������@�F�@�i��}�X�^���ڕҏW<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.00�@�@2003/04/17�@ �q��p�� �@�@  �V�K�쐬<BR>
	 *<BR>
	  * @param     iRecLayout[]  IN:���R�[�h���C�A�E�g
	  * @param     sInputRec     IN:���̓��R�[�h
	  * @param     iIRecCnt      IN:���̓��R�[�h�J�E���g
	  * @return    pvHnsmstRec :�e�v�f�ɕ��������x�N�^�[�z��
	  */
	public static Vector getLayoutData(int[]     iRecLayout,
					   String  sInputRec,
					   int     iIRecCnt,
					   OutputStreamWriter writer
					   ) {
		int iYousoSuu = 0;

		//�t�@�C�����C�A�E�g�ɔ��p�ƑS�p�����݂��鍀�ڂ����邽�߁A��U�o�C�g�ɕϊ����J�����ʒu�ō��ڂ��Ƃɕ�������
		iYousoSuu = iRecLayout.length;
		byte[] b = sInputRec.getBytes();
		int iLengCnt = 0;
		Vector  vecKOUMOKU;
		vecKOUMOKU = new Vector();
		try{
			for ( int i = 0; i <= iYousoSuu - 1; i++ ){
				byte[] byteTmp = new byte[iRecLayout[i]];
				for ( int j = 0; j <= ( iRecLayout[i] - 1 ); j++){
					byteTmp[j] = b[ j + iLengCnt ];
				}
				iLengCnt += iRecLayout[i];
				String sStringTmp = new String( byteTmp );
				vecKOUMOKU.addElement( sStringTmp );
			}
		}catch( ArrayIndexOutOfBoundsException e ){
			System.out.println( "���R�[�h���Z�����܂� �s��= "+ iIRecCnt );
			System.out.println( sInputRec );
		}
		return vecKOUMOKU;
	}

	/**
	 *<BR>
	 *�@�^�C�v�@�F�@���[�U�[��`<BR>
	 *�@�������@�F�@IF�t�@�C�����擾<BR>
	 *�@���@�l�@�F�@<BR>
	 *<BR>
	 *�@�ύX����<BR>
	 *<BR>
	 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
	 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
	 *�@�@00.01�@�@2004/08/30�@Tsuka�@�@�V�K�쐬<BR>
         *   00.02    2005/01/14 Yery     File Path���C��
	 *<BR>
 	 * @param   syori�@�@  IN�@�F�@��������
	 * @return  �t�@�C����
	 */
	public static String getFileName( String syori ) {
		String sFilename = "";
                String sPropertiFile = FILENAME;
                //�v���p�e�B�[�t�@�C���̓Ǎ�
		System.out.println( "GetFilename/syori : " + syori);

                /**
                 * File Path���C��
                 */
                try {
                  ResourceBundle resources = ResourceBundle.getBundle(FILENAME,
                      Locale
                      .getDefault());
                  System.out.println( "GetFilenames/load");

                  Enumeration keys = resources.getKeys();

                  while (keys.hasMoreElements()) {
                    String sKey = (String) keys.nextElement();
                    String sValue = resources.getString(sKey).trim();
                    if(sKey.equals(syori)) {
                      sFilename=sValue;
                      break;
                    }
                  }
                }
                catch (Exception e) {
                  e.printStackTrace();
                  sFilename = "";
                }
		System.out.println( "GetFilenames/Filename : " + sFilename);
		return sFilename;
	}


        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@�z�X�g���M�p���l�����ϊ�����<BR>
         *�@���@�l�@�F�@�Ō�̌��ɕ����R�[�h�ϊ�<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2004/09/02�@����@�_�i�@�@�V�K�쐬<BR>
         *�@�@00.01�@�@2004/12/22�@����@�_�i�@�@�v���X(F)�p�ǉ�<BR>
         *<BR>
         * @param	sValue	:�����f�[�^
         * @param	iKbn	:�v���X(F)= 0, �v���X = 1, �}�C�i�X = 2
         * @return	sValue		:�ϊ���f�[�^
         */
        public static String  toSign( String sValue, int iKbn ){
                int	iLength;
                char	cCode1;
                char  cCode2 = ' ';

                cCode1 = sValue.charAt( sValue.length() - 1 );
                //ins 2004.12.22 str
                if ( iKbn == 0 ) {
                        switch ( cCode1 ) {
                                case '0':
                                        cCode2 = 0x30;
                                        break;
                                case '1':
                                        cCode2 = 0x31;
                                        break;
                                case '2':
                                        cCode2 = 0x32;
                                        break;
                                case '3':
                                        cCode2 = 0x33;
                                        break;
                                case '4':
                                        cCode2 = 0x34;
                                        break;
                                case '5':
                                        cCode2 = 0x35;
                                        break;
                                case '6':
                                        cCode2 = 0x36;
                                        break;
                                case '7':
                                        cCode2 = 0x37;
                                        break;
                                case '8':
                                        cCode2 = 0x38;
                                        break;
                                case '9':
                                        cCode2 = 0x39;
                                        break;
                                default:
                        }
                } else if ( iKbn == 1 ) {
                //ins 2004.12.22 end
                        switch ( cCode1 ) {
                                case '0':
                                        cCode2 = 0x7B;
                                        break;
                                case '1':
                                        cCode2 = 0x41;
                                        break;
                                case '2':
                                        cCode2 = 0x42;
                                        break;
                                case '3':
                                        cCode2 = 0x43;
                                        break;
                                case '4':
                                        cCode2 = 0x44;
                                        break;
                                case '5':
                                        cCode2 = 0x45;
                                        break;
                                case '6':
                                        cCode2 = 0x46;
                                        break;
                                case '7':
                                        cCode2 = 0x47;
                                        break;
                                case '8':
                                        cCode2 = 0x48;
                                        break;
                                case '9':
                                        cCode2 = 0x49;
                                        break;
                                default:
                        }
                } else {
                        switch ( cCode1 ) {
                                case '0':
                                        cCode2 = 0x7D;
                                        break;
                                case '1':
                                        cCode2 = 0x4A;
                                        break;
                                case '2':
                                        cCode2 = 0x4B;
                                        break;
                                case '3':
                                        cCode2 = 0x4C;
                                        break;
                                case '4':
                                        cCode2 = 0x4D;
                                        break;
                                case '5':
                                        cCode2 = 0x4E;
                                        break;
                                case '6':
                                        cCode2 = 0x4F;
                                        break;
                                case '7':
                                        cCode2 = 0x50;
                                        break;
                                case '8':
                                        cCode2 = 0x51;
                                        break;
                                case '9':
                                        cCode2 = 0x52;
                                        break;
                                default:
                        }
                }

                return sValue.substring( 0, sValue.length() - 1 ) + cCode2;
        }


        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@�S�p���͔��菈��<BR>
         *�@���@�l�@�F�@<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2004/11/29�@Yery�@�@�V�K�쐬<BR>
         *<BR>
         * @param	text	:�����f�[�^
         * @param	msg   	:JMsText�̖�
         * @return	sValue		:true�@�F�@�n�j<BR>false�@�F�@�G���[����
         */

        public static boolean checkFullWidthInput(String text,String msg) {
          String	sCheckStr;	// ���蕶����
          boolean	bRet=true;	// �߂�l

          //++************************
          //	�S�p���͔��菈��
          //--************************
          sCheckStr = text;
          int iCharNumbers=sCheckStr.length();
          int iByteLength=sCheckStr.getBytes().length;
          if(iCharNumbers!=iByteLength/2) {
                  bRet=false;
                  //text�̓u�����N�Ƃ���ƁA�G���[���b�Z�[�W��\�����܂���B
                  if(msg!=null && msg.trim().length()>0) {
                    comMSG.dispErrorDialog(msg + "�͑S�p�œ��͂��Ă��������B");
                  }
          }
          return bRet;
        }

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@�S�p���͔��菈��<BR>
         *�@���@�l�@�F�@<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2004/11/29�@Yery�@�@�V�K�쐬<BR>
         *<BR>
         * @param	text	:�����f�[�^
         * @param	msg   	:JMsText�̖�
         * @return	sValue		:true�@�F�@�n�j<BR>false�@�F�@�G���[����
         */

        public static boolean checkFullWidthInput(JMsText text,String msg) {
          String	sCheckStr;	// ���蕶����
          boolean	bRet=true;	// �߂�l

          //++************************
          //	�S�p���͔��菈��
          //--************************
          sCheckStr = text.getText();
          int iCharNumbers=sCheckStr.length();
          int iByteLength=sCheckStr.getBytes().length;
          if(iCharNumbers!=iByteLength/2) {
                  bRet=false;
                  text.setErrorField( true );
                  //text�̓u�����N�Ƃ���ƁA�G���[���b�Z�[�W��\�����܂���B
                  if(msg!=null && msg.trim().length()>0) {
                    comMSG.dispErrorDialog(msg + "�͑S�p�œ��͂��Ă��������B");
                    comFORM.setFocus(text);
                  }
          }
          return bRet;
        }

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@�S�p�𔼊p�ɕϊ�<BR>
         *�@���@�l�@�F�@<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2004/11/29�@Yery�@�@�V�K�쐬<BR>
         *<BR>
         * @param halfWidthText String
         * @return String
         */
        public static String toFullWidthText(String halfWidthText) {
          java.util.ResourceBundle resources = java.util.ResourceBundle.getBundle("com_zennoh/common/charset",
              java.util.Locale
              .getDefault());

          java.util.Enumeration keys = resources.getKeys();
          java.util.HashMap ht=new java.util.HashMap();

          while (keys.hasMoreElements()) {
            String sKey = (String) keys.nextElement();
            String sValue = resources.getString(sKey).trim();
            ht.put(sKey,sValue);
          }

          if(halfWidthText!=null) {
            StringBuffer sbTemp = new StringBuffer();
            try {
              //System.out.println("before:"+halfWidthText);
              //halfWidthText = new String(halfWidthText.getBytes("SJIS"));
              //System.out.println("after: "+halfWidthText);

              for (int i = 0; i < halfWidthText.length(); i++) {
                String c = halfWidthText.substring(i,i+1);
                /**
                 * ���p
                 */
                if (c.getBytes().length == 1) {
                  byte[] bTexts = c.getBytes();
                  //Character character=new  Character(c);
                  String sHex = Integer.toHexString(bTexts[0]).toUpperCase();
                  if(sHex.length()>2) {
                    sHex=sHex.substring(sHex.length()-2);
                  }
                  String sKey = "0x"+sHex;

                  String sValue = (String) ht.get(sKey);

                  if (sValue != null) {
                    sbTemp.append(sValue);
                  }
                  else {
                    sbTemp.append(c);
                  }
                }
                /**
                 * �S�p
                 */
                else {
                  sbTemp.append(c);
                }
              }
              return sbTemp.toString();
            } catch(Exception e) {
              return null;
            }
          } else {
            return null;
          }
        }

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@���p��S�p�ɕϊ�<BR>
         *�@���@�l�@�F�@<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2004/12/24�@Yery�@�@�V�K�쐬<BR>
         *<BR>
         * @param halfWidthText String
         * @return String
         */
        public static String toHalfWidthText(String fullWidthText) {
          java.util.ResourceBundle resources = java.util.ResourceBundle.getBundle("com_zennoh/common/charset",
              java.util.Locale
              .getDefault());

          java.util.Enumeration keys = resources.getKeys();
          java.util.HashMap ht=new java.util.HashMap();

          while (keys.hasMoreElements()) {
            String sKey = (String) keys.nextElement();
            String sValue = resources.getString(sKey).trim();
            ht.put(sValue,sKey);
          }

          if(fullWidthText!=null) {
            StringBuffer sbTemp = new StringBuffer();
            try {
              //System.out.println("before:"+halfWidthText);
              //halfWidthText = new String(halfWidthText.getBytes("SJIS"));
              //System.out.println("after: "+halfWidthText);

              for (int i = 0; i < fullWidthText.length(); i++) {
                String c = fullWidthText.substring(i,i+1);
                /**
                 * �S�p
                 */
                boolean isFind=false;

                if (c.getBytes().length == 2) {
                  String sKey=(String)ht.get(c);
                  if(sKey!=null) {
                    byte[] bTexts=new byte[1];
                    bTexts[0]=(byte)(Integer.parseInt(sKey.substring(2),16)&0xFF);
                    String sText=new String(bTexts);
                    sbTemp.append(sText);
                  } else {
                    sbTemp.append(c);
                  }
                }
                /**
                 * ���p
                 */
                else {
                  sbTemp.append(c);
                }
              }
              return sbTemp.toString();
            } catch(Exception e) {
              e.printStackTrace();
              return null;
            }
          } else {
            return null;
          }
        }
}
