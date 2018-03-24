package server.essp.timesheet.rmmaint.service;

import static org.easymock.EasyMock.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import c2s.essp.common.user.DtoUser;

import com.wits.util.ThreadVariant;

import db.essp.timesheet.TsRmMaint;

import junit.framework.TestCase;
import server.essp.timesheet.rmmaint.dao.IRmMaintDao;
import server.essp.timesheet.rmmaint.service.RmMaintServiceImp;

public class TestRmMaintServiceImp extends TestCase {

	private RmMaintServiceImp rmMaintService;

	private IRmMaintDao rmMaintDaoMock;

	private static Map<String, Object> dataMap;

	static {
		dataMap = new HashMap<String, Object>();
		TsRmMaint tsRmMaint = new TsRmMaint();
		tsRmMaint.setLoginId("WH0607015");
		tsRmMaint.setRmId("WH0607015");
		dataMap.put("tsRmMaint", tsRmMaint);
	}

	protected void setUp() throws Exception {
		super.setUp();
		rmMaintService = new RmMaintServiceImp();
		rmMaintDaoMock = createMock(IRmMaintDao.class);
	}

	protected void tearDown() throws Exception {
		rmMaintService = null;
		super.tearDown();
	}

	public void testGetUserInfo() {
		ThreadVariant thread = ThreadVariant.getInstance();
		DtoUser user = new DtoUser();
		user.setUserLoginId("WH0607015");
		user.setPassword("666");
		user.setDomain("wh");
		user.setUserName("wenhaizheng");
		thread.set(DtoUser.SESSION_USER, user);
		thread.set(DtoUser.SESSION_LOGIN_USER, user);
		String loginIds = "WH0607015,WH0607016";
		List userList = rmMaintService.getUserInfo(loginIds);
		assertNotNull("userList is null", userList);
	}

	public void testGetRmByLoginId_AD() {
		expect(rmMaintDaoMock.getRmByLoginId("WH0607015")).andReturn(null);
		ThreadVariant thread = ThreadVariant.getInstance();
		DtoUser user = new DtoUser();
		user.setUserLoginId("WH0607015");
		user.setPassword("666");
		user.setDomain("wh");
		user.setUserName("wenhaizheng");
		thread.set(DtoUser.SESSION_USER, user);
		thread.set(DtoUser.SESSION_LOGIN_USER, user);
		replay(rmMaintDaoMock);
		rmMaintService.setRmMaintDao(rmMaintDaoMock);
		String rmId = rmMaintService.getRmByLoginId("WH0607015");
		assertNotNull("rm id is null", rmId);
		assertEquals("rm id is not WH", "WH0302008", rmId);
	}

	public void testGetRmByLoginId_DB() {
		expect(rmMaintDaoMock.getRmByLoginId("WH0607015")).andReturn(
				(TsRmMaint) dataMap.get("tsRmMaint"));
		ThreadVariant thread = ThreadVariant.getInstance();
		DtoUser user = new DtoUser();
		user.setUserLoginId("WH0607015");
		user.setPassword("666");
		user.setDomain("wh");
		user.setUserName("wenhaizheng");
		thread.set(DtoUser.SESSION_USER, user);
		thread.set(DtoUser.SESSION_LOGIN_USER, user);
		replay(rmMaintDaoMock);
		rmMaintService.setRmMaintDao(rmMaintDaoMock);
		String rmId = rmMaintService.getRmByLoginId("WH0607015");
		assertNotNull("rm id is null", rmId);
		assertEquals("rm id is not WH", "WH0607015", rmId);
	}

	public void testSaveOrUpdateRm_save() {
		ThreadVariant thread = ThreadVariant.getInstance();
		DtoUser user = new DtoUser();
		user.setUserLoginId("WH0607015");
		user.setPassword("666");
		user.setDomain("wh");
		user.setUserName("wenhaizheng");
		thread.set(DtoUser.SESSION_USER, user);
		thread.set(DtoUser.SESSION_LOGIN_USER, user);
		expect(rmMaintDaoMock.getRmByLoginId("WH0607015")).andReturn(null);
		rmMaintDaoMock.addRmMaint((TsRmMaint)anyObject());
		expectLastCall().once();
		replay(rmMaintDaoMock);
		rmMaintService.setRmMaintDao(rmMaintDaoMock);
		rmMaintService.saveOrUpdateRm("WH0607015", "WH0607015");
	}

	public void testSaveOrUpdateRm_update() {
		ThreadVariant thread = ThreadVariant.getInstance();
		DtoUser user = new DtoUser();
		user.setUserLoginId("WH0607015");
		user.setPassword("666");
		user.setDomain("wh");
		user.setUserName("wenhaizheng");
		thread.set(DtoUser.SESSION_USER, user);
		thread.set(DtoUser.SESSION_LOGIN_USER, user);
		expect(rmMaintDaoMock.getRmByLoginId("WH0607015"))
		                      .andReturn((TsRmMaint) dataMap.get("tsRmMaint"));
		rmMaintDaoMock.updateRmMaint((TsRmMaint)anyObject());
		expectLastCall().once();
		replay(rmMaintDaoMock);
		rmMaintService.setRmMaintDao(rmMaintDaoMock);
		rmMaintService.saveOrUpdateRm("WH0607015", "WH0607015");
	}
	public void testDelRm() {
		expect(rmMaintDaoMock.getRmByLoginId("WH0607015"))
        					 .andReturn((TsRmMaint) dataMap.get("tsRmMaint"));
		rmMaintDaoMock.delRmMaint((TsRmMaint)anyObject());
		expectLastCall().once();
		replay(rmMaintDaoMock);
		rmMaintService.setRmMaintDao(rmMaintDaoMock);
		rmMaintService.delRm("WH0607015");
	}
}
