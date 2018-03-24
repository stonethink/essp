package com.wits.util;

import java.util.*;

/**
 * <p> �ṩ����/���ܴ�������: ���ܺ���: encrypt(String sPass),�����̼����ִ�����ֵΪ���ܺ���ִ� ���ܺ���: decrypt(String sCode),�����̽����ִ�����ֵΪ���ܺ���ִ�
 * �ȶ�����/����ƥ��:DecodeMatch(String bfStr, String afStr), ����Ϊ����,����,�ȶ������Ƿ�����, ƥ��ʱ����Ϊtrue,���򷵻�Ϊfalse </p>
 * @version 1.0
 * @date 2003/03/06
 * @author Stone
 */
public class Decode_nohex {
    private static final int MAX_PASS_LEN = 256;
    private static char[] g_caCodeTbl = new char[65];

    public static void main(String[] args) {
        String inStr;
        if (args.length != 1) {
            inStr = "stone  xx";
            //inStr = null;
        } else {
            inStr = args[0];
        }
        System.err.println("Input string is:\n" + inStr);
        String newStr = Decode_nohex.encrypt(inStr);
        System.err.println("The encrypted string is:\n" + newStr);
        String oldStr = Decode_nohex.decrypt(newStr);
        System.err.println("The decrypted string is:\n" + oldStr);
        if (DecodeMatch(inStr, newStr)) {
            System.err.println("Test successfully");
        } else {
            System.err.println("Test failed");
        }
    }

    /**
     *���ܺ���: encrypt(String sPass),�����̼����ִ�����ֵΪ���ܺ���ִ�
     *@sPass���̼����ִ�
     */
    public static String encrypt(String sPass) {
        int i, j;
        int shift;
        long accum, value;
        char ucKey;
        String sCode = new String("");

        System.out.println("In  encrypt =[" + sPass +"]");

        if (sPass == null) return null;
        int iPassLen = sPass.length();
        if (iPassLen == 0) return sCode;

        char[] pcaPass = sPass.toCharArray();
        char[] pcaCode = new char[MAX_PASS_LEN];
        Initial_CodeTbl();
        ucKey = MakeKey();
        Rebuild_CodeTbl(ucKey);
        j = 1;
        accum = value = shift = 0;
        pcaCode[0] = (char)ucKey;
        //System.err.print("code[0]= "+pcaCode[j-1]+"\n");
        for (i = 0; i <= iPassLen; i++) {
            if (i != iPassLen) {
                value = (long)pcaPass[i];
                //System.err.println("pass["+i+"]="+pcaPass[i]+"  v="+value);
            } else {
                value = 0;
            }
            //value = (long) pcaPass[i];
            accum <<= 8;
            accum |= value;
            shift += 8;
            while (shift >= 6) {
                shift -= 6;
                value = (accum >> shift);
                value &= 0x3F;
                //System.err.print("v="+value);
                pcaCode[j++] = g_caCodeTbl[(int)value];
                //System.err.println("code["+(j-1)+"]= "+pcaCode[j-1]);
            }
        }

        System.out.println("Out encrypt =[" + (new String(pcaCode)) +"]");
        //System.err.println("\n end encode");
        return (String.copyValueOf(pcaCode, 0, j));
    }
    //End encode

    /**
     *���ܺ���: decrypt(String sCode),�����̽����ִ�����ֵΪ���ܺ���ִ�
     *@sCode���̽����ִ�
     */
    //Begin decode
    public static String decrypt(String sCode) {
        int i, j;
        long accum, value, shift;
        char ucKey;
        String sPass = new String("");

        System.out.println("In  decrypt =[" + sCode +"]");

        if (sCode == null) return null;
        int iCodeLen = sCode.length();
        if (iCodeLen == 0) return sPass;

        char[] caTmpCode = new char[MAX_PASS_LEN];
        char[] pcaCode = sCode.toCharArray();
        char[] pcaPass = new char[MAX_PASS_LEN];

        Initial_CodeTbl();
        //ucKey = GetKey(sCode);
        ucKey = pcaCode[0];
        System.out.println("decrypt getKey =" + ucKey);
        Rebuild_CodeTbl(ucKey);
        //memcpy (caTmpCode, pcaCode+1, sizeof(caTmpCode));
        //for (i=0;i<iCodeLen-1;i++) caTmpCode[i] = pcaCode[i+1];
        //caTmpCode[i+1] = '\0';
        //for (i=0;i<iCodeLen;i++) System.err.println("caTmpCode["+i+"]="+caTmpCode[i]);
        accum = value = shift = j = 0;
        // for ( i = 0; i<iCodeLen; i++ )
        for (i = 1; i < iCodeLen; i++) {
            value = cvt_code(pcaCode[i]);
            //System.err.println("code=["+i+"]="+pcaCode[i]+"  v="+value);
            if (value < 64) {
                accum <<= 6;
                shift += 6;
                accum |= value;
                if (shift >= 8) {
                    shift -= 8;
                    value = accum >> shift;
                    value &= 0xFF;
                    //System.err.print("v="+value);
                    pcaPass[j++] = (char)value;
                    //System.err.println("pass=["+(j-1)+"]="+pcaPass[j-1]);
                }
            } else {
                break;
            }
        }
        System.out.println("Out decrypt =[" + (new String(pcaPass)) + "]");

        if (pcaPass[j - 1] == 0)
            return (String.copyValueOf(pcaPass, 0, j - 1));
        else
            return (String.copyValueOf(pcaPass, 0, j));
    }

    /**
     *�ȶ�����/����ƥ��:DecodeMatch(String bfStr, String afStr),
     *@bfStr Ϊ����,
     *@afStr Ϊ����, �ȶ������Ƿ�����,ƥ��ʱ����Ϊtrue,���򷵻�Ϊfalse
     */
    public static boolean DecodeMatch(String bfStr, String afStr) {
        boolean bMatch = false;
        String tempStr = null;

        if(bfStr == null||afStr == null) return false;

        tempStr = decrypt(afStr);
        if (tempStr.compareTo(bfStr) == 0) {
            bMatch = true;
        }
        return bMatch;
    }

    // Begin Initial_codeTbl
    private static void Initial_CodeTbl() {
        int i;
        for (i = 0; i < 64; i++) {
            if (i == 0)
                g_caCodeTbl[i] = 'A';
            else if (i == 26)
                g_caCodeTbl[i] = 'a';
            else if (i == 52)
                g_caCodeTbl[i] = '0';
            else if (i == 62)
                g_caCodeTbl[i] = '+';
            else if (i == 63)
                g_caCodeTbl[i] = '/';
            else
                g_caCodeTbl[i] = (char)(g_caCodeTbl[i - 1] + 1);
            //System.err.print(g_caCodeTbl[i]);
        }
        //System.err.println("test init"+"\n");
    }

    // End Initial_codeTbl
    //Begin Rebuild_CodeTbl
    private static void Rebuild_CodeTbl(char ucKey2) {
    	char ucKey=ucKey2;
        int i, j;
        char[] caBuf = new char[65];
        //memset (caBuf, 0, sizeof(caBuf));
        for (i = 0; i < 64; i++)
            caBuf[i] = 0;
        while (ucKey >= 64)
            ucKey -= 64;
        for (i = ucKey, j = 0; i < 64; i++, j++) {
            caBuf[j] = g_caCodeTbl[i];
        }
        for (i = 0; i < ucKey; i++, j++) {
            caBuf[j] = g_caCodeTbl[i];
        }
        //memcpy (g_caCodeTbl, caBuf, sizeof(caBuf));
        for (i = 0; i < 64; i++) {
            g_caCodeTbl[i] = caBuf[i];
            //System.err.print(g_caCodeTbl[i]);
        }
    }

    //End Rebuild_CodeTbl
    //Begin cvt_code
    private static int cvt_code(char code) {
        int i;
        for (i = 0; i < 64; i++)
            if (code == g_caCodeTbl[i])
                return (i);
        return (-1);
    }

    //End cvt_code
    //Begin MakeKey
    private static char MakeKey() {
        //long lTime;
        char ucKey;
        long lRand;
        //System.err.println("Begin in makeKey");
        //lTime = Calendar.time;//java.util.Date
        //srand (iTime % 1000);
        Random random = new Random();
        lRand = (random.nextInt()) % 1000; //java.util.Random
        ucKey = (char)(lRand % 26 + 65);
        // System.err.println("iRand="+lRand);
        // System.err.println("ucKey="+ucKey);
        return (ucKey);
    }

    //End MakeKey
    //Begin GetKey
    private static char GetKey(String sCode) {
        char ucKey;
        char[] caCode = sCode.toCharArray();
        ucKey = caCode[0];
        return (ucKey);
    }

    //End GetKey

     private static String string2Hex(String sIn){
     	return sIn;

     }

      private static String hex2String(String sIn){
     	return sIn;

      }
}