package server.essp.timesheet.outworker;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import c2s.dto.DtoTreeNode;
import c2s.dto.ITreeNode;
import c2s.essp.timesheet.outwork.DtoPrivilege;
import c2s.essp.timesheet.outwork.DtoUser;
import server.essp.security.service.role.RoleServiceImpl;
import server.essp.timesheet.outwork.privilege.dao.IOutWorkerPrivilegeDao;
import server.essp.timesheet.outwork.privilege.dao.IOutWorkerPrivilegeDbDao;
import server.essp.timesheet.outwork.privilege.service.OutWorkerPriImpService;
import junit.framework.TestCase;

public class TestOutWorkerPriServiceImp extends TestCase {
        private OutWorkerPriImpService owPriService;
        private IOutWorkerPrivilegeDbDao owPrivilegeDbMock;
        private IOutWorkerPrivilegeDao owPrivilegeMock;
        private static Map<String, Object> dataMap;
        static {
    		dataMap = new HashMap<String, Object>();
    		List userList = new ArrayList();
    		DtoUser dtoUser = new DtoUser();
    		dtoUser.setLoginId("WH0607011");
    		dtoUser.setLoginName("ttt");
    		dtoUser.setRole("TTTTTTT");
    		userList.add(dtoUser);    		
    		dataMap.put("userList",userList);
    		
    		
    		List rootList=new ArrayList();
    		DtoPrivilege pri=new DtoPrivilege();
    		pri.setLoginId("wh0607016");
    		pri.setLoginName("");
    		pri.setAccountId("W0000");
    		pri.setAccountName("11111");    		
    		pri.setIsPrivilege(false);    	
    		rootList.add(pri);
    		dataMap.put("root",rootList);
    		
     		List privilegeList=new ArrayList();
     		pri=new DtoPrivilege();
     		pri.setLoginId("wh0607016");
     		pri.setLoginName("");
     		pri.setAccountId("W3100");
     		pri.setAccountName("2222");    		
     		pri.setIsPrivilege(false);    	
    		privilegeList.add(pri);
    		dataMap.put("part1",privilegeList);
    		
    		List privilegeList2=new ArrayList();
    		pri=new DtoPrivilege();
    		pri.setLoginId("wh0607016");
    		pri.setLoginName("");
    		pri.setAccountId("W3120");
    		pri.setAccountName("3333");    		
    		pri.setIsPrivilege(true);    	
    		privilegeList2.add(pri);
    		dataMap.put("part2",privilegeList2);
    		
        }
        
    	protected void setUp() throws Exception {
    		super.setUp();
    		owPriService = new OutWorkerPriImpService();
    		owPrivilegeDbMock = createMock(IOutWorkerPrivilegeDbDao.class);
            owPrivilegeMock = createMock(IOutWorkerPrivilegeDao.class);
    	}


    	protected void tearDown() throws Exception {
    		owPriService = null;
    		super.tearDown();
    	}
    	
    	public void testListUserInfoSuccess() throws Exception{
    		    List userLst = (List)dataMap.get("userList");
	    		expect(owPrivilegeDbMock.listUser())
	            .andReturn(userLst);
	            replay(owPrivilegeDbMock);
	            owPriService.setOutworkPrivilegeDbDao(owPrivilegeDbMock);
	           List uList = owPriService.listUserInfo();
//	           owPriService.setRoleService(roleService);
               assertNotNull("result is null", uList);
               assertEquals("result List size is 1",uList.size(),1);
    	}
    	
    	public void testDelUserSuccess() throws Exception{
    		owPrivilegeDbMock.delete("wh0607016");    
    		expectLastCall().once();
    		replay(owPrivilegeDbMock);
    		owPriService.setOutworkPrivilegeDbDao(owPrivilegeDbMock);
    		owPriService.delOutworkerPrivilege("wh0607016");     		
    	}
    	
    	public void testLoadQueyPri()throws Exception{
    		List list1 = (List)dataMap.get("root");
    		List list2 =  (List)dataMap.get("part1");
    		List list3 = (List)dataMap.get("part2");
    		expect(owPrivilegeDbMock.loadPrivilege("wh0607016","W0000",true)).andReturn(list1);
    		expect(owPrivilegeDbMock.loadPrivilege("wh0607016","W0000",false)).andReturn(list2);
    		expect(owPrivilegeDbMock.loadPrivilege("wh0607016","W3100",false)).andReturn(new ArrayList());
//    		expectLastCall().once();
    		replay(owPrivilegeDbMock);
    		owPriService.setOutworkPrivilegeDbDao(owPrivilegeDbMock);
    		ITreeNode treenode=owPriService.loadQueryPrivilege("wh0607016");
    		assertNotNull("result is null",treenode);	    		
    	}
    	
    	public void testSaveOutWorkerPri()throws Exception{
    		DtoPrivilege pri=new DtoPrivilege();
    		pri.setLoginId("wh0607016");
    		pri.setLoginName("");
    		pri.setAccountId("W0000");
    		pri.setAccountName("11111");  
    		pri.setIsPrivilege(false);    	
    		ITreeNode treenode= new DtoTreeNode(pri);
    		
     		DtoPrivilege childPri=new DtoPrivilege();
     		childPri.setLoginId("wh0607016");
     		childPri.setLoginName("");
     		childPri.setAccountId("W3200");
     		childPri.setAccountName("11111");    		
     		childPri.setIsPrivilege(true);
    		ITreeNode child= new DtoTreeNode(childPri);
    		treenode.addChild(child);
    	
    		owPrivilegeDbMock.delete("wh0607016");
//    		owPrivilegeMock.insert(pri);
//    		owPrivilegeDbMock.add(childPri);
//    		expect(owPrivilegeDbMock.loadPrivilege("wh0607016", "W0000", true))
//				.andReturn((List) dataMap.get("priList"));
    		replay(owPrivilegeDbMock);
    		owPriService.setOutworkPrivilegeDbDao(owPrivilegeDbMock);    
    		owPriService.saveOutWorkerPri("wh0607016",treenode);
    		
    	}
        
}
