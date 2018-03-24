package server.essp.timesheet.code.codevalue.service;

import java.util.List;

import c2s.dto.ITreeNode;
import c2s.essp.timesheet.code.DtoCodeValue;

public interface ICodeValueService {

    /**
     * 根据CodeType的Rid列出对应的CodeValue信息
     * @param codeTypeRid Long
     * @return List
     * @throws BusinessException
     */
    public List listLeaveCodeValue();

    /**
     * 获取Job Code Value根节点
     * @return ITreeNode
     */
    public ITreeNode getJobCoeValueTree();
    
    /**
     * 获取Leave Code Value根节点
     * @return
     */
    public ITreeNode getLeaveCodeValueTree();
    /**
     * 更新CodeValue信息
     * @param dtoCodeValue DtoCodeValue
     * @throws BusinessException
     */
    public void save(DtoCodeValue dtoCodeValue);

    /**
     * 保存整棵树的Enable属性
     * @param root ITreeNode
     */
    public void saveEnables(ITreeNode root);

    /**
    * 上移CodeValue信息
    * @param rid Long
    * @throws BusinessException
    */
   public void moveUpCodeValue(DtoCodeValue dtoCodeValue);

   /**
    * 下移CodeValue信息
    * @param rid Long
    * @throws BusinessException
    */
   public void moveDownCodeValue(DtoCodeValue dtoCodeValue);
   
   /**
    * 左移CodeValue信息
    * @param dtoCodeValue
    */
   public void moveLeftCodeValue(DtoCodeValue dtoCodeValue);
   
   /**
    * 右移CodeValue信息
    * @param dtoCodeValue
    */
   public void moveRightCodeValue(DtoCodeValue dtoCodeValue);
   
   

    /**
     * 删除CodeValue信息
     * @param dtoCodeValue DtoCodeValue
     * @throws BusinessException
     */
    public void delete(DtoCodeValue dtoCodeValue);

    /**
     * 删除CodeValue及其子孙节点信息信息
     * @param node ITreeNode
     * @throws BusinessException
     */
    public void delete(ITreeNode node);
    
    /**
     * 根据Rid获取CodeValue记录
     * @param rid Long
     * @return TsCodeValue
     */
    public DtoCodeValue getCodeValue(Long rid);


}
