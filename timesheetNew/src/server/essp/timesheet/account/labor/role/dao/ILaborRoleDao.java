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
     * �õ�����Role��¼
     * @return List
     */
    public List listLaborRole();

    /**
     * ����һ����¼
     * @param role TsLaborRole
     */
    public void add(TsLaborRole role);

    /**
     * ����һ����¼
     * @param role TsLaborRole
     */
    public void update(TsLaborRole role);

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
     * ɾ��һ��Role��¼
     * @param rid Long
     */
    public void delete(TsLaborRole role);

    /**
     * ��ѯ��һ��seq��Role����
     * @param seq Long
     * @return TsLaborRole
     */
    public TsLaborRole getUpSeqLaborRole(Long seq);

    /**
     * ��ѯ��һ��seq��Role����
     * @param seq Long
     * @return TsLaborRole
     */
    public TsLaborRole getDownSeqLaborRole(Long rid);
    
    /**
     * ����Rid��ѯRole����
     * @param rid
     * @return
     */
    public TsLaborRole getRoleByRid(Long rid);

}
