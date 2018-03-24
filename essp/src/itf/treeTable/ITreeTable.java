package itf.treeTable;

import c2s.essp.common.treeTable.TreeTableConfig;
import c2s.dto.TransactionData;

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
public interface ITreeTable {
    public String createTreeTable(TreeTableConfig[] treeTableConfig,
                                  Class className, TransactionData data);
}
