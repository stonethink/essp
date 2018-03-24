package itf.webservices;

import c2s.essp.common.account.IDtoAccount;
import java.util.Map;

/**
 * 系统间进行Account数据同步时,使用WebService方式,各子系统提供的接口
 * 注意:由于WebService只负责调用暴露的方法,不会处理事务,所以各方法内要自己处理事务
 * @author not attributable
 * @version 1.0
 */
public interface IAccountWService {
        /**
         * 外部系统在本系统中增加一个Account
         * @param account IDtoAccount
         */
        public void addAccount (Map accountFields);
        /**
         * 外部系统更新系统中的一个Account
         * @param account IDtoAccount
         */
        public void updateAccount (Map accountFields);
        /**
         * 外部系统对本系统中的Account结案
         * @param acntId String
         */
        public void closeAccount (String acntId);
}
