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
     * 更新CodeValue信息
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
	 * 保存整棵树的Enable属性
	 * 
	 * @param root
	 *            ITreeNode
	 */
    public void saveEnables(ITreeNode root) {
        saveEnable(root);
    }

    /**
     * 保存Enable属性
     * @param node ITreeNode
     */
    private void saveEnable(ITreeNode node) {
        if(node == null) return;
        DtoCodeValue dto = (DtoCodeValue) node.getDataBean();
        if(dto != null && dto.isInsert() == false) {
            codeValueDao.updateEnable(dto.getRid(), dto.getIsEnable());
        }
        //保存子节点
        List<ITreeNode> children = node.children();
        for(ITreeNode child : children) {
            saveEnable(child);
        }
    }


    /**
     * 删除CodeValue信息
     * @param rid Long
     * @throws BusinessException
     */
    public void delete(DtoCodeValue dtoCodeValue) {
        codeValueDao.delete(dto2Bean(dtoCodeValue));
    }

    /**
     * 删除CodeValue及其子孙节点信息信息
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
     * 根据CodeType的Rid列出对应的CodeValue信息
     * @param codeTypeRid Long
     * @return List
     * @throws BusinessException
     */
    public List listLeaveCodeValue() {
        return beanList2DtoList(codeValueDao.listLeaveCodeValue());
    }

    /**
     * 获取Job Code Value根节点
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
     * 获取子孙节点
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
     * 上移CodeValue信息
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
     * 下移CodeValue信息
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
     * 根据Rid获取CodeValue记录
     * @param rid Long
     * @return TsCodeValue
     */
    public DtoCodeValue getCodeValue(Long rid) {
    	return bean2Dto(codeValueDao.getCodeValue(rid));
    }


    /**
     * 将TsCodeType list转换成DtoCodeType list
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
     * 将TsCodeType转换成DtoCodeType
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
     * 将DtoCodeType转换成TsCodeType
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
     * 获取Leave Code的根节点
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
	 * 左移
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
		//该节点之后的兄弟节点seq-1(上移一行)
		List<TsCodeValue> oldChildren = codeValueDao
        							.getChildrenAfterSeq(dtoCodeValue.getParentRid(), dtoCodeValue.getSeq() + 1);
		if(oldChildren != null && oldChildren.size() > 0) {
			for(TsCodeValue oldChild : oldChildren) {
				codeValueDao.setSeq(oldChild.getRid(), oldChild.getSeq() - 1);
			}
		}
		//该节点插入到父的父后，该节点后的兄弟seq+1(下移一行)
		List<TsCodeValue> newChildren = codeValueDao
		                              .getChildrenAfterSeq(grdBean.getRid(), fathBean.getSeq() + 1);
		if(newChildren != null && newChildren.size() > 0) {
			for(TsCodeValue child : newChildren) {
				codeValueDao.setSeq(child.getRid(), child.getSeq() + 1);
			}
		}
		//在父的父下插入要左移的节点,seq为父的seq+1
		leftBean.setParentRid(grdBean.getRid());
		leftBean.setSeq(fathBean.getSeq() + 1);
		codeValueDao.update(leftBean);
	}
	/**
	 * 右移
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
		
		//该节点之后的兄弟节点seq-1(上移一行)
		List<TsCodeValue> oldChildren = codeValueDao
        							.getChildrenAfterSeq(dtoCodeValue.getParentRid(), dtoCodeValue.getSeq() + 1);
		if(oldChildren != null && oldChildren.size() > 0) {
			for(TsCodeValue oldChild : oldChildren) {
				codeValueDao.setSeq(oldChild.getRid(), oldChild.getSeq() - 1);
			}
		}
		//在上一个兄弟下插入右移的节点
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
