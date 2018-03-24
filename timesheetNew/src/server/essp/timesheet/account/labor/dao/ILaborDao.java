package server.essp.timesheet.account.labor.dao;

import java.util.List;

import db.essp.timesheet.TsLaborResource;

/**
 * <p>Title: labor resource data access interface</p>
 *
 * <p>Description: 与项目人力相关的数据访问</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface ILaborDao {

    /**
     * 列出指定项目下的人力资源
     * @param AccountRid Long
     * @return List<TsLaborResource>
     */
    public List listLabor(Long accountRid);

    /**
     * 把指定人力资源从项目中删除
     * @param labor TsLaborResource
     */
    public void deleteLabor(TsLaborResource labor);

    /**
     * 为某项目增加人力资源
     * @param labor TsLaborResource
     */
    public void addLabor(TsLaborResource labor);

    /**
     * 保存人力资源信息
     * @param labor TsLaborResource
     */
    public void saveLabor(TsLaborResource labor);
    /**
     * 通过rid查询labor资料
     * @param rid Long
     * @return TsLaborResource
     */
    public TsLaborResource loadLabor(Long rid);
    /**
     * 根据专案代码和login id查询Labor资料
     * @param accountId String
     * @param loginId String
     * @return TsLaborResource
     */
    public TsLaborResource loadByAccountIdLoginId(Long accountRid,
                                                  String loginId);

    /**
    * 根据登录者ID到LaborResource表中取得对应的AccountRid集合
    * @param loginId String
    * @return Long
    */
   public List getAccountRidByLabor(String loginId);
   /**
    * 查询对应资源经理下的所有人员资料
    * @param managerId String 资料经理login id
    * @return List
    */
   public List<String> listLoginIdByResourceManager(String managerId);
   
   /**
    * 列出所有状态为IN的员工的LOGINID
    * @return List
    */
   public List listAllEmpId(final String deptStr);
   
   /**
    * 通过专案代码查询专案中的人员
    * @param accountId
    * @return
    */
   public List listPersonInProject(String accountId);
}
