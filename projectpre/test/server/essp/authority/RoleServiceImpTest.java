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

	//��������Roleʱ,Role�����Ѵ���
	public void testAddRoleNameExsit(){
		//�趨Mock�������Ϊ,����"Admin"��ɫʱ,����role1
		Role role1 = new Role();
		role1.setName("Admin");
		role1.setRoleId("1");
		mockDAO.get("Admin");
		control.setReturnValue(role1);

		Role role2 = new Role();
		role2.setName("Admin");
		role2.setRoleId("1");
		/* ��Mock�������в��� */   
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
	 * ���Ը���Roleʱ,Role�����Ѵ���
	 */
	public void testUpdateRoleNameExsit() {
//		�趨Mock�������Ϊ,����"Admin"��ɫʱ,����role1
		Role role1 = new Role();
		role1.setName("Admin");
		role1.setRoleId("1");
		mockDAO.get("Admin");
		control.setReturnValue(role1);

		Role role2 = new Role();
		role2.setName("Admin");
		role2.setRoleId("2");
		/* ��Mock�������в��� */   
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
	 * ���Բ����ڵ�Role��״̬
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
	 * ���Դ��ڵ�Role��״̬
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
	 * ���Դ��ڵ�Role��״̬
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
	//���Ա��������û���Ӧ�Ľ�ɫ�б�
	public void testSaveOrUpdateUserRole(){
		
	}
}
