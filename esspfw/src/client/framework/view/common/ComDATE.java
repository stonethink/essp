package client.framework.view.common;

import java.util.Calendar;
import java.text.*;
import org.apache.log4j.*;

/**
 *<BR>
 *�@�T�v<BR>
 *<BR>
 *�@�@���ʃ��W���[���i���t�֘A�j<BR>
 *<BR>
 *�@�ύX����<BR>
 *<BR>
 *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
 *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
 *�@�@00.00�@�@2004/06/21�@Tsukamoto�@�@�V�K�쐬<BR>
 *�@�@00.01�@�@2004/11/16�@Yery     �@�@\u589E��getCurrentTime<BR>
 *<BR>
 */
public class ComDATE
{
        static Category log = Category.getInstance(ComDATE.class.getName());

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[�֐�<BR>
         *�@�������@�F�@�����񐔒l��ύX����<BR>
         *�@���@�l�@�F�@���t"/"�ҏW
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2002/05/30�@�󗈁@�K�O�@�@�V�K�쐬<BR>
         *<BR>
         */
        public static String tofromatDate (String sDate2 ) {
                String	sResult;
                String sDate=sDate2;
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
         *�@�������@�F�@�N�擾����<BR>
         *�@���@�l�@�F�@�N��������N���擾����i��^NULL�̏ꍇ�́A""��Ԃ��j
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2003/03/10�@�󗈁@�K�O�@�@�V�K�쐬<BR>
         *<BR>
         * @param	sDate�@�@�@IN�@�F�@�N����
         */
        public static String getYear( String sDate ) {
                if ( ComDATA.nvl( sDate ).equals( "" ) == true ) {
                        return "";
                }
                return  sDate.substring( 0, 4 );
        }

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@�N�擾����<BR>
         *�@���@�l�@�F�@�N�������猎���擾����i��^NULL�̏ꍇ�́A""��Ԃ��j
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2003/03/10�@�󗈁@�K�O�@�@�V�K�쐬<BR>
         *<BR>
         * @param	sDate�@�@�@IN�@�F�@�N����
         */
        public static String getMonth( String sDate ) {
                if ( ComDATA.nvl( sDate ).equals( "" ) == true ) {
                        return "";
                }
                return  sDate.substring( 4, 6 );
        }

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@���t�f�[�^�`�F�b�N<BR>
         *�@���@�l�@�F�@���͂��ꂽ���t�f�[�^�����������`�F�b�N����B<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
         *<BR>
         * @param   iYear�@IN�@	�F�@�N�f�[�^
         * @param   iMonth�@IN�@�F�@���f�[�^
         * @param   iDay�@IN�@	�F�@���f�[�^
         * @return 	true��false��Ԃ��B
         */
        public static boolean checkDate(
                String  sInDate
        ){
                int	iYear  = Integer.parseInt( sInDate.substring( 0, 4 ));
                int	iMonth = Integer.parseInt( sInDate.substring( 4, 6 ));
                int	iDay   = Integer.parseInt( sInDate.substring( 6, 8 ));
                int	iHizuke;

                //++****************************************
                //	�N�̔���i1900�`2050�͈͓̔��ł��邩�H�j
                //--****************************************
                if (( iYear < 1900 ) || ( iYear > 2050 )) {
                        return false;
                }

                //++****************************************
                //	���̔���i1�`12���͈͓̔��ł��邩�H�j
                //--****************************************
                if (( iMonth < 1 ) || ( iMonth > 12 )) {
                        return false;
                }

                //++**********************************************
                //	���̔���i1�`���̍ő�����͈͓̔��ł��邩�H�j
                //--**********************************************
                iHizuke = getDate2( iYear, iMonth );
                if (( iDay < 1 ) || ( iDay > iHizuke )) {
                        return false;
                } else {
                        return true;
                }
        }

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@���̍ő�����̏o��<BR>
         *�@���@�l�@�F�@���͂��ꂽ�N�ƌ��ɂ���Ă��̌��̍ő������Ԃ��B<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2002/06/01�@�󗈁@�K�O�@�@�V�K�쐬<BR>
         *<BR>
         * @param   iYear2�@IN�@	�F�@�N�f�[�^
         * @param   iMonth2�@IN�@�F�@���f�[�^
         * @return 	���̍ő������Ԃ��B
         */
         private static int getDate2( int iYear2, int iMonth2 ){
                //++***********************************
                //	���̔���i�ő�������R�O���H�j
                //--***********************************
                if ( iMonth2 == 4 || iMonth2 == 6 || iMonth2 == 9 || iMonth2 == 11 ) {
                        return 30;
                }

                //++***********************************
                //	���̔���i2���ȊO�̌����H�j
                //--***********************************
                if ( iMonth2 != 2 ) {
                        return 31;
                }

                //++***********************************
                //	���̔���i���邤�N�̂Q�����H�j
                //--***********************************
                if ( iYear2%400 == 0 || ((iYear2%100 != 0)&&(iYear2%4 == 0 ))) {
                        return 29;
                } else {
                        return 28;
                }
        }

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@���t���v�Z����<BR>
         *�@���@�l�@�F�@�C�ӂ̓����オ�����ɂ�����̂����v�Z����<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.01�@�@2002/09/10�@�󗈁@�K�O�@�@�V�K�쐬<BR>
         *<BR>
         * @param	Date�@�@	  IN�@�F�@�������t��
         * @param	iAfterDay�@�@ IN�@�F�@���Z�����i�}�C�i�X���͂��n�j�j
         * @return	�������ʁ@�@sRetDate�@�@���Z����t��
         */
        public static String afterDate(
                String	date1,
                int	iAfterDay
        ) {
                String	sRetDate = "";
                Calendar cal = Calendar.getInstance();

                cal.set( Integer.parseInt( date1.substring( 0, 4 )),
                         Integer.parseInt( date1.substring( 4, 6 ))-1,
                         Integer.parseInt( date1.substring( 6, 8 ))
                );

                cal.add( Calendar.DATE , iAfterDay );

                String	sRetYear  = ComDATA.zeroFormat( cal.get( Calendar.YEAR ), 4 );
                String	sRetMonth = ComDATA.zeroFormat( cal.get( Calendar.MONTH ) + 1,2 );
                String	sRetDay   = ComDATA.zeroFormat( cal.get( Calendar.DATE ),2 );

                sRetDate = sRetYear + sRetMonth + sRetDay;

                return sRetDate;
        }

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@���t���v�Z����<BR>
         *�@���@�l�@�F�@�C�ӂ̓�����i�����w��j�������ɂ�����̂����v�Z����<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.01�@�@2002/09/10�@�󗈁@�K�O�@�@�V�K�쐬<BR>
         *<BR>
         * @param	Date�@�@	  IN�@�F�@�������t��
         * @param	iAfterMonth�@ IN�@�F�@���Z�����i�}�C�i�X���͂��n�j�j
         * @param	iAfterDay�@�@ IN�@�F�@���Z�����i�}�C�i�X���͂��n�j�j
         * @return	�������ʁ@�@sRetDate�@�@���Z����t��
         */
        public static String afterMonthDate(
                String	date,
                int	iAfterMonth,
                int	iAfterDay
        ) {
                String	sRetDate = "";
                Calendar cal = Calendar.getInstance();

                cal.set( Integer.parseInt( date.substring( 0, 4 )),
                         Integer.parseInt( date.substring( 4, 6 ))-1,
                         Integer.parseInt( date.substring( 6, 8 ))
                );

                cal.add( Calendar.MONTH , iAfterMonth );
                cal.add( Calendar.DATE , iAfterDay );

                String	sRetYear  = ComDATA.zeroFormat( cal.get( Calendar.YEAR ), 4 );
                String	sRetMonth = ComDATA.zeroFormat( cal.get( Calendar.MONTH ) + 1,2 );
                String	sRetDay   = ComDATA.zeroFormat( cal.get( Calendar.DATE ),2 );

                sRetDate = sRetYear + sRetMonth + sRetDay;

                return sRetDate;
        }

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@���t���v�Z����<BR>
         *�@���@�l�@�F�@�C�ӂ̓�����i�N�����w��j�������ɂ�����̂����v�Z����<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.01�@�@2002/09/10�@�󗈁@�K�O�@�@�V�K�쐬<BR>
         *<BR>
         * @param	Date�@�@	  IN�@�F�@�������t��
         * @param	iAfterYear�@  IN�@�F�@���Z�N���i�}�C�i�X���͂��n�j�j
         * @param	iAfterMonth�@ IN�@�F�@���Z�����i�}�C�i�X���͂��n�j�j
         * @param	iAfterDay�@�@ IN�@�F�@���Z�����i�}�C�i�X���͂��n�j�j
         * @return	�������ʁ@�@sRetDate�@�@���Z����t��
         */
        public static String afterYMD(
                String	date,
                int	iAfterYear,
                int	iAfterMonth,
                int	iAfterDay
        ) {

                String	sRetDate = "";
                Calendar cal = Calendar.getInstance();

                cal.set( Integer.parseInt( date.substring( 0, 4 )),
                         Integer.parseInt( date.substring( 4, 6 ))-1,
                         Integer.parseInt( date.substring( 6, 8 ))
                        );

                cal.add( Calendar.YEAR , iAfterYear );
                cal.add( Calendar.MONTH , iAfterMonth );
                cal.add( Calendar.DATE , iAfterDay );

                String	sRetYear  = ComDATA.zeroFormat( cal.get( Calendar.YEAR ), 4 );
                String	sRetMonth = ComDATA.zeroFormat( cal.get( Calendar.MONTH ) + 1,2 );
                String	sRetDay   = ComDATA.zeroFormat( cal.get( Calendar.DATE ),2 );

                sRetDate = sRetYear + sRetMonth + sRetDay;

                return sRetDate;
        }

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@�{�����t������擾<BR>
         *�@���@�l�@�F�@<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2000/09/07�@�󗈁@�K�O�@�@�V�K�쐬<BR>
         *<BR>
         * @param	�Ȃ�
         * @return	�{�����t������( YYYYMMDD )
         */
        public static String getTodayStr( ) {
                SimpleDateFormat clsFormat;
                clsFormat = new SimpleDateFormat( "yyyyMMdd" );
                return clsFormat.format( new java.util.Date() );
        }

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@���ԓ����擾<BR>
         *�@���@�l�@�F�@���͂��ꂽ�N�̊��ԓ�����Ԃ��B<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2004/08/24�@Tsuka�@�@�V�K�쐬<BR>
         *<BR>
         * @param   Year1�@IN�@�F�@�J�n�N�f�[�^
         * @param   Year2�@IN�@�F�@�I���N�f�[�^
         * @return 	���ԓ�����Ԃ��B
         */
         public static long getDays( String yearS, String yearE ){
                int  yy = 0;
                int  mm = 0;
                int  dd = 0;
                long iMSDays  = 0L;
                long iMSStart = 0L;
                long iMSEnd   = 0L;
                String buff = new String("");

                log.debug("getDays / prm : " + yearS + " , " + yearE);

                Calendar cl = Calendar.getInstance();

                // ���t(string)��N�E���E��(int)�ɕϊ�
                // �J�n��
                buff = new String( yearS.toCharArray() , 0 ,4 );
                yy = Integer.valueOf( buff ).intValue();
                buff = new String( yearS.toCharArray() , 4 ,2 );
                mm = Integer.valueOf( buff ).intValue();
                buff = new String( yearS.toCharArray() , 6 ,2 );
                dd = Integer.valueOf( buff ).intValue();
                cl.set( yy , mm - 1 , dd );
                iMSStart = (cl.getTime()).getTime();
                log.debug("getDays / MS_start : " + iMSStart);

                // �I����
                buff = new String( yearE.toCharArray() , 0 ,4 );
                yy = Integer.valueOf( buff ).intValue();
                buff = new String( yearE.toCharArray() , 4 ,2 );
                mm = Integer.valueOf( buff ).intValue();
                buff = new String( yearE.toCharArray() , 6 ,2 );
                dd = Integer.valueOf( buff ).intValue();
                cl.set( yy , mm - 1 , dd );
                iMSEnd = (cl.getTime()).getTime();
                log.debug("getDays / MS_end : " + iMSEnd);

                // �J�n��>�I�����̏ꍇ�G���[
                if(iMSStart > iMSEnd) {
                        iMSDays = -1;
                } else {
                        // �I�u�W�F�N�g�Ԃ̓����i�~���b���P���̃~���b�Ŋ���j�����߂�
                        iMSDays = Math.abs(iMSStart - iMSEnd ) / 86400000L;
                        log.debug("getDays / MS_days : " + iMSDays);
                }
                return iMSDays;
        }

        /**
         *<BR>
         *�@�^�C�v�@�F�@���[�U�[��`<BR>
         *�@�������@�F�@���ݎ��ԓ��t������擾<BR>
         *�@���@�l�@�F�@<BR>
         *<BR>
         *�@�ύX����<BR>
         *<BR>
         *�@�@Version�@�@���@�t�@�@�@�X�V�ҁ@�@�@�@�@�R�����g<BR>
         *�@�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|�|<BR>
         *�@�@00.00�@�@2004/11/16�@Yery�@�@�V�K�쐬<BR>
         *<BR>
         * @param	�Ȃ�
         * @return	���ݎ��ԓ��t������( yy/MM/dd HH:mm:ss )
         */
        public static String getCurrentTime( ) {
                SimpleDateFormat clsFormat;
                clsFormat = new SimpleDateFormat( "yy/MM/dd�@HH:mm:ss" );
                return clsFormat.format( new java.util.Date() );
        }

}
