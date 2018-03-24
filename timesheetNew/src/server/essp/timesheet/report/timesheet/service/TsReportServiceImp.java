package server.essp.timesheet.report.timesheet.service;

import java.math.BigDecimal;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;

import server.essp.timesheet.account.dao.IAccountDao;
import server.essp.timesheet.account.dao.LgAccountPrivilege;
import server.essp.timesheet.account.labor.dao.ILaborDao;
import server.essp.timesheet.period.dao.IPeriodDao;
import server.essp.timesheet.report.LevelCellStyleUtils;
import server.essp.timesheet.report.timesheet.dao.ITsReportDao;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import server.essp.timesheet.rmmaint.service.IRmMaintService;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetP3ApiDao;
import server.framework.common.BusinessException;
import c2s.dto.DtoComboItem;
import c2s.essp.common.user.DtoRole;
import c2s.essp.timesheet.report.*;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

import com.wits.excel.ICellStyleSwitch;
import com.wits.util.comDate;

import db.essp.timesheet.*;


/**
 * <p>Title:TsReportServiceImp</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: WistronITS</p>
 *
 * @author wenhaizheng
 * @version 1.0
 */
public class TsReportServiceImp implements ITsReportService{

    private final static String SELECT_FIRST_OPTION = "Select All";//下拉框首选项
    private ITsReportDao tsReportDao;
    private IAccountDao accountDao;
    private ILaborDao laborDao;
    private IRmMaintService rmMaintService;
    private IPeriodDao periodDao;
    private IRmMaintDao rmMaintDao;
    private ITimeSheetP3ApiDao  timeSheetApiDao;

	/**
     * 根据查询条件查询报表数据
     * @param dtoCondition DtoQueryCondition
     * @param loginId String
     * @return List
     */
    public List queryReport(DtoQueryCondition dtoCondition, String loginId) {
        String queryWay = dtoCondition.getQueryWay(); //取出查询方式，区别不同角色的查询
        String loginIds = "";
        String orderBy = "";
        String acntRids = null;
        List<Object[]> list = null;
        //如果是一般人员查询,设置当前查询人的loginId为查询条件
        if (DtoQueryCondition.DTO_QUERY_WAY_EMP.equals(queryWay)) {
            loginIds = "(lower('" + loginId + "'))";
            list = tsReportDao.queryReport(dtoCondition, loginIds, orderBy);
            if (list == null || list.size() == 0) {
                return new ArrayList();
            }
            return obj2ListForEmp(list, queryWay);
            //如果是PM查询或者是RM使用专案界面查询
            //根据输入条件查询要显示的工时单填写人的loginId
        } else if (DtoQueryCondition.DTO_QUERY_WAY_PM.equals(queryWay)
                 ||DtoQueryCondition.DTO_QUERY_WAY_RM.equals(queryWay) ) {
        	if(dtoCondition.isIncludeSub()) {
        		String deptIds = getDeptIds(dtoCondition.getDeptId());
        		list = tsReportDao.queryReportForPmDept(dtoCondition, deptIds, loginId);
        	} else {
        		Map resultMap = getLoginIdsByProject(dtoCondition);
                if(resultMap.containsKey("loginIds")) {
                	loginIds = (String)resultMap.get("loginIds");
                }
                if(resultMap.containsKey("acntRids")) {
                	acntRids = (String)resultMap.get("acntRids");
                }
                orderBy = "order by a.accountId";
                list = tsReportDao.queryReportForPm(dtoCondition, loginIds, orderBy, acntRids);
        	}
            if (list == null || list.size() == 0) {
                return new ArrayList();
            }
            return obj2ListForPm(list, queryWay, loginId);
        } else if(DtoQueryCondition.DTO_QUERY_WAY_RMP.equals(queryWay)) {
        	if(dtoCondition.isIncludeSub()) {
        		String deptIds = getDeptIds(dtoCondition.getDeptId());
        		list = tsReportDao.queryReportByDept(dtoCondition, deptIds);
        	} else {
        		loginIds = getLoginIds(dtoCondition);
                orderBy = "order by m.loginId";
                list = tsReportDao.queryReport(dtoCondition, loginIds, orderBy);
        	}
        	if (list == null || list.size() == 0) {
                return new ArrayList();
            }
        	return obj2ListForRmP(list, queryWay);
        }
        return new ArrayList();
    }
    
    /**
     * PMO查询时获取所有部门
     * @return
     */
    public Vector getDeptsForPmo(String loginId, String roleId) {
    	Vector depts = new Vector();
    	Map deptMap = new HashMap();
        List<TsAccount> deptList = null;
        if(DtoRole.ROLE_SYSUSER.equals(roleId)
         ||DtoRole.ROLE_HAP.equals(roleId)) {
        	deptList = accountDao.listAllDept();
        } else if(DtoRole.ROLE_UAP.equals(roleId)){
        	LgAccountPrivilege lg = new LgAccountPrivilege();
        	String units = lg.getPrivilegeUnit(loginId);
        	String[] unit = null;
        	if(units != null && !"".equals(units)) {
        		unit = units.split(",");
        	}
        	deptList = new ArrayList<TsAccount>();
        	TsAccount account = null;
        	if (unit != null && unit.length > 0) {
				for (String unitId : unit) {
					account = accountDao.loadByAccountId(unitId);
					if (account != null && account.getAccountId() != null) {
						deptList.add(account);
					}
				}
			}
        }
        if(deptList == null || deptList.size() ==0) {
        	return depts;
        }
        DtoComboItem dtoItem = null;
        for(TsAccount account : deptList) {
        	dtoItem = new DtoComboItem(account.getAccountId()+"--"+account.getAccountName(),
        			                   account.getAccountId());
        	depts.add(dtoItem);
        }
    	return depts;
    }
    /**
     * PMO查询时根据所选的部门列出部门下的专案
     * @param deptId
     * @return
     */
    public Vector getProjectsForPmo(String deptId) {
    	Vector projects = new Vector();
    	List<TsAccount> projectList = accountDao.listProjectsByDept("('"+deptId+"')");
    	if(projectList == null || projectList.size() == 0) {
    		return projects;
    	}
    	DtoComboItem dtoItem = new DtoComboItem(SELECT_FIRST_OPTION, "");
    	projects.add(dtoItem);
    	for(TsAccount account : projectList) {
    		dtoItem = new DtoComboItem(account.getAccountId()+"--"+account.getAccountName(),
    								   account.getAccountId());
    		projects.add(dtoItem);
    	}
    	return projects; 
    }
    /**
     * PMO查询时根据部门列出部门下的所有人员
     * @param deptId
     * @return
     */
    public Vector getPersonsForPmo(String deptId) {
    	Vector persons = new Vector();
//    	TsAccount tsAccount = accountDao.loadByAccountId(deptId);
//    	if(tsAccount == null) {
//    		return persons;
//    	}
//    	
//        List<TsLaborResource> personList = laborDao.listLabor(tsAccount.getRid());
        List<TsHumanBase> personList = rmMaintDao.listPersonsByDept(deptId);
        if(personList == null || personList.size() == 0) {
        	return persons;
        }
        DtoComboItem dtoItem = new DtoComboItem(SELECT_FIRST_OPTION, "");
        persons.add(dtoItem);
        for(TsHumanBase human : personList) {
        	dtoItem = new DtoComboItem(human.getFullName(), human.getEmployeeId());
        	persons.add(dtoItem);
        }
    	return persons;
    }
    /**
     * 根据部门代码获取连同其子孙部门的代码
     * @param deptId
     * @return
     */
    private String getDeptIds(String deptId) {
    	String result = "('')";
    	TsAccount account = new TsAccount();
    	account.setAccountId(deptId);
    	Map deptMap = new HashMap();
    	List deptList = new ArrayList();
    	deptList.add(account);
    	findDeptChildrenByLoginId(deptMap, deptList);
    	List list = new ArrayList(deptMap.keySet());
    	String tmp = "''";
    	int size = list.size();
    	for(int i = 0; i < size; i++) {
    		if(i == 0) {
    			tmp = "'" + (String) list.get(0) + "'";
    		} else {
    			tmp += ", '" + (String) list.get(i) + "'";
    		}
    	}
    	result = "(" + tmp + ")";
    	return result;
    }
    /**
     * 通过选择的人员获取人员的loginId
     * @param dto DtoQueryCondition
     * @return String
     */
    private String getLoginIds(DtoQueryCondition dto) {
        String result = "('')";
        if("".equals(dto.getLoginId())){
            Vector<DtoComboItem> personItem = dto.getPersonItem();
            if(personItem == null || personItem.size() == 0) {
                return result;
            }
            String tmp = "''";
            int size = personItem.size();
            DtoComboItem person = null;
            for (int i = 0; i < size; i++) {
                person = personItem.get(i);
                if (i == 0) {
                    tmp = "lower('" + (String) person.getItemValue() + "')";
                } else {
                    tmp += ", lower('" + (String) person.getItemValue() + "')";
                }
            }
            result = "(" + tmp + ")";
        } else {
            result = "(lower('"+dto.getLoginId()+"'))";
        }
        return result;
    }
    /**
     * 递归查询部门下的子孙部门
     * @param deptMap Map
     * @param deptList List
     */
    private void findDeptChildrenByLoginId(Map deptMap, List<TsAccount> deptList) {
        List list = null;
        for(TsAccount tsAccount : deptList) {
            deptMap.put(tsAccount.getAccountId(), tsAccount.getAccountId());
            list = accountDao.listChildrenDeptByParentId(tsAccount.getAccountId());
            if(list == null || list.size() ==0) {
                continue;
            }
            findDeptChildrenByLoginId(deptMap, list);
        }
    }

    /**
     * 根据界面选择的部门和专案查询专案或部门下的相关人员的loginId
     * 并将其拼接为('loginId1', 'loginId2', 'loginId3')的格式返回
     * @param accountRid Long
     * @param deptId String
     * @param loginId String
     * @param queryWay String
     * @return String
     */
    private Map getLoginIdsByProject(DtoQueryCondition dto){
        String result = "('')";
        String acntRids = "()";
        Map resultMap = new HashMap();
        if(dto.getProjectId() == null) {
        	result = "('null')";
        	acntRids = "(-1)";
        	resultMap.put("loginIds", result);
            resultMap.put("acntRids", acntRids);
        	return resultMap;
        }
        if("".equals(dto.getProjectId())){
            Vector<DtoComboItem> projectItem = dto.getProjectItem();
            if (projectItem == null || projectItem.size() == 0) {
            	result = "('null')";
            	acntRids = "(-1)";
            	resultMap.put("loginIds", result);
                resultMap.put("acntRids", acntRids);
                return resultMap;
            }
            List<TsLaborResource> listLabor = null;
            int size = 0;
            String tmp = "''";
            String temp = "";
            Map personMap = new HashMap();
            TsAccount tsAccount = null;
            for (DtoComboItem item : projectItem) {
                tsAccount = accountDao.loadByAccountId((String) item.
                        getItemValue());
                if(tsAccount != null) {
                	temp = temp + "," + tsAccount.getRid();
                	listLabor = laborDao.listLabor(tsAccount.getRid());
                }
                if (listLabor == null || listLabor.size() == 0) {
                    continue;
                }
                for (TsLaborResource tsLabor : listLabor) {
                    personMap.put(tsLabor.getLoginId(), tsLabor.getName());
                }
            }
            List loginIdList = new ArrayList(personMap.keySet());
            size = loginIdList.size();
            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    tmp = "lower('" + (String) loginIdList.get(i) + "')";
                } else {
                    tmp += ", lower('" + (String) loginIdList.get(i) + "')";
                }
            }
            result = "(" + tmp + ")";
            if(temp.indexOf(",") > -1){
            	acntRids = "("+temp.substring(1)+")";
            } else {
            	acntRids = null;
            }
            
        } else {
            TsAccount tsAccount = accountDao.loadByAccountId(dto.getProjectId());
            List<TsLaborResource>
                    listLabor = laborDao.listLabor(tsAccount.getRid());
            if (listLabor == null || listLabor.size() == 0) {
            	result = "('null')";
            	acntRids = "(-1)";
            	resultMap.put("loginIds", result);
                resultMap.put("acntRids", acntRids);
                return resultMap;
            }
            Map personMap = new HashMap();
            int size = 0;
            String tmp = "''";
            for (TsLaborResource tsLabor : listLabor) {
                personMap.put(tsLabor.getLoginId(), tsLabor.getName());
            }
            List loginIdList = new ArrayList(personMap.keySet());
            size = loginIdList.size();
            for (int i = 0; i < size; i++) {
                if (i == 0) {
                    tmp = "lower('" + (String) loginIdList.get(i) + "')";
                } else {
                    tmp += ", lower('" + (String) loginIdList.get(i) + "')";
                }
            }
            result = "(" + tmp + ")";
            acntRids = "("+tsAccount.getRid()+")";
        }
        resultMap.put("loginIds", result);
        resultMap.put("acntRids", acntRids);
        return resultMap;
    }
    /**
     * 将查询的结果按要求格式组合成显示到界面的list
     * @param list List
     * @param queryWay String
     * @return List
     */
    private List obj2ListForRmP(List<Object[]> list, String queryWay) {
        List resultList = new ArrayList();
        DtoTsDetailReport dto = null;
        Double totalWorkHours = new Double(0);
        Double totalOverTimeHours = new Double(0);
        Double totalLeaveHours = new Double(0);
        Double stotalWorkHours = new Double(0);
        Double stotalOverTimeHours = new Double(0);
        Double stotalLeaveHours = new Double(0);
        String lastLoginId = (String)((Object[])list.get(0))[3];
        boolean isClear = false;
        for(Object[] obj : list) {
            dto = getDtosDetailInstance();
            setDtoValue(dto, obj);//设置查询结果到DTO中
            totalWorkHours += nvl(dto.getNormalWorkHours());
            totalOverTimeHours += nvl((Double)obj[9]);
            totalLeaveHours += nvl(dto.getLeaveHours());
            //根据条件设置小计
            if(!lastLoginId.equals((String)obj[3])) {
                insertSum(resultList, stotalWorkHours,
                            stotalOverTimeHours, stotalLeaveHours,
                            queryWay, lastLoginId);
                isClear = true;
            }
            //小计后将累计数据清除
            if(isClear) {
                stotalWorkHours = new Double(0);
                stotalOverTimeHours = new Double(0);
                stotalLeaveHours = new Double(0);
                isClear =false;
            }
            stotalWorkHours += nvl(dto.getNormalWorkHours());
            stotalOverTimeHours += nvl((Double)obj[9]);
            stotalLeaveHours += nvl(dto.getLeaveHours());

            lastLoginId = (String)obj[3];
            resultList.add(dto);
        }
        insertSum(resultList, stotalWorkHours,
                            stotalOverTimeHours,
                            stotalLeaveHours,
                            queryWay, lastLoginId);
        dto = getDtosDetailInstance();
        dto.setWorkDate("Total");
        dto.setWorkHours(roundDouble(totalWorkHours));
        dto.setOverTimeHours(roundDouble(totalOverTimeHours));
        dto.setLeaveHours(roundDouble(totalLeaveHours));
        dto.setNormalWorkHours(roundDouble(totalWorkHours));
        dto.setTotal(roundDouble(totalWorkHours+totalLeaveHours+totalOverTimeHours));
        dto.setIsLeaveType(false);
        dto.setSumLevel(2);
        resultList.add(dto);
        return resultList;

    }
    private Double roundDouble(Double d){
    	if(d == null) {
    		return new Double(0.00);
    	}
    	BigDecimal b = new BigDecimal(d);
    	b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
    	return b.doubleValue();
    }
    /**
     * 将查询的结果按要求格式组合成显示到界面的list
     * @param list List
     * @param queryWay String
     * @param loginId String
     * @return List
     */
    private List obj2ListForPm(List<Object[]> list, String queryWay, String loginId) {
        List resultList = new ArrayList();
        DtoTsDetailReport dto = null;
        Double totalWorkHours = new Double(0);
        Double totalOverTimeHours = new Double(0);
        Double totalLeaveHours = new Double(0);
        Double stotalWorkHours = new Double(0);
        Double stotalOverTimeHours = new Double(0);
        Double stotalLeaveHours = new Double(0);
        String lastProjectId = (String)((Object[])list.get(0))[1];
        boolean isClear = false;
        for(Object[] obj : list) {
            dto = getDtosDetailInstance();
            setDtoValue(dto, obj);//设置查询结果到DTO中
            totalWorkHours += nvl(dto.getNormalWorkHours());
            totalOverTimeHours += nvl((Double)obj[9]);
            totalLeaveHours += nvl(dto.getLeaveHours());
            //根据条件设置小计
            if(!lastProjectId.equals((String)obj[1])) {
            	insertSum(resultList, stotalWorkHours, stotalOverTimeHours,
						stotalLeaveHours, queryWay, lastProjectId);
				isClear = true;
            }
            // 小计后将累计数据清除
            if(isClear) {
                stotalWorkHours = new Double(0);
                stotalOverTimeHours = new Double(0);
                stotalLeaveHours = new Double(0);
                isClear =false;
            }
            stotalWorkHours += nvl(dto.getNormalWorkHours());
            stotalOverTimeHours += nvl((Double)obj[9]);
            stotalLeaveHours += nvl(dto.getLeaveHours());

            lastProjectId = (String)obj[1];
            resultList.add(dto);
        }
        insertSum(resultList, stotalWorkHours,
                            stotalOverTimeHours,
                            stotalLeaveHours, queryWay, lastProjectId);
        dto = getDtosDetailInstance();
        dto.setWorkDate("Total");
        dto.setWorkHours(roundDouble(totalWorkHours));
        dto.setOverTimeHours(roundDouble(totalOverTimeHours));
        dto.setLeaveHours(roundDouble(totalLeaveHours));
        dto.setNormalWorkHours(roundDouble(totalWorkHours));
        dto.setTotal(roundDouble(totalWorkHours+totalLeaveHours+totalOverTimeHours));
        dto.setIsLeaveType(false);
        dto.setSumLevel(2);
        resultList.add(dto);
        return resultList;

    }
    /**
     * 将查询的结果按要求格式组合成显示到界面的list
     * @param list List
     * @return List
     */
    private List obj2ListForEmp(List<Object[]> list, String queryWay) {
        List resultList = new ArrayList();
        DtoTsDetailReport dto = null;
        Double totalWorkHours = new Double(0);
        Double totalOverTimeHours = new Double(0);
        Double totalLeaveHours = new Double(0);
        String loginId = (String)((Object[])list.get(0))[3];
        for(Object[] obj : list) {
            dto = getDtosDetailInstance();
            setDtoValue(dto, obj);//设置查询结果到DTO中
            totalWorkHours += nvl(dto.getNormalWorkHours());
            totalOverTimeHours += nvl((Double)obj[9]);
            totalLeaveHours += nvl(dto.getLeaveHours());
            resultList.add(dto);
        }
        insertSum(resultList, totalWorkHours,
                            totalOverTimeHours, totalLeaveHours,
                            queryWay, loginId);
        dto = getDtosDetailInstance();
        dto.setWorkDate("Total");
        dto.setWorkHours(roundDouble(totalWorkHours));
        dto.setOverTimeHours(roundDouble(totalOverTimeHours));
        dto.setLeaveHours(roundDouble(totalLeaveHours));
        dto.setNormalWorkHours(roundDouble(totalWorkHours));
        dto.setTotal(roundDouble(totalWorkHours+totalLeaveHours+totalOverTimeHours));
        dto.setIsLeaveType(false);
        dto.setSumLevel(2);
        resultList.add(dto);
        return resultList;
    }


    /**
     * 设置查询结果到DTO中
     * @param dto DtoTsDetailReport
     * @param obj Object[]
     */
    private void setDtoValue(DtoTsDetailReport dto, Object[] obj){
            dto.setWorkDate(comDate.dateToString((Date)obj[0]));
            dto.setProjectId((String)obj[1]);
            dto.setProjectName((String)obj[2]);
            dto.setLoginId((String)obj[3]);
            dto.setDeptId(rmMaintService.getDept((String)obj[3]));
            dto.setName(rmMaintService.getUserName((String)obj[3]));
            dto.setJobCode((String)obj[4]);
            dto.setJobCodeDesc((String)obj[5]);
            dto.setIsLeaveType((Boolean)obj[6]);
            dto.setJobDesc((String)obj[7]);
            dto.setWorkHours(roundDouble((Double)obj[8]));
            dto.setOverTimeHours(roundDouble((Double)obj[9]));
            dto.setSumLevel(0);
    }
    /**
     * 插入小计记录(PM，RM适用)
     * @param resultList List
     * @param stotalWorkHours Double
     * @param stotalOverTimeHours Double
     * @param stotalLeaveHours Double
     * @param queryWay String
     */
    private void insertSum(List resultList, Double stotalWorkHours,
                             Double stotalOverTimeHours,
                             Double stotalLeaveHours,
                             String queryWay, String lastId) {
        DtoTsDetailReport dto = getDtosDetailInstance();
        dto.setWorkDate("Sum");
        dto.setWorkHours(roundDouble(stotalWorkHours));
        dto.setOverTimeHours(roundDouble(stotalOverTimeHours));
        dto.setLeaveHours(roundDouble(stotalLeaveHours));
        dto.setNormalWorkHours(roundDouble(stotalWorkHours));
        dto.setTotal(roundDouble(stotalWorkHours+stotalLeaveHours+stotalOverTimeHours));
        dto.setIsLeaveType(false);
        dto.setSumLevel(1);
        if(DtoQueryCondition.DTO_QUERY_WAY_PM.equals(queryWay)
         ||DtoQueryCondition.DTO_QUERY_WAY_RM.equals(queryWay)){
            dto.setProjectId(lastId);
        } else if(DtoQueryCondition.DTO_QUERY_WAY_EMP.equals(queryWay)
                ||DtoQueryCondition.DTO_QUERY_WAY_RMP.equals(queryWay)) {
            dto.setLoginId(lastId);
        }
        resultList.add(dto);
    }

    /**
     * 过滤值为NULL的数值
     * @param d Double
     * @return Double
     */
    private Double nvl(Double d) {
        if(d == null){
           return new Double(0);
        }
        return d;
    }
    /**
     * 获取部门和专案下拉选项
     * @param loginId String
     * @return Map
     */
    public Map getDeptsProjects(String loginId) {
        Map deptMap = new HashMap();
        Map projectMap = new HashMap();
        Map relationMap = new HashMap();
        List<TsAccount> deptList = accountDao.listDeptByManager(loginId);
        findDeptChildrenByLoginId(deptMap, deptList);
        List<TsAccount> projectList = null;
        Map relatedProjectMap = null;
        List<String> list = new ArrayList(deptMap.values());
        if(list !=null && list.size() > 0) {
            for(String deptId : list) {
                deptMap.put(deptId,deptId);
                projectList = accountDao.listProjectsByDept("('"+deptId+"')");
                if(projectList == null || projectList.size() == 0) {
                    continue;
                }
                relatedProjectMap = new HashMap();
                for(TsAccount project : projectList) {
                    projectMap.put(project.getAccountId(),
                            project.getAccountId());
                    relatedProjectMap.put(project.getAccountId(),
                                          project.getAccountId()+"--"+project.getAccountName());
                }
                relationMap.put(deptId, relatedProjectMap);
            }
        }
        projectList = accountDao.listProjects(loginId);
        Map tmpMap = null;
        for(TsAccount project : projectList) {
        	if(project.getOrgCode() == null) {
        		continue;
        	}
            if(relationMap.containsKey(project.getOrgCode())) {
                tmpMap = (Map) relationMap.get(project.getOrgCode());
                tmpMap.put(project.getAccountId(),
                           project.getAccountId()+"--"+project.getAccountName());
                projectMap.put(project.getAccountId(),
                               project.getAccountId());
            } else {
                tmpMap = new HashMap();
                tmpMap.put(project.getAccountId(),
                           project.getAccountId()+"--"+project.getAccountName());
                projectMap.put(project.getAccountId(),
                               project.getAccountId());
                relationMap.put(project.getOrgCode(), tmpMap);
                deptMap.put(project.getOrgCode(),
                            project.getOrgCode());
            }
        }
        Map newRelationMap = sortRelationMap(relationMap);
        Map resultMap = new HashMap();
        Vector deptItem = map2Vector(deptMap);
        Vector projectItem = map2Vector(projectMap);
        resultMap.put(DtoTsDetailReport.DTO_DEPT_LIST, deptItem);
        resultMap.put(DtoTsDetailReport.DTO_PROJECT_LIST, projectItem);
        resultMap.put(DtoTsDetailReport.DTO_RELATION_MAP, newRelationMap);
        return resultMap;
    }
    /**
     * 将部门和专案关联的map中的专案数据排序并组成下拉
     * 框中的选项
     * @param relationMap Map
     * @return Map
     */
    private Map sortRelationMap(Map relationMap) {
        List<String> list = new ArrayList(relationMap.keySet());
        if(list == null || list.size() == 0) {
            return new HashMap();
        }
        Map newRelationMap = new HashMap();
        Map projectMap = null;
        List<String> listProject = null;
        Vector projectItem = null;
        for(String deptId : list) {
           projectMap = (Map) relationMap.get(deptId);
           listProject = new ArrayList(projectMap.keySet());
           Collections.sort(listProject, new SortByAccountId());
           projectItem = new Vector();
           DtoComboItem dtoItem = new DtoComboItem(SELECT_FIRST_OPTION,
                   "");
           projectItem.add(dtoItem);
           for(String projectId : listProject) {
               dtoItem = new DtoComboItem((String)projectMap.get(projectId), projectId);
               projectItem.add(dtoItem);
           }
           newRelationMap.put(deptId, projectItem);
        }
        return newRelationMap;
    }
    /**
     * 将map中的数据转化成下拉框中的选项
     * @param map Map
     * @return Vector
     */
    private Vector map2Vector(Map map) {
        List<String> list = new ArrayList(map.keySet());
        if(list == null || list.size() ==0) {
            return new Vector();
        }
        Collections.sort(list, new SortByAccountId());
        Vector v = new Vector();
        TsAccount tsAccount = null;
        DtoComboItem dtoItem = new DtoComboItem(SELECT_FIRST_OPTION,
                                                "");
        v.add(dtoItem);
        for (String accountId : list) {
            tsAccount = accountDao.loadByAccountId(accountId);
            if(tsAccount != null && tsAccount.getAccountId() != null){
            	dtoItem = new DtoComboItem(tsAccount.getAccountId() + "--"
                        + tsAccount.getAccountName(),
                        tsAccount.getAccountId());
            } else {
            	dtoItem = new DtoComboItem(accountId + "--"
                        + "null",
                        accountId);
            }
            v.add(dtoItem);
        }
        return v;
    }
    /**
     * 获取部门和人员下拉框数据
     * @param loginId String
     * @return Map
     */
    public Map getDeptsPersons(String loginId) {
        Map deptMap = new HashMap();
        Map personMap = new HashMap();
        Map relationMap = new HashMap();
        List<TsAccount> deptList = accountDao.listDeptByManager(loginId);
        findDeptChildrenByLoginId(deptMap, deptList);
        List<TsHumanBase> personList = null;
        Map relatedPersonMap = null;
        TsAccount tsAccount = null;
        List<String> list = new ArrayList(deptMap.values());
        if(list !=null && list.size() > 0) {
            for(String deptId : list) {
                deptMap.put(deptId,deptId);
                personList = rmMaintDao.listPersonsByDept(deptId);
//                tsAccount = accountDao.loadByAccountId(deptId);
//                personList = laborDao.listLabor(tsAccount.getRid());
                if(personList == null || personList.size() == 0) {
                    continue;
                }
                relatedPersonMap = new HashMap();
                for(TsHumanBase person : personList) {
                    personMap.put(person.getEmployeeId(),
                                  person.getFullName());
                    relatedPersonMap.put(person.getEmployeeId(),
                                         person.getFullName());
                }
                relationMap.put(deptId, relatedPersonMap);
            }
        }
        Map newRelationMap = sortPersonRelationMap(relationMap);
        Map resultMap = new HashMap();
        Vector deptItem = map2Vector(deptMap);
        Vector personItem = map2PersonVector(personMap);
        resultMap.put(DtoTsDetailReport.DTO_DEPT_LIST, deptItem);
        resultMap.put(DtoTsDetailReport.DTO_PERSON_LIST, personItem);
        resultMap.put(DtoTsDetailReport.DTO_RELATION_MAP, newRelationMap);
        return resultMap;
    }
    /**
     * 将部门和人员关联数据排序并组成下拉框中的数据
     * @param relationMap Map
     * @return Map
     */
    private Map sortPersonRelationMap(Map relationMap) {
        List<String> list = new ArrayList(relationMap.keySet());
         if(list == null || list.size() == 0) {
             return new HashMap();
         }
         Map newRelationMap = new HashMap();
         Map personMap = null;
         List<String> listPerson = null;
         Vector personItem = null;
         for(String deptId : list) {
            personMap = (Map) relationMap.get(deptId);
            listPerson = new ArrayList(personMap.keySet());
            Collections.sort(listPerson, new SortByAccountId());
            personItem = new Vector();
            DtoComboItem dtoItem = new DtoComboItem(SELECT_FIRST_OPTION,
                    "");
            personItem.add(dtoItem);
            for(String personId : listPerson) {
                dtoItem = new DtoComboItem((String)personMap.get(personId), personId);
                personItem.add(dtoItem);
            }
            newRelationMap.put(deptId, personItem);
         }
         return newRelationMap;

    }
    /**
     * 将map中的数据转化为下拉框中的数据
     * @param personMap Map
     * @return Vector
     */
    private Vector map2PersonVector(Map personMap) {
        List<String> list = new ArrayList(personMap.keySet());
        if (list == null || list.size() == 0) {
            return new Vector();
        }
        Collections.sort(list, new SortByAccountId());
        Vector v = new Vector();
        DtoComboItem dtoItem = new DtoComboItem(SELECT_FIRST_OPTION,
                                                "");
        v.add(dtoItem);
        for (String personId : list) {
            dtoItem = new DtoComboItem((String)personMap.get(personId), personId);
            v.add(dtoItem);
        }
        return v;
    }
    /**
     * 导出工时汇总记录
     */
    public List queryGatherForExport(DtoQueryCondition dtoCondition, String loginId, boolean isPMO) {
    	List list = new ArrayList();
    	if(dtoCondition.isIncludeSub()){
    		String deptIds = getDeptIds(dtoCondition.getDeptId());
    		list = tsReportDao.queryGatherReportDept(dtoCondition, deptIds, loginId);
    	} else {
    		String projectIds = "('')";
            if("".equals(dtoCondition.getProjectId())) {
                Vector<DtoComboItem> projectItem = getProjectItem(
                        dtoCondition.getDeptId(), loginId, isPMO);
                if(projectItem != null && projectItem.size() > 0) {
                    int size = projectItem.size();
                    String tmp = "";
                    DtoComboItem item = null;
                    for (int i = 0; i < size; i++) {
                        item = projectItem.get(i);
                        if (i == 0) {
                            tmp = "'" + (String) item.getItemValue() + "'";
                        } else {
                            tmp += ", '" + (String) item.getItemValue() + "'";
                        }
                    }
                    projectIds = "("+tmp+")";
                }
            } else {
                projectIds = "('"+dtoCondition.getProjectId()+"')";
            }
             list = tsReportDao.queryGatherReport(dtoCondition, projectIds);
    	}
         if (list == null || list.size() == 0) {
                 return new ArrayList();
         }
         return obj2ListForGather(list, dtoCondition.getBegin(), dtoCondition.getEnd());

   }
    /**
     * 查询工时汇总报表记录
     */
    public List queryGatherReport(DtoQueryCondition dtoCondition, String loginId) {
    	List list = new ArrayList();
    	if(dtoCondition.isIncludeSub()){
    		String deptIds = getDeptIds(dtoCondition.getDeptId());
    		list = tsReportDao.queryGatherReportDept(dtoCondition, deptIds, loginId);
    	} else {
    		String projectIds = "('')";
            if("".equals(dtoCondition.getProjectId())) {
                Vector<DtoComboItem> projectItem = dtoCondition.getProjectItem();
                if(projectItem != null && projectItem.size() > 0) {
                    int size = projectItem.size();
                    String tmp = "";
                    DtoComboItem item = null;
                    for (int i = 0; i < size; i++) {
                        item = projectItem.get(i);
                        if (i == 0) {
                            tmp = "'" + (String) item.getItemValue() + "'";
                        } else {
                            tmp += ", '" + (String) item.getItemValue() + "'";
                        }
                    }
                    projectIds = "("+tmp+")";
                }
            } else {
                projectIds = "('"+dtoCondition.getProjectId()+"')";
            }
            	list = tsReportDao.queryGatherReport(dtoCondition, projectIds);
    	}
        if (list == null || list.size() == 0) {
                return new ArrayList();
        }
        return obj2ListForGather(list, dtoCondition.getBegin(), dtoCondition.getEnd());
    }
    private List obj2ListForGather(List<Object[]> list, Date begin, Date end) {
        List resultList = new ArrayList();
        DtoTsGatherReport dto = null;
        Double totalWorkHours = new Double(0);
        Double totalOverTimeHours = new Double(0);
        Double totalLeaveHours = new Double(0);
        Double stotalWorkHours = new Double(0);
        Double stotalOverTimeHours = new Double(0);
        Double stotalLeaveHours = new Double(0);
        String lastDeptId = (String)((Object[])list.get(0))[0];
        boolean isClear = false;
        for(Object[] obj : list) {
        	if(obj[0] == null) {
        		continue;
        	}
            dto = getDtoGatherInstance();
             setDtoValueForGather(dto, obj, begin, end);//设置查询结果到DTO中
             totalWorkHours += nvl(dto.getNormalWorkHours());
             totalOverTimeHours += nvl(dto.getOverTimeHours());
             totalLeaveHours += nvl(dto.getLeaveHours());
             //根据条件设置小计
             if(!lastDeptId.equals((String)obj[0])) {
                 insertSumForGather(resultList, stotalWorkHours,
                             stotalOverTimeHours, stotalLeaveHours);
                 isClear = true;
             }
             //小计后将累计数据清除
             if(isClear) {
                 stotalWorkHours = new Double(0);
                 stotalOverTimeHours = new Double(0);
                 stotalLeaveHours = new Double(0);
                 isClear =false;
             }
             stotalWorkHours += nvl(dto.getNormalWorkHours());
             stotalOverTimeHours += nvl(dto.getOverTimeHours());
             stotalLeaveHours += nvl(dto.getLeaveHours());

             lastDeptId = (String)obj[0];
             resultList.add(dto);
         }
         insertSumForGather(resultList, stotalWorkHours,
                             stotalOverTimeHours,
                             stotalLeaveHours);
         dto = getDtoGatherInstance();
         dto.setDeptId("Total");
         dto.setWorkHours(totalWorkHours);
         dto.setOverTimeHours(totalOverTimeHours);
         dto.setLeaveHours(totalLeaveHours);
         dto.setNormalWorkHours(totalWorkHours);
         dto.setTotal(totalWorkHours+totalLeaveHours+totalOverTimeHours);
         dto.setIsLeaveType(false);
         dto.setSumLevel(2);
         resultList.add(dto);
         return resultList;

    }
    private void insertSumForGather(List resultList, Double stotalWorkHours,
                                    Double stotalOverTimeHours,
                                    Double stotalLeaveHours) {
        DtoTsGatherReport dto = getDtoGatherInstance();
        dto.setDeptId("Sum");
        dto.setWorkHours(roundDouble(stotalWorkHours));
        dto.setOverTimeHours(roundDouble(stotalOverTimeHours));
        dto.setLeaveHours(roundDouble(stotalLeaveHours));
        dto.setNormalWorkHours(roundDouble(stotalWorkHours));
        dto.setTotal(roundDouble(stotalWorkHours+stotalLeaveHours+stotalOverTimeHours));
        dto.setIsLeaveType(false);
        dto.setSumLevel(1);
        resultList.add(dto);
    }
    private void setDtoValueForGather(DtoTsGatherReport dto, Object[] obj,
                                      Date begin, Date end) {
        if(DtoTsGatherReport.DTO_PROJECT.equals((String)obj[5])) {
            dto.setDeptId((String)obj[0]);
        } else {
            dto.setDeptId((String)obj[1]);
        }
        boolean isLeaveType = false;
        String leaveTpye = "0";
        if((Boolean)obj[4]) {
        	isLeaveType = true;
        	leaveTpye = "1";
        }
        dto.setProjectId((String)obj[1]);
        dto.setProjectName((String)obj[2]);
        dto.setEstWorkHours(roundDouble((Double)obj[3]));
        List list = tsReportDao.getSum((String)obj[1],begin,end,leaveTpye);
        Object[] objSum = (Object[])list.get(0);
        Double workHours = new Double(0);
        Double overTimeHours = new Double(0);
        if(objSum[0] != null) {
            workHours = (Double)objSum[0];
        }
        if(objSum[1] != null) {
            overTimeHours = (Double)objSum[1];
        }
        dto.setWorkHours(roundDouble(workHours));
        dto.setOverTimeHours(roundDouble(overTimeHours));
        dto.setIsLeaveType(isLeaveType);
        dto.setSumLevel(0);
    }
    public List queryForExport(DtoQueryCondition dtoCondition, String loginId, boolean isPMO) {
        String queryWay = dtoCondition.getQueryWay(); //取出查询方式，区别不同角色的查询
        String loginIds = "";
        String orderBy = "";
        String acntRids = null;
        List<Object[]> list = null;
        //如果是一般人员查询,设置当前查询人的loginId为查询条件
        if (DtoQueryCondition.DTO_QUERY_WAY_EMP.equals(queryWay)) {
            loginIds = "(lower('" + loginId + "'))";
            list = tsReportDao.queryReport(dtoCondition, loginIds, orderBy);
            if (list == null || list.size() == 0) {
                return new ArrayList();
            }
            return obj2ListForEmp(list, queryWay);
            //如果是PM查询或者是RM使用专案界面查询
            //根据输入条件查询要显示的工时单填写人的loginId
        } else if (DtoQueryCondition.DTO_QUERY_WAY_PM.equals(queryWay)
                 ||DtoQueryCondition.DTO_QUERY_WAY_RM.equals(queryWay) ) {
        	if(dtoCondition.isIncludeSub()){
        		String deptIds = getDeptIds(dtoCondition.getDeptId());
        		list = tsReportDao.queryReportForPmDept(dtoCondition, deptIds, loginId);
        	} else {
        		if("".equals(dtoCondition.getProjectId())) {
                    dtoCondition.setProjectItem(getProjectItem(
                            dtoCondition.getDeptId(), loginId, isPMO));
                }
                Map resultMap = getLoginIdsByProject(dtoCondition);
                if(resultMap.containsKey("loginIds")) {
                	loginIds = (String)resultMap.get("loginIds");
                }
                if(resultMap.containsKey("acntRids")) {
                	acntRids = (String)resultMap.get("acntRids");
                }
                orderBy = "order by a.accountId";
                list = tsReportDao.queryReportForPm(dtoCondition, loginIds, orderBy, acntRids);
        	}
            if (list == null || list.size() == 0) {
                return new ArrayList();
            }
            return obj2ListForPm(list, queryWay, loginId);
        } else if(DtoQueryCondition.DTO_QUERY_WAY_RMP.equals(queryWay)) {
        	if(dtoCondition.isIncludeSub()){
        		String deptIds = getDeptIds(dtoCondition.getDeptId());
        		list = tsReportDao.queryReportByDept(dtoCondition, deptIds);
        	} else {
        		if("".equals(dtoCondition.getLoginId())){
                    dtoCondition.setPersonItem(getPersonItem(
                            dtoCondition.getDeptId(), loginId, isPMO));
                }
                loginIds = getLoginIds(dtoCondition);
                orderBy = "order by m.loginId";
                list = tsReportDao.queryReport(dtoCondition, loginIds, orderBy);
        	}
            if (list == null || list.size() == 0) {
                return new ArrayList();
            }
            return obj2ListForRmP(list, queryWay);
        }
        return new ArrayList();

    }
    public Vector getPersonItem(String deptId, String loginId, boolean isPMO) {
    	if(isPMO) {
    		return this.getPersonsForPmo(deptId);
    	} else {
    		Map resultMap = getDeptsPersons(loginId);
            Map relationMap = (Map) resultMap.get(DtoTsDetailReport.DTO_RELATION_MAP);
            if(!"".equals(deptId)){
                return (Vector) relationMap.get(deptId);
            } else {
                return (Vector) resultMap.get(DtoTsDetailReport.DTO_PERSON_LIST);
            }
    	}
    }
    public Vector getProjectItem(String deptId, String loginId, boolean isPMO) {
    	if(isPMO){
    		return this.getProjectsForPmo(deptId);
    	} else {
    		Map resultMap = getDeptsProjects(loginId);
            Map relationMap = (Map) resultMap.get(DtoTsDetailReport.DTO_RELATION_MAP);
            if(!"".equals(deptId)){
                return (Vector) relationMap.get(deptId);
            } else {
                return (Vector) resultMap.get(DtoTsDetailReport.DTO_PROJECT_LIST);
            }
    	}
    }
    
    /**
     * 根据输入的时间范围将工时单划分成若干个周期，查询出实际工时，加班，请假工时
     * @param beginDate
     * @param endDate
     * @param loginId
     * @return Map
     */
    public Map queryByPeriodForExport(Date beginDate,Date endDate,
            String loginId) {
            Map tsMap = new TreeMap();
            List actList = null;
            DtoTsByPeriod dtoTs;
            String dateStr;
            String beginStr;
            String endStr;
            try{
             TsHumanBase tsHumanBase = rmMaintDao.loadHumanById(loginId);
             if(tsHumanBase != null){
              List periodList= periodDao.getPeriodByDate(beginDate,endDate);
              for(int i=0;i<periodList.size();i++){
                dtoTs = new DtoTsByPeriod();
                DtoTimeSheetPeriod dtoPeriod = (DtoTimeSheetPeriod)periodList.get(i);
                Date begin = dtoPeriod.getBeginDate();
                Date end = dtoPeriod.getEndDate();
                beginStr = comDate.dateToString(begin,comDate.pattenDate);
                endStr = comDate.dateToString(end,comDate.pattenDate);
                dateStr = beginStr +"~"+ endStr;
                //實際工作時數
                actList = tsReportDao.getActualHoursInfo(loginId,begin,end);
                List newActList = copyPropertiesToDtoList(actList,HSSFColor.LIGHT_GREEN.index);
                //請假時數
                List leaveList = tsReportDao.getLeaveHoursInfo(loginId,begin,end);
                List newLeaveList = copyPropertiesToDtoList(leaveList,HSSFColor.LIGHT_ORANGE.index);
                //加班時數
                List otList = tsReportDao.getOvertimeHoursInfo(loginId,begin,end);
                List newOTList = copyPropertiesToDtoList(otList,HSSFColor.YELLOW.index);
                dtoTs.setDateStr(dateStr);
                dtoTs.setEmpId(tsHumanBase.getEmployeeId());
                dtoTs.setEmpName(tsHumanBase.getFullName());
                dtoTs.setUnitCode(tsHumanBase.getUnitCode());
                dtoTs.setLeaveHoursList(newLeaveList);
                dtoTs.setActHoursList(newActList);
                dtoTs.setOtHoursList(newOTList);
                //標準工時
                List dateList = getDateList(begin,end);
                List standHoursList = timeSheetApiDao.listStandarHours(loginId,dateList);
                List standHList = getStandHoursList(dateList,standHoursList);
                dtoTs.setStandardHoursList(standHList);
                if((newActList != null && newActList.size() > 0)
                        || (newLeaveList != null && newLeaveList.size( )> 0) 
                        || (newOTList != null && newOTList.size() > 0)){
                tsMap.put(dateStr,dtoTs);
                }
              }
             }
            }catch(Exception e){
                e.printStackTrace();
                throw new BusinessException("B3001", "QueryByPeriodForExport is error.");
            }
            return tsMap;
      }
      
    /**
     * 得到指定日期范圍內的標準工時
     * @param dateList
     * @param standList
     * @return List
     */
      private List getStandHoursList(List dateList, List standList){
              List standHoursList = new ArrayList();
              DtoTimesheetReport dtoTs = getDtoTimesheetInstance();
              dtoTs.setColorIndex(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
              DtoTimesheetReport dtoSum = new DtoTimesheetReport();
              for(int i = 0; i<dateList.size();i++){
                  Date workDate = (Date)dateList.get(i);
                  Double workHours = (Double)standList.get(i);
                  setDate(dtoTs,dtoSum,workDate,workHours);
              }
              standHoursList.add(dtoTs);
              return standHoursList;
      }
    
        /**
         * 根据时间区间将日期划分成天存放在LIST中
         * @param beginDate
         * @param endDate
         * @return List
         */
        private List getDateList(Date beginDate, Date endDate){
            List dateList = new ArrayList();
            dateList.add(beginDate);
            while(endDate.after(beginDate)){
                 Calendar calendar = Calendar.getInstance();
                 calendar.setTime(beginDate);
                 calendar.add(Calendar.DATE, 1);
                 Date date = calendar.getTime();
                 dateList.add(date);
                 beginDate = date;
            }
            return dateList;
        }
    
        /**
         * 将得到的数据放入DTO中
         * @param resList
         * @return List
         */
        private List copyPropertiesToDtoList(List resList,short colour){
                DtoTimesheetReport dtoTs = getDtoTimesheetInstance();
                dtoTs.setColorIndex(colour);
                List newList = new ArrayList();
                DtoTimesheetReport dtoSum =getDtoTimesheetInstance();
                dtoSum.setUnitCode("Sum");
                dtoSum.setColorIndex(HSSFColor.WHITE.index);
                String oldProjId = null;
                for(int i=0;i<resList.size();i++){
                    Object[] obj = (Object[])resList.get(i);
                    String projId = (String)obj[0];
                    String projName = (String)obj[1];
                    String unitCode = (String)obj[2];
                    Date workDate = (Date)obj[3];
                    String description = (String)obj[4];
                    Double workHours = (Double)obj[5];
                    Long detailRid = (Long)obj[6];
                    if(oldProjId!= null && !oldProjId.equals(projId)){//專案代碼不同時，新建一條記錄
                        newList.add(dtoTs);
                        oldProjId = projId;
                        dtoTs = getDtoTimesheetInstance();
                        dtoTs.setColorIndex(colour);
                        dtoTs.setProjId(projId);
                        dtoTs.setProjName(projName);
                        dtoTs.setUnitCode(unitCode);
                        dtoTs.setDetailRid(detailRid);
                        dtoTs.setDescription(description);
                    }else if(oldProjId!= null && 
                            !detailRid.equals(dtoTs.getDetailRid())){//專案代號相同，但工作代碼不同時新建一條記錄
                        newList.add(dtoTs);
                        dtoTs = getDtoTimesheetInstance();
                        dtoTs.setColorIndex(colour);
                        dtoTs.setUnitCode(unitCode);
                        dtoTs.setDescription(description);
                        dtoTs.setDetailRid(detailRid);
                    }else if(oldProjId == null){//當i=0時
                        oldProjId = projId;
                        dtoTs.setProjId(projId);
                        dtoTs.setProjName(projName);
                        dtoTs.setUnitCode(unitCode);
                        dtoTs.setDescription(description);
                        dtoTs.setDetailRid(detailRid);
                    }
                    dtoTs = setDate(dtoTs,dtoSum,workDate,workHours);
                    if(i==resList.size()-1){
                        newList.add(dtoTs);
                    }
                }
                if(resList != null && resList.size() > 0){
                  newList.add(dtoSum);
                }
                return newList;
        }
    
    /**
     * 根据日期將其放入對應的周幾中
     * @param dtoTs
     * @param dtoSum
     * @param date
     * @param hours
     * @return DtoTimesheetReport
     */
    private DtoTimesheetReport setDate(DtoTimesheetReport dtoTs,
            DtoTimesheetReport dtoSum,Date date,Double hours){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int week = cal.get(Calendar.DAY_OF_WEEK);
            switch(week){
              case 1:
                  dtoTs.setSunHours(hours);
                  dtoSum.setSunHours(dtoSum.getSunHours()+hours);
              break;
              case 2:
                  dtoTs.setMonHours(hours);
                  dtoSum.setMonHours(dtoSum.getMonHours()+hours);
              break;
              case 3:
                  dtoTs.setTueHours(hours);
                  dtoSum.setTueHours(dtoSum.getTueHours()+hours);
              break;
              case 4:
                  dtoTs.setWedHours(hours);
                  dtoSum.setWedHours(dtoSum.getWedHours()+hours);
              break;
              case 5:
                  dtoTs.setThursHours(hours);
                  dtoSum.setThursHours(dtoSum.getThursHours()+hours);
              break;
              case 6:
                  dtoTs.setFriHours(hours);
                  dtoSum.setFriHours(dtoSum.getFriHours()+hours);
              break;
              case 7:
                  dtoTs.setSatHours(hours);
                  dtoSum.setSatHours(dtoSum.getSatHours()+hours);
              break;
        }
          dtoTs.setSumHours(dtoTs.getSumHours() + hours);
          dtoSum.setSumHours(dtoSum.getSumHours() + hours);
          return dtoTs;
    }
    
    public void setTsReportDao(ITsReportDao tsReportDao) {
        this.tsReportDao = tsReportDao;
    }

    public void setAccountDao(IAccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setLaborDao(ILaborDao laborDao) {
        this.laborDao = laborDao;
    }

    public void setExcelDto(boolean excelDto) {
        this.excelDto = excelDto;
    }

    public void setPeriodDao(IPeriodDao periodDao) {
        this.periodDao = periodDao;
    }
    
    private DtoTsGatherReport getDtoGatherInstance() {
        if(excelDto) {
            return new StyledDtoTsGatherReport();
        } else {
            return new DtoTsGatherReport();
        }
    }

    private DtoTsDetailReport getDtosDetailInstance() {
        if(excelDto) {
            return new StyledDtoTsDetailReport();
        } else {
            return new DtoTsDetailReport();
        }
    }

    private boolean excelDto = false;

    public class StyledDtoTsGatherReport extends DtoTsGatherReport
            implements ICellStyleSwitch {
        public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
            return LevelCellStyleUtils.getStyleByName(styleName, cellStyle);
        }

        public String getStyleName(String propertyName) {
            return LevelCellStyleUtils.getStyleName(this);
        }
    }

    public class StyledDtoTsDetailReport extends DtoTsDetailReport implements ICellStyleSwitch {
        public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
            return LevelCellStyleUtils.getStyleByName(styleName, cellStyle);
        }

        public String getStyleName(String propertyName) {
            return LevelCellStyleUtils.getStyleName(this);
        }
    }

    private DtoTimesheetReport getDtoTimesheetInstance() {
            return new StyledDtoTsReport();
    }

    public class StyledDtoTsReport extends DtoTimesheetReport
            implements ICellStyleSwitch {
        public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
            if (!"".equals(styleName)) {
                cellStyle.setFillPattern((short) HSSFCellStyle.SOLID_FOREGROUND);
                cellStyle.setFillForegroundColor(Short.valueOf(styleName));
            }
            return cellStyle;
        }

        public String getStyleName(String propertyName) {
            
            return this.getColorIndex() + "";
        }
    }
    
    /**
     *
     * <p>Title: SortByAccountId</p>
     *
     * <p>Description: 用于对list排序的类</p>
     *
     * <p>Copyright: Copyright (c) 2007</p>
     *
     * <p>Company: WistronITS</p>
     *
     * @author wenhaizheng
     * @version 1.0
     */
    private static class SortByAccountId implements Comparator{
           public int compare(Object o1, Object o2) {
               if(o1 instanceof String
                   && o2 instanceof String ){
                   String d1 = (String) o1;
                   String d2 = (String) o2;
                   return d1.compareTo(d2);
                }
                return 0;
           }
           public boolean equals(Object obj) {
               return false;
           }
    }


    public void setTimeSheetApiDao(ITimeSheetP3ApiDao timeSheetApiDao) {
        this.timeSheetApiDao = timeSheetApiDao;
    }
    public void setRmMaintDao(IRmMaintDao rmMaintDao) {
        this.rmMaintDao = rmMaintDao;
    }
    public void setRmMaintService(IRmMaintService rmMaintService) {
        this.rmMaintService = rmMaintService;
    }
  
    
}
