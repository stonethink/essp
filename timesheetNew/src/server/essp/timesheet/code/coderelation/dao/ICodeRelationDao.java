package server.essp.timesheet.code.coderelation.dao;

import db.essp.timesheet.TsCodeRelation;
import java.util.List;

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
public interface ICodeRelationDao {

    /**
     * 列出某code type下所有的code relation
     * @param isLeaveType String
     * @param typeRid Long
     * @return List
     */
    public List listRelation(Long typeRid, String isLeaveType);

    /**
     * 删除某code type下所有的code relation
     * @param typeRid Long
     * @param isLeaveType String
     */
    public void deleteRelations(Long typeRid, String isLeaveType);

    /**
     * 新增code relation
     * @param relation TsCodeRelation
     */
    public void add(TsCodeRelation relation);
}
