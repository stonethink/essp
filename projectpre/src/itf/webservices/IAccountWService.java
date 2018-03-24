package itf.webservices;

import c2s.essp.common.account.IDtoAccount;
import java.util.Map;

/**
 * ϵͳ�����Account����ͬ��ʱ,ʹ��WebService��ʽ,����ϵͳ�ṩ�Ľӿ�
 * ע��:����WebServiceֻ������ñ�¶�ķ���,���ᴦ������,���Ը�������Ҫ�Լ���������
 * @author not attributable
 * @version 1.0
 */
public interface IAccountWService {
        /**
         * �ⲿϵͳ�ڱ�ϵͳ������һ��Account
         * @param account IDtoAccount
         */
        public void addAccount (Map accountFields);
        /**
         * �ⲿϵͳ����ϵͳ�е�һ��Account
         * @param account IDtoAccount
         */
        public void updateAccount (Map accountFields);
        /**
         * �ⲿϵͳ�Ա�ϵͳ�е�Account�᰸
         * @param acntId String
         */
        public void closeAccount (String acntId);
}
