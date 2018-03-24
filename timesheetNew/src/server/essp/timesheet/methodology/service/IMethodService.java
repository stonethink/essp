package server.essp.timesheet.methodology.service;

import java.util.List;
import java.util.Vector;

import server.essp.timesheet.methodology.form.AfMethod;
import server.essp.timesheet.methodology.form.VbMethod;

public interface IMethodService {	
	
	/**
	 * 保存新创建的methodology
	 * @param form
	 */
    public Long saveMethod(AfMethod form);  
    
    /**
     * 根据rid获取当前的methodology
     * @param rid Long
     * @return VbMethod
     */
    public VbMethod getMethod(Long rid);
    
    /**
     * 根据rid删除methodology
     * @param rid
     */
    public void deleteMethod(Long rid);
    
    /**
     * 列出所有的methodology
     * @return
     */
    public List getMethodList();   
    
    /**
     * 通過方法庫類型獲得支持該方法庫的模版列表
     * @param methodRid
     * @return
     */
    public List getTemplateListByMethod(String methodRid); 
    
    /**
     * 根据项目代号获取相应摸版列表
     * @param acntId String
     * @return List
     */
    public List getTemplateListByAcntId(String acntId);
    
    /**
     * 获取methodology下拉框选项
     * @return
     */
    public Vector getMethodCmb();

}
