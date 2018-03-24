package server.essp.timesheet.period.service;

import static org.easymock.EasyMock.*;

import java.util.*;

import junit.framework.TestCase;
import server.essp.timesheet.period.dao.IPeriodDao;
import server.essp.timesheet.period.dao.IPeriodP3DbDao;
import server.essp.timesheet.period.service.TsDatesServiceImp;
import server.essp.timesheet.preference.dao.IPreferenceDao;
import c2s.essp.timesheet.tsdates.DtoTsDates;
import c2s.essp.timesheet.weeklyreport.DtoTimeSheetPeriod;

import com.wits.util.comDate;

import db.essp.timesheet.TsParameter;

public class TestTsDatesServiceImp extends TestCase {
	
	private TsDatesServiceImp tsDatesService;
	private IPeriodDao periodDaoMock;
	private IPreferenceDao preferenceDaoMock;
	private IPeriodP3DbDao periodP3DbDaoMock;
	private static Map<String, Object> dataMap;
	static {
		dataMap = new HashMap<String, Object>();
		List periodList = new ArrayList();
		DtoTimeSheetPeriod dtoPeriod = new DtoTimeSheetPeriod();
		dtoPeriod.setBeginDate(comDate.toDate("2007-06-01"));
		dtoPeriod.setEndDate(comDate.toDate("2007-06-07"));
		periodList.add(dtoPeriod);
		dataMap.put("dtoPeriod", dtoPeriod);
		dtoPeriod = new DtoTimeSheetPeriod();
		dtoPeriod.setBeginDate(comDate.toDate("2007-06-08"));
		dtoPeriod.setEndDate(comDate.toDate("2007-06-14"));
		periodList.add(dtoPeriod);
		dtoPeriod = new DtoTimeSheetPeriod();
		dtoPeriod.setBeginDate(comDate.toDate("2007-06-15"));
		dtoPeriod.setEndDate(comDate.toDate("2007-06-21"));
		periodList.add(dtoPeriod);
		dataMap.put("periodList", periodList);
		DtoTsDates dtoTsDates = new DtoTsDates();
		dtoTsDates.setCreateWay(DtoTimeSheetPeriod.CREATE_WAY_EVERYWEEK);
		dtoTsDates.setStartDate(comDate.toDate("2007-06-22"));
		dtoTsDates.setEndDate(comDate.toDate("2007-06-28"));
		dtoTsDates.setEndsOn(1);
		dataMap.put("dtoEveryWeek", dtoTsDates);
		dtoTsDates = new DtoTsDates();
		dtoTsDates.setCreateWay(DtoTimeSheetPeriod.CREATE_WAY_EVERYMONTH);
		dtoTsDates.setStartDate(comDate.toDate("2007-06-01"));
		dtoTsDates.setEndDate(comDate.toDate("2007-06-30"));
		dtoTsDates.setEndsOn(1);
		dataMap.put("dtoEveryMonth1", dtoTsDates);
		dtoTsDates = new DtoTsDates();
		dtoTsDates.setCreateWay(DtoTimeSheetPeriod.CREATE_WAY_EVERYMONTH);
		dtoTsDates.setStartDate(comDate.toDate("2007-06-30"));
		dtoTsDates.setEndDate(comDate.toDate("2007-08-01"));
		dtoTsDates.setEndsOn(1);
		dataMap.put("dtoEveryMonth2", dtoTsDates);
		TsParameter tsPara = new TsParameter();
		tsPara.setMonthStartDay(Long.valueOf(26));
		dataMap.put("tsPara", tsPara);
	}
	protected void setUp() throws Exception {
		super.setUp();
		tsDatesService = new TsDatesServiceImp();
		periodDaoMock = createMock(IPeriodDao.class);
		preferenceDaoMock = createMock(IPreferenceDao.class);
		periodP3DbDaoMock = createMock(IPeriodP3DbDao.class);
	}

	protected void tearDown() throws Exception {
		tsDatesService = null;
		super.tearDown();
	}
	
	
	public void testGetLastTsDate() throws Exception{
		expect(periodDaoMock.getLastEndPeriod())
		              .andReturn((DtoTimeSheetPeriod)dataMap.get("dtoPeriod"));
		replay(periodDaoMock);
		tsDatesService.setPeriodDao(periodDaoMock);
		DtoTimeSheetPeriod result = tsDatesService.getLastTsDate();
		assertNotNull("result is null", result);
	}
	
	public void testGetTsDatesList() throws Exception {
		expect(periodDaoMock.getAllTimeSheetPeriod())
								.andReturn((List)dataMap.get("periodList"));
		replay(periodDaoMock);
		tsDatesService.setPeriodDao(periodDaoMock);
		List result = tsDatesService.getTsDatesList();
		assertNotNull("result is null", result);
	}
	public void testCreateTsDates_week() throws Exception {
		expect(periodDaoMock.isDuplicate((Date)anyObject(),(Date)anyObject())).andReturn(false);
		expect(periodP3DbDaoMock.getNewTsDatesId())
											.andReturn(Long.valueOf(1)).anyTimes();
		expect(preferenceDaoMock.loadPreference())
											.andReturn((TsParameter)dataMap.get("tsPara"));
		periodP3DbDaoMock.insertPeriod((DtoTimeSheetPeriod)anyObject());
		expectLastCall().anyTimes();
		replay(periodP3DbDaoMock);
		replay(preferenceDaoMock);
		replay(periodDaoMock);
		tsDatesService.setPeriodP3DbDao(periodP3DbDaoMock);
		tsDatesService.setPreferenceDao(preferenceDaoMock);
		tsDatesService.setPeriodDao(periodDaoMock);
		tsDatesService.createTsDates((DtoTsDates)dataMap.get("dtoEveryWeek"));
		
	}
	public void testCreateTsDates_month1() throws Exception{
		expect(periodDaoMock.isDuplicate((Date)anyObject(),(Date)anyObject())).andReturn(false);
		expect(periodP3DbDaoMock.getNewTsDatesId()).andReturn(Long.valueOf(1))
				.anyTimes();
		expect(preferenceDaoMock.loadPreference()).andReturn(
				(TsParameter) dataMap.get("tsPara"));
		periodP3DbDaoMock.insertPeriod((DtoTimeSheetPeriod) anyObject());
		expectLastCall().anyTimes();
		replay(periodP3DbDaoMock);
		replay(preferenceDaoMock);
		replay(periodDaoMock);
		tsDatesService.setPeriodP3DbDao(periodP3DbDaoMock);
		tsDatesService.setPreferenceDao(preferenceDaoMock);
		tsDatesService.setPeriodDao(periodDaoMock);
		tsDatesService.createTsDates((DtoTsDates)dataMap.get("dtoEveryMonth1"));
	}
	public void testCreateTsDates_month2() throws Exception {
		expect(periodDaoMock.isDuplicate((Date)anyObject(),(Date)anyObject())).andReturn(false);
		expect(periodP3DbDaoMock.getNewTsDatesId()).andReturn(Long.valueOf(1))
				.anyTimes();
		expect(preferenceDaoMock.loadPreference()).andReturn(
				(TsParameter) dataMap.get("tsPara")).anyTimes();
		periodP3DbDaoMock.insertPeriod((DtoTimeSheetPeriod) anyObject());
		expectLastCall().anyTimes();
		replay(periodP3DbDaoMock);
		replay(preferenceDaoMock);
		replay(periodDaoMock);
		tsDatesService.setPeriodP3DbDao(periodP3DbDaoMock);
		tsDatesService.setPreferenceDao(preferenceDaoMock);
		tsDatesService.setPeriodDao(periodDaoMock);
		tsDatesService
				.createTsDates((DtoTsDates) dataMap.get("dtoEveryMonth2"));
	}
	public void testAddPeriod() throws Exception {
		expect(periodDaoMock.isDuplicate((Date)anyObject(),(Date)anyObject())).andReturn(false);
		expect(periodP3DbDaoMock.getNewTsDatesId()).andReturn(Long.valueOf(1))
				.anyTimes();
		expect(preferenceDaoMock.loadPreference()).andReturn(
				(TsParameter) dataMap.get("tsPara")).anyTimes();
		periodP3DbDaoMock.insertPeriod((DtoTimeSheetPeriod) anyObject());
		expectLastCall().anyTimes();
		replay(periodP3DbDaoMock);
		replay(preferenceDaoMock);
		replay(periodDaoMock);
		tsDatesService.setPeriodP3DbDao(periodP3DbDaoMock);
		tsDatesService.setPreferenceDao(preferenceDaoMock);
		tsDatesService.setPeriodDao(periodDaoMock);
		DtoTimeSheetPeriod dtoPeriod = new DtoTimeSheetPeriod();
		dtoPeriod.setBeginDate(comDate.toDate("2007-06-22"));
		dtoPeriod.setEndDate(comDate.toDate("2007-06-28"));
		tsDatesService.addPeriod(dtoPeriod);
	}
	public void testDetelePeriod() {
		periodP3DbDaoMock.deletePeriod((Long)anyObject());
		expectLastCall().anyTimes();
		replay(periodP3DbDaoMock);
		tsDatesService.setPeriodP3DbDao(periodP3DbDaoMock);
		tsDatesService.detelePeriod((List)dataMap.get("periodList"));
		
	}
	
}
