package server.essp.timesheet.account.labor.role.dao;

import db.essp.timesheet.TsLaborRole;
import java.util.List;

/**
 * <p>Title: ILaborRoleDao</p>
 *
 * <p>Description: Labor role data access object interface</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ILaborRoleDao {
    /**
     * 得到所有Role记录
     * @return List
     */
    public List listLaborRole();

    /**
     * 新增一条记录
     * @param role TsLaborRole
     */
    public void add(TsLaborRole role);

    /**
     * 更新一条记录
     * @param role TsLaborRole
     */
    public void update(TsLaborRole role);

    /**
     * 获取最大的Seq
     * @return Long
     */
    public Long getMaxSeq();

    /**
     * 设置seq
     * @param rid Long
     * @param seq Long
     */
    public void setSeq(Long rid, Long seq);

    /**
     * 删除一条Role记录
     * @param rid Long
     */
    public void delete(TsLaborRole role);

    /**
     * 查询上一个seq的Role资料
     * @param seq Long
     * @return TsLaborRole
     */
    public TsLaborRole getUpSeqLaborRole(Long seq);

    /**
     * 查询下一个seq的Role资料
     * @param seq Long
     * @return TsLaborRole
     */
    public TsLaborRole getDownSeqLaborRole(Long rid);
    
    /**
     * 根据Rid查询Role资料
     * @param rid
     * @return
     */
    public TsLaborRole getRoleByRid(Long rid);

}
