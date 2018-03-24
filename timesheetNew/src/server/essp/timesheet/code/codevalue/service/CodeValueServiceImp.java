package server.essp.timesheet.code.codevalue.service;

import java.util.ArrayList;
import java.util.List;

import c2s.dto.DtoTreeNode;
import c2s.dto.DtoUtil;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.code.DtoCodeValue;
import db.essp.timesheet.TsCodeValue;
import server.essp.timesheet.code.codevalue.dao.ICodeValueDao;
import server.framework.common.BusinessException;
import c2s.dto.IDto;

public class CodeValueServiceImp implements ICodeValueService{

    private ICodeValueDao codeValueDao;

    /**
     * ����CodeValue��Ϣ
     * @param dtoCodeValue DtoCodeValue
     * @throws BusinessException
     */
    public void save(DtoCodeValue dtoCodeValue) {
        if(dtoCodeValue.isInsert()) {
        	if (dtoCodeValue.getParentRid() != null) {
				if (dtoCodeValue.getIsLeaveType()) {
					dtoCodeValue.setSeq(codeValueDao.getJobCodeMaxSeq(
							dtoCodeValue.getParentRid(),
							DtoCodeValue.DTO_LEAVE_TYPE) + 1);
				} else {
					dtoCodeValue.setSeq(codeValueDao.getJobCodeMaxSeq(
							dtoCodeValue.getParentRid(),
							DtoCodeValue.DTO_NON_LEAVE_TYPE) + 1);
				}
			} else {
				dtoCodeValue.setSeq(Long.valueOf(1));
			}

			TsCodeValue bean = dto2Bean(dtoCodeValue);
			codeValueDao.add(bean);
			DtoUtil.copyBeanToBean(dtoCodeValue, bean);
			
        } else {
            codeValueDao.update(dto2Bean(dtoCodeValue));
        }
    }

    /**
	 * ������������Enable����
	 * 
	 * @param root
	 *            ITreeNode
	 */
    public void saveEnables(ITreeNode root) {
        saveEnable(root);
    }

    /**
     * ����Enable����
     * @param node ITreeNode
     */
    private void saveEnable(ITreeNode node) {
        if(node == null) return;
        DtoCodeValue dto = (DtoCodeValue) node.getDataBean();
        if(dto != null && dto.isInsert() == false) {
            codeValueDao.updateEnable(dto.getRid(), dto.getIsEnable());
        }
        //�����ӽڵ�
        List<ITreeNode> children = node.children();
        for(ITreeNode child : children) {
            saveEnable(child);
        }
    }


    /**
     * ɾ��CodeValue��Ϣ
     * @param rid Long
     * @throws BusinessException
     */
    public void delete(DtoCodeValue dtoCodeValue) {
        codeValueDao.delete(dto2Bean(dtoCodeValue));
    }

    /**
     * ɾ��CodeValue��������ڵ���Ϣ��Ϣ
     * @param node ITreeNode
     * @throws BusinessException
     */
    public void delete(ITreeNode node) {
        List<ITreeNode> list = node.listAllChildrenByPostOrder();
        list.add(node);
        for(ITreeNode child : list) {
            DtoCodeValue dto = (DtoCodeValue) child.getDataBean();
            if(!dto.isInsert()) {
                delete(dto);
            }
        }
    }


    /**
     * ����CodeType��Rid�г���Ӧ��CodeValue��Ϣ
     * @param codeTypeRid Long
     * @return List
     * @throws BusinessException
     */
    public List listLeaveCodeValue() {
        return beanList2DtoList(codeValueDao.listLeaveCodeValue());
    }

    /**
     * ��ȡJob Code Value���ڵ�
     * @return ITreeNode
     */
    public ITreeNode getJobCoeValueTree() {
        DtoCodeValue dtoRoot = bean2Dto(codeValueDao.getRootJobCodeValue(
        		DtoCodeValue.DTO_NON_LEAVE_TYPE));
        if(dtoRoot == null) return null;
        DtoTreeNode root = new DtoTreeNode(dtoRoot);
        getChildren(root, DtoCodeValue.DTO_NON_LEAVE_TYPE);
        return root;
    }
    
    /**
     * ��ȡ����ڵ�
     * @param node ITreeNode
     */
    private void getChildren(ITreeNode node, String isLeaveType) {
        DtoCodeValue dto = (DtoCodeValue) node.getDataBean();
        List<TsCodeValue> children =
            codeValueDao.listJobCodeValueByParentRid(dto.getRid(), isLeaveType);
        for(TsCodeValue child : children) {
            DtoTreeNode childNode = new DtoTreeNode(bean2Dto(child));
            node.addChild(childNode, IDto.OP_NOCHANGE);
            getChildren(childNode, isLeaveType);
        }
    }

    /**
     * ����CodeValue��Ϣ
     * @param rid Long
     * @throws BusinessException
     */
    public void moveUpCodeValue(DtoCodeValue dtoCodeValue) {
        TsCodeValue upBean;
        if(dtoCodeValue.getIsLeaveType()) {
        	upBean = codeValueDao.getUpSeqJobCodeValue(
                    dtoCodeValue.getParentRid(),dtoCodeValue.getSeq(), 
                    DtoCodeValue.DTO_LEAVE_TYPE);
        } else {
            upBean = codeValueDao.getUpSeqJobCodeValue(
                             dtoCodeValue.getParentRid(),dtoCodeValue.getSeq(), 
                             DtoCodeValue.DTO_NON_LEAVE_TYPE);
        }
        if(upBean == null) {
            throw new BusinessException("error.logic.common.isUppermost",
                                        "This Value is uppermost");
        }
        codeValueDao.setSeq(dtoCodeValue.getRid(), upBean.getSeq());
        codeValueDao.setSeq(upBean.getRid(), dtoCodeValue.getSeq());
    }

    /**
     * ����CodeValue��Ϣ
     * @param rid Long
     * @throws BusinessException
     */
    public void moveDownCodeValue(DtoCodeValue dtoCodeValue) {
        TsCodeValue downBean;
        if(dtoCodeValue.getIsLeaveType()) {
        	downBean = codeValueDao.getDownSeqJobCodeValue(
                    dtoCodeValue.getParentRid(),dtoCodeValue.getSeq(), 
                    DtoCodeValue.DTO_LEAVE_TYPE);
        } else {
            downBean = codeValueDao.getDownSeqJobCodeValue(
                             dtoCodeValue.getParentRid(),dtoCodeValue.getSeq(), 
                             DtoCodeValue.DTO_NON_LEAVE_TYPE);
        }
        if(downBean == null) {
            throw new BusinessException("error.logic.common.isDownmost",
                                        "This Value is downmost");
        }
        codeValueDao.setSeq(dtoCodeValue.getRid(), downBean.getSeq());
        codeValueDao.setSeq(downBean.getRid(), dtoCodeValue.getSeq());
    }
    
    /**
     * ����Rid��ȡCodeValue��¼
     * @param rid Long
     * @return TsCodeValue
     */
    public DtoCodeValue getCodeValue(Long rid) {
    	return bean2Dto(codeValueDao.getCodeValue(rid));
    }


    /**
     * ��TsCodeType listת����DtoCodeType list
     * @param beanList List
     * @return List
     */
    private static List beanList2DtoList(List<TsCodeValue> beanList) {
        List dtoList = new ArrayList();
        for(TsCodeValue bean : beanList) {
            dtoList.add(bean2Dto(bean));
        }
        return dtoList;
    }

    /**
     * ��TsCodeTypeת����DtoCodeType
     * @param bean TsCodeType
     * @return DtoCodeType
     */
    private static DtoCodeValue bean2Dto(TsCodeValue bean) {
        if(bean == null) return null;
        DtoCodeValue dto = new DtoCodeValue();
        DtoUtil.copyBeanToBean(dto, bean);
        return dto;
    }

    /**
     * ��DtoCodeTypeת����TsCodeType
     * @param dto DtoCodeType
     * @return TsCodeType
     */
    private static TsCodeValue dto2Bean(DtoCodeValue dto) {
        if(dto == null) return null;
        TsCodeValue bean = new TsCodeValue();
        DtoUtil.copyBeanToBean(bean, dto);
        return bean;
    }

    public void setCodeValueDao(ICodeValueDao codeValueDao) {
        this.codeValueDao = codeValueDao;
    }
    
    /**
     * ��ȡLeave Code�ĸ��ڵ�
     */
	public ITreeNode getLeaveCodeValueTree() {
		DtoCodeValue dtoRoot = bean2Dto(codeValueDao.getRootJobCodeValue(
        		DtoCodeValue.DTO_LEAVE_TYPE));
        if(dtoRoot == null) return null;
        DtoTreeNode root = new DtoTreeNode(dtoRoot);
        getChildren(root, DtoCodeValue.DTO_LEAVE_TYPE);
        return root;
	}
	
	/**
	 * ����
	 */
	public void moveLeftCodeValue(DtoCodeValue dtoCodeValue) {
		TsCodeValue leftBean;
		TsCodeValue fathBean;
		TsCodeValue grdBean;
		leftBean = codeValueDao.getCodeValue(dtoCodeValue.getRid());
		fathBean = codeValueDao.getCodeValue(dtoCodeValue.getParentRid());
		grdBean = codeValueDao.getCodeValue(fathBean.getParentRid());
		if(grdBean == null) {
			throw new BusinessException("error.logic.common.isLeftmost",
            								"This Value is leftmost");
		}
		//�ýڵ�֮����ֵܽڵ�seq-1(����һ��)
		List<TsCodeValue> oldChildren = codeValueDao
        							.getChildrenAfterSeq(dtoCodeValue.getParentRid(), dtoCodeValue.getSeq() + 1);
		if(oldChildren != null && oldChildren.size() > 0) {
			for(TsCodeValue oldChild : oldChildren) {
				codeValueDao.setSeq(oldChild.getRid(), oldChild.getSeq() - 1);
			}
		}
		//�ýڵ���뵽���ĸ��󣬸ýڵ����ֵ�seq+1(����һ��)
		List<TsCodeValue> newChildren = codeValueDao
		                              .getChildrenAfterSeq(grdBean.getRid(), fathBean.getSeq() + 1);
		if(newChildren != null && newChildren.size() > 0) {
			for(TsCodeValue child : newChildren) {
				codeValueDao.setSeq(child.getRid(), child.getSeq() + 1);
			}
		}
		//�ڸ��ĸ��²���Ҫ���ƵĽڵ�,seqΪ����seq+1
		leftBean.setParentRid(grdBean.getRid());
		leftBean.setSeq(fathBean.getSeq() + 1);
		codeValueDao.update(leftBean);
	}
	/**
	 * ����
	 */
	public void moveRightCodeValue(DtoCodeValue dtoCodeValue) {
		TsCodeValue upBean;
        if(dtoCodeValue.getIsLeaveType()) {
        	upBean = codeValueDao.getUpSeqJobCodeValue(
                    dtoCodeValue.getParentRid(),dtoCodeValue.getSeq(), 
                    DtoCodeValue.DTO_LEAVE_TYPE);
        } else {
            upBean = codeValueDao.getUpSeqJobCodeValue(
                             dtoCodeValue.getParentRid(),dtoCodeValue.getSeq(), 
                             DtoCodeValue.DTO_NON_LEAVE_TYPE);
        }
        if(upBean == null) {
			throw new BusinessException("error.logic.common.isRightmost",
            								"This Value is rightmost");
		}
		TsCodeValue rightBean;
		rightBean = codeValueDao.getCodeValue(dtoCodeValue.getRid());
		
		//�ýڵ�֮����ֵܽڵ�seq-1(����һ��)
		List<TsCodeValue> oldChildren = codeValueDao
        							.getChildrenAfterSeq(dtoCodeValue.getParentRid(), dtoCodeValue.getSeq() + 1);
		if(oldChildren != null && oldChildren.size() > 0) {
			for(TsCodeValue oldChild : oldChildren) {
				codeValueDao.setSeq(oldChild.getRid(), oldChild.getSeq() - 1);
			}
		}
		//����һ���ֵ��²������ƵĽڵ�
		Long seq = null;
		if(dtoCodeValue.getIsLeaveType()) {
			seq =codeValueDao.getJobCodeMaxSeq(upBean.getRid(), DtoCodeValue.DTO_LEAVE_TYPE);
		} else {
			seq =codeValueDao.getJobCodeMaxSeq(upBean.getRid(), DtoCodeValue.DTO_NON_LEAVE_TYPE);
		}
		rightBean.setParentRid(upBean.getRid());
		rightBean.setSeq(seq + 1);
		codeValueDao.update(rightBean);
		
	}
}
