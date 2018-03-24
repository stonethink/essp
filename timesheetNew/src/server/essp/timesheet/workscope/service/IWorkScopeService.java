package server.essp.timesheet.workscope.service;

import java.util.List;
/**
 * <p>Title: IWorkScopeService��</p>
 *
 * <p>Description:��P3��ESSP�л�÷������������� </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author tbh
 * @version 1.0
 */
public interface IWorkScopeService {

    /**
     * �õ�ESSP��P3�е���Ŀ���ϵĲ�,����õ���P3�е���Ŀ��TS_ACCOUNT�д���ȡ����,��������
     * @param loginId String
     * @return List
     */
    public List getAccountList(String loginId);

    /**
     * �г�Ϊ�ļٱ�List
     * @param leaveCodeTypeRid Long
     * @return List
     */
    public List getCodeLeave(Long leaveCodeTypeRid);

    /**
     * �õ�����������CODEVALUE��¼
     * 1.������Ŀ���ŵ�TSACCOUNT�еõ���Ӧ��CodeTypeRid,����CodeTypeRid��CodeRelation�в�ѯ���������ļ�¼
     * 2.��������ҵõ���CODEVALUE�����Ҷ�������codeTypeRid��CODEVALUE�еõ���Ӧ�ļ�¼��
     * 3.�����������ٵ�CODETYPE�в�ѯ
     * @param accountId String
     * @return List
     */
    public List getJobCode(Long codeTypeRid);
    
    /**
     * ����ActivityId��ȡActivityShowName,���û�ҵ��򷵻ؿ��ַ���
     * @param activityId Long
     * @return String
     */
    public String getActivityShowName(Long activityId);
    
    /**
     * ����acntRid��ȡAccountShowName,���û�ҵ��򷵻ؿ��ַ���
     * @param acntRid Long
     * @return String
     */
    public String getAccountShowName(Long acntRid);
}
