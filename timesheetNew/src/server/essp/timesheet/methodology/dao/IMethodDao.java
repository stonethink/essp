package server.essp.timesheet.methodology.dao;

import java.util.List;

import db.essp.timesheet.TsMethod;

public interface IMethodDao {
	
	/**
	 * ɾ������
	 * @param method TsMethod
	 */
	 public void deleteMethodType(TsMethod method);
	 
	 /**
	  * ����rid��ȡ����
	  * @param rid Long
	  * @return TsMethod
	  */
	 public TsMethod getMethodType(Long rid);
	 
	 /**
	  * �޸Ķ���
	  * @param tsMethod TsMethod
	  */
	 public void updateMethodType(TsMethod tsMethod);
	 
	 /**
	  * ��������
	  * @param tsMethod TsMethod
	  */
	 public Long addMethodType(TsMethod tsMethod);
	 
	 /**
	  * �г����е�methodology
	  * @return
	  */
	 public List listAllMethodType();
	 
	 /**
	  * ����rid��������Ӧ�õ���template
	  * @param rid Long	
	  * @return List
	  */
	 public List getTemplateByMethod(Long rid);
	 
	 /**
	  * �г����п��õ�methodology
	  * @return
	  */
	 public List listNormalMethod();

}
