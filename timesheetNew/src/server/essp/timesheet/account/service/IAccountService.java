package server.essp.timesheet.account.service;

import java.util.List;
import c2s.essp.timesheet.account.DtoAccount;

/**
 * <p>Title: Account service interface</p>
 *
 * <p>Description: ����Ŀ��ص�ҵ���߼�����</p>
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
     * �г���ǰ�û�Ϊ��Ŀ�����״̬��������Ŀ
     * @param loginId String 
     * @param isPmo boolean
     * @return List
     */
    public List listAccounts(String loginId, boolean isPmo);

    /**
     * ��ȡ��Ŀ��Ϣ
     * @param AccountRid Long
     * @return DtoAccount
     */
    public DtoAccount loadAccount(Long accountRid);

    /**
     * ������Ŀ��Ϣ
     * @param Account DtoAccount
     */
    public void saveAccount(DtoAccount account);
    
    /**
     * ����������ѯAccounts
     * @param dto
     * @return
     */
    public List queryAccounts(DtoAccount dto, String loginId);
}
