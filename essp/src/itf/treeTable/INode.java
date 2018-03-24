package itf.treeTable;

import c2s.dto.TransactionData;
import c2s.dto.ITreeNode;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface INode {
    public ITreeNode createNodes(TransactionData data);
}
