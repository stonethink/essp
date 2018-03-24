package server.essp.timesheet.methodology.service;

import java.util.List;
import java.util.Vector;

import server.essp.timesheet.methodology.form.AfMethod;
import server.essp.timesheet.methodology.form.VbMethod;

public interface IMethodService {	
	
	/**
	 * �����´�����methodology
	 * @param form
	 */
    public Long saveMethod(AfMethod form);  
    
    /**
     * ����rid��ȡ��ǰ��methodology
     * @param rid Long
     * @return VbMethod
     */
    public VbMethod getMethod(Long rid);
    
    /**
     * ����ridɾ��methodology
     * @param rid
     */
    public void deleteMethod(Long rid);
    
    /**
     * �г����е�methodology
     * @return
     */
    public List getMethodList();   
    
    /**
     * ͨ�^��������ͫ@��֧��ԓ�������ģ���б�
     * @param methodRid
     * @return
     */
    public List getTemplateListByMethod(String methodRid); 
    
    /**
     * ������Ŀ���Ż�ȡ��Ӧ�����б�
     * @param acntId String
     * @return List
     */
    public List getTemplateListByAcntId(String acntId);
    
    /**
     * ��ȡmethodology������ѡ��
     * @return
     */
    public Vector getMethodCmb();

}
