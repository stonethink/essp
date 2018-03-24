package server.essp.timesheet.account.labor.service;

import java.util.List;
import c2s.essp.timesheet.labor.DtoLaborResource;

/**
 * <p>Title: labor resource service interface</p>
 *
 * <p>Description: ����Ŀ������Դ��ص�ҵ���߼�����</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ILaborService {

    /**
     * �г�ָ����Ŀ�µ�������Դ
     * @param AccountRid Long
     * @return List
     */
    public List listLabor(Long accountRid);

    /**
     * ��ָ��������Դ����Ŀ��ɾ��
     * @param labor DtoLaborResource
     */
    public void deleteLabor(DtoLaborResource labor);

    /**
     * Ϊĳ��Ŀ����������Դ
     * @param labor DtoLaborResource
     */
    public void addLabor(DtoLaborResource labor);
    
    /**
     * ���Ӷ����Ա
     * @param loginIds
     * @param rid
     */
    public List addLabors(String loginIds, Long rid);

    /**
     * ����������Դ��Ϣ
     * @param labor DtoLaborResource
     */
    public void saveLabor(DtoLaborResource labor);
    
    /**
     * ��ר�������ѯ��Ա
     * @param accountId
     * @return
     */
    public List getPersonInProject(String accountId);
}
