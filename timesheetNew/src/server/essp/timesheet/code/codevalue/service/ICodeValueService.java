package server.essp.timesheet.code.codevalue.service;

import java.util.List;

import c2s.dto.ITreeNode;
import c2s.essp.timesheet.code.DtoCodeValue;

public interface ICodeValueService {

    /**
     * ����CodeType��Rid�г���Ӧ��CodeValue��Ϣ
     * @param codeTypeRid Long
     * @return List
     * @throws BusinessException
     */
    public List listLeaveCodeValue();

    /**
     * ��ȡJob Code Value���ڵ�
     * @return ITreeNode
     */
    public ITreeNode getJobCoeValueTree();
    
    /**
     * ��ȡLeave Code Value���ڵ�
     * @return
     */
    public ITreeNode getLeaveCodeValueTree();
    /**
     * ����CodeValue��Ϣ
     * @param dtoCodeValue DtoCodeValue
     * @throws BusinessException
     */
    public void save(DtoCodeValue dtoCodeValue);

    /**
     * ������������Enable����
     * @param root ITreeNode
     */
    public void saveEnables(ITreeNode root);

    /**
    * ����CodeValue��Ϣ
    * @param rid Long
    * @throws BusinessException
    */
   public void moveUpCodeValue(DtoCodeValue dtoCodeValue);

   /**
    * ����CodeValue��Ϣ
    * @param rid Long
    * @throws BusinessException
    */
   public void moveDownCodeValue(DtoCodeValue dtoCodeValue);
   
   /**
    * ����CodeValue��Ϣ
    * @param dtoCodeValue
    */
   public void moveLeftCodeValue(DtoCodeValue dtoCodeValue);
   
   /**
    * ����CodeValue��Ϣ
    * @param dtoCodeValue
    */
   public void moveRightCodeValue(DtoCodeValue dtoCodeValue);
   
   

    /**
     * ɾ��CodeValue��Ϣ
     * @param dtoCodeValue DtoCodeValue
     * @throws BusinessException
     */
    public void delete(DtoCodeValue dtoCodeValue);

    /**
     * ɾ��CodeValue��������ڵ���Ϣ��Ϣ
     * @param node ITreeNode
     * @throws BusinessException
     */
    public void delete(ITreeNode node);
    
    /**
     * ����Rid��ȡCodeValue��¼
     * @param rid Long
     * @return TsCodeValue
     */
    public DtoCodeValue getCodeValue(Long rid);


}
