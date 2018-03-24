package server.essp.timesheet.methodology.dao;

import java.util.List;

import db.essp.timesheet.TsMethod;

public interface IMethodDao {
	
	/**
	 * 删除对象
	 * @param method TsMethod
	 */
	 public void deleteMethodType(TsMethod method);
	 
	 /**
	  * 根据rid获取数据
	  * @param rid Long
	  * @return TsMethod
	  */
	 public TsMethod getMethodType(Long rid);
	 
	 /**
	  * 修改对象
	  * @param tsMethod TsMethod
	  */
	 public void updateMethodType(TsMethod tsMethod);
	 
	 /**
	  * 新增对象
	  * @param tsMethod TsMethod
	  */
	 public Long addMethodType(TsMethod tsMethod);
	 
	 /**
	  * 列出所有的methodology
	  * @return
	  */
	 public List listAllMethodType();
	 
	 /**
	  * 根据rid查找所有应用到的template
	  * @param rid Long	
	  * @return List
	  */
	 public List getTemplateByMethod(Long rid);
	 
	 /**
	  * 列出所有可用的methodology
	  * @return
	  */
	 public List listNormalMethod();

}
