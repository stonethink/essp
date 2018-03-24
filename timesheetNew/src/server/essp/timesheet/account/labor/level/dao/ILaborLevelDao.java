package server.essp.timesheet.account.labor.level.dao;

import db.essp.timesheet.TsLaborLevel;
import java.util.List;

/**
 * <p>Title: ILaborLevelDao</p>
 *
 * <p>Description: Labor level data access object interface</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ILaborLevelDao {

    /**
     * 得到所有Level记录
     * @return List
     */
    public List listLaborLevel();

    /**
     * 新增一条记录
     * @param level TsLaborLevel
     */
    public void add(TsLaborLevel level);

    /**
     * 更新一条记录
     * @param level TsLaborLevel
     */
    public void update(TsLaborLevel level);

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
     * 删除一条Level记录
     * @param rid Long
     */
    public void delete(TsLaborLevel level);

    /**
     * 查询上一个seq的Level资料
     * @param seq Long
     * @return TsLaborLevel
     */
    public TsLaborLevel getUpSeqLaborLevel(Long seq);

    /**
     * 查询下一个seq的Level资料
     * @param seq Long
     * @return TsLaborLevel
     */
    public TsLaborLevel getDownSeqLaborLevel(Long seq);
    
    /**
     * 根据Rid获取Level资料
     * @param rid
     * @return
     */
    public TsLaborLevel getLevelByRid(Long rid);
}
