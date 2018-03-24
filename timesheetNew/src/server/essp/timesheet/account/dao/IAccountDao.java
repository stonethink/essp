package server.essp.timesheet.account.dao;

import java.util.List;
import db.essp.timesheet.TsAccount;
import c2s.essp.timesheet.account.DtoAccount;

/**
 * <p>Title: Account data access interface</p>
 *
 * <p>Description: 与项目相关的数据访问</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author lipengxu
 * @version 1.0
 */
public interface IAccountDao {

    /**
     * 根据专案经理的loginId列出在某部门下的专案
     * @param deptId String
     * @param loginId String
     * @return List
     */
    public List listProjectsByLoginIdInDept(String deptId, String loginId);
    /**
     * 根据deptId列出部门下的所有专案
     * deptIds格式为('deptId1','deptId2','deptId3')
     * @param deptIds String
     * @return List
     */
    public List listProjectsByDept(String deptIds);

    /**
     * 根据父部门的id列出子部门
     * @param parentId String
     * @return List
     */
    public List listChildrenDeptByParentId(String parentId);

    /**
     * 根据部门主管的loginId查询部门
     * @param loginId String
     * @return List
     */
    public List listDeptByManager(String loginId);

    /**
     * 列出指定用户为项目经理的状态正常的项目
     * @param loginId String
     * @return List
     */
    public List listAccounts(String loginId);
    
    /**
     * 列出指定OU下状态正常的项目
     * 	ou可为多个部门代码，以","分隔。s
     * @param ou String
     * @return List<TsAccount>
     */
    public List listAccountByOu(String ou);
    
    /**
     * 列出状态正常的所有Account（包括部门）
     * @return
     */
    public List listNormalAccounts();
    /**
     * 获取指定rid的项目
     * @param rid Long
     * @return TsAccount
     */
    public TsAccount loadAccount(Long rid);


    /**
     * 保存项目
     * @param Account TsAccount
     */
    public void saveAccount(TsAccount account);

    /**
     * 新增项目
     * @param Account TsAccount
     */
    public void addPorject(TsAccount account);

    /**
     * 根据专案代码获取专案信息
     *
     * @param AccountId String
     * @return TsAccount
     */
    public TsAccount loadByAccountId(String accountId);
    /**
     * 列出account中的项目
     * @param loginId String
     * @return List
     */
    public List listProjects(String loginId);
    /**
     * 根据accountRid得到当前项目的实际累计时间
     * @param accountRid Long
     * @return BigDecimal
     */
    public Double sumWorkHoursByAccount(final Long accountRid,String loginId);
    
    /**
     * 根据accountRid得到当前项目的实际累计时间(PMO使用不带专案经理条件)
     * @param accountRid Long
     * @return BigDecimal
     */
    public Double sumWorkHoursByAccount(final Long accountRid);

    /**
     * 根据部门Id列出部门下的状态为正常的所有专案
     * deptIds格式为('deptId1','deptId2','deptId3')
     * @param deptId String
     * @return List
     */
    public List listAccountByDept(String deptIds);
    
    /**
     * 根据条件查询Accounts
     * @param dto
     * @return
     */
    public List listByCondtion(DtoAccount dto, String privilegeOu);
    
    /**
     * 列出所有狀態為N的部門
     * @return List
     */
    public List listAllDept();
    
    /**
     * 根据loginId查询出项目
     * @param loginId
     * @return List
     */ 
    public List listAccountByLabResLoginId(final String loginId);
    
    /**
     * 获取用户选中项目代号
     * @param loginId String
     * @return String
     */
    public String getSelectAccount(String loginId);
    
    /**
     * 设置用户选中项目
     * @param loginId String
     * @param accountId String
     */
    public void setSelectAccount(String loginId, String accountId);
    
    /**
     * 获取需要发送日报的专案
     * @return
     */
    public List listMailProject();
}
