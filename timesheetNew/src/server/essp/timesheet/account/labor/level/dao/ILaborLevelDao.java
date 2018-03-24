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
     * �õ�����Level��¼
     * @return List
     */
    public List listLaborLevel();

    /**
     * ����һ����¼
     * @param level TsLaborLevel
     */
    public void add(TsLaborLevel level);

    /**
     * ����һ����¼
     * @param level TsLaborLevel
     */
    public void update(TsLaborLevel level);

    /**
     * ��ȡ����Seq
     * @return Long
     */
    public Long getMaxSeq();

    /**
     * ����seq
     * @param rid Long
     * @param seq Long
     */
    public void setSeq(Long rid, Long seq);

    /**
     * ɾ��һ��Level��¼
     * @param rid Long
     */
    public void delete(TsLaborLevel level);

    /**
     * ��ѯ��һ��seq��Level����
     * @param seq Long
     * @return TsLaborLevel
     */
    public TsLaborLevel getUpSeqLaborLevel(Long seq);

    /**
     * ��ѯ��һ��seq��Level����
     * @param seq Long
     * @return TsLaborLevel
     */
    public TsLaborLevel getDownSeqLaborLevel(Long seq);
    
    /**
     * ����Rid��ȡLevel����
     * @param rid
     * @return
     */
    public TsLaborLevel getLevelByRid(Long rid);
}
