package server.essp.timesheet.code.coderelation.service;

import c2s.dto.ITreeNode;
import server.essp.timesheet.code.coderelation.dao.ICodeRelationDao;
import server.essp.timesheet.code.codevalue.service.ICodeValueService;
import java.util.List;

import c2s.essp.timesheet.code.DtoCodeType;
import c2s.essp.timesheet.code.DtoCodeValue;
import db.essp.timesheet.TsCodeRelation;
import c2s.dto.DtoTreeNode;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public class CodeRelationServiceImp implements ICodeRelationService {

    private ICodeRelationDao codeRelationDao;
    private ICodeValueService codeValueService;

    /**
     * ��ȡCode Type��Value�Ķ�Ӧ��ϵ��
     * @param typeRid Long
     * @param isLeaveType boolean
     * @return ITreeNode
     */
    public ITreeNode getRelationTree(Long typeRid, boolean isLeaveType) {
    	
        ITreeNode root = null;
        if(isLeaveType) {
        	root = codeValueService.getLeaveCodeValueTree();
        } else {
        	root = codeValueService.getJobCoeValueTree();
        }
        if(root == null) return null;

        List valueList = root.listAllChildrenByPreOrder();
        valueList.add(root);
        
        List relationList = null;
        if(isLeaveType) {
        	relationList = codeRelationDao.listRelation(typeRid, DtoCodeValue.DTO_LEAVE_TYPE);
        } else {
        	relationList = codeRelationDao.listRelation(typeRid, DtoCodeValue.DTO_NON_LEAVE_TYPE);
        }

        checkRelation(valueList, relationList);

        return root;
    }


    /**
     * ����Relation��ȷ��Value Treeÿ���ڵ��Ƿ�Check
     * @param valueList List
     * @param relationList List
     */
    private void checkRelation(List<ITreeNode> valueList,
            List<TsCodeRelation> relationList) {

        for(ITreeNode node : valueList) {
            DtoCodeValue value = (DtoCodeValue) node.getDataBean();
            if(value.getIsEnable()) {
                value.setIsEnable(false);
            } else {
                //ʧЧCode Value����ʾ��Code Relation��ͼ��
                ITreeNode parent = node.getParent();
                if(parent != null) {
                    parent.deleteChild(node);
                }
            }
            for(TsCodeRelation relation : relationList) {
                if(relation.getValueRid().equals(value.getRid())) {
                    value.setIsEnable(true);
                }
            }
        }
    }

    /**
     * ����Code Type��Value�Ķ�Ӧ��ϵ
     *   ��ɾTypeRid��ԭ�еĳ����й�ϵ����������ǰ��ϵ
     * @param typeRid Long
     * @param root ITreeNode
     * @param isLeaveType boolean
     */
    public void saveRelation(Long typeRid, ITreeNode root, boolean isLeaveType) {
    	if(isLeaveType) {
    		codeRelationDao.deleteRelations(typeRid, DtoCodeType.DTO_LEAVE_TYPE);
    	} else {
    		codeRelationDao.deleteRelations(typeRid, DtoCodeType.DTO_NON_LEAVE_TYPE);
    	}
        
        List valueList = root.listAllChildrenByPostOrder();
        valueList.add(root);
        addRelations(typeRid, valueList, isLeaveType);
    }

    /**
     * �����ж�Ӧ��ϵ
     * @param typeRid Long
     * @param valueList List
     * @param isLeaveType boolean
     */
    private void addRelations(Long typeRid, List<DtoTreeNode> valueList, boolean isLeaveType) {
        for(DtoTreeNode node : valueList) {
            DtoCodeValue value = (DtoCodeValue) node.getDataBean();
            if(value.getIsEnable()) {
                TsCodeRelation relation = new TsCodeRelation();
                relation.setTypeRid(typeRid);
                relation.setValueRid(value.getRid());
                relation.setIsLeaveType(isLeaveType);
                codeRelationDao.add(relation);
            }
        }
    }

    public void setCodeRelationDao(ICodeRelationDao codeRelationDao) {
        this.codeRelationDao = codeRelationDao;
    }

    public void setCodeValueService(ICodeValueService codeValueService) {
        this.codeValueService = codeValueService;
    }
}
