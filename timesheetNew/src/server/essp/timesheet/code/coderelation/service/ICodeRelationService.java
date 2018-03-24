package server.essp.timesheet.code.coderelation.service;

import c2s.dto.ITreeNode;

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
public interface ICodeRelationService {

    /**
     * 获取Code Type与Value的对应关系树
     * @param typeRid Long
     * @param isLeaveType boolean
     * @return ITreeNode
     */
    public ITreeNode getRelationTree(Long typeRid, boolean isLeaveType);

    /**
     * 保存Code Type与Value的对应关系
     * @param root ITreeNode
     * @param typeRid Long
     * @param isLeaveType boolean
     */
    public void saveRelation(Long typeRid, ITreeNode root, boolean isLeaveType);
}
