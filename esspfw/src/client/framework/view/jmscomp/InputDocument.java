package client.framework.view.jmscomp;

import javax.swing.text.*;

public class InputDocument extends PlainDocument {

    public static final int ALL = 0; //�S��
    public static final int NUMERIC = 1; //�����̂�
    public static final int ALPHA = 2; //�ʏ핶���̂�
    public static final int ALPHA_NUMERIC = 3; //�ʏ핶���Ɛ����̂�
    public static final int INTEGER = 4; //�����i�����A�{�A�|�j
    public static final int FLOAT = 5; //�����i�����A�{�A�|�A�����_�j

    int itype = ALL;
    boolean b2byte = false;
    boolean canNegative = true;
    int periodCount = 0;

    int giLimit; // ��������
    int iMaxInputIntegerDigit = 0;
    int iMaxInputDecimalDigit = 0;

    String[] gsLimitedStr = {"\'", ";"}; // �֎~������z��


    /**
     *<BR>
     *�@�^�C�v�@�F�@���[�U�[��`<BR>
     *�@�������@�F�@InputDocument<BR>
     *�@���@�l�@�F�@<BR>
     *<BR>
     *�@�ύX����<BR>
     *<BR>
     *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
     *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
     *�@�@00.00�@�@2002/05/28�@�󗈁@�K�O�@�@�V�K�쐬<BR>
     *<BR>
     * @param   iLimitDir�@�@IN�@�F�@�ő����byte��
     * @param   type�@�@     IN�@�F�@�R���|�[���g�^�C�v
     * @param   �@�@�@�@�@�@�@IN�@�F�@�f�B���N�g����
     * @param   �@�@�@�@�@�@�@IN�@�F�@�f�B���N�g����
     * @return  true�@�F�@����<BR>false�@�F�@���݂��Ȃ�
     */
    public InputDocument(int prm_iLimit,
                         int prm_type,
                         boolean prm_b2byte
            ) {
        if (prm_iLimit == -1) {
            this.giLimit = 999;
        } else {
            this.giLimit = prm_iLimit;
        }

        this.itype = prm_type;
        this.b2byte = prm_b2byte;
    }

    /**
     *<BR>
     *�@�^�C�v�@�F�@���[�U�[��`<BR>
     *�@�������@�F�@InputDocument<BR>
     *�@���@�l�@�F�@<BR>
     *<BR>
     *�@�ύX����<BR>
     *<BR>
     *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
     *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
     *�@�@00.00�@�@2002/05/28�@�󗈁@�K�O�@�@�V�K�쐬<BR>
     *<BR>
     * @param   iLimitDir�@�@IN�@�F�@�ő����byte��
     * @param   type�@�@     IN�@�F�@�R���|�[���g�^�C�v�i�V���{���Q�Ɓj
     * @param   �@�@�@�@�@�@�@IN�@�F�@�f�B���N�g����
     * @param   �@�@�@�@�@�@�@IN�@�F�@�f�B���N�g����
     * @return  true�@�F�@����<BR>false�@�F�@���݂��Ȃ�
     */
    public InputDocument(int prm_iLimit,
                         int prm_type,
                         int prm_iMaxInteger,
                         int prm_iMaxDecimal
            ) {
        //-1�̏ꍇ�A���͌�����999�ɒu������
        if (prm_iLimit == -1) {
            this.giLimit = 999;
        } else {
            this.giLimit = prm_iLimit;
        }

//2002.07.09  MS(HORAI)
//�^�������^�̏ꍇ�A�l�`�w������ύX
        if (prm_type == INTEGER) {
            this.giLimit = prm_iMaxInteger;
        }
//2002.07.09  MS(HORAI)



        this.itype = prm_type;

        if (prm_iMaxInteger == -1) {
            this.iMaxInputIntegerDigit = 999;
        } else {
            this.iMaxInputIntegerDigit = prm_iMaxInteger;
        }

        if (prm_iMaxDecimal == -1) {
            this.iMaxInputDecimalDigit = 999;
        } else {
            this.iMaxInputDecimalDigit = prm_iMaxDecimal;
        }

    }


    /**
     *<BR>
     *�@�������@�F�@insertString<BR>
     *�@���@�l�@�F�@<BR>
     *<BR>
     *�@�ύX����<BR>
     *<BR>
     *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
     *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
     *�@�@00.00�@�@2002/05/28�@�󗈁@�K�O�@�@�V�K�쐬<BR>
     *<BR>
     * @param   �ő���͕������ibyte)�A
     * @return  �Ȃ�
     */
    public void insertString(int iOff, String sStr, AttributeSet att) throws
            BadLocationException {
        String sCheckedStr = ""; //�`�F�b�N�ςݕ�����
        StringBuffer sbDspNowSeisu = new StringBuffer(); //���ݕ\��������
        StringBuffer sbDspNowSyosu = new StringBuffer(); //���ݕ\��������
        StringBuffer sbDspAftSeisu = new StringBuffer(); //�}���㐮����
        StringBuffer sbDspAftSyosu = new StringBuffer(); //�}���㏬����
        StringBuffer sbDspAftAll = new StringBuffer(); //���͌㉼������
        boolean bDecPFlg = false; //�����_�L���t���O
        char chTemp; //�P�����i�[�p
        int iDecPoint = 0; //�����_�\����
        String sDspNowAll; //���ݕ\���S�̊i�[�p
        int iPassByteLengthSeisu = 0; //�ǉ����͉\���i�����j
        int iPassByteLengthSyosu = 0; //�ǉ����͉\���i�����j
        int i;
        int j;
        boolean bRet;

        //add to support the none negative input
        String[] limitedStr = gsLimitedStr;
        if( this.canNegative() == false ){
            limitedStr = new String[gsLimitedStr.length+1];
            System.arraycopy(gsLimitedStr,0,limitedStr,0,gsLimitedStr.length);
            limitedStr[gsLimitedStr.length]="-";
        }
        String sLimitCheckedStr = deleteLimitedString(sStr, limitedStr);

        // ���͐���������폜
//        String sLimitCheckedStr = deleteLimitedString(sStr, gsLimitedStr);

        // 2byte��������
        if (b2byte == false) {
            bRet = check2Byte(sStr);
            if (bRet == true) {
                return;
            }
        }

        // ��������Ȃ�I��
        if (sLimitCheckedStr.length() == 0) {
            return;
        }

        // FEP���N�����Ă���Ƃ����͊m��܂ł̓f�t�H���g�̏���
        if ((att != null) &&
            (att.getAttribute(StyleConstants.ComposedTextAttribute) != null)) {
            super.insertString(iOff, sStr, att);
            return;
        }

        /* �����񒷐��� */
        // Document�I�u�W�F�N�g�̕�����̃o�C�g�z����擾
        String sLenCheckStr = getText(0, getLength());
        byte[] bLenCheckStr = sLenCheckStr.getBytes();

        // ���͉\�ȃo�C�g���擾
        int iPassByteLength = giLimit - bLenCheckStr.length;

//horai+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        //�R���|�[�l���g�^�C�v�������^�C�v�ɐݒ肳��Ă���ꍇ
        if (itype == FLOAT) {

            sDspNowAll = getText(0, getLength());

            //���������񂵂Ȃ���P����������
            for (i = 0; i < sDspNowAll.length(); i++) {
                chTemp = sDspNowAll.charAt(i);
                if (chTemp != ',') {
                    if (chTemp == '.') {
//                        iPoffset = i;
                        bDecPFlg = true;
                    }

                    if (bDecPFlg == false) {
                        try {
                            sbDspNowSeisu = (StringBuffer) sbDspNowSeisu.append(
                                    chTemp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (chTemp != '.') {
                            try {
                                sbDspNowSyosu = (StringBuffer) sbDspNowSyosu.
                                                append(chTemp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

//System.out.println( " �\������i�����j : " +  sbDspNowSeisu );
//System.out.println( " �\������i�����j : " +  sbDspNowSyosu );

            // �ǉ����͉\�Ȑ����񐔂��v�Z�i���p�����[�^�ōő��ݒ肷�邱�Ɓj
            iPassByteLengthSeisu = iMaxInputIntegerDigit -
                                   sbDspNowSeisu.toString().length();

            // �ǉ����͉\�ȏ����񐔂��v�Z�i���p�����[�^�ōő��ݒ肷�邱�Ɓj
            iPassByteLengthSyosu = iMaxInputDecimalDigit -
                                   sbDspNowSyosu.toString().length();

//System.out.println( " �ǉ����͉\�������i�����j : " +  iPassByteLengthSeisu );
//3System.out.println( " �ǉ����͉\�������i�����j : " +  iPassByteLengthSyosu );

            //�u�\�����v�u�}�����v�u�\�����v�̕��тŕ�������쐬
            for (i = 0; i < iOff; i++) {
                chTemp = sDspNowAll.charAt(i);
                try {
                    sbDspAftAll = (StringBuffer) sbDspAftAll.append(chTemp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (i = 0; i < sStr.length(); i++) {
                chTemp = sStr.charAt(i);
                try {
                    sbDspAftAll = (StringBuffer) sbDspAftAll.append(chTemp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for (i = iOff; i < sDspNowAll.length(); i++) {
                chTemp = sDspNowAll.charAt(i);
                try {
                    sbDspAftAll = (StringBuffer) sbDspAftAll.append(chTemp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//System.out.println( " ���͌�̕\�� : " + sbDspAftAll  );

            //�����_�L���t���O��������
            bDecPFlg = false;

            //���������񂵂Ȃ���P����������
            for (i = 0; i < sbDspAftAll.toString().length(); i++) {
                chTemp = sbDspAftAll.toString().charAt(i);
                if (chTemp != ',') {
                    if (chTemp == '.') {
                        bDecPFlg = true;
                    }

                    if (bDecPFlg == false) {
                        try {
                            sbDspAftSeisu = (StringBuffer) sbDspAftSeisu.append(
                                    chTemp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (chTemp != '.') {
                            try {
                                sbDspAftSyosu = (StringBuffer) sbDspAftSyosu.
                                                append(chTemp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    iPassByteLength = iPassByteLength + 1;
                }
            }

//System.out.println( " �}���㐔��i�����j : " +  sbDspAftSeisu );
//System.out.println( " �}���㐔��i�����j : " +  sbDspAftSyosu );

            //�}���㐮���������A�ݒ�l�𒴂���ꍇ�I��
            if (sbDspAftSeisu.toString().length() > iMaxInputIntegerDigit) {
//System.out.println( "���������ݒ�l�𒴂������ߏI��" );
                return;
            }

            //�}���㏬���������A�ݒ�l�𒴂���ꍇ�I��
            if (sbDspAftSyosu.toString().length() > iMaxInputDecimalDigit) {
//System.out.println( "���������ݒ�l�𒴂������ߏI��" );
                return;
            }

        }

//horai-----------------------------------------------------------------------------
//System.out.println( " iPassByteLength : " + iPassByteLength  );
//System.out.println( " sLimitCheckedStr.length() : " + sLimitCheckedStr.length()  );
//System.out.println( " sLimitCheckedStr : " + sLimitCheckedStr  );

        for (i = 0; i < sLimitCheckedStr.length(); i++) {

            char ch = sStr.charAt(i);

            //�����i�S�p�̐������܂ށj
            if (itype == NUMERIC && Character.isDigit(ch) == false) {
                return;
            }

            //�ʏ핶���i�S�p�����܂ށj�L���͊܂܂Ȃ�
            if (itype == ALPHA && Character.isLetter(ch) == false) {
                return;
            }

            //�ʏ핶���Ɛ��������B�L���͊܂܂Ȃ��B
            if (itype == ALPHA_NUMERIC && Character.isLetterOrDigit(ch) == false) {
                return;
            }

            //�����i�����A+�A-�j
            if (itype == INTEGER) {
                if (iOff == 0) {
                    if (Character.isDigit(ch) == false && ch != '-') {
                        return;
                    }
                }

                if (iOff > 0) {
                    if (Character.isDigit(ch) == false) {
                        return;
                    }
                }
            }

            //�����i�����A+�A-�A�����_�j
            if (itype == FLOAT) {
                if (i == 0) {
                    if (iOff == 0) {
//horai+++++++++++++++++++++++++++++++++++++++++++++++++++
//���͂̑}���悪�擪�̏ꍇ�isetText( "****" )�܂ށj�œ��͕����̍ŏ��� '-' �ł͖����ꍇreturn;
                        if (Character.isDigit(sStr.charAt(0)) == false &&
                            ch != '-') {
//System.out.println( "check-point-1" );
                            return;
                        }
                    }
//horai---------------------------------------------------

//                    if( Character.isDigit(ch)==false && ch!='-'  ) {
//                      return;
//                    }
                }

                if (iOff > 0) {
                    if (ch == '.') {

                        sDspNowAll = getText(0, getLength());

                        //���������񂵂Ȃ���P����������
                        for (j = 0; j < sDspNowAll.length(); j++) {
                            chTemp = sDspNowAll.charAt(j);
                            if (chTemp == '.') {
                                return;
                            }
                        }

                    } else {
                        if (Character.isDigit(ch) == false) {
//System.out.println( "check-point-2" );
                            return;
                        }
                    }
                }
            }

            String sSplitStr = sLimitCheckedStr.substring(i, i + 1);
            iPassByteLength -= sSplitStr.getBytes().length;

            if (iPassByteLength >= 0) {
                // �ύX���f

                super.insertString(iOff + i, sSplitStr, att);
            } else {
                return;
            }
        }
    }


    private String deleteLimitedString(String sStr, String[] sLimitedStrings) {
        if (sStr == null) {
            return sStr;
        }

        String sLimitCheckStr = new String(sStr); // �`�F�b�N�p������쐬
        try {

            // ��{�֎~�������폜
            // ���s�R�[�h���������Ă���ꍇ���s�R�[�h�ȍ~�͐؂�̂�
            int iLF = sLimitCheckStr.indexOf("\n");
            if (iLF >= 0) {
                sLimitCheckStr = sLimitCheckStr.substring(0, iLF);
            }
            int iCR = sLimitCheckStr.indexOf("\r");
            if (iCR >= 0) {
                sLimitCheckStr = sLimitCheckStr.substring(0, iCR);
            }

            // TAB�R�[�h���������Ă���ꍇ�X�y�[�X�ɕϊ�
            int iTAB = sLimitCheckStr.indexOf("\t");
            if (iTAB >= 0) {
                sLimitCheckStr = sLimitCheckStr.replace('\t', ' ');
            }

            // �֎~������`�F�b�N�J�n
            for (int i = 0; i < sLimitedStrings.length; i++) {
                int iLim; // �֎~������̃C���f�b�N�X
                while ((iLim = sLimitCheckStr.indexOf(sLimitedStrings[i])) >= 0) {
                    // 1�����Ȃ�󕶎���Ԃ�
                    if (sLimitCheckStr.length() == 1) {
                        return new String("");
                        // �֎~�������폜
                    } else {
                        StringBuffer sb = new StringBuffer(sLimitCheckStr);
                        sb = (StringBuffer) sb.deleteCharAt(iLim);
                        sLimitCheckStr = sb.toString();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sLimitCheckStr;
    }

    /**
     * �@�\     : �S�p�������ݔ���
     * �߂�l   : �S�p����Ftrue  �S�p�Ȃ�:false
     * ������   :�@�`�F�b�N�Ώە�����
     * �@�\���� :
     * ���l     : ���l�A���t
     */
    private boolean check2Byte(String prm_sCheck) {

        byte[] btValue;
        try {
            // String����{��EUC��byte[]�ɕϊ�����B
            btValue = prm_sCheck.getBytes("EUCJIS");

            // �S�p�������ӂ��܂�Ă���ꍇ
            for (int i = 0; i < btValue.length; i++) {
                if (btValue[i] < 0) { // btValue[i]��8�r�b�g�ڂ������Ă���B
                    return true;
                }
            }
        } catch (Exception Err) {
        }

        return false;
    }

    public boolean canNegative() {
        return this.canNegative;
    }

    public void setCanNegative(boolean canNegative) {
        this.canNegative = canNegative;
    }
}
