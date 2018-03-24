package server.essp.authority;

import org.easymock.MockControl;

import server.essp.common.authority.Role;
import server.essp.common.authority.dao.RoleDAO;
import server.essp.common.authority.service.RoleServiceImp;
import server.framework.common.BusinessException;
import server.framework.common.Constant;

import junit.framework.TestCase;

public class RoleServiceImpTest extends TestCase {
	RoleServiceImp roleService = null;
	MockControl control = MockControl.createControl(RoleDAO.class);
	RoleDAO mockDAO = (RoleDAO) control.getMock();
	protected void setUp() throws Exception {
		super.setUp();
		roleService = new RoleServiceImp();
		roleService.setRoleDAO(mockDAO);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		roleService = null;
	}

	//测试增加Role时,Role名字已存在
	public void testAddRoleNameExsit(){
		//设定Mock对象的行为,查找"Admin"角色时,返回role1
		Role role1 = new Role();
		role1.setName("Admin");
		role1.setRoleId("1");
		mockDAO.get("Admin");
		control.setReturnValue(role1);

		Role role2 = new Role();
		role2.setName("Admin");
		role2.setRoleId("1");
		/* 带Mock对象运行测试 */   
		control.replay();

		try{
			roleService.addRole(role2);
			this.fail("role has existed");
		}catch(BusinessException ex){
			this.assertEquals("role has existed","role.error.nameexist",ex.getErrorCode());
		}
		control.verify();
	}

	/*
	 * 测试更新Role时,Role名字已存在
	 */
	public void testUpdateRoleNameExsit() {
//		设定Mock对象的行为,查找"Admin"角色时,返回role1
		Role role1 = new Role();
		role1.setName("Admin");
		role1.setRoleId("1");
		mockDAO.get("Admin");
		control.setReturnValue(role1);

		Role role2 = new Role();
		role2.setName("Admin");
		role2.setRoleId("2");
		/* 带Mock对象运行测试 */   
		control.replay();

		try{
			roleService.updateRole(role2);
			this.fail("role has existed");
		}catch(BusinessException ex){
			this.assertEquals("role has existed","role.error.nameexist",ex.getErrorCode());
		}
		control.verify();
	}

	/*
	 * 测试不存在的Role的状态
	 */
	public void testIsEnabledWithoutRole() {
		mockDAO.get("Admin");
		control.setReturnValue(null);
		control.replay();
		try{
			roleService.isEnabled("Admin");
			this.fail("role doesnot existed");
		}catch(BusinessException ex){
			this.assertEquals("role doesnot existed","role.error.notexist",ex.getErrorCode());
			
		}
		control.verify();
	}
	
	/**
	 * 测试存在的Role的状态
	 */
	public void testIsEnabled(){
		Role role1 = new Role();
		role1.setName("Admin");
		role1.setRoleId("1");
		role1.setStatus(Constant.RST_ENABLED);
		mockDAO.get("Admin");
		control.setReturnValue(role1);
		control.replay();
		
		this.assertEquals("enabled role",true,roleService.isEnabled("Admin"));
		control.verify();		
	}

	/**
	 * 测试存在的Role的状态
	 */
	public void testIsDisabled(){
		Role role1 = new Role();
		role1.setName("Admin");
		role1.setRoleId("1");
		role1.setStatus(Constant.RST_DISABLED);
		mockDAO.get("Admin");
		control.setReturnValue(role1);
		control.replay();
		
		this.assertEquals("disabled role",false,roleService.isEnabled("Admin"));
		control.verify();		
	}
	//测试保存或更新用户对应的角色列表
	public void testSaveOrUpdateUserRole(){
		
	}
}
