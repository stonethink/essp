package server.essp.timesheet.account.service;

import java.util.List;
import c2s.essp.timesheet.account.DtoAccount;

/**
 * <p>Title: Account service interface</p>
 *
 * <p>Description: 与项目相关的业务逻辑服务</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IAccountService {

    /**
     * 列出当前用户为项目经理的状态正常的项目
     * @param loginId String 
     * @param isPmo boolean
     * @return List
     */
    public List listAccounts(String loginId, boolean isPmo);

    /**
     * 获取项目信息
     * @param AccountRid Long
     * @return DtoAccount
     */
    public DtoAccount loadAccount(Long accountRid);

    /**
     * 保存项目信息
     * @param Account DtoAccount
     */
    public void saveAccount(DtoAccount account);
    
    /**
     * 根据条件查询Accounts
     * @param dto
     * @return
     */
    public List queryAccounts(DtoAccount dto, String loginId);
}
