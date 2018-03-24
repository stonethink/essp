package server.essp.timesheet.report.humanreport.service;

import java.math.BigDecimal;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import server.essp.common.ldap.LDAPUtil;
import server.essp.common.mail.ContentBean;
import server.essp.common.mail.SendHastenMail;
import server.essp.timesheet.preference.dao.IPreferenceDao;
import server.essp.timesheet.report.LevelCellStyleUtils;
import server.essp.timesheet.report.humanreport.dao.IHumanReportDao;
import server.essp.timesheet.weeklyreport.dao.ITimeSheetP3ApiDao;
import c2s.dto.DtoComboItem;
import c2s.essp.common.user.DtoUserInfo;
import c2s.essp.timesheet.report.*;

import com.wits.excel.ICellStyleSwitch;
import com.wits.util.comDate;

public class HumanReportServiceImp implements IHuamnReportService {
	
	private IHumanReportDao humanReportDao;
	private IPreferenceDao preferenceDao;
	private ITimeSheetP3ApiDao timeSheetApiDao;
	private Map standarHoursMap = new HashMap();
	private boolean excelDto = false;
	private static final String vmFile = "mail/template/ts/CallingMail.htm";
	
	/**
	 * 查询人力报表资料
	 */
	public Map queryHumanReport(Date begin, Date end, String site) {
		Map resultMap = new HashMap();
		resultMap.put(DtoHumanReport.DTO_QUERY_RESULT, new ArrayList());
		resultMap.put(DtoHumanTimes.DTO_QUERY_LIST, new ArrayList());
		//查询人力报表所需要的资料
		List<Object[]> list = humanReportDao.queryHumanReportByDate(begin, end, site);
		if(list == null || list.size() == 0) {
			return resultMap;
		}
		//查每个人的专案总工时
		List<Object[]> totalHoursList = humanReportDao.getTotalHoursList(begin, end, site);
		if(totalHoursList == null || totalHoursList.size() == 0) {
			return resultMap;
		}
		Map totalHoursMap = new HashMap();
		for(Object[] obj : totalHoursList) {
			totalHoursMap.put(((String)obj[0]).toUpperCase(), nvl((Double)obj[1]) - nvl((Double)obj[2]));
		}
		//查询人对应的专案代号，以便查询最后一个专案时使用
		List<Object[]> nameList = humanReportDao.queryNameAndProject(begin, end, site);
		if(nameList == null || nameList.size() == 0) {
			return resultMap;
		}
		Map lastProjectMap = new HashMap();
		for(Object[] obj : nameList) {
			//按查询出人力报表资料的顺序，将工号和专案名称放入MAP
			//循环结束后MAP保存某个人对应在人力报表中的最后一个专案
			lastProjectMap.put(((String)obj[0]).toUpperCase(), obj[1]);
		}
		//查询某人在某期间内某专案中的总加班时间
		List<Object[]> overtimeList = humanReportDao.queryOvertimeHours(begin, end, site, "P");
		if(overtimeList == null || overtimeList.size() == 0) {
			overtimeList = new ArrayList();
		}
		Map overtimeMap = new HashMap();
		Map humanAccountMap = null;
		for(Object[] obj : overtimeList) {
			if(overtimeMap.containsKey((String)obj[0])) {
				humanAccountMap = (Map) overtimeMap.get((String)obj[0]);
				humanAccountMap.put(((String)obj[1]).toUpperCase(), nvl((Double)obj[2]));
			} else {
				humanAccountMap = new HashMap();
				humanAccountMap.put((String)obj[1], nvl((Double)obj[2]));
				overtimeMap.put((String)obj[0], humanAccountMap);
			}
		}
		List dateList = getDateList(begin, end);
		DtoHumanReport dto = null;
		Double totalHours = new Double(0);
		Double totalOvertimeHours = new Double(0);
		Double totalAssignHours = new Double(0);
		Double stotalHours = new Double(0);
		Double stotalOvertimeHours = new Double(0);
		Double stotalAssignHours = new Double(0);
		String lastProjectId = (String)((Object[])list.get(0))[0];
		List resultList = new ArrayList();
        boolean isClear = false;
        Map sumAssignMap = new HashMap();
		for(Object[] obj : list) {
			dto = getDtoInstance();
			setDtoValue(dto, obj, begin, end, totalHoursMap, 
					dateList, lastProjectMap, sumAssignMap, overtimeMap);//设置查询结果到DTO中
			//累计各工时总数
			totalHours += dto.getActualHours();
			totalAssignHours += dto.getAssignedHours();
			totalOvertimeHours += dto.getOvertimeHours();
			if(!lastProjectId.equals((String)obj[0])) {
				insertSum(resultList, stotalHours, stotalAssignHours,
						stotalOvertimeHours, lastProjectId);
				isClear = true;
			}
			//小计后将累计数据清除
            if(isClear) {
            	stotalHours = new Double(0);
            	stotalAssignHours = new Double(0);
                stotalOvertimeHours = new Double(0);
                isClear = false;
            }
            stotalHours += dto.getActualHours();
            stotalAssignHours += dto.getAssignedHours();
            stotalOvertimeHours += dto.getOvertimeHours();
            lastProjectId = (String)obj[0];
            resultList.add(dto);
		}
		//插入最后一条小计
		insertSum(resultList, stotalHours, stotalAssignHours,
				stotalOvertimeHours, lastProjectId);
		//插入总计
		dto = getDtoInstance();
        dto.setProjectId("Total");
        dto.setActualHours(totalHours);
        dto.setAssignedHours(totalAssignHours);
        dto.setOvertimeHours(totalOvertimeHours);
        dto.setSumLevel(2);
        resultList.add(dto);
        
        resultMap.put(DtoHumanReport.DTO_QUERY_RESULT, resultList);
        resultMap.put(DtoHumanTimes.DTO_QUERY_LIST, 
        		getHumanTimeList(begin, end, site));
		return resultMap;
	}
	/**
	 * 获取统计报表的数据
	 * @param begin
	 * @param end
	 * @param site
	 * @return
	 */
	private List getHumanTimeList(Date begin, Date end, String site) {
		List<Object[]> list = humanReportDao.queryHumanTimes(begin, end, site);
		if(list == null || list.size() == 0) {
			return new ArrayList();
		}
		//查询某人在某期间内某部门中的总请假时间
		List<Object[]> leaveList = humanReportDao.queryLeaveHours(begin, end, site);
		if(leaveList == null || leaveList.size() == 0) {
			leaveList = new ArrayList();
		}
		Map leaveMap = new HashMap();
		Map tempMap = null;
		for(Object[] obj : leaveList) {
			if(leaveMap.containsKey((String)obj[0])) {
				tempMap = (Map) leaveMap.get((String)obj[0]);
				tempMap.put(((String)obj[1]).toUpperCase(), nvl((Double)obj[2]));
			} else {
				tempMap = new HashMap();
				tempMap.put(((String)obj[1]).toUpperCase(), nvl((Double)obj[2]));
				leaveMap.put((String)obj[0], tempMap);
			}
		}
		//查询某人在某期间内某部门中的总加班时间
		List<Object[]> overtimeList = humanReportDao.queryOvertimeHours(begin, end, site, "D");
		if(overtimeList == null || overtimeList.size() == 0) {
			overtimeList = new ArrayList();
		}
		Map overtimeMap = new HashMap();
		Map map = null;
		for(Object[] obj : overtimeList) {
			if(overtimeMap.containsKey((String)obj[0])) {
				map = (Map) overtimeMap.get((String)obj[0]);
				map.put(((String)obj[1]).toUpperCase(), nvl((Double)obj[2]));
			} else {
				map = new HashMap();
				map.put(((String)obj[1]).toUpperCase(), nvl((Double)obj[2]));
				overtimeMap.put((String)obj[0], map);
			}
		}
		List resultList = new ArrayList();
		DtoHumanTimes dto = null;
		Double totalHours = new Double(0);
		Double totalNormalHours = new Double(0);
		Double totalOvertimeHours = new Double(0);
		Double totalLeaveHours = new Double(0);
		Double totalStandarHours = new Double(0);
		Double totalPlus = new Double(0);
		Double totalNegative = new Double(0);
		Double stotalHours = new Double(0);
		Double stotalOvertimeHours = new Double(0);
		Double stotalLeaveHours = new Double(0);
		Double stotalStandarHours = new Double(0);
		Double stotalPlus = new Double(0);
		Double stotalNegative = new Double(0);
		String unitCode = (String)((Object[])list.get(0))[0];
		String unitName = (String)((Object[])list.get(0))[1];
		boolean isClear = false;
		for(Object[] obj : list) {
			dto = getDtoTimesInstance();
			setTimesDto(dto, obj, begin, end, overtimeMap, leaveMap);
			totalHours += dto.getActualHours();
			totalStandarHours += dto.getStandarHours();
			totalNormalHours += dto.getNormalHours();
			totalOvertimeHours += dto.getOvertimeHours();
			totalLeaveHours +=dto.getLeaveHours();
			if(dto.getBalance().doubleValue() >= 0) {
				totalPlus += dto.getBalance().doubleValue();
			} else {
				totalNegative += dto.getBalance().doubleValue();
			}
			if(!unitCode.equals((String)obj[0])) {
				insertSumForTimes(resultList, stotalHours, stotalStandarHours,
						stotalOvertimeHours,stotalLeaveHours, unitCode, unitName,
						stotalPlus, stotalNegative);
				isClear = true;
			}
			//小计后将累计数据清除
            if(isClear) {
            	stotalHours = new Double(0);
            	stotalStandarHours = new Double(0);
            	stotalLeaveHours = new Double(0);
                stotalOvertimeHours = new Double(0);
                stotalPlus = new Double(0);
                stotalNegative = new Double(0);
                isClear = false;
            }
            stotalHours += dto.getActualHours();
            stotalStandarHours += dto.getStandarHours();
            stotalOvertimeHours += dto.getOvertimeHours();
			stotalLeaveHours += dto.getLeaveHours();
			if(dto.getBalance().doubleValue() >= 0) {
				stotalPlus += dto.getBalance().doubleValue();
			} else {
				stotalNegative += dto.getBalance().doubleValue();
			}
			unitCode = (String)obj[0];
			unitName = (String)obj[1];
			resultList.add(dto);
		}
		insertSumForTimes(resultList, stotalHours, stotalStandarHours,
				stotalOvertimeHours,stotalLeaveHours, unitCode, unitName,
				stotalPlus, stotalNegative);
		dto = getDtoTimesInstance();
		dto.setUnitCode("Total");
		dto.setSumLevel(2);
		dto.setActualHours(totalHours);
		dto.setStandarHours(totalStandarHours);
		dto.setOvertimeHours(totalOvertimeHours);
		dto.setLeaveHours(totalLeaveHours);
		BigDecimal plus = new BigDecimal(totalPlus);
		plus = plus.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal negative = new BigDecimal(totalNegative);
		negative = negative.setScale(2, BigDecimal.ROUND_HALF_UP);
		dto.setSumBalance(plus+"/"+negative);
		resultList.add(dto);
		return resultList;
	}
	/**
	 * 工时统计表中插入小计
	 * @param resultList
	 * @param stotalHours
	 * @param stotalStandarHours
	 * @param stotalOvertimeHours
	 * @param stotalLeaveHours
	 * @param unitCode
	 * @param unitName
	 */
	private void insertSumForTimes(List resultList, Double stotalHours,
			Double stotalStandarHours,Double stotalOvertimeHours,
			Double stotalLeaveHours,String unitCode, String unitName,
			Double stotalPlus, Double stotalNegative) {
		DtoHumanTimes dto = getDtoTimesInstance();
		dto.setSumLevel(1);
		dto.setUnitCode(unitCode);
		dto.setUnitName(unitName);
		dto.setActualHours(stotalHours);
		dto.setStandarHours(stotalStandarHours);
		dto.setOvertimeHours(stotalOvertimeHours);
		dto.setLeaveHours(stotalLeaveHours);
		BigDecimal plus = new BigDecimal(stotalPlus);
		plus = plus.setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal negative = new BigDecimal(stotalNegative);
		negative = negative.setScale(2, BigDecimal.ROUND_HALF_UP);
		dto.setSumBalance(plus+"/"+negative);
		resultList.add(dto);
	}
	/**
	 * 设置工时统计表的数据
	 * @param dto
	 * @param obj
	 * @param begin
	 * @param end
	 * @param overtimeMap
	 * @param leaveMap
	 */
	private void setTimesDto(DtoHumanTimes dto, Object[] obj, Date begin, Date end,
			Map overtimeMap, Map leaveMap) {
		String loginId = (String) obj[2];
		String unitCode = (String) obj[0];
		dto.setUnitCode((String)obj[0]);
		dto.setUnitName((String)obj[1]);
		dto.setEmpId((String)obj[2]);
		dto.setEmpName((String)obj[3]);
		dto.setBeginDate(begin);
		dto.setEndDate(end);
		dto.setEmpProperty((String)obj[4]);
		dto.setActualHours((Double)obj[5]);
		
		//获取标准工时
		if(standarHoursMap.containsKey(loginId.toUpperCase())){
			dto.setStandarHours((Double)standarHoursMap.get(loginId.toUpperCase()));
		} else {
			Double standardHour = new Double(0);
			List dateList = getDateList(begin, end);
			List standHoursList = timeSheetApiDao.listStandarHours(loginId,dateList);
			if(standHoursList != null && standHoursList.size() > 0){
	            standardHour = (Double)standHoursList.get(standHoursList.size() - 1);
	            standarHoursMap.put(loginId.toUpperCase(), standardHour);
			}
			dto.setStandarHours(standardHour);
		}
		//获取加班工时
		if(overtimeMap.containsKey(unitCode)){
			Map oTempMap = (Map) overtimeMap.get(unitCode);
			if(oTempMap.containsKey(loginId.toUpperCase())) {
				dto.setOvertimeHours((Double)oTempMap.get(loginId.toUpperCase()));
			} else {
				dto.setOvertimeHours(new Double(0));
			}
		} else {
			dto.setOvertimeHours(new Double(0));
		}
		//获取请假工时
		if(leaveMap.containsKey(unitCode)) {
			Map lTempMap = (Map) leaveMap.get(unitCode);
			if(lTempMap.containsKey(loginId.toUpperCase())) {
				dto.setLeaveHours((Double)lTempMap.get(loginId.toUpperCase()));
			} else {
				dto.setLeaveHours(new Double(0));
			}
		} else {
			dto.setLeaveHours(new Double(0));
		}
		dto.setSumLevel(0);
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
     * 四舍五入
     * @param d
     * @return
     */
    private Double roundDouble(Double d){
    	if(d == null) {
    		return new Double(0.00);
    	}
    	BigDecimal b = new BigDecimal(d);
    	b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
    	return b.doubleValue();
    }
    /**
     * 插入专案小记资料
     * @param resultList
     * @param stotalHours
     * @param stotalAssignHours
     * @param lastProjectId
     */
	private void insertSum(List resultList, Double stotalHours, 
			Double stotalAssignHours, 
			Double stotalOvertimeHours, String lastProjectId) {
		DtoHumanReport dto = getDtoInstance();
		dto.setSumLevel(1);
		List objList = humanReportDao.getProjectInfo(lastProjectId);
		Object[] obj = null;
		if(objList !=null && objList.size() >0){
			obj = (Object[]) objList.get(0);
		}
		if(obj != null) {
			dto.setProjectName((String)obj[1]);
			dto.setDeptId((String)obj[2]);
			dto.setDeptName((String)obj[3]);
			dto.setEmpId((String)obj[4]);
			dto.setEmpName((String)obj[5]);
			dto.setQueryFlag("Sum||"+dto.getProjectName());
			String billable = (String)obj[6];
			if("1".equals(billable)) {
				dto.setEmpProperty("Billable");
			} else {
				dto.setEmpProperty("NonBillable");
			}
		}
		dto.setProjectId("Sum");
		dto.setActualHours(stotalHours);
		dto.setAssignedHours(stotalAssignHours);
		dto.setOvertimeHours(stotalOvertimeHours);
		resultList.add(dto);
	}
	/**
	 * 将资料填入DTO，计算各专案分配工时
	 * @param dto
	 * @param obj
	 * @param begin
	 * @param end
	 * @param totalHoursMap
	 * @param dateList
	 * @param lastProjectMap
	 * @param sumAssignMap
	 */
	private void setDtoValue(DtoHumanReport dto, Object[] obj, 
			Date begin, Date end, Map totalHoursMap, 
			List dateList, Map lastProjectMap, Map sumAssignMap, Map overtimeMap) {
		dto.setSumLevel(0);
		dto.setProjectId((String)obj[0]);
		dto.setProjectName((String)obj[1]);
		dto.setProjectType(DtoHumanReport.codeToName((String)obj[2]));
		dto.setDeptId((String)obj[3]);
		dto.setDeptName((String)obj[4]);
		dto.setEmpName((String)obj[6]);
		dto.setEmpType(DtoHumanReport.e2c((String)obj[7]));
		dto.setEmpProperty((String)obj[8]);
		dto.setBeginDate(begin);
		dto.setEndDate(end);
		dto.setActualHours(nvl((Double)obj[9]));
		String loginId = (String)obj[5];
		List beginEnd = humanReportDao.queryBeginEnd(dto.getProjectId(), loginId, begin, end);
		if(beginEnd != null && beginEnd.size() >0){
			Object[] objDate = (Object[]) beginEnd.get(0);
			dto.setBeginDate((Date)objDate[1]);
			if(beginEnd.size() > 1){
				objDate = (Object[]) beginEnd.get(beginEnd.size() - 1);
			}
			dto.setEndDate((Date)objDate[2]);
		}
		
		if(overtimeMap.containsKey((String)obj[0])){
			Map humanAccountMap = (Map) overtimeMap.get((String)obj[0]);
			if(humanAccountMap.containsKey(loginId.toUpperCase())) {
				dto.setOvertimeHours((Double)humanAccountMap.get(loginId.toUpperCase()));
			} else {
				dto.setOvertimeHours(new Double(0));
			}
		} else {
			dto.setOvertimeHours(new Double(0));
		}
		dto.setEmpId(loginId);
		Double standardHour = new Double(0);
		//查询标准工时
		if(standarHoursMap.containsKey(loginId.toUpperCase())) {
			standardHour = (Double) standarHoursMap.get(loginId.toUpperCase());
		} else {
			List standHoursList = timeSheetApiDao.listStandarHours(loginId,dateList);
			if(standHoursList != null && standHoursList.size() > 0){
	            standardHour = (Double)standHoursList.get(standHoursList.size() - 1);
	            standarHoursMap.put(loginId.toUpperCase(), standardHour);
			}
		}
		
		Double totalHours = new Double(0);
		//取出专案总工时
		if(totalHoursMap.containsKey(loginId.toUpperCase())) {
			totalHours = (Double) totalHoursMap.get(loginId.toUpperCase());
		}
		if(lastProjectMap.containsKey(loginId.toUpperCase())) {
			//取出某人在人力报表资料中的最后个专案
			String projectId = (String) lastProjectMap.get(loginId.toUpperCase());
			//如果当前计算的是最后个专案的分配工时
			//则用标准工时减去除最后一个专案的其他专案累加的分配工时得到最后个专案的分配工时
			if(dto.getProjectId().equals(projectId)){
				Double assignedHours = standardHour.doubleValue() - (nvl((Double)sumAssignMap.get(loginId.toUpperCase()))).doubleValue();
				dto.setAssignedHours(assignedHours);
			} else {//如果不是计算最后个专案
				if(dto.getActualHours() == 0 || totalHours == 0 || standardHour == 0) {
					dto.setAssignedHours(new Double(0));//计算数据中有0则分配工时为0
				} else if(dto.getActualHours() != 0 && totalHours != 0 && standardHour != 0) {
					//计算分配工时，公式为：本专案实际工时/所有专案总工时*标准工时
					Double assignedHours = dto.getActualHours().doubleValue() / totalHours.doubleValue() * standardHour;
					dto.setAssignedHours(roundDouble(assignedHours));
					//将不是最后一个专案的分配工时进行累加放入Map中
					if(sumAssignMap.containsKey(loginId.toUpperCase())) {
						Double sum = (Double) sumAssignMap.get(loginId.toUpperCase());
						sum += dto.getAssignedHours();
						sumAssignMap.put(loginId.toUpperCase(), sum);
					} else {
						sumAssignMap.put(loginId.toUpperCase(), dto.getAssignedHours());
					}
				}
			}
		}
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
	public List queryHumanReportForExport(Date begin, Date end, String site) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setExcelDto(boolean excelDto) {
		this.excelDto = excelDto;
	}
	private DtoHumanReport getDtoInstance() {
        if(excelDto) {
            return new StyledDtoHumanReport();
        } else {
            return new DtoHumanReport();
        }
    }
	private DtoHumanTimes getDtoTimesInstance() {
		if(excelDto) {
            return new StyledDtoHumanTimes();
        } else {
            return new DtoHumanTimes();
        }
	}
	 public class StyledDtoHumanReport extends DtoHumanReport implements ICellStyleSwitch {
	        public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
	            return LevelCellStyleUtils.getStyleByName(styleName, cellStyle);
	        }

	        public String getStyleName(String propertyName) {
	            return LevelCellStyleUtils.getStyleName(this);
	        }
	 }
	 public class StyledDtoHumanTimes extends DtoHumanTimes implements ICellStyleSwitch {
	        public HSSFCellStyle getStyle(String styleName, HSSFCellStyle cellStyle) {
	            return LevelCellStyleUtils.getStyleByName(styleName, cellStyle);
	        }

	        public String getStyleName(String propertyName) {
	            return LevelCellStyleUtils.getStyleName(this);
	        }
	 }
	/**
	 * 获取数据库中的所有site
	 */ 
	public Vector getSites() {
		List<String> list = preferenceDao.listSitesInTsHumanbase();
		Vector sites = new Vector();
		if(list == null || list.size() == 0) {
			return sites;
		}
		DtoComboItem item = null;
		for(String site : list) {
			item = new DtoComboItem(site.toUpperCase(), site.toUpperCase());
			sites.add(item);
		}
		return sites;
	}

	public void setHumanReportDao(IHumanReportDao humanReportDao) {
		this.humanReportDao = humanReportDao;
	}

	public void setPreferenceDao(IPreferenceDao preferenceDao) {
		this.preferenceDao = preferenceDao;
	}
	public void setTimeSheetApiDao(ITimeSheetP3ApiDao timeSheetApiDao) {
		this.timeSheetApiDao = timeSheetApiDao;
	}
	public void clearMap() {
		standarHoursMap.clear();
	}
	public void sendMails(List<DtoHumanTimes> sendList) {
		HashMap hm = new HashMap();
		DtoUserInfo dtoUser = null;
		ArrayList al = null;
		ContentBean cb = null;
		String userMail = null;
		String userName = null;
		LDAPUtil ldapUtil = new LDAPUtil();
		DtoHumanMail dtoMail = null;
		for(DtoHumanTimes dto : sendList){
			dtoMail = new DtoHumanMail();
			dtoMail.setBeginDate(comDate.dateToString(dto.getBeginDate(), "yyyy-MM-dd"));
			dtoMail.setEndDate(comDate.dateToString(dto.getEndDate(), "yyyy-MM-dd"));
			dtoMail.setStandardHours(dto.getStandarHours());
			dtoMail.setActualHours(dto.getActualHours());
			dtoMail.setNormalHours(dto.getNormalHours());
			dtoMail.setOvertimeHours(dto.getOvertimeHours());
			dtoMail.setLeaveHours(dto.getLeaveHours());
			dtoMail.setVariantHours(dto.getBalance().doubleValue());
			al = new ArrayList();
			cb = new ContentBean();
			
			al.add(dtoMail);
			dtoUser = ldapUtil.findUser(LDAPUtil.DOMAIN_ALL, dto.getEmpId());
			if (dtoUser != null) {
				userMail = dtoUser.getEmail();
				userName = dtoUser.getUserName();
				cb.setUser(userName);
				cb.setEmail(userMail);
				cb.setMailcontent(al);
				hm.put(userName, cb);
			}
		}
		sendMail(vmFile, "Check TimeCard", hm);
	}
	/**
	 * 发送邮件
	 * @param vmFile
	 * @param title
	 * @param hm
	 */
	private void sendMail(final String vmFile, final String title, final HashMap hm) {
		final SendHastenMail shMail = new SendHastenMail();
        Thread t = new Thread(new Runnable() {
            public void run() {
                shMail.sendHastenMail(vmFile, title, hm);
            }
        });
        try {
            t.start();
        } catch (Throwable tx) {
            tx.printStackTrace();
            t.stop();
        }
	}
}
